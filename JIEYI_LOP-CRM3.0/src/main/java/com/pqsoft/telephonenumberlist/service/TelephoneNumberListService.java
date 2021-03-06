package com.pqsoft.telephonenumberlist.service;

import java.util.*;
import java.util.concurrent.*;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.telephonenumberlist.model.*;
import com.pqsoft.telephonenumberlist.model.report.*;
import com.pqsoft.util.HttpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;

/**
 * 电话清单-聚信立采集接口
 * @author wanghuaying
 *
 */
public class TelephoneNumberListService {

	private static volatile TelephoneNumberListService telephoneNumberListService;
	private String xmlPath = "CollectReportMapper.";
	private static final String APPLICATION_URL = "https://www.juxinli.com/orgApi/rest/v2/applications/";
	private static final String COLLECT_URL = "https://www.juxinli.com/orgApi/rest/v2/messages/collect/req";
	private static final String TOKEN_URL = "https://www.juxinli.com/api/access_report_token";
	private static final String REPORT_URL = "https://www.juxinli.com/api/access_report_data";
	private static final String ORG_CODE = "jiezhong";
	private static final String SECRET = "5c8f30ff17fb43a4b802c8e2185031f3";
	// 获取电话清单进行重试的最大次数
	private static final Integer MAX_RETRY_TIMES = 30;
	// 重试间隔时间
	private static final Integer IDLE_SECONDS = 300;
	// 线程池
	private ExecutorService executorService;
	// 缓存容器 用以判断目标客户的线程是否开启
	private Map<String, Object> threadMap;
	private static final String APPLICATION_RESPONSE = "100";
	private static final String COLLECT_RESPONSE = "200";
	private static final String REPORT_RESPONSE = "300";
	private static final Logger logger = Logger.getLogger(TelephoneNumberListService.class);

	/**
	 * 单例保证只初始化一个线程池
	 */
	public static TelephoneNumberListService getInstance(){
		if(telephoneNumberListService == null){
			synchronized (TelephoneNumberListService.class){
				if(telephoneNumberListService == null){
					telephoneNumberListService = new TelephoneNumberListService();
				}
			}
		}
		return telephoneNumberListService;
	}

	/**
	 * 初始化线程池和判断线程是否开启的缓存容器
	 */
	private TelephoneNumberListService(){
		executorService = Executors.newFixedThreadPool(32);
		threadMap = new ConcurrentHashMap<>();
	}

	/**
	 * 调用通信数据申请的接口
	 *
	 * @param paramString json格式参数
	 * @return 响应对象
	 */
	public Response application(String paramString){
		// 参数包装对象的子对象
		BasicInfo basicInfo = JSON.parseObject(paramString, BasicInfo.class);
		// 组装通信数据包装对象
		CollectReport collectReport = assembleQueryParam(basicInfo);
		Response response;
		synchronized (basicInfo.getIdCardNum().intern()){
			// 报告检验，判断是否已有符合条件的报告数据
			response = checkReport(collectReport);
			if(response == null){
				response = sendApplication(basicInfo);
			}
		}
		return response;
	}

	/**
	 * 根据已有的响应对象判断是否需要发送申请
	 *
	 * @param basicInfo 客户信息封装对象
	 * @return 响应对象
	 */
	private Response sendApplication(BasicInfo basicInfo) {
		// 申请数据接口的参数包装对象
		Application object = new Application();
		object.setBasicInfo(basicInfo);
		object.setUid("APP_ID");
		// 调用申请接口
		String result = HttpUtil.jsonPost(APPLICATION_URL + ORG_CODE, object);
		ApplicationResponse applicationResponse = JSON.parseObject(result, ApplicationResponse.class);
		if (applicationResponse.isSuccess()) {
			applicationResponse.setMessage(APPLICATION_RESPONSE);
			String password = Dao.selectOne(xmlPath + "selectCellPasswordByIdCardNum", basicInfo.getIdCardNum());
			ApplicationResponseData applicationResponseData = applicationResponse.getApplicationResponseData();
			applicationResponseData.setPassword(password);
			applicationResponseData.setName(basicInfo.getName());
			applicationResponseData.setIdCardNum(basicInfo.getIdCardNum());
		}
		return applicationResponse;
	}

	/**
	 * 检查是否存在符合条件的报告，并根据检查结果获取报告或者等待信息
	 *
	 * @param queryCollectReport 查询报告的参数封装对象
	 * @return 报告或者等待信息
	 */
	private Response checkReport(CollectReport queryCollectReport) {
		// 查找符合条件的最新的请求报告,数据库也需要加锁
		CollectReport collectReport = queryCollectReportForUpdate(queryCollectReport);
		// 发送过请求
		if(collectReport != null){
			// 收到报告
			if(collectReport.getDataReport() != null){
				// 返回报告
				ReportResponse reportResponse = new ReportResponse();
				reportResponse.setSuccess(true);
				reportResponse.setMessage(REPORT_RESPONSE);
				return reportResponse;
			}
			// 未收到报告
			else{
				// 检查是否已开启线程
				checkThread(collectReport);
				// 返回等待页面
				CollectResponse collectResponse = new CollectResponse();
				collectResponse.setSuccess(true);
				collectResponse.setMessage(COLLECT_RESPONSE);
				return collectResponse;
			}
		}
		// 未发送过请求
		return null;
	}

	private CollectReport queryCollectReportForUpdate(CollectReport queryCollectReport) {
		Preconditions.checkNotNull(queryCollectReport, "通话记录对象为空！");
		CollectReport resultCollectReport = Dao.selectOne(xmlPath + "selectCollectReport", queryCollectReport);
		queryReportItem(resultCollectReport, queryCollectReport);
		return resultCollectReport;
	}

	/**
	 * 检查是否已开启线程在进行获取对应客户的报告的工作
	 *
	 * @param collectReport 报告参数封装对象
	 */
	private void checkThread(CollectReport collectReport) {
		// 缓存获取对应客户身份号的状态
		Object o = threadMap.get(collectReport.getCollect().getIdCardNum());
		// 未存在该客户的线程
		if(o == null){
			openThread(collectReport);
		}
	}

	/**
	 * 调用报告数据收集请求的接口
	 *
	 * @param paramString json格式参数
	 * @return 报告数据或者等待信息
	 */
	public Response collect(String paramString) {
		// 收集请求的参数封装对象
		Collect collect = JSON.parseObject(paramString, Collect.class);
		CollectReport collectReport = assembleQueryParam(collect);
		Response response;
		synchronized(collect.getIdCardNum().intern()){
			// 检查是否已获取报告
			response = checkReport(collectReport);
			if(response == null){
				response = sendCollect(collectReport);
			}
		}
		return response;
	}

	/**
	 * 发送收集请求
	 *
	 * @param collectReport 收集请求参数封装
	 * @return 响应对象
	 */
	private Response sendCollect(CollectReport collectReport) {
		Collect collect = collectReport.getCollect();
		String result = HttpUtil.jsonPost(COLLECT_URL, collect);
		CollectResponse collectResponse = JSON.parseObject(result, CollectResponse.class);
		// 收集请求成功时有可能需要短信验证码有可能流程结束
		if(collectResponse.isSuccess()){
			collectResponse.setMessage(COLLECT_RESPONSE);
			// 流程结束，通过返回处理码来判断
			if(collectResponse.getData().getProcessCode() == 10008){
				// 储存申请记录
				collect.setTime(new Date());
				saveCollect(collect);
				// 开线程获取数据报告
				openThread(collectReport);
			}
		}
		// 返回页面信息
		return collectResponse;
	}

	/**
	 * 开启新线程尝试获取报告数据
	 *
	 * @param collectReport 收集报告的数据封装对象
	 */
	private void openThread(CollectReport collectReport) {
		final Collect collect = collectReport.getCollect();
		final String idCardNum = collect.getIdCardNum();
		final String name = collect.getName();
		final String cellPhone = collect.getAccount();
		final String collectToken = collect.getToken();
		logger.debug(idCardNum + " 新线程加入!");
		// 开启新线程获取报告
		executorService.execute(new Runnable(){
			@Override
			public void run() {
				try{

					int retryTimes = 0;
					String token = getAccessToken();
					while(true){
						Thread.sleep(IDLE_SECONDS * 1000);

						Map<String, String> params = new HashMap<>();
						params.put("access_token", token);
						params.put("client_secret", SECRET);
						params.put("name", name);
						params.put("idcard", idCardNum);
						params.put("phone", cellPhone);
						String result = HttpUtil.httpGet(REPORT_URL, params);
						ReportResponse reportResponse =  JSON.parseObject(result, ReportResponse.class, Feature.UseBigDecimal);
						// 成功获取到报告
						if(reportResponse.isSuccess() && collectToken.equals(reportResponse.getDataReport().getReport().getToken())){
							// 持久化报告
							if(saveDataReport(reportResponse.getDataReport()) != null){
								// 持久化成功
								Dao.commit();
								break;
							}
						}

						retryTimes++;
						if(retryTimes == MAX_RETRY_TIMES){
							throw new ActionException("没有匹配token的电话清单报告！");
						}
					}
				}
				// 接口调用有可能会因网络状态不稳定导致异常
				catch (Exception e){
					Dao.rollback();
					logger.error("获取数据报告异常, 姓名：" + name + ", 身份证号：" + idCardNum + ", 手机号：" + cellPhone, e);
				}
				finally {
					// 移除缓存线程
					Dao.close();
					threadMap.remove(idCardNum);
					logger.debug(idCardNum + " 线程结束！");
				}
			}

			private String getAccessToken() {
				Map<String, String> params = new HashMap<>();
				params.put("org_name", ORG_CODE);
				params.put("client_secret", SECRET);
				params.put("hours", "24");
				String result = HttpUtil.httpGet(TOKEN_URL, params);
				@SuppressWarnings("unchecked")
				Map<String, Object> resultMap = (Map<String, Object>) JSONUtils.parse(result);
				if(!BooleanUtils.toBoolean(String.valueOf(resultMap.get("success")))) {
					throw new ActionException("通信接口调用失败！" + resultMap.get("note"));
				}
				return (String) resultMap.get("access_token");
			}
		});
		threadMap.put(idCardNum, true);
	}

	/**
	 * 通过客户基本信息组装查询参数的封装对象
	 *
	 * @param basicInfo 客户基本信息
	 * @return 收集报告的数据封装对象
	 */
	private CollectReport assembleQueryParam(BasicInfo basicInfo) {
		String idCardNum = basicInfo.getIdCardNum();
		String cellPhoneNum = basicInfo.getCellPhoneNum();
		String name = basicInfo.getName();
		if(Strings.isNullOrEmpty(idCardNum)){
			throw new ActionException("客户身份证号获取失败！");
		}
		if(Strings.isNullOrEmpty(cellPhoneNum)){
			throw new ActionException("客户手机号获取失败！");
		}
		if(Strings.isNullOrEmpty(name)){
			throw new ActionException("客户姓名获取失败！");
		}
		Collect collect = new Collect();
		collect.setIdCardNum(idCardNum);
		collect.setAccount(cellPhoneNum);
		collect.setName(name);
		DataReport dataReport = new DataReport();
		Report report = new Report();
		report.setUpdt(basicInfo.getUpdt());
		dataReport.setReport(report);
		CollectReport collectReport = new CollectReport();
		collectReport.setCollect(collect);
		collectReport.setDataReport(dataReport);
		collectReport.setProjectId(basicInfo.getProjectId());
		return collectReport;
	}

	/**
	 * 通过收集请求参数组装查询参数的封装对象
	 *
	 * @param collect 收集请求信息
	 * @return 收集报告的数据封装对象
	 */
	private CollectReport assembleQueryParam(Collect collect){
		if(Strings.isNullOrEmpty(collect.getName())){
			throw new ActionException("客户姓名获取失败！");
		}
		if(Strings.isNullOrEmpty(collect.getAccount())){
			throw new ActionException("客户手机号获取失败！");
		}
		if(Strings.isNullOrEmpty(collect.getIdCardNum())){
			throw new ActionException("客户身份证号获取失败！");
		}
		if(Strings.isNullOrEmpty(collect.getWebsite())){
			throw new ActionException("客户手机号所属运营商信息获取失败！");
		}
		if(Strings.isNullOrEmpty(collect.getPassword())){
			throw new ActionException("客户服务密码获取失败！");
		}
		if(Strings.isNullOrEmpty(collect.getToken())){
			throw new ActionException("通信密码获取失败！");
		}

		CollectReport collectReport = new CollectReport();
		collectReport.setCollect(collect);
		DataReport dataReport = new DataReport();
		Report report = new Report();
		report.setUpdt(collect.getUpdt());
		dataReport.setReport(report);
		collectReport.setDataReport(dataReport);
		return collectReport;
	}

	/**
	 * 通过项目id查询关联的客户信息
	 *
	 * @param projectId 项目id
	 * @return 客户信息
	 */
	public Map<String,Object> getClientInfoByProjectId(String projectId) {
		if(Strings.isNullOrEmpty(projectId)){
			throw new ActionException("获取项目信息失败！");
		}
		return Dao.selectOne(xmlPath + "selectClientInfoByProjectId", projectId);
	}

	/**
	 * 查询符合条件的收集报告数据
	 *
	 * @param collectReport 条件查询参数
	 * @return 收集报告数据
	 */
	public CollectReport queryCollectReport(CollectReport collectReport){
		Preconditions.checkNotNull(collectReport, "通话记录对象为空！");
		CollectReport resultCollectReport = Dao.selectOne(xmlPath + "selectCollectReport", collectReport);
		queryReportItem(resultCollectReport, collectReport);
		return resultCollectReport;
	}

	/**
	 * 查询符合筛选条件的对应报告头的报告子项
	 *
	 * @param resultCollectReport 报告头信息对象
	 * @param queryCollectReport 报告子项查询条件
	 */
	private void queryReportItem(CollectReport resultCollectReport, CollectReport queryCollectReport) {
		// 报告头为空不做查询
		if(resultCollectReport != null){
			DataReport dataReport = resultCollectReport.getDataReport();
			// 报告头主体为空不做查询
			if(dataReport != null){
				List<com.pqsoft.telephonenumberlist.model.report.Contact> contacts = Dao.selectList(xmlPath + "selectContacts", queryCollectReport);
				List<DataSource> dataSources = Dao.selectList(xmlPath + "selectDataSources", queryCollectReport);
				List<Check> behaviorChecks = Dao.selectList(xmlPath + "selectBehaviorChecks", queryCollectReport);
				List<CollectionContact> collectionContacts = Dao.selectList(xmlPath + "selectCollectionContacts", queryCollectReport);
				if(collectionContacts.get(0) == null){
					collectionContacts = new ArrayList<>(0);
				}
				List<MainService> mainServices = Dao.selectList(xmlPath + "selectMainServices", queryCollectReport);
				if(mainServices.get(0) == null){
					mainServices = new ArrayList<>(0);
				}
				List<ContactRegion> contactRegions = Dao.selectList(xmlPath + "selectContactRegions", queryCollectReport);
				List<Check> applicationChecks = Dao.selectList(xmlPath + "selectApplicationChecks", queryCollectReport);
				List<TripInfo> tripInfos = Dao.selectList(xmlPath + "selectTripInfos", queryCollectReport);
				List<CellBehavior> cellBehaviors = Dao.selectList(xmlPath + "selectCellBehaviors", queryCollectReport);
				if(cellBehaviors.get(0) == null){
					cellBehaviors = new ArrayList<>(0);
				}
				List<EBusinessExpense> eBusinessExpenses = new ArrayList<>(0);
//						Dao.selectList(xmlPath + "selectEBusinessExpense", collectReport);
				List<TripConsume> tripConsumes = new ArrayList<>(0);
//						Dao.selectList(xmlPath + "selectTripConsumes", collectReport);
				List<DeliverAddress> deliverAddresses = new ArrayList<>(0);
//						Dao.selectList(xmlPath + "selectDeliverAddresses", collectReport);
				dataReport.setContacts(contacts);
				dataReport.setDataSources(dataSources);
				dataReport.setBehaviorChecks(behaviorChecks);
				dataReport.setCollectionContacts(collectionContacts);
				dataReport.setMainServices(mainServices);
				dataReport.setContactRegions(contactRegions);
				dataReport.setApplicationChecks(applicationChecks);
				dataReport.setTripInfos(tripInfos);
				dataReport.setCellBehaviors(cellBehaviors);
				dataReport.setEBusinessExpenses(eBusinessExpenses);
				dataReport.setTripConsumes(tripConsumes);
				dataReport.setDeliverAddresses(deliverAddresses);
			}
		}
	}

	/**
	 * 持久化收集请求的参数
	 *
	 * @param collect 收集请求参数对象
	 * @return 收集请求对象
	 */
	public Collect saveCollect(Collect collect){
		CollectReport collectReport = new CollectReport();
		collectReport.setCollect(collect);
		Dao.insert(xmlPath + "insertCollect", collectReport);
		return collect;
	}

	/**
	 * 持久化数据报告
	 *
	 * @param dataReport 数据报告
	 * @return 数据报告
	 */
	public DataReport saveDataReport(DataReport dataReport){
		Preconditions.checkNotNull(dataReport, "数据报告为空！");
		CollectReport collectReport = new CollectReport();
		collectReport.setDataReport(dataReport);
		int rowNum = Dao.update(xmlPath + "updateCollectReport", collectReport);
		if(rowNum == 0){
			return null;
		}
//		String reportId = dataReport.getReport().getId();
//		Dao.delete(xmlPath + "deleteContactsByReportId", reportId);
//		Dao.delete(xmlPath + "deleteDataSourcesByReportId", reportId);
//		Dao.delete(xmlPath + "deleteCollectionContactsByReportId", reportId);
//		Dao.delete(xmlPath + "deleteChecksByReportId", reportId);
//		Dao.delete(xmlPath + "deleteMainServicesByReportId", reportId);
//		Dao.delete(xmlPath + "deleteContactRegionsByReportId", reportId);
//		Dao.delete(xmlPath + "deleteTripInfosByReportId", reportId);
//		Dao.delete(xmlPath + "deleteBehaviorsByReportId", reportId);

		if(!CollectionUtils.isEmpty(dataReport.getContacts())){
			Dao.insert(xmlPath + "batchInsertContact", dataReport);
		}
		if(!CollectionUtils.isEmpty(dataReport.getDataSources())){
			Dao.insert(xmlPath + "batchInsertDataSource", dataReport);
		}
		if(!CollectionUtils.isEmpty(dataReport.getBehaviorChecks()) || !CollectionUtils.isEmpty(dataReport.getApplicationChecks())){
			Dao.insert(xmlPath + "batchInsertCheck", dataReport);
		}

		DataReport temp = new DataReport();
		temp.setReport(dataReport.getReport());
		for(CollectionContact collectionContact : dataReport.getCollectionContacts()){
			collectionContact.setDataReport(temp);
			Dao.insert(xmlPath + "insertCollectionContact", collectionContact);
			Dao.insert(xmlPath + "batchInsertContactDetail", collectionContact);
		}

		for(MainService mainService : dataReport.getMainServices()){
			mainService.setDataReport(temp);
			Dao.insert(xmlPath + "insertMainService", mainService);
			Dao.insert(xmlPath + "batchInsertServiceDetail", mainService);
		}


		if(!CollectionUtils.isEmpty(dataReport.getContactRegions())){
			Dao.insert(xmlPath + "batchInsertContactRegion", dataReport);
		}
		if(!CollectionUtils.isEmpty(dataReport.getTripInfos())){
			Dao.insert(xmlPath + "batchInsertTripInfo", dataReport);
		}

		for(CellBehavior cellBehavior: dataReport.getCellBehaviors()){
			cellBehavior.setDataReport(temp);
			Dao.insert(xmlPath + "insertCellBehavior", cellBehavior);
			Dao.insert(xmlPath + "batchInsertBehavior", cellBehavior);
		}
//        throw new RuntimeException("哈哈哈哈哈!");
		return dataReport;
	}
}

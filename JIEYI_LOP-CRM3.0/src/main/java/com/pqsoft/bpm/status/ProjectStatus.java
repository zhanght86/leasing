package com.pqsoft.bpm.status;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import payment.tools.util.GUID;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.base.grantCredit.service.SupplierCreditService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.bpm.service.MemoService;
import com.pqsoft.bpm.service.SystemMail;
import com.pqsoft.contract.service.BuyContractService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.delivery.service.InvoiceApplicationService;
import com.pqsoft.dossier.service.CreditCoreFileInfoService;
import com.pqsoft.fhfaManager.FhfaManagerService;
import com.pqsoft.leaseApplication.service.LeaseApplicationService;
import com.pqsoft.leeds.materialMgt.service.MaterialMgtService;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.osi.api.Mail;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.FIinterface;
import com.pqsoft.util.StringUtils;

/**
 * 项目状态变更维护
 * 
 * @author King 2013-9-22
 */
public class ProjectStatus {
	private String namespace = "bpm.status.";
	private static final Logger logger = Logger.getLogger(ProjectStatus.class);

	// private Map<String, Object> param = null;
	// private void getVeriable(String jbpmId) {
	// param = JBPM.getVeriable(jbpmId);
	// }

	/**
	 * 草稿 Draft
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void Draft(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "0");
			Dao.update(namespace + "updateProjectStatus", m);
		} else {
			throw new ActionException("草稿状态变更失败");
		}
	}

	/**
	 * 立项中 ProjectApprovaling
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void ProjectApprovaling(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "1");
			Dao.update(namespace + "updateProjectStatus", m);
		} else {
			throw new ActionException("立项中状态变更失败");
		}
	}

	/**
	 * 立项通过 ProjectApprovalPass
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void ProjectApprovalPass(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "10");
			Dao.update(namespace + "updateProjectStatus", m);
		} else {
			throw new ActionException("立项通过状态变更失败");
		}
	}
	
	/**
	 * 初始化历史车贷记录
	 * @param PROJECT_ID
	 * @author wanghl
	 * @datetime 2015年4月13日,下午3:17:21
	 */
	public void addAutoLoan(String PROJECT_ID){
		Map<String,Object> param = new HashMap<>();
//		String CLIENT_ID = "";
		param.put("PROJECT_ID", PROJECT_ID);
		CustomersService service = new CustomersService();
		Map<String,Object> map = service.findClientId(param);
		service.delLoad(param);
		if(map!=null){
			//历史车贷记录
			map.put("PROJECT_ID", PROJECT_ID);
			List<Map<String,Object>> list = service.findAutoLoan(map);
			if(list!=null&&list.size()>0){
//				Map<String,Object> params = new HashMap<String, Object>();
				for (Map<String, Object> mapCd : list) {
					mapCd.put("PRO_ID", PROJECT_ID);
					service.addAutoLoan(mapCd);
				}
			}
		}
	}

	/**
	 * 立项驳回 ProjectApprovalReject
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void ProjectApprovalReject(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "2");
			Dao.update(namespace + "updateProjectStatus", m);
			/***** 销售型售后回租，状态要同步更新 ******/
			Dao.update(namespace + "updateProjectStatus_tb", m);
		} else {
			throw new ActionException("立项驳回状态变更失败");
		}
	}

	/**
	 * 立项不通过 ProjectApprovalNotPass
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void ProjectApprovalNotPass(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "3");
			Dao.update(namespace + "updateProjectStatus", m);
			/***** 销售型售后回租，状态要同步更新 ******/
			Dao.update(namespace + "updateProjectStatus_tb", m);
		} else {
			throw new ActionException("立项不通过状态变更失败");
		}
	}

	/**
	 * 初审通过 FirstTrialPass
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void FirstTrialPass(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			
			Map<String,Object> param = new HashMap<>();
			param.put("PROJECT_ID",PROJECT_ID);
			Map<String,Object> selectOne = Dao.selectOne("DTI.findCustType",param);
			List<Map<String,Object>> list = Dao.selectList("DTI.findByProjectId",param);
			if(selectOne!=null&&selectOne.get("TYPE")!=null&&"NP".equals(selectOne.get("TYPE").toString())){
				if(list==null||list.size()<=0){
					throw new ActionException("请计算负债收入比并保存！");
				}
			}

			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "4");

			Dao.update(namespace + "updateProjectStatus", m);
			/***** 销售型售后回租，状态要同步更新 ******/
			Dao.update(namespace + "updateProjectStatus_tb", m);
			
			{
				if(list!=null&&list.size()>0){
					Map<String,Object> map = list.get(0);
					if(map.get("TPCL")!=null&&!"".equals(map.get("TPCL").toString())){
						Map<String,Object> tMap = Dao.selectOne("DTI.findTPCL", map);
						String tpcl = (String) tMap.get("FLAG");
						String remark = (String) map.get("REMARK");
						String memo = "特批处理："+tpcl+","+"原因："+remark;
						
						Map<String,Object> jMap = Dao.selectOne("DTI.findJBPMID", param);
						String jbpmId = (String) jMap.get("JBPM_ID");
						String taskName = (String) jMap.get("TASKNAME");
						String code = Security.getUser().getCode();			
						String name = Security.getUser().getName();
						
						new MemoService().addMemo(null, jbpmId,taskName, null,name,code,memo,null);
					}
				}
			}
		} else {
			throw new ActionException("初审通过状态变更失败");
		}
	}

	/**
	 * 初审驳回 FirstTrialReject
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void FirstTrialReject(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "6");
			Dao.update(namespace + "updateProjectStatus", m);
			/***** 销售型售后回租，状态要同步更新 ******/
			Dao.update(namespace + "updateProjectStatus_tb", m);
		} else {
			throw new ActionException("初审驳回状态变更失败");
		}
	}

	/**
	 * 资信提交 DataInfoSubmit
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void DataInfoSubmit(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "7");
			Dao.update(namespace + "updateProjectStatus", m);
		} else {
			throw new ActionException("资信提交状态变更失败");
		}
	}

	/**
	 * 评审通过 JudgePass (评审通过,资料补齐 12)
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void JudgePass(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
//			Map<String, Object> status = new HashMap<String, Object>(); // 用来接收
																		// ——查询——后返回的STATUS值
//			Map<String, Object> para = new HashMap<String, Object>(); // 用来向查询提供——PROJECT_ID——参数

//			para.put("PROJECT_ID", PROJECT_ID); // 设置查询参数——PROJECT_ID

			// status=(Map)Dao.selectOne(namespace+"queryProjectStatus",para);
			// //查询结果
			// if(status==null)
			// throw new ActionException("请保存风控会议纪要！");
			// else if(status.get("STATUS").toString().equals("0")) //无条件通过
			// {
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "8");
			String LEASE_CODE = CodeService.getCode("融资租赁合同编号", null, PROJECT_ID);
			m.put("LEASE_CODE", LEASE_CODE);
			Dao.update(namespace + "updateProjectStatus", m);
			// /*****销售型售后回租，状态要同步更新******/
			// String LEASE_CODE1 = CodeService.getCode("融资租赁合同编号", null,null);
			// m.put("LEASE_CODE", LEASE_CODE1);
			// Dao.update(namespace + "updateProjectStatus_tb", m);
			// }
			// else if(status.get("STATUS").toString().equals("3")) //有条件通过
			// {
			// m.put("PROJECT_ID", PROJECT_ID);
			// m.put("STATUS", "12");
			// String LEASE_CODE = CodeService.getCode("融资租赁合同编号", null,null);
			// m.put("LEASE_CODE", LEASE_CODE);
			// Dao.update(namespace + "updateProjectStatus", m);
			// //发起一条跟踪申请
			//
			// //查询项目信息
			// Map baseInfo=Dao.selectOne(namespace+"queryProjectInfo",m);
			//
			// baseInfo.put("USER_NAME", Security.getUser().getName());
			// baseInfo.put("USER_CODE", Security.getUser().getCode());
			// baseInfo.put("TYPE", "有条件通过");
			// baseInfo.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
			// String MEMO="风控评审为有条件通过。";
			// baseInfo.put("MEMO", MEMO);
			// Dao.insert(namespace+"insertTrack",baseInfo);
			// }
			// else {
			// throw new ActionException("风控委会议结果请选择通过！");
			// }

			// 拆分设备
			// 先查询设备数量为多台的设备
			List<Map<String,Object>> eqList = Dao.selectList(namespace + "queryAmountByProjectID", PROJECT_ID);
			for (Map<String, Object> eqMap : eqList) {
				int AMOUNT = Integer.parseInt(eqMap.get("AMOUNT") + "");
				for (int h = 0; h < AMOUNT; h++) {
					// 先复制
					Dao.insert(namespace + "insertEqAmount", eqMap);
				}
				// 在删除原有设备
				Dao.delete(namespace + "deleteEqById", eqMap);
			}
		} else {
			throw new ActionException("评审通过状态变更失败");
		}
		List<Map<String,Object>> list=Dao.selectList(namespace + "selcfsbsf",PROJECT_ID);
		//list是报价方案信息 不能为空
		if(list.size()>0){
			for (Map<String, Object> m : list) {
				m.put("PROJECT_ID", PROJECT_ID);
				if(m.containsKey("VALUE_STR") && "1".equals(m.get("VALUE_STR").toString())){
					Dao.update(namespace + "updcfsbsf", m);
				}else{
					synchronized(this){
						//判断是否数据重复
						List<Map<String,Object>> mPayList = Dao.selectList("leaseApplication.queryMaxPayList",PROJECT_ID);
						if(!(mPayList.size() >0)){
							// 自动生成支付表 和还款约定
							autoCopyRent(PROJECT_ID,m.get("ID")+"");
						}
					}
				}
			}
		}

		// Map<String, Object> mp = Dao.selectOne("project.getclientphone",
		// PROJECT_ID);
		// mp.put("PHONE", mp.get("TEL_PHONE"));
		// mp.put("CONTENT", mp.get("NAME")+"，您的贷款申请已经审批通过。");
		// new SmsService().sendSms(mp);
	}

	// 补充调查
	public void InvestigateAdd(String PROJECT_ID) {
		Map<String, Object> m = new HashMap<>();
		Map<String, Object> status; // 用来接收
																	// ——查询——后返回的STATUS值
		Map<String, Object> para = new HashMap<>(); // 用来向查询提供——PROJECT_ID——参数

		para.put("PROJECT_ID", PROJECT_ID); // 设置查询参数——PROJECT_ID
		status = Dao.selectOne(namespace + "queryProjectStatus", para); // 查询结果

		if (status == null)
			throw new ActionException("请保存风控会议纪要！");
		else if (status.get("STATUS") == null || status.get("STATUS").toString().equals(""))
			throw new ActionException("无项目状态！");

		else if (StringUtils.isNotBlank(PROJECT_ID)) {
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "9");
			Dao.update(namespace + "updateProjectStatus", m);
			/***** 销售型售后回租，状态要同步更新 ******/
			Dao.update(namespace + "updateProjectStatus_tb", m);
		} else {
			throw new ActionException("补充调查状态变更失败");
		}
	}

	/**
	 * 评审不通过 JudgeNoPass
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-22
	 */
	public void JudgeNoPass(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "27");
			Dao.update(namespace + "updateProjectStatus", m);
			/***** 销售型售后回租，状态要同步更新 ******/
			Dao.update(namespace + "updateProjectStatus_tb", m);
		} else {
			throw new ActionException("评审不通过状态变更失败");
		}
	}

	/**
	 * 项目复核通过 ProjectJudgeAgainPass
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void ProjectJudgeAgainPass(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "15");

			// 查询项目信息
			Map<String,Object> baseInfo = Dao.selectOne(namespace + "queryProjectInfo", m);
			List<Map<String, Object>> lst = Dao.selectList(namespace
					+ "queryRECIEVEFILEFile", m);
			if (StringUtils.isNotBlank(lst) && lst.size() > 0) {
				List<Map<String,Object>> listFile = Dao.selectList(namespace + "queryFileBQ", m);
				for (Map<String, Object> mapFile : listFile) {
					mapFile.putAll(baseInfo);
					mapFile.put("USER_NAME", Security.getUser().getName());
					mapFile.put("USER_CODE", Security.getUser().getCode());
					mapFile.put("TYPE", "资料提醒");
					mapFile.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
					String file_name = (mapFile.get("FILE_NAME") != null && !mapFile
							.get("FILE_NAME").equals("")) ? mapFile.get(
							"FILE_NAME").toString() : "";
					int num = (mapFile.get("NUM") != null && !mapFile
							.get("NUM").equals("")) ? Integer.parseInt(mapFile
							.get("NUM").toString()) : 0;
					String MEMO = file_name + "需要补齐" + num + "份";
					mapFile.put("MEMO", MEMO);
					Dao.insert(namespace + "insertTrack", mapFile);
				}

				Dao.update(namespace + "updateProjectStatus", m);
				/***** 销售型售后回租，状态要同步更新 ******/
				Dao.update(namespace + "updateProjectStatus_tb", m);
			} else {
				throw new ActionException("请先维护合同资料");
			}

		} else {
			throw new ActionException("项目复核通过状态变更失败");
		}
	}

	/**
	 * 项目签约 ProjectSign
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void ProjectQY(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			// 先判断出租人和承租人是否有签订日期
			// int num=Dao.selectInt(namespace+"queryDateSing", PROJECT_ID);
			// if(num>0){
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "20");
			Dao.update(namespace + "updateProjectStatus", m);
			/***** 销售型售后回租，状态要同步更新 ******/
			Dao.update(namespace + "updateProjectStatus_tb", m);

			// 售后回租反写到库存
			Dao.insert(namespace + "insertStockByProjectEq", PROJECT_ID);

			// 查询项目信息
			Map<String,Object> baseInfo = Dao.selectOne(namespace + "queryProjectInfo", m);

			baseInfo.put("USER_NAME", Security.getUser().getName());
			baseInfo.put("USER_CODE", Security.getUser().getCode());
			baseInfo.put("TYPE", "起租");
			baseInfo.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
			String MEMO = "项目已签约，请及时起租。";
			baseInfo.put("MEMO", MEMO);
			Dao.insert(namespace + "insertTrack", baseInfo);

			// 朱总需求，签约（没有起租）就提示付首期款，一般一个合同就一个设备一个方案一个支付表
			Map<String,Object> dateMap = Dao.selectOne(namespace + "FIRSTDAYBYPROJECT", m);
			if (dateMap != null && dateMap.get("FIRSTPAYDATE") != null
					&& !dateMap.get("FIRSTPAYDATE").equals("")) {
				String FIRSTPAYDATE = dateMap.get("FIRSTPAYDATE").toString();
				MEMO = "项目已签约，请在" + FIRSTPAYDATE + "前收首期款。";
				baseInfo.put("MEMO", MEMO);
				baseInfo.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
				Dao.insert(namespace + "insertTrack", baseInfo);
			}

			// 如果签约通过，则此客户修改为签约客户
			Dao.update(namespace + "updateCustStausQY", m);

			// TODO 张路 ： 刷资料new
			//King 2015-05-14 刷新档案数据
			new CreditCoreFileInfoService().doAddProjectContractList(PROJECT_ID,"立项合同");
			// }
			// else{
			// throw new ActionException("请选保存出租人和承租人签字日期！");
			// }

			
			// 扣除经销商授信 king 2015-04-09
			Map<String, Object> SuppMap = new projectService()
					.querySuppByProjectID(PROJECT_ID);
			String CUST_ID = SuppMap.get("SUPPLIER_ID") + "";
			if (StringUtils.isNotBlank(CUST_ID)) {
				String RZE = UtilFinance.formatString(new projectService()
						.selectProjectDetails(m).get("FINANCE_TOPRIC"), 2);
				SupplierCreditService scservice = new SupplierCreditService();
				List<Map<String, Object>> list = scservice
						.queryCreditInfoBySubject("3", CUST_ID);
				if (StringUtils.isNotBlank(list)&& !list.isEmpty()&&list.size()>=1){
					boolean flag=false;
					for (Map<String, Object> map : list) {
						if (StringUtils.isNotBlank(map.get("GRANT_PRICE"))) {
							String GRANT_PRICE = map.get("GRANT_PRICE") + "";
							if (Double.parseDouble(RZE) <= Double
									.parseDouble(GRANT_PRICE)) {
								scservice.deductSubjectCreditLastMoney(RZE,
										"3", CUST_ID, map.get("CUGP_ID") + "");
								flag=true;
								Map<String,Object> mm=new HashMap<>();
								mm.put("PROJECT_ID", PROJECT_ID);
								mm.put("TYPE", "3");
								mm.put("CUGP_ID", map.get("CUGP_ID"));
								mm.put("CUST_ID", CUST_ID);
								mm.put("MONEY", RZE);
								scservice.doAddProjectGRANTPLAN(mm);
								break;
							}
						}
					}
					if(!flag){
						throw new ActionException("该项目的经销商授信余额已低于项目融资额，不足以支持该项目的继续进行");
					}
				}
			}
			//扣除厂商授信 King  2015-06-06
			String COMPANY_ID= SuppMap.get("COMPANY_ID") + "";
			if (StringUtils.isNotBlank(COMPANY_ID)) {
				String RZE = UtilFinance.formatString(new projectService()
						.selectProjectDetails(m).get("FINANCE_TOPRIC"), 2);
				SupplierCreditService scservice = new SupplierCreditService();
				List<Map<String, Object>> list = scservice
						.queryCreditInfoBySubject("4", COMPANY_ID);
				if (StringUtils.isNotBlank(list)&& !list.isEmpty()&&list.size()>=1){
					boolean flag=false;
					for (Map<String, Object> map : list) {
						if (StringUtils.isNotBlank(map.get("GRANT_PRICE"))) {
							String GRANT_PRICE = map.get("GRANT_PRICE") + "";
							if (Double.parseDouble(RZE) <= Double
									.parseDouble(GRANT_PRICE)) {
								scservice.deductSubjectCreditLastMoney(RZE,
										"4", COMPANY_ID, map.get("CUGP_ID") + "");
								flag=true;
								Map<String,Object> mm=new HashMap<>();
								mm.put("PROJECT_ID", PROJECT_ID);
								mm.put("TYPE", "4");
								mm.put("CUGP_ID", map.get("CUGP_ID"));
								mm.put("CUST_ID", COMPANY_ID);
								mm.put("MONEY", RZE);
								scservice.doAddProjectGRANTPLAN(mm);
								break;
							}
						}
					}
					if(!flag){
						throw new ActionException("该项目的厂商授信余额已低于项目融资额，不足以支持该项目的继续进行");
					}
				}
			}
			
			//扣除客户授信 King  2015-06-06
			String CLIENT_ID= SuppMap.get("CLIENT_ID") + "";
			if (StringUtils.isNotBlank(CLIENT_ID)) {
				String RZE = UtilFinance.formatString(new projectService()
						.selectProjectDetails(m).get("FINANCE_TOPRIC"), 2);
				SupplierCreditService scservice = new SupplierCreditService();
				List<Map<String, Object>> list = scservice
						.queryCreditInfoBySubject("1", CLIENT_ID);
				if (StringUtils.isNotBlank(list)&& !list.isEmpty()&&list.size()>=1){
					boolean flag=false;
					for (Map<String, Object> map : list) {
						if (StringUtils.isNotBlank(map.get("GRANT_PRICE"))) {
							String GRANT_PRICE = map.get("GRANT_PRICE") + "";
							if (Double.parseDouble(RZE) <= Double
									.parseDouble(GRANT_PRICE)) {
								scservice.deductSubjectCreditLastMoney(RZE,
										"1", CLIENT_ID, map.get("CUGP_ID") + "");
								flag=true;
								Map<String,Object> mm=new HashMap<>();
								mm.put("PROJECT_ID", PROJECT_ID);
								mm.put("TYPE", "1");
								mm.put("CUGP_ID", map.get("CUGP_ID"));
								mm.put("CUST_ID", CLIENT_ID);
								mm.put("MONEY", RZE);
								scservice.doAddProjectGRANTPLAN(mm);
								break;
							}
						}
					}
					if(!flag){
						throw new ActionException("该项目的客户授信余额已低于项目融资额，不足以支持该项目的继续进行");
					}
				}
			}
			
		} else {
			throw new ActionException("项目签约状态变更失败");
		}
	}

	/**
	 * 项目通过不通过通知 ProjectSign
	 * 
	 * @param PROJECT_ID
	 */
	public void ProjectTZ(String PROJECT_ID) {
		// 占时为空方法
		Map<String,Object> map=new HashMap<>();
		map.put("PROJECT_ID", PROJECT_ID);
		Map<String,Object> m=Dao.selectOne(namespace+"getEmail",map);
		String newContent=m.get("JBPM_ID").toString();
		if("27".equals(m.get("STATUS").toString())){
			newContent=newContent+"客户"+m.get("NAME")+"的申请审批中因很多原因已经退回，请在“业务申请”功能补充完整资料，再次提交申请，若需帮助致电4000-648-698";
		}else
		{
			newContent=newContent+"客户"+m.get("NAME")+"的融资申请已经批复，请在“申请放款”功能签订合同，快速获得融资款，若需帮助致电4000-648-698";
		}
		String address = "";
		if(m.containsKey("EMAIL")){
			address=m.get("EMAIL").toString();
			String title = m.get("JBPM_ID").toString();
			try {
				SystemMail.sendMail(address, title, newContent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		String wxq = m.get("CODE").toString();
//		WeixinService.sendTextMsg(wxq, newContent, "app5");
//		String phone = "";
//		if(m.containsKey("MOBILE")){
//			phone=m.get("MOBILE").toString();
//		}
//		Map<String, Object> param1 = new HashMap<String, Object>();
//		param1.put("PHONE", phone);
//		param1.put("CONTENT", newContent);
//		new SmsService().sendSms(param1);
	}

	/**
	 * 部分结清 PartBalanceAccount
	 * 
	 * @param project_id
	 * @author:YangJ 2014-4-21
	 */
	public void PartBalanceAccount(String project_id) {
		if (StringUtils.isNotBlank(project_id)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", project_id);
			m.put("STATUS", "25");
			Dao.update(namespace + "updateProjectStatus", m);
		} else {
			throw new ActionException("部分结清状态变更失败");
		}
	}

	/**
	 * 全部结清 AllBalanceAccount
	 * 
	 * @param project_id
	 * @author:YangJ 2014-4-21
	 */
	public void AllBalanceAccount(String project_id) {
		if (StringUtils.isNotBlank(project_id)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", project_id);
			m.put("STATUS", "50");
			Dao.update(namespace + "updateProjectStatus", m);
		} else {
			throw new ActionException("全部结清状态变更失败");
		}
	}

	/**
	 * 项目作废中 ProjectDeleting
	 * 
	 * @param project_id
	 * @author:YangJ 2014-4-21
	 */
	public void ProjectDeleting(String project_id) {
		if (StringUtils.isNotBlank(project_id)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", project_id);
			m.put("STATUS", "99");
			Dao.update(namespace + "updateProjectStatus", m);
		} else {
			throw new ActionException("项目作废中状态变更失败");
		}
	}

	/**
	 * 项目作废 ProjectDelete
	 * 
	 * @param project_id
	 * @author:YangJ 2014-4-21
	 */
	public void ProjectDelete(String project_id) {
		if (StringUtils.isNotBlank(project_id)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", project_id);
			m.put("STATUS", "100");
			Dao.update(namespace + "updateProjectStatus", m);
		} else {
			throw new ActionException("项目作废状态变更失败");
		}
	}

	/**
	 * 项目签约
	 * 
	 * @param project_id
	 * @author:King 2013-9-22 By：YangJ 2014-4-22
	 */
	public void ProjectSign(String project_id) {
		if (StringUtils.isNotBlank(project_id)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", project_id);
			m.put("STATUS", "13");
			Dao.update(namespace + "updateProjectStatus", m);
			try {
				// 未签约
				int count = Dao
						.selectInt(namespace + "queryWEIQIANYUECount", m);
				if (count >= 1) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					m.put("SIGNED_DATE", sdf.format(new Date()));
					Dao.update(namespace + "updateQianyue", m);
				}
				Dao.update(namespace + "updateProHeadInfo", m);
				Dao.update(namespace + "updateProHeadInfo1", m);
				Dao.update(namespace + "updateProHeadInfo2", m);
			} catch (Exception ignored) {
			}
		} else {
			throw new ActionException("项目签约状态变更失败");
		}
	}

	/**
	 * 资料录入岗后台数据校验
	 * 
	 * @param PROJECT_ID
	 * @author:YangJ 2014-4-21
	 */
	public void zllrDataJy(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			// 查询项目信息(担保人多个取一个)
			List<Map<String,Object>> list=Dao.selectList(namespace + "queryClientTypeById",PROJECT_ID);
			Map<String,Object> projectMap = list.get(0);
			if (projectMap == null) {
				projectMap = new HashMap<>();
			}
			// 客户信息校验
			if (projectMap.get("IS_SAVE") != null
					&& !projectMap.get("IS_SAVE").equals("")) {
				String IS_SAVE = projectMap.get("IS_SAVE").toString();
				if (!IS_SAVE.equals("2")) {
					throw new ActionException("请确认客户信息是否填写完整！");
				}
				
				//如果客户为个人身份证验证
				Map<String,Object> clientmap=Dao.selectOne(namespace + "queryCardByClientInfo", projectMap.get("CLIENT_ID").toString());
				if(clientmap !=null && clientmap.get("CLIENT_ID") !=null && !clientmap.get("CLIENT_ID").equals("")){
					String msg=this.YZCARD(clientmap);
					System.out.println("-------client_id="+clientmap.get("CLIENT_ID")+"---------客户身份证验证-----"+msg);
				}
				
			} else {
				throw new ActionException("请确认客户信息是否填写完整！");
			}

			// 银行信息校验
//			if (projectMap.get("BANK_ID") != null
//					&& !projectMap.get("BANK_ID").equals("")) {
//				;
//			} else {
//				throw new ActionException("请先选择关联银行后在提交到下一步");
//			}

			String hasJt = projectMap.get("JOINT_TENANT") == null ? "1" : projectMap.get("JOINT_TENANT").toString();
			
			// 共同承租人信息校验
			if (projectMap.get("JOINT_TENANT_ID") != null
					&& !projectMap.get("JOINT_TENANT_ID").equals("") && !hasJt.trim().equals("1")) {
				if (projectMap.get("GT_IS_SAVE") != null
						&& !projectMap.get("GT_IS_SAVE").equals("")) {
					String GT_IS_SAVE = projectMap.get("GT_IS_SAVE").toString();
					if (!GT_IS_SAVE.equals("2")) {
						throw new ActionException("请先录入共同承租人信息完全后在提交到下一步");
					}
				} else {
					throw new ActionException("请先录入共同承租人信息完全后在提交到下一步");
				}
				
				//如果共同承租人为个人身份证验证
				Map<String,Object> clientmap=Dao.selectOne(namespace + "queryCardByClientInfo", projectMap.get("JOINT_TENANT_ID").toString());
				if(clientmap !=null && clientmap.get("CLIENT_ID") !=null && !clientmap.get("CLIENT_ID").equals("")){
					String msg=this.YZCARD(clientmap);
					System.out.println("-------client_id="+clientmap.get("CLIENT_ID")+"---------共同承租人身份证验证-----"+msg);
				}
			}

			String hasGuarantee = projectMap.get("GUARANTEE") == null ? "1" : projectMap.get("GUARANTEE").toString();
			// 担保人信息校验
			if (projectMap.get("CLIENT_ID1") != null
					&& !projectMap.get("CLIENT_ID1").equals("") && !hasGuarantee.trim().equals("1")) {
				if (projectMap.get("DBR_IS_SAVE") != null
						&& !projectMap.get("DBR_IS_SAVE").equals("")) {
					String DBR_IS_SAVE = projectMap.get("DBR_IS_SAVE")
							.toString();
					if (!DBR_IS_SAVE.equals("2")) {
						throw new ActionException("请先录入担保人信息完全后在提交到下一步");
					}
				} else {
					throw new ActionException("请先录入担保人信息完全后在提交到下一步");
				}
				
				//如果担保人为个人身份证验证
				Map<String,Object> clientmap=Dao.selectOne(namespace + "queryCardByClientInfo", projectMap.get("CLIENT_ID1").toString());
				if(clientmap !=null && clientmap.get("CLIENT_ID") !=null && !clientmap.get("CLIENT_ID").equals("")){
					String msg=this.YZCARD(clientmap);
					System.out.println("-------client_id="+clientmap.get("CLIENT_ID")+"---------担保人身份证验证-----"+msg);
				}
			}

			// //客户信息校验
			// String TYPE="";
			// if(projectMap.get("TYPE") !=null &&
			// !projectMap.get("TYPE").equals("")){
			// TYPE=projectMap.get("TYPE")+"";
			// }
			//
			// if(TYPE.equals("NP")){
			// Map mapNpInfo=Dao.selectOne(namespace + "queryNpInfo",
			// PROJECT_ID);
			// String[] NPSZ = new
			// String[]{"ID_CARD_TYPE","ID_CARD_NO","BIRTHDAY","SEX","AGE","TEL_PHONE","PHONE","DEGREE_EDU","IS_MARRY","WORK_UNIT","INDUSTRY_FICATION","POSITION","COMPANY_ADDR_PROVINCE","COMPANY_ADDR_CITY","COMPANY_ADDR_COUNTY","COMPANY_ADDR","HOUSE_ADDRESS_PROVINCE","HOUSE_ADDRESS_CITY","HOUSE_ADDRESS_COUNTY","HOUSE_ADDRESS"};
			// yanzheng("1",mapNpInfo,NPSZ);
			//
			// }else if(TYPE.equals("LP")){
			// Map mapNpInfo=Dao.selectOne(namespace + "queryLpInfo",
			// PROJECT_ID);
			// String[] NPSZ = new
			// String[]{"ID_CARD_TYPE","ID_CARD_NO","BIRTHDAY","SEX","AGE","TEL_PHONE","PHONE","DEGREE_EDU","IS_MARRY","WORK_UNIT","INDUSTRY_FICATION","POSITION","COMPANY_ADDR_PROVINCE","COMPANY_ADDR_CITY","COMPANY_ADDR_COUNTY","COMPANY_ADDR","HOUSE_ADDRESS_PROVINCE","HOUSE_ADDRESS_CITY","HOUSE_ADDRESS_COUNTY","HOUSE_ADDRESS"};
			// yanzheng("1",mapNpInfo,NPSZ);
			// }
			//
			// //银行信息校验
			// if(projectMap.get("BANK_ID") !=null && !projectMap.get("BANK_ID")
			// .equals("")){
			// ;
			// }else{
			// throw new ActionException("请先选择关联银行后在提交到下一步");
			// }
			//
			// //共同承租人信息校验
			//
			// //担保人信息校验

		} else {
			throw new ActionException("资料信息没有录入完全，请先录入资料信息完全后在提交到下一步");
		}
	}

	public void yanzheng(String TYPE, Map<String,Object> map, String[] SZInfo) {
		String TYPEFlag = "";
		if (TYPE.equals("1")) {
			TYPEFlag = "承租人";
		} else if (TYPE.equals("2")) {
			TYPEFlag = "共同承租人";
		} else if (TYPE.equals("3")) {
			TYPEFlag = "担保人";
		}
//		int count = SZInfo.length;
		if (map != null) {
			for (String aSZInfo : SZInfo) {
				if (map.get(aSZInfo) == null || map.get(aSZInfo).equals("")) {
					String title_name = map.get(aSZInfo + "_NAME") + "";
					throw new ActionException(TYPEFlag + "信息：" + title_name
							+ "未录入！");
				}
			}
		}
	}

	/**
	 * 立项节点， 初审验证家访是否
	 * @author: yx 
	 * @date: 2015-4-11
	 * @return_type:void
	 * @param PROJECT_ID
	 */
	public void yzJiaFang(String PROJECT_ID){
		Map<String,Object> m = new HashMap<>();
		m.put("PROJECT_ID", PROJECT_ID);
//		Map<String,Object> jiafang = Dao.selectOne("ReturnVisit.toViewVisit1", m);
//		if(jfList==null){
//			throw new ActionException("请录入尽职调查任务信息");
//		}
		List<Map<String,Object>> jfList = Dao.selectList("ReturnVisit.toViewVisit1", m);
		int num =jfList.size();
		for (Map<String, Object> askMap : jfList) {
			if (askMap != null && !askMap.isEmpty()) {
				String askId = askMap.get("ZX_ASKID").toString();
				if ("0".equals(askId)) {
					Dao.update("ReturnVisit.doUpVisitAskId", askMap);
				}
			}
		}
		if(num ==0){
			throw new ActionException("请录入尽职调查任务信息");
		}
	}

	public void Visitingcheck(String PROJECT_ID){
		int i=Dao.selectInt(namespace+"getallVisiting", PROJECT_ID);
		int j=Dao.selectInt(namespace+"getUploadedVisiting", PROJECT_ID);
		if(i>j){
			throw new ActionException("尽职调查报告上传文件不齐全！");
		}
		
	}
	
	/**
	 * 更新任务所处当前节点
	 * 
	 * @param PROJECT_ID
	 * @param taskName
	 * @author:King 2013-11-20
	 */
	public void updateProjectJbpmTaskName(String PROJECT_ID, String taskName) {
		Map<String, Object> map = new HashMap<>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("TASKNAME", null == taskName ? " " : taskName);
		Dao.update(namespace + "updateProjectJbpmTaskName", map);
	}

	
	public void autoCopyRent(String PROJECT_ID) {
		autoCopyRent(PROJECT_ID,null);
	}
	public void autoCopyRent(String PROJECT_ID,String SCHEME_ID) {
		// ///////////////////////支付表数据
		Map<String,Object> map = new HashMap<>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("SCHEME_ID", SCHEME_ID);
		map = Dao.selectOne(namespace + "getEquipmentData", map);

		// 生成支付表号
		Map<String,Object> MapPayCode =  Dao.selectOne("leaseApplication.createPayListCode", map);
		String PAYLIST_CODE = MapPayCode.get("PAYLIST_CODE").toString();
		map.put("PAYLIST_CODE", PAYLIST_CODE);

//		projectService projectSer = new projectService();
//		Map<String,Object> baseMap = projectSer.queryInfoByEqBase(map);

//		LeaseApplicationService las = new LeaseApplicationService();
//		List<Map<String,Object>> eqlist = las.queryEqByChange(map);
		// 获取支付表ID
		String PAY_ID = Dao.getSequence("SEQ_FIL_RENT_PLAN_HEAD");
		// 查询方案和支付表
		this.queryScheme(map, PAY_ID);

		// ///////////////////////////////付款约定数据

		// 判断是否分批起租
		map.put("SCHEME_ID", SCHEME_ID);
		int rentCount = Dao.selectInt(namespace + "getRentCount", map);
		Map<String,Object> RentHeadData = Dao.selectOne(namespace + "getRentHeadData", map);
		Map<String,Object> buyContractData = Dao.selectOne(namespace
				+ "getCreateBuyContractData", map);
		buyContractData.put("PAYLIST_CODE", PAYLIST_CODE);
		buyContractData.put("SQ_PRICE", RentHeadData.get("FIRSTPAYALL") + "");
		buyContractData.put("PLATFORM_TYPE", RentHeadData.get("PLATFORM_TYPE")
				+ "");
		String LEASE_ORDER_CODE = CodeService.getCode("买卖合同编号",
				buyContractData.get("CLIENT_ID") + "", PROJECT_ID);
		buyContractData.put("LEASE_ORDER_CODE", LEASE_ORDER_CODE);
		buyContractData.put("PRODUCT_CODE", LEASE_ORDER_CODE);
		buyContractData.put("PROJECT_ID", PROJECT_ID);
		buyContractData.put("PAY_ID", PAY_ID);

		// 平台ID
		String platformId = Security.getUser().getOrg().getPlatformId();
		Long fhfa_id = null == platformId ? null : Long.valueOf(platformId);
		FhfaManagerService fhfaManagerService = new FhfaManagerService();
		Map<String,Object> fhfaManager = fhfaManagerService.selectFHFAManagerById(fhfa_id);
		buyContractData.put("LESSEE_NAME", fhfaManager.get("FA_NAME") + "");
		buyContractData.put("SCHEME_ID", SCHEME_ID);
		if (rentCount == 1) {
			String BUY_CONTRACT_ID = Dao.getSequence("SEQ_FIL_BUY_CONTRACT");
			buyContractData.put("BUY_CONTRACT_ID", BUY_CONTRACT_ID);
			Dao.insert(namespace + "autoCreateBuyContract", buyContractData);
			this.addBuyContract(PROJECT_ID, buyContractData);
		}
		// 自动起租
		LeaseApplicationJbpm la = new LeaseApplicationJbpm();
		la.leaseApplicationPassAuto(PAY_ID, PAYLIST_CODE);

		// 自动生成开票协议
		InvoiceApplicationService IaService = new InvoiceApplicationService();
		String clientId = IaService.getClientIdByProjectId(map) == null ? null
				: IaService.getClientIdByProjectId(map).toString();
		String invoiceAppCode = CodeService.getCode("开票协议编号", clientId, map
				.get("PROJECT_ID").toString());
		Map<String,Object> invoMap = new HashMap<>();
		invoMap.put("NO", invoiceAppCode);

		invoMap.put("BUY_CODE", map.get("LEASE_ORDER_CODE"));
		invoMap.put("PAY_ID", PAY_ID);
		invoMap.put("PAYLIST_CODE", PAYLIST_CODE);
		invoMap.put("PROJECT_ID", map.get("PROJECT_ID"));
		invoMap.put("CONTRACT_ID", map.get("PROJECT_ID"));
		invoMap.put("INVOICE_APPLICATION_ID",
				IaService.getInvoiceApplicationNextVal());
		invoMap.put("EMS_ID", IaService.getEMSInfoNextVal());

		// 查询融资租赁信息 甲方
		invoMap.put("PARTANAME", fhfaManager.get("FA_NAME"));
		invoMap.put("PARTAPHTOE", fhfaManager.get("FA_PHONE"));
		invoMap.put("PARTADUTY_ID", fhfaManager.get("TAX_NUM"));
		invoMap.put("PARTAADRS", fhfaManager.get("REG_ADD"));
		invoMap.put("PARTABANK", fhfaManager.get("FA_BINK"));
		invoMap.put("PARTANUMBER", fhfaManager.get("FA_ACCOUNT"));
		invoMap.put("PARTAATTORNEY", fhfaManager.get("LEGAL_PERSON"));
		invoMap.put("PARTACODE", fhfaManager.get("ORG_CODE"));

		// 查询客户信息 乙方
		Map<String,Object> payMap = new HashMap<>();
		payMap.put("PAY_ID", PAY_ID);
		Map<String, Object> custInfo = IaService.searchCustInfo(payMap);
		invoMap.put("CODE", custInfo.get("LEASE_CODE"));
		invoMap.put("PARTBNAME", custInfo.get("NAME"));
		
		invoMap.put("PARTBBANK", custInfo.get("BANK_NAME"));
		invoMap.put("PARTBNUMBER", custInfo.get("BANK_ACCOUNT"));
		invoMap.put("PARTBDUTY", custInfo.get("TAX_CODE"));

		if (custInfo.get("TYPE") != null && custInfo.get("TYPE").equals("LP")) {
			invoMap.put("PARTBATTORNEY", custInfo.get("LEGAL_PERSON"));
			invoMap.put("PARTBCODE_OR_CARD", custInfo.get("ORAGNIZATION_CODE"));
			invoMap.put("PARTBPHONE", custInfo.get("REGISTE_PHONE"));
			invoMap.put("PARTBADRS", custInfo.get("REGISTE_ADDRESS"));
			invoMap.put("W_CODE_OR_CARD", custInfo.get("ORAGNIZATION_CODE"));
			invoMap.put("W_PHONE", custInfo.get("REGISTE_PHONE"));
			invoMap.put("W_ADDR", custInfo.get("REGISTE_ADDRESS"));
		} else {
			invoMap.put("PARTBCODE_OR_CARD", custInfo.get("ID_CARD_NO"));
			invoMap.put("PARTBPHONE", custInfo.get("TEL_PHONE"));
			invoMap.put("PARTBADRS", custInfo.get("HOUSE_ADDRESS"));
			invoMap.put("W_CODE_OR_CARD", custInfo.get("ID_CARD_NO"));
			invoMap.put("W_PHONE", custInfo.get("TEL_PHONE"));
			invoMap.put("W_ADDR", custInfo.get("HOUSE_ADDRESS"));
		}

		if (custInfo.get("TAX_QUALIFICATION") != null
				&& !custInfo.get("TAX_QUALIFICATION").equals("")) {
			// 纳税人资质
			String taxTYPE = "纳税资质";
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> taxTYPEList =  new DataDictionaryMemcached().get(taxTYPE);
			for (Map<String, Object> taxMap : taxTYPEList) {
				if (taxMap.get("CODE") != null
						&& !taxMap.get("CODE").equals("")
						&& taxMap
						.get("CODE")
						.toString()
						.equals(custInfo.get("TAX_QUALIFICATION")
								.toString())) {
					invoMap.put("PARTB_TAX_QUALIFICATION", taxMap.get("FLAG"));
					invoMap.put("W_TAX_QUALIFICATION", taxMap.get("FLAG"));
				}
			}
		}
		
		//租金发票对象
		invoMap.put("W_NAME", custInfo.get("NAME"));
		invoMap.put("W_BANK", custInfo.get("BANK_NAME"));
		invoMap.put("W_BANK_NUMBER", custInfo.get("BANK_ACCOUNT"));
		invoMap.put("W_DUTY", custInfo.get("TAX_CODE"));
		
		invoMap.put("ZHENGJIFAPIAO", "客户");
		invoMap.put("INVOICEPATTERN", "FP_MONTH");

		Dao.insert("invoiceApplication.insertInvoiceApplication", invoMap);
	}

	@SuppressWarnings("unchecked")
	public void queryScheme(Map<String,Object> m, String PAY_ID) {
		String basePath = "leaseApplication.";

		Map<String,Object> map = new HashMap<>();
		map.put("PAYLIST_CODE", m.get("PAYLIST_CODE").toString());
		// 先查出本次拆分的比例
		m.put("MONEYCF", Dao.selectDouble(basePath + "queryEqInId", m));
		map.put("MONEYCF", Dao.selectDouble(basePath + "queryEqInId", m));
		m.put("MONEYTOTAL", Dao.selectDouble(basePath + "queryEqByScheme", m));
		map.put("MONEYTOTAL", Dao.selectDouble(basePath + "queryEqByScheme", m));
		BaseSchemeService bsService = new BaseSchemeService();
		List<Map<String, Object>> SCHEME_CLOB=bsService.getSchemeClob(null, m.get("PROJECT_ID") + "",m.get("SCHEME_ROW_NUM") + "");
		map.put("SCHEMEDETAIL",SCHEME_CLOB);
		Map<String,Object> mapbase = Dao.selectOne(basePath
				+ "getSchemeBaseInfoByProjectIdINT", m);
		map.putAll(mapbase);

//		if (mapbase != null) {
		mapbase.put("SCHEME_ID", mapbase.get("ID"));
		List<Map<String,Object>> clobList=Dao.selectList(basePath+"queryfil_scheme_clobForCs", mapbase);
		for (Map<String, Object> map2 : clobList) {
			if("BEFORETHREE_PERCENT".equals(map2.get("KEY_NAME_EN")+"") && map2.get("VALUE_STR") !=null && !"".equals(map2.get("VALUE_STR")+"")){
				BigDecimal   b   =   new   BigDecimal(Double.parseDouble(map2.get("VALUE_STR").toString())/100);
				double   f1   =   b.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();
				mapbase.put(map2.get("KEY_NAME_EN")+"", f1);
			}else{
				mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
			}
		}

		try{
			mapbase.put("AMOUNT", (m.get("EQ_IDS")+"").split(",").length);
		}catch(Exception ignored){}

		mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
		mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
		mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
		mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
		mapbase.put("pay_way", mapbase.get("PAY_WAY"));
		mapbase.put("date", m.get("START_DATE_CHANGE"));
		mapbase.put("date1", m.get("REPAYMENT_DATE_CHANGE"));
		mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
		mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
		mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
		mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
		/**2016年4月20日 16:26:21 捷众拆分法 吴国伟*/
		PayTaskService payTast = new PayTaskService();
		JSONObject page=null;
		try {

			if("8".equals(mapbase.get("pay_way"))){//2016年4月20日 16:26:21 捷众拆分法 吴国伟
				Map<String,Object> mp2p = new HashMap<>();
				mp2p.put("SCHEME_SYL_BZ_VALUE", mapbase.get("SCHEME_SYL_BZ_VALUE"));
				mp2p.put("LEASE_TERM", mapbase.get("_leaseTerm"));
				mp2p.put("TOTAL_FINACING", mapbase.get("FINANCE_TOPRIC"));
				mp2p.put("YEAR_INTEREST", Double.valueOf(mapbase.get("YEAR_INTEREST").toString())*100);
				mp2p.put("SCALE", null == mapbase.get("SCALE")?0:Integer.valueOf(mapbase.get("SCALE").toString()));
				mp2p.put("ROUND_UP_TYPE", null == mapbase.get("ROUND_UP_TYPE")?2:Integer.valueOf(mapbase.get("ROUND_UP_TYPE").toString()));
				mp2p.put("LEASE_TOPRIC", mapbase.get("LEASE_TOPRIC"));
				mp2p.put("CC_PRICE", null ==m.get("CC_PRICE")?mapbase.get("LEASE_TOPRIC"):m.get("CC_PRICE"));
				mp2p.put("MQGLF", null == mapbase.get("MQGLF")?0:Integer.valueOf(mapbase.get("MQGLF").toString()));
				mp2p.put("HTZCF", null == mapbase.get("HTZCF")?0:Integer.valueOf(mapbase.get("HTZCF").toString()));
				mp2p.put("CKYS", null == mapbase.get("CKYS")?1:Integer.valueOf(mapbase.get("CKYS").toString()));
				page = payTast.algorithmP2PCalculate(mp2p);
			}else{
				page = payTast.quoteCalculateTest(mapbase);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		assert page != null;
		List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");
		double ZJHJ = 0.00;
		Map<String,String> hjmap;
		for (Map<String, String> anIrrList : irrList) {
			if (anIrrList != null) {
				hjmap = anIrrList;
				if (hjmap.get("zj") != null && !hjmap.get("zj").equals("")) {
					ZJHJ = ZJHJ	+ Double.parseDouble(hjmap.get("zj"));
				}
			}
		}
			map.put("MONTH_PRICE", ZJHJ);
			map.put("detailList", irrList);
//		}
		map.put("PROJECT_ID", m.get("PROJECT_ID").toString());
		map.put("SCHEME_ID", mapbase.get("ID").toString());
		this.updateEqStatus(map, PAY_ID);
	}
	

	@SuppressWarnings("unchecked")
	public void updateEqStatus(Map<String,Object> param, String PAY_ID) {
		String projectId = param.get("PROJECT_ID").toString();
		logger.debug("合同签约，支付表和设备表相关写操作开始，相关项目id:" + projectId);
		LeaseApplicationService las = new LeaseApplicationService();
		String basePath = "leaseApplication.";
//		PayTaskService pay = new PayTaskService();
		// 方案
//		Object schemeObject = null;
		// 从方案中获取首期款和其他费用
		// 获得支付表ID
		// String PAY_ID=Dao.getSequence("SEQ_FIL_RENT_PLAN_HEAD");

		// 是否固定利息
		String LXTQSQ = param.get("LXTQSQ") != null ? param.get("LXTQSQ") + ""
				: "1";// 1:否，2：是

//		// 计算起租日期和还款日期利息差(前面已经计算)
//		double DateLXMoney = 0.0;
		// double residualPrincipal=param.get("FINANCE_TOPRIC")!=null ?
		// Double.parseDouble(param.get("FINANCE_TOPRIC").toString()):0d;
		// double annualRate=param.get("YEAR_INTEREST")!=null ?
		// Double.parseDouble(param.get("YEAR_INTEREST").toString()):0d;
		// DateLXMoney=pay.getDateLiXiMoney(param.get("START_DATE_CHANGE")+"",
		// param.get("REPAYMENT_DATE_CHANGE")+"", residualPrincipal,
		// annualRate);

		// 保存支付表头表(先不生成支付表号，当流程审批通过后在给支付表编号并且刷数据到财务系统)
		// 保存支付表子表
		double ZJHJ = Double.parseDouble(param.get("MONTH_PRICE").toString());
		double LXHJ = Double.parseDouble(param.get("GDLX").toString());
		JSONArray josn = JSONArray.fromObject(param.get("detailList"));

		List<Map<String, String>> list = PayTaskService.getParsePayList(josn);
		for (Map<String, String> map_ : list) {
			map_.put("PAY_ID", PAY_ID);
			map_.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map_);
		}

		// 获取元方案信息
		Map<String,Object> headMap = Dao.selectOne(basePath + "queryHeadByScheme", param);
		Map<String,Object> returnMap = las.jiexiScheme(headMap, param, PAY_ID, LXTQSQ, LXHJ);
		if (headMap != null) {
			Map<String,Object> headBaseInfoMap = Dao.selectOne(basePath + "queryHeadBaseInfo",
					param);
			headMap.putAll(headBaseInfoMap);
			headMap.put("PAY_ID", PAY_ID);
			headMap.put("STATUS", "1");
			headMap.put("START_DATE", param.get("START_DATE_CHANGE"));
			headMap.put("REPAYMENT_DATE", param.get("REPAYMENT_DATE_CHANGE"));
			headMap.put("SCHEME_CLOB", returnMap.get("SCHEME_CLOB").toString());
			headMap.put("MONEY_ALL", ZJHJ);

			if (LXTQSQ.equals("2")) {
				headMap.put("GDLX", LXHJ);
			}

			headMap.put("STATUS", "0");
			headMap.put("VERSION_CODE", "1");
			headMap.put("PAYLIST_CODE", param.get("PAYLIST_CODE").toString());
			
			//取得方案下的客户保证金
			headMap.put("DEPOSIT_VALUE", Dao.selectDouble(basePath + "queryKhBzjByScheme", param));
			
			Dao.insert(basePath + "insertRent_Plan_head", headMap);
		}

		if(StringUtils.isNotBlank(returnMap.get("listFY"))){
			List<Map<String,Object>> listFY = (List<Map<String,Object>>) returnMap.get("listFY");
			for (Map<String, Object> mapFY : listFY) {
				Dao.insert("PayTask.payplan_detail", mapFY);
			}
		}

		// 修改设备表状态并反更支付表ID
		param.put("PAY_ID", PAY_ID);
		logger.debug("合同签约，更新设备表paylist_code开始，更新参数param:" + param);
		Dao.update(basePath + "updateEqStatus", param);
		// 更新设备表
		int rows = Dao.insert(basePath + "updateProject_Equipment", param);
		logger.debug("合同签约，更新设备表paylist_code完成，影响行数:" + rows);
	}

	public void addBuyContract(String PROJECT_ID, Map<String,Object> map) {
		BuyContractService bcs = new BuyContractService();

		// map.put("PAYMENT_HEAD_ID",map.get("PAY_ID")+"") ;
		map.put("PAY_NAME", "设备款");
		map.put("PAY_TYPE", 1);
		map.put("STATUS", 1);
		map.put("PAYEE_NAME", map.get("SELLER_UNIT_NAME"));
		map.put("PAY_BANK_ACCOUNT", map.get("SELLER_ACCOUNT"));
		map.put("PAY_BANK_ADDRESS", map.get("SELLER_PAY_BANK_ADDRESS"));
		map.put("PROJECT_ID", PROJECT_ID);
		// 查询代收金额
		Map<String,Object> dkje = Dao.selectOne("paymentApply.getDkje", map);
		Clob clob = (Clob) dkje.get("SCHEME_CLOB");
		String str;
		try {
			str=clob.getSubString(1, (int) clob.length());
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ActionException("报价详细未录入全，请联系管理员");
		}
//		List<Map<String, Object>> fkje1=new ArrayList<Map<String, Object>>();
		JSONArray SCHEME_CLOB=JSONArray.fromObject(str);
		Double rze=0.00d;
		Double ds=0.00d;
		for (Object aSCHEME_CLOB : SCHEME_CLOB) {
			Map mClob = (Map) aSCHEME_CLOB;

			if ((mClob.get("KEY_NAME_EN").toString()).contains("MONEY") || (mClob.get("KEY_NAME_EN").toString()).contains("PRICE")) {
				if (!"".equals(mClob.get("VALUE_STR").toString()) && !mClob.get("VALUE_STR").toString().equals("0") && mClob.containsKey("FYGS")) {
					if ("JRSQK".equals(mClob.get("FYGS").toString())) {
						if (mClob.containsKey("DSFS") && !"".equals(mClob.get("DSFS").toString()) && "1".equals(mClob.get("DSFS").toString())) {
							ds = ds + (StringUtils.isBlank(mClob.get("VALUE_STR")) ? 0 : Double.parseDouble(mClob.get("VALUE_STR").toString()));
						}
					} else if ("JRRZE".equals(mClob.get("FYGS").toString()) && !"GPS费用".equals(mClob.get("KEY_NAME_ZN").toString())) {
						rze = rze + (StringUtils.isBlank(mClob.get("VALUE_STR")) ? 0 : Double.parseDouble(mClob.get("VALUE_STR").toString()));
					}
				}
			}
		}
		Double fkje=Double.parseDouble(map.get("LEASE_TOPRIC").toString())+rze-ds;
		map.put("PAY_MONEY",fkje);
		map.put("PROJECT_CODE", map.get("PRO_CODE"));
		bcs.insertPaymentDetail(map);

		List<Map<String, Object>> typeList = bcs.queryPaymentMouldDetail(map);
		for (Map<String, Object> temp : typeList) {
			if (temp != null && temp.size() > 0) {
				temp.put("PAYLIST_CODE", map.get("PAYLIST_CODE") + "");
				temp.put("PAYMENT_ID", map.get("BUY_CONTRACT_ID") + "");
				bcs.insertPaymentTerm(temp);
			}
		}

		/*
		 * //如果是销售型售后回租-添加一笔来款
		 * System.out.println("============"+obj.get("LEASE_MODEL"));
		 * Map<String,Object> map = new HashMap<String,Object>();
		 * if("back_leasing".equals(obj.get("LEASE_MODEL"))){ map.put("FUND_ID",
		 * Dao.selectOne("fi.fund.getFundId")); map.put("FUND_FUNDCODE",
		 * Dao.selectOne("fi.fund.getFundCode")); map.put("FUND_NOTDECO_STATE",
		 * "0"); map.put("FUND_STATUS", "0"); map.put("FUND_ISCONGEAL", "0");
		 * map.put("FUND_RED_STATUS", "0"); map.put("FUND_PRANT_ID", "0");
		 * map.put("FUND_IMPORT_PERSON", Security.getUser().getName());
		 * map.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
		 * 
		 * map.put("FUND_COMENAME",allInfoMap.get("LESSOR_NAME"));//来款人
		 * map.put("FUND_COMECODE",allInfoMap.get("LESSOR_NAME"));//来款户名
		 * map.put("FUND_ACCEPT_DATE",allInfoMap.get("SIGN_DATE"));//来款时间
		 * //map.put("FUND_ACCEPT_NAME",);//收款人
		 * //map.put("FUND_ACCEPT_CODE",);//收款户名
		 * map.put("FUND_RECEIVE_MONEY",Dao
		 * .selectOne("buyContract.getLEASE_TOPRIC",payListCode));//收款金额
		 * map.put("FUND_DOCKET","销售型售后回租设备款当做来款");//摘要
		 * Dao.insert("fi.fund.add", map); }
		 */

	}

	// 放款通过
	public void jbpmByPayMentPassStatue(String PAYMENT_JBPM_ID) {
		MaterialMgtService Material=new MaterialMgtService();
		Map<String,Object> m=new HashMap<>();
		m.put("PAYMENT_JBPM_ID", PAYMENT_JBPM_ID);
		m=Dao.selectOne("paymentApply.getpayment",m);
		Map<String,Object> money=Dao.selectOne("paymentApply.getSqk", m);
//		PaymentApplyService service = new PaymentApplyService();
//		service.tranxTradeNew();
//		Map<String,Object> map=new HashMap<String,Object>();
		if(money !=null && money.containsKey("MONEY") && !"0".equals(money.get("MONEY").toString())){
			throw new ActionException("请先发起核销首期款！");
		}
		Material.getLevelOneListFK("放款后", m.get("PROCODE").toString(), m.get("PAYMENT_ID").toString());
//		List<Map<String,Object>> listReturn=Dao.selectList("paymentApply.getTranx", m);
//		Map<String,Object> mapTitle=Dao.selectOne("paymentApply.getTranxHead",m);
//		new FIinterface().tranx(listReturn, mapTitle,"100002");
/********begin中金网银接口*********************************************/
		if(FIinterface.on_off()!=null){
			List<Map<String,Object>> listReturn=Dao.selectList("paymentApply.getZhongjin", m);
//			Map<String,Object> mapTitle=Dao.selectOne("paymentApply.getZhongjinHead",m);
//			new FIinterface().daifu(listReturn, mapTitle, "1");
			
			// 如果没有接口支付的金额，则不插入记录
			if(null != listReturn && listReturn.size() > 0){
				//批次号
				String batchNo = GUID.getTxNo();
				Map<String,Object> map = listReturn.get(0);
				if(map.get("BANK_NAME")==null||map.get("ACCOUNT_NAME")==null||
						map.get("ACCOUNT_NAME")==null||map.get("ACCOUNT_NUMBER")==null){
					throw new ActionException("银行账号信息不能为空"); 
				}
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> bankcode = new SysDictionaryMemcached().get("中金银行代码");
				Map<String,Object> m1;
				for (Map<String, Object> aBankcode : bankcode) {
					m1 = aBankcode;
					//匹配银行ID
					if (map.get("BANK_NAME").toString().contains(m1.get("FLAG").toString())) {
						map.put("BANK_CODE", m1.get("CODE").toString());
						break;
					}
				}
				if(map.get("BANK_CODE")==null){
					throw new ActionException("没有匹配到银行ID，请更换银行信息");
				}
				map.put("BATCH_NO", batchNo);
				map.put("TYPE", "1");//1代付
				map.put("USE_STATE", "-1");//未提交接口
				Dao.insert("rentWriteNew.insertZhongjinMiddle", map);
			}
		}
/***************end中金网银接口*****************************************************/
		Dao.update("paymentApply.jbpmByPayMentPassStatue", PAYMENT_JBPM_ID);
		Dao.insert("paymentApply.insRebate",PAYMENT_JBPM_ID);
		m.put("PAYEE", Security.getUser().getOrg().getPlatform());
		if(m.get("PAYEE")==null || "".equals(m.get("PAYEE"))){
			throw new ActionException("公司维护中融资租赁总公司记录不存在，请添加总公司信息");
		}
		m.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		if(m.get("MANAGER_ID")==null || "".equals(m.get("MANAGER_ID"))){
			throw new ActionException("公司维护中融资租赁总公司记录不存在，请添加总公司信息");
		}
		Map<String,Object> map_tem = Dao.selectOne("fi.fund.getBinkInfo", m);
		m.put("BANK", map_tem.get("FA_BINK"));
		if(m.get("BANK")==null || "".equals(m.get("BANK"))){
			throw new ActionException("融资租赁总公司银行卡开户行不存在，请添加融资租赁总公司银行卡开户行信息");
		}
		m.put("BANK_ACCOUNT", map_tem.get("FA_ACCOUNT"));
		if(m.get("BANK_ACCOUNT")==null || "".equals(m.get("BANK_ACCOUNT"))){
			throw new ActionException("融资租赁总公司银行卡账号不存在，请添加融资租赁总公司银行卡账号信息");
		}
		m.put("STATUS", 0);
		m.put("TYPE", 1);
		m.put("PAYMENT_JBPM_ID",PAYMENT_JBPM_ID);
		Dao.insert("paymentApply.insServiceFee",m);//插入服务费
		List<Map<String,Object>> list=Dao.selectList("paymentApply.selPaymentBank",PAYMENT_JBPM_ID);
		for (Map<String, Object> mapbank : list) {
			String PAYMENT_ID=Dao.getSequence("SEQ_FI_FUND_PAYMENT_DETAIL");
			mapbank.put("PAYMENT_ID", PAYMENT_ID);
			mapbank.put("PAYMENT_HEAD_ID", PAYMENT_JBPM_ID);
			Dao.insert("paymentApply.insPaymentDetail",mapbank);
			Dao.update("paymentApply.updPaymentBank",mapbank);
		}
	}
	//放款扣除保证金处理
	public void marginTreatment(String PAYMENT_JBPM_ID) {
		Map<String,Object> m=new HashMap<>();
		m.put("PAYMENT_JBPM_ID", PAYMENT_JBPM_ID);
		m=Dao.selectOne("paymentApply.getpayment",m);
		m=Dao.selectOne("paymentApply.getProject",m);
		m.put("MONEY", m.get("CS_PRICE"));
		String PAYMENT_HEAD_ID=Dao.getSequence("SEQ_FI_FUND_PAYMENT_HEAD");
		m.put("PAYMENT_JBPM_ID", PAYMENT_JBPM_ID);
		m.put("NEW_PAYMENT_ID", Dao.getSequence("SEQ_FI_FUND_PAYMENT_DETAIL"));
		m.put("NEW_PAYMENT_HEAD_ID", PAYMENT_HEAD_ID);
		if(!m.get("CS_PRICE").toString().equals("0")){
			Dao.insert("paymentApply.insMarginTreatment1",m);
			Dao.insert("paymentApply.insMarginTreatment",m);
		}
	}
	// /**
	// * 立项申请
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectApply(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "0");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("立项申请状态变更失败");
	// }
	// }
	//
	// /**
	// * 项目评审中(立项前)
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectCredit(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	//
	// // 需要判断流程节点
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "1");
	// m.put("SQFKCONFIRM_DATE", "");
	// Dao.update(namespace + "updateProjectStatus", m);
	// Dao.update(namespace + "updateProsub_company_name", m);
	// } else {
	// throw new ActionException("立项审核状态变更失败");
	// }
	// }
	//
	// /**
	// * 退回到01节点的动作配置
	// * @param jbpmId
	// * @author:King 2014-3-6
	// */
	// public void ProjectCredit01(String jbpmId){
	// this.getVeriable(jbpmId);
	// if (param.containsKey("PROJECT_ID")) {
	// int count = Dao.selectInt(namespace + "doShowFirstRefundCheck1", param);
	// if (count >=1) {
	// throw new ActionException("首期款已核销,请先进行首期款退款");
	// }
	// }
	// }
	//
	// /**
	// * 项目立项确认
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectConfirm(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "10");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("项目立项确认状态变更失败");
	// }
	// }
	//
	// /**
	// * 卡号验证通过
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void accountCheckPass(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "11");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("卡号验证通过状态变更失败");
	// }
	// }
	//
	//
	// /**
	// * 项目撤消
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectRevocation(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "100");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("项目撤消状态变更失败");
	// }
	// }
	//
	// /**
	// * 项目起租
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectStart(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "20");
	// Dao.update(namespace + "updateProjectStatus", m);
	//
	// try {
	// String PAY_CODE = Dao.selectOne(namespace + "queryPayCodeByProjectId",
	// m);
	// m.put("PAY_CODE", PAY_CODE);
	// Dao.update(namespace + "upMoneyToJoin", m);
	// } catch (Exception e) {
	//
	// }
	// } else {
	// throw new ActionException("项目起租状态变更失败");
	// }
	// /**
	// * 追加续保提醒记录 hxl 2014/3/13
	// * 涉及表 INSUR_REMIND
	// */
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// RenewalInsure.insertInsurRemind(project_id);
	// }
	// }
	//
	// /**
	// * 提前还租
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectAdvance(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "30");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("提前还租状态变更失败");
	// }
	// }
	//
	// /**
	// * 设备回购
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectRepo(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "35");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("设备回购状态变更失败");
	// }
	// }
	//
	// /**
	// * 质量问题结束
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectQualityProblemsEnd(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "36");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("质量问题结束状态变更失败");
	// }
	// }
	//
	// /**
	// * 项目留购
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectLeavingPurchase(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "40");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("项目留购状态变更失败");
	// }
	// }
	//
	// /**
	// * 信用审查
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectCreditReview(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "5");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("信用审查状态变更失败");
	// }
	// }
	//
	// /**
	// * 项目结束
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectEnd(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "50");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("项目结束状态变更失败");
	// }
	// }
	//
	// /**
	// * 首付款验证通过
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectFirstPayPass(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("STATUS", "14");
	// String PAYMENT_STATUS = Dao.selectOne(namespace + "doShowPAYMENT_STATUS",
	// m);
	//
	// if (!PAYMENT_STATUS.equals("2")) {
	// // 全额放款 判断首付款核销
	// int count = Dao.selectInt(namespace + "doShowFirstPayCheck", m);
	// try {
	// Dao.update(namespace + "updateProHeadInfo", m);
	// Dao.update(namespace + "updateProHeadInfo1", m);
	// Dao.update(namespace + "updateProHeadInfo2", m);
	// } catch (Exception e) {
	// }
	// if (count < 1) {
	// throw new ActionException("首期款未核销,请先核销首期款");
	// }
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("首付款验证变更失败");
	// }
	// }
	//
	// /**
	// * 首付款退款验证
	// *
	// * @param jbpmId
	// * @author:King 2013-9-22
	// */
	// public void ProjectFirstRefundPass(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// int count = Dao.selectInt(namespace + "doShowFirstRefundCheck", m);
	// try {
	// Dao.update(namespace + "updateProHeadInfo", m);
	// Dao.update(namespace + "updateProHeadInfo1", m);
	// Dao.update(namespace + "updateProHeadInfo2", m);
	// } catch (Exception e) {
	// }
	// if (count < 1) {
	// throw new ActionException("首期款已核销,请先进行首期款退款");
	// }
	// }
	//
	// /**
	// * 判断发票是否存在
	// *
	// * @param jbpmId
	// * @author:King 2014-1-17
	// */
	// public void isKPCheck(String jbpmId) {
	// this.getVeriable(jbpmId);
	// int count = Dao.update(namespace + "queryFPCount", param);
	// if (count >= 1) {
	// throw new ActionException("该项目发票在处理中，请先处理发票信息");
	// }
	// }
	//
	// /**
	// * DB确认租赁物通过
	// *
	// * @param jbpmId
	// */
	// public void dbConfirm(String jbpmId) {
	// this.getVeriable(jbpmId);
	// String project_id = null;
	// if (param.containsKey("PROJECT_ID")) {
	// project_id = param.get("PROJECT_ID") + "";
	// }
	// if (StringUtils.isNotBlank(project_id)) {
	// Map<String, Object> m = new HashMap<String, Object>();
	// m.put("PROJECT_ID", project_id);
	// m.put("DB_CONFRIM_DATE", "trunc(sysdate)");
	// Dao.update(namespace + "updateProjectStatus", m);
	// } else {
	// throw new ActionException("DB确认租赁物通过失败");
	// }
	// }
	
	
	public void SendNotification(String PROJECT_ID,String NODE_NAME,String JBPM)
	{
//		Map<String,Object> map=new HashMap<String,Object>();
//		Map<String,Object> map1=new HashMap<String,Object>();
//		Map<String, Object> mqz=new HashMap<String, Object>();
//		Map<String, Object> m=new HashMap<String, Object>();
//		String pdfName="";
//		if(JBPM.equals("立项审批流程")){
//			map=Dao.selectOne("bpm.status.getProjectSend",PROJECT_ID);
//		}else if(JBPM.equals("放款审批流程")){
//			map=Dao.selectOne("bpm.status.getPaymentSend",PROJECT_ID);
//		}
//		if(NODE_NAME.equals("审批结果")){
//			pdfName="审批通知函";
//		}else if(NODE_NAME.equals("放款审查审批通过")){
//			pdfName="放款通知函";
//		}
//		m.put("PDFNAME", pdfName);
//		String TYPE = "";
//			try {
//				if (map != null && map.get("CODE") != null && !map.get("CODE").equals("")) {
//					// 微信通知
//						TYPE = "WX"; 
//						new RunInterfaceTemplate().sendJbpmMessage(NODE_NAME, map, TYPE);
//				}
//				if(!"".equals(pdfName)){
//				if (map != null && map.get("EMAIL") != null && !map.get("EMAIL").equals("")) {
//					// 邮件通知
//					String email = (String) map.get("EMAIL");
//					String Time=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
//					String addresses[]={email};
//					String root = SkyEye.getConfig("file.path");
//					String imgPath = "";
//					String targetPath="";
//					System.out.println(m+"======================");
//					List<Map<String, Object>> list=Dao.selectList("contract.queryContractTemplateList1",m);
//					File[] file1 = new File[list.size()];
//					try {
//						ContractService service = new ContractService();
//						for (int j = 0; j < list.size(); j++) {
//							map1=list.get(j);
//							map1.put("PROJECT_ID", PROJECT_ID);
//							System.out.println(map1+"=========================");
//							String TPM_ID = map1.get("TEP_ID") + "";
//							// pdf文件的路径
//							String path = map1.get("TEMPLATE_PATH") + "";
//							String name = map1.get("FILE_NAME")+".pdf";
//							mqz=service.getElectronicSignature(map1);
//							String text=mqz.get("TPM_SEAL_SERVICE").toString();
//							String outputPath = service.ExpContractFile(TPM_ID, map1, path, name);
//							File outputFile = new File(outputPath);
//							targetPath = outputFile.getParent() + File.separator + Time + outputFile.getName();
//							imgPath = root + File.separator + "baseRes" + File.separator + mqz.get("TPM_SIGNATURE_LOGO") + ".gif";
//							try {
//								OASClient.execPdf(outputPath, targetPath, text, imgPath);
//							} catch (Exception e) {
//								e.printStackTrace();
//								throw new ActionException("添加签章失败");
//							}
//							file1[j]=new File(targetPath);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//						throw new ActionException("PDF导出失败!");
//					}
//					try {
//						this.sendMail1(addresses, pdfName, pdfName, file1);
//					} catch (Exception e) {
//						e.printStackTrace();
//						throw new ActionException("邮件发送失败!");
//					}
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
	}
	
	public void VerificationBank(String PAYMENT_JBPM_ID){
		Map<String,Object> m=Dao.selectOne("paymentApply.VerificationBank", PAYMENT_JBPM_ID);
		if(m==null || m.get("PAY_BANK_ACCOUNT")==null || "".equals(m.get("PAY_BANK_ACCOUNT").toString()))
		{
			throw new ActionException("请先录入收款银行信息");
		}
		if(m.get("BANK_ID")==null || "".equals(m.get("BANK_ID").toString())){
			throw new ActionException("请先选择关联银行后在提交到下一步");
		}
	}
	
	public void sqkVerification (String PAYMENT_JBPM_ID){
		Map<String,Object> m=new HashMap<>();
		m.put("PAYMENT_JBPM_ID", PAYMENT_JBPM_ID);
		m=Dao.selectOne("paymentApply.getpayment",m);
		Map<String,Object> money=Dao.selectOne("paymentApply.getSqk", m);
		Map Ys=Dao.selectOne("paymentApply.getYsqc",m);
		if(Ys !=null){
			m.put("QC", Ys.get("VALUE_STR").toString());
			Map yszj=Dao.selectOne("paymentApply.getSqkZj",m);
			if(yszj !=null && yszj.containsKey("MONEY") && !"0".equals(yszj.get("MONEY").toString())){
				throw new ActionException("提前预收租金为收完，请核销首期款！");
			}
		}
		if(money !=null && money.containsKey("MONEY") && !"0".equals(money.get("MONEY").toString())){
			throw new ActionException("请先发起核销首期款！");
		}
	}
	
	public String YZCARD(Map<String,Object> param) {
		BaseSchemeService service = new BaseSchemeService();
		param.put("CUST_ID", param.get("CLIENT_ID"));
		String msg = "验证成功！";
//		Boolean flag = true ;
		Map<String,Object> client=service.getClient(param);
		if(client==null)
		{
			msg="系统中无此客户！";
//			flag = false ;
		}else if("".equals(client.get("NAME"))){
			msg="系统中无此客户！";
//			flag = false ;
		}else if("".equals(client.get("ID_CARD_NO"))){
			msg="身份证号为空，请先录入身份证号！";
//			flag = false ;
		}
		List<Map<String, Object>> list = service.getIdCard(param);
		if(list.size()>0)
		{
			msg="系统已验证此客户！";
//			flag = true ;
		}
		
		//del gengchangbao
		/*else{
		List<Object> sbm=(List)new SysDictionaryMemcached().get("SBM");
		Map<String,Object> mapsbm=(Map<String,Object>) sbm.get(0);
		String xml="<ROWS><INFO><SBM>"+mapsbm.get("CODE")+"</SBM></INFO><ROW><GMSFHM>公民身份号码</GMSFHM><XM>姓名</XM></ROW>"
				+"<ROW FSD='北京' YWLX='租赁'><GMSFHM>"+client.get("ID_CARD_NO")+"</GMSFHM><XM>"+client.get("NAME")+"</XM></ROW></ROWS>";
		String LICENSE="";
		try {
			 xml=Nciic.executeClient(LICENSE, xml);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			msg="身份验证失败！";
			flag = false ;
		}
		Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); 
            Element rootElt = doc.getRootElement(); 
            Iterator iter = rootElt.elementIterator("ROW"); 
            Map<String,Object> m=new HashMap<String,Object>();
            m.put("CUST_ID", param.get("CLIENT_ID"));
            // 遍历head节点
            while (iter.hasNext()) {
                Element recordEle = (Element) iter.next();
                Iterator OUTPUT = recordEle.elementIterator("OUTPUT"); 
                while (OUTPUT.hasNext()) {
                    Element itemEle = (Element) OUTPUT.next();
                    Iterator ITEM = itemEle.elementIterator("ITEM"); 
                    while (ITEM.hasNext()) {
                        Element itemItem = (Element) ITEM.next();
                        String gmsfhm = itemItem.elementTextTrim("gmsfhm"); 
                        if(gmsfhm !=null && !"".equals(gmsfhm))
                        {
                        	m.put("CARD_ID", gmsfhm);
                        }
                        String result_gmsfhm = itemItem.elementTextTrim("result_gmsfhm");
                        if(result_gmsfhm !=null && !"".equals(result_gmsfhm))
                        {
                        	m.put("ID_CHECK_RESULT", result_gmsfhm);
                        }
                        String xm = itemItem.elementTextTrim("xm"); 
                        if(xm !=null && !"".equals(xm))
                        {
                        	m.put("CARD_NAME", xm);
                        }
                        String result_xm = itemItem.elementTextTrim("result_xm");
                        if(result_xm !=null && !"".equals(result_xm))
                        {
                        	m.put("NAME_CHECK_RESULT", result_xm);
                        }
                        String cym = itemItem.elementTextTrim("cym");
                        if(cym !=null && !"".equals(cym))
                        {
                        	m.put("CYM", cym);
                        }
                        String result_cym = itemItem.elementTextTrim("result_cym");
                        if(result_cym !=null && !"".equals(result_cym))
                        {
                        	m.put("result_cym", result_cym);
                        }
                        String xb = itemItem.elementTextTrim("xb"); 
                        if(xb !=null && !"".equals(xb))
                        {
                        	m.put("GENDER", xb);
                        }
                        String result_xb = itemItem.elementTextTrim("result_xb"); 
                        if(result_xb !=null && !"".equals(result_xb))
                        {
                        	m.put("result_xb", result_xb);
                        }
                        String mz = itemItem.elementTextTrim("mz");
                        if(mz !=null && !"".equals(mz))
                        {
                        	m.put("RACE", mz);
                        }
                        String result_mz = itemItem.elementTextTrim("result_mz");
                        if(result_mz !=null && !"".equals(result_mz))
                        {
                        	m.put("result_mz", result_mz);
                        }
                        String csrq = itemItem.elementTextTrim("csrq");
                        if(csrq !=null && !"".equals(csrq))
                        {
                        	m.put("BIRTH_DATE", csrq);
                        }
                        String result_csrq = itemItem.elementTextTrim("result_csrq");
                        if(result_csrq !=null && !"".equals(result_csrq))
                        {
                        	m.put("result_csrq", result_csrq);
                        }
                        String fwcs = itemItem.elementTextTrim("fwcs");
                        if(fwcs !=null && !"".equals(fwcs))
                        {
                        	m.put("FWCS", fwcs);
                        }
                        String result_fwcs = itemItem.elementTextTrim("result_fwcs");
                        if(result_fwcs !=null && !"".equals(result_fwcs))
                        {
                        	m.put("result_fwcs", result_fwcs);
                        }
                        String whcd = itemItem.elementTextTrim("whcd");
                        if(whcd !=null && !"".equals(whcd))
                        {
                        	m.put("WHCD", whcd);
                        }
                        String result_whcd = itemItem.elementTextTrim("result_whcd");
                        if(result_whcd !=null && !"".equals(result_whcd))
                        {
                        	m.put("result_whcd", result_whcd);
                        }
                        String hyzk = itemItem.elementTextTrim("hyzk");
                        if(hyzk !=null && !"".equals(hyzk))
                        {
                        	m.put("HYZT", hyzk);
                        }
                        String result_hyzk = itemItem.elementTextTrim("result_hyzk");
                        if(result_hyzk !=null && !"".equals(result_hyzk))
                        {
                        	m.put("result_hyzk",result_hyzk);
                        }
                        String zz = itemItem.elementTextTrim("zz");
                        if(zz !=null && !"".equals(zz))
                        {
                        	m.put("ZZ", zz);
                        }
                        String result_zz = itemItem.elementTextTrim("result_zz");
                        if(result_zz !=null && !"".equals(result_zz))
                        {
                        	m.put("result_zz", result_zz);
                        }
                        String jgssx = itemItem.elementTextTrim("jgssx");
                        if(jgssx !=null && !"".equals(jgssx))
                        {
                        	m.put("JGSSX", jgssx);
                        }
                        String result_jgssx = itemItem.elementTextTrim("result_jgssx");
                        if(result_jgssx !=null && !"".equals(result_jgssx))
                        {
                        	m.put("result_jgssx", result_jgssx);
                        }
                        String csdssx = itemItem.elementTextTrim("csdssx");
                        if(csdssx !=null && !"".equals(csdssx))
                        {
                        	m.put("CSDSSX", csdssx);
                        }
                        String result_csdssx = itemItem.elementTextTrim("result_csdssx");
                        if(result_csdssx !=null && !"".equals(result_csdssx))
                        {
                        	m.put("result_csdssx", result_csdssx);
                        }
                        String xp = itemItem.elementTextTrim("xp");
                        if(xp !=null && !"".equals(xp))
                        {
                        	m.put("IMAGE", xp);
                        }
                        String result_name = itemItem.elementTextTrim("result_name");
                        if(result_name !=null && !"".equals(result_name))
                        {
                        	m.put("SXBZXR", result_name);
                        }
                        String result_cust_code = itemItem.elementTextTrim("result_cust_code");
                        if(result_cust_code !=null && !"".equals(result_cust_code))
                        {
                        	m.put("CASE_CODE", result_cust_code);
                        }
                        String result_gist_unit = itemItem.elementTextTrim("result_gist_unit");
                        if(result_gist_unit !=null && !"".equals(result_gist_unit))
                        {
                        	m.put("GIST_UNIT", result_gist_unit);
                        }
                        String result_area_name = itemItem.elementTextTrim("result_area_name");
                        if(result_area_name !=null && !"".equals(result_area_name))
                        {
                        	m.put("AREA_NAME", result_area_name);
                        }
                        String result_performance = itemItem.elementTextTrim("result_performance");
                        if(result_performance !=null && !"".equals(result_performance))
                        {
                        	m.put("PERFORMANCE", result_performance);
                        }
                        String result_disreput_type_name = itemItem.elementTextTrim("result_disreput_type_name");
                        if(result_disreput_type_name !=null && !"".equals(result_disreput_type_name))
                        {
                        	m.put("DISREPUT_TYPE_NAME", result_disreput_type_name);
                        }
                        String result_publish_date = itemItem.elementTextTrim("result_publish_date");
                        if(result_publish_date !=null && !"".equals(result_publish_date))
                        {
                        	m.put("PUBLISH_DATE", result_publish_date);
                        }
                        String ERROR_MESAGE = itemItem.elementTextTrim("errormesage");
                        if(ERROR_MESAGE !=null && !"".equals(ERROR_MESAGE))
                        {
                        	m.put("ERROR_MESAGE", ERROR_MESAGE);
                        	msg = ERROR_MESAGE;
                        }
                        String errormesagecol = itemItem.elementTextTrim("errormesagecol");
                        if(errormesagecol !=null && !"".equals(errormesagecol))
                        {
                        	m.put("ERROR_MESAGECOL", errormesagecol);
                        }
                        String ErrorCode = itemItem.elementTextTrim("ErrorCode");
                        if(ErrorCode !=null && !"".equals(ErrorCode))
                        {
                        	m.put("ERROR_CODE", ErrorCode);
                        }
                        String ErrorMsg = itemItem.elementTextTrim("ErrorMsg");
                        if(ErrorMsg !=null && !"".equals(ErrorMsg))
                        {
                        	m.put("ERROR_MSG", ErrorMsg);
                        }
                    }
                }
            }
            m.put("CREATE_CUST_ID", Security.getUser().getId());
            m.put("CREATE_CUST", Security.getUser().getName());
            service.insAuthentication(m);
            
        } catch (DocumentException e) {
            e.printStackTrace();
            msg="读取身份验证信息失败！";
			flag = false ;
        } catch (Exception e) {
            e.printStackTrace();
            msg="读取身份验证信息失败！";
			flag = false ;
        }} 
		*/
		//del
		return msg;
	}
	
	/**
	 * 初审不通过验证DTI拒绝原因
	 * @param PROJECT_ID
	 * @author wanghl
	 * @datetime 2015年5月4日,下午3:00:43
	 */
	public void VerificationDTI(String PROJECT_ID){
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String,Object> param = new HashMap<>();
			param.put("PROJECT_ID",PROJECT_ID);
			List<Map<String,Object>> list = Dao.selectList("DTI.findByProjectId", param);
			if(list!=null&&list.size()>0){
				Map<String,Object> map = list.get(0);
				if(map.get("REPULSE_CAUSE")==null||"".equals(map.get("REPULSE_CAUSE").toString())){
					throw new ActionException("请选择拒绝原因");
				}
			}else{
				throw new ActionException("请选择拒绝原因");
			}
		}else {
			throw new ActionException("不通过状态变更失败");
		}
	}
	
	
	private void sendMail1(final String[] addresses, final String title, final String text, File[] files) throws Exception {
		String stmp = null;
		String username = null;
		String password = null;
		try {
		    @SuppressWarnings("unchecked")
			List<Object> list=(List)new SysDictionaryMemcached().get("系统邮箱");
			if (list != null) {
				for (Object aList : list) {
					Map<?, ?> m = (Map<?, ?>) aList;
					if (m.get("FLAG").toString().equalsIgnoreCase("stmp")) {
						stmp = m.get("CODE").toString();
					}
					if (m.get("FLAG").toString().equalsIgnoreCase("password")) {
						password = m.get("CODE").toString();
					}
					if (m.get("FLAG").toString().equalsIgnoreCase("username")) {
						username = m.get("CODE").toString();
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("读取邮箱配置失败", e);
		}
		if (stmp == null || username == null || password == null) throw new Exception("邮箱配置错误");

		if (addresses == null || title == null || text == null) throw new Exception("邮件格式错误");

		try {
			final String s1 = stmp;
			final String s2 = username;
			final String s3 = password;
			new Thread() {
				public void run() {
					try {
						new Mail(s1, s2, s3).sendHtml(addresses, title, text);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (Exception e) {
			throw new Exception("发送邮件失败:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 放款后督,档案保存
	 * @param PROJECT_ID
	 * @author zengqt
	 * @datetime 2015-6-8
	 */
	public void paymentArchives(String PROJECT_ID){
//		Map<String,Object> m=new HashMap<String,Object>();
//		m.put("PAYMENT_JBPM_ID", PAYMENT_JBPM_ID);
//		m=Dao.selectOne("paymentApply.getpayment",m);
//		String PROJECT_ID=m.get("PROJECT_ID").toString();
		CreditCoreFileInfoService service = new CreditCoreFileInfoService();
		service.doAddProjectContractList(PROJECT_ID,"");
	}
	
	public void updateOldPhotoColor(String projectId){
		Dao.update("project.updateOldPhotoColor",projectId);
	}
	
	/**
	 * 
	 * @param PROJECT_ID
	 * @author xingsumin
	 * @datetime 2015年12月10日14:29:13
	 */
	public void verifyProjectCredit(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
			m.put("PROJECT_ID", PROJECT_ID);
			
			int scheme=Dao.selectOne("project.getSchemeByProjectID", m);
			if(scheme==0){
				throw new ActionException("资料录入不全！");
			}
			
			int equip=Dao.selectOne("project.getEquipmentByProjectID", m);
			if(equip==0){
				throw new ActionException("资料录入不全！");
			}
			
//			int count=Dao.selectOne(namespace + "queryProjectStatusNew", m);
//			if(count>0){
//				throw new ActionException("该客户已有单子在信审环节，暂不能进件！");
//			}
			
			List<Map<String,Object> > projects =Dao.selectList(namespace + "queryProjectStatusNew",m);
			if(null!=projects&&projects.size()>0){
				for(Map<String,Object> map:projects){
					if(null!=map.get("STATUS_NEW")&&!"".equals(map.get("STATUS_NEW"))){
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> status = (List<Map<String, Object>>)DataDictionaryMemcached.getList("捷众流程状态");
						if(null!=status&&status.size()>0){
							  for(Map<String, Object> statu:status){
								     if(map.get("STATUS_NEW").equals(statu.get("FLAG"))&&Integer.parseInt(statu.get("CODE").toString())>19&&Integer.parseInt(statu.get("CODE").toString())<30){
								    	 throw new ActionException("该客户已有单子在信审环节，暂不能进件！");
								     }
							  }
						}
					}
				}
			}
		} 
	}
	/**
	 * 判断首付款是否核销
	 * @param ID
	 */
	public void isPaymentStatus(String ID) {
		Map<String,Object> param = new HashMap<>();
	    param.put("ID", ID);
	    int sumPayment= Dao.selectInt("project.querysumPaymentByProjectID",param);
		if(0 < sumPayment){
			throw new ActionException("首付款已核销，请冲红已核销的款项");
		}
	}
	/**
	 * 判断是否放款
	 * @param ID
	 */
	public void isLoanStatus(String ID) {
		Map<String,Object> param = new HashMap<>();
	    param.put("ID", ID);
	    int sumLoan= Dao.selectInt("project.queryLoanByProjectID",param);
		if(0 < sumLoan){
			throw new ActionException("已经放款，请先追回款项");
		}
	}
	
	/**
	 * 项目重签评审通过 JudgePass (评审通过,资料补齐 12)
	 * 
	 * @param PROJECT_ID
	 * @author: 
	 */
	public void JudgePassCQ(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<>();
//			Map<String, Object> para = new HashMap<>(); // 用来向查询提供——PROJECT_ID——参数
//			para.put("PROJECT_ID", PROJECT_ID); // 设置查询参数——PROJECT_ID
			m.put("PROJECT_ID", PROJECT_ID);
			m.put("STATUS", "8");
//			String LEASE_CODE = CodeService.getCode("融资租赁合同编号", null, PROJECT_ID);
//			m.put("LEASE_CODE", LEASE_CODE);
			Dao.update(namespace + "updateProjectStatus", m);
			List<Map<String,Object>> eqList = Dao.selectList(namespace + "queryAmountByProjectID",
					PROJECT_ID);
			for (Map<String, Object> eqMap : eqList) {
				int AMOUNT = Integer.parseInt(eqMap.get("AMOUNT") + "");
				for (int h = 0; h < AMOUNT; h++) {
					// 先复制
					Dao.insert(namespace + "insertEqAmount", eqMap);
				}
				// 在删除原有设备
				Dao.delete(namespace + "deleteEqById", eqMap);
			}
		} else {
			throw new ActionException("评审通过状态变更失败");
		}
		List<Map<String,Object>> list=Dao.selectList(namespace + "selcfsbsf",PROJECT_ID);
		//list是报价方案信息 不能为空
		if(list.size()>0){
			for (Map<String, Object> m : list) {
				m.put("PROJECT_ID", PROJECT_ID);
				if(m.containsKey("VALUE_STR") && "1".equals(m.get("VALUE_STR").toString())){
					Dao.update(namespace+"updcfsbsf",m);
				}else{
					// 自动生成支付表 和还款约定
					autoCopyRent(PROJECT_ID,m.get("ID")+"");
				}
			}
		}
	}
}

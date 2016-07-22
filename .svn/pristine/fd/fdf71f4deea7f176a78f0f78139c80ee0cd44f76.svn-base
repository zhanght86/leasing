package com.pqsoft.telephonenumberlist.action;

import java.io.IOException;
import java.util.Map;

import com.pqsoft.skyeye.api.*;
import com.pqsoft.telephonenumberlist.model.*;
import com.pqsoft.telephonenumberlist.model.report.Person;
import com.pqsoft.telephonenumberlist.model.report.ReportResponse;
import org.apache.velocity.VelocityContext;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.telephonenumberlist.model.report.DataReport;
import com.pqsoft.telephonenumberlist.service.TelephoneNumberListService;
import com.pqsoft.util.IOUtil;

/**
 * 电话清单-聚信立采集接口
 * @author wanghuaying
 *
 */
public class TelephoneNumberListAction   extends Action {

	private TelephoneNumberListService telephoneNumberListService = TelephoneNumberListService.getInstance();

	public Reply execute() {
		return null;
	}

	/**
	 * 处理获取通信数据报告的请求
	 *
	 * @return 客户信息信息录取页
     */
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"项目修改","电话清单-聚信立采集接口","采集接口"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply toReport() {
		//http://localhost:8080/jieyi-project/telephonenumberlist/TelephoneNumberList!execute.action
		Map<String, Object> params = _getParameters();
		// 通过项目id查找身份证，姓名和手机号
		String projectId = (String) params.get("projectId");
		Map<String, Object> client = telephoneNumberListService.getClientInfoByProjectId(projectId);
		// 有身份证
		String idCardNo = (String) client.get("ID_CARD_NO");
		if(Strings.isNullOrEmpty(idCardNo)){
			throw new ActionException("请先完善客户信息！");
		}
		//返回申请页面
		VelocityContext context = new VelocityContext();
		// 匹配页面参数名重新包装
		context.put("projectId", projectId);
		context.put("idCardNo", idCardNo);
		context.put("name", client.get("NAME"));
		context.put("telPhone", client.get("TEL_PHONE"));
		context.put("updt", params.get("updt"));
		return new ReplyHtml(VM.html("telephonenumberlist/telephoneNumberList.vm", context));
	}

	/**
	 * 处理申请收集通信数据的请求
	 *
	 * @return 进一步的客户数据收集信息，等待信息，通信数据信息或者错误反馈信息
     */
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"接口管理","电话清单-聚信立采集接口","采集接口"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply application(){
		//http://localhost:8080/jieyi-project/telephonenumberlist/TelephoneNumberList!application.action
		try{
			//将输入流转化为字符串
			String paramString = IOUtil.getStreamString(SkyEye.getRequest().getInputStream());
			Response response = telephoneNumberListService.application(paramString);
			return resolve(response);
		}catch (IOException e){
			Dao.rollback();
			return new ReplyAjax(false, "", "请求失败！");
		}catch (ActionException e){
			Dao.rollback();
			return new ReplyAjax(false, "", e.getMessage());
		}
    }

	/**
	 * 处理收集通信数据的请求
	 *
	 * @return 等待信息，通信数据信息或者错误反馈信息
     */
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"接口管理","电话清单-聚信立采集接口","采集接口"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply collect(){
		//http://localhost:8080/jieyi-project/telephonenumberlist/TelephoneNumberList!collect.action
		try{
			//将输入流转化为字符串
			String paramString = IOUtil.getStreamString(SkyEye.getRequest().getInputStream());
			Response response = telephoneNumberListService.collect(paramString);
			return resolve(response);
		}catch (IOException e){
			Dao.rollback();
			return new ReplyAjax(false, "", "请求失败！");
		}catch (ActionException e){
			Dao.rollback();
			return new ReplyAjax(false, "", e.getMessage());
		}
	}

	/**
	 * 根据响应对象类型决定返回值
	 *
	 * @param response 响应对象
	 * @return 进一步的客户数据收集信息，等待信息，通信数据信息或者错误反馈信息
     */
	private Reply resolve(Response response) {
		if(response instanceof ReportResponse){
			ReportResponse reportResponse = (ReportResponse) response;
			return new ReplyAjax(reportResponse.isSuccess(), "", reportResponse.getMessage());
		}else if(response instanceof ApplicationResponse){
			ApplicationResponse applicationResponse = (ApplicationResponse) response;
			return new ReplyAjax(applicationResponse.isSuccess(), JSON.toJSONStringWithDateFormat(applicationResponse.getApplicationResponseData(), "yyyy-MM-dd HH;mm:ss"), applicationResponse.getMessage());
		}else if(response instanceof CollectResponse){
			CollectResponse collectResponse = (CollectResponse) response;
			return new ReplyAjax(collectResponse.isSuccess(), JSON.toJSONStringWithDateFormat(collectResponse.getData(), "yyyy-MM-dd HH:mm:ss"), collectResponse.getMessage());
		}else{
			throw new ActionException("未知请求！");
		}
	}

	/**
	 * 处理跳转报告页面的请求
	 *
	 * @return 数据报告页面
     */
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"项目修改","电话清单-聚信立采集接口","采集接口"})
	@aDev(code = "D050", email = "linjunzhong@huashenghaoche.com", name = "钟林俊")
	public Reply toReportPage(){
		VelocityContext context = new VelocityContext(_getParameters());
		return new ReplyHtml(VM.html("telephonenumberlist/telephoneNumberList_report.vm", context));
	}

	/**
	 * 处理获取报告数据的请求
	 *
	 * @return 报告数据
     */
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"项目修改","电话清单-聚信立采集接口","采集接口"})
	@aDev(code = "D050", email = "linjunzhong@huashenghaoche.com", name = "钟林俊")
	public Reply accessDataReport(){
		try{
			CollectReport queryCollectReport = transformParams(IOUtil.getStreamString(SkyEye.getRequest().getInputStream()));
			CollectReport collectReport = telephoneNumberListService.queryCollectReport(queryCollectReport);
			ReportResponse reportResponse = new ReportResponse();
			reportResponse.setSuccess(true);
			reportResponse.setDataReport(collectReport.getDataReport());
			return new ReplyAjax(reportResponse.isSuccess(), JSON.toJSONStringWithDateFormat(reportResponse.getDataReport(), "yyyy-MM-dd HH:mm:ss"), reportResponse.getNote());
		}catch (IOException e){
			Dao.rollback();
			return new ReplyAjax(false, "", "获取请求参数失败！");
		}
	}

	/**
	 * 转换请求参数
	 *
	 * @param jsonParam json格式字符串
	 * @return 参数封装对象
     */
	private CollectReport transformParams(String jsonParam) {
		@SuppressWarnings("unchecked")
		Map<String,Object> parameters = (Map<String, Object>) JSON.parse(jsonParam);
		String projectIdStr = String.valueOf(parameters.get("projectId"));
		String name = String.valueOf(parameters.get("name"));
		String account = String.valueOf(parameters.get("cellPhone"));
		String idCardNum = String.valueOf(parameters.get("idCardNum"));

		if(Strings.isNullOrEmpty(name)){
			throw new ActionException("客户姓名获取失败！");
		}
		if(Strings.isNullOrEmpty(account)){
			throw new ActionException("客户手机号获取失败！");
		}
		if(Strings.isNullOrEmpty(idCardNum)){
			throw new ActionException("客户身份证号获取失败！");
		}

		CollectReport collectReport = new CollectReport();
//		if(!"null".equals(projectIdStr) && !"".equals(projectIdStr)){
//			Long projectId = Long.parseLong(projectIdStr);
//			collectReport.setProjectId(projectId);
//		}
		Collect collect = new Collect();
		collect.setAccount(account);
		DataReport dataReport = new DataReport();
		Person person = new Person();
		person.setIdCardNum(idCardNum);
		person.setRealName(name);
		dataReport.setPerson(person);
		collectReport.setCollect(collect);
		collectReport.setDataReport(dataReport);
		return collectReport;
	}

}

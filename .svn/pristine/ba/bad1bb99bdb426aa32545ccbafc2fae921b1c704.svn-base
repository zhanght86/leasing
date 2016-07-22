package com.pqsoft.call.action;
/**
 *  05. 呼叫中心审批
 *  @author 韩晓龙
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.call.service.CallCenterFlowService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.util.UTIL;

public class CallCenterFlowAction extends Action{
	
	private String vmPath = "project/";
	private CallCenterFlowService service = new CallCenterFlowService();
	
	@Override
	//@aPermission(name = { "立项流程", "呼叫中心审批", "信息验证"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		//需要 客户编号 CLIENT_ID
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		// 客户信息service 个人NP
		CustomersService custService = new CustomersService();
		// 以下三个参照 杨雪 的方法
		context.put("custInfo", custService.findCustDetailXZS(param));// 1客户详细信息
		context.put("custLinkInfo", custService.findCustLinkInfoXZS(param));//3客户关联信息 配偶信息
		
		List<Object> marital_status = (List)new DataDictionaryMemcached().get("婚姻状况");
		context.put("marital_status", marital_status);
		
		//公司性质
		List<Object> com_typeL = (List)new DataDictionaryMemcached().get("公司性质");
		context.put("com_typeL", com_typeL);
		
		//以下为租赁物信息验证
		
		context.put("FORMAT", UTIL.FORMAT);
		context.put("productInfo", service.selectProductDetailById(param));
		
		context.put("TYPE", param.get("TYPE"));//客户类型
		context.put("PROJ_ID", param.get("PROJ_ID"));//项目编号
		context.put("PROJECT_ID", param.get("PROJECT_ID"));//项目编号数字
		return new ReplyHtml(VM.html(vmPath + "callCheckForJbpm.vm", context));
	}
	
	//@aPermission(name = { "立项流程", "呼叫中心审批", "信息保存"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply saveMessage() {
		Map<String,Object> param = _getParameters();
		System.out.println("---------------------param="+param);
		/*
		 * 保存承租人的信息    个人或者法人
		 */
		param.put("CREATOR", Security.getUser().getName());
		if ("NP".equals(param.get("TYPE"))) {
			//FIL_IDENTITY_CHECK_INFO   //自然人信息验证
			//1 先删除原有
			service.deleteNPDetailById(param);
			//2 再保存新的验证信息
			service.insertNPDetailById(param);
		} else {
			// 法人LP
			//FIL_IDENTITYLP_CHECK_INFO  //法人信息验证
			//1 先删除原有
			service.deleteLPDetailById(param);
			//2 再保存新的验证信息
			service.insertLPDetailById(param);
		}
		
		//保存小助手修改信息
		service.doAddCustBaseINfoXZS(param);
		
		
		/*
		 * 保存租赁物确认信息
		 * 	FIL_EQUIP_CHECK_INFO  //租赁物信息验证
		 */
		//先删除原有的信息
		service.deleteProductDetailById(param);
		Map tempMap = new HashMap();
		//查询该项目设备信息
		List eqList = service.selectProductDetailById(param);
		String unit = "";
		for (int i = 0; i < eqList.size(); i++) {
			tempMap.clear();
			tempMap.putAll(param);
			tempMap.putAll((Map) eqList.get(i));
			//融资期限需要变换
			unit = ((Map) eqList.get(i)).get("UNIT").toString();
			if (!"月".equals(unit)) {
				if("双月".equals(unit)){
					tempMap.put("LEASE_TERM", (Integer.parseInt(tempMap.get("LEASE_TERM").toString()))*2);
				}
				if("季".equals(unit)){
					tempMap.put("LEASE_TERM", (Integer.parseInt(tempMap.get("LEASE_TERM").toString()))*3);
				}
				if("半年".equals(unit)){
					tempMap.put("LEASE_TERM", (Integer.parseInt(tempMap.get("LEASE_TERM").toString()))*6);
				}
				if("年".equals(unit)){
					tempMap.put("LEASE_TERM", (Integer.parseInt(tempMap.get("LEASE_TERM").toString()))*12);
				}
			}
			//规范格式
			tempMap.put("IS_SUP", param.get("IS_SUP" + (i+1)));
			tempMap.put("IS_EQ", param.get("IS_EQ" + (i+1)));
			tempMap.put("TOTAL_PRICE", param.get("TOTAL_PRICE" + (i+1)));
			tempMap.put("IS_MONEY", param.get("IS_MONEY" + (i+1)));
			tempMap.put("IS_SIGN", param.get("IS_SIGN" + (i+1)));
			tempMap.put("IS_GRANT", param.get("IS_GRANT" + (i+1)));
			tempMap.put("PRODUCT_IS_AGREE", param.get("PRODUCT_IS_AGREE" + (i+1)));
			//插入新的设备信息
			service.insertProductDetailById(tempMap);
		}
		return  new ReplyAjax(true,"");
	}
	
	//@aPermission(name = { "立项流程", "呼叫中心页签", "信息显示"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply showProductDetail() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("productInfo", service.selectProductAlreadyDetailById(param));//3客户租赁物
		if ("NP".equals(param.get("TYPE"))) {
			//婚姻状况
			context.put("MARRY_LIST", new DataDictionaryMemcached().get("婚姻状况"));
			context.put("custNPInfo", service.selectNPAlreadyDetailById(param));
			//查询客户及配偶的照片及验证结果
			context.put("custNPPhote", service.queryPhone(param));
		} else {
			// 法人LP
			context.put("custLPInfo", service.selectLPAlreadyDetailById(param));
		}
		context.put("TYPE", param.get("TYPE"));//客户类型
		context.put("PROJ_ID", param.get("PROJ_ID"));//项目编号
		context.put("PROJECT_ID", param.get("PROJECT_ID"));//项目编号数字
		context.put("param", param);
		return new ReplyHtml(VM.html(vmPath + "callCheckForJbpmOnlyRead.vm", context));
	}
}

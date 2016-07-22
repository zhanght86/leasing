package com.pqsoft.contract.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.contract.service.BuyContractService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.fhfaManager.FhfaManagerService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
public class BuyContractAction extends Action {

	private VelocityContext context = new VelocityContext();

	private BuyContractService service = new BuyContractService();

	/**
	 * 买卖合同
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理", "买卖合同管理", "列表显示" })
	public Reply execute() {
		//行业类型
		String TYPE = "业务类型";
				List<Object> list = (List) new DataDictionaryMemcached().get(TYPE);
		context.put("ProjectTypeList", list);	
		return new ReplyHtml(VM.html("contract/buyContractList.vm", context));
	}
	
	/**
	 * 买卖合同管理首页
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	public Reply buyContractListing() {
		Map<String, Object> param = _getParameters();
		Page page = service.queryPage(param);
		return new ReplyAjaxPage(page);
	}

	
	/**
	 * 买卖合同
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理", "买卖合同管理", "新建"})
	public Reply addBuyContractPage(){
		Map<String, Object> param = _getParameters();
		context.put("param", _getParameters());
		context.put("Format", UTIL.FORMAT);
		
		// 项目ID
		String projectId = null == _getParameters().get("PROJECT_ID") ? null
				: _getParameters().get("PROJECT_ID").toString();

		String lease_model = service.queryProjecdModel(projectId);
		context.put("LEASE_MODEL", lease_model);
		// 客户ID
		String clientId = null == _getParameters().get("CLIENT_ID") ? null
				: _getParameters().get("CLIENT_ID").toString();

		
		//平台ID
		String platformId = Security.getUser().getOrg().getPlatformId() ;
		Long fhfa_id = null == platformId ? null : Long.valueOf(platformId) ;
		FhfaManagerService fhfaManagerService = new FhfaManagerService() ;
		Object fhfaManager = fhfaManagerService.selectFHFAManagerById(fhfa_id) ;
		context.put("FHFAMANAGER", fhfaManager) ;
		
		// 支付ID
		String PAY_ID = null == _getParameters().get("ID") ? null
				: _getParameters().get("ID").toString();
		// 查询录入的基本信息
		Map<String, Object> RENTHEAD = service.doShowRentHeadByPayId(PAY_ID);
		context.put("RENTHEAD", RENTHEAD);

		// 生成买卖合同编号
		String buyContractCode = CodeService.getCode("买卖合同编号", clientId,
				projectId);
		context.put("BUY_CONTRACT_CODE", buyContractCode);
		param.putAll(RENTHEAD);
		if (RENTHEAD.containsKey("PAYLIST_CODE")) {
			List list = new projectService().queryEqByPID(RENTHEAD);
			context.put("PROEQULIST", list);
			Object equipment = null == list ?null: list.get(list.size()-1) ;
			context.put("EQUIPMENT",equipment);
			context.put("SCHEMELIST", service.queryPaymentByPaylistCode(param));
		}
		//通过PLATFORM_TYPE 查询约束条件
		context.put("normList", service.queryPaymentMouldDetail(param));
		return new ReplyHtml(VM.html("contract/buyContract_add.vm", context));
	
	}
	
	/**
	 * 买卖合同
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理", "买卖合同管理", "修改" })
	public Reply editBuyContractPage(){
		Map<String, Object> param = _getParameters();
		context.put("param", _getParameters());
		context.put("Format", UTIL.FORMAT);
		
		// 项目ID
		String projectId = null == _getParameters().get("PROJECT_ID") ? null
				: _getParameters().get("PROJECT_ID").toString();

		// 客户ID
		String clientId = null == _getParameters().get("CLIENT_ID") ? null
				: _getParameters().get("CLIENT_ID").toString();
		context.put("CLIENT_ID1", clientId);
		
		//平台ID
		
				String platformId = Security.getUser().getOrg().getPlatformId() ;
				Long fhfa_id = null == platformId ? null : Long.valueOf(platformId) ;
				FhfaManagerService fhfaManagerService = new FhfaManagerService() ;
				Object fhfaManager = fhfaManagerService.selectFHFAManagerById(fhfa_id) ;
				context.put("FHFAMANAGER", fhfaManager) ;	
		
		//买卖合同主键ID
		Long id =  null == _getParameters().get("ID") ? null
				: Long.valueOf(_getParameters().get("ID").toString());
		Map<String,Object> buyContract = service.getBuyContractAndFilRentPlanHeadById(id) ;
		context.put("BUYCONTRACT", buyContract) ;
		
		Map<String,Object> paymentMouldDetailParm = new HashMap<String,Object>() ;
		paymentMouldDetailParm.put("PLATFORM_TYPE", buyContract.get("PLATFORM_TYPE")) ;
		context.put("normList", service.queryPaymentMouldDetail(buyContract));
		List<Map<String,Object>> paymentDetails = service.getPaymentDetailsByBuyContractId(id) ;
		//Map<String,List<Map<String,Object>>> paymentTermsMap = new HashMap<String,List<Map<String,Object>>>() ;
		context.put("PROJECT_CODE1", paymentDetails.get(0).get("PROJECT_CODE"));
		for(Map<String,Object> map:paymentDetails){
			Map<String,Object> refer = new HashMap<String,Object>() ;
			refer.put("PAYMENT_ID", map.get("ID").toString()) ;
			List<Map<String,Object>> paymentTerms = service.getPaymentTermsByPaymentDetailId(refer) ;
			map.put("paymentTerms",paymentTerms) ;
		}
		context.put("paymentDetails", paymentDetails) ;
		if(buyContract.containsKey("PAYLIST_CODE")){
			List list = new projectService().queryEqByPID(buyContract);
			context.put("PROEQULIST", list); 
			context.put("EQUIPMENT", list.get(0));
		}
		return new ReplyHtml(VM.html("contract/buyContract_edit.vm", context));

	}
	
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理", "买卖合同管理", "修改" })
	public Reply editBuyContractPageProject(){
		Map<String, Object> param = _getParameters();
		context.put("param", _getParameters());
		context.put("Format", UTIL.FORMAT);
		
		// 项目ID
		String projectId = null == _getParameters().get("PROJECT_ID") ? null
				: _getParameters().get("PROJECT_ID").toString();

		// 客户ID
		String clientId = null == _getParameters().get("CLIENT_ID") ? null
				: _getParameters().get("CLIENT_ID").toString();

		
		//平台ID
		String platformId = Security.getUser().getOrg().getPlatformId() ;
		Long fhfa_id = null == platformId ? null : Long.valueOf(platformId) ;
		FhfaManagerService fhfaManagerService = new FhfaManagerService() ;
		Object fhfaManager = fhfaManagerService.selectFHFAManagerById(fhfa_id) ;
		context.put("FHFAMANAGER", fhfaManager) ;
		
		//买卖合同主键ID
		Long id =  null == _getParameters().get("ID") ? null
				: Long.valueOf(_getParameters().get("ID").toString());
		Map<String,Object> buyContract = service.getBuyContractAndFilRentPlanHeadById(id) ;
		context.put("BUYCONTRACT", buyContract) ;
		
		Map<String,Object> paymentMouldDetailParm = new HashMap<String,Object>() ;
		paymentMouldDetailParm.put("PLATFORM_TYPE", buyContract.get("PLATFORM_TYPE")) ;
		context.put("normList", service.queryPaymentMouldDetail(buyContract));
		List<Map<String,Object>> paymentDetails = service.getPaymentDetailsByBuyContractId(id) ;
		//Map<String,List<Map<String,Object>>> paymentTermsMap = new HashMap<String,List<Map<String,Object>>>() ;
		for(Map<String,Object> map:paymentDetails){
			Map<String,Object> refer = new HashMap<String,Object>() ;
			refer.put("PAYMENT_ID", map.get("ID").toString()) ;
			List<Map<String,Object>> paymentTerms = service.getPaymentTermsByPaymentDetailId(refer) ;
			map.put("paymentTerms",paymentTerms) ;
		}
		context.put("paymentDetails", paymentDetails) ;
		if(buyContract.containsKey("PAYLIST_CODE")){
			List list = new projectService().queryEqByPID(buyContract);
			context.put("PROEQULIST", list); 
		}
		return new ReplyHtml(VM.html("contract/buyContract_edit.vm", context));

	}
	
	/**
	 * 买卖合同
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理", "买卖合同管理", "查看" })
	public Reply showBuyContractPage(){
		
		@SuppressWarnings("unused")
		Map<String, Object> param = _getParameters();
		context.put("param", _getParameters());
		context.put("Format", UTIL.FORMAT);
		
		// 项目ID
		String projectId = null == _getParameters().get("PROJECT_ID") ? null
				: _getParameters().get("PROJECT_ID").toString();

		// 客户ID
		String clientId = null == _getParameters().get("CLIENT_ID") ? null
				: _getParameters().get("CLIENT_ID").toString();

		
		//平台ID
	
			
				String platformId = Security.getUser().getOrg().getPlatformId() ;
				Long fhfa_id = null == platformId ? null : Long.valueOf(platformId) ;
					FhfaManagerService fhfaManagerService = new FhfaManagerService() ;
					Object fhfaManager = fhfaManagerService.selectFHFAManagerById(fhfa_id) ;
					context.put("FHFAMANAGER", fhfaManager) ;	
		

		//买卖合同主键ID
		Long id =  null == _getParameters().get("ID") ? null
				: Long.valueOf(_getParameters().get("ID").toString());
		Map<String,Object> buyContract = service.getBuyContractAndFilRentPlanHeadById(id) ;
		context.put("BUYCONTRACT", buyContract) ;
		
		Map<String,Object> paymentMouldDetailParm = new HashMap<String,Object>() ;
		paymentMouldDetailParm.put("PLATFORM_TYPE", buyContract.get("PLATFORM_TYPE")) ;
		context.put("normList", service.queryPaymentMouldDetail(buyContract));
		List<Map<String,Object>> paymentDetails = service.getPaymentDetailsByBuyContractId(id) ;
		//Map<String,List<Map<String,Object>>> paymentTermsMap = new HashMap<String,List<Map<String,Object>>>() ;
		for(Map<String,Object> map:paymentDetails){
			Map<String,Object> refer = new HashMap<String,Object>() ;
			refer.put("PAYMENT_ID", map.get("ID").toString()) ;
			List<Map<String,Object>> paymentTerms = service.getPaymentTermsByPaymentDetailId(refer) ;
			map.put("paymentTerms",paymentTerms) ;
		}
		context.put("paymentDetails", paymentDetails) ;
		if(buyContract.containsKey("PAYLIST_CODE")){
			List list = new projectService().queryEqByPID(buyContract);
			context.put("PROEQULIST", list); 
		}
		return new ReplyHtml(VM.html("contract/buyContract_show.vm", context));

	}
	
	/**
	 * 买卖合同
	 */
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
//	@aPermission(name = { "项目管理", "项目一览", "核心要件","买卖合同","查看" })
//	public Reply showBuyContractPageProject(){
//		
//		Map<String, Object> param = _getParameters();
//		context.put("param", _getParameters());
//		context.put("Format", UTIL.FORMAT);
//		
//		// 项目ID
//		String projectId = null == _getParameters().get("PROJECT_ID") ? null
//				: _getParameters().get("PROJECT_ID").toString();
//
//		// 客户ID
//		String clientId = null == _getParameters().get("CLIENT_ID") ? null
//				: _getParameters().get("CLIENT_ID").toString();
//
//		
//		//平台ID
//		String platformId = Security.getUser().getOrg().getPlatformId() ;
//		Long fhfa_id = null == platformId ? null : Long.valueOf(platformId) ;
//		FhfaManagerService fhfaManagerService = new FhfaManagerService() ;
//		Object fhfaManager = fhfaManagerService.selectFHFAManagerById(fhfa_id) ;
//		context.put("FHFAMANAGER", fhfaManager) ;
//		
//		//买卖合同主键ID
//		Long id =  null == _getParameters().get("ID") ? null
//				: Long.valueOf(_getParameters().get("ID").toString());
//		Map<String,Object> buyContract = service.getBuyContractAndFilRentPlanHeadById(id) ;
//		context.put("BUYCONTRACT", buyContract) ;
//		
//		Map<String,Object> paymentMouldDetailParm = new HashMap<String,Object>() ;
//		paymentMouldDetailParm.put("PLATFORM_TYPE", buyContract.get("PLATFORM_TYPE")) ;
//		context.put("normList", service.queryPaymentMouldDetail(buyContract));
//		List<Map<String,Object>> paymentDetails = service.getPaymentDetailsByBuyContractId(id) ;
//		//Map<String,List<Map<String,Object>>> paymentTermsMap = new HashMap<String,List<Map<String,Object>>>() ;
//		for(Map<String,Object> map:paymentDetails){
//			Map<String,Object> refer = new HashMap<String,Object>() ;
//			refer.put("PAYMENT_ID", map.get("ID").toString()) ;
//			List<Map<String,Object>> paymentTerms = service.getPaymentTermsByPaymentDetailId(refer) ;
//			map.put("paymentTerms",paymentTerms) ;
//		}
//		context.put("paymentDetails", paymentDetails) ;
//		if(buyContract.containsKey("PAYLIST_CODE")){
//			List list = new projectService().queryEqByPID(buyContract);
//			context.put("PROEQULIST", list); 
//		}
//		return new ReplyHtml(VM.html("contract/buyContract_show.vm", context));
//
//	}

	/**
	 * 买卖合同
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	public Reply buyContractHandle() {
		Map<String, Object> param = _getParameters();
		context.put("param", _getParameters());
		context.put("Format", UTIL.FORMAT);
		
		// 项目ID
		String projectId = null == _getParameters().get("PROJECT_ID") ? null
				: _getParameters().get("PROJECT_ID").toString();

		// 客户ID
		String clientId = null == _getParameters().get("CLIENT_ID") ? null
				: _getParameters().get("CLIENT_ID").toString();

		
		//平台ID
		String platformId = Security.getUser().getOrg().getPlatformId() ;
		Long fhfa_id = null == platformId ? null : Long.valueOf(platformId) ;
		FhfaManagerService fhfaManagerService = new FhfaManagerService() ;
		Object fhfaManager = fhfaManagerService.selectFHFAManagerById(fhfa_id) ;
		context.put("FHFAMANAGER", fhfaManager) ;
		
		//方法类型   增加=add 更新=update 查询=query 
		String methodType = null == _getParameters().get("METHOD_TYPE") ? null
				: _getParameters().get("METHOD_TYPE").toString();
			context.put("METHOD_TYPE", methodType) ;
		if("add".equals(methodType)){
			// 支付ID
			String PAY_ID = null == _getParameters().get("ID") ? null
					: _getParameters().get("ID").toString();
			// 查询录入的基本信息
			Map<String, Object> RENTHEAD = service.doShowRentHeadByPayId(PAY_ID);
			context.put("RENTHEAD", RENTHEAD);

			// 生成买卖合同编号
			String buyContractCode = CodeService.getCode("买卖合同编号", clientId,
					projectId);
			context.put("BUY_CONTRACT_CODE", buyContractCode);
			param.putAll(RENTHEAD);
			if (RENTHEAD.containsKey("PAYLIST_CODE")) {
				List list = new projectService().queryEqByPID(RENTHEAD);
				context.put("PROEQULIST", list);
				Object equipment = null == list ?null: list.get(list.size()-1) ;
				context.put("EQUIPMENT",equipment);
				context.put("SCHEMELIST", service.queryPaymentByPaylistCode(param));
			}
			//通过PLATFORM_TYPE 查询约束条件
			context.put("normList", service.queryPaymentMouldDetail(param));
			return new ReplyHtml(VM.html("contract/buyContract_add.vm", context));
		}else if("update".equals(methodType) || "query".equals(methodType)){
			//买卖合同主键ID
			Long id =  null == _getParameters().get("ID") ? null
					: Long.valueOf(_getParameters().get("ID").toString());
			Map<String,Object> buyContract = service.getBuyContractAndFilRentPlanHeadById(id) ;
			context.put("BUYCONTRACT", buyContract) ;
			
			Map<String,Object> paymentMouldDetailParm = new HashMap<String,Object>() ;
			paymentMouldDetailParm.put("PLATFORM_TYPE", buyContract.get("PLATFORM_TYPE")) ;
			context.put("normList", service.queryPaymentMouldDetail(buyContract));
			List<Map<String,Object>> paymentDetails = service.getPaymentDetailsByBuyContractId(id) ;
			//Map<String,List<Map<String,Object>>> paymentTermsMap = new HashMap<String,List<Map<String,Object>>>() ;
			for(Map<String,Object> map:paymentDetails){
				Map<String,Object> refer = new HashMap<String,Object>() ;
				refer.put("PAYMENT_ID", map.get("ID").toString()) ;
				List<Map<String,Object>> paymentTerms = service.getPaymentTermsByPaymentDetailId(refer) ;
				map.put("paymentTerms",paymentTerms) ;
			}
			context.put("paymentDetails", paymentDetails) ;
			if(buyContract.containsKey("PAYLIST_CODE")){
				List list = new projectService().queryEqByPID(buyContract);
				context.put("PROEQULIST", list); 
			}
			return new ReplyHtml(VM.html("contract/buyContract_queryOrUpdate.vm", context));
		}else{
			
		}		
		return null;
	}

	
	@aPermission(name = { "合同管理", "买卖合同管理", "新建"})
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aAuth(type = aAuthType.LOGIN)
	public Reply saveBuyContract() {
		Map<String, Object> param = _getParameters();
		JSONObject  object = JSONObject.fromObject(param.get("alldata"));
		object.put("USER_CODE", Security.getUser().getCode());		
		int a = service.insertBuyContractAndPayment(object) ;
		
		boolean flag=false;
		String msg = "添加失败" ;
		if(a>0)
		{
			flag=true;
			msg = "添加成功" ;
		}
		return new ReplyAjax(flag, msg);
	}
	
	
//	@aPermission(name = { "合同中心", "买卖合同", "更新" })
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aAuth(type = aAuthType.LOGIN)
	public Reply updateBuyContract() {
		Map<String, Object> param = _getParameters();
		JSONObject  object = JSONObject.fromObject(param.get("alldata"));
		object.put("USER_CODE", Security.getUser().getCode());		
		int a = service.updateBuyContractAndPayment(object) ;
		
		boolean flag=false;
		String msg = "更新失败" ;
		if(a>0)
		{
			flag=true;
			msg = "更新成功" ;
		}
		return new ReplyAjax(flag, msg);
	}
	
//	@aPermission(name = { "合同中心", "买卖合同", "更新", "校验放款条件" })
	@aDev(code = "zl", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.LOGIN)
	public Reply chk1() {
		Map<String, Object> param = _getParameters();
		Map res = new HashMap();
		for(String id : param.get("IDS").toString().split(",")) {
			param.put("IDS", id);
			res = service.validFKTJ(param);
			if(!(Boolean)res.get("flag")) break;
		}
		return new ReplyAjax(res);
	}
}

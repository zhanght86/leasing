//package com.pqsoft.base.channel.action;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.base.channel.service.CreditAmountManagerService;
//import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.BaseUtil;
//
//public class CreditAmountManagerAction extends Action {
//    private String path = "base/channel/" ;
//    private CreditAmountManagerService service = new CreditAmountManagerService();
//	
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理", "代理商授信管理", "列表显示"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	@Override
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		List<Object> statusMess = (List<Object>)new DataDictionaryMemcached().get("供应商授信状态");
//		context.put("creditStatus", statusMess);
//		List<Object> busPlate = (List<Object>)new DataDictionaryMemcached().get("PDF模版所属商务板块");
////		Map<String,Object> SUP_MAP = BaseUtil.getSup();
//		context.put("busPlate", busPlate);
////		context.put("SUP_MAP", SUP_MAP);
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path+"creditAmountManager.vm", context));
//	}
//	
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理", "代理商授信管理", "列表显示"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getCreditAmountPageData(){
//		Map<String,Object> param = _getParameters();
//		Page pagedata = service.getCreditAmountPageData(param);
//		return new ReplyAjaxPage(pagedata);
//	}
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理","代理商授信管理","变更额度"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply upCreditAmount(){
//    	Map<String,Object> param = _getParameters();
////    	String INCREASE_AMOUNT = param.containsKey("INCREASE_AMOUNT") && param.get("INCREASE_AMOUNT")!=null?param.get("INCREASE_AMOUNT").toString():"";
////    	String UPSINGLE_AMOUNT = param.containsKey("UPSINGLE_AMOUNT") && param.get("UPSINGLE_AMOUNT") !=null ? param.get("UPSINGLE_AMOUNT").toString():"";
//    	int result = service.updateCreditAmount(param);
//    	if(result > 0 ){
//    		return new ReplyAjax(true, "操作成功！");
//    	}else{
//    		return new ReplyAjax(false, "操作失败！");
//    	}
//    }
//
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理","代理商授信管理","额度调整申请单列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply creditApplyDanManager(){
//        VelocityContext context = new VelocityContext();
//        Map<String,Object> param = _getParameters();
//        context.put("param", param);
//        return new ReplyHtml(VM.html(path+"", context));
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理","代理商授信管理","额度调整申请单列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply getApplyDanPageData(){
//    	Map<String,Object> param = _getParameters();
//    	Page pageData = service.getApplyDanPageData(param);
//    	return new ReplyAjaxPage(pageData);
//    }
//}

package com.pqsoft.PaymentTerm.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.PaymentTerm.service.PaymentTermService;
import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;

public class PaymentTermAction  extends Action {
	

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(DataDictionaryAction.class);
	PaymentTermService service = new PaymentTermService();

	@aPermission(name = { "放款管理", "放款条件管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("PaymentTerm/PaymentTermMg.vm", context));
	}
	
	@aPermission(name = {  "放款管理", "放款条件管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgPaymentTerm() {
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		return new ReplyAjaxPage(service.getPage(param));
		
	}
	
	@aPermission(name = {  "放款管理", "放款条件管理", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doAddPaymentTerm(){
		Map<String, Object> param = _getParameters();
		boolean flag = service.addPaymentTerm(param);
		if(flag){
			return new ReplyAjax(param).addOp(new OpLog("添加付款条件", "添加保存", param.toString()));
		}else{
			return new ReplyAjax(flag,"保存失败");
		}
	}
	
	@aPermission(name = { "放款管理", "放款条件管理", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doDeletePaymentTerm(){
		Map<String,Object> param = _getParameters();
		boolean flag = service.deletePaymentTerm(param);
		if(flag){
			return new ReplyAjax(param).addOp(new OpLog("删除付款条件", "删除", param.toString()));
		}else{
			return new ReplyAjax(false,"删除失败");
		}
	}
	
	@aPermission(name = { "放款管理", "放款条件管理", "修改" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toUpdatePaymentTerm(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> detailsMap = service.selectPaymentTermDetails(param);
		return new ReplyAjax(detailsMap);
	}
	
	@aPermission(name = { "放款管理", "放款条件管理", "修改" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doUpdatePaymentTerm(){
		Map<String,Object> param = _getParameters();
		boolean flag = service.updatePaymentTerm(param);
		if(flag){
			return new ReplyAjax(param).addOp(new OpLog("修改付款条件", "修改保存", param.toString()));
		}else{
			return new ReplyAjax(false,"修改失败");
		}
	}
	
	@aPermission(name = { "放款管理", "放款条件管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toShowPaymentTermDetails(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> detailsMap = service.selectPaymentTermDetails(param);
		return new ReplyAjax(detailsMap);
	}
	
	@aPermission(name = { "放款管理", "放款条件管理", "判断SQL" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply checkSql(){
		Map<String,Object> param = _getParameters();
		if(param.containsKey("sql") && !"".equals(param.get("sql"))){
			String sql = param.get("sql").toString();
			List<String> fieldList = service.parseSql(sql);
			if(fieldList != null){
				if(fieldList.size() >= 1 && fieldList.size() <= 1){
					for (int i = 0; i < fieldList.size(); i++) {
						if("*".equals(fieldList.get(i))){
							return new ReplyAjax(false,"sql不可用");
						}
					}
					param.put("field", fieldList.get(0));
					return new ReplyAjax(param);
				}else{
					return new ReplyAjax(false,"sql只能查询1个字段");
				}
			}else{
				return new ReplyAjax(false,"sql不可用");
			}
		}else{
			return new ReplyAjax(false,"sql不可用");
		}
	}
	
	@aPermission(name = { "放款管理", "放款条件管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply checkTermName(){
		Map<String,Object> param = _getParameters();
		if(param.containsKey("TermName") && !"".equals(param.get("TermName"))){
			return new ReplyAjax(service.checkTermName(param),"");
		}else{
			return new ReplyAjax(true,"");
		}
	}
	
}

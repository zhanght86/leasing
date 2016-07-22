//package com.pqsoft.call.action;
//
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.call.service.PersonnelConfigurationService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.OpLog;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.JsonUtil;
//
//public class PersonnelConfigurationAction extends Action {
//	
//	PersonnelConfigurationService service = new PersonnelConfigurationService();
//	
//	/**
//	 * 
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-10-8  上午10:20:07
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "人员配置", "查看" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	@Override
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("call/personnelConfigurationMg.vm", context));
//	}
//	
//	/**
//	 * 管理页列表数据
//	 * @param 列表页查询条件
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-10-8  上午11:01:39
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "人员配置", "查看" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	public Reply toMgPersonnelConfiguration() {
//		Map<String, Object> param = _getParameters();
//		if(param.containsKey("param")){
//			JSONObject json = JSONObject.fromObject(param.get("param"));
//			param.remove("param");
//			param.putAll(JsonUtil.toMap(json));
//		}
//		return new ReplyAjaxPage(service.getPage(param));
//		
//	}
//	
//	/**
//	 * 添加人员配置
//	 * @param
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-10-8  下午02:15:31
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "人员配置", "添加" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	public Reply doAddPersonnelConfiguration(){
//		Map<String, Object> param = _getParameters();
//		boolean flag = service.addPersonnelConfiguration(param);
//		if(flag){
//			return new ReplyAjax(param).addOp(new OpLog("添加人员配置", "添加保存", param.toString()));
//		}else{
//			return new ReplyAjax(flag,"保存失败");
//		}
//	}
//	
//	/**
//	 * 删除人员配置
//	 * @param
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-10-8  下午02:15:31
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "人员配置", "删除" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	public Reply doDeletePersonnelConfiguration(){
//		Map<String, Object> param = _getParameters();
//		boolean flag = service.deletePersonnelConfiguration(param);
//		if(flag){
//			return new ReplyAjax(param).addOp(new OpLog("删除人员配置", "删除", param.toString()));
//		}else{
//			return new ReplyAjax(flag,"删除失败");
//		}
//	}
//
//	/**
//	 * 修改人员配置页面
//	 * @param
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-10-8  下午03:20:59
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "人员配置", "修改" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	public Reply toUpdatePersonnelConfiguration(){
//		Map<String,Object> param = _getParameters();
//		Map<String,Object> detailsMap = service.selectPersonnelConfigurationDetails(param);
//		return new ReplyAjax(detailsMap);
//	}
//
//	/**
//	 * 执行修改人员配置
//	 * @param
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-10-8  下午03:23:34
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "人员配置", "修改" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	public Reply doUpdatePersonnelConfiguration(){
//		Map<String,Object> param = _getParameters();
//		boolean flag = service.updatePersonnelConfiguration(param);
//		if(flag){
//			return new ReplyAjax(param).addOp(new OpLog("修改人员配置", "修改保存", param.toString()));
//		}else{
//			return new ReplyAjax(false,"修改失败");
//		}
//	}
//	
//	/**
//	 * 查看人员配置页面
//	 * @paramID PDF模版表单域ID
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-9-9  下午03:38:34
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "人员配置", "查看" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	public Reply toShowPersonnelConfigurationDetails(){
//		Map<String,Object> param = _getParameters();
//		Map<String,Object> detailsMap = service.selectPersonnelConfigurationDetails(param);
//		return new ReplyAjax(detailsMap);
//	}
//
//}

package com.pqsoft.pdfTemplate.action;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.pdfTemplate.service.PdfTemplateFormLabelService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;

public class PdfTemplateFormLabelAction  extends Action {
	

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(DataDictionaryAction.class);
	PdfTemplateFormLabelService service = new PdfTemplateFormLabelService();

	/**
	 * 
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午12:26:20
	 */
	@aPermission(name = { "参数配置", "模版参数", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("pdfTemplate/pdfTemplateFormLabelMg.vm", context));
	}
	
	/**
	 * 管理页列表数据
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午01:33:31
	 */
	@aPermission(name = {"参数配置", "模版参数", "列表"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toMgPdfTemplateFormLabel() {
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		return new ReplyAjaxPage(service.getPage(param));
		
	}
	
	/**
	 * 添加PDF模版表单域
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午02:31:45
	 */
	@aPermission(name = { "参数配置", "模版参数", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddPdfTemplateFormLabel(){
		Map<String, Object> param = _getParameters();
		boolean flag = service.addPdfTemplateFormLabel(param);
		if(flag){
			return new ReplyAjax(param).addOp(new OpLog("添加PDF模版表单域", "添加保存", param.toString()));
		}else{
			return new ReplyAjax(flag,"保存失败");
		}
	}
	
	/**
	 * 删除PDF模版表单域
	 * @param ID PDF模版表单域ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午02:52:27
	 */
	@aPermission(name = { "参数配置", "模版参数", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doDeletePdfTemplateFormLabel(){
		Map<String,Object> param = _getParameters();
		boolean flag = service.deletePdfTemplateFormLabel(param);
		if(flag){
			return new ReplyAjax(param).addOp(new OpLog("删除pdf模版表单域", "删除", param.toString()));
		}else{
			return new ReplyAjax(false,"删除失败");
		}
	}
	
	/**
	 * 修改PDF模版表单域页面
	 * @paramID PDF模版表单域ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午03:09:54
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toUpdatePdfTemplateFormLabel(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> detailsMap = service.selectPdfTemplateFormLabelDetails(param);
		return new ReplyAjax(detailsMap);
	}
	
	/**
	 * 执行修改PDF模版表单域
	 * @paramID PDF模版表单域ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午03:09:54
	 */
	@aPermission(name = { "参数配置", "模版参数", "修改" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdatePdfTemplateFormLabel(){
		Map<String,Object> param = _getParameters();
		boolean flag = service.updatePdfTemplateFormLabel(param);
		if(flag){
			return new ReplyAjax(param).addOp(new OpLog("修改pdf模版表单域", "修改保存", param.toString()));
		}else{
			return new ReplyAjax(false,"修改失败");
		}
	}
	
	/**
	 * 查看PDF模版表单域页面
	 * @paramID PDF模版表单域ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午03:38:34
	 */
	@aPermission(name = { "参数配置", "模版参数", "查看" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toShowPdfTemplateFormLabelDetails(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> detailsMap = service.selectPdfTemplateFormLabelDetails(param);
		return new ReplyAjax(detailsMap);
	}
	
	/**
	 * 判断输入的sql是否可用
	 * @param sql
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午07:55:18
	 */
	@aPermission(name = { "法务管理", "合同管理", "模版参数", "判断SQL" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
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

}

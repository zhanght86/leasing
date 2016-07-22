package com.pqsoft.zcfl.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDataDictionaryService;
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
import com.pqsoft.zcfl.service.TitleStandardService;

/**
 * 题目和题目标准的模版配置
 * @author Administrator
 *下午06:33:38
 */
public class TitleStandardAction extends Action {
	
	private String path = "zcfl/titleStandard/";
	private TitleStandardService service= new TitleStandardService();
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"资产分类管理","题目模板管理","列表显示"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("businessTypeList", SysDataDictionaryService.queryDataDictionary("业务类型"));
		return new ReplyHtml(VM.html(path+"titleStandardMg.vm", context));
	}
	
	@aPermission(name = {"资产分类管理","题目模板管理","列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getMgTitleStandardData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		param.put("TSDD_TYPE", "业务类型");
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	@aPermission(name = { "资产分类管理","题目模板管理","添加模版"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddTS(){
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doAddTS(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("添加题目标准模版","添加",param.toString()));
	}
	

	@aPermission(name = {"资产分类管理","题目模板管理","修改"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getTSData() {
		Map<String, Object> param = _getParameters();
		param.put("TSDD_TYPE", "业务类型");
		return new ReplyAjax(service.getTSData(param));
	}
	
	@aPermission(name = { "资产分类管理","题目模板管理","修改"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdateTS() {
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doUpdateTS(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("题目标准模版管理","修改",param.toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={ "资产分类管理","题目模板管理","设置题目"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toSetTAddAndUpdate() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("allTList", service.getAllT(param));//查询题目及选项
		param.put("TSDD_TYPE", "业务类型");
		param.putAll(service.getTSDataFroShow(param));//模版信息
		context.put("param", param);
		context.put("dsList", service.getAllDs());//数据源
		return new ReplyHtml(VM.html(path+"setTAddAndUpdate.vm", context));
	}
	
	@aPermission(name = {"资产分类管理","题目模板管理","设置题目"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doSaveT() {
		Map<String, Object> param = _getParameters();
		JSONObject  tMap = JSONObject.fromObject(param.get("alldata"));
		boolean flag = service.doSaveT(tMap);
		return new ReplyAjax(flag,tMap).addOp(new OpLog("题目标准模版管理","设置题目",param.toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={ "资产分类管理","题目模板管理","设置标准"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toSetCAddAndUpdate() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("allCList", service.getAllC(param));//查询标准及初始级别范围
		param.put("TSDD_TYPE", "业务类型");
		param.putAll(service.getTSDataFroShow(param));//模版信息
		context.put("param", param);
		context.put("zcTypeList", DataDictionaryMemcached.getList("资产分类类型"));
		return new ReplyHtml(VM.html(path+"setCAddAndUpdate.vm", context));
	}
	
	@aPermission(name = {"资产分类管理","题目模板管理","设置标准"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doSaveC() {
		Map<String, Object> param = _getParameters();
		JSONObject  tMap = JSONObject.fromObject(param.get("alldata"));
		boolean flag = service.doSaveC(tMap);
		return new ReplyAjax(flag,tMap).addOp(new OpLog("题目标准模版管理","设置标准",param.toString()));
	}
	
	@aPermission(name = { "资产分类管理","题目模板管理","删除"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doDeleteTC() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.doDeleteTC(param)).addOp(new OpLog("题目标准模版管理","删除",param.toString()));
	}

}

package com.pqsoft.zcfl.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
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
import com.pqsoft.zcfl.service.DataSourceService;

/**
 * 配置数据源，刷数据的sql和系统打分时题目值的sql
 * @author 付玉龙  fuyulong47@foxmail.com
 * @date 2014-8-7  上午10:09:05
 */
public class DataSourceAction extends Action{
	private String path = "zcfl/dataSource/";
	private DataSourceService service= new DataSourceService();

	@Override
	@aPermission (name = {"资产分类管理","数据源管理","列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("typeList", DataDictionaryMemcached.getList("资产分类数据源SQL类型"));
		return new ReplyHtml(VM.html(path+"dataSourceMg.vm", context));
	}
	
	@aPermission(name = {"资产分类管理","数据源管理","列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getMgDataSourceData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		param.put("TSDD_TYPE", "资产分类数据源SQL类型");
		return new ReplyAjaxPage(service.getPage(param));
	}

	@aPermission(name = {"资产分类管理","数据源管理","添加"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddDS() {
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doAddDS(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("数据源管理","添加",param.toString()));
	}
	

	@aPermission(name = {"资产分类管理","数据源管理","删除"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doDeleteDS() {
		Map<String, Object> param = _getParameters();
		boolean flag =  service.doDeleteDS(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("数据源管理","删除",param.toString()));
	}
	
	@aPermission(name = { "资产分类管理","数据源管理","修改"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdateDS() {
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doUpdateDS(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("数据源管理","修改",param.toString()));
	}
	
	@aPermission(name = {"资产分类管理","数据源管理","查看"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getDSData() {
		Map<String, Object> param = _getParameters();
		param.put("TSDD_TYPE", "资产分类数据源SQL类型");
		return new ReplyAjax(service.getDSData(param));
	}
	
}

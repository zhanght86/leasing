package com.pqsoft.base.interfaceTemplate.action;

import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.velocity.VelocityContext;
import com.pqsoft.base.interfaceTemplate.service.InterfaceTemplateSqlService;
import com.pqsoft.base.interfaceTemplate.service.SqlConfigMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
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

public class InterfaceTemplateSqlAction extends Action {

	private String path = "interfaceTemplate/";
	private InterfaceTemplateSqlService service= new InterfaceTemplateSqlService();

	@Override
	@aPermission (name = {"参数配置","SQL配置"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("sqlList", new SysDictionaryMemcached().get("SQL类型"));
		return new ReplyHtml(VM.html(path+"itSqlMg.vm", context));
	}

	@aPermission (name = {"参数配置","SQL配置"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getItSqlData(){
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","SQL配置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public  Reply doAddSql(){
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doAddSql(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("接口SQL管理","添加",param.toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","SQL配置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public  Reply doUpdateSql(){
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doUpdateSql(param);
		if(flag){
			SqlConfigMemcached.cleanSqlMemcach(param.get("NAME").toString());
		}
		return new ReplyAjax(flag,param).addOp(new OpLog("接口SQL管理","修改",param.toString()));
	}
	
	@aPermission(name = {"参数配置","SQL配置"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply deleteSql() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.deleteSql(param);
		if(flag){
			SqlConfigMemcached.cleanSqlMemcach(param.get("NAME").toString());
		}
		return new ReplyAjax(flag);
	}
	
	@aPermission(name = {"参数配置","SQL配置"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply checkNameAdd() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.checkName(param),null);
	}
	
	@aPermission(name = {"参数配置","SQL配置"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply checkNameUpdate() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.checkName(param),null);
	}

}

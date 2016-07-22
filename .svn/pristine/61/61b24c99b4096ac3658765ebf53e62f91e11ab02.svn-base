package com.pqsoft.project.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.project.service.FollowProjectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;

public class FollowProjectAction extends Action{

	private String path = "project/";
	private FollowProjectService service = new FollowProjectService();
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
//	@aPermission(name = { "项目管理", "项目一览", "添加项目跟进/花费记录" })
	public Reply toAddProjectFollowUp() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		//查询项目基本信息
		service.queryProjectBaseInfo(param);
		List cpList = service.getFollowProject(param);
		context.put("custProject", cpList);
		String TYPE = "跟进状态";
		List<Object> codeType = (List)new DataDictionaryMemcached().get(TYPE);
		context.put("codeType", codeType);
		List SPEND = service.getSpendProject(param);
		context.put("SPEND", SPEND);
		String TYPE1 = "花费项目";
		List<Object> codeType1 = (List)new DataDictionaryMemcached().get(TYPE1);
		context.put("codeType1", codeType1);
		context.put("map", param);
		return new ReplyHtml(VM.html(path+"addProject.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
//	@aPermission(name = { "项目管理", "项目一览", "查看项目跟进/花费记录" })
	public Reply toProjectFollowView() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		//查询项目基本信息
		service.queryProjectBaseInfo(param);
		List cpList = service.getFollowProject(param);
		context.put("custProject", cpList);
		String TYPE = "跟进状态";
		List<Object> codeType = (List)new DataDictionaryMemcached().get(TYPE);
		context.put("codeType", codeType);
		List SPEND = service.getSpendProject(param);
		context.put("SPEND", SPEND);
		String TYPE1 = "花费项目";
		List<Object> codeType1 = (List)new DataDictionaryMemcached().get(TYPE1);
		context.put("codeType1", codeType1);
		context.put("map", param);
		return new ReplyHtml(VM.html(path+"viewProject.vm", context));
	}


	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
//	@aPermission(name = { "项目管理", "项目一览", "保存项目跟进" })
	public Reply addfollow() {
		Map<String,Object> param = _getParameters();
		System.out.println(param+"===============");
		Map  m=service.addfollow(param);
		if(m.get("i").toString().equals("1"))
		{
			return new ReplyAjax(true,m.get("ID").toString(),"");
		}else
		{
			return new ReplyAjax(false,"");
		}
		
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
//	@aPermission(name = { "项目管理", "项目一览", "删除项目跟进" })
	public Reply delfollow() {
		Map<String,Object> param = _getParameters();
		int i=service.delfollow(param);
		if(i==1)
		{
			return new ReplyAjax(true,"");
		}else
		{
			return new ReplyAjax(false,"");
		}
		
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
//	@aPermission(name = { "项目管理", "项目一览", "删除花费记录" })
	public Reply delspend() {
		Map<String,Object> param = _getParameters();
		int i=service.delspend(param);
		if(i==1)
		{
			return new ReplyAjax(true,"");
		}else
		{
			return new ReplyAjax(false,"");
		}
		
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
//	@aPermission(name = { "项目管理", "项目一览", "保存花费记录" })
	public Reply addspend() {
		Map<String,Object> param = _getParameters();
		System.out.println(param+"===============");
		param.put("CREATE_ID",Security.getUser().getId());
		Map  m=service.addspend(param);
		if(m.get("i").toString().equals("1"))
		{
			return new ReplyAjax(true,m.get("ID").toString(),"");
		}else
		{
			return new ReplyAjax(false,"");
		}
		
	}
}

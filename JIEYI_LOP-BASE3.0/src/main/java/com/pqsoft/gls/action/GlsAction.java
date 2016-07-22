package com.pqsoft.gls.action;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.gls.service.GlsService;
import com.pqsoft.leeds.utils.FileUploadService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GlsAction extends Action {

	private String path = "gls/";
	private GlsService service= new GlsService();
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"业务管理","预立项管理","列表显示"})
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply execute() {
		VelocityContext context = new VelocityContext();  
		Map<String, Object> param = _getParameters();
		context.put("oUser", Security.getUser().getOrg()==null?"god":Security.getUser().getOrg().getName());
		context.put("USERID", Security.getUser().getId()==null?"god":Security.getUser().getId());
		String org_id=Security.getUser().getOrg()==null?"1":Security.getUser().getOrg().getId();
		context.put("ORG_ID", org_id);
		context.put("user1", Security.getUser().getName());
		context.put("code1", CodeService.getCode("预立项编号",null,null));
		List<Map<String,Object>> list2 =(List)SysDictionaryMemcached.getList("客户类型");
		param.put("ORG_ID", org_id);
		List<Map<String,Object>> list =(List)SysDictionaryMemcached.getList("GLS");
		
		List<Map<String,Object>> list1 =(List)service.getSysEmp(param);
		//List<Map<String,Object>> list1 =(List)service.getSysEmp("name");
		context.put("list", list);
		context.put("list1", list1);
		context.put("list2", list2);
		
		return new ReplyHtml(VM.html(path+"glsMg.vm", context));
	}
	
	@aPermission(name = {"业务管理","预立项管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply getMgGlsData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		BaseUtil.getDataAllAuth(param);
		
		/**获取用户权限id及下属id--start**/
		param.put("ORG_CHILDREN", Security.getUser().getOrg().getId());
		User user=Security.getUser();
		System.out.println("userOrgid=("+user.getOrg().getId()+")");
		JSONObject jsonOrg = JSONObject.fromObject( Dao.selectOne("getOrgs",param));
		/**增加权限范围限制**/
		param.put("ORG_CHILDREN", jsonOrg.getString("ORG_CHILDREN"));
		/**获取用户权限id及下属id--end**/
		/*if(null==param.get("sortName")||"".equals(param.get("sortName").toString())){
			param.put("sortName", "ID");
			param.put("sortOrder", "ASC");
		}*/
		return new ReplyAjaxPage(service.getPage(param));
	}
	//添加
	@aPermission(name = {"业务管理","预立项管理","添加"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply doAddGls() {
	
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		JSONArray list=JSONArray.fromObject(param.get("list"));
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		/*文件list*/
		param.put("num", list.size());
		param.put("list", list);
		param.put("CLERK_ID", Security.getUser().getId());
		param.put("ORD_ID", Security.getUser().getOrg().getId());
		boolean flag =  service.doAddGls(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("预立项管理","添加",param.toString()));
	}
	@aPermission(name = {"业务管理","预立项管理","删除"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply doDeleteGls() {
		Map<String, Object> param = _getParameters();
		boolean flag =  service.doDeleteGls(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("预立项管理","删除",param.toString()));
	}
	//添加跟进
	@aPermission(name = {"业务管理","预立项管理","添加"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply doAddGls1() {
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doAddGls1(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("预立项管理","添加",param.toString()));
	}
	
	//修改
	@aPermission(name = { "业务管理","预立项管理","修改"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply doUpdateGls() {
	
		Map<String, Object> param = _getParameters();
		
		/*文件上传begin*/
		List<FileItem> fileList = _getFileItem();
		param.put("num", fileList.size());
		List<GlsFile> fileList2=new ArrayList<GlsFile>();
		if(fileList.size()>0){
			for (int i = 0; i < fileList.size(); i++) {
				FileItem fileItem=fileList.get(i);
				if(null==fileItem.getName()||"".equals(fileItem.getName())){
					fileList.remove(i);
					continue;
				};
				List<FileItem> newFileList=new ArrayList<FileItem>();
				newFileList.add(fileList.get(i));
				Map<String, Object> fileMap = FileUploadService.uploadFileForOne(newFileList,"yingxiaoxiangmu");
				GlsFile glsFile=new GlsFile();
				glsFile.setFILE_NAME(fileMap.get("FILE_NAME").toString());
				glsFile.setFILE_PATH(fileMap.get("FILE_PATH").toString());
				glsFile.setFILE_TYPE(fileMap.get("FILE_TYPE").toString());
				glsFile.setFILE_SIZE(Long.parseLong(fileMap.get("FILE_SIZE").toString()));
				fileList2.add(glsFile);
			}
		}
		param.put("list", fileList2);
		/*文件上传end*/
		
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doUpdateGls(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("预立项管理","修改",param.toString()));
	}
	//查询
	@aPermission(name = {"业务管理","预立项管理","列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply getGlsData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getGlsData(param));
	}
	//查询跟进
	@aPermission(name = {"业务管理","预立项管理","列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply getGlsData1() {
		
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		
		return new ReplyAjaxPage(service.getGlsData1(param));
	}
	@aPermission(name = {"业务管理","预立项管理","列表显示"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply getGlsData11() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getGlsData11(param));
	}

	
	@aPermission(name = {"业务管理","预立项管理","上传"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply uploadFile(){
		Map<String,Object> param = _getParameters();
		List<FileItem> fileList = _getFileItem();
		Map<String, Object> fileMap = FileUploadService.uploadFileForOne(fileList,"genjinjilu");
		
		fileMap.put("GLS_ID", param.get("GLS_ID"));
		boolean count = service.addCreditReports(fileMap);
		
		if(count){
			return new ReplyAjax(true, "上传成功！");
		}
		return new ReplyAjax(false,"上传失败！");
	}
	

	@aPermission(name = {"业务管理","预立项管理","下载"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply download(){
		Map<String, Object> param = _getParameters();
		/*Map<String,Object> fileMap = service.findFileById(param);
		String filePath = "";
		if(fileMap!=null&&!"".equals(fileMap.get("FILE_PATH"))){
			filePath = fileMap.get("FILE_PATH").toString();
		}
		
		String fileName = fileMap.get("FILE_NAME").toString();*/
		
		File file = new File(param.get("path").toString());
		
		return new ReplyFile(file, param.get("fileName").toString());
	}
	@aPermission(name = {"业务管理","预立项管理","立项"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply confirmPro(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.doInsertPro(param));
	}
	@aPermission(name = {"业务管理","预立项管理"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply toValidateCode() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.selectCountByCode(param)>0;
		return new ReplyAjax(flag,param).addOp(new OpLog("预立项管理","校验",param.toString()));
	}
}
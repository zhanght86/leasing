package com.pqsoft.softmg.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.velocity.VelocityContext;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.softmg.service.FileUploadService;

public class FileUploadAction extends Action{
	FileUploadService service=new FileUploadService();
	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"参数配置", "常用软件", "列表"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
//		String path = SkyEye.getConfig("file.path");
//		path+=File.separator+"filesoftMg";
//		File file = new File(path);
//		List<Map<String,Object>> resultLMap = new ArrayList<Map<String,Object>>();
//		File[] listFiles = file.listFiles();
//		SimpleDateFormat sdf = new SimpleDateFormat();
//		sdf.applyPattern("yyyy-MM-dd");
//		context.put("dateFormat", sdf);
//		context.put("fileList", listFiles);
		return new ReplyHtml(VM.html("softmg/FileUpload.vm", context));	
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"参数配置", "常用软件", "列表"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findAll()
	{
		String path = SkyEye.getConfig("file.path");
		path+=File.separator+"filesoftMg";
		File file = new File(path);
		List<Map<String,Object>> resultLMap = new ArrayList<Map<String,Object>>();
		File[] listFiles = file.listFiles();
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");
		for(int i=0;i<listFiles.length;i++)
		{
			Map<String,Object> map=new HashMap<String, Object>(); 
			map.put("NAME", listFiles[i].getName());
			map.put("WENSIZE", listFiles[i].length());
			map.put("XIUGAIDATE", sdf.format(listFiles[i].lastModified()));
			resultLMap.add(map);
		}
		Map<String,Object> param=_getParameters();
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(resultLMap);
		page.addDate(array, listFiles.length);
		return new ReplyAjaxPage(page);
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"参数配置", "常用软件", "下载"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply xml() {
		Map<String,Object> param=_getParameters();
		//String path= String.valueOf(param.get("path"));
		String id= String.valueOf(param.get("id"));
		String path = SkyEye.getConfig("file.path");
		path+=File.separator+"filesoftMg";
		File file  =   new  File(path+File.separator+id);
		return new ReplyFile(file, id);
		
//		FundUploadService service = new FundUploadService();
//		Map<String, Object> m = service.getFile(SkyEye.getRequest().getParameter("id"));
//		String path = (String) m.get("PATH");
//		return new ReplyFile(new File(path), (String) m.get("NAME"));
		
//		String pdfPath=map.get("PDF_PATH").toString();
//		File file  =   new  File(pdfPath);\pqsoft\file\ce60df00-77c6-4013-a356-17767a2b36ffDJ-CAR.xlsx
//		String fileName = file.getName();
//		String  type = pdfPath.substring(pdfPath.lastIndexOf("."));
//		String Name=map.get("NAME")+"";
//		boolean flag=Name.contains(".");
//		if(flag){
//			fileName = null==map.get("NAME")?"项目附件":Name;
//		}else
//			fileName = null==map.get("NAME")?"项目附件"+type:Name+type;
//		return new ReplyFile(file,fileName);
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"参数配置", "常用软件", "上传"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply uploadPdfTemplate(){
		Map<String, Object> param = new HashMap<String, Object>();
		List<FileItem> fileList = _getFileItem();
		boolean flag = service.uploadPdfTemplate(param,fileList);
		if(flag){			
			//return new ReplyAjax(false,"保存成功！");
			return new ReplyAjax(param);
		}
		return new ReplyAjax(false,"保存失败！");
	}
}

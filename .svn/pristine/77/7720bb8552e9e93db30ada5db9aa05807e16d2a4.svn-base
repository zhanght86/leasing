package com.pqsoft.creditReports.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.velocity.VelocityContext;

import com.pqsoft.creditReports.service.CreditReportsService;
import com.pqsoft.leeds.utils.FileUploadService;
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

public class CreditReportsAction extends Action{

	private CreditReportsService service = new CreditReportsService();
	private String basePath = "creditReports/";
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "征信报告", "列表显示" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(basePath+"creditReports.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "征信报告", "列表显示" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.getPageData(param));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "征信报告", "列表显示" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply fileData(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.getFileData(param));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "征信报告", "上传" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply uploadFile(){
		Map<String,Object> param = _getParameters();
		List<FileItem> fileList = _getFileItem();
		Map<String, Object> fileMap = FileUploadService.uploadFileForOne(fileList,"creditReports");
		
		fileMap.put("PROJECT_ID", param.get("PROJECT_ID"));
		int count = service.addCreditReports(fileMap);
		
		if(count>0){
			return new ReplyAjax(true, "上传成功！");
		}
		return new ReplyAjax(false,"上传失败！");
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "业务管理", "征信报告", "下载" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply download(){
		Map<String, Object> param = _getParameters();
		Map<String,Object> fileMap = service.findFileById(param);
		String filePath = "";
		if(fileMap!=null&&!"".equals(fileMap.get("FILE_PATH"))){
			filePath = fileMap.get("FILE_PATH").toString();
		}
		
		String fileName = fileMap.get("FILE_NAME").toString();
		
		File file = new File(filePath);
		
		return new ReplyFile(file, fileName);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "征信报告", "删除" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply deleteFile(){
		Map<String,Object> param = _getParameters();
		
		Map<String,Object> file = service.findFileById(param);
		String filePath = "";
		if(file!=null&&!"".equals(file.get("FILE_PATH"))){
			filePath = file.get("FILE_PATH").toString();
		}
		FileUploadService.deleteFile(filePath);
		
		int count = service.deleteFile(param);
		boolean flag = false;
		if(count>0){
			flag = true;
		}
		return new ReplyAjax(flag);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(basePath+"toView.vm",context ));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toData(){
		Map<String,Object> param = _getParameters();
		
		return new ReplyAjaxPage(service.getFileData(param));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply exportExcel(){
		Map<String,Object> param = _getParameters();
		HSSFWorkbook wb = service.getDataList(param);
		OutputStream ouputStream = null;
		try {
			String fileName = "征信报告"+new SimpleDateFormat("yyyyMMddHHmmss")
								.format(Calendar.getInstance().getTime());
			fileName = new String(fileName.getBytes(),"iso-8859-1");
			HttpServletResponse response = SkyEye.getResponse();
			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");
	        
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(ouputStream!=null){
					ouputStream.flush();
					ouputStream.close();  
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}

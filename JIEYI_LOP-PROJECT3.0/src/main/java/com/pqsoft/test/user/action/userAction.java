package com.pqsoft.test.user.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import jxl.common.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.velocity.VelocityContext;



import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.test.user.service.userService;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ReplyExcel;

public class userAction extends Action{
	Map<String, Object> m = null;
	private String path = "test/user/";
	private final Logger log = Logger.getLogger(this.getClass());
	
	public Reply execute() {
		return null; 
	}
	private userService service = new userService();
	
	
	/**
	 * getPageParameter 获取页面参数
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.ALL)
	private void getPageParameter() {
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME", Security.getUser().getName());
		m.put("USERCODE", Security.getUser().getCode());
		m.put("USERID", Security.getUser().getId());
	}

	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = {"用户测试模块", "用户管理", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply findUsersPage() {
		VelocityContext context=new VelocityContext();
		List<Map<String,Object>> selectList=service.findByUser(m);
		Map<String, Object> param = _getParameters();
		context.put("PContext", param);
	//	context.put("EscapeTool", new EscapeTool());
		context.put("list", selectList);
		return new ReplyHtml(VM.html(path + "Users.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"用户测试模块", "用户管理", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doShowUserConfgForPage(){
		Map<String, Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	





	//	1161092 1161873
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = {"用户测试模块", "用户管理", "添加"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply addUser(){
		this.getPageParameter();
		String username = SkyEye.getRequest().getParameter("username");
		String password = SkyEye.getRequest().getParameter("password");
		String job= SkyEye.getRequest().getParameter("job");
		String age = SkyEye.getRequest().getParameter("age");
		String record = SkyEye.getRequest().getParameter("record");
		System.out.println("sfsf:"+username);
		m = new HashMap<String, Object>();
		m.put("USERNAME", username);
		m.put("PASSWORD", password);
		m.put("JOB", job);
		m.put("AGE", age);
		m.put("RECORD", record);
		int result=service.addUser(m);
		if (result > 0) {
			return new ReplyAjax().addOp(new OpLog("用户测试模块-用户管理", "添加保存",m + ""));
		} else 
			return new ReplyAjax(false, "添加失败");
			
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = {"用户测试模块", "用户管理", "修改"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doUpdateUSERConfig(){
		log.info(_getParameters());
		int i = service.doUpdateUSERConfig(_getParameters());
		if (i > 0) {
			return new ReplyAjax().addOp(new OpLog("用户测试模块-用户管理", "修改", _getParameters()  + ""));
		} else 
			return new ReplyAjax(false, "修改失败");
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = {"用户测试模块", "用户管理", "删除"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doDelUserConfig(){
		log.info(_getParameters());
		int i = service.doDelUserConfig(_getParameters());
		if (i > 0) {
			return new ReplyAjax().addOp(new OpLog("用户测试模块-用户管理", "删除", _getParameters()  + ""));
		} else 
			return new ReplyAjax(false, "删除失败");
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = {"用户测试模块", "用户管理", "导出"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply exportExcel(){
		Map<String,Object> param = _getParameters();
		HSSFWorkbook wb = service.getDataList(param);
		OutputStream ouputStream = null;
		try {
			String fileName = "用户测试模块"+new SimpleDateFormat("yyyyMMddHHmmss")
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
	
	/**
	 * 
	 * @Title: doExportApplyExcel 
	 * @Description: (申请页面导出) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = {"用户测试模块", "用户管理", "导出"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doExportApplyExcelNew(){
		m=_getParameters();
		String exportAll="false";
		if(m !=null && m.get("exportAll") !=null && !m.get("exportAll").equals("")){
			exportAll=m.get("exportAll")+"";
		}
		
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		if(exportAll.equals("false")){
			if(m.get("sqlData") !=null && !m.get("sqlData").equals("")){
				String sqlData=m.get("sqlData")+"";
				String[] sqlDataL=sqlData.split(",");
				dataList=service.sendQyInfoDc(sqlDataL,sqlData);
			}
		}
		
		
		return new ReplyExcel(service.getExportApplyExcelDc(dataList),"cardCheck"+DateUtil.getSysDate()+Math.abs(new Random().nextInt(10000))+".xls").addOp(new OpLog("用户测试模块", "签约标识", "申请页面导出excel"));
	}
	
	
}

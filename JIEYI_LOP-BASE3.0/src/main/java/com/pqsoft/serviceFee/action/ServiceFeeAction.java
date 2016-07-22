package com.pqsoft.serviceFee.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.serviceFee.service.ServiceFeeService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;

public class ServiceFeeAction extends Action {
	private ServiceFeeService service = new ServiceFeeService();
	private String path = "serviceFee/";
	
	@SuppressWarnings("unchecked")
	@aPermission(name = { "贷后管理", "服务费管理", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "1226781445@qq.com", name = "肖云飞")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Object> status = (List<Object>)new DataDictionaryMemcached().get("结算状态");
		context.put("status", status);
		return new ReplyHtml(VM.html(path+"serviceFee.vm", context));
	}
	@aPermission(name = { "贷后管理", "服务费管理", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "1226781445@qq.com", name = "肖云飞")
	public Reply pageData() {
		Map<String,Object> param = _getParameters();
		System.out.println(param+"---------pp----------");
		List<Object> list = service.getPage(param);
		return new ReplyJson(JSONArray.fromObject(list));
	}
	@aPermission(name = { "贷后管理", "服务费管理", "导出" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "1226781445@qq.com", name = "肖云飞")
	public Reply exportExcel() {
		Map<String,Object> param = _getParameters();
		System.out.println(param+"------------------");
		Workbook wb = service.exportExcel(param);
		OutputStream outputStream = null;
		try {
			String fileName = "服务费管理"+new SimpleDateFormat("yyyymmddhhmmss")
								.format(Calendar.getInstance().getTime());
			fileName = new String(fileName.getBytes(),"iso-8859-1");
			HttpServletResponse response = SkyEye.getResponse();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");
			outputStream = response.getOutputStream();
			wb.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(outputStream!=null){
					outputStream.flush();
					outputStream.close();  
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@aPermission(name = { "贷后管理", "服务费管理", "导入" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "1226781445@qq.com", name = "肖云飞")
	public Reply readExcel() {
		File file = _getFileOne();
		System.out.println(file+"----------");
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(Calendar.getInstance().getTime())+UUID.randomUUID()+".xls";
		Workbook wb;
		try {
			wb = WorkbookFactory.create(file);
			Sheet sheet = wb.getSheetAt(0);
			System.out.println(sheet.getLastRowNum()+"---dd---");
			for(int i=1;i<=sheet.getLastRowNum();i++){
				Row row = sheet.getRow(i);
				Cell idCell = row.getCell(0);
				//返回服务费 付费状态
				Cell payStatusCell = row.getCell(14);
				Map<String,Object> map = new HashMap<String, Object>();
				if(idCell!=null){
					double id = idCell.getNumericCellValue();
					map.put("ID",id);
				}
				if(payStatusCell!=null){
					String payStatus = payStatusCell.getStringCellValue();
					System.out.println(payStatus+"--------bb---------"+map.get("ID"));
					if("成功".equals(payStatus)){
						map.put("STATUS", 2);
						map.put("TYPE", 2);
					}else if("失败".equals(payStatus)){
						map.put("STATUS", 3);
						map.put("TYPE", 1);
					}
					System.out.println(map+"--------");
					service.updateServiceFeeStatus(map);
				}
			}
			String filePath = SkyEye.getConfig("file.path")+File.separator+"serviceFee";
			BaseUtil.createDirectory(filePath);
			File file2 = new File(filePath+File.separator+fileName);
			System.out.println(filePath+"----path----");
			FileCopyUtils.copy(file, file2);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ReplyAjax(true, "成功").addOp(new OpLog("贷后管理","返佣管理",
				"导入文件名："+fileName+",导入时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date())));
	}
}

package com.pqsoft.commission.action;

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
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.commission.service.CommissionService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;


public class CommissionAction extends Action {

	private CommissionService service = new CommissionService();
	private String path = "commission/";
	
	@SuppressWarnings("unchecked")
	@aPermission(name = { "贷后管理", "返佣管理", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Object> status = (List<Object>)new DataDictionaryMemcached().get("结算状态");
		context.put("status", status);
		return new ReplyHtml(VM.html(path+"commission.vm", context));
	}
	
	@aPermission(name = { "贷后管理", "返佣管理", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply pageData() {
		Map<String,Object> param = _getParameters();
		List<Object> list = service.getPage(param);
		return new ReplyJson(JSONArray.fromObject(list));
	}
	
	@aPermission(name = { "贷后管理", "返佣管理", "导出" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply exportExcel() {
		Map<String,Object> param = _getParameters();
		Workbook wb = service.getDataList(param);
		OutputStream outputStream = null;
		try {
			String fileName = "返佣管理"+new SimpleDateFormat("yyyymmddhhmmss")
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
	
	@aPermission(name = { "贷后管理", "返佣管理", "导入" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply readExcel(){ 
		File file = _getFileOne();
//		File file = new File("/返佣管理20151926061954.xls");
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(Calendar.getInstance().getTime())+UUID.randomUUID()+".xls";
		Workbook wb;
		try {
			wb = WorkbookFactory.create(file);
			Sheet sheet = wb.getSheetAt(0);
			System.out.println(sheet.getLastRowNum());
			for(int i=1;i<=sheet.getLastRowNum();i++){
				Row row = sheet.getRow(i);
				Cell idCell = row.getCell(0);
				//返佣金额
				Cell rebateCell = row.getCell(21);
				if(idCell!=null&&rebateCell!=null){
					double id = idCell.getNumericCellValue();
					double rebate = rebateCell.getNumericCellValue();
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("ID", id);
					param.put("REBATE_AMOUNT", rebate);
					service.updateRebate(param);
				}
			}
			String filePath = SkyEye.getConfig("file.path")+File.separator+"commission";
			BaseUtil.createDirectory(filePath);
			File file2 = new File(filePath+File.separator+fileName);
			FileCopyUtils.copy(file, file2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax(true, "成功").addOp(new OpLog("贷后管理","返佣管理",
				"导入文件名："+fileName+",导入时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date())));
	}
	
	@aPermission(name = { "贷后管理", "返佣管理", "返佣申请" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply apply(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		List<Map<String,Object>> applyList = service.getApplyList(param);
		
		List<List<Map<String,Object>>> list = new ArrayList<List<Map<String,Object>>>();
		
		for(int i=0;i<applyList.size();i++){
			Map<String,Object> imap = (Map<String, Object>) applyList.get(i);
			String BANK_ACCOUNT1 = (String) imap.get("BANK_ACCOUNT");
			List<Map<String,Object>> ilist = new ArrayList<Map<String,Object>>();
			ilist.add(imap);
			for(int j=i+1;j<applyList.size();j++){
				Map<String,Object> jmap = (Map<String, Object>) applyList.get(j);
				String BANK_ACCOUNT2 = (String) jmap.get("BANK_ACCOUNT");
				if(BANK_ACCOUNT1.equals(BANK_ACCOUNT2)){
					ilist.add(jmap);
					applyList.remove(j);
				}
			}
			double total = 0.0;
			for(Map<String,Object> m : ilist){
				total += Double.parseDouble(m.get("REBATE_AMOUNT").toString());
			}
			String upper = service.digitUppercase(total);
			
			String ids="";
			for(int x=0;x<ilist.size();x++){
				Map<String, Object> map = ilist.get(x);
				ids += map.get("ID");
				if(x!=ilist.size()-1){
					ids +="_";
				}
			}
			Map<String,Object> m = ilist.get(0);
			m.put("total", total);
			m.put("upper", upper);
			m.put("ids", ids);
			list.add(ilist);
		}
		
		context.put("list", list);
		context.put("applyList", applyList);
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"apply.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply startCommissionByJbpm(){
		
		List<String> list = JBPM.getDeploymentListByModelName("返佣审批",
				null, Security.getUser()
						.getOrg().getPlatformId());
		String pid = null;
		if (list.size() > 0) {
			pid = list.get(0);
		} else
			throw new ActionException("没有找到流程图");

		if (pid == null) {
			throw new ActionException("没有匹配到审批流程");
		}
		Map<String, Object> jmap = new HashMap<String, Object>();
		String apply_id = service.selectApplyId()+"";
		jmap.put("APPLY_ID", apply_id);
		String jbpmId = JBPM.startProcessInstanceById(pid,
				null, null,null, jmap).getId();
		String nextCode = new TaskService().getAssignee(jbpmId);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("JBPM_ID", jbpmId);
		map.put("apply_id", apply_id);
		map.put("CREATE_DATE", new Date());
		map.put("CREATER", Security.getUser().getName());
		//插入数据返佣申请表
		int count = service.addApply(map);
		if(count>0){
			Map<String,Object> param = _getParameters();
			String jsonStr = (String) param.get("jsonStr");
			List<Map<String,Object>> jsonList = JSONArray.fromObject("["+jsonStr+"]");
			
			for(int i=0;i<jsonList.size();i++){
				Map<String,Object> jsonMap = jsonList.get(i);
				jsonMap.put("APPLY_ID", apply_id);
				//返佣放款表
				int count1 = service.addLoan(jsonMap);
				if(count1>0){
					Map<String,Object> params = new HashMap<String, Object>();
					String loan_id = jsonMap.get("loan_id").toString();
					params.put("LOAN_ID", loan_id);
					//结算状态 1：结算中
					params.put("STATUS", "1");
					//更新返佣表LOAN_ID（返佣放款表ID）
					String ids = (String)jsonMap.get("IDS");
					String[] rebate_id = ids.split("_");
					for(int j=0;j<rebate_id.length;j++){
						String id = rebate_id[j];
						params.put("ID", id);
						service.updateRebate(params);
					}
				}
			}
		}
		return new ReplyAjax(true, nextCode, "流程发起成功").addOp(new OpLog(
				"贷后管理","返佣管理","返佣申请"));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toShowCommissionJbpm(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
//		param.put("APPLY_ID", "14");
		List<Map<String,Object>> loanList = service.selectLoanByApplyId(param);
		for(int i=0;i<loanList.size();i++){
			Map<String, Object> map = loanList.get(i);
			String loan_id = map.get("LOAN_ID")+"";
			List<Map<String, Object>> rebateList = service.selectRebateByLoanId(loan_id);
			map.put("rebateList", rebateList);
		}
		context.put("loanList", loanList);
		return new ReplyHtml(VM.html(path+"toShowCommission.vm", context));
	}
	
}

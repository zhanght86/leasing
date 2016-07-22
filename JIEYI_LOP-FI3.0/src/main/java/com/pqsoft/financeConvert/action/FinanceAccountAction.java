package com.pqsoft.financeConvert.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.financeConvert.service.FinanceAccountService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class FinanceAccountAction extends Action {

	private Map<String, Object> m = null;
	private FinanceAccountService service = new FinanceAccountService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@Override
	@SuppressWarnings("unchecked")
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "财务管理", "财务接口","Exlce导出数据" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	@SuppressWarnings("unchecked")
//	public Reply exportExcel(){
//		m=_getParameters();
//		m.put("USER_CODE", Security.getUser().getCode());		
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//        //m.put("V_NAME", "FIL_SUPER_TABLE");
//		
//		return new ReplyExcel(service.getExportApplyExcel(m),"财务对账数据"+m.get("FINANCE_ID")+"_"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls").addOp(new OpLog("财务客户管理", "财务接口", "导出excel"));
//	}
	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "财务管理", "财务接口","数据查询(必选)" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	@SuppressWarnings("unchecked")
//	public Reply doTableShow(){
//		m = _getParameters();		
//		m.put("USER_CODE", Security.getUser().getCode());		
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		String finance_name="VI_FINANCE_ACCOUNT_"+(String)m.get("FINANCE_ID");
//		System.out.println("数据加载程序---finance_id:"+finance_name+"##COLUMN_NAMES##"+m.get("COLUMN_NAMES"));
//		m.put("REPORT_NAME", finance_name);
//		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史		
//		if(m.get("COLUMN_NAMES") != null){
//			//System.out.println(m.get("COLUMN_NAMES"));
//			service.delReportColumnByReportAndUser(m);
//			service.insertReportColumnByReportAndUser(m);
//		}		
//		m.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
//		if(m.get("COLUMN_NAMES") == null || m.get("COLUMN_NAMES") == ""){
//			m.put("COLUMN_NAMES","*");
//		}else{
//			m.put("COLUMN_NAMES",m.get("COLUMN_NAMES").toString()+",ID");
//		}	
//		m.put("V_NAME", finance_name);
//		return new ReplyAjaxPage(service.getTableData(m));
//	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口", "T5超级表_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finance_account_t501_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "T501");
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeAccount/finance_account_t5.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口", "T5超级表_营业税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finance_account_t502_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "T502");
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeAccount/finance_account_t5.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口", "T101租金_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finance_account_t101_0101_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "T101_0101");
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html(
				"financeAccount/finance_account_t101_01.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口", "T101租金_营业税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finance_account_t101_0102_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "T101_0102");
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html(
				"financeAccount/finance_account_t101_01.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口", "T101违约金_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finance_account_t101_0201_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "T101_0201");
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html(
				"financeAccount/finance_account_t101_02.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口", "T101违约金_营业税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finance_account_t101_0202_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "T101_0202");
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html(
				"financeAccount/finance_account_t101_02.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口", "T102逾期租金明细_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finance_account_t102_01_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "T102_01");
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeAccount/finance_account_t102.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口", "T102逾期租金明细_营业税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finance_account_t102_02_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "T102_02");
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeAccount/finance_account_t102.vm",
				context));
	}
	/**
	 * 
	 * @Title: finanace_convert_out_list 
	 * @Description: (销项税对账) 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务对账接口", "销项税对账" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply finance_account_out_list() {
		m = _getParameters();
		VelocityContext context = new VelocityContext();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "OUT");
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeAccount/finance_account_out.vm",
				context));
	}
}

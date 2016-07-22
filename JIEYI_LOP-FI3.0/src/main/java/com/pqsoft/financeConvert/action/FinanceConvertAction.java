package com.pqsoft.financeConvert.action;

import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.financeConvert.service.FinanceConvertService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ReplyExcel;

/**
 * <p>
 * Title: 融资租赁信息系统平台 财务接口管理
 * </p>
 * <p>
 * Description: 财务接口 ;
 * </p>
 * <p>
 * Company: SFLC
 * </p>
 * 
 * @author yuq@strongflc.com
 * @version 1.0
 */
public class FinanceConvertAction extends Action {

	private FinanceConvertService service = new FinanceConvertService();
	private Map<String, Object> m = null;

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@Override
	@SuppressWarnings("unchecked")
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口","Exlce导出数据" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply exportExcel(){
		m=_getParameters();
		m.put("USER_CODE", Security.getUser().getCode());		
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		
//		service.getExportApplyExcel(m);
//		return null;
		return new ReplyExcel(service.getExportApplyExcel(m),"financeConver"+m.get("FINANCE_ID")+"_"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls").addOp(new OpLog("财务客户管理", "财务接口", "导出excel"));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口","数据查询(必选)" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply doTableShow(){
		m = _getParameters();		
		m.put("USER_CODE", Security.getUser().getCode());		
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		String finance_name="vi_finanace_convert_"+(String)m.get("FINANCE_ID");
//		System.out.println("数据加载程序---finance_id:"+finance_name+"##COLUMN_NAMES##"+m.get("COLUMN_NAMES"));
		m.put("REPORT_NAME", finance_name);
		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史		
		if(m.get("COLUMN_NAMES") != null){
			//System.out.println(m.get("COLUMN_NAMES"));
			service.delReportColumnByReportAndUser(m);
			service.insertReportColumnByReportAndUser(m);
		}		
		m.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
		if(m.get("COLUMN_NAMES") == null || m.get("COLUMN_NAMES") == ""){
			m.put("COLUMN_NAMES","*");
		}else{
			m.put("COLUMN_NAMES",m.get("COLUMN_NAMES").toString()+",ID");
		}	
		m.put("V_NAME", finance_name);
		return new ReplyAjaxPage(service.getTableData(m));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "01购入租赁物和项目起租" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_01_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "01");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);

		// 查询已配置的字段显示
		// context.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));

		return new ReplyHtml(VM.html("financeConvert/financeConvert.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "02收首付款" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_02_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "02");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert02.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "03付租赁物价款_差额放款" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_0302_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "0302");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert03.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "04利息收入_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_0401_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "0401");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert04.vm",
				context));
	}

//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "财务管理", "财务接口", "04利息收入_营业税" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	@SuppressWarnings("unchecked")
//	public Reply finanace_convert_0402_list() {
//		m = _getParameters();
//		m.put("USER_CODE", Security.getUser().getCode());
//		m.put("FINANCE_ID", "0402");
//		//System.out.println("yuq-----" + m);
//		VelocityContext context = new VelocityContext();
//		service.getBaseQueryDate(m, context);
//		return new ReplyHtml(VM.html("financeConvert/financeConvert04.vm",
//				context));
//	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "05收租金款和违约金" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_05_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "05");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert05.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "06转付设备保险费" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_06_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "06");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert06.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "07转付担保费" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_07_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "07");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert07.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "08逾期两期冲红利息_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_0801_list() {
		m = _getParameters();
			
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "0801");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		context.put("curry_date",DateUtil.getSysDate("yyyy-MM-dd"));
		return new ReplyHtml(VM.html("financeConvert/financeConvert08.vm",
				context));
	}

//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "财务管理", "财务接口", "08逾期两期冲红利息_营业税" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	@SuppressWarnings("unchecked")
//	public Reply finanace_convert_0802_list() {
//		m = _getParameters();
//	
//		m.put("USER_CODE", Security.getUser().getCode());
//		m.put("FINANCE_ID", "0802");
//		//System.out.println("yuq-----" + m);
//		VelocityContext context = new VelocityContext();
//		service.getBaseQueryDate(m, context);
//		context.put("curry_date",DateUtil.getSysDate("yyyy-MM-dd"));
//		return new ReplyHtml(VM.html("financeConvert/financeConvert08.vm",
//				context));
//	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "09调整未起租项目首付款" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_09_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "09");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert09.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "10调息_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1001_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1001");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert10.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "10调息_营业税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1002_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1002");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert10.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "11收回购款_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1101_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1101");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert11.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "11收回购款_营业税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1102_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1102");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert11.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "12收提前还租款_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1201_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1201");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert12.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "12收提前还租款_营业税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1202_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1202");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert12.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "13退客户保证金" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_13_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "13");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert13.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "14A转B项目_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1401_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1401");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert14.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "14A转B项目_营业税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1402_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1402");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert14.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "15留购价款转收入" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_15_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "15");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert15.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "17退DB保证金" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_17_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "17");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert17.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "18规则转不规则明细_增值税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1801_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1801");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert18.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "18规则转不规则明细_营业税" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_1802_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "1802");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert18.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "21月末未收发票已起租" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_21_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "21");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert21.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "22月末起租租金暂估_差额放款" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_22_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "22");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert22.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "23开具本金发票" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_23_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "23");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert23.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "24购入设备（分期模式）" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_24_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "24");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert24.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "25向经销商开票（分期模式）" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_25_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "25");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert25.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "26分期收款（分期模式）" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_26_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "26");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert26.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口", "27分期收首付款（分期模式）" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@SuppressWarnings("unchecked")
	public Reply finanace_convert_27_list() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("FINANCE_ID", "27");
		//System.out.println("yuq-----" + m);
		VelocityContext context = new VelocityContext();
		service.getBaseQueryDate(m, context);
		return new ReplyHtml(VM.html("financeConvert/financeConvert27.vm",
				context));
	}

}

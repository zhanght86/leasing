package com.pqsoft.overdue.action;





import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.fundNotEBank.service.FundNotEBankService;
import com.pqsoft.overdue.service.OverdueService;
import com.pqsoft.overdue.service.assetUtilService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
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
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.CnUpperCaser;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.StringUtils;

public class OverdueAction extends Action {

	public VelocityContext context = new VelocityContext();
	OverdueService service = new OverdueService();
	
	/*
	 * @Override
	 * 
	 * @aAuth(type = aAuth.aAuthType.LOGIN)
	 * 
	 * @aDev(code = "170043", email = "lichaohn@163.com", name = "李超") public
	 * Reply execute() { new Thread() { public void run() { try { OverdueService
	 * service = new OverdueService(); service.upDueAll(); } catch (Exception e)
	 * { e.printStackTrace(); Dao.rollback(); } finally { Dao.commit();
	 * Dao.close(); } }; }.start(); return new ReplyAjax(); }
	 */

	/*
	 * @aPermission(name = { "债权管理", "逾期总表", "列表" })
	 * 
	 * @aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	 * 
	 * @aAuth(type = aAuthType.USER) public Reply query_Supp_OverDue_MG() { Map
	 * map = this._getParameters(); List AREA_LIST = service.query_Area_List();
	 * context.put("AREA_LIST", AREA_LIST); Map mapAll =
	 * service.query_OverDue_All(map); context.put("mapAll", mapAll); return new
	 * ReplyHtml(VM.html("overdue/overdue_supper_MG.vm", context)).addOp(new
	 * OpLog("债权管理", "逾期总表", "列表查询错误")); }
	 */
	@aPermission(name = { "报表管理", "逾期总表", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Supp_OverDue_Mg_AJAX() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Supp_OverDue_Page(param);
		return new ReplyAjaxPage(page);
	}

	// 导出
	@aPermission(name = { "报表管理", "逾期总表", "导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply overDueUpload() {
		return new ReplyExcel(service.overDueUpload(), "逾期总表列表")
				.addOp(new OpLog("债权管理", "逾期总表", "导出错误"));
	}

	@aPermission(name = { "资产管理", "催收管理", "催收信息" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_Price_main() {
		Map<String, Object> map = _getParameters();
		return new ReplyHtml(VM.html("overdue/overdue_Cust_main.vm", context))
				.addOp(new OpLog("债权管理", "逾期催收主页面", "列表查询错误"));
	}

	
	@aPermission(name = { "资产管理", "逾期管理", "还款情况", "列表显示" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_Repayment_Mg() {
		Map<String, Object> map = _getParameters();
		context.put("Repayment", service.getRepayment(map));
		return new ReplyHtml(VM
				.html("overdue/overdue_Repayment_Mg.vm", context))
				.addOp(new OpLog("债权管理", "还款情况", "列表查询错误"));
	}

	@aPermission(name = { "资产管理", "逾期管理", "合同信息", "列表显示" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_Contract_Mg() {
		Map<String, Object> map = _getParameters();
		List<Map<String, Object>> eqlist = service.queryEqByProjectID(map);
		context.put("eqList", eqlist);
		return new ReplyHtml(VM.html("overdue/overdue_Contract_Mg.vm", context))
				.addOp(new OpLog("债权管理", "合同信息", "列表查询错误"));
	}

	/**
	 * 用于项目方案逾期查询
	 */
	@aPermission(name = { "资产管理", "逾期管理", "逾期信息", "列表显示" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply yqSearch() {
		Map<String, Object> param = _getParameters();
		System.out.println(param + "============");
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = service.getemployee(param);
		context.put("KHMC", map.get("CLIENT_ID"));
		context.put("param", param);
		return new ReplyHtml(VM.html("overdue/overdue_overdue_Mg.vm", context));
	}

	@aPermission(name = { "资产管理", "逾期管理", "资金信息", "列表显示" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_Funds_Mg() {
		Map<String, Object> map = _getParameters();
		Map<String, Object> RENTHEAD = service.doShowRentHeadByPayId(map);
		context.put("RENTHEAD", RENTHEAD);
		context.put("Format", UTIL.FORMAT);
		context.put("OTHERFEElIST", service
				.doShowRentDetailOtherFeeListByPayId(map));
		List<Map<String, Object>> paymentDetails = service
				.getPaymentDetailsByBuyContractId(map);
		for (Map<String, Object> m : paymentDetails) {
			Map<String, Object> refer = new HashMap<String, Object>();
			refer.put("PAYMENT_ID", m.get("ID").toString());
			List<Map<String, Object>> paymentTerms = service
					.getPaymentTermsByPaymentDetailId(refer);
			m.put("paymentTerms", paymentTerms);
		}
		context.put("paymentDetails", paymentDetails);
		Map<String, Object> buyContract = service
				.getBuyContractAndFilRentPlanHeadById(map);
		context.put("normList", service.queryPaymentMouldDetail(buyContract));
		return new ReplyHtml(VM.html("overdue/overdue_Funds_Mg.vm", context))
				.addOp(new OpLog("债权管理", "资金信息", "列表查询错误"));
	}

	@aPermission(name = { "资产管理", "催收管理" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_Price_MG() {
		Map<String, Object> map = this._getParameters();
		return new ReplyHtml(VM.html("overdue/overdue_Cust_Mg.vm", context))
				.addOp(new OpLog("债权管理", "逾期催收", "列表查询错误"));
	}

	@aPermission(name = { "资产管理", "催收管理" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_Price_Mg_AJAX() {
		Map<String, Object> param = _getParameters();
		System.out.println(param);
		Page page = service.query_overdue_Price_MG(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "资产管理", "催收管理", "转催收" })
	@aDev(code = "170021", email = "zhuxu@pasoft.com", name = "zhuxu")
	@aAuth(type = aAuthType.USER)
	public Reply addUrge() {
		Map<String, Object> param = _getParameters();
		String[] arr = (param.get("PAY_ID_ARR") + "").split(",");
		for (int i = 0; i < arr.length; i++) {
			param.put("PAY_ID", arr[i]);
			service.addUrgeData(param);
		}
		return new ReplyAjax(true, null);
	}

	@aPermission(name = { "资产管理", "催收管理", "承租人基本信息", "列表显示" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_employee_MG() {
		Map<String, Object> map = _getParameters();
		context.put("type", service.findCustDataType());// 客户关联信息
		Map<String, Object> employee = service.getemployee(map);
		context.put("custInfo", employee);
		context.put("getProvince", service.getProvince());
		if (employee.containsKey("PROVINCE")
				&& !StringUtils.isBlank(employee.get("PROVINCE"))) {
			map.put("PARENT_ID", employee.get("PROVINCE"));
			context.put("city", service.getCity(map));
		}
		if (employee.get("TYPE") != null) {
			if (employee.get("TYPE").toString().equals("NP")) {// 进入自然人页面
				return new ReplyHtml(VM.html("overdue/overdue_employee_np.vm",
						context)).addOp(new OpLog("债权管理", "承租人基本信息", "列表查询错误"));
			} else {// 进入法人页面
				return new ReplyHtml(VM.html("overdue/overdue_employee_lp.vm",
						context)).addOp(new OpLog("债权管理", "承租人基本信息", "列表查询错误"));
			}
		}
		return null;
	}

	@aPermission(name = { "资产管理", "催收管理", "担保信息", "列表显示" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_Guarantee_MG() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = service.getCredit(param);
		context.put("CREDIT_ID", m.get("CREDIT_ID"));
		context.put("PROJECT_ID", m.get("CREDIT_ID"));
		context.put("GUARANTOR_LIST", service.getGuarantorList(m));
		return new ReplyHtml(VM
				.html("overdue/overdue_Guarantee_Mg.vm", context))
				.addOp(new OpLog("债权管理", "担保信息", "列表查询错误"));
	}

	@aPermission(name = { "风险管理", "代理商逾期催收", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_Price_MG_SUPP() {
		Map map = this._getParameters();
		return new ReplyHtml(VM
				.html("overdue/overdue_Cust_Mg_SUPP.vm", context))
				.addOp(new OpLog("风险管理", "代理商逾期催收", "列表查询错误"));
	}

	@aPermission(name = { "风险管理", "代理商逾期催收", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_Price_Mg_AJAX_SUPP() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_overdue_Price_MG_SUPP(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "风险管理", "代理商逾期催收", "列表显示", "逾期明细" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_All_SUPP() {
		Map<String, Object> param = _getParameters();
		context.put("ovarDueAll", service.query_overdue_All(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("overdue/query_overdue_All.vm", context))
				.addOp(new OpLog("债权管理", "逾期催收", "逾期明细查询错误"));
	}

	@aPermission(name = { "资产管理", "催收管理", "添加沟通记录" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply create_GT_page() {
		Map<String, Object> param = _getParameters();
		context.put("overDue_Type", new DataDictionaryMemcached().get("催收类型"));
		context.put("NotRepaymentReason", new DataDictionaryMemcached()
				.get("逾期原因"));
		context.put("PressResults", new DataDictionaryMemcached().get("催收反馈"));
		context.put("CollectRecords", service
				.getOverdueCollectRecordByPayCode(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("overdue/overDue_OP.vm", context))
				.addOp(new OpLog("债权管理", "逾期催收-沟通记录", "列表查询错误"));
	}

	@aPermission(name = { "风险管理", "代理商逾期催收", "沟通记录", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply create_GT_page_SUPP() {
		Map<String, Object> param = _getParameters();
		context.put("overDue_Type", new DataDictionaryMemcached().get("催收类型"));
		context.put("NotRepaymentReason", new DataDictionaryMemcached()
				.get("逾期原因"));
		context.put("PressResults", new DataDictionaryMemcached().get("催收反馈"));
		context.put("CollectRecords", service
				.getOverdueCollectRecordByPayCode(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("overdue/overDue_OP.vm", context))
				.addOp(new OpLog("债权管理", "逾期催收-沟通记录", "列表查询错误"));
	}

	@aPermission(name = { "债权管理", "逾期催收", "沟通记录", "添加" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doAddpressDunLog() {
		Map<String, Object> map = _getParameters();
		Map mapPath = this._getParametersIO(null, null, null);
		int result = service.doAddPressDunLog(map);

		boolean flag = false;
		String msg = "";
		if (result > 0) {
			flag = true;
			msg = "添加成功!";
		} else {
			flag = false;
			msg = "添加失败!";
		}
		// 刚刚插入的催收记录数据的Id
		int preId = service.selectDunLogPreId();
		map.put("FIL_DUN_LOG_ID", preId);
		// service.addUrgeData(map);//催收任务类型分配
		if (mapPath != null && mapPath.get("_FILE_NAME") != null
				&& !mapPath.get("_FILE_NAME").equals("")) {
			map.put("FILE_NAME", mapPath.get("_FILE_NAME"));
			map.put("FILE_PATH", mapPath.get("file"));
			service.doAddCollectionRecord(map);
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("债权管理",
				"逾期催收-沟通记录", "添加错误"));

	}

	@aPermission(name = { "债权管理", "逾期催收", "沟通记录", "删除" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeletePressDunLog() {
		Map<String, Object> map = _getParameters();
		String ID = map.get("ID") == null ? "" : map.get("ID").toString();
		int result = 0;
		if (!"".equals(ID)) {
			result = service.doDeletePressDunLog(ID);
		}
		boolean flag = false;
		String msg = "";
		if (result > 0) {
			flag = true;
			msg = "删除成功!";
		} else {
			flag = false;
			msg = "删除失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("债权管理",
				"逾期催收-沟通记录", "删除错误"));
	}

	@aPermission(name = { "债权管理", "逾期催收", "沟通记录", "更多" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getMoreCollectRecords() {
		Map<String, Object> param = _getParameters();
		context.put("overDue_Type", new DataDictionaryMemcached().get("催收类型"));
		context.put("NotRepaymentReason", new DataDictionaryMemcached()
				.get("逾期原因"));
		context.put("PressResults", new DataDictionaryMemcached().get("催收反馈"));
		context.put("DunLog_More", service.DunLog_More(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("overdue/allPressDunLog.vm", context))
				.addOp(new OpLog("债权管理", "逾期催收-沟通记录", "查询更多错误"));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "债权管理", "逾期催收", "沟通记录", "下载" })
	public Reply downLoadRecordFile() {
		Map map = _getParameters();
		return new ReplyFile(new File(map.get("file_url") + ""), map
				.get("file_name")
				+ "").addOp(new OpLog("债权管理", "逾期催收-沟通记录", "下载错误"));
	}

	@aPermission(name = { "资产管理", "催收管理", "逾期明细" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_overdue_All() {
		Map<String, Object> param = _getParameters();
		context.put("ovarDueAll", service.query_overdue_All(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("overdue/query_overdue_All.vm", context))
				.addOp(new OpLog("债权管理", "逾期催收", "逾期明细查询错误"));
	}

	@aPermission(name = { "资产管理", "催收管理", "还款明细" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply STATEMENTS_ALL() {
		Map<String, Object> param = _getParameters();
		context.put("statements_list", service.STATEMENTS_ALL(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("overdue/STATEMENTS_ALL.vm", context))
				.addOp(new OpLog("债权管理", "逾期催收", "还款明细查询错误"));
	}

	@aPermission(name = { "资产管理", "催收管理", "更新" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply referDue() {
		// 罚息更新
		int result = 1;
		this.execute();
		boolean flag = false;
		String msg = "";
		if (result > 0) {
			flag = true;
			msg = "更新成功!";
		} else {
			flag = false;
			msg = "更新失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("债权管理", "逾期催收",
				"更新错误"));
	}

	// 计算罚息
	@aPermission(name = { "资产管理", "催收管理", "测算罚息" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply catulateFI() {
		Map map = _getParameters();
		if (map == null) {
			map = new HashMap();
		}
		// 传递参数支付表code和测算日期
		String PAYLIST_CODE = (map.get("PAYLIST_CODE") != null && !map.get(
				"PAYLIST_CODE").equals("")) ? map.get("PAYLIST_CODE")
				.toString() : "";
		String CURR_DATE = (map.get("CURR_DATE") != null && !map.get(
				"CURR_DATE").equals("")) ? map.get("CURR_DATE").toString() : "";
		// ---------------------------------------------------计算---------------------------------------------
		// ----------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("SUM_FINE", new OverdueService().zjPayList(PAYLIST_CODE,
				CURR_DATE));// 应付金额
		result.put("CURR_DUN_MONEY", new OverdueService().dunPayList(
				PAYLIST_CODE, CURR_DATE));// 罚息金额
		result.put("DUNDAY", new OverdueService().dayPayList(PAYLIST_CODE,
				CURR_DATE));// 逾期天数

		// ----------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------
		// ---------------------------------------------------计算---------------------------------------------
		return new ReplyAjax(JSONArray.fromObject(result)).addOp(new OpLog(
				"债权管理", "逾期催收", "测算罚息错误"));
	}

	@aPermission(name = { "资产管理", "催收管理", "转诉讼" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply subLawsuit() {
		Map map = _getParameters();
		if (map == null) {
			map = new HashMap();
		}
		int result = service.subLawsuit(map);

		boolean flag = false;
		String msg = "";
		if (result > 0) {
			flag = true;
			msg = "转诉讼成功!";
		} else {
			flag = false;
			msg = "转诉讼失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("债权管理", "逾期催收",
				"转诉讼错误"));
	}

	@aPermission(name = { "资产管理", "催收管理", "发送短信" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply sendSMS() {
		Map map = _getParameters();
		if (map == null) {
			map = new HashMap();
		}
		int result = service.sendSMS(map);

		boolean flag = false;
		String msg = "";
		if (result > 0) {
			flag = true;
			msg = "提交短信平台成功，等待发送中!";
		} else {
			flag = false;
			msg = "提交短信平台失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("债权管理", "逾期催收",
				"发送短信"));
	}

	@aPermission(name = { "资产管理", "资产管理", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_asset_MG() {
		Map map = this._getParameters();
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));// 获取租赁物类型
		return new ReplyHtml(VM.html("overdue/asset_MG.vm", context))
				.addOp(new OpLog("债权管理", "资产管理", "列表查询错误"));
	}

	@aPermission(name = { "资产管理", "资产管理", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_asset_MG_AJAX() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_asset_MG(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "资产管理", "资产管理", "预警-导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply YUJINExpPDF() {
		Map map = this._getParameters();
		assetUtilService assetSer = new assetUtilService();
		assetSer.exportWarningReportDoc(SkyEye.getResponse(), map);
		return null;
	}

	@aPermission(name = { "资产管理", "资产管理", "出险-导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply CHUXIANExpPDF() {
		Map map = this._getParameters();
		assetUtilService assetSer = new assetUtilService();
		assetSer.exportWarningReportDoc1(SkyEye.getResponse(), map);
		return null;
	}

	@aPermission(name = { "资产管理", "资产管理", "出险-事故" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply SHIGUExpPDF() {
		Map map = this._getParameters();
		assetUtilService assetSer = new assetUtilService();
		assetSer.exportWarningReportDoc2(SkyEye.getResponse(), map);
		return null;
	}

	@aPermission(name = { "资金管理", "减免罚息", "查询" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.USER)
	public Reply toExemptOverdueApplyList() {
		VelocityContext context = new VelocityContext();
		context.put("EQUI", Dao.selectList("fi.overdue.queryAllProduct"));
		return new ReplyHtml(VM.html("overdue/exemptApplyPage.vm", context));
	}

	@aPermission(name = { "资金管理", "减免罚息", "查询" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.USER)
	public Reply toExemptOverdueApplyPage() {
		Map<String, Object> m = _getParameters();
		Map<String, Object> sup = BaseUtil.getSup();
		if (sup != null) {
			m.put("SUP", sup.get("SUP_SHORTNAME"));
		}
		return new ReplyAjaxPage(new OverdueService().getExemptApplyPage(m));
	}

	@aPermission(name = { "资金管理", "减免查询", "查询" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.USER)
	public Reply toExemptOverdueList() {
		return new ReplyHtml(VM.html("overdue/exemptPage.vm", null));
	}

	@aPermission(name = { "资金管理", "减免查询", "查询" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.USER)
	public Reply toExemptOverduePage() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> sup = BaseUtil.getSup();
		if (sup != null) {
			param.put("SUP", sup.get("SUP_SHORTNAME"));
		}
		return new ReplyAjaxPage(new OverdueService().getExemptPage(param));
	}

	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toExemptOverdue() {
		OverdueService service = new OverdueService();
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("format", UTIL.FORMAT);
		context.put("data", service.getExempt(param));
		context.put("list", service.getExemptList(param));
		return new ReplyHtml(VM.html("overdue/exempt.vm", context));
	}

	@aPermission(name = { "债权管理", "违约金免单", "修改" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toExemptOverdueUp() {
		OverdueService service = new OverdueService();
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("format", UTIL.FORMAT);
		context.put("data", service.getExempt(param));
		context.put("list", service.getExemptList(param));
		return new ReplyHtml(VM.html("overdue/exemptUp.vm", context));
	}

	@aPermission(name = { "债权管理", "违约金免单", "修改" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doExemptUp() {
		Map<String, Object> param = _getParameters();
		OverdueService service = new OverdueService();
		service.exemptItemUp(param);
		return new ReplyAjax();
	}

	@aPermission(name = { "资金管理", "减免罚息", "申请" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.USER)
	public Reply doExemptOverdue() {
		OverdueService service = new OverdueService();
		Map<String, Object> param = _getParameters();
		param.put("CREATE_CODE", Security.getUser().getCode());
		param.put("CREATE_NAME", Security.getUser().getName());
		if (param.get("ids") == null)
			throw new ActionException("未获取到支付表编号");
		List<String> list = JBPM.getDeploymentListByModelName("违约金免除");
		if (list.size() != 1) {
			throw new ActionException("配置流程不唯一，当前使用了[" + list.size()
					+ "条]违约金免除流程");
		}

		String ids = param.get("ids").toString();
		int count = 0;
		Map<String, Object> sup = BaseUtil.getSup();
		if (sup != null) {
			param.put("SUP", sup.get("SUP_SHORTNAME"));
		}
		service.createExemptApply(param);
		for (String id : ids.split("[,;，；]")) {
			if (id == null || "".equals(id.trim()))
				continue;
			service.exemptOverdue(id, (String) param.get("ID"));
			count++;
		}
		service.joinDataClean((String) param.get("ID"));
		if (count == 0)
			throw new ActionException("请选择需要减免的违约金");
		// Map<String, Object> bpmInfo = service.getBpmInfoByPayCode(param);
		// service.exemptOverdue(param);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("EXEMPT_ID", param.get("ID"));
		String JBPM_ID = JBPM.startProcessInstanceById(list.get(0), null, null,
				null, m).getId();
		param.put("JBPM_ID", JBPM_ID);
		service.upExemptOverdue(param);
		String taskId = new TaskService().getMytaskId(JBPM_ID);
		return new ReplyAjax(taskId).addOp(new OpLog("违约金", "免除", JBPM_ID));
	}

	@aPermission(name = { "资金管理", "减免罚息", "申请" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.USER)
	public Reply doExemptOverdueAll() {
		OverdueService service = new OverdueService();
		Map<String, Object> param = _getParameters();
		param.put("CREATE_CODE", Security.getUser().getCode());
		param.put("CREATE_NAME", Security.getUser().getName());
		List<String> list = JBPM.getDeploymentListByModelName("违约金免除");
		if (list.size() != 1) {
			throw new ActionException("配置流程不唯一，当前使用了[" + list.size()
					+ "条]违约金免除流程");
		}
		Map<String, Object> sup = BaseUtil.getSup();
		if (sup != null) {
			param.put("SUP", sup.get("SUP_SHORTNAME"));
		}
		service.createExemptApply(param);
		service.exemptOverdueAll(param);
		service.joinDataClean((String) param.get("ID"));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("EXEMPT_ID", param.get("ID"));
		String JBPM_ID = JBPM.startProcessInstanceById(list.get(0), null, null,
				null, m).getId();
		param.put("JBPM_ID", JBPM_ID);
		service.upExemptOverdue(param);
		String taskId = new TaskService().getMytaskId(JBPM_ID);
		return new ReplyAjax(taskId).addOp(new OpLog("违约金", "免除", JBPM_ID));
	}

	// Add By YangJ 2014年5月15日11:01:31 查询asset资产明细
	@aPermission(name = { "资产管理", "资产管理", "明细" })
	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	@aAuth(type = aAuthType.USER)
	public Reply query_asset_Detail() {
		// System.out.println("输出明细action调用 ");
		Map<String, Object> param = com.pqsoft.skyeye.api.Action
				._getParameters();
		// System.out.println("输出明细action调用param "+param);

		return new ReplyAjax(service.query_asset_Detail(param));
	}

	// // Add By YangJ 22014年5月15日19:37:36 查询GPS(添加地点信息)
	// @aPermission(name = { "债权管理", "资产管理", "明细" })
	// @aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	// @aAuth(type = aAuthType.USER)
	// public Reply GPSManage(){
	// Map<String, Object> param =
	// com.pqsoft.skyeye.api.Action._getParameters();
	// System.out.println("GPS输出参数：：param "+param);
	// VelocityContext context=new VelocityContext();
	// // service.GPSManage();
	// context.put("areas", service.getFilSystemArea());
	// context.put("list", service.selectDeviceGps(param));
	// context.put("param", param);
	// return new ReplyHtml(VM.html("overdue/DeviceGPS.vm", context)).addOp(new
	// OpLog("债权管理", "资产管理", "GPS管理"));}
	// // return new ReplyHtml(VM.html("overdue/overdue_Cust_Mg.vm",
	// context)).addOp(new OpLog("债权管理", "逾期催收", "列表查询错误"));

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@aPermission(name = { "报表管理", "风险成因", "列表显示" })
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public Reply over_Mg() {
		List<Object> OVERDUE_LIST = (List) new DataDictionaryMemcached()
				.get("逾期原因");
		context.put("OVERDUE_LIST", OVERDUE_LIST);
		return new ReplyHtml(VM.html("overdue/risk_causes_mg.vm", context));
	}

	@aPermission(name = { "报表管理", "风险成因", "列表显示" })
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public Reply over_Mg_AJAX() {
		Map<String, Object> param = _getParameters();
		Page page = service.over_Mg_AJAX(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "风险管理", "风险成因管理列表", "图形统计" })
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public void refresh() {
		VelocityContext context = new VelocityContext();
		List<Object> list = (List) new DataDictionaryMemcached().get("逾期原因");
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		for (Object object : list) {
			Map<String, Object> map = (Map<String, Object>) object;
			// 获取数据源
			map.put("FLAG", map.get("FLAG").toString());
			map
					.put("COUNTS", service.getStatistics(map.get("CODE")
							.toString()));
			listData.add(map);
		}
		context.put("listData", listData);
		String xmlStr = VM.html("overdue/over_Cause_data.vm", context)
				.toString();
		System.out.println(xmlStr);
		if (xmlStr.length() > 0) {
			try {
				InputStream bfis = new ByteArrayInputStream(xmlStr
						.getBytes("GBK"));
				byte[] b = FileCopyUtils.copyToByteArray(bfis);
				int length = b.length;
				SkyEye.getResponse().setCharacterEncoding("UTF-8");
				SkyEye.getResponse().addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String("chart.xml".getBytes("GB2312"),
										"ISO-8859-1"));
				SkyEye.getResponse().setContentType("application/octet-stream");
				SkyEye.getResponse().addHeader("Content-Length", "" + length);
				OutputStream toClient = new BufferedOutputStream(SkyEye
						.getResponse().getOutputStream());
				toClient.write(b);
				toClient.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@aPermission(name = { "报表管理", "风险成因消除", "列表显示" })
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public Reply over_Remove_Mg() {
		List<Object> OVERDUE_LIST = (List) new DataDictionaryMemcached()
				.get("逾期原因");
		context.put("OVERDUE_LIST", OVERDUE_LIST);
		return new ReplyHtml(VM.html("overdue/risk_causes_remove_mg.vm",
				context));
	}

	@aPermission(name = { "报表管理", "风险成因消除", "列表显示" })
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public Reply over_Remove_Mg_AJAX() {
		Map<String, Object> param = _getParameters();
		Page page = service.over_Mg_AJAX(param);
		return new ReplyAjaxPage(page);
	}

	// "风险管理", "风险成因消除管理列表", "查看消除数据"
	@aPermission(name = { "报表管理", "风险成因消除", "查看" })
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public Reply queryReomve() {
		Map<String, Object> map = _getParameters();
		map.put("remove", service.getRemoveData(map));
		return new ReplyJson(JSONObject.fromObject(map));
	}

	@aPermission(name = { "报表管理", "风险成因消除", "消除" })
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public Reply addReomve() {
		Map<String, Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());
		System.out.println(map.toString());
		int i = service.addRemoveData(map);
		return new ReplyAjax(i == 1 ? true : false, null, "").addOp(new OpLog(
				"风险管理", "风险管消除理列表", "添加消除数据"));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "催收管理", "导出", "" })
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply exportExcels() {
		Map<String, Object> param = _getParameters();

		List<Map<String, Object>> dataList = service.getData(param);
		OutputStream ouputStream = null;
		try {

			Resource resource = UTIL.RES
					.getResource("classpath:/content/template/data_row.xls");
			Workbook wb = WorkbookFactory.create(resource.getInputStream());
			Sheet sheet = wb.getSheetAt(0);
			for (int j = 0; j < 1; j++) {
				Row row = sheet.getRow(j);
				for (int i = 0; i < row.getFirstCellNum(); i++) {
					Cell cell = row.getCell(i);
					System.out.println(cell.getRichStringCellValue());// 印出来看看数据
				}
			}
			for (int x = 0; x < dataList.size(); x++) {
				Row row = sheet.createRow(x + 1);
				Map<String, Object> map = new HashMap<String, Object>();
				map = dataList.get(x);
				if (map != null) {
					int cellIndex = 0;
					row.createCell(cellIndex++).setCellValue(x + 1);
					/* 客户名称 */
					String CUST_NAME = "";
					if (map.get("CUST_NAME") != null
							&& !"".equals(map.get("CUST_NAME"))) {
						CUST_NAME = map.get("CUST_NAME").toString();
					}
					row.createCell(cellIndex++).setCellValue(CUST_NAME);
					/* 催收状态 */
					String PLAN_STATE = "";
					if (map.get("PLAN_STATE") != null
							&& !"".equals(map.get("PLAN_STATE"))) {
						PLAN_STATE = map.get("PLAN_STATE").toString();
					}
					row.createCell(cellIndex++).setCellValue(PLAN_STATE);
					/* 还款计划 */
					String PAYLIST_CODE = "";
					if (map.get("PAYLIST_CODE") != null
							&& !"".equals(map.get("PAYLIST_CODE"))) {
						CUST_NAME = map.get("PAYLIST_CODE").toString();
					}
					row.createCell(cellIndex++).setCellValue(PAYLIST_CODE);
					/* 应收总金额 */
					String PAID_MONEY_ALL = "";
					if (map.get("PAID_MONEY_ALL") != null
							&& !"".equals(map.get("PAID_MONEY_ALL"))) {
						PAID_MONEY_ALL = map.get("PAID_MONEY_ALL").toString();
					}
					row.createCell(cellIndex++).setCellValue(PAID_MONEY_ALL);
					/* 逾期金额 */
					String RENT_RECE_ALL = "";
					if (map.get("RENT_RECE_ALL") != null
							&& !"".equals(map.get("RENT_RECE_ALL"))) {
						RENT_RECE_ALL = map.get("RENT_RECE_ALL").toString();
					}
					row.createCell(cellIndex++).setCellValue(RENT_RECE_ALL);
					/* 代理商垫付金额 */
					String VINUAL_MONEY_ALL = "";
					if (map.get("VINUAL_MONEY_ALL") != null
							&& !"".equals(map.get("VINUAL_MONEY_ALL"))) {
						VINUAL_MONEY_ALL = map.get("VINUAL_MONEY_ALL")
								.toString();
					}
					row.createCell(cellIndex++).setCellValue(VINUAL_MONEY_ALL);
					/* 罚息金额 */
					String PENALTY_RECE_ALL = "";
					if (map.get("PENALTY_RECE_ALL") != null
							&& !"".equals(map.get("PENALTY_RECE_ALL"))) {
						PENALTY_RECE_ALL = map.get("PENALTY_RECE_ALL")
								.toString();
					}
					row.createCell(cellIndex++).setCellValue(PENALTY_RECE_ALL);
					/* 代理商垫付罚息 */
					String VINUAL_PAID_ALL = "";
					if (map.get("VINUAL_PAID_ALL") != null
							&& !"".equals(map.get("VINUAL_PAID_ALL"))) {
						VINUAL_PAID_ALL = map.get("VINUAL_PAID_ALL").toString();
					}
					row.createCell(cellIndex++).setCellValue(VINUAL_PAID_ALL);

				}
			}
			String fileName = "催收导出"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar
							.getInstance().getTime());
			fileName = new String(fileName.getBytes(), "iso-8859-1");
			HttpServletResponse response = SkyEye.getResponse();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName + ".xls");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ouputStream != null) {
					ouputStream.flush();
					ouputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;

	}
	


     
 
    Document document = new Document();// 建立一个Document对象     
    
  

   

      
    
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "催收管理", "导出", "" })
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply generatePDFs(){
		Map<String, Object> param = _getParameters();
		OutputStream os = null;
	
		 try{
			 
			String fileName = "逾期催收金额导出"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());		   
		    fileName = new String(fileName.getBytes(), "iso-8859-1");
		
			 
			 
			 
			 
			Resource resource = UTIL.RES.getResource("classpath:/file/overduepdf.xlsx");
			
	        Workbook wb = WorkbookFactory.create(resource.getInputStream());
	        /*****************************/
	        Font headfont= wb.createFont();
	        headfont.setFontHeight((short) 200);
	        headfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        
	        Font keyfont= wb.createFont();// 设置字体大小        
	        keyfont.setFontHeight((short) 100);
	        
	        Font textfont =wb.createFont();// 设置字体大小 
	        textfont.setFontHeight((short) 150);
	        /*****************************/
	        Sheet sheet = wb.getSheetAt(0);
            System.out.println("param:"+param);
            System.out.println("resource:"+resource);

			String cust_name="";   //客户
			if(param.get("CUST_NAME")!=null&&!"".equals(param.get("CUST_NAME"))){
				cust_name=param.get("CUST_NAME").toString();
			}
			        
			String create_date="";
			if(param.get("CREATE_DATE")!=null&&!"".equals(param.get("CREATE_DATE"))){
		        String[] arr = param.get("CREATE_DATE").toString().split("-");	        
		        create_date=arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
		        System.out.println("create_date:"+create_date);
			}

			String PAY_START="";
			if(param.get("PAY_START")!=null&&!"".equals(param.get("PAY_START"))){
		        String[] arr = param.get("PAY_START").toString().split("-");	        
		        PAY_START=arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
			}
			
			String TOPRIC_SUBFIRENT="";
			if(param.get("TOPRIC_SUBFIRENT")!=null&&!"".equals(param.get("TOPRIC_SUBFIRENT"))){
				TOPRIC_SUBFIRENT=param.get("TOPRIC_SUBFIRENT").toString();
			}
			
			String RENT_RECE_ALL="";
			if(param.get("RENT_RECE_ALL")!=null&&!"".equals(param.get("RENT_RECE_ALL"))){
				RENT_RECE_ALL=param.get("RENT_RECE_ALL").toString();
			}
			
			String PENALTY_RECE_ALL="";
			if(param.get("PENALTY_RECE_ALL")!=null&&!"".equals(param.get("PENALTY_RECE_ALL"))){
				PENALTY_RECE_ALL=param.get("PENALTY_RECE_ALL").toString();
			}
			
			String RECE_ALL="";
			if(param.get("RECE_ALL")!=null&&!"".equals(param.get("RECE_ALL"))){	
				RECE_ALL=param.get("RECE_ALL").toString();
			}
			
			String OveDay="自 "+create_date+"（逾期日）至 "+PAY_START+"（实际还款日天数）";
			
			String cp=new CnUpperCaser(TOPRIC_SUBFIRENT).getCnString();
			String sx=new CnUpperCaser(RECE_ALL).getCnString();
			
			String yuan="元";
			String bigFont="，大写：";
			String kongGeB="          ";
			String kongGeS="     ";
			
            setCellValues(sheet,1,2,cust_name,wb);
            setCellValues(sheet,3,4,TOPRIC_SUBFIRENT+yuan+bigFont+cp,wb);	
            setCellValues(sheet,4,2,create_date,wb);	
            setCellValues(sheet,6,2,RENT_RECE_ALL,wb);	
            setCellValues(sheet,7,2,PENALTY_RECE_ALL,wb);
            setCellValues(sheet,8,2,OveDay,wb);
            setCellValues(sheet,9,2,create_date,wb);
            setCellValues(sheet,9,9,RECE_ALL,wb);/////////rj
            setCellValues(sheet,10,0,sx,wb); //合大写           
            setCellValues(sheet,15,0,create_date,wb);	
            
            
       
            
            
     	 	PdfPTable t1 = new PdfPTable(2);
            
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();
            String currentCellValue=null;
            // 读取数据行
            System.out.println("sfds:"+setCellValue1(sheet,0,0,wb));
            System.out.println("sfds:"+setCellValue1(sheet,1,0,wb)+setCellValue1(sheet,1,1,wb)+ "\t");
            
            
            for (int rowIndex = firstRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);// 当前行
                int firstColumnIndex = currentRow.getFirstCellNum(); // 首列
                int lastColumnIndex = currentRow.getLastCellNum();// 最后一列
                for (int columnIndex = firstColumnIndex; columnIndex <= lastColumnIndex; columnIndex++) {
                    Cell currentCell = currentRow.getCell(columnIndex);// 当前单元格
                    currentCellValue = this.getCellValue(currentCell, true);// 当前单元格的值              
                    if(rowIndex==2||rowIndex==3||rowIndex==4||rowIndex==5||rowIndex==11||rowIndex==12||rowIndex==14||rowIndex==15){
                    	 currentCellValue="         "+ currentCellValue;
                    }     
                    System.out.print(currentCellValue+ "\t");
                }
              
                System.out.println("t1:"+t1);
            }
            System.out.println("======================================================");
       

		   HttpServletResponse response = SkyEye.getResponse();		    
		   response.setContentType("application/vnd.ms-excel");
		   response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
		      //下载指定PDF文件
		   os = response.getOutputStream();
		   wb.write(os);
		  

	        
		} catch (Exception e) {
			e.printStackTrace();
		}  
		finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
		

	}

  
	   
	private String setCellValue1(Sheet sheet, int iRow, int iCol,Workbook wb){

			CellStyle head_cellStyle = wb.createCellStyle();
			Font head_font = wb.createFont();
			
			head_cellStyle.setAlignment(CellStyle.ALIGN_LEFT); // 指定单元格居左对齐 
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐 
			head_cellStyle.setWrapText(true);// 指定单元格自动换行 
			
			
			head_font.setBoldweight(Font.BOLDWEIGHT_NORMAL); 
			head_font.setFontName("宋体"); 
			head_font.setUnderline(Font.U_DOUBLE);
			
			head_cellStyle.setFont(head_font); 
	    	
	    	
	    	Row row = sheet.getRow((short)iRow);
	        Cell cell = row.getCell((short)iCol);
	        cell.setCellStyle(head_cellStyle);
			return  cell.getStringCellValue();
	}
	   
	   
	private void setCellValues(Sheet sheet, int iRow, int iCol,String val,Workbook wb){

   	
		CellStyle head_cellStyle = wb.createCellStyle();
		Font head_font = wb.createFont();
		head_cellStyle.setWrapText(true);// 指定单元格自动换行 
		
		//head_font.setFontName("宋体"); 
	    //head_font.setUnderline(Font.U_DOUBLE);
		//head_font.setFontHeight((short) 300); 
		
		head_cellStyle.setFont(head_font); 
    	
    	
    	Row row = sheet.getRow((short)iRow);
        Cell cell = row.getCell((short)iCol);
        cell.setCellStyle(head_cellStyle);
		row.createCell(1).setCellValue(val);
    }
	
	
    /**
     * 取单元格的值
     * @param cell 单元格对象
     * @param treatAsStr 为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    private String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }

        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }


    public PdfPCell createCell(String value,Font font,int align){ 
        PdfPCell cell = new PdfPCell(); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);         
        cell.setHorizontalAlignment(align);     
        cell.setPhrase(new Phrase(value,null)); 
       return cell; 
   } 
    
    
    
    public PdfPCell createCell(String value,Font font,int align,int colspan,boolean boderFlag){ 
        PdfPCell cell = new PdfPCell(); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
        cell.setHorizontalAlignment(align);     
        cell.setColspan(colspan); 
        cell.setPhrase(new Phrase(value,null)); 
        cell.setPadding(3.0f); 
        if(!boderFlag){ 
            cell.setBorder(0); 
            cell.setPaddingTop(15.0f); 
            cell.setPaddingBottom(8.0f); 
        } 
       return cell; 
   } 

}

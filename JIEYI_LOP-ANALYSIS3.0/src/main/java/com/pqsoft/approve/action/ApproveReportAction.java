package com.pqsoft.approve.action;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.approve.service.ApproveReportService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.UtilChart;

public class ApproveReportAction extends Action {

	private final String path = "approveReport/";
	private final ApproveReportService service = new ApproveReportService();
	//页面自定义显示列的下拉列表所需，同时也是sql所需--审批总表
	private String[] titlesName = new String[] { "提交申请总数", "重复提交件数", "已批复通过申请", "已拒绝申请", "尚未处理申请", "退回申请件数", "批复通过率", "拒绝率", "重复提交率", "退回申请比例" };
	private String[] sqlsName = new String[] { "TOTAL","COLUMN2", "THROUGH", "NOTBY",  "UNTREATED", "REJECT", "APPROVED", "REFUSE","GOBACK", "GOBACK" };
	//页面自定义显示列的下拉列表所需，同时也是sql所需--特殊处理总报表
	private String[] titlesName1 = new String[] { "时间", "提交申请总数", "已批复通过申请", "尚未处理申请", "支持销售", "缺少必要文件", "免除家访", "免除电话核实", "特批财务指标", "其他", "特批处理申请数", "特批处理比例" };
	private String[] sqlsName1 = new String[] { "C_DATE", "SQZS","TG", "WCL", "ZCXS", "QSBY", "MCJF", "MCDH", "TPCW", "QT", "ZS", "BL" };
	//页面自定义显示列的下拉列表所需，同时也是sql所需--特批处理明细报表
    private String[] titlesName2 = new String[] {"时间", "提交申请总数", "支持销售", "支持销售比例", "缺少必要文件", "缺少必要文件比例", "免除家访", "免除家访比例", "免除电话核实", "免除电话核实比例", "特批财务指标", "特批财务指标比例", "其他", "其他比例" };
	private String[] sqlsName2 = new String[] { "C_DATE","ZS", "ZCXS", "ZCXSBL", "QSBY", "QSBYBL", "MCJF", "MCJFBL", "MCDH", "MCDHBL", "TPCW","TPCWBL", "QT", "QTBL" };
	//页面自定义显示列的下拉列表所需，同时也是sql所需--拒绝申请统计明细表
    private String[] titlesName3 = new String[] { "时间", "提交申请总数", "已拒绝申请", "拒绝率", "尚未处理申请", "融资额(0,5]万被拒绝的申请数", "融资额(0,5]万被拒绝的申请比例", "融资额（5,10]万被拒绝的申请数", "融资额（5,10]万被拒绝的申请比例", "融资额（10,30]万被拒绝的申请数", "融资额（10,30]万被拒绝的申请比例", "融资额（30,50]万被拒绝的申请数", "融资额（30,50]万被拒绝的申请比例", "融资额（50,80]万被拒绝的申请数", "融资额（50,80]万被拒绝的申请比例", "融资额（80,100]万被拒绝的申请数", "融资额（80,100]万被拒绝的申请比例", "融资额（100,+∞)万被拒绝的申请数", "融资额（100,+∞)万被拒绝的申请比例" };
	private String[] sqlsName3 = new String[] { "C_DATE","ZS", "YJJ", "JJL", "WCL", "YJJ5", "YJJ5BL", "YJJ10", "YJJ10BL", "YJJ30", "YJJ30BL","YJJ50", "YJJ50BL", "YJJ80", "YJJ80BL","YJJ100", "YJJ100BL", "YJJ111","YJJ111BL" };
	//页面自定义显示列的下拉列表所需，同时也是sql所需--拒绝原因分类报表
    private String[] titlesName4 = new String[] { "时间", "提交申请总数", "已拒绝申请", "拒绝率", "尚未处理申请", "信用记录较差", "信息/材料虚假", "年龄不符", "高风险行业", "工作居住不稳定", "代购", "财务指标不足", "其它" };
	private String[] sqlsName4 = new String[] { "C_DATE","ZS", "YJJ", "JJL", "WCL", "XYJL", "XXCL", "NLBF", "GFX", "GZJZ", "DG","CWZB", "QT" };
	//页面自定义显示列的下拉列表所需，同时也是sql所需--拒绝原因比例表
	private String[] titlesName5 = new String[] { "提交申请总数", "信用记录较差", "信用记录较差比例", "信息/材料虚假", "信息/材料虚假比例", "年龄不符", "年龄不符比例", "高风险行业", "高风险行业比例", "工作居住不稳定", "工作居住不稳定比例", "代购", "代购比例", "财务指标不足", "财务指标不足比例", "其它", "其它比例" };
	private String[] sqlsName5 = new String[] { "ZS","XYJL", "XYJLBL", "XXCL", "XXCLBL", "NLBF", "NLBFBL", "GFX", "GFXBL", "GZJZ", "GZJZBL","DG", "DGBL", "CWZB", "CWZBBL","QT", "QTBL" };
	//页面自定义显示列的下拉列表所需，同时也是sql所需--拒绝区域分类表
    private String[] titlesName6 = new String[] { "提交申请总数", "已拒绝申请", "尚未处理申请", "山东地区", "山东地区比例", "河南地区", "河南地区比例", "广东地区", "广东地区比例", "湖南地区", "湖南地区 比例" };
	private String[] sqlsName6 = new String[] { "COLUMN1","COLUMN2", "COLUMN3", "COLUMN4", "COLUMN5", "COLUMN6", "COLUMN7", "COLUMN8", "COLUMN9", "COLUMN10", "COLUMN11" };
		
		
	//报表工具调用
    UtilChart utilChart=new UtilChart();
	
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理", "审批报表", "审批总报表", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply execute() {
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("审批总报表", titlesName, sqlsName));
		/********************************************************************/
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toMgApproveAllReport.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "审批总报表", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgApproveAllReport(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgApproveAllReport(map));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "审批总报表", "导出"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toExcelApproveAll(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) service.toExcelApproveAll(map);
		new com.pqsoft.util.ImportExcelPoi().expExcel_2007(map, "审批总报表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx","特批处理总报表",titlesName,sqlsName,dataList);
		return null;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理", "审批报表", "特批处理总报表", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgSpecialProcedureAll() {
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("特批处理总报表", titlesName1, sqlsName1));
		/********************************************************************/
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toMgSpecialProcedureAll.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "特批处理总报表", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgSpecialProcedureAllData(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgSpecialProcedureAllData(map));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "特批处理总报表", "导出"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toImportSpecial(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) service.toImportSpecial(map);
		new com.pqsoft.util.ImportExcelPoi().expExcel_2007(map, "特批处理总报表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx","特批处理总报表",titlesName1,sqlsName1,dataList);
		return null;
	}
	
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理", "审批报表", "特批处理明细报表", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgSpecialProcedureDetail() {
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("特批处理明细报表", titlesName2, sqlsName2));
		/********************************************************************/
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toMgSpecialProcedureDetail.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "特批处理明细报表", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgSpecialProDetailData(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgSpecialProDetail(map));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "特批处理明细报表", "导出"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toImportSpecialProDetai(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) service.toImportSpecialProDetail(map);
		new com.pqsoft.util.ImportExcelPoi().expExcel_2007(map, "特批处理明细报表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx","特批处理明细报表",titlesName2,sqlsName2,dataList);
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理", "审批报表", "拒绝申请统计明细", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgRefusePro(){
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("拒绝申请统计明细", titlesName3, sqlsName3));
		/********************************************************************/
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toMgRefusePro.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "拒绝申请统计明细", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgRefuseProData(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgRefusePro(map));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "拒绝申请统计明细", "导出"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toImportRefusePro(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) service.toImportRefusePro(map);
		new com.pqsoft.util.ImportExcelPoi().expExcel_2007(map, "拒绝申请统计明细"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx","拒绝申请统计明细",titlesName3,sqlsName3,dataList);
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理", "审批报表", "拒绝原因分类报表", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgRefuseReason(){
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("拒绝原因分类报表", titlesName4, sqlsName4));
		/********************************************************************/
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toMgRefuseReason.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "拒绝原因分类报表", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgRefuseReasonData(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgRefuseReason(map));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "拒绝原因分类报表", "导出"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toImportRefuseReason(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) service.toImportRefuseReason(map);
		new com.pqsoft.util.ImportExcelPoi().expExcel_2007(map, "拒绝原因分类报表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx","拒绝原因分类报表",titlesName4,sqlsName4,dataList);
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理", "审批报表", "拒绝原因比率", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgRefuseReasonRate(){
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("拒绝原因比率", titlesName5, sqlsName5));
		/********************************************************************/
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toMgRefuseReasonRate.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "拒绝原因比率", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgRefuseReasonRateData(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgRefuseReasonRateData(map));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "拒绝原因比率", "导出"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toImportRefuseReasonRate(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) service.toImportRefuseReasonRate(map);
		new com.pqsoft.util.ImportExcelPoi().expExcel_2007(map, "拒绝原因比率"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx","拒绝原因比率",titlesName5,sqlsName5,dataList);
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理", "审批报表", "拒绝区域分类", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgRefuseArea(){
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("拒绝区域分类", titlesName6, sqlsName6));
		/********************************************************************/
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toMgRefuseArea.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "审批报表", "拒绝区域分类", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgRefuseAreaData(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgRefuseAreaData(map));
	}
}

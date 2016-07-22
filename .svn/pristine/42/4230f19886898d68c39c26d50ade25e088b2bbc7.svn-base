//package com.pqsoft.base.grantCredit.action;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//import org.jfree.data.general.DefaultPieDataset;
//
//import com.pqsoft.base.company.service.CompanyService;
//import com.pqsoft.base.grantCredit.service.CreditLimitStatisticService;
//import com.pqsoft.entity.Excel;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyFile;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.filter.ResMime;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.JfreeChartUtil;
//import com.pqsoft.util.ReplyExcel;
//
//public class CreditLimitStatisticAction extends Action {
//    private String path ="base/grantCredit/";
//    private CreditLimitStatisticService service = new CreditLimitStatisticService();
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理", "授信额度占用统计", "列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	@Override
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		//查询厂商(下拉选)
//		CompanyService comService = new CompanyService();
//		context.put("companys", comService.getAllCompany());
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path+"CreditTongJiManager.vm", context));
//	}
//	
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理","授信额度占用统计","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getPageData(){
//		Map<String,Object> param = _getParameters();
//		Page pageData = service.getCreditLimitPageData(param);
//		return new ReplyAjaxPage(pageData);
//	}
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理","授信额度占用统计","导出数据"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply exportLimitRateMess(){
//    	Map<String,Object> param = _getParameters();
//    	Excel excel = new Excel();
//		excel = service.exportLimitRateMess(param);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//	    String fileName = "auditSupervision-("+sdf.format(new Date())+").xls";
//		return new ReplyExcel(excel,fileName);
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理","授信额度占用统计","下载饼图"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply downLoadPic(){
//    	Map<String,Object> param = _getParameters();
//    	Boolean flag = true ;
//    	String msg = "";
//    	List<Object> dataList = service.getLimitMessList(param);
//    	DefaultPieDataset dataset = new DefaultPieDataset();
//    	for (int i = 0; i < dataList.size()-1; i++) {
//			Map<String,Object> data = (Map<String,Object>)dataList.get(i);
//			Double term_money = data.containsKey("SUM_MONEY") && data.get("SUM_MONEY") != null ? Double.parseDouble(data.get("SUM_MONEY").toString()): 0d;
//			dataset.setValue(data.get("KEY_WORD_VALUE").toString(),term_money);
//		}
//		try {
//			JfreeChartUtil util = new JfreeChartUtil();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//			String fileName = "auditSupervision-("+sdf.format(new Date())+")";
//			OutputStream pw = SkyEye.getResponse().getOutputStream();
//			SkyEye.getResponse().reset();
//			SkyEye.getResponse().setCharacterEncoding("UTF-8");
//            SkyEye.getResponse().setHeader("Content-Disposition", new StringBuilder("attachment;filename=").append(new String(fileName.getBytes("GB2312"), "ISO-8859-1")).toString());
//            SkyEye.getResponse().setContentType(ResMime.get("jpg"));
//			util.generatePieChart(SkyEye.getRequest().getSession(), pw, dataset, 720, 500);
//		} catch (Exception e) {
//			flag = false;
//			msg += "下载出错！";
//		}
//    	return null;//new ReplyAjax(flag,msg);
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"授信管理","授信额度占用统计","查看饼图"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply lookPiePic(){
//    	Map<String,Object> param = _getParameters();
//    	String msg = "";
//    	List<Object> dataList = service.getLimitMessList(param);
//    	DefaultPieDataset dataset = new DefaultPieDataset();
//    	for (int i = 0; i < dataList.size()-1; i++) {
//			Map<String,Object> data = (Map<String,Object>)dataList.get(i);
//			Double term_money = data.containsKey("SUM_MONEY") && data.get("SUM_MONEY") != null ? Double.parseDouble(data.get("SUM_MONEY").toString()): 0d;
//			dataset.setValue(data.get("KEY_WORD_VALUE").toString(),term_money);
//		} 
//    	JfreeChartUtil util = new JfreeChartUtil();
//    	String fileName = util.showGeneratePieChart(SkyEye.getRequest().getSession(),dataset, 720, 500);
//    	if(!"".equals(fileName)){
//    		return new ReplyAjax(true, fileName);
//    	}else{
//    		return new ReplyAjax(false,"生成出错");
//    	}
//    } 
//
//}

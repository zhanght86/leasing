//package com.pqsoft.T1Report.action;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import net.sf.json.JSONObject;
//
//import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
//import com.pqsoft.superTable.service.ReportBaseService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.POIExcelUtil;
//
//
///**
// * <p>
// * Title: 融资租赁信息系统平台 数据统计  T105风险债权统计明细
// * </p>
// * <p>
// * Description: T1租金查询
// * </p>
// * <p>
// * Company: 平强软件
// * </p>
// * 
// * @author 吴剑东 wujd@pqsoft.cn
// * @version 1.0
// */
//public class T105ReportSonAction extends Action {
//	private ReportBaseService service = new ReportBaseService();
//	Map<String,Object> m = null;
//	
//	String[] titlesName = new String[]{"区域",	"供应商",	"在租总台量",	"租金余额",	"逾期总台量",	"逾期金额",	"逾期风险%",	"逾期租金余额",	"逾期租金风险%",	"逾期1期台量",	"逾期1期租金余额",	"逾期1期风险%",	"逾期2期台量",	"逾期2期租金余额",	"逾期2期风险%",	"逾期3期台量",	"逾期3期租金余额",	"逾期3期风险%",	"逾期3期以上台量",	"逾期3期以上租金余额",	"逾期3期以上风险%"};
//	private  String COLUMN_VIEW="COLUMN2,COMPANY_NAME,ID";
//	private  String COLUMN_FORM="PARAM1,COMPANY_NAME,SUP_TYPE";
//	private  String COLUMN_RELATION="LIKE,EQ,EQ";
//	private  int[] titleForm=new  int[]{4,6,7,8,9,11,12,14,15,17,18,20,21};
//	
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T105风险债权统计明细", "列表页面" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@Override
//	@SuppressWarnings("unchecked")
//	public Reply execute() {
//		m = _getParameters();
//		VelocityContext context = new VelocityContext();
//		m.put("USER_CODE", Security.getUser().getCode());
//		m.put("REPORT_NAME", "T105ReportSon");
//		context.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
//		
////		List<Map<String, Object>> productTypeList = new DataDictionaryMemcached().get("产品类型");
////		context.put("productTypeList",productTypeList);
////		List<Map<String, Object>> repayTypeList = new DataDictionaryMemcached().get("还款政策");
////		context.put("repayTypeList",repayTypeList);
////		List<Map<String, Object>> projectTypeList = new SysDictionaryMemcached().get("项目状态位");
////		context.put("projectTypeList",projectTypeList);
//		
//		List<Map<String, Object>> columnList = new ArrayList<Map<String,Object>>();
//		for (int i = 0; i < titlesName.length; i++) {
//			Map<String,Object> colMap = new HashMap<String, Object>();;
//			colMap.put("FLAG",titlesName[i]);
//			columnList.add(colMap);
//		}
//		context.put("columnList",columnList);
//		context.put("COMPANY_NAME",m.get("COMPANY_NAME"));
////		System.out.println(m.toString());
//		return new ReplyHtml(VM.html("T1Report/T105ReportSon.vm", context));
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T105风险债权统计明细", "列表数据" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply doTableShow(){
//		m = _getParameters();
//		m.put("USER_CODE", Security.getUser().getCode());
//		
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		
//		m.put("REPORT_NAME", "T105ReportSon");
//		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
//		if(m.get("COLUMN_NAMES") != null){
//			service.delReportColumnByReportAndUser(m);
//			service.insertReportColumnByReportAndUser(m);
//		}
//		m.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
//		if(m.get("COLUMN_NAMES") == null || m.get("COLUMN_NAMES") == ""){
//			m.put("COLUMN_NAMES","*");
//		}else{
//			m.put("COLUMN_NAMES",m.get("COLUMN_NAMES").toString()+",ID");
//		}
//		m.put("V_NAME", "V_REPORT_T105_SON");
//		
//        service.getWhereStatement(COLUMN_VIEW, COLUMN_FORM, COLUMN_RELATION, m);
//        m.put("titleForm", titleForm);
//		return new ReplyAjaxPage(service.getTableDataPage(m));
//	}
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T105风险债权统计明细", "列表导出" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public ReplyAjax exportExcel(){
//		m = _getParameters();
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		
//		m.putAll(json);
//		//查询导出字段
//		String[] titleName = null;
//		int[] titleIndex = null;
//		
//		m.put("USER_CODE", Security.getUser().getCode());
//		m.put("REPORT_NAME", "T105ReportSon");
//		m.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
//		//判断是否全部导出
//		if(m.get("COLUMN_NAMES") == null || m.get("COLUMN_NAMES") == ""){
//			m.put("COLUMN_NAMES","*");
//			titleName = titlesName;
//			titleIndex = new int[titlesName.length];
//			for (int i = 0; i < titlesName.length; i++) {
//				titleIndex[i] = i+1;
//			}
//		}else{
//			String[] arr = m.get("COLUMN_NAMES").toString().split(",");
//			titleName = new String[arr.length];
//			titleIndex = new int[arr.length];
//			//数据排序
//			for (int i = 0; i < arr.length; i++) {
//				int j = Integer.parseInt(arr[i].replace("COLUMN", ""));
//				titleIndex[i] = j;
//			}
//			Arrays.sort(titleIndex);
//			//title排序
//			for (int i = 0; i < titleIndex.length; i++) {
//				titleName[i] = titlesName[titleIndex[i]-1];
//			}
//		}
//
//		m.put("V_NAME", "V_REPORT_T105_SON");
//		
//		new POIExcelUtil().expExcel2007(m,"T105风险债权统计明细.xlsx", "T105风险债权统计明细", titleName , titleIndex, service.getTableExplorData(m));
//
//		return null;
//	}
//
//}

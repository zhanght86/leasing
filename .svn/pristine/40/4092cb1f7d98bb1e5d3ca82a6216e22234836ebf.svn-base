//package com.pqsoft.T3Report.action;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
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
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.DateUtil;
//import com.pqsoft.util.POIExcelUtil;
//
//
///**
// * <p>
// * Title: 融资租赁信息系统平台 数据统计  T306期租金还款情况
// * </p>
// * <p>
// * Description: T3债权统计
// * </p>
// * <p>
// * Company: 平强软件
// * </p>
// * 
// * @author 吴剑东 wujd@pqsoft.cn
// * @version 1.0
// */
//public class T306ReportAction extends Action {
//	private ReportBaseService service = new ReportBaseService();
//	Map<String,Object> m = null;
//	
//	String[] titlesName = new String[]{"项目编号",	"客户",	"机型",	"出厂编号",	"起租确认日期","供应商","厂商",	"第一期   ",	"第二期   ",	"第三期   ",	"第四期   ",	"第五期   ",	"第六期   ",	"第七期   ",	"第八期   ",	"第九期   ",	"第十期   ",	"第十一期   ",	"第十二期   ",	"第十三期   ",	"第十四期   ",	"第十五期   ",	"第十六期   ",	"第十七期   ",	"第十八期   ",	"第十九期   ",	"第二十期   ",	"第二十一期   ",	"第二十二期   ",	"第二十三期   ",	"第二十四期   ",	"第二十五期   ",	"第二十六期   ",	"第二十七期   ",	"第二十八期   ",	"第二十九期   ",	"第三十期   ",	"第三十一期   ",	"第三十三期   ",	"第三十三期   ",	"第三十四期   ",	"第三十五期   ",	"第三十六期   ",	"第三十七期   ",	"第三十八期   ",	"第三十九期   ",	"第四十期   ",	"第四十一期   ",	"第四十四期   ",	"第四十四期   ",	"第四十四期   ",	"第四十五期   ",	"第四十六期   ",	"第四十七期   ",	"第四十八期   ",	"台量 ",	"截至目前逾期租金 ",	"累计逾期 ","当前逾期 "};
//	private  String COLUMN_VIEW="T.COLUMN47,T.COLUMN5,T.COLUMN2,T.COLUMN13,T.COLUMN8,T.COLUMN8";
//	private  String COLUMN_FORM="PARAM0,PARAM1,PARAM2,PARAM3,PARAM4,PARAM5";
//	private  String COLUMN_RELATION="LIKE,LIKE,LIKE,EQ,EG,EL";
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T306期租金还款情况", "列表页面" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@Override
//	@SuppressWarnings("unchecked")
//	public Reply execute() {
//		m = _getParameters();
//		VelocityContext context = new VelocityContext();
//		m.put("USER_CODE", Security.getUser().getCode());
//		m.put("REPORT_NAME", "T306Report");
//		context.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
//		
//		List<Map<String, Object>> manufacturer=service.getManufacturer();
//		context.put("manufacturer",manufacturer );
//		
//		List<Map<String, Object>> columnList = new ArrayList<Map<String,Object>>();
//		for (int i = 0; i < titlesName.length; i++) {
//			Map<String,Object> colMap = new HashMap<String, Object>();;
//			colMap.put("FLAG",titlesName[i]);
//			columnList.add(colMap);
//		}
//		context.put("columnList",columnList);
//		
//		return new ReplyHtml(VM.html("T3Report/T306Report.vm", context));
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T306期租金还款情况", "列表数据" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply doTableShow(){
//		m = _getParameters();
//		m.put("USER_CODE", Security.getUser().getCode());
//		
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//
//		
//		m.put("REPORT_NAME", "T306Report");
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
//
//		m.put("V_NAME", "V_REPORT_T306");
//		service.getWhereStatement(COLUMN_VIEW, COLUMN_FORM, COLUMN_RELATION, m);
//		if(m.get("WHERESTRING") != null && !"".equals(m.get("WHERESTRING").toString())){
//			m.put("WHERESTRING", m.get("WHERESTRING").toString()+" AND T.COLUMN16 = '逾期' ");
//		}else{
//			m.put("WHERESTRING", " WHERE T.COLUMN16 = '逾期' ");
//		}
//		return new ReplyAjaxPage(service.getT306TableData(m));
//	}
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "数据统计", "T306期租金还款情况", "列表导出" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void exportExcel(){
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
//		m.put("REPORT_NAME", "T306Report");
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
//		if ("true".equals(m.get("exportAll"))) {
//			service.getWhereStatement(COLUMN_VIEW, COLUMN_FORM, COLUMN_RELATION, m);
//			if(m.get("WHERESTRING") != null && !"".equals(m.get("WHERESTRING").toString())){
//				m.put("WHERESTRING", m.get("WHERESTRING").toString()+" AND T.COLUMN16 = '逾期' ");
//			}else{
//				m.put("WHERESTRING", " WHERE T.COLUMN16 = '逾期' ");
//			}
//		} else {
//			m.put("WHERESTRING", "WHERE T.COLUMN47 IN(" + m.get("sqlData") + ") AND T.COLUMN16 = '逾期' ");
//		}
//		
//		m.put("V_NAME", "V_REPORT_T306");
//		
//		new POIExcelUtil().T306expExcel2007(m,"T306期租金还款情况.xlsx", "T306期租金还款情况", titleName , titleIndex, service.getT306TableDataForExp(m));
//	}
//
//}

//package com.pqsoft.T3Report.action;
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
//import com.pqsoft.util.DateUtil;
//import com.pqsoft.util.POIExcelUtil;
//
//
///**
// * <p>
// * Title: 融资租赁信息系统平台 数据统计  T304机型统计
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
//public class T304ReportAction extends Action {
//	private ReportBaseService service = new ReportBaseService();
//	Map<String,Object> m = null;
//	
//	String[] titlesName = new String[]{"租赁物名称",	"机型",	"一月",	"二月",	"三月",	"四月",	"五月",	"六月",	"七月",	"八月",	"九月",	"十月",	"十一月",	"十二月",	"小计",	"百分比"};
//	
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T304机型统计", "列表页面" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@Override
//	@SuppressWarnings("unchecked")
//	public Reply execute() {
//		m = _getParameters();
//		VelocityContext context = new VelocityContext();
//		m.put("USER_CODE", Security.getUser().getCode());
//		m.put("REPORT_NAME", "T304Report");
//		context.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
//		
//		List<Map<String, Object>> productTypeList = new DataDictionaryMemcached().get("产品类型");
//		context.put("productTypeList",productTypeList);
//		List<Map<String, Object>> repayTypeList = new DataDictionaryMemcached().get("还款政策");
//		context.put("repayTypeList",repayTypeList);
//		List<Map<String, Object>> projectTypeList = new SysDictionaryMemcached().get("项目状态位");
//		context.put("projectTypeList",projectTypeList);
//		
//		List<Map<String, Object>> columnList = new ArrayList<Map<String,Object>>();
//		for (int i = 0; i < titlesName.length; i++) {
//			Map<String,Object> colMap = new HashMap<String, Object>();;
//			colMap.put("FLAG",titlesName[i]);
//			columnList.add(colMap);
//		}
//		context.put("DATA_TIME",DateUtil.getSysDate("yyyy-MM-dd"));
//		context.put("columnList",columnList);
//		
//		return new ReplyHtml(VM.html("T3Report/T304Report.vm", context));
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T304机型统计", "列表数据" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply doTableShow(){
//		m = _getParameters();
//		m.put("USER_CODE", Security.getUser().getCode());
//		
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//
//		if(m.get("DATA_TIME") == null || "".equals((m.get("DATA_TIME")==null?"":m.get("DATA_TIME").toString()))){
//			m.put("DATA_TIME",DateUtil.getSysDate("yyyy"));
//		}else{
//			// 数据时点字符串转日期后获取年份
//			m.put("DATA_TIME",DateUtil.getYear(DateUtil.parseSqlDate(m.get("DATA_TIME").toString())));
//		}
//		m.put("REPORT_NAME", "T304Report");
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
//		m.put("V_NAME", "V_REPORT_T304");
//		return new ReplyAjaxPage(service.getTableData(m));
//	}
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T304机型统计", "列表导出" })
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
//		m.put("REPORT_NAME", "T304Report");
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
//		m.put("V_NAME", "V_REPORT_T304");
//		
//		new POIExcelUtil().expExcel2007(m,"T304机型统计.xlsx", "T304机型统计", titleName , titleIndex, service.getTableDataForExp(m));
//
//		return null;
//	}
//
//}

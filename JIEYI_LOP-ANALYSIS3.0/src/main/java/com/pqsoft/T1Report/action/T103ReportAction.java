//package com.pqsoft.T1Report.action;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//
//import org.apache.velocity.VelocityContext;
//
//import net.sf.json.JSONObject;
//
//
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
// * Title: 融资租赁信息系统平台 数据统计  T103逾期总表
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
//public class T103ReportAction extends Action {
//	private ReportBaseService service = new ReportBaseService();
//	private  Map<String,Object> m = null;
//	
//	private  String[] titlesName = new String[]{"区域",	"厂商名称",	"供应商简称",	"一期逾期项目数量",	"一期逾期设备数量","一期逾期金额",	"二期逾期项目数量",	"二期逾期设备数量","二期逾期金额",	"三期逾期项目数量",	"三期逾期设备数量","三期逾期金额",	"超三期逾期项目数量",	"超三期逾期设备数量","超三期逾期金额","在租台量","逾期台量总计","台量逾期率","剩余租金","逾期租金","租金逾期率","到期租金","到期租金逾期率"};
//	private  String COLUMN_VIEW="COLUMN3,ID,COMPANY_ID";
//	private  String COLUMN_FORM="PARAM1,SUP_TYPE,COM_TYPE";
//	private  String COLUMN_RELATION="LIKE,EQ,EQ";
//	private  int[] titleForm=new  int[]{6,9,12,15,18,19,20,21};  //需要数字格式化处理的列
//	private  int[] totalColumn=new int[]{4,5,6,7,8,9,10,11,12,13,14,15,16,17,19,20};
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T103逾期总表", "列表页面" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@Override
//	@SuppressWarnings("unchecked")
//	public Reply execute() {
//		
//		m = _getParameters();
//		VelocityContext context = new VelocityContext();
//		m.put("USER_CODE", Security.getUser().getCode());
//		m.put("REPORT_NAME", "T103Report");
//		context.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
//		
//		context.put("COLSIZE", titlesName.length);
//		List<Map<String, Object>> columnList = new ArrayList<Map<String,Object>>();
//		for (int i = 0; i < titlesName.length; i++) {
//			Map<String,Object> colMap = new HashMap<String, Object>();;
//			colMap.put("FLAG",titlesName[i]);
//			columnList.add(colMap);
//		}
//		context.put("columnList",columnList);
////		System.out.println(context.toString());
//		return new ReplyHtml(VM.html("T1Report/T103Report.vm", context));
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T103逾期总表", "列表数据" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply doTableShow(){
//		m = _getParameters();
//		m.put("USER_CODE", Security.getUser().getCode());
//
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		
//		m.put("REPORT_NAME", "T103Report");
//		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
//		System.out.println("**********COLUMN_NAMES*******"+m.get("COLUMN_NAMES"));
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
//		m.put("V_NAME", "V_REPORT_T103");
//		service.getWhereStatement(COLUMN_VIEW, COLUMN_FORM, COLUMN_RELATION, m);
////		System.out.println("数据加载前参数："+m.toString());
//		m.put("titleForm", titleForm);
//		m.put("totalColumn", totalColumn);
//		
//		
//		return new ReplyAjaxPage(service.getTablet103DataPage(m));
////		return new ReplyAjaxPage(service.getTableData(m));
//	}
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "数据统计", "T103逾期总表", "列表导出" })
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
//		m.put("REPORT_NAME", "T103Report");
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
//		m.put("V_NAME", "V_REPORT_T103");
//		
//		
//		if ("true".equals(m.get("exportAll"))) {
//			service.getWhereStatement(COLUMN_VIEW, COLUMN_FORM, COLUMN_RELATION, m);
//		} else {
//			m.put("WHERESTRING", "where id in (" + m.get("sqlData") + ")");
//		}
//		new POIExcelUtil().expExcel2007(m,"T103逾期总表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "T103逾期总表", titleName , titleIndex, service.getTableExplorT103Data(m));
//
//		return null;
//	}
//}

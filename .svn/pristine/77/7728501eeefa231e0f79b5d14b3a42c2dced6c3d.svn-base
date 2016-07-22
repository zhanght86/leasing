//package com.pqsoft.T1Report.action;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
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
//import com.pqsoft.util.ExplorTxt;
//import com.pqsoft.util.POIExcelUtil;
//
//
///**
// * <p>
// * Title: 融资租赁信息系统平台 数据统计  T101租金表查询
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
//public class T101ReportAction extends Action {
//	private ReportBaseService service = new ReportBaseService();
//	private Map<String,Object> m = null;
//	
//	private String[] titlesName = new String[]{"区域",	"供应商",	"分支机构",	"项目编号",	"客户名称",	"客户编号",	"身份证",	"起租确认日期",	"起租比例",	"租赁期限",	"规则付款",	"租赁物类型",	"厂商",	"机型",	"出厂编号",	"资金状态",	"付款方式",	"期次",	"租金核销银行",	"融资金额",	"应收日期",	"应收金额",	"本金",	"利息",	"剩余租金",	"剩余本金",	"实收日期",	"实收金额",	"核销状态",	"应收违约金","逾期天数"  ,"免除违约金额",	  "实收违约金" , "违约金核销日期"  ,"违约金状态",	"逾期原因",	"逾期说明",	"详细描述",	"开户人",	"开户银行",	"开户账户",	"租金备注" ,"违约金备注","年利率","违约金核销银行","台量","支付表编号","修改时间","扣款失败原因","终端客户","交货地点","还款计划状态","租金虚拟实收","租金虚拟核销日期","违约金虚拟实收","违约金虚拟核销日期"};
//	private  String COLUMN_VIEW="COLUMN2,COLUMN4,COLUMN12,COLUMN21,COLUMN21,COLUMN16,COLUMN13,COLUMN5,COLUMN11,COLUMN29,COLUMN8,COLUMN8,COLUMN27,COLUMN27,COLUMN17,COLUMN27,SUP_ID,COLUMN13,STATUS";
//	private  String COLUMN_FORM="PARAM1,PARAM2,PARAM3,PARAM4,PARAM5,PARAM6,PARAM7,PARAM8,PARAM9,PARAM10,PARAM11,PARAM12,PARAM13,PARAM14,PARAM15,PARAM16,SUP_TYPE,COM_NAME,STATUS";
//	private  String COLUMN_RELATION="LIKE,LIKE,EQ,EG,EL,EQ,LIKE,LIKE,EQ,T101,EG,EL,EG,EL,EQ,POINT_101,EQ,EQ,EG";
//	private String ORDER=" ORDER BY COLUMN47,COLUMN18  ";
//	private  int[] titleForm=new  int[]{20,22,23,24,25,26,28,30,32,33,44};
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T101租金表查询", "列表页面" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@Override
//	@SuppressWarnings("unchecked")
//	public Reply execute() {
//		m = _getParameters();
//		VelocityContext context = new VelocityContext();
//		m.put("USER_CODE", Security.getUser().getCode());
//		m.put("REPORT_NAME", "T101Report");
//		context.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
//		
//		List<Map<String, Object>> productTypeList = new DataDictionaryMemcached().get("产品类型");
//		context.put("productTypeList",productTypeList);
//		List<Map<String, Object>> repayTypeList = new DataDictionaryMemcached().get("还款政策");
//		context.put("repayTypeList",repayTypeList);
//		List<Map<String, Object>> projectTypeList = new SysDictionaryMemcached().get("项目状态位");
//		context.put("projectTypeList",projectTypeList);
//		List<Map<String, Object>> manufacturer=service.getManufacturer();
//		context.put("manufacturer",manufacturer );
//		List<Map<String, Object>> columnList = new ArrayList<Map<String,Object>>();
//		int colSize = titlesName.length;
//		context.put("COLSIZE", colSize);
//		for (int i = 0; i < colSize; i++) {
//			Map<String,Object> colMap = new HashMap<String, Object>();;
//			colMap.put("FLAG",titlesName[i]);
//			columnList.add(colMap);
//		}
//		context.put("columnList",columnList);
////		System.out.println(context.toString());
//		return new ReplyHtml(VM.html("T1Report/T101Report.vm", context));
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "数据统计", "T101租金表查询", "列表数据" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply doTableShow(){
//		m = _getParameters();
//		m.put("USER_CODE", Security.getUser().getCode());
//		
//
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		
//		m.put("REPORT_NAME", "T101Report");
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
//		m.put("STATUS", 20);
//		m.put("V_NAME", "FIL_REPORT_T101");
//		service.getWhereStatement(COLUMN_VIEW, COLUMN_FORM, COLUMN_RELATION, m);
//		service.getOrderStatement(ORDER,m);
//		m.put("titleForm", titleForm);
//		return new ReplyAjaxPage(service.getTableDataPage(m));
//	}
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "数据统计", "T101租金表查询", "列表导出" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public ReplyAjax exportExcel(){
//		m = _getParameters();
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		
//	
//		
//		m.putAll(json);
//		//查询导出字段
//		String[] titleName = null;
//		int[] titleIndex = null;
//		
//		m.put("USER_CODE", Security.getUser().getCode());
//		m.put("REPORT_NAME", "T101Report");
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
//		m.put("V_NAME", "FIL_REPORT_T101");
//		m.put("STATUS", 20);
//		if ("true".equals(m.get("exportAll"))) {
//			service.getWhereStatement(COLUMN_VIEW, COLUMN_FORM, COLUMN_RELATION, m);
//		} else {
//			m.put("WHERESTRING", "where id in (" + m.get("sqlData") + ")");
//		}
//		
//		int totalCount=service.getDateAmount(m);
//		System.out.println(totalCount+"@@@@@@@@@@@@@@@@@@@");
//		if (totalCount>200000)
//			return new ReplyAjax(false);
//		
//		service.getOrderStatement(ORDER,m);
//				
//		new POIExcelUtil().expExcel2007(m,"T101租金表查询"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "T101租金表查询", titleName , titleIndex, service.getTableExplorData(m));
////		new ExplorTxt().expTxt("T101租金表查询"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".txt", titleName, titleIndex, service.getTableExplorData(m));
//		return null;
//	}
//
//}

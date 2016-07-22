package com.pqsoft.superTable.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.superTable.service.ReportBaseService;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.POIExcelUtil;


/**
 * <p>
 * Title: 融资租赁信息系统平台 数据统计
 * </p>
 * <p>
 * Description: 超级表 ; 
 * </p>
 * <p>
 * Company: sflc
 * </p>
 * 
 * @author yuq
 * @version 1.0
 */
public class SuperTableHistoryAction extends Action {
	private ReportBaseService service = new ReportBaseService();
	Map<String,Object> m = null;
	
	String[] titlesName = new String[]{"区域",	"供应商",	"分支机构",	"担保公司",	"项目编号",	"业务模式",	"客户名称",	"纳税资质",	"终端客户名称",	"籍贯",	"客户编号",	"身份证号",	"联系方式",	"租赁物类型",	"厂商",	"租赁模式",	"规则付款",	"机型",	"出厂编号",	"车架号",	"台量",	"申请日期",	"信审批准日期",	"签约日期",	"验收日期",	"起租确认日期",	"租赁期限",	"租赁到期日",	"交货地点",	"放款比例",	"是否放款",	"计划放款日期",	"实际放款日期",	"租赁物购买价款",	"融资租赁额",	"首期租金",	"起租比例",	"现行利率",	"融资金额",	"1起租租金",	"2保证金",	"3第一期租金",	"4保险费",	"5手续费",	"6担保费",	"7留购价款",	"首期付款合计",	"8DB保证金",	"转DB保证金",	"9管理服务费",	"首付方式",	"租金方式",	"租金支付方式",	"计划首期付款日",	"实际首期付款日",	"租金总额",	"利息总计",	"已还租期数",	"已还租金",	"剩余租金期数",	"租金余额",	"剩余本金",	"剩余利息",	"逾期租金",	"逾期天数",	"违约金合计",	"已收违约金",	"未收违约金",	"免除违约金",	"违约金处理",	"逾期期数",	"累计逾期数",	"开户银行",	"开户账号",	"结束方式",	"状态",	"DB留购价款",	"结束时间",	"上牌方式",	"放款账号",	"放款方式",	"燃料种类",	"第三方担保",	"担保人",	"实物抵押",	"抵押物",	"放款金额",	"是否新车",	"开票方式",	"是否再融资",	"剩余客户保证金",	"剩余DB保证金",	"担保模式","立项业务模式","放款账户类型"};
	private  String COLUMN_VIEW="COLUMN2,COLUMN5,COLUMN14,COLUMN26,COLUMN26,COLUMN15,COLUMN7,COLUMN75,COLUMN17,COLUMN76,COLUMN9,COLUMN94,SUP_ID,COLUMN15,POINT_DATE";
	private  String COLUMN_FORM="PARAM1,PARAM2,PARAM3,PARAM4,PARAM5,PARAM6,PARAM7,PARAM8,PARAM9,PARAM10,PARAM11,PARAM12,SUP_TYPE,COM_NAME,point_date";
	private  String COLUMN_RELATION="LIKE,LIKE,EQ,EG,EL,EQ,LIKE,EQ,EQ,EQ,LIKE,EQ,EQ,EQ,EQ";
	private  int[] titleForm=new  int[]{34,35,36,39,40,41,42,43,44,45,46,47,48,49,50,56,57,58,59,60,61,62,63,64,65,66,67,68,69,87,91,92};
	
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "超级表" })
	@aDev(code = "170062", email = "yuq@strongflc.com", name = "于奇")
	@Override
	@SuppressWarnings("unchecked")
	public Reply execute() {
		m = _getParameters();
		VelocityContext context = new VelocityContext();
		//查询已配置的字段显示
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("REPORT_NAME", "SUPER_TABLE_HISTORY");
		context.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
		//查询条件所需数据
		List<Map<String, Object>> productTypeList = new DataDictionaryMemcached().get("产品类型");
		context.put("productTypeList",productTypeList);
		List<Map<String, Object>> repayTypeList = new DataDictionaryMemcached().get("还款政策");
		context.put("repayTypeList",repayTypeList);
		List<Map<String, Object>> projectTypeList = new SysDictionaryMemcached().get("项目状态位");
		context.put("projectTypeList",projectTypeList);
		List<Map<String, Object>> projectModle = new DataDictionaryMemcached()
		.get("业务类型");		 
		context.put("projectModle", projectModle);
		List<Map<String, Object>> manufacturer=service.getManufacturer();
		context.put("manufacturer",manufacturer );
		//列title
		List<Map<String, Object>> columnList = new ArrayList<Map<String,Object>>();
		int colSize = titlesName.length;
		context.put("COLSIZE", colSize);
		for (int i = 0; i < colSize; i++) {
			Map<String,Object> colMap = new HashMap<String, Object>();;
			colMap.put("FLAG",titlesName[i]);
			columnList.add(colMap);
		}
		context.put("columnList",columnList);
		context.put("pointDate", DateUtil.format(DateUtil.addDate(new Date(), -1), "yyyy-MM-dd"));
		System.out.println(context.get("columnList").toString());
		return new ReplyHtml(VM.html("superTable/superTableMgHistory.vm", context));
	}
	
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "超级表"})
	@aDev(code = "170062", email = "yuq@strongflc.com", name = "于奇")
	public Reply doTableShow(){
		
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		
		m.put("REPORT_NAME", "SUPER_TABLE_HISTORY");
		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if(m.get("COLUMN_NAMES") != null){
			service.delReportColumnByReportAndUser(m);
			service.insertReportColumnByReportAndUser(m);
		}
		m.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
		if(m.get("COLUMN_NAMES") == null || m.get("COLUMN_NAMES") == ""){
			m.put("COLUMN_NAMES","*");
		}else{
			m.put("COLUMN_NAMES",m.get("COLUMN_NAMES").toString()+",ID");
		}
		m.put("V_NAME", "FIL_SUPER_TABLE_HIS");
		service.getWhereStatement(COLUMN_VIEW, COLUMN_FORM, COLUMN_RELATION, m);
		System.out.println(m.toString());
		m.put("titleForm", titleForm);
		return new ReplyAjaxPage(service.getTableDataPage(m));
	}
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "数据统计", "超级表历史", "列表导出" })
	@aDev(code = "170062", email = "yuq@strongflc.com", name = "于奇")
	public ReplyAjax exportExcel(){

		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		
		m.putAll(json);
		//查询导出字段
		String[] titleName = null;
		int[] titleIndex = null;
		
		m.put("USER_CODE", Security.getUser().getCode());
		m.put("REPORT_NAME", "SUPER_TABLE_HISTORY");
		m.put("COLUMN_NAMES",service.queryReportColumnByReportAndUser(m));
		//判断是否全部导出
		if(m.get("COLUMN_NAMES") == null || m.get("COLUMN_NAMES") == ""){
			m.put("COLUMN_NAMES","*");
			titleName = titlesName;
			titleIndex = new int[titlesName.length];
			for (int i = 0; i < titlesName.length; i++) {
				titleIndex[i] = i+1;
			}
		}else{
			String[] arr = m.get("COLUMN_NAMES").toString().split(",");
			titleName = new String[arr.length];
			titleIndex = new int[arr.length];
			//数据排序
			for (int i = 0; i < arr.length; i++) {
				int j = Integer.parseInt(arr[i].replace("COLUMN", ""));
				titleIndex[i] = j;
			}
			Arrays.sort(titleIndex);
			//title排序
			for (int i = 0; i < titleIndex.length; i++) {
				titleName[i] = titlesName[titleIndex[i]-1];
			}
		}

		m.put("V_NAME", "FIL_SUPER_TABLE_HIS");
		
		
		if ("true".equals(m.get("exportAll"))) {
			service.getWhereStatement(COLUMN_VIEW, COLUMN_FORM, COLUMN_RELATION, m);
		} else {
			m.put("WHERESTRING", "where id in (" + m.get("sqlData") + ")");
		}
		
		
		new POIExcelUtil().expExcel2007(m,"超级表历史"+"_"+m.get("point_date")+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "超级表历史", titleName , titleIndex, service.getTableExplorData(m));
	
		return null;
	}

}

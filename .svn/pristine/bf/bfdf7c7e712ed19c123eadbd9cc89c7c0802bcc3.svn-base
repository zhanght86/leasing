package com.pqsoft.superTable.action;


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
import com.pqsoft.util.UtilChart;


/*******超级表报表****@auth: leeds 2014年8月7日 *************************/
public class SuperTableAction extends Action {
	private ReportBaseService service = new ReportBaseService();
	

	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "省份", "城市","分支机构","项目编号","业务类型","申请日期","批准日期","批准状态","签约日期","客户名称","客户编号","证件号码",
			"联系方式","客户类型","纳税资质","籍贯","租赁物名称","租赁物型号","厂商","供应商","付款方式","整机编号/车架号","数量","验收日期","起租日期","租赁期限","租赁到期日","交货地点",
			"抵押状态","保险状态","是否放款","预计放款日期","实际放款日期","租赁物总价","融资额","约定利率","现行利率","首期租金","首期租金比例","第一期租金","保证金","保险费","手续费","管理费",
			"留购价","首付款合计","其他费用合计","首期付款日期","租金总额","利息合计","已还期数","已还租金","剩余期数","租金余额","剩余本金","剩余利息","逾期租金","逾期天数","违约金合计","已收违约金",
			"未收违约金","减免违约金","逾期期数","累计逾期天数","累计逾期期数","开户银行","开户帐号","状态","第三方担保","担保人","开票方式" };
	private String[] sqlsName = new String[] { "PROVINCE_NAME", "CITY_NAME","SUB_COMPANY_NAME","PRO_CODE","PLATFORM_TYPE","APPLY_DATE","CONFIRM_DATE","WIND_RESULT","SIGNED_DATE","CLIENT_NAME","CLIENT_CODE","ID_CARD_NO",
			"CONTACT_WAY","CLIENT_TYPE","TAX_QUALIFICATION","HOUSR_RE_ADDRESS","PRODUCT_NAME","SPEC_NAME","COMPANY_NAME","SUPPLIERS_NAME","RENT_PAYMENT_TYPE","WHOLE_ENGINE_CODE","AMOUNT","ACCEPT_DATE","ON_HIRE_DATE","LEASE_TERM","END_DATE","DELIVER_ADDRESS",
			"MORT_STATE","INSURE_STATE","IS_LOAN","EXPECT_LOAN_DATE","REAL_LOAN_DATE","LEASE_TOPRIC","TOPRIC_SUBFIRENT","YD_INTEREST","YEAR_INTEREST","FIRSTRENT_VALUE","FIRSTRENT_RATE","FIRST_RENTMONEY","DEPOSIT_VALUE","INSURANCE_VALUE","POUNDAGE_PRICE","POUNDAGE_VALUE",
			"STAYBUY_PRICE","FIRST_MONEY_ALL","OTHER_SUM","FIRSTPAYDATE","MONEY_ALL","INTEREST_SUM","PAID_COUNT","PAID_SUM","UNPAID_COUNT","UNPAID_MONEY","UNPAID_PRINCIPAL","UNPAID_INTEREST","OVER_MONEY","OVER_DAYS","PENAL_SUM","PENAL_PAID",
			"PENAL_UNPAID","PENAL_RELIEF","OVER_PERIOD","OVER_DAYS_SUM","OVER_PERIOD_SUM","BANK_NAME","BANK_ACCOUNT","BANK_STATUS","THIRD_GUA","GUAR_NAME","INVOICEPATTERN" };
	
	//报表工具调用
	UtilChart utilChart=new UtilChart();	
	Map<String,Object> m = null;
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "超级表" })
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@Override
	public Reply execute() {
		m = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PLATFORMTYPEList",
				new SysDictionaryMemcached().get("业务类型"));
		context.put("cust_type",new DataDictionaryMemcached().get("客户类型"));
		context.put("getProvince",service.getProvince1()) ;
		//分支机构
		context.put("branches",service.getBranches()) ;
		//批准状态
		context.put("applyStatus",service.getApplyStatus()) ;
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("超级表统计", titlesName, sqlsName));
		/********************************************************************/
		return new ReplyHtml(VM.html("superTable/superTableMg.vm", context));
	}
	
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "超级表" })
	@aDev(code = "170020", email = "wujd@pqsoft.cn", name = "张路")
	public Reply doTableShow(){
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);

		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if(param.get("COLUMN_NAMES") != null){
			utilChart.delReportColumnByReportAndUser("超级表统计", Security.getUser().getCode());;
			utilChart.insertReportColumnByReportAndUser("超级表统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
		}
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES")+""));
		return new ReplyAjaxPage(service.getSuperTablePage(param));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "数据统计", "超级表", "列表导出" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public ReplyAjax exportExcel(){
		Map<String,Object> m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		//查询导出字段
		String[] titleName = null;
		String[] sql_columns = null;
		
		m.put("USER_CODE", Security.getUser().getCode());
		//判断是否全部导出
		m.put("SQL_COLUMN",utilChart.getColumns(m.get("SQL_COLUMN")+""));
		
		if(m.get("SQL_COLUMN") == null || m.get("SQL_COLUMN") == ""|| m.get("SQL_COLUMN") == "*"){
			titleName = titlesName;
			sql_columns=sqlsName;
		}else{
			String[] arr = m.get("SQL_COLUMN").toString().split(",");
			titleName = new String[arr.length];
			sql_columns=arr;
			//数据排序
			for (int i = 0; i < arr.length; i++) {
				for(int j=0;j<sqlsName.length;j++){
					System.out.println("aaaa="+arr[i]);
					if(arr[i]==sqlsName[j]){
						//titleName匹配
						titleName[i]=titlesName[j];
					}
				}
			}
		}
				
		new POIExcelUtil().expExcel_2007(m,"超级表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "超级表", titleName,sql_columns, service.addStatisticsField(m));
	
		return null;
	}


}

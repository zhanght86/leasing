package com.pqsoft.financeConvert.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.StringUtils;

public class FinanceConvertService {

	private String[] titlesName = null;
	private String finance_type = "";

	/**
	 * 用于生成批量创建视图的列名
	 * 
	 * @param
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String columnString = "";
		String columnString2 = "";
		String columnString3 = "";
		for (int i = 1; i < 15; i++) {
			// columnString+="'''' AS COLUMN"+i+",";
			columnString += "PARAM" + i + ",";
			columnString2 += "COLUMN" + ",";
			columnString3 += "LIKE" + ",";
		}
		System.out.println(columnString);
		System.out.println(columnString2);
		System.out.println(columnString3);
		String view="" ;
		
		view+="CREATE OR REPLACE VIEW VI_FINANCE_ACCOUNT_T501  AS  ###SELECT 123 AS ID,###序号,供应商,项目编号,客户名称,客户编号,租赁物类型,厂商,台量,起租确认日期,放款比例百分数,是否放款,实际放款日期,租赁物价款_含税,租赁物价款_不含税,融资租赁额,起租比例,融资金额,A1起租租金,A2保证金,A3第一期租金,A4保险费,A5手续费_含税,A5手续费_不含税,A6担保费,A7留购价款_含税,A7留购价款_不含税,首期付款合计,A8DB保证金,转DB保证金,A9管理服务费,计划首期付款日,实际首期付款日,租金总额,利息总计,已还租期数,已还租金,剩余租金期数,租金余额,剩余本金,剩余利息,逾期租金,逾期天数,违约金合计,已收违约金,未收违约金,逾期期数,累计逾期,结束方式,业务模式,状态,DB留购价款,结束时间,上牌方式,利率,备注,业务模式###FROM DUAL;&";
		view+="CREATE OR REPLACE VIEW VI_FINANCE_ACCOUNT_T502  AS  ###SELECT 123 AS ID,###序号,供应商,项目编号,客户名称,客户编号,租赁物类型,厂商,台量,起租确认日期,放款比例百分数,是否放款,实际放款日期,租赁物价款_含税,租赁物价款_不含税,融资租赁额,起租比例,融资金额,A1起租租金,A2保证金,A3第一期租金,A4保险费,A5手续费_含税,A5手续费_不含税,A6担保费,A7留购价款_含税,A7留购价款_不含税,首期付款合计,A8DB保证金,转DB保证金,A9管理服务费,计划首期付款日,实际首期付款日,租金总额,利息总计,已还租期数,已还租金,剩余租金期数,租金余额,剩余本金,剩余利息,逾期租金,逾期天数,违约金合计,已收违约金,未收违约金,逾期期数,累计逾期,结束方式,业务模式,状态,DB留购价款,结束时间,上牌方式,利率,备注###FROM DUAL;&";
		view+="CREATE OR REPLACE VIEW VI_FINANCE_ACCOUNT_T101_0101  AS  ###SELECT 123 AS ID,###序号,供应商,项目编号,客户名称,客户编号,起租确认日期,起租比例,租赁期限,规则付款,租赁物类型,厂商,款项内容,付款方式,期次,应收日期,应收金额_含税,本金_含税,利息_含税,应收金额_不含税,本金_不含税,利息_不含税,剩余租金,剩余本金,实收日期,实收金额,核销状态,资金状态,备注,业务模式###FROM DUAL;&";
		view+="CREATE OR REPLACE VIEW VI_FINANCE_ACCOUNT_T101_0102  AS  ###SELECT 123 AS ID,###序号,供应商,项目编号,客户名称,客户编号,起租确认日期,起租比例,租赁期限,规则付款,租赁物类型,厂商,款项内容,付款方式,期次,应收日期,应收金额_含税,本金_含税,利息_含税,应收金额_不含税,本金_不含税,利息_不含税,剩余租金,剩余本金,实收日期,实收金额,核销状态,资金状态,备注###FROM DUAL;&";
		view+="CREATE OR REPLACE VIEW VI_FINANCE_ACCOUNT_T101_0201  AS  ###SELECT 123 AS ID,###序号,供应商,项目编号,客户名称,客户编号,起租确认日期,起租比例,租赁期限,租赁物类型,厂商,款项内容,付款方式,期次,应收日期,应收金额_含税,应收金额_不含税,本金,利息,剩余租金,剩余本金,实收日期,实收金额_含税,实收金额_不含税,核销状态,资金状态,备注,业务模式###FROM DUAL;&";
		view+="CREATE OR REPLACE VIEW VI_FINANCE_ACCOUNT_T101_0202  AS  ###SELECT 123 AS ID,###序号,供应商,项目编号,客户名称,客户编号,起租确认日期,起租比例,租赁期限,租赁物类型,厂商,款项内容,付款方式,期次,应收日期,应收金额_含税,应收金额_不含税,本金,利息,剩余租金,剩余本金,实收日期,实收金额_含税,实收金额_不含税,核销状态,资金状态,备注###FROM DUAL;&";
		view+="CREATE OR REPLACE VIEW VI_FINANCE_ACCOUNT_T102_01  AS  ###SELECT 123 AS ID,###序号,供应商,项目编号,客户名称,客户编号,台量,租赁物类型,厂商,款项内容,起租比例,期次,应收日期,应收金额_含税,本金_含税,利息_含税,应收金额_不含税,本金_不含税,利息_不含税,实收日期,实收金额,核销状态,开户银行,核销银行,逾期天数,逾期原因,逾期说明,详细描述,业务模式###FROM DUAL;&";
		view+="CREATE OR REPLACE VIEW VI_FINANCE_ACCOUNT_T102_02  AS  ###SELECT 123 AS ID,###序号,供应商,项目编号,客户名称,客户编号,台量,租赁物类型,厂商,款项内容,起租比例,期次,应收日期,应收金额_含税,本金_含税,利息_含税,应收金额_不含税,本金_不含税,利息_不含税,实收日期,实收金额,核销状态,开户银行,核销银行,逾期天数,逾期原因,逾期说明,详细描述###FROM DUAL;";

		
        String[] viewStrings=view.split("&");
        for (int i = 0; i < viewStrings.length; i++) {
			String[]views=viewStrings[i].split("###");
        	for (int j = 0; j < views.length; j++) {
        		if(j==2){
        			String[] columns = views[j].split(",");
        			for (int z = 1; z <= columns.length; z++) {
        				String string = columns[z - 1];
        				if(z==columns.length){
        					System.out.println("'' AS COLUMN" + z + "  --" + string);
            				
        				}else{
        					System.out.println("'' AS COLUMN" + z + ",  --" + string);
            				
        				}
        				
        			}
        		}else{
				    System.out.println(views[j]);
        		}
			}
        	
		}
        
        
//		String column = "序号,供应商,项目编号,客户名称,客户编号,租赁物类型,厂商,台量,起租确认日期,放款比例百分数,是否放款,实际放款日期,租赁物价款_含税,租赁物价款_不含税,融资租赁额,起租比例,融资金额,A1起租租金,A2保证金,A3第一期租金,A4保险费,A5手续费_含税,A5手续费_不含税,A6担保费,A7留购价款_含税,A7留购价款_不含税,首期付款合计,A8DB保证金,转DB保证金,A9管理服务费,计划首期付款日,实际首期付款日,租金总额,利息总计,已还租期数,已还租金,剩余租金期数,租金余额,剩余本金,剩余利息,逾期租金,逾期天数,违约金合计,已收违约金,未收违约金,逾期期数,累计逾期,结束方式,业务模式,状态,DB留购价款,结束时间,上牌方式,利率,备注";
//		String[] columns = column.split(",");
//		
//		for (int i = 1; i <= columns.length; i++) {
//			String string = columns[i - 1];
//			System.out.println("'' as COLUMN" + i + ",  --" + string);
//		}

	}

	/**
	 * 查询财务接口查询界面列表数据以及基本配置数据
	 * 
	 * @param m
	 *            REPORT_NAME：报表名称， USER_CODE：用户编号
	 * @author: 于奇
	 * @date: 2013-11-02 21:06
	 */
	@SuppressWarnings("unchecked")
	public void getBaseQueryDate(Map<String, Object> m, VelocityContext context) {
		// TODO Auto-generated method stub
		String FINANCE_ID = (String) m.get("FINANCE_ID");
		m.put("REPORT_NAME", "vi_finanace_convert_" + FINANCE_ID);
		// System.out.println("vi_finanace_convert_"+FINANCE_ID);
		// 查询该用户下该报名要显示的字段
		context.put("COLUMN_NAMES", Dao.selectOne(
				"financeConvert.queryReportColumnByReportAndUser", m));

		// System.out.println("##################"+context.get("COLUMN_NAMES"));
		// 查询条件所需数据
		List<Map<String, Object>> statusList = new ArrayList<Map<String, Object>>();
		String[] flagsString = { "未导出", "已导出", "全显示" };
		for (int i = 0; i < flagsString.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (i == 2) {
				map.put("CODE", "");
			} else {
				map.put("CODE", i);
			}
			map.put("FLAG", flagsString[i]);
			statusList.add(map);
		}
		// 导出状态
		context.put("statusList", statusList);
		context.put("manufacturer", Dao
				.selectList("financeConvert.queryTSysCompany"));
		// 业务类型 直租 回租
		List<Map<String, Object>> projectType = new DataDictionaryMemcached()
				.get("业务类型");
		context.put("projectType", projectType);
		// 业务模式 新业务模式
		List<Map<String, Object>> projectModle = new DataDictionaryMemcached()
				.get("立项业务模式_财务");
		context.put("projectModle", projectModle);
		// 付款方式
		List<Map<String, Object>> PAY_TYPE =new ArrayList<Map<String,Object>>();
		String[] pay_typeString={"网银","非网银"};
		for (int i = 0; i < pay_typeString.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CODE", i+1);
			map.put("FLAG", pay_typeString[i]);
			PAY_TYPE.add(map);
		}
		
		context.put("PAY_TYPE", PAY_TYPE);
		// 列title queryFinanceColumnByFinance_id

		setTitlesName(m);

		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();

		int colSize = titlesName.length;
		// System.out.println("colSize...............................:"+colSize);
		context.put("COLSIZE", colSize);
		for (int i = 0; i < titlesName.length; i++) {
			Map<String, Object> colMap = new HashMap<String, Object>();
			
			colMap.put("FLAG", titlesName[i]);
			columnList.add(colMap);
		}
		m.remove("FINANCE_ID");
		context.put("FINANCE_ID", FINANCE_ID);
		// System.out.println(FINANCE_ID);
		context.put("columnList", columnList);
		System.out.println("财务接口页面加载时页面传的参数：" + context.toString());
	}

	/**
	 * 给标题列数组赋值
	 * 
	 * @author: 于奇
	 */
	private void setTitlesName(Map<String, Object> m) {
		Map<String, Object> map = Dao.selectOne(
				"financeConvert.queryFinanceColumnByFinance_id", m);
		String columnString = (String) map.get("FINANCE_COLUMN");

		titlesName = columnString.split(",");
		finance_type = (String) map.get("FINANCE_NAME");
	}

	/**
	 * 查询报表的分页数据
	 * 
	 * @param params
	 * @author: 于奇
	 * @return Page
	 */
	public Page getTableData(Map<String, Object> params) {
		getWhereStatement(params);

		Page page = new Page(params);
//		System.out.println("查询分页数据之前的参数" + params.toString());
		if (params.get("FINANCE_ID").toString().startsWith("08")) {
			JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
					"financeConvert.queryTableData08", params));
			page.addDate(jsonArray, Dao.selectInt(
					"financeConvert.queryTableCount08", params));
		} else {
			JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
					"financeConvert.queryTableData", params));
			page.addDate(jsonArray, Dao.selectInt(
					"financeConvert.queryTableCount", params));
		}
		return page;
	}

	/**
	 * 获得where查询条件
	 * 
	 * @param params
	 */
	private void getWhereStatement(Map<String, Object> params) {
		Map<String, Object> m = Dao.selectOne(
				"financeConvert.T_FINANCE_CONVERT_QUERY", params);
		String COLUMN_VIEW = (String) m.get("COLUMN_VIEW");
		String COLUMN_FORM = (String) m.get("COLUMN_FORM");
		String COLUMN_RELATION = (String) m.get("COLUMN_RELATION");
		String[] column_views = COLUMN_VIEW.split(",");
		String[] column_forms = COLUMN_FORM.split(",");
		String[] column_relations = COLUMN_RELATION.split(",");
		String wheresString = "";

		for (int i = 0; i < column_forms.length; i++) {
			if (StringUtils.isNotBlank(params.get(column_forms[i]))) {
				wheresString += getWhereOpinion(column_views[i],
						params.get(column_forms[i]).toString().trim(),
						column_relations[i]);
			}
		}
		if (StringUtils.isNotBlank(wheresString)) {

			wheresString = " where "
					+ wheresString.substring(0, wheresString.length() - 3);
		}

		params.put("WHERESTRING", wheresString);

	}

	/**
	 * 获得where 语句 LIKE , EQ "=", EG ">=", EL "<=" POINT09 "节点日期需要特殊处理财务接口09"
	 * POINT22 "财务接口22节点日期" POINT08 财务接口 0801 和0802
	 * 
	 * @param view
	 *            视图值
	 * @param form
	 *            表单查询值
	 * @param relation
	 *            关系值
	 * @return
	 */
	private String getWhereOpinion(String view, String form, String relation) {
		String where = "  ";
		if ("LIKE".equals(relation)) {
			where += view + "  like  '%" + form + "%'  and";
		} else if ("EQ".equals(relation)) {
			where += view + " = '" + form + "'  and";
		} else if ("EG".equals(relation)) {
			where += view + " >= '" + form + "'  and";
		} else if ("EL".equals(relation)) {
			where += view + " <= '" + form + "'  and";
		} else if ("POINT09".equals(relation)) {
			// view 视图字段存的是首付款核销日期 小于等于节点日期 form
			where += view + " <= '" + form + "'  and";
			where += " (START_CONFIRM_DATE > '" + form + "' or status<20)  and";
		} else if ("POINT22".equals(relation)) {
			// view 视图字段存的是起租确认日 小于等于节点日期 form
			where += view + " <= '" + form + "'  and";
			where += " NVL(CREATE_DATE,'" + form + "')>='" + form + "'  and";
		} else if ("POINT08".equals(relation)) {
			// view 视图字段存的是应收日期 小于等于 节点日期 form
			where += view + " <= '" + form + "'  and";
//			where += " PAYLIST_CODE IN ( SELECT  F.PAY_CODE  FROM FI_OVERDUE F  ";
//			where += " WHERE (F.RENT_RECE-F.RENT_PAID)>0 AND TO_CHAR(F.CREATE_DATE,'YYYY-MM-DD')= '"
//					+ form + "' ";
//			where += " GROUP BY F.PAY_CODE HAVING COUNT(F.ID)>2 )  and";
		}
		return where;
	}

	/**
	 * 删除该用户存储的报表查询显示列
	 * 
	 * @param m
	 *            用户ID 报表名 等参数
	 * @author: 于奇
	 */
	public void delReportColumnByReportAndUser(Map<String, Object> m) {
		// TODO Auto-generated method stub
		Dao.delete("financeConvert.delReportColumnByReportAndUser", m);
	}

	/**
	 *插入该用户存储的报表查询显示列
	 * 
	 * @param m
	 *            用户ID 报表名 等参数
	 * @author: 于奇
	 */

	public void insertReportColumnByReportAndUser(Map<String, Object> m) {

		Dao.delete("financeConvert.insertReportColumnByReportAndUser", m);
	}

	/**
	 *查询该用户存储的报表查询显示列
	 * 
	 * @param m
	 *            用户ID 报表名 等参数
	 * @author: 于奇
	 */

	public Object queryReportColumnByReportAndUser(Map<String, Object> m) {

		// System.out.println(Dao.selectOne("financeConvert.queryReportColumnByReportAndUser",m));
		return Dao.selectOne("financeConvert.queryReportColumnByReportAndUser",
				m);
	}

	/**
	 * 导出Excle
	 * 
	 * @param m
	 *            页面传递参数
	 * @return Excel
	 * @author: 于奇
	 */
	public Excel getExportApplyExcel(Map<String, Object> m) {
		setTitlesName(m);
		String COLUMN_NAMES = "";
		String V_NAME = "vi_finanace_convert_" + (String) m.get("FINANCE_ID");
		// 表头
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		for (int i = 1; i <= titlesName.length; i++) {
			title.put("COLUMN" + i, titlesName[i - 1]);
			// System.out.println("COLUMN"+i+" : "+ titlesName[i-1]);
			COLUMN_NAMES += " COLUMN" + i + ",";

		}

		// System.out.println("COLUMN_NAMES###33:"+COLUMN_NAMES);
		COLUMN_NAMES = COLUMN_NAMES.substring(0, COLUMN_NAMES.length() - 1);
		m.remove("COLUMN_NAMES");
		m.put("COLUMN_NAMES", COLUMN_NAMES);
		m.put("V_NAME", V_NAME);
		if ("true".equals(m.get("exportAll"))) {
			getWhereStatement(m);
		} else {
			m.put("WHERESTRING", "where id in (" + m.get("sqlData") + ")");
		}
		// head标题名
         
		// 数据
		List<Map<String, Object>> dataList = null;
		
		
		if (m.get("FINANCE_ID").toString().startsWith("08")) {
			dataList = Dao.selectList("financeConvert.queryTableExplorData08", m);
		}else{
			dataList = Dao.selectList("financeConvert.queryTableExplorData", m);
		}
		
		
		// 封装excel
		Excel excel = new Excel();
		excel.addData(dataList);
		excel.addTitle(title);
		excel.setHeadDate(true);
		excel.setHeadTitles(finance_type);

		// 处理数据是否导出
		String WHERESTRING = (String) m.get("WHERESTRING");

		// System.out.println("******替换前******"+WHERESTRING.indexOf("EXPORTSIZE = '1'"));

		if (WHERESTRING.indexOf("EXPORTSIZE = '1'") > -1) {

			WHERESTRING = WHERESTRING.replaceAll("EXPORTSIZE = '1'",
					"EXPORTSIZE = '0'");

		}
		if (WHERESTRING.indexOf("EXPORTSIZE = '0'") == -1) {
			if (StringUtils.isNotBlank(WHERESTRING)) {
				WHERESTRING = WHERESTRING + " and EXPORTSIZE = '0' ";
			} else {
				WHERESTRING = " WHERE EXPORTSIZE = '0' ";
			}
		}
		m.put("WHERESTRING", WHERESTRING);
		// proj_id, convert_type, item_id
		String COLUMN = " proj_id," + "'FINACE_" + m.get("FINANCE_ID")
				+ "',ID ";
		m.put("COLUMN", COLUMN);
//		System.out.println("导出EXCLE前参数查看：" + m.toString());
		if (m.get("FINANCE_ID").toString().startsWith("08")) {
			Dao.insert("financeConvert.insertT_finance_convert_explor08", m);
		}else{
		    Dao.insert("financeConvert.insertT_finance_convert_explor", m);
	    }

		return excel;

	}

}

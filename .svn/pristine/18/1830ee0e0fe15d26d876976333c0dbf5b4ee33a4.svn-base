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

public class FinanceAccountService {

	private String[] titlesName = null;
	private String finance_type = "";

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
		m.put("REPORT_NAME", "VI_FINANCE_ACCOUNT_" + FINANCE_ID);
		// System.out.println("vi_finanace_convert_"+FINANCE_ID);
		// 查询该用户下该报名要显示的字段
		context.put("COLUMN_NAMES", Dao.selectOne(
				"financeConvert.queryReportColumnByReportAndUser", m));

		// System.out.println("##################"+context.get("COLUMN_NAMES"));
		// 查询条件所需数据
		List<Map<String, Object>> productTypeList = new DataDictionaryMemcached()
				.get("产品类型");
		context.put("productTypeList", productTypeList);
		List<Map<String, Object>> repayTypeList = new DataDictionaryMemcached()
				.get("还款政策");
		context.put("repayTypeList", repayTypeList);
		List<Map<String, Object>> projectTypeList = new DataDictionaryMemcached()
				.get("项目状态位");
		context.put("projectTypeList", projectTypeList);

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
		List<Map<String, Object>> PAY_TYPE = new DataDictionaryMemcached()
				.get("付款方式");
		context.put("PAY_TYPE", PAY_TYPE);
		// 列title queryFinanceColumnByFinance_id

		setTitlesName(m);

		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();

		int colSize = titlesName.length;
		// System.out.println("colSize...............................:"+colSize);
		context.put("COLSIZE", colSize);
		for (int i = 0; i < titlesName.length; i++) {
			Map<String, Object> colMap = new HashMap<String, Object>();
			;
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
	 * 查询报表的分页数据
	 * 
	 * @param params
	 * @author: 于奇
	 * @return Page
	 */

	public Page getTableData(Map<String, Object> params) {
		getWhereStatement(params);

		Page page = new Page(params);
		System.out.println("查询分页数据之前的参数" + params.toString());

		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
				"financeConvert.queryTableData", params));
		page.addDate(jsonArray, Dao.selectInt("financeConvert.queryTableCount",
				params));

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
						(String) params.get(column_forms[i]),
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
	 * 获得where 语句 LIKE , EQ "=", EG ">=", EL "<="
	 *  T102POINT
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
		}else if("T102POINT".equals(relation)){
			where += "NVL(" + view + ",TO_CHAR(TO_DATE('"+form+"','YYYY-MM-DD')+1,'YYYY-MM-DD'))" + " > '" + form
			+ "'  and";
		}
		return where;
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
		String V_NAME = "VI_FINANCE_ACCOUNT_" + (String) m.get("FINANCE_ID");
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

		// 数据
		List<Map<String, Object>> dataList = null;

		dataList = Dao.selectList("financeConvert.queryTableExplorData", m);

		// 封装excel
		Excel excel = new Excel();
		excel.addData(dataList);
		excel.addTitle(title);
		System.out.println("导出EXCLE前参数查看：" + m.toString());

		return excel;
	}

}

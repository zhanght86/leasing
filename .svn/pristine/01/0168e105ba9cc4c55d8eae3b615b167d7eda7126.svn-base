package com.pqsoft.superTable.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.map.HashedMap;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class ReportBaseService {

	public static void main(String[] args) {

		NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
		DecimalFormat df1 = new DecimalFormat("#,###.00");

		System.out.println(df1.format(334444456.3));
		System.out.println(df1.format(""));

	}

	/**
	 * 获得ORDER查询条件
	 * 
	 * @author: 于奇
	 * @param params
	 */
	public void getOrderStatement(String order, Map<String, Object> params) {

		if (StringUtils.isBlank(order))
			return;
		String whereString = params.get("WHERESTRING").toString();
		if (StringUtils.isBlank(whereString)) {
			params.put("WHERESTRING", order);
		} else {
			params.put("WHERESTRING", whereString + "  " + order);
		}

	}

	/**
	 * 获得where查询条件
	 * 
	 * @author: 于奇
	 * @param params
	 */
	@SuppressWarnings("unchecked")
	public void getWhereStatement(String COLUMN_VIEW, String COLUMN_FORM,
			String COLUMN_RELATION, Map<String, Object> params) {

		// 数据权限 判断供应商登录只能看到当前供应商数据 生产商看到下属供应商数据
		ManageService mgService = new ManageService();
		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
		Map comMap = (Map) mgService.getCompanyByUserId(Security.getUser()
				.getId());

		if (supMap != null && supMap.get("SUP_ID") != null) {
			params.put("SUP_TYPE", supMap.get("SUP_ID"));
		}
		if (comMap != null && comMap.get("COMPANY_ID") != null) {
//			System.out.println(comMap.get("COMPANY_ID")
//					+ "**********************");
			params.put("COM_TYPE", comMap.get("COMPANY_ID"));
		}
		if (comMap != null && comMap.get("COMPANY_NAME") != null) {
			params.put("COM_NAME", comMap.get("COMPANY_NAME"));
		}

		// System.out.println(params.toString());

		String[] column_views = COLUMN_VIEW.split(",");
		String[] column_forms = COLUMN_FORM.split(",");
		String[] column_relations = COLUMN_RELATION.split(",");
		String wheresString = "";

		System.out.println("column_views:" + column_views.length
				+ "   column_forms:" + column_forms.length
				+ "   column_relations:" + column_relations.length);

		for (int i = 0; i < column_forms.length; i++) {
			if (StringUtils.isNotBlank(params.get(column_forms[i]))) {
				wheresString += getWhereOpinion(column_views[i], params.get(
						column_forms[i]).toString().trim(), column_relations[i]);
			}
		}
		if (StringUtils.isNotBlank(wheresString)) {

			wheresString = " where "
					+ wheresString.substring(0, wheresString.length() - 3);
		}

		params.put("WHERESTRING", wheresString);

	}

	/**
	 * 获得where 语句 LIKE , EQ "=", EG ">=", EL "<=" POINT_101 节点日期 特殊处理标示 T103
	 * 
	 * @author: 于奇
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
		} else if ("POINT_101".equals(relation)) {
			where += "NVL(" + view + ",TO_CHAR(TO_DATE('"+form+"','YYYY-MM-DD')+1,'YYYY-MM-DD'))" + " > '" + form
					+ "'  and";
		} else if ("T103".equals(relation)) {
			if (Integer.parseInt(form) < 4) {
				where += view + " = " + form + "  and";
			} else {
				where += view + " > 3  and";
			}

		} else if ("T101".equals(relation)) {
			/*
			 * <option value="0">租金核销违约金未核销</option> <option
			 * value="1">租金或违约金未核销</option> <option value="2">租金已核销</option>
			 * <option value="3">违约金已核销</option> <option
			 * value="4">租金未核销</option> <option value="5">违约金未核销</option>
			 */
			if ("0".equals(form)) {
				where += "COLUMN29 = '已核销'  and  COLUMN35 = '未核销'  and";
			} else if ("1".equals(form)) {
				where += "(COLUMN29 = '未核销'  or  COLUMN35 = '未核销')  and";
			} else if ("2".equals(form)) {
				where += view + " = '已核销'  and";
			} else if ("3".equals(form)) {
				where += "COLUMN35 = '已核销'  and";
			} else if ("4".equals(form)) {
				where += view + " = '未核销'  and";
			} else if ("5".equals(form)) {
				where += "COLUMN35 = '未核销'  and";
			}
		}
		return where;
	}
	
	
	
	
	
	public List<Map<String, Object>> getTableExplorT103Data(
			Map<String, Object> params) {
		
		return Dao.selectList(
				"reportBase.queryT103TableExplorData", params);
	}
	/**
	 * 查询报表t103分页数据
	 * 
	 * @param params
	 * @return
	 * @author: 于奇
	 * @date: 2013-12-15 上午10:44:14
	 */
	public Page getTablet103DataPage(Map<String, Object> params) {
		Page page = new Page(params);
		List<Map<String, Object>> dateList = Dao.selectList(
				"reportBase.queryT103TableData", params);
		List<Map<String, Object>> dateListFormat = new ArrayList<Map<String, Object>>();

		if (StringUtils.isBlank(params.get("SUP_TYPE"))) {
			if (StringUtils.isNotBlank(params.get("totalColumn"))) {
				int[] totalColumn = (int[]) params.get("totalColumn");
				dateList = getTotalDate(dateList, totalColumn);
//				System.out.println(dateList.toString());
			}
		}
		if (StringUtils.isNotBlank(params.get("titleForm"))) {
			int[] titleForm = (int[]) params.get("titleForm");
			// System.out.println(titleForm.toString());
			dateListFormat = doFormatNumber(dateList, titleForm);
		} else {
			dateListFormat = dateList;
		}
		JSONArray jsonArray = JSONArray.fromObject(dateListFormat);

		page.addDate(jsonArray, Dao.selectInt(
				"reportBase.queryT103TableDataCountTotal", params));
		return page;
	}

	
	
	/**
	 * 查询报表分页数据
	 * 
	 * @param params
	 * @return
	 * @author: 于奇
	 * @date: 2013-11-30 上午10:44:14
	 */
	public Page getTableDataPage(Map<String, Object> params) {
		Page page = new Page(params);
		List<Map<String, Object>> dateList = Dao.selectList(
				"reportBase.queryTableDataPage", params);
		List<Map<String, Object>> dateListFormat = new ArrayList<Map<String, Object>>();

		if (StringUtils.isBlank(params.get("SUP_TYPE"))) {
			if (StringUtils.isNotBlank(params.get("totalColumn"))) {
				int[] totalColumn = (int[]) params.get("totalColumn");
				dateList = getTotalDate(dateList, totalColumn);
//				System.out.println(dateList.toString());
			}
		}
		if (StringUtils.isNotBlank(params.get("titleForm"))) {
			int[] titleForm = (int[]) params.get("titleForm");
			// System.out.println(titleForm.toString());
			dateListFormat = doFormatNumber(dateList, titleForm);
		} else {
			dateListFormat = dateList;
		}
		JSONArray jsonArray = JSONArray.fromObject(dateListFormat);

		page.addDate(jsonArray, Dao.selectInt(
				"reportBase.queryTableCountTotal", params));
		Dao.commit();
		Dao.close();
		return page;
	}
	
	/**
	 * 查询报表分页数据
	 * 
	 * @param params
	 * @return
	 * @author: WuYanFei
	 * @date: 
	 */
	public Page getSuperTablePage(Map<String, Object> params) {
		Page page = PageUtil.getPage(params, "reportBase.querySuperTableAll", "reportBase.querySuperTableCount");
		//添加合计行
		String[] totalColumns =  {"LEASE_TOPRIC","TOPRIC_SUBFIRENT","DEPOSIT_VALUE","INSURANCE_VALUE","POUNDAGE_PRICE"
				,"POUNDAGE_VALUE","STAYBUY_PRICE","FIRST_MONEY_ALL","OTHER_SUM","MONEY_ALL","INTEREST_SUM"
				,"PAID_SUM","UNPAID_MONEY","UNPAID_PRINCIPAL","UNPAID_INTEREST","OVER_MONEY","PENAL_SUM","PENAL_PAID"
				,"PENAL_UNPAID","PENAL_RELIEF"};
		List<Map> datas = page.getData();
		String totalField = "PROVINCE_NAME";
		datas = this.getTotalData(datas, totalColumns, totalField);
		Page page1 = new Page();
		page1.addDate(JSONArray.fromObject(datas), page.getTotal());
		return page1;
	}

	/**
	 * TODO
	 *@requestFunction
	 * 对指定的字段求和，生成合计行，添加到数据列表中
	 * 
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年8月7日 下午4:24:28
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private List<Map> getTotalData(
			List<Map> datas, String[] totalColumns, String totalField) {
		Map total = new HashMap();
		total.put(totalField, "合计");
		for(String totalColumn : totalColumns) {
			BigDecimal col = new BigDecimal(0);
			for(Map data : datas) {
				BigDecimal col0 = (BigDecimal)(data.get(totalColumn)==null ? new BigDecimal(0) : data.get(totalColumn));
				col = col.add(col0);
			}
			total.put(totalColumn, new DecimalFormat("0.00").format(col.doubleValue()));
		}
		datas.add(total);
		return datas;
	}
	
	/**
	 * 对页面数据汇总求和
	 * 
	 * @param dateList
	 * @param totalColumn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getTotalDate(
			List<Map<String, Object>> dateList, int[] totalColumn) {
		List<Map<String, Object>> totalList = dateList;
		Map<String, Object> totaldateMap = new HashedMap();
		for (int i = 0; i < dateList.size(); i++) {
			Map<String, Object> rowMap = (Map<String, Object>) dateList.get(i);

			for (int j = 0; j < totalColumn.length; j++) {
				Object value = rowMap.get("COLUMN" + (totalColumn[j])) == null ? ""
						: rowMap.get("COLUMN" + (totalColumn[j]));
				if (value instanceof java.math.BigDecimal) {
					Object valueT = totaldateMap.get("COLUMN"
							+ (totalColumn[j])) == null ? 0 : totaldateMap
							.get("COLUMN" + (totalColumn[j]));
					totaldateMap.put("COLUMN" + (totalColumn[j]), Double
							.parseDouble(valueT.toString())
							+ ((java.math.BigDecimal) value).doubleValue());
				}
			}
		}
		totaldateMap.put("COLUMN1", "页面汇总");

		totalList.add(totaldateMap);
		return totalList;
	}

	/**
	 * 格式化数据显示
	 * 
	 * @param dateList
	 */

	private List<Map<String, Object>> doFormatNumber(
			List<Map<String, Object>> dateList, int[] titleForm) {

		// NumberFormat nf=NumberFormat.getInstance(Locale.CHINA);
		List<Map<String, Object>> formatList = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("#,##0.00");
		for (int i = 0; i < dateList.size(); i++) {
			Map<String, Object> rowMap = (Map<String, Object>) dateList.get(i);
			// System.out.println("行数据"+i+"     "+rowMap.toString());
			for (int j = 0; j < titleForm.length; j++) {
				Object value = rowMap.get("COLUMN" + (titleForm[j])) == null ? ""
						: rowMap.get("COLUMN" + (titleForm[j]));

				if (value instanceof java.math.BigDecimal) {
					// rowMap.remove("COLUMN" + (titleForm[j]));
					// System.out.println(df.format(value));
					rowMap.put("COLUMN" + (titleForm[j]), df.format(value));
				} else if (value instanceof Double) {
					rowMap.put("COLUMN" + (titleForm[j]), df.format(value));
				}
			}
			// System.out.println("处理完的 数据："+rowMap.toString());
			formatList.add(rowMap);
		}
		return formatList;
	}
/**
 * 对查询数据条数求和
 * @param m
 * @return
 */
	public int getDateAmount(Map<String, Object> m) {
		
		return Dao.selectInt("reportBase.queryTableCountTotal", m);
	}

	/**
	 * 快速查询T1041 数据总条数分页用
	 * 
	 * @param params
	 * @return
	 */
	public Page getTableT1041DataPage(Map<String, Object> params) {
		Page page = new Page(params);
		List<Map<String, Object>> dateList = Dao.selectList(
				"reportBase.queryTableDataPage", params);
		List<Map<String, Object>> dateListFormat = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(params.get("titleForm"))) {
			int[] titleForm = (int[]) params.get("titleForm");
			// System.out.println(titleForm.toString());
			dateListFormat = doFormatNumber(dateList, titleForm);
		} else {
			dateListFormat = dateList;
		}
		JSONArray jsonArray = JSONArray.fromObject(dateListFormat);

		page.addDate(jsonArray, Dao.selectInt(
				"reportBase.queryTableT1041CountTotal", params));
		return page;
	}

	public Page getTableT1043DataPage(Map<String, Object> params) {
		Page page = new Page(params);
		List<Map<String, Object>> dateList = Dao.selectList(
				"reportBase.queryTableDataPage", params);
		List<Map<String, Object>> dateListFormat = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(params.get("titleForm"))) {
			int[] titleForm = (int[]) params.get("titleForm");
			// System.out.println(titleForm.toString());
			dateListFormat = doFormatNumber(dateList, titleForm);
		} else {
			dateListFormat = dateList;
		}
		JSONArray jsonArray = JSONArray.fromObject(dateListFormat);

		page.addDate(jsonArray, Dao.selectInt(
				"reportBase.queryTableT1043CountTotal", params));
		return page;
	}

	/**
	 * 查询导出Excle的数据
	 * 
	 * @author yuq
	 * @date: 2013-11-30
	 */
	public List<Map<String, Object>> getTableExplorData(
			Map<String, Object> params) {
		List<Map<String, Object>> list = Dao.selectList(
				"reportBase.queryTableExplorData", params);
		Dao.commit();
		Dao.close();
		return list;
	}

	/**
	 * 查询厂商的数据
	 * 
	 * @author yuq
	 * @date: 2013-11-30
	 */
	public List<Map<String, Object>> getManufacturer() {
		// TODO Auto-generated method stub
		return Dao.selectList("reportBase.queryTSysCompany");
	}

	/**
	 * T1042son 数据
	 * 
	 * @param params
	 * @return
	 * @author: 于奇
	 * @date: 2013-11-17 上午10:44:14
	 */
	public List<Map<String, Object>> getT1042SONTableDataForExp(
			Map<String, Object> params) {
		return Dao.selectList("reportBase.queryT1042SONTableData", params);
	}

	public Page getT1042SONTableData(Map<String, Object> params) {
		Page page = new Page(params);
		List<Map<String, Object>> dateList = Dao.selectList(
				"reportBase.queryT1042SONTableData", params);
		List<Map<String, Object>> dateListFormat = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(params.get("titleForm"))) {
			int[] titleForm = (int[]) params.get("titleForm");
			// System.out.println(titleForm.toString());
			dateListFormat = doFormatNumber(dateList, titleForm);
		} else {
			dateListFormat = dateList;
		}
		JSONArray jsonArray = JSONArray.fromObject(dateListFormat);

		page.addDate(jsonArray, Dao.selectInt(
				"reportBase.queryT1042SONTableCount", params));
		return page;
	}

	// ************以下******************************************************************//

	/**
	 * 查询列表数据
	 * 
	 * @param params
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-10-13 上午10:44:14
	 */
	public Page getTableData(Map<String, Object> params) {
		Page page = new Page(params);
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
				"reportBase.queryTableData", params));
		page.addDate(jsonArray, Dao.selectInt("reportBase.queryTableCount",
				params));
		Dao.commit();
		Dao.close();
		return page;
	}

	/**
	 * 查询导出数据
	 * 
	 * @param params
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-10-13 上午10:44:14
	 */
	public List<Map<String, Object>> getTableDataForExp(
			Map<String, Object> params) {
		List<Map<String, Object>> dateList = Dao.selectList("reportBase.queryTableData", params);
		Dao.commit();
		Dao.close();
		return dateList;
	}

	// ************以上***************************************************************//

	/**
	 * 删除报表自定义字段显示配置
	 * 
	 * @param m
	 *            REPORT_NAME：报表名称， USER_CODE：用户编号
	 * @author: 吴剑东
	 * @date: 2013-10-21 上午10:46:05
	 */
	public void delReportColumnByReportAndUser(Map<String, Object> m) {
		Dao.delete("reportBase.delReportColumnByReportAndUser", m);
		Dao.commit();
		Dao.close();
	}

	/**
	 * 新增报表自定义字段显示配置
	 * 
	 * @param m
	 *            REPORT_NAME：报表名称， USER_CODE：用户编号 ，COLUMN_NAMES：字段
	 * @author: 吴剑东
	 * @date: 2013-10-21 上午10:46:05
	 */
	public void insertReportColumnByReportAndUser(Map<String, Object> m) {
		Dao.delete("reportBase.insertReportColumnByReportAndUser", m);
		Dao.commit();
		Dao.close();
	}

	/**
	 * 查询报表自定义字段显示配置
	 * 
	 * @param m
	 *            REPORT_NAME：报表名称， USER_CODE：用户编号
	 * @author: 吴剑东
	 * @date: 2013-10-21 上午10:46:05
	 */
	public Object queryReportColumnByReportAndUser(Map<String, Object> m) {
		return Dao.selectOne("reportBase.queryReportColumnByReportAndUser", m);
	}

	/**
	 * 根据厂商获取系列
	 * 
	 * @param m
	 *            REPORT_NAME：报表名称， USER_CODE：用户编号
	 * @author: 吴剑东
	 * @date: 2013-10-21 上午10:46:05
	 */
	public List<Map<String, Object>> getCatentNameByComId(Map<String, Object> m) {
		return Dao.selectList("reportBase.getCatentNameByComId", m);
	}

	// /**
	// * T101 数据
	// * @param params
	// * @return
	// * @author: 吴剑东
	// * @date: 2013-10-13 上午10:44:14
	// */
	// public Page getT101TableData(Map<String, Object> params) {
	// Page page = new Page(params);
	// JSONArray jsonArray =
	// JSONArray.fromObject(Dao.selectList("reportBase.queryT101TableData",params));
	// page.addDate(jsonArray,
	// Dao.selectInt("reportBase.queryT101TableCount",params));
	// return page;
	// }
	// public List<Map<String,Object>> getT101TableDataForExp(Map<String,
	// Object> params) {
	// return Dao.selectList("reportBase.queryT101TableData",params);
	// }
	//	

	/**
	 * T102 数据
	 * 
	 * @param params
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-10-13 上午10:44:14
	 */
	public Page getT102TableData(Map<String, Object> params) {
		Page page = new Page(params);
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
				"reportBase.queryT102TableData", params));
		page.addDate(jsonArray, Dao.selectInt("reportBase.queryT102TableCount",
				params));
		return page;
	}

	public List<Map<String, Object>> getT102TableDataForExp(
			Map<String, Object> params) {
		return Dao.selectList("reportBase.queryT102TableData", params);
	}

	/**
	 * T404son 数据
	 * 
	 * @param params
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-10-13 上午10:44:14
	 */
	public Page getT404SONTableData(Map<String, Object> params) {
		Page page = new Page(params);
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
				"reportBase.queryT404SONTableData", params));
		page.addDate(jsonArray, Dao.selectInt(
				"reportBase.queryT404SONTableCount", params));
		return page;
	}

	public List<Map<String, Object>> getT404SONTableDataForExp(
			Map<String, Object> params) {
		return Dao.selectList("reportBase.queryT404SONTableData", params);
	}

	//
	// /**
	// * 103son 数据
	// * @param params
	// * @return
	// * @author: 吴剑东
	// * @date: 2013-11-17 上午10:44:14
	// */
	// public List<Map<String, Object>> getT103SONTableDataForExp(Map<String,
	// Object> params) {
	// return Dao.selectList("reportBase.queryT103SONTableData",params);
	// }
	// public Page getT103SONTableData(Map<String, Object> params) {
	// Page page = new Page(params);
	// JSONArray jsonArray =
	// JSONArray.fromObject(Dao.selectList("reportBase.queryT103SONTableData",params));
	// page.addDate(jsonArray,
	// Dao.selectInt("reportBase.queryT103SONTableCount",params));
	// return page;
	// }

	//
	//
	// /**
	// * T1052son 数据
	// * @param params
	// * @return
	// * @author: 吴剑东
	// * @date: 2013-11-17 上午10:44:14
	// */
	// public List<Map<String, Object>> getT105SONTableDataForExp(Map<String,
	// Object> params) {
	// return Dao.selectList("reportBase.queryT105SONTableData",params);
	// }
	// public Page getT105SONTableData(Map<String, Object> params) {
	// Page page = new Page(params);
	// JSONArray jsonArray =
	// JSONArray.fromObject(Dao.selectList("reportBase.queryT105SONTableData",params));
	// page.addDate(jsonArray,
	// Dao.selectInt("reportBase.queryT105SONTableCount",params));
	// return page;
	// }

	/**
	 * T202 数据
	 * 
	 * @param params
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-11-17 上午10:44:14
	 */
	public List<Map<String, Object>> getT202TableDataForExp(
			Map<String, Object> params) {
		return Dao.selectList("reportBase.queryT202TableData", params);
	}

	public Page getT202TableData(Map<String, Object> params) {
		Page page = new Page(params);
		System.out.println(params);
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
				"reportBase.queryT202TableData", params));
		page.addDate(jsonArray, Dao.selectInt("reportBase.queryT202TableCount",
				params));
		return page;
	}

	/**
	 * T403son 数据
	 * 
	 * @param params
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-11-17 上午10:44:14
	 */
	public List<Map<String, Object>> getT403SONTableDataForExp(
			Map<String, Object> params) {
		return Dao.selectList("reportBase.queryT403SONTableData", params);
	}

	public Page getT403SONTableData(Map<String, Object> params) {
		Page page = new Page(params);
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
				"reportBase.queryT403SONTableData", params));
		page.addDate(jsonArray, Dao.selectInt(
				"reportBase.queryT403SONTableCount", params));
		return page;
	}

	/**
	 * T202son 数据
	 * 
	 * @param params
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-11-17 上午10:44:14
	 */
	public List<Map<String, Object>> getT306TableDataForExp(
			Map<String, Object> params) {
		return Dao.selectList("reportBase.queryT306TableData", params);
	}

	public Page getT306TableData(Map<String, Object> params) {
		Page page = new Page(params);
		System.out.println(params);
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
				"reportBase.queryT306TableData", params));
		page.addDate(jsonArray, Dao.selectInt("reportBase.queryT306TableCount",
				params));
		return page;
	}

	/**
	 * T108 数据
	 * 
	 * @param params
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-11-17 上午10:44:14
	 */
	public List<Map<String, Object>> getT108TableDataForExp(
			Map<String, Object> params) {
		return Dao.selectList("reportBase.queryT108TableData", params);
	}

	public Page getT108TableData(Map<String, Object> params) {
		Page page = new Page(params);
		System.out.println(params);
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(
				"reportBase.queryT108TableData", params));
		page.addDate(jsonArray, Dao.selectInt("reportBase.queryT108TableCount",
				params));
		return page;
	}

	public void insertT108TableData() {
		Dao.insert("reportBase.insertT108TableData");
	}
	
	public List<Map<String,Object>> superTableList(Map<String,Object> param){
		return Dao.selectList("reportBase.querySuperTableAll", param) ;
	}

	public List<Map<String, Object>> addStatisticsField(Map<String, Object> param) {
		param.put("rows", 50000);//不分页，全部导出
		Page page = this.getSuperTablePage(param);
		return page.getData();
	}

	public Object getProvince1() {
		
		return Dao.selectList("reportBase.getProvince1");
	}
	/**
	 * TODO
	 *@requestFunction
	 * 拿到分支机构
	 * 
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年8月7日 下午2:53:53
	 */
	public List<Map> getBranches() {
		
		return Dao.selectList("reportBase.getBranches");
	}

	/**
	 * TODO
	 *@requestFunction
	 * 拿到批准状态
	 * 
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年8月7日 下午2:55:27
	 */
	public Object getApplyStatus() {

		return Dao.selectList("reportBase.getApplyStatus");
	}
	

}

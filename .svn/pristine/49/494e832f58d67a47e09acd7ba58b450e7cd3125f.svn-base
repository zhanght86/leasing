package com.pqsoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UTIL;

public class UtilChart {
	/********* 图形报表生成工具类**@auth: king 2014年7月30日 *************************/
	// 图标文件路径
	private static final String PATH_ = "chartData/";

	// sql命名空间
	private static final String _NAMESPACE = "reportBase.";

	/**
	 * 生成饼状图报表
	 * 
	 * @param CHART_NAME
	 *            图形报表名称 如 新增客户统计
	 * @param list
	 *            包含DATA_TYPE 数据类型名称、DATA_VALUE 数据数量 <br>
	 *            如 DATA_TYPE:自然人 DATA_VALUE:30
	 * @return
	 * @author King 2014年7月30日
	 */
	public String createPieChart(String CHART_NAME, List<Map<String, Object>> list) {
		VelocityContext context = new VelocityContext();
		context.put("CHART_NAME", CHART_NAME);
		context.put("pieList", list);
		return VM.html(PATH_ + "chartPie.vm", context);
	}
	
	/**
	 * 生成饼状图报表数据统计
	 * 
	 * @param CHART_NAME
	 *            图形报表名称 如 新增客户统计
	 * @param dataList
	 *            包含DATA_TYPE 数据类型名称、DATA_VALUE 数据数量 <br>
	 *            如 DATA_TYPE:自然人 DATA_VALUE:30
	 * @return
	 * @author King 2014年8月5日
	 */
	public String createPieData(String CHART_NAME,
			 List<Map<String, Object>> dataList){
		VelocityContext context = new VelocityContext();
		context.put("CHART_NAME", CHART_NAME);
		context.put("pieList", dataList);
		context.put("FORMAT", UTIL.FORMAT);
		return VM.html(PATH_ + "chartPieData.vm", context);
	}
	

	/**
	 * 
	 * 查看饼状图或统计数据
	 * @param param
	 * @param TYPE TYPE 统计类型 数据字典配置的TYPE
	 * @param REPORT_NAME_BEFOR  报表名称的前缀
	 * @param XMLMETHOD 访问的xml及方法（XX.XX）
	 * @param REPORT_QZTX 1标识图标  2 标识统计数据
	 * @return
	 * @author King 2014年8月8日
	 */
	public String to_chartPie(Map<String,Object> param,String TYPE,String REPORT_NAME_BEFOR,String XMLMETHOD,String REPORT_QZTX){
		BaseUtil.getDataAllAuth(param);
		this.doCreateChartInit(param, TYPE, REPORT_NAME_BEFOR);
		List<Map<String,Object>> list=Dao.selectList(XMLMETHOD, param);
		if("2".equals(REPORT_QZTX))
			return this.createPieData(REPORT_NAME_BEFOR, list);
		else
			return this.createPieChart(REPORT_NAME_BEFOR, list);
	}
	
	
	/**
	 * 生成折线图报表
	 * 
	 * @param CHART_NAME
	 *            图形报表名称 如 新增客户统计
	 * @param xAxisList
	 *            折线图 横坐标标识 泛型为String <br>
	 *            如 横坐标为月份 2014-01,2014-02,2014-03
	 * @param dataList
	 *            包含DATA_TYPE 数据类型名称、DATA_VALUE 表示数据列表List<String> <br>
	 *            20,10,41
	 * @return
	 * @author King 2014年7月30日
	 */
	public String createLineChart(String CHART_NAME, List<String> xAxisList, List<Map<String, Object>> dataList) {
		VelocityContext context = new VelocityContext();
		context.put("CHART_NAME", CHART_NAME);
		context.put("xAxisList", xAxisList);
		context.put("lineList", dataList);
		return VM.html(PATH_ + "chartLine.vm", context);
	}
	
	
	/**
	 * 生成折线图报表数据统计
	 * 
	 * @param CHART_NAME
	 *            图形报表名称 如 新增客户统计
	 * @param xAxisList
	 *            折线图 横坐标标识 泛型为String <br>
	 *            如 横坐标为月份 2014-01,2014-02,2014-03
	 * @param dataList
	 *            包含DATA_TYPE 数据类型名称、DATA_VALUE 表示数据列表List<double> <br>
	 *            20,10,41
	 * @return
	 * @author King 2014年7月30日
	 */
	public String createLineData(String CHART_NAME, List<String> xAxisList, List<Map<String, Object>> dataList) {
		VelocityContext context = new VelocityContext();
		context.put("CHART_NAME", CHART_NAME);
		context.put("xAxisList", xAxisList);
		context.put("lineList", dataList);
		context.put("FORMAT", UTIL.FORMAT);
		double []yA=new double[xAxisList.size()];
		for (Map<String,Object> m : dataList) {
			List<Double> vlist=JSONArray.fromObject(m.get("DATA_VALUE"));
			for (int j=0;j<vlist.size();j++) {
				yA[j]+=vlist.get(j);
			}
		}
		context.put("ylist", yA);
		return VM.html(PATH_ + "chartLineData.vm", context);
	}
	
	/**
	 * 查看折线图或统计数据
	 * @param param
	 * @param TYPE 统计类型 数据字典配置的TYPE
	 * @param REPORT_NAME_BEFOR 报表名称的前缀
	 * @param XMLMETHOD 访问的xml及方法 如XX1,XX2,XX3（XX1为横坐标，XX2为类型（例如：法人，自然人），XX3为数据）
	 * @param REPORT_QZTX 1标识图标  2 标识统计数据
	 * @return
	 * @author King 2014年8月8日
	 */
	public String to_chartLine(Map<String,Object> param,String TYPE,String REPORT_NAME_BEFOR,String XMLMETHOD,String REPORT_QZTX){
		BaseUtil.getDataAllAuth(param);
		this.doCreateChartInit(param, TYPE, REPORT_NAME_BEFOR);
		String[] METHOD = XMLMETHOD.split(",");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		//查询折线条数
		List<Map<String,Object>> listDa=Dao.selectList(METHOD[1], param);
		for(int i=0;i<listDa.size();i++){
			Map<String,Object> mapDa=listDa.get(i);
			if(mapDa!=null)
			{
				Map<String,Object> map=new HashMap<String,Object>();
				map.putAll(param);
				map.putAll(mapDa);
				// 数据库数据（获取的是list<Map>类型，需要目标数据list<String>）
				List<Map<String,Object>> list1 = Dao.selectList(METHOD[2], map);
				// 目标list
				List<Double> dlist = new ArrayList<Double>();
				for (int jj = 0; jj < list1.size(); jj++) {
					dlist.add(Double.parseDouble((list1.get(jj)).get("COUNTS")+""));
				}
				mapDa.put("DATA_VALUE", dlist);
				dataList.add(mapDa);
			}
		}
		// 横坐标数据库数据（获取的是list<Map>类型，需要目标数据list<String>）
		List<Map<String,Object>> list2 = Dao.selectList(METHOD[0], param);
		// 目标list
		List<String> xlist = new ArrayList<String>();
		for (int kk = 0; kk < list2.size(); kk++) {
			xlist.add((list2.get(kk)).get("X_VALUE") + "");
		}
		if("2".equals(REPORT_QZTX)){
			return createLineData(REPORT_NAME_BEFOR, xlist, dataList);
		}else{
			return createLineChart(REPORT_NAME_BEFOR, xlist, dataList);
		}
	}
	
	/**
	 * 生成散点图报表
	 * 
	 * @param CHART_NAME
	 *            图形报表名称 如 新增客户统计
	 * @param dataList
	 *            包含DATA_TYPE 数据类型名称、DATA_VALUE 表示数据列表List<Map<String,Object>
	 *            其中map中包含XVALUE,YVALUE的key值对 <br>
	 * @param STANDARD 标准线
	 * @return
	 * @author King 2014年7月30日
	 */
	public String createDashedChart(String CHART_NAME,
			 List<Map<String, Object>> dataList,String STANDARD) {
		VelocityContext context = new VelocityContext();
		context.put("CHART_NAME", CHART_NAME);
		context.put("STANDARD", STANDARD);
		context.put("dashedList", dataList);
		return VM.html(PATH_ + "chartsDashed.vm", context);
	}
	
	/**
	 * 生成散点图报表
	 * 
	 * @param CHART_NAME
	 *            图形报表名称 如 新增客户统计
	 * @param dataList
	 *            包含DATA_TYPE 数据类型名称、DATA_VALUE 表示数据列表List<Map<String,Object>
	 *            其中map中包含XVALUE,YVALUE的key值对 <br>
	 * @param STANDARD 标准线
	 * @return
	 * @author King 2014年7月30日
	 */
	public String createDashedData(String CHART_NAME,
			 List<Map<String, Object>> dataList,String STANDARD) {
		VelocityContext context = new VelocityContext();
		context.put("CHART_NAME", CHART_NAME);
		context.put("STANDARD", STANDARD);
		context.put("dashedList", dataList);
		context.put("FORMAT", UTIL.FORMAT);
		return VM.html(PATH_ + "chartDashedData.vm", context);
	}
	
	/**
	 * 查看散点图或统计数据
	 * @param param
	 * @param TYPE 统计类型 数据字典配置的TYPE
	 * @param REPORT_NAME_BEFOR 报表名称的前缀
	 * @param XMLMETHOD 访问的xml及方法 如 XMLMETHOD为XX1，XX2（XX1为类型（例如：法人，自然人），XX2为数据）
	 * @param STANDARD 散点图标准线 一个数值 不需要时 可以为空
	 * @param REPORT_QZTX 1标识图标  2 标识统计数据
	 * @return
	 * @author King 2014年8月8日
	 */
	public String to_chartDashed(Map<String,Object> param,String TYPE,String REPORT_NAME_BEFOR,String XMLMETHOD,String STANDARD,String REPORT_QZTX){
		BaseUtil.getDataAllAuth(param);
		this.doCreateChartInit(param, TYPE, REPORT_NAME_BEFOR);
		String[] METHOD=XMLMETHOD.split(",");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		//查询散点个数
		List<Map<String,Object>> listDa=Dao.selectList(METHOD[0], param);
		for(int i=0;i<listDa.size();i++){
			Map<String,Object> mapDa=listDa.get(i);
			if(mapDa!=null)
			{
				Map<String,Object> map=new HashMap<String,Object>();
				map.putAll(param);
				map.putAll(mapDa);
				//数据库数据（获取的是list<Map>类型，需要目标数据list<String>）
				List<Map<String,Object>> list1=Dao.selectList(METHOD[1], map);
				mapDa.put("DATA_VALUE", list1);
				dataList.add(mapDa);
			}
		}
		if("2".equals(REPORT_QZTX))
			return createDashedData(REPORT_NAME_BEFOR, dataList,STANDARD);
		else
			return createDashedChart(REPORT_NAME_BEFOR, dataList,STANDARD);
	}
	
	
	/**
	 * 生成柱状图报表
	 * @param Y_UNIT
	 *            纵坐标单位
	 * 
	 * @param CHART_NAME
	 *            图形报表名称 如 新增客户统计
	 * @param xAxisList
	 *            折线图 横坐标标识 泛型为String <br>
	 *            如 横坐标为月份 2014-01,2014-02,2014-03
	 * @param dataList
	 *            包含DATA_TYPE 数据类型名称、DATA_VALUE 表示数据列表List<String> <br>
	 *            20,10,41
	 * @return
	 * @author King 2014年7月30日
	 */
	public String createBarChart(String Y_UNIT,String CHART_NAME, List<String> xAxisList, List<Map<String, Object>> dataList) {
		VelocityContext context = new VelocityContext();
		context.put("CHART_NAME", CHART_NAME);
		context.put("xAxisList", xAxisList);
		context.put("barList", dataList);
		context.put("Y_UNIT", Y_UNIT);
		return VM.html(PATH_ + "chartBar.vm", context);
	}	
	
	/**
	 * 生成柱状图报表数据统计
	 * 
	 * @param CHART_NAME
	 *            图形报表名称 如 新增客户统计
	 * @param xAxisList
	 *            折线图 横坐标标识 泛型为String <br>
	 *            如 横坐标为月份 2014-01,2014-02,2014-03
	 * @param dataList
	 *            包含DATA_TYPE 数据类型名称、DATA_VALUE 表示数据列表List<double> <br>
	 *            20,10,41
	 * @return
	 * @author King 2014年7月30日
	 */
	public String createBarData(String CHART_NAME, List<String> xAxisList, List<Map<String, Object>> dataList) {
		VelocityContext context = new VelocityContext();
		context.put("CHART_NAME", CHART_NAME);
		context.put("xAxisList", xAxisList);
		context.put("barList", dataList);
		context.put("FORMAT", UTIL.FORMAT);
		double []yA=new double[xAxisList.size()];
		for (Map<String,Object> m : dataList) {
			List<Double> vlist=JSONArray.fromObject(m.get("DATA_VALUE"));
			for (int j=0;j<vlist.size();j++) {
				yA[j]+=vlist.get(j);
			}
		}
		context.put("ylist", yA);
		return VM.html(PATH_ + "chartBarData.vm", context);
	}
	
	/**
	 * 生成柱状图报表数据统计
	 * 
	 * @param CHART_NAME
	 *            图形报表名称 如 新增客户统计
	 * @param xAxisList
	 *            折线图 横坐标标识 泛型为String <br>
	 *            如 横坐标为月份 2014-01,2014-02,2014-03
	 * @param dataList
	 *            包含DATA_TYPE 数据类型名称、DATA_VALUE 表示数据列表List<double> <br>
	 *            20,10,41
	 * @return
	 * @author King 2014年7月30日
	 */
	public String createBarDataTS(String CHART_NAME, List<String> xAxisList, List<Map<String, Object>> dataList) {
		VelocityContext context = new VelocityContext();
		context.put("CHART_NAME", CHART_NAME);
		context.put("xAxisList", xAxisList);
		context.put("barList", dataList);
		context.put("FORMAT", UTIL.FORMAT);
		double []yA=new double[xAxisList.size()];
		for (Map<String,Object> m : dataList) {
			List<Double> vlist=JSONArray.fromObject(m.get("DATA_VALUE"));
			for (int j=0;j<vlist.size();j++) {
				yA[j]+=vlist.get(j);
			}
		}
		context.put("ylist", yA);
		return VM.html(PATH_ + "chartBarDataNoHj.vm", context);
	}
	
	/**
	 * 查看柱状图或统计数据
	 * @param param
	 * @param TYPE 统计类型 数据字典配置的TYPE
	 * @param REPORT_NAME_BEFOR 报表名称的前缀
	 * @param XMLMETHOD 访问的xml及方法 如XX1,XX2,XX3（XX1为横坐标，XX2为类型（例如：法人，自然人），XX3为数据）
	 * @param REPORT_QZTX 1标识图标  2 标识统计数据
	 * @return
	 * @author King 2014年8月8日
	 */
	public String to_chartBar(Map<String,Object> param,String TYPE,String REPORT_NAME_BEFOR,String XMLMETHOD,String REPORT_QZTX){
		this.doCreateChartInit(param, TYPE, REPORT_NAME_BEFOR);
		String[] METHOD = XMLMETHOD.split(",");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		//查询折线条数
		List<Map<String,Object>> listDa=Dao.selectList(METHOD[1], param);
		for(int i=0;i<listDa.size();i++){
			Map<String,Object> mapDa=listDa.get(i);
			if(mapDa!=null)
			{
				Map<String,Object> map=new HashMap<String,Object>();
				if(param != null){
					map.putAll(param);
				}
				map.putAll(mapDa);
				// 数据库数据（获取的是list<Map>类型，需要目标数据list<String>）
				List<Map<String,Object>> list1 = Dao.selectList(METHOD[2], map);
				// 目标list
				List<Double> dlist = new ArrayList<Double>();
				for (int jj = 0; jj < list1.size(); jj++) {
					dlist.add(Double.parseDouble((list1.get(jj)).get("COUNTS")+""));
				}
				mapDa.put("DATA_VALUE", dlist);
				dataList.add(mapDa);
			}
		}
		// 横坐标数据库数据（获取的是list<Map>类型，需要目标数据list<String>）
		List<Map<String,Object>> list2 = Dao.selectList(METHOD[0], param);
		// 目标list
		List<String> xlist = new ArrayList<String>();
		for (int kk = 0; kk < list2.size(); kk++) {
			xlist.add((list2.get(kk)).get("X_VALUE") + "");
		}
		if("2".equals(REPORT_QZTX)){
			return createBarData(REPORT_NAME_BEFOR, xlist, dataList);
		}else{
			if(param !=null && param.get("Y_UNIT")!=null && !param.get("Y_UNIT").equals("")){
				return createBarChart(param.get("Y_UNIT")+"",REPORT_NAME_BEFOR, xlist, dataList);
			}
			return createBarChart(null,REPORT_NAME_BEFOR, xlist, dataList);
		}
	}

	/**
	 * 统计图标数据生成初始化
	 * @param param
	 * @param TYPE 统计类型 数据字典配置的TYPE
	 * @param REPORT_NAME_BEFOR 报表名称的前缀
	 * @return
	 * @author King 2014年8月8日
	 */
	private void doCreateChartInit(Map<String,Object> param,String TYPE,String REPORT_NAME_BEFOR){
		//判断前台是否有数据传入，没有则默认数据
		String REPORT_TYPE="1";//1:客户类型
		String REPORT_DATE="1";//默认为全部
		if(param ==null){
			param=new HashMap<String,Object>();
			param.put("REPORT_TYPE", REPORT_TYPE);
			param.put("REPORT_DATE", REPORT_DATE);
		} else {
			if (param.get("REPORT_TYPE") != null && !param.get("REPORT_TYPE").equals("")) {
				REPORT_TYPE = param.get("REPORT_TYPE").toString();
			} else {
				param.put("REPORT_TYPE", REPORT_TYPE);
			}
			
			if (param.get("REPORT_DATE") == null || param.get("REPORT_DATE").equals("")) {
				param.put("REPORT_DATE", REPORT_DATE);
			}
		}
		
		// 查询数据字典该图信息（报表名称，折线图还是柱状图）
		Map<String,Object> dateMap = new HashMap<String,Object>();
		dateMap.put("CODE", REPORT_TYPE);
		dateMap.put("TYPE", TYPE);
		dateMap = Dao.selectOne("clientReport.dictionaryByTypeCode", dateMap);
		String REPORT_FLAG = dateMap.get("FLAG") + "";
		REPORT_NAME_BEFOR = REPORT_NAME_BEFOR + "-" + REPORT_FLAG + "-统计";
	}

	/**
	 * 解析action约束的所有sql字段 即页面列表字段，将之传到前台页面备用
	 * 
	 * @param ReportTiTle
	 *            报表名称
	 * @param titlesName
	 *            列表字段名数组
	 * @param sqlsName
	 *            列表sql字段数组
	 * @return
	 * @author King 2014年8月2日
	 */
	public Map<String, Object> parseColumnInit(String ReportTiTle, String[] titlesName, String[] sqlsName) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();
		int colSize = titlesName.length;
		for (int i = 0; i < colSize; i++) {
			Map<String, Object> colMap = new HashMap<String, Object>();
			colMap.put("FLAG", titlesName[i]);
			colMap.put("FIELD", sqlsName[i]);
			columnList.add(colMap);
		}
		map.put("COLSIZE", colSize);
		map.put("columnList", columnList);
		map.put("COLUMN_NAMES", this.queryReportColumnByReportAndUser(ReportTiTle, Security.getUser().getCode()));
		return map;
	}

	/**
	 * 自定义sql字段与前台页面表头解析
	 * @param ReportTiTle
	 * @param titlesName
	 * @param sqlsName
	 * @return
	 * @author King 2014年8月8日
	 */
	public Map<String, Object> parseColumnInit(String ReportTiTle, List<String> titlesName, List<String> sqlsName) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();
		int colSize = titlesName.size();
		for (int i = 0; i < colSize; i++) {
			Map<String, Object> colMap = new HashMap<String, Object>();
			colMap.put("FLAG", titlesName.get(i));
			colMap.put("FIELD", sqlsName.get(i));
			columnList.add(colMap);
		}
		map.put("COLSIZE", colSize);
		map.put("columnList", columnList);
		map.put("COLUMN_NAMES", this.queryReportColumnByReportAndUser(ReportTiTle, Security.getUser().getCode()));
		return map;
	}

	/**
	 * 解析前台展示的sql列字符串 用于拼接sql语句
	 * 
	 * @param columns
	 * @return
	 * @author King 2014年8月1日
	 */
	public String getColumns(String columnsStr) {
		if (StringUtils.isBlank(columnsStr)) {
			return "*";
		} else if (columnsStr.indexOf(",") != -1) {
			String[] columnsArray = columnsStr.split(",");
			String columns = "";
			for (String string : columnsArray) {
				columns += string + ",";
			}
			return columns.substring(0, columns.length() - 1);
		} else {
			return columnsStr;
		}
	}

	/**
	 * 查询报表自定义字段显示配置
	 * 
	 * @param REPORT_NAME
	 *            报表名称
	 * @param USER_CODE用户编号
	 * @return
	 * @author King 2014年8月2日
	 */
	public Object queryReportColumnByReportAndUser(String REPORT_NAME, String USER_CODE) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("REPORT_NAME", REPORT_NAME);
		m.put("USER_CODE", USER_CODE);
		return Dao.selectOne(_NAMESPACE + "queryReportColumnByReportAndUser", m);
	}

	/**
	 * 删除报表自定义字段显示配置
	 * 
	 * @param REPORT_NAME
	 *            报表名称
	 * @param USER_CODE
	 *            用户编号
	 * @author King 2014年8月2日
	 */
	public void delReportColumnByReportAndUser(String REPORT_NAME, String USER_CODE) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("REPORT_NAME", REPORT_NAME);
		m.put("USER_CODE", USER_CODE);
		Dao.delete(_NAMESPACE + "delReportColumnByReportAndUser", m);
		Dao.commit();
		Dao.close();
	}

	/**
	 * 新增报表自定义字段显示配置
	 * 
	 * @param REPORT_NAME
	 *            报表名称
	 * @param USER_CODE
	 *            用户编号
	 * @param COLUMN_NAMES
	 * @author King 2014年8月2日
	 */
	public void insertReportColumnByReportAndUser(String REPORT_NAME, String USER_CODE, Object COLUMN_NAMES) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("REPORT_NAME", REPORT_NAME);
		m.put("USER_CODE", USER_CODE);
		m.put("COLUMN_NAMES", COLUMN_NAMES);
		Dao.delete(_NAMESPACE + "insertReportColumnByReportAndUser", m);
		Dao.commit();
		Dao.close();
	}
	
	
	/**
	 * 查看折线图或统计数据
	 * @param param
	 * @param REPORT_NAME_BEFOR 报表名称的前缀
	 * @param XMLMETHOD 访问的xml及方法 如XX1,XX2,XX3（XX1为横坐标，XX2为类型（例如：法人，自然人），XX3为数据）
	 * @param REPORT_QZTX 1标识图标  2 标识统计数据
	 * @return
	 * @author King 2014年8月8日
	 */
	public String queryProjectChar(Map<String,Object> param,String REPORT_NAME_BEFOR,String XMLMETHOD){
		BaseUtil.getDataAllAuth(param);
		String[] METHOD = XMLMETHOD.split(",");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		//查询折线条数
		List<Map<String,Object>> listDa=new ArrayList<Map<String,Object>>();
		Map mapDaa=new HashMap<String,Object>();
		mapDaa.put("DATA_TYPE", param.get("PAYLIST_CODE")+"");
		listDa.add(mapDaa);
		for(int i=0;i<listDa.size();i++){
			Map<String,Object> mapDa=listDa.get(i);
			if(mapDa!=null)
			{
				Map<String,Object> map=new HashMap<String,Object>();
				map.putAll(param);
				map.putAll(mapDa);
				// 数据库数据（获取的是list<Map>类型，需要目标数据list<String>）
				List<Map<String,Object>> list1 = Dao.selectList(METHOD[2], map);
				// 目标list
				List<Double> dlist = new ArrayList<Double>();
				for (int jj = 0; jj < list1.size(); jj++) {
					dlist.add(Double.parseDouble((list1.get(jj)).get("COUNTS")+""));
				}
				mapDa.put("DATA_VALUE", dlist);
				dataList.add(mapDa);
			}
		}
		// 横坐标数据库数据（获取的是list<Map>类型，需要目标数据list<String>）
		List<Map<String,Object>> list2 = Dao.selectList(METHOD[0], param);
		// 目标list
		List<String> xlist = new ArrayList<String>();
		for (int kk = 0; kk < list2.size(); kk++) {
			xlist.add((list2.get(kk)).get("X_VALUE") + "");
		}
		
			return createLineChart(REPORT_NAME_BEFOR, xlist, dataList);
		
	}
	
	/**
	 * 查看柱状图或统计数据
	 * @param param
	 * @param TYPE 统计类型 数据字典配置的TYPE
	 * @param REPORT_NAME_BEFOR 报表名称的前缀
	 * @param XMLMETHOD 访问的xml及方法 如XX1,XX2,XX3（XX1为横坐标，XX2为类型（例如：法人，自然人），XX3为数据）
	 * @param REPORT_QZTX 1标识图标  2 标识统计数据
	 * @return
	 * @author King 2014年8月8日
	 */
	public String to_chartBarTS(Map<String,Object> param,String TYPE,String REPORT_NAME_BEFOR,String XMLMETHOD,String REPORT_QZTX){
		this.doCreateChartInit(param, TYPE, REPORT_NAME_BEFOR);
		String[] METHOD = XMLMETHOD.split(",");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		//查询折线条数
		List<Map<String,Object>> listDa=Dao.selectList(METHOD[1], param);
		for(int i=0;i<listDa.size();i++){
			Map<String,Object> mapDa=listDa.get(i);
			if(mapDa!=null)
			{
				Map<String,Object> map=new HashMap<String,Object>();
				if(param != null){
					map.putAll(param);
				}
				map.putAll(mapDa);
				// 数据库数据（获取的是list<Map>类型，需要目标数据list<String>）
				List<Map<String,Object>> list1 = Dao.selectList(METHOD[2], map);
				// 目标list
				List<Double> dlist = new ArrayList<Double>();
				for (int jj = 0; jj < list1.size(); jj++) {
					dlist.add(Double.parseDouble((list1.get(jj)).get("COUNTS")+""));
				}
				mapDa.put("DATA_VALUE", dlist);
				dataList.add(mapDa);
			}
		}
		// 横坐标数据库数据（获取的是list<Map>类型，需要目标数据list<String>）
		List<Map<String,Object>> list2 = Dao.selectList(METHOD[0], param);
		// 目标list
		List<String> xlist = new ArrayList<String>();
		for (int kk = 0; kk < list2.size(); kk++) {
			xlist.add((list2.get(kk)).get("X_VALUE") + "");
		}
		if("2".equals(REPORT_QZTX)){
			return createBarDataTS(REPORT_NAME_BEFOR, xlist, dataList);
		}else{
			if(param !=null && param.get("Y_UNIT")!=null && !param.get("Y_UNIT").equals("")){
				return createBarChart(param.get("Y_UNIT")+"",REPORT_NAME_BEFOR, xlist, dataList);
			}
			return createBarChart(null,REPORT_NAME_BEFOR, xlist, dataList);
		}
	}

}

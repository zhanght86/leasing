package com.pqsoft.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.sourceforge.jeval.Evaluator;

import org.apache.commons.collections.map.HashedMap;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;

@SuppressWarnings("static-access")
public class DateSiteUtil {
	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
	public static String getDate(String dateStr) {
		return dateStr;
	}

	public static String getDate(String dateStr, int t) throws Exception {
		calendar.setTime(df.parse(dateStr));
		calendar.add(calendar.DATE, t);
		dateStr = df.format(calendar.getTime());
		return dateStr;
	}

	/*
	 * 数组格式 支持 5-15-25 顺序由小到大 长度为3
	 */
	public static String getDate(String dateStr, String[] extend)//
			throws Exception {
		calendar.setTime(df.parse(dateStr));
		df.format(calendar.getTime());
		String[] dayArr = dateStr.split("-");
		int day = Integer.parseInt(dayArr[2]);// 日
		// int month = Integer.parseInt(dayArr[1]);//月
		if (day >= 1 && Integer.parseInt(extend[0]) >= day) {
			day = Integer.parseInt(extend[0]);
			calendar.set(calendar.DATE, day);
		} else if (day >= Integer.parseInt(extend[0])
				&& Integer.parseInt(extend[1]) >= day) {
			day = Integer.parseInt(extend[1]);
			calendar.set(calendar.DATE, day);
		} else if (day >= Integer.parseInt(extend[1])
				&& Integer.parseInt(extend[2]) >= day) {
			day = Integer.parseInt(extend[2]);
			calendar.set(calendar.DATE, day);
		} else if (day >= Integer.parseInt(extend[2]) && 31 >= day) {// 如果是处理月末最后几天
																		// 日期往后多推一个月
			day = Integer.parseInt(extend[0]);
			calendar.set(calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			calendar.set(calendar.DATE, day);
		}

		// for(int i=0;i<extend.length;i++){
		// if(i==0){
		// if (day >= 1 && Integer.parseInt(extend[i]) >= day) {
		// day = Integer.parseInt(extend[i]);
		// calendar.set(calendar.DATE, day);
		// }
		// }else if(i==(extend.length-1)){
		// if (day >= Integer.parseInt(extend[i]) && 31 >= day) {// 如果是处理月末最后几天
		// 日期往后多推一个月
		// day = Integer.parseInt(extend[0]);
		// calendar.set(calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		// calendar.set(calendar.DATE, day);
		// }
		// }else{
		// if (day >= Integer.parseInt(extend[i-1])
		// && Integer.parseInt(extend[i]) >= day) {
		// day = Integer.parseInt(extend[i]);
		// calendar.set(calendar.DATE, day);
		// }
		// }
		//
		// }

		dateStr = df.format(calendar.getTime());
		return dateStr;
	}

	/*
	 * 判断日期 是否最后几天
	 */
	public static Boolean getDateFlag(String dateStr, String[] extend)//
			throws Exception {
		Boolean flag = false;
		calendar.setTime(df.parse(dateStr));
		df.format(calendar.getTime());
		String[] dayArr = dateStr.split("-");
		int day = Integer.parseInt(dayArr[2]);// 日
		for (int i = 0; i < extend.length; i++) {
			System.out.println(extend[i] + "+============");
		}
		if (day >= Integer.parseInt(extend[extend.length - 1]) && 31 >= day) {// 如果是处理月末最后几天
																				// 日期往后多推一个月
			flag = true;
		}
		return flag;
	}

	/*
	 * 更新起租日 参数 支付表id 起租日
	 */
	@SuppressWarnings("unchecked")
	public static void updateStartDate(String PAY_ID, String STARTDATE) {
		Map<String, Object> map = new HashedMap();
		map.put("PAY_ID", PAY_ID);
		map.put("START_DATE", STARTDATE);
		Dao.update("payment.updateRentStartDate", map);
	}

	/*
	 * 更新还款日 参数 支付表id 起租日
	 */
	public static void updateRepaymentDate(String PAY_ID, String start_date,
			Boolean flag) throws Exception {
		calendar.setTime(df.parse(start_date));
		Map<String, Object> dateMap = Dao.selectOne("PayTask.getRentDateData",
				PAY_ID);
		Map<String, Object> CjMap = Dao
				.selectOne("PayTask.getCjStatus", PAY_ID);
		dateMap.put("PAY_ID", PAY_ID);

		String PAY_WAY = dateMap.get("PAY_WAY") + "";
		int LEASE_PERIOD = dateMap.get("LEASE_PERIOD") == null ? -1 : Integer
				.parseInt(dateMap.get("LEASE_PERIOD") + "");// 周期
		int LEASE_TERM = dateMap.get("LEASE_TERM") == null ? -1 : Integer
				.parseInt(dateMap.get("LEASE_TERM") + "");// 期次

		if (!PAY_WAY.contains("期初")) {
			for (int t = 0; t < LEASE_TERM; t++) {
				if (flag) {
					calendar.add(Calendar.MONTH, LEASE_PERIOD);
				} else {
					calendar.add(Calendar.MONTH, LEASE_PERIOD);
				}
				if (CjMap != null && CjMap.get("VALUE_STR") != null
						&& "1".equals(CjMap.get("VALUE_STR").toString())) {
					int i = calendar.get(Calendar.MONTH);
					if (i == 1) {
						t = t - 1;
						continue;
					}
				}
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				ArrayList<Map<String, Object>> dateList = new SysDictionaryMemcached()
						.get("日期段");
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				Evaluator e = new Evaluator();
				for (int i = 0; i < dateList.size(); i++) {
					Map<String, Object> map = (Map<String, Object>) dateList
							.get(i);
					String flagStr = map.get("FLAG").toString()
							.replaceAll("x", day + " && " + day);
					boolean bo = e.getBooleanResult(flagStr);
					if (bo) {
						int code = Integer.parseInt(map.get("CODE").toString());
						calendar.set(Calendar.DAY_OF_MONTH, code);
						break;
					}
				}
				dateMap.put("STARTDATE", df.format(calendar.getTime()));
				System.out.println("----STARTDATE:"
						+ df.format(calendar.getTime()));
				// dateMap.put("PERIOD_NUM", t + 1);
				dateMap.put("PERIOD_NUM", t + 1);
				// if(t==0){
				// continue;
				// }
				Dao.update("payment.updateRentDetailStartDate", dateMap);
			}
		} else if (PAY_WAY.contains("期初")) {
			for (int t = 0; t <= LEASE_TERM; t++) {
				calendar.add(Calendar.MONTH, (t == 0 ? 0 : LEASE_PERIOD));
				if (CjMap != null
						&& "1".equals(CjMap.get("VALUE_STR").toString())) {
					int i = calendar.get(Calendar.MONTH);
					if (i == 1) {
						t = t - 1;
						continue;
					}
				}
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				ArrayList<Map<String, Object>> dateList = new SysDictionaryMemcached()
						.get("日期段");
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				Evaluator e = new Evaluator();
				for (int i = 0; i < dateList.size(); i++) {
					Map<String, Object> map = (Map<String, Object>) dateList
							.get(i);
					String flagStr = map.get("FLAG").toString()
							.replaceAll("x", day + " && " + day);
					boolean bo = e.getBooleanResult(flagStr);
					if (bo) {
						int code = Integer.parseInt(map.get("CODE").toString());
						calendar.set(Calendar.DAY_OF_MONTH, code);
						break;
					}
				}
				dateMap.put("STARTDATE", df.format(calendar.getTime()));
				System.out.println("----STARTDATE:"
						+ df.format(calendar.getTime()));
				// dateMap.put("PERIOD_NUM", t + 1);
				dateMap.put("PERIOD_NUM", t);
				if (t == 0) {
					continue;
				}
				Dao.update("payment.updateRentDetailStartDate", dateMap);
			}
		}

		// 处理有尾款的应收日期
		if (LEASE_TERM > 0) {
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("PAY_ID", PAY_ID);
			tempMap.put("LEASE_TERM", LEASE_TERM);
			Dao.update("payment.updateDetailWKPayDate", tempMap);
		}

		// 同步财务数据
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("PAY_ID", PAY_ID);
		temp.put("PAYLIST_CODE", dateMap.get("PAYLIST_CODE") + "");
		temp.put("PMT", "PMT租金");
		temp.put("ZJ", "租金");
		temp.put("SYBJ", "剩余本金");
		temp.put("D", "第");
		temp.put("QI", "期");
		// 删除财务表数据
		Dao.delete("PayTask.deleteBeginning", temp);
		Dao.insert("PayTask.synchronizationBeginning", temp);

		// 同步中间表
		// 刷单条逾期数据
		Dao.update("PayTask.doDunDateByPaylist_code", dateMap);
		Dao.update("PayTask.doPRC_BE_QJL_PAY_CODE", dateMap);
	}

	/*
	 * public void setStartData(String PAY_ID,String BASEDATE) throws Exception{
	 * Map dateMap = Dao.selectOne("bpm.status.getDateMap", PAY_ID);//日期
	 * Map<String, String> map = new HashMap<String, String>();
	 * map.put("PAY_ID", PAY_ID); map.put("BASEDATE", BASEDATE); String dateStr
	 * = ""; String startTime = ""; int t = 0 ; Map rentMap =
	 * Dao.selectOne("PayTask.getProjectDataById", map);//起租规则 if(rentMap !=
	 * null){ if("1".endsWith(BASEDATE)){//合同签订日 dateStr =
	 * dateMap.get("QD_DATE")+""; }else if("2".endsWith(BASEDATE)){//设备签收日
	 * dateStr = dateMap.get("QS_DATE")+""; }else
	 * if("3".endsWith(BASEDATE)){//放款日 dateStr = dateMap.get("FK_DATE")+""; }
	 * if("0".equals(rentMap.get("DATESITE")+"")){//起租日
	 * 
	 * if("1".equals(rentMap.get("DATERULE")+"")){//等于基准日 startTime =
	 * this.getDate(dateStr); }else
	 * if("2".equals(rentMap.get("DATERULE")+"")){//等于基准日后延 t =
	 * Integer.parseInt(rentMap.get("DATERULE")+""); startTime =
	 * this.getDate(dateStr,t); }else
	 * if("3".equals(rentMap.get("DATERULE")+"")){//每月固定日期 String[] extend =
	 * (rentMap.get("DATERULE") + "").split("-"); startTime =
	 * this.getDate(dateStr, extend); } this.updateStartDate(PAY_ID, startTime);
	 * 
	 * } if("1".equals(rentMap.get("DATESITE")+"")){//还款日
	 * if("1".equals(rentMap.get("DATERULE")+"")){//等于基准日 this.getDate(dateStr);
	 * }else if("2".equals(rentMap.get("DATERULE")+"")){//等于基准日后延 t =
	 * Integer.parseInt(rentMap.get("DATERULE")+""); this.getDate(dateStr,t);
	 * }else if("3".equals(rentMap.get("DATERULE")+"")){//每月固定日期 String[] extend
	 * = (rentMap.get("DATERULE") + "").split("-"); this.getDate(dateStr,
	 * extend); } } } }
	 */
	
	public static void setDateData(String PAY_ID,String BASEDATE,boolean isRunFlag) throws Exception{
		Map<String,Object> dateMap = Dao.selectOne("bpm.status.getDateMap", PAY_ID);//日期
		Map<String, String> map = new HashMap<String, String>();
		map.put("PAY_ID", PAY_ID);
		if(StringUtils.isNotBlank(BASEDATE))
			map.put("BASEDATE", BASEDATE);
		List<Map<String, Object>> rentMapList = Dao.selectList(
				"PayTask.getProjectDataById", map);// 起租规则
		Map<String, Object> m = new HashMap<String, Object>();
		for (int t = 0; t < rentMapList.size(); t++) {
			m = rentMapList.get(t);
			BASEDATE=m.get("BASEDATE")+"";
			updateDate(m,PAY_ID,BASEDATE,dateMap,isRunFlag);

		}
	}


	public static void updateDate(Map<String,Object> rentMap, String PAY_ID,String BASEDATE,Map<String,Object> dateMap,boolean isRunFlag) throws Exception{

		String dateStr = "";
		String startTime = "";
		int t = 0;
		Boolean flag = false;
		if (rentMap != null) {
			String EXTEND = rentMap.get("EXTEND") + "";
			if ("1".equals(BASEDATE)) {// 合同签订日
				dateStr = dateMap.get("QD_DATE") + "";
			} else if ("2".equals(BASEDATE)) {// 设备签收日
				dateStr = dateMap.get("QS_DATE") + "";
			} else if ("3".equals(BASEDATE)) {// 放款日
				dateStr = dateMap.get("FK_DATE") + "";
				// dateStr="2014-10-12";
			} else if ("4".equals(BASEDATE)) {// 放款日或设备签收日
				String QS_DATE = dateMap.get("QS_DATE") + "";
				String FK_DATE = dateMap.get("FK_DATE") + "";
				if (StringUtils.isNotBlank(QS_DATE)
						&& StringUtils.isNotBlank(FK_DATE)) {
					if (StringUtils.isNotBlank(QS_DATE)
							&& df.parse(FK_DATE).after(df.parse(QS_DATE)))
						dateStr = QS_DATE;
					else
						dateStr = FK_DATE;
				} else if (StringUtils.isNotBlank(FK_DATE))
					dateStr = FK_DATE;
				else if (StringUtils.isNotBlank(QS_DATE))
					dateStr = QS_DATE;
			}
			System.out.println(BASEDATE + "-------------dateStr=" + dateStr);
			if ("0".equals(rentMap.get("DATESITE") + "")) {// 起租日

				if ("1".equals(rentMap.get("DATERULE") + "")) {// 等于基准日
					startTime = getDate(dateStr);
				} else if ("2".equals(rentMap.get("DATERULE") + "")) {// 等于基准日后延
					t = Integer.parseInt(EXTEND);
					startTime = getDate(dateStr, t);
				} else if ("3".equals(rentMap.get("DATERULE") + "")) {// 每月固定日期
					String[] extend = EXTEND.split("-");
					startTime = getDate(dateStr, extend);
				} else if ("4".equals(rentMap.get("DATERULE") + "")) {// 日期段
					String[] extend = EXTEND.split("-");
					startTime = getDate(dateStr, extend);
				}
				updateStartDate(PAY_ID, startTime);

			}
			if ("1".equals(rentMap.get("DATESITE") + "")) {// 还款日
				if ("1".equals(rentMap.get("DATERULE") + "")) {// 等于基准日
					startTime = getDate(dateStr);
				} else if ("2".equals(rentMap.get("DATERULE") + "")) {// 等于基准日后延
					t = Integer.parseInt(EXTEND);
					startTime = getDate(dateStr, t);
				} else if ("3".equals(rentMap.get("DATERULE") + "")) {// 每月固定日期
					String[] extend = EXTEND.split("-");
					startTime = getDate(dateStr, extend);
					if (rentMap.get("EXTEND") != null && EXTEND != "") {
						flag = getDateFlag(dateStr, EXTEND.split("-"));
					}
				} else if ("4".equals(rentMap.get("DATERULE") + "")) {// 日期段
					List<Map<String,Object>> list= new SysDictionaryMemcached().get("日期段");
					String day=dateStr.split("-")[2].replaceAll("0", "");
					Map<String,Object> map=new HashMap<String,Object>();
					String Formula="";
					for(int i=0;i<list.size();i++){
						map=list.get(i);
						Formula=map.get("FLAG").toString();
						Formula=Formula.replaceAll("x", "x&&x");
						Formula=Formula.replaceAll("x", day);
						if((Boolean) jse.eval(Formula)){
							day=map.get("CODE").toString();
							continue;
						}
					}
					startTime = getDate(dateStr.split("-")[0]+"-"+dateStr.split("-")[1]+"-"+(Integer.parseInt(day)>=10?day:0+day));
				}
				updateREPAYMENT_DATE(PAY_ID, startTime);
				if(isRunFlag){
					updateRepaymentDate(PAY_ID, startTime, flag);
				}
				
			}
		}
	}

	public static List getRend_ID(String PROJECT_ID) {
		return Dao.selectList("PayTask.getRend_ID", PROJECT_ID);
	}

	public static void updateREPAYMENT_DATE(String PAY_ID, String dateStr) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("PAY_ID", PAY_ID);
		m.put("dateStr", dateStr);
		Dao.update("PayTask.updateREPAYMENT_DATE", m);
	}
	
	public static void setDateDataByPaylistCode(String PAYLIST_CODE,String START_DATE,String BASEDATE,boolean isBoolFlag) throws Exception{
		Map<String,Object> dateMap = Dao.selectOne("bpm.status.getDateMapByPaylistCode", PAYLIST_CODE);//日期FK_DATE

		dateMap.put("FK_DATE", START_DATE);
		Map<String, String> map = new HashMap<String, String>();
		map.put("PAY_ID", dateMap.get("PAY_ID").toString());
		map.put("BASEDATE", BASEDATE);

		List<Map<String,Object>> rentMapList = Dao.selectList("PayTask.getProjectDataById", map);//起租规则
		Map<String,Object> m = new HashMap<String,Object>();
		for(int t = 0 ; t<rentMapList.size();t++){
			m = (Map<String,Object>)rentMapList.get(t);
			updateDate(m,dateMap.get("PAY_ID").toString(),m.get("BASEDATE").toString(),dateMap,isBoolFlag);

		}

	}
}

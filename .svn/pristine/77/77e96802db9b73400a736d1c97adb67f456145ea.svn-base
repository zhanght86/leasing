package com.pqsoft.holiday.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class HolidayMainService {

	private final String xmlPath = "holiday.";
	
	/**
	 * 节假日管理列表
	 * toMgHoliday
	 * @date 2013-10-18 下午05:41:45
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toMgHoliday(Map<String,Object> map) {
		Page page = new Page(map);
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"toMgHoliday",map);
		JSONArray addJson = new JSONArray();
		for(int i=0; i<list.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)list.get(i);
			json.put("ID", m.get("ID"));
			json.put("CREATE_YEAR", m.get("CREATE_YEAR"));
			addJson.add(json);
		}
		
		page.addDate(addJson, Dao.selectInt(xmlPath+"toMgHolidayCount", map));
		return page;
	}
	
	/**
	 * 根据输入的年份来查询数据库任务表中是否有该年份
	 * queryYear
	 * @date 2013-10-18 下午05:50:58
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryYear(Map<String,Object> map){//financing.task.getYear
		return Dao.selectList(xmlPath+"getYear", map);
	}
	
	/**
	 * 添加节假日
	 * insertTask
	 * @date 2013-10-18 下午05:53:50
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int insertTask(Map<String,Object> map){
		int result = Dao.insert(xmlPath+"insertTask", map);
		if(result>0){
			return Dao.selectInt(xmlPath+"getTaskMaxId", map);
		}
		return 0;
	}
	
	/**
	 * 插入节假日 到 数据库
	 * insertHoliday
	 * @date 2013-10-18 下午06:01:58
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int insertHoliday(Map<String, Object> map) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		int h = 0;
		String year = (String) map.get("year");
		String month = (String) map.get("month");
		String[] date = ((String) map.get("d")).split(",");
		//date[1] 表示日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date COMMON_DATE = sdf.parse(year + "-" + month + "-" + date[1]);
		//取得FISHOLIDAY 是1或0 
		String IS_HOLIDAY = date[0];
		m.put("COMMON_DATE", COMMON_DATE);
		m.put("IS_HOLIDAY", IS_HOLIDAY);
		m.put("TASK_ID",map.get("TASK_ID"));
		h = Dao.insert(xmlPath+"insertHoliday", m);
		return h;
	}
	
	/**
	 * 根据给定日期查询是否是节假日
	 * @createDate 2012-10-18
	 * @createAuthor 杨雪
	 * @param map 
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getHolidayByDate(Map<String,Object> map) {
		 return (Map<String,Object>)Dao.selectOne(xmlPath+"getHolidayByDate", map);
	}
	
	/**
	 * 根据年去查询天数以及相应的星期  且设置在页面格式
	 * toGetDateByYear
	 * @date 2013-10-18 下午06:00:41
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String,Object>> toGetDateByYear(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int year = Integer.parseInt((String) map.get("year"));
		java.text.DateFormat f = new java.text.SimpleDateFormat("yy-MM-dd");
		//week 是 年-月的第一天对应的 星期
		String s1 = year + "-" + map.get("month") + "-" + 1;
		@SuppressWarnings("unused")
		String week = HolidayMainService.getWeekOfDate(f.parse(s1));
		
		//以下是月大   的输出情况
		if (map.get("month").equals("1") || map.get("month").equals("3")
				|| map.get("month").equals("5") || map.get("month").equals("7")
				|| map.get("month").equals("8")
				|| map.get("month").equals("10")
				|| map.get("month").equals("12")) {
			for(int i=1;i<=31;i++){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("d", i);
				String s = year + "-" + map.get("month") + "-" + i;
				java.util.Date d = f.parse(s);
				m.put("wk", HolidayMainService.getWeekOfDate(d));
				list.add(m);
			}
		}
		//如果是月小
		if (map.get("month").equals("4") || map.get("month").equals("6")
				|| map.get("month").equals("9")
				|| map.get("month").equals("11")) {
			for(int i=1;i<=30;i++){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("d", i);
				String s = year + "-" + map.get("month") + "-" + i;
				java.util.Date d = f.parse(s);
				m.put("wk", HolidayMainService.getWeekOfDate(d));
				list.add(m);
			}
		}
		//不是瑞年的2月份   输出日期和对应星期的格式
		if (!(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				&& map.get("month").equals("2")) {
			for(int i=1;i<=28;i++){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("d", i);
				String s = year + "-" + map.get("month") + "-" + i;
				java.util.Date d = f.parse(s);
				m.put("wk", HolidayMainService.getWeekOfDate(d));
				list.add(m);
			}
		}
		//是瑞年的二月份
		if ((year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				&& map.get("month").equals("2")) {
			for(int i=1;i<=29;i++){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("d", i);
				String s = year + "-" + map.get("month") + "-" + i;
				java.util.Date d = f.parse(s);
				m.put("wk", HolidayMainService.getWeekOfDate(d));
				list.add(m);
			}
		}
		return list;
	}
	
	/**
	 * 根据年月日  输出对应的星期
	 * getWeekOfDate
	 * @date 2013-10-18 下午06:00:19
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 公共方法
	 * 
	 * 根据给定日期判断该日期是否是节假日
	 * @createDate 2013-10-18 下午06:00:19
	 * @createAuthor 杨雪<br>
	 * @return result 0 代表不是节假日  1代表节假日 2代表现有节假日中不存在该日期  3 代表不是指定格式的日期字符串
	 * @param 字符串日期 格式 YYYY-MM-DD
	 * @throws Exception 
	 */
	public int isHolidayByDate(String date) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		if(date==null||"".equals(date)){
			return 3;
		}
		// 判断是否是给定格式的字符串
		if (ValidateTime.validate(date) == false) {
			return 3;
		}
		map.put("COMMON_DATE", date);
		if (this.getHolidayByDate(map) == null|| "".equals(this.getHolidayByDate(map))) {
			result = 2;
		} else if (this.getHolidayByDate(map).get("IS_HOLIDAY").equals("1")) {
			result = 1;
		} else {
			result = 0;
		}
		System.out.println("0 代表不是节假日  1代表节假日 2代表现有节假日中不存在该日期  3 代表不是指定格式的日期字符串：                "+ result);
		return result;
	}
	
	/**
	 * 查看节假日
	 * queryHoliday
	 * @date 2013-10-20 上午10:22:48
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Object> queryHoliday(Map<String, Object> map) throws Exception {
		List<Object> list = new ArrayList<Object>();
		list = Dao.selectList(xmlPath+"getHoliday", map);
		return list;
	}
	
	/**
	 * 修改节假日信息<br>
	 * 需要传入具体修改的日期参数，和修改后的信息<br>
	 * 返回修改结果，int类型<br>
	 * <hr>
	 * doSetHolidayByDate
	 * @date 2013-10-20 上午10:35:20
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doSetHolidayByDate(Map<String, Object> map) {
		return Dao.update(xmlPath+"doSetHolidayByDate", map);
	}
	
	/**
	 * 添加备注
	 * doAddPbComment
	 * @date 2013-10-20 下午04:29:48
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doAddPbComment(Map<String,Object> map) {
		return Dao.insert(xmlPath+"addComment", map);
	}
	
	/**
	 * 查询相应模块的一条数据的备注列表
	 * queryPbComment
	 * @date 2013-10-20 下午04:29:48
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Object> queryPbComment(Map<String, Object> map) {
		List<Object> list = new ArrayList<Object>();
		list = Dao.selectList(xmlPath+"queryComment", map);
		return list;
	}
	
	/**
	 * 根据两个时间的值获取天数,返回一个天数
	 */
	public int queryHoliByStartEnd(Map<String, Object> map){
		map.put("STARTDAY", "2015-08-01");
		map.put("ENDDAY", "2015-08-25");	
		map.put("IS_HOLIDAY", 0);
		return Dao.selectInt(xmlPath+"queryHoliByStartEnd", map);
	}
	
	/**
	 * 两个日期之差
	 */
	public int queryHoliByDay(Map<String, Object> map){
		map.put("STARTDAY", "2015-08-01");
		map.put("ENDDAY", "2015-08-25");	
		return Dao.selectInt(xmlPath+"queryHoliByDay", map);
	}
	
	
}

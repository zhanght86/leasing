package com.pqsoft.overdue.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class GpsSearchService {

	


// Add By YangJ 2014年5月15日20:02:52 查询 省级行政区划
public List getFilSystemArea(){
	return Dao.selectList("fi.overdue.gps.getFilSystemArea");
}
//Add By YangJ 2014年5月15日20:03:20 查询GPS地点信息
public List selectDeviceGps(Map param){
	return Dao.selectList("fi.overdue.gps.selectDeviceGps",param);
}


//Add By YangJ 2014年5月16日9:31:13 录入GPS地点信息
public Object DeviceGps(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return Dao.insert("fi.overdue.gps.DeviceGps", param);
}
//Add By YangJ 2014年5月16日9:33:38 删除GPS地点信息
public Object deleteDeviceGps(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return Dao.delete("fi.overdue.gps.deleteDeviceGps", param);
}

	
/**
 * 
 * @memo  方法说明：获取城市
 *
 
 */
public List<Object> getFilSystemCity(String PARENT_ID)  {
	return  Dao.selectList("fi.overdue.gps.getFilSystemCity",PARENT_ID);
}

/**
 * 
 * @memo  方法说明：获取县区
 *
 
 */
public List<Object> getFilSystemCoun(String PARENT_ID)  {
	return  Dao.selectList("fi.overdue.gps.getFilSystemCoun",PARENT_ID);
}
//	----------------------------------------Yangj Add  2014年5月17日15:32:18 GPS地图-统计
/**
 * 统计各省设备数量
 * */
public List<?> getAreaCount(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return Dao.selectList("GpsPlan.getAreaCount",map);
}
/**
 * 根据省统计总支付表数量
 * */
public Map<String,Object> getPayCountByArea(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return (Map<String,Object>)Dao.selectOne("GpsPlan.getPayCountByArea",map);
}
/**
 * 根据省统计逾期设备数量
 * */
public Map<String,Object> getOverDueEqCountByArea(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return (Map<String,Object>)Dao.selectOne("GpsPlan.getOverDueEqCountByArea",map);
}

/**
 * 根据省统计逾期支付表数量
 * */
public Map<String,Object> getOverDuePayCountByArea(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return (Map<String,Object>)Dao.selectOne("GpsPlan.getOverDuePayCountByArea",map);
}

	
}

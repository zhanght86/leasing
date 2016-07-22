package com.pqsoft.gpsPlan.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class GpsSearchService {

	/**
	 * 查询所有设备信息的GPS信息
	 * @author 张勇
	 * */
	public Object getSearch(Map<String, Object> map) {
		// TODO Auto-generated method stub
	return Dao.selectList("GpsPlan.getSearch",map);
	}


	/**
	 * 单个用户查询设备信息
	 * @author 张勇
	 * */
	public Object getSearchForOne(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectList("GpsPlan.getSearchForOne",map);
	}


	/**
	 * 设备录入
	 * @author 张勇
	 * */
	public Object DeviceGps(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.insert("GpsPlan.DeviceGps", map);
	}
	/**
	 * 删除设备录入经纬度
	
	 * */
	public Object deleteDeviceGps(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.delete("GpsPlan.deleteDeviceGps", map);
	}

	/**
	 * 设备查询 经纬度
	 * @author 张勇
	 * */
	public Object selectDeviceGps(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectList("GpsPlan.selectDeviceGps",map);
	}

/**
 * 折线 2012-10-25
 * @author 张勇
 * */
	public Object brokenLine(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectList("GpsPlan.brokenLine",map);
	}


public Object car(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return Dao.selectList("GpsPlan.car",map);
}

/**
 * 根据设备id查询设备详细信息
 * */
public List<?> getGpsEqDetial(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return Dao.selectList("GpsPlan.getGpsEqDetial",map);
}
/**
 * 根据支付表查询逾期详情
 * */
public List<?> getEpOverdueDetail(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return Dao.selectList("GpsPlan.getEpOverdueDetail",map);
}
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

/**
 * 
 * @memo  方法说明：获取区域
 *

 */
public List<Map<String, Object>> getFilSystemArea() {
	//return new BaseDao<Map<String, Object>>().selectForList("crmBusiness.Business.getFilSystemArea", PLATFORM);
	return Dao.selectList("crmBusiness.Business.getFilSystemArea");
}

/**
 * 
 * @memo  方法说明：获取城市
 *
 
 */
public List<Object> getFilSystemCity(String PARENT_ID) throws Exception {
	return  Dao.selectList("crmBusiness.Business.getFilSystemCity",PARENT_ID);
}

/**
 * 
 * @memo  方法说明：获取县区
 *
 
 */
public List<Object> getFilSystemCoun(String PARENT_ID) throws Exception {
	return  Dao.selectList("crmBusiness.Business.getFilSystemCoun",PARENT_ID);
}
/**
 * 
 * 
 *查询租赁物详细信息
 
 */
public List<?> getEquipDetail(Map<String, Object> param) {
	return  Dao.selectList("GpsPlan.getEquipDetail",param);
}
}

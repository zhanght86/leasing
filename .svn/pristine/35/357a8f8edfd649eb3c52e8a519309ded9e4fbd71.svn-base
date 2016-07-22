package com.pqsoft.gpsPlan.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.gpsPlan.service.GpsSearchService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
public class GpsSearchAction extends Action{

	private  GpsSearchService service=new GpsSearchService();

//	Add By YangJ 2014年5月16日18:52:41 设备地址录入
	@aPermission(name = { "资产管理", "资产管理","GPS定位搜索" })
	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	@aAuth(type = aAuthType.USER)
	public Reply search()
	{System.out.println("GPS地图ACTION执行！！！");
		return GPS.search();
	}
	@aPermission(name = { "资产管理", "资产管理", "GPS定位设备信息" })
	@aDev(code = "xgm", email = "wowxgm@163.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply getDetail()
	{   System.out.println("detail 气泡框！！！！！！！！！！");
		VelocityContext context=new VelocityContext();
		Map<String,Object> map=new HashMap<String,Object>();
	    map.put("ID", _getParameters().get("ID"));
	    List<Map<String,Object>> detailList=(List<Map<String,Object>>)service.getGpsEqDetial(map);
	    List<Map<String,Object>> overdueList=new ArrayList<Map<String,Object>>();
	    for(Map<String,Object> map1:detailList){
	    	List<Map<String,Object>> overdue=(List<Map<String,Object>>)service.getEpOverdueDetail(map1);
	    	overdueList.addAll(overdue);
	    }
	    List<Map<String,Object>> eqdetailList=(List<Map<String,Object>>)service.getEquipDetail(map);
	    for(Map<String,Object> eqmap:eqdetailList){
	    	if(eqmap.get("TB_NUM")==null){
	    		eqmap.put("TB_NUM", 0);
	    	}
	    }
	    context.put("detail", detailList);
	    context.put("overdue", overdueList);
	    context.put("eqdetail", eqdetailList);
		return new ReplyHtml(VM.html("gpsPlan/gpsDetail.vm", context));
	}

	/**
	 * 卫星地图定位
	 * @author 张勇
	 * */
	public Reply getSearchForOne()
	{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ID", _getParameters().get("ID"));
		return GPS.getSearchForOne();
	}
	
	
	/**
	 * 设备经纬度录入
	 * @author 张勇
	 * @throws mException 
	 * */
	public Reply DeviceG()
	{
		Map<String,Object> map=new  HashMap<String,Object>();
		map.put("EQUIPMENT_ID", _getParameters().get("EQUIPMENT_ID"));
		return GPS.DeviceG();
	}
	
	
	/**
	 * 查询设备经纬度
	 * @author 张勇
	 * */
	public Reply SelectDeviceGps()
	{
		return GPS.SelectDeviceGps();
	}
	
	/**
	 * 查询设备经纬度
	 * @author 张勇
	 * */
	public Reply SelectForDeviceGps() throws Exception
	{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("EQUIPMENT_ID", _getParameters().get("EQUIPMENT_ID"));
		VelocityContext context=new VelocityContext();
		context.put("list", service.selectDeviceGps(map));
		context.put("areas", service.getFilSystemArea());
		context.put("map", map);
		return   new ReplyHtml(VM.html("gpsPlan/DeviceGPS.vm", context));
	}
	
	public Reply getCity()throws Exception{
		String PARENT_ID= _getParameters().get("PARENT_ID").toString();
		return new ReplyJson(JSONArray.fromObject(service.getFilSystemCity(PARENT_ID))); 
	}
	
	public Reply getCoun()throws Exception{
		String PARENT_ID= _getParameters().get("PARENT_ID").toString();
		return new ReplyJson(JSONArray.fromObject(service.getFilSystemCoun(PARENT_ID))); 
	}
	
	/**
	 * 折线图
	 * @author 张勇
	 * */
	public Reply brokenLing() throws Exception
	{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("EQUIPMENT_ID", _getParameters().get("EQUIPMENT_ID"));
		return GPS.brokenLing(map);
	}
	
	
	/**
	 *设备分布 
	 * @author 张勇
	 * */
	
	public Reply car() throws Exception
	{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("EQUIPMENT_ID", _getParameters().get("EQUIPMENT_ID"));
		return GPS.car();
	}
//	/**
//	 * 测试用
//	 * @author 张勇
//	 * */
//	public static String list()
//	{
//		VelocityContext context=new VelocityContext();
//		return mSGA.mergeVelocity("gpsPlan/list.vm", context);
//	}
//	
//	
//	/**
//	 * 卫星地图定位
//	 * @author 张勇
//	 * */
//	public static String search()
//	{
//		VelocityContext context=new  VelocityContext();
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map=new HashMap<String,Object>();
//		context.put("list", service.getSearch(map));
//		return mSGA.mergeVelocity("gpsPlan/SatellitePositioning.vm", context);
//	}
//	
//	
//
//	/**
//	 * 卫星地图定位
//	 * @author 张勇
//	 * */
//	public String getSearchForOne()
//	{
//		VelocityContext context=new  VelocityContext();
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("ID", _getParameters().get("ID"));
//		context.put("list", service.getSearchForOne(map));
//		return mSGA.mergeVelocity("gpsPlan/SatellitePositioning.vm", context);
//	}
//	
//
//	
//	
//	/**
//	 * 设备经纬度录入
//	 * @author 张勇
//	 * @throws mException 
//	 * */
//	public String DeviceG()
//	{
//		VelocityContext context=new VelocityContext();
//		Map<String,Object> map=new  HashMap<String,Object>();
//		map.put("EQUIPMENT_ID", _getParameters().get("EQUIPMENT_ID"));
//		context.put("map", map);
//		return mSGA.mergeVelocity("gpsPlan/DeviceGPS.vm", context);
//	}
//	
	/**
	 * 设备经纬度录入
	
	 * */
//	@aOperation(name = "资信中设备经纬度录入")
	public Reply DeviceGps() throws Exception
	{
		VelocityContext context=new VelocityContext();
		Map<String,Object> map=new  HashMap<String,Object>();
		map=_getParameters();
//		map.put("EQUIPMENT_ID", _getParameters().get("EQUIPMENT_ID"));
//		map.put("EQMT_LONGITUDE", _getParameters().get("EQMT_LONGITUDE"));
//		map.put("EQMT_LATITUDE", _getParameters().get("EQMT_LATITUDE"));
//	    map.put("ADDRESS",_getParameters().get("ADDRESS"));
	    service.DeviceGps(map);
		
	    return new ReplyJson( JSONArray.fromObject(map));
	}
	
	
	/**
	 * 查询设备经纬度
	 * @author 张勇
	 * */
	public Reply deleteDeviceGps()
	{
		Map<String,Object> map=new  HashMap<String,Object>();
		map.put("ID",_getParameters().get("ID"));
		service.deleteDeviceGps(map);
		return  new ReplyJson( JSONArray.fromObject(map));
	}
	
	
	@aPermission(name = { "资产管理", "资产管理", "统计" })
	@aDev(code = "xgm", email = "wowxgm@163.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply tongji()
	{
		VelocityContext context=new VelocityContext();
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("AREA_NAME", _getParameters().get("AREA_NAME"));
		List<Map<String,Object>> list=(List<Map<String,Object>>)service.getAreaCount(map);//统计各省设备数量
		for(Map<String,Object> amap:list){
			Map<String,Object> pmap=service.getPayCountByArea(amap);
			if(pmap==null||pmap.size()==0){
				amap.put("PAY_NUM", 0);
			}else{
				amap.put("PAY_NUM", pmap.get("PAY_NUM"));
			}
			Map<String,Object> oemap=service.getOverDueEqCountByArea(amap);
			if(oemap==null||oemap.size()==0){
				amap.put("OVER_EQ_NUM", 0);
			}else{
				amap.put("OVER_EQ_NUM", oemap.get("OVER_NUM"));
			}
			Map<String,Object> opmap=service.getOverDuePayCountByArea(amap);
			if(opmap==null||opmap.size()==0){
				amap.put("OVER_PAY_NUM", 0);
			}else{
				amap.put("OVER_PAY_NUM", opmap.get("OVER_NUM"));
			}
		}
		return  new ReplyJson(JSONArray.fromObject(list));
		
	}
//	
//	/**
//	 * 折线图
//	 * @author 张勇
//	 * */
//	public String brokenLing() throws Exception
//	{
//		VelocityContext context=new VelocityContext();
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("EQUIPMENT_ID", _getParameters().get("EQUIPMENT_ID"));
//		context.put("list", service.brokenLine(map));
//		return mSGA.mergeVelocity("gpsPlan/BrokenLine.vm", context);
//	}
//	
//	/**
//	 *设备分布 
//	 * @author 张勇
//	 * */
//	
//	public String car() throws Exception
//	{
//		VelocityContext context=new VelocityContext();
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("EQUIPMENT_ID", _getParameters().get("EQUIPMENT_ID"));
//		context.put("list", service.car(map));
//		return mSGA.mergeVelocity("gpsPlan/car.vm", context);
//	}

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

}

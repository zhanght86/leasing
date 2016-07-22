package com.pqsoft.overdue.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.gpsPlan.action.GPS;
import com.pqsoft.overdue.service.GpsSearchService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
//import com.mframework.system.mSGA;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;



public class GpsSearchAction extends Action{

private	GpsSearchService service = new GpsSearchService();

	public Reply execute(){
		return null;
	}
	
//	Add By YangJ 22014年5月15日19:37:36 查询GPS(添加地点信息)
	@aPermission(name = { "资产管理", "资产管理", "明细" })
	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	@aAuth(type = aAuthType.USER)	
	public Reply SelectForDeviceGps(){
		Map<String, Object> param = _getParameters();
		System.out.println("GPS输出参数：：param "+param);
		VelocityContext context=new VelocityContext();
//		service.GPSManage();
		context.put("areas", service.getFilSystemArea());
		context.put("list", service.selectDeviceGps(param));
//		param.put("EQUIPMENT_ID",);
		context.put("map", param);
		
		return new ReplyHtml(VM.html("overdue/DeviceGPS.vm", context));//.addOp(new OpLog("债权管理", "资产管理", "GPS管理"));
		
//		return new ReplyHtml(VM.html("overdue/overdue_Cust_Mg.vm", context)).addOp(new OpLog("债权管理", "逾期催收", "列表查询错误"));

		
	}
	
	
//	Add By YangJ 2014年5月16日9:26:22 设备地址录入
	@aPermission(name = { "资产管理", "资产管理", "设备地址录入" })
	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	@aAuth(type = aAuthType.USER)	
	public Reply DeviceGps()
	{
		Map<String,Object> param = _getParameters();
		System.out.println("设备地址录入："+param);
	    service.DeviceGps(param);
	    return new ReplyJson(JSONArray.fromObject(param));
	}
//	Add By YangJ 2014年5月16日13:29:20 设备地址录入
	@aPermission(name = { "资产管理", "资产管理", "地级市查询" })
	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	@aAuth(type = aAuthType.USER)	
	public Reply getCity(){
			System.out.println("获取城市------->"+_getParameters().get("PARENT_ID").toString());
		String PARENT_ID= com.pqsoft.skyeye.api.Action._getParameters().get("PARENT_ID").toString();
			System.out.println("获取城市-------->"+service.getFilSystemCity(PARENT_ID));
		return new ReplyJson(JSONArray.fromObject(service.getFilSystemCity(PARENT_ID))); 
		
	}
//	Add By YangJ 2014年5月16日13:29:242 设备地址查询
	@aPermission(name = { "资产管理", "资产管理", "区县查询" })
	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	@aAuth(type = aAuthType.USER)	
	public Reply getCoun(){
		String PARENT_ID= com.pqsoft.skyeye.api.Action._getParameters().get("PARENT_ID").toString();
		return new ReplyJson(JSONArray.fromObject(service.getFilSystemCoun(PARENT_ID))); 
	}
//	Add By YangJ 2014年5月16日15:51:32 设备地址删除
	@aPermission(name = { "资产管理", "资产管理", "地址删除" })
	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	@aAuth(type = aAuthType.USER)
	public Reply deleteDeviceGps()
	{
		Map<String,Object> map=_getParameters();
		service.deleteDeviceGps(map);
		return new ReplyJson(JSONArray.fromObject(map));
	}
	
	
	
	
	
	
//	Add By YangJ 2014年5月16日15:51:32 设备地址删除
	@aPermission(name = { "资产管理", "资产管理", "GPS地图" })
	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	@aAuth(type = aAuthType.USER)
	public Reply search() 
	{
		return GPS.search();
	}
	
	
//	Add By YangJ 2014年5月17日15:29:46 设备数量、逾期统计
	@aPermission(name = { "资产管理", "资产管理", "GPS地图" })
	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
	@aAuth(type = aAuthType.USER)	
	public Reply tongji() throws Exception
	{
		VelocityContext context=new VelocityContext();
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("AREA_NAME", _getParameters().get("AREA_NAME"));
		List<Map<String,Object>> list=(List<Map<String,Object>>)service.getAreaCount(map);//统计各省设备数量
		for(Map<String,Object> amap:list){
			Map<String,Object> pmap=service.getPayCountByArea(amap);//统计各省总支付表数量
			if(pmap==null||pmap.size()==0){
				amap.put("PAY_NUM", 0);
			}else{
				amap.put("PAY_NUM", pmap.get("PAY_NUM"));
			}
			Map<String,Object> oemap=service.getOverDueEqCountByArea(amap);//统计各省逾期设备数量
			if(oemap==null||oemap.size()==0){
				amap.put("OVER_EQ_NUM", 0);
			}else{
				amap.put("OVER_EQ_NUM", oemap.get("OVER_NUM"));
			}
			Map<String,Object> opmap=service.getOverDuePayCountByArea(amap);//统计各省逾期支付表数量
			if(opmap==null||opmap.size()==0){
				amap.put("OVER_PAY_NUM", 0);
			}else{
				amap.put("OVER_PAY_NUM", opmap.get("OVER_NUM"));
			}
		}
		return  new ReplyJson(JSONArray.fromObject(list));
		
	}
	
	
	
	
//	Add By YangJ 2014年5月16日10:16:00  取得GPS信息
//	@aPermission(name = { "债权管理", "资产管理", "GPS信息" })
//	@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
//	@aAuth(type = aAuthType.USER)	
//	public String search() throws mException
//	{
//		return GPS.search();
//	}
//	
//	
//	
//	/**
//	 * 卫星地图定位
//	 * @author 张勇
//	 * */
//	public  String search1() throws mException
//	{
//		VelocityContext context=new  VelocityContext();
//		Map<String,Object> map=new HashMap<String,Object>();
//		context.put("open_flag", mSGA.getCurrentRequest().getParameter("open_flag"));
//		context.put("list", service.getSearch(map));
//		return mSGA.mergeVelocity("gpsPlan/SatellitePositioning.vm", context);
//	}
	
}

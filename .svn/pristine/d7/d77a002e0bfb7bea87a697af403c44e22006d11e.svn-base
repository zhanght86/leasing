package com.pqsoft.gpsPlan.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.gpsPlan.service.GpsSearchService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class GPS {
		private static GpsSearchService service=new GpsSearchService();
		/**
		 * 测试用
		 * @author 张勇
		 * */
		public static Reply list()
		{
			VelocityContext context=new VelocityContext();
			return new ReplyHtml(VM.html("gpsPlan/list.vm", context));
		}
		
		
		/**
		 * 卫星地图定位
		 * @author 张勇
		 * */
//		Add By YangJ 2014年5月17日17:20:33 GPS定位搜索
		@aPermission(name = { "GPS定位搜索" })
		@aDev(code = "170055", email = "bingyyf@foxmail.com", name = "杨杰")
		@aAuth(type = aAuthType.USER)
		public static Reply search() 
		{
			VelocityContext context=new  VelocityContext();
			Map<String,Object> map=new HashMap<String,Object>();
			
			context.put("open_flag",com.pqsoft.skyeye.api.Action._getParameters().get("open_flag") );
			context.put("list", service.getSearch(map));
			return new ReplyHtml(VM.html("gpsPlan/SatellitePositioning.vm", context));
		}
		
		/**
		 * 卫星地图定位
		 * @author 张勇
		 * */
		public static Reply getSearchForOne()
		{
			VelocityContext context=new  VelocityContext();
			Map<String,Object> map=new HashMap<String,Object>();
			context.put("list", service.getSearchForOne(map));
			return new ReplyHtml(VM.html("gpsPlan/SatellitePositioning.vm", context));
		}
		

		
		
		/**
		 * 设备经纬度录入
		 * @author 张勇
		 * @throws mException 
		 * */
		public static Reply DeviceG() 
		{
			VelocityContext context=new VelocityContext();
			Map<String,Object> map=new  HashMap<String,Object>();
			context.put("map", map);
			return new ReplyHtml(VM.html("gpsPlan/DeviceGPS.vm", context));
		}
		
		/**
		 * 设备经纬度录入
		 * @author 张勇
		 * */
		public static Reply DeviceGps() 
		{
			VelocityContext context=new VelocityContext();
			Map<String,Object> map=new  HashMap<String,Object>();
			context.put("map", service.DeviceGps(map));
//			service.DeviceGps(map);
			return  SelectDeviceGps();
		}
		
		
		/**
		 * 查询设备经纬度
		 * @author 张勇
		 * */
		public static Reply SelectDeviceGps()
		{
			VelocityContext context=new VelocityContext();
			Map<String,Object> map=new  HashMap<String,Object>();
			context.put("map", service.DeviceGps(map));
			return  SelectDeviceGps();
		}
		
		/**
		 * 查询设备经纬度
		 * @author 张勇
		 * */
		public static Reply SelectForDeviceGps() 
		{
			return  SelectDeviceGps();
		}
		
		/**
		 * 折线图
		 * @author 张勇
		 * */
		public static Reply brokenLing(Map<String,Object> map) 
		{
			VelocityContext context=new VelocityContext();
			context.put("list", service.brokenLine(map));
			return new ReplyHtml(VM.html("gpsPlan/BrokenLine.vm", context));
		}
		
		
		
		
		public static Reply car() 
		{
			VelocityContext context=new VelocityContext();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String,Object> map=new HashMap<String,Object>();
			context.put("list", service.car(map));
			return new ReplyHtml(VM.html("gpsPlan/car.vm", context));
		}
}

//package com.pqsoft.base.gps.action;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.base.gps.service.GPSService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.StringUtils;
//
//public class GPSMGAction extends Action {
//
//	private GPSService service = new GPSService();
//
//	/**
//	 * 
//	 * <p>Title: execute</p> 
//	 * <p>Description: TODO()</p> 
//	 * @return 
//	 * @see com.pqsoft.skyeye.api.Action#execute() 
//	 * @throws 
//	 */
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "接口平台", "GPS", "查询页面", "查询" })
//	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
//	public Reply execute() {
//		return new ReplyHtml(VM.html("base/gps/gpsMg.vm",null));
//	}
//
//	/**
//	 * 
//	 * @Title: getGPSMgPageData 
//	 * @Description: () 
//	 * @return 
//	 * @return Reply 
//	 * @throws 
//	 */
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "接口平台", "GPS", "查询页面", "加载数据" })
//	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
//	public Reply getGPSMgPageData(){
//		Map<String,Object> param = new HashMap<String,Object>();
//		return new ReplyAjaxPage(service.getGPSMgPageData(param));
//	}
//	
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "接口平台", "GPS", "查询页面", "地图" })
//	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
//	public Reply toMapView() {
//		return new ReplyHtml(VM.html("base/gps/gaodemap.vm",null));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "接口平台", "GPS", "查询页面", "地图" })
//	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
//	public Reply getGPSInfo() {
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = this._getParameters();
//		context.put("GPS", service.getGPSInfo(param));
//		return new ReplyHtml(VM.html("base/gps/GPSInfo.vm",context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "接口平台", "GPS", "查询页面", "工作报告" })
//	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
//	public Reply getWorkLine() {
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = this._getParameters();
//		Map<String,String> workMap  = service.getWorkLine(param);
//		String workLine = "";
//		String dayStr = "";
//		
//		DateFormat format = new SimpleDateFormat("MM-dd");
//		for(int i = 0; i <= 29; i++){
//			Date today = new Date();
//			Calendar cal = Calendar.getInstance(); 
//			cal.setTime(today);
//			cal.add(Calendar.DAY_OF_YEAR, i-30);
//			dayStr += "'" + format.format(cal.getTime()) +"',";
//			workLine += StringUtils.nullToOther(workMap.get(format.format(cal.getTime())), "0") + ",";
//		}
//
//		workLine = workLine.substring(0,workLine.length()-1);
//		dayStr = dayStr.substring(0,dayStr.length()-1);
//		context.put("WORKDAYS", dayStr);
//		context.put("WORKLINE", workLine);
//		return new ReplyHtml(VM.html("base/gps/workLine.vm",context));
//	}
//	
//	/**
//	 * 
//	 * @Title: getCurrGPSInfo 
//	 * @Description: TODO() 
//	 * @return 
//	 * @return Reply 
//	 * @throws 
//	 */
//	@aAuth(type = aAuth.aAuthType.ALL)
//	@aPermission(name = { "接口平台", "GPS", "查询页面", "测试"  })
//	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
//	public Reply getCurrGPSInfo(){
//		SkyEye.getResponse().setCharacterEncoding("UTF-8");
//		return new ReplyAjax("看峻铭");
//	}
//	
//}

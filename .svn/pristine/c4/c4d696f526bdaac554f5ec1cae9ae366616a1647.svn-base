//package com.pqsoft.T31Report.action;
//
//import java.math.BigDecimal;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.fusionChart.service.ReportCreditService;
//import com.pqsoft.fusionChart.service.ReportProjectService;
//
///**
// * <p>
// * Title: 融资租赁信息系统平台 报表管理 图表
// * </p>
// * <p>
// * Description: T315台量逾期率
// * </p>
// * <p>
// * Company: 平强软件
// * </p>
// * @author 吴剑东  wujiandongit@163.com 
// * @version 1.0
// */
//public class T3151ReportAction extends Action{
//	
//	private ReportCreditService service = new ReportCreditService();
//	
//	
//	@Override
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3151台量逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		
//		return new ReplyHtml(VM.html("/T31Report/T315/T3151Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3151台量逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3151Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "501");
//		
//		String xmlStr = service.getDunweiTailiangXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	
//	
//
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3152租金逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT3152Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T315/T3152Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3152租金逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3152Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "502");
//		
//		String xmlStr = service.getDunweiTailiangXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	
//}

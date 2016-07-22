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
// * Description: T314分版块
// * </p>
// * <p>
// * Company: 平强软件
// * </p>
// * @author 吴剑东  wujiandongit@163.com 
// * @version 1.0
// */
//public class T3141ReportAction extends Action{
//	
//	private ReportCreditService service = new ReportCreditService();
//	
//	
//	@Override
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3141建机台量逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		
//		return new ReplyHtml(VM.html("/T31Report/T314/T3141Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3141建机台量逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3141Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "401");
//		map.put("NAME", "建机板块台量逾期率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		System.out.println(xmlStr);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3142建机租金逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT3142Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T3142Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3142建机租金逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3142Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "402");
//		map.put("NAME", "建机板块租金逾期率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3143建机风险资产率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT3143Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T3143Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3143建机风险资产率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3143Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "403");
//		map.put("NAME", "建机板块风险资产率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3144商用车台量逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT3144Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T3144Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3144商用车台量逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3144Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "404");
//		map.put("NAME", "商用车台量逾期率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3145商用车租金逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT3145Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T3145Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3145商用车租金逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3145Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "405");
//		map.put("NAME", "商用车租金逾期率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3146商用车风险资产率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT3146Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T3146Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3146商用车风险资产率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3146Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "406");
//		map.put("NAME", "商用车风险资产率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3147SFLC到期应收逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT3147Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T3147Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3147SFLC到期应收逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3147Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "407");
//		map.put("NAME", "SFLC到期应收逾期率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3148SFLC台量逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT3148Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T3148Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3148SFLC台量逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3148Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "408");
//		map.put("NAME", "SFLC台量逾期率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T3149SFLC租金逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT3149Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T3149Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T3149SFLC租金逾期率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT3149Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "409");
//		map.put("NAME", "SFLC租金逾期率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T31410SFLC风险资产率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT31410Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T31410Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T31410SFLC风险资产率"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT31410Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "410");
//		map.put("NAME", "SFLC风险资产率走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"数据统计", "T31411SFLC到期应收租金"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply toT31411Parent() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("/T31Report/T314/T31411Parent.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"数据统计", "T31411SFLC到期应收租金"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public void getT31411Chart(){
//		Map<String,Object> map = _getParameters();
//		String TIMES = map.get("BEGIN_TIME")+"";
//		String[] timeArr = TIMES.split("@");
//		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
//		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
//		// 统计类型
//		map.put("CREDIT_TYPE", "411");
//		map.put("NAME", "SFLC到期应收租金走势图");
//		
//		String xmlStr = service.getBankuanXml(map);
//		if(xmlStr.length() > 0){
//			service.doStringXmlExp(xmlStr);
//		}
//	}
//}

//package com.pqsoft.fusionChart.action;
//
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.fusionChart.service.ReportProjectService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//
///**
// * <p>
// * Title: 融资租赁信息系统平台 报表管理 图表
// * </p>
// * <p>
// * Description: 客户统计报表； 项目收益统计报表；
// * </p>
// * <p>
// * Company: 平强软件
// * </p>
// * @author 吴剑东  wujiandongit@163.com 
// * @version 1.0
// */
//public class ReportAction extends Action{
//	
//	private ReportProjectService service = new ReportProjectService();
//	
//	
//	@Override
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = {"报表管理","报表图表","项目报表"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		
//		return new ReplyHtml(VM.html("analysis/report/ladder.vm", context));
//	}
//
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"报表管理","报表图表","根据模块卡片返回页面"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Object goReportView(){
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> map = _getParameters();
//		String velocity = (String)map.get("velocity");
//		return new ReplyHtml(VM.html(velocity, context));
//	}
//	
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = {"报表管理","报表图表","图表点击事件跳转"})
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	public Reply goLink() {
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("analysis/report/testProject.vm", context));
//	}
//	
//}

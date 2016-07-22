package com.pqsoft.manufacturerApproval.action;
/**
 *  厂商审批页签
 *  @author 韩晓龙
 */
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.manufacturerApproval.service.ManufacturerSearchService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class ManufacturerWorkFlowAction extends Action{
	
	private String basePath = "manufacturerApproval/"; 
	private ManufacturerSearchService service = new ManufacturerSearchService();
	
	/**
	 *  显示主页面
	 */
	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("AdviceDetail", service.selectDetailByProjId(param));
		return new ReplyHtml(VM.html(basePath + "manufacturerAdviceForJbpm.vm", context));
	}
	
}
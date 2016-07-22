package com.pqsoft.insurebxbc.action;
/**
 *   保险补差收取明细查询
 */
import java.util.Map;

import javax.mail.Flags.Flag;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.insure.service.InsureService;
import com.pqsoft.insurebxbc.service.InsureBXBCDetailService;
import com.pqsoft.insurebxbc.service.InsureBXBCManagementService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class InsureBXBCDetailAction extends Action{
	
	private String basePath = "insure_bxbc/"; 
	
	private InsureBXBCDetailService insureBXBCDetailService = new InsureBXBCDetailService();
	private ManageService mgService = new ManageService();//权限控制
	
	/**
	 *  显示主页面
	 *  根据单号查询明细
	 */
	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("DHID", getNotNullValue(param,"GLIDE_ID"));
		context.put("METHOD", getNotNullValue(param,"METHOD"));
		return new ReplyHtml(VM.html(basePath + "insureBXBCDetailByApplyId.vm", context));
	}
	
	/**
	 * 用于明细分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageDetailData(){
		Map<String,Object> param = _getParameters();
		//数据权限,根据对应的厂商显示信息
		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
		if(supMap != null && supMap.get("SUP_ID") != null){
			param.put("SUP_ID", supMap.get("SUP_ID"));
		}
		//根据参数判断是收款还是付款
		param.put("DHID", getNotNullValue(param,"DHID"));
		if("m1".equals(getNotNullValue(param,"METHOD"))){
			param.put("FUNDS_NAME", "保险补差收款");
		}else{//m2
			param.put("FUNDS_NAME", "保险补差付款");
		}
		Page pagedata = insureBXBCDetailService.getDetailPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 *  解决无值 返回 空字符串的问题
	 */
	private String getNotNullValue(Map map,String key){
		 return map.containsKey(key) ? map.get(key).toString() : "";
	}
	
}

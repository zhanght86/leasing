package com.pqsoft.insure.action;
/**
 *  险种管理
 *  @author 韩晓龙
 */
import java.util.Map;

import com.pqsoft.insure.service.InsureTypeManagementService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class InsureTypeManagementAction extends Action{

	private String basePath = "insure/"; 
	private InsureTypeManagementService service = new InsureTypeManagementService();
	
	@Override
	@aPermission(name = { "保险管理", "险种配置", "列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		return new ReplyHtml(VM.html(basePath + "insureTypeManagementShow.vm", null));
	}
	
	/*
	 * 用于分页查询TODO
	 */
	@aPermission(name = { "保险管理", "险种配置", "列表显示" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/*
	 * 新增一个险种信息
	 */
	@aPermission(name = { "保险管理", "险种配置", "新增" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doAddInsureType() {
		Map<String,Object> param = _getParameters();
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		service.insertInsureType(param);
		return  new ReplyAjax(true,"");
	}
	
	/*
	 * 修改一个险种信息
	 */
	@aPermission(name = { "保险管理", "险种配置", "修改" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doUpdateInsureType() {
		Map<String,Object> param = _getParameters();
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		service.updateInsureType(param);
		//TODO 还应考虑删除保险公司与险种关系表 的关联关系
		return  new ReplyAjax(true,"");
	}
	
	/*
	 * 删除一个险种信息
	 */
	@aPermission(name = { "保险管理", "险种配置", "删除" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doDeleteInsureType() {
		Map<String,Object> param = _getParameters();
		service.deleteInsureType(param);
		//TODO 还应考虑删除保险公司与险种关系表 的关联关系
		return  new ReplyAjax(true,"");
	}

}

package com.pqsoft.custTour.action;
/**
 *   客户巡视
 */
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.custTour.service.CustTourService;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class CustTourAction extends Action{
	
	private String basePath = "custTour/"; 
	private CustTourService service = new CustTourService();
	
	/**
	 *  显示主页面
	 */
	@Override
	@aPermission(name = { "债权管理", "客户巡视", "主页面"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		return new ReplyHtml(VM.html(basePath + "custTour.vm", null));
	}
	
	/**
	 * 用于分页查询
	 */
	@aPermission(name = { "债权管理", "客户巡视", "主页面分页查询"})
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
/////////////////////////////////////设备巡视部分开始///////////////////////////////////////////////////////////	
	/**
	 *  跳转至添加设备巡视记录页面
	 */
	@aPermission(name = { "债权管理", "客户巡视", "跳转添加设备巡视记录页面"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowAddEquipRecord() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PROJ_ID", param.get("PROJ_ID"));
		return new ReplyHtml(VM.html(basePath + "custTourAddEquipRecord.vm", context));
	}
	
	/**
	 * 用于 添加设备巡视记录
	 */
	@aPermission(name = { "债权管理", "客户巡视", "添加设备巡视记录" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doAddEquipTour() {
		boolean flag = false;
		String msg = "";
		Map<String,Object> param = _getParameters();
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		int i = service.insertEquipTour(param);
		if (i>0) {
			flag = true;
			msg = "记录添加成功!";
		}else {
			flag = false;
			msg = "记录添加失败,请重试!";
		}
		return  new ReplyAjax(flag,msg);
	}
	
	/**
	 * 用于设备巡视分页查询
	 */
	@aPermission(name = { "债权管理", "客户巡视", "设备巡视分页查询"})
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageDataEquip(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageDataEquip(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 *  跳转至查看设备巡视记录页面
	 */
	@aPermission(name = { "债权管理", "客户巡视", "跳转查看设备巡视记录页面"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowDetailEquipRecord() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PROJ_ID", param.get("PROJ_ID"));
		return new ReplyHtml(VM.html(basePath + "custTourShowEquipRecord.vm", context));
	}
	
	/**
	 * 用于删除设备巡视记录
	 */
	@aPermission(name = { "债权管理", "客户巡视", "删除设备巡视记录" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doDeleteEquipTour() {
		boolean flag = false;
		String msg = "";
		Map<String,Object> param = _getParameters();
		int i = service.deleteEquipTour(param);
		if (i>0) {
			flag = true;
			msg = "记录删除成功!";
		}else {
			flag = false;
			msg = "记录删除失败,请重试!";
		}
		return  new ReplyAjax(flag,msg);
	}
/////////////////////////////////////设备巡视部分结束///////////////////////////////////////////////////////////		
/////////////////////////////////////交往巡视部分开始///////////////////////////////////////////////////////////	
	/**
	 *  跳转至添加交往巡视记录页面
	 */
	@aPermission(name = { "债权管理", "客户巡视", "跳转添加交往巡视记录页面"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowAddContactRecord() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PROJ_ID", param.get("PROJ_ID"));
		return new ReplyHtml(VM.html(basePath + "custTourAddContactRecord.vm", context));
	}
	
	/**
	 * 用于 添加交往巡视记录
	 */
	@aPermission(name = { "债权管理", "客户巡视", "添加交往巡视记录" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doAddContactTour() {
		boolean flag = false;
		String msg = "";
		Map<String,Object> param = _getParameters();
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		int i = service.insertContactTour(param);
		if (i>0) {
			flag = true;
			msg = "记录添加成功!";
		}else {
			flag = false;
			msg = "记录添加失败,请重试!";
		}
		return  new ReplyAjax(flag,msg);
	}
	
	/**
	 * 用于交往巡视分页查询
	 */
	@aPermission(name = { "债权管理", "客户巡视", "交往巡视分页查询"})
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageDataContact(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageDataContact(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 *  跳转至查看交往巡视记录页面
	 */
	@aPermission(name = { "债权管理", "客户巡视", "跳转查交往巡视记录页面"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowDetailContactRecord() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PROJ_ID", param.get("PROJ_ID"));
		return new ReplyHtml(VM.html(basePath + "custTourShowContactRecord.vm", context));
	}
	
	/**
	 * 用于删除交往巡视记录
	 */
	@aPermission(name = { "债权管理", "客户巡视", "删除交往巡视记录" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doDeleteContactTour() {
		boolean flag = false;
		String msg = "";
		Map<String,Object> param = _getParameters();
		int i = service.deleteContactTour(param);
		if (i>0) {
			flag = true;
			msg = "记录删除成功!";
		}else {
			flag = false;
			msg = "记录删除失败,请重试!";
		}
		return  new ReplyAjax(flag,msg);
	}
/////////////////////////////////////交往巡视部分结束///////////////////////////////////////////////////////////		
/////////////////////////////////////理赔巡视部分开始///////////////////////////////////////////////////////////	
	/**
	 *  跳转至添加理赔巡视记录页面
	 */
	@aPermission(name = { "债权管理", "客户巡视", "跳转添加理赔巡视记录页面"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowAddClaimsRecord() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PROJ_ID", param.get("PROJ_ID"));
		return new ReplyHtml(VM.html(basePath + "custTourAddClaimsRecord.vm", context));
	}
	
	/**
	 * 用于 添加理赔巡视记录
	 */
	@aPermission(name = { "债权管理", "客户巡视", "添加理赔巡视记录" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doAddClaimsTour() {
		boolean flag = false;
		String msg = "";
		Map<String,Object> param = _getParameters();
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		int i = service.insertClaimsTour(param);
		if (i>0) {
			flag = true;
			msg = "记录添加成功!";
		}else {
			flag = false;
			msg = "记录添加失败,请重试!";
		}
		return  new ReplyAjax(flag,msg);
	}
	
	/**
	 * 用于理赔巡视分页查询
	 */
	@aPermission(name = { "债权管理", "客户巡视", "理赔巡视分页查询"})
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageDataClaims(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageDataClaims(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 *  跳转至查看理赔巡视记录页面
	 */
	@aPermission(name = { "债权管理", "客户巡视", "跳转查理赔巡视记录页面"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowDetailClaimsRecord() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PROJ_ID", param.get("PROJ_ID"));
		return new ReplyHtml(VM.html(basePath + "custTourShowClaimsRecord.vm", context));
	}
	
	/**
	 * 用于删除理赔巡视记录
	 */
	@aPermission(name = { "债权管理", "客户巡视", "删除理赔巡视记录" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doDeleteClaimsTour() {
		boolean flag = false;
		String msg = "";
		Map<String,Object> param = _getParameters();
		int i = service.deleteClaimsTour(param);
		if (i>0) {
			flag = true;
			msg = "记录删除成功!";
		}else {
			flag = false;
			msg = "记录删除失败,请重试!";
		}
		return  new ReplyAjax(flag,msg);
	}
/////////////////////////////////////理赔巡视部分结束///////////////////////////////////////////////////////////		
}

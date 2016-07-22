package com.pqsoft.insure.action;
/**
 *   保险公司管理
 *   @author 韩晓龙
 */
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.insure.service.InsureCompanyService;
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

public class InsureCompanyAction extends Action{
	
	private String basePath = "insure/"; 
	private InsureCompanyService service = new InsureCompanyService();
	private InsureTypeManagementService insureTypeService = new InsureTypeManagementService();
	
	/**
	 *  显示主页面
	 */
	@Override
	@aPermission(name = { "合作机构", "保险公司", "列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("insureType", insureTypeService.getAll());
		return new ReplyHtml(VM.html(basePath + "insureCompanyShow.vm", context));
	}
	/**
	 * 用于分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	/**
	 * 用于得到 添加保险公司 页面
	 */
	@aPermission(name = { "合作机构", "保险公司", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowInsureCompanyAdd() {
		return new ReplyHtml(VM.html(basePath + "insureCompanyAdd.vm", null));
	}
	/**
	 * 用于 添加保险公司 信息
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doAddInsureCompany() {
		Map<String,Object> param = _getParameters();
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		service.insertInsureCompany(param);
		return  new ReplyAjax(true,"");
	}
	
	/**
	 *  打开修改保险公司的详细信息的页面
	 */
	@aPermission(name = { "合作机构", "保险公司", "修改" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowInsureCompanyAlter() {
		Map<String,Object> param = _getParameters();
		Map map = (Map)service.selectInsureCompanyById(param);
		VelocityContext context = new VelocityContext();
		context.put("Company", map);//将map直接给context
		return new ReplyHtml(VM.html(basePath + "insureCompanyAlter.vm", context));
	}
	
	/**
	 * 用于 修改保险公司 信息
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doUpdateInsureCompany() {
		Map<String,Object> param = _getParameters();
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		service.updateInsureCompany(param);
		return  new ReplyAjax(true,"");
	}
	

	/**
	 * 用于 配置保险公司 的险种信息
	 */
	@aPermission(name = { "合作机构", "保险公司", "配置险种" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply configInsureType() {
		Map<String,Object> param = _getParameters();//得到ALLINSURETYPE
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		//1先删除原有中间表中的数据
		service.deleteMiddleTable(param);
		//2往中间表中插入新的数据
		service.insertMiddleTable(param);
		return  new ReplyAjax(true,"");
	}
	
	/**
	 * 用于 删除保险公司 信息
	 */
	@aPermission(name = { "合作机构", "保险公司", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doDeleteInsureCompany() {
		Map<String,Object> param = _getParameters();
		//1 删除原有中间表中的数据
		service.deleteMiddleTable(param);
		//2 删除保险公司信息
		service.deleteInsureCompany(param);
		return  new ReplyAjax(true,"");
	}
	
	/**
	 *  查看一个保险公司的详细信息
	 */
	@SuppressWarnings("unchecked")
	@aPermission(name = { "合作机构", "保险公司", "查看" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowInsureCompanyDetail() {
		Map<String,Object> param = _getParameters();
		//1.查询
		Map map = (Map)service.selectInsureCompanyById(param);
		VelocityContext context = new VelocityContext();
		context.put("Company", map);//将map直接给context
		//2.查询中间表 得到关联的险种信息
		context.put("InsuranceType", service.selectInsureTypeByCid(param));
		System.out.println("InsuranceType====" + context.get("InsuranceType"));
		return new ReplyHtml(VM.html(basePath + "insureCompanyDetailShow.vm", context));
	}
	
}

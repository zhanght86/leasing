package com.pqsoft.customers.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.velocity.VelocityContext;

import com.pqsoft.customers.service.CustMainRelationService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.lmrm.service.LeaseMortgageService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

public class CustMainRelationAction extends Action{
	
	private final String pagePath = "customers/enterpriseInfoOther/";

	@Override
	public Reply execute() {
		return null;
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息" })
	public Reply findMgRelation(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath+"toMgEnterpriseDetail.vm", context));
	}
	
	/******************************************************企业关联***************************************************************************/
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息","企业关联页面" })
	public Reply toMgEnterpriseRel() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("relationToCust", new DataDictionaryMemcached().get("与客户关系L"));
		context.put("rc_unit", new DataDictionaryMemcached().get("货币种类"));
		return new ReplyHtml(VM.html(pagePath+"BusinessRelation/toMgBusinessRelation.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业关联信息(查询)" })
	public Reply findMgEnterpriseRel() {
		Map<String,Object> map = _getParameters();
		CustomersService service = new CustomersService();
		return new ReplyAjaxPage(service.getCustLink(map));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业关联-修改" })
	public Reply getCustRelation() {
		Map<String,Object> map = _getParameters();
		CustomersService service = new CustomersService();
		VelocityContext context = new VelocityContext();
		context.put("business", service.getCustRelation(map));
		context.put("relationCu", new DataDictionaryMemcached().get("与客户关系L"));
		context.put("rc_unit", new DataDictionaryMemcached().get("货币种类"));
		return new ReplyAjax(VM.html(pagePath+"BusinessRelation/toUpdateBusiness.vm", context));
	}

	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","企业关联信息", "企业关联-修改(操作)" })
	public Reply doUpdateBusiness() {
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustomersService service = new CustomersService();
		int i = service.doUpdateCustInfo(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "企业关系修改成功！！";
		}else {
			flag = false;
			msg = "企业关系修改成功！！";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","企业关系修改",map.get("USERCODE").toString()));
	}
	
	
	/******************************************************企业团队***************************************************************************/
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","企业关联信息", "企业团队页面" })
	public Reply toMgEnterpriseTeam() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("id_typeL", new DataDictionaryMemcached().get("证件类型"));
		context.put("nationL", new DataDictionaryMemcached().get("民族"));
		context.put("degree_edu", new DataDictionaryMemcached().get("文化程度"));
		context.put("duty", new DataDictionaryMemcached().get("职务"));
		return new ReplyHtml(VM.html(pagePath+"EnterpriseTeam/toMgEnterpriseTeam.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业团队页面(查看)" })
	public Reply findMgEnterpriseTeam() {
		Map<String,Object> map = _getParameters();
		CustMainRelationService service = new CustMainRelationService();
		return new ReplyAjaxPage(service.getComDetail(map));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业团队-添加" })
	public Reply doInsertCpmpanyTeam() {
		List<FileItem> fileList = _getFileItem();
		Map<String,Object> map = (Map<String, Object>) new LeaseMortgageService().uploadFileForOne(fileList) ;
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doInsertComTeam(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "添加企业团队成功";
		}else {
			flag = false;
			msg = "添加企业团队失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","添加企业团队",map.get("USERCODE").toString()));
	}//toTeamDesc
	
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业团队-团队描述" })
	public Reply toTeamDesc() {
		Map<String, Object> params = _getParameters();
		VelocityContext context = new VelocityContext();
		CustomersService service = new CustomersService();
		Map teamDesc = service.getTeamDesc(params);
		if(StringUtils.isNotBlank(teamDesc)&&StringUtils.isNotBlank(teamDesc.get("FILE_PATH"))) {
			String path = teamDesc.get("FILE_PATH").toString();
			String filename = path.substring(path.lastIndexOf("/")+1);
			teamDesc.put("FILE_NAME", filename);
		}
		context.put("teamDesc", teamDesc);
		return new ReplyHtml(VM.html(pagePath+"EnterpriseTeam/toMgEnterpriseTeamDesc.vm", context));
	}
	
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业团队-团队描述保存" })
	public Reply doTeamDesc() {
		List<FileItem> fileList = _getFileItem();
		Map<String,Object> map = (Map<String, Object>) new LeaseMortgageService().uploadFileForOne(fileList) ;
		map.put("USERCODE",Security.getUser().getCode());
		CustomersService service = new CustomersService();
		int i = 0;
		if(StringUtils.isNotBlank(map.get("ID"))) {
			i = service.updateTeamDesc(map);
		} else {
			i = service.insertTeamDesc(map);
		}
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "保存企业团队描述成功";
		}else {
			flag = false;
			msg = "保存企业团队描述失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","保存企业团队描述",map.get("USERCODE").toString()));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","企业关联信息", "企业团队-删除" })
	public Reply doDelComTeam() {
		Map<String,Object> map = _getParameters();
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doDelComTeam(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "企业团队删除成功";
		}else {
			flag = false;
			msg = "企业团队删除失败";
		}
		map.put("USERID",Security.getUser().getId());
		return new ReplyAjax(flag,null).addOp(new OpLog("客户资料管理", "删除企业团队",msg));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","企业关联信息", "企业团队-修改" })
	public Reply getComTeam(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		CustMainRelationService service = new CustMainRelationService();
		context.put("param", map);
		context.put("id_typeL", new DataDictionaryMemcached().get("证件类型"));
		context.put("nationL", new DataDictionaryMemcached().get("民族"));
		context.put("degree_edu", new DataDictionaryMemcached().get("文化程度"));
		context.put("duty", new DataDictionaryMemcached().get("职务"));
		Map comTeam = (Map)service.getComTeam(map);
		if(StringUtils.isNotBlank(comTeam)&&StringUtils.isNotBlank(comTeam.get("FILE_PATH"))) {
			String path = comTeam.get("FILE_PATH").toString();
			String filename = path.substring(path.lastIndexOf("/")+1);
			comTeam.put("FILE_NAME", filename);
		}
		context.put("comTeam", comTeam);
		return new ReplyHtml(VM.html(pagePath+"EnterpriseTeam/toUpdateEnterpriseTeam.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业团队-修改(操作)" })
	public Reply doUpdateComTeam(){
		List<FileItem> fileList = _getFileItem();
		Map<String,Object> map = (Map<String, Object>) new LeaseMortgageService().uploadFileForOne(fileList) ;
//		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.updateComTeam(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "修改企业团队成功";
		}else {
			flag = false;
			msg = "修改企业团队失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","修改企业团队",map.get("USERCODE").toString()));
	}
	
	/******************************************************公司股东及份额***************************************************************************/
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "股东及份额页面" })
	public Reply toMgHolder() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath+"Holder/toMgHolder.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "股东及份额页面" })
	public Reply findMgHolder() {
		Map<String,Object> map = _getParameters();
		CustMainRelationService service = new CustMainRelationService();
		return new ReplyAjaxPage(service.getHolderDetail(map));
	}
	
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业股东及份额-添加" })
	public Reply doInsertHolder() {
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doInsertHolder(map);
		boolean flag = false;
		if(i>0){
			flag = true;
		}else {
			flag = false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("客户资料管理","添加企业股东及份额",map.get("USERCODE").toString()));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业股东及份额-删除" })
	public Reply doDelHolder() {
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doDelHolder(map);
		boolean flag = false;
		if(i>0){
			flag = true;
		}else {
			flag = false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("客户资料管理","删除企业股东及份额",map.get("USERCODE").toString()));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业股东及份额数据-修改" })
	public Reply getHolder(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		CustMainRelationService service = new CustMainRelationService();
		context.put("param", map);
		context.put("holder", service.getHolder(map));
		return new ReplyHtml(VM.html(pagePath+"Holder/toUpdateHolder.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "企业关联信息", "企业股东及份额-修改(操作)" })
	public Reply doUpdateHolder(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.updateHolder(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "修改企业股东及份额成功";
		}else {
			flag = false;
			msg = "修改企业股东及份额失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","修改企业股东及份额",map.get("USERCODE").toString()));
	}
	
	/*******************************************************客户从业历程**************************************************************************/
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "查看","客户从业历程" })
	public Reply findMgCustWorkExp() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath+"toMgCustWorkExp.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "修改","客户从业历程" })
	public Reply findMgCustWorkExp1() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath+"toMgCustWorkExp.vm", context));
	}
	
	/*******************************************************客户合作伙伴**************************************************************************/
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "客户合作伙伴" })
	public Reply toMgCustPartner() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("com_type", new DataDictionaryMemcached().get("公司性质"));
		return new ReplyHtml(VM.html(pagePath+"Partner/toMgCustPartner.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "客户合作伙伴" })
	public Reply findMgCustPartner() {
		Map<String,Object> map = _getParameters();
		CustMainRelationService service = new CustMainRelationService();
		return new ReplyAjaxPage(service.getParDetail(map));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "合作伙伴-添加" })
	public Reply doCustPartners() {
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doInsertPartners(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "添加合作伙伴成功";
		}else {
			flag = false;
			msg = "添加合作伙伴失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","添加合作伙伴",map.get("USERCODE").toString()));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "合作伙伴-删除" })
	public Reply doDelPartners() {
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doDelPartners(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "删除合作伙伴成功";
		}else {
			flag = false;
			msg = "删除合作伙伴成功";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","删除合作伙伴",map.get("USERCODE").toString()));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "合作伙伴数据-修改" })
	public Reply getPartners(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		CustMainRelationService service = new CustMainRelationService();
		context.put("param", map);
		context.put("com_type", new DataDictionaryMemcached().get("公司性质"));
		context.put("partners", service.getPartners(map));
		return new ReplyHtml(VM.html(pagePath+"Partner/toUpdatePartners.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "合作伙伴-修改(操作)" })
	public Reply doUpdatePartners(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.updatePartners(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "修改合作伙伴成功";
		}else {
			flag = false;
			msg = "修改合作伙伴失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","修改合作伙伴",map.get("USERCODE").toString()));
	}
	
	/*******************************************************客户投资情况*************************************************************************/
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "客户投资情况页面" })
	public Reply toMgCustInvestInfo() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("com_type", new DataDictionaryMemcached().get("公司性质"));
		context.put("nation", new DataDictionaryMemcached().get("民族"));
		return new ReplyHtml(VM.html(pagePath+"CustInvestInfo/toMgCustInvestInfo.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "客户投资情况" })
	public Reply findMgCustInvestInfo() {
		Map<String,Object> map = _getParameters();
		CustMainRelationService service = new CustMainRelationService();
		return new ReplyAjaxPage(service.getTouziDetail(map));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "投资情况-添加" })
	public Reply doInsertInvestInfo() {
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doInsertInvestInfo(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "添加投资情况成功";
		}else {
			flag = false;
			msg = "添加投资情况失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","添加",map.get("USERCODE").toString()));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "投资情况-删除" })
	public Reply doDelInvest() {
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doDelInvest(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "删除投资情况成功";
		}else {
			flag = false;
			msg = "删除投资情况失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","删除投资情况",map.get("USERCODE").toString()));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "投资情况数据-修改" })
	public Reply getInvest(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		CustMainRelationService service = new CustMainRelationService();
		context.put("param", map);
		context.put("com_type", new DataDictionaryMemcached().get("公司性质"));
		context.put("nation", new DataDictionaryMemcached().get("民族"));
		context.put("invest", service.getInfoDetail(map));
		return new ReplyHtml(VM.html(pagePath+"CustInvestInfo/toUpdateInversInfo.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "投资情况-修改(操作)" })
	public Reply doUpdateInvest(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doUpdateInvest(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "修改投资情况成功";
		}else {
			flag = false;
			msg = "修改投资情况失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","修改投资情况",map.get("USERCODE").toString()));
	}
	
	/*******************************************************客户从业经验*************************************************************************/
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "客户从业经验页面" })
	public Reply toMgCustExperience() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("project_type", new DataDictionaryMemcached().get("项目类型"));
		return new ReplyHtml(VM.html(pagePath+"CustExperience/toMgCustExperience.vm", context));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "客户从业经验" })
	public Reply findMgCustExperience() {
		Map<String,Object> map = _getParameters();
		CustMainRelationService service = new CustMainRelationService();
		return new ReplyAjaxPage(service.getWorkExpDetail(map));
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "从业经验-添加" })
	public Reply doInsertWoekExp() {
		Map<String,Object> map = _getParameters();
		map=JSONObject.fromObject(map.get("param"));
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doInsertWoekExp(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "添加从业经验成功";
		}else {
			flag = false;
			msg = "添加从业经验失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","添加从业经验",map.get("USERCODE").toString()));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "从业经验数据-修改" })
	public Reply getUpdateExperience(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		CustMainRelationService service = new CustMainRelationService();
		context.put("param", map);
		context.put("project_type", new DataDictionaryMemcached().get("项目类型"));
		context.put("workExp", service.getExperience(map));
		return new ReplyHtml(VM.html(pagePath+"CustExperience/toUpdateExperience.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "从业经验-修改(操作)" })
	public Reply doUpdateExperence(){
		Map<String,Object> map = _getParameters();
		map=JSONObject.fromObject(map.get("param"));
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.doUpdateExperence(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "修改从业经验成功";
		}else {
			flag = false;
			msg = "修改从业经验失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","修改从业经验",map.get("USERCODE").toString()));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "客户从业历程", "从业经验-删除" })
	public Reply delWoekExp() {
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());
		CustMainRelationService service = new CustMainRelationService();
		int i = service.delWoekExp(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "删除从业经验成功";
		}else {
			flag = false;
			msg = "删除从业经验失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户资料管理","删除从业经验",map.get("USERCODE").toString()));
	}
}

package com.pqsoft.target.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.base.channel.service.CreditAmountManagerService;
import com.pqsoft.base.grantCredit.service.SupplierCreditService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.base.suppliersInfo.service.SuppliersInfoService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.target.service.TargetCompanyService;

public class TargetCompanyAction extends Action {
	private SuppliersService service = new SuppliersService();
	private SuppliersInfoService suppliersInfoService = new SuppliersInfoService();
	private TargetCompanyService tService = new TargetCompanyService();
	private String path = "mgTargets/company/";

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"指标管理", "指标公司管理", "列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply execute() {
		return new ReplyHtml(VM.html(path + "toMgTargetCompany.vm", null));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		param.put("DIC_SUP_STATUS", "供应商状态");
		Page pagedata = tService.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "指标管理", "指标公司管理", "添加" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply toAddCompany() {
		VelocityContext context = new VelocityContext();
		//context.put("allArea", service.getAllArea());
		context.put("allArea", new AreaService().getAllQuYu());
		context.put("companys", service.getAlllCompany());
		List taxQual = (List) new DataDictionaryMemcached().get("纳税资质");
		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		context.put("taxQual", taxQual);
		return new ReplyHtml(VM.html(path + "toAddCompany.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "指标管理", "指标公司管理", "查看" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply toViewDetail() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("supplier", service.getOneSupplier(param));
		context.put("LinkMans", service.getLinkManList(param));
		param.put("INVEST_TYPE", "0");
		context.put("Naturals", service.getInvestsByType(param));
		param.put("INVEST_TYPE", "1");
		context.put("Legals", service.getInvestsByType(param));
		context.put("files", service.findSupFileUploads(param.get("SUP_ID").toString()));
		context.put("allArea", service.getAllArea());
		context.put("supplierInfo", suppliersInfoService.getOneSupplierInfo(param));
		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		return new ReplyHtml(VM.html(path + "toViewDetail.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "指标管理", "指标公司管理", "修改" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toUpTargetMain() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toUpTargetMain.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "指标管理", "指标公司管理", "修改页面基本信息" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply modifySupPage() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		Map<String, Object> suppMess = service.getOneSupplier(param);
		context.put("supplier", suppMess);
		context.put("LinkMans", service.getLinkManList(param));
		param.put("INVEST_TYPE", "0");
		context.put("Naturals", service.getInvestsByType(param));
		param.put("INVEST_TYPE", "1");
		context.put("Legals", service.getInvestsByType(param));
		context.put("files", service.findSupFileUploads(param.get("SUP_ID").toString()));
		context.put("allArea", new AreaService().getAllQuYu());
		Map<String, Object> paramtwo = new HashMap<String, Object>();
		paramtwo.put(
				"PARENT_ID",
				suppMess.containsKey("AREA_ID") && suppMess.get("AREA_ID") != null && !"".equals(suppMess.get("AREA_ID").toString()) ? suppMess.get(
						"AREA_ID").toString() : "");
		
		context.put("areaProvs", service.getQuYuSubset(paramtwo));
		paramtwo.put("PARENT_ID", suppMess.containsKey("PROV_ID") && suppMess.get("PROV_ID") != null ? suppMess.get("PROV_ID").toString() : "");
		context.put("provCity", service.getAreaDownByParentId(paramtwo));
		context.put("companys", service.getAlllCompany());
		List taxQual = (List) new DataDictionaryMemcached().get("纳税资质");
		context.put("taxQual", taxQual);
		List statusMemo = (List) new DataDictionaryMemcached().get("供应商状态");
		context.put("supplierStatus", statusMemo);

		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		context.put("group", service.findGroup(param));
		context.put("supplierInfo", suppliersInfoService.getOneSupplierInfo(param));
		return new ReplyHtml(VM.html(path + "toUpTarget.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "指标管理", "指标公司管理", "保存供应商信息" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply saveTargerMess() {
		Map<String, Object> params = _getParameters();
		Map<String, Object> param = JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		Boolean flag = true;
		int result = 0;
		String msg = "";
		if (param.containsKey("SUP_ID") && param.get("SUP_ID") != null && !"".equals(param.get("SUP_ID").toString())) {
			result = service.updateSupplier(param);
			int count = suppliersInfoService.selectCount(param);

			Map<String, Object> suppliersInfo = new HashMap<String, Object>();
			suppliersInfo.put("TYPE", param.get("TYPE"));
			suppliersInfo.put("BUSINESS_TYPE", param.get("BUSINESS_TYPE"));
			suppliersInfo.put("IS_GUARANTEE", param.get("IS_GUARANTEE"));
			suppliersInfo.put("REGISTE_DATE", param.get("REGISTE_DATE"));
			suppliersInfo.put("REGISTE_PHONE", param.get("REGISTE_PHONE"));
			suppliersInfo.put("NUMBER_PER", param.get("NUMBER_PER"));
			suppliersInfo.put("RATEPAYING", param.get("RATEPAYING"));
			suppliersInfo.put("MAIN_BUSINESS", param.get("MAIN_BUSINESS"));
			suppliersInfo.put("SCALE_ENTERPRISE", param.get("SCALE_ENTERPRISE"));
			suppliersInfo.put("WORK_ADDRESS", param.get("WORK_ADDRESS"));
			suppliersInfo.put("REMARK", param.get("REMARK"));
			suppliersInfo.put("SUP_ID", param.get("SUP_ID"));
			if (count > 0) {
				suppliersInfoService.updateSupplierInfo(suppliersInfo);
			} else {
				suppliersInfoService.addSupplierInfo(suppliersInfo);
			}
		} else {
			int SUP_ID = service.getSuppSeq();
			param.put("SUP_ID", SUP_ID);
			param.put("SUP_TYPE", 1);
			result = service.addSupplier(param);
			// 同时添加一条授信记录
			Map<String, Object> creditMess = new HashMap<String, Object>();
			creditMess.put("SUP_ID", SUP_ID);
			creditMess.put("COACH_LIMIT", 0.25);
			creditMess.put("SALE_NAME", Security.getUser().getName());
			creditMess.put("COACH_REMIND_MONEY", Double.parseDouble("1000000"));
			creditMess.put("POSITIVE_REMIND_MONEY", Double.parseDouble("5000000"));
			SupplierCreditService creditService = new SupplierCreditService();
			creditService.addCreditMess(creditMess);
			// 添加
			Map<String, Object> creditNewMess = new HashMap<String, Object>();
			creditNewMess.put("SUP_ID", SUP_ID);
			creditNewMess.put("INITIAL_AMOUNT", "0");
			creditNewMess.put("STANDARD_AMOUNT", "0");
			creditNewMess.put("CREATE_USER", Security.getUser().getName());
			creditNewMess.put("CREDIT_REMIND_AMOUNT", Double.parseDouble("1000000"));
			creditNewMess.put("EXPAND_MULTIPLE", "1");
			CreditAmountManagerService creditNewService = new CreditAmountManagerService();
			creditNewService.addCreditMess(creditNewMess);
			// 添加供应商信息
			Map<String, Object> suppliersInfo = new HashMap<String, Object>();
			suppliersInfo.put("TYPE", param.get("TYPE"));
			suppliersInfo.put("BUSINESS_TYPE", param.get("BUSINESS_TYPE"));
			suppliersInfo.put("IS_GUARANTEE", param.get("IS_GUARANTEE"));
			suppliersInfo.put("REGISTE_DATE", param.get("REGISTE_DATE"));
			suppliersInfo.put("REGISTE_PHONE", param.get("REGISTE_PHONE"));
			suppliersInfo.put("NUMBER_PER", param.get("NUMBER_PER"));
			suppliersInfo.put("RATEPAYING", param.get("RATEPAYING"));
			suppliersInfo.put("MAIN_BUSINESS", param.get("MAIN_BUSINESS"));
			suppliersInfo.put("SCALE_ENTERPRISE", param.get("SCALE_ENTERPRISE"));
			suppliersInfo.put("WORK_ADDRESS", param.get("WORK_ADDRESS"));
			suppliersInfo.put("REMARK", param.get("REMARK"));
			suppliersInfo.put("SUP_ID", SUP_ID);
			suppliersInfoService.addSupplierInfo(suppliersInfo);

		}
		if (result > 0) {
			flag = true;
			msg = "操作成功！";
		} else {
			flag = false;
			msg = "操作失败！";
		}
		return new ReplyAjax(flag, msg);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply getAreaMess() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> resultMess = new HashMap<String, Object>();
		List<Object> listArea = service.getQuYuSubset(param);
		
		resultMess.put("provs", listArea);
		return new ReplyJson(JSONObject.fromObject(resultMess));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply getCityMess() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> resultMess = new HashMap<String, Object>();
		List<Object> listArea = service.getAreaDownByParentId(param);
		
		resultMess.put("provs", listArea);
		return new ReplyJson(JSONObject.fromObject(resultMess));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"指标管理", "指标公司管理", "删除"})
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply delSuppliers() {
		Map<String, Object> param = _getParameters();
		int result = service.delSupplier(param);
		if (result > 0) {
			return new ReplyAjax(true, "删除成功！");
		} else {
			return new ReplyAjax(false, "删除失败！");
		}
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "指标管理", "指标公司管理", "保存投资人" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply saveInvestor() {
		Map<String, Object> params = _getParameters();
		Map<String, Object> param = JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		Boolean flag = true;
		int result = 0;
		String msg = "";
		if (param.containsKey("ID") && param.get("ID") != null && !"".equals(param.get("ID").toString())) {
			result = service.updateInvestor(param);
		} else {
			result = service.addInvestor(param);
		}
		if (result > 0) {
			param.put("ID", "");
			return new ReplyJson(JSONArray.fromObject(service.getInvestsByType(param)));
		} else {
			flag = false;
			msg = "操作失败！";
			return new ReplyAjax(flag, msg);
		}
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "指标管理", "指标公司管理", "删除联系人" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply delLinkMan() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.delOneLinkMan(param);
		if (result > 0) {
			flag = true;
			msg = "删除成功！";
		} else {
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "指标管理", "指标公司管理", "保存联系人" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply saveLinkMan() {
		Map<String, Object> params = _getParameters();
		Map<String, Object> param = JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		Boolean flag = true;
		int result = 0;
		String msg = "";
		if (param.containsKey("LINK_ID") && param.get("LINK_ID") != null && !"".equals(param.get("LINK_ID").toString())) {
			result = service.updataLinkMan(param);
		} else {
			result = service.addLinkMan(param);
		}
		if (result > 0) {
			return new ReplyJson(JSONArray.fromObject(service.getLinkManList(param)));
		} else {
			flag = false;
			msg = "操作失败！";
			return new ReplyAjax(flag, msg);
		}
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "指标管理", "指标公司管理", "删除投资人" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply delInvestor() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.delInvest(param);
		if (result > 0) {
			flag = true;
			msg = "删除成功！";
		} else {
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "指标管理", "指标公司管理", "上传附件" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply uploadFile() {
		int count = 0;
		Map<String, Object> m = _getParameters();
		String suppliers_id = m.get("SUP_ID").toString();
		try {
			Map<String, Object> map = service.uploadFileForOne(SkyEye.getRequest());
			map.put("FIL_MEMO", m.get("FIL_MEMO"));
			map.put("SUP_ID", suppliers_id);
			count = service.addSupFileUpload(map);
		} catch (Exception e) {
			logger.error(e);
		}
		if (count > 0) {
			List<Object> listfile = service.findSupFileUploads(suppliers_id);
			System.out.println(listfile.size());
			return new ReplyJson(JSONArray.fromObject(listfile));
		} else {
			return new ReplyAjax(true, "上传成功！");
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "指标管理", "指标公司管理", "下载附件" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply downLoadSupFile() {
		Map<String, Object> param = _getParameters();
		String file_id = param.get("FIL_ID").toString();
		Map<String, Object> fileMess = (Map<String, Object>) service.findDupFileByID(file_id);
		// path是指欲下载的文件的路径。
		String filePath = fileMess.get("FIL_PATH").toString();
		String fileName = fileMess.get("FIL_NAME").toString();
		File file = new File(filePath);
		return new ReplyFile(file, fileName);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "指标管理", "指标公司管理", "删除附件" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply deleteSupFile() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		String fil_id = param.get("FIL_ID").toString();
		int result = service.deleteSupFile(fil_id);
		if (result > 0) {
			flag = true;
			msg = "删除成功！";
		} else {
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "指标管理", "指标公司管理", "是否受理业务开关" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yx")
	public Reply supTurnSwitch() {
		Map<String, Object> param = _getParameters();
		if (param.containsKey("SUP_SWITCH") && param.get("SUP_SWITCH") != null && "0".equals(param.get("SUP_SWITCH").toString())) {
			param.put("SUP_SWITCH", "1");
		} else if (param.containsKey("SUP_SWITCH") && param.get("SUP_SWITCH") != null) {
			param.put("SUP_SWITCH", "0");
		}
		if (param.containsKey("BALANCE_LOAN_SWITCH") && param.get("BALANCE_LOAN_SWITCH") != null
				&& "0".equals(param.get("BALANCE_LOAN_SWITCH").toString())) {
			param.put("BALANCE_LOAN_SWITCH", "1");
		} else if (param.containsKey("BALANCE_LOAN_SWITCH") && param.get("BALANCE_LOAN_SWITCH") != null) {
			param.put("BALANCE_LOAN_SWITCH", "0");
		}
		if (param.containsKey("IRREGULAR_REPAYMENT_SWITCH") && param.get("IRREGULAR_REPAYMENT_SWITCH") != null
				&& "0".equals(param.get("IRREGULAR_REPAYMENT_SWITCH").toString())) {
			param.put("IRREGULAR_REPAYMENT_SWITCH", "1");
		} else if (param.containsKey("IRREGULAR_REPAYMENT_SWITCH") && param.get("IRREGULAR_REPAYMENT_SWITCH") != null) {
			param.put("IRREGULAR_REPAYMENT_SWITCH", "0");
		}
		if (param.containsKey("B_MODEL_SWITCH") && param.get("B_MODEL_SWITCH") != null && "0".equals(param.get("B_MODEL_SWITCH").toString())) {
			param.put("B_MODEL_SWITCH", "1");
		} else if (param.containsKey("B_MODEL_SWITCH") && param.get("B_MODEL_SWITCH") != null) {
			param.put("B_MODEL_SWITCH", "0");
		}
		if (param.containsKey("SMS_SWITCH") && param.get("SMS_SWITCH") != null && "0".equals(param.get("SMS_SWITCH").toString())) {
			param.put("SMS_SWITCH", "1");
		} else if (param.containsKey("SMS_SWITCH") && param.get("SMS_SWITCH") != null) {
			param.put("SMS_SWITCH", "0");
		}
		if (param.containsKey("CREDIT_SWITCH") && param.get("CREDIT_SWITCH") != null && "0".equals(param.get("CREDIT_SWITCH").toString())) {
			param.put("CREDIT_SWITCH", "1");
		} else if (param.containsKey("CREDIT_SWITCH") && param.get("CREDIT_SWITCH") != null) {
			param.put("CREDIT_SWITCH", "0");
		}
		if (param.containsKey("SCAN_SWITCH") && param.get("SCAN_SWITCH") != null && "0".equals(param.get("SCAN_SWITCH").toString())) {
			param.put("SCAN_SWITCH", "1");
		} else if (param.containsKey("SCAN_SWITCH") && param.get("SCAN_SWITCH") != null) {
			param.put("SCAN_SWITCH", "0");
		}
		if (param.containsKey("DATAFILL_SWITCH") && param.get("DATAFILL_SWITCH") != null && "0".equals(param.get("DATAFILL_SWITCH").toString())) {
			param.put("DATAFILL_SWITCH", "1");
		} else if (param.containsKey("DATAFILL_SWITCH") && param.get("DATAFILL_SWITCH") != null) {
			param.put("DATAFILL_SWITCH", "0");
		}
		if (param.containsKey("YINGYE_STATUS") && param.get("YINGYE_STATUS") != null && "0".equals(param.get("YINGYE_STATUS").toString())) {
			param.put("YINGYE_STATUS", "1");
		} else if (param.containsKey("YINGYE_STATUS") && param.get("YINGYE_STATUS") != null) {
			param.put("YINGYE_STATUS", "0");
		}
		System.out.println("-=====----======---" + param);
		int result = service.supSwitchMethod(param);
		Boolean flag = true;
		String msg = "";
		if (result > 0) {
			flag = true;
			msg = "操作成功！";
		} else {
			flag = false;
			msg = "操作失败！";
		}
		return new ReplyAjax(flag, msg);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toUnderSuppliers(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"toUnderSuppliers.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply underPageData(){
		Map<String,Object> param = _getParameters();
		Page page = service.underPageData(param);
		return new ReplyAjaxPage(page);
	}
}

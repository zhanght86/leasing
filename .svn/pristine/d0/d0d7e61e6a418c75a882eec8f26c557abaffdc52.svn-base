package com.pqsoft.base.sp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.base.channel.service.CreditAmountManagerService;
import com.pqsoft.base.grantCredit.service.SupplierCreditService;
import com.pqsoft.base.sp.service.SpService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class SpAction extends Action{
	private SpService service = new SpService();
	private String path = "base/sp/";

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "列表显示" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("Param", param);
		return new ReplyHtml(VM.html(path + "spManager.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "列表显示" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		param.put("DIC_SUP_STATUS", "供应商状态");
		param.put("SUP_TYPE", 2);
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "查看详细页面" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply getShowDetail() {
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
		return new ReplyHtml(VM.html(path + "detailSp.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "进入添加页面" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply addSupPage() {
		VelocityContext context = new VelocityContext();
		AreaService area = new AreaService();
//		context.put("allArea", service.getAllArea());
		context.put("allArea", area.getAllQuYu());
		context.put("companys", service.getAlllCompany());
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("SUP_TYPE", 1);
//		context.put("allSupplier", service.getAllSupplier(param));
		List taxQual = (List) new DataDictionaryMemcached().get("纳税资质");
		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		context.put("taxQual", taxQual);
		//所有省份
		context.put("getProvince", area.getAllProvince());
		return new ReplyHtml(VM.html(path + "updateSp.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "对应供应商" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply getAllSupplier() {
		Map<String, Object> param = _getParameters();
		param.put("SUP_TYPE", 1);
		List<Object> allSupplier = service.getAllSupplier(param);
		return new ReplyAjax(allSupplier);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "进入修改页面" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply modifySupPage() {
		VelocityContext context = new VelocityContext();
		AreaService area = new AreaService();
		Map<String, Object> param = _getParameters();
		Map<String, Object> suppMess = service.getOneSupplier(param);
		context.put("supplier", suppMess);
		context.put("LinkMans", service.getLinkManList(param));
		param.put("INVEST_TYPE", "0");
		context.put("Naturals", service.getInvestsByType(param));
		param.put("INVEST_TYPE", "1");
		context.put("Legals", service.getInvestsByType(param));
		context.put("files", service.findSupFileUploads(param.get("SUP_ID").toString()));
		context.put("allArea", area.getAllQuYu());
		Map<String,Object> paramtwo = new HashMap<String, Object>();
		paramtwo.put("PARENT_ID", suppMess.containsKey("AREA_ID") && suppMess.get("AREA_ID") !=null && !"".equals(suppMess.get("AREA_ID").toString())? suppMess.get("AREA_ID").toString() : "");
		context.put("areaProvs", service.getQuYuSubset(paramtwo));
		paramtwo.put("PARENT_ID", suppMess.containsKey("PROV_ID") && suppMess.get("PROV_ID") != null ? suppMess.get("PROV_ID").toString() : "");
		context.put("provCity", service.getAreaDownByParentId(paramtwo));
		List taxQual = (List) new DataDictionaryMemcached().get("纳税资质");
		context.put("taxQual", taxQual);
		List statusMemo = (List) new DataDictionaryMemcached().get("供应商状态");
		context.put("supplierStatus", statusMemo);
		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		
		Map<String,Object> infoMess = service.getOneSupplierInfo(param);
		context.put("supplierInfo", infoMess);
		//所有省份
		context.put("getProvince", area.getAllProvince());
		//常住地址省市区
		paramtwo.put("PARENT_ID", suppMess.containsKey("SUB_LEGAL_PROV")
				&&suppMess.get("SUB_LEGAL_PROV") !=null
				&&!"".equals(suppMess.get("SUB_LEGAL_PROV").toString()) 
				? suppMess.get("SUB_LEGAL_PROV").toString() : "");
		context.put("subCity",area.getSubset(paramtwo));
		paramtwo.put("PARENT_ID", suppMess.containsKey("SUB_LEGAL_CITY")
				&&suppMess.get("SUB_LEGAL_CITY")!=null
				? suppMess.get("SUB_LEGAL_CITY").toString():"");
		context.put("subArea", area.getSubset(paramtwo));
		//副表办公地址省市区
		if(infoMess!=null){
			paramtwo.put("PARENT_ID", infoMess.containsKey("WORK_PROV")
					&&infoMess.get("WORK_PROV") !=null
					&&!"".equals(infoMess.get("WORK_PROV").toString()) 
					? infoMess.get("WORK_PROV").toString() : "");
			context.put("workCity",area.getSubset(paramtwo));
			paramtwo.put("PARENT_ID", infoMess.containsKey("WORK_CITY")
					&&infoMess.get("WORK_CITY")!=null
					? infoMess.get("WORK_CITY").toString():"");
			context.put("workArea", area.getSubset(paramtwo));
		}
		return new ReplyHtml(VM.html(path + "updateSp.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "保存" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply saveSpMess() {
		Map<String, Object> params = _getParameters();
		Map<String, Object> param = JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		Boolean flag = true;
		int result = 0;
		String msg = "";
		if (param.containsKey("SUP_ID") && param.get("SUP_ID") != null && !"".equals(param.get("SUP_ID").toString())) {
			result = service.updateSupplier(param);
			
			int count = service.selectSpInfo(param);
			
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
			suppliersInfo.put("WORK_PROV", param.get("WORK_PROV"));
			suppliersInfo.put("WORK_CITY", param.get("WORK_CITY"));
			suppliersInfo.put("WORK_AREA", param.get("WORK_AREA"));
			if (count > 0) {
				service.updateSupplierInfo(suppliersInfo);
			} else {
				service.addSupplierInfo(suppliersInfo);
			}
			
		} else {
			int SUP_ID = service.getSuppSeq();
			param.put("SUP_ID", SUP_ID);
			// 供应商类型 2:SP
			param.put("SUP_TYPE", 2);
			result = service.addSupplier(param);
			// 同时添加一条授信记录
//			Map<String, Object> creditMess = new HashMap<String, Object>();
//			creditMess.put("SUP_ID", SUP_ID);
//			creditMess.put("COACH_LIMIT", 0.25);
//			creditMess.put("SALE_NAME", Security.getUser().getName());
//			creditMess.put("COACH_REMIND_MONEY", Double.parseDouble("1000000"));
//			creditMess.put("POSITIVE_REMIND_MONEY", Double.parseDouble("5000000"));
//			SupplierCreditService creditService = new SupplierCreditService();
//			creditService.addCreditMess(creditMess);
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
			
			// 添加SP副表信息
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
			suppliersInfo.put("WORK_PROV", param.get("WORK_PROV"));
			suppliersInfo.put("WORK_CITY", param.get("WORK_CITY"));
			suppliersInfo.put("WORK_AREA", param.get("WORK_AREA"));
			service.addSupplierInfo(suppliersInfo);

		}
		if(result >0){
			flag = true ; 
			msg = "操作成功！";
		}else{
			flag = false;
			msg = "操作失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "删除SP" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply delSuppliers() {
		Map<String, Object> param = _getParameters();
		int result = service.delSupplier(param);
		service.delSupplierInfo(param);
		if(result >0){
			return new ReplyAjax(true, "删除成功！");
		}else{
			return new ReplyAjax(false, "删除失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "合作机构", "渠道管理", "获取区域下拉选择值" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getAreaMess() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> resultMess = new HashMap<String, Object>();
//		List<Object> listArea = service.getAreaDownByParentId(param);
		
		List<Object> listArea = service.getQuYuSubset(param);
		
		resultMess.put("provs", listArea);
		return new ReplyJson(JSONObject.fromObject(resultMess));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "合作机构", "渠道管理", "获取区域下拉选择值" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getCityMess() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> resultMess = new HashMap<String, Object>();
		List<Object> listArea = service.getAreaDownByParentId(param);
		
		resultMess.put("provs", listArea);
		return new ReplyJson(JSONObject.fromObject(resultMess));
	}
	
	@aAuth(type = aAuth.aAuthType.USER )
	@aPermission(name = {"合作机构", "SP管理", "关联供应商" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply spSuppliers(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"spSuppliersManager.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "关联供应商-列表" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply spSuppDateList(){
		Map<String,Object> param = _getParameters();
		param.put("DIC_SUP_STATUS", "供应商状态");
		param.put("SUP_TYPE", 1);
		Page pagedata = service.getSuppDataList(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "关联供应商-关联" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply relevanceSupp(){
		Map<String,Object> map = _getParameters();
		int result = service.addSuppDealer(map);
		if(result>0){
			return new ReplyAjax(true,"关联成功");
		}else{
			return new ReplyAjax(false,"关联失败");
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "关联供应商-取消关联" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply delSpSupp(){
		Map<String,Object> map = _getParameters();
		int result = service.delSpSupp(map);
		if(result>0){
			return new ReplyAjax(true,"取消关联成功！");
		}else{
			return new ReplyAjax(false,"取消关联失败！");
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "SP管理", "修改" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toUpdateSpMain() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toUpdateSpMain.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply checkSupName(){
		Map<String,Object> map = _getParameters();
		int count = service.selectSupName(map);
		boolean flag = true;
		String msg = "";
		if(count>0){
			flag = false;
			msg = "此名称已存在，请更换后重试!";
		}
		return new ReplyAjax(flag,msg);
	}
	
	
}

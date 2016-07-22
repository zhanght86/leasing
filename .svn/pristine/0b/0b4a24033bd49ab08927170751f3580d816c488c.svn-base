package com.pqsoft.base.financingInstitution.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.base.channel.service.CreditAmountManagerService;
import com.pqsoft.base.financingInstitution.service.FinancingInstitutionService;
import com.pqsoft.base.grantCredit.service.SupplierCreditService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
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

public class FinancingInstitutionAction extends Action{
	private FinancingInstitutionService service = new FinancingInstitutionService();
	private String path = "base/financingInstitution/";
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"合作机构", "合作金融机构", "列表显示"})
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply execute() {
		return new ReplyHtml(VM.html(path+"financingManager.vm",null));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"合作机构", "合作金融机构", "列表显示"})
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		param.put("DIC_SUP_STATUS", "供应商状态");
		if(param.get("SUP_TYPE")==null||"".equals(param.get("SUP_TYPE")))
			param.put("SUP_TYPE", 6);
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"合作机构", "合作金融机构", "添加"})
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply addSupPage() {
		VelocityContext context = new VelocityContext();
		AreaService area = new AreaService();
		context.put("allArea", area.getAllQuYu());
		context.put("getProvince", area.getAllProvince());
		List taxQual = (List) new DataDictionaryMemcached().get("纳税资质");
		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		context.put("taxQual", taxQual);
		return new ReplyHtml(VM.html(path + "updateFinancing.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"合作机构", "合作金融机构", "查看"})
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
		context.put("supplierInfo", service.getOneSupplierInfo(param));
		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		return new ReplyHtml(VM.html(path + "detailFinancing.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
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
		
//		context.put("allArea", service.getAllArea());
		context.put("allArea", area.getAllQuYu());
		context.put("getProvince", area.getAllProvince());
		Map<String, Object> paramtwo = new HashMap<String, Object>();
		paramtwo.put("PARENT_ID",suppMess.containsKey("AREA_ID") 
				&& suppMess.get("AREA_ID") != null 
				&& !"".equals(suppMess.get("AREA_ID").toString()) 
				? suppMess.get("AREA_ID").toString() : "");
		context.put("areaProvs", service.getQuYuSubset(paramtwo));
		paramtwo.put("PARENT_ID", suppMess.containsKey("PROV_ID") 
				&& suppMess.get("PROV_ID") != null 
				? suppMess.get("PROV_ID").toString() : "");
		context.put("provCity", service.getAreaDownByParentId(paramtwo));
		
		context.put("getProvince", area.getAllProvince());
		//省市区
		AreaService areaS = new AreaService();
		paramtwo.put("PARENT_ID", suppMess.containsKey("SUB_LEGAL_PROV")
				&&suppMess.get("SUB_LEGAL_PROV") !=null
				&&!"".equals(suppMess.get("SUB_LEGAL_PROV").toString()) 
				? suppMess.get("SUB_LEGAL_PROV").toString() : "");
		context.put("subCity",areaS.getSubset(paramtwo));
		paramtwo.put("PARENT_ID", suppMess.containsKey("SUB_LEGAL_CITY")
				&&suppMess.get("SUB_LEGAL_CITY")!=null
				? suppMess.get("SUB_LEGAL_CITY").toString():"");
		context.put("subArea", areaS.getSubset(paramtwo));
		//副表信息
		Map<String,Object> infoMess = service.getOneSupplierInfo(param);
		context.put("supplierInfo", infoMess);
		if(infoMess!=null){
			paramtwo.put("PARENT_ID", infoMess.containsKey("WORK_PROV")
					&&infoMess.get("WORK_PROV") !=null
					&&!"".equals(infoMess.get("WORK_PROV").toString()) 
					? infoMess.get("WORK_PROV").toString() : "");
			context.put("workCity",areaS.getSubset(paramtwo));
			paramtwo.put("PARENT_ID", infoMess.containsKey("WORK_CITY")
					&&infoMess.get("WORK_CITY")!=null
					? infoMess.get("WORK_CITY").toString():"");
			context.put("workArea", areaS.getSubset(paramtwo));
		}
//		context.put("companys", service.getAlllCompany());
		List taxQual = (List) new DataDictionaryMemcached().get("纳税资质");
		context.put("taxQual", taxQual);
		List statusMemo = (List) new DataDictionaryMemcached().get("供应商状态");
		context.put("supplierStatus", statusMemo);

		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		return new ReplyHtml(VM.html(path + "updateFinancing.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply saveSupplierMess() {
		Map<String, Object> params = _getParameters();
		Map<String, Object> param = JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		Boolean flag = true;
		int result = 0;
		String msg = "";
		if (param.containsKey("SUP_ID") && param.get("SUP_ID") != null && !"".equals(param.get("SUP_ID").toString())) {
			
			result = service.updateSupplier(param);
			

			//经销商副表信息
			int count = service.selectCount(param);
			if (count > 0) {
				service.updateSupplierInfo(param);
			} else {
				service.addSupplierInfo(param);
			}
			
		} else {
			int SUP_ID = service.getSuppSeq();
			param.put("SUP_ID", SUP_ID);
			//6:合作金融机构
			param.put("SUP_TYPE", 6);
			result = service.addSupplier(param);
			
			// 同时添加一条授信记录
//			Map<String, Object> creditMess = new HashMap<String, Object>();
//			creditMess.put("SUP_ID", SUP_ID);
//			creditMess.put("COACH_LIMIT", 0.25);
//			creditMess.put("USER_ID", Security.getUser().getId());
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
			
			// 添加供应商副表信息
			service.addSupplierInfo(param);
			
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
	
	
	
}

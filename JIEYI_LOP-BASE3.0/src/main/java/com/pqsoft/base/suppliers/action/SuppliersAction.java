package com.pqsoft.base.suppliers.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.base.channel.service.CreditAmountManagerService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.base.suppliersInfo.service.SuppliersInfoService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.crm.service.BankSignMgService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
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
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class SuppliersAction extends Action {
	private SuppliersService service = new SuppliersService();
	private SuppliersInfoService suppliersInfoService = new SuppliersInfoService();
	private String path = "base/suppliers/";

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"合作机构", "渠道管理", "列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		// List<Object> busPlate = (List<Object>)new
		// DataDictionaryMemcached().get("PDF模版所属商务板块");
		// context.put("busPlate", busPlate);
		context.put("Param", param);
		return new ReplyHtml(VM.html(path + "supplierManager.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		param.put("DIC_SUP_STATUS", "供应商状态");
		if(param.get("SUP_TYPE")==null||"".equals(param.get("SUP_TYPE")))
			param.put("SUP_TYPE", 1);
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "查看" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getShowDetail() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		//经营产品对应厂商
		List<Map<String,Object>> sup_com = service.getSuppCompany(param);
		String cname = "";
		for(int i=0;i<sup_com.size();i++ ){
			cname += sup_com.get(i).get("COMPANY_NAME");
			if(i != sup_com.size()-1){
				cname +=",";
			}
		}
		Map<String, Object> suppMess = service.getOneSupplier(param);
		suppMess.put("COMPANY_NAME", cname);
		String addressText = service.selectAreaText(suppMess.get("SUB_LEGAL_PROV"), suppMess.get("SUB_LEGAL_CITY"), suppMess.get("SUB_LEGAL_AREA"));
		suppMess.put("SUB_LEGAL_ADDRESS",addressText+(suppMess.get("SUB_LEGAL_ADDRESS")==null?"":suppMess.get("SUB_LEGAL_ADDRESS").toString()));
		context.put("supplier", suppMess);
		context.put("LinkMans", service.getLinkManList(param));
		param.put("INVEST_TYPE", "0");
		context.put("Naturals", service.getInvestsByType(param));
		param.put("INVEST_TYPE", "1");
		context.put("Legals", service.getInvestsByType(param));
		context.put("files", service.findSupFileUploads(param.get("SUP_ID").toString()));
		context.put("allArea", service.getAllArea());
		Map<String, Object> SupplierInfo = suppliersInfoService.getOneSupplierInfo(param);
		String workText = service.selectAreaText(SupplierInfo.get("WORK_PROV"), SupplierInfo.get("WORK_CITY"), SupplierInfo.get("WORK_AREA"));
		SupplierInfo.put("WORK_ADDRESS", workText+(SupplierInfo.get("WORK_ADDRESS")==null?"":SupplierInfo.get("WORK_ADDRESS").toString()));
		context.put("supplierInfo", SupplierInfo);
		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		return new ReplyHtml(VM.html(path + "detailSuppliers.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "查看" })
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	public Reply getShowDetailSupp() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "detailSuppliersMain.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改" })
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toUpdateSuppliersMain() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toUpdateSuppliersMain.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "合作机构", "渠道管理", "修改页面基本信息" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply modifySupPage() {
		VelocityContext context = new VelocityContext();
		AreaService area = new AreaService();
		Map<String, Object> param = _getParameters();
		Map<String, Object> suppMess = service.getOneSupplier(param);
		//经营产品对应厂商
		List<Map<String,Object>> sup_com = service.getSuppCompany(param);
		String cid = "";
		String cname = "";
		for(int i=0;i<sup_com.size();i++ ){
			cid += sup_com.get(i).get("COMPANY_ID");
			cname += sup_com.get(i).get("COMPANY_NAME");
			if(i != sup_com.size()-1){
				cid +=",";
				cname +=",";
			}
		}
		suppMess.put("COMPANY_ID", cid);
		suppMess.put("COMPANY_NAME", cname);
		
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
		Map<String,Object> infoMess = suppliersInfoService.getOneSupplierInfo(param);
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
		//add gengchangbao JZZL-231 2016年6月27日15:34:49 Start
		List<Map<String, Object>> fgs = service.getJxsDataSS();//add JZZL-231
		Map<String, Object> fgsMap = null;
		String SSJG = "";
		if (suppMess.get("SSJG") != null) {
			SSJG ="," + suppMess.get("SSJG").toString();
		}
		String SSJGNM = "";		
		if (fgs != null && fgs.size() > 0) {
			for (int i = 0; i < fgs.size(); i++) {
				fgsMap = new HashMap<String, Object>();
				fgsMap = fgs.get(i);
				if (SSJG.indexOf(","+fgsMap.get("ID").toString()+",") >= 0) {
					fgsMap.put("SSFlag", "1");
					SSJGNM = SSJGNM + fgsMap.get("NAME").toString()+",";
				}
			}
		}
		
		context.put("fgs", fgs);
		//add gengchangbao JZZL-231 2016年6月27日15:34:49 End
		context.put("supplierStatus", statusMemo);
		suppMess.put("SSJGNM", SSJGNM);
		context.put("supplier", suppMess);

		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		context.put("group", service.findGroup(param));//集团
		context.put("AREA_MANAGER", service.findAreaManager());//负责人
		context.put("bank", new DataDictionaryMemcached().get("开户行所属总行"));
		context.put("BRANCH_BANK_PROV", area.getAllProvince());
		paramtwo.put("PARENT_ID", suppMess.containsKey("BRANCH_BANK_PROV") 
				&& suppMess.get("BRANCH_BANK_PROV") != null 
				? suppMess.get("BRANCH_BANK_PROV") : "");
		context.put("BRANCH_BANK_CITY", service.getAreaDownByParentId(paramtwo));
		return new ReplyHtml(VM.html(path + "updateSuppliers.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "添加" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply addSupPage() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("sp_id", map);
		AreaService area = new AreaService();
		context.put("allArea", area.getAllQuYu());
		context.put("getProvince", area.getAllProvince());
		context.put("group", service.findGroup(new HashMap<String,Object>()));//集团
		List taxQual = (List) new DataDictionaryMemcached().get("纳税资质");
		context.put("type", service.findCustDataType());// 获取用户配置信息 下拉选
		context.put("taxQual", taxQual);
		context.put("AREA_MANAGER", service.findAreaManager());//负责人
		
		List<Map<String, Object>> fgs = service.getJxsDataSS();//add JZZL-231
		context.put("fgs", fgs);
		
		return new ReplyHtml(VM.html(path + "updateSuppliers.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "合作机构", "渠道管理", "保存供应商信息" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply saveSupplierMess() {
		Map<String, Object> params = _getParameters();
		Map<String, Object> param = JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		Boolean flag = true;
		int result = 0;
		String msg = "";
		if (param.containsKey("SUP_ID") && param.get("SUP_ID") != null && !"".equals(param.get("SUP_ID").toString())) {
			
			//如果是集团把PARENT_ID设-1
			if(param.get("SUP_TYPE")!=null&&param.get("SUP_TYPE").equals("4")){
				param.put("PARENT_ID", "-1");
			}
			result = service.updateSupplier(param);
			if(result>0){
				List<Map<String,Object>> list = service.getSuppCompanyBySupid(param);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("SUP_ID", param.get("SUP_ID"));
				//删除经销商厂商关系表
				service.delSuppCompany(map);
				//添加经销商厂商关系表
				if(param.get("COMPANY_ID")!=null&&!"".equals(param.get("COMPANY_ID"))){
					String company_id = (String) param.get("COMPANY_ID");
					String[] cid = company_id.split(",");
					for(int i=0;i<cid.length;i++){
						param.put("COMPANY_ID", cid[i]);
						service.addSuppCompany(param);
					}
				}
			}

			//经销商副表信息
			int count = suppliersInfoService.selectCount(param);
			if (count > 0) {
				suppliersInfoService.updateSupplierInfo(param);
			} else {
				suppliersInfoService.addSupplierInfo(param);
			}
		} else {
			int SUP_ID = service.getSuppSeq();
			param.put("SUP_ID", SUP_ID);
			param.put("USER_ID", Security.getUser().getId());
			result = service.addSupplier(param);
			//添加经销商厂商关系表
			if(param.get("COMPANY_ID")!=null&&!"".equals(param.get("COMPANY_ID"))){
				String company_id = (String) param.get("COMPANY_ID");
				String[] cid = company_id.split(",");
				for(int i=0;i<cid.length;i++){
					param.put("COMPANY_ID", cid[i]);
					service.addSuppCompany(param);
				}
			}
			//添加到当前登录用户公司的经销商信息
			if(Security.getUser().getOrg()!=null){
				String manager_id = Security.getUser().getOrg().getPlatformId();
				Map<String,Object> manager = new HashMap<String,Object>();
				manager.put("TYPE", "供应商");
				manager.put("TYPE_ID", SUP_ID);
				manager.put("MANAGER_ID", manager_id);
				service.addManager(manager);
			}
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
			suppliersInfoService.addSupplierInfo(param);
			//添加经销商和sp关联关系
			if(param.get("SP_ID")!=null&&!"".equals(param.get("SP_ID"))){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("SP_ID", param.get("SP_ID"));
				map.put("SUP_ID", SUP_ID);
				service.addSpSupp(map);
			}
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
	//@aPermission(name = { "合作机构", "渠道管理", "获取区域下拉选择值" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getAreaMess() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> resultMess = new HashMap<String, Object>();
		
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
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@126.com", name = "王海龙")
	public Reply getAddress(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> address = new HashMap<String,Object>();
		List<Object> listAddress = new AreaService().getSubset(param);
		address.put("address", listAddress);
		return new ReplyJson(JSONObject.fromObject(address));
	}
	

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"合作机构", "渠道管理", "删除"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delSuppliers() {
		Map<String, Object> param = _getParameters();
		int result = service.delSupplier(param);
		service.delSupplierInfo(param);
		if (result > 0) {
			return new ReplyAjax(true, "删除成功！");
		} else {
			return new ReplyAjax(false, "删除失败！");
		}
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "合作机构", "渠道管理", "保存投资人" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
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
	//@aPermission(name = { "合作机构", "渠道管理", "删除联系人" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
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
	//@aPermission(name = { "合作机构", "渠道管理", "保存联系人" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
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
	//@aPermission(name = { "合作机构", "渠道管理", "删除投资人" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
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
	@aPermission(name = { "合作机构", "渠道管理", "上传附件" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
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
	@aPermission(name = { "合作机构", "渠道管理", "下载附件" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
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
	//@aPermission(name = { "合作机构", "渠道管理", "删除附件" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
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
	@aPermission(name = { "合作机构", "渠道管理", "是否受理业务开关" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
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
	
	/**
	 * 厂商
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月9日,下午12:15:38
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply findCompany(){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Object> companys= service.getAlllCompany();
		result.put("companys", companys);
		return new ReplyJson(JSONObject.fromObject(result));
	}
	
	/**
	 * 集团
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月9日,下午12:15:23
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply findGroup(){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> group = service.findGroup(result);
		result.put("group", group);
		return new ReplyJson(JSONObject.fromObject(result));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply checkGroup(){
		Map<String,Object> param = _getParameters();
		int count = service.checkGroup(param);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("count", count);
		return new ReplyJson(JSONObject.fromObject(result));
	}
	
	/**
	 * 验证是否有其他业务关联
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月11日,下午3:54:15
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply checkDel(){
		Map<String,Object> param = _getParameters();
		int count = service.checkDel(param);
		int count2 = service.checkDel2(param);
		boolean falg = true;
		if(count>0||count2>0){
			falg = false;
		}
		
		param.put("falg", falg);
		return new ReplyJson(JSONObject.fromObject(param));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply startSuppliersByJbpm(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> map = service.getOneSupplier(param);
		List<String> list = JBPM.getDeploymentListByModelName("经销商审批",
				"",
				Security.getUser().getOrg().getPlatformId());
		String pid = null;
		if (list.size() > 0) {
			pid = list.get(0);
		} else
			throw new ActionException("没有找到流程图");

		if (pid == null) {
			throw new ActionException("业务模式没有匹配到审批流程");
		}
		Map<String,Object> jmap = new HashMap<String,Object>();
		jmap.put("SUP_ID", map.get("SUP_ID"));
		String jbpmId = JBPM.startProcessInstanceById(pid, 
				null, null, null, jmap).getId();
		
		return new ReplyAjax(true,"成功");
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
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply getProv(){
		AreaService area = new AreaService();
		List<Object> list = area.getAllProvince();
		JSONArray json = JSONArray.fromObject(list);
		return new ReplyJson(json);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply getCity(){
		Map<String,Object> param = _getParameters();
		List<Object> list = new AreaService().getSubset(param);
		JSONArray json = JSONArray.fromObject(list);
		return new ReplyJson(json);
	}
	
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply test(){
		BankSignMgService service = new BankSignMgService();
		service.autoSigning();
		return null;
	}

}

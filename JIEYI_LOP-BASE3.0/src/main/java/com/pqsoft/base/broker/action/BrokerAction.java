package com.pqsoft.base.broker.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.base.broker.service.BrokerService;
import com.pqsoft.base.channel.service.CreditAmountManagerService;
import com.pqsoft.base.grantCredit.service.SupplierCreditService;
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

public class BrokerAction extends Action {
	private BrokerService service = new BrokerService();
	private SuppliersInfoService suppliersInfoService = new SuppliersInfoService();
	private String path = "base/broker/";
	
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构", "经纪人管理", "列表"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("Param", param);
		return new ReplyHtml(VM.html(path+"brokerManager.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","查询[按钮]"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		param.put("DIC_SUP_STATUS", "供应商状态");
		param.put("SUP_TYPE", 3);
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","查看详细页面"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply getShowDetail(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("supplier", service.getOneSupplier(param));
		context.put("LinkMans", service.getLinkManList(param));
		param.put("INVEST_TYPE", "0");
		context.put("Naturals", service.getInvestsByType(param));
		param.put("INVEST_TYPE", "1");
		context.put("Legals", service.getInvestsByType(param));
		context.put("files", service.findSupFileUploads(param.get("SUP_ID").toString()));
		context.put("allArea", service.getAllArea());
		return new ReplyHtml(VM.html(path+"detailBroker.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","进入修改页面"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply toUpdateSuppliersMain(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"toUpdateBrokerMain.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","修改页面基本信息"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply modifySupPage(){
		VelocityContext context = new VelocityContext();
		AreaService area = new AreaService();
		Map<String, Object> param = _getParameters();
		Map<String, Object> suppMess = service.getOneSupplier(param);
		context.put("supplier", suppMess);
		Map<String,Object> infoMess = service.getOneSupplierInfo(param);
		context.put("supplierInfo", infoMess);
		
		
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
			
		if(infoMess!=null&&"NP".equals(infoMess.get("TYPE").toString())){//个人
			if(infoMess!=null){
				//工作地址
				paramtwo.put("PARENT_ID", infoMess.containsKey("COMPANY_ADDR_PROVINCE")
						&&infoMess.get("COMPANY_ADDR_PROVINCE") !=null
						&&!"".equals(infoMess.get("COMPANY_ADDR_PROVINCE").toString()) 
						? infoMess.get("COMPANY_ADDR_PROVINCE").toString() : "");
				context.put("companyCity",area.getSubset(paramtwo));
				paramtwo.put("PARENT_ID", infoMess.containsKey("COMPANY_ADDR_CITY")
						&&infoMess.get("COMPANY_ADDR_CITY")!=null
						? infoMess.get("COMPANY_ADDR_CITY").toString():"");
				context.put("companyArea", area.getSubset(paramtwo));
				//家庭地址
				paramtwo.put("PARENT_ID", infoMess.containsKey("HOUSE_ADDRESS_PROVINCE")
						&&infoMess.get("HOUSE_ADDRESS_PROVINCE") !=null
						&&!"".equals(infoMess.get("HOUSE_ADDRESS_PROVINCE").toString()) 
						? infoMess.get("HOUSE_ADDRESS_PROVINCE").toString() : "");
				context.put("houseCity",area.getSubset(paramtwo));
				paramtwo.put("PARENT_ID", infoMess.containsKey("HOUSE_ADDRESS_CITY")
						&&infoMess.get("HOUSE_ADDRESS_CITY")!=null
						? infoMess.get("HOUSE_ADDRESS_CITY").toString():"");
				context.put("houseArea", area.getSubset(paramtwo));
				//邮寄地址
				paramtwo.put("PARENT_ID", infoMess.containsKey("MAIL_ADDRESS_PROVINCE")
						&&infoMess.get("MAIL_ADDRESS_PROVINCE") !=null
						&&!"".equals(infoMess.get("MAIL_ADDRESS_PROVINCE").toString()) 
						? infoMess.get("MAIL_ADDRESS_PROVINCE").toString() : "");
				context.put("mailCity",area.getSubset(paramtwo));
				paramtwo.put("PARENT_ID", infoMess.containsKey("MAIL_ADDRESS_CITY")
						&&infoMess.get("MAIL_ADDRESS_CITY")!=null
						? infoMess.get("MAIL_ADDRESS_CITY").toString():"");
				context.put("mailArea", area.getSubset(paramtwo));
			}
			return new ReplyHtml(VM.html(path+"updateBrokerNatu.vm", context));
		}
		
		return new ReplyHtml(VM.html(path+"updateBrokerLegal.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","进入添加页面"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply addSupPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		String type = "";
		if(param.get("TYPE")!=null&&!"".equals(param.get("TYPE"))){
			type = param.get("TYPE").toString();
		}
		AreaService area = new AreaService();
		context.put("allArea", area.getAllQuYu());
		context.put("companys", service.getAlllCompany());
		List taxQual = (List)new DataDictionaryMemcached().get("纳税资质");
		context.put("type", service.findCustDataType());//获取用户配置信息 下拉选
		context.put("taxQual", taxQual);
		//所有省份
		context.put("getProvince", area.getAllProvince());
		if("NP".equals(type)){
			return new ReplyHtml(VM.html(path+"updateBrokerNatu.vm", context));
		}
		return new ReplyHtml(VM.html(path+"updateBrokerLegal.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","保存经纪人信息"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply saveSupplierMess(){
		Map<String,Object> params = _getParameters();
		Map<String,Object> param=JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		Boolean flag = true;
		int result = 0 ; 
		String msg = "";
		if(param.containsKey("SUP_ID") && param.get("SUP_ID") !=null && !"".equals(param.get("SUP_ID").toString())){
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
			suppliersInfo.put("WORK_PROV", param.get("WORK_PROV"));
			suppliersInfo.put("WORK_CITY", param.get("WORK_CITY"));
			suppliersInfo.put("WORK_AREA", param.get("WORK_AREA"));
			if (count > 0) {
				service.updateSupplierInfo(suppliersInfo);
			} else {
				service.addSupplierInfo(suppliersInfo);
			}
		}else{
			int SUP_ID = service.getSuppSeq();
			param.put("SUP_ID", SUP_ID);
			param.put("SUP_TYPE", 3);// 经销商类型 3:经纪人
			result = service.addSupplier(param);
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
			
			// 添加副表信息
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
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","保存经纪人信息"})
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply saveSupplierNP(){
		Map<String,Object> params = _getParameters();
		Map<String,Object> param = JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		param.put("USERNAME", Security.getUser().getName());
		param.put("USERCODE", Security.getUser().getCode());
		Boolean flag = true;
		int result = 0 ; 
		String msg = "";
		
		if(param.containsKey("SUP_ID") && param.get("SUP_ID") !=null && !"".equals(param.get("SUP_ID").toString())){
			result = service.updateSupplierNP(param);
			int count = suppliersInfoService.selectCount(param);
			if (count > 0) {
				service.updateSupplierInfo(param);
			} else {
				service.addSupplierNPInfo(param);
			}
		}else{
			int SUP_ID = service.getSuppSeq();
			param.put("SUP_ID", SUP_ID);
			param.put("SUP_TYPE", 3);// 经销商类型 3:经纪人
			result = service.addSupplierNP(param);
			
			service.addSupplierNPInfo(param);
		}
		if(result>0){
			msg = "操作成功";
		}else{
			flag = false;
			msg = "操作失败";
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","获取区域下拉选择值"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply getAreaMess(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> resultMess = new HashMap<String, Object>();
		List<Object> listArea = service.getAreaDownByParentId(param);
		resultMess.put("provs", listArea);
		return new ReplyJson(JSONObject.fromObject(resultMess));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","删除经纪人"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply delSuppliers(){
		Map<String,Object> param = _getParameters();
		int result = service.delSupplier(param);
		if(result >0){
			return new ReplyAjax(true, "删除成功！");
		}else{
			return new ReplyAjax(false, "删除失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","保存投资人"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply saveInvestor(){
		Map<String,Object> params = _getParameters();
		Map<String,Object> param=JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		Boolean flag = true;
		int result = 0;
		String msg = "";
		if(param.containsKey("ID") && param.get("ID") !=null && !"".equals(param.get("ID").toString())){
			result = service.updateInvestor(param);
		}else{
			result = service.addInvestor(param);
		}
		if( result > 0){
			param.put("ID", "");
			return new ReplyJson(JSONArray.fromObject(service.getInvestsByType(param)));
		}else{
			flag = false;
			msg = "操作失败！";
			return new ReplyAjax(flag,msg);
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","删除联系人"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply delLinkMan(){
		Map<String,Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.delOneLinkMan(param);
		if(result > 0){
			flag = true ; 
			msg = "删除成功！";
		}else{
			flag = false ; 
			msg = "删除失败！";
		}
		return new ReplyAjax(flag, msg);
	}
	
	
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","保存联系人"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply saveLinkMan(){
		Map<String,Object> params = _getParameters();
		Map<String,Object> param=JSONObject.fromObject(params.get("json").toString());
		param.put("SUP_ID", params.get("SUP_ID").toString());
		Boolean flag = true;
		int result = 0;
		String msg = "";
		if(param.containsKey("LINK_ID") && param.get("LINK_ID") !=null && !"".equals(param.get("LINK_ID").toString())){
			result = service.updataLinkMan(param);
		}else{
			result = service.addLinkMan(param);
		}
		if(result > 0 ){
			return new ReplyJson(JSONArray.fromObject(service.getLinkManList(param)));
		}else{
			flag = false;
			msg = "操作失败！";
		    return new ReplyAjax(flag, msg);
		}
		
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","删除投资人"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply delInvestor(){
		Map<String,Object> param = _getParameters();
		Boolean flag = true ;
		String msg = "";
		int result = service.delInvest(param);
		if(result > 0){
			flag = true ;
			msg ="删除成功！";
		}else{
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag, msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","上传附件"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply uploadFile(){
		int count= 0;
		Map<String,Object> m = _getParameters();
		String suppliers_id = m.get("SUP_ID").toString();
		try {
			Map<String,Object> map = service.uploadFileForOne(SkyEye.getRequest());
			map.put("FIL_MEMO", m.get("FIL_MEMO"));
			map.put("SUP_ID", suppliers_id);
			count= service.addSupFileUpload(map);
		} catch (Exception e) {
			logger.error(e);
		}
		if(count>0){
			List<Object> listfile =  service.findSupFileUploads(suppliers_id);
			System.out.println(listfile.size());
			return new ReplyJson(JSONArray.fromObject(listfile));
		}else{
			return new ReplyAjax(true, "上传成功！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","下载附件"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply downLoadSupFile(){
		Map<String,Object> param = _getParameters();
		String file_id = param.get("FIL_ID").toString();
		Map<String, Object> fileMess = (Map<String, Object>) service.findDupFileByID(file_id);
		// path是指欲下载的文件的路径。
		String filePath =  fileMess.get("FIL_PATH").toString();
		String fileName = fileMess.get("FIL_NAME").toString();
		File file = new File(filePath);
		return new ReplyFile(file, fileName);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","删除附件"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply deleteSupFile(){
		Map<String,Object> param = _getParameters();
		Boolean flag = true;
	    String msg = "";
		String fil_id = param.get("FIL_ID").toString();
		int result = service.deleteSupFile(fil_id);
		if(result>0){
			flag = true ;
			msg = "删除成功！";
		}else{
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag, msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"合作机构","经纪人管理","经纪人开关控制"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply supTurnSwitch(){
		Map<String,Object> param = _getParameters();
		if(param.containsKey("SUP_SWITCH") && param.get("SUP_SWITCH") != null && "0".equals(param.get("SUP_SWITCH").toString())){
			param.put("SUP_SWITCH", "1");
		}else if(param.containsKey("SUP_SWITCH") && param.get("SUP_SWITCH") != null){
			param.put("SUP_SWITCH", "0");
		}
		if(param.containsKey("BALANCE_LOAN_SWITCH") && param.get("BALANCE_LOAN_SWITCH") != null && "0".equals(param.get("BALANCE_LOAN_SWITCH").toString())){
			param.put("BALANCE_LOAN_SWITCH", "1");
		}else if(param.containsKey("BALANCE_LOAN_SWITCH") && param.get("BALANCE_LOAN_SWITCH") != null){
			param.put("BALANCE_LOAN_SWITCH", "0");
		}
		if(param.containsKey("IRREGULAR_REPAYMENT_SWITCH") && param.get("IRREGULAR_REPAYMENT_SWITCH") != null && "0".equals(param.get("IRREGULAR_REPAYMENT_SWITCH").toString())){
			param.put("IRREGULAR_REPAYMENT_SWITCH", "1");
		}else if(param.containsKey("IRREGULAR_REPAYMENT_SWITCH") && param.get("IRREGULAR_REPAYMENT_SWITCH") != null){
			param.put("IRREGULAR_REPAYMENT_SWITCH", "0");
		}
		if(param.containsKey("B_MODEL_SWITCH") && param.get("B_MODEL_SWITCH") != null && "0".equals(param.get("B_MODEL_SWITCH").toString())){
			param.put("B_MODEL_SWITCH", "1");
		}else if(param.containsKey("B_MODEL_SWITCH") && param.get("B_MODEL_SWITCH") != null){
			param.put("B_MODEL_SWITCH", "0");
		}
		if(param.containsKey("SMS_SWITCH") && param.get("SMS_SWITCH") != null && "0".equals(param.get("SMS_SWITCH").toString())){
			param.put("SMS_SWITCH", "1");
		}else if(param.containsKey("SMS_SWITCH") && param.get("SMS_SWITCH") != null){
			param.put("SMS_SWITCH", "0");
		}
		if(param.containsKey("CREDIT_SWITCH") && param.get("CREDIT_SWITCH") != null && "0".equals(param.get("CREDIT_SWITCH").toString())){
			param.put("CREDIT_SWITCH", "1");
		}else if(param.containsKey("CREDIT_SWITCH") && param.get("CREDIT_SWITCH") !=null){
			param.put("CREDIT_SWITCH", "0");
		}
		if(param.containsKey("SCAN_SWITCH") && param.get("SCAN_SWITCH") != null && "0".equals(param.get("SCAN_SWITCH").toString())){
			param.put("SCAN_SWITCH", "1");
		}else if(param.containsKey("SCAN_SWITCH") && param.get("SCAN_SWITCH") !=null){
			param.put("SCAN_SWITCH", "0");
		}
		if(param.containsKey("DATAFILL_SWITCH") && param.get("DATAFILL_SWITCH") !=null && "0".equals(param.get("DATAFILL_SWITCH").toString())){
			param.put("DATAFILL_SWITCH", "1");
		}else if(param.containsKey("DATAFILL_SWITCH") && param.get("DATAFILL_SWITCH") !=null){
			param.put("DATAFILL_SWITCH", "0");
		}
		if(param.containsKey("YINGYE_STATUS") && param.get("YINGYE_STATUS") !=null && "0".equals(param.get("YINGYE_STATUS").toString())){
			param.put("YINGYE_STATUS", "1");
		}else if(param.containsKey("YINGYE_STATUS") && param.get("YINGYE_STATUS") !=null){
			param.put("YINGYE_STATUS", "0");
		}
		System.out.println("-=====----======---"+param);
		int result  = service.supSwitchMethod(param);
		Boolean flag = true ; 
		String msg = "";
		if(result >0 ){
			flag = true ;
			msg = "操作成功！";
		}else{
			flag = false ; 
			msg = "操作失败！";
		}
		return new ReplyAjax(flag, msg);
	}
	
	
}

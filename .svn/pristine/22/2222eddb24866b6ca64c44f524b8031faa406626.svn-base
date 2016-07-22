package com.pqsoft.base.organization.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.base.organization.service.AddService;
import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.UserCache;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class AddAction extends Action {

	private static final String basePath = "base/organization/";
	private final Logger logger = Logger.getLogger(getClass());
	private final AddService service = new AddService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		DataDictionaryService dataDictionaryService = new DataDictionaryService();
		CompanyService compService = new CompanyService();
		context.put("companys", compService.getAllCompany());
		// add gengchangbao jzzl-122 start
		List sealPathList = DataDictionaryMemcached.getList("签约主体");
		context.put("sealPathList", sealPathList);
		List channelTypeList = DataDictionaryMemcached.getList("渠道类型");
		context.put("channelTypeList", channelTypeList);
		context.put("TPM_TYPE_LIST", new SysDictionaryMemcached().get("PDF模版类型"));
		// add gengchangbao jzzl-122 end
		List datadiclist = (List) new SysDictionaryMemcached().get("组织架构类型");
		List gw = (List) new SysDictionaryMemcached().get("岗位");
		context.put("gw", gw);
		context.put("orgtype", datadiclist);
		List<Object> suppliers = compService.getSuppMessByCompanyId1();
		context.put("suppliers", suppliers);
		List<Object> SP = compService.getSuppMessByCompanyId2();
		context.put("SP", SP);
		return new ReplyHtml(VM.html(basePath + "add.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getSuppliers() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> newSup = new HashMap<String, Object>();
		CompanyService compService = new CompanyService();
		List<Object> suppliers = compService.getSuppMessByCompanyId(param);
		newSup.put("supplier", suppliers);
		return new ReplyJson(JSONObject.fromObject(newSup));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getOrganizations() {
		String ORG_PARENT_ID = SkyEye.getRequest().getParameter("ORG_PARENT_ID");
		ORG_PARENT_ID = ORG_PARENT_ID == null || ORG_PARENT_ID.toString().trim().length() == 0 ? "0" : ORG_PARENT_ID.toString().trim();
		VelocityContext context = new VelocityContext();
		context.put("data", new ManageService().getOrganizations(ORG_PARENT_ID));
		return new ReplyHtml(VM.html(basePath + "add_organization.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply getRegional() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("data", new ManageService().getRegional(param));
		return new ReplyHtml(VM.html(basePath + "add_Regional.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getRole() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		System.out.println(param+"=============");
		context.put("roleN", service.getRoles(param));
		return new ReplyHtml(VM.html(basePath + "add_role.vm", context));
	}

	/**
	 * 添加组织架构操作
	 */
	/**
	 * @return
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply doAdd() {
		Map<String, Object> param = _getParameters();
		String msg = "";
		Boolean flag = true;
		int result = 0;
		try {
			String org_id = service.getOrgIdSeq();
			param.put("ORG_ID", org_id);
			result = service.add(param);
			// 更新厂商/供应商基础信息关联
			if (param.containsKey("TYPE") && param.get("TYPE") != null && "1".equals(param.get("TYPE").toString())) {
				// 更新厂商ORG_ID
				CompanyService compService = new CompanyService();
				compService.updateOrgIdByCompanyId(param);
			} else if (param.containsKey("TYPE") && param.get("TYPE") != null && "3".equals(param.get("TYPE").toString())) {
				// 更新对应供应商ORG_BMID
				SuppliersService supService = new SuppliersService();
				supService.updateSupOrgIdBySupId(param);
			}else if (param.containsKey("TYPE") && param.get("TYPE") != null && "16".equals(param.get("TYPE").toString())) {
				// 更新对应SP ORG_BMID
				SuppliersService supService = new SuppliersService();
				supService.updateSupOrgIdBySupId1(param);
			} else if (param.containsKey("TYPE") && param.get("TYPE") != null && "11".equals(param.get("TYPE").toString())) {
				// 更新平台总公司
				service.addPlatform(param);
			} else if (param.containsKey("TYPE") && param.get("TYPE") != null && "12".equals(param.get("TYPE").toString())) {
				// 更新平台子公司
				service.addPlatform_SUN(param);
			}

		} catch (Exception e) {
			logger.error(e, e);
		}
		if (result > 0) {
			msg = "保存成功!";
			flag = true;
			return new ReplyAjax(flag, msg).addOp(new OpLog("组织架构", "添加", param.toString()));
		} else {
			msg = "保存失败!";
			flag = false;
			return new ReplyAjax(flag, msg);
		}
	}

	/**
	 * 修改组织架构页面
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply update() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = (Map<String, Object>) service.getInfo(SkyEye.getRequest().getParameter("ID"));
		//处理合同模版 2016年3月30日 11:20:19 吴国伟
		if(null != map.get("TEMPLATE_TYPE")){
			String[] type = map.get("TEMPLATE_TYPE").toString().split(",");
			List<Map<String,Object>> tlist = new ArrayList();
			Map<String,Object> m;
			for(int i=0;i<type.length;i++){
			    m = new HashMap<String,Object>();
				m.put("TEM_TYPE", type[i]);
				tlist.add(m);
			}
			context.put("tlist", tlist);
		}
		/*合同模版权限2016年3月31日 13:44:38吴国伟*/
		if(null != map.get("TEMPLATE_PERMISSIONS")){
			String[] type = map.get("TEMPLATE_PERMISSIONS").toString().split(",");
			List<Map<String,Object>> plist = new ArrayList();
			Map<String,Object> m;
			for(int i=0;i<type.length;i++){
				m = new HashMap<String,Object>();
				m.put("TEM_TYPE", type[i]);
				plist.add(m);
			}
			context.put("plist", plist);
		}
		context.put("data", map);
		List datadiclist = (List) new SysDictionaryMemcached().get("组织架构类型");
		// add gengchangbao jzzl-122 start
		List sealPathList = DataDictionaryMemcached.getList("签约主体");
		List channelTypeList = DataDictionaryMemcached.getList("渠道类型");
		context.put("channelTypeList", channelTypeList);
		context.put("TPM_TYPE_LIST", new SysDictionaryMemcached().get("PDF模版类型"));
		// add gengchangbao jzzl-122 end
		
		List gw = (List) new SysDictionaryMemcached().get("岗位");
		context.put("gw", gw);
		CompanyService compService = new CompanyService();
		context.put("companys", compService.getAllCompany());
		context.put("orgtype", datadiclist);
		context.put("sealPathList", sealPathList);
		List<Object> suppliers = compService.getSuppMessByCompanyId1();
		context.put("suppliers", suppliers);
		List<Object> SP = compService.getSuppMessByCompanyId2();
		context.put("SP", SP);
		return new ReplyHtml(VM.html(basePath + "update.vm", context));
	}

	/**
	 * 修改组织架构操作
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply doUpdate() {
		Boolean flag = true;
		String msg = "";
		int result = 0;
		Map<String, Object> param = _getParameters();
		System.out.println("-----------" + param);
		// 根据组织架构查询下面你人员
		List<String> userCodes = service.getOrgUserCode(param.get("ID").toString());
		try {
			result = service.update(param);
			// 更新厂商/供应商基础信息关联
			if (param.containsKey("TYPE") && param.get("TYPE") != null && "1".equals(param.get("TYPE").toString())) {
				// 更新厂商ORG_ID
				CompanyService compService = new CompanyService();

				Object COMPANY_ID_ORIGINAL = param.get("COMPANY_ID_ORIGINAL");
				if (COMPANY_ID_ORIGINAL != null && StringUtils.isNotEmpty(COMPANY_ID_ORIGINAL.toString())) {
					Map<String, Object> compParam = new HashMap<String, Object>();
					compParam.put("COMPANY_ID", COMPANY_ID_ORIGINAL);
					compService.updateOrgIdForBlankByCompanyId(compParam);
				}

				compService.updateOrgIdByCompanyId(param);
			} else if (param.containsKey("TYPE") && param.get("TYPE") != null && "3".equals(param.get("TYPE").toString())) {

				// 更新厂商ORG_ID
				CompanyService compService = new CompanyService();

				Object COMPANY_ID_ORIGINAL = param.get("COMPANY_ID_ORIGINAL");
				if (COMPANY_ID_ORIGINAL != null && StringUtils.isNotEmpty(COMPANY_ID_ORIGINAL.toString())) {
					Map<String, Object> compParam = new HashMap<String, Object>();
					compParam.put("COMPANY_ID", COMPANY_ID_ORIGINAL);
					compService.updateOrgIdForBlankByCompanyId(compParam);
				}

				compService.updateOrgIdByCompanyId(param);

				// 更新对应供应商ORG_BMID
				SuppliersService supService = new SuppliersService();

				Object SUP_ID_ORIGINAL = param.get("SUP_ID_ORIGINAL");
				if (SUP_ID_ORIGINAL != null && StringUtils.isNotEmpty(SUP_ID_ORIGINAL.toString())) {
					Map<String, Object> supParam = new HashMap<String, Object>();
					supParam.put("SUP_ID", SUP_ID_ORIGINAL);
					supService.updateSupOrgIdForBlankBySupId(supParam);
				}
				supService.updateSupOrgIdBySupId(param);
			}else if (param.containsKey("TYPE") && param.get("TYPE") != null && "16".equals(param.get("TYPE").toString())) {
				//sp
				SuppliersService supService = new SuppliersService();
				Object SP_ID_ORIGINAL_ID = param.get("SP_ID_ORIGINAL");
				if (SP_ID_ORIGINAL_ID != null && StringUtils.isNotEmpty(SP_ID_ORIGINAL_ID.toString())) {
					Map<String, Object> supParam = new HashMap<String, Object>();
					supParam.put("SUP_ID", SP_ID_ORIGINAL_ID);
					supService.updateSupOrgIdForBlankBySupId(supParam);
				}
				supService.updateSupOrgIdBySupId1(param);
			}
			UserCache uscache = new UserCache();
			// 清理组织架构下的所有人员的缓存
			for (String usercode : userCodes) {
				uscache.clean(usercode);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		if (result > 0) {
			flag = true;
			msg = "保存成功";
			return new ReplyAjax(flag, msg).addOp(new OpLog("组织架构", "修改", param.toString()));
		} else {
			flag = false;
			msg = "保存失败";
			return new ReplyAjax(flag, msg);
		}
	}

}

package com.pqsoft.customers.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.customers.service.CompanyManagerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class CompanyManagerAction extends Action {

	private Map<String, Object> m = null;
	private final String pagePath = "customers/";

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * getPageParameter 获取页面参数
	 * 
	 * @date 2013-8-28 下午06:25:35
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.ALL)
	private Map<String, Object> getPageParameter() {
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME", Security.getUser().getName());
		m.put("USERCODE", Security.getUser().getCode());
		m.put("USERID", Security.getUser().getId());
		return m;
	}

	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置", "公司维护" })
	public Reply findCompanyPage() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param", m);
		return new ReplyHtml(VM.html(pagePath + "Company.vm", context));
	}

	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findCompanyData() {
		Map<String, Object> param = _getParameters();
		CompanyManagerService service = new CompanyManagerService();
		return new ReplyAjaxPage(service.findCompany(param));
	}

	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置", "公司维护" })
	public Reply toAddCompany() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		CompanyManagerService service = new CompanyManagerService();
		context.put("zgs", service.getZgsData());
		context.put("hyfl", service.getTypeData("行业类型"));
		context.put("ywlx", service.getTypeData("业务类型"));
		context.put("cs", service.getCsData());
		context.put("jxs", service.getJxsData(map));
		//查询所有分公司信息
		ManageService  manageService = new ManageService();
		context.put("ORG_INFOLIST", manageService.getSonOrganizations());
		return new ReplyHtml(VM.html(pagePath + "Company_add.vm", context));
	}

	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置", "公司维护" })
	public Reply queryJxs() {
		Map<String, Object> map = _getParameters();
		CompanyManagerService service = new CompanyManagerService();
		map.put("jxs", service.getJxsData(map));
		return new ReplyJson(JSONObject.fromObject(map));
	}

	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置", "公司维护" })
	public Reply doAddCompany() {
		CompanyManagerService service = new CompanyManagerService();
		System.out.println(SkyEye.getRequest().getParameter("COMPANYDATA").toString());
		JSONObject m = JSONObject.fromObject(SkyEye.getRequest().getParameter("COMPANYDATA"));
		String ID = Dao.getSequence("SEQ_MANAGER");
		m.put("ID", ID);
		m.put("USERNAME", Security.getUser().getName());
		int f = service.addCompany(m);// 添加主表FHFA_MANAGER
		if (f > 0) {// 添加行业、业务分类、区域、供应商、厂商
			System.out.println(m.get("FA_INSTRUTRY"));
			service.addCompanyAffilidated(JSONArray.fromObject(m.get("FA_INSTRUTRY")), ID);// 行业
			service.addCompanyAffilidated(JSONArray.fromObject(m.get("FA_BUSINESS")), ID);// 业务分类
			service.addCompanyAffilidated(JSONArray.fromObject(m.get("FA_MANUFACTURERS")), ID);// 厂商
			service.addCompanyAffilidated(JSONArray.fromObject(m.get("FA_AGENT")), ID);// 供应商
			return new ReplyHtml("<script> alert('添加成功');" + "window.location='CompanyManager!findCompanyPage.action'; </script>  ");
		} else {
			return new ReplyHtml("<script> alert('添加失败');" + "window.location='CompanyManager!findCompanyPage.action'; </script>  ");
		}

	}

	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置", "公司维护" })
	public Reply toUpdateCompany() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		CompanyManagerService service = new CompanyManagerService();
		context.put("param", service.getCompanyData(map));
		List<Map<String, Object>> hyfl = service.getCompanyHY(map);
		List<Map<String, Object>> ywlx = service.getCompanyOtherDataHJ(map, "业务类型", "业务类型");
		List<Map<String, Object>> cs = service.getCsDataHJ(map, "厂商");
		List<Map<String, Object>> jxs = service.getJxsDataHJ(map, "供应商");
		List<Map<String, Object>> area = service.queryArea(map);
		context.put("qylx", service.getTypeData("公司性质"));
		context.put("hyfl", hyfl);
		context.put("ywlx", ywlx);
		context.put("cs", cs);
		context.put("jxs", jxs);
		context.put("area", area);
		context.put("zgs", service.getZgsData());
		context.put("ad", service.AssembleData(hyfl, ywlx, cs, jxs));
		context.put("AreaContext", AreaService.areaPlug(""));
		//查询所有分公司信息
		ManageService  manageService = new ManageService();
		context.put("ORG_INFOLIST", manageService.getSonOrganizations());
		context.put("BANK_INFOLIST", service.queryFHFABANK(map));
		return new ReplyHtml(VM.html(pagePath + "Company_update.vm", context));
	}

	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置", "公司维护" })
	public Reply doUpdateCompany() {
		CompanyManagerService service = new CompanyManagerService();
		System.out.println(SkyEye.getRequest().getParameter("COMPANYDATA").toString());
		JSONObject m = JSONObject.fromObject(SkyEye.getRequest().getParameter("COMPANYDATA"));
		String ID = m.getString("ID");
		System.out.println(m.get("FA_NAMEADDRESS"));
		m.put("USERNAME", Security.getUser().getName());
		int f = service.updateCompany(m);// 更新主表FHFA_MANAGER
		if (f > 0) {// 添加行业、业务分类、区域、供应商、厂商
			System.out.println(m.get("FA_INSTRUTRY"));
			service.deleteArea(m);
			service.addArea(m);
			if ("1".equals(m.get("FA_INSTRUTRY_ZT"))) {
				service.deleteAffilidaed(m, "行业类型");
				service.addCompanyAffilidated(JSONArray.fromObject(m.get("FA_INSTRUTRY")), ID);// 行业
			}
			if ("1".equals(m.get("FA_BUSINESS_ZT"))) {
				service.deleteAffilidaed(m, "业务类型");
				service.addCompanyAffilidated(JSONArray.fromObject(m.get("FA_BUSINESS")), ID);// 业务分类
			}
			if ("1".equals(m.get("FA_MANUFACTURERS_ZT"))) {
				service.deleteAffilidaed(m, "厂商");
				service.addCompanyAffilidated(JSONArray.fromObject(m.get("FA_MANUFACTURERS")), ID);// 厂商
			}
			if ("1".equals(m.get("FA_AGENT_ZT"))) {
				service.deleteAffilidaed(m, "供应商");
				service.addCompanyAffilidated(JSONArray.fromObject(m.get("FA_AGENT")), ID);// 供应商
			}

			// 银行保存
			List bankInfo = (List) m.get("bankInfo");
			service.updateBankInfo(bankInfo, ID);
			Map<String,Object> map_tem = Dao.selectOne("fi.fund.getBinkInfoTH", m);
			//更改服务费表中未放款、放款不成功数据的融资租赁公司名、账号、开户行信息
			Dao.update("serviceFee.updateUnPayInfo", map_tem);
			return new ReplyHtml("<script> alert('修改成功');</script>  ");
		} else {
			return new ReplyHtml("<script> alert('修改失败');</script>  ");
		}

	}

	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置", "公司维护" })
	public Reply toViewCompany() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		CompanyManagerService service = new CompanyManagerService();
		context.put("param", service.getCompanyData(map));
		List<Map<String, Object>> hyfl = service.getCompanyHY(map);
		// List<Map<String,Object>> hyfl = service.getCompanyOtherDataHJ(map,
		// "行业类型","行业类型");
		List<Map<String, Object>> ywlx = service.getCompanyOtherDataHJ(map, "业务类型", "业务类型");
		List<Map<String, Object>> cs = service.getCsDataHJ(map, "厂商");
		List<Map<String, Object>> jxs = service.getJxsDataHJ(map, "供应商");
		List<Map<String, Object>> area = service.queryArea(map);
		context.put("hyfl", hyfl);
		context.put("ywlx", ywlx);
		context.put("cs", cs);
		context.put("jxs", jxs);
		context.put("area", area);
		context.put("zgs", service.getZgsData());
		context.put("ad", service.AssembleData(hyfl, ywlx, cs, jxs));
		//查询所有分公司信息
		ManageService  manageService = new ManageService();
		context.put("ORG_INFOLIST", manageService.getSonOrganizations());
		context.put("BANK_INFOLIST", service.queryFHFABANK(map));
		return new ReplyHtml(VM.html(pagePath + "Company_view.vm", context));
	}
}

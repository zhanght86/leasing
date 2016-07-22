package com.pqsoft.base.user.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.sql.BLOB;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.base.user.service.ManageService;
import com.pqsoft.base.user.service.UserService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyMobile;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.cache.Cache;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.UserCache;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.IMG;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.StringUtils;

public class ManageAction extends Action {

	private static final String basePath = "base/user/";
	private static final String baseOrgPath = "base/organization/";
	private UserService service = new UserService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("PContext", param);
		return new ReplyHtml(VM.html(basePath + "userManager.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delUser() {
		Map<String, Object> param = _getParameters();
		int result = service.delUser(param);
		if (result > 0) {
			return new ReplyAjax(true, "删除成功！");
		} else {
			return new ReplyAjax(false, "删除失败！");
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply add() {
		VelocityContext countext = new VelocityContext();
		Map<String, Object> m = new HashMap<String, Object>();
		List empType = (List) new DataDictionaryMemcached().get("员工类型");
		List empStatus = (List) new DataDictionaryMemcached().get("员工状态");
		List level = (List) new DataDictionaryMemcached().get("岗位级别");
		countext.put("empType", empType);
		countext.put("empStatus", empStatus);
		countext.put("level", level);
		// countext.put("BM", service.getOrganinzation());
		// countext.put("personType", service.getPersonType());
		return new ReplyHtml(VM.html(basePath + "addUser.vm", countext));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "销售人员管理" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply addSale() {
		VelocityContext countext = new VelocityContext();
		Map<String, Object> m = _getParameters();
		countext.put("param", m);
		return new ReplyHtml(VM.html(baseOrgPath + "sale_add.vm", countext));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply doAdd() {
		Map<String, Object> m = new HashMap<String, Object>();
		List<FileItem> fileList = _getFileItem();
		m.put("EMPLOYEE_ID", service.selectSeq());
		Iterator<?> i = fileList.iterator();
		File file = null;
		while (i.hasNext()) {
			FileItem fileitem = (FileItem) i.next();
			if (!fileitem.isFormField()) {// 如果是文件
				if (!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())) {
					byte[] buffer = fileitem.get();
					m.put("PHOTO", buffer);
				}
			}
		}
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			m.put(enN.toString(), SkyEye.getRequest().getParameter(enN.toString()).trim());
		}
		String code = (String) m.get("EMPLOYEE_CODE");
		if (code != null) {
			code = code.toLowerCase();
			m.put("EMPLOYEE_CODE", code);
		}
		if (service.validateUserCode(SkyEye.getRequest().getParameter("EMPLOYEE_CODE")) > 0 ? true : false) { return new ReplyAjax(false, "工号重复"); }
		// m.put("PERSON_TYPE", "0");
		m.put("EMPLOYEE_PWD", "CD598281A946ACC7035C7D2C615CC6FB");
		// m.put("EMPLOYEE_ID", service.selectSeq());
		if (1 == service.saveUser(m)) { return new ReplyAjax(true, "", "添加成功"); }
		return new ReplyAjax(false, "", "添加失败");
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "销售人员管理" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply doAddSale() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("EMPLOYEE_ID", service.selectSeq());
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			m.put(enN.toString(), SkyEye.getRequest().getParameter(enN.toString()).trim());
		}
		String code = (String) m.get("EMPLOYEE_CODE");
		if (code != null) {
			code = code.toLowerCase();
			m.put("EMPLOYEE_CODE", code);
		}
		if (service.validateUserCode(SkyEye.getRequest().getParameter("EMPLOYEE_CODE")) > 0 ? true : false) { return new ReplyAjax(false, "工号重复"); }
		// m.put("PERSON_TYPE", "0");
		m.put("EMPLOYEE_PWD", "CD598281A946ACC7035C7D2C615CC6FB");
		// m.put("EMPLOYEE_ID", service.selectSeq());
		
		
		if (1 == service.saveUser(m)) { 
			
			// 将当前新增的用户添加到当前销售组织架构中
			com.pqsoft.base.organization.service.ManageService manageService = new com.pqsoft.base.organization.service.ManageService();
			
			String orgId = m.get("ORG_ID") + "";
			String userId = m.get("EMPLOYEE_ID") + "";
			String JOB_NAME = "销售";
			// 通过人员id查询人员工号
			UserService userService = new UserService();
			Map<String, Object> userMess = new HashMap<String, Object>();
			userMess.put("EMPLOYEE_ID", userId);
			userMess = userService.getUserInfoById(userMess);
			String DEPT_NAME = "";
			String id = orgId;
			while ("".equals(DEPT_NAME)) {
				Map<String, Object> org = manageService.getInfoById(id);
				if (org != null) {
					if ("2".equals(org.get("TYPE") + "")) {
						DEPT_NAME = org.get("NAME") + "";
						break;
					} else {
						id = org.get("PARENT_ID") + "";
					}
				} else {
					break;
				}
			}
			manageService.addOrgUser(orgId, userId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("EMPLOYEE_ID", userId);
			map.put("DEPT_NAME", DEPT_NAME);
			map.put("JOB_NAME", JOB_NAME);
			userService.updateUserById(map);
			
			return new ReplyAjax(true, "", "添加成功"); 
		}
		return new ReplyAjax(false, "", "添加失败");
	}

	@aPermission(name = { "权限管理", "人员管理" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply downloadPictrue() {
		Map<String, Object> param = _getParameters();
		try {
			Map<String, Object> map = service.selectImageById(param);
			BLOB blob = (BLOB) map.get("PHOTO");
			SkyEye.getResponse().setContentType("image/png");
			InputStream in = new BufferedInputStream(blob.getBinaryStream());
			byte[] buf = new byte[1024];
			int hasRead = 0;
			while ((hasRead = in.read(buf)) > 0) {
				SkyEye.getResponse().getOutputStream().write(buf, 0, hasRead);
			}
			SkyEye.getResponse().getOutputStream().flush();
			SkyEye.getResponse().getOutputStream().close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 重置用户密码
	 * 
	 * @author jinhongdong
	 * @return
	 * @throws Exception
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构", "重置密码" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply doUpdatePwdByUserId() {
		String userid = SkyEye.getRequest().getParameter("EMPLOYEE_ID").toString().trim();
		if (service.updatePwdByUserId(userid) != 0) {
			Cache.clear();
			return new ReplyAjax(true, "修改成功");
		} else {
			return new ReplyAjax(false, "修改失败");
		}

	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply show() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("EMPLOYEE_ID", SkyEye.getRequest().getParameter("EMPLOYEE_ID").toString().trim());
		List empType = (List) new DataDictionaryMemcached().get("员工类型");
		List empStatus = (List) new DataDictionaryMemcached().get("员工状态");
		List level = (List) new DataDictionaryMemcached().get("岗位级别");
		context.put("empType", empType);
		context.put("empStatus", empStatus);
		context.put("level", level);
		context.put("user", service.getUserInfoById(m));
		context.put("orgList", new ManageService().getOrgByUser(SkyEye.getRequest().getParameter("EMPLOYEE_ID")));
		context.put("EMPLOYEE_ID", SkyEye.getRequest().getParameter("EMPLOYEE_ID").toString().trim());
		context.put("orgName", service.getSelectOrgParentList(SkyEye.getRequest().getParameter("EMPLOYEE_ID")));
		return new ReplyHtml(VM.html(basePath + "show.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "销售人员管理" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply showSale() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		context.put("user", service.getUserInfoById(m));
		context.put("orgList", new ManageService().getOrgByUser(SkyEye.getRequest().getParameter("EMPLOYEE_ID")));
		context.put("EMPLOYEE_ID", SkyEye.getRequest().getParameter("EMPLOYEE_ID").toString().trim());
		
		if("show".equals(m.get("sale") + "")){
			
			return new ReplyHtml(VM.html(baseOrgPath + "sale_show.vm", context));
		}else{
			
			return new ReplyHtml(VM.html(baseOrgPath + "sale_update.vm", context));
		}
	}
	
	//组织机构里面人员查看页面
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply show1() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("EMPLOYEE_ID", SkyEye.getRequest().getParameter("EMPLOYEE_ID").toString().trim());
		List empType = (List) new DataDictionaryMemcached().get("员工类型");
		List empStatus = (List) new DataDictionaryMemcached().get("员工状态");
		List level = (List) new DataDictionaryMemcached().get("岗位级别");
		context.put("empType", empType);
		context.put("empStatus", empStatus);
		context.put("level", level);
		context.put("user", service.getUserInfoById(m));
		context.put("orgList", new ManageService().getOrgByUser(SkyEye.getRequest().getParameter("EMPLOYEE_ID")));
		context.put("EMPLOYEE_ID", SkyEye.getRequest().getParameter("EMPLOYEE_ID").toString().trim());
		
		context.put("orgName", service.getSelectOrgParentList(SkyEye.getRequest().getParameter("EMPLOYEE_ID")));
		return new ReplyHtml(VM.html(basePath + "show1.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "销售人员管理" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply showSale1() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		context.put("user", service.getUserInfoById(m));
		context.put("orgList", new ManageService().getOrgByUser(SkyEye.getRequest().getParameter("EMPLOYEE_ID")));
		context.put("EMPLOYEE_ID", SkyEye.getRequest().getParameter("EMPLOYEE_ID").toString().trim());
			
		if("show".equals(m.get("sale") + "")){
			
			return new ReplyHtml(VM.html(baseOrgPath + "sale_show.vm", context));
		}else{
			
			return new ReplyHtml(VM.html(baseOrgPath + "sale_update.vm", context));
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply doUpdate() {
		Map<String, Object> m = new HashMap<String, Object>();
		List<FileItem> fileList = _getFileItem();
		Iterator<?> i = fileList.iterator();
		File file = null;
		while (i.hasNext()) {
			FileItem fileitem = (FileItem) i.next();
			if (!fileitem.isFormField()) {// 如果是文件
				if (!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())) {
					byte[] buffer = fileitem.get();
					m.put("PHOTO", buffer);
				}
			}
		}
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			m.put(enN.toString(), SkyEye.getRequest().getParameter(enN.toString()).trim());
		}
		if (1 == service.updateUserById(m)) {
			Cache.clear();
			return new ReplyAjax(true, "", "修改成功");
		} else {
			return new ReplyAjax(false, "", "修改失败");
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "销售人员管理" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply doUpdateSale() {
		Map<String, Object> m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			m.put(enN.toString(), SkyEye.getRequest().getParameter(enN.toString()).trim());
		}
		if (1 == service.updateUserById(m)) {
			Cache.clear();
			return new ReplyAjax(true, "", "修改成功");
		} else {
			return new ReplyAjax(false, "", "修改失败");
		}
	}

	/**
	 * 获取组织架构树
	 * 
	 * @return
	 * @throws Exception
	 * @author 靳洪东
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getOrganizations() {
		String ORG_PARENT_ID = SkyEye.getRequest().getParameter("ORG_PARENT_ID");
		ORG_PARENT_ID = ORG_PARENT_ID == null || ORG_PARENT_ID.toString().trim().length() == 0 ? "0" : ORG_PARENT_ID.toString().trim();
		VelocityContext context = new VelocityContext();
		context.put("data", new com.pqsoft.base.organization.service.ManageService().getOrganizations(ORG_PARENT_ID));
		return new ReplyAjax(VM.html(basePath + "add_organization.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "人员管理" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply toValidateUserCode() {
		Map<String, Object> param = _getParameters();
		int result = service.validateUserCode(param.get("data").toString().trim());
		if (result > 0) {
			return new ReplyAjax(false, "编号已经存在！");
		} else {
			return new ReplyAjax(true, "可以使用");
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "销售人员管理" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply toValidateUserCodeSale() {
		Map<String, Object> param = _getParameters();
		int result = service.validateUserCode(param.get("data").toString().trim());
		if (result > 0) {
			return new ReplyAjax(false, "编号已经存在！");
		} else {
			return new ReplyAjax(true, "可以使用");
		}
	}
}

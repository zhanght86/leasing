package com.pqsoft.base.organization.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.base.user.service.UserService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.cache.Cache;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.UserCache;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

/**
 * 组织架构管理
 */
public class ManageAction extends Action {

	private static final String basePath = "base/organization/";
	private ManageService service = new ManageService();

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("data", service.getOrganizations("0"));
		return new ReplyHtml(VM.html(basePath + "manage.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getOrganizations() {
		String PARENT_ID = SkyEye.getRequest().getParameter("PARENT_ID");
		PARENT_ID = PARENT_ID == null || PARENT_ID.toString().trim().length() == 0 ? "0" : PARENT_ID.toString().trim();
		return new ReplyAjax(service.getOrganizations(PARENT_ID));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getRole() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		String orgId = param.get("ORG_ID").toString();
		String roleContent = param.containsKey("roleContent") && param.get("roleContent") != null ? param.get("roleContent").toString() : "";
		Map<String, Object> info = service.getInfoById(orgId);
		context.put("orgName", info.get("NAME"));
		context.put("orgId", orgId);
		context.put("roleY", service.getHaveRole(orgId));
		context.put("roleN", service.getNotHaveRole(orgId, roleContent));
		return new ReplyHtml(VM.html(basePath + "manage_role.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply doUpdateRole() {
		String type = SkyEye.getRequest().getParameter("TYPE");
		String orgId = SkyEye.getRequest().getParameter("ORG_ID");
		String roleId = SkyEye.getRequest().getParameter("ROLE_ID");
		if ("REMOVE".equals(type)) {
			service.updateOrgRole(orgId, "");
			Cache.clear();
			return new ReplyAjax(true, "removesuccess");
		} else if ("ADD".equals(type)) {
			service.updateOrgRole(orgId, roleId);
			Cache.clear();
			return new ReplyAjax(true, "addsuccess");
		}
		return null;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getUser() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> mm = _getParameters();
		String orgId = mm.get("ORG_ID") + "";
		String userContent = mm.containsKey("userContent") && mm.get("userContent") != null ? mm.get("userContent").toString() : "";
		userContent = "".equals(userContent) ? "" : userContent;
		Map<String, Object> info = service.getInfoById(orgId);
		context.put("orgName", info.get("NAME"));
		context.put("orgId", orgId);
		context.put("userContent", userContent);
		context.put("userY", service.getHaveUser(orgId));
		if (userContent != null && !"".equals(userContent)) {
			context.put("userN", service.getNotHaveUser(orgId, userContent));
		}
		return new ReplyAjax(VM.html(basePath + "manage_user.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply doUpdateUser() {
		String type = SkyEye.getRequest().getParameter("TYPE");
		String orgId = SkyEye.getRequest().getParameter("ORG_ID");
		String userId = SkyEye.getRequest().getParameter("USER_ID");
		String JOB_NAME = SkyEye.getRequest().getParameter("JOB_NAME");
		// 通过人员id查询人员工号
		UserService userService = new UserService();
		Map<String, Object> userMess = new HashMap<String, Object>();
		userMess.put("EMPLOYEE_ID", userId);
		userMess = userService.getUserInfoById(userMess);
		UserCache us = new UserCache();
		if ("REMOVE".equals(type)) {
			service.removeOrgUser(orgId, userId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("EMPLOYEE_ID", userId);
			map.put("DEPT_NAME", "");
			map.put("JOB_NAME", "");
			userService.updateUserById(map);
			// 清除人员信息缓存
			us.clean(userMess.get("EMPLOYEE_CODE").toString());
			return new ReplyAjax(true, "removesuccess");
		} else if ("ADD".equals(type)) {
			String DEPT_NAME = "";
			String id = orgId;
			while ("".equals(DEPT_NAME)) {
				Map<String, Object> org = service.getInfoById(id);
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
			service.addOrgUser(orgId, userId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("EMPLOYEE_ID", userId);
			map.put("DEPT_NAME", DEPT_NAME);
			map.put("JOB_NAME", JOB_NAME);
			userService.updateUserById(map);
			// 清除人员信息缓存
			us.clean(userMess.get("EMPLOYEE_CODE").toString());
			return new ReplyAjax(true, "addsuccess");
		}
		return null;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "销售人员管理" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply doUpdateSaleUser() {
		String type = SkyEye.getRequest().getParameter("TYPE");
		String orgId = SkyEye.getRequest().getParameter("ORG_ID");
		String userId = SkyEye.getRequest().getParameter("USER_ID");
		String JOB_NAME = SkyEye.getRequest().getParameter("JOB_NAME");
		// 通过人员id查询人员工号
		UserService userService = new UserService();
		Map<String, Object> userMess = new HashMap<String, Object>();
		userMess.put("EMPLOYEE_ID", userId);
		userMess = userService.getUserInfoById(userMess);
		UserCache us = new UserCache();
		if ("REMOVE".equals(type)) {
			service.removeOrgUser(orgId, userId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("EMPLOYEE_ID", userId);
			map.put("DEPT_NAME", "");
			map.put("JOB_NAME", "");
			userService.updateUserById(map);
			// 清除人员信息缓存
			us.clean(userMess.get("EMPLOYEE_CODE").toString());
			return new ReplyAjax(true, "removesuccess");
		} else if ("ADD".equals(type)) {
			String DEPT_NAME = "";
			String id = orgId;
			while ("".equals(DEPT_NAME)) {
				Map<String, Object> org = service.getInfoById(id);
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
			service.addOrgUser(orgId, userId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("EMPLOYEE_ID", userId);
			map.put("DEPT_NAME", DEPT_NAME);
			map.put("JOB_NAME", JOB_NAME);
			userService.updateUserById(map);
			// 清除人员信息缓存
			us.clean(userMess.get("EMPLOYEE_CODE").toString());
			return new ReplyAjax(true, "addsuccess");
		}
		return null;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply doUpdateUserAll() throws Exception {
		Boolean b = null;
		Cache.clear();
		String x;
		if (b == true) {
			x = "刷新成功";
		} else {
			x = "刷新失败";
		}
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "销售人员管理" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply getSaleUser() {
		
		VelocityContext context = new VelocityContext();
		
		String orgId = "";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("NAME", Security.getUser().getOrg().getRole());
		map.put("CODE", Security.getUser().getCode());
		
		// 根据用户编码得到当前对象
		Map<String,Object> user = Dao.selectOne("orgazization.getUserOrgInfo", map);
		
		if(StringUtils.isNotBlank(user)){
			
			// 判断当前对象是客服还是客服主管 
			if(StringUtils.isNotBlank(user.get("NAME"))){
				
				// 如果JOB_NAME为客服，找到客服所在组织（组织名为客服）的上级组织并行的名为“销售”的组织，选择销售组织下所有人
				if(user.get("NAME").toString().equals("客服")){
					
					// 根据用户CODE获取当前用户的上上级ORG对象
					Map<String,Object> ppOrg = service.getUserPPOrgByUserCode(map);
					
					if(StringUtils.isNotBlank(ppOrg)){
						
						orgId = getSaleOrgID(orgId, ppOrg);
					}
					
				}else{
					
					// 如果是客服主管（组织名为客服主管），找到客服主管所在组织的并行的名为“销售”的组织，选择销售组织下所有人
					Map<String,Object> parentOrg = service.getUserParentOrgByUserCode(map);
					
					if(StringUtils.isNotBlank(parentOrg)){
						
						orgId = getSaleOrgID(orgId, parentOrg);
					}
					
				}
				
			}
			
		}
		
		context.put("orgId", orgId);
		context.put("userY", service.getHaveUser(orgId));
		
		return new ReplyHtml(VM.html(basePath + "sale_manage_user.vm", context));
	}

	/**
	 * 得到下属销售人员ORGID
	 * @param orgId 	最终返回结果
	 * @param parentOrg 组织架构中客服主管的上级，客服的上上级
	 * @return
	 */
	private String getSaleOrgID(String orgId, Map<String, Object> parentOrg) {
		
		// 获取当前节点下的下属角色
		List<Map<String, Object>> childList = service.getOrganizations(parentOrg.get("ID") + "");
		
		for (Map<String, Object> map : childList) {
			
			if("销售".equals(map.get("NAME")+"")){
				orgId = map.get("ID") + "";
				break;
			}
		}
		return orgId;
	}
	
}

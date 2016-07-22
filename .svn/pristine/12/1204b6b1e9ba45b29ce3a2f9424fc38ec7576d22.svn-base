package com.pqsoft.base.policyPublish.action;

/**
 * 政策发布
 */
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.base.policyPublish.service.PolicyPublishService;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class PolicyPublishAction extends Action {

	private PolicyPublishService service = new PolicyPublishService();

	/**
	 * 用于点击政策发布得到返回的页面
	 */
	@Override
	@aPermission(name = { "参数配置", "政策发布", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		// 将组织机构的信息放入context中
		// ManageService service = new ManageService();
		// context.put("data", service.getOrganizations("0"));
		return new ReplyHtml(VM.html("base/policyPublish/policyPublishShow.vm", context));
	}

	/**
	 * 政策可视用户选择
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toChooserPage() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		// 将组织机构的信息放入context中
		context.put("data", service.getOrganizationsroot("0", param.get("POLICY_ID").toString()));
		context.put("POLICYID", param.get("POLICY_ID").toString());
		return new ReplyHtml(VM.html("base/policyPublish/policyPublishChoser.vm", context));
	}

	/**
	 * 用于主页显示政策发布 TODO-- 根据OrgId判断有无查询权限
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply firstPagePolicy() {
		VelocityContext context = new VelocityContext();
		context.put("USERID", Security.getUser().getId());
		Map<String, Object> param = _getParameters();
		// 此处需要添加根据OrgId判断有无查询权限
		if ("BIG".equals(param.get("ISBIG"))) {
			return new ReplyHtml(VM.html("base/policyPublish/policyPublishByOrgIdBigShow.vm", context));
		} else {
			return new ReplyHtml(VM.html("base/policyPublish/policyPublishByOrgIdShow.vm", context));
		}
	}

	/**
	 * 用于分页查询
	 */
	// @aPermission(name = { "参数配置", "政策发布", "主页面" })
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}

	/**
	 * 用于保存修改后的政策发布
	 */
	@aPermission(name = { "参数配置", "政策发布", "修改" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doUpdatePolicy() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.updatePolicy(param);
		if (result > 0) {
			flag = true;
			msg = "保存成功！";
		} else {
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 用于插入一条新的政策发布
	 */
	@aPermission(name = { "参数配置", "政策发布", "增加" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doAddPolicy() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.insertPolicy(param);
		System.out.println(result);
		if (result > 0) {
			flag = true;
			msg = "保存成功！";
		} else {
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 用于删除一条政策发布
	 */
	@aPermission(name = { "参数配置", "政策发布", "删除" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doDeletePolicy() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		// 1.删除政策条目
		int result = service.deletePolicy(param);
		// 2.删除政策附件
		param.put("POLICY_ID", param.get("ID"));
		result *= service.deletePolicyFile(param);
		// 3.删除中间表
		service.deletePolicyMidTab(param);
		return new ReplyAjax(true, "");
	}

	/**
	 * 用于上传附件
	 * 文件上传还有问题
	 */
	@aPermission(name = { "参数配置", "政策发布", "上传文件" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongsflc.com", name = "韩晓龙")
	public Reply uploadAttachment() {
		// 1.得到上传的文件
		File filetemp = _getFileOne();

		// 新增内容
		// 2.创建政策附件的目录 webapp下 创建policyFile目录用于存放 政策公告附件
		String filepath = "";
		// filepath =
		// SkyEye.getRequest().getSession().getServletContext().getRealPath("/");
		// filepath += "policyFile";
		String root = SkyEye.getConfig("file.path").toString();
		// 拿到配置文件中设置的上传文件路径
		filepath = root + File.separator + "policyFile";
		// createDirectory(root);// 调用创建存放上传文件 文件夹方法
		File temp = null;
		temp = new File(filepath);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		filepath += File.separator + filetemp.getName();
		temp = new File(filepath);
		try {
			FileCopyUtils.copy(FileCopyUtils.copyToByteArray(filetemp), temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 4.删除上传的临时文件
		// filetemp.delete();
		Map<String, Object> param = _getParameters();
		// 从拼接的请求参数中取出ID
		param.put("POLICY_ID", param.get("ID"));
		// param.put("FILE_NAME", filetemp.getName());
		param.put("FILE_NAME", temp.getName());
		// param.put("FILE_PATH", filetemp.getPath());
		param.put("FILE_PATH", temp.getPath());

		Boolean flag = true;
		String msg = "";
		int result = service.insertPolicyFile(param);
		if (result > 0) {
			flag = true;
			msg = "保存成功！";
		} else {
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	/* 修改前
	 * public Reply uploadAttachment(){
	 * //1.得到上传的文件
	 * File filetemp = _getFileOne();
	 * //2.创建政策附件的目录 webapp下 创建policyFile目录用于存放 政策公告附件
	 * String filepath = "";
	 * filepath =
	 * SkyEye.getRequest().getSession().getServletContext().getRealPath("/");
	 * filepath += "policyFile";
	 * File temp = null;
	 * File target = null;
	 * try {
	 * temp = new File(filepath);
	 * if (!temp.exists()) {
	 * temp.mkdirs();
	 * }
	 * filepath += "/" + filetemp.getName();
	 * // 3.将文件复制到指定的路径 webapp下的policyFile目录
	 * target = new File(filepath);
	 * FileCopyUtils.copy(FileCopyUtils.copyToByteArray(filetemp), target);
	 * //4.删除上传的临时文件
	 * filetemp.delete();
	 * } catch (IOException e) {
	 * throw new ActionException("error text!");
	 * }
	 * Map<String,Object> param = _getParameters();
	 * //从拼接的请求参数中取出ID
	 * param.put("POLICY_ID", param.get("ID"));
	 * param.put("FILE_NAME", target.getName());
	 * param.put("FILE_PATH", target.getPath());
	 * Boolean flag = true;
	 * String msg = "";
	 * int result = service.insertPolicyFile(param);
	 * if(result>0){
	 * flag = true;
	 * msg = "保存成功！";
	 * }else{
	 * flag = false;
	 * msg = "保存失败！";
	 * }
	 * return new ReplyAjax(flag,msg);
	 * } */
	/**
	 * 用于下载文件
	 * 未完成 文件名还是有问题
	 * 
	 * @return
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply downloadAttachment() {
		Map<String, Object> param = _getParameters();
		Map map = (Map) service.selectPolicyFileById(param);
		return new ReplyFile(new File((String) map.get("FILE_PATH")), (String) map.get("FILE_NAME"));
	}

	/**
	 * 可视化用户选择
	 * 插入中间关系表 T_MID_POLICY_TO_ORG_AND_FILE
	 * 
	 * @return
	 */
	@aPermission(name = { "参数配置", "政策发布", "可视化用户选择" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply chooseReader() {
		String arrOrg = "";
		// 传入政策ID,可视化的组织机构ID
		Map<String, Object> param = _getParameters();
		param.put("POLICY_ID", param.get("ID"));
		arrOrg = (String) param.get("arrOrg");
		// 1.先删除
		service.deleteMidPolicyTable(param);
		if (!"".equals(arrOrg)) {
			// 如果有选择组织
			String[] arrays = arrOrg.split(",");
			// TODO 如果是按照用户组保存
			List list;
			if ("IFORG".equals(param.get("IFORG"))) {
				for (int i = 0; i < arrays.length; i++) {
					param.put("ORGID", arrays[i]);
					list = service.getAllOrg(param);
					if (list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							param.put("ORG_ID", ((Map) list.get(j)).get("NEWORGID").toString());
							service.insertMidPolicyTable(param);
						}
					}
				}
			} else {
				// 2.再插入新记录
				for (int i = 0; i < arrays.length; i++) {
					param.put("ORG_ID", arrays[i]);
					service.insertMidPolicyTable(param);
				}
			}
		}
		return new ReplyAjax(true, "");
	}

	/**
	 * 可视化用户查询
	 * 
	 * @return
	 */
	@aPermission(name = { "参数配置", "政策发布", "查看可视化用户" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply selectReader() {
		// 传入政策ID
		Map<String, Object> param = _getParameters();
		param.put("POLICY_ID", param.get("ID"));
		JSONArray json = JSONArray.fromObject(service.selectMidPolicyTable(param));
		return new ReplyJson(json);
	}

	/**
	 * 查询所有的附件
	 * 
	 * @return
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply selectAllFiles() {
		// 传入政策ID
		Map<String, Object> param = _getParameters();
		param.put("POLICY_ID", param.get("ID"));
		JSONArray json = JSONArray.fromObject(service.selectPolicyFile(param));
		return new ReplyJson(json);
	}

	/**
	 * 根据id删除一个附件
	 * 
	 * @return
	 */
	@aPermission(name = { "参数配置", "政策发布", "删除附件" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply deleteOneFile() {
		Map<String, Object> param = _getParameters();
		int result = service.deletePolicyFileById(param);
		Boolean flag = true;
		String msg = "";
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
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply getOrganizations() {
		String PARENT_ID = SkyEye.getRequest().getParameter("PARENT_ID");
		PARENT_ID = PARENT_ID == null || PARENT_ID.toString().trim().length() == 0 ? "0" : PARENT_ID.toString().trim();
		return new ReplyAjax(service.getOrganizations(PARENT_ID, _getParameters().get("POLICY_ID").toString()));
	}
}

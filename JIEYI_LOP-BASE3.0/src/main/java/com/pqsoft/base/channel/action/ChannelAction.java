package com.pqsoft.base.channel.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.channel.service.ChannelService;
import com.pqsoft.bpm.service.JBPMService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.SecuUtil;
import com.pqsoft.util.StringUtils;

public class ChannelAction extends Action {

	private ChannelService service = new ChannelService();
	private JBPMService jService = new JBPMService();

	/**
	 * @Title: startWorkflow
	 * @Description: TODO()
	 * @return
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "发起审批流程" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doStartProcess() {
		Map<String, Object> param = _getParameters();
		return jService.doStartProcess(param);
	}

	/**
	 * <p>
	 * Title: execute
	 * </p>
	 * <p>
	 * Description: TODO(表单01)
	 * </p>
	 * 
	 * @return
	 * @see com.pqsoft.skyeye.api.Action#execute()
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "表单01" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply form01() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		// System.out.println(param);
		// String app_type = StringUtils.nullToString(param.get("app_type"));
		// String sup_id = StringUtils.nullToString(param.get("sup_id"));
		// String credit_id = StringUtils.nullToString(param.get("credit_id"));
		// 供应商基础信息
		// param.put("SUP_ID", param.get("PROJECT_ID"));
		// param.put("APP_TYPE", app_type);
		// param.put("SUP_ID", sup_id);
		// param.put("CREDIT_ID", credit_id);

		SecuUtil.putUserInfo(param);
		// 生成资信申请单
		if (param.get("APP_ID") == null || "".equals(param.get("APP_ID"))) {
			param.put("APP_ID", service.insertCreditApp(param));

		}
		context.put("APP_ID", param.get("APP_ID"));
		context.put("AppInfos", service.getAppInfo(param));
		context.put("score", service.selectCreditScore(param));

		context.put("supplier", service.getOneSupplier(param));
		context.put("LinkMans", service.getLinkManList(param));
		param.put("INVEST_TYPE", "0");
		context.put("Naturals", service.getInvestsByType(param));
		param.put("INVEST_TYPE", "1");
		context.put("Legals", service.getInvestsByType(param));
		context.put("files", service.findSupFileUploads(param.get("SUP_ID").toString(), "基本材料", StringUtils.nullToString(param.get("APP_ID")), null));
		context.put("bgfiles",
				service.findSupFileUploads(param.get("SUP_ID").toString(), "财务报表", StringUtils.nullToString(param.get("APP_ID")), null));
		context.put("dfkfiles",
				service.findSupFileUploads(param.get("SUP_ID").toString(), "打分卡", StringUtils.nullToString(param.get("APP_ID")), null));
		context.put("allArea", service.getAllArea());

		Map<String, Object> supplier = (Map<String, Object>) context.get("supplier");
		// 未授信 或者初始授信下 不添加历史信用记录授信
		if (!StringUtils.nullToOther(supplier.get("STATUS"), "-1").equals("-1")) {
			// 历史逾期记录 月
			service.getOverdueInfos(context, param);
		}

		return new ReplyHtml(VM.html("/base/channel/form01.vm", context));
	}

	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * <p>
	 * Title: execute
	 * </p>
	 * <p>
	 * Description: TODO(表单01)
	 * </p>
	 * 
	 * @return
	 * @see com.pqsoft.skyeye.api.Action#execute()
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "表单02" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply form02() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		// System.out.println(param);
		// String app_type = StringUtils.nullToString(param.get("app_type"));
		// String sup_id = StringUtils.nullToString(param.get("sup_id"));
		// String credit_id = StringUtils.nullToString(param.get("credit_id"));
		// 供应商基础信息
		// param.put("SUP_ID", param.get("PROJECT_ID"));
		// param.put("APP_TYPE", app_type);
		// param.put("SUP_ID", sup_id);
		// param.put("CREDIT_ID", credit_id);

		SecuUtil.putUserInfo(param);
		// 生成资信申请单
		if (param.get("APP_ID") == null || "".equals(param.get("APP_ID"))) {
			param.put("APP_ID", service.insertCreditApp(param));
		}
		context.put("APP_ID", param.get("APP_ID"));
		context.put("AppInfos", service.getAppInfo(param));
		context.put("score", service.selectCreditScore(param));

		context.put("supplier", service.getOneSupplier(param));
		context.put("LinkMans", service.getLinkManList(param));
		param.put("INVEST_TYPE", "0");
		context.put("Naturals", service.getInvestsByType(param));
		param.put("INVEST_TYPE", "1");
		context.put("Legals", service.getInvestsByType(param));
		context.put("files", service.findSupFileUploads(param.get("SUP_ID").toString(), "基本材料", StringUtils.nullToString(param.get("APP_ID")), null));
		context.put("bgfiles",
				service.findSupFileUploads(param.get("SUP_ID").toString(), "财务报表", StringUtils.nullToString(param.get("APP_ID")), null));
		context.put("dfkfiles",
				service.findSupFileUploads(param.get("SUP_ID").toString(), "打分卡", StringUtils.nullToString(param.get("APP_ID")), null));
		context.put("allArea", service.getAllArea());

		Map<String, Object> supplier = (Map<String, Object>) context.get("supplier");
		// 未授信不添加历史信用记录授信
		if (StringUtils.nullToOther(supplier.get("STATUS"), "-1").equals("1")) {
			// 历史逾期记录 月
			service.getOverdueInfos(context, param);
		}

		return new ReplyHtml(VM.html("/base/channel/form02.vm", context));
	}

	/**
	 * @Title: uploadFile
	 * @Description: TODO()
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "上传附件" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply uploadFile() {
		int count = 0;
		Map<String, Object> m = _getParameters();
		System.out.println(m);
		String suppliers_id = m.get("SUP_ID").toString();
		Map<String, Object> map = service.uploadFileForOne(SkyEye.getRequest());
		try {
			map.put("FIL_MEMO", m.get("FIL_MEMO"));
			map.put("SUP_ID", suppliers_id);
			map.put("FIL_TYPE", m.get("FIL_TYPE"));
			map.put("APP_ID", m.get("APP_ID"));
			map.put("APP_NAME", m.get("APP_NAME"));
			count = service.addSupFileUpload(map);
		} catch (Exception e) {
			logger.error(e);
		}
		if (count > 0) {
			List<Object> listfile = service.findSupFileUploads(suppliers_id, (String) m.get("FIL_TYPE"), StringUtils.nullToString(m.get("APP_ID")),
					StringUtils.nullToString(m.get("APP_NAME")));
			return new ReplyJson(JSONArray.fromObject(listfile));
		} else {
			return new ReplyAjax(true, "上传成功！");
		}
	}

	/**
	 * @Title: downLoadSupFile
	 * @Description: TODO()
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "下载附件" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "程龙达")
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

	/**
	 * @Title: deleteSupFile
	 * @Description: TODO()
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170016", email = "jinhondon@126.com", name = "程龙达")
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

	/**
	 * @Title: savestat
	 * @Description: (保存申请单)
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply savestat() {

		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);
		Boolean flag = true;
		String msg = "";
		System.out.println(param);
		// 保存打分卡
		service.savescore(param);
		service.savestat(param);

		msg = "保存成功！";
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 来款核销
	 */

	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "来款核销" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toMgAppCheckMg() {
		// 获取页面参数
		VelocityContext context = new VelocityContext();
		// context.put("param", map);
		return new ReplyHtml(VM.html("/base/channel/toAppCheckMg.vm", context));
	}

	/**
	 * 来款核销 流程中
	 */

	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "流程中", "来款核销" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toMgAppCheckPro() {
		Map<String, Object> param = _getParameters();
		// 获取页面参数
		VelocityContext context = new VelocityContext(param);

		context.put("param", param);
		return new ReplyHtml(VM.html("/base/channel/toAppCheckMgPro.vm", context));
	}

	/**
	 * @Title: toMgAppCheckMgData
	 * @Description: TODO(流程中)
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "流程中", "核销界面数据" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toMgAppCheckProData() {
		Map<String, Object> param = _getParameters();// 页面参数
		System.out.println("====" + param);
		param.put("APP_ID", param.get("APPLY_ID").toString());
		return new ReplyAjaxPage(service.toMgAppAlreadyPro(param));// 查看核销列表
	}

	/**
	 * @Title: toMgAppCheckMgData
	 * @Description: TODO()
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "核销界面数据" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toMgAppCheckMgData() {
		Map<String, Object> param = _getParameters();// 页面参数
		return new ReplyAjaxPage(service.toMgAppAlready(param));// 查看核销列表
	}

	/**
	 * @Title: getPoolInfo
	 * @Description: TODO()
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "资金池信息" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply getPoolInfo() {
		Map<String, Object> param = _getParameters();// 页面参数
		return new ReplyAjax(service.getPoolInfo(param));
	}

	/**
	 * @Title: getCheckedDetail
	 * @Description: TODO()
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "来款核销" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply dohexiao() {
		Map<String, Object> param = _getParameters();// 页面参数
		SecuUtil.putUserInfo(param);
		return new ReplyAjax(service.dohexiao(param));
	}

	/**
	 * @Title: getCheckedDetail
	 * @Description: TODO()
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "渠道管理", "额度变更", "来款核销" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply getCheckedDetail() {
		Map<String, Object> param = _getParameters();// 页面参数
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("/base/channel/fundNEbankAppDe.vm", context));
	}

}

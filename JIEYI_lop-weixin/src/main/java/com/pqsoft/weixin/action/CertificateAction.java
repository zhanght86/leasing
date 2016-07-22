package com.pqsoft.weixin.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import jsp.weixin.ParamesAPI.util.WeixinJSUtil;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bjca.service.OASClient;
import com.pqsoft.bpm.service.MemoService;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.buyCertificate.service.BuyCertificateService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.project.service.ContractService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.rzzlAPI.CarRegistAPI;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.osi.api.Mail;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;
import com.pqsoft.weixin.service.BzManageService;
import com.pqsoft.weixin.utils.AppUtils.Phase;

/**
 * 合同签订
 * 
 * @author LUYANFENG @ 2015年4月1日
 *
 */
public class CertificateAction extends AbstractWeiXinResponseAction {

	private BzManageService bzManageSer = new BzManageService();
	private BuyCertificateService buycerSer = new BuyCertificateService();

	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.ALL)
	public Reply listHTML() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/certificate/listHTML.ejs.vm",
				context));
	}

	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.ALL)
	public Reply qryListHTML() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/certificate/qryListHTML.ejs.vm",
				context));
	}

	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.ALL)
	public Reply bcListHTML() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/certificate/bcListHTML.ejs.vm",
				context));
	}

	/**
	 * 签订合同列表
	 */
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply list() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		/*
		 * Page page = this.getFKPage( param); context.put("page", page);
		 */
		return new ReplyHtml(VM.html("weixin/certificate/list.vm", context));
	}

	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply nextPageJson() {
		Map<String, Object> param = _getParameters();
		JSONObject json = new JSONObject();
		// IS_BC_DATA |
		Object IS_FK_ING = param.get("IS_FK_ING");
		Page page = null;
		if (StringUtils.isBlank(IS_FK_ING) || "cx".equals(IS_FK_ING)) { // 放款中
			page = this.getFKCXPage(param);
		} else if ("fk".equals(IS_FK_ING)) { // 放款前
			page = this.getFKSQPage(param);
		} else if ("bc".equals(IS_FK_ING)) { // 补充
			page = this.getFKBCPage(param);
		}
		json.put("page", page);
		return new ReplyJson(json);
	}

	/**
	 * 补充放款资料列表
	 */
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply bcList() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/certificate/bcList.vm", context));
	}

	/**
	 * 查询列表
	 */
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply qryList() {
		// param.put("IS_FK_ING", "-100"); // 放款中（申请 - 通过 | 不通过 - 后督）
		// param.put("IS_FK_ING", "2"); // 放款申请
		// param.put("IS_FK_ING", "4"); // 放款通过
		// param.put("IS_FK_ING", "5"); // 放款不通过
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/certificate/qryList.vm", context));
	}

	/**
	 * 放款申请
	 */
	private Page getFKSQPage(Map<String, Object> param) {
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSuppId());
		}
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSpId());
		}
		BaseUtil.getDataAllAuth(param);
		param.put("USERCODE", Security.getUser().getCode());
		Page page = PageUtil.getPage(param, "weixin_fk.getCertificatePageList",
				"weixin_fk.getCertificatePageList_count");
		return page;
	}

	/**
	 * 放款查询 查询发起的和通过的
	 * 
	 * @author xgm 2015年4月16日
	 */
	private Page getFKCXPage(Map<String, Object> param) {
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSuppId());
		}
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSpId());
		}
		BaseUtil.getDataAllAuth(param);
		param.put("USERCODE", Security.getUser().getCode());
		Page page = PageUtil.getPage(param,
				"weixin_fk.getCertificatePageList1",
				"weixin_fk.getCertificatePageList_count1");
		return page;
	}

	/**
	 * 放款补充资料
	 * 
	 * @author xgm 2015年4月16日
	 */
	private Page getFKBCPage(Map<String, Object> param) {
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSuppId());
		}
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSpId());
		}
		BaseUtil.getDataAllAuth(param);
		param.put("USERCODE", Security.getUser().getCode());
		Page page = PageUtil.getPage(param,
				"weixin_fk.getCertificatePageList2",
				"weixin_fk.getCertificatePageList_count2");
		return page;
	}

	/**
	 * 签订合同 : 上传资料 提交申请列表
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	public Reply upMaterial() {
		Map<String, Object> param = _getParameters();
		String proId = (String) param.get("PROJECT_ID"); // PROJECT_ID
		if (StringUtils.isBlank(proId)) {
			throw new ActionException("无法访问");
		}

		String FK_ID = (String) param.get("DETAIL_ID");
		String FFPH_STATUS = (String) param.get("FFPH_STATUS");
		Phase PHASE = Phase.放款前;
		// 流程改了，所以现在放款终审没有驳回，扣款后（就意味着成功了）就是4 ， 后督驳回到补充资料也最终也是会放款通过的。
		if (StringUtils.isNotBlank(FFPH_STATUS)
				&& ("4".equals(FFPH_STATUS) || "5".equals(FFPH_STATUS))) {
			PHASE = Phase.放款后;
		}
		VelocityContext vc = new VelocityContext();
		vc.put("param", param);
		// ----------------------以下代码来自
		// com.pqsoft.crm.action.CustomerAction.toMgElectronicPhotoAlbum11()
		// String mainType = (String)param.get("mainType"); // 没用？？

		Map<String, Object> qryMap = new HashMap<String, Object>();
		qryMap.put("ID", proId);
		Map<String, Object> selectOne = Dao.selectOne(
				"weixin_view.select_business_detail1", proId);
		vc.put("is_owner",
				Security.getUser().getId().equals(selectOne.get("CREATE_ID")));
		// if (selectOne != null) vc.put("is_canWrite",
		// (selectOne.get("LNAME").toString().contains("补充资料") ||
		// "0".equals(selectOne.get("STATUS").toString())));

		List<Map<String, Object>> mts = this.bzManageSer.getFile(vc, PHASE,
				proId, FK_ID);
		vc.put("mts", mts);
		vc.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/certificate/upMaterial.vm", vc));
	}

	/**
	 * 进程
	 * 
	 * @author LUYANFENG @ 2015年4月15日
	 * @deprecated
	 * @see Business!detail3.action
	 */
	@aDev(code = "170043", email = "lichaohn@163.com", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply detail3() {
		VelocityContext vc = new VelocityContext();
		Map<String, Object> param = _getParameters();
		vc.put("param", param);
		param.put("ID", param.get("PROJECT_ID"));

		if (StringUtils.isBlank((String) param.get("ID"))) {
			throw new ActionException("无法访问");
		}
		// Map<String, Object> selectOne =
		// Dao.selectOne("weixin_view.select_payment_detail3", param);
		/*
		 * List<Map<String, Object>> list =
		 * Dao.selectList("weixin_view.getJbpmMemo", selectOne); vc.put("list",
		 * list);
		 */
		/*
		 * Map<String, Object> map=Dao.selectOne("weixin_view.getJbpmNow",
		 * selectOne); if(map==null) { map=new HashMap<String, Object>();
		 * map.put("TASK_NAME", "结束"); } vc.put("map", map);
		 */

		TaskService service = new TaskService();
		// 流程ID
		String jbpmId = "";
		projectService proSservice = new projectService();
		List<Map<String, Object>> doShowProjectJbpmList = proSservice
				.doShowProjectJbpmList((String) param.get("PROJECT_ID"));
		if (doShowProjectJbpmList != null && !doShowProjectJbpmList.isEmpty()) {
			Map<String, Object> showjbpm = doShowProjectJbpmList
					.get(doShowProjectJbpmList.size() - 1);
			String memoId = showjbpm.get("MEMEID").toString();
			Map<String, Object> memo = new MemoService().getMemo(memoId);
			if (memo == null)
				throw new ActionException("等待流程审批！");
			jbpmId = (String) memo.get("JBPM_ID");
		}
		if (StringUtils.isBlank(jbpmId))
			throw new ActionException("等待流程审批！");

		List<Map<String, Object>> memos = new MemoService().getMemos(service
				.getShortName(jbpmId));
		vc.put("jbpm", service.getJbpm(service.getShortName(jbpmId)));
		vc.put("progress", memos);
		vc.put("memos", service.reverse(memos));
		vc.put("actUsers",
				service.getTaskByJbpmId(service.getShortName(jbpmId)));

		vc.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/certificate/detail3.vm", vc));
	}

	/**
	 * 合格证录入页面
	 * 
	 * @deprecated
	 * @see Business!detail_tab.action
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	public Reply certificateData() {
		VelocityContext context = new VelocityContext();

		Map<String, Object> map = _getParameters();
		context.put("param", map);
		String PROJECT_ID = (String) map.get("PROJECT_ID");

		Map<String, Object> qryMap = new HashMap<String, Object>();
		// 设备信息 PROJECT_ID
		qryMap.put("PROJECT_ID", PROJECT_ID);
		List<Map<String, Object>> equipInfos = Dao.selectList(
				"weixin_view.selectEquipInfo", qryMap);
		context.put("eqiupInfo",
				equipInfos != null && !equipInfos.isEmpty() ? equipInfos.get(0)
						: null);
		// 查询最大流水号
		/*
		 * String maxno=buycerSer.getMaxno(); context.put("NO", maxno);
		 * List<Object> crelist =
		 * Dao.selectList("buyCertificate.queryDataDictionaryForName", "唯一标识");
		 * context.put("crelist",crelist );
		 */
		return new ReplyHtml(VM.html("weixin/certificate/certificateData.vm",
				context));
	}

	/**
	 * 生成合格证 更新设备信息
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	public Reply generateCertificate() {
		Map<String, Object> param = _getParameters();
		String CERTIFICATE_ID = (String) param.get("CERTIFICATE_ID");
		String productCode = (String) param.get("productCode");
		JSONObject json = new JSONObject();
		json.put("ok", false);

		try {

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.putAll(param);
			dataMap.put("USERNAME", Security.getUser().getName());
			dataMap.put("PLATFORM", Security.getUser().getOrg().getPlatform());

			if (StringUtils.isBlank(dataMap.get("carSysmle"))) {
				json.put("ok", false);
				json.put("error", "请指定整机编号（车架号）");
				return new ReplyJson(json);
			}
			if (StringUtils.isBlank(dataMap.get("GPS_CODE"))) { // != GPS_NUMBER
																// ??
				json.put("ok", false);
				json.put("error", "请指定GPS编号");
				return new ReplyJson(json);
			}
			boolean a = false;
			// Dao.selectOne("weixin_view.selectPayInfo", PROJECT_ID);
			if (StringUtils.isBlank(CERTIFICATE_ID)) { // 添加合格证信息
				CERTIFICATE_ID = buycerSer.getCertificateid();
				dataMap.put("CERTIFICATE_ID", CERTIFICATE_ID);
				dataMap.put("code", dataMap.get("carSysmle"));
				a = buycerSer.addCertificateInfo(dataMap);
				CarRegistAPI.sendCarInfoMonitor(CERTIFICATE_ID, productCode);
			} else { // 更新合格证信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("GPS_CODE", dataMap.get("GPS_CODE"));
				map.put("CAR_SYMBOL", dataMap.get("carSysmle"));
				map.put("ID", CERTIFICATE_ID);
				int update = Dao.update("weixin_view.update_certificate", map);
				a = update == 1;
				CarRegistAPI.modifyCarInfoMonitor(CERTIFICATE_ID, productCode);
			}
			dataMap.put("status", "1");
			boolean b = buycerSer.updateEquip_Certificateid(dataMap); // 添加设备信息

			if (a && b) {
				json.put("ok", true);
			} else {
				json.put("ok", false);
				Dao.rollback();
			}
		} catch (Exception e) {
			Dao.rollback();
			e.printStackTrace();
			json.put("ok", false);
			json.put("error", e.getMessage());
		}
		return new ReplyJson(json);
	}

	/**
	 * old 签订合同
	 * 
	 * @see com.pqsoft.weixin.action.CertificateAction.list()
	 */
	@aDev(code = "170025", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply qianding() {
		VelocityContext context = new VelocityContext();
		context.put("qlist", Dao.selectList("buyCertificate.getPageList"));
		return new ReplyHtml(VM.html("weixin/certificate/Qianding.vm", context));
	}

	@aDev(code = "170025", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findone() {
		Map<String, Object> param = _getParameters();
		String PAY_ID = (String) param.get("PAY_ID");
		String PROJECT_ID = (String) param.get("PROJECT_ID");
		String PAY_MONEY = (String) param.get("PAY_MONEY");

		VelocityContext context = new VelocityContext();
		context.put("param", param);

		Map<String, Object> qryMap = new HashMap<String, Object>();
		qryMap.put("PAY_ID", PAY_ID);
		// Object CERTIFICATE_ID=param.get("CERTIFICATE_ID");
		List<Map<String, Object>> dd = Dao.selectList("buyCertificate.chejia",
				qryMap);
		context.put("chejia", dd);
		if (!dd.isEmpty()) {
			// context.put("chejia", Dao.selectList("buyCertificate.chejia",
			// param));
			// context.put("gpshao", Dao.selectList("buyCertificate.gpshao",
			// param));
			qryMap.clear();
			qryMap.put("PAY_MONEY", PAY_MONEY);
			qryMap.put("PROJECT_ID", PROJECT_ID);
			context.put("qlist",
					Dao.selectList("buyCertificate.getone", qryMap));
			return new ReplyHtml(VM.html("weixin/certificate/findone.vm",
					context));
		} else {
			// context.put("gpshao", Dao.selectList("buyCertificate.gpshao",
			// param));
			context.put("qlist",
					Dao.selectList("buyCertificate.getone", qryMap));
			return new ReplyHtml(VM.html("weixin/certificate/findkong.vm",
					context));
		}
	}

	@aDev(code = "170025", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply saveqian() {
		Map<String, Object> param = _getParameters();
		System.out.println("dddddddddddddd" + param);
		int flag = Dao.update("buyCertificate.updateding", param);
		if (flag > 0) {
			return new ReplyAjax(true, "保存成功");
		} else {
			return new ReplyAjax(false, "保存失败");
		}
	}

	@aDev(code = "170025", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply saveqianding() {
		Map<String, Object> param = _getParameters();
		System.out.println("rrrrrrrrrrrrr" + param);
		int flag = Dao.insert("buyCertificate.saveqian", param);
		if (flag > 0) {
			return new ReplyAjax(true, "保存成功");
		} else {
			return new ReplyAjax(false, "保存失败");
		}
	}

	/**
	 * 立项流程通过发邮件
	 * 
	 * @param project_id
	 * @author xgm 2015年4月7日
	 */
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "xgm")
	@aAuth(type = aAuthType.LOGIN)
	public Reply sendMail() {
		String msg = "邮件发送失败!";
		Boolean flag = false;
		try {
			Map<String, Object> params = _getParameters();
			Object PROJECT_ID = params.get("PROJECT_ID");
			if (StringUtils.isBlank(PROJECT_ID)) {
				return new ReplyAjax(false, "邮件发送失败：请指定项目ID");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mqz = new HashMap<String, Object>();

			String pdfName = "电子合同导出";
			Map<String, Object> emailParam = Dao.selectOne("bpm.status.getEmail", params);
			/*if (param.get("SEND_NUM").toString().equals("0")) {
				String email = param.get("EMAIL").toString();
				String email1[] = email.split("@");
				int cd = email1[0].length() - 2;
				String xing = "";
				for (int i = 0; i < cd; i++) {
					xing = xing + "*";
				}
				email = email1[0].substring(0, 2) + xing + "@" + email1[1];
				msg = "合同已生成，请去邮箱查收!" + email;
				flag = true;
			}*/
			String email = (String) emailParam.get("EMAIL");
			if (StringUtils.isBlank(email)) {
				return new ReplyAjax(false, "邮件发送失败：没有邮件地址");
			}
			String p = "^\\w+@.+$";
			if (!Pattern.matches(p, email)) {
				return new ReplyAjax(false, "邮件发送失败：邮件地址格式不正确");
			}
			String time = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			String addresses[] = { email };
			String root = SkyEye.getConfig("file.path");
			String imgPath = "";
			String targetPath = "";
			List<Map<String, Object>> list = Dao.selectList("contract.queryContractTemplateList", params);
			File[] file1 = new File[list.size()];
			try {
				ContractService service = new ContractService();
				for (int j = 0; j < list.size(); j++) {
					map = list.get(j);
					String TPM_ID = map.get("TEP_ID") + "";
					// pdf文件的路径
					String path = map.get("TEMPLATE_PATH") + "";
					String name = map.get("FILE_NAME") + ".pdf";
					mqz = service.getElectronicSignature(map);
					String text = mqz.get("TPM_SEAL_SERVICE").toString();
					String outputPath = service.ExpContractFile(TPM_ID, map,path, name);
					File outputFile = new File(outputPath);
					targetPath = outputFile.getParent() + File.separator + time
							+ outputFile.getName();
					imgPath = root + File.separator + "baseRes"
							+ File.separator + mqz.get("TPM_SIGNATURE_LOGO")
							+ ".gif";
					try {
						OASClient.execPdf(outputPath, targetPath, text, imgPath);
					} catch (Exception e) {
						e.printStackTrace();
						return new ReplyAjax(false, "添加签章失败");
					}
					file1[j] = new File(targetPath);
					params.put("TYPE", pdfName);
					params.put("PATH", targetPath);
					params.put("NAME", time + outputFile.getName());
					Dao.insert("bpm.status.insHistoryContract", params);
					params.put("SEND_NUM", "1");
					Dao.update("weixin_view.UpdProjecctEmail", params);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ReplyAjax(false, "PDF导出失败!");
			}
			try {
				this.sendMail1(addresses, pdfName, pdfName, file1);
				
				String email1[] = email.split("@");
				int cd = email1[0].length() - 2;
				String xing = "";
				for (int i = 0; i < cd; i++) {
					xing = xing + "*";
				}
				email = email1[0].substring(0, 2) + xing + "@" + email1[1];
				msg = "合同已生成，请去邮箱查收!" + email;
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				msg = "邮件发送失败!";
				flag = false;
			}

		} catch (Throwable e) {
			e.printStackTrace();
			flag = false;
			msg = "" + e.getMessage();
		}
		return new ReplyAjax(flag, msg);
	}

	public void sendMail1(final String[] addresses, final String title, final String text,
			final File[] files) throws Exception {
		String stmp = null;
		String username = null;
		String password = null;
		try {
			List<Object> list = (List) new SysDictionaryMemcached().get("系统邮箱");
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<?, ?> m = (Map<?, ?>) list.get(i);
					if (m.get("FLAG").toString().equalsIgnoreCase("stmp")) {
						stmp = m.get("CODE").toString();
					}
					if (m.get("FLAG").toString().equalsIgnoreCase("password")) {
						password = m.get("CODE").toString();
					}
					if (m.get("FLAG").toString().equalsIgnoreCase("username")) {
						username = m.get("CODE").toString();
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("读取邮箱配置失败", e);
		}
		if (stmp == null || username == null || password == null)
			throw new Exception("邮箱配置错误");

		if (addresses == null || title == null || text == null)
			throw new Exception("邮件格式错误");

		try {

			final String s1 = stmp;
			final String s2 = username;
			final String s3 = password;
			new Thread() {
				public void run() {
					try {
						new Mail(s1, s2, s3).sendHtmlWithFile(addresses,
								title, text, files);
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
		} catch (Exception e) {
			throw new Exception("发送邮件失败:" + e.getMessage(), e);
		}
	}
}

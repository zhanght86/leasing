package com.pqsoft.bank.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.bank.service.BankService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.AnnoAnalysis;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * @author 齐姜龙
 */
public class BankAction extends Action {

	@Override
	public Reply execute() {
		return null;
	}

	Map<String, Object> m = null;
	private static Logger logger = Logger.getLogger(BankAction.class);
	private static com.pqsoft.bank.service.BankService bankService = new BankService();
	private static DataDictionaryService dataDictionaryService = new DataDictionaryService();
	public VelocityContext context = new VelocityContext();

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	private void getPageParameter() {
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
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getBankInforList() {
		this.getPageParameter();
		context.put("PContext", m);
		return new ReplyHtml(VM.html("bank/bankInforManage.vm", context));
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	public Reply pageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = bankService.queryPage(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getCreateBank() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("parentBank", bankService.getParentBank(m));
		return new ReplyHtml(VM.html("bank/bankInforCreate.vm", context));
	}

	@aPermission(name = { "参数配置", "账号维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getCreateAccount() {
		this.getPageParameter();
		List parentAccount = null;
		VelocityContext context = new VelocityContext();
		// 银行列表
		context.put("bankList", bankService.queryAllBank(m));
		// 主账号
		parentAccount = bankService.getParentAccount(m);
		context.put("parentAccount", parentAccount);

		JSONArray json = JSONArray.fromObject(parentAccount);
		context.put("parentAccountJson", json);

		m.put("TYPE", "银行账号类型");
		context.put("accountType", dataDictionaryService.getDataTypeInfo(m));
		return new ReplyHtml(VM.html("bank/bankAccountCreate.vm", context));
	}

	@aPermission(name = { "参数配置", "账号维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply createBankAccount() {
		this.getPageParameter();
		bankService.createBankAccount(m);
		return queryBankAccountInforList();
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getBankByIdForShow() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("bank", bankService.getBankInforById(m));
		return new ReplyHtml(VM.html("bank/bankInforShow.vm", context));
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getBankByIdForUpdate() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("bank", bankService.getBankInforById(m));
		// 查找所有的总银行 用于修改 不包括本身
		context.put("parentBank", bankService.parentBank(m));
		context.put("BABI_ID_UPADATE", m.get("BABI_ID"));
		return new ReplyHtml(VM.html("bank/bankInforUpdate.vm", context));
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply updateBank() {
		this.getPageParameter();
		bankService.updateBank(m);
		return getBankInforList();
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getBankAndAccountCountByParentId() {
		this.getPageParameter();
		int count = (Integer) bankService.getChildCountByParentId(m);
		count += (Integer) bankService.getAccountCountUserBank(m);
		boolean flag = false;
		if (count > 0) {
			flag = true;
		}
		return new ReplyAjax(flag, null);
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply invalidBank() {
		this.getPageParameter();
		bankService.invalidBank(m);
		return getBankInforList();
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getChildCountByParentId() {
		this.getPageParameter();
		int count = (Integer) bankService.getChildCountByParentId(m);
		boolean flag = false;
		if (count > 0) {
			flag = true;
		}
		return new ReplyAjax(flag, null);
	}

	@aPermission(name = { "参数配置", "账号维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	public Reply queryBankAccountInforList() {
		this.getPageParameter();
		context.put("PContext", m);
		return new ReplyHtml(VM.html("bank/bankAccountManager.vm", context));
	}

	@aPermission(name = { "参数配置", "账号维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	public Reply pageAccountAjax() {
		Map<String, Object> param = _getParameters();
		Page page = bankService.queryAccountPage(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "参数配置", "账号维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getBankAccountByIdForShow() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		logger.debug(m.get("BABA_ID") + "BABA_ID");
		// 帐号信息
		context.put("account", bankService.getBankAccountById(m));
		m.put("TYPE", "银行账号类型");
		context.put("accountType", dataDictionaryService.getDataTypeInfo(m));
		return new ReplyHtml(VM.html("bank/bankAccountShow.vm", context));
	}

	@aPermission(name = { "参数配置", "账号维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getBankAccountByIdForUpdate() {
		List parentAccount = null;
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		// 帐号信息
		context.put("account", bankService.getBankAccountById(m));

		// 银行列表
		context.put("bank", bankService.queryAllBank(m));

		// 主账号 用于修改 不包括本身
		parentAccount = bankService.getParentAccountForUpdate(m);
		context.put("parentAccount", parentAccount);

		JSONArray json = JSONArray.fromObject(parentAccount);
		context.put("parentAccountJson", json);

		m.put("TYPE", "银行账号类型");
		context.put("accountType", dataDictionaryService.getDataTypeInfo(m));

		return new ReplyHtml(VM.html("bank/bankAccountUpdate.vm", context));
	}

	@aPermission(name = { "参数配置", "账号维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply updateBankAccount() {
		this.getPageParameter();
		bankService.updateBankAccount(m);
		return queryBankAccountInforList();
	}

	@aPermission(name = { "参数配置", "账号维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	public Reply getAccountCountByParentId() {
		this.getPageParameter();
		int count = (Integer) bankService.getAccountCountByParentId(m);
		boolean flag = false;
		if (count > 0) {
			flag = true;
		}
		return new ReplyAjax(flag, null);
	}

	@aPermission(name = { "参数配置", "账号维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply invalidBankAccount() {
		this.getPageParameter();
		bankService.invalidBankAccount(m);
		return queryBankAccountInforList();
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply createBank() {
		this.getPageParameter();
		bankService.insertBank(m);
		return getBankInforList();
	}

	@aPermission(name = { "参数配置", "银行维护" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public void test() {
		HttpServletRequest request = SkyEye.getRequest();
		HttpServletResponse response = SkyEye.getResponse();
		String className = (String) request.getAttribute("_SKYEYE_FILTER_CLASS_");
		String methodName = (String) request.getAttribute("_SKYEYE_FILTER_METHOD_");

		// StringBuffer sb = new StringBuffer();
		// sb.append("[功能异常]:");
		// sb.append("\n\ttarget:" + className + "->" + methodName);
		String sb = "[功能异常]:";
		sb += "\n\ttarget:" + className + "->" + methodName;
		try {
			System.out.println(AnnoAnalysis.getPer());
		} catch (Exception e) {
			throw new ActionException("error test !");
		}

	}
}

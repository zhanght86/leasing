package com.pqsoft.base.suppliersInfo.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.suppliersInfo.service.CreditDossierService;
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
import com.pqsoft.skyeye.exception.Action404Exception;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * <p>
 * Title: 融资租赁信息系统平台 合作机构 资产档案
 * </p>
 * <p>
 * Description: 信用档案
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * 
 * @author 吴剑东 wujd@pqsoft.cn
 * @version 1.0
 */
public class CreditDossierAction extends Action {

	private static CreditDossierService service = new CreditDossierService();
	String path = "base/suppMainRelation/creditDossier/";

	@aPermission(name = { "合作机构", "渠道管理", "查看", "信用档案" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply execute() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "creditDossierMg.vm", context));
	}

	@aPermission(name = { "合作机构", "渠道管理", "修改", "信用档案" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply executeUpdate() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "creditDossierMg.vm", context));
	}
	
	@aPermission(name = { "合作机构", "渠道管理", "修改", "信用档案" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "180008", email = "tao_yan_min@163.com", name = "陶言民")
	public Reply executeShow() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "creditDossierMgDetail.vm", context));
	}

	/** ---------------------------------------------债务档案--------------------- **/
	/**
	 * 债务档案页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgDebt
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-8-29 下午12:10:38
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toMgDebt() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();

		List<Object> debtTypeList = (List) new DataDictionaryMemcached().get("债务类型");
		context.put("debtTypeList", debtTypeList);
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "debtMg.vm", context));
	}
	
	/** ---------------------------------------------债务档案--------------------- **/
	/**
	 * 债务档案页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgDebt
	 * @return
	 * @author: 陶言民
	 * @date: 2015-9-1 下午12:10:38
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	public Reply toMgDebtDetail() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();

		List<Object> debtTypeList = (List) new DataDictionaryMemcached().get("债务类型");
		context.put("debtTypeList", debtTypeList);
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "debtMgDetail.vm", context));
	}

	/**
	 * 债务档案列表
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.doSelectDebt
	 * @return 债务档案Page
	 * @author: 吴剑东
	 * @date: 2013-8-28 下午05:58:35
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSelectDebt() {
		Map<String, Object> param = _getParameters();
		Page page = service.getDebtListPage(param);
		return new ReplyAjaxPage(page);
	}

	/**
	 * 债务档案保存
	 * 
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doSaveDebt
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-8-30 下午04:03:56
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSaveDebt() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		System.out.println(param + "=============");
		int result = 0;
		try {
			result = service.saveDebt(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "保存成功";
		} else {
			flag = false;
			msg = "保存失败";
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 债务档案修改
	 * 
	 * @author: 吴剑东
	 * @date: 2013-8-30 下午04:03:56
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doUpdateDebt() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.updateDebt(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			flag = false;
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 债务档案删除
	 * 
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doDeleteDebt
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-8-30 下午04:04:19
	 */
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeleteDebt() {
		try {

			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteDebt(param);
			boolean flag = false;
			if (count > 0) {
				flag = true;
			}
			return new ReplyAjax(flag, null);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** ---------------------------------------------银行征信--------------------- **/

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "wanghailong", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toMgPayment() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println(param);
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "paymentMg.vm", context));
	}
	
	/** ---------------------------------------------银行征信--------------------- **/

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "180006", email = "tao_yan_min@163.com", name = "陶言民")
	public Reply toMgPaymentDetail() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println(param);
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "paymentMgDetail.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "银行征信" })
	@aDev(code = "wanghailong", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSelectPayment() {
		Map<String, Object> param = _getParameters();
		System.out.println(param);
		Page page = service.getPaymentListPage(param);
		return new ReplyAjaxPage(page);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "银行征信" })
	@aDev(code = "wanghailong", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSavePayment() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		System.out.println(param.toString());
		int result = 0;
		try {
			result = service.savePayment(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, msg);
	}

	@aPermission(name = { "合作机构", "渠道管理", "修改", "银行征信" })
	@aDev(code = "wanghailong", email = "whlpqsoft@163.com", name = "王海龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeletePayment() {
		try {
			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeletePayment(param);
			boolean flag = false;
			if (count > 0) {
				flag = true;
			}
			return new ReplyAjax(flag, null);

		} catch (Exception e) {
			throw new Action404Exception(e);
		}
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "银行征信" })
	@aDev(code = "wanghailong", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doUpdatePayment() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.updatPayment(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			flag = false;
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply uploadRecord() {
		Map<String, Object> m = _getParameters();
		Map<String, Object> obj = (Map<String, Object>) service.uploadFileForOne(SkyEye.getRequest());
		JSONArray array = JSONArray.fromObject(obj);
		return new ReplyJson(array);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply downLoadRecordFile() {
		Map<String, Object> map = _getParameters();
		return new ReplyFile(new File(map.get("FILE_URL") + ""), map.get("FILE_NAME") + "");
	}

	/** ---------------------------------------------信誉档案--------------------- **/

	/**
	 * 信誉档案页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgReputation
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-8-29 下午12:10:38
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	public Reply toMgReputation() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> loanList = (List) new DataDictionaryMemcached().get("贷款记录");
		List<Object> arrearageList = (List) new DataDictionaryMemcached().get("欠款记录");
		List<Object> oldUserList = (List) new DataDictionaryMemcached().get("老用户");//
		List<Object> bankList = (List) new DataDictionaryMemcached().get("银行资信调查");
		List<Object> legalList = (List) new DataDictionaryMemcached().get("法律诉讼案件");
		List<Object> payList = (List) new DataDictionaryMemcached().get("付款情况");
		List<Object> LeverList = (List) new DataDictionaryMemcached().get("信用等级");
		context.put("loanList", loanList);
		context.put("arrearageList", arrearageList);
		context.put("oldUserList", oldUserList);
		context.put("bankList", bankList);
		context.put("legalList", legalList);
		context.put("payList", payList);
		context.put("LeverList", LeverList);
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "reputationMg.vm", context));
	}

	/**
	 * 信誉档案列表
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.doSelectReputation
	 * @return 债务档案Page
	 * @author: 吴剑东
	 * @date: 2013-8-28 下午05:58:35
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSelectReputation() {
		Map<String, Object> param = _getParameters();
		Page page = service.getReputationListPage(param);
		return new ReplyAjaxPage(page);
	}

	/**
	 * 信誉档案保存
	 * 
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doSaveReputation
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-8-30 下午04:04:19
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSaveReputation() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.saveReputation(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 信誉档案修改
	 * 
	 * @author: 吴剑东
	 * @date: 2013-8-30 下午04:03:56
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doUpdateReputation() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		System.out.println("wjd--信誉档案--" + param);
		try {
			result = service.updateReputation(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			flag = false;
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 信誉档案删除
	 * 
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doDeleteReputation
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-8-30 下午04:04:19
	 */
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeleteReputation() {
		try {

			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteReputation(param);
			boolean flag = false;
			if (count > 0) {
				flag = true;
			}
			return new ReplyAjax(flag, null);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** ---------------------------------------------法院信息--------------------- **/

	/**
	 * 法院信息页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgCourt
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午12:10:38
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toMgCourt() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "courtMg.vm", context));
	}
	
	/**
	 * 法院信息页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgCourt
	 * @return
	 * @author: 陶言民
	 * @date: 2015-9-01下午12:10:38
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	public Reply toMgCourtDetail() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "courtMgDetail.vm", context));
	}

	/**
	 * 法院信息列表
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.doSelectCourt
	 * @return 法院信息Page
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午05:58:35
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSelectCourt() {
		Map<String, Object> param = _getParameters();
		Page page = service.getCourtListPage(param);
		return new ReplyAjaxPage(page);
	}

	/**
	 * 法院信息保存
	 * 
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doSaveCourt
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午04:04:19
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSaveCourt() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.saveCourt(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doUpdateCourt() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		System.out.println("wjd----" + param);
		try {
			result = service.updateCourt(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			flag = false;
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 法院信息删除
	 * 
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doDeleteCourt
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午04:04:19
	 */
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeleteCourt() {
		try {

			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteCourt(param);
			boolean flag = false;
			if (count > 0) {
				flag = true;
			}
			return new ReplyAjax(flag, null);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** ---------------------------------------------其他资产档案--------------------- **/

	/**
	 * 其他资产档案页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgOther
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午12:10:38
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toMgOther() {

		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "otherMg.vm", context));
	}

	/**
	 * 其他资产档案列表
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.doSelectOther
	 * @return 债务档案Page
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午05:58:35
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSelectOther() {
		Map<String, Object> param = _getParameters();
		Page page = service.getOtherListPage(param);
		return new ReplyAjaxPage(page);
	}

	/**
	 * 其他资产档案保存
	 * 
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doSaveOther
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午04:04:19
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doSaveOther() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.saveOther(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doUpdateOther() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		System.out.println("wjd----" + param);
		try {
			result = service.updateOther(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			flag = false;
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 其他资产档案删除
	 * 
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doDeleteOther
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午04:04:19
	 */
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeleteOther() {
		try {
			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteOther(param);
			boolean flag = false;
			if (count > 0) {
				flag = true;
			}
			return new ReplyAjax(flag, null);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

package com.pqsoft.crm.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.crm.service.CreditDossierService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

/**
 * <p>
 * Title: 融资租赁信息系统平台 客户管理 资产档案
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
	String path = "crm/creditDossier/";

	@aPermission(name = { "业务管理", "客户管理","查看","信用档案"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply execute() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"creditDossierMg.vm", context));
	}
	
	@aPermission(name = { "业务管理", "客户管理","修改","信用档案"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply executeUpdate() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"creditDossierMg.vm", context));
	}

    /**        ---------------------------------------------债务档案---------------------          **/
	/**
	 * 债务档案页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgDebt
	 * @return
	 * 
	 * @author: 吴剑东
	 * @date: 2013-8-29 下午12:10:38
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-债务档案", "列表" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgDebt() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		Map<String, Object> map=Dao.selectOne("customers.findCustDetail",param);
		param.put("NAME", map.get("NAME"));
		List<Object> debtTypeList = (List)new DataDictionaryMemcached().get("债务类型");
		context.put("debtTypeList",debtTypeList);
		context.put("param",param);
		return new ReplyHtml(VM.html(path+"debtMg.vm", context));
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
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-债务档案", "数据" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSelectDebt() {
		Map<String, Object> param = _getParameters();
		Page page = service.getDebtListPage(param);
		return new ReplyAjaxPage(page);
	}


	/**
	 *债务档案保存
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doSaveDebt
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:03:56
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-债务档案", "保存" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSaveDebt() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		System.out.println(param+"=============");
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
	 *债务档案修改
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:03:56
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-债务档案", "修改" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
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
	 *债务档案删除
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doDeleteDebt
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:04:19
	 */
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-债务档案", "删除" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
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
	
	

    /**        ---------------------------------------------信誉档案---------------------          **/
	
	
	/**
	 * 信誉档案页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgReputation
	 * @return
	 * 
	 * @author: 吴剑东
	 * @date: 2013-8-29 下午12:10:38
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-信誉档案", "列表" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgReputation() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> loanList = (List)new DataDictionaryMemcached().get("贷款记录");
		List<Object> arrearageList = (List)new DataDictionaryMemcached().get("欠款记录");
		List<Object> oldUserList = (List)new DataDictionaryMemcached().get("老用户");//      
		List<Object> bankList = (List)new DataDictionaryMemcached().get("银行资信调查");
		List<Object> legalList = (List)new DataDictionaryMemcached().get("法律诉讼案件");
		List<Object> payList = (List)new DataDictionaryMemcached().get("付款情况");
		List<Object> LeverList = (List)new DataDictionaryMemcached().get("信用等级");
		context.put("loanList",loanList);
		context.put("arrearageList",arrearageList);
		context.put("oldUserList",oldUserList);
		context.put("bankList",bankList);
		context.put("legalList",legalList);
		context.put("payList",payList);
		context.put("LeverList",LeverList);
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"reputationMg.vm", context));
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
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-信誉档案", "数据" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSelectReputation() {
		Map<String, Object> param = _getParameters();
		Page page = service.getReputationListPage(param);
		return new ReplyAjaxPage(page);
	}

	/**
	 *信誉档案保存
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doSaveReputation
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:04:19
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-信誉档案", "保存" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
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
	 *信誉档案修改
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:03:56
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-信誉档案", "修改" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doUpdateReputation() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		System.out.println("wjd--信誉档案--"+param);
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
	 *信誉档案删除
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doDeleteReputation
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:04:19
	 */
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-信誉档案",  "删除" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
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
	
	
	
/**        ---------------------------------------------法院信息---------------------          **/
	
	
	/**
	 * 法院信息页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgCourt
	 * @return
	 * 
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午12:10:38
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "列表" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgCourt() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		return new ReplyHtml(VM.html(path+"courtMg.vm", context));
	}
	//工商
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply toMgcircles() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		System.out.println(param+"===================");
		return new ReplyHtml(VM.html(path+"circlesMg.vm", context));
	}
	//税务
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply toMgtax() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		return new ReplyHtml(VM.html(path+"taxMg.vm", context));
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
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "数据" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSelectCourt() {
		Map<String, Object> param = _getParameters();
		Page page = service.getCourtListPage(param);
		return new ReplyAjaxPage(page);
	}
	

	/**
	 *法院信息保存
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doSaveCourt
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-9-02 下午04:04:19
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "保存" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
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
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "保存" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply doSaveCircles() {
		Map<String, Object> param = _getParameters();
		System.out.println(param+"================");
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.saveCircles(param);
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
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "保存" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply doSaveTax() {
		Map<String, Object> param = _getParameters();
		System.out.println(param+"================");
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.saveTax(param);
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
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "修改" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doUpdateCourt() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		System.out.println("wjd----"+param);
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

	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "修改" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply doUpdateCircles() {
		Map<String, Object> param = _getParameters();
		System.out.println(param+"=============");
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.updateCircles(param);
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
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "修改" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply doUpdateTax() {
		Map<String, Object> param = _getParameters();
		System.out.println(param+"=============");
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.updateTax(param);
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
	 *法院信息删除
	 * @Function: com.pqsoft.crm.action.CreditDossierAction.doDeleteCourt
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-9-02 下午04:04:19
	 */
//	@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "删除" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
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
	
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeleteCircles() {
		try {

			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteCircles(param);
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
	
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeleteTax() {
		try {

			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteTax(param);
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
	

	/**        ---------------------------------------------其他资产档案---------------------          **/
		
		
		/**
		 * 其他资产档案页面
		 * 
		 * @Function: com.pqsoft.crm.action.CustomerAction.toMgOther
		 * @return
		 * 
		 * @author: 吴剑东
		 * @date: 2013-9-02 下午12:10:38
		 */
		@aAuth(type = aAuth.aAuthType.LOGIN)
//		@aPermission(name = { "客户管理", "客户资料管理","信用档案-其他资产档案","列表" })
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
		public Reply toMgOther() {

			Map<String, Object> param = _getParameters();
			VelocityContext context = new VelocityContext();
			context.put("param", param);
			return new ReplyHtml(VM.html(path+"otherMg.vm", context));
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
//		@aPermission(name = { "客户管理", "客户资料管理","信用档案-其他资产档案", "数据" })
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
		public Reply doSelectOther() {
			Map<String, Object> param = _getParameters();
			Page page = service.getOtherListPage(param);
			return new ReplyAjaxPage(page);
		}

		/**
		 *其他资产档案保存
		 * @Function: com.pqsoft.crm.action.CreditDossierAction.doSaveOther
		 * @return
		 *
		 * @author:  吴剑东
		 * @date:    2013-9-02 下午04:04:19
		 */
		@aAuth(type = aAuth.aAuthType.LOGIN)
//		@aPermission(name = { "客户管理", "客户资料管理","信用档案-其他资产档案", "保存" })
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
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
//		@aPermission(name = { "客户管理", "客户资料管理","信用档案-其他资产档案", "修改" })
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
		public Reply doUpdateOther() {
			Map<String, Object> param = _getParameters();
			Boolean flag = true;
			String msg = "";
			int result = 0;
			System.out.println("wjd----"+param);
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
		 *其他资产档案删除
		 * @Function: com.pqsoft.crm.action.CreditDossierAction.doDeleteOther
		 * @return
		 *
		 * @author:  吴剑东
		 * @date:    2013-9-02 下午04:04:19
		 */
//		@aPermission(name = { "客户管理", "客户资料管理","信用档案-其他资产档案", "删除" })
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
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

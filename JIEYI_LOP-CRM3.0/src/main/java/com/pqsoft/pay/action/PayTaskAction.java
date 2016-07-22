package com.pqsoft.pay.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.buyBack.service.BuyBackService;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.overdue.service.OverdueService;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.math.Finance;
import com.pqsoft.skyeye.math.FinanceBean;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.target.service.TargetService;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ExcelFinancialUtil;
import com.pqsoft.util.RentBeforeInterest;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class PayTaskAction extends Action {

	private PayTaskService service = new PayTaskService();
	private VelocityContext context = new VelocityContext();

	Map<String, Object> m = null;

	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	private void getPageParameter(){
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME",Security.getUser().getName());
		m.put("USERCODE",Security.getUser().getCode());
	}
	/**
	 * 支付表管理首页
	 */
	@aPermission(name = { "合同管理", "还款表管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply execute() {
		List<Object> queryDataDictionary = DataDictionaryService.queryDataDictionary("还款计划状态");
		List<Object> queryDataDictionary_ywlx = new SysDictionaryMemcached().get("业务类型");
		String ORD_ID = BaseUtil.getSupOrgChildren();
		// 当前操作员是否为供应商
		boolean flag = false;
		if (ORD_ID != null && !"".equals(ORD_ID)) {
			flag = true;
		}
		// //更新丢失放款数据起租确认日期 预付日期
		// service.updateConfirmDateForLosePayData();

		context.put("flag", flag);
		context.put("dcflag", Security.hasPermission(new String[]{"业务管理", "业务管理", "导出还款计划表"}));
		context.put("queryDataDictionary", queryDataDictionary);
		context.put("ywlx", queryDataDictionary_ywlx);
		return new ReplyHtml(VM.html("pay/payList.vm", context));
	}

//	@aPermission(name = { "合同管理", "IRR","列表" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply irrShow() {
//		return new ReplyHtml(VM.html("pay/irrData.vm", context));
//	}

	/**
	 * irr查询数据
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply irrData() {
		Map<String, Object> param = _getParameters();
		Page page = service.irrData(param);
		return new ReplyAjaxPage(page);
	}

	/**
	 * irr查询数据导出excel
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply exportExcel() {
		Map<String, Object> param = _getParameters();
		return new ReplyExcel(service.exportData(param), "IRR" + DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "") + ".xls");
	}

	/**
	 * 支付表管理首页
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply payListing() {
		Map<String, Object> param = _getParameters();
		Page page = service.queryPage(param);
		return new ReplyAjaxPage(page);
	}

	//判断是否在有数据在核销流程中或者在虚拟核销中，不能变更
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply doPayListCodeIng(){
		Map<String, Object> param = _getParameters();
		int num=service.doPayListCodeIng(param);
		if(num>0){
			return new ReplyAjax(false, null);
		}
		else{
			return new ReplyAjax(true, null);
		}
	}

	/**
	 * 支付表管理首页
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply payListing_stuts() {
		Map<String, Object> param = _getParameters();
		Page page = service.queryPage_stuts(param);
		return new ReplyAjaxPage(page);
	}

	/**
	 * 支付表展现页
	 */
	@aPermission(name = { "合同管理", "还款表管理", "列表显示" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply showPay() {
		// TODO
		Map<String, Object> param = _getParameters();
		context.put("queryDataDictionary", "");
		return new ReplyHtml(VM.html("pay/payList.vm", context));
	}

	/**
	 * 缩展期显示页面
	 */
	@aPermission(name = { "风险管理","还款计划变更","变更" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply shortenExtendShow() {
		Map<String, Object> param = _getParameters();
		// 检查此项目是否有租金或者违约金被锁定，如有锁定则不让继续走流程
		if (!BaseUtil.getVinualByPayListCode(param.get("PAYLIST_CODE") + "")) { throw new ActionException("该还款计划在申请中或者被虚拟核销占用中"); }

		// 查询支付表及明细
		context.put("pay", service.queryPayByPayCodeOld(param));
		//能操作的期次
		context.put("PEROID_LIST", Dao.selectList("PayTask.queryPeroidList", param));

		return new ReplyHtml(VM.html("pay/shortenExtendShow.vm", context));
	}

	//	@aPermission(name = { "合同管理", "结清管理", "提前结清" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply settleShow() {
		Map<String, Object> param = _getParameters();
		// 查询支付表及明细
		context.put("pay", service.queryPayByPayCode(param));
		// 结清查询表单信息
		Map<String,Object> settleInfo = Dao.selectOne("PayTask.settleInfo", param);
		context.put("settleInfo", settleInfo);

		//结清期次
		context.put("JQ_PEROID_LIST", Dao.selectList("PayTask.queryPeroidList", param));
		return new ReplyHtml(VM.html("pay/settleShow.vm", context));
	}

	//	@aPermission(name = { "合同管理", "结清管理", "正常结清" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply settleNormalShow() {
		Map<String, Object> param = _getParameters();
		// 查询支付表及明细
		context.put("pay", service.queryPayByPayCode(param));
		// 结清查询表单信息
		Map<String,Object> settleInfo = Dao.selectOne("PayTask.settleInfo", param);
		context.put("settleInfo", settleInfo);

		Map peroidMap=Dao.selectOne("PayTask.queryPeroidListEnd", param);
		peroidMap.putAll((Map)Dao.selectOne("PayTask.queryInfoNormalByPeriod", peroidMap));

		//结清期次
		context.put("peroidMap", peroidMap);

		//获取结清期次的罚息MAX_DATA
		OverdueService overdueS=new OverdueService();
		double dunMoney=overdueS.dunPayList(peroidMap.get("PAYLIST_CODE")+"",peroidMap.get("MAX_DATA")+"");
		context.put("PENALTY_RECE", dunMoney);
		return new ReplyHtml(VM.html("pay/settleNormalShow.vm", context));
	}

	/**
	 * 查询支付表明细
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply payDetailShow() {
		Map<String, Object> param = _getParameters();
		// 查询支付表及明细
		JSONObject page = service.payDetailShow(param);
		return new ReplyAjax(page);
	}

	/**
	 * 查询支付表明细
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply payDetailShowNew() {
		Map<String, Object> param = _getParameters();
		// 查询支付表及明细
		JSONObject page = service.payDetailShowNew(param);
		return new ReplyAjax(page);
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply payDetailShow1() {
		Map<String, Object> param = _getParameters();
		// 查询支付表及明细
		return new ReplyAjaxPage(service.payDetailShow1(param));
	}

	/**
	 * 缩展期测算
	 */
	@aPermission(name = { "风险管理","还款计划变更", "变更" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply calculateTest() {
		Map<String, Object> param = _getParameters();
		try{
			JSONObject page = service.calculateTest(param);
			return new ReplyAjax(page);
		}catch(Exception e){
			throw new ActionException(e.getMessage());
		}
	}

	/**
	 * 缩展期保存
	 */
	@aPermission(name = { "风险管理","还款计划变更", "变更" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply calculateSave() {
		Map<String, Object> param = _getParameters();
		// 通过还款计划编号查询该还款计划是否在申请中或者被虚拟核销占用中（未退款未转应收）
		if (!BaseUtil.getVinualByPayListCode(param.get("PAYLIST_CODE") + "")) { throw new ActionException("该还款计划在申请中或者被虚拟核销占用中"); }
		//Dao.selectOne("PRC_BE_QJL_PAY_CODE", param.get("PAYLIST_CODE") + "");//5-12暂时注视掉

		String nextCode = service.calculateSave(param);

		//刷中间表数据
		Dao.update("PayTask.upDateToJoin",param);
		return new ReplyAjax(true,nextCode,"下一步操作人为: ");
	}

	/**
	 * 缩展期失败
	 */
	@aPermission(name = { "合同管理", "还款表管理", "变更" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply changeDefeated() {
		Map<String, Object> param = _getParameters();
		service.changeDefeated(param);
		return new ReplyAjax("操作成功");
	}
	/**
	 * 流程缩展期-未通过
	 */
	@aPermission(name = { "合同管理", "还款表管理", "变更" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public Reply changeFailure(String PAYLIST_CODE) {
		Map<String, Object> param =new HashMap<String, Object>();
		param.put("PAYLIST_CODE", PAYLIST_CODE);
		service.changeDefeated(param);
		return new ReplyAjax("操作成功");
	}

	/**
	 * 缩展期成功
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply changeSucceed(String PAYLIST_CODE) {
		Map<String, Object> param = _getParameters();
//		Map<String, Object> veriable = JBPM.getVeriable(jbpm_id);
//		param.put("PAYLIST_CODE", veriable.get("PAYLIST_CODE"));
		param.put("PAYLIST_CODE", PAYLIST_CODE);
		// 判断变更参数是否已经核销
		double other = Dao.selectDouble("PayTask.check_other_rent", param);
		if (other > 0.2) { throw new ActionException("租金变更产生的费用还为核销"); }
		service.changeSucceed(param);
		return new ReplyAjax("操作成功");
	}

	/**
	 * 缩展期成功
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply changeSucceed() {
		Map<String, Object> param = _getParameters();
//		 Map<String, Object> veriable = JBPM.getVeriable(jbpm_id);
//		 param.put("PAYLIST_CODE", veriable.get("PAYLIST_CODE"));
		service.changeSucceed(param);
		return new ReplyAjax("操作成功");
	}

	/**
	 * 更新支付表的支付时间 需要参数有：pay_id,start_date 注意只能在为正式生成支付表的时候调用，不维护财务支付表
	 */
	@aPermission(name = { "合同管理", "还款表管理", "还款计划修改起租日" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply updatePayDate() {
		Map<String, Object> param = _getParameters();
		service.updatePayDate(param);
		return new ReplyAjax("操作成功");
	}

	/**
	 * 提前还款管理首页
	 *  "合同管理", "项目提前终止", "提前还租(部分)", "列表" 
	 */
	@aPermission(name = {"项目管理", "提前结清查询", "列表显示2"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply forwardRepaymentManage() {
		List<Object> queryDataDictionary = DataDictionaryService.queryDataDictionary("还款计划状态");
		String ORD_ID = BaseUtil.getSupOrgChildren();
		// 当前操作员是否为供应商
		boolean flag = false;
		if (ORD_ID != null && !"".equals(ORD_ID)) {
			flag = true;
		}
		context.put("flag", flag);
		context.put("queryDataDictionary", queryDataDictionary);
		return new ReplyHtml(VM.html("pay/forwardRepaymentManage.vm", context));
	}

	/**
	 * 提前还款管理首页
	 * "合同管理", "项目提前终止", "提前还租(部分)查询", "列表"
	 */
	@aPermission(name = {"项目管理", "提前结清查询", "列表显示1"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply forwardRepaymentQuery() {
		List<Object> queryDataDictionary = DataDictionaryService.queryDataDictionary("还款计划状态");
		String ORD_ID = BaseUtil.getSupOrgChildren();
		// 当前操作员是否为供应商
		boolean flag = false;
		if (ORD_ID != null && !"".equals(ORD_ID)) {
			flag = true;
		}
		context.put("flag", flag);
		context.put("P_STATUS", "");
		context.put("queryDataDictionary", queryDataDictionary);
		return new ReplyHtml(VM.html("pay/forwardRepaymentQuery.vm", context));
	}

	/**
	 * 提前结清管理首页
	 * "合同管理", "项目提前终止", "提前结清查询", "列表"
	 */
	@aPermission(name = {"项目管理", "提前结清查询", "列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply earlySettlementQuery() {
		List<Object> queryDataDictionary = DataDictionaryService.queryDataDictionary("还款计划状态");
		String ORD_ID = BaseUtil.getSupOrgChildren();
		// 当前操作员是否为供应商
		boolean flag = false;
		if (ORD_ID != null && !"".equals(ORD_ID)) {
			flag = true;
		}
		context.put("flag", flag);
		context.put("queryDataDictionary", queryDataDictionary);
		return new ReplyHtml(VM.html("pay/earlySettlementQuery.vm", context));
	}

	/**
	 * 提前结清管理首页
	 * "合同管理", "项目提前终止", "提前结清", "列表"
	 */
	@aPermission(name = {"项目管理", "提前结清", "列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply earlySettlementManage() {
		List<Object> queryDataDictionary = Dao.selectList("PayTask.queryStatusForName");
//		String ORD_ID = BaseUtil.getSupOrgChildren();
//		// 当前操作员是否为供应商
//		boolean flag = false;
//		if (ORD_ID != null && !"".equals(ORD_ID)) {
//			flag = true;
//		}
//		context.put("flag", flag);
		context.put("queryDataDictionary", queryDataDictionary);
		return new ReplyHtml(VM.html("pay/earlySettlementManage.vm", context));
	}

	/**
	 * 提前还款展示页面准备数据 需要参数有：PAYLIST_CODE
	 */
	@aPermission(name = { "合同管理", "还款表管理", "提前还款展示页" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply forwardRepaymentShow() {
		Map<String, Object> param = _getParameters();
		context.put("data", service.forwardRepaymentShow(param));
		return new ReplyHtml(VM.html("pay/forwardRepayment.vm", context));
	}

	/**
	 * 提前还款展示页面准备数据 需要参数有：PAYLIST_CODE
	 */
	@aPermission(name = { "合同管理", "还款表管理", "变更展示页" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply changeToShow() {
		Map<String, Object> param = _getParameters();
		context.put("data", service.forwardRepaymentShow(param));
		return new ReplyHtml(VM.html("pay/changeToShow.vm", context));
	}

	/**
	 * 提前还款展示页面准备数据 需要参数有：PAYLIST_CODE
	 */
	@aPermission(name = { "合同管理", "还款表管理", "变更展示页" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply changeToShowMan() {
		Map<String, Object> param = _getParameters();
		System.out.println("-----------------param="+param);

		//通过支付表编号查询支付表ID
		Map mapPay=(Map)Dao.selectOne("PayTask.changeToShowManPay", param);
		param.put("PAY_ID", mapPay.get("PAY_ID"));
		// new
		// ProjectContractManagerService().doAddProjectContractList(param.get("JBPM_ID").toString());//保存欲归档文件
		Map map = Dao.selectOne("PayTask.changeToShowMan", param);
		param.put("PROJECT_ID", map.get("ID"));
		String step = param.get("step") + "";
		String url = "";
		if ("2".equals(step) || "1".equals(step)) {
			url = "/project/ProjectContractManager!doShowRetentionData.action?PROJECT_ID=" + map.get("ID") + "&CLIENT_ID=" + map.get("CLIENT_ID")
					+ "&LC_TYPE=ZJBG" + "&PAGE_TYPE=HTLB";
			context.put("chang_name", "租金变更资料");
		} else if ("3".equals(step) || "4".equals(step) || "6".equals(step)) {
			context.put("chang_name", "租金变更资料");
			url = "/project/ProjectContractManager!doShowProjectContractListData.action?PROJECT_ID=" + map.get("ID") + "&CLIENT_ID="
					+ map.get("CLIENT_ID") + "&LC_TYPE=ZJBG" + "&PAGE_TYPE=HTLB";
		} else if ("5".equals(step)) {
			context.put("chang_name", "档案归档");
			int selectInt = Dao.selectInt("PayTask.DOSSIER_APPLY_ID", param);

			url = "/pigeonhole/Pigeonhole!toDossierConfirm.action?DOSSIER_APPLY_ID=" + selectInt + "&TASK_NAME=档案上传资料";
		}
		String url_2 = "/project/ProjectContractManager!toContractFileBG.action?PROJECT_ID=" + map.get("ID") + "&CLIENT_ID=" + map.get("CLIENT_ID")
				+ "&LC_TYPE=ZJBG" + "&PAGE_TYPE=HTLB";
		context.put("url_", url);
		context.put("url_2", url_2);
		context.put("param_", param);
		// ProjectContractManagerService sv = new
		// ProjectContractManagerService();
		// Map<String, Object> typeByProjectId =
		// sv.doShowClientTypeByProjectId(param.get("PROJECT_ID") + "");
		// List<Map<String, Object>> fromDataDictionary =
		// sv.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE")
		// + "", "立项",null);
		// System.out.println("sssssssssss");
		return new ReplyHtml(VM.html("pay/changeToShowMan.vm", context));
	}

	/**
	 * 提前还款发起流程 需要参数有：PAYLIST_CODE (PAYLIST_CODE=SFNXSZ130040-1&code_=99)
	 */
	@aPermission(name = { "合同管理", "还款表管理", "提前还款发起流程" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply forwardRepaymentJBPM() {
		Map<String, Object> param = _getParameters();
		Map<String,Object>  map = service.forwardRepaymentJBPMMessage(param);
		return new ReplyAjax((Boolean)map.get("flag"),map.get("data"),map.get("info").toString());
	}

	/**
	 * 提前还款发起流程 需要参数有：PAYLIST_CODE (PAYLIST_CODE=SFNXSZ130040-1)
	 */
	@aPermission(name = { "合同管理", "还款表管理", "提前还款发起流程" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply startJBPM() {
		Map<String, Object> param = _getParameters();
		Map temp = Dao.selectOne("PayTask.query_maxVersion_stauts", param);
		param.put("code_", temp.get("STATUS") + "" + temp.get("STATUS"));
		String string = service.forwardRepaymentJBPM(param);
		return new ReplyAjax(string);
	}

	// ----------------------------------------计算报价的支付表开始
	/**
	 * 2016年4月13日 15:41:40 吴国伟新加算法p2p金融
	 * @return
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	public Reply quoteP2PCalculate(){
		Map<String,Object> m = _getParameters();
		JSONObject page=null;
		page = service.algorithmP2PCalculate(m);
		return new ReplyAjax(page);

	}
	/**
	 * 报价测算
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply quoteCalculateTest() {
		Map<String, Object> param = _getParameters();
		System.out.println("----------------param="+param);
		if(param.containsKey("QUOTA_ID")&&StringUtils.isNotBlank(param.get("QUOTA_ID"))){
			TargetService tserv=new TargetService();
			Map<String,Object> mm=new HashMap<String, Object>();
			mm.put("SUPPLIERS_ID", param.get("QUOTA_ID"));
			tserv.toViewTargetList(mm);
		}
		// service.updateProjectInfo(param);
		JSONObject page=null;
		try {
			page = service.quoteCalculateTest(param);
		} catch (Exception e) {
			throw new ActionException(e.getMessage(),e);
		}


		/******************* 收益率 START *************************************/
		//保证金
		List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");
		param.put("LEASE_TERM", param.get("_leaseTerm"));
		page.put("GDLX_PRICE", param.get("GDLX_PRICE"));

		Vector<Double> irrData=new Vector<Double>();
		Calendar nowday=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(param.get("YGQZR_DATE"))){
			String YGQZR_DATE=param.get("YGQZR_DATE")+"";
			try {
				nowday.setTime(sdf.parse(YGQZR_DATE));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else
			nowday.setTime(new Date());
		irrData.add(-Double.parseDouble(param.get("LEASE_TOPRIC") + "")+Double.parseDouble(param.get("FIRSTPAYALL")+""));
		int term=12/Integer.parseInt(param.get("_payCountOfYear") + "");
		FinanceBean bean =new FinanceBean();
		if(StringUtils.isNotBlank(param.get("DEPOSIT_MONEY")))
			bean.setDEPOSIT_MONEY(Double.parseDouble(param.get("DEPOSIT_MONEY")+""));
		if(StringUtils.isNotBlank(param.get("POUNDAGE_WAY")))
			bean.setDEPOSIT_WAY(param.get("POUNDAGE_WAY")+"");
		//续保保证金
		double RenewalMargin_MONEY=0;
		if(StringUtils.isNotBlank(param.get("RenewalMargin_MONEY")))
			RenewalMargin_MONEY=Double.parseDouble(param.get("RenewalMargin_MONEY")+"");

		for (int i = 0; i < irrList.size(); i++) {
			double zj=Double.parseDouble(irrList.get(i).get("zj") + "");
			double lxzzs=Double.parseDouble(irrList.get(i).get("lxzzs")+"");
			if(irrList.size()>=11&&i==11 && "12".equals(bean.getRenewalMargin_WAY())){
				zj=zj-RenewalMargin_MONEY;
			}else if(irrList.size()<11&&i==irrList.size()-1&& "12".equals(bean.getRenewalMargin_WAY())){
				zj=zj-RenewalMargin_MONEY;
			}
			zj=UtilFinance.sub(zj, lxzzs);
			nowday.add(Calendar.MONTH, term);
			page.put("MYZJ", zj);//获取每月租金
			if(nowday.get(Calendar.MONTH)==1&&StringUtils.isNotBlank(param.get("ISNOPAYFORNEWYEAR"))&&Boolean.parseBoolean(param.get("ISNOPAYFORNEWYEAR")+"")==true){
				//每年春节(2月)不还款
				irrData.add(0d);
				nowday.add(Calendar.MONTH, term);
				irrData.add(zj);
			}else
				irrData.add(zj);
		}
		double irr = Math.round(Finance.getIRR(irrData,bean) * Integer.parseInt(param.get("_payCountOfYear") + "") * 10000) * 100;
		System.out.println("irr="+UtilFinance.div(irr, 10000, 6));
		page.put("INTERNAL_RATE", irr / 10000);
		/******************* 收益率 END *************************************/
		return new ReplyAjax(page);
	}

	// ----------------------------------------计算报价的支付表结束

	/**
	 * 查看还款计划明细
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "合同管理", "还款表管理", "查看" })
	public Reply toMgshowDetail() {
		context.put("param", _getParameters());
		context.put("Format", UTIL.FORMAT);
		String ID = null == _getParameters().get("ID") ? null : _getParameters().get("ID").toString();
		Map<String, Object> RENTHEAD = service.doShowRentHeadByPayId(ID);
		context.put("RENTHEAD", RENTHEAD);
		context.put("detailList", service.doShowRentPlanDetail(_getParameters()));
		if (RENTHEAD.containsKey("PAYLIST_CODE")) context.put("PROEQULIST", new projectService().queryEqByProjectID(RENTHEAD));
		context.put("OTHERFEElIST", service.doShowRentDetailOtherFeeListByPayId(ID));
		context.put("SCHEMELIST", service.queryPayScheme(RENTHEAD.get("PAYLIST_CODE")+""));
		context.put("dicTag", Util.DICTAG);
		return new ReplyHtml(VM.html("pay/rentPlanDetail.vm", context));
	}


	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "风险管理","还款计划变更","查看" })
	public Reply toMgshowDetailChange() {
		context.put("param", _getParameters());
		context.put("Format", UTIL.FORMAT);
		String ID = null == _getParameters().get("ID") ? null : _getParameters().get("ID").toString();
		Map<String, Object> RENTHEAD = service.doShowRentHeadByPayId(ID);
		context.put("RENTHEAD", RENTHEAD);
		context.put("detailList", service.doShowRentPlanDetail(_getParameters()));
		if (RENTHEAD.containsKey("PAYLIST_CODE")) context.put("PROEQULIST", new projectService().queryEqByProjectID(RENTHEAD));
		context.put("OTHERFEElIST", service.doShowRentDetailOtherFeeListByPayId(ID));
		String PROJECT_ID = null == _getParameters().get("PROJECT_ID") ? null : _getParameters().get("PROJECT_ID").toString();
//		context.put("SCHEMELIST", service.doShowProjSchemeInfoByProjectId(PROJECT_ID));
		return new ReplyHtml(VM.html("pay/rentPlanDetail.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply toMgshowDetailChangeJBPM() {
		context.put("param", _getParameters());
		System.out.println("---------_getParameters()="+_getParameters());
		context.put("Format", UTIL.FORMAT);
		String ID = null == _getParameters().get("ID") ? null : _getParameters().get("ID").toString();
		Map<String, Object> RENTHEAD = service.doShowRentHeadByPayId(ID);
		context.put("RENTHEAD", RENTHEAD);
		context.put("detailList", service.doShowRentPlanDetail(_getParameters()));
		if (RENTHEAD.containsKey("PAYLIST_CODE")) context.put("PROEQULIST", new projectService().queryEqByProjectID(RENTHEAD));
		context.put("OTHERFEElIST", service.doShowRentDetailOtherFeeListByPayId(ID));
		String PROJECT_ID = null == _getParameters().get("PROJECT_ID") ? null : _getParameters().get("PROJECT_ID").toString();
//		context.put("SCHEMELIST", service.doShowProjSchemeInfoByProjectId(PROJECT_ID));
		return new ReplyHtml(VM.html("pay/rentPlanDetail.vm", context));
	}

	/**
	 * 查看租前息还款计划明细
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "合同管理", "租前息计划管理", "查看" })
	public Reply toMgshowZQXDetail() {
		context.put("param", _getParameters());
		context.put("Format", UTIL.FORMAT);
		String ID = null == _getParameters().get("ID") ? null : _getParameters().get("ID").toString();
		Map<String, Object> RENTHEAD = service.doShowRentHeadByPayId(ID);
		context.put("RENTHEAD", RENTHEAD);
		context.put("detailList", service.doShowRentPlanDetail(_getParameters()));
		if (RENTHEAD.containsKey("PAYLIST_CODE")) context.put("PROEQULIST", new projectService().queryZQXEqByProjectID(RENTHEAD));
		context.put("OTHERFEElIST", service.doShowRentDetailOtherFeeListByPayId(ID));
		String PROJECT_ID = null == _getParameters().get("PROJECT_ID") ? null : _getParameters().get("PROJECT_ID").toString();
//		context.put("SCHEMELIST", service.doShowProjSchemeInfoByProjectId(PROJECT_ID));
		return new ReplyHtml(VM.html("pay/rentPlanDetail.vm", context));
	}
	/**
	 * 买卖合同
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "合同管理", "还款表管理", "买卖合同" })
	public Reply showBuyContract() {
		Map<String,Object> param = _getParameters();
		context.put("param", _getParameters());
		context.put("Format", UTIL.FORMAT);
		String ID = null == _getParameters().get("ID") ? null : _getParameters().get("ID").toString();
		Map<String, Object> RENTHEAD = service.doShowRentHeadByPayId(ID);
		context.put("RENTHEAD", RENTHEAD);
		param.putAll(RENTHEAD);
		if (RENTHEAD.containsKey("PAYLIST_CODE")){
			context.put("PROEQULIST", new projectService().queryEqByProjectID(RENTHEAD));
			context.put("SCHEMELIST", service.queryPaymentByPaylistCode(param));
		}
		context.put("normList", service.queryPaymentMouldDetail(param));
		return new ReplyHtml(VM.html("pay/showBuyContract.vm", context));
	}

	@aPermission(name = { "参数配置", "定量打分项", "添加" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply saveBuyContract() {
		this.getPageParameter();
		JSONObject  object = JSONObject.fromObject(m.get("alldata"));
		object.put("USER_CODE", Security.getUser().getCode());
		int a = service.insertPaymentDetail(object,m);
		boolean flag=false;
		if(a>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}


	//----------------------------------------------------变更核销其他费用

	// ----------------------------------------------------变更核销其他费用
	/**
	 * 分期回购(流程表单-财务核销)
	 * toCheckedFormHG1
	 *
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toCheckedFormHG1() {
		Map<String, Object> param = _getParameters();
		BuyBackService backService = new BuyBackService();
		param.put("PROJECT_ID", Dao.selectInt("PayTask.queryPay_PROJECT_ID", param));
		context.put("data", param);
		context.put("proData", backService.getProData(param));// 查看项目数据
		// context.put("buyBack", service.toCheckedFormHG(param));//查看回购数据
		context.put("f_data", Dao.selectList("rentDk.getRentData2", param));// 查看付款申请单
		context.put("firstMoney", backService.getFirstMoney(param));// 查看首期款
		return new ReplyHtml(VM.html("pay/toCheckedFormHG1.vm", context));
	}

	/**
	 * 分期回购(流程表单-财务核销)
	 * toCheckedFormHG1
	 *
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply cancelAfterVerification() {
		Map<String, Object> param = _getParameters();
		service.cancelAfterVerification(param);
		return new ReplyAjax("操作成功");
	}

	/* 提前结清保存 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply settleSave_Old() {
		Map<String, Object> param = _getParameters();
//		// 检查此项目是否有租金或者违约金被锁定，如有锁定则不让继续走流程
//		Map m_flag = Dao.selectOne("BuyBack.m_flag", param);
//		if (m_flag != null && !m_flag.isEmpty()) {
//			throw new ActionException("当前有租金被核销锁定，暂时不能操作");
//		} else {
//			Map m_flag2 = Dao.selectOne("BuyBack.m_flag2", param);
//			if (m_flag2 != null && !m_flag2.isEmpty()) { throw new ActionException("当前有租金被核销锁定，暂时不能操作"); }
//		}
		// 通过还款计划编号查询该还款计划是否在申请中或者被虚拟核销占用中（未退款未转应收）
		if (!BaseUtil.getVinualByPayListCode(param.get("PAYLIST_CODE") + "")) { throw new ActionException("该还款计划在申请中或者被虚拟核销占用中"); }
		//Dao.selectOne("PRC_BE_QJL_PAY_CODE", param.get("PAYLIST_CODE") + "");
		// 插入结算日期的罚息信息
		JSONObject settleInfo = JSONObject.fromObject(param.get("settleInfo"));
		BuyBackService bs = new BuyBackService();
		bs.refreshPenaltyaAccountsDate(param.get("PAYLIST_CODE") + "", settleInfo.get("RENT_DATE") + "", "");
		// 插入变更租金参数表FIL_PAYCHANGE_PARAMETER
		service.calculateParameter(param,"提前结清");
		service.calculateSave2(param);
		// 如果该项目有发起过回购预警则删除
		Dao.delete("BuyBack.del_fil_buy_back_status0", param.get("PAYLIST_CODE"));
		//发起流程
		param.put("code_", "66");
		String nextCode = service.calculateSaveJBPMNextCode(param);
		return new ReplyAjax(true,nextCode,"下一步操作人为: ");
	}

	/* 提前结清保存(方法重写，上有方法settleSave_Old为原始方法) */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply settleSave() {
		Map<String, Object> param = _getParameters();
		// 通过还款计划编号查询该还款计划是否在申请中或者被虚拟核销占用中（未退款未转应收）
		if (!BaseUtil.getVinualByPayListCode(param.get("PAYLIST_CODE") + "")) { throw new ActionException("该还款计划在申请中或者被虚拟核销占用中"); }
		// 插入结算日期的罚息信息
		JSONObject settleInfo = JSONObject.fromObject(param.get("settleInfo"));
		BuyBackService bs = new BuyBackService();
		bs.refreshPenaltyaAccountsDate(param.get("PAYLIST_CODE") + "", settleInfo.get("RENT_DATE") + "", "");
//		//提前结清冻结后的期次不产生罚息
//		double PENALTY_RECEPAGE=Double.parseDouble((settleInfo.get("PENALTY_RECE") == null || "".equals(settleInfo.get("PENALTY_RECE"))) ? "0" : settleInfo.get("PENALTY_RECE").toString());
//		double TOTAL_MONEY=Double.parseDouble((settleInfo.get("TOTAL_MONEY") == null || "".equals(settleInfo.get("TOTAL_MONEY"))) ? "0" : settleInfo.get("TOTAL_MONEY").toString());
//		double PENALTY_RECE=service.chuliOverDue(param.get("PAYLIST_CODE") + "", settleInfo.get("RENT_DATE") + "");
//		settleInfo.put("PENALTY_RECE",PENALTY_RECE);
//		settleInfo.put("TOTAL_MONEY",TOTAL_MONEY-PENALTY_RECEPAGE+PENALTY_RECE);
		param.put("settleInfo", settleInfo);
		// 插入支付表主表
		//* 新增记录
		Dao.insert("PayTask.upgrade_payplan", param);
		// 插入变更租金参数表FIL_PAYCHANGE_PARAMETER
		//* fil_paychange_parameter一条新记录
		service.calculateParameter(param, "提前结清");

		param.put("code_", "66");
		service.calculateSaveJQ(param);
		// 如果该项目有发起过回购预警则删除
		Dao.delete("BuyBack.del_fil_buy_back_status0", param.get("PAYLIST_CODE"));
		//发起流程
		String nextCode = service.calculateSaveJBPMNextCode(param);
		//刷中间表数据
		Dao.update("PayTask.upDateToJoin",settleInfo);
		return new ReplyAjax(true,nextCode,"下一步操作人为: ");
	}

	/* 提前结清发起流程 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply settleJBPM() {
		Map<String, Object> param = _getParameters();
		// 修改成功后发起流程
		Map temp = Dao.selectOne("PayTask.query_maxVersion_stauts", param);
		param.put("code_", temp.get("STATUS") + "" + temp.get("STATUS"));
		String string = service.calculateSaveJBPM(param);
		return new ReplyAjax(string);
	}

	/* 提前结清取消（支付表变更但未发起流程可以取消） */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply settleCancel() {
		Map<String, Object> param = _getParameters();
		// 删除支付表参数表数据
		Dao.delete("PayTask.delete_paychange_parameter", param);
		// 调用变更支付表失败方法
		service.changeDefeated(param);
		return new ReplyAjax("操作成功");
	}

	// ----------------------------------------------------变更核销其他费用
	/**
	 * 分期回购(流程表单-财务核销)
	 * toCheckedFormHG1
	 *
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toCheckedSettle() {
		Map<String, Object> param = _getParameters();

		// 在核销前先同步财务数据
		// 删除财务表数据
		Dao.delete("PayTask.deleteBeginning", param);
		// 同步财务数据
		Map<String, String> temp1 = new HashMap<String, String>();
		// temp1.put("PAY_ID", ID + "");
		temp1.put("PAYLIST_CODE", param.get("PAYLIST_CODE") + "");
		temp1.put("PMT", "PMT租金");
		temp1.put("ZJ", "租金");
		temp1.put("SYBJ", "剩余本金");
		temp1.put("D", "第");
		temp1.put("QI", "期");
		Dao.insert("PayTask.synchronizationBeginning", temp1);

		BuyBackService backService = new BuyBackService();
		param.put("PROJECT_ID", Dao.selectInt("PayTask.queryPay_PROJECT_ID", param));
		context.put("data", param);
		context.put("proData", backService.getProData(param));// 查看项目数据
		// context.put("buyBack", service.toCheckedFormHG(param));//查看回购数据
		// context.put("f_data", Dao.selectList("rentDk.getRentData3",
		// param));//查看付款申请单
		context.put("f_data", Dao.selectList("rentDk.getRentData6", param));// 查看付款申请单
		context.put("firstMoney", Dao.selectOne("rentDk.getFirstMoney2", param));// 查看首期款
		Map tmp = new HashMap();
		tmp.put("PAYLIST_CODE", param.get("PRO_CODE"));
		context.put("JQ_YS_MONEY", Dao.selectDouble("BuyBack.JQ_YS_MONEY", param));
		return new ReplyHtml(VM.html("pay/toCheckedSettle.vm", context));
	}

	/**
	 * 分期回购(流程表单-财务核销)
	 * toCheckedFormHG1
	 *
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply cancelSettle() {
		Map<String, Object> param = _getParameters();
		service.cancelSettle(param);
		return new ReplyAjax("操作成功");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public void doShowRentStartInfo() {
		Map<String, Object> param = _getParameters();
		try {
			service.exportRentIllustrateExcel(param.get("PROJECT_ID") + "", SkyEye.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170017", email = "hanjian@pqsoft.cn", name = "韩建")
	@aPermission(name = { "风险管理","还款计划变更", "拆分" })
	public Reply disconnect() {
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("PROEQULIST", Dao.selectList("PayTask.sel_PROEQULIST", param));// 查询设备信息
		context.put("YH_BEGINNING_NUM", Dao.selectInt("PayTask.sel_YH_BEGINNING_NUM", param));// 已还最大期次
		context.put("MAX_BEGINNING_NUM", Dao.selectOne("PayTask.sel_MAX_BEGINNING_NUM", param));//

		return new ReplyHtml(VM.html("pay/disconnect.vm", context));
	}

	// 拆分测算
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170017", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply disconnectCalculate() {
		Map<String, Object> param = _getParameters();
		JSONObject json=new JSONObject();
		try {
			json = service.disconnectCalculate(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ReplyAjax(json);
	}

	// 拆分测算保存
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170017", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply disconnectCalculateSave() {
		Map<String, Object> param = _getParameters();
		Map<String,Object> refer = service.disconnectCalculateSaveMsg(param);
		return new ReplyAjax((Boolean)refer.get("flag"),refer.get("data"),refer.get("msg").toString());
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170017", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply disconnectJBPMshow() {
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("PROEQULIST", Dao.selectList("PayTask.sel_PROEQULIST2", param));// 查询设备信息
		Map split = Dao.selectOne("PayTask.query_fil_pay_split", param);
		context.put("split", split);// 拆分信息
		//
		int pay_id1 = Dao.selectInt("PayTask.query_split_pay_id", split.get("PAYLIST_CODE"));
		Map temp = new HashMap();
		temp.put("ID", pay_id1);
		JSONObject detailShow1 = service.payDetailShow(temp);
		int pay_id2 = Dao.selectInt("PayTask.query_split_pay_id", split.get("NEW_CODE"));
		temp = new HashMap();
		temp.put("ID", pay_id2);
		JSONObject detailShow2 = service.payDetailShow(temp);
		context.put("detailShow1", detailShow1.get("data"));
		context.put("detailShow2", detailShow2.get("data"));
		return new ReplyHtml(VM.html("pay/disconnectJBPMshow.vm", context));
	}

	/**
	 * 拆分成功
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply disconnectSucceed(String jbpm_id) {
		Map<String, Object> veriable = JBPM.getVeriable(jbpm_id);
		// Map split = Dao.selectOne("PayTask.query_fil_pay_split", veriable);
		// 修改支付表版本为正常版本
		Dao.update("PayTask.disconnectSucceed", veriable);
		return new ReplyAjax("操作成功");
	}

	/**                                    财务支付表                   **/
	/**
	 * 财务租金管理
	 */
	@aPermission(name = { "资金管理", "财务租金计划", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public Reply payFinancial() {
		List<Object> queryDataDictionary = DataDictionaryService.queryDataDictionary("还款计划状态");
		String ORD_ID = BaseUtil.getSupOrgChildren();
		// 当前操作员是否为供应商
		boolean flag = false;
		if (ORD_ID != null && !"".equals(ORD_ID)) {
			flag = true;
		}
		// //更新丢失放款数据起租确认日期 预付日期
		// service.updateConfirmDateForLosePayData();

		context.put("flag", flag);
		context.put("queryDataDictionary", queryDataDictionary);
		return new ReplyHtml(VM.html("pay/payFinancialList.vm", context));
	}
	@aPermission(name = { "资金管理", "财务租金计划", "导出" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public void exportFinancialExcel() {
		Map<String, Object> map = this._getParameters();
		Map<String, Object> payhead = (Map<String, Object>) service.getPayLineBase(map);

		double LEASE_TOPRIC = Double.parseDouble(payhead.get("LEASE_TOPRIC").toString());
		double FIRSTRENT_RATE = Double.parseDouble(payhead.get("FIRSTRENT_RATE").toString())/100;
		double POUNDAGE_RATE = Double.parseDouble(payhead.get("POUNDAGE_RATE").toString())/100;
		payhead.put("sumTopric", LEASE_TOPRIC - LEASE_TOPRIC * FIRSTRENT_RATE - LEASE_TOPRIC * POUNDAGE_RATE );

		FIPPOAction fippo = new FIPPOAction();
		List<Map<String,Object>> payList = fippo.getPayDetail(payhead);
		ToExportExcle(payList,payhead);
	}

	private void ToExportExcle(List<Map<String,Object>> payList , Map<String,Object> payhead) {

		List<Map<String,String>> flaglist=new ArrayList<Map<String,String>>();

		ExcelFinancialUtil.flagListBuilder(flaglist, "支付时间", "PAY_DATE","date");
		ExcelFinancialUtil.flagListBuilder(flaglist, "期次", "PERIOD_NUM","number");
		ExcelFinancialUtil.flagListBuilder(flaglist, "租金", "MONTHPRICE","number");
		ExcelFinancialUtil.flagListBuilder(flaglist, "本金", "OWNPRICE","number");
		ExcelFinancialUtil.flagListBuilder(flaglist, "利息", "RENPRICE","number");
		ExcelFinancialUtil.flagListBuilder(flaglist, "剩余本金", "LASTPRICE","number");
		ExcelFinancialUtil.flagListBuilder(flaglist, "实际利率(%)", "Irrrate","number");
		ExcelFinancialUtil.flagListBuilder(flaglist, "每期摊销的管理费", "renSub","number");
		try{
			ExcelFinancialUtil.excelBuilder(payhead , flaglist , payList , "财务支付表" , "财务支付表" ,SkyEye.getResponse(),SkyEye.getRequest());

		}catch(Exception e){

		}

	}
	/****************付表变更管理*********/
	/**
	 * 支付表变更管理首页
	 */
	@aPermission(name = {"变更管理", "支付表变更", "列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public Reply changePayRent() {
		List<Object> queryDataDictionary = DataDictionaryService.queryDataDictionary("还款计划状态");
		String ORD_ID = BaseUtil.getSupOrgChildren();
		// 当前操作员是否为供应商
		boolean flag = false;
		if (ORD_ID != null && !"".equals(ORD_ID)) {
			flag = true;
		}
		// //更新丢失放款数据起租确认日期 预付日期
		// service.updateConfirmDateForLosePayData();
		System.out.println(".........");
		context.put("flag", flag);
		context.put("queryDataDictionary", queryDataDictionary);
		return new ReplyHtml(VM.html("pay/payChangeList.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public Reply payRentList() {
		getPageParameter();
		Page page = service.getAllPayRent(m);
		return new ReplyAjaxPage(page);
	}

	/*****************/

	/***********************************88租前息**************************/
	/**
	 * 租前息显示页面
	 */
	@aPermission(name = { "合同管理", "还款表管理", "租前息" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public Reply rentBeforeInterest(){
		getPageParameter();
		context.put("pay", service.queryPayCode(m));
		return new ReplyHtml(VM.html("pay/rentBeforeInterest.vm", context));
	}
	@aPermission(name = { "合同管理", "还款表管理", "租前息" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public Reply rentBeforeCalculation(){
		getPageParameter();
		RentBeforeInterest rentBeforeInterest = new RentBeforeInterest();
		return new ReplyAjax(rentBeforeInterest.PayShrinkPMT(m));
	}
	@aPermission(name = { "合同管理", "还款表管理", "租前息" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public Reply rentBeforeSave() {
		getPageParameter();
		// 通过还款计划编号查询该还款计划是否在申请中或者被虚拟核销占用中（未退款未转应收）
		service.rentBeforeSave(m);
		return new ReplyAjax("操作成功");
	}
	/**
	 * 租前息支付表管理
	 */
	@aPermission(name = { "项目管理", "租前息计划管理", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public Reply payRentBeforeManager() {
		List<Object> queryDataDictionary = DataDictionaryService.queryDataDictionary("还款计划状态");
		String ORD_ID = BaseUtil.getSupOrgChildren();
		// 当前操作员是否为供应商
		boolean flag = false;
		if (ORD_ID != null && !"".equals(ORD_ID)) {
			flag = true;
		}
		// //更新丢失放款数据起租确认日期 预付日期
		// service.updateConfirmDateForLosePayData();

		context.put("flag", flag);
		context.put("queryDataDictionary", queryDataDictionary);
		return new ReplyHtml(VM.html("pay/payRentBeforeList.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuguowei", email = "wugw@pqsoft.cn", name = "吴国伟")
	public Reply payRentBefore() {
		getPageParameter();
		Page page = service.getAllPayRentBefore(m);
		return new ReplyAjaxPage(page);
	}

	public static void main(String[] args) {
		int x = 0 ;
		if(x>0){
			System.out.println("x>0");
		}else{
			throw new ActionException("x<=0") ;
		}
		System.out.println(1);
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryInfoByPeriod() {
		this.getPageParameter();
		//* rent_date 期次对应的支付日期，
		//* no_rent_money 小于等于当前期次的应收减实收
		//* not_interest 大于当前期次的利息，手续费，管理费，利息增值税的和的应收减实收
		//* benjin_after 大于当前期次的本金的应收减实收
		Map map = Dao.selectOne("PayTask.queryInfoByPeriod", m);

		double dunMoney = 0;
		//未来罚息
		if(map != null && map.get("RENT_DATE") != null && !map.get("RENT_DATE").equals("")){
			OverdueService overdueS = new OverdueService();
			//* 找结清期次前的逾期记录，计算逾期金额和罚息
			dunMoney = overdueS.dunPayList(m.get("PAYLIST_CODE") + "", map.get("RENT_DATE") + "");
		}
		map.put("DUNMONEY", dunMoney);
//		System.out.println("-----------------dunMoney="+dunMoney);
		return new ReplyJson(JSONArray.fromObject(map));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryDunInfoByDate() {
		this.getPageParameter();

		double dunMoney=0;
		//未来罚息
		if(m !=null && m.get("RENT_DATE")!=null && !m.get("RENT_DATE").equals("")){
			OverdueService overdueS=new OverdueService();
			dunMoney=overdueS.dunPayList(m.get("PAYLIST_CODE")+"",m.get("RENT_DATE")+"");
		}
		m.put("DUNMONEY", dunMoney);
		return new ReplyJson(JSONArray.fromObject(m));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryInfoNormalByPeriod() {
		this.getPageParameter();
		Map map=Dao.selectOne("PayTask.queryInfoNormalByPeriod", m);
		return new ReplyJson(JSONArray.fromObject(map));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "合同管理", "结清管理", "查看" })
	public Reply toMgshowDetailTQ() {
		Map<String, Object> map = _getParameters();
		context.put("Format", UTIL.FORMAT);
		map.put("ID", map.get("PAY_ID"));
		Map<String, Object> settleInfo = service.doShowJQByPayId(map.get("PAY_ID") + "");
		context.put("settleInfo", settleInfo);
		context.put("detailList", service.doShowRentPlanDetail(map));
		return new ReplyHtml(VM.html("pay/settleViewShow.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "合同管理", "结清管理", "查看" })
	public Reply toMgshowDetailZC() {
		Map<String, Object> map=_getParameters();
		context.put("Format", UTIL.FORMAT);
		map.put("ID", map.get("PAY_ID"));
		Map<String, Object> settleInfo = service.doShowNormalByPayId(map.get("PAY_ID")+"");
		context.put("settleInfo", settleInfo);
		context.put("detailList", service.doShowRentPlanDetail(map));
		return new ReplyHtml(VM.html("pay/settleViewNormalShow.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply toMgshowDetailJBPMUpLoad() {
		Map map=_getParameters();
		context.put("Format", UTIL.FORMAT);
		map.put("ID", map.get("PAY_ID"));
		//提前结清保存项
		Map<String, Object> settleInfo = service.doShowJQByPayId(map.get("PAY_ID")+"");
		context.put("settleInfo", settleInfo);
		map.put("RENT_DATE", settleInfo.get("RENT_DATE"));
		//未核销明细项
		context.put("detailList", service.queryJQDate(map));
		if(settleInfo!=null && settleInfo.get("DEPOSIT")!=null && !settleInfo.get("DEPOSIT").equals("")){
			double DEPOSIT=Double.parseDouble(settleInfo.get("DEPOSIT")+"");
			if(DEPOSIT>0){//表明有抵扣款
				List DIKOUFUND=service.DIKOUFUND(map);
				context.put("DIKOUFUND", DIKOUFUND);
			}
		}

		//已经认款的来款
		List listFund=service.listFundByCustID(settleInfo);
		context.put("listFund", listFund);
		return new ReplyHtml(VM.html("pay/settleViewShowJBPM.vm", context));
	}


	/* 正常结清保存(方法重写，上有方法settleSave_Old为原始方法) */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply settleNormalSave() {
		Map<String, Object> param = _getParameters();
		// 通过还款计划编号查询该还款计划是否在申请中或者被虚拟核销占用中（未退款未转应收）
		if (!BaseUtil.getVinualByPayListCode(param.get("PAYLIST_CODE") + "")) { throw new ActionException("该还款计划在申请中或者被虚拟核销占用中"); }
		// 插入结算日期的罚息信息
		JSONObject settleInfo = JSONObject.fromObject(param.get("settleInfo"));
		BuyBackService bs = new BuyBackService();
		bs.refreshPenaltyaAccountsDate(param.get("PAYLIST_CODE") + "", settleInfo.get("RENT_DATE") + "", "");
		//正常结清冻结后的期次不产生罚息
//		double PENALTY_RECEPAGE=Double.parseDouble((settleInfo.get("PENALTY_RECE") == null || "".equals(settleInfo.get("PENALTY_RECE"))) ? "0" : settleInfo.get("PENALTY_RECE").toString());
//		double TOTAL_MONEY=Double.parseDouble((settleInfo.get("TOTAL_MONEY") == null || "".equals(settleInfo.get("TOTAL_MONEY"))) ? "0" : settleInfo.get("TOTAL_MONEY").toString());
//		double PENALTY_RECE=service.chuliOverDue(param.get("PAYLIST_CODE") + "", settleInfo.get("RENT_DATE") + "");
//		settleInfo.put("PENALTY_RECE",PENALTY_RECE);
//		settleInfo.put("TOTAL_MONEY",TOTAL_MONEY-PENALTY_RECEPAGE+PENALTY_RECE);
		param.put("settleInfo", settleInfo);
		// 插入变更租金参数表FIL_PAYCHANGE_PARAMETER
		// 插入支付表主表
		Dao.insert("PayTask.upgrade_payplan", param);
		service.calculateParameter(param,"正常结清");
		param.put("code_", "33");
		service.calculateSaveNormalJQ(param);

		//发起流程
		String nextCode = service.calculateSaveNormalJBPMNextCode(param);
		//刷中间表数据
		Dao.update("PayTask.upDateToJoin",settleInfo);
		return new ReplyAjax(true,nextCode,"下一步操作人为: ");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply toMgshowNormalJBPMUpLoad() {
		Map map=_getParameters();
		context.put("Format", UTIL.FORMAT);
		map.put("ID", map.get("PAY_ID"));
		//提前结清保存项
		Map<String, Object> settleInfo = service.doShowNormalByPayId(map.get("PAY_ID")+"");
		context.put("settleInfo", settleInfo);
		map.put("RENT_DATE", settleInfo.get("RENT_DATE"));
		//未核销明细项
		context.put("detailList", service.queryJQDate(map));
		if(settleInfo!=null && settleInfo.get("DEPOSIT")!=null && !settleInfo.get("DEPOSIT").equals("")){
			double DEPOSIT=Double.parseDouble(settleInfo.get("DEPOSIT")+"");
			if(DEPOSIT>0){//表明有抵扣款
				List DIKOUFUND=service.DIKOUFUND(map);
				context.put("DIKOUFUND", DIKOUFUND);
			}
		}

		//已经认款的来款
		List listFund=service.listFundByCustID(settleInfo);
		context.put("listFund", listFund);
		return new ReplyHtml(VM.html("pay/settleViewNormalShowJBPM.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "业务管理", "新建申请","查看收益率"})
	public Reply showIrr(){
		return new ReplyAjax();
	}
}

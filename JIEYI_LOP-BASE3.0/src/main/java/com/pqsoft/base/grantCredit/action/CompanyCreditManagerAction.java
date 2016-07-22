package com.pqsoft.base.grantCredit.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.base.grantCredit.service.CompanyCreditService;
import com.pqsoft.base.grantCredit.service.CreditLogService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class CompanyCreditManagerAction extends Action {
	private String path = "base/grantCredit/";
	private CompanyCreditService creditService = new CompanyCreditService();
	private CreditLogService logService = new CreditLogService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "授信管理", "厂商授信管理", "列表" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute() {
		return new ReplyHtml(VM.html(path + "companyCreditManager.vm", null));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "授信管理", "厂商授信管理", "列表" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply pageNew() {
		Map<String,Object> map_param = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(map_param.get("params"));
		Map<String,Object> map = (Map<String,Object>)(jsonArray.get(0));
		if("".equals(map.get("ID"))){
			map.put("COMPANY_MONEY_BZJ", 0);
			map.put("COMPANY_MONEY_BZJ_YE", 0);
			map.put("COMPANY_MONEY_RATIO", 0);
			map.put("ID", 0);
		}
		System.out.println(map+"------33-------");
		VelocityContext context = new VelocityContext();
		String COMPANY_NAME = ((String)map.get("COMPANY_NAME")).replace("_"," ");
		map.put("COMPANY_NAME", COMPANY_NAME);//厂商名
		map.put("COMPANY",Security.getUser().getOrg().getPlatform());//收款户名
		map.put("MANAGER_ID",Security.getUser().getOrg().getPlatformId());//收款户名ID
		Map<String,Object> map_tem = Dao.selectOne("fi.fund.getBinkInfo", map);
		System.out.println(map_tem+"---map-------");
		map.put("FA_BINK",map_tem.get("FA_BINK"));//默认融资租赁公司开户行
		map.put("FA_ACCOUNT",map_tem.get("FA_ACCOUNT"));//默认融资租赁公司银行账号
		Map<String,Object> map_tem2 = Dao.selectOne("fi.fund.get_CS_BinkInfo", map);//获取厂商默认开户行、银行账号
		System.out.println(map_tem2+"----tem2------");
		if(map_tem2!=null){
			map.put("OPEN_BANK_ACCOUNT",map_tem2.get("OPEN_BANK_ACCOUNT"));
			map.put("OPEN_BANK",map_tem2.get("OPEN_BANK"));
		}
		context.put("map", map);
		//跳转到保证金页面
		return new ReplyHtml(VM.html(path + "companyCreditManagerNewbzj.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "授信管理", "厂商授信管理", "列表" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply pageDataCS() {
		//跳转到厂商列表
		return new ReplyHtml(VM.html(path + "companyCreditManagerNewcs.vm", null));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name ={"授信管理","经销商授信管理","列表"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = creditService.getCreditPage(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "授信管理", "厂商授信管理", "添加授信" })
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toAddCompanyCredit() {
		VelocityContext context = new VelocityContext();
		context.put("param",
				creditService.queryCompanyIfno(_getParameters().get("COMPANY_ID")+""));
		context.put("CUGP_CODE", CodeService.getCode("经销商授信协议编号", null, null));
		context.put("MSG", "-1");
		return new ReplyHtml(VM.html(path + "addCompanyCredit.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理理","添加授信"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Object doAddCompanyCredit() {
		Map<String, Object> param = _getParametersIO(null, null, null);
		param.put("CREATE_ID", Security.getUser().getId());
		param.put("TYPE", "4");
		param.put("STATUS", "2");
		int i = creditService.doAddCompanyCredit(param);
		VelocityContext context = new VelocityContext();
		if (i >= 1) {
			List<String> list = JBPM.getDeploymentListByModelName("厂商授信审批",
					"", "");
			String pid = null;
			if (list.size() > 0) {
				pid = list.get(0);
			} else
				throw new ActionException("没有找到流程图");

			if (pid == null) {
				throw new ActionException("没有匹配到审批流程");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUGP_ID", param.get("CUGP_ID") + "");
			map.put("COMPANY_ID", param.get("CUST_ID") + "");
			context.put("CUGP_CODE", param.get("CUGP_CODE") + "");
			String jbpmid = JBPM.startProcessInstanceById(pid, "", "", "", map)
					.getId();
			context.put("RST", jbpmid);
			context.put("param", creditService.queryCompanyIfno(param.get("CUST_ID") + ""));
		}
		param.put("MEMO", "厂商授信添加");
		CreditLogService logService = new CreditLogService();
		logService.doInsertCreditLog(param);
		context.put("map", param);
		context.put("MSG", "1");
		return new ReplyHtml(VM.html(path + "addCompanyCredit.vm", context))
				.addOp(new OpLog("授信管理", "添加授信", param + ""));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toShowCompanyCreditForJbpm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");
		context.put("param", param);
		param.put("COMPANY_ID", param.get("CUST_ID"));
		context.put("sup", creditService.queryCompanyIfno(param.get("CUST_ID") + ""));
		return new ReplyHtml(VM.html(path + "showCompanyCreditForJbpm.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理","添加授信"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toShowCompanyCredit() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");
		context.put("param", param);
		param.put("SUP_ID", param.get("CUST_ID"));
		context.put("sup", creditService.queryCompanyIfno(param.get("CUST_ID") + ""));
		context.put("log", logService.toFindCreditLog(param));
		return new ReplyHtml(VM.html(path + "showCompanyCredit.vm",
				context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toUpCompanyCreditForJbpm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");
		context.put("param", param);
		param.put("COMPANY_ID", param.get("CUST_ID"));
		context.put("sup", creditService.queryCompanyIfno(param.get("CUST_ID") + ""));
		context.put("log", logService.toFindCreditLog(param));
		return new ReplyHtml(VM.html(path + "updateCompanyCreditForJbpm.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
    @aPermission(name={"授信管理","厂商授信管理","客户信息"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply showClientInfo() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "showClientInfo.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name={"授信管理","厂商授信管理","客户信息"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//查询同一厂商相关的承租人信息
	public Reply searchClientInfo() {
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(creditService.searchClient(param));
	}
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//查询保证金列表
	public Reply getBZJ_Info() {
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(creditService.getBZJ_Info(map));
	}
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//新增保证金
	public Reply insertBZJ_Info() {
		Map<String,Object> map_tem = this._getParametersIO(null, null, null);
		if("".equals( map_tem.get("T_HEAD_ID"))){
			map_tem.put("T_HEAD_ID", Dao.selectInt("companyCredit.searchHeadId", null));
			Dao.insert("companyCredit.insertHeadRecord", map_tem);
		}
		System.out.println(map_tem+"------dd------"+map_tem.get("T_HEAD_ID"));
		map_tem.put("ID", Dao.selectInt("companyCredit.searchDetailId", null));
		map_tem.put("TYPE", 3);
		map_tem.put("STATUS", "2");
		if(!map_tem.containsKey("FILE_PATH")){
			map_tem.put("FILE_PATH", null);
			map_tem.put("_FILE_NAME", null);
		}
		//插入新增保证金
		int flag = creditService.insertBZJ_Info(map_tem);
		//更新t_sys_company_bzj_head表 保证金总计、保证金余额、使用率
		Dao.update("companyCredit.update_t_sys_bzj_Head", map_tem);
		//获得新的保证金总计、保证金余额、使用率
		Map<String,Object> map2 = Dao.selectOne("companyCredit.searchMoneyInfo", map_tem);
		if(flag == 1){
			return new ReplyAjax(true, map2, "添加成功");
		}else{
			return new ReplyAjax(false, "添加失败!!");
		}
	}
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//退还保证金
	public Reply refund_BZJ_Info() {
		Map<String,Object> map = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(map.get("param"));
		Map<String,Object> map_tem = (Map<String,Object>)(jsonArray.get(0));
		map_tem.put("ID", Dao.selectInt("companyCredit.searchDetailId", null));
		map_tem.put("TYPE", 1);
		//插入退还保证金记录 
		int flag = creditService.insertBZJ_Info(map_tem);
		//发起流程
		Map JBPMMAP=new HashMap();
		JBPMMAP.put("ID", map_tem.get("ID").toString());
		List<String> list = JBPM.getDeploymentListByModelName("厂商保证金退还审批","", Security.getUser().getOrg().getPlatformId());
		String jbpm_id =JBPM.startProcessInstanceById(list.get(0), "", "", "", JBPMMAP).getId();
		String nextCode = new TaskService().getAssignee(jbpm_id);
		return new ReplyAjax(true, nextCode, "下一步操作人为: ");
	}
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170067", name = "肖云飞", email = "xiaoyf@pqsoft.cn")
	//保证金转来款
	public Reply convert_BZJ_Info() {
		Map<String,Object> map = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(map.get("param"));
		Map<String,Object> map_tem = (Map<String,Object>)(jsonArray.get(0));
		map_tem.put("ID", Dao.selectInt("companyCredit.searchDetailId", null));
		map_tem.put("TYPE", 2);
		map_tem.put("STATUS", "2");
		//插入转来款保证金记录 
		int flag = creditService.insertBZJ_Info(map_tem);
		//插入一条新的数据进入fi_fund资金表
		map_tem.put("FUND_ID", Dao.selectOne("fi.fund.getFundId"));
		map_tem.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
		map_tem.put("FUND_DOCKET", "厂商保证金转来款");
		map_tem.put("FUND_NOTDECO_STATE", "4");
		map_tem.put("FUND_STATUS", "0");
		map_tem.put("FUND_ISCONGEAL", "0");
		map_tem.put("FUND_RED_STATUS", "0");
		map_tem.put("FUND_PRANT_ID", "0");
		map_tem.put("FUND_IMPORT_PERSON", Security.getUser().getName());
		map_tem.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
		map_tem.put("FUND_PIDENTIFY_PERSON", Security.getUser().getName());
		map_tem.put("FUND_PIDENTIFY_PERSON_ID", Security.getUser().getId());
		map_tem.put("FUND_ACCEPT_DATE", map_tem.get("COMEMONEY_TIME"));
		map_tem.put("FUND_RECEIVE_MONEY", map_tem.get("COMEMONEY"));
		map_tem.put("FUND_CLIENT_NAME", map_tem.get("CLIENT_NAME"));
		map_tem.put("FUND_CLIENT_ID", map_tem.get("CLIENT_ID"));
		map_tem.put("FUND_ACCEPT_CODE", map_tem.get("ACCEPTMONEY_ZH"));
		map_tem.put("FUND_ACCEPT_NAME", map_tem.get("ACCEPTMONEY_NAME"));
		map_tem.put("FUND_COMENAME", map_tem.get("COMEMONEY_NAME"));
		map_tem.put("FUND_COMECODE", map_tem.get("COMEMONEY_ZH"));
		map_tem.put("FUND_SY_MONEY", map_tem.get("COMEMONEY"));
		Dao.insert("fi.fund.add", map_tem);
		//更新t_sys_company_bzj_head表 保证金总计、保证金余额、使用率
		Dao.update("companyCredit.update_t_bzj_Head_Refund", map_tem);
		//获得新的保证金总计、保证金余额、使用率
		Map<String,Object> map2 = Dao.selectOne("companyCredit.searchMoneyInfo", map_tem);
		if(flag == 1){
			return new ReplyAjax(true, map2, "添加成功");
		}else{
			return new ReplyAjax(false, "添加失败!!");
		}
	}
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170067", name = "肖云飞", email = "xiaoyf@pqsoft.cn")
	//跳转厂家保证金明细页面
	public Reply showDetail() {
		Map<String,Object> map = _getParameters();
		map.put("sel", 1);
		VelocityContext context = new VelocityContext();
		context.put("map", map);
		return new ReplyHtml(VM.html(path+"showDetail.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","厂商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170067", name = "肖云飞", email = "xiaoyf@pqsoft.cn")
	//跳转厂家保证金明细页面
	public Reply getShowDetailData() {
		Map<String,Object> map = _getParameters();
		Map<String,Object> map_tem = creditService.selectDetailData(map);
		return new ReplyJson(JSONArray.fromObject(map_tem));
	}
	
}

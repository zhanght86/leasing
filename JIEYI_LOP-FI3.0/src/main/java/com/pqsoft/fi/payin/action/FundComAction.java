package com.pqsoft.fi.payin.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.fi.payin.service.FundComService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;

public class FundComAction extends Action {
	
	FundComService service = new FundComService();

	@Override
	@aPermission(name = { "资金管理", "还款录入","列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		List<Map<String,Object>> clientMap=null;
		if(param.get("PROJECT_ID") !=null && !param.get("PROJECT_ID").equals("")){
			context.put("PROJECT_ID", param.get("PROJECT_ID"));
		}
		//1 来自业务管理下的首付款登记 其他来自 资金管理 租金管理下的还款录入
		if(param.get("fromType") !=null && !param.get("fromType").equals("")){
			context.put("fromType", param.get("fromType"));
		}
		return new ReplyHtml(VM.html("fi/payin/fund_com.vm", context));
	}

	@aPermission(name = { "资金管理", "还款录入","列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		param.put("ID", Security.getUser().getId());
		return new ReplyAjaxPage(new FundComService().getPage(param));
	}

	@aPermission(name = { "资金管理", "还款录入","添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply add() {
		VelocityContext context = new VelocityContext();
		/******由原来的sql查询平台变更为直接取值   King 2014-8-29***/
//		context.put("COMPANY",service.getCompany(Security.getUser().getId()));//收款户名
		context.put("COMPANY",Security.getUser().getOrg().getPlatform());//收款户名
		/*************************************/
//		context.put("bankList",service.getBank();//收款帐号
		/******来款页添加默认值         add by xingsumin begine***/
		Map<String, Object> param = _getParameters();
		Map<String,Object> clientMap=null;
		if(param.get("PROJECT_ID") !=null && !param.get("PROJECT_ID").equals("")){
			String PROJECT_ID=param.get("PROJECT_ID").toString();
			clientMap=service.queryUserInfoByProjectId(PROJECT_ID);
		}
		if(null!=clientMap&&clientMap.size()>0){
			//来款户名
			context.put("CUST_NAME", clientMap.get("NAME"));
			//来款账号
			context.put("BANK_ACCOUNT", clientMap.get("BANK_ACCOUNT"));
			//来款金额
			context.put("FIRSTPAYALL", clientMap.get("FIRSTPAYALL"));
		}
		//来款时间
		context.put("FUND_TIME", DateUtil.format(new Date(), "yyyy-MM-dd"));
		//所属分公司信息
		Map<String,Object> map=service.getBranchCompanyInfo(Security.getUser().getOrg().getId());
		if(null!=map){
			context.put("COMPANY",map.get("NAME"));//收款户名
			Map<String,Object> accMap=service.getBranchCompanyBankAcc(map.get("ID").toString());
			if(null!=accMap){
				//收款账号
				context.put("RECEIV_BANK_ACCOUNT", accMap.get("FA_ACCOUNT").toString());
			}
		}
		//1 来自业务管理下的首付款登记 其他来自 资金管理 租金管理下的还款录入
		if(param.get("fromType") !=null && !param.get("fromType").equals("")){
			context.put("fromType", param.get("fromType"));
		}
		/******来款页添加默认值         add by xingsumin end***/
		return new ReplyHtml(VM.html("fi/payin/fund_com_in.vm", context));
	}

	@aPermission(name = { "资金管理", "还款录入","添加" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doAdd() {
		Map<String, Object> param = _getParameters();
		int flag = service.add(param);
		if(flag == 1){
			return new ReplyAjax(true, "自动认款已成功!");
		}else{
			return new ReplyAjax(false, "自动认款失败!");
		}
	}

	@aPermission(name = { "资金管理", "还款录入","查看" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply get() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("data", service.getData(param));
		context.put("dfj", service.getDfj(param));
		context.put("details", service.getFundDetail(param));
		return new ReplyHtml(VM.html("fi/payin/fund_com_get.vm", context));
	}

	@aPermission(name = { "资金管理", "还款录入","删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply delFund() {
		Map<String, Object> param = _getParameters();
		service.del(param);
		return new ReplyAjax();
	}
	
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply fundBank() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/payin/fundBank.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply fundBankPage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new FundComService().fundBankPage(param));
	}
}

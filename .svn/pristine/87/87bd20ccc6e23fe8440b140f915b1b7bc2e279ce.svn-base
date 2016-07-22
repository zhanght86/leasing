package com.pqsoft.fundNotEBank.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.fundNotEBank.service.FundNotEBankService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class FundNotEBankAction extends Action {

	private final String path = "fundNotEBank/";
	
	@Override
	public Reply execute() {
		return null;
	}

	/********************************************资金扣划-非网银-申请**********Auth=杨雪***********************************************************/
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-申请","列表"})
	public Reply toMgDeduct() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));
		return new ReplyHtml(VM.html(path+"fundNotEBankMg.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-申请","列表"})
	public Reply toMgDeductData(){
		Map<String, Object> param = _getParameters();
		FundNotEBankService service = new FundNotEBankService();
		return new ReplyAjaxPage(service.toMgDeductData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-申请","新增列表"})
	public Reply toAppPaymentMg(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		FundNotEBankService service = new FundNotEBankService();
		map.put("USERCODE", Security.getUser().getCode());//获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());//获取登陆人
		context.put("param", map);		
		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));//获取付款方式		
		context.put("toGetCompany", service.toGetCompany(map));//获取厂商		
		context.put("toGetProduct", service.toGetProduct(map));//获取租赁物类型
		return new ReplyHtml(VM.html(path+"fundNotEBankApp.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-申请","新增列表"})
	public Reply toAppPaymentData(){
		Map<String, Object> param = _getParameters();
		FundNotEBankService service = new FundNotEBankService();
		return new ReplyAjaxPage(service.toGetAppData(param));
	}
	

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-申请","新增列表"})
	public Reply doInsertAppData(){
		Map<String,Object> map = _getParameters();//获取页面参数
	
		map.put("USERCODE",Security.getUser().getCode());//用户编号
		map.put("USERNAME",Security.getUser().getName());//用户名
		FundNotEBankService service = new FundNotEBankService();
		int i = 0;
		i = service.doInsertAppData(map);
		boolean flag = false;
		if(i>0){
			flag = true;
		}else {
			flag = false;
		}		
		return new ReplyAjax(flag, null).addOp(new OpLog("资金扣划-非网银","申请-保存", map.get("USERCODE").toString()));
		
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-申请","下拉选"})
	public Reply getFundDetail() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(path+"fundNEbankAppDe.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-申请","下拉选（查看）"})
	public Reply getFundDetailData(){
		Map<String, Object> param = _getParameters();
		FundNotEBankService service = new FundNotEBankService();
		return new ReplyAjax(service.getFundDetail(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-申请","提交"})
	public Reply doSubmitApp() {
		Map<String,Object> map = _getParameters();//获取页面参数
		
		map.put("USERCODE",Security.getUser().getCode());//用户编号
		map.put("USERNAME",Security.getUser().getName());//用户名
		
		FundNotEBankService service = new FundNotEBankService();
		int i = 0;
		i = service.dosubmitApp(map);
		
		boolean flag = false;
		if(i>0){
			flag = true;
		}else {
			flag = false;
		}		
		return new ReplyAjax(flag, null).addOp(new OpLog("资金扣划-非网银","申请-提交", map.get("USERCODE").toString()));
	}
	
	/********************************************资金扣划-非网银-审核**********Auth=杨雪***********************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-核销","列表"})
	public Reply toMgAppCheckMg() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		//context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));
		return new ReplyHtml(VM.html(path+"toAppCheckMg.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-核销","列表"})
	public Reply toMgAppCheckMgData(){
		Map<String, Object> param = _getParameters();//页面参数
		FundNotEBankService service = new FundNotEBankService();
		return new ReplyAjaxPage(service.toMgAppAlready(param));//查看核销列表
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-核销","下拉选"})
	public Reply getCheckedDetail() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		FundNotEBankService service = new FundNotEBankService();
		Map<String,Object> f_data = (Map<String, Object>) service.toGetFundData(map);//查看申请表数据
	
		List<Map<String,Object>> listTage = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> poolList=new ArrayList<Map<String,Object>>();
		
		String OWNER_ID=null;
		
		//当为承租人非网银， 只需要保证金池，承租人资金池，保险理赔资金池，待处理资金池
		if(f_data != null && f_data.get("FI_FLAG")!=null && !f_data.get("FI_FLAG").equals("") && f_data.get("FI_FLAG").toString().equals("1")){
			
			listTage = service.queryCustByFundID(map);
			if(listTage.size()>0)
			{
				Map MapTage=(Map)listTage.get(0);
				OWNER_ID=(MapTage.get("FI_TAGE_ID")!=null && !MapTage.get("FI_TAGE_ID").equals(""))?MapTage.get("FI_TAGE_ID").toString():"";
			}
			
			
			//虚拟数据
			Map map1=new HashMap();
			map1.put("OWNER_ID", OWNER_ID);
			map1.put("FUND_ID", map.get("FUND_ID"));
			map1.put("POOLNAME", "保证金池");
			map1.put("POOLID", "4");
			double userMoney1=service.queryPoolMoneyDe(map1);
			map1.put("POOLUSERMONEY", service.queryPoolMoney(map1)+userMoney1);
			map1.put("dichong_money", userMoney1);
			poolList.add(map1);
			
			Map map2=new HashMap();
			map2.put("OWNER_ID", OWNER_ID);
			map2.put("FUND_ID", map.get("FUND_ID"));
			map2.put("POOLNAME", "承租人资金池");
			map2.put("POOLID", "5");
			double userMoney2=service.queryPoolMoneyDe(map2);
			map2.put("POOLUSERMONEY", service.queryPoolMoney(map2)+userMoney2);
			map2.put("dichong_money", userMoney2);
			poolList.add(map2);
			
			Map map3=new HashMap();
			map3.put("OWNER_ID", OWNER_ID);
			map3.put("FUND_ID", map.get("FUND_ID"));
			map3.put("POOLNAME", "保险理赔资金池");
			map3.put("POOLID", "6");
			double userMoney3=service.queryPoolMoneyDe(map3);
			map3.put("POOLUSERMONEY", service.queryPoolMoney(map3)+userMoney3);
			map3.put("dichong_money", userMoney3);
			poolList.add(map3);
			
			Map map4=new HashMap();
			map4.put("OWNER_ID", OWNER_ID);
			map4.put("FUND_ID", map.get("FUND_ID"));
			map4.put("POOLNAME", "待处理资金池");
			map4.put("POOLID", "7");
			double userMoney4=service.queryPoolMoneyDe(map4);
			map4.put("POOLUSERMONEY", service.queryPoolMoney(map4)+userMoney4);
			map4.put("dichong_money", userMoney4);
			poolList.add(map4);
		}
		else if(f_data!=null && f_data.get("FI_FLAG")!=null && !f_data.get("FI_FLAG").equals("") && f_data.get("FI_FLAG").toString().equals("6"))
		{
			listTage=service.querySuppByFundID(map);
			if(listTage.size()>0)
			{
				Map MapTage=(Map)listTage.get(0);
				OWNER_ID=(MapTage.get("FI_TAGE_ID")!=null && !MapTage.get("FI_TAGE_ID").equals(""))?MapTage.get("FI_TAGE_ID").toString():"";
			}
			
			//只需要DB保证金池，供应商电汇资金池，设备付款资金池，待处理资金池
			//虚拟数据
			Map map1=new HashMap();
			map1.put("OWNER_ID", OWNER_ID);
			map1.put("FUND_ID", map.get("FUND_ID"));
			map1.put("POOLNAME", "DB保证金池");
			map1.put("POOLID", "1");
			double userMoney1=service.queryPoolMoneyDe(map1);
			map1.put("POOLUSERMONEY", service.queryPoolMoney(map1)+userMoney1);
			map1.put("dichong_money", userMoney1);
			poolList.add(map1);
			
			Map map2=new HashMap();
			map2.put("OWNER_ID", OWNER_ID);
			map2.put("FUND_ID", map.get("FUND_ID"));
			map2.put("POOLNAME", "代理垫汇资金池");
			map2.put("POOLID", "2");
			double userMoney2=service.queryPoolMoneyDe(map2);
			map2.put("POOLUSERMONEY", service.queryPoolMoney(map2)+userMoney2);
			map2.put("dichong_money", userMoney2);
			poolList.add(map2);
			
			Map map3=new HashMap();
			map3.put("OWNER_ID", OWNER_ID);
			map3.put("FUND_ID", map.get("FUND_ID"));
			map3.put("POOLNAME", "设备付款资金池");
			map3.put("POOLID", "3");
			double userMoney3=service.queryPoolMoneyDe(map3);
			map3.put("POOLUSERMONEY", service.queryPoolMoney(map3)+userMoney3);
			map3.put("dichong_money", userMoney3);
			poolList.add(map3);
			
			Map map4=new HashMap();
			map4.put("OWNER_ID", OWNER_ID);
			map4.put("FUND_ID", map.get("FUND_ID"));
			map4.put("POOLNAME", "待处理资金池");
			map4.put("POOLID", "7");
			double userMoney4=service.queryPoolMoneyDe(map4);
			map4.put("POOLUSERMONEY", service.queryPoolMoney(map4)+userMoney4);
			map4.put("dichong_money", userMoney4);
			poolList.add(map4);
		}
		
		context.put("f_data", f_data);
		
		context.put("listTage", listTage);
		
		context.put("poolList", poolList);
		
		return new ReplyHtml(VM.html(path+"fundNEbankAppDe.vm", context));//申请单明细信息
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-核销","列表"})
	public Reply getCheckedDetailData(){
		Map<String, Object> param = _getParameters();
		FundNotEBankService service = new FundNotEBankService();
		return new ReplyAjaxPage(service.getChedkedDetail(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-核销","下拉选(保存)"})
	public Reply doCommitHXSheet(){
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		FundNotEBankService service = new FundNotEBankService();
		int i =0;
		boolean flag = false;
		i = service.doUpdateFHead(paramMap);//更新申请单数据
		if(i>0){
			service.doInsertAccount(paramMap);//添加实收明细
			if(i>0){
				flag = true;
			}else{
				flag = false;
			}
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("资金扣划-非网银","核销-提交", paramMap.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-核销","下拉选(核销)"})
	public Reply doCommitHXSheetPool(){
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		
		FundNotEBankService service = new FundNotEBankService();
		int i =0;
		boolean flag = false;
		String msg = "";
		
		i = service.doUpdateFHead(paramMap);//更新申请单数据
		
		if(i>0){
			service.doInsertAccount_1(paramMap);//添加实收明细
			if(i>0){
				flag = true;
				msg = "提交成功";
			}else{
				flag = false;
				msg = "提交失败";
			}
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("资金扣划-非网银","核销-提交", msg));
	}
	

	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-查询","列表"})
	public Reply checkFirstInfoByHeadIds(){
		Map<String,Object> paramMap = _getParameters();
		FundNotEBankService service = new FundNotEBankService();
		int i = 0;
		boolean flag = false;
		String msg = "";
		i = service.checkFirstInfoByHeadId(paramMap);//校验资金明细表首付款应收明细是否在应收期初表中存在
		if(i > 0){
			flag = true;
			msg = "首付款信息错误,请驳回!";
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-核销","核销"})
	public Reply doCheckedFund() {
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		FundNotEBankService service = new FundNotEBankService();
		int i = 0;
		boolean flag = false;
		String msg = "";
		i=service.doCheckedFund(paramMap);//核销资金
		if(i>0){
			flag = true;
			msg = "核销成功";
		}else{
			flag = false;
			msg = "核销失败";
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("资金扣划-非网银","核销", msg));
	}
	
	/********************************************资金扣划-非网银-驳回**********Auth=杨雪***********************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-核销","驳回"})
	public Reply doReject() {
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		FundNotEBankService service = new FundNotEBankService();
		int i = 0;
		boolean flag = false;
		
		i = service.doReject(paramMap);//驳回
		if(i>0){
			flag=true;
		}else {
			flag=false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("资金扣划-非网银","驳回", paramMap.get("USERCODE").toString()));
	}
	
	/********************************************资金扣划-非网银-作废**********Auth=杨雪***********************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-申请","作废"})
	public Reply doCancellation() {
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		FundNotEBankService service = new FundNotEBankService();
		int i = 0;
		boolean flag = false;
		
		i = service.doCancellation(paramMap);//作废
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("资金扣划-非网银","作废", paramMap.get("USERCODE").toString()));
	}
	
	/********************************************资金扣划-非网银-查询**********Auth=杨雪***********************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-查询","列表"})
	public Reply toMgFirstPeriod() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));
		return new ReplyHtml(VM.html(path+"toMgFirstPeriod.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-查询","列表"})
	public Reply toMgFirstPeriodData(){
		Map<String, Object> param = _getParameters();
		FundNotEBankService service = new FundNotEBankService();
		return new ReplyAjaxPage(service.toMgDeductData1(param));
	}
	
	/****************************************************** 资金扣划-非网银-清单查看 ********************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "资金扣划-非网银", "资金扣划-非网银-清单查询","列表"})
	public Reply toMgFirstPerideDetail() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));
		return new ReplyHtml(VM.html(path+"toMgFirstPerideDetail.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = { "资金管理", "资金扣划-非网银", "资金扣划-非网银-查询","列表"})
	public Reply toMgFirstPeriodDeatiData(){
		Map<String, Object> param = _getParameters();
		FundNotEBankService service = new FundNotEBankService();
		return new ReplyAjaxPage(service.findDetialDataList(param));
	}
	
}

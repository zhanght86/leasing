package com.pqsoft.capitalPool.claimsPool.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.capitalPool.claimsPool.service.ClaimsPoolService;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.fundNotEBank.service.FundNotEBankService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ClaimsPoolAction extends Action{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	public VelocityContext context=new VelocityContext();
	private static DataDictionaryService dataDictionaryService = new DataDictionaryService();
	ClaimsPoolService service=new ClaimsPoolService();

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_C_Manger()
	{
		Map map=this._getParameters();
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		//签约状态
		List<Object> PROJECT_STATUS_LIST = (List)new SysDictionaryMemcached().get("项目状态位");
		context.put("PROJECT_STATUS_LIST", PROJECT_STATUS_LIST);
		
		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPoolManager.vm", context));
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_C_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.getClaimsPool_Page(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资金管理","保险理赔-放款", "申请" ,"修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claims_Info_Update()
	{
		Map map=this._getParameters();
		int num=service.updateclaims(map);
		boolean flag=false;
		if(num>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag,null,null).addOp(new OpLog("资金管理", "保险理赔-放款-申请", "修改"));
	}
	
	@aPermission(name = { "资金管理","保险理赔-放款", "申请" ,"保存" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claims_C_Submit(){
		Map map=this._getParameters();
		service.claims_C_Submit(map);
		return this.claimsHead_S_Manger();
	}
	
	@aPermission(name = { "资金管理","保险理赔-放款", "申请" ,"列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsHead_S_Manger()
	{
		Map map=this._getParameters();
		return new ReplyHtml(VM.html("capitalPool/claimsPool/claims_Head_S_Manager.vm", context));
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsHead_S_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.getClaimsHead_Page(param);
		return new ReplyAjaxPage(page);
	}
	
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply claimsPool_Detail(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getClaimsPoolDetail(param));
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","作废" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_Remove(){
		Map<String, Object> param = _getParameters();
		service.claimsPool_Remove(param);
		return this.claimsHead_S_Manger();
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","提交" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply JBPM_ClaimsPool(){
		Map<String, Object> param = _getParameters();
		service.JBPM_ClaimsPool(param);
		return this.claimsHead_S_Manger();
	}
	
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply claimsPool_JBPM_VIEW_MS()
	{
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		
		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPool_Jbpm_view.vm", context));
	}
	
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply claimsPool_JBPM_VIEW_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.claimsPool_JBPM_VIEW(param);
		return new ReplyAjaxPage(page);
	}
	
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply claimsPool_JBPM_Detail(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getClaimsPoolDetail(param));
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "放款","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_Back_MS()
	{
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		
		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPool_Back_Manager.vm", context));
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "放款","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_back_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.claimsPool_back_PageAjax(param);
		return new ReplyAjaxPage(page);
	}
	
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "放款","驳回" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_BOHUI(){
		Map<String, Object> param = _getParameters();
		service.claimsPool_BOHUI(param);
		return this.claimsPool_Back_MS();
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "放款","核销" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_Confirm(){
		Map<String, Object> param = _getParameters();
		service.claimsPool_Confirm(param);
		return this.claimsPool_Back_MS();
	}
	
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "放款单查询","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_HeadView_MS()
	{
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		
		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPool_HeadView_MS.vm", context));
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "放款单查询","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_HeadView_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.claimsPool_HeadView_VIEW(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "明细查询","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_Detail_Manger()
	{
		Map map=this._getParameters();
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		//签约状态
		List<Object> PROJECT_STATUS_LIST = (List)new SysDictionaryMemcached().get("项目状态位");
		context.put("PROJECT_STATUS_LIST", PROJECT_STATUS_LIST);
		
		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPool_Detail_Manager.vm", context));
	}
	
	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply claimsPool_Detail_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.getClaimsPool_Detail_Page(param);
		return new ReplyAjaxPage(page);
	}
}
//package com.pqsoft.capitalPool.claimsPool.action;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
//import com.pqsoft.dataDictionary.service.DataDictionaryService;
//import com.pqsoft.fundNotEBank.service.FundNotEBankService;
//import com.pqsoft.capitalPool.claimsPool.service.ClaimsPoolService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.OpLog;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//
//public class ClaimsPoolAction extends Action{
//	
//	private final Logger logger = Logger.getLogger(this.getClass());
//	public VelocityContext context=new VelocityContext();
//	private static DataDictionaryService dataDictionaryService = new DataDictionaryService();
//	ClaimsPoolService service=new ClaimsPoolService();
//
//	@Override
//	public Reply execute() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","列表"  })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_C_Manger()
//	{
//		Map map=this._getParameters();
//		FundNotEBankService Fundservice = new FundNotEBankService();
//		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
//		
//		//签约状态
//		List<Object> PROJECT_STATUS_LIST = (List)new DataDictionaryMemcached().get("项目状态位");
//		context.put("PROJECT_STATUS_LIST", PROJECT_STATUS_LIST);
//		
//		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPoolManager.vm", context));
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_C_PageAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.getClaimsPool_Page(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//	@aPermission(name = { "资金管理","保险理赔-放款", "申请" ,"修改" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claims_Info_Update()
//	{
//		Map map=this._getParameters();
//		int num=service.updateclaims(map);
//		boolean flag=false;
//		if(num>0)
//		{
//			flag=true;
//		}
//		return new ReplyAjax(flag,null,null).addOp(new OpLog("资金管理", "保险理赔-放款-申请", "修改"));
//	}
//	
//	@aPermission(name = { "资金管理","保险理赔-放款", "申请" ,"保存" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claims_C_Submit(){
//		Map map=this._getParameters();
//		service.claims_C_Submit(map);
//		return this.claimsHead_S_Manger();
//	}
//	
//	@aPermission(name = { "资金管理","保险理赔-放款", "申请" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsHead_S_Manger()
//	{
//		Map map=this._getParameters();
//		return new ReplyHtml(VM.html("capitalPool/claimsPool/claims_Head_S_Manager.vm", context));
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsHead_S_PageAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.getClaimsHead_Page(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//	
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.LOGIN)
//	public Reply claimsPool_Detail(){
//		Map<String, Object> param = _getParameters();
//		return new ReplyAjax(service.getClaimsPoolDetail(param));
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","作废" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_Remove(){
//		Map<String, Object> param = _getParameters();
//		service.claimsPool_Remove(param);
//		return this.claimsHead_S_Manger();
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","提交" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply JBPM_ClaimsPool(){
//		Map<String, Object> param = _getParameters();
//		service.JBPM_ClaimsPool(param);
//		return this.claimsHead_S_Manger();
//	}
//	
//	
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.LOGIN)
//	public Reply claimsPool_JBPM_VIEW_MS()
//	{
//		Map<String, Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		
//		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPool_Jbpm_view.vm", context));
//	}
//	
//	
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.LOGIN)
//	public Reply claimsPool_JBPM_VIEW_PageAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.claimsPool_JBPM_VIEW(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//	
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.LOGIN)
//	public Reply claimsPool_JBPM_Detail(){
//		Map<String, Object> param = _getParameters();
//		return new ReplyAjax(service.getClaimsPoolDetail(param));
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "放款","列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_Back_MS()
//	{
//		Map<String, Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		
//		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPool_Back_Manager.vm", context));
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "放款","列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_back_PageAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.claimsPool_back_PageAjax(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "放款","驳回" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_BOHUI(){
//		Map<String, Object> param = _getParameters();
//		service.claimsPool_BOHUI(param);
//		return this.claimsPool_Back_MS();
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "放款","核销" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_Confirm(){
//		Map<String, Object> param = _getParameters();
//		service.claimsPool_Confirm(param);
//		return this.claimsPool_Back_MS();
//	}
//	
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "放款单查询","列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_HeadView_MS()
//	{
//		Map<String, Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		
//		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPool_HeadView_MS.vm", context));
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "放款单查询","列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_HeadView_PageAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.claimsPool_HeadView_VIEW(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "明细查询","列表"  })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_Detail_Manger()
//	{
//		Map map=this._getParameters();
//		FundNotEBankService Fundservice = new FundNotEBankService();
//		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
//		
//		//签约状态
//		List<Object> PROJECT_STATUS_LIST = (List)new DataDictionaryMemcached().get("项目状态位");
//		context.put("PROJECT_STATUS_LIST", PROJECT_STATUS_LIST);
//		
//		return new ReplyHtml(VM.html("capitalPool/claimsPool/claimsPool_Detail_Manager.vm", context));
//	}
//	
//	@aPermission(name = { "资金管理", "保险理赔-放款", "申请","列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply claimsPool_Detail_PageAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.getClaimsPool_Detail_Page(param);
//		return new ReplyAjaxPage(page);
//	}
//}

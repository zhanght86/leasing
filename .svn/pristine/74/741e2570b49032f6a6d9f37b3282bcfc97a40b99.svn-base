package com.pqsoft.rentWrite.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.fundNotEBank.service.FundNotEBankService;
import com.pqsoft.payment.action.PaymentApplyAction;
import com.pqsoft.payment.service.PaymentApplyService;
import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.rentWrite.service.rentWriteService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DataDictionaryUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ExcelUtilMoreRow;
import com.pqsoft.util.FIinterface;
import com.pqsoft.util.FiEbankUtil;
import com.pqsoft.util.ReplyExcel;

public class rentWriteNewAction extends Action{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	public VelocityContext context=new VelocityContext();
	private static DataDictionaryService dataDictionaryService = new DataDictionaryService();
	rentWriteNewService service=new rentWriteNewService();
	private FiEbankUtil util = new FiEbankUtil();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划", "申请","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_C_Manger()
	{
		Map map=this._getParameters();
		
		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));//获取付款方式	
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));//获取厂商		
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		map.put("USERCODE", Security.getUser().getCode());//获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());//获取登陆人
		
		//通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp=new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		}
		else{
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteNew/bank_C_MangerNew.vm", context));
	}
	
	//非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划", "申请","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_C_MangerSUPP()
	{
		Map map=this._getParameters();
		
		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));//获取付款方式	
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));//获取厂商		
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		map.put("USERCODE", Security.getUser().getCode());//获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());//获取登陆人
		
		//通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp=new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		}
		else{
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteNew/bank_C_MangerNew1.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply Bank_C_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Bank_C_Page(param);
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "申请","列表"})
	public Reply toMgDeduct() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));
		
		//判断是否为供应商，供应商（厂商）则不能修改金额提交
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			context.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			context.put("COMPANY_ID",COM_MAP.get("COMPANY_ID"));
		}
		
		return new ReplyHtml(VM.html("rentWriteNew/bank_S_MangerNew.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "申请", "列表"})
	public Reply toAjaxData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toAjaxData(param));
	}
	
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply LockTypeIsF(){
		Map map=this._getParameters();
		int num=service.LockTypeIsF(map);
		return new ReplyAjax(true,num);
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply LockTypeIsFNew(){
		Map map=this._getParameters();
		int num=service.LockTypeIsFNew(map);
		return new ReplyAjax(true,num);
	}
	
	@aPermission(name = { "资金管理", "租金扣划", "申请","提交" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_C_Submit()
	{
		Map map=this._getParameters();
		//保存选中的数据插入到结算单和明细中
		service.bank_C_Submit(map);
		return this.toMgDeduct();
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划","申请","提交"})
	public Reply toSubmitPayment() {
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
		return new ReplyAjax(flag, null).addOp(new OpLog("租金扣划-非网银","申请-提交", map.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划","申请","作废"})
	public Reply toRemovePayment() {
		Map<String,Object> map = _getParameters();//获取页面参数
		
		map.put("USERCODE",Security.getUser().getCode());//用户编号
		map.put("USERNAME",Security.getUser().getName());//用户名
		
		int i = 0;
		i = service.doCancellation(map);
		
		boolean flag = false;
		if(i>0){
			flag = true;
		}else {
			flag = false;
		}		
		return new ReplyAjax(flag, null).addOp(new OpLog("租金扣划-非网银","申请-作废", map.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划","申请","查看明细"})
	public Reply getFundDetailData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getFundDetail(param));
	}
	
	@aPermission(name = { "资金管理", "租金扣划","申请","导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply HEAD_Upload()
	{
		Map map=_getParameters();
		return new ReplyExcel(service.HEAD_Upload(map), "租金扣划明细导出").addOp(new OpLog("资金管理", "租金扣划申请导出", "导出错误"));
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply CreateJoinFundDate()
	{
		service.CreateJoinFundDate();
		return new ReplyAjax(true,null);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销","列表"})
	public Reply toMgAppCheckMg() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteNew/toFund_ConfirmNew.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销","列表"})
	public Reply toMgAppCheckMgData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toMgAppAlready(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销","核销操作"})
	public Reply getCheckedDetail() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		
		context.put("param", map);
		Map mapPage=(Map)service.queryHeXiaoPage(map);
		//判断是租金非网银还是供应商垫付 和租金池
		List listTage=new ArrayList();
		
		List poolList=new ArrayList();
		String OWNER_ID=null;
		if(mapPage!=null && mapPage.get("FI_FLAG")!=null && !mapPage.get("FI_FLAG").equals("") && mapPage.get("FI_FLAG").toString().equals("3")){
			listTage=service.queryCustByFundID(mapPage);
			
			if(listTage.size()>0)
			{
				Map MapTage=(Map)listTage.get(0);
				OWNER_ID=(MapTage.get("FI_TAGE_ID")!=null && !MapTage.get("FI_TAGE_ID").equals(""))?MapTage.get("FI_TAGE_ID").toString():"";
			}
			//只需要保证金池，承租人资金池，保险理赔资金池，待处理资金池
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
		else if(mapPage!=null && mapPage.get("FI_FLAG")!=null && !mapPage.get("FI_FLAG").equals("") && mapPage.get("FI_FLAG").toString().equals("5"))
		{
			listTage=service.querySuppByFundID(mapPage);
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
		context.put("listTage", listTage);
		//查询是否有池子存在
		mapPage.put("POOLSTATE", service.queryPoolNumber(map));
		context.put("f_data", mapPage);//查看申请表数据
		context.put("poolList", poolList);
		return new ReplyHtml(VM.html("rentWriteNew/to_fund_confirm_detailNew.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销","保存"})
	public Reply doCommitHXSheet(){
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		
		int i =0;
		boolean flag = false;
		i = service.doUpdateFHead(paramMap);//更新申请单数据
		if(i>0){
			service.doInsertAccount1(paramMap);//添加实收明细
			if(i>0){
				flag = true;
			}else{
				flag = false;
			}
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("租金扣划-非网银","核销-保存", paramMap.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销","核销"})
	public Reply doCommitHXSheetHexiao(){
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		
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
		return new ReplyAjax(flag,null).addOp(new OpLog("租金扣划-非网银","核销-核销", paramMap.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销","驳回"})
	public Reply doReject() {
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		int i = 0;
		boolean flag = false;
		
		i = service.doReject(paramMap);//驳回
		if(i>0){
			flag=true;
		}else {
			flag=false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("租金扣划-非网银","驳回", paramMap.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销","核销"})
	public Reply doCheckedFund() {
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		int i = 0;
		boolean flag = false;
		
		i=service.doCheckedFund(paramMap);//核销资金
		if(i>0){
			flag = true;
		}else{
			flag = false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("租金扣划-非网银","核销", paramMap.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","列表"})
	public Reply toSupperFundMg() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteNew/tocyber_ConfirmNew.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","列表"})
	public Reply toSupperFundMgData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toSupperFundMg(param));
	}
	
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply toSupper_Upload()
	{
		Map map=_getParameters();
		if(map!=null && map.get("uploadType1")!=null && !map.get("uploadType1").equals(""))
		{
			String uploadType=map.get("uploadType1").toString();
			String bankFlag=map.get("bankFlag")!=null?map.get("bankFlag").toString():"-1";//判断选择的银行模版   1：建设银行  2：民生银行
			if(uploadType.equals("select"))//导出选中项
			{
				List list=new ArrayList();
				JSONArray guaranjsonArray = JSONArray.fromObject(map.get("selectDateHidden"));
				for (Object object : guaranjsonArray) {
					Map<String, Object> m = (Map<String, Object>) object;
					list.add(m);
				}
				List listReturn=service.toSupper_Upload(list,map);
				
				map.put("dataList", listReturn);
				if(bankFlag.equals("1"))
				{
					//excel导出
					String no = new FiEbankUtil().getExportNo("供应商垫付");
					return new ReplyExcel(util.exportData(map),"headpayment"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+no+".xls");
				}
				else if(bankFlag.equals("2"))
				{
					//txt导出
					map.put("EXPORT_TYPE", "供应商垫付");
					map.put("FILE_HEAD", "DK");
					util.exportMSEbank(SkyEye.getResponse(),map);
					return null;
				}
			}
			else//导出所有
			{
				//根据条件查询所需要导出的数据和修改状态
				List list=service.querySupp_uplad_All(map);
				List listReturn=service.toSupper_Upload(list,map);
				
				map.put("dataList", listReturn);
				if(bankFlag.equals("1"))
				{
					//excel导出
					String no = new FiEbankUtil().getExportNo("供应商垫付");
					return new ReplyExcel(util.exportData(map),"headpayment"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+no+".xls");
				}
				else if(bankFlag.equals("2"))
				{
					//txt导出
					map.put("EXPORT_TYPE", "供应商垫付");
					map.put("FILE_HEAD", "DK");
					util.exportMSEbank(SkyEye.getResponse(),map);
					return null;
				}
			}
		}
		return this.toSupperFundMg();
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","上传核销" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply uploadExcel1(){
		boolean flag = false;
		String msg = "";
		List<File> files = _getFile();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map m = _getParameters();
		try {
				
			//判断选择的银行模版   1：建设银行  其他：民生银行
			if("1".equals(m.get("bankFlag")+"")){
				list = util.parseJSEbank(files.get(0));
				m.put("FI_REALITY_BANK", "中国建设银行");
			}else{
				list = util.parseMSEbank(files.get(0));
				m.put("FI_REALITY_BANK", "中国建设银行");
			}
			//对回执的数据进行处理
			Map mapDate=service.backFileOp(list,m);
			int SUCCESS_NUM=Integer.parseInt(mapDate.get("SUCCESS_NUM").toString());
			int ERROR_BANK_NUM=Integer.parseInt(mapDate.get("ERROR_BANK_NUM").toString());
			int ERROR_NUM=list.size()-SUCCESS_NUM-ERROR_BANK_NUM;
			if(list.size() > 0){
				msg = "核销成功【"+SUCCESS_NUM+"】条，失败【"+ERROR_BANK_NUM+"】条，异常【"+ERROR_NUM+"】条！";
				flag = true;
			}else{
				msg = "核销失败,请检查上传文件格式！";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax(flag,null,msg);
	}
	
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","重置" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply destroySuppUp()
	{
		Map map=_getParameters();
		service.destroySuppUp(map);
		return this.toSupperFundMg();
	}
	
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply updateFundHeadBank()
	{
		Map map=this._getParameters();
		int num=service.updateFundHeadBank(map);
		boolean flag=false;
		if(num>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag,null,null).addOp(new OpLog("付款管理", "租赁物放款", "修改"));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","查看明细"})
	public Reply getSupperFundData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getFundDetail(param));
	}
	
	//网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-网银", "申请","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBank_C_Manger()
	{
		Map map=this._getParameters();
		//签约状态
		List<Object> SIGN_FLAG_LIST = (List)new DataDictionaryMemcached().get("签约标识");
		context.put("SIGN_FLAG_LIST", SIGN_FLAG_LIST);
		
		//客户类型CUST_TYPE_LIST
		List<Object> CUST_TYPE_LIST = (List)new DataDictionaryMemcached().get("客户类型");
		context.put("CUST_TYPE_LIST", CUST_TYPE_LIST);
		
		//导出状态EXP_STATE
		List<Object> EXP_STATE_LIST = (List)new DataDictionaryMemcached().get("导出状态");
		context.put("EXP_STATE_LIST", EXP_STATE_LIST);
		
		//开户行所属总行
		List<Object> BANK_NAME_LIST = (List)new DataDictionaryMemcached().get("开户行所属总行");
		context.put("BANK_NAME_LIST", BANK_NAME_LIST);
		
		//网银导出模版类型
		List<Object> WY_DC_TYPE_LIST = (List)new SysDictionaryMemcached().get("网银导出模版类型");
		context.put("WY_DC_TYPE_LIST", WY_DC_TYPE_LIST);
		
		//租金类别FEE_TYPE
		List<Object> FEE_TYPE_LIST = (List)new DataDictionaryMemcached().get("租金类别");
		List listFee=new ArrayList();
		for(int i=0;i<FEE_TYPE_LIST.size();i++)
		{
			Map fee_type_Map=(Map)FEE_TYPE_LIST.get(i);
			if(fee_type_Map.get("FLAG").equals("租金") || fee_type_Map.get("FLAG").equals("违约金"))
			{
				listFee.add(fee_type_Map);
			}
		}
		context.put("FEE_TYPE_LIST", listFee);
		
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		map.put("USERCODE", Security.getUser().getCode());//获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());//获取登陆人
		
		//锁
		String LOCKTYPE=service.JoinLock();
		map.put("LOCKTYPE", LOCKTYPE);
		context.put("param", map);
		
		return new ReplyHtml(VM.html("rentWriteNew/cyberBank_C_MangerNew.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBank_C_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_cyberBank_C_Page(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "申请","导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBank_C_Upload()
	{
		Map map=_getParameters();
		String PR_ID="";
		if(map!=null && map.get("uploadType")!=null && !map.get("uploadType").equals(""))
		{
			String uploadType=map.get("uploadType").toString();
			String bankFlag=map.get("bankFlag")!=null?map.get("bankFlag").toString():"-1";//判断选择的银行模版   1：建设银行  2：民生银行
			if(uploadType.equals("select"))//导出选中项
			{
				PR_ID=service.query_NewCyberBank_select(map);
			}
			else//导出所有
			{
				//根据条件查询所需要导出的数据和修改状态
				PR_ID=service.query_cyberBank_All(map);
			}
			
			List listReturn=new ArrayList();;
			
			if(bankFlag.equals("TLTBZEXCEL"))//通联通excel
			{
				listReturn=service.query_cyberBank_ListJY(PR_ID);
			}
			else if(bankFlag.equals("TLTBZTXT"))//通联通txt
			{
				listReturn=service.query_cyberBank_ListJY(PR_ID);
			}
			else if(bankFlag.equals("MSYHTXT"))//民生txt
			{
				listReturn=service.query_cyberBank_ListSZ(PR_ID);
			}else if(bankFlag.equals("JSYHEXCEL"))//建设银行excel
			{
				listReturn=service.query_cyberBank_ListSZ(PR_ID);
			}else if(bankFlag.equals("JSYHTXT"))//建设银行TXT
			{
				listReturn=service.query_cyberBank_ListSZ(PR_ID);
			}else if(bankFlag.equals("KQDKEXCEL"))//快钱代扣导出Excel
			{
				listReturn=service.query_cyberBank_ListFC(PR_ID);
			}else if(bankFlag.equals("GDYHDK"))//光大银行代扣导出Excel
			{
				listReturn=service.query_cyberBank_ListFCGD(PR_ID);
			}else if(bankFlag.equals("ZJZFTZT"))//中金支付txt
			{
				listReturn=service.query_cyberBank_ListJY(PR_ID);
			}
			
			map.put("dataList", listReturn);
			
			String SHH="00000001";//商户号
			String BS="S";//标示，S为收款
			String FGF="_";//分割符
			String BBH="02";//版本号
			String DA=DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","");
			
			if(bankFlag.equals("TLTBZEXCEL"))//通联通excel
			{
				//excel导出
				String no = new FiEbankUtil().getExportNo("租金网银-通联通excel");
				return new ReplyExcel(util.exportDataJY(map),SHH+FGF+BS+BBH+DA+FGF+no+".xls");//建元导出
			}
			else if(bankFlag.equals("TLTBZTXT"))//通联通txt
			{
				//txt导出
				String no = new FiEbankUtil().getExportNo("租金网银-通联通txt");
				
				String FILE_NAME=SHH+FGF+BS+BBH+DA+FGF+no+".txt";
				map.put("FILE_NAME", FILE_NAME);
				util.exportMSEbankJY(SkyEye.getResponse(),map);//建元导出
				return null;
			}
			else if(bankFlag.equals("MSYHTXT"))//民生txt
			{
				map.put("EXPORT_TYPE", "租金网银-民生txt");
				map.put("FILE_HEAD", "DK");
				util.exportMSEbank(SkyEye.getResponse(),map);
				return null;
			}else if(bankFlag.equals("JSYHEXCEL"))//建设银行excel
			{
				//excel导出
				String no = new FiEbankUtil().getExportNo("租金网银-建设银行excel");
				return new ReplyExcel(util.exportData(map),"rent"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+no+".xls");//山重导出
			}else if(bankFlag.equals("JSYHTXT"))//建设银行txt
			{
				map.put("EXPORT_TYPE", "租金网银-建设银行txt");
				map.put("FILE_HEAD", "DK");
				util.exportJSYHTXTEbank(SkyEye.getResponse(),map);
				return null;
			}else if(bankFlag.equals("KQDKEXCEL"))//快钱代扣txt
			{
				String no = new FiEbankUtil().getExportNo("租金网银-快钱代扣excel");
				Map m=new HashMap();
				
				m.put("FILE_NAME", "快钱代扣模板"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+no+".xls");
				m.put("PATH", "KQDKMB.xls");
				m.put("listData", listReturn);
				m.put("rowNum", 3);//从excel第几行开始插入（excel行从0开始计算）
				String[] tilteCell=new String[] {"AGREEMENTNO","LEASE_CODE","BANK_CUSTNAME","BANK_ACCOUNT","MONEY","YT","PROJ_ID","CCV2","YXQ"};
				m.put("tilteCell", tilteCell);

				//有固定行列值
				List listGD=new ArrayList();
				Map mapGD1=new HashMap();
				mapGD1.put("ROWCOL", 1);
				mapGD1.put("CELLCOL", 1);
				mapGD1.put("CELLVALUE", "K15-2000-786");
				
				Map mapGD=new HashMap();
				mapGD.put("ROWCOL", 1);
				mapGD.put("CELLCOL", 3);
				mapGD.put("CELLVALUE", DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+"-"+no);
				listGD.add(mapGD);
				
				m.put("GD", listGD);
				
				return new ExcelUtilMoreRow(m);
			}else if(bankFlag.equals("GDYHDK")){
				//excel导出
				String no = new FiEbankUtil().getExportNo("租金网银-光大银行代扣");
				return new ReplyExcel(util.exportDataGD(map),"光大银行代扣"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+no+".xls");//山重导出
			}else if(bankFlag.equals("ZJZFTZT")){	// 中金支付txt
				
				//txt导出
				map.put("EXPORT_TYPE", "租金网银-中金支付.txt");
				util.exportDataPay(SkyEye.getResponse(),map);
				
				return null;
			}
			
		}
		return this.cyberBank_C_Manger();
	}
	
	
	//网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberConfirm_Manger()
	{
		Map map=this._getParameters();
		//客户类型CUST_TYPE_LIST
		List<Object> CUST_TYPE_LIST = (List)new DataDictionaryMemcached().get("客户类型");
		context.put("CUST_TYPE_LIST", CUST_TYPE_LIST);
		
		//开户行所属总行
		List<Object> BANK_NAME_LIST = (List)new DataDictionaryMemcached().get("开户行所属总行");
		context.put("BANK_NAME_LIST", BANK_NAME_LIST);
		
		//网银导出模版类型
		List<Object> WY_DC_TYPE_LIST = (List)new SysDictionaryMemcached().get("网银导出模版类型");
		context.put("WY_DC_TYPE_LIST", WY_DC_TYPE_LIST);
		
		//租金类别FEE_TYPE
		List<Object> FEE_TYPE_LIST = (List)new DataDictionaryMemcached().get("租金类别");
		List listFee=new ArrayList();
		for(int i=0;i<FEE_TYPE_LIST.size();i++)
		{
			Map fee_type_Map=(Map)FEE_TYPE_LIST.get(i);
			if(fee_type_Map.get("FLAG").equals("租金") || fee_type_Map.get("FLAG").equals("违约金"))
			{
				listFee.add(fee_type_Map);
			}
		}
		context.put("FEE_TYPE_LIST", listFee);
		
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		map.put("USERCODE", Security.getUser().getCode());//获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());//获取登陆人
		context.put("param", map);
		
		return new ReplyHtml(VM.html("rentWriteNew/cyberBankConfirmNew.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberConfirm_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.cyberConfirm_PageAjax(param);
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销", "重置"})
	public Reply cyberBankRoll()
	{
		Map map=this._getParameters();
		if(map!=null && map.get("IDS")!=null && !map.get("IDS").equals(""))
		{
			String IDS="";
			IDS=map.get("IDS").toString();
			Map mapID=new HashMap();
			mapID.put("IDS", IDS);
			service.cyberBankRoll(mapID);
		}
		return this.cyberConfirm_Manger();
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销", "重置"})
	public Reply rollBackAll()
	{
		Map map=this._getParameters();
		service.rollBackAll(map);
		return this.cyberConfirm_Manger();
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","上传回执" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply uploadExcel(){
		boolean flag = false;
		String msg = "";
		List<File> files = _getFile();
		
		
		String filepath = SkyEye.getConfig("file.path") + File.separator+"fileRentWriteBack";
		
		File filetemp = files.get(0);
		
		File temp = null;
		temp = new File(filepath);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		filepath += "/" + filetemp.getName();
		temp = new File(filepath);
		try {
			FileCopyUtils.copy(FileCopyUtils.copyToByteArray(filetemp), temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map m = _getParameters();
		String seqFileId=Dao.getSequence("SEQ_JOIN");
		if(m!=null){
			m.put("FILE_ID", seqFileId);
		}
		try {
				
			//判断选择的银行模版 
			if("JSYHEXCEL".equals(m.get("bankFlag")+"")){//建设银行excel
				list = util.parseJSEbankNew(files.get(0),m);//山重
			}else if("MSYHTXT".equals(m.get("bankFlag")+"")){//民生银行txt
				list = util.parseMSEbankNew(files.get(0),m);//山重
			}else if("TLTBZEXCEL".equals(m.get("bankFlag")+"")){//通联通excel
				list = util.parseJSEbankNewJY(files.get(0),m);//建元
			}else if("TLTBZTXT".equals(m.get("bankFlag")+"")){//通联通txt
				list = util.parseMSEbankNewJY(files.get(0),m);
			}else if("JSYHTXT".equals(m.get("bankFlag")+"")){//建设银行txt
				list = util.parseJSYHTXTEbankNew(files.get(0),m);
			}else if("KQDKEXCEL".equals(m.get("bankFlag")+"")){
				list = util.parseJSEbankNewFC(files.get(0),m);//复昌快钱excel
			}else if("GDYHDK".equals(m.get("bankFlag")+"")){
				list = util.parseGDEbankNew(files.get(0),m);//复昌光大银行
			}
			//对回执的数据进行处理
			m.put("PATH", temp.getAbsolutePath());
			m.put("FILE_TEMP_NAME", filetemp.getName());
			
			int number=service.backOP(list,m);
			if(number >0){
				//异步调用核销方法
				new Thread(new Runnable() {
					@Override
					public void run() {
						long start = System.currentTimeMillis();
						try {
							new rentWriteNewService().autoHxLKSC();
							Dao.commit();
						} catch (Exception e) {
							Dao.rollback();
							e.printStackTrace();
						} finally {
							Dao.close();
						}

						System.out.println("耗时："
								+ ((System.currentTimeMillis() - start) / 1000)+"秒");
					}
				}).start();
			}
			msg = "数据正在核销，请稍后查看回执结果！";
			
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax(flag,null,msg);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销单明细","列表"})
	public Reply fundDetail_Manger() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));//获取厂商		
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteNew/fundDetail_MangerNew.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销单明细","列表"})
	public Reply fundDetail_MangerData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.queryFundDetailAll(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销单查询"})
	public Reply toQueryMgDeduct() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));
		return new ReplyHtml(VM.html("rentWriteNew/query_S_MangerNew.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销单查询"})
	public Reply toQueryAjaxData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toQueryAjaxData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销单查询"})
	public Reply getQueryFundDetailData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getFundDetailAll(param));
	}
	
	//网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","回执结果"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBank_Result_Manger()
	{
		Map map=this._getParameters();
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteNew/resultMangerNew.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","回执结果" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "guozheng")
	public ReplyFile downFileTemplate() {
		Map<String, Object> param = _getParameters();
		String pdfPath = param.get("PATH").toString();
		File file = new File(pdfPath);
		String fileName = file.getName();
		return new ReplyFile(file, fileName);
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","回执结果" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBank_Result_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Result_C_Page(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","回执结果"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply ERROR_APP()
	{
		Map map=this._getParameters();
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteNew/resultERRORNew.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","回执结果" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply ERROR_APP_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.ERROR_APP_Page(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","回执结果"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply ERROR_INFO(){
		String ID = SkyEye.getRequest().getParameter("ID");
		service.ERROR_INFO(ID);
		return new ReplyAjax();
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply toQueryMgDeductJBPM() {
		//获取页面参数
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("rentWriteNew/query_S_MangerNewJBPM.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply autoHx(){
		Map map=_getParameters();
		String PR_ID="";
		PR_ID=service.query_cyberBank_All(map);
		
		//代扣数据
		List listReturn=service.query_cyberBank_ListAuto(PR_ID);
		
		//代扣条数和代扣总金额
		Map mapTitle=service.queryTitleAuto(PR_ID);
		
		if(listReturn.size()>0){
			String seqFileId=Dao.getSequence("SEQ_FI_FUND_FILE");
			map.put("ID", seqFileId);
			map.put("CREATE_NAME", Security.getUser().getName());
			map.put("TYPE", 2);
			map.put("FILE_ALL_NUM", listReturn.size());
			map.put("fileN", "系统自动核销");
			Dao.insert("rentWriteNew.insertFundFileAutoNew",map);
			
			
//			//提供给银行核销的数据
//			Map<String,Object> mapBank=new HashMap<String,Object>();
//			mapBank.put("dataList", listReturn);
//			mapBank.put("dataMap", mapTitle);
			
			System.out.println("++++++++++++++++++=");
			new FIinterface().tranx(listReturn, mapTitle,"100001");
		}
		service.autoHx();
		return new ReplyAjax(true);
	}
	
}

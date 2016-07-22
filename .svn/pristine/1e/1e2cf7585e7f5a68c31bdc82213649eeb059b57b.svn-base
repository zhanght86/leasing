package com.pqsoft.payment.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.fundNotEBank.service.FundNotEBankService;
import com.pqsoft.payment.service.paymentService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.ReplyExcel;

public class paymentAction  extends Action{

	public VelocityContext context=new VelocityContext();
	paymentService service=new paymentService();
	
//	@Override
//	@aPermission(name = { "放款管理", "租赁物放款", "申请" ,"新增"})
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
	public Reply execute() {

//		service.createPaymentData("553099");
//		Map map=new HashMap();
//		map.put("PROJECT_CODE", "SFNXSJ130235");
//		map.put("PAY_TYPE", 1);
//		map.put("INVOICE_NUM", "FP-005");
//		map.put("INVOICE_DATE", "2013-10-8");
//		map.put("GUARANTEE_NUM", "DB-005");
//		map.put("GUARANTEE_DATE", "2013-6-18");
//		map.put("START_DATE", "2013-10-14");
//		map.put("RESERVE_DATE", "2013-10-15");
//		map.put("ACCEPTANCE_DATE", "2013-10-14");
//		service.updatePMByDate(map);
//		service.updatePMByComplement("SFNXSJ130235");
//		service.updatePMByStateDate(map);
		return null;
	}
	
	//设备放款申请页面
	@aPermission(name = {"业务管理", "放款申请","添加"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Eq_PayMent_C()
	{
		Map map=this._getParameters();
		context.put("param", map);
	//2014-5-9注释吴国伟	
//		//可以申请的数据统计
//		context.put("DetailList", service.query_Eq_PayMent_C(map));
//		//可以申请的数据按照供应商分组统计
//		context.put("SuppList", service.query_EqSupp_PayMent_C(map));
//		//租赁物名称
//		FundNotEBankService Fundservice = new FundNotEBankService();
//		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		//重写付款2014-5-9
		context.put("paymentlist", service.getAllPaymentContract(map));
		
		//return new ReplyHtml(VM.html("payment/equipmentPayMent/eq_PayMent_C_MS.vm", context));
		return new ReplyHtml(VM.html("payment/equipmentPayMent/contract_PayMent_add.vm", context));
	}
	
	//其他费用放款申请页面
//	@aPermission(name = { "放款管理", "其他费用放款", "申请" ,"新增"})
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply query_OTHER_PayMent_C()
//	{
//		Map map=this._getParameters();
//		context.put("param", map);
//		
//		//可以申请的数据统计
//		context.put("DetailList", service.query_OTHER_PayMent_C(map));
//		//可以申请的数据按照供应商分组统计
//		context.put("SuppList", service.query_OTHERSupp_PayMent_C(map));
//		
//		//租赁物名称
//		FundNotEBankService Fundservice = new FundNotEBankService();
//		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
//		
//		return new ReplyHtml(VM.html("payment/otherPayMent/other_PayMent_C_MS.vm", context));
//	}
	
	//担保费放款申请页面
//	@aPermission(name = { "放款管理", "担保费放款", "申请" ,"新增"})
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply query_DB_PayMent_C()
//	{
//		Map map=this._getParameters();
//		context.put("param", map);
//		
//		//可以申请的数据统计
//		context.put("DetailList", service.query_DB_PayMent_C(map));
//		//可以申请的数据按照供应商分组统计
//		context.put("SuppList", service.query_DBSupp_PayMent_C(map));
//		
//		//租赁物名称
//		FundNotEBankService Fundservice = new FundNotEBankService();
//		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
//		
//		return new ReplyHtml(VM.html("payment/guaranteePayMent/guar_PayMent_C_MS.vm", context));
//	}
	
	
	@aPermission(name = { "放款管理", "租赁物放款", "申请" ,"新增-保存"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply eq_C_Submit()
	{
		Map map=this._getParameters();
		//保存选中的数据插入到结算单和明细中
		map.put("TYPE", 1);
		service.payMent_C_Submit(map);
		return this.pay_Head_Eq_Mg();
	}
	
	//@aPermission(name = {"业务管理", "放款申请","添加"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply payMentSave()
	{
		Map map=this._getParameters();
		//保存选中的数据插入到结算单和明细中
		map.put("TYPE", 1);
		service.payMent_C_Submit(map);
		return this.pay_Head_Eq_Mg();
	}
//	@aPermission(name = { "放款管理", "其他费用放款", "申请" ,"新增-保存" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply other_C_Submit()
//	{
//		Map map=this._getParameters();
//		//保存选中的数据插入到结算单和明细中
//		map.put("TYPE", 4);
//		service.payMentSave(map);
//		return this.pay_Head_Other_Mg();
//	}
//	
//	@aPermission(name = { "放款管理", "担保费放款", "申请" ,"新增-保存" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply db_C_Submit()
//	{
//		Map map=this._getParameters();
//		//保存选中的数据插入到结算单和明细中
//		map.put("TYPE", 2);
//		service.payMent_C_Submit(map);
//		return this.pay_Head_DB_Mg();
//	}
	
	
	@aPermission(name = {"业务管理", "放款申请","列表显示"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Head_Eq_Mg()
	{
		Map map=this._getParameters();
//		service.createPaymentData("551887");
		return new ReplyHtml(VM.html("payment/equipmentPayMent/eq_payMent_Head_MS.vm", context));
	}
	
	@aPermission(name = {"业务管理", "放款申请","列表显示"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Head_Eq_Mg_AJAX()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.pay_Head_Eq_Mg(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = {"业务管理", "放款申请","修改"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Head_Eq_Update()
	{
		Map map=this._getParameters();
		int num=service.updatePayMent(map);
		boolean flag=false;
		if(num>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag,null,null).addOp(new OpLog("业务管理", "放款申请", "修改"));
	}
	
//	@aPermission(name = { "放款管理", "其他费用放款", "申请" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Head_Other_Mg()
//	{
//		Map map=this._getParameters();
//		
//		return new ReplyHtml(VM.html("payment/otherPayMent/other_payMent_Head_MS.vm", context));
//	}
//	
//	@aPermission(name = { "放款管理", "其他费用放款", "申请" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Head_Other_Mg_AJAX()
//	{
//		Map<String, Object> param = _getParameters();
//		Page page = service.pay_Head_Other_Mg(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//	@aPermission(name = { "放款管理", "担保费放款", "申请" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Head_DB_Mg()
//	{
//		Map map=this._getParameters();
//		
//		return new ReplyHtml(VM.html("payment/guaranteePayMent/guar_payMent_Head_MS.vm", context));
//	}
//	
//	@aPermission(name = { "放款管理", "担保费放款", "申请" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Head_DB_Mg_AJAX()
//	{
//		Map<String, Object> param = _getParameters();
//		Page page = service.pay_Head_DB_Mg(param);
//		return new ReplyAjaxPage(page);
//	}
	
	@aPermission(name = {"业务管理", "放款申请","提交"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply eq_PayHead_Status()
	{
		Map<String, Object> map = _getParameters();
		map.put("STATUS", 2);
		service.PayHead_Status_Submit(map);
//		return this.pay_Head_Eq_Mg();
		String nextCode = new TaskService().getAssignee(map.get("JBPM_ID").toString());
		return new ReplyAjax(true,nextCode,"下一个操作人为: ").addOp(new OpLog("放款管理", "租赁物放款", "申请" ,"提交" ));
	}
	
//	@aPermission(name = { "放款管理", "其他费用放款", "申请" ,"提交" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply other_PayHead_Status()
//	{
//		Map<String, Object> map = _getParameters();
//		map.put("STATUS", 2);
//		service.PayHead_Status_Submit(map);
//		return this.pay_Head_Other_Mg();
//	}
	
//	@aPermission(name = { "放款管理", "担保费放款", "申请" ,"提交" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply db_PayHead_Status()
//	{
//		Map<String, Object> map = _getParameters();
//		map.put("STATUS", 2);
//		service.PayHead_Status_Submit(map);
//		return this.pay_Head_DB_Mg();
//	}
	
	@aPermission(name = {"业务管理", "放款申请","删除"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply eq_PayHead_De_Status()
	{
		Map<String, Object> map = _getParameters();
		service.PayHead_De(map);
		return this.pay_Head_Eq_Mg();
	}
	
//	@aPermission(name = { "放款管理", "其他费用放款", "申请" ,"删除" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply other_PayHead_De_Status()
//	{
//		Map<String, Object> map = _getParameters();
//		service.PayHead_De(map);
//		return this.pay_Head_Other_Mg();
//	}
	
//	@aPermission(name = { "放款管理", "担保费放款", "申请" ,"删除" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply db_PayHead_De_Status()
//	{
//		Map<String, Object> map = _getParameters();
//		service.PayHead_De(map);
//		return this.pay_Head_DB_Mg();
//	}
	
	
	@aPermission(name = { "放款管理", "放款回执上传", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_HeadBack_Eq_Mg()
	{
		Map map=this._getParameters();
		//通过平台查询平台银行
		// 查询平台ID
		map.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		List BANK_INFOLIST=service.queryBankListByMa(map);
		context.put("BANK_INFOLIST", BANK_INFOLIST);
		
		return new ReplyHtml(VM.html("payment/equipmentPayMent/eq_Back_Head_MS.vm", context));
	}
	
	@aPermission(name = { "放款管理", "放款回执上传", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_HeadBack_Eq_Mg_AJAX()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.pay_HeadBack_Eq_Mg(param);
		return new ReplyAjaxPage(page);
	}
	
//	@aPermission(name = { "付款管理", "其他费用放款", "回执" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_HeadBack_Other_Mg()
//	{
//		Map map=this._getParameters();
//		
//		return new ReplyHtml(VM.html("payment/otherPayMent/other_Back_Head_MS.vm", context));
//	}
	
//	@aPermission(name = { "付款管理", "其他费用放款", "回执" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_HeadBack_Other_Mg_AJAX()
//	{
//		Map<String, Object> param = _getParameters();
//		Page page = service.pay_HeadBack_Other_Mg(param);
//		return new ReplyAjaxPage(page);
//	}
	
//	@aPermission(name = { "付款管理", "担保费放款", "回执" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_HeadBack_DB_Mg()
//	{
//		Map map=this._getParameters();
//		
//		return new ReplyHtml(VM.html("payment/guaranteePayMent/guar_Back_Head_MS.vm", context));
//	}
	
//	@aPermission(name = { "付款管理", "担保费放款", "回执" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_HeadBack_DB_Mg_AJAX()
//	{
//		Map<String, Object> param = _getParameters();
//		Page page = service.pay_HeadBack_DB_Mg(param);
//		return new ReplyAjaxPage(page);
//	}
	
	@aPermission(name = {"放款管理", "放款回执上传", "核销"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply eq_PayHeadSub_Status()
	{
		Map<String, Object> map = _getParameters();
		map.put("STATUS", 5);
		map.put("EQ", "EQ");
		service.PayHead_Status(map);
		return this.pay_HeadBack_Eq_Mg();
	}
	
//	@aPermission(name = { "付款管理", "其他费用放款", "回执" ,"核销" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply other_PayHeadSub_Status()
//	{
//		Map<String, Object> map = _getParameters();
//		map.put("STATUS", 5);
//		service.PayHead_Status(map);
//		return this.pay_HeadBack_Other_Mg();
//	}
	
//	@aPermission(name = { "付款管理", "担保费放款", "回执" ,"核销" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply db_PayHeadSub_Status()
//	{
//		Map<String, Object> map = _getParameters();
//		map.put("STATUS", 5);
//		service.PayHead_Status(map);
//		return this.pay_HeadBack_DB_Mg();
//	}
	
	@aPermission(name = {"放款管理", "放款回执上传", "驳回"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply eq_PayHeadBack_Status()
	{
		Map<String, Object> map = _getParameters();
		map.put("STATUS", 7);
		service.PayHead_Status(map);
		return this.pay_HeadBack_Eq_Mg();
	}
	
//	@aPermission(name = { "付款管理", "其他费用放款", "回执" ,"驳回" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply other_PayHeadBack_Status()
//	{
//		Map<String, Object> map = _getParameters();
//		map.put("STATUS", 2);
//		service.PayHead_Status(map);
//		return this.pay_Head_Other_Mg();
//	}
	
//	@aPermission(name = { "付款管理", "担保费放款", "回执" ,"驳回" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply db_PayHeadBack_Status()
//	{
//		Map<String, Object> map = _getParameters();
//		map.put("STATUS", 2);
//		service.PayHead_Status(map);
//		return this.pay_HeadBack_Eq_Mg();
//	}
	
	
	@aPermission(name = { "放款管理", "放款查询", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Head_Query_Eq_Mg()
	{
		Map map=this._getParameters();
		
		List<Object> LOANSTATUS_LIST = (List)new SysDictionaryMemcached().get("放款状态");
		context.put("LOANSTATUS_LIST", LOANSTATUS_LIST);
		return new ReplyHtml(VM.html("payment/equipmentPayMent/eq_Query_Head_MS.vm", context));
	}
	
	
	/**
	 * 重签放款查询页面初期显示
	 * @return
	 */
	//add gengchangbao JZZL-85 2016年3月2日15:54:39 start
	@aPermission(name = { "放款管理", "重签查询", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "耿长宝")
	@aAuth(type = aAuthType.USER)
	public Reply reSignPayHeadQueryList()
	{
		Map<String, Object> param = _getParameters();
		
		return new ReplyHtml(VM.html("payment/equipmentPayMent/eq_Query_reSignHead_MS.vm", context));
	}
	
	
	/**
	 * 重签放款查询页面查询处理
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name = { "放款管理", "重签查询", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "耿长宝")
	public Reply reSignPayHeadQueryDataList(){
		Map<String, Object> params = _getParameters();
		Object Curr=params.get("page");//当前页
		Object count=params.get("rows");//每页显示的条数
		int pageCurr = Integer.parseInt(Curr==null?"1":Curr.toString());
		int pageCount = Integer.parseInt(count==null?"10":count.toString());

		params.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);//开始条数
		params.put("PAGE_END", pageCount * pageCurr);//结束的条数
		Page page = service.getReSignPayHeadListData(params); 
		
		return new ReplyAjaxPage(page);
	}
	
	/**
	 * 重签放款明细【查看】页面初期显示
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name = { "放款管理", "重签查看", "查看" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "耿长宝")
	public Reply reSignPayHeadQueryData(){
		Map<String, Object> params = _getParameters();
		Map<String,Object> mm=new HashMap<String,Object>();
		List<Map<String,Object>> cc= service.getSlsjfyInfo(mm);
		context.put("item", cc);
		context.put("PROJECT_ID", params.get("PROJECT_ID"));
//		//重签项目费用明细查看数据整理
//		service.getReSignPayHeadDataByProId(context,params); 
		service.getReSignPayHead_feeRemarks(context,params);
		params.put("IS_PAY", 1);
		List<Map<String,Object>> detailList = service.findXmmxlist(params);
		
		Map<String,Object> parProject = null; // 重签合同数据【主要获取前版【废弃】项目ID】
		List<Map<String,Object>> reSignDetaillist = null; //前版【废弃】项目
		//获取重签合同前版【废弃】项目编号
		List<Map<String,Object>> parProjectList = service.queryParentProjectById(params);
		if (parProjectList != null && parProjectList.size() > 0) {
			parProject = parProjectList.get(0);
			if (parProject.get("PARENT_ID") != null && !"".equals(parProject.get("PARENT_ID"))) {
				context.put("PAR_PROJECT_ID", parProject.get("PARENT_ID"));
				
				//获取重签合同前版【废弃】放款详细金额 
				// 查询重签合同编号废弃合同已经支付费用明细
				Map<String, Object> parProjectMap = new HashMap<String, Object>();
				parProjectMap.put("PROJECT_ID", parProject.get("PARENT_ID"));
				parProjectMap.put("isLoan", 1); 
				//获取已支付费用明细
				reSignDetaillist = service.findReSignXmmxlist(parProjectMap);
				if (reSignDetaillist != null && reSignDetaillist.size()>0) {
					reSignDetaillist = service.findXmmxlist(parProjectMap);
				}
			}
		}
		
		// 实付金额【废弃合同】
		Map<String, Object> detail = new HashMap<String, Object>();
		// 差价
		Map<String, Object> difference = new HashMap<String, Object>();
		if (reSignDetaillist != null) {
			//detail = getProjectDetailAndBase(detaillist);
			//整合重签合同费用明细与旧合同实付金额的差额
			//difference = getParentDifference(context,reSignDetaillist,(Map)context.get("schemeMoney"));
			difference = getParentDifference(context, detailList, reSignDetaillist);
		}
		
		Map reLeaseCode = service.getReSignLeaseCode(params);
		
		context.put("DEL_PROJECT_ID", reLeaseCode.get("DEL_PROJECT_ID"));
		context.put("DEL_LEASE_CODE", reLeaseCode.get("DEL_LEASE_CODE"));
		context.put("LEASE_CODE", reLeaseCode.get("LEASE_CODE"));
		context.put("PROJECT_ID", reLeaseCode.get("PROJECT_ID"));
		
		return new ReplyHtml(VM.html("payment/equipmentPayMent/reSignPayHeadToShow.vm", context));
	}
	
	/**
	 * 重签放款明细【修改】页面初期显示
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name = { "放款管理", "重签查询", "修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "耿长宝")
	public Reply reSignPayHeadUpdData(){
		Map<String, Object> params = _getParameters();
		Map<String,Object> mm=new HashMap<String,Object>();
		List<Map<String,Object>> cc= service.getSlsjfyInfo(mm);
		context.put("item", cc);
		context.put("PROJECT_ID", params.get("PROJECT_ID"));
//		//重签项目费用明细查看数据整理
//		service.getReSignPayHeadDataByProId(context,params); 
		service.getReSignPayHead_feeRemarks(context,params);
		params.put("IS_PAY", 1);
		List<Map<String,Object>> detailList = service.findXmmxlist(params);
		
		Map<String,Object> parProject = null; // 重签合同数据【主要获取前版【废弃】项目ID】
		List<Map<String,Object>> reSignDetaillist = null; //前版【废弃】项目
		//获取重签合同前版【废弃】项目编号
		List<Map<String,Object>> parProjectList = service.queryParentProjectById(params);
		if (parProjectList != null && parProjectList.size() > 0) {
			parProject = parProjectList.get(0);
			if (parProject.get("PARENT_ID") != null && !"".equals(parProject.get("PARENT_ID"))) {
				context.put("PAR_PROJECT_ID", parProject.get("PARENT_ID"));
				
				//获取重签合同前版【废弃】放款详细金额 
				// 查询重签合同编号废弃合同已经支付费用明细
				Map<String, Object> parProjectMap = new HashMap<String, Object>();
				parProjectMap.put("PROJECT_ID", parProject.get("PARENT_ID"));
				parProjectMap.put("isLoan", 1); 
				//获取已支付费用明细
				reSignDetaillist = service.findReSignXmmxlist(parProjectMap);
				if (reSignDetaillist != null && reSignDetaillist.size()>0) {
					reSignDetaillist = service.findXmmxlist(parProjectMap);
				}
			}
		}
		
		// 实付金额【废弃合同】
		Map<String, Object> detail = new HashMap<String, Object>();
		// 差价
		Map<String, Object> difference = new HashMap<String, Object>();
		if (reSignDetaillist != null) {
			//detail = getProjectDetailAndBase(detaillist);
			//整合重签合同费用明细与旧合同实付金额的差额
			//difference = getParentDifference(context,reSignDetaillist,(Map)context.get("schemeMoney"));
			difference = getParentDifference(context, detailList, reSignDetaillist);
		}
		
		Map reLeaseCode = service.getReSignLeaseCode(params);
		
		context.put("DEL_PROJECT_ID", reLeaseCode.get("DEL_PROJECT_ID"));
		context.put("DEL_LEASE_CODE", reLeaseCode.get("DEL_LEASE_CODE"));
		context.put("LEASE_CODE", reLeaseCode.get("LEASE_CODE"));
		context.put("PROJECT_ID", reLeaseCode.get("PROJECT_ID"));
		
		return new ReplyHtml(VM.html("payment/equipmentPayMent/reSignPayHeadToUpd.vm", context));
	}
	
	/**
	 * 重签放款明细【修改】页面保存处理
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name = { "放款管理", "重签修改", "修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "耿长宝")
	public Reply saveReSignPayHeadUpdData(){
		Map<String, Object> params = _getParameters();
		//重签项目费用明细查看数据整理
		service.saveReSignPayHeadUpdData(params); 
		return new ReplyHtml(VM.html("payment/equipmentPayMent/reSignPayHeadToUpd.vm", context));
	}
	
	/**
	 * 
	 * 整合重签合同费用明细与旧合同实付金额的差额
	 * @param detaillist 实付【废弃合同】类型和价格
	 * @param schemeMoney 应付款项
	 * @return 价钱类型和对应的值Map对象
	 */
	private Map<String, Object> getParentDifference(VelocityContext context, List<Map<String, Object>> detaillist,List<Map<String, Object>> reSignDetailList) {
		
		// 实付金额【废弃合同】
		Map<String, Object> reSignDetail = new HashMap<String, Object>();
		reSignDetail.put("money1", "0");	//车辆实际采购价
		reSignDetail.put("money2", "0");	//车辆购置税
		reSignDetail.put("money3", "0");	//商业险
		reSignDetail.put("money4", "0");	//交强险
		reSignDetail.put("money5", "0");	//车船税
		reSignDetail.put("money6", "0");	//路桥费
		reSignDetail.put("money7", "0");	//环保标费
		reSignDetail.put("money8", "0");	//上牌费
		reSignDetail.put("money9", "0");	//临牌费
		reSignDetail.put("money10", "0");	//其他费用
		// 前版已付设备费用明细整理 Start
		for (Map<String, Object> map : reSignDetailList) {
			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
			if("1".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money1", map.get("APPLY_MONEY"));	//车辆实际采购价
				} else {
					reSignDetail.put("money1", "0");
				}
				
			}else if("2".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money2", map.get("APPLY_MONEY"));	//车辆购置税
				} else {
					reSignDetail.put("money2", "0");
				}
			}else if("3".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money3", map.get("APPLY_MONEY"));	//商业险
				} else {
					reSignDetail.put("money3", "0");
				}
			}else if("4".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money4", map.get("APPLY_MONEY"));	//交强险
				} else {
					reSignDetail.put("money4", "0");
				}
			}else if("5".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money5", map.get("APPLY_MONEY"));	//车船税
				} else {
					reSignDetail.put("money5", "0");
				}
			}else if("6".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money6", map.get("APPLY_MONEY"));	//路桥费
				} else {
					reSignDetail.put("money6", "0");
				}
			}else if("7".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money7", map.get("APPLY_MONEY"));	//环保标费
				} else {
					reSignDetail.put("money7", "0");
				}
			}else if("8".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money8", map.get("APPLY_MONEY"));	//上牌费
				} else {
					reSignDetail.put("money8", "0");
				}
			}else if("9".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money9", map.get("APPLY_MONEY"));	//临牌费
				} else {
					reSignDetail.put("money9", "0");
				}
			}else if("10".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money10", map.get("APPLY_MONEY"));	//其他费用
				} else {
					reSignDetail.put("money10", "0");
				}
			}
		}
		// 前版已付设备费用明细整理 end
		
		// 实付金额【废弃合同】
		Map<String, Object> detail = new HashMap<String, Object>();
		// 差价
		Map<String, Object> difference = new HashMap<String, Object>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("APPLY_MONEY", "0");
		detail.put("CLSJ", initMap);	//车辆实际采购价
		detail.put("CLGZS", initMap);	//车辆购置税
		detail.put("SYBX", initMap);	//商业险
		detail.put("JQX", initMap);		//交强险
		detail.put("CCS", initMap);		//车船税
		detail.put("LQF", initMap);		//路桥费
		detail.put("HBFY", initMap);	//环保标费
		detail.put("SPF", initMap);		//上牌费
		detail.put("LPF", initMap);		//临牌费
		detail.put("QTFY", initMap);	//其他费用
		BigDecimal sfje; //作废合同实付金额
		BigDecimal yfje; //重签合同应付金额
		BigDecimal cj; //差价
		for (Map<String, Object> map : detaillist) {
			yfje = new BigDecimal(0);
			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
			
			if("1".equals(TYPEID)){
				detail.put("CLSJ", map);	//车辆实际采购价
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money1").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference1", cj);

			}else if("2".equals(TYPEID)){
				detail.put("CLGZS", map);	//车辆购置税
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money2").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference2", cj);

			}else if("3".equals(TYPEID)){
				detail.put("SYBX", map);	//商业险
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money3").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference3", cj);
				
			}else if("4".equals(TYPEID)){
				detail.put("JQX", map);		//交强险
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money4").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference4", cj);
				
			}else if("5".equals(TYPEID)){
				detail.put("CCS", map);		//车船税
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money5").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference5", cj);
				
			}else if("6".equals(TYPEID)){
				detail.put("LQF", map);		//路桥费
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money6").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference6", cj);

			}else if("7".equals(TYPEID)){
				detail.put("HBFY", map);	//环保标费
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money7").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference7", cj);

			}else if("8".equals(TYPEID)){
				detail.put("SPF", map);		//上牌费
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money8").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference8", cj);
				
			}else if("9".equals(TYPEID)){
				detail.put("LPF", map);		//临牌费
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money9").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference9", cj);
				
			}else if("10".equals(TYPEID)){
				detail.put("QTFY", map);	//其他费用
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money10").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference10", cj);
				
			}
		}
		
		context.put("schemeMoney",reSignDetail);
		context.put("detaillist",detail);
		context.put("differencelist",difference);
		return detail;
	}
	
	//add gengchangbao JZZL-85 2016年3月2日15:54:39 end
	
	@aPermission(name = { "业务管理", "放款申请", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Head_Query_Eq_Mg_AJAX()
	{
		Map<String, Object> param = _getParameters();
		System.out.println("-----------param="+param);
		Page page = service.pay_Head_Query_Eq_Mg(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "业务管理", "放款申请", "列表显示" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Head_Excle_Eq_Mg()
	{
		Map<String, Object> param = _getParameters();

		service.pay_Head_Excle_Mg(param);
		return null;
	}
	
	@aPermission(name = { "业务管理", "放款申请", "列表显示" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Detail_Excle_Eq_Mg()
	{
		Map<String, Object> param = _getParameters();

		service.pay_Detail_Excle_Mg(param);
		return null;
	}
	
	
//	@aPermission(name = { "付款管理", "其他费用放款", "查询" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Head_Query_Other_Mg()
//	{
//		Map map=this._getParameters();
//		List<Object> LOANSTATUS_LIST = (List)new DataDictionaryMemcached().get("放款状态");
//		context.put("LOANSTATUS_LIST", LOANSTATUS_LIST);
//		return new ReplyHtml(VM.html("payment/otherPayMent/other_Query_Head_MS.vm", context));
//	}
//	
//	@aPermission(name = { "付款管理", "其他费用放款", "查询" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Head_Query_Other_Mg_AJAX()
//	{
//		Map<String, Object> param = _getParameters();
//		Page page = service.pay_Head_Query_Other_Mg(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//	@aPermission(name = { "付款管理", "担保费放款", "查询" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Head_Query_DB_Mg()
//	{
//		Map map=this._getParameters();
//		List<Object> LOANSTATUS_LIST = (List)new DataDictionaryMemcached().get("放款状态");
//		context.put("LOANSTATUS_LIST", LOANSTATUS_LIST);
//		return new ReplyHtml(VM.html("payment/guaranteePayMent/guar_Query_Head_MS.vm", context));
//	}
//	
//	@aPermission(name = { "付款管理", "担保费放款", "查询" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Head_Query_DB_Mg_AJAX()
//	{
//		Map<String, Object> param = _getParameters();
//		Page page = service.pay_Head_Query_DB_Mg(param);
//		return new ReplyAjaxPage(page);
//	}
	
	@aPermission(name = { "放款管理", "租赁物放款-查询明细", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Detail_Eq_Mg()
	{
		Map<String,Object> map=_getParameters();
		//租赁物名称
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		return new ReplyHtml(VM.html("payment/equipmentPayMent/eq_Detail_Ms.vm", context));
	}
	
	@aPermission(name = { "放款管理", "租赁物放款-查询明细", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Detail_Eq_Mg_AJAX()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.pay_Detail_Eq_Mg(param);
		return new ReplyAjaxPage(page);
	}
	
//	@aPermission(name = { "付款管理", "其他费用放款", "查询明细" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Detail_Other_Mg()
//	{
//		Map map=this._getParameters();
//		//租赁物名称
//		FundNotEBankService Fundservice = new FundNotEBankService();
//		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
//		return new ReplyHtml(VM.html("payment/otherPayMent/other_Detail_Ms.vm", context));
//	}
//	
//	@aPermission(name = { "付款管理", "其他费用放款", "查询明细" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Detail_Other_Mg_AJAX()
//	{
//		Map<String, Object> param = _getParameters();
//		Page page = service.pay_Detail_Other_Mg(param);
//		return new ReplyAjaxPage(page);
//	}
	
//	@aPermission(name = { "付款管理", "担保费放款", "查询明细" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Detail_DB_Mg()
//	{
//		Map map=this._getParameters();
//		//租赁物名称
//		FundNotEBankService Fundservice = new FundNotEBankService();
//		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
//		return new ReplyHtml(VM.html("payment/guaranteePayMent/guar_Detail_Ms.vm", context));
//	}
//	
//	@aPermission(name = { "付款管理", "担保费放款", "查询明细" ,"列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pay_Detail_DB_Mg_AJAX()
//	{
//		Map<String, Object> param = _getParameters();
//		Page page = service.pay_Detail_DB_Mg(param);
//		return new ReplyAjaxPage(page);
//	}
	
	@aPermission(name = { "放款管理", "租赁物放款-查询明细", "导出选中项/全导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply EQ_Upload()
	{
		Map<String,Object> map=_getParameters();
		return new ReplyExcel(service.EQ_Upload(map), "放款明细导出").addOp(new OpLog("债权管理", "放款明细导出", "导出错误"));
	}
	
	//@aPermission(name = { "付款管理", "租赁物放款","查询" ,"明细查询" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Detail_Query_Eq_Date()
	{
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.pay_Detail_Query_Eq_Date(param));
	}
	
	/* 
	 * 放款申请-申请资料,在原来上传资料页面基础上,增加一个页面,实现卡片跳转
	 * 跳转到上传资料 和 租赁投放审查审批表
	 */
	@aDev(code = "ZQT024", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply toInformationAlbum(){
		Map<String, Object> param =_getParameters();
		System.out.println("--623--param:"+param);
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("payment/toInformationAlbumMain.vm", context));
		
	}
}

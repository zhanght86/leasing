package com.pqsoft.rentWrite.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.fundNotEBank.service.FundNotEBankService;
import com.pqsoft.rentWrite.service.rentWriteService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FiEbankUtil;
import com.pqsoft.util.ReplyExcel;

public class rentWriteAction extends Action{

	private final Logger logger = Logger.getLogger(this.getClass());
	public VelocityContext context=new VelocityContext();
	private static DataDictionaryService dataDictionaryService = new DataDictionaryService();
	rentWriteService service=new rentWriteService();
	private FiEbankUtil util = new FiEbankUtil();
	
	@Override
	public Reply execute() {
		return null;
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
		
//		//开户银行BANK_LIST
//		List<Object> BANK_NAME_LIST = service.query_bankList();
//		context.put("BANK_NAME_LIST", BANK_NAME_LIST);
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		map.put("USERCODE", Security.getUser().getCode());//获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());//获取登陆人
		
		//锁
		String LOCKTYPE=service.JoinLock();
		map.put("LOCKTYPE", LOCKTYPE);
		context.put("param", map);
		
		return new ReplyHtml(VM.html("rentWrite/cyberBank_C_Manger.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBank_C_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_cyberBank_C_Page(param);
		return new ReplyAjaxPage(page);
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
		
		//开户银行BANK_LIST
		List<Object> BANK_NAME_LIST = service.query_bankList();
		context.put("BANK_NAME_LIST", BANK_NAME_LIST);
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		map.put("USERCODE", Security.getUser().getCode());//获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());//获取登陆人
		context.put("param", map);
		
		return new ReplyHtml(VM.html("rentWrite/cyberBankConfirm.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberConfirm_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.cyberConfirm_PageAjax(param);
		return new ReplyAjaxPage(page);
	}
	
	//非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划", "申请","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_C_Manger()
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
		
		//开户银行BANK_LIST
		List<Object> BANK_NAME_LIST = service.query_bankList();
		context.put("BANK_NAME_LIST", BANK_NAME_LIST);
		
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
		return new ReplyHtml(VM.html("rentWrite/bank_C_Manger.vm", context));
	}
	
	//非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划", "申请","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_C_MangerSUPP()
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
		
		//开户银行BANK_LIST
		List<Object> BANK_NAME_LIST = service.query_bankList();
		context.put("BANK_NAME_LIST", BANK_NAME_LIST);
		
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
		return new ReplyHtml(VM.html("rentWrite/bank_C_Manger1.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply Bank_C_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Bank_C_Page(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "申请","导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBank_C_Upload()
	{
		Map map=_getParameters();
		if(map!=null && map.get("uploadType")!=null && !map.get("uploadType").equals(""))
		{
			String uploadType=map.get("uploadType").toString();
			String bankFlag=map.get("bankFlag")!=null?map.get("bankFlag").toString():"-1";//判断选择的银行模版   1：建设银行  2：民生银行
			if(uploadType.equals("select"))//导出选中项
			{
				List list=new ArrayList();
				JSONArray guaranjsonArray = JSONArray.fromObject(map.get("selectDateHidden"));
				for (Object object : guaranjsonArray) {
					Map<String, Object> m = (Map<String, Object>) object;
					list.add(m);
				}
				
				service.updateJoinSelect(list);
				List listReturn=service.cyberBankUploadOp(list);
				
				map.put("dataList", listReturn);
				
				if(bankFlag.equals("1"))
				{
					//excel导出
					String no = new FiEbankUtil().getExportNo("租金网银");
					return new ReplyExcel(util.exportData(map),"rent"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+no+".xls");
				}
				else if(bankFlag.equals("2"))
				{
					//txt导出
					map.put("EXPORT_TYPE", "租金网银");
					map.put("FILE_HEAD", "DK");
					util.exportMSEbank(SkyEye.getResponse(),map);
					return null;
				}
			}
			else//导出所有
			{
				
				/**
				 * 导出所有提高效率处理
				 * 1.先将期初表和零时表修改未申请中状态，将期初表INVENT_STATUS状态修改成2
				 * 2.开启线程，当导出数据生成核销单后将INVENT_STATUS置为0
				 */
				//根据条件查询所需要导出的数据和修改状态
				Map mapDate=service.query_cyberBank_All(map);
				List list=(List)mapDate.get("list");
				map.put("dataList", list);
				
				service.auto(map.get("PR_ID").toString());
				if(bankFlag.equals("1"))
				{
					//excel导出
					String no = new FiEbankUtil().getExportNo("租金网银");
					return new ReplyExcel(util.exportData(map),"rent"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+no+".xls");
				}
				else if(bankFlag.equals("2"))
				{
					//txt导出
					map.put("EXPORT_TYPE", "租金网银");
					map.put("FILE_HEAD", "DK");
					util.exportMSEbank(SkyEye.getResponse(),map);
					return null;
				}
			}
		}
		return this.cyberBank_C_Manger();
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
		
		return new ReplyHtml(VM.html("rentWrite/bank_S_Manger.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "申请", "列表"})
	public Reply toAjaxData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toAjaxData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "还款记录", "租金-核销单查询"})
	public Reply toQueryMgDeduct() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));
		return new ReplyHtml(VM.html("rentWrite/query_S_Manger.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "还款记录", "租金-核销单查询"})
	public Reply toQueryAjaxData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toQueryAjaxData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销单查询"})
	public Reply getQueryFundDetailData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getFundDetail(param));
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
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map m = _getParameters();
		try {
				
			//判断选择的银行模版   1：建设银行  其他：民生银行
			if("1".equals(m.get("bankFlag")+"")){
				list = util.parseJSEbank(files.get(0));
				m.put("FI_REALITY_BANK", "中国建设银行");
			}else{
				list = util.parseMSEbank(files.get(0));
				m.put("FI_REALITY_BANK", "中国民生银行");
			}
			//对回执的数据进行处理
			service.backOP(list,m);
//			service.aa(list,m);
//			service.bb(list, m);
//			service.cc(list,m);
//			service.dd(list, m);
//			service.gg();
//			service.ee();
//			service.ff();
			msg = "数据正在核销，请稍后查看回执结果！";
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax(flag,null,msg);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划","申请","查看明细"})
	public Reply getFundDetailData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getFundDetail(param));
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
	/********************************************租金扣划-非网银-审核（租金非网银，供应商代付非网银）**********Auth=齐姜龙***********************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "核销","列表"})
	public Reply toMgAppCheckMg() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWrite/toFund_Confirm.vm", context));
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
		
		
		
		
		return new ReplyHtml(VM.html("rentWrite/to_fund_confirm_detail.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划","核销","查看明细"})
	public Reply getFundDetailDataPage(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.getFundDetailPage(param));
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
	
	
	
	
	
	/********************************************租金扣划-非网银-审核（供应商代付网银）**********Auth=齐姜龙***********************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","列表"})
	public Reply toSupperFundMg() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWrite/tocyber_Confirm.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","列表"})
	public Reply toSupperFundMgData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toSupperFundMg(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","查看明细"})
	public Reply getSupperFundData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getFundDetail(param));
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
	
	@aPermission(name = { "资金管理", "租金扣划", "供应商垫付-网银核销","重置" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply destroySuppUp()
	{
		Map map=_getParameters();
		service.destroySuppUp(map);
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
//			service.dunCommit();
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
	@aPermission(name = {"资金管理", "还款记录", "租金-核销明细"})
	public Reply fundDetail_Manger() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));//获取厂商		
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWrite/fundDetail_Manger.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "还款记录", "租金-核销明细"})
	public Reply fundDetail_MangerData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.queryFundDetailAll(param));
	}
	
	@aPermission(name = { "资金管理", "租金扣划","申请","导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply HEAD_Upload()
	{
		Map map=_getParameters();
		return new ReplyExcel(service.HEAD_Upload(map), "租金扣划明细导出").addOp(new OpLog("资金管理", "租金扣划申请导出", "导出错误"));
	}
	
	@aPermission(name = { "资金管理", "来款核销","同步应收项" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply CreateJoinFundDate()
	{
		service.CreateJoinFundDate();
		return new ReplyAjax(true,null);
	}
	
	//网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","回执结果"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBank_Result_Manger()
	{
		Map map=this._getParameters();
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWrite/resultManger.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","回执结果" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBank_Result_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Result_C_Page(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply updateLockType(){
		Map map=this._getParameters();
		service.updateLockType(map);
		return new ReplyAjax(true,null);
	}
	
	@aPermission(name = { "资金管理", "租金扣划", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply LockTypeIsF(){
		Map map=this._getParameters();
		int num=service.LockTypeIsF(map);
		return new ReplyAjax(true,num);
	}
}

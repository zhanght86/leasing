package com.pqsoft.rentWrite.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

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
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FiEbankUtil;
import com.pqsoft.util.ReplyExcel;

public class rentNotWriteAction  extends Action{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	public VelocityContext context=new VelocityContext();
	private static DataDictionaryService dataDictionaryService = new DataDictionaryService();
	rentWriteService service=new rentWriteService();
	private FiEbankUtil util = new FiEbankUtil();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "申请","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBankNot_C_Manger()
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
		
		//判断是否为供应商，供应商（厂商）则不能修改金额提交
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			return new ReplyHtml(VM.html("rentWrite/cyberBankNot_C_Manger.vm", context));
		}
		
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			return new ReplyHtml(VM.html("rentWrite/cyberBankNot_C_Manger.vm", context));
		}
		
		return new ReplyHtml(VM.html("rentWrite/cyberBankNot_ADMIN_C_Manger.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "申请","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBankNot_C_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_cyberBankNot_C_Page(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "申请","提交" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_Not_C_Submit()
	{
		Map map=this._getParameters();
		//保存选中的数据插入到结算单和明细中
		service.bank_Not_C_Submit(map);
		
		return this.cyberBankNot_C_Manger();
	}
	
	//网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "导出","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberCreateNot_Manger()
	{
		Map map=this._getParameters();
		//客户类型CUST_TYPE_LIST
		List<Object> CUST_TYPE_LIST = (List)new DataDictionaryMemcached().get("客户类型");
		context.put("CUST_TYPE_LIST", CUST_TYPE_LIST);
		
		//开户银行BANK_LIST
		List<Object> BANK_NAME_LIST = service.query_bankList();
		context.put("BANK_NAME_LIST", BANK_NAME_LIST);
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		map.put("USERCODE", Security.getUser().getCode());//获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());//获取登陆人
		context.put("param", map);
		
		return new ReplyHtml(VM.html("rentWrite/cyberBankCreateNot.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "导出","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberCreateNot_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.cyberCreateNot_PageAjax(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "导出","导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberBankNot_C_Upload()
	{
		Map map=_getParameters();
		if(map!=null && map.get("uploadType")!=null && !map.get("uploadType").equals(""))
		{
			String uploadType=map.get("uploadType").toString();
			String bankFlag=map.get("bankFlag")!=null?map.get("bankFlag").toString():"-1";//判断选择的银行模版   1：建设银行  2：民生银行
			String FI_REALITY_BANK="";
			if(bankFlag.equals("1"))
			{
				FI_REALITY_BANK="中国建设银行";
			}
			else if(bankFlag.equals("2"))
			{
				FI_REALITY_BANK="中国民生银行";
			}
			if(uploadType.equals("select"))//导出选中项
			{
				List list=new ArrayList();
				JSONArray guaranjsonArray = JSONArray.fromObject(map.get("selectDateHidden"));
				for (Object object : guaranjsonArray) {
					Map<String, Object> m = (Map<String, Object>) object;
					list.add(m);
				}
				
				List listDate=service.query_Not_list(list);
				List listReturn=service.queryNotOne_Upload(listDate,FI_REALITY_BANK);
				
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
				List listDate=service.query_All_list(map);
				List listReturn=service.queryNotOne_Upload(listDate,FI_REALITY_BANK);
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
		}
		return this.cyberCreateNot_Manger();
	}
	
	//网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "核销","列表"  })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberConfirmNot_Manger()
	{
		Map map=this._getParameters();
		//客户类型CUST_TYPE_LIST
		List<Object> CUST_TYPE_LIST = (List)new DataDictionaryMemcached().get("客户类型");
		context.put("CUST_TYPE_LIST", CUST_TYPE_LIST);
		
		//开户银行BANK_LIST
		List<Object> BANK_NAME_LIST = service.query_bankList();
		context.put("BANK_NAME_LIST", BANK_NAME_LIST);
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetProduct", Fundservice.toGetProduct(map));//获取租赁物类型
		
		map.put("USERCODE", Security.getUser().getCode());//获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());//获取登陆人
		context.put("param", map);
		
		return new ReplyHtml(VM.html("rentWrite/cyberBankConfirmNot.vm", context));
	}
	
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "核销","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply cyberConfirmNot_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.cyberConfirmNot_PageAjax(param);
		return new ReplyAjaxPage(page);
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "核销", "重置"})
	public Reply rollBackNotAll()
	{
		Map map=this._getParameters();
		service.rollBackNotAll(map);
		return this.cyberConfirmNot_Manger();
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划-网银-不足额", "核销", "重置"})
	public Reply cyberBankRollNot()
	{
		Map map=this._getParameters();
		if(map!=null && map.get("IDS")!=null && !map.get("IDS").equals(""))
		{
			String IDS="";
			IDS=map.get("IDS").toString();
			Map mapID=new HashMap();
			mapID.put("IDS", IDS);
			service.rollBackNot(mapID);
		}
		
		return this.cyberConfirmNot_Manger();
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "租金扣划-网银", "核销","上传回执" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply uploadNotExcel(){
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
			service.backNotOP(list,m);
			msg = "数据正在核销，请稍后查看回执结果！";
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax(flag,null,msg);
	}
}

package com.pqsoft.fundEbank.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.fundEbank.service.FundEbankService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FiEbankUtil;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.SecuUtil;


/**
 * <p>
 * Title: 融资租赁信息系统平台 资金管理 首付款网银
 * </p>
 * <p>
 * Description: 首付款网银 申请 核销  查询
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * 
 * @author 吴剑东 wujd@pqsoft.cn
 * @version 1.0
 */
public class FundEbankAction extends Action {
	private FundEbankService service = new FundEbankService();
	private FiEbankUtil util = new FiEbankUtil();
	Map<String,Object> m = null;
	
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "首付款网银","首付款网银-申请" ,"列表" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@Override
	public Reply execute() {
		return new ReplyHtml(VM.html("fundEbank/fundEbankApply.vm", null));
	}
	
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "首付款网银", "首付款网银-申请" ,"数据"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply refreshAccounts(){
		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		
		m.put("DATA_TYPE", "客户类型");
		//首付款申请状态 未申请
		m.put("FIRST_APP_STATE", "0,5,6");
		m.putAll(json);
		return new ReplyAjaxPage(service.getAccountsData(m));
	}
	
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "首付款网银", "首付款网银-申请" ,"提交"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply exportExcel(){
		m = _getParameters();
		//加入操作人信息
		SecuUtil.putUserInfo(m);
		m.put("EXPORT_TYPE", "首付款网银");
		m.put("DATA_TYPE", "客户类型");
		m.put("FILE_HEAD", "DK");
		//作为查询条件使用
		m.put("FIRST_APP_STATE", "0,5,6");
		List<Map<String, Object>> list = service.getFirstPaymentByPaycodes(m);
		m.put("dataList", list);
		
		//1.维护资金主表、资金明细、到账明细  2.修改项目主表首付款状态为1:已申请
		service.saveFundEbankDetail(m);
		m.put("FIRST_APP_STATE", "1");
		service.updateStatusByProIds(m);
		
		
		//判断选择的银行模版   1：建设银行  其他：民生银行
		if("1".equals(m.get("bankFlag").toString())){
			String no = new FiEbankUtil().getExportNo("首付款网银");
			return new ReplyExcel(util.exportData(m),"headpayment"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+"-"+no+".xls");
		}else{
			util.exportMSEbank(SkyEye.getResponse(),m);
			return null;
		}
	}


	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "首付款网银", "首付款网银-核销" ,"列表"  })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply getAccountsConfirm(){
		return new ReplyHtml(VM.html("fundEbank/fundEbankConfirm.vm", null));
	}
	

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "首付款网银", "首付款网银-核销" ,"数据" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply refreshAccountsConfirm(){
		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.put("DATA_TYPE", "客户类型");
		m.put("FIRST_APP_STATE", "1");
		m.putAll(json);
		
		return new ReplyAjaxPage(service.getAccountsData(m));
	}


	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "首付款网银", "首付款网银-核销" ,"核销" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply uploadExcel(){
		boolean flag = false;
		String msg = "";
		List<File> files = _getFile();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		m = _getParameters();
		//加入操作人信息
		SecuUtil.putUserInfo(m);
		
		try {
				
			//判断选择的银行模版   1：建设银行  其他：民生银行
			if("1".equals(m.get("bankFlag")+"")){
				list = util.parseJSEbank(files.get(0));
			}else{
				list = util.parseMSEbank(files.get(0));
			}
			
			//根据回执数据核销首付款网银数据
			int succ_count = service.doFundEbankConfirmData(list,m);
			
			if(list.size() > 0){
				msg = "核销【"+list.size()+"】条！扣款成功【"+succ_count+"】条！";
				flag = true;
			}else{
				msg = "核销失败,请检查上传文件格式！";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax(flag,null,msg);
	}


	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "首付款网银", "首付款网银-核销" ,"驳回"  })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply resetSignAccount(){
		boolean flag = true;
		String msg = "";
		Object data = null;
		m = _getParameters();
//		m.put("sqlData", m.get("sqlData").toString());
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		m.put("FIRST_APP_STATE", "0");
		//删除资金明细数据
		service.deleteFundDetailByProIds(m);
		//删除资金主表数据
		service.deleteFundHeadByProIds(m);
		
		int count = service.updateStatusByProIds(m);
		
		if(count>0){
			msg = "重置成功" + count + "条";
		}else{
			msg = "重置失败";
		}
		return new ReplyAjax(flag,data,msg);
	}


	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "首付款网银", "首付款网银-查询" ,"列表"  })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply search(){
		return new ReplyHtml(VM.html("fundEbank/fundEbankSearch.vm", null));
	}


	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "首付款网银", "首付款网银-查询" ,"数据" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply refreshSearch(){
		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.put("DATA_TYPE", "客户类型");
		m.putAll(json);
		//首付款申请状态 已申请
		if(m.get("FIRST_APP_STATE") == null || "".equals(m.get("FIRST_APP_STATE")+"")){
			m.put("FIRST_APP_STATE", "0,1,2,3,6");
		}
		
		return new ReplyAjaxPage(service.getAccountsData(m));
	}
	


}

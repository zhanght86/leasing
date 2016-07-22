package com.pqsoft.refund.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.capitalPool.supplierPool.service.SupplierBailPoolService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.refund.service.RefundDBApplyService;
import com.pqsoft.refund.service.RefundDBHeXiaoService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.StringUtils;

public class RefundDBApplyAction extends Action {
    private String path = "refund/";
    private RefundDBApplyService service = new RefundDBApplyService();
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","添加DB退款列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		List<Object> busPlate = (List<Object>)new DataDictionaryMemcached().get("PDF模版所属商务板块");
		context.put("busPlate", busPlate);
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"RefundDBApplyPage.vm", context));
	}
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","添加DB退款列表查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getRefdundApplyPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getPageData(param);
		return new ReplyAjaxPage(pageData);
	}

    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","添加DB下拉列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getRefundDropList(){
    	Map<String,Object> param = _getParameters();
		List<Object> bailList = service.getCanRefundList(param);
		return new ReplyJson(JSONArray.fromObject(bailList));
    }
    
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","新建退款单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply refundApply(){
		SupplierBailPoolService poolService = new SupplierBailPoolService();
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//查询供应商相关账号信息
		Map<String,Object> supMess = poolService.getSupRefundMess(param);
		//保存退款申请单
		Map<String,Object> refundDan = new HashMap<String, Object>();
		String re_code = supMess.get("SUP_SHORTNAME").toString()+"-"+param.get("PAY_TIME").toString();
		String re_id = poolService.getRefundDanSeq();
		refundDan.put("RE_ID", re_id);
		refundDan.put("RE_CODE", re_code);
		refundDan.put("RE_DATE", param.get("PAY_TIME").toString());
		refundDan.put("RE_MONEY", param.get("REFUND_MONEY").toString());
		refundDan.put("RE_PROJECT_COUNT", param.get("PROJECT_COUNT").toString());
		refundDan.put("RE_TYPE", param.containsKey("RE_TYPE")&&param.get("RE_TYPE")!=null?param.get("RE_TYPE").toString():"1");
		refundDan.put("RE_APPLY_NAME", Security.getUser().getName());
		refundDan.put("RE_APPLY_TIME", sdf.format(new Date()));
		refundDan.put("RE_PAYEE_TYPE", "1");
		refundDan.put("CUST_ID", param.get("SUP_ID").toString());
		refundDan.put("RE_PAYEE_NAME", supMess.containsKey("SUP_NAME") && supMess.get("SUP_NAME") !=null ? supMess.get("SUP_NAME").toString():"");
		refundDan.put("RE_PAYEE_UNIT", supMess.containsKey("LOANS_OWN_UNIT") && supMess.get("LOANS_OWN_UNIT") !=null ? supMess.get("LOANS_OWN_UNIT").toString():"");
		refundDan.put("RE_PAYEE_ADDRESS", supMess.containsKey("SUP_ADDRESS") && supMess.get("SUP_ADDRESS") !=null ? supMess.get("SUP_ADDRESS").toString():"");
		refundDan.put("RE_PAYEE_BANK", supMess.containsKey("LOANS_OWN_BANK") && supMess.get("LOANS_OWN_BANK") !=null ? supMess.get("LOANS_OWN_BANK").toString():"");
		refundDan.put("RE_PAYEE_ACCOUNT", supMess.containsKey("LOANS_OWN_ACCOUNT") && supMess.get("LOANS_OWN_ACCOUNT") !=null ? supMess.get("LOANS_OWN_ACCOUNT").toString():"");
		refundDan.put("RE_PAYEE_BANK_ADDR", supMess.containsKey("LOANS_OWN_ADDR") && supMess.get("LOANS_OWN_ADDR") !=null ? supMess.get("LOANS_OWN_ADDR").toString():"");
		refundDan.put("RE_STATUS", "0");
		int result = poolService.addRefundDan(refundDan);
		//将退款单号回更到DB保证金记录上 并将DB保证金改为已退款状态
		param.put("STATUS", "2");
		param.put("RE_ID", re_id);
		String POOL_ID_ITEMS = param.containsKey("POOL_ID_ITEMS") && param.get("POOL_ID_ITEMS") !=null ? param.get("POOL_ID_ITEMS").toString():"";
		String [] POOL_IDS = POOL_ID_ITEMS.split(",");
		param.put("POOL_ID", POOL_IDS);
		poolService.updateSupDbRefundId(param);
		if(result > 0 ){
			return new ReplyAjax(true, "操作成功!");
		}else{
		    return new ReplyAjax(false,"操作失败!"); 
		}
	}
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","导出退款明细"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply exportExcelDetail(){
    	Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Excel excel = new Excel();
		String titleName = "";
		if(param.containsKey("RE_TYPE") && param.get("RE_TYPE") !=null && "2".equals(param.get("RE_TYPE").toString())){
			titleName="客户保证金退款明细表";
		}else{
			titleName = "供应商保证金退款明细表";
		}
		param.put("titleName", titleName);
		String HeadName = sdf.format(new Date())+titleName;
		excel = service.getExportDBDetail(param);
	    String fileName = HeadName+".xls";
		return new ReplyExcel(excel,fileName);
    }
	    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","导出Excel"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply exportExcelData(){
    	Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Excel excel = new Excel();
		String titleName = "";
		if(param.containsKey("RE_TYPE") && param.get("RE_TYPE") !=null && "2".equals(param.get("RE_TYPE").toString())){
			titleName="客户保证金退款表";
		}else{
			titleName = "供应商保证金退款表";
		}
		param.put("titleName", titleName);
		String HeadName = sdf.format(new Date())+ titleName;
		//excel = service.getExcelExportRefundData(param);
		excel = service.getExportSumMess(param);
	    String fileName = HeadName+".xls";
		return new ReplyExcel(excel,fileName);
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply showApplyDBRefund(){
    	VelocityContext context = new VelocityContext();
    	Map<String,Object> param = _getParameters();
    	param.put("RE_APPLY_NAME", Security.getUser().getName());
    	List<Object> busPlate = (List<Object>)new DataDictionaryMemcached().get("PDF模版所属商务板块");
		context.put("busPlate", busPlate);
    	context.put("param", param);
    	return new ReplyHtml(VM.html(path+"vatDebtApplyPage.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getRefundPageData(){
    	Map<String,Object> param = _getParameters();
    	param.put("RE_APPLY_NAME", Security.getUser().getName());
    	Page pageData = service.getRefundPageData(param);
    	return new ReplyAjaxPage(pageData);
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款","下拉详细"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getDebtDropDownList(){
    	Map<String,Object> param = _getParameters();
    	List<Object> bailList = service.getRefundDropList(param);
		return new ReplyJson(JSONArray.fromObject(bailList));
    }
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款审核","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getPlanAuditPage(){
    	Map<String,Object> param = _getParameters();
    	VelocityContext context = new VelocityContext();
    	List<Object> busPlate = (List<Object>)new DataDictionaryMemcached().get("PDF模版所属商务板块");
		context.put("busPlate", busPlate);
    	context.put("param", param);
    	return new ReplyHtml(VM.html(path+"vatPlanAuditPage.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款审核","查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getPlanPageData(){
    	Map<String,Object> param = _getParameters();
    	Page pageData = service.getPlanAuditPageData(param); 	
    	return new ReplyAjaxPage(pageData);
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款查询","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getRefundSearch(){
    	VelocityContext context = new VelocityContext();
    	Map<String,Object> param = _getParameters();
    	List<Object> busPlate = (List<Object>)new DataDictionaryMemcached().get("PDF模版所属商务板块");
    	List<Object> refundStatus = (List<Object>)new DataDictionaryMemcached().get("退款单状态");
		context.put("busPlate", busPlate);
		context.put("refundStatus", refundStatus);
    	context.put("param", param);
    	return new ReplyHtml(VM.html(path+"RefundSearchPage.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款查询","查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getSearchPageData(){
    	Map<String,Object> param = _getParameters();
    	Page pagedata = service.getSearchPageData(param);
    	return new ReplyAjaxPage(pagedata);
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款查询","导出"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply exportSearchRefundMess(){
    	Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Excel excel = new Excel();
		String HeadName = sdf.format(new Date())+ "保证金退款表";
		excel = service.getSearchRefundMess(param);
	    String fileName = HeadName+".xls";
    	return new ReplyExcel(excel,fileName);
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","提交"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply applySubmit(){
    	Map<String,Object> param = _getParameters();
    	//发起评审流程ID更入申请单中
    	String msg = ""; 
    	Boolean flag = true;
    	List<String> user = Security.getUser().getRoles();
    	String creditDeploymentName = "";
    	if(user.get(0).indexOf("企划") != -1){ 
    		creditDeploymentName = "保证金退款流程";
    	}else{
    		creditDeploymentName = "不足额退款流程";
    	} 
    	Map<String,Object> prcessParam = new HashMap<String, Object>();
    	prcessParam.put("RE_IDS", StringUtils.nullToString(param.get("sqlSubmitData")));
		prcessParam.put("APPLY_TYPE", param.get("APPLY_TYPE"));
		prcessParam.put("USER_ID", Security.getUser().getId());
		
    	List<String> prcessList = JBPM.getDeploymentListByModelName(creditDeploymentName);
		if(prcessList.size() > 0){
			String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),"","","",prcessParam).getId(); 
			//跟新流程id
			msg +=" 评审流程："+jbpm_id+"已发起！";
			JSONArray jsonList = JSONArray.fromObject(param.get("sqlSubmitData"));
			for (Object obj : jsonList) {
				Map<String,Object> objMess = (Map<String,Object>)obj;
				objMess.put("JBPM_ID", jbpm_id);
	    		RefundDBHeXiaoService dbService = new RefundDBHeXiaoService();
	    	    dbService.updateRefundDan(objMess);
			}
		}else{
			flag = false;
			msg = "未找到流程："+creditDeploymentName;
			throw new ActionException("未找到流程："+creditDeploymentName);
		}
    	return new ReplyAjax(flag,msg);
    }
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","other提交"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply submitMethod(){
    	Map<String,Object> param = _getParameters();
    	JSONArray jsonMess = JSONArray.fromObject(param.get("sqlSubmitData").toString());
    	int result = 0;
    	for (Object obj : jsonMess) {
			Map<String,Object> objMess = (Map<String,Object>)obj;
			if(objMess.containsKey("RE_TYPE") && objMess.get("RE_TYPE") != null && "保证金退款".equals(objMess.get("RE_TYPE").toString())){
				objMess.put("RE_STATUS", "1");
			} 
			result += service.submitMethod(objMess);
		}
    	 
    	if(result >0){
    		return new ReplyAjax(false,"操作成功！");
    	}else{
    		return new ReplyAjax(true ,"操作失败");
    	}
    	
    }
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","撤销退款"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply delmainMethod(){
    	Map<String,Object> param = _getParameters();
    	SupplierBailPoolService poolService = new SupplierBailPoolService();
    	JSONArray jsonArray = JSONArray.fromObject(param.get("sqldelData").toString());
    	int result = 0;
    	for (Object obj : jsonArray) {
			Map<String,Object> newParam = (Map<String,Object>)obj;
			newParam.put("STATUS2", "2");
			List<Object> dbBails = service.getPadSinkList(newParam);
			if(dbBails.size() != 0){
				String POOL_ID = "";
				for (Object dbBail : dbBails) {
					Map<String,Object> bail = (Map<String,Object>)dbBail;
					POOL_ID +=  bail.get("POOL_ID").toString() + ",";
				}
				POOL_ID = POOL_ID.substring(0, POOL_ID.lastIndexOf(","));
				String [] POOL_IDS = POOL_ID.split(",");
				Map<String,Object> reFund = new HashMap<String, Object>();
				reFund.put("POOL_ID", POOL_IDS);
				reFund.put("RE_ID", "");
				reFund.put("STATUS", "0");
				poolService.updateSupDbRefundId(reFund);
			}
		    result += poolService.delRefundMess(newParam);
		}
    	if(result > 0){
    		return new ReplyAjax(true, "操作成功！");
    	}else{
    		return new ReplyAjax(false, "操作失败！");
    	}
    }
     
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","[退款]移除退款单部分款项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply removeMoney(){
		Map<String,Object> param = _getParameters();
		SupplierBailPoolService poolService = new SupplierBailPoolService();
		//当退款单中最后一笔钱移除时删掉退款单
		Map<String,Object> reFund = new HashMap<String, Object>();
		String[] POOL_ID = param.get("POOL_ID").toString().split(",");
		reFund.put("POOL_ID", POOL_ID);
		reFund.put("RE_ID", "");
		reFund.put("STATUS", "0");
		int result = poolService.updateSupDbRefundId(reFund);
		//查询退款单对应的DB保证金中是否存在数据
		param.put("STATUS2", "2");
		List<Object> dbBails = service.getPadSinkList(param);
		if(dbBails.size() == 0){
			//删除对应的退款单数据
			poolService.delRefundMess(param);
		}else{
			//更新退款单的信息
			Double lastMoney = poolService.getRefundMoney(param);
			param.put("RE_MONEY", lastMoney);
			param.put("RE_PROJECT_COUNT", dbBails.size());
			poolService.updateRefundMess(param);
		}
		if(result >0){
			return new ReplyAjax(true,"操作成功!");
		}else{
			return new ReplyAjax(false,"操作失败!");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","[退款]删除退款单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delRefundDan(){
		Map<String,Object> param = _getParameters();
		SupplierBailPoolService poolService = new SupplierBailPoolService();
		//查询退款单对应的DB保证金记录
		param.put("STATUS2", "2");
		List<Object> dbBails = service.getRefundDropList(param);
		String POOL_IDS = "";
		if(dbBails.size() > 0 ){
			for (Object dbBail : dbBails) {
				Map<String,Object> bail = (Map<String,Object>)dbBail;
				POOL_IDS +=  bail.get("POOL_ID").toString() + ",";
			}
			POOL_IDS = POOL_IDS.substring(0, POOL_IDS.lastIndexOf(","));
			String [] POOL_ID = POOL_IDS.split(",");
			Map<String,Object> reFund = new HashMap<String, Object>();
			reFund.put("POOL_ID", POOL_ID);
			reFund.put("RE_ID", "");
			reFund.put("STATUS", "0");
			poolService.updateSupDbRefundId(reFund);
		}	
	    int result = poolService.delRefundMess(param);
	    if(result >0){
		    return new ReplyAjax(true,"操作成功!");
	    }else{
	    	return new ReplyAjax(false,"操作失败!");
	    }
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款审核","解冻退款单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply revRefundApp(){
		Map<String,Object> param = _getParameters();
		SupplierBailPoolService poolService = new SupplierBailPoolService();
		List<Object> dbBails = service.getRefundDropList(param);
		if(dbBails.size()>0){
			String POOL_IDS = "";
			for (Object dbBail : dbBails) {
				Map<String,Object> bail = (Map<String,Object>)dbBail;
				POOL_IDS +=  bail.get("POOL_ID").toString() + ",";
			}
			POOL_IDS = POOL_IDS.substring(0, POOL_IDS.lastIndexOf(","));
			String [] POOL_ID = POOL_IDS.split(",");
			Map<String,Object> reFund = new HashMap<String, Object>();
			reFund.put("POOL_ID", POOL_ID);
			reFund.put("RE_ID", param.get("RE_ID").toString());
			reFund.put("THAW_TIME", new Date());
			reFund.put("STATUS", "1");
			poolService.updateSupDbRefundId(reFund);
		}
	    int result = service.submitMethod(param);
	    if(result >0){
		    return new ReplyAjax(true,"操作成功!");
	    }else{
	    	return new ReplyAjax(false,"操作失败!");
	    }
	}
	
}

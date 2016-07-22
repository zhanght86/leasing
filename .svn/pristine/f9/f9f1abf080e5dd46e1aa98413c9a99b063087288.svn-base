package com.pqsoft.capitalPool.supplierPool.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.capitalPool.supplierPool.service.EqPayBalancePoolService;
import com.pqsoft.capitalPool.supplierPool.service.SupplierBailPoolService;
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

public class EqPayBalancePoolAction extends Action {
    private String path = "capitalPool/supplierPool/";
    private EqPayBalancePoolService service = new EqPayBalancePoolService();
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","主页面"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"supEqPayPoolMain.vm", context));
	}

    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","可用余款页面-列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showEqPayBalancePage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"supEqPayBalancePage.vm", context));
	}
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","可用余款页面-查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getEqPayBalancePageData(){
    	Map<String,Object> param = _getParameters();
		Page pagedata = service.getBalancePageData(param);
    	return new ReplyAjaxPage(pagedata);
    }
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","退款信息页面-列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showEqPayRefundPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"supEqPayRefundPage.vm", context));
	}
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","退款信息页面-查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getEqPayRefundPageData(){
    	Map<String,Object> param = _getParameters();
		Page pagedata = service.getRefundPageData(param);
    	return new ReplyAjaxPage(pagedata);
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","冲抵信息页面-列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply showEqPayOffsetPage(){
        VelocityContext context = new VelocityContext();
        Map<String,Object> param = _getParameters();
		context.put("param", param);
        return new ReplyHtml(VM.html(path+"supEqPayOffsetPage.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","冲抵信息页面-查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getEqPayOffsetPageData(){
    	Map<String,Object> param = _getParameters();
		Page pagedata = service.getOffsetPageData(param);
    	return new ReplyAjaxPage(pagedata);
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","设备付款余款池信息"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getEqPayPoolList(){
    	Map<String,Object> param = _getParameters();
    	List<Object> poolList = service.getEqPayPoolList(param);
    	return new ReplyJson(JSONArray.fromObject(poolList));  	
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","保存退款单信息"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply refundApply(){
    	Map<String,Object> param = _getParameters();
		SupplierBailPoolService poolService = new SupplierBailPoolService();
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
		refundDan.put("RE_TYPE", "4");
		refundDan.put("RE_APPLY_NAME", Security.getUser().getName());
		refundDan.put("RE_APPLY_TIME", sdf.format(new Date()));
		refundDan.put("RE_PAYEE_TYPE", "1");
		refundDan.put("CUST_ID", param.get("SUP_ID").toString());
		refundDan.put("RE_PAYEE_NAME", supMess.containsKey("SUP_NAME") && supMess.get("SUP_NAME") !=null ? supMess.get("SUP_NAME").toString():"");
		refundDan.put("RE_PAYEE_UNIT", supMess.containsKey("SUP_NAME") && supMess.get("SUP_NAME") !=null ? supMess.get("SUP_NAME").toString():"");
		refundDan.put("RE_PAYEE_ADDRESS", supMess.containsKey("SUP_ADDRESS") && supMess.get("SUP_ADDRESS") !=null ? supMess.get("SUP_ADDRESS").toString():"");
		refundDan.put("RE_PAYEE_BANK", supMess.containsKey("OPEN_BANK") && supMess.get("OPEN_BANK") !=null ? supMess.get("OPEN_BANK").toString():"");
		refundDan.put("RE_PAYEE_ACCOUNT", supMess.containsKey("OPEN_BANK_NUMBER") && supMess.get("OPEN_BANK_NUMBER") !=null ? supMess.get("OPEN_BANK_NUMBER").toString():"");
		refundDan.put("RE_PAYEE_BANK_ADDR", supMess.containsKey("OPEN_BANK_ADDR") && supMess.get("OPEN_BANK_ADDR") !=null ? supMess.get("OPEN_BANK_ADDR").toString():"");
		refundDan.put("RE_STATUS", "0");
		int result = poolService.addRefundDan(refundDan);
		//将退款单号回更到DB保证金记录上 并将DB保证金改为已退款状态
		param.put("STATUS", "2");
		param.put("RE_ID", re_id);
		param.put("POOL_ID", param.get("POOL_ID_ITEMS").toString());
		poolService.updateSupDbRefundId(param);
		if(result > 0 ){
			return new ReplyAjax(true, "操作成功!");
		}else{
		    return new ReplyAjax(false,"操作失败!"); 
		}
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","资金解冻/资金冻结"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply freezeOrThaw(){
    	Map<String,Object> param = _getParameters();
		SupplierBailPoolService poolService = new SupplierBailPoolService();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if("1".equals(param.get("STATUS").toString().trim())){
			param.put("THAW_TIME", sdf.format(new Date()));
		}
		int result = poolService.updateFundsPool(param);
		if(result >0 ){
			return new ReplyAjax(true, "操作成功!");
		}else{
		    return new ReplyAjax(false,"操作失败!");
		}
    }
    
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","移除退款单相关金额"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply removeMoney(){
		Map<String,Object> param = _getParameters();
		SupplierBailPoolService poolService = new SupplierBailPoolService();
		//当退款单中最后一笔钱移除时删掉退款单
		Map<String,Object> reFund = new HashMap<String, Object>();
		reFund.put("POOL_ID", param.get("POOL_ID").toString());
		reFund.put("RE_ID", "");
		reFund.put("STATUS", "0");
		int result = poolService.updateSupDbRefundId(reFund);
		//查询退款单对应的DB保证金中是否存在数据
		param.put("STATUS2", "2");
		List<Object> dbBails = service.getEqPayPoolList(param);
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
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","删除退款单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply delRefundDan(){
		Map<String,Object> param = _getParameters();
		SupplierBailPoolService poolService = new SupplierBailPoolService();
		//查询退款单对应的DB保证金记录
		param.put("STATUS2", "2");
		List<Object> dbBails = service.getEqPayPoolList(param);
		String POOL_IDS = "";
		for (Object dbBail : dbBails) {
			Map<String,Object> bail = (Map<String,Object>)dbBail;
			POOL_IDS +=  bail.get("POOL_ID").toString() + ",";
		}
		POOL_IDS = POOL_IDS.substring(0, POOL_IDS.indexOf(","));
		Map<String,Object> reFund = new HashMap<String, Object>();
		String [] POOL_ID = POOL_IDS.split(",");
		reFund.put("POOL_ID", POOL_ID);
		reFund.put("RE_ID", "");
		reFund.put("STATUS", "0");
		poolService.updateSupDbRefundId(reFund);
	    int result = poolService.delRefundMess(param);
	    if(result >0){
		    return new ReplyAjax(true,"操作成功!");
	    }else{
	    	return new ReplyAjax(false,"操作失败!");
	    }
	}
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","设备放款余款池","设备付款余款池发起退款流程"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply startRefundApp(){
		Map<String,Object> param = _getParameters();
		SupplierBailPoolService poolService = new SupplierBailPoolService();
		String msg = "" ;
		Boolean flag = true; 
		int result = 0;
		//发起退款评审流程
		List<String> prcessList = JBPM.getDeploymentListByModelName("资金池退款审核流程");
		if(prcessList.size() > 0){
			String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),"","","",param).getId(); 
			msg += jbpm_id+"已发起！";
    		param.put("JBPM_ID", jbpm_id);
    		//变更退款单状态
    		param.put("RE_STATUS", "1");
    		result = poolService.updateRefundMess(param);
		}else{
			flag = false;
			msg = "未找到流程";
			throw new ActionException("未找到流程");
		}
		if(result >0){
			flag = true;
			msg +="操作成功!";
		}else{
			flag = false;
			msg +="操作失败!";
		}
		return new ReplyAjax(flag,msg);
	}
}

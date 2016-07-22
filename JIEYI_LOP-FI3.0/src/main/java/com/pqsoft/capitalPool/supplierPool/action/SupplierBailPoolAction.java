package com.pqsoft.capitalPool.supplierPool.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.capitalPool.supplierPool.service.SupplierBailPoolService;
import com.pqsoft.entity.Excel;
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

public class SupplierBailPoolAction extends Action {
    private String path = "capitalPool/supplierPool/";
    private SupplierBailPoolService service = new SupplierBailPoolService();
	
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","主页面"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply mainPageShow(){
    	VelocityContext context = new VelocityContext();
    	Map<String,Object> param = _getParameters();
    	context.put("param", param);
    	return new ReplyHtml(VM.html(path+"supDbBailMain.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","可用余额管理","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"supBailPoolManager.vm", context));
	}
    
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","可用余额管理","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getPagedata(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getDBPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金池管理","供应商保证金池管理","可用余额管理","DB保证金退款申请"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply refundApply(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//查询供应商相关账号信息
		Map<String,Object> supMess = service.getSupRefundMess(param);
		//保存退款申请单
		Map<String,Object> refundDan = new HashMap<String, Object>();
		String re_code = supMess.get("SUP_SHORTNAME").toString()+"-"+param.get("PAY_TIME").toString();
		String re_id = service.getRefundDanSeq();
		refundDan.put("RE_ID", re_id);
		refundDan.put("RE_CODE", re_code);
		refundDan.put("RE_DATE", param.get("PAY_TIME").toString());
		refundDan.put("RE_MONEY", param.get("REFUND_MONEY").toString());
		refundDan.put("RE_PROJECT_COUNT", param.get("PROJECT_COUNT").toString());
		refundDan.put("RE_TYPE", "1");
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
		int result = service.addRefundDan(refundDan);
		//将退款单号回更到DB保证金记录上 并将DB保证金改为已退款状态
		param.put("STATUS", "2");
		param.put("RE_ID", re_id);
		param.put("POOL_ID", param.get("POOL_ID_ITEMS").toString());
		service.updateSupDbRefundId(param);
		if(result > 0 ){
			return new ReplyAjax(true, "操作成功!");
		}else{
		    return new ReplyAjax(false,"操作失败!"); 
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","可用余额管理","资金解冻/资金冻结"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply freezeOrThaw(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if("1".equals(param.get("STATUS").toString().trim())){
			param.put("THAW_TIME", sdf.format(new Date()));
		}
		int result = service.updateFundsPool(param);
		if(result >0 ){
			return new ReplyAjax(true, "操作成功!");
		}else{
		    return new ReplyAjax(false,"操作失败!");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","可用余额管理","明细"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getDbBailList(){
		Map<String,Object> param = _getParameters();
		List<Object> bailList = service.getdbBailList(param);
		return new ReplyJson(JSONArray.fromObject(bailList));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","DB退款明细","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showRefundList(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"supDbBailRefundManager.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","DB退款明细","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getRefundPagedata(){
		Map<String,Object> param = _getParameters();
		Page page = service.getDbRefundDan(param);
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","DB退款明细","导出"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply exportRefundMess(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Excel excel = new Excel();
		String HeadName = sdf.format(new Date())+"DB保证金退款表";
		String DetailHead = sdf.format(new Date())+"DB保证金退款项目明细";
		param.put("HEAD_TITLE", HeadName);
		param.put("DETAIL_HEAD", DetailHead);
		param.put("MAIN_SHEET", "退款表");
		param.put("DETAIL_SHEET", "退款明细");
		String type = param.containsKey("type") && param.get("TYPE") != null ? param.get("type").toString() :"";
		excel = service.getExcelExportRefundData(param,type );
		
	    String fileName = sdf.format(new Date())+HeadName+".xls";
		return new ReplyExcel(excel,fileName);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金池管理","供应商保证金池管理","DB保证金发起退款流程"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply startRefundApp(){
		Map<String,Object> param = _getParameters();
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
    		result = service.updateRefundMess(param);
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
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","DB退款明细","移除退款单部分款项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply removeMoney(){
		Map<String,Object> param = _getParameters();
		//当退款单中最后一笔钱移除时删掉退款单
		Map<String,Object> reFund = new HashMap<String, Object>();
		reFund.put("POOL_ID", param.get("POOL_ID").toString());
		reFund.put("RE_ID", "");
		reFund.put("STATUS", "0");
		int result = service.updateSupDbRefundId(reFund);
		//查询退款单对应的DB保证金中是否存在数据
		param.put("STATUS2", "2");
		List<Object> dbBails = service.getdbBailList(param);
		if(dbBails.size() == 0){
			//删除对应的退款单数据
			service.delRefundMess(param);
		}else{
			//更新退款单的信息
			Double lastMoney = service.getRefundMoney(param);
			param.put("RE_MONEY", lastMoney);
			param.put("RE_PROJECT_COUNT", dbBails.size());
			service.updateRefundMess(param);
		}
		if(result >0){
			return new ReplyAjax(true,"操作成功!");
		}else{
			return new ReplyAjax(false,"操作失败!");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","DB退款明细","删除"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delRefundDan(){
		Map<String,Object> param = _getParameters();
		//查询退款单对应的DB保证金记录
		param.put("STATUS2", "2");
		List<Object> dbBails = service.getdbBailList(param);
		String POOL_IDS = "";
		for (Object dbBail : dbBails) {
			Map<String,Object> bail = (Map<String,Object>)dbBail;
			POOL_IDS +=  bail.get("POOL_ID").toString() + ",";
		}
		POOL_IDS = POOL_IDS.substring(0, POOL_IDS.indexOf(","));
		String [] POOL_ID = POOL_IDS.split(",");
		Map<String,Object> reFund = new HashMap<String, Object>();
		reFund.put("POOL_ID", POOL_ID);
		reFund.put("RE_ID", "");
		reFund.put("STATUS", "0");
		service.updateSupDbRefundId(reFund);
	    int result = service.delRefundMess(param);
	    if(result >0){
		    return new ReplyAjax(true,"操作成功!");
	    }else{
	    	return new ReplyAjax(false,"操作失败!");
	    }
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","DB保证金冲抵租金","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showDBOffsetPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
        return new ReplyHtml(VM.html(path+"supDbOffsetManager.vm", context));		
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金池管理","供应商保证金池管理","DB保证金冲抵租金","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getOffsetData(){
		Map<String,Object> param = _getParameters();
		Page page = service.getPageDBOffSet(param);
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金池管理","供应商保证金池管理","退款流程审批页面"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showRefundProcessPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		Map<String,Object> refundPageData = service.getOneSupdbBail(param);
		context.put("refundMess", refundPageData);
		return new ReplyHtml(VM.html(path+"", context));
	}

}

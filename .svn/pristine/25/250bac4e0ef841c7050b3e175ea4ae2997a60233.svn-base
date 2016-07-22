package com.pqsoft.refund.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.capitalPool.supplierPool.service.SupplierBailPoolService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.refund.service.RefundProcessService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class RefundProcessAction extends Action {
    private String path = "refund/";
    private RefundProcessService  processService = new RefundProcessService();
	
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款流程","企划确认[列表]"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
    	Map<String,Object> param = _getParameters();
    	List<Object> busPlate = (List<Object>)new DataDictionaryMemcached().get("PDF模版所属商务板块");
		context.put("busPlate", busPlate);
    	context.put("param", param);
    	return new ReplyHtml(VM.html(path+"applyConfigProcessPage.vm", context));
	}
	
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款流程","企划确认[查询]"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getConfigPageData(){
		Map<String,Object> param = _getParameters();
    	Page pageData = processService.getProcesConfigPageData(param);
    	return new ReplyAjaxPage(pageData);
	}

    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款流程","资金解冻"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply refundFreeze(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if("1".equals(param.get("STATUS").toString().trim())){
			param.put("THAW_TIME", sdf.format(new Date()));
		}
		SupplierBailPoolService poolService = new SupplierBailPoolService();
		int result = poolService.updateFundsPool(param);
		if(result >0 ){
			//更新退款单的信息
			Double lastMoney = poolService.getRefundMoney(param);
			if(lastMoney > 0){
			   param.put("RE_MONEY", lastMoney);
			}else{
			   param.put("RE_STATUS", "11");
			   param.put("RE_MONEY", lastMoney);
			}
			poolService.updateRefundMess(param);
			return new ReplyAjax(true, "操作成功!");
		}else{
		    return new ReplyAjax(false,"操作失败!");
		}
	}
}

package com.pqsoft.refund.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.refund.service.RefundBailApplyService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

public class RefundBailApplyAction extends Action{
	private String path = "refund/";
	private RefundBailApplyService service = new RefundBailApplyService();
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","添加保证金退款列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		List<Object> busPlate = (List<Object>)new DataDictionaryMemcached().get("PDF模版所属商务板块");
		context.put("busPlate", busPlate);
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"RefundBailApplyPage.vm", context));
	}
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","添加保证金退款查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getRefundPageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getRefundPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款申请","可退保证金下拉"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getRefundBailDropList(){ 
		Map<String,Object> param = _getParameters();
		List<Object> dropList = service.getCanRefundBailList(param);
		return new ReplyJson(JSONArray.fromObject(dropList));
	}

    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款明细查询","退款明细列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply refundSearchDetail(){
    	VelocityContext context = new VelocityContext();
    	Map<String,Object> param = _getParameters();
    	List<Object> busPlate = (List<Object>)new DataDictionaryMemcached().get("PDF模版所属商务板块");
    	List<Object> refundStatus = (List<Object>)new DataDictionaryMemcached().get("退款单状态");
		context.put("busPlate", busPlate);
		context.put("refundStatus", refundStatus);
    	context.put("param", param);
    	return new ReplyHtml(VM.html(path+"RefundSearchDetailPage.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","退款管理","保证金退款明细查询","退款明细查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getSearchDetailPageData(){
    	Map<String,Object> param = _getParameters();
    	Page pageDataPage = service.getRefundDetailPageData(param);
    	return new ReplyAjaxPage(pageDataPage);
    }
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款明细查询","退款明细查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply showRefundPrcessMess(){ 
    	VelocityContext context = new VelocityContext();
    	Map<String,Object> param = _getParameters();
    	List<String> user = Security.getUser().getRoles();
//    	param.put("JBPM_ID", "保证金退款流程.2480301");
    	//退款汇总表
    	List<Map<String,Object>> listApply = service.getRefundList(param);
    	Map<String,Object> oneRefundDan = service.getOneRefundDan(param);
    	context.put("REFUND_TYPE", StringUtils.nullToString(oneRefundDan.get("RE_DATE"))+""+StringUtils.nullToString(oneRefundDan.get("RE_TYPE")) );
    	context.put("listApply", listApply);
    	List<Object> listApplyDetail = service.getRefundDetail(param);
    	context.put("listApplyDetail", listApplyDetail);
    	context.put("param", param);
    	return new ReplyHtml(VM.html(path+"RefundApprovalPage.vm", context));
    }
}

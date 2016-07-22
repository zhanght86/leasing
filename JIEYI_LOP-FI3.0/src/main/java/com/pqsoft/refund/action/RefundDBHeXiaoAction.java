package com.pqsoft.refund.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

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
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.ReplyExcel;

public class RefundDBHeXiaoAction extends Action {
    private String path = "refund/";
    private RefundDBHeXiaoService service = new RefundDBHeXiaoService();
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款核销","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		List<Object> busPlate = (List<Object>)new DataDictionaryMemcached().get("PDF模版所属商务板块");
		context.put("busPlate", busPlate);
		List<Object> sflcBanks = (List<Object>)new DataDictionaryMemcached().get("SFLC付款银行");
		context.put("banks", sflcBanks);
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"RefundDbHeXiaoPage.vm", context)); 
	}
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款核销","查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getHeXiaoPageData(){
    	Map<String,Object> param = _getParameters();
    	Page pageData = service.getHeXiaoPageData(param);
		return new ReplyAjaxPage(pageData);
    }
	
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款核销","核销"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply heXiaoMethod(){
		Map<String,Object> param = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData2").toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int result = 0;
		for (int i = 0; i < jsonArray.size(); i++) {
			Map<String,Object> param2 = (Map<String,Object>)jsonArray.get(i);
			param.put("RE_STATUS", "2");
			param.put("RE_ID", param2.get("RE_ID").toString());
			param.put("RE_PAY_BANK", param2.get("RE_PAY_BANK").toString());
			param.put("RE_CHECKED_CODE", Security.getUser().getName());
			param.put("RE_CHECKED_DATE", sdf.format(new Date()));
			result = service.updateRefundDan(param);
			param.put("RE_STATUS", "2");
			//将退款单号回更到DB保证金记录上 并将DB保证金改为已退款状态
			param.put("STATUS", "3");
			service.updatePoolStatusByRe_id(param);
		}
		
		if(result > 0 ){
			return new ReplyAjax(true, "核销成功！");
		}else{
			return new ReplyAjax(false,"核销失败！");
		}
	}
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金管理","退款管理","保证金退款核销","下拉列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getDropDownList(){
    	Map<String,Object> param = _getParameters();
    	List<Object> bailList = service.refundDanDetail(param);
		return new ReplyJson(JSONArray.fromObject(bailList));
    }
    
    public Reply exportExcelMess(){
    	Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Excel excel = new Excel();
		RefundDBApplyService applyService = new RefundDBApplyService();
		String HeadName = sdf.format(new Date())+"DB保证金退款表";
		excel = applyService.getExportDBDetail(param);
	    String fileName = sdf.format(new Date())+HeadName+".xls";
		return new ReplyExcel(excel,fileName);
    }
  
    
}

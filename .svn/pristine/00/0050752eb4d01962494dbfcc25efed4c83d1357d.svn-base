package com.pqsoft.invoiceForRed.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.invoiceForRed.service.InvoiceForRedService;
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

public class InvoiceForRedAction extends Action {
	private String path ="invoiceForRed/";
	private InvoiceForRedService service = new InvoiceForRedService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "票据作废冲红管理","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("dic_invoice_typename", (List<Object>)new DataDictionaryMemcached().get("发票_类型名称"));
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"invoiceForRedApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "票据作废冲红管理","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getForRedPageData(){
		Map<String,Object> param = _getParameters();
		Page redPageData = service.getForRedPage(param);
		return new ReplyAjaxPage(redPageData);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "票据作废冲红管理","冲红发票"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyForRedMethod(){
		Map<String,Object> param = _getParameters();
		param.put("INVOICE_STATUS", "2");
		int result = service.updateInvoiceStatus(param);
		service.updateInvoiceDetailStatus(param);
		if(result > 0 ){
			return new ReplyAjax(true, "冲红申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "票据作废冲红管理","作废选中发票"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply invalidInvoiceMethod(){
		Map<String,Object> param = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		Boolean flag = true;
		String msg = "";
		int result = 0 ;
		for (Object obj : jsonArray) {
			Map<String,Object> param2 = (Map<String,Object>)obj;
			param2.put("INVOICE_STATUS", "3");
			service.updateInvoiceDetailStatus(param2);
		    service.updateInvoiceStatus(param2);
		}
		if(result > 0) {
			return new ReplyAjax(flag, msg);
		}else{
			return new ReplyAjax(flag, msg);
		}
	}
	
}

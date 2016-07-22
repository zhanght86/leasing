package com.pqsoft.eqInvoices.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.eqInvoices.service.EqInvoiceLegerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.ReplyExcel;

public class EqInvoiceLegerAction extends Action {
	private String path = "eqInvoices/";
	private EqInvoiceLegerService service = new EqInvoiceLegerService();

	//@aAuth(type = aAuth.aAuthType.USER)
	//@aPermission(name ={"票据管理", "租赁物发票台账","列表显示"})
	//@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		context.put("param", param);
//		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
//		return new ReplyHtml(VM.html(path+"eqInvoiceLegePage.vm", context));
		return null;
	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理", "租赁物发票台账","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getLegerPageData(){
//		Map<String,Object> param = _getParameters();
//		Page pageData = service.getEqLegerPageData(param);
//		return new ReplyAjaxPage(pageData);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理", "租赁物发票台账","导出台账"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply exportEqInvoiceLedger(){
//		Map<String,Object> param = _getParameters();
//		Excel excel = new Excel();
//		excel = service.exportEqInvoiceLedger(param);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String fileName = "租赁物发票台账-（"+sdf.format(new Date())+"）"+Math.round(Math.random()*8999+1000)+".xls";
//		return new ReplyExcel(excel,fileName);
//	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票台帐","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getLegerDunData() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(path+"dunInvoiceLegePage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票台帐","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getLegerPageDunData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getDunLegerPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票台帐","导出台账"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply exportDunInvoiceLedger(){
		Map<String,Object> param = _getParameters();
		Excel excel = new Excel();
		excel = service.exportDunInvoiceLedger(param);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "代收违约金发票台账-（"+sdf.format(new Date())+"）"+Math.round(Math.random()*8999+1000)+".xls";
		return new ReplyExcel(excel,fileName);
	}

}

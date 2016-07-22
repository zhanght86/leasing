package com.pqsoft.delivery.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.delivery.service.InvoiceApplicationService;
import com.pqsoft.fhfaManager.FhfaManagerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class InvoiceApplicationAction extends Action {

	VelocityContext context = new VelocityContext();
	InvoiceApplicationService service = new InvoiceApplicationService();

	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理", "开票协议", "列表显示" })
	public Reply execute() {
		return new ReplyHtml(VM.html("delivery/invoiceApplicationList.vm",
				context));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理", "开票协议", "列表显示" })
	public Reply getInvoiceApplicationList() {
		Map<String, Object> param = _getParameters();
		Page page = service.getInvoiceApplicationPage(param);
		return new ReplyAjaxPage(page);
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理","开票协议","新建" })
	public Reply addInvoiceApplicationPage() {
		Map<String, Object> param = _getParameters();

		// 开票方式
		String TYPE = "开票方式";
		List<Object> list = (List) new DataDictionaryMemcached().get(TYPE);
		context.put("ProjectTypeList", list);
		
		//纳税人资质
		String taxTYPE = "纳税资质";
		List<Object> taxTYPEList = (List) new DataDictionaryMemcached().get(taxTYPE);
		context.put("taxTYPEList", taxTYPEList) ;

		// 平台ID
		String platformId = Security.getUser().getOrg().getPlatformId();
		Long fhfa_id = null == platformId ? null : Long.valueOf(platformId);
		FhfaManagerService fhfaManagerService = new FhfaManagerService();
		// 查询融资租赁信息 甲方
		Map<String, Object> fhfaManager = fhfaManagerService
				.selectFHFAManagerById(fhfa_id);
		context.put("PARTY_A", fhfaManager);

		// 查询客户信息 乙方
		Map<String, Object> custInfo = service.searchCustInfo(param);
		context.put("PARTY_B", custInfo);

		// 生成开票协议编号
		String clientId = service.getClientIdByProjectId(param) == null ? null
				: service.getClientIdByProjectId(param).toString();
		String invoiceAppCode = CodeService.getCode("开票协议编号", clientId, param
				.get("PROJECT_ID").toString());
		param.put("INVOICEAPPCODE", invoiceAppCode);
		context.put("param", param);
		return new ReplyHtml(VM.html("delivery/invoiceApplication_add.vm",
				context));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理", "开票协议", "修改" })
	public Reply editInvoiceApplicationPage() {
		Map<String, Object> param = _getParameters();
		// 开票方式
		String TYPE = "开票方式";
		List<Object> list = (List) new DataDictionaryMemcached().get(TYPE);
		
		//纳税人资质
		String taxTYPE = "纳税资质";
		List<Object> taxTYPEList = (List) new DataDictionaryMemcached().get(taxTYPE);
		context.put("taxTYPEList", taxTYPEList) ;
		
		context.put("ProjectTypeList", list);
		Map<String, Object> invoiceAppInfo = service
				.getInvoiceApplicationById(param);
		context.put("INVOICEAPPINFO", invoiceAppInfo);
		return new ReplyHtml(VM.html("delivery/invoiceApplication_edit.vm",
				context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理","开票协议","修改" })
	public Reply editInvoiceApplicationPageProject() {
		Map<String, Object> param = _getParameters();
		// 开票方式
		String TYPE = "开票方式";
		List<Object> list = (List) new DataDictionaryMemcached().get(TYPE);
		
		//纳税人资质
		String taxTYPE = "纳税资质";
		List<Object> taxTYPEList = (List) new DataDictionaryMemcached().get(taxTYPE);
		context.put("taxTYPEList", taxTYPEList) ;
		
		context.put("ProjectTypeList", list);
		Map<String, Object> invoiceAppInfo = service
				.getInvoiceApplicationById(param);
		context.put("INVOICEAPPINFO", invoiceAppInfo);
		return new ReplyHtml(VM.html("delivery/invoiceApplication_edit.vm",
				context));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理","开票协议","查看" })
	public Reply showInvoiceApplicationPage() {
		Map<String, Object> param = _getParameters();
		// 开票方式
		String TYPE = "开票方式";
		List<Object> list = (List) new DataDictionaryMemcached().get(TYPE);
		context.put("ProjectTypeList", list);
		
		//纳税人资质
		String taxTYPE = "纳税资质";
		List<Object> taxTYPEList = (List) new DataDictionaryMemcached().get(taxTYPE);
		context.put("taxTYPEList", taxTYPEList) ;
		Map<String, Object> invoiceAppInfo = service
				.getInvoiceApplicationById(param);
		context.put("INVOICEAPPINFO", invoiceAppInfo);
		return new ReplyHtml(VM.html("delivery/invoiceApplication_show.vm",
				context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同管理","开票协议","查看" })
	public Reply showInvoiceApplicationPageProject() {
		Map<String, Object> param = _getParameters();
		// 开票方式
		String TYPE = "开票方式";
		List<Object> list = (List) new DataDictionaryMemcached().get(TYPE);
		context.put("ProjectTypeList", list);
		
		//纳税人资质
		String taxTYPE = "纳税资质";
		List<Object> taxTYPEList = (List) new DataDictionaryMemcached().get(taxTYPE);
		context.put("taxTYPEList", taxTYPEList) ;
		Map<String, Object> invoiceAppInfo = service
				.getInvoiceApplicationById(param);
		context.put("INVOICEAPPINFO", invoiceAppInfo);
		return new ReplyHtml(VM.html("delivery/invoiceApplication_show.vm",
				context));
	}

//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
//	@aPermission(name = { "项目管理", "项目一览", "核心要件","开票协议","新建-保存"  })
//	public Reply addInvoiceApplication() {
//		Map<String, Object> param = _getParameters();
//		int isAdd = service.insertInvoiceApplication(param);
//		boolean flag = false;
//		String info = "添加开票协议失败!";
//		if (isAdd > 0) {
//			flag = true;
//			info = "添加开票协议成功!";
//		}
//		return new ReplyAjax(flag, info);
//	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "合同中心", "开票协议", "修改-保存" })
	public Reply editInvoiceApplication() {
		Map<String, Object> param = _getParameters();
		// 开票方式
		int isUpdate = service.updateInvoiceApplication(param);
		boolean flag = false;
		String info = "更新开票协议失败!";
		if (isUpdate > 0) {
			flag = true;
			info = "更新开票协议成功!";
		}
		return new ReplyAjax(flag, info);
	}

}

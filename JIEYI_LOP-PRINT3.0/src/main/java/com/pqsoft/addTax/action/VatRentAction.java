package com.pqsoft.addTax.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.addTax.service.VatFirstPayService;
import com.pqsoft.addTax.service.VatRentService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
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
import com.pqsoft.skyeye.rbac.api.aPermission;

public class VatRentAction extends Action {

	private String path = "addTax/";
	private VatRentService service = new VatRentService();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","租金发票申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showRentApplyPage(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"vatRentApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","租金发票申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply rentApplyPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getRentApplyData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","租金发票申请","申请全部/申请选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyVatRentMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService vatService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		int result = 0 ;
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size()>0){
			applyMess = jsonArray ;
		}else{
			param.put("VAT_FLAG", "YES");
			param.put("INVOICE_TYPE", "增值税发票");
			applyMess = service.getAllRentInvoiceMess(param);
		}
		//插入发票详细信息
		List<String> s = new ArrayList<String>();
		for (Object object : applyMess) {
			s.clear();
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "租金");
			if(newParam.containsKey("ITEM_FLAG") && newParam.get("ITEM_FLAG") !=null && "违约金".equals(newParam.get("ITEM_FLAG").toString())){
				newParam.put("FUND_NAME", "违约金");
				s.add("违约金");
			}else{
				newParam.put("FUND_NAME", "租金");
				s.add("本金");
				s.add("利息");
				s.add("管理费");
				s.add("手续费");
				s.add("利息增值税");
			}
			
			String D_STATUS ="1,2";
			newParam.put("D_STATUS", D_STATUS);
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "增值税发票");
			newParam.put("ITEM_MODEL", newParam.containsKey("BEGINNING_NUM") && newParam.get("BEGINNING_NUM") !=null ?newParam.get("BEGINNING_NUM").toString()+"期":"");
			newParam.put("IF_INVOICE", "yes");
			result += vatService.addInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
}

package com.pqsoft.capitalInstall.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.capitalInstall.service.CapitalInstallService;
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

public class CapitalInstallAction extends Action {
	private String path = "capitalInstall/";
    private CapitalInstallService service = new CapitalInstallService();
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "增值税发票管理", "分期/本金开票申请", "列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"capitalInstallApplyPage.vm", context));
	}
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "增值税发票管理", "分期/本金开票申请", "列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getPageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getCapitalInstallApply(param);
		return new ReplyAjaxPage(pagedata);
	}
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "增值税发票管理", "分期/本金开票申请", "申请"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyCapitalInstallMethod(){
		Map<String,Object> param = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(param.get("proData").toString());
		System.out.println("============"+jsonArray);
		int result = 0 ; 
		List<Object> arryList = null;
		if(jsonArray.size()>0){
			arryList = jsonArray;
		}else{
			//查询申请页面数据
			arryList = service.getAllCapitalIntall(param);
		}
		for (Object obj : arryList) {
			Map<String,Object> newParam = (Map<String,Object>)obj;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("INVOICE_TYPE", "增值税发票");
			newParam.put("D_STATUS", "0");
			if(newParam.containsKey("PROJECT_MODEL") && newParam.get("PROJECT_MODEL") != null && "2".equals(newParam.get("PROJECT_MODEL").toString())){
				newParam.put("FUND_TYPE", "本金");
				newParam.put("FUND_NAME", "本金");
			}else{
				newParam.put("FUND_TYPE", "货款");
				newParam.put("FUND_NAME", "货款");
			}
			//插入本金分期票据开具信息
			result = service.addCapitalInstallMess(newParam);
		}			
		if(result > 0){
		    return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败");
		}
    }
}

package com.pqsoft.finance.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.finance.service.DossierFinanceService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

/*********融资档案管理**@auth: king 2014年9月22日 *************************/
public class DossierFinanceAction extends Action {
	
	private String path="finance/";
	private DossierFinanceService service=new DossierFinanceService();
	
	@Override
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"档案管理","融资档案管理","列表显示"})
	public Reply execute() {
//		return new ReplyHtml(VM.html(path+"dossierFinanceMg.vm", null));
		return null;
	}
	
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	public Reply doMgDossierFinance(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.doMgDossierFinance(map));
	}
	
	/**
	 * 查看融资项目需要文件列表
	 * @return
	 * @author King 2014年9月28日
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	public Reply doShowFinanceDossierDetailList(){
		VelocityContext context=new VelocityContext();
		Map<String,Object> param=_getParameters();
		context.put("fileList", service.getProjectPayList(param));
		return new ReplyHtml(VM.html(path+"subFinanceDossierFile.vm", context));
	}
}

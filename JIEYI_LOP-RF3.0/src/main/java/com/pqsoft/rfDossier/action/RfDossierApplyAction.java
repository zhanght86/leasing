package com.pqsoft.rfDossier.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.rfDossier.service.RfDossierApplyService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * 融资档案管理Action
 * 
 * @author King 2013-11-1
 */
public class RfDossierApplyAction extends Action {
	private String path = "rfDossier/";
	private RfDossierApplyService service=new RfDossierApplyService();
	
	@Override
	public Reply execute() {
		return null;
	}
	
//	/**
//	 * 进入融资档案管理页面
//	 * @return
//	 * @author:King 2013-11-1
//	 */
//	@aAuth(type=aAuthType.USER)
//	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
//	@aPermission(name={"融资管理","融资档案","列表"})
//	public Reply toMgRfDossier() {
//		Map<String, Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path+"rfDossierMg.vm", context));
//	}
//	/**
//	 * 融资档案管理页面查询数据
//	 * @return
//	 * @author:King 2013-11-1
//	 */
//	@aAuth(type=aAuthType.LOGIN)
//	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
////	@aPermission(name={"融资中心","融资档案","列表","查询"})
//	public Reply doShowMgRfDossier(){
//		Map<String,Object> param=_getParameters();
//		return new ReplyAjaxPage(service.doShowMgRfDossierPage(param)); 
//	}
	
	
}

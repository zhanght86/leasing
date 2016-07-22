package com.pqsoft.Transfer.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Transfer.service.DossierTransferManagerService;
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
 * 档案移交管理
 * @author King 2013-10-22
 */
public class DossierTransferManagerAction extends Action {
	private DossierTransferManagerService service = new DossierTransferManagerService();
	private String path = "transfer/";

	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * 进入档案移交管理页面
	 * 
	 * @return
	 * @author:King 2013-10-14
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "档案管理", "档案移交", "列表显示" })
	public Reply toMgDossierTransferManager() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(path + "dossierTransferManager.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理中心", "档案移交管理", "加载列表数据" })
	public Reply doShowDossierTransferManager() {
		Map<String,Object> param=_getParameters();
		if (param.containsKey("param")) {
			param.putAll(JSONObject.fromObject(param.get("param")));
		} 
		return new ReplyAjaxPage(service.doShowDossierTransferManager(param));
	}

//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理中心", "档案移交管理", "下载所有权证书" })
//	public Reply downLoad() {
//		List<Map<String,Object>> list=service.doShowDossierTransferManagerList(_getParameters());
//		return new ReplyFile(new File(list.get(0).get("FILEPATH") + ""), list.get(0).get("FILEPATH_NAME") + "");
//	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理", "档案移交管理", "查询移交详细" })
	public Reply doShowTransferDetailList() {
		VelocityContext context = new VelocityContext();
		context.put("subList", service.doShowBorrowDetailList(_getParameters()));
		return new ReplyHtml(VM.html(path + "TransferSubList.vm", context));
	}
}

package com.pqsoft.borrow.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.borrow.service.DossierBorrowManagerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * 档案借还管理
 * 
 * @author King 2013-10-14
 */
public class DossierBorrowManagerAction extends Action {
	private DossierBorrowManagerService service = new DossierBorrowManagerService();
	private String path = "dossierBorrow/";

	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * 进入档案借还管理页面
	 * 
	 * @return
	 * @author:King 2013-10-14
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "档案管理", "借阅管理", "列表显示" })
	public Reply toMgDossierBorrowManager() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(path + "dossierBorrowManager.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理", "档案借还管理", "加载列表数据" })
	public Reply doShowDossierBorrowManager() {
		Map<String,Object> param=_getParameters();
		if (param.containsKey("param")) {
			param.putAll(JSONObject.fromObject(param.get("param")));
		} 
		return new ReplyAjaxPage(service.doShowDossierBorrowManager(param));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "档案管理", "档案借阅管理", "下载借阅函(数据列表)" })
	public Reply downLoad() {
		List<Map<String,Object>> list=service.doShowDossierBorrowManagerList(_getParameters());
		return new ReplyFile(new File(list.get(0).get("FILEPATH") + ""), list.get(0).get("FILEPATH_NAME") + "");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理中心", "档案借还管理", "查询借阅详细" })
	public Reply doShowBorrowDetailList() {
		VelocityContext context = new VelocityContext();
		context.put("subList", service.doShowBorrowDetailList(_getParameters().get("BORROW_APP_ID") + ""));
		return new ReplyHtml(VM.html(path + "borrowSubList.vm", context));
	}
}

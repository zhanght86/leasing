package com.pqsoft.Transfer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Transfer.service.DossierTransferAppService;
import com.pqsoft.borrow.service.DossierBorrowService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.dossier.service.DossierManagerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.StringUtils;

/**
 * 档案移交申请流程
 * 
 * @author King 2013-10-21
 */
public class DossierTransferAppAction extends Action {
	private String path = "transfer/";
	private DossierTransferAppService service = new DossierTransferAppService();

	@Override
	public Reply execute() {
		return null;
	}

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "档案管理", "档案管理", "档案移交申请"})
	@aAuth(type = aAuthType.USER)
	public Reply toTransferAppForm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Map<String, Object>> FILELIST = service.doShowDossierTransferAppList(param);
		context.put("FILELIST", FILELIST);
		if (FILELIST.size() > 0)
			context.put("BASEINFO", FILELIST.get(0));
		return new ReplyHtml(VM.html(path + "dossierTransferApply.vm", context));
	}
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理", "档案移交管理", "档案移交修改", "加载数据列表" })
	@aAuth(type = aAuthType.LOGIN)
	public Reply toUpdateTransferAppForm() {
		Map<String, Object> map = service.doShowTransferApp(_getParameters());
		VelocityContext context = new VelocityContext();
		context.put("baseMap", map);
		context.put("fileList", JSONArray.fromObject(map.get("FILEINFO")));
		return new ReplyHtml(VM.html(path + "updateTransferappForm.vm", context));
	}
	
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理", "档案移交管理", "档案移交修改", "保存" })
	@aAuth(type = aAuthType.LOGIN)
	public Reply doUpdateTransferApp(){
		Map<String, Object> param = _getParameters();
		try{
			service.doAddTransferApp(param);
			return new ReplyAjax().addOp(new OpLog("档案管理-档案移交管理-档案移交申请", "修改", "档案移交申请修改，申请ID为" + param.get("TRANSFERAPPID")));
		}catch(Exception e){
			throw new ActionException("移交申请失败");
		}
	}

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案移交管理", "档案移交申请", "保存" })
	@aAuth(type = aAuthType.LOGIN)
	public Reply doAddTransferApp() {
		Map<String, Object> param = _getParameters();
		String TRANSFER_APPLY_ID = service.doAddTransferApp(param);
		if (StringUtils.isBlank(TRANSFER_APPLY_ID)) {
			throw new ActionException("移交申请失败");
		} else{
			List<String> list=JBPM.getDeploymentListByModelName("dossierTransfer");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("TRANSFER_APPLY_ID", TRANSFER_APPLY_ID);
			JBPM.startProcessInstanceById(list.get(0), param.get("PROJECT_ID")+"", param.get("CLIENT_ID")+"", param.get("PAYLIST_CODE")+"", map);
			return new ReplyAjax().addOp(new OpLog("档案管理-档案移交管理-档案移交申请", "保存", "档案移交申请保存，申请ID为" + TRANSFER_APPLY_ID));
		}
	}

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案移交管理", "档案移交申请", "查看" })
	@aAuth(type = aAuthType.LOGIN)
	public Reply doShowTransferApp() {
		Map<String, Object> map = service.doShowTransferApp(_getParameters());
		VelocityContext context = new VelocityContext();
		context.put("baseMap", map);
		List<Map<String, Object>> list = JSONArray.fromObject(map.get("FILEINFO"));
		new DossierBorrowService().doShowDossierStatus(list);
		context.put("fileList", list);
		return new ReplyHtml(VM.html(path + "showDossierTransferApply.vm", context));
	}

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案移交管理", "确认移交", "操作页面" })
	@aAuth(type = aAuthType.LOGIN)
	public Reply doShowTransferDesign() {
		Map<String, Object> map = service.doShowTransferApp(_getParameters());
		VelocityContext context = new VelocityContext();
		context.put("baseMap", map);
		List<Map<String, Object>> list = JSONArray.fromObject(map.get("FILEINFO"));
		new DossierBorrowService().doShowDossierStatus(list);
		context.put("fileList", list);
		return new ReplyHtml(VM.html(path + "showDossierTransferDesign.vm", context));
	}

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案移交管理", "确认移交", "确认移交保存" })
	@aAuth(type = aAuthType.LOGIN)
	public Reply doAddTransferDesign() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		service.doDelTransferDesign(list.get(0).get("TRANSFER_APP_ID") + "");
		int i = 0;
		for (Map<String, Object> map2 : list) {
			i += service.doAddTransferDesign(map2);
			new DossierManagerService().doUpdateDossierStatusById(map2.get("DOSSIERID") + "", "2");
		}
		if (i == list.size()) {
			return new ReplyAjax().addOp(new OpLog("档案移交管理-确认移交", "保存", "确认移交申请信息,移交申请id为" + list.get(0).get("TRANSFER_APP_ID") + ""));
		} else {
			throw new ActionException("确认移交失败");
		}
	}
}

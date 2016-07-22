package com.pqsoft.borrow.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;
import org.springframework.util.StringUtils;

import com.pqsoft.borrow.service.DossierBorrowService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.dossier.service.DossierManagerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * 档案借阅管理
 * 
 * @author King 2013-10-11
 */
public class DossierBorrowAction extends Action {
	private DossierBorrowService service = new DossierBorrowService();
	private String path = "dossierBorrow/";

	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * 查出可以借阅的档案信息 进入档案借阅申请表操作页面
	 * 
	 * @return
	 * @author:King 2013-10-11
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "档案管理", "档案管理", "借阅申请"})
	public Reply toBorrowAppForm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Map<String, Object>> FILELIST = service.doShowDossierBorrowApplyList(param);
		context.put("FILELIST", FILELIST);
		if (FILELIST.size() > 0)
			context.put("BASEINFO", FILELIST.get(0));
		return new ReplyHtml(VM.html(path + "dossierBorrowApply.vm", context));
	}

	/**
	 * 保存借阅申请信息
	 * 
	 * @return
	 * @author:King 2013-10-11
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理", "档案借阅管理", "借阅申请", "保存" })
	public Reply doAddBorrowApp() {
		// TODO King 确定文件存放位置
		Map<String, Object> param = _getParametersIO(null, null, null);
		String id = service.doAddBorrowApp(param);
		if (!StringUtils.isEmpty(id)){
			List<String> list=JBPM.getDeploymentListByModelName("档案借还");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("BORROW_APP_ID", id);
			JBPM.startProcessInstanceById(list.get(0), param.get("PROJECT_ID")+"", param.get("CLIENT_ID")+"", param.get("PAYLIST_CODE")+"", map);
			return new ReplyAjax(true).addOp(new OpLog("档案借阅管理-借阅申请", "保存", "保存档案借阅申请单ID为：" + id));
		}else
			throw new ActionException("保存失败");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案借阅管理", "借阅申请", "查看" })
	public Reply doShowBorrowApp() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = service.doShowBorrowApp(param.get("BORROW_APP_ID") + "");
		context.put("DOSSIERINFO", map);
		context.put("FILELIST", JSONArray.fromObject(map.get("DOSSIERINFO")));
		return new ReplyHtml(VM.html(path + "dossierBorrowApplyShow.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案借阅管理", "借阅申请", "修改" })
	public Reply doUpdateBorrowApp(){
		VelocityContext context=new VelocityContext();
		Map<String, Object> param = _getParameters();
		Map<String, Object> map = service.doShowBorrowApp(param.get("BORROW_APP_ID") + "");
		context.put("DOSSIERINFO", map);
		context.put("FILELIST", JSONArray.fromObject(map.get("DOSSIERINFO")));
		return new ReplyHtml(VM.html(path+"dossierBorrowUpdate.vm", context));
	}
	/**
	 * 修改借阅申请信息
	 * 
	 * @return
	 * @author:King 2013-10-11
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理", "档案借阅管理", "借阅申请", "保存" })
	public Reply doUpdateBorrowAppSave() {
		Map<String, Object> param = _getParametersIO(null, null, null);
		param.put("FILEPATH_NAME", param.get("_FILE_NAME"));
		try{
			service.doUpdateBorrowAppSave(param);
			return new ReplyAjax(true).addOp(new OpLog("档案借阅管理-借阅申请", "修改", "修改申请id：" + param.get("BORROWID")));
		}catch(Exception e){
			throw new ActionException("");
		}
	}
	
	/**
	 * 借出登记页面
	 * 
	 * @return
	 * @author:King 2013-10-12
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案借阅管理", "借出登记", "加载数据" })
	public Reply toBorrowOutForm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = service.doShowBorrowApp(param.get("BORROW_APP_ID") + "");
		List<Map<String, Object>> list = JSONArray.fromObject(map.get("DOSSIERINFO"));
		service.doShowDossierStatus(list);
		context.put("DOSSIERINFO", map);
		context.put("FILELIST", list);
		return new ReplyHtml(VM.html(path + "toBorrowOutForm.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理", "档案借阅管理", "借出登记", "下载借阅函" })
	public Reply downFile() {
		Map<String, Object> param = _getParameters();
		return new ReplyFile(new File(param.get("FILE_PATH") + ""), param.get("FILE_PATH_NAME") + "");
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理", "档案借阅管理", "借出登记", "保存借出信息" })
	public Reply doAddBorrowOutInfo() {
		List<Map<String, Object>> list = JSONArray.fromObject(_getParameters().get("param"));
		int i = 0;
		for (Map<String, Object> map : list) {
			new DossierManagerService().doUpdateDossierStatusById(map.get("DOSSIER_STORAGE_ID") + "", "1");
			i += service.doAddBorrowOutInfo(map);
		}
		if (i == list.size())
			return new ReplyAjax().addOp(new OpLog("档案借阅管理-借出登记", "保存借出信息", "借出档案申请id为：" + list.get(0).get("DOSSIER_BORROWAPPID")));
		else
			throw new ActionException("借出登记失败");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案借阅管理", "借出登记", "查看" })
	public Reply doShowDossierBorrowedInfo() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Map<String, Object>> list = service.doShowDossierBorrowedInfo(param);
		context.put("PARAM", list.get(0));
		context.put("FILELIST", list);
		return new ReplyHtml(VM.html(path + "toBorrowedForm.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案借阅管理", "归还登记", "表单" })
	public Reply doReturnRegister() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Map<String, Object>> list = service.doShowDossierBorrowedInfo(param);
		context.put("PARAM", list.get(0));
		context.put("FILELIST", list);
		return new ReplyHtml(VM.html(path + "toReturnRegisterForm.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案借阅管理", "归还登记", "归还入柜保存" })
	public Reply doReturnRegistered() {
		List<Map<String, Object>> list = JSONArray.fromObject(_getParameters().get("param"));
		int i = 0;
		for (Map<String, Object> map : list) {
			if (!StringUtils.isEmpty(map.get("DOSSIER_STORAGE_ID")))
				new DossierManagerService().doUpdateDossierStatusById(map.get("DOSSIER_STORAGE_ID") + "", "0");
			if (!StringUtils.isEmpty(map.get("DOSSIER_BORROW_ID")))
				i += service.updateDossierBorrowStatusById(map.get("DOSSIER_BORROW_ID") + "", "1");
		}
		if (i == list.size())
			return new ReplyAjax().addOp(new OpLog("档案借阅管理-归还登记", "归还入柜保存", "归还档案申请id为：" + list));
		else
			throw new ActionException("归还登记失败");
	}
}

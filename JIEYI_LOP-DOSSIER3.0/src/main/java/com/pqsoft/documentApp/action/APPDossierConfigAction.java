package com.pqsoft.documentApp.action;

import jxl.common.Logger;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.documentApp.service.APPDossierConfigService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class APPDossierConfigAction extends Action{
	private String path = "documentApp/Config/";
	private final Logger log = Logger.getLogger(this.getClass());
	private APPDossierConfigService service = new APPDossierConfigService();
	
	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * 进入档案柜编码管理页
	 * 
	 * @return
	 * @author:yangxue 2013-9-15
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案柜编码", "列表显示" })
	public Reply toMgDossierConfig() {
		VelocityContext context = new VelocityContext();
		context.put("TRADETYPE", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(path + "dossierConfigMg.vm", context));
	}

	/**
	 * 查询档案柜编码规则-分页
	 * 
	 * @return
	 * @author:yangxue 2013-9-15
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
//	@aPermission(name = { "权证管理", "档案柜编码", "查询" })
	public Reply doShowDossierConfigForPage() {
		return new ReplyAjaxPage(service.doShowDossierConfigForPage(_getParameters()));
	}

	/**
	 * 添加保存档案柜编码规则
	 * 
	 * @return
	 * @author:yangxue 2013-9-15
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案柜编码",  "添加" })
	public Reply doAddDossierConfig() {
		_getParameters().put("USERCODE", Security.getUser().getCode());
		int i = service.doAddDossierConfig(_getParameters());
		if (i > 0) {
			return new ReplyAjax().addOp(new OpLog("权证管理-档案柜编码", "添加保存", _getParameters() + ""));
		} else
			return new ReplyAjax(false, "添加失败");
	}

	/**
	 * 修改档案柜编码规则
	 * 
	 * @return
	 * @author:yangxue 2013-9-15
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案柜编码", "修改"})
	public Reply doUpdateDossierConfig() {
		log.info(_getParameters());
		int i = service.doUpdateDossierConfig(_getParameters());
		log.info("i===="+i);
		if (i > 0) {
			return new ReplyAjax().addOp(new OpLog("权证管理-档案柜编码", "修改保存", _getParameters() + ""));
		} else {
			return new ReplyAjax(false, "修改失败");
		}
	}
	
	/**
	 * 删除档案柜编码规则
	 * @return
	 * @author:yangxue 2013-9-15
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案柜编码", "删除" })
	public Reply doDelDossierConfig(){
		log.info(_getParameters());
		int i=service.doDelDossierConfig(_getParameters());
		if(i>0){
			return new ReplyAjax().addOp(new OpLog("权证管理-档案柜编码", "删除", _getParameters()+""));
		}else{
			return new ReplyAjax(false,"删除失败");
		}
	}
}

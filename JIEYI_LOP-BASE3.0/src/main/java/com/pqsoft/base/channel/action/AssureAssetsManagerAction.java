package com.pqsoft.base.channel.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.channel.service.AssureAssetsManagerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class AssureAssetsManagerAction extends Action {
	private String path = "base/channel/";
	private AssureAssetsManagerService service = new AssureAssetsManagerService();

    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","供应商担保资产管理","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"assureAssetsManager.vm", context));
	}

    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","供应商担保资产管理","查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getPageData(){
		Map<String,Object> param = _getParameters();
		Page page = service.getAssetsPageData(param);
		return new ReplyAjaxPage(page);
	}
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","供应商担保资产管理","下拉数据"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getDropList(){
    	Map<String,Object> param  = _getParameters();
    	List<Object> listData = service.getDropListData(param);
    	return new ReplyJson(JSONArray.fromObject(listData));
    }
}

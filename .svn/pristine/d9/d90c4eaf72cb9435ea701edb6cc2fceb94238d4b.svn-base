package com.pqsoft.lmrm.action;

import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.velocity.VelocityContext;
import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.lmrm.service.DmvService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;

public class DmvAction extends Action {
	
	String vmPath = "lmrm/";
	DmvService service = new DmvService();
	AreaService areaService = new AreaService();

	/**
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-22  上午09:57:54
	 */
	@aPermission(name = { "资产管理", "租赁物抵押机构管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("areaList", service.selectAreaList());
		return new ReplyHtml(VM.html(vmPath + "dmvMg.vm", context));
	}
	
	/**
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-22  下午07:34:51
	 */
	@aPermission(name = { "资产管理", "租赁物抵押机构管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toMgDmvData(){
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	/**
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-22  下午07:34:51
	 */
	@aPermission(name = { "资产管理", "租赁物抵押机构管理", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectProvince(){
		return new ReplyAjax(areaService.getAllProvince());
	}
	
	/**
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-22  下午07:34:51
	 */
	@aPermission(name = { "资产管理", "租赁物抵押机构管理", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectCity(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(areaService.getSubset(param));
	}
	
	/**
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-22  下午07:34:51
	 */
	@aPermission(name = { "资产管理", "租赁物抵押机构管理", "查看" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectArea(){
		return new ReplyAjax(service.selectAreaList());
	}
	
	/**
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-8-31  下午02:01:47
	 */
	@aPermission(name = { "资产管理", "租赁物抵押机构管理", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toAddDmv(){
		Map<String, Object> param = _getParameters();
		boolean flag = service.addDmv(param);
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("添加租赁抵押备案", "添加", param.toString()));
		} else {
			return new ReplyAjax(false,"保存失败！");
		}
	}
	
	/**
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-8-31  下午02:01:47
	 */
	@aPermission(name = { "资产管理", "租赁物抵押机构管理", "修改" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toUpdateDmv(){
		Map<String, Object> param = _getParameters();
		boolean flag = service.updateDmv(param);
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("修改租赁抵押备案", "添加", param.toString()));
		} else {
			return new ReplyAjax(false,"保存失败！");
		}
	}
	
	/**
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-8-31  下午02:01:47
	 */
	@aPermission(name = { "资产管理", "租赁物抵押机构管理", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toDeleteDmv(){
		Map<String, Object> param = _getParameters();
		boolean flag = service.deleteDmv(param);
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("删除租赁抵押备案", "删除", param.toString()));
		} else {
			return new ReplyAjax(false,"删除失败！");
		}
	}

}

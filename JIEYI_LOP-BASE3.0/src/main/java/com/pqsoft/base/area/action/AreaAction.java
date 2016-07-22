package com.pqsoft.base.area.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;

public class AreaAction extends Action {

	private String path = "base/area/";
	private AreaService service = new AreaService();

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置",  "行政区域划分", "列表显示" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		return new ReplyHtml(VM.html(path + "Area.vm", null));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "leeds", email = "leedsjung@126.com", name = "张路")
	public Reply loadAreas() {
		Map<String, Object> param = _getParameters();
		List<Map> areas = service.getAreas(param);
		return new ReplyAjax(areas);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "参数配置",  "行政区域划分", "列表显示" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectAllCountries() {
		Page page = new Page();
		JSONArray ja = JSONArray.fromObject(service.selectAllCountries());
		page.addDate(ja, ja.size());
		return new ReplyAjaxPage(page);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "参数配置",  "行政区域划分", "列表显示", "删除" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doDeleteArea() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.deleteArea(param)).addOp(new OpLog( "行政区域划分", "删除", param.toString()));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "参数配置",  "行政区域划分", "列表显示" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectSubset() {
		Map<String, Object> param = _getParameters();
		JSONArray ja = JSONArray.fromObject(service.getSubset(param));
		return new ReplyAjax(ja);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "参数配置",  "行政区域划分", "列表显示", "添加" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddArea() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		param.remove("param");
		param.putAll(JsonUtil.toMap(json));
		try {
			service.insertArea(param);
			JSONObject object = JSONObject.fromObject(service.selectArea(param));
			return new ReplyAjax(object).addOp(new OpLog( "行政区域划分", "添加(国家、省份、市、区/县)", param.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return new ReplyAjax(false).addOp(new OpLog( "行政区域划分", "添加(国家、省份、市、区/县)", param.toString()));
		}
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "参数配置",  "行政区域划分", "列表显示", "修改" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdateArea() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		param.remove("param");
		param.putAll(JsonUtil.toMap(json));
		try {
			service.updateArea(param);
			JSONObject object = JSONObject.fromObject(service.selectArea(param));
			return new ReplyAjax(object).addOp(new OpLog( "行政区域划分", "修改(国家、省份、市、区/县)", param.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return new ReplyAjax(false).addOp(new OpLog( "行政区域划分", "修改(国家、省份、市、区/县)", param.toString()));
		}
	}

	/**
	 * 修改页面调用时需传参数：省份ID 用逗号分隔 eg：1,2,3,4
	 * 添加页面调用时传空值即可
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2014-3-29 下午02:31:54
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "参数配置",  "行政区域划分", "列表显示" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getAreaVM() {
		VelocityContext context = new VelocityContext();
		context.put("areaPlug", AreaService.areaPlug(null));
		return new ReplyHtml(VM.html(path + "GetArea.vm", context));
	}

}

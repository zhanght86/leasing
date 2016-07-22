package com.pqsoft.zcfl.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.SysDataDictionaryService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.zcfl.service.RatingService;

/**
 * 评级模版管理配置
 * @author Administrator
 *上午12:46:33
 */
public class RatingAction extends Action{
	
	private String path = "zcfl/rating/";
	private RatingService service= new RatingService();

	@Override
	@aPermission (name = {"资产分类管理","评级模板管理","列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("businessTypeList", SysDataDictionaryService.queryDataDictionary("业务类型"));
		return new ReplyHtml(VM.html(path+"ratingMg.vm", context));
	}
	
	@aPermission (name = { "资产分类管理","评级模板管理","列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getMgRatingData(){
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		param.put("TSDD_TYPE", "业务类型");
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	@aPermission(name = {"资产分类管理","评级模板管理","列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getRatingData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getRatingConfig(param));
	}
	
	@aPermission(name = {"资产分类管理", "评级模板管理", "添加模板" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply saveRating() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.saveRatingConfig(param));
	}
	
	@aPermission(name = { "资产分类管理","评级模板管理","修改" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply updateRating() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.updateRatingConfig(param));
	}
	
	@aPermission(name = {"资产分类管理","评级模板管理","删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply deleteRating() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.deleteRatingConfig(param));
	}
	
	@aPermission(name = {"资产分类管理", "评级模板管理", "配置模板" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getTitleData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getTitleData(param));
	}
	
	@aPermission(name = {"资产分类管理", "评级模板管理", "配置模板" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getConfigTitleData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getConfigTitleData(param));
	}
	
	@aPermission(name = {"资产分类管理", "评级模板管理", "配置模板" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply saveConfigTitle() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.saveConfigTItle(param));
	}
	
	@aPermission(name = {"资产分类管理", "评级模板管理", "配置模板" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getConfigTitleForName() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.saveConfigTItle(param));
	}

}

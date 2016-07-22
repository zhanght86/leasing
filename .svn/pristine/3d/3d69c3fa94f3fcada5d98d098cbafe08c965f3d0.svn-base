package com.pqsoft.secuEvaluate.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.secuEvaluate.service.NormScoreService;
import com.pqsoft.secuEvaluate.service.SecuEvaluateService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class SecuEvaluateAction extends Action {
	private SecuEvaluateService service = new SecuEvaluateService();
	private Map<String, Object> m = null;
	private VelocityContext context = null;
	private static final String ns = "SecuEvaluate/";

	@Override
	public Reply execute() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getParameters() {
		context = new VelocityContext();
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object men = en.nextElement();
			if ("json".equals(men.toString())) {
				m.putAll(JSONObject.fromObject(SkyEye.getRequest().getParameter("json").trim()));
			} else {
				m.put(men.toString(), SkyEye.getRequest().getParameter(men.toString()).trim());
			}
		}
		m.put("USERNAME", Security.getUser().getName());
		m.put("USERCODE", Security.getUser().getCode());
		m.put("USERID", Security.getUser().getId());
		m.put("RELATED", SkyEye.getRequest().getParameter("RELATED"));
		m.putAll(_getParameters());
		context.put("param", m);
		return m;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = {"参数配置", "评分设置", "打分模板", "列表显示"})
	public Reply toManager() {
		this.getParameters();
		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		//context.put("type", new DataDictionaryMemcached().get("行业类型-打分"));
		context.put("type",service.toFindIndustryMemcache());
		context.put("custTypes", new DataDictionaryMemcached().get("客户类型"));
		return new ReplyHtml(VM.html(ns + "SecuEvaluateManager.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgSecuEvaluateData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(_getParameters().get("param"));
		param.remove("param");
		param.putAll(json);
		return new ReplyAjaxPage(service.toSecuEvaluateManager(param));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = {"参数配置", "评分设置", "打分模板", "添加模板"})
	public Reply addUI() {
		this.getParameters();
		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		//context.put("type", new DataDictionaryMemcached().get("行业类型-打分"));
		context.put("type",service.toFindIndustryMemcache());
		context.put("custTypes", new DataDictionaryMemcached().get("客户类型"));
		return new ReplyHtml(VM.html(ns + "SecuEvaluateAdd.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = {"参数配置", "评分设置", "打分模板", "修改"})
	public Reply updateUI() {
		this.getParameters();
		Map<String, Object> self = service.querySelfByid(m);
		m.put("INDUSTRY_TYPE", self.get("INDUSTRY_TYPE"));
		// context.put("pqsoft", service.queryByid(m));
		context.put("pqsoft", service.queryTreeByid(m));
		context.put("self", self);

		List<Map<String, Object>> list = service.queryMouldNorm(m);
		// if(StringUtils.isBlank(list)||list.size()==0){
		// list=new EvaluateDictionaryService().getTypesEvaluateDictionary(m);
		// }
		context.put("norm", list);

		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
//		context.put("hytype", new DataDictionaryMemcached().get("行业类型-打分"));
		context.put("hytype", service.toFindIndustryMemcache());
		context.put("custTypes", new DataDictionaryMemcached().get("客户类型"));
		context.put("autoValue", new DataDictionaryMemcached().get("承租人自动打分参数"));
		return new ReplyHtml(VM.html(ns + "SecuEvaluateUpdate.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = {"参数配置", "评分设置", "打分模板", "复制"})
	public Reply copyUI() {
		this.getParameters();
		context.put("pqsoft", service.queryByid(m));
		context.put("self", service.querySelfByid(m));
		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		context.put("type", new DataDictionaryMemcached().get("行业类型-打分"));
		context.put("custTypes", new DataDictionaryMemcached().get("承租人类型"));
		context.put("autoValue", new DataDictionaryMemcached().get("承租人自动打分参数"));
		return new ReplyHtml(VM.html(ns + "SecuEvaluateCopy.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = {"参数配置", "评分设置", "打分模板", "自动取值"})
	public Reply autoValueUI() {
		this.getParameters();
		context.put("pqsoft", service.queryByid(m));
		context.put("self", service.querySelfByid(m));
		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		context.put("type", new DataDictionaryMemcached().get("行业类型-打分"));
		context.put("custTypes", new DataDictionaryMemcached().get("承租人类型"));
		context.put("autoValue", new DataDictionaryMemcached().get("承租人自动打分参数"));
		return new ReplyHtml(VM.html(ns + "SecuEvaluateAutoValue.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "增加" })
	public Reply add() {
		this.getParameters();
		JSONObject m = JSONObject.fromObject(SkyEye.getRequest().getParameter("fromData"));
		JSONArray jsonArray = m.getJSONArray("aa");
		for (int i = 0; i < jsonArray.size(); i++) {
			Map map = (JSONObject) jsonArray.get(i);

			if ("1".equals(map.get("norm") + "")) {
				service.saveMouldOfNorm(map);
			} else {
				service.add(map);
			}
		}
		return new ReplyAjax(m);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "修改" })
	public Reply update() {
		this.getParameters();
		m.put("ID", m.get("id"));
		service.deleted(m);
		JSONObject m = JSONObject.fromObject(SkyEye.getRequest().getParameter("fromData"));
		JSONArray jsonArray = m.getJSONArray("aa");
		for (int i = 0; i < jsonArray.size(); i++) {
			Map map = (JSONObject) jsonArray.get(i);

			if ("1".equals(map.get("norm") + "")) {
				service.saveMouldOfNorm(map);
			} else {
				service.add(map);
			}
		}
		return new ReplyAjax(m);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "查询" })
	public Reply query() {
		this.getParameters();
		Map<String, Object> self = service.querySelfByid(m);
		// context.put("pqsoft", service.queryByid(m));
		context.put("pqsoft", service.queryTreeByid(m));
		context.put("self", self);
		m.put("INDUSTRY_TYPE", self.get("INDUSTRY_TYPE"));
		context.put("norm", service.queryMouldNorm(m));

		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
//		context.put("type", new DataDictionaryMemcached().get("行业类型-打分"));
		context.put("type", service.toFindIndustryMemcache());
		context.put("custTypes", new DataDictionaryMemcached().get("客户类型"));
		context.put("autoValue", new DataDictionaryMemcached().get("承租人自动打分参数"));
		return new ReplyHtml(VM.html(ns + "SecuEvaluateQuery.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "查询" })
	public Reply querybyid() {
		this.getParameters();

		// service.queryByid(m);
		return new ReplyAjax(null);
	}

	/* 如查询到有重复，把id加上1毫秒再次验证，知道不重复后返回加了多少毫秒 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "修改" })
	public Reply checkId() {
		this.getParameters();
		String num = service.checkId(m);
		m.put("NUM", num);
		return new ReplyAjax(m);
	}

	/* 根据parentid由ajax查询 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "修改" })
	public Reply querybyparentid() {
		this.getParameters();
		List list = service.queryByParentid(m);
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (map.get("TITLE") != null) {
				str = str + "<tr><td id='" + map.get("ID") + "' calss='" + map.get("PARENTSID") + "'>" + map.get("TITLE") + "</td></tr>";
			} else {
				str = str + "<td id='" + map.get("ID") + "' calss='" + map.get("PARENTSID") + "'>" + map.get("CONTENT") + "</td>";
			}
		}
		m.put("STR", str);
		return new ReplyAjax(m);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "删除" })
	public Reply deleted() {
		this.getParameters();
		service.deleted(m);
		return new ReplyAjax(true);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "复制" })
	public Reply doCopy() {
		this.getParameters();
		service.doCopy(m);
		return this.toManager();
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "打分" })
	public Reply doGradeScore() {
		this.getParameters();
		Map<String, Object> map = service.querySecuEvalInfo(m);
		if (map == null) { throw new ActionException("未找到对应的打分模板,请配置后重试!", null); }

		// 打分模板ID
		m.put("id", map.get("ID"));
		// 对应打分模板明细项
		List<Map> xx = (List<Map>) service.queryTreeByid(m);
		context.put("pqsoft", xx);
		// 对应打分模板
		Map<String, Object> xx1 = service.querySelfByid(m);
		context.put("self", xx1);

		List<Map<String, Object>> normList = service.queryMouldNorm(m);
		NormScoreService normService = new NormScoreService(m);

		// 循环计算定量打分项分值
		for (Map<String, Object> normMap : normList) {
			System.out.println("normMap------" + normMap);
			normMap.put("SCORE", normService.getNormScore(normMap));
		}

		context.put("norm", normList);
		context.put("prarm", m);

		return new ReplyHtml(VM.html(ns + "doGradeScore.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置", "评分设置", "打分模板", "打分" })
	public Reply saveCustGrade() {
		this.getParameters();
		System.out.println(m);
		return new ReplyAjax(service.insertCustGrade(m), m);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "合作机构","渠道管理", "打分" })
	public Reply doGradeScoreSupp() {
		this.getParameters();
		Map<String, Object> map = service.querySecuEvalInfo(m);
		if (map == null) { throw new ActionException("未找到对应的打分模板,请配置后重试!", null); }

		// 打分模板ID
		m.put("id", map.get("ID"));
		// 对应打分模板明细项
		context.put("pqsoft", service.queryTreeByid(m));
		// 对应打分模板
		context.put("self", service.querySelfByid(m));

		List<Map<String, Object>> normList = service.queryMouldNorm(m);
		NormScoreService normService = new NormScoreService(m);

		// 循环计算定量打分项分值
		for (Map<String, Object> normMap : normList) {
			normMap.put("SCORE", normService.getNormScore(normMap));
		}

		context.put("norm", normList);
		context.put("prarm", m);

		return new ReplyHtml(VM.html(ns + "doGradeScore.vm", context));
	}

	// TODO start wuyanfei

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "项目管理", "资信管理", "担保人打分" })
	public Reply updateGradeForDBR() {
		this.getParameters();
		System.out.println(m);
		return new ReplyAjax(service.updateGradeForDBR(m), m);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "设置中心", "经销商管理", "打分" })
	public Reply updateGradeForDLS() {
		this.getParameters();
		System.out.println(m);
		return new ReplyAjax(service.updateGradeForDLS(m), m);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	//@aPermission(name = { "设置中心", "产品管理", "打分" })
	public Reply updateGradeForProduct() {
		this.getParameters();
		System.out.println(m);
		return new ReplyAjax(service.updateGradeForProduct(m), m);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "项目管理", "资信报告管理", "信誉打分" })
	public Reply updateGradeForZXXY() {
		this.getParameters();
		System.out.println(m);
		return new ReplyAjax(service.updateGradeForZXXY(m), m);
	}

}

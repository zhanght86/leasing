package com.pqsoft.base.suppliersInfo.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.suppliersInfo.service.SuppMainRelationService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
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
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class SuppMainRelationAction extends Action {
	private String pagePath = "base/suppMainRelation/";
	private SuppMainRelationService service = new SuppMainRelationService();

	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * 经销商从业经历
	 * 
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月1日,上午10:21:18
	 */
	@aDev(code = "170026", email = "whlpqsoft@163.cn", name = "王海龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "进入经销商从业经历" })
	public Reply findMgSuppWorkExp1() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath + "toMgSuppWorkExp.vm", context));
	}
	/**
	 * 查看经销商从业经历
	 * 
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月1日,上午10:21:18
	 */
	@aDev(code = "180006", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "进入经销商从业经历" })
	public Reply findMgSuppWorkExp2() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath + "toMgSuppWorkExpDetail.vm", context));
	}

	/**
	 * 经销商
	 * 
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月1日,上午10:21:56
	 */
	@aDev(code = "170026", email = "whlpqsoft@163.cn", name = "王海龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商从业经历" })
	public Reply toSuppExperience() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "suppExperience/toSuppExperience.vm", context));
	}
	
	/**
	 * 查看经销商
	 * 
	 * @return
	 * @author wanghl
	 * @datetime 2015年9月1日,上午10:21:56
	 */
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商从业经历" })
	public Reply toSuppExperience1() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "suppExperience/toSuppExperienceDetail.vm", context));
	}

	@aDev(code = "170026", email = "whlpqsoft@163.cn", name = "王海龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商从业经历数据列表" })
	public Reply findSuppExperience() {
		Map<String, Object> param = _getParameters();

		return new ReplyAjaxPage(service.getWorkExpDetail(param));
	}

	@aDev(code = "170026", email = "whlpqsoft@163.cn", name = "王海龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商从业经历-添加" })
	public Reply doInsertWoekExp() {
		Map<String, Object> map = _getParameters();
		map = JSONObject.fromObject(map.get("param"));

		map.put("USERCODE", Security.getUser().getCode());
		int i = service.doInsertWoekExp(map);
		boolean flag = false;
		String msg = "";
		if (i > 0) {
			flag = true;
			msg = "添加从业经历成功";
		} else {
			flag = false;
			msg = "添加从业经历失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("渠道管理", "添加从业经验", map.get("USERCODE").toString()));
	}

	@aDev(code = "170026", email = "whlpqsoft@163.cn", name = "王海龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商从业经历修改" })
	public Reply getUpdateExperience() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("project_type", new DataDictionaryMemcached().get("项目类型"));
		context.put("workExp", service.getExperience(map));
		return new ReplyHtml(VM.html(pagePath + "suppExperience/toUpdateExperience.vm", context));
	}

	@aDev(code = "170026", email = "whlpqsoft@163.cn", name = "王海龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商从业经历修改-保存" })
	public Reply doUpdateExperence() {
		Map<String, Object> map = _getParameters();
		map = JSONObject.fromObject(map.get("param"));
		map.put("USERCODE", Security.getUser().getCode());
		int i = service.doUpdateExperence(map);
		boolean flag = false;
		String msg = "";
		if (i > 0) {
			flag = true;
			msg = "修改从业经验成功";
		} else {
			flag = false;
			msg = "修改从业经验失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("渠道管理", "修改从业经验", map.get("USERCODE").toString()));
	}

	@aDev(code = "170026", email = "whlpqsoft@163.cn", name = "王海龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商从业经历删除" })
	public Reply delWoekExp() {
		Map<String, Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());
		int i = service.delWoekExp(map);
		boolean flag = false;
		String msg = "";
		if (i > 0) {
			flag = true;
			msg = "删除从业经验成功";
		} else {
			flag = false;
			msg = "删除从业经验失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("渠道管理", "删除从业经验", map.get("USERCODE").toString()));
	}
}

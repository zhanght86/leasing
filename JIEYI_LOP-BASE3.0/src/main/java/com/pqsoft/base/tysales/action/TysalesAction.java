package com.pqsoft.base.tysales.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.suppliersInfo.service.SuppMainRelationService;
import com.pqsoft.base.tysales.service.TysalesService;
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

public class TysalesAction extends Action{
	
	private String pagePath = "base/tysales/";
	private TysalesService service = new TysalesService();
	
	@Override
	public Reply execute() {
		return null;
	}
	
	/**
	 * 经销商三年的销售状况
	 * 
	 * @return
	 * @author taoym
	 * @datetime 2015年8月31日,下午16:21:18
	 */
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "三年的销售情况" })
	public Reply findMgTysales() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath + "Tysales.vm", context));
	}
	
	/**
	 * 经销商三年的销售状况
	 * 
	 * @return
	 * @author taoym
	 * @datetime 2015年8月31日,下午16:21:18
	 */
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "三年的销售情况" })
	public Reply findMgTysalesDetail() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath + "TysalesDetail.vm", context));
	}
	
	/**
	 * 经销商
	 * 
	 * @return
	 * @author taoym
	 * @datetime 2015年9月1日,上午8:21:56
	 */
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "三年的销售情况" })
	public Reply toSuppTysales() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "suppTysales/toSuppTysales.vm", context));
	}
	
	/**
	 * 经销商
	 * 
	 * @return
	 * @author taoym
	 * @datetime 2015年9月1日,上午8:21:56
	 */
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "三年的销售情况" })
	public Reply toSuppTysalesDetail() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "suppTysales/toSuppTysalesDetail.vm", context));
	}
	
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商三年的销售情况数据列表" })
	public Reply findSuppTysales() {
		Map<String, Object> param = _getParameters();

		return new ReplyAjaxPage(service.getTysalesDetail(param));
	}
	
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商三年的销售情况-添加" })
	public Reply doInsertTysales() {
		Map<String, Object> map = _getParameters();
		map = JSONObject.fromObject(map.get("param"));

		map.put("USER_ID", Security.getUser().getCode());
		int i = service.doInsertTysales(map);
		boolean flag = false;
		String msg = "";
		if (i > 0) {
			flag = true;
			msg = "添加经销商三年的销售情况成功";
		} else {
			flag = false;
			msg = "添加经销商三年的销售情况失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("渠道管理", "添加经销商三年的销售情况", map.get("USER_ID").toString()));
	}
	
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商三年的销售情况-修改" })
	public Reply getUpdateTysales() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("workExp", service.getTysales(map));
		return new ReplyHtml(VM.html(pagePath + "suppTysales/toUpdateTysales.vm", context));
	}
	
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商三年的销售情况修改-保存" })
	public Reply doUpdateTysales() {
		Map<String, Object> map = _getParameters();
		map = JSONObject.fromObject(map.get("param"));
		map.put("USER_ID", Security.getUser().getCode());
		int i = service.doUpdateTysales(map);
		boolean flag = false;
		String msg = "";
		if (i > 0) {
			flag = true;
			msg = "修改三年的销售情况成功";
		} else {
			flag = false;
			msg = "修改三年的销售情况失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("渠道管理", "修改三年的销售情况", map.get("USER_ID").toString()));
	}
	
	@aDev(code = "180004", email = "tao_yan_min@163.com", name = "陶言民")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合作机构", "渠道管理", "修改", "经销商三年的销售情况删除" })
	public Reply delTysales() {
		Map<String, Object> map = _getParameters();
		map.put("USER_ID", Security.getUser().getCode());
		int i = service.delTysales(map);
		boolean flag = false;
		String msg = "";
		if (i > 0) {
			flag = true;
			msg = "删除三年的销售情况成功";
		} else {
			flag = false;
			msg = "删除三年的销售情况失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("渠道管理", "删除三年的销售情况", map.get("USER_ID").toString()));
	}

}

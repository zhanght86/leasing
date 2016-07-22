package com.pqsoft.rentDraw.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.rentDraw.service.RentDrawService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * 租金划扣管理
 * @author zhengshangcheng
 *
 */
public class RentDrawAction extends Action {
	
	private RentDrawService service = new RentDrawService();

	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * 进入列表页
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "贷后管理", "租金划扣管理", "进入列表页" })
	@aDev(code = "18003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	public Reply toRentDrawPage(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("rentDraw/rentDraw.vm", context));
	}
	
	/**
	 * 租金划扣列表数据展示
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "贷后管理", "租金划扣管理", "数据展示" })
	@aDev(code = "18003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	public Reply getRentDrawPage(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.getRentDrawPage(param));
	}
	
	/**
	 * 租金划扣管理跑批插入数据
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "贷后管理", "租金划扣管理", "数据展示" })
	@aDev(code = "18003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	public void re(){
		service.refreshRentDraw();
	}
	
	/**
	 * 进入租金划扣明细页面
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "贷后管理", "租金划扣管理", "明细" })
	@aDev(code = "18003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	public Reply toMgshowDetail(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("rentDraw/rentDrawDetail.vm", context));
	}
	
	/**
	 * 租金划扣列表数据展示
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "贷后管理", "租金划扣管理", "数据展示" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply getRentDrawDetailPage(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.getRentDrawDetailPage(param));
	}
	
	/**
	 * 进入租金划扣日志明细页面
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "贷后管理", "租金划扣管理", "明细" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply toMgshowLog(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("rentDraw/rentDrawLog.vm", context));
	}
	
	/**
	 * 租金划扣日志列表数据展示
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "贷后管理", "租金划扣管理", "数据展示" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply getRentDrawLogPage(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.getRentDrawLogPage(param));
	}
	
}

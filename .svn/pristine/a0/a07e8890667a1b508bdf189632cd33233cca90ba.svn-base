package com.pqsoft.base.interestRateConfig.action;

/**
 * 利率配置
 * 
 * @author hxl
 *         T_SYS_RATE
 *         ID NUMBER(6),
 *         ADJUST_TIME DATE,
 *         SIX_MONTHS FLOAT,
 *         ONE_YEAR FLOAT,
 *         ONE_THREE_YEARS FLOAT,
 *         THREE_FIVE_YEARS FLOAT,
 *         OVER_FIVE_YEARS FLOAT,
 *         REMARK VARCHAR2(400)
 */
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.interestRateConfig.service.InterestRateConfigService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class InterestRateConfigAction extends Action {

	private InterestRateConfigService service = new InterestRateConfigService();

	/* 用于点击利率配置得到返回的页面 */
	@Override
	@aPermission(name = { "参数配置", "利率配置", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("InterestContext", param);// 用法待研究
		return new ReplyHtml(VM.html("base/interestRateConfig/interestRateShow.vm", context));
	}

	/* 用于分页查询 */
	@aPermission(name = { "参数配置", "利率配置", "列表显示" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}

	/* 用于保存修改后的利率配置 */
	@aPermission(name = { "参数配置", "利率配置", "修改" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doUpdateInterestRate() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.updateInterestRate(param);
		if (result > 0) {
			flag = true;
			msg = "保存成功！";
		} else {
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	/* 用于插入一条新的利率配置 */
	@aPermission(name = { "参数配置", "利率配置", "增加" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doAddInterestRate() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.insertInterestRate(param);
		System.out.println(result);
		if (result > 0) {
			flag = true;
			msg = "保存成功！";
		} else {
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	/* 用于删除一条利率配置 */
	@aPermission(name = { "参数配置", "利率配置", "删除" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doDeleteInterestRate() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.deleteInterestRate(param);
		if (result > 0) {
			flag = true;
			msg = "保存成功！";
		} else {
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag, msg);
	}

}

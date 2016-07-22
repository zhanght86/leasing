package com.pqsoft.adjustRate.action;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.velocity.VelocityContext;

import com.pqsoft.adjustRate.service.AdjustRateService;
import com.pqsoft.adjustRate.service.AdjustRateThread;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

/**
 * 调息
 * 
 * @author: King 2014年9月10日
 */
public class AdjustRateAction extends Action {

	private String path = "adjustRate/";

	private AdjustRateService service = new AdjustRateService();

	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "参数配置", "调息管理", "列表显示" })
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("BaseRate", service.queryNewBaseRate());
		return new ReplyHtml(VM.html(path + "adjustRateMg.vm", context));
	}

	/**
	 * 调息列表显示查询
	 * 
	 * @return
	 * @author King 2014年9月11日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doShowAdjustRateMg() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.doShowAdjustRateMg(param));
	}

	/**
	 * 查看明细
	 * 
	 * @return
	 * @author King 2014年9月11日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply showAdjustDetail() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("list", service.showAdjustDetail(param));
		context.put("param",param);
		return new ReplyHtml(VM.html(path + "showAdjustDetail.vm", context));
	}
	
	/**
	 * 获取调息的批次
	 * @return
	 * @author King 2014年9月23日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "调息管理", "调息" })
	public Reply getBatch(){
		return new ReplyAjax(true,service.getBatch(),"");
	}
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "参数配置", "调息管理", "调息" })
	public Reply adjustRate() {
		Map<String, Object> param = _getParameters();
		// 查询调息支付表
		List<Map<String, Object>> list = service.showRentPlanHead(param);
		if ((list.isEmpty() || list.size() < 1)&&"0".equals(param.get("COUNT").toString().trim())) {
			throw new ActionException("没有符合调息的还款计划");
		}else if (list.isEmpty() || list.size() < 1){
			return new ReplyAjax(true,"","end").addOp(new OpLog("调息管理", "调息", "基于"
					+ param.get("DEPEND_TIME") + "调息"));
		}

		// 记录调息结果
		// 查询央行调整利息（查询最近两次的利息）
		Map<String, Object> rate = service.getRateAdjust();
		ExecutorService pool = Executors.newFixedThreadPool(4);
		for (Map<String, Object> map : list) {
			map.put("STARTDATE", param.get("ADJUSTRATEDATE"));
			map.put("DEPEND_TIME", param.get("DEPEND_TIME"));
			map.put("CREATE_CODE", Security.getUser().getCode());
			map.put("CREATE_NAME", Security.getUser().getName());
			//获取调息批次
			map.put("BATCH", param.get("BATCH"));
			
			pool.execute(new AdjustRateThread(map,rate));
			
		}		
		return new  ReplyAjax(true,"","end").addOp(new OpLog("调息管理", "调息", "基于"
				+ param.get("DEPEND_TIME") + "调息"));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "参数配置", "调息管理", "数据恢复" })
	public Reply returnPay() {
		Map<String, Object> param = _getParameters();
		if (service.checkPay(param) == 0) {
			try {
				List<Map<String, Object>> list = service
						.queryhfPayList(param);
				for (Map<String, Object> map : list) {
					map.put("BATCH", param.get("BATCH"));
					service.doDelRentDetail(map.get("PAY_ID"));
					service.doDelRentHead(map.get("PAY_ID"));
					map = service.queryhfPayList(map).get(0);
					map.put("NEWID", map.get("PAY_ID"));
					map.put("BATCH", param.get("BATCH"));
					service.synchronizationBeginning(map);
					service.doUpdateRentStatus(map);
				}
			} catch (Exception e) {
				throw new ActionException("恢复失败:" + e.getMessage());
			}
			return new ReplyAjax(true,"","恢复成功").addOp(new OpLog("调息管理", "数据恢复", param + ""));
		} else {
			return new ReplyAjax(false, "", "请从最新调息的记录开始恢复");
		}
	}
}

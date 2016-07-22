package com.pqsoft.project.finance_Project.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.project.finance_Project.service.FinanceMainService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class FinanceMainAction extends Action {
	private final Logger logger = Logger.getLogger(this.getClass());
	public VelocityContext context = new VelocityContext();
	FinanceMainService service = new FinanceMainService();

	@Override
	public Reply execute() {
		return null;
	}

//	@aPermission(name = {"客户管理", "立项", "法人财报","录入" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply finance_Add() {
		Map<String, Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());
		context.put("PARAM", map);
		if (map.get("SORT").equals("1")) {
			logger.debug("资产负债表");
			if (map.get("PROJECT_ID") == null || map.get("PROJECT_ID").equals("")) {
				return new ReplyHtml(VM.html("project/finance_Project/finance_debtUpdate.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "资产负债表查询错误"));
			}
			return this.AddDebtView(context);
		} else if (map.get("SORT").equals("2")) {
			logger.debug("利润及利润分配表");

			if (map.get("PROJECT_ID") == null || map.get("PROJECT_ID").equals("")) {
				return new ReplyHtml(VM.html("project/finance_Project/finnace_ProfitDistribution.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "利润及利润分配表查询错误"));
			}
			return this.getFinnaceProfitDistribution(context);

		} else if (map.get("SORT").equals("3")) {
			logger.debug("现金流量表");

			if (map.get("PROJECT_ID") == null || map.get("PROJECT_ID").equals("")) {
				return new ReplyHtml(VM.html("project/finance_Project/finnace_Cash.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "现金流量表查询错误"));
			}
			return this.getFinnaceCridit_Cash(context);

		}
		return null;
	}

//	@aPermission(name = {"客户管理", "立项", "法人财报","查看" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply finance_Show() {

		Map<String, Object> map = _getParameters();
		context.put("PARAM", map);
		if (map.get("SORT").equals("1")) {
			logger.debug("资产负债表");
			return this.toShowDebtView();
		} else if (map.get("SORT").equals("2")) {
			logger.debug("利润及利润分配表");
			this.getFinnaceProfitDistributionView();
		} else if (map.get("SORT").equals("3")) {
			logger.debug("现金流量表");
			return this.getFinnaceCridit_CashView();
		}
		return new ReplyHtml(VM.html("project/finance_Project/finnace_ProfitView.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "利润及利润分配表查询错误"));
	}

//	@aPermission(name = { "项目管理", "项目一览", "修改财报" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply finance_Update() {
		Map<String, Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());
		context.put("PARAM", map);
		if (map.get("SORT").equals("1")) {
			logger.debug("资产负债表");
			if (map.get("PROJECT_ID") == null || map.get("PROJECT_ID").equals("")) {
				return new ReplyHtml(VM.html("project/finance_Project/finance_debtUpdate.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "资产负债表查询错误"));
			}
			return this.AddDebtView(context);
		} else if (map.get("SORT").equals("2")) {
			logger.debug("利润及利润分配表");

			if (map.get("PROJECT_ID") == null || map.get("PROJECT_ID").equals("")) {
				return new ReplyHtml(VM.html("project/finance_Project/finnace_ProfitDistribution.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "利润及利润分配表查询错误"));
			}
			return this.getFinnaceProfitDistribution(context);

		} else if (map.get("SORT").equals("3")) {
			logger.debug("现金流量表");

			if (map.get("PROJECT_ID") == null || map.get("PROJECT_ID").equals("")) {
				return new ReplyHtml(VM.html("project/finance_Project/finnace_Cash.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "现金流量表查询错误"));
			}
			return this.getFinnaceCridit_Cash(context);

		}
		return null;
	}

//	@aPermission(name = { "客户管理", "立项", "法人财报","录入" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply AddDebtView(VelocityContext context) {
		Object o = context.get("PARAM");
		String PROJECT_ID = ((Map<String, Object>) o).get("PROJECT_ID").toString();
		// 获取负债表数据
		Map<String, Object> map = service.initDebt(PROJECT_ID);

		// 获取利润及利润分配表中的加:年初未分配利润（D20：NOT_ASSIGNED_PROFIT）
		Map mp = new HashMap();
		mp.put("PROJECT_ID", PROJECT_ID);
		Map maprisk = service.getFinnaceCridit(mp);

		// 年初未分配利润
		context.put("mapData", maprisk);
		context.put("data", JSONObject.fromObject(map));
		return new ReplyHtml(VM.html("project/finance_Project/finance_debtUpdate.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "现金流量表查询错误"));
	}

	// 齐姜龙
	// 利润及利润分配表 财报
//	@aPermission(name = { "客户管理", "立项", "法人财报","录入" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getFinnaceProfitDistribution(VelocityContext context) {
		Map map = (Map) context.get("PARAM");
		Map mapInfo = (Map) service.getFinnaceProfitDistriButtion(map);
		if (mapInfo == null) {
			mapInfo = new HashMap();
		}

		Map map1 = (Map) service.getFinnaceProfitDistriButtionByMap(map);

		if (map1 != null) {
			Set<String> key = map1.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				Map map2 = JSONObject.fromObject(map1.get(s).toString());
				mapInfo.put(s, map2);
			}

		}

		// 查询资金负债表的未分配利润
		Map mapDebt = (Map) service.getFinnaceDebt(map);
		if (mapDebt != null) {
			Set<String> key = mapDebt.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				Map map2 = JSONObject.fromObject(mapDebt.get(s).toString());
				mapInfo.put(s, map2);
			}
		}

		context.put("data", mapInfo);

		return new ReplyHtml(VM.html("project/finance_Project/finnace_ProfitDistribution.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "利润及利润分配表查询错误"));
	}

	// 齐姜龙
	// 现金流量表 财报
//	@aPermission(name = { "客户管理", "立项", "法人财报","录入" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getFinnaceCridit_Cash(VelocityContext context) {
		Map map = (Map) context.get("PARAM");
		Map mapInfo = (Map) service.getFinnaceCash(map);
		if (mapInfo == null) {
			mapInfo = new HashMap();
		}

		Map map1 = (Map) service.getFinnaceCashByMap(map);

		if (map1 != null) {
			Set<String> key = map1.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				Map map2 = JSONObject.fromObject(map1.get(s).toString());
				mapInfo.put(s, map2);
			}

		}
		// 查询资金负债表的未分配利润
		Map mapDebt = (Map) service.getFinnaceDebt(map);
		if (mapDebt != null) {
			Set<String> key = mapDebt.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				Map map2 = JSONObject.fromObject(mapDebt.get(s).toString());
				mapInfo.put(s, map2);
			}
		}

		// 查询利润及利润分配表的净利润
		Map mapDis = (Map) service.getFinnaceDis(map);
		if (mapDis != null) {
			Set<String> key = mapDis.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				Map map2 = JSONObject.fromObject(mapDis.get(s).toString());
				mapInfo.put(s, map2);
			}
		}

		context.put("data", mapInfo);

		return new ReplyHtml(VM.html("project/finance_Project/finnace_Cash.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "现金流量表查询错误"));
	}

//	@aPermission(name = { "客户管理", "立项", "法人财报","录入" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply AddDebtView() {
		Object o = context.get("PARAM");
		String PROJECT_ID = ((Map<String, Object>) o).get("PROJECT_ID").toString();

		// 获取负债表数据
		Map<String, Object> map = service.initDebt(PROJECT_ID);

		// 获取利润及利润分配表中的加:年初未分配利润（D20：NOT_ASSIGNED_PROFIT）
		Map mp = new HashMap();
		mp.put("PROJECT_ID", PROJECT_ID);
		Map maprisk = service.getFinnaceCridit(mp);

		// 年初未分配利润
		context.put("mapData", maprisk);
		context.put("data", JSONObject.fromObject(map));
		return new ReplyHtml(VM.html("project/finance_Project/finance_debtUpdate.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "资产负债表查询错误"));
	}

//	@aPermission(name = { "客户管理", "立项", "法人财报","查看" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toShowDebtView() {
		Object o = context.get("PARAM");

		String PROJECT_ID = ((Map<String, Object>) o).get("PROJECT_ID").toString();
		// 获取负债表数据
		Map<String, Object> map = service.initDebt(PROJECT_ID);

		// 获取利润及利润分配表中的加:年初未分配利润（D20：NOT_ASSIGNED_PROFIT）
		Map mp = new HashMap();
		mp.put("PROJECT_ID", PROJECT_ID);
		Map maprisk = new HashMap();
		try {
			if (!map.isEmpty()) {
				maprisk = service.getFinnaceCridit(map);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}

		// 年初未分配利润
		context.put("mapData", maprisk);

		context.put("data", JSONObject.fromObject(map));
		return new ReplyHtml(VM.html("project/finance_Project/finance_debtView.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "资产负债表查询错误"));
	}

	// 齐姜龙
	// 利润及利润分配表 财报
//	@aPermission(name = { "客户管理", "立项", "法人财报","查看" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getFinnaceProfitDistributionView() {
		Map map = (Map) context.get("PARAM");
		Map mapInfo = (Map) service.getFinnaceProfitDistriButtion(map);
		if (mapInfo == null) {
			mapInfo = new HashMap();
		}

		Map map1 = (Map) service.getFinnaceProfitDistriButtionByMap(map);
		if (map1 != null) {
			Set<String> key = map1.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				Map map2 = JSONObject.fromObject(map1.get(s).toString());
				mapInfo.put(s, map2);
			}

		}
		context.put("data", mapInfo);
		return new ReplyHtml(VM.html("project/finance_Project/finnace_ProfitView.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "利润及利润分配表查询错误"));
	}

//	@aPermission(name = { "客户管理", "立项", "法人财报","查看" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getFinnaceCridit_CashView() {
		Map map = (Map) context.get("PARAM");

		Map mapInfo = (Map) service.getFinnaceCash(map);
		if (mapInfo == null) {
			mapInfo = new HashMap();
		}

		Map map1 = (Map) service.getFinnaceCashByMap(map);

		if (map1 != null) {
			Set<String> key = map1.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				Map map2 = JSONObject.fromObject(map1.get(s).toString());
				mapInfo.put(s, map2);
			}

		}

		context.put("data", mapInfo);

		return new ReplyHtml(VM.html("project/finance_Project/finnace_CashView.vm", context)).addOp(new OpLog("客户管理", "立项-法人财报", "现金流量表查询错误"));
	}

//	@aPermission(name = {  "客户管理", "立项", "法人财报","录入" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doAddDebt() {
		boolean flag = true;
		Map<String, Object> map = _getParameters();
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_CODE", Security.getUser().getCode());
		flag = service.insertDebt(map);
		return new ReplyHtml(flag).addOp(new OpLog("客户管理", "立项-创建项目", "保存资产负债表信息"));
	}

	// 齐姜龙
	// 现金流量表保存 财报
//	@aPermission(name = { "客户管理", "立项", "法人财报","录入" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply saveFinnaceCash() {
		Map map = _getParameters();
		Map jbject = JSONObject.fromObject(map.get("ChangeViewData"));
		int returnValue = service.saveFinnaceCash(jbject);
		boolean message = true;
		if (returnValue > 0) {
			message = true;
		} else {
			message = false;
		}
		return new ReplyHtml(message).addOp(new OpLog("客户管理", "立项-创建项目", "保存现金流量表信息"));
	}

	// 齐姜龙
	// 利润及利润分配表保存 财报
//	@aPermission(name = { "客户管理", "立项", "法人财报","录入" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply saveFinnaceProfitDistribution() {
		Map map = _getParameters();
		Map jbject = JSONObject.fromObject(map.get("ChangeViewData"));
		int returnValue = service.saveFinnaceProfitDistriButtion(jbject);
		boolean message = true;
		if (returnValue > 0) {
			message = true;
		} else {
			message = false;
		}
		return new ReplyHtml(message).addOp(new OpLog("客户管理", "立项-创建项目", "保存利润及利润分配表信息"));
	}
}

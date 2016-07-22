package com.pqsoft.base.stock.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.stock.service.StockManagerService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

public class StockManagerAction extends Action {
	private String path = "base/stock/";
	private StockManagerService stockService = new StockManagerService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "库存管理", "列表" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "stockManager.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "库存管理", "查询" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getStockPageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = stockService.getStockManager(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "库存管理", "修改" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showAddUpStockPage() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		SuppliersService supService = new SuppliersService();
		context.put("companys", supService.getAlllCompany());
		List<Object> suppliers = stockService.getSupByCompanyId(param);
		context.put("supplier", suppliers);
		List<Object> PLATFORM_TYPE = new SysDictionaryMemcached().get("业务类型");
		context.put("platform_types", PLATFORM_TYPE);
		if (!param.containsKey("STOCK_ID")
				|| (param.containsKey("STOCK_ID") && param.get("STOCK_ID") != null && !"".equals(StringUtils.nullToString(param.get("STOCK_ID"))))) {
			Map<String, Object> stockMess = stockService.getOneStock(param);
			context.put("stock", stockMess);
		}
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "showEditStockPage.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getSupplierMess() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> newParam = new HashMap<String, Object>();
		List<Object> suppliers = stockService.getSupByCompanyId(param);
		newParam.put("SUPPLIER", suppliers);
		return new ReplyJson(JSONObject.fromObject(newParam));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "库存管理", "保存" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply saveStockMess() {
		Map<String, Object> param = _getParameters();
		int result = 0;
		if (param.containsKey("STOCK_ID") && param.get("STOCK_ID") != null && !"".equals(StringUtils.nullToString(param.get("STOCK_ID")))) {
			result = stockService.updateStockMess(param);
		} else {
			result = stockService.addStockMess(param);
		}
		if (result > 0) {
			return new ReplyAjax(true, "操作成功！");
		} else {
			return new ReplyAjax(false, "操作失败");
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "库存管理", "查看" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showStockDetail() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		Map<String, Object> oneStockMess = stockService.getOneStock(param);
		context.put("stock", oneStockMess);
		return new ReplyHtml(VM.html(path + "showStockDetailPage.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "库存管理", "删除" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delStockMethod() {
		Map<String, Object> param = _getParameters();
		int result = stockService.delStock(param);
		if (result > 0) {
			return new ReplyAjax(true, "删除成功！");
		} else {
			return new ReplyAjax(false, "删除失败！");
		}
	}

}

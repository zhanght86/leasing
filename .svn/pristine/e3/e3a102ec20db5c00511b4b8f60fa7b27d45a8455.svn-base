package com.pqsoft.analysisBySynthesis.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.analysisBySynthesis.service.IndustryService;
import com.pqsoft.customers.service.FinancialStatisticsService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class IndustryAction extends Action {

	private final String pagePath = "analysisBySynthesis/industry/";
	
	//****************************************************************进入行业管理页面***********************************************************************/
	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"权限管理", "行业管理", "列表显示"})
	public Reply execute() {
		// TODO Auto-generated method stub
	   return  new ReplyHtml(VM.html(pagePath+"toMgIndustry.vm", null));
	}
	
	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgIndustry() {
		Map<String, Object> param = _getParameters();
		IndustryService service = new IndustryService();
		return new ReplyAjaxPage(service.toMgIndustry(param));
	}
	
	//****************************************************************进入添加行业信息页面***********************************************************************/
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"权限管理", "行业管理", "添加" })
	public Reply toAddIndustry() {
		Map<String,Object> param = _getParameters();
		param.put("HY_ID", Dao.getSequence("SEQ_FIL_INDUSTRY"));//行业序列号
		VelocityContext context = new VelocityContext();	
		context.put("param", param);
		return  new ReplyHtml(VM.html(pagePath+"toAddIndustry.vm", context));
	}
	
	//****************************************************************添加行业分析(三个函数)***********************************************************************/
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doInertIndustry(){
		Map<String,Object> params = _getParameters();		
		params.put("USERCOE", Security.getUser().getCode());//获取用户编号
		IndustryService service = new IndustryService();
		int i = service.doInertIndustry(params);//添加行业基本信息
		String msg = "";
		boolean flag = false;
		if(i>0){
			flag = true;
			msg = "行业信息添加成功";
		}else {
			msg = "行业信息添加失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("权限管理-行业管理","添加行业信息", msg));
	}
	
	//添加行业主要数据
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doInertIndustryChild(){
		Map<String,Object> param = _getParameters();
		JSONObject  Reply = JSONObject.fromObject(param.get("alldata"));
		Reply.put("USER_ID", Security.getUser().getId());
		IndustryService service = new IndustryService();
		int a = service.doInertIndustryChild(Reply);
		String msg = "";
		boolean flag = false;
		if(a>0){
			flag = true;
			msg = "行业主要数据添加成功";
		}else {
			msg = "行业主要数据添加失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("权限管理-行业管理","添加行业主要数据", msg));
	}
	
	//行业分析
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doInertIndustryANALYZE(){
		Map<String,Object> param = _getParameters();
		JSONObject  json = JSONObject.fromObject(param.get("alldata"));
		json.put("USER_ID", Security.getUser().getId());
		IndustryService service = new IndustryService();
		int a = service.doInertIndustryANALYZE(json);
		String msg = "";
		boolean flag = false;
		if(a>0){
			flag = true;
			msg = "行业分析添加成功";
		}else {
			msg = "行业分析添加失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("权限管理-行业管理","添加行业分析", msg));
	}
	
	//****************************************************************查看行业分析***********************************************************************
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"权限管理", "行业管理", "查看行业信息" })
	public Reply toShowIndustry() {
		Map<String,Object> param = _getParameters();
		IndustryService service = new IndustryService();
		VelocityContext context = new VelocityContext();
		context.put("toShowIndustry", service.toShowIndustry(param));
		context.put("toShowIndustryChild", service.toShowIndustryChild(param));
		context.put("toShowIndustryANALYZE", service.toShowIndustryANALYZE(param));
		return  new ReplyHtml(VM.html(pagePath+"toShowIndustry.vm", context));
	}
	

	//****************************************************************加载修改页面***********************************************************************
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"权限管理", "行业管理", "修改行业信息[页面]" })
	public Reply toUpdateIndustry() {
		Map<String,Object> param = _getParameters();
		System.out.println("加载编辑页面 输出获得的参数param：："+param);
		IndustryService service = new IndustryService();
		VelocityContext context = new VelocityContext();
		context.put("toShowIndustry", service.toShowIndustry(param));
		context.put("toShowIndustryChild", service.toShowIndustryChild(param));
		context.put("toShowIndustryANALYZE", service.toShowIndustryANALYZE(param));
		System.out.println("加载编辑页面 输出提交给编辑页面的参数context：："+context);
		return  new ReplyHtml(VM.html(pagePath+"toUpdateIndustry.vm", context));
	}
	
	//****************************************************************修改行业分析(三个函数)***********************************************************************
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doUpdateINDUSTRY(){
		Map<String,Object> params = _getParameters();
		Map<String,Object> param =JSONObject.fromObject(params.get("json").toString());
		param.put("USEERID", Security.getUser().getId());
		IndustryService service = new IndustryService();
		int i = service.doUpdateINDUSTRY(param);
		String msg = "";
		boolean flag = false;
		if(i>0){
			flag = true;
			msg = "行业基础数据修改成功";
		}else {
			msg = "行业基础数据修改失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("权限管理-行业管理","修改行业基础数据", msg));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doUpdateIndustryChild(){
		Map<String,Object> param = _getParameters();
		JSONObject  Reply = JSONObject.fromObject(param.get("alldata"));
		Reply.put("USER_ID", Security.getUser().getId());
		IndustryService service = new IndustryService();
		int a = service.doUpdateINDUSTRYChild(Reply);
		String msg = "";
		boolean flag = false;
		if(a>0){
			flag = true;
			msg = "行业主要数据修改成功";
		}else {
			msg = "行业主要数据修改失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("权限管理-行业管理","修改行业主要数据", msg));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doUpdateANALYZE(){
		Map<String,Object> param = _getParameters();
		JSONObject  Reply = JSONObject.fromObject(param.get("alldata"));
		Reply.put("USER_ID", Security.getUser().getId());
		IndustryService service = new IndustryService();
		int a = service.doUpdateANALYZE(Reply);
		String msg = "";
		boolean flag = false;
		if(a>0){
			flag = true;
			msg = "行业分析修改成功";
		}else {
			msg = "行业分析修改失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("权限管理-行业管理","修改行业分析", msg));
	}
	
	
	//****************************************************************删除行业分析*****************************************************************************
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"权限管理", "行业管理", "删除行业信息" })
	public Reply delINDUSTRY(){
		Map<String,Object> param = _getParameters();
		IndustryService service = new IndustryService();
		int a = service.delINDUSTRY(param);//删除结果返回值0或1 ，
		String msg = "";
		boolean flag = false;
		if(a>0){
			flag = true;
			msg = "行业信息删除成功";
		}else {
			msg = "行业信息删除失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("权限管理-行业管理","删除行业信息", msg)); 
	}	
	/********************************************************************************************************************
	 ********************************************************************************************************************  
	 ***************************以下为财务报表的相关内容  @author 杨雪   @date 2015-02-27*************************************
	 ********************************************************************************************************************
	 *******************************************************************************************************************/
	
	//****************************************************************行业财报查看*****************************************************************************
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@qpsoft.con", name = "杨雪")
	@aPermission(name = {"权限管理", "行业管理", "行业财报查看"})
	public Reply toMgFinancialReport() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		IndustryService service = new IndustryService();
		context.put("param", map);
		context.put("financialList", service.toMgFinancialReport(map));//查看财务总表
		return new ReplyHtml(VM.html(pagePath + "toMgFinancialReport.vm", context));
	}
	
	//****************************************************************添加行业财报主页面（页面头部信息）**************************************************************
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@qpsoft.con", name = "杨雪")
	@aPermission(name = {"权限管理", "行业管理", "行业财报添加"})
	public Reply toAddManagePage() {//进入行业财报主页面
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		FinancialStatisticsService service = new FinancialStatisticsService();
		String seq = service.getFinancialSeq();//获取财务报表序列
		param.put("MANAGEID", seq);
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "toAddFinanceMain.vm", context));
	}
	
	//****************************************************************添加行业财报子页面(资产负债页面， 损益表页面，现金流表， 财务指标页面)*****************************************************************************
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@qpsoft.con", name = "杨雪")
	public Reply toAddFinancial() {//进入行业财报分页面
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();//获取页面参数
		context.put("param", map);
		String sort = (String) map.get("SORT");
		//service.testCalculate(map, context, false);
		if (sort != null && !sort.equals("")) {
			if (sort.equals("1")) {// 资产负债表				
				return new ReplyHtml(VM.html(pagePath + "toAddFinance_debt.vm",
						context));
			} else if (sort.equals("2")) {//损益表
				return new ReplyHtml(VM.html(pagePath
						+ "toAddfinnace_Profit.vm", context));
			} else if (sort.equals("3")) {//现金流
				return new ReplyHtml(VM.html(pagePath + "toAddfinnace_Cash.vm",
						context));
			} else if (sort.equals("4")) {//财务指标			
				return new ReplyHtml(VM.html(pagePath
						+ "toAddfinnace_BankStatistics.vm", context));
			}
		}
		return new ReplyHtml(VM.html("", context));
	}
	
	//****************************************************************保存行业财报(三个函数)***********************************************************************
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")	
	public Reply doInsertDebt() {//操作保存资产负债表
		FinancialStatisticsService service = new FinancialStatisticsService();
		boolean flag = true;
		Map<String, Object> map = _getParameters();
		try {
			int num = service.findFinanceManageById(map);
			if (num < 1) {
				service.insertFinanceManage(map);
			} else {
				service.updateFinanceManageById(map);
			}
			int cashCount = service.selectCountForCashByManagerId(map);
			int profitdisCount = service.selectCountForProfitByManagerId(map);
			if (cashCount > 0) {
				service.updateCashInDateByManagerId(map);
			}
			if (profitdisCount > 0) {
				service.updateProfitInDateByManagerId(map);
			}

			flag = service.insertDebt(map);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			logger.error(e.getMessage());
			flag = false;
		}
		return new ReplyAjax(flag).addOp(new OpLog("行业管理", "行业财报添加", "资产负债[保存]"));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "权限管理", "行业管理", "损益表[保存]" })
	public Reply doInsertProfitDistri() {
		FinancialStatisticsService service = new FinancialStatisticsService();
		Map<String, Object> map = _getParameters();
		int num = service.findFinanceManageById(map);
		if (num < 1) {
			service.insertFinanceManage(map);
		} else {
			service.updateFinanceManageById(map);
		}
		int cashCount = service.selectCountForCashByManagerId(map);
		int debtCount = service.selectCountForDebtByManagerId(map);
		if (cashCount > 0) {
			service.updateCashInDateByManagerId(map);
		}
		if (debtCount > 0) {
			service.updateDebtInDateByManagerId(map);
		}
		int returnValue = service.insertProfitDistri(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
		} else {
			result = "保存失败";
		}
		return new ReplyAjax(result).addOp(new OpLog("行业管理", "行业财报添加-损益表[保存]",result));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "权限管理", "行业管理", "现金流量[保存]" })
	public Reply doInsertCash() {
		FinancialStatisticsService service = new FinancialStatisticsService();
		Map<String, Object> map = _getParameters();
		map.put("OPERATOR_ID", Security.getUser().getId());// 登陆用户编号
		map.put("OPERATOR_NAME", Security.getUser().getName());// 登陆用户姓名
		map.put("OPERATOR_CODE", Security.getUser().getCode());// 登陆用户工号
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_CODE", Security.getUser().getCode());
		int num = service.findFinanceManageById(map);
		if (num < 1) {
			service.insertFinanceManage(map);
		} else {
			service.updateFinanceManageById(map);
		}

		int profitCount = service.selectCountForProfitByManagerId(map);
		int debtCount = service.selectCountForDebtByManagerId(map);
		if (profitCount > 0) {
			service.updateProfitInDateByManagerId(map);
		}
		if (debtCount > 0) {
			service.updateDebtInDateByManagerId(map);
		}

		int returnValue = service.insertCash(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
		} else {
			result = "保存失败";			
		}
		return new ReplyAjax(result).addOp(new OpLog("行业管理", "行业财报添加-现金流量[保存]",result));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = { "权限管理", "行业管理", "财务指标[保存]" })
	public Reply doEditIndex() {
		FinancialStatisticsService service = new FinancialStatisticsService();
		Map<String, Object> map = _getParameters();
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_ID", Security.getUser().getId());
		// /如果记录已存在则先删除再保存
		service.deleteFinanceAnalyzeRecord(map);
		service.updateFinanceManageById(map);
		int returnValue = service.insertIndex(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
		} else {
			result = "保存失败";
		}
		return new ReplyAjax(result).addOp(new OpLog("行业管理", "行业财报添加-财务指标[保存]",result));
	}
	//****************************************************************查看行业财报(主页面)***********************************************************************
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@qpsoft.con", name = "杨雪")
	@aPermission(name = {"权限管理", "行业管理", "行业财报[查看]"})
	public Reply toViewFinancilMain(){
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "toViewFinancilMain.vm", context));
	}
	
	//****************************************************************查看行业财报(三个函数)***********************************************************************
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doShowFinancil() {
		FinancialStatisticsService service = new FinancialStatisticsService();
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();//获取页面参数
		map.put("USERCODE", Security.getUser().getCode());// 登陆用户编号
		context.put("SORT", map.get("SORT"));
		context.put("param", map);
		if (map.get("SORT") != null && !"".equals(map.get("SORT"))) {
			if (map.get("SORT").equals("1")) {
				service.queryDebtByClientid(map, context);
				logger.debug("资产负债表");
				return new ReplyHtml(VM.html(pagePath+ "toShowFinance_debt.vm", context));
			} else if (map.get("SORT").equals("2")) {
				service.queryProfitByID(map, context);
				logger.debug("损益表");
				return new ReplyHtml(VM.html(pagePath+ "toShowfinnace_Profit.vm", context));
			} else if (map.get("SORT").equals("3")) {
				logger.debug("现金流量表");
				service.queryCashById(map, context);
				return new ReplyHtml(VM.html(pagePath+ "toShowfinnace_Cash.vm", context));
			} else if (map.get("SORT").equals("4")) {
				logger.debug("财务指标分析");
				service.queryFinanceAnalyzeById(map, context);
				return new ReplyHtml(VM.html(pagePath+ "toShowfinnace_BankStatistics.vm", context));
			}
		} else {// 默认的查询当前用户的所有任务
			logger.debug("默认的查询当前用户的所有任务");
		}
		return new ReplyHtml(VM.html("", context));
	}
	
	//****************************************************************加载更新行业财报页面(三个函数)***********************************************************************
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doEditFinancil() {
		FinancialStatisticsService service = new FinancialStatisticsService();
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());// 登陆用户编号
		context.put("SORT", map.get("SORT"));
		context.put("param", map);
		if (map.get("SORT") != null && !"".equals(map.get("SORT"))) {
			if (map.get("SORT").equals("1")) {
				service.queryDebtByClientid(map, context);
				logger.debug("资产负债表");
				return new ReplyHtml(VM.html(
						pagePath + "toEditFinance_debt.vm", context));
			} else if (map.get("SORT").equals("2")) {
				service.queryProfitByID(map, context);
				logger.debug("损益表");
				return new ReplyHtml(VM.html(pagePath
						+ "toEditfinnace_Profit.vm", context));
			} else if (map.get("SORT").equals("3")) {
				logger.debug("现金流量表");
				service.queryCashById(map, context);
				return new ReplyHtml(VM.html(
						pagePath + "toEditfinnace_Cash.vm", context));
			} else if (map.get("SORT").equals("4")) {
				logger.debug("财务指标分析");
				service.testCalculate(map, context, true);
				return new ReplyHtml(VM.html(pagePath
						+ "toEditfinnace_BankStatistics.vm", context));
			}
		} else {// 默认的查询当前用户的所有任务
			logger.debug("默认的查询当前用户的所有任务");
		}
		return new ReplyHtml(VM.html("", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doEditDebt() {//更新资产负债表
		boolean flag = true;
		FinancialStatisticsService service = new FinancialStatisticsService();
		Map<String, Object> map = _getParameters();
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_CODE", Security.getUser().getCode());
		try {
			service.deleteDebt(map);
			service.updateFinanceManageById(map);
			int cashCount = service.selectCountForCashByManagerId(map);
			int profitdisCount = service.selectCountForProfitByManagerId(map);
			if (cashCount > 0) {
				service.updateCashInDateByManagerId(map);
			}
			if (profitdisCount > 0) {
				service.updateProfitInDateByManagerId(map);
			}

			flag = service.insertDebt(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			logger.error(e.getMessage());
			flag = false;
		}
		return new ReplyAjax(flag).addOp(new OpLog("权限管理", "行业管理", "资产负债[修改]"));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doEditCash() {//更新现金流量表
		FinancialStatisticsService service = new FinancialStatisticsService();
		Map<String, Object> map = _getParameters();
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_CODE", Security.getUser().getCode());
		service.deleteCashByID(map);
		service.updateFinanceManageById(map);
		int profitCount = service.selectCountForProfitByManagerId(map);
		int debtCount = service.selectCountForDebtByManagerId(map);
		if (profitCount > 0) {
			service.updateProfitInDateByManagerId(map);
		}
		if (debtCount > 0) {
			service.updateDebtInDateByManagerId(map);
		}
		int returnValue = service.insertCash(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
		} else {
			result = "保存失败";
		}
		return new ReplyAjax(result).addOp(new OpLog("权限管理", "行业管理财报更新-现金流量[保存]",result));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doEditProfitDistri() {
		FinancialStatisticsService service = new FinancialStatisticsService();
		Map<String, Object> map = _getParameters();
		service.deleteProfitDistri(map);
		service.updateFinanceManageById(map);
		int cashCount = service.selectCountForCashByManagerId(map);
		int debtCount = service.selectCountForDebtByManagerId(map);
		if (cashCount > 0) {
			service.updateCashInDateByManagerId(map);
		}
		if (debtCount > 0) {
			service.updateDebtInDateByManagerId(map);
		}
		int returnValue = service.insertProfitDistri(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
		} else {
			result = "保存失败";
		}
		return new ReplyAjax(result).addOp(new OpLog("权限管理", "损益表[保存]",result));
	}

	
	//****************************************************************删除行业财报(三个函数)***********************************************************************
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"权限管理","行业管理","财报删除"})
	public Reply deleteFinancilManage() {
		FinancialStatisticsService service = new FinancialStatisticsService();
		boolean flag = false;
		String msg = "";
		Map<String, Object> map = _getParameters();
		map.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		service.deleteDebt(map);//资产负债
		service.deleteProfitDistri(map);//损益表
		service.deleteCashByID(map);//现金流量
		service.deleteFinanceAnalyzeRecord(map);//财报记录
		int a = service.deleteFinancilManage(map);//
		if (a > 0) {
			flag = true;
			msg = "删除成功";
		} else {
			flag = false;
			msg = "删除失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("行业管理", "行业财报删除",msg));
	}
	
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170039", email = "bingyyf@foxmail.com", name = "杨杰")
//	@aPermission(name = {"权限管理", "行业管理", "列表显示" })
//	public Reply toMgIndustryView(){
//		Map<String,Object> param = _getParameters();
//		System.out.println("杨杰：param：："+param);
//		VelocityContext context = new VelocityContext();	
//		context.put("param", param);
//		System.out.println("****************项目ID，资信ID 在这里么 找一找：  "+param);// Add By:YangJ 2014-4-23
//		return  new ReplyHtml(VM.html(pagePath+"toMgIndustryView.vm", context));
//	}
}

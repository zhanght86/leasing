package com.pqsoft.buyBack.action;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.bpm.status.ProjectStatus;
import com.pqsoft.buyBack.service.BuyBackCheckService;
import com.pqsoft.buyBack.service.BuyBackService;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.pigeonhole.service.PigeonholeService;
import com.pqsoft.project.service.ProjectContractManagerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.PoiExcelUtil;
import com.pqsoft.util.Util;

public class BuyBackAction extends Action {

	private BuyBackService service = new BuyBackService();
	private VelocityContext context = new VelocityContext();

	/**
	 * 回购预警申请首页
	 */
//	@aPermission(name = { "资产管理", "回购预警", "列表" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply execute() {
//		Map map = service.queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("buyBack/buyBackList.vm", context));
		return null;
	}
	
	/**
	 * 回购申请首页
	 */
	@aPermission(name = { "项目管理", "项目回购", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply buyBackManagement() {
		Map map = service.queryProductAndCompany();
		context.put("data", map);
		return new ReplyHtml(VM.html("buyBack/buyBackManagementList.vm", context));
	}
	
	/**
	 * 回购申请数据
	 */
	@aPermission(name = { "项目管理", "项目回购", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply buyBackManagementListing() {
		Map<String, Object> param = _getParameters();
		Page page = service.buyBackManagementPage(param);
		return new ReplyAjaxPage(page);
	}
	/**
	 * 回购查询首页
	 */
	@aPermission(name = { "项目管理", "项目回购查询", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply buyBackQuery() {
		Map map = service.queryProductAndCompany();
		context.put("data", map);
		return new ReplyHtml(VM.html("buyBack/buyBackQuery.vm", context));
	}
	/**
	 * 回购查询请数据
	 */
	@aPermission(name = { "项目管理", "项目回购查询", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply buyBackQueryListing() {
		Map<String, Object> param = _getParameters();
		System.out.println("param=" + param);
		Page page = service.buyBackQueryPage(param);
		return new ReplyAjaxPage(page);
	}


//	/**
//	 * 回购申请数据
//	 */
//	@aPermission(name = { "资产管理", "回购预警","列表" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply buyBackListing() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.queryPage(param);
//		return new ReplyAjaxPage(page);
//	}
//	/**
//	 * 回购预警
//	 */
//	@aPermission(name = { "资产管理", "回购预警","申请"  })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply warning() {
//		Map<String, Object> param = _getParameters();
//		service.warning(param);
//		return new ReplyAjax("操作成功");
//	}
//	/**
//	 * 批量预警
//	 */
//	@SuppressWarnings("unchecked")
//	@aPermission(name = { "资产管理", "回购预警","批量预警"  })
//	@aAuth(type = aAuthType.ALL)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply batchWarning() {
//		Map<String, Object> param = _getParameters();
//		List list = Dao.selectList("BuyBack.butchWarning_pay_code", param);
//		for (int i = 0; i < list.size(); i++) {
//			Map m = (Map)list.get(i);
//			m.put("ACCOUNT_DATE", param.get("ACCOUNT_DATE"));
//			m.put("BUY_BACK_STATUS", "0");
//			service.warning(m);
//		}
//		return new ReplyAjax("操作成功");
//	}
	
	@aPermission(name = { "项目管理", "项目回购","申请"  })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply buyBackNormal() {
		Map<String, Object> param = _getParameters();
		
		//检查此项目是否有租金或者违约金被锁定，如有锁定则不让继续走流程
		Map  m_flag = Dao.selectOne("BuyBack.m_flag",param);
		if(m_flag!=null&&!m_flag.isEmpty()){
			throw new ActionException("当前有租金被核销锁定，暂时不能操作");
		}else{
			Map  m_flag2 = Dao.selectOne("BuyBack.m_flag2",param);
			if(m_flag2!=null&&!m_flag2.isEmpty()){
				throw new ActionException("当前有租金被核销锁定，暂时不能操作");
			}
		}
		//通过还款计划编号查询该还款计划是否在申请中或者被虚拟核销占用中（未退款未转应收）
		if(!BaseUtil.getVinualByPayListCode(param.get("PAYLIST_CODE")+"")){
			throw new ActionException("该还款计划在申请中或者被虚拟核销占用中");
		}
		//Dao.selectOne("PRC_BE_QJL_PAY_CODE", param.get("PAYLIST_CODE")+"");
		//如果发起回购的时候回购表里面有值就先删除
		Dao.delete("BuyBack.del_fil_buy_back", param);
		//在保存回购测算之前判断下是否存在已保存的数据(如果有就删除)
		service.deleteAlreadyData(param);
		//计算结算日的罚息，插入罚息表（结算日期的罚息数据）
		double fine = service.refreshPenaltyaAccountsDate(param.get("PAY_CODE")+"", param.get("ACCOUNT_DATE")+"", param.get("PAY_ID")+"");
		
		String buy_id = Dao.getSequence("SEQ_FIL_BUY_BACK");
		param.put("buy_id", buy_id);
		
		//计算到结算日为止的罚息金额
		Map<String, Object>  m = new HashMap<String, Object>();
		param.put("USER_ID", Security.getUser().getId());
		m = Dao.selectOne("BuyBack.queryFI_OVERDUE", param);
//		double fine = 0 ;
//		if(m!=null&&!m.isEmpty()){
//			OverdueService ov = new OverdueService();
//			fine = ov.dueTemp(
//					m.get("RENT_RECE") == null || "".equals(m.get("RENT_RECE")) ? 0 : Double.parseDouble(m.get("RENT_RECE") + ""),
//					m.get("PENALTY_RECE") == null || "".equals(m.get("PENALTY_RECE")) ? 0 : Double.parseDouble(m.get("PENALTY_RECE") + ""),
//					(int) getDaySub(getStringToday(), param.get("ACCOUNT_DATE") + ""));
//			fine = Util.formatDouble2(fine);
//		}
		
		param.put("WS_YQ", fine);
		service.buyBackNormal(param);
		//如果第三方为空就默认当前承租人供应商为第三方
		param.put("PAY_ID", param.get("NEWID"));
		if(param.get("PAYEE_NAME")==null||"".equals(param.get("PAYEE_NAME"))){
			Dao.update("BuyBack.update_sup_third", param);
		}
		boolean flag = false;
		String msg = "";
		String nextCode=""; 
		//发起退款评审流程
		List<String> prcessList = JBPM.getDeploymentListByModelName("正常回购审批流程");
		if(prcessList.size() > 0){
			Map<String, Object> jmap = new HashMap<String, Object>();
			jmap.put("BUY_BACK_ID", buy_id);
    		jmap.put("PROJECT_ID",param.get("PROJECT_ID").toString());
    		jmap.put("PAY_ID",param.get("PAY_ID").toString());
    		jmap.put("PAYLIST_CODE",param.get("PAYLIST_CODE").toString());
    		jmap.put("PRO_CODE",param.get("PRO_CODE").toString());
    		jmap.put("CLIENT_ID",param.get("CLIENT_ID").toString());
    		jmap.put("CLIENT_TYPE",param.get("CLIENT_TYPE").toString());
    		jmap.put("SUPPLIER_ID",param.get("SUPPLIER_ID").toString());
    		//jmap.put("END_STATUS",param.get("STATUS").toString());
			String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),param.get("PROJECT_ID").toString(), param.get("CLIENT_ID").toString(),"",jmap).getId(); 
			msg += jbpm_id+"已发起！";
			nextCode = new TaskService().getAssignee(jbpm_id);
    		param.put("JBPM_ID", jbpm_id);
    		//变更退款单状态
    		BuyBackCheckService bbcs = new BuyBackCheckService();
    		param.put("IS_END_STATUS", "0");
    		param.put("BUY_ID", buy_id);
    		bbcs.doUpdateEnd_status(param);
    		flag = true;
		}else{
			flag = false;
			msg = "未找到流程";
			throw new ActionException("未找到流程");
		}
		
		return new ReplyAjax(flag,nextCode,msg);
	}
	
//	/**
//	 * 回购预警首页
//	 */
//	@aPermission(name = { "资产管理", "回购预警查询", "列表" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply buyBackWarning() {
//		Map map = service.queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("buyBack/buyBackWarning.vm", context));
//	}

//	/**
//	 * 回购预警数据
//	 */
//	@aPermission(name = { "资产管理", "回购预警查询", "列表" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply buyBackWarningListing() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.buyBackWarning(param);
//		return new ReplyAjaxPage(page);
//	}
	/**
	 * 回购通知书导出excel
	 */
	@aPermission(name = { "项目管理", "项目回购", "回购通知书导出excel" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply exportExcel(){
		Map<String, Object> param = _getParameters();
		String [] PAY_ID = (param.get("PAY_ID")+"").split(",");
		if((param.get("PAY_ID")+"")!=null&&!"".equals(param.get("PAY_ID")+"")){
			param.put("PAY_ID", PAY_ID);
		}else {
			param.put("PAY_ID", "");
		}
		return new ReplyBuyBackExcel(param);
	}
	
//	/**
//	 * 回购预警数据刷新
//	 */
//	@aPermission(name = { "资产管理", "回购预警", "刷新" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply refreshWarning() {
//		service.refreshWarning();
//		return new ReplyAjax("操作成功");
//	}
	
	
//	/**
//	 * 分期回购
//	 */
//	@aPermission(name = { "项目管理", "项目回购","分期回购"  })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply buyBackStages() {
//		Map<String, Object> param = _getParameters();
//		JSONObject pm = JSONObject.fromObject(param.get("param"));
//		context.put("param", pm);
//		context.put("bzj", service.getProBzj(pm));
//		return new ReplyHtml(VM.html("buyBack/buyBackStages.vm", context));
//	}
	
	//@aPermission(name = { "项目管理", "项目回购","分期回保存"  })
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply toSaveBuyBack(){
//		Map<String, Object> param = _getParameters();
//		JSONObject json = JSONObject.fromObject(param.get("json"));		
//		//检查此项目是否有租金或者违约金被锁定，如有锁定则不让继续走流程
//		Map  m_flag = Dao.selectOne("BuyBack.m_flag",json);
//		if(m_flag!=null&&!m_flag.isEmpty()){
//			throw new ActionException("当前有租金被核销锁定，暂时不能操作");
//		}else{
//			Map  m_flag2 = Dao.selectOne("BuyBack.m_flag2",json);
//			if(m_flag2!=null&&!m_flag2.isEmpty()){
//				throw new ActionException("当前有租金被核销锁定，暂时不能操作");
//			}
//		}
//		//通过还款计划编号查询该还款计划是否在申请中或者被虚拟核销占用中（未退款未转应收）
//		if(!BaseUtil.getVinualByPayListCode(json.get("PAYLIST_CODE")+"")){
//			throw new ActionException("该还款计划在申请中或者被虚拟核销占用中");
//		}
//		Dao.selectOne("PRC_BE_QJL_PAY_CODE", json.get("PAYLIST_CODE")+"");
//		//调用李超写的存过计算罚息
//		String ACCOUNT_DATE=json.get("ACCOUNT_DATE")+"";
//		String PAYLIST_CODE=json.get("PAY_CODE")+"";
//		boolean flag_ = BuyBackService.strToDate(ACCOUNT_DATE).getTime()>BuyBackService.strToDate(BuyBackService.dateToStr(new Date())).getTime();
//		Map tm = new HashMap();
//		tm.put("PAYCODEPARAM", PAYLIST_CODE);
//		tm.put("NEW_DATE", ACCOUNT_DATE);
//		if(flag_){
//			Dao.selectOne("BuyBack.calculate_overdue_appointed", tm);
//		}
//		//并更新取整
//		Dao.update("BuyBack.round_overdue_appointed", tm);
//		
//		
//		//如果发起回购的时候回购表里面有值就先删除
//		Dao.delete("BuyBack.del_fil_buy_back", json);
//		//在保存回购测算之前判断下是否存在已保存的数据(如果有就删除)
//		
//		
//		String buy_id = Dao.getSequence("SEQ_FIL_BUY_BACK");
//		json.put("buy_id",buy_id);
//		service.toSaveBuyBack(json);
//		
//		//如果第三方为空就默认当前承租人供应商为第三方
//		if(json.get("PAYEE_NAME")==null||"".equals(json.get("PAYEE_NAME"))){
//			Dao.update("BuyBack.update_sup_third", json);
//		}
//		boolean flag = false;
//		String msg = "";
//		
//		//发起退款评审流程
//		List<String> prcessList = JBPM.getDeploymentListByModelName("分期回购审批流程");
//		if(prcessList.size() > 0){
//			Map<String, Object> jmap = new HashMap<String, Object>();
//
//			jmap.put("BUY_BACK_ID", buy_id);//回购表单id
//    		//jmap.put("FUND_ID", HEAD_ID);
//    		jmap.put("PROJECT_ID",json.get("PROJECT_ID").toString()); //项目id
//    		jmap.put("PRO_CODE",json.get("PRO_CODE").toString());//项目编号
//    		//jmap.put("STATUS",json.get("STATUS").toString());//状态
//    		jmap.put("PAY_ID",json.get("PAY_ID").toString());//支付表id
//    		jmap.put("PAYLIST_CODE",json.get("PAYLIST_CODE").toString());//支付表编号
//    		jmap.put("YEAR_INTEREST",json.get("YEAR_INTEREST").toString());//年利率
//    		jmap.put("CLIENT_ID",json.get("CLIENT_ID").toString());//客户id
//    		jmap.put("CLIENT_TYPE",json.get("CLIENT_TYPE").toString());//客户类型
//    		jmap.put("SUPPLIER_ID", json.get("SUPPLIER_ID").toString());//供应商id
//    		//jmap.put("END_STATUS",json.get("STATUS").toString());//结束状态项目
//			String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),json.get("PROJECT_ID").toString(), null,"",jmap).getId(); 
//			msg += jbpm_id+"已发起！";
//    		param.put("JBPM_ID", jbpm_id);
//    		flag = true;
//    		
//    		//更新项目状态
//    		//service.doUpdateProStatus(jmap);
//		}else{
//			flag = false;
//			msg = "未找到流程";
//			throw new ActionException("未找到流程");
//		}
//		return new ReplyAjax(flag,msg);
//	}
	
	/**
	 * 分期回测算
	 */
//	@SuppressWarnings("unchecked")
//	@aPermission(name = { "项目管理", "项目回购","分期回测算"  })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply buyBackStagesCalculate() {
//		Map<String, Object> param = _getParameters();
//		Map temp = new HashMap();
//		temp.put("_leaseTerm",param.get("lease_term"));
//		//temp.put("annualRate",Double.parseDouble(param.get("YEAR_INTEREST")+"")>1?
//		//		Util.div(Double.parseDouble(param.get("YEAR_INTEREST")+""), 100, 4):Double.parseDouble(param.get("YEAR_INTEREST")+""));
//		temp.put("annualRate",Double.parseDouble(param.get("interest")+"")>1?
//				Util.div(Double.parseDouble(param.get("interest")+""), 100, 4):Double.parseDouble(param.get("interest")+""));
//		temp.put("_payCountOfYear","12");
//		temp.put("date",param.get("ACCOUNT_DATE"));
//		temp.put("pay_way",param.get("pay_way"));
//		//计算罚息总额并插入相应的罚息表
//		//double fine = service.refreshPenaltyaAccountsDate(param.get("PAY_CODE")+"", param.get("ACCOUNT_DATE")+"", param.get("PAY_ID")+"");
//		double fine = Dao.selectDouble("BuyBack.query_fi_overdue_ACCOUNT_DATE", param);
//		//查询结算日往后的利息
//		Map  m = Dao.selectOne("BuyBack.queryPayInterests", param);
//		double Interests = (m==null||"".equals(m.get("ITEM_MONEY"))?0:Double.parseDouble(m.get("ITEM_MONEY")+""));
//		
//		//查询保证金
//		double Deposit = 0 ;
//		if("0".equals(param.get("BZJ"))){
//			m = Dao.selectOne("BuyBack.queryPayDeposit", param);
//			Deposit = (m==null||"".equals(m.get("ITEM_MONEY"))
//					?0:Double.parseDouble(m.get("ITEM_MONEY")+""));
//		}
//		
//		//查询db保证金
//		double DBDeposit = 0;
//		if("0".equals(param.get("DBBZJ"))){
//			m = Dao.selectOne("BuyBack.queryPayDBDeposit", param);
//			DBDeposit = (m==null||"".equals(m.get("ITEM_MONEY"))
//			 		  ?0:Double.parseDouble(m.get("ITEM_MONEY")+""));
//		}
//		
//		//留购价
//		double Leave= 0;
//		if("0".equals(param.get("isLiugou"))){
//			m = Dao.selectOne("BuyBack.queryPayLeave", param);
//			Leave= (m==null||"".equals(m.get("ITEM_MONEY"))
//					   ?0:Double.parseDouble(m.get("ITEM_MONEY")+""));
//		}
//		
//		//已付租金
//		double BEGINNING_PAID = param.get("YS_MONEY")==null||"".equals(param.get("YS_MONEY"))
//		?0:Double.parseDouble(param.get("YS_MONEY")+"");
//		
//		//租金总计
//		double SUM_ZJ = param.get("SUM_ZJ")==null||"".equals(param.get("SUM_ZJ"))
//		?0:Double.parseDouble(param.get("SUM_ZJ")+"");
//		
//		m = Dao.selectOne("BuyBack.queryFI_OVERDUE", param);
////		double fine = 0 ;
////		if(m!=null&&!m.isEmpty()){
////			OverdueService ov = new OverdueService();
////			fine = ov.dueTemp(
////					m.get("RENT_RECE") == null || "".equals(m.get("RENT_RECE")) ? 0 : Double.parseDouble(m.get("RENT_RECE") + ""),
////					m.get("PENALTY_RECE") == null || "".equals(m.get("PENALTY_RECE")) ? 0 : Double.parseDouble(m.get("PENALTY_RECE") + ""),
////					(int) getDaySub(getStringToday(), param.get("ACCOUNT_DATE") + ""));
////			fine = Util.formatDouble2(fine);
////		}
//		double startRent = (param.get("startRent")!=null&&!"".equals(param.get("startRent"))?Double.parseDouble(param.get("startRent")+""):0);
//		
//		double residualPrincipal = SUM_ZJ-BEGINNING_PAID-Interests -Deposit -Leave-DBDeposit - startRent+ fine;
//		//double residualPrincipal = 689062.98;
//		//到回购表里面查询回购款   fil_buy_back
//		//Dao.selectDouble(string, object)
//		temp.put("residualPrincipal", residualPrincipal);
//		PayTaskService payService = new PayTaskService();
//		//调用计算还款计划service方法
//		JSONObject quoteCalculateTest = payService.quoteCalculateTest(temp);
//		
//		//在新还款计划的前面加上已收的还款计划（包括保证金抵扣）
//		param.put("ID", param.get("PAY_ID"));
//		JSONObject detailOld = payService.payDetailShow(param);
//		JSONArray arrayOld = (JSONArray)detailOld.get("data");
//		//double dk = Deposit+DBDeposit+Leave - fine;
//		double dk = Deposit+DBDeposit+Leave;
//		double lx_new = -1;
//		double bj_new = -1;
//		int BEGINNING_NUM = Integer.parseInt(param.get("BEGINNING_NUM")+"");
//		if(dk>0){
//			/*for (int j = 0; j < arrayOld.size(); j++) {
//				JSONObject json = (JSONObject)arrayOld.get(j);
//				if(json.getInt("qc")== BEGINNING_NUM){
//					dk = dk - (json.getString("lx")!=null&&!"".equals(json.getString("lx"))?json.getDouble("lx"):0);
//					if(dk<=0){
//						lx_new = (json.getString("lx")!=null&&!"".equals(json.getString("lx"))?json.getDouble("lx"):0)+dk;
//						bj_new =0;
//						break;
//					}else {
//						dk = dk - (json.getString("bj")!=null&&!"".equals(json.getString("bj"))?json.getDouble("bj"):0);
//						if(dk<=0){
//							bj_new = (json.getString("bj")!=null&&!"".equals(json.getString("bj"))?json.getDouble("bj"):0)+dk;
//							//lx_new = 0;
//							break;
//						}
//					}
//					BEGINNING_NUM++;
//				}
//			}*/
//			//查询抵扣的金额
//			
//			List dkList = Dao.selectList("BuyBack.queryDKlist", param);
//			for (int i = 0; i < dkList.size(); i++) {
//				Map dkMap = (Map)dkList.get(i);
//				dk=dk-Double.parseDouble(dkMap.get("BEGINNING_MONEY")+"");
//				if(dk<=0){
//					if("利息".equals(dkMap.get("BEGINNING_NAME")+"")){
//						BEGINNING_NUM = Integer.parseInt(dkMap.get("BEGINNING_NUM")+"");
//						lx_new = Double.parseDouble(dkMap.get("BEGINNING_MONEY")+"")+dk;
//						bj_new =0;
//						break;
//					}else  if("本金".equals(dkMap.get("BEGINNING_NAME")+"")){
//						BEGINNING_NUM = Integer.parseInt(dkMap.get("BEGINNING_NUM")+"");
//						bj_new = Double.parseDouble(dkMap.get("BEGINNING_MONEY")+"")+dk;
//						break;
//					}
//					break;
//				}
//			}
//			
//		}
//			//截取出还款和抵扣后的期次后，合并新算出的
//			List newList = arrayOld.subList(0, BEGINNING_NUM);
//			JSONArray array = quoteCalculateTest.getJSONArray("ln");
//			JSONObject tmp = (JSONObject)newList.get(BEGINNING_NUM-1);
//			if(lx_new>=0){
//				tmp.put("lx", Util.formatDouble2(lx_new));
//			}
//			if (bj_new>=0) {
//				tmp.put("bj", Util.formatDouble2(bj_new));
//			}
//			tmp.put("zj", (tmp.getString("lx")==null||"".equals(tmp.getString("lx"))?0:tmp.getDouble("lx"))
//					+(tmp.getString("bj")==null||"".equals(tmp.getString("bj"))?0:tmp.getDouble("bj")));
//			newList.remove(BEGINNING_NUM-1);
//			newList.add(tmp);
//			for (int i = 0; i < array.size(); i++) {
//				JSONObject object = (JSONObject)array.get(i);
//				object.put("qc", BEGINNING_NUM+1+i);
//				newList.add(object);
//			}
//			Map mm = new HashMap();
//			mm.put("ln", newList);
//			mm.put("fine", fine);
//			mm.put("Deposit", Deposit);
//			mm.put("DBDeposit", DBDeposit);
//			mm.put("Leave", Leave);
//		return new ReplyAjax(JSONObject.fromObject(mm));
//	}
	
	/**
	 * 分期回购
	 */
//	@SuppressWarnings("unchecked")
//	@aPermission(name = { "项目管理", "项目回购","分期回购保存"  })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply buyBackStagesSave() {
//		Map<String, Object> param = _getParameters();
//		JSONObject json = JSONObject.fromObject(param.get("json"));
//		
//		//在保存回购测算之前判断下是否存在已保存的数据(如果有就删除)
//		service.deleteAlreadyData(json);
//		service.buyBackStages(json);
//		
//		//分期回购保存同时改变还款人(A_B)
//		service.synchronizationCustSuppliers(json);
//		
//		return new ReplyAjax("操作成功");
//	}
	
	
	/****************************************************************************************
	 ***********************************流程表单*****正常回购********Auth=杨雪****************
	 ****************************************************************************************/
	
	/**
	 * 正常回购
	 */
	@aPermission(name = { "项目管理", "项目回购", "申请" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toFindHGData(){
		Map<String,Object> hgData = _getParameters();
		return new ReplyAjax(service.toFindHGData(hgData));
	} 
	
	/**
	 * 正常回购(流程表单)
	 * tobuyBackAppZC
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply tobuyBackAppZC(){//信息录入
		Map<String,Object> param = _getParameters();
		context.put("data", param);
		context.put("proData", service.getProData(param));
		context.put("buyBack", service.toCheckedFormHG(param));
		context.put("getOverDue", service.queryFI_OVERDUE(param));
		return new ReplyHtml(VM.html("buyBack/buyBackAppZC.vm",context));
	}
	
	/**
	 * 正常回购(流程表单-退回至申请人修改操作)
	 * doSavbuyBackReturnApp
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doSavbuyBackReturnAppZC(){//信息录入
		Map<String, Object> param = _getParameters();
		
		//回购表id
		String buy_id = Dao.getSequence("SEQ_FIL_BUY_BACK");
		param.put("buy_id",buy_id);
		
		//在保存回购测算之前判断下是否存在已保存的数据(如果有就删除)
		service.deleteAlreadyData(param);
		//计算结算日的罚息，插入罚息表（结算日期的罚息数据）
		param.put("PAY_CODE", param.get("PAYLIST_CODE"));
		double fine = service.refreshPenaltyaAccountsDate(param.get("PAY_CODE")+"", param.get("ACCOUNT_DATE")+"", param.get("PAY_ID")+"");
	
		//计算到结算日为止的罚息金额
		Map<String, Object>  m = new HashMap<String, Object>();
		param.put("USER_ID", Security.getUser().getId());		
		m = Dao.selectOne("BuyBack.queryFI_OVERDUE", param);
		
		param.put("WS_YQ", fine);
		service.buyBackNormal(param);
		
		Map<String,Object> jbpm = new HashMap<String,Object>();
		jbpm.put("BUY_BACK_ID", buy_id);
		JBPM.setExecutionVariable(param.get("JBPM_ID") + "", jbpm);
	
		return new ReplyAjax().addOp(new OpLog("项目回购","流程-申请人修改信息","修改"));
	}
	
	/**
	 * 正常回购(流程表单)
	 * toShowBuyBackHGMain
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toShowBuyBackHGMain(){
		Map<String,Object> param = _getParameters();
		
		context.put("param", param);	
		String PROJECT_ID = param.get("PROJECT_ID").toString();
		// 判断当前流程节点名称
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = new TaskService().doShowTaskInfoByJbpmId(param);
		if (!list.isEmpty() && list.size() > 0) {
			String taskName = list.get(0).get("NAME_") + "";
			if (taskName.contains("合同")) {
				new ProjectContractManagerService().doAddProjectContractList(param.get("JBPM_ID").toString());//保存欲归档文件		
				context.put("TASKNAME", "examineAndContract");
			}else if (taskName.contains("资料")) {
				context.put("TASKNAME", "DangAnUp");
			}else if(taskName.contains("收款")) {
				context.put("TASKNAME", "INVOICE");
			}else if(taskName.contains("债权")) {
				context.put("TASKNAME", "ZHAIQUAN");
			}
			//更新项目主表 当前节点名称
			new ProjectStatus().updateProjectJbpmTaskName(PROJECT_ID, taskName);
		}
		return new ReplyHtml(VM.html("buyBack/toShowBuyBackHGMain.vm",context));
	}
	
	/**
	 * 正常回购(流程表单-回购表单信息查看)
	 * toShowFormHG
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toShowFormHG(){
		Map<String,Object> param = _getParameters();
		//查询PAYLIST_CODE   hxl
		Map temp = (Map)Dao.selectOne("BuyBack.queryByIDfil_buy_back", param);
		if(temp!=null && temp.containsKey("PAYLIST_CODE")){
			param.put("PAYLIST_CODE", temp.get("PAYLIST_CODE"));
		}else{
			throw new ActionException("缺少支付表号，请联系管理员");
		}
		context.put("data", param);
		context.put("proData", service.getProData(param));
		context.put("buyBack", service.toCheckedFormHG(param));
		return new ReplyHtml(VM.html("buyBack/toShowFormHG.vm",context));
	}
	
	/**
	 * 正常回购(流程表单-资料上传)
	 * toShowFormHG
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toShowMaterialHG(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		ProjectContractManagerService service = new ProjectContractManagerService();
		param.putAll(service.doShowClientTypeByProjectId(param.get("PROJECT_ID") + ""));
		context.put("param", param);
		context.put("FILELIST", service.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE") + "", "正常回购", "1"));
		return new ReplyHtml(VM.html("buyBack/toShowMaterialHG.vm",context));
	}
	
	/**
	 * 保存项目合同列表
	 * 
	 * @return
	 * @author:King 2013-10-9
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doAddCheckedContractzc() {
		Map<String, Object> param = _getParameters();
		PigeonholeService pService = new PigeonholeService();
		ProjectContractManagerService service = new ProjectContractManagerService();
		pService.doDelPigeonholeApplyInfo(param);
		try {
			String APPLY_ID = pService.doAddPigeonholeApplyInfo(param);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("DOSSIER_APPLY_ID", APPLY_ID);
			JBPM.setExecutionVariable(param.get("JBPM_ID") + "", m);
		} catch (Exception e) {
			throw new ActionException("归档申请失败", e);
		}
		if (param.containsKey("FILEINFO")) {
			List<Map<String, Object>> list = JSONArray.fromObject(param.get("FILEINFO"));
			int i = 0;
			service.doDelContractByProjectId(list.get(0));
			for (Map<String, Object> map : list) {		
				map.put("TPM_BUSINESS_PLATE", "正常回购");
				i += service.doAddCheckedContract(map);
			}
			if (i == list.size()) return new ReplyAjax(true).addOp(new OpLog("分期回购合同文件列表", "保存", "项目id为" + list.get(0).get("PROJECT_ID")));
			else throw new ActionException("保存失败");
		} else {
			return new ReplyAjax(false, "没有索引到需要的数据");
		}
	}
	
	
	/**
	 * 正常回购(流程表单-财务核销)
	 * toCheckedFormHG
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toCheckedFormHG(){
		Map<String,Object> param = _getParameters();
		// 删除财务表数据
		Dao.delete("PayTask.deleteBeginning", param);
		// 同步财务数据
		Map<String, String> temp1 = new HashMap<String, String>();
		temp1.put("PAYLIST_CODE", param.get("PAYLIST_CODE") + "");
		temp1.put("PMT", "PMT租金");
		temp1.put("ZJ", "租金");
		temp1.put("SYBJ", "剩余本金");
		temp1.put("D", "第");
		temp1.put("QI", "期");
		Dao.insert("PayTask.synchronizationBeginning", temp1);
		context.put("data", param);
		context.put("proData", service.getProData(param));//查看项目数据
		context.put("buyBack", service.toCheckedFormHG(param));//查看回购数据
		//context.put("f_data", service.getRentData(param));//查看付款申请单
		context.put("f_data", Dao.selectList("rentDk.getRentData5", param));//查看付款申请单
//		context.put("firstMoney", service.getFirstMoney(param));//查看首期款
		context.put("HG_YS_MONEY", Dao.selectDouble("BuyBack.HG_YS_MONEY", param));//计算回购款
		
		
		return new ReplyHtml(VM.html("buyBack/toCheckedFormHG.vm",context));
	}
	/**
	 * 回购在财务核销前先确认来款的明细项
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply toCheckedDeposit(){
		Map<String,Object> param = _getParameters();
		context.put("HG_YS_MONEY", Dao.selectDouble("BuyBack.HG_YS_MONEY", param));//计算回购款
		context.put("f_data", Dao.selectList("BuyBack.toCheckedDeposit", param));//计算回购款
		return new ReplyHtml(VM.html("buyBack/toCheckedDeposit.vm",context));
	}
	
	/**
	 * 回购在财务核销前先确认来款的明细项(分期)
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply toCheckedDepositFQ(){
		Map<String,Object> param = _getParameters();
		context.put("HG_YS_MONEY", ((Map)service.getFirstMoney1(param)).get("YS_MONEY"));//计算回购款
		context.put("f_data", Dao.selectList("BuyBack.toCheckedDeposit", param));//计算回购款
		return new ReplyHtml(VM.html("buyBack/toCheckedDeposit.vm",context));
	}
	
	
	/**
	 * 回购在财务核销前先确认来款的明细项(保存)
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply saveCheckedDeposit(){
		List<FileItem> fileList = _getFileItem();
		Map<String,Object> param = _getParameters();
		if(param==null||param.isEmpty()){
			try {
				param = getPostParams(fileList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Iterator<?> i = fileList.iterator();
		String path = SkyEye.getConfig("file.path")+"/buyBack/";
		String name = "";
		while (i.hasNext()) {
			FileItem fileitem = (FileItem) i.next();
			if (!fileitem.isFormField()) {// 如果是文件
				if (fileitem.getName() == null || fileitem.getName().isEmpty()
						|| "".equals(fileitem.getName()))
					continue;
				if (!fileitem.getName().isEmpty()
						&& !"".equals(fileitem.getName())) {
					// fileitem.getOutputStream();
					// FileUtils.
					try {
						name = path + new Date().getTime()+fileitem.getName();
						File fp = new File(path);
						if(!fp.exists()){
							fp.mkdirs();
						}
						fileitem.write(new File(name));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		JSONArray POOL_INFO = JSONArray.fromObject(param.get("POOL_INFO"));
//		if(POOL_INFO!=null&&POOL_INFO.size()>0){
//			for (int i = 0; i < POOL_INFO.size(); i++) {
//				JSONObject json = POOL_INFO.getJSONObject(i);
//				if(json.get("pool_id")!=null&&!"".equals(json.get("pool_id"))){
//					Dao.update("BuyBack.saveCheckedDeposit_pool", json.get("pool_id"));
//				}
//			}
//		}
		param.put("FILE_PATH", name);
		if(param.get("POOL_INFO")==null){
			param.put("POOL_INFO", "");
		}
		Dao.update("BuyBack.saveCheckedDeposit", param);
		return new ReplyAjax(name);
	}
	/**
	 * 回购在财务核销前先确认来款的明细项(债权确认页面)
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply affirmCheckedDeposit(){
		Map<String,Object> param = _getParameters();
		context.put("HG_YS_MONEY", Dao.selectDouble("BuyBack.HG_YS_MONEY", param));//计算回购款
		context.put("f_data", Dao.selectList("BuyBack.toCheckedDeposit", param));//
		context.put("data_", Dao.selectOne("BuyBack.query_pool_file", param));//
		
		return new ReplyHtml(VM.html("buyBack/affirmCheckedDeposit.vm",context));
	}
	/**
	 * 回购在财务核销前先确认来款的明细项(在流程进入的时候先把回购表的pool_info修改为空)
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply updateCheckedDeposit(){
		Map<String,Object> param = _getParameters();
		param.put("POOL_INFO", "");
		Dao.update("BuyBack.saveCheckedDeposit", param);
		return new ReplyAjax("操作成功！");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toCheckedFund(){//核销数据操作
		Map<String,Object> map = _getParameters();
		//TODO  /////////////////////////////缺少PAYLIST_CODE//////////////////////////////////////////////
		//查询PAYLIST_CODE   hxl   有可能有Buy_back_id错误引发的bug
		Map temp = (Map)Dao.selectOne("BuyBack.queryByIDfil_buy_back", map);
		if(temp!=null && temp.containsKey("PAYLIST_CODE")){
			map.put("PAYLIST_CODE", temp.get("PAYLIST_CODE"));
		}else{
			throw new ActionException("缺少支付表号，请联系管理员");
		}
		
		
		
		//在供应商选择了回购款的来源后修改池子里面的钱数量
		Map pool_infos = Dao.selectOne("BuyBack.quey_pool_infos",map.get("PAYLIST_CODE"));
		if(pool_infos!=null&&!pool_infos.isEmpty()){
			JSONArray arr = JSONArray.fromObject(pool_infos.get("POOL_INFO"));
			if(arr!=null&&arr.size()>0){
				for (int i = 0; i < arr.size(); i++) {
					JSONObject json = arr.getJSONObject(i);
					if(json.get("pool_id")!=null&&!"".equals(json.get("pool_id"))){
						double money = Dao.selectDouble("BuyBack.deposit_pool_money",json.get("pool_id"));
						if(money<json.getDouble("pool_money")){
							throw new ActionException("供应商选择的低扣款小于回购款，请退回重新选择低扣款");
						}
						Map jsonMap = JsonUtil.toMap(json);
						Dao.update("BuyBack.updateCheckedDeposit_pool_money", jsonMap);
					}
				}
			}
		}
		/////////////////////////////////////////////////////////////////////////////
		//查询如果资金没有未核销的就说明已全部核销
		List is_verification_finish = Dao.selectList("BuyBack.is_verification_finish", map);
		if(is_verification_finish==null||is_verification_finish.isEmpty()){
			throw new ActionException("请勿重复操作核销");
		}
		//删除大于结算日的罚息
		Dao.delete("BuyBack.FI_ACCOUNT_DATE_FX", map);
		//更新提前结清表数据
		update_fil_buy_back(map);
		//计算回购款违约金，并插入罚息表
		int tt = Dao.update("BuyBack.buy_faxi", map);
//		if(tt>0){//如果有回购款约金再插入违约金表
//			Map faxi = Dao.selectOne("BuyBack.buy_faxi_id", map);
//			if(faxi!=null&&!faxi.isEmpty()){
//				Dao.insert("BuyBack.insert_buy_faxi", faxi);
//			}else {
//				throw new ActionException("违约金插入失败，请联系管理员");
//			}
//		}
		//如果选择了保证金抵扣就冲抵保证金
		//并且保证金金额大于0
		Map  isM = Dao.selectOne("BuyBack.is_bzj", map);
		if(isM!=null) new ActionException("回购数据错误");
		double DEPOSIT_ = (isM.get("BZJ")!=null&&!"".equals(isM.get("BZJ").toString()))?Double.parseDouble(isM.get("BZJ").toString()):0;
		if("0".equals(isM.get("IS_DEPOSIT")+"")&&DEPOSIT_>0){
			Map tm = new HashMap();
			tm.put("PAYCODEPARAM", map.get("PAYLIST_CODE"));
			double EXTRA_MONEY = map.get("FI_REALITY_ACCOUNT")!=null&&!"".equals(map.get("FI_REALITY_ACCOUNT")+"")?Double.parseDouble(map.get("FI_REALITY_ACCOUNT")+""):0d;
			tm.put("EXTRA_MONEY", EXTRA_MONEY);
			tm.put("FI_ACCOUNT_DATE_", map.get("FI_ACCOUNT_DATE"));
			tm.put("FI_REALITY_BANK_", map.get("FI_REALITY_BANK"));
			Dao.selectOne("BuyBack.PRC_FUNDRED_BUY_BACK", tm);
		}else {
			Map tm = new HashMap();
			tm.put("PAYCODEPARAM", map.get("PAYLIST_CODE"));
			double EXTRA_MONEY = map.get("FI_REALITY_ACCOUNT")!=null&&!"".equals(map.get("FI_REALITY_ACCOUNT")+"")?Double.parseDouble(map.get("FI_REALITY_ACCOUNT")+""):0d;
			tm.put("EXTRA_MONEY", EXTRA_MONEY);
			tm.put("FI_ACCOUNT_DATE_", map.get("FI_ACCOUNT_DATE"));
			tm.put("FI_REALITY_BANK_", map.get("FI_REALITY_BANK"));
			Dao.selectOne("BuyBack.PRC_FUNDRED_BUY_BACK_NO", tm);
		}
		
		
		//放弃代码核销改为
		/*param.put("USERCODE", Security.getUser().getCode());//用户编号
		param.put("USERNAME", Security.getUser().getName());//用户姓名
		BuyBackCheckService bbcs = new BuyBackCheckService();
		Map<String,Object> m = new HashMap<String,Object>();
		//首先冲红
		Map<String,Object> bbcsMap = (Map<String, Object>) bbcs.toRedFinance(param);
		if(bbcsMap!=null){
			if(!"".equals(bbcsMap.get("BZJ_HEAD"))&&bbcsMap.get("BZJ_HEAD")!=null){
				m.put("BZJ_HEAD", bbcsMap.get("BZJ_HEAD"));
			}else if(!"".equals(bbcsMap.get("DBBZJ_HEAD"))&&bbcsMap.get("DBBZJ_HEAD")!=null){
				m.put("DBBZJ_HEAD", bbcsMap.get("DBBZJ_HEAD"));
			}else if(!"".equals(bbcsMap.get("LGJ_HEAD"))&&bbcsMap.get("LGJ_HEAD")!=null){
				m.put("LGJ_HEAD", bbcsMap.get("LGJ_HEAD"));
			}
		}
		
		//首先抵扣保证金留购价 
		if((param.get("BZJ")!=null&&!"".equals(param.get("BZJ"))&&"0".equals(param.get("BZJ")))||(param.get("isLiugou")!=null&&!"".equals(param.get("isLiugou"))&&"0".equals(param.get("isLiugou").toString()))){
			Map<String,Object> bzjAndLgj = (Map<String, Object>) bbcs.doAddBZJandLgj(param);
			
			System.out.println("=====bzjAndLgj== yx ======"+bzjAndLgj);
			
		    if(bzjAndLgj!=null){
		    	//添加时候明细
		    	bzjAndLgj.put("BUY_BACK_ID", param.get("BUY_BACK_ID"));
		    	bbcs.doCheckedBZJandLGJ(bzjAndLgj);
		    	
		    	//核销审核
		    	bzjAndLgj.put("FUND_ID", bzjAndLgj.get("BZJandLGJ"));
		    	bbcs.doCheckedFund1(bzjAndLgj);
		    	
		    	m.put("BZJandLGJ", bzjAndLgj.get("BZJandLGJ"));
		    }
		}
		
	    //其次核销DB保证金
		if(param.get("DBBZJ")!=null&&!"".equals(param.get("DBBZJ"))&&"0".equals(param.get("DBBZJ").toString())){
			Map<String,Object> DBBZJ = (Map<String, Object>) bbcs.toAddDBDk(param);
		    if(DBBZJ!=null){
		    	//添加时候明细
		    	DBBZJ.put("BUY_BACK_ID", param.get("BUY_BACK_ID"));
		    	bbcs.doCheckedDBbzj(DBBZJ);
		    	//核销审核
		    	DBBZJ.put("FUND_ID", DBBZJ.get("dbBZJdk"));
		    	bbcs.doCheckedFund1(DBBZJ);
		    	
		    	m.put("dbBZJdk", DBBZJ.get("dbBZJdk"));
		    }
		}
		
		//最后核销来款
		String HEAD_ID = (String) bbcs.toAppRentCheck(param);
		
		if(HEAD_ID != ""){
			m.put("LK_HEAD", HEAD_ID);
		}
		
		JBPM.setExecutionVariable(param.get("JBPM_ID") + "", m);*/
		
		return new ReplyAjax("操作成功");
	}
	
	
	/****************************************************************************************
	 ***********************************流程表单*****分期回购********Auth=杨雪****************
	 ****************************************************************************************/
	/**
	 * 分期回购(流程表单-退回至申请人)
	 * tobuyBackReturnApp
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply tobuyBackReturnApp(){//信息录入
		Map<String,Object> param = _getParameters();
		context.put("data", param);
		context.put("proData", service.getProData(param));
		context.put("buyBack", service.toCheckedFormHG(param));
		context.put("getOverDue", service.queryFI_OVERDUE(param));
		return new ReplyHtml(VM.html("buyBack/tobuyBackReturnApp.vm",context));
	}
	
	
	/**
	 * 分期回购(流程表单-退回至申请人修改操作)
	 * doSavbuyBackReturnApp
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doSavbuyBackReturnApp(){//信息录入
		Map<String,Object> param = _getParameters();
		boolean flag = false;
		String msg = "";
		JSONObject json = JSONObject.fromObject(param.get("json"));		
		int i = service.toSaveBuyBack1(json);
		if(i>0){
			flag = true;
			msg = "操作成功！";
		}else{
			msg = "操作失败！";
		}
		
		return new ReplyAjax(flag,msg).addOp(new OpLog("项目回购","流程-申请人修改信息","修改"));
	}
	
	
	/**
	 * 分期回购(流程表单-信息查看)
	 * toShowFormHG1
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toShowFormHG1(){
		Map<String,Object> param = _getParameters();
		//查询支付表id和分期回购期次
		Map temp = Dao.selectOne("BuyBack.queryByIDfil_buy_back", param);
		if(temp.containsKey("PAYLIST_CODE")){
			param.put("PAYLIST_CODE", temp.get("PAYLIST_CODE"));
		}else{
			throw new ActionException("缺少支付表号，请联系管理员");
		}
		//分期回购期次
		int LEASE_TERM = Integer.parseInt(temp.get("LEASE_TERM")+"");
		context.put("data", param);
		context.put("proData", service.getProData(param));
		//更新回购表，保证数据准确性
		update_fil_buy_back(temp);
		//查询回购参数
		context.put("buyBack", service.toCheckedFormHG(param));
		//查询还款计划数据
		Map mp = new HashMap();
		mp.put("ID", temp.get("PAY_ID"));
		PayTaskService payTaskService = new PayTaskService();
		JSONObject detailShow1 = payTaskService.payDetailShow(mp);
		JSONArray json = detailShow1.getJSONArray("data");
		JSONArray detail = new JSONArray();
		for (int i = 0; i < json.size(); i++) {
			if(i>=json.size()-LEASE_TERM)
			detail.add(json.get(i));
		}
		context.put("detail", detail);
		return new ReplyHtml(VM.html("buyBack/toShowFormHG1.vm",context));
	}
	
	/**
	 * 分期回购(流程表单-支付表测算)
	 * toShowFormHG
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply tobuyBackApp(){
		Map<String,Object> param = _getParameters();
		context.put("data", param);
		context.put("proData", service.getProData(param));
		context.put("buyBack", service.toCheckedFormHG(param));
		context.put("getOverDue", service.queryFI_OVERDUE(param));
		//分期回购保证金
		context.put("new_bzj", Dao.selectDouble("BuyBack.query_new_bzj", param));
		//分期最大期次
		context.put("fq_qc", Dao.selectInt("BuyBack.query_fq_qc", param));
		return new ReplyHtml(VM.html("buyBack/buyBackApp.vm",context));
	}
	 
	/**
	 * 分期回购(流程表单)
	 * toShowMaterialHGMain
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toShowMaterialHGMain(){
		Map<String, Object> param = _getParameters();
		
		context.put("param", param);		
		String PROJECT_ID = param.get("PROJECT_ID").toString();
		// 判断当前流程节点名称
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = new TaskService().doShowTaskInfoByJbpmId(param);
		if (!list.isEmpty() && list.size() > 0) {
			String taskName = list.get(0).get("NAME_") + "";
			if (taskName.contains("签约")) {
				new ProjectContractManagerService().doAddProjectContractList(param.get("JBPM_ID").toString());//保存欲归档文件
				context.put("TASKNAME", "examineAndContract");
			} else if (taskName.contains("档案")) {
				context.put("TASKNAME", "DangAnUp");
			} else if (taskName.contains("首期款")) {
				context.put("TASKNAME", "FirstPeriod");
			} else if (taskName.contains("债权")) {
				context.put("TASKNAME", "ZHAIQUAN");
			} 
			//更新项目主表 当前节点名称
			new ProjectStatus().updateProjectJbpmTaskName(PROJECT_ID, taskName);
		}
		return new ReplyHtml(VM.html("buyBack/toShowMaterialHGMain.vm",context));
	}
	
	
	
	/**
	 * 分期回购(流程表单-资料审核)
	 * toShowMaterialHG1
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toShowMaterialHG1(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		ProjectContractManagerService service = new ProjectContractManagerService();
		param.putAll(service.doShowClientTypeByProjectId(param.get("PROJECT_ID") + ""));
		context.put("param", param);
		context.put("FILELIST", service.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE") + "", "分期回购", "1"));
		return new ReplyHtml(VM.html("buyBack/toShowMaterialHG1.vm",context));
	}
	
	/**
	 * 保存项目合同列表
	 * 
	 * @return
	 * @author:King 2013-10-9
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doAddCheckedContract() {
		Map<String, Object> param = _getParameters();
		PigeonholeService pService = new PigeonholeService();
		ProjectContractManagerService service = new ProjectContractManagerService();
		pService.doDelPigeonholeApplyInfo(param);
		try {
			String APPLY_ID = pService.doAddPigeonholeApplyInfo(param);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("DOSSIER_APPLY_ID", APPLY_ID);
			JBPM.setExecutionVariable(param.get("JBPM_ID") + "", m);
		} catch (Exception e) {
			throw new ActionException("归档申请失败", e);
		}
		if (param.containsKey("FILEINFO")) {
			List<Map<String, Object>> list = JSONArray.fromObject(param.get("FILEINFO"));
			int i = 0;
			service.doDelContractByProjectId(list.get(0));
			for (Map<String, Object> map : list) {
				map.put("TPM_BUSINESS_PLATE", "分期回购");
				i += service.doAddCheckedContract(map);
			}
			if (i == list.size()) return new ReplyAjax(true).addOp(new OpLog("分期回购合同文件列表", "保存", "项目id为" + list.get(0).get("PROJECT_ID")));
			else throw new ActionException("保存失败");
		} else {
			return new ReplyAjax(false, "没有索引到需要的数据");
		}
	}
	
	/**
	 * 分期回购(流程表单-财务核销)
	 * toCheckedFormHG1
	 * @date 2013-11-22 下午08:09:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toCheckedFormHG1(){
		Map<String,Object> param = _getParameters();
		context.put("data", param);
		context.put("proData", service.getProData(param));//查看项目数据
		context.put("buyBack", service.toCheckedFormHG(param));//查看回购数据
		context.put("f_data", service.getRentData(param));//查看付款申请单
		//context.put("firstMoney", service.getFirstMoney(param));//查看首期款
		context.put("firstMoney", service.getFirstMoney1(param));//查看首期款
		
		// 删除财务表数据
		Dao.delete("PayTask.deleteBeginning", param);
		
		// 同步财务数据
		Map<String, String> temp1 = new HashMap<String, String>();
		temp1.put("PAYLIST_CODE", param.get("PAYLIST_CODE") + "");
		temp1.put("PMT", "PMT租金");
		temp1.put("ZJ", "租金");
		temp1.put("SYBJ", "剩余本金");
		temp1.put("D", "第");
		temp1.put("QI", "期");
		Dao.insert("PayTask.synchronizationBeginning", temp1);
		
		return new ReplyHtml(VM.html("buyBack/toCheckedFormHG1.vm",context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "yangxue", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toCheckedFund1(){//核销数据操作 分期核销
		Map<String,Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());//用户编号
		param.put("USERNAME", Security.getUser().getName());//用户姓名
		BuyBackCheckService bbcs = new BuyBackCheckService();
		
		Map<String,Object> map = _getParameters();
		//查询如果资金没有未核销的就说明已全部核销
		List is_verification_finish = Dao.selectList("BuyBack.is_verification_finish", map);
		if(is_verification_finish==null||is_verification_finish.isEmpty()){
			throw new ActionException("请勿重复操作核销");
		}
		
		
		//在供应商选择了回购款的来源后修改池子里面的钱数量
		Map pool_infos = Dao.selectOne("BuyBack.quey_pool_infos",map.get("PAYLIST_CODE"));
		if(pool_infos!=null&&!pool_infos.isEmpty()){
			JSONArray arr = JSONArray.fromObject(pool_infos.get("POOL_INFO"));
			if(arr!=null&&arr.size()>0){
				for (int i = 0; i < arr.size(); i++) {
					JSONObject json = arr.getJSONObject(i);
					if(json.get("pool_id")!=null&&!"".equals(json.get("pool_id"))){
						double money = Dao.selectDouble("BuyBack.deposit_pool_money",json.get("pool_id"));
						if(money<json.getDouble("pool_money")){
							throw new ActionException("供应商选择的低扣款小于回购款，请退回重新选择低扣款");
						}
						Map jsonMap = JsonUtil.toMap(json);
						Dao.update("BuyBack.updateCheckedDeposit_pool_money", jsonMap);
					}
				}
			}
		}
		
		//删除大于结算日的罚息
		Dao.delete("BuyBack.FI_ACCOUNT_DATE_FX", map);
		//更新提前结清表数据
		update_fil_buy_back(map);
		//如果选择了保证金抵扣就冲抵保证金
		//并且保证金金额大于0
		Map  isM = Dao.selectOne("BuyBack.is_bzj", map);
		if(isM!=null) new ActionException("回购数据错误");
		double DEPOSIT_ = (isM.get("BZJ")!=null&&!"".equals(isM.get("BZJ").toString()))?Double.parseDouble(isM.get("BZJ").toString()):0;
		if("0".equals(isM.get("IS_DEPOSIT")+"")&&DEPOSIT_>0){
			Map tm = new HashMap();
			tm.put("PAYCODEPARAM", map.get("PAYLIST_CODE"));
			double EXTRA_MONEY = map.get("FI_REALITY_ACCOUNT")!=null&&!"".equals(map.get("FI_REALITY_ACCOUNT")+"")?Double.parseDouble(map.get("FI_REALITY_ACCOUNT")+""):0d;
			tm.put("EXTRA_MONEY", EXTRA_MONEY);
			tm.put("FI_ACCOUNT_DATE_", map.get("FI_ACCOUNT_DATE"));
			tm.put("FI_REALITY_BANK_", map.get("FI_REALITY_BANK"));
			Dao.selectOne("BuyBack.PRC_FUNDRED_BUY_FQ", tm);
		}else {
			Map tm = new HashMap();
			tm.put("PAYCODEPARAM", map.get("PAYLIST_CODE"));
			double EXTRA_MONEY = map.get("FI_REALITY_ACCOUNT")!=null&&!"".equals(map.get("FI_REALITY_ACCOUNT")+"")?Double.parseDouble(map.get("FI_REALITY_ACCOUNT")+""):0d;
			tm.put("EXTRA_MONEY", EXTRA_MONEY);
			tm.put("FI_ACCOUNT_DATE_", map.get("FI_ACCOUNT_DATE"));
			tm.put("FI_REALITY_BANK_", map.get("FI_REALITY_BANK"));
			Dao.selectOne("BuyBack.PRC_FUNDRED_BUY_FQ_NO", tm);
		}
		
		/*//首先冲红
		Map<String, Object> m = new HashMap<String, Object>();	
		Map<String,Object> bbcsMap = (Map<String, Object>) bbcs.toRedFinance(param);
		if(bbcsMap!=null){
			if(!"".equals(bbcsMap.get("BZJ_HEAD"))&&bbcsMap.get("BZJ_HEAD")!=null){
				m.put("BZJ_HEAD", bbcsMap.get("BZJ_HEAD"));
			}else if(!"".equals(bbcsMap.get("DBBZJ_HEAD"))&&bbcsMap.get("DBBZJ_HEAD")!=null){
				m.put("DBBZJ_HEAD", bbcsMap.get("DBBZJ_HEAD"));
			}else if(!"".equals(bbcsMap.get("LGJ_HEAD"))&&bbcsMap.get("LGJ_HEAD")!=null){
				m.put("LGJ_HEAD", bbcsMap.get("LGJ_HEAD"));
			}
		}
		
		
		//首先抵扣保证金留购价 
		if((param.get("BZJ")!=null&&!"".equals(param.get("BZJ"))&&"0".equals(param.get("BZJ")))||(param.get("isLiugou")!=null&&!"".equals(param.get("isLiugou"))&&"0".equals(param.get("isLiugou").toString()))){
			Map<String,Object> bzjAndLgj = (Map<String, Object>) bbcs.doAddBZJandLgj(param);
			
		    if(bzjAndLgj!=null){
		    	//添加时候明细
		    	bzjAndLgj.put("BUY_BACK_ID", param.get("BUY_BACK_ID"));
		    	bbcs.doCheckedBZJandLGJ(bzjAndLgj);
		    	
		    	//核销审核
		    	bzjAndLgj.put("FUND_ID", bzjAndLgj.get("BZJandLGJ"));
		    	bbcs.doCheckedFund1(bzjAndLgj);
		    	
		    	
		    	m.put("BZJandLGJ", bzjAndLgj.get("BZJandLGJ"));
		    }
		}
		
	    //其次核销DB保证金
		if(param.get("DBBZJ")!=null&&!"".equals(param.get("DBBZJ"))&&"0".equals(param.get("DBBZJ").toString())){
			Map<String,Object> DBBZJ = (Map<String, Object>) bbcs.toAddDBDk(param);
		    if(DBBZJ!=null){
		    	//添加时候明细
		    	DBBZJ.put("BUY_BACK_ID", param.get("BUY_BACK_ID"));
		    	bbcs.doCheckedDBbzj(DBBZJ);
		    	//核销审核
		    	DBBZJ.put("FUND_ID", DBBZJ.get("dbBZJdk"));
		    	bbcs.doCheckedFund1(DBBZJ);
		    	
		    	m.put("dbBZJdk", DBBZJ.get("dbBZJdk"));
		    }
		}
		
		//最后核销来款
		String HEAD_ID = (String) bbcs.toAppRentCheckFQ(param);
		
		if(HEAD_ID != ""){
			m.put("LK_HEAD", HEAD_ID);
		}
		
		JBPM.setExecutionVariable(param.get("JBPM_ID") + "", m);
		*/
		return new ReplyAjax().addOp(new OpLog("分期回购","核销首期款",""));
	}
	
	/****************************************************************************************
	 ****************************************项目回其他信息********Auth=杨雪****************
	 ****************************************************************************************/
	
	
	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyy-mm-dd
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 *         long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

//	/**
//	 * 回购通知书导出excel
//	 */
//	@aPermission(name = { "项目管理", "项目回购", "回购通知书导出excel" })
//	@aAuth(type = aAuthType.ALL)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply ExcelTest(){
//		Map<String, Object> param = _getParameters();
//		String path = null;
//		return new ExcelTest(param);
//	}
	/*
	 * 查询回购方id
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply getCustName(){
		Map<String, Object> param = _getParameters();
		List list = Dao.selectList("BuyBack.getCustName", param);
		JSONArray fromObject = JSONArray.fromObject(list);
		
		return new ReplyAjax(fromObject);
	}
	
	/**
	 * 适用回购流程
	 * 生成合同excel文件方法 需要传递TPM_ID(文件类型id(t_pdftemplate_maintenance表ID)) PATH(文件路径)
	 * PAYLIST_CODE 支付表编号
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply ExcelFile() {
		BuyBackService service1 = new BuyBackService();
		Map<String, Object> param = _getParameters();
		
		param.putAll(service1.selectRepoData(param));// 模版中需要的业务数据
		{
			// 计算excel数据
			Double ZJ_ZJ = 0.00;// 租金总计
			Double YS_ZJ_ZE = 0.00;// 已收租金总额
			Double SY_ZJ_ZE = 0.00;// 剩余租金总额
			Double HJ = 0.00;// 合计
			Double START_ZJ = 0.00;// 起租租金
			Double SUM_ZJ = 0.00;// 租金总计
			Double DEPOSIT = 0.00;// 保证金
			Double LEAVE = 0.00;// 留购价
			Double RENT_RECE = 0.00;// 到期未付租金违约金
			Double QT_FY = 0.00;// 其他费用合计
			Double UNPAID_INTEREST = 0.00;// 未到期租金利息
			Double PENALTY_RECE = 0.00;// 逾期金额
			Double LEASE_TOPRIC = 0.00;// 租赁物总价值
			Double BEGINNING_PAID = 0.00;// 已还租金
			int WD_ZJ_QC = 0;// 未到租金期次
			int BEGINNING_NUM = 0;// 已还的最大期次
			
			if (param.containsKey("PENALTY_RECE") && !param.get("PENALTY_RECE").equals("")) {
				PENALTY_RECE = Double.parseDouble(param.get("PENALTY_RECE").toString());
				PENALTY_RECE = Util.formatDouble2(PENALTY_RECE);
			}
			param.put("PENALTY_RECE", PENALTY_RECE);// 逾期金额
			
			if (param.containsKey("LEASE_TOPRIC") && !param.get("LEASE_TOPRIC").equals("")) {
				LEASE_TOPRIC = Double.parseDouble(param.get("LEASE_TOPRIC").toString());
			}
			param.put("LEASE_TOPRIC", LEASE_TOPRIC);// 租赁物总价值
			
			if (param.containsKey("BEGINNING_PAID") && !param.get("BEGINNING_PAID").equals("")) {
				BEGINNING_PAID = Double.parseDouble(param.get("BEGINNING_PAID").toString());
			}
			param.put("BEGINNING_PAID", BEGINNING_PAID);// 已还租金
			
			if (param.containsKey("START_ZJ") && !param.get("START_ZJ").equals("")) {
				START_ZJ = Double.parseDouble(param.get("START_ZJ").toString());
			}
			param.put("START_ZJ", START_ZJ);// 起租租金
			
			if (param.containsKey("SUM_ZJ") && !param.get("SUM_ZJ").equals("")) {
				SUM_ZJ = Double.parseDouble(param.get("SUM_ZJ").toString());
			}
			param.put("SUM_ZJ", SUM_ZJ);// 租金总计
			
			ZJ_ZJ = START_ZJ + SUM_ZJ;
			param.put("ZJ_ZJ", ZJ_ZJ);// 租金总计
			
			if (param.containsKey("DEPOSIT") && !param.get("DEPOSIT").equals("")) {
				DEPOSIT = Double.parseDouble(param.get("DEPOSIT").toString());
			}
			if(DEPOSIT>0){
				param.put("DEPOSIT", "元+保证金"+DEPOSIT);// 保证金
				param.put("is_bzj", "+保证金冲抵");
			}
			if (param.containsKey("LEAVE") && !param.get("LEAVE").equals("")) {
				LEAVE = Double.parseDouble(param.get("LEAVE").toString());
			}
			param.put("LEAVE", LEAVE);// 留购价
			
			double HG_LEAVE = param.get("HG_LEAVE")==null||"".equals(param.get("HG_LEAVE"))?0:Double.parseDouble(param.get("HG_LEAVE")+"");
			param.put("HG_LEAVE", HG_LEAVE);
			
			YS_ZJ_ZE = BEGINNING_PAID+START_ZJ+LEAVE+DEPOSIT;
			param.put("YS_ZJ_ZE", YS_ZJ_ZE);// 已收租金总额
			
			SY_ZJ_ZE = ZJ_ZJ - YS_ZJ_ZE;
			param.put("SY_ZJ_ZE", SY_ZJ_ZE);// 剩余租金总额
			
			if (param.containsKey("RENT_RECE") && !param.get("RENT_RECE").equals("")) {
				RENT_RECE = Double.parseDouble(param.get("RENT_RECE").toString());
			}
			param.put("RENT_RECE", RENT_RECE);// 到期未付租金违约金
			
			if (param.containsKey("QT_FY") && !param.get("QT_FY").equals("")) {
				QT_FY = Double.parseDouble(param.get("QT_FY").toString());
			}
			param.put("QT_FY", QT_FY);// 其他费用合计
			
			if (param.containsKey("UNPAID_INTEREST1") && !param.get("UNPAID_INTEREST1").equals("")) {
				UNPAID_INTEREST = Double.parseDouble(param.get("UNPAID_INTEREST1").toString());
			}
			param.put("UNPAID_INTEREST", UNPAID_INTEREST);// 未到期租金利息
			
			HJ = SY_ZJ_ZE + PENALTY_RECE + HG_LEAVE + QT_FY - UNPAID_INTEREST;
			HJ = Util.formatDouble2(HJ);
			param.put("HJ", HJ);// 合计
			
			if (param.containsKey("BEGINNING_NUM") && !param.get("BEGINNING_NUM").equals("")) {
				BEGINNING_NUM = Integer.parseInt(param.get("BEGINNING_NUM").toString());// 已还的最大期次
			}
			WD_ZJ_QC = BEGINNING_NUM + 1;
			param.put("WD_ZJ_QC", WD_ZJ_QC);// 未到租金期次
			
			//-----------------------租赁期限
			param.put("ZLQX", (param.get("LEASE_PERIOD")==null||"".equals(param.get("LEASE_PERIOD"))?1:Integer.parseInt(param.get("LEASE_PERIOD")+""))
				*(param.get("LEASE_TERM")==null||"".equals(param.get("LEASE_TERM"))?1:Integer.parseInt(param.get("LEASE_TERM")+""))+""	
				);
			//顺道跟新下回购表（保证数据的准确性）
			Dao.update("BuyBack.buyBackNormal23",param);
			param.put("FILE_NAME", param.get("NAME")+""+param.get("PRO_CODE")+param.get("FILE_NAME"));
		}
		//分期回购导出债权协议的时候特殊处理
		if("B59.xls".equals(param.get("PATH"))){
			List<Map<String,String>> rowDenormaliser = PayTaskService.rowDenormaliser(Dao.selectInt("BuyBack.queryPlanMaxId", param)+"");
			int lease_term = Dao.selectInt("BuyBack.buy_back_lease_term", param);
			double fqlx = 0;
			String fqzj = "";
			List<Map<String, String>> list_zj1 = new ArrayList<Map<String,String>>(rowDenormaliser.subList(rowDenormaliser.size()-lease_term, rowDenormaliser.size()));
			List<Map<String, String>> list_zj = new ArrayList<Map<String,String>>();
			for(int j=0;j<list_zj1.size();j++){
				Map<String, String> map = list_zj1.get(j);
				//累加利息
				fqlx = fqlx + (map.get("lx")==null||"".equals(map.get("lx"))?0:Double.parseDouble(map.get("lx")));
				fqzj = map.get("zj");
				Map<String, String> tm = new HashMap<String, String>();
				tm.put("xh", j+1+"");
				tm.put("sj", map.get("PAY_DATE"));
				tm.put("zj", map.get("zj"));
				tm.put("lx", map.get("lx"));
				tm.put("bj", map.get("bj"));
				list_zj.add(tm);
				
			}
			param.putAll((Map)Dao.selectOne("BuyBack.buy_back_fq_lqbz", param));
			param.put("fqqc", lease_term);
			param.put("fqlx", fqlx+"");
			param.put("fqzj", fqzj);
			param.put("list_zj", list_zj);
		}
		return new ExcelTest(param);
			
			
	}
	/**
	 * 适用回购流程
	 * 生成合同excel文件方法 需要传递TPM_ID(文件类型id(t_pdftemplate_maintenance表ID)) PATH(文件路径)
	 * PAYLIST_CODE 支付表编号
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply ExcelFile11() {
		BuyBackService service1 = new BuyBackService();
		Map<String, Object> param = _getParameters();
		// param.put("PAYLIST_CODE", "SFPDJT130408-1");
		// param.put("TPM_ID", "77");
		//param.putAll(service1.selectTpmPath(param));// 查询模版path
		//param.put("FILE_PATH", "");
		//if (StringUtils.isBlank(param.get("FILE_PATH").toString())) { throw new ActionException("没有找到指定的合同模板"); }
		param.putAll(service1.selectRepoData11(param));// 模版中需要的业务数据
		{
			// 计算excel数据
			Double ZJ_ZJ = 0.00;// 租金总计
			Double YS_ZJ_ZE = 0.00;// 已收租金总额
			Double SY_ZJ_ZE = 0.00;// 剩余租金总额
			Double HJ = 0.00;// 合计
			Double START_ZJ = 0.00;// 起租租金
			Double SUM_ZJ = 0.00;// 租金总计
			Double DEPOSIT = 0.00;// 保证金
			Double LEAVE = 0.00;// 留购价
			Double RENT_RECE = 0.00;// 到期未付租金违约金
			Double QT_FY = 0.00;// 其他费用合计
			Double UNPAID_INTEREST = 0.00;// 未到期租金利息
			Double PENALTY_RECE = 0.00;// 逾期金额
			Double LEASE_TOPRIC = 0.00;// 租赁物总价值
			Double BEGINNING_PAID = 0.00;// 已还租金
			int WD_ZJ_QC = 0;// 未到租金期次
			int BEGINNING_NUM = 0;// 已还的最大期次
			
			if (param.containsKey("PENALTY_RECE") && !param.get("PENALTY_RECE").equals("")) {
				PENALTY_RECE = Double.parseDouble(param.get("PENALTY_RECE").toString());
				PENALTY_RECE = Util.formatDouble2(PENALTY_RECE);
			}
			param.put("PENALTY_RECE", PENALTY_RECE);// 逾期金额
			
			if (param.containsKey("LEASE_TOPRIC") && !param.get("LEASE_TOPRIC").equals("")) {
				LEASE_TOPRIC = Double.parseDouble(param.get("LEASE_TOPRIC").toString());
			}
			param.put("LEASE_TOPRIC", LEASE_TOPRIC);// 租赁物总价值
			
			if (param.containsKey("BEGINNING_PAID") && !param.get("BEGINNING_PAID").equals("")) {
				BEGINNING_PAID = Double.parseDouble(param.get("BEGINNING_PAID").toString());
			}
			param.put("BEGINNING_PAID", BEGINNING_PAID);// 已还租金
			
			if (param.containsKey("START_ZJ") && !param.get("START_ZJ").equals("")) {
				START_ZJ = Double.parseDouble(param.get("START_ZJ").toString());
			}
			param.put("START_ZJ", START_ZJ);// 起租租金
			
			if (param.containsKey("SUM_ZJ") && !param.get("SUM_ZJ").equals("")) {
				SUM_ZJ = Double.parseDouble(param.get("SUM_ZJ").toString());
			}
			param.put("SUM_ZJ", SUM_ZJ);// 租金总计
			
			ZJ_ZJ = START_ZJ + SUM_ZJ;
			param.put("ZJ_ZJ", ZJ_ZJ);// 租金总计
			
			if (param.containsKey("DEPOSIT") && !param.get("DEPOSIT").equals("")) {
				DEPOSIT = Double.parseDouble(param.get("DEPOSIT").toString());
			}
			if(DEPOSIT>0){
				param.put("DEPOSIT", "元+保证金"+DEPOSIT);// 保证金
				param.put("is_bzj", "+保证金冲抵");
			}
			if (param.containsKey("LEAVE") && !param.get("LEAVE").equals("")) {
				LEAVE = Double.parseDouble(param.get("LEAVE").toString());
			}
			param.put("LEAVE", LEAVE);// 留购价
			
			double HG_LEAVE = param.get("HG_LEAVE")==null||"".equals(param.get("HG_LEAVE"))?0:Double.parseDouble(param.get("HG_LEAVE")+"");
			param.put("HG_LEAVE", HG_LEAVE);
			
			YS_ZJ_ZE = BEGINNING_PAID+START_ZJ+LEAVE+DEPOSIT;
			param.put("YS_ZJ_ZE", YS_ZJ_ZE);// 已收租金总额
			
			SY_ZJ_ZE = ZJ_ZJ - YS_ZJ_ZE;
			param.put("SY_ZJ_ZE", SY_ZJ_ZE);// 剩余租金总额
			
			if (param.containsKey("RENT_RECE") && !param.get("RENT_RECE").equals("")) {
				RENT_RECE = Double.parseDouble(param.get("RENT_RECE").toString());
			}
			param.put("RENT_RECE", RENT_RECE);// 到期未付租金违约金
			
			if (param.containsKey("QT_FY") && !param.get("QT_FY").equals("")) {
				QT_FY = Double.parseDouble(param.get("QT_FY").toString());
			}
			param.put("QT_FY", QT_FY);// 其他费用合计
			
			if (param.containsKey("UNPAID_INTEREST1") && !param.get("UNPAID_INTEREST1").equals("")) {
				UNPAID_INTEREST = Double.parseDouble(param.get("UNPAID_INTEREST1").toString());
			}
			param.put("UNPAID_INTEREST", UNPAID_INTEREST);// 未到期租金利息
			
			HJ = SY_ZJ_ZE + PENALTY_RECE + HG_LEAVE + QT_FY - UNPAID_INTEREST;
			HJ = Util.formatDouble2(HJ);
			param.put("HJ", HJ);// 合计
			
			if (param.containsKey("BEGINNING_NUM") && !param.get("BEGINNING_NUM").equals("")) {
				BEGINNING_NUM = Integer.parseInt(param.get("BEGINNING_NUM").toString());// 已还的最大期次
			}
			WD_ZJ_QC = BEGINNING_NUM + 1;
			param.put("WD_ZJ_QC", WD_ZJ_QC);// 未到租金期次
			
			//-----------------------租赁期限
			param.put("ZLQX", (param.get("LEASE_PERIOD")==null||"".equals(param.get("LEASE_PERIOD"))?1:Integer.parseInt(param.get("LEASE_PERIOD")+""))
					*(param.get("LEASE_TERM")==null||"".equals(param.get("LEASE_TERM"))?1:Integer.parseInt(param.get("LEASE_TERM")+""))+""	
			);
			//顺道跟新下回购表（保证数据的准确性）
			Dao.update("BuyBack.buyBackNormal23",param);
			param.put("FILE_NAME", param.get("NAME")+""+param.get("PRO_CODE")+param.get("FILE_NAME"));
		}
		return new ExcelTest(param);
		
		
	}
	@SuppressWarnings({ "unused", "unchecked" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply ExcelFileBatch() {
		Map<String, Object> m = _getParameters();
		String fileName = "回购通知书(预警).zip";
		List list = Dao.selectList("BuyBack.butchWarning_pay_code2", m);
		ZipOutputStream out = null;
		byte[] buffer = new byte[2048];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String path = SkyEye.getConfig("file.path.temp")+"/"+new Date().getTime()+"/";
		String PATH = m.get("PATH") + "";
		try {
			out = new ZipOutputStream(baos);
			for (int i = 0; i < list.size(); i++) {
				HSSFWorkbook wb = new HSSFWorkbook(UTIL.RES.getResource("classpath:file/" + PATH).getInputStream());
				Map map = (Map)list.get(i);
				map.put("PAYLIST_CODE", map.get("PAY_CODE"));
				BatchExcelTest.getBatchExcel(map);
				m.putAll(map);
				PoiExcelUtil.doExcelTemplateMerge(wb, m);
				String name = (String) m.get("PAY_CODE")+m.get("NAME")+".xls";
				out.putNextEntry(new ZipEntry(name));
				out.setEncoding("GBK");
				
				wb.write(out);
				out.closeEntry();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new ReplyFile(baos.toByteArray(), "回购通知书(预警)"+m.get("SUP_NAME")+".zip");
	}
	
	
	/**
	 * 提前结清
	 * 生成合同excel文件方法 需要传递TPM_ID(文件类型id(t_pdftemplate_maintenance表ID)) PATH(文件路径)
	 * PAYLIST_CODE 支付表编号
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply ExcelFile2() {
		BuyBackService service1 = new BuyBackService();
		Map<String, Object> param = _getParameters();
		param.putAll(service1.doShowJQBaseByPayId(param));// 模版中需要的业务数据
		return new ExcelTest(param);
	}
	
	/**
	 * 正常结清
	 * 生成合同excel文件方法 需要传递TPM_ID(文件类型id(t_pdftemplate_maintenance表ID)) PATH(文件路径)
	 * PAYLIST_CODE 支付表编号
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply ExcelFile4() {
		BuyBackService service1 = new BuyBackService();
		Map<String, Object> param = _getParameters();
		param.putAll(service1.doShowZCJQBaseByPayId(param));// 模版中需要的业务数据
		return new ExcelTest(param);
	}
	
	/**
	 * 项目回购
	 * 生成合同excel文件方法 需要传递TPM_ID(文件类型id(t_pdftemplate_maintenance表ID)) PATH(文件路径)
	 * PAYLIST_CODE 支付表编号
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply ExcelFile3() {
		BuyBackService service1 = new BuyBackService();
		Map<String, Object> param = _getParameters();
		param.putAll(service1.doShowXMHGBaseByPayId(param));// 模版中需要的业务数据
		return new ExcelTest(param);
	}
	
	
	
	/**
	 *结清核销
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply settleVerification() {
		BuyBackService service1 = new BuyBackService();
		Map<String, Object> param = _getParameters();
		// param.put("PAYLIST_CODE", "SFPDJT130408-1");
		// param.put("TPM_ID", "77");
		//param.putAll(service1.selectTpmPath(param));// 查询模版path
		//param.put("FILE_PATH", "");
		//if (StringUtils.isBlank(param.get("FILE_PATH").toString())) { throw new ActionException("没有找到指定的合同模板"); }
		param.putAll(service1.selectRepoData(param));// 模版中需要的业务数据
		{
			// 计算excel数据
			Double ZJ_ZJ = 0.00;// 租金总计
			Double YS_ZJ_ZE = 0.00;// 已收租金总额
			Double SY_ZJ_ZE = 0.00;// 剩余租金总额
			Double HJ = 0.00;// 合计
			Double START_ZJ = 0.00;// 起租租金
			Double SUM_ZJ = 0.00;// 租金总计
			Double DEPOSIT = 0.00;// 保证金
			Double LEAVE = 0.00;// 留购价
			Double RENT_RECE = 0.00;// 到期未付租金违约金
			Double QT_FY = 0.00;// 其他费用合计
			Double UNPAID_INTEREST = 0.00;// 未到期租金利息
			Double PENALTY_RECE = 0.00;// 逾期金额
			Double LEASE_TOPRIC = 0.00;// 租赁物总价值
			Double BEGINNING_PAID = 0.00;// 已还租金
			int WD_ZJ_QC = 0;// 未到租金期次
			int BEGINNING_NUM = 0;// 已还的最大期次
			
			if (param.containsKey("PENALTY_RECE") && !param.get("PENALTY_RECE").equals("")) {
				PENALTY_RECE = Double.parseDouble(param.get("PENALTY_RECE").toString());
				PENALTY_RECE = Util.formatDouble2(PENALTY_RECE);
			}
			param.put("PENALTY_RECE", PENALTY_RECE);// 逾期金额
			
			if (param.containsKey("LEASE_TOPRIC") && !param.get("LEASE_TOPRIC").equals("")) {
				LEASE_TOPRIC = Double.parseDouble(param.get("LEASE_TOPRIC").toString());
			}
			param.put("LEASE_TOPRIC", LEASE_TOPRIC);// 租赁物总价值
			
			if (param.containsKey("BEGINNING_PAID") && !param.get("BEGINNING_PAID").equals("")) {
				BEGINNING_PAID = Double.parseDouble(param.get("BEGINNING_PAID").toString());
			}
			param.put("BEGINNING_PAID", BEGINNING_PAID);// 已还租金
			
			if (param.containsKey("START_ZJ") && !param.get("START_ZJ").equals("")) {
				START_ZJ = Double.parseDouble(param.get("START_ZJ").toString());
			}
			param.put("START_ZJ", START_ZJ);// 起租租金
			
			if (param.containsKey("SUM_ZJ") && !param.get("SUM_ZJ").equals("")) {
				SUM_ZJ = Double.parseDouble(param.get("SUM_ZJ").toString());
			}
			param.put("SUM_ZJ", SUM_ZJ);// 租金总计
			
			ZJ_ZJ = START_ZJ + SUM_ZJ;
			param.put("ZJ_ZJ", ZJ_ZJ);// 租金总计
			
			if (param.containsKey("DEPOSIT") && !param.get("DEPOSIT").equals("")) {
				DEPOSIT = Double.parseDouble(param.get("DEPOSIT").toString());
			}
			param.put("DEPOSIT", DEPOSIT);// 保证金
			//查询是否要做保证金抵扣
			boolean flag = false;
			Map dm = Dao.selectOne("BuyBack.select_fil_paychange_parameter", param);
			if(dm!=null&&!dm.isEmpty()){
				JSONObject PARAM_VALUE = JSONObject.fromObject(dm.get("PARAM_VALUE"));
				if("0".equals(PARAM_VALUE.get("STATUS"))){
					param.put("保证金冲抵", "+保证金冲抵"+DEPOSIT+"元");
					flag = true;
				}
			}
			
			if (param.containsKey("LEAVE") && !param.get("LEAVE").equals("")) {
				LEAVE = Double.parseDouble(param.get("LEAVE").toString());
			}
			param.put("LEAVE", LEAVE);// 留购价
			
			YS_ZJ_ZE = BEGINNING_PAID+START_ZJ;
			if(flag){//如果选了保证金冲抵，则已收要加上保证金冲抵金额
				YS_ZJ_ZE = YS_ZJ_ZE + DEPOSIT;
			}
			param.put("YS_ZJ_ZE", YS_ZJ_ZE);// 已收租金总额
			
			SY_ZJ_ZE = ZJ_ZJ - YS_ZJ_ZE;
			param.put("SY_ZJ_ZE", SY_ZJ_ZE);// 剩余租金总额
			
			if (param.containsKey("RENT_RECE") && !param.get("RENT_RECE").equals("")) {
				RENT_RECE = Double.parseDouble(param.get("RENT_RECE").toString());
			}
			param.put("RENT_RECE", RENT_RECE);// 到期未付租金违约金
			
			if (param.containsKey("QT_FY") && !param.get("QT_FY").equals("")) {
				QT_FY = Double.parseDouble(param.get("QT_FY").toString());
			}
			param.put("QT_FY", QT_FY);// 其他费用合计
			
			if (param.containsKey("UNPAID_INTEREST1") && !param.get("UNPAID_INTEREST1").equals("")) {
				UNPAID_INTEREST = Double.parseDouble(param.get("UNPAID_INTEREST1").toString());
			}
			param.put("UNPAID_INTEREST", UNPAID_INTEREST);// 未到期租金利息
			
			HJ = SY_ZJ_ZE + RENT_RECE + LEAVE + QT_FY - UNPAID_INTEREST;
			param.put("HJ", HJ);// 合计
			
			if (param.containsKey("BEGINNING_NUM") && !param.get("BEGINNING_NUM").equals("")) {
				BEGINNING_NUM = Integer.parseInt(param.get("BEGINNING_NUM").toString());// 已还的最大期次
			}
			WD_ZJ_QC = BEGINNING_NUM + 1;
			param.put("WD_ZJ_QC", WD_ZJ_QC);// 未到租金期次
			
			//-----------------------租赁期限
			param.put("ZLQX", (param.get("LEASE_PERIOD")==null||"".equals(param.get("LEASE_PERIOD"))?1:Integer.parseInt(param.get("LEASE_PERIOD")+""))
				*(param.get("LEASE_TERM")==null||"".equals(param.get("LEASE_TERM"))?1:Integer.parseInt(param.get("LEASE_TERM")+""))+""	
			);
			//合计（结清款）
			double hej = ZJ_ZJ-YS_ZJ_ZE+RENT_RECE+QT_FY-UNPAID_INTEREST;
			System.out.println("合计--------------------》"+hej);
		}
		return new ReplyAjax("操作成功");
	}
	//更新结清表参数
	@SuppressWarnings("unchecked")
	private Map update_fil_buy_back(Map<String, Object> param) {
		BuyBackService service1 = new BuyBackService();
		// param.put("PAYLIST_CODE", "SFPDJT130408-1");
		// param.put("TPM_ID", "77");
		//param.putAll(service1.selectTpmPath(param));// 查询模版path
		//param.put("FILE_PATH", "");
		//if (StringUtils.isBlank(param.get("FILE_PATH").toString())) { throw new ActionException("没有找到指定的合同模板"); }
		param.putAll(service1.selectRepoData(param));// 模版中需要的业务数据
		{
			// 计算excel数据
			Double ZJ_ZJ = 0.00;// 租金总计
			Double YS_ZJ_ZE = 0.00;// 已收租金总额
			Double SY_ZJ_ZE = 0.00;// 剩余租金总额
			Double HJ = 0.00;// 合计
			Double START_ZJ = 0.00;// 起租租金
			Double SUM_ZJ = 0.00;// 租金总计
			Double DEPOSIT = 0.00;// 保证金
			Double LEAVE = 0.00;// 留购价
			Double RENT_RECE = 0.00;// 到期未付租金违约金
			Double QT_FY = 0.00;// 其他费用合计
			Double UNPAID_INTEREST = 0.00;// 未到期租金利息
			Double PENALTY_RECE = 0.00;// 逾期金额
			Double LEASE_TOPRIC = 0.00;// 租赁物总价值
			Double BEGINNING_PAID = 0.00;// 已还租金
			int WD_ZJ_QC = 0;// 未到租金期次
			int BEGINNING_NUM = 0;// 已还的最大期次
			
			if (param.containsKey("PENALTY_RECE") && !param.get("PENALTY_RECE").equals("")) {
				PENALTY_RECE = Double.parseDouble(param.get("PENALTY_RECE").toString());
				PENALTY_RECE = Util.formatDouble2(PENALTY_RECE);
			}
			param.put("PENALTY_RECE", PENALTY_RECE);// 逾期金额
			
			if (param.containsKey("LEASE_TOPRIC") && !param.get("LEASE_TOPRIC").equals("")) {
				LEASE_TOPRIC = Double.parseDouble(param.get("LEASE_TOPRIC").toString());
			}
			param.put("LEASE_TOPRIC", LEASE_TOPRIC);// 租赁物总价值
			
			if (param.containsKey("BEGINNING_PAID") && !param.get("BEGINNING_PAID").equals("")) {
				BEGINNING_PAID = Double.parseDouble(param.get("BEGINNING_PAID").toString());
			}
			param.put("BEGINNING_PAID", BEGINNING_PAID);// 已还租金
			
			if (param.containsKey("START_ZJ") && !param.get("START_ZJ").equals("")) {
				START_ZJ = Double.parseDouble(param.get("START_ZJ").toString());
			}
			param.put("START_ZJ", START_ZJ);// 起租租金
			
			if (param.containsKey("SUM_ZJ") && !param.get("SUM_ZJ").equals("")) {
				SUM_ZJ = Double.parseDouble(param.get("SUM_ZJ").toString());
			}
			param.put("SUM_ZJ", SUM_ZJ);// 租金总计
			
			ZJ_ZJ = START_ZJ + SUM_ZJ;
			param.put("ZJ_ZJ", ZJ_ZJ);// 租金总计
			
			if (param.containsKey("DEPOSIT") && !param.get("DEPOSIT").equals("")) {
				DEPOSIT = Double.parseDouble(param.get("DEPOSIT").toString());
			}
			param.put("DEPOSIT", DEPOSIT);// 保证金
			//查询是否要做保证金抵扣
			boolean flag = false;
			Map dm = Dao.selectOne("BuyBack.select_fil_paychange_parameter", param);
			if(dm!=null&&!dm.isEmpty()){
				JSONObject PARAM_VALUE = JSONObject.fromObject(dm.get("PARAM_VALUE"));
				if("0".equals(PARAM_VALUE.get("STATUS"))){
					param.put("保证金冲抵", "+保证金冲抵"+DEPOSIT+"元");
					flag = true;
				}
			}
			
			if (param.containsKey("LEAVE") && !param.get("LEAVE").equals("")) {
				LEAVE = Double.parseDouble(param.get("LEAVE").toString());
			}
			param.put("LEAVE", LEAVE);// 留购价
			
			YS_ZJ_ZE = BEGINNING_PAID+START_ZJ;
			if(flag){//如果选了保证金冲抵，则已收要加上保证金冲抵金额
				YS_ZJ_ZE = YS_ZJ_ZE + DEPOSIT;
			}
			param.put("YS_ZJ_ZE", YS_ZJ_ZE);// 已收租金总额
			
			SY_ZJ_ZE = ZJ_ZJ - YS_ZJ_ZE;
			param.put("SY_ZJ_ZE", SY_ZJ_ZE);// 剩余租金总额
			
			if (param.containsKey("RENT_RECE") && !param.get("RENT_RECE").equals("")) {
				RENT_RECE = Double.parseDouble(param.get("RENT_RECE").toString());
			}
			param.put("RENT_RECE", RENT_RECE);// 到期未付租金违约金
			
			if (param.containsKey("QT_FY") && !param.get("QT_FY").equals("")) {
				QT_FY = Double.parseDouble(param.get("QT_FY").toString());
			}
			param.put("QT_FY", QT_FY);// 其他费用合计
			
			if (param.containsKey("UNPAID_INTEREST1") && !param.get("UNPAID_INTEREST1").equals("")) {
				UNPAID_INTEREST = Double.parseDouble(param.get("UNPAID_INTEREST1").toString());
			}
			param.put("UNPAID_INTEREST", UNPAID_INTEREST);// 未到期租金利息
			
			HJ = SY_ZJ_ZE + RENT_RECE + LEAVE + QT_FY - UNPAID_INTEREST;
			param.put("HJ", HJ);// 合计
			
			if (param.containsKey("BEGINNING_NUM") && !param.get("BEGINNING_NUM").equals("")) {
				BEGINNING_NUM = Integer.parseInt(param.get("BEGINNING_NUM").toString());// 已还的最大期次
			}
			WD_ZJ_QC = BEGINNING_NUM + 1;
			param.put("WD_ZJ_QC", WD_ZJ_QC);// 未到租金期次
			
			//-----------------------租赁期限
			param.put("ZLQX", (param.get("LEASE_PERIOD")==null||"".equals(param.get("LEASE_PERIOD"))?1:Integer.parseInt(param.get("LEASE_PERIOD")+""))
				*(param.get("LEASE_TERM")==null||"".equals(param.get("LEASE_TERM"))?1:Integer.parseInt(param.get("LEASE_TERM")+""))+""	
				);
			param.put("FILE_NAME", param.get("NAME")+""+param.get("PRO_CODE")+param.get("FILE_NAME"));
			
			//更新回购
			Map mm = new HashMap();
			mm.put("HIRE_BANK", param.get("FI_REALITY_BANK"));//核销银行
			mm.put("RENT_TOTAL", param.get("ZJ_ZJ"));//租金总计
			mm.put("RENT_PAID", BEGINNING_PAID+START_ZJ);//已收租金包含起租租金
			mm.put("RENT_LEFT", ZJ_ZJ -BEGINNING_PAID-START_ZJ);//剩余租金
			mm.put("PENALTY_AMT", param.get("PENALTY_RECE"));//违约金
			mm.put("INTEREST_UNDO", param.get("UNPAID_INTEREST"));//未到期租金利息
			mm.put("SUM_AMT_BACK", param.get("FI_REALITY_ACCOUNT"));//提前结清款
			mm.put("OTHER_SHOULD_PAY", param.get("QT_FY"));//其他应收款
			mm.put("CORPUS_UNDO", Dao.selectDouble("BuyBack.ws_bj", param));//未收本金
			mm.put("PAYLIST_CODE", param.get("PAYLIST_CODE"));
			mm.put("REALITY_DATE", param.get("FI_ACCOUNT_DATE"));
			
			int num = Dao.update("BuyBack.update_fil_buy_back",mm);
			if(num>1) throw new ActionException("数据错误，请联系管理员");
			
			
		}
		return param;
		
	}
	
	//在流程第一步驳回的操作，撤销回购数据
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply repealBuyBack(String PAYLIST_CODE){
		/*Map<String, Object> param = JBPM.getVeriable(jbpm_id);*/
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("PAYLIST_CODE", PAYLIST_CODE);
		//Dao.selectOne("PRC_BE_QJL_PAY_CODE", param.get("PAYLIST_CODE")+"");
		//删除新建支付表，并回更应收表
		PayTaskService taskService = new PayTaskService();
		taskService.changeDefeated(param);
		//删除罚息
		Dao.delete("BuyBack.FI_ACCOUNT_DATE_FX2", param);
		//刷新罚息
		Map tm = new HashMap();
		tm.put("PAYCODEPARAM", param.get("PAYLIST_CODE"));
		tm.put("NEW_DATE", DateUtil.getDate(new Date()));
		Dao.selectOne("BuyBack.calculate_overdue_appointed", tm);
		
		//删除回购数据
		Dao.delete("BuyBack.FIL_BUY_BACK_DATE",param);
		return new ReplyAjax("操作成功");
	}
	
	//在流程第一步驳回的操作，撤销回购数据
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply deletePaymentTask(String jbpm_id){
		Map<String, Object> param = JBPM.getVeriable(jbpm_id);
		return new ReplyAjax("操作成功");
	}
	//在流程第一步驳回的操作，撤销提前结清数据数据
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply repealEarlySettlement(String jbpm_id){
		Map<String, Object> param = JBPM.getVeriable(jbpm_id);
		Dao.selectOne("PRC_BE_QJL_PAY_CODE", param.get("PAYLIST_CODE")+"");
		//删除新建支付表，并回更应收表
		PayTaskService taskService = new PayTaskService();
		taskService.changeDefeated(param);
		//删除罚息
		Dao.delete("BuyBack.FI_ACCOUNT_DATE_FX3", param);
		//刷新罚息
		Map tm = new HashMap();
		tm.put("PAYCODEPARAM", param.get("PAYLIST_CODE"));
		tm.put("NEW_DATE", DateUtil.getDate(new Date()));
		Dao.selectOne("BuyBack.calculate_overdue_appointed", tm);
		
		//删除回购数据
		Dao.delete("BuyBack.FIL_BUY_BACK_DATE2",param);
		return new ReplyAjax("操作成功");
	}
	//在流程第一步驳回的操作，撤销分期回购数据
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply repealBuyBackStages(String jbpm_id){
		Map<String, Object> param = JBPM.getVeriable(jbpm_id);
		Dao.selectOne("PRC_BE_QJL_PAY_CODE", param.get("PAYLIST_CODE")+"");
		//查询分期回购是否生成了新支付表，如果有新的就删除
		List list = Dao.selectList("BuyBack.query_repealBuyBackStages_fq", param);
		if(list!=null&&!list.isEmpty()){
			//删除新建支付表，并回更应收表
			PayTaskService taskService = new PayTaskService();
			taskService.changeDefeated(param);
		}
		//删除罚息
		Dao.delete("BuyBack.FI_ACCOUNT_DATE_FX2", param);
		//刷新罚息
		Map tm = new HashMap();
		tm.put("PAYCODEPARAM", param.get("PAYLIST_CODE"));
		tm.put("NEW_DATE", DateUtil.getDate(new Date()));
		Dao.selectOne("BuyBack.calculate_overdue_appointed", tm);
		
		//删除回购数据
		Dao.delete("BuyBack.FIL_BUY_BACK_DATE",param);
		return new ReplyAjax("操作成功");
	}
	//处理自设的 传参数
	public  Map<String, Object> getPostParams(List<FileItem> items) throws Exception {
		  Map<String, Object> result = new HashMap<String, Object>();
		  Iterator<FileItem> iter = items.iterator();
		  while (iter.hasNext()) {
		   FileItem item = iter.next();
		   if (item.isFormField() && null == item.getName()) {
		    result.put(item.getFieldName(), inputStream2String(item.getInputStream()));
		   } 
		  }
		  
		  return result;
	}
	private  String inputStream2String(InputStream is) throws Exception {
		  BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		  StringBuffer sb = new StringBuffer();
		  String line = "";
		  while (null != (line = bf.readLine())) {
		   sb.append(line);
		  }
		  return sb.toString();
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
	public Reply buyBackShow() {
		Map<String, Object> param = _getParameters();
		context.put("Format", UTIL.FORMAT);
		// 结清查询表单信息
		Map<String,Object> settleInfo = Dao.selectOne("BuyBack.buyBackInfo", param);
		context.put("buyBackInfo", settleInfo);
		
		//结清期次
		context.put("JQ_PEROID_LIST", Dao.selectList("PayTask.queryPeroidList", param));
		return new ReplyHtml(VM.html("buyBack/buyBackShow.vm", context));
	}
	
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply buyBackSave() {
		Map<String, Object> param = _getParameters();
		// 通过还款计划编号查询该还款计划是否在申请中或者被虚拟核销占用中（未退款未转应收）
		if (!BaseUtil.getVinualByPayListCode(param.get("PAYLIST_CODE") + "")) { throw new ActionException("该还款计划在申请中或者被虚拟核销占用中"); }
		// 插入结算日期的罚息信息
		JSONObject settleInfo = JSONObject.fromObject(param.get("settleInfo"));
		BuyBackService bs = new BuyBackService();
		bs.refreshPenaltyaAccountsDate(param.get("PAYLIST_CODE") + "", settleInfo.get("ACCOUNT_DATE") + "", "");
//		//提前结清冻结后的期次不产生罚息
//		double PENALTY_RECEPAGE=Double.parseDouble((settleInfo.get("DUE_RECE") == null || "".equals(settleInfo.get("DUE_RECE"))) ? "0" : settleInfo.get("DUE_RECE").toString());
//		double TOTAL_MONEY=Double.parseDouble((settleInfo.get("TOTAL_MONEY") == null || "".equals(settleInfo.get("TOTAL_MONEY"))) ? "0" : settleInfo.get("TOTAL_MONEY").toString());
//		
//		double PENALTY_RECE=new PayTaskService().chuliOverDue(param.get("PAYLIST_CODE") + "", settleInfo.get("ACCOUNT_DATE") + "");
//		
//		settleInfo.put("PENALTY_RECE",PENALTY_RECE);
//		settleInfo.put("DUE_RECE",PENALTY_RECE);
//		settleInfo.put("TOTAL_MONEY",TOTAL_MONEY-PENALTY_RECEPAGE+PENALTY_RECE);
		param.put("settleInfo", settleInfo);
		// 插入变更租金参数表FIL_PAYCHANGE_PARAMETER
		// 插入支付表主表
		//主表升版本
		Dao.insert("PayTask.upgrade_payplan", param);
		service.calculateParameter(param);
		service.calculateSaveHG(param);
		// 如果该项目有发起过回购预警则删除
		Dao.delete("BuyBack.del_fil_buy_back_status0", param.get("PAYLIST_CODE"));
		//发起流程
		String nextCode = service.calculateSaveJBPMNextCode(param);
		//刷中间表数据
		Dao.update("PayTask.upDateToJoin",settleInfo);
		return new ReplyAjax(true,nextCode,"下一步操作人为: ");
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "项目管理", "项目回购", "查看" })
	public Reply toMgshowDetailHG() {
		Map map=_getParameters();
		context.put("Format", UTIL.FORMAT);
		map.put("ID", map.get("PAY_ID"));
		Map<String, Object> settleInfo = service.doShowHGByPayId(map.get("PAY_ID")+"");
		context.put("buyBackInfo", settleInfo);
		return new ReplyHtml(VM.html("buyBack/buyBackViewShow.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply toMgHGshowDetailJBPM() {
		Map map=_getParameters();
		PayTaskService payService=new PayTaskService();
		context.put("Format", UTIL.FORMAT);
		map.put("ID", map.get("PAY_ID"));
		//项目回购保存项
		Map<String, Object> buyBackInfo = service.doShowHGByPayId(map.get("PAY_ID")+"");
		context.put("buyBackInfo", buyBackInfo);
		map.put("RENT_DATE", buyBackInfo.get("ACCOUNT_DATE"));
		//未核销明细项
		context.put("detailList", payService.queryJQDate(map));
		
		List DIKOUFUND=new PayTaskService().DIKOUFUND(map);
		context.put("DIKOUFUND", DIKOUFUND);
			
		
		//已经认款的来款
		List listFund=payService.listFundByCustID(buyBackInfo);
		context.put("listFund", listFund);
		return new ReplyHtml(VM.html("buyBack/buyBackViewShowJBPM.vm", context));
	}

}

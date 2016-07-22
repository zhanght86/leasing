package com.pqsoft.base.grantCredit.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.base.grantCredit.service.CreditLogService;
import com.pqsoft.base.grantCredit.service.SupplierCreditService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

public class SupplierCreditManagerAction extends Action {
	private String path = "base/grantCredit/";
	private SupplierCreditService creditService = new SupplierCreditService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "授信管理", "经销商授信管理", "列表" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		return new ReplyHtml(VM.html(path + "supplierCreditManager.vm", null));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name ={"授信管理","经销商授信管理","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = creditService.getCreditPage(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "授信管理", "经销商授信管理", "添加授信" })
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toAddSupplierCredit() {
		VelocityContext context = new VelocityContext();
		context.put("param",
				new SuppliersService().getOneSupplier(_getParameters()));
		context.put("CUGP_CODE", CodeService.getCode("经销商授信协议编号", null, null));
		List list=SysDictionaryMemcached.getList("经销商入网保证金规则");
		if(StringUtils.isNotBlank(list)&&list.size()>=1){
			context.put("RWBZJ", list);
			context.put("RWBZJBL", DataDictionaryMemcached.getList("经销商入网保证金比例").get(0));
		}else
			context.put("RWBZJ", null);
			
		context.put("MSG", "-1");
		return new ReplyHtml(VM.html(path + "addSupplierCredit.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","经销商授信管理","添加授信"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Object doAddSupplierCredit() {
		Map<String, Object> param = _getParametersIO(null, null, null);
		param.put("CREATE_ID", Security.getUser().getId());
		param.put("TYPE", "3");
		param.put("STATUS", "2");
		int i = creditService.doAddSupplierCredit(param);
		VelocityContext context = new VelocityContext();
		if (i >= 1) {
			List<String> list = JBPM.getDeploymentListByModelName("经销商授信审批",
					"", "");
			String pid = null;
			if (list.size() > 0) {
				pid = list.get(0);
			} else
				throw new ActionException("没有找到流程图");

			if (pid == null) {
				throw new ActionException("没有匹配到审批流程");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUGP_ID", param.get("CUGP_ID") + "");
			map.put("SUP_ID", param.get("CUST_ID") + "");
			context.put("CUGP_CODE", param.get("CUGP_CODE") + "");
			String jbpmid = JBPM.startProcessInstanceById(pid, "", "", "", map)
					.getId();
			context.put("RST", jbpmid);
			context.put("param", new SuppliersService().getOneSupplier(map));
		}		
		context.put("map", param);
		param.put("MEMO", "经销商授信添加");
		CreditLogService logService = new CreditLogService();
		logService.doInsertCreditLog(param);
		context.put("MSG", "1");
		return new ReplyHtml(VM.html(path + "addSupplierCredit.vm", context))
				.addOp(new OpLog("授信管理", "添加授信", param + ""));
	}

	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","经销商授信管理","添加授信"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toShowSupplierCredit() {
		Map<String, Object> param = _getParameters();
		CreditLogService logService = new CreditLogService();
		VelocityContext context = new VelocityContext();
		param = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");
		context.put("param", param);
		param.put("SUP_ID", param.get("CUST_ID"));
		List list=SysDictionaryMemcached.getList("经销商入网保证金规则");
		if(StringUtils.isNotBlank(list)&&list.size()>=1){
			context.put("RWBZJ", list);
			context.put("RWBZJBL", DataDictionaryMemcached.getList("经销商入网保证金比例").get(0));
		}else
			context.put("RWBZJ", null);
			
		context.put("sup", new SuppliersService().getOneSupplier(param));
		context.put("log", logService.toFindCreditLog(param));
		return new ReplyHtml(VM.html(path + "showSupplierCredit.vm",
				context));
	}

	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","经销商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toShowSupplierCreditForJbpm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");
		context.put("param", param);
		param.put("SUP_ID", param.get("CUST_ID"));
		List list=SysDictionaryMemcached.getList("经销商入网保证金规则");
		if(StringUtils.isNotBlank(list)&&list.size()>=1){
			context.put("RWBZJ", list);
			context.put("RWBZJBL", DataDictionaryMemcached.getList("经销商入网保证金比例").get(0));
		}else
			context.put("RWBZJ", null);
			
		context.put("sup", new SuppliersService().getOneSupplier(param));
		return new ReplyHtml(VM.html(path + "showSupplierCreditForJbpm.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","经销商授信管理","修改控制额度及剩余额度"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply doUpdateCreditGrantAndLastPrice() {
		Map<String, Object> param = _getParameters();
		CreditLogService logService = new CreditLogService();
		Map<String, Object> credit = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");		
		String memo = "";
		if(credit.size()>0){
			if(credit.get("GRANT_PRICE").equals(param.get("GRANT_PRICE"))){
				memo += "授信控制金额未改变;";
			}else {
				memo=memo+"授信控制金额由"+new BigDecimal(credit.get("GRANT_PRICE").toString()).toPlainString()+"修改为"+new BigDecimal(param.get("GRANT_PRICE").toString()).toPlainString()+";";
			}
			
			if(credit.get("LAST_PRICE").equals(param.get("LAST_PRICE"))){
				memo += "授信余额未改变;";
			}else {
				memo=memo+"授信余额由"+new BigDecimal(credit.get("LAST_PRICE").toString()).toPlainString()+"修改为"+new BigDecimal(param.get("LAST_PRICE").toString()).toPlainString()+";";
			}
			
			if(credit.get("START_DATE").equals(param.get("START_DATE"))){
				memo += "授信起始日期未改变;";
			}else {
				memo=memo+"授信起始日期由"+credit.get("START_DATE").toString()+"修改为"+param.get("START_DATE").toString()+";";
			}
			
			if(credit.get("END_DATE").equals(param.get("END_DATE"))){
				memo += "授信结束日期未改变;";
			}else {
				memo=memo+"授信结束日期由"+credit.get("END_DATE").toString()+"修改为"+param.get("END_DATE").toString()+";";
			}
		}
		int i = creditService.updateCreditGrantAndLastPrice(param);
		param.put("CREATE_ID", Security.getUser().getId());
		param.put("MEMO", memo);
		logService.doInsertCreditLog(param);
		if (i >= 1)
			return new ReplyAjax(true).addOp(new OpLog("授信管理", "修改授信控制额度及剩余额度",
					param + ""));
		else
			return new ReplyAjax(false);
	}
	
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理",  "客户授信管理","修改授信"})
	@aDev(code = "170039", name = "yangxue", email = "yangxue@pqsoft.cn")
	public Reply toUpSupplierCreditForJbpm(){
		Map<String, Object> param = _getParameters();
		CreditLogService logService = new CreditLogService();
		VelocityContext context = new VelocityContext();
		param = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");
		context.put("param", param);
		param.put("SUP_ID", param.get("CUST_ID"));
		
		List list=SysDictionaryMemcached.getList("经销商入网保证金规则");
		if(StringUtils.isNotBlank(list)&&list.size()>=1){
			context.put("RWBZJ", list);
			context.put("RWBZJBL", DataDictionaryMemcached.getList("经销商入网保证金比例").get(0));
		}else
			context.put("RWBZJ", null);
		
		context.put("sup", new SuppliersService().getOneSupplier(param));
		context.put("log", logService.toFindCreditLog(param));
		return new ReplyHtml(VM.html(path + "updateSupplierCreditForJbpm.vm",
				context));
	}
	
	// @aAuth(type = aAuth.aAuthType.USER)
	// @aPermission(name ={"授信管理","经销商授信管理","查看"})
	// @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	// public Reply showApplyDan(){
	// VelocityContext context = new VelocityContext();
	// Map<String,Object> param = _getParameters();
	// Map<String,Object> creditMess = creditService.getOneCredit(param);
	// context.put("APPLY_TYPE", param.get("applyType").toString());
	// context.put("credits", creditMess);
	// return new ReplyHtml(VM.html(path+"changeApplyForm.vm", context));
	// }

	// @aAuth(type = aAuth.aAuthType.USER)
	// @aPermission(name ={"授信管理","经销商授信管理","基础授信申请单"})
	// @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	// public Reply firstApplyDan(){
	// VelocityContext context = new VelocityContext();
	// Map<String,Object> param = _getParameters();
	// Map<String,Object> creditMess = creditService.getOneCredit(param);
	// context.put("credits", creditMess);
	// return new ReplyHtml(VM.html(path+"firstApplyForm.vm", context));
	// }

	// @aAuth(type = aAuth.aAuthType.USER)
	// @aPermission(name ={"授信管理","经销商授信管理","验证[数据验证]"})
	// @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	// public Reply checkApplyOpe(){
	// Map<String,Object> param = _getParameters();
	// //查询各个申请是否已申请并在流程中
	// List<Object> appList = creditService.getAppOperate(param);
	// return new ReplyJson(JSONArray.fromObject(appList));
	// }
	// @aAuth(type = aAuth.aAuthType.USER)
	// @aPermission(name ={"授信管理","经销商授信管理","保存申请单"})
	// @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	// public Reply saveApply(){
	// Map<String,Object> params = _getParameters();
	// Map<String,Object>
	// param=JSONObject.fromObject(params.get("json").toString());
	// param.put("APPLY_SALE_ID", Security.getUser().getName());
	// Boolean flag = true ;
	// String msg = "";
	// int apply_id = creditService.getApplySeq();
	// param.put("APPLY_ID", apply_id);
	// int result = creditService.addApplyDan(param);
	// if(result >0){
	// flag = true ;
	// msg = "申请单保存成功！";
	// //发起评审流程
	// if(param.containsKey("APPLY_TYPE") && param.get("APPLY_TYPE") !=null ){
	// Map<String,Object> prcessParam = new HashMap<String, Object>();
	// prcessParam.put("APPLY_ID", String.valueOf(apply_id));
	// prcessParam.put("APPLY_TYPE", param.get("APPLY_TYPE"));
	// prcessParam.put("SUP_ID", param.get("SUP_ID").toString());
	// String creditDeploymentName = "";
	// //根据申请单类型发起对应的流程
	// if("0".equals(param.get("APPLY_TYPE").toString())){
	// creditDeploymentName ="供应商授信专决类审批";
	// }else if("1".equals(param.get("APPLY_TYPE").toString())){
	// creditDeploymentName ="授信额度辅导期转正申请";
	// }else if("2".equals(param.get("APPLY_TYPE").toString())){
	// creditDeploymentName ="授信标准额度变更审批";
	// }else if("3".equals(param.get("APPLY_TYPE").toString())){
	// creditDeploymentName ="授信特别调整额度审批";
	// }else if("4".equals(param.get("APPLY_TYPE").toString())){
	// creditDeploymentName ="授信临时保证额度变更申请";
	// }else if("5".equals(param.get("APPLY_TYPE").toString())){
	// creditDeploymentName ="授信一单一议额度变更申请";
	// }
	// List<String> prcessList =
	// JBPM.getDeploymentListByModelName(creditDeploymentName);
	// if(prcessList.size() > 0){
	// String jbpm_id =
	// JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),"","","",prcessParam).getId();
	// //变更授信状态
	// msg +=" 评审流程："+jbpm_id+"已发起！";
	// if("0".equals(param.get("APPLY_TYPE").toString())){
	// param.put("CREDIT_STATUS", "2");
	// param.put("JBPM_ID", jbpm_id);
	// creditService.updateCreditStatus(param);
	// }
	//
	// }else{
	// flag = false;
	// msg = "未找到流程："+creditDeploymentName;
	// throw new ActionException("未找到流程："+creditDeploymentName);
	// }
	// }
	// }else{
	// flag = false ;
	// msg = "保存失败！";
	// }
	// return new ReplyAjax(flag, msg);
	// }

	// @aAuth(type = aAuth.aAuthType.USER)
	// @aPermission(name ={"授信管理","授信申请单管理","列表显示"})
	// @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	// public Reply showCreditAppDan(){
	// VelocityContext context = new VelocityContext();
	// Map<String,Object> param = _getParameters();
	// context.put("param", param);
	// List statusMess = (List)new DataDictionaryMemcached().get("供应商授信申请单类型");
	// context.put("applyTypes", statusMess);
	// return new ReplyHtml(VM.html(path+"creditApplyManager.vm", context));
	// }
	//
	// @aAuth(type = aAuth.aAuthType.USER)
	// @aPermission(name ={"授信管理","授信申请单管理","列表显示"})
	// @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	// public Reply getApplyPage(){
	// Map<String,Object> param = _getParameters();
	// param.put("DIC_APPLY_TYPE", "供应商授信申请单类型");
	// param.put("DIC_APPLY_STATUS", "授信申请单状态");
	// Page applyPage = creditService.getApplyPage(param);
	// return new ReplyAjaxPage(applyPage);
	// }
	//
	// @aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name ={"授信管理","授信申请单管理","查看[列表操作]"})
	// @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	// public Reply detailApplyDan(){
	// VelocityContext context = new VelocityContext();
	// Map<String,Object> param = _getParameters();
	// Map<String,Object> applyDan = creditService.getApplyDan(param);
	// if("0".equals(applyDan.get("APPLY_TYPE").toString())){
	// context.put("credits", applyDan);
	// return new ReplyHtml(VM.html(path+"firstApplyDetail.vm", context));
	// }else{
	// context.put("APPLY_TYPE", applyDan.get("APPLY_TYPE").toString());
	// context.put("credits", applyDan);
	// return new ReplyHtml(VM.html(path+"changeApplyDetail.vm", context));
	// }
	// }
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "授信管理", "经销商授信管理", "列表" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply pageDataJXS() {
		//跳转到厂商列表
		return new ReplyHtml(VM.html(path + "supplierCreditManagerNewjxs.vm", null));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "授信管理", "经销商授信管理", "列表" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply pageNewJXS() {
		Map<String,Object> map_param = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(map_param.get("params"));
		Map<String,Object> map = (Map<String,Object>)(jsonArray.get(0));
		System.out.println(map+"--------");
		if("".equals(map.get("ID"))){
			map.put("SUPPLIERS_BZJ", 0);
			map.put("SUPPLIERS_BZJ_YE", 0);
			map.put("SUPPLIERS_BZJ_RATIO", 0);
			map.put("ID", 0);
		}
		VelocityContext context = new VelocityContext();
		String SUP_NAME = ((String)map.get("SUP_NAME")).replace("_"," ");
		map.put("SUP_NAME", SUP_NAME);//厂商名
		map.put("COMPANY",Security.getUser().getOrg().getPlatform());//收款户名
		map.put("MANAGER_ID",Security.getUser().getOrg().getPlatformId());//收款户名ID
		Map<String,Object> map_tem = Dao.selectOne("fi.fund.getBinkInfo", map);
		map.put("FA_BINK",map_tem.get("FA_BINK"));//默认融资租赁公司开户行
		map.put("FA_ACCOUNT",map_tem.get("FA_ACCOUNT"));//默认融资租赁公司银行账号
		Map<String,Object> map_tem2 = Dao.selectOne("fi.fund.get_Bank_jxs",map);
		if(map_tem2!=null){
			map.put("OPEN_BANK_NUMBER",map_tem2.get("OPEN_BANK_NUMBER"));
			map.put("OPEN_BANK",map_tem2.get("OPEN_BANK"));
		}
		context.put("map", map);
		//跳转到保证金页面
		return new ReplyHtml(VM.html(path + "supplierCreditManagerNewbzj.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "授信管理", "经销商授信管理", "列表" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply showClientInfo() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "showClientInfoJXS.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	//@aPermission(name={"授信管理","经销商授信管理","承租人信息"})
	@aDev(code = "170053", email = "1226781445@qq.com", name = "肖云飞")
	//查询同一厂商相关的承租人信息
	public Reply searchClientInfo() {
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(creditService.searchClient(param));
	}
	@aAuth(type = aAuthType.LOGIN)
	//@aPermission(name={"授信管理","经销商授信管理","新增保证金"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//新增保证金
	public Reply insertBZJ_Info() {
		Map<String,Object> map_tem = this._getParametersIO(null, null, null);
		if("".equals( map_tem.get("T_SUP_ID"))){
			map_tem.put("T_SUP_ID", Dao.selectInt("SupplierCredit.searchHeadId", null));
			Dao.insert("SupplierCredit.insertHeadRecord", map_tem);
		}
		System.out.println(map_tem+"-----map-----");
		map_tem.put("ID", Dao.selectInt("SupplierCredit.searchDetailId", null));
		map_tem.put("TYPE", 3);
		map_tem.put("STATUS","2");
		if(!map_tem.containsKey("FILE_PATH")){
			map_tem.put("FILE_PATH", null);
			map_tem.put("_FILE_NAME", null);
		}
		//插入新增保证金
		int flag = creditService.insertBZJ_Info(map_tem);
		//更新t_sys_company_bzj_head表 保证金总计、保证金余额、使用率
		Dao.update("SupplierCredit.update_t_sup_bzj_Head", map_tem);
		//获得新的保证金总计、保证金余额、使用率 
		Map<String,Object> map2 = Dao.selectOne("SupplierCredit.searchMoneyInfo", map_tem);
		return new ReplyAjax(true, map2, "添加成功");
	}
	@aAuth(type = aAuthType.LOGIN)
	//@aPermission(name={"授信管理","经销商授信管理","退还保证金"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//退还保证金
	public Reply refund_BZJ_Info() {
		Map<String,Object> map = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(map.get("param"));
		System.out.println(jsonArray+"----xx-----map---------xx---------");
		System.out.println((Map<String,Object>)(jsonArray.get(0))+"xxxxxxxxx");
		Map<String,Object> map_tem = (Map<String,Object>)(jsonArray.get(0));
		map_tem.put("ID", Dao.selectInt("SupplierCredit.searchDetailId", null));
		map_tem.put("TYPE", 1);
		map_tem.put("STATUS",'0');
		//插入退还保证金记录 
		int flag = creditService.insertBZJ_Info(map_tem);
		
		//发起流程
		Map JBPMMAP=new HashMap();
		JBPMMAP.put("ID", map_tem.get("ID").toString());
		List<String> list = JBPM.getDeploymentListByModelName("经销商保证金退还审批","", Security.getUser().getOrg().getPlatformId());
		System.out.println(list.get(0)+"----list-----"+JBPMMAP.get("ID"));
		String jbpm_id =JBPM.startProcessInstanceById(list.get(0), "", "", "", JBPMMAP).getId();
		System.out.println(jbpm_id+"----jbpm_id-----");
		String nextCode = new TaskService().getAssignee(jbpm_id);
		return new ReplyAjax(true, nextCode, "下一步操作人为: ");
	}
	@aAuth(type = aAuthType.LOGIN)
	//@aPermission(name={"授信管理","经销商授信管理","保证金转来款"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//保证金转来款
	public Reply convert_BZJ_Info() {
		Map<String,Object> map = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(map.get("param"));
		Map<String,Object> map_tem = (Map<String,Object>)(jsonArray.get(0));
		map_tem.put("ID", Dao.selectInt("SupplierCredit.searchDetailId", null));
		map_tem.put("TYPE", 2);
		map_tem.put("STATUS", '2');
		//插入转来款保证金记录 
		int flag = creditService.insertBZJ_Info(map_tem);
		//插入一条新的数据进入fi_fund资金表
		map_tem.put("FUND_ID", Dao.selectOne("fi.fund.getFundId"));
		map_tem.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
		map_tem.put("FUND_DOCKET", "经销商保证金转来款");
		map_tem.put("FUND_NOTDECO_STATE", "4");
		map_tem.put("FUND_STATUS", "0");
		map_tem.put("FUND_ISCONGEAL", "0");
		map_tem.put("FUND_RED_STATUS", "0");
		map_tem.put("FUND_PRANT_ID", "0");
		map_tem.put("FUND_IMPORT_PERSON", Security.getUser().getName());
		map_tem.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
		map_tem.put("FUND_PIDENTIFY_PERSON", Security.getUser().getName());
		map_tem.put("FUND_PIDENTIFY_PERSON_ID", Security.getUser().getId());
		map_tem.put("FUND_ACCEPT_DATE", map_tem.get("COMEMONEY_TIME"));
		map_tem.put("FUND_RECEIVE_MONEY", map_tem.get("COMEMONEY"));
		map_tem.put("FUND_CLIENT_NAME", map_tem.get("CLIENT_NAME"));
		map_tem.put("FUND_CLIENT_ID", map_tem.get("CLIENT_ID"));
		map_tem.put("FUND_ACCEPT_CODE", map_tem.get("ACCEPTMONEY_ZH"));
		map_tem.put("FUND_ACCEPT_NAME", map_tem.get("ACCEPTMONEY_NAME"));
		map_tem.put("FUND_COMENAME", map_tem.get("COMEMONEY_NAME"));
		map_tem.put("FUND_COMECODE", map_tem.get("COMEMONEY_ZH"));
		map_tem.put("FUND_SY_MONEY", map_tem.get("COMEMONEY"));
		Dao.insert("fi.fund.add", map_tem);
		//更新t_sys_company_bzj_head表 保证金总计、保证金余额、使用率
		Dao.update("SupplierCredit.update_t_sup_Head_Refund", map_tem);
		//获得新的保证金总计、保证金余额、使用率
		Map<String,Object> map2 = Dao.selectOne("SupplierCredit.searchMoneyInfo", map_tem);
		if(flag == 1){
			return new ReplyAjax(true, map2, "添加成功");
		}else{
			return new ReplyAjax(false, "添加失败!!");
		}
	}
	@aAuth(type = aAuthType.LOGIN)
	//@aPermission(name={"授信管理","经销商授信管理","保证金列表"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//查询保证金列表
	public Reply selectDetailInfo() {
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(creditService.selectDetailInfo(map));
	}
	@aAuth(type = aAuthType.LOGIN)
	//@aPermission(name={"授信管理","经销商授信管理","保证金明细"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//跳转经销商保证金明细页面
	public Reply showDetail() {
		Map<String,Object> map = _getParameters();
		map.put("sel", 2);
		VelocityContext context = new VelocityContext();
		context.put("map", map);
		return new ReplyHtml(VM.html(path+"showDetail.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	//@aPermission(name={"授信管理","经销商授信管理","保证金明细"})
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	//跳转经销商保证金明细页面
	public Reply getShowDetailData() {
		Map<String,Object> map = _getParameters();
		Map<String,Object> map_tem = creditService.selectDetailData(map);
		return new ReplyJson(JSONArray.fromObject(map_tem));
	}
}

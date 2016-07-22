package com.pqsoft.insureSettlement.action;

/**
 *  保险理赔 款项确认
 *  @author hanxl
 *  涉及表 FI_INSUREPAID_FEE_UPLOAD
 *  涉及表 FI_DEPOSIT_POOL
 */
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.insureSettlement.service.InsureSettlementConfirmService;
import com.pqsoft.insureSettlement.service.InsureSettlementUtilService;
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
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class InsureSettlementConfirmAction extends Action {

	private String basePath = "insureSettlement/";
	private InsureSettlementConfirmService service = new InsureSettlementConfirmService();
	private InsureSettlementUtilService utilService = new InsureSettlementUtilService();

	@Override
//	@aPermission(name = { "保险管理", "保险理赔", "款项确认主页面" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		context.put("SUPPLIERS", utilService.getSuppliers());
//		return new ReplyHtml(VM.html(basePath + "insureSettlementConfirm.vm", context));
		return null;
	}
	
//	/**
//	 * 用于分页查询
//	 */
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply pageData(){
//		Map<String,Object> param = _getParameters();
//		Page pagedata = service.getPageData(param);
//		return new ReplyAjaxPage(pagedata);
//	}
//	
//	/**
//	 * 用于查询一个供应商下的所有客户
//	 */
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply getClients() {
//		Map<String, Object> param = _getParameters();
//		return new ReplyJson(utilService.getClients(param));
//	}
//	
//	/**
//	 *  用于查询一个客户下的所有项目
//	 */
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply getProjects() {
//		Map<String, Object> param = _getParameters();
//		return new ReplyJson(utilService.getProjects(param));
//	}
//	
//	/**
//	 *  保存认款信息
//	 */
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply saveConfirmMes() {
//		Map<String, Object> param = _getParameters();
//		//1、数据进入资金池
//		String pool_id = Dao.getSequence("SEQ_FI_DEPOSIT_POOL");//得到POOL_ID
//		param.put("POOL_ID", pool_id);//POOL_ID
//		param.put("STATUS", "1");//默认已解冻
//		param.put("TYPE", "6");//保险理赔金池  6
//		param.put("SOURCE_ID", param.get("ID").toString());//SOURCE_ID 为来款明细表 FI_INSUREPAID_FEE_UPLOAD 的ID 
//		param.put("PAYER", Security.getUser().getName());//认款人
//		param.put("REMARK", "保险理赔款");//备注
//		
//		service.insertPoolInsureFee(param);
//		
//		String id = param.get("ID").toString();//FI_INSUREPAID_FEE_UPLOAD 的ID 
//		//2、更新来款明细表状态
//		param.clear();//先清空map 保证数据准确
//		param.put("ID", id);//ID
//		param.put("IDENTIFY_PERSON", Security.getUser().getName());//认款人
//		param.put("ALREADY_IDENTIFY", "1");//认款状态
//		param.put("IF_IGNORE", "0");//认款状态
//		param.put("POOL_ID", pool_id);//FI_DEPOSIT_POOL的POOL_ID
//		
//		service.updateInsurePaidFeeStatus(param);
//		
//		return  new ReplyAjax(true,"");
//	}
//	
//	/**
//	 *  撤销认款操作
//	 */
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply cancelConfirm() {
//		Map<String, Object> param = _getParameters();
//		//1、撤销来款明细表 FI_INSUREPAID_FEE_UPLOAD 状态
//		service.cancelInsurePaidFeeStatus(param);
//		
//		//2、删除资金池记录
//		param.put("SOURCE_ID", param.get("ID").toString());
//		service.deletePoolInsureFee(param);
//		
//		return  new ReplyAjax(true,"");
//	}
//	
//	@aPermission(name = { "保险管理", "保险理赔", "款项确认通知页面" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply showInsureFeeNotice() {
//		return new ReplyHtml(VM.html(basePath + "insureSettlementNoticeShow.vm", null));
//	}
//	
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply pageNoticeData() {
//		Map<String, Object> param = _getParameters();
//		Page pagedata = service.queryAllNotice(param);
//		return new ReplyAjaxPage(pagedata);
//	}
	///////////////////////////////////////////////////////////////
	/**
	 *  首页通知页面查询
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply exeNotice() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("SUPPLIERS", utilService.getSuppliers());
		context.put("CREATE_TIME", param.get("CREATE_TIME"));
		return new ReplyHtml(VM.html(basePath + "insureSettlementNoticeConfirm.vm", context));
	}
	
	/**
	 * 用于分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageDataNotice(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	/**
	 *  忽略操作
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply ignoreIt() {
		Map<String, Object> param = _getParameters();
		param.put("IDENTIFY_PERSON", Security.getUser().getName());
		param.put("ALREADY_IDENTIFY", "0");//是否认款  0 未认款
		param.put("IF_IGNORE", "1");//是否忽略 1 忽略
		service.ignoreInsurePaidFeeStatusChange(param);
		return  new ReplyAjax(true,"");
	}
	
	/**
	 *  取消忽略操作
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply notIgnoreIt() {
		Map<String, Object> param = _getParameters();
		param.put("IDENTIFY_PERSON", Security.getUser().getName());
		param.put("ALREADY_IDENTIFY", "0");//是否认款  0 未认款
		param.put("IF_IGNORE", "0");//是否忽略 0 不忽略
		service.ignoreInsurePaidFeeStatusChange(param);
		return  new ReplyAjax(true,"");
	}
	
	/**
	 *  忽略其他未认款款项
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply ignoreElse() {
		Map<String, Object> param = _getParameters();
		System.out.println("=============param==============" + param.toString());
		param.put("IDENTIFY_PERSON", Security.getUser().getName());
		param.put("ALREADY_IDENTIFY", "0");//是否认款  0 未认款
		param.put("IF_IGNORE", "1");//是否忽略 1 忽略
		service.ignoreElseInsurePaidFee(param);
		return  new ReplyAjax(true,"");
	}
	///////////////////////////////////////////////////////////////
	
}

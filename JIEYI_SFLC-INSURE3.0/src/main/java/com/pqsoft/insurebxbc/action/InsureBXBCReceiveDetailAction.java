package com.pqsoft.insurebxbc.action;
/**
 *   保险补差收取明细查询
 */
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.insure.service.InsureService;
import com.pqsoft.insurebxbc.service.InsureBXBCManagementService;
import com.pqsoft.insurebxbc.service.InsureBXBCReceiveService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class InsureBXBCReceiveDetailAction extends Action{
	
	private String basePath = "insure_bxbc/"; 
	
	private InsureService insureService = new InsureService();
	private InsureBXBCReceiveService insureBXBCReceive = new InsureBXBCReceiveService();
	private ManageService mgService = new ManageService();//权限控制
	private InsureBXBCManagementService bxbcService = new InsureBXBCManagementService();
	
	/**
	 *  显示主页面
	 */
	@Override
//	@aPermission(name = { "保险管理", "保险补差管理", "补差收取明细查询"})
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		//利用韩建的方法取得所有厂商 和 租赁物类型
//		context.put("ProductAndCompany", insureService.queryProductAndCompany());
//		return new ReplyHtml(VM.html(basePath + "insureBXBCReceiveDetail.vm", context));
		return null;
	}
	
	/**
	 * 用于分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		//数据权限,根据对应的厂商显示信息
		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
		if(supMap != null && supMap.get("SUP_ID") != null){
			param.put("SUP_ID", supMap.get("SUP_ID"));
		}
		Page pagedata = insureBXBCReceive.getAddPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 * 用于明细分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageDetailData(){
		Map<String,Object> param = _getParameters();
		//数据权限,根据对应的厂商显示信息
		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
		if(supMap != null && supMap.get("SUP_ID") != null){
			param.put("SUP_ID", supMap.get("SUP_ID"));
		}
		Page pagedata = insureBXBCReceive.getDetailPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 *  添加保存记录
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doUpdateInsurChargeExhib() {
		Map<String,Object> param = _getParameters();
		String ids = getNotNullValue(param, "ID_DATA");//INSUR_CHARGE_EXHIB 的 id
		String fk_amt = getNotNullValue(param, "FK_AMT");//实收金额
		String proj_amt = getNotNullValue(param, "PROJ_AMT");//项目数量
		String fact_date = getNotNullValue(param, "FACT_DATE");//实付日期
		String apply_id = Dao.getSequence("SEQ_FIL_APPLY_INFO");//得到单号
		param.put("ID", apply_id);//ID
		param.put("PAY_AMT", fk_amt);
		param.put("AMOUNT", proj_amt);
		param.put("PAY_DATE", fact_date);
		param.put("FUND_NAME", "保险补差收款");
		param.put("STATUS", "0");
		param.put("IS_SUB", "未提交");
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		bxbcService.addApplyInfo(param);//生成付款单
		param.clear();//为保万无一失清空map
		//更新INSUR_CHARGE_EXHIB
		param.put("ID_DATA", ids);//ID序列
		param.put("STATUS", "0");//STATUS
		param.put("APPLY_ID", apply_id);//APPLY_ID
		bxbcService.upChargeExhibAdd(param);
		return  new ReplyAjax(true,"");
	}
	
	/**
	 *  解决无值 返回 空字符串的问题
	 */
	private String getNotNullValue(Map map,String key){
		 return map.containsKey(key) ? map.get(key).toString() : "";
	}
	
}

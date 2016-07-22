package com.pqsoft.refundFirst.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.fundEbank.service.FundEbankService;
import com.pqsoft.refundFirst.service.RefundFirstService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.SecuUtil;

public class RefundFirstAction extends Action {

	private final String path = "refundFirst/";

	private RefundFirstService service = new RefundFirstService();
	
	@Override
	public Reply execute() {
		return null;
	}

	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-申请","列表"})
	public Reply toMgRefundFirstApply(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		SecuUtil.putUserInfo(map);
		context.put("param", map);		
		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));//获取付款方式		
		context.put("toGetCompany", service.toGetCompany(map));//获取厂商		
		context.put("toGetProduct", service.toGetProduct(map));//获取租赁物类型
		return new ReplyHtml(VM.html(path+"refundFirstApp.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-申请","数据"})
	public Reply toMgRefundFirstApplyData(){
		Map<String, Object> param = _getParameters();

		return new ReplyAjaxPage(service.toGetAppData(param));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-申请","提交"})
	public Reply doSubmitRental(){
		
		Map<String,Object> map = _getParameters();//获取页面参数
		SecuUtil.putUserInfo(map);
		
		String SELECT_PRO_IDS = map.get("SELECT_PRO_IDS").toString();
		String[] PRO_IDS = SELECT_PRO_IDS.split("-");
		String PRO_INFO ="";
		String PRO_ID ="";
		String REALITY_MONEY ="0";
		boolean flag = false;
		
		FundEbankService ebankService = new FundEbankService();
		Map<String,Object> pro_map = new HashMap<String, Object>();
		pro_map.put("FIRST_APP_STATE", "4");
		//首期款退款单信息
		Map<String,Object> refundDan = new HashMap<String, Object>();
		String refund_id = "";
		// 公用字段
		refundDan.put("RE_DATE", map.get("RE_DATE"));//预计退款时间
		refundDan.put("RE_PROJECT_COUNT", "1");//项目数量 默认1 一个项目一个退款单
		refundDan.put("RE_TYPE", "0");//退款类型   0：首付款退款
		refundDan.put("RE_APPLY_NAME", map.get("USERNAME"));//退款申请人姓名
		refundDan.put("RE_APPLY_CODE", map.get("USERCODE"));//退款申请人编号
		refundDan.put("RE_STATUS", "0");//退款单状态  0:未退款
		for (int i = 0; i < PRO_IDS.length; i++) {
			PRO_INFO = PRO_IDS[i];
			if(PRO_INFO.split(":").length < 2){
				continue;
			}
			PRO_ID = PRO_INFO.split(":")[0];
			REALITY_MONEY = PRO_INFO.split(":")[1];
			if(!"".equals(PRO_ID) && PRO_ID != ""){
				
				//生成首期款退款单 
				String re_code = PRO_ID+"-首付款退款";
				refundDan.put("RE_CODE", re_code);//退款单号
				refundDan.put("PRO_ID",PRO_ID);
				refundDan.put("RE_MONEY",REALITY_MONEY);
				
				//1.保存首付款退款单明细
				refund_id = service.insertRefundHeadForFrist(refundDan);
				//2.保存首付款退款单对应关系
				refundDan.put("RE_ID",refund_id);
				service.insertRefundAccountForFrist(refundDan);
				pro_map.put("deducted_id", PRO_ID);
				pro_map.put("FIRST_APP_STATE", "4");
				
				//3.更新项目主表首付款状态为4退款申请
				ebankService.updateStatusByProId(pro_map);  
				flag = true;
			}
		}
			
		return new ReplyAjax(flag, null).addOp(new OpLog("首付款退款","申请-提交", map.get("USERCODE").toString()));
		
	}
	
	/********************************************首付款退款-审核**********Auth=吴剑东***********************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-核销","列表"})
	public Reply toMgRefundFirstConfirm() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(path+"refundFirstConfirm.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-核销","数据"})
	public Reply toMgRefundFirstConfirmData(){
		Map<String, Object> param = _getParameters();
		if(param.get("REFUND_TYPE") == null || "".equals(param.get("REFUND_TYPE")+"")){
			param.put("REFUND_TYPE", "0");
		}
		return new ReplyAjaxPage(service.getRefundFirstConfirmData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-核销","驳回"})
	public Reply doReturnRefundApp(){
		
		Map<String,Object> map = _getParameters();//获取页面参数
		SecuUtil.putUserInfo(map);
		//获取需要驳回的退款单ID

		String SELECT_PRO_IDS = map.get("SELECT_PRO_IDS").toString();
		String[] PRO_IDS = SELECT_PRO_IDS.split("-");
		String PRO_INFO ="";
		String PRO_ID ="";
		String RE_ID ="";
		boolean flag = false;
		
		FundEbankService ebankService = new FundEbankService();
		Map<String,Object> pro_map = new HashMap<String, Object>();
		pro_map.put("FIRST_APP_STATE", "2");
		
		for (int i = 0; i < PRO_IDS.length; i++) {
			PRO_INFO = PRO_IDS[i];
			if(PRO_INFO.split(":").length < 2){
				continue;
			}
			PRO_ID = PRO_INFO.split(":")[0];
			RE_ID = PRO_INFO.split(":")[1];
			
			if(!"".equals(PRO_ID) && PRO_ID != ""){
				map.put("RE_ID",RE_ID);
				//1.删除首付款退款单明细
				service.delFristRefundHeadByReId(map);
				//2.删除首付款退款单对应关系
				service.delFristRefundAccountByReId(map);
				pro_map.put("deducted_id", PRO_ID);
				//3.更新项目主表首付款状态为4退款申请
				ebankService.updateStatusByProId(pro_map);  
				flag = true;
			}
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("资金管理", "首付款退款-驳回", map.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-核销","核销"})
	public Reply doConfirmRefundApp(){
		
		Map<String,Object> map = _getParameters();//获取页面参数
		SecuUtil.putUserInfo(map);
		//获取需要驳回的退款单ID

		String SELECT_PRO_IDS = map.get("SELECT_PRO_IDS").toString();
		String[] PRO_IDS = SELECT_PRO_IDS.split("-");
		String PRO_INFO ="";
		String PRO_ID ="";
		String RE_ID ="";
		System.out.println("wjd-----------"+map);
		boolean flag = false;
		//循环调用首付款退款核销存储过程
		for (int i = 0; i < PRO_IDS.length; i++) {
			PRO_INFO = PRO_IDS[i];
			if(PRO_INFO.split(":").length < 2){
				continue;
			}
			PRO_ID = PRO_INFO.split(":")[0];
			RE_ID = PRO_INFO.split(":")[1];
			
			if(!"".equals(PRO_ID) && PRO_ID != ""){
				map.put("RE_ID",RE_ID);
				map.put("PRO_ID", PRO_ID); 
				
				//调用存过核销执行操作 1.退款单信息维护 2.退款单明细关系 3.项目主表项目首付款状态改为“已退款”
				//4.应收期初表项目首付款状态由已核销改为已退款 5.首期款付款单信息6.首期款付款单明细信息
				//7.资金明细关系表信息 8.客户保证金池数据状态9.是否退款到供应商垫汇池
				service.doConfirmRefundAppByProId(map);
				
				flag = true;
			}
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("资金管理", "首付款退款-核销", map.get("USERCODE").toString()));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-核销","核销"})
	public Reply doConfirmRefundOver(){
		
		Map<String,Object> map = _getParameters();//获取页面参数
		SecuUtil.putUserInfo(map);
		//获取需要驳回的退款单ID

		boolean flag = false;
		
		String PRO_ID =map.get("PRO_ID").toString();
		String REALITY_MONEY =map.get("REALITY_MONEY").toString();
		
		FundEbankService ebankService = new FundEbankService();
		Map<String,Object> pro_map = new HashMap<String, Object>();
		pro_map.put("FIRST_APP_STATE", "4");
		//首期款退款单信息
		Map<String,Object> refundDan = new HashMap<String, Object>();
		String RE_ID = "";
		// 公用字段
		refundDan.put("RE_DATE", map.get("PLAN_DATE"));//预计退款时间
		refundDan.put("RE_PROJECT_COUNT", "1");//项目数量 默认1 一个项目一个退款单
		refundDan.put("RE_TYPE", "0");//退款类型   0：首付款退款
		refundDan.put("RE_APPLY_NAME", map.get("USERNAME"));//退款申请人姓名
		refundDan.put("RE_APPLY_CODE", map.get("USERCODE"));//退款申请人编号
		refundDan.put("RE_STATUS", "0");//退款单状态  0:未退款


		//生成首期款退款单 
		String re_code = PRO_ID+"-首付款退款";
		refundDan.put("RE_CODE", re_code);//退款单号
		refundDan.put("PRO_ID",PRO_ID);
		refundDan.put("RE_MONEY",REALITY_MONEY);
		
		//1.保存首付款退款单明细
		RE_ID = service.insertRefundHeadForFrist(refundDan);
		//2.保存首付款退款单对应关系
		refundDan.put("RE_ID",RE_ID);
		service.insertRefundAccountForFrist(refundDan);
			
		map.put("PRO_ID", PRO_ID); 
		map.put("RE_ID",RE_ID); 
		//存过核销
		service.doConfirmRefundAppByProId(map);
				
		flag = true;
		return new ReplyAjax(flag, null).addOp(new OpLog("资金管理", "首付款退款-核销", map.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-查询","列表"})
	public Reply toMgRefundFirstSearch() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(path+"refundFirstSearch.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-查询","数据"})
	public Reply toMgRefundFirstSearchData(){
		Map<String, Object> param = _getParameters();

		return new ReplyAjaxPage(service.getRefundFirstConfirmData(param));
	}
	
}

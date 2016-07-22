package com.pqsoft.rePayment.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.rePayment.service.RePaymentService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class RePaymentAction extends Action{

	private final RePaymentService service = new RePaymentService();
	private final String pagePath = "rePayment/";
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","列表显示"})
	public Reply execute() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath+"toMgRePayment.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","列表显示"})
	public Reply toMgPayment(){
		Map<String,Object> map = _getParameters();
		JSONObject obj = JSONObject.fromObject(map.get("param"));	
		map.remove("param");
		map.putAll(obj);
		return new ReplyAjaxPage(service.toMgRePayment(map));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","方案录入"})
	public Reply toPayPlan(){
		Map<String,Object> map=_getParameters();
		VelocityContext context = new VelocityContext();
		Map<String,Object> paymap = (Map<String,Object>)service.queryT_REFUND_PAYMENTPLAN(map);
		if(paymap==null){
			Map<String,Object> addMap = (Map<String,Object>)service.queryT_REFUND_BAILOUTWAY(map);
			context.put("payParameters", service.getSchemeByProportion(addMap, map.get("LOAN_AMUNT").toString()));
		}else{
			context.put("ctype", "1");
			context.put("payParameters", paymap);
		}
		context.put("data", map);
		context.put("project_id", map.get("PROJECT_ID"));
		return new ReplyHtml(VM.html(pagePath+"updatePaymentPlan.vm", context));
	}
	
	/**
	 * 保存方案信息
	 * doPayPlan
	 * @date 2013-10-23 下午09:12:05
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","方案录入"})
	public Reply doPayPlan(){
		Map<String,Object> map=_getParameters();
		String lastInterest = "";
		double INTEREST = 0;
		if(map.get("ctype")==null||map.get("ctype").toString().equals("")){//添加
			Map<String,Object> interestMap = service.getInterest();//获取基准利率
			lastInterest = service.getInterestByPeriod(map, interestMap);
			INTEREST = Double.parseDouble(lastInterest);
		}else{//修改    利率
			map.put("INTEREST1","2");
			lastInterest = map.get("INTEREST1").toString();
			//利率下浮
			if(map.get("RATE_TYPE").equals("3")){
				//根据下浮比例计算利率
				INTEREST = (Double.parseDouble(lastInterest))*
				(1-Double.parseDouble(map.get("RATE_SCALE").toString())/100);
			}
			//利率上浮
			else if(map.get("RATE_TYPE").equals("2")){
				//根据上浮比例计算利率
				INTEREST = (Double.parseDouble(lastInterest))*
				( 1+ Double.parseDouble(map.get("RATE_SCALE").toString())/100);
			}
			//利率固定
			else{
				INTEREST = Double.parseDouble(lastInterest);
			}
		}
		map.put("INTEREST", INTEREST);
		int i = 0;
		if(map.get("ctype")==null||map.get("ctype").toString().equals("")){//添加
			i = service.savePaymentPlan(map);
		}else{//修改
			i = service.updatePaymentPlan(map);
			map.put("PAY_ID",map.get("ID"));
		}
		boolean flag = false;
		if(i>0) {
//			if("2".equals(map.get("STATUS"))){
				map.put("STATUS", "3");
//			}else{
//				map.put("STATUS", "2");
//			}
			service.updatePaymentPlanStatus(map);
			flag = true;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("融资管理","融资回款管理","方案确定"));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","支付表生成"})
	public Reply toaddPayPlan(){
		Map<String,Object> map=_getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println(map);
		Map<String,Object> paymap = (Map<String,Object>)service.queryT_REFUND_PAYMENTPLAN(map);
		context.put("loan", service.queryT_REFUND_LOAN(map));
		context.put("payParameters", paymap);
		context.put("data", map);
		return new ReplyHtml(VM.html(pagePath+"toCreatePayment.vm", context));
	}
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","支付表生成"})
	public Reply systemCreatePay(){
		Map<String,Object> map=_getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println(map);
		Map<String,Object> paymap = (Map<String,Object>)service.queryT_REFUND_PAYMENTPLAN(map);
		context.put("loan", service.queryT_REFUND_LOAN(map));
		context.put("payParameters", paymap);
		context.put("data", map);
		return new ReplyHtml(VM.html(pagePath+"systemCreatePay.vm", context));
	}
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","保存支付表明细"})
	public Reply systemCreatePaySave(){
		Map<String,Object> m=_getParameters();
		List<Map<String,String >> list = JSONArray.fromObject(m.get("row"));
		Dao.delete("paymentDetail.deletePayDetail",m.get("PAY_ID")+"");//此处id为支付表id
		for(Map<String,String > map : list){
			map.put("PAY_ID", m.get("PAY_ID")+"");
			map.put("PERIOD_NUM",map.get("qc")+"");
			map.put("MONTH_PRICE",map.get("zj")+"");
			map.put("OWN_PRICE",map.get("bj")+"");
			map.put("REN_PRICE",map.get("lx")+"");
			map.put("LAST_PRICE",map.get("sybj")+"");
			map.put("ISHOLIDAY",1+"");
			map.put("PROJECT_ID",m.get("PROJECT_ID")+"");
			Dao.insert("paymentDetail.addPayMentDetail",map);
		}
		m.put("STATUS", "4");
		service.updatePaymentPlanStatus(m);
		return new ReplyAjax("保存成功");
	}
	
	/**
	 * 修改还款计划书
	 * toUpdatePayPlan
	 * @date 2013-10-25 上午11:46:44
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","修改还款计划书"})
	public Reply toUpdatePayPlan(){
		Map<String,Object> map=_getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println(map);
		Map<String,Object> paymap = (Map<String,Object>)service.queryT_REFUND_PAYMENTPLAN(map);
		
		context.put("loan", service.queryT_REFUND_LOAN(map));
		context.put("payParameters", paymap);
		context.put("detailList", (List<Map<String,Object>>)service.getPaymentDetailByPid(paymap));
		context.put("data", map);
		return new ReplyHtml(VM.html(pagePath+"UpdatejustSetRepayParam.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","查看还款计划书"})
	public Reply toqueryPayDetail(){
		Map<String,Object> map=_getParameters();
		VelocityContext context = new VelocityContext();
		Map<String,Object> paymap = (Map<String,Object>)service.queryT_REFUND_PAYMENTPLAN(map);
		context.put("loan", service.queryT_REFUND_LOAN(map));
		context.put("payParameters", paymap);
		context.put("detailList", service.getPaymentDetailByPid(paymap));
		context.put("data", map);
		if(map.get("jump_type").equals("1")){
			return new ReplyHtml(VM.html(pagePath+"queryPaymentDetail.vm", context));
		}
		else{
			return new ReplyHtml(VM.html(pagePath+"confrimPaymentDetail.vm", context));
		}
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","确认还款计划信息"})
	public Reply updatePaymentPlanStatus(){
		Map<String,Object> map=_getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println(map);
		map.put("STATUS", "5");
		int i = service.updatePaymentPlanStatus(map);
		context.put("data", map);
		context.put("succ", "1");
		context.put("project_id", map.get("PROJECT_ID"));
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("融资管理","融资回款管理","确认还款计划"));
	}
	
}

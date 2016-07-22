/**
 *
*/
package com.pqsoft.rePayment.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.pqsoft.rePayment.service.RePaymentDetailService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class RePaymentDetailAction extends Action {
	RePaymentDetailService service = new RePaymentDetailService();
	protected static final Logger logger = Logger.getLogger(RePaymentDetailAction.class);

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","方案录入"})
	public Reply addPayMentDetail(){
		JSONObject jsonMainPayment = null;
		JSONArray arrayMainPayment = JSONArray.fromObject(SkyEye.getRequest().getParameter("json2")); // 还款计划书
		JSONArray arrayPayment = JSONArray.fromObject(SkyEye.getRequest().getParameter("json1")); // 还款计划书
		if (arrayMainPayment.size() > 0) {
			jsonMainPayment = arrayMainPayment.getJSONObject(0);
		}
		int i =service.addPayMentDetailService(arrayPayment,jsonMainPayment);
		
		boolean flag = false;
		if(i>0){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("STATUS", "4");
			map.put("PAY_ID", jsonMainPayment.get("PAY_ID"));
			service.updatePaymentPlanStatus(map);
			flag = true; 
		}
		logger.info(flag);
		
		return new ReplyAjax(flag,null).addOp(new OpLog("融资管理","融资回款管理","手动录入支付表"));		
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资回款管理","修改还款计划书"})
	public Reply updatePayMentDetail(){
		JSONObject jsonMainPayment = null;
		JSONArray arrayMainPayment = JSONArray.fromObject(SkyEye.getRequest().getParameter("json2")); // 还款计划书
		JSONArray arrayPayment = JSONArray.fromObject(SkyEye.getRequest().getParameter("json1")); // 还款计划书
		if (arrayMainPayment.size() > 0) {
			jsonMainPayment = arrayMainPayment.getJSONObject(0);
		}
		int i = service.updatePayMentDetailService(arrayPayment,jsonMainPayment);
		boolean flag = false;
		if(i>0){
			flag = true;
		} else {
			flag = false;
		}
		logger.info(flag+"===============================");
		return new ReplyAjax(flag,null).addOp(new OpLog("融资管理","融资回款管理","修改还款计划"));
	}
}

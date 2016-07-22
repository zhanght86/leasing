package com.pqsoft.analysisBySynthesis.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.analysisBySynthesis.service.RepaymentSourceService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class RepaymentSourceAction extends Action {

	private final String pagePath = "analysisBySynthesis/";
	private RepaymentSourceService  service = new RepaymentSourceService();
	
	/***************************************************************************************************
	 * *                               还款来源分析-盈利目的                                                                                                                 * *
	 ***************************************************************************************************/
	
	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = {"客户管理", "综合分析标签页", "还款来源分析[盈利目的]", "还款来源分析[盈利目的]" })
	public Reply execute() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		Map<String,Object> map =  service.queryForProfit(param);
		if(null !=map){
			Set<String> dataSet = map.keySet();
			Map<String,Object> childMap;
			JSONObject  jsonObject;
	 		for(String key:dataSet){
	 			
	 			if(key==null || key.equals("ID") ||key.equals("CREATE_ID") ||key.equals("CREATE_TIME") 
	 					||key.equals("CLIENT_ID") ||key.equals("CREDIT_ID")){
	 				continue;
	 			}
	 			childMap = new HashMap<String,Object>();
	 			jsonObject  = new JSONObject();
	 			
	 			Map m1 = jsonObject.fromObject(map.get(key));
	 			System.out.println(key+"==========="+m1.toString());
	 			context.put(key, m1);
			}
		}
		return new ReplyHtml(VM.html(pagePath+"forProfit/toAddForProfit.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = {"客户管理", "综合分析标签页", "还款来源分析[盈利目的]", "还款来源分析[盈利目的]-查看" })
	public Reply queryForProfit() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		Map<String,Object> map =  service.queryForProfit(param);
		if(null !=map){
			Set<String> dataSet = map.keySet();
			Map<String,Object> childMap;
			JSONObject  jsonObject;
	 		for(String key:dataSet){
	 			
	 			if(key==null || key.equals("ID") ||key.equals("CREATE_ID") ||key.equals("CREATE_TIME") 
	 					||key.equals("CLIENT_ID") ||key.equals("CREDIT_ID")){
	 				continue;
	 			}
	 			childMap = new HashMap<String,Object>();
	 			jsonObject  = new JSONObject();
	 			
	 			Map m1 = jsonObject.fromObject(map.get(key));
	 			System.out.println(key+"==========="+m1.toString());
	 			context.put(key, m1);
			}
		}
		return new ReplyHtml(VM.html(pagePath+"forProfit/queryForProfit.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = {"客户管理", "综合分析标签页", "还款来源分析[盈利目的]","还款来源分析[盈利目的][添加]" })
	public Reply doAddForProfit(){
		Map<String,Object> param = _getParameters();
		param.put("USERID", Security.getUser().getId());
		int i = service.doAddForProfit(param);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		return new ReplyAjax(flag).addOp(new OpLog("综合分析标签页","还款来源分析","添加盈利目的")); 
	}
	
	
	
	
	/***************************************************************************************************
	 * *                               还款来源分析-消费目的                                                                                                                 * *
	 ***************************************************************************************************/
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = {"客户管理", "综合分析标签页", "还款来源分析[消费目的]", "还款来源分析[消费目的][添加]" })
	public Reply toAddConsumptIonaim(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		System.out.println("param----"+param);
		Map map=service.QueryForExpend(param);
		if(map==null){
			System.out.println("还款来源，消费目的为空~！");
			return  new ReplyHtml(VM.html(pagePath+"consumptIonaim/toAddConsumptIonaim.vm", context));
		}
		Set<String> dataSet = map.keySet();
//		Map<String,Object> childMap;
		JSONObject  jsonObject = new JSONObject();;
 		for(String key:dataSet){
 			
 			if(key==null || key.equals("ID") ||key.equals("CREATE_ID") ||key.equals("CREATE_TIME") 
 					||key.equals("CLIENT_ID") ||key.equals("CREDIT_ID")){
 				continue;
 			}
// 			childMap = new HashMap<String,Object>();
// 			System.out.println("===="+key);
// 			System.out.println("========"+jsonObject.fromObject(map.get(key)));
 			Map m1 = jsonObject.fromObject(map.get(key));
 			System.out.println(key+"==========="+m1.toString());
 			context.put(key, m1);
		}
		
		
		
		
//		context.put("column", service.QueryData());
//		System.out.println("service 查询到的数据：：："+service.QueryData());
//		System.out.println("service 查询到的数据：：："+service.QueryData().get("COLUMN4"));
		return new ReplyHtml(VM.html(pagePath+"consumptIonaim/toAddConsumptIonaim.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170055", email = "bingyyf@foxmai.com", name = "杨杰")
//	@aPermission(name = {"客户管理", "综合分析标签页", "还款来源分析[消费目的]", "还款来源分析[消费目的][添加]" })
	public Reply queryConsumptIonaim(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param.put("isView","1");
		System.out.println("查看para：： "+param);
		context.put("param",param);
		System.out.println("param----"+param);
		Map map=service.QueryForExpend(param);
		if(map==null){
			System.out.println("还款来源，消费目的为空~！");
			return  new ReplyHtml(VM.html(pagePath+"consumptIonaim/queryConsumptIonaim.vm", context));
		}
		Set<String> dataSet = map.keySet();
//		Map<String,Object> childMap;
		JSONObject  jsonObject = new JSONObject();;
 		for(String key:dataSet){
 			
 			if(key==null || key.equals("ID") ||key.equals("CREATE_ID") ||key.equals("CREATE_TIME") 
 					||key.equals("CLIENT_ID") ||key.equals("CREDIT_ID")){
 				continue;
 			}
// 			childMap = new HashMap<String,Object>();
// 			System.out.println("===="+key);
// 			System.out.println("========"+jsonObject.fromObject(map.get(key)));
 			Map m1 = jsonObject.fromObject(map.get(key));
 			context.put(key, m1);
		}
		return new ReplyHtml(VM.html(pagePath+"consumptIonaim/queryConsumptIonaim.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = {"客户管理", "综合分析标签页", "还款来源分析[消费目的]", "还款来源分析[消费目的][添加]" })
	public Reply doAddConsumptIonaim(){
		Map<String,Object> param = _getParameters();
		System.out.println("还款来源分析--：param： "+param);
		param.put("USERID", Security.getUser().getId());
		int i = service.doInertCONSUMPTIONAIM(param);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		return new ReplyAjax(flag).addOp(new OpLog("综合分析标签页","还款来源分析","添加消费目的")); 
	}
}

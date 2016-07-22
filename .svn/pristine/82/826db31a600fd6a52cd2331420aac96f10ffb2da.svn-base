package com.pqsoft.bpm.action;


import java.util.List;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import com.pqsoft.action.SysAction;
import com.pqsoft.bpm.service.FundRemindJbpmService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;


public class FundRemindJbpmAction extends Action{
	private String path = "bpm/";
	public VelocityContext context = new VelocityContext();
	FundRemindJbpmService services =new FundRemindJbpmService();
	
	@Override
	public Reply execute() {
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toReceive(){
		boolean flag = false;
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> xlist=services.query_fundRemind(param);
		
	    //发送对象	    
		for (int j = 0; j < xlist.size(); j++) {			
	        try{
	        	Map<String, Object>  xMap=xlist.get(j);
	        	if(xMap!=null&&xMap.get("CODE")!=null&&!xMap.get("CODE").equals("")){
	        		SysAction.setMsg(xMap.get("CODE")+"", "通知：收到新任务--" );
	        		String TYPE = "";
	        		if("yyyy".equals(xMap.get("EMAIL"))){
	        			TYPE = "EMAIL"; 
	        			services.sendJbpmMessage("task节点", xMap, TYPE);
	        		}
	        		if("yyyy".equals(xMap.get("SMS"))){
	        			TYPE = "SMS"; 
	        			services.sendJbpmMessage("task节点", xMap, TYPE);
	        		}
	   
	        	}	        	
	         }catch(Exception e){
	        	logger.error(e, e);
	         }	     
		}
		
		return new ReplyAjax(flag, "提交成功！");
		
	}


}

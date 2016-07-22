package com.pqsoft.leeds.idCard.action;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.leeds.idCard.service.IdCardSrvice;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;

public class IdCardAction extends Action {

	private IdCardSrvice service = new IdCardSrvice();

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		System.out.println("-------------------param="+param);
//		List<Map<String, Object>> list = service.getIdCard(param);
		List<Map<String, Object>> list = service.getIdCardNew(param);
		if(list == null){
			list=new ArrayList<Map<String, Object>>();
		}
		if (list.size() > 0) {
			Map<String, Object> m = list.get(0);
			if(m == null){
				m=new HashMap<String, Object>();
			}
			try {
				if(m.get("IMAGE") !=null && !m.get("IMAGE").equals("")){
					Clob clob = (Clob) m.get("IMAGE");
					m.put("IMAGE", clob.getSubString(1, (int) clob.length()));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			context.put("idCard", m);
		}
		if(param !=null && param.get("TASKBZ")!=null && !param.get("TASKBZ").equals("")){
			String TASKBZ=param.get("TASKBZ").toString();
			if(TASKBZ.equals("ZS")){
				return new ReplyHtml(VM.html("leeds/idCard/toIdCardView.vm", context));
			}
		}
		return new ReplyHtml(VM.html("leeds/idCard/toIdCard.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toHistoryData() {
		Map<String, Object> map = _getParameters();
//		return new ReplyAjaxPage(service.getIdCardPage(map));
		return new ReplyAjaxPage(service.getIdCardPageNew(map));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toView() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		Map<String, Object> map = service.getIdCardById(param);
		
		if(map == null){
			map=new HashMap<String, Object>();
		}
		try {
			if(map.get("IMAGE") !=null && !map.get("IMAGE").equals("")){
				Clob clob = (Clob) map.get("IMAGE");
				map.put("IMAGE", clob.getSubString(1, (int) clob.length()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.put("idCard", map);
		return new ReplyHtml(VM.html("leeds/idCard/toView.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toLpCardView() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("leeds/idCard/LPCard.vm", context));
	}
}

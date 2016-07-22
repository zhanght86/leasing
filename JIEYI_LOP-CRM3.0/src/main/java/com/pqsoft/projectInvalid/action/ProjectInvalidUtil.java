package com.pqsoft.projectInvalid.action;
/**
 *   项目作废
 *   @author 韩晓龙
 */
import java.util.HashMap;
import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.projectInvalid.service.Project_InvalidService;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class ProjectInvalidUtil{

	private Project_InvalidService service = new Project_InvalidService();
	private Map<String, Object> param = new HashMap();
	
	private void getVeriable(String jbpmId) {
		param = JBPM.getVeriable(jbpmId);
	}
	
	/**
	 *  修改项目状态   作废中 99
	 */
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	@aAuth(type = aAuthType.LOGIN)
	public void changeProStatus99(String jbpmId) {
		this.getVeriable(jbpmId);
		param.put("STATUS", "99");//作废中 99
		service.updateProStatus(param);
	}
	
	/**
	 *  修改项目状态   已作废100
	 */
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	@aAuth(type = aAuthType.LOGIN)
	public void changeProStatus100(String jbpmId) {
		this.getVeriable(jbpmId);
		param.put("STATUS", "100");//已作废100
		service.updateProStatus(param);
	}
	
}

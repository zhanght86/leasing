package com.pqsoft.action;

import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.sequence.SequenceUtils;

import java.util.Map;

public class TestAction extends Action{

	/**
	 * 
	 * <p>Title: execute</p> 
	 * <p>Description: TODO()</p> 
	 * @return 
	 * @see com.pqsoft.skyeye.api.Action#execute() 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "测试", "测试", "序列" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	@Override
	public Reply execute() {
		SequenceUtils se = SequenceUtils.getInstance();
		long str = se.nextValue("靳洪东",100,true);
		return new ReplyAjax(str);
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "D050", email = "linjunzhong@huashenghaoche.com", name = "钟林俊")
	public void testCarPurchaseRequest(){
		TaskService taskService = new TaskService();
		Map<String, Object> parameters = _getParameters();
		taskService.CarPurchaseRequest(String.valueOf(parameters.get("projectId")));
	}

}

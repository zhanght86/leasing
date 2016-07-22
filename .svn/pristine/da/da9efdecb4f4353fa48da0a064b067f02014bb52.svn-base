package com.pqsoft.bpm.refundProject;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.refundProject.service.RefundProjectSerivce;

public class RefundProject {

	/**
	 * 项目通过
	 * refundProjectSHStatus
	 * @date 2013-11-12 下午09:28:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void refundProjectSHStatus(String PROJECT_ID) {
		RefundProjectSerivce service = new RefundProjectSerivce();
		 Map<String, Object> param = new HashMap<String, Object>();
		param.put("STATUS", "0");
		param.put("PROJECT_ID", PROJECT_ID+"");
		service.updRefundProjectSHStatus(param);
	}
	
	
	/**
	 * 项目不通过
	 * NotSHStatus
	 * @date 2013-11-12 下午09:28:12
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void NotSHStatus(String PROJECT_ID) {
		RefundProjectSerivce service = new RefundProjectSerivce();
		 Map<String, Object> param = new HashMap<String, Object>();
		param.put("STATUS", "-1");
		param.put("PROJECT_ID", PROJECT_ID);
		service.updRefundProjectSHStatus(param);
	}
}

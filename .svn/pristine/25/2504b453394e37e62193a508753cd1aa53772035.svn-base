package com.pqsoft.bpm.status;

import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.call.service.CallCenterFlowService;
import com.pqsoft.skyeye.exception.ActionException;

/**
 * 呼叫中心验证结果
 * 
 * @author King 2013-11-7
 */
public class CallCenterStatus {

	public void callCenterCheckPass(String jbpmId) {
		Map<String, Object> param = JBPM.getVeriable(jbpmId);
		String PROJECT_ID = param.get("PROJECT_ID") + "";
		boolean flag = false;
		// 验证有无租赁物验证信息记录和承租人信息验证记录
		flag = judgeMessageStatus(param);
		// 如果失败
		if (flag) {
			// 判断承租人信息和租赁物信息是否都为通过
			flag = judgeAllPass(param);
			if (flag == false) {
				throw new ActionException("由于验证结果不通过，请驳回");
			}
		} else {
			throw new ActionException("请录入验证结果");
		}
	}

	/**
	 * 用于在呼叫中心流程开始前,清空承租人身份验证信息和租赁物验证信息
	 * 
	 * @date 2013-11-7 下午04:52:20
	 * @author 韩晓龙
	 */
	public void callCenterMessageClean(String jbpmId) {
		Map<String, Object> param = JBPM.getVeriable(jbpmId);
		String PROJECT_ID = param.get("PROJECT_ID") + "";
		CallCenterFlowService callCenterFlowService = new CallCenterFlowService();
		// 先根据PROJECT_ID得到PROJ_ID
		Map map = (Map) callCenterFlowService.selectProjIdByProjectId(param);
		map.put("PROJ_ID",map.get("PRO_CODE").toString());
		callCenterFlowService.deleteLPDetailById(map);// 根据项目编号删除 法人身份验证信息
		callCenterFlowService.deleteNPDetailById(map);// 根据项目编号删除 自然人身份验证信息
		callCenterFlowService.deleteProductDetailById(map);// 根据项目编号删除 租赁物信息验证信息
	}

	/**
	 * 验证有无租赁物验证信息记录和承租人信息验证记录, param需要包含PROJECT_ID
	 * 
	 * @date 2013-11-7 下午05:04:08
	 * @author 韩晓龙
	 */
	public boolean judgeMessageStatus(Map param) {
		CallCenterFlowService callCenterFlowService = new CallCenterFlowService();
		// 先根据PROJECT_ID得到PROJ_ID
		Map map = (Map) callCenterFlowService.selectProjIdByProjectId(param);
		int num1 = 0;
		int num2 = 0;
		map.put("PROJ_ID",map.get("PRO_CODE").toString());
		num1 = callCenterFlowService.getCountNP(map)
				+ callCenterFlowService.getCountLP(map);// 一并判断
		num2 = callCenterFlowService.getCountEquip(map);// 判断设备条目数
		if (num1 > 0 & num2 > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断承租人信息和租赁物信息是否都为通过, param需要包含PROJECT_ID
	 * 
	 * @date 2013-11-7 下午05:45:10
	 * @author 韩晓龙
	 */
	public boolean judgeAllPass(Map param) {
		CallCenterFlowService callCenterFlowService = new CallCenterFlowService();
		// 先根据PROJECT_ID得到PROJ_ID
		Map map = (Map) callCenterFlowService.selectProjIdByProjectId(param);
		//判断承租人信息和租赁物信息是否都为通过
		int num1 = 0;
		int num2 = 0;
		map.put("PROJ_ID",map.get("PRO_CODE").toString());
		num1 = callCenterFlowService.judgeNp(map) + callCenterFlowService.judgeLp(map);// 一并判断
		num2 = callCenterFlowService.judgeEquip(map);// 判断设备条目数
		if (num1 > 0 & num2 > 0) {
			return true;
		} else {
			return false;
		}
	}

}

package com.pqsoft.bpm;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

/**
 * 根据流程ID查询发起人父机构下用户的code（即发起人的直接领导）
 * @author xingzhili
 * @since 2015-12-25 17:44:46
 *
 */
public class OpLevelLeaderAssignee2 implements AssignmentHandler {

	private static final long serialVersionUID = -4110584093976423121L;

	@Override
	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		String JBPMID = execution.getId();
		if(StringUtils.isBlank(JBPMID)){
			throw new ActionException("流程不存在!");
		}
		//查找流程发起人上级机构领导的登录名
		List<String> oneLvlLeaders = Dao.selectList("bpm.handler.getOpPos1",JBPMID);
		for(String op : oneLvlLeaders){
			if (!StringUtils.isBlank(op)) {
				assignable.setAssignee(op);
				return;
			}
		}

		throw new ActionException("未找到下个节点对应审批人!");
	}

}

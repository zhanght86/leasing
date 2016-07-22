package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;

/**
 * 交叉质检时，查找发起人 
 * @author yipan
 * @date   2015年12月4日 11:44:43
 */
public class StartPersonCodeAssignee2 implements AssignmentHandler {

	/**
	 * @author:yipan 2015年12月4日 11:45:19
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * SECU_USER表，PERSON_TYPE(人员所属类型)字段为1时为内部人员
	 */
	private static final String PERSON_TYPE = "1";

	@Override
	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		
		String JBPMID = execution.getId();
		String op = null;	// 发起人Code
		
		// 得到发起人工号
		List<String> list = Dao.selectList("bpm.handler.queryStartPersonCode", JBPMID);
		if (!list.isEmpty() && list.size() > 0) {
			op = list.get(0);
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("JBPMID", JBPMID);
			op = Dao.selectOne("bpm.handler.queryStartPersonCode2", param);
			// 当没有找到人员时，将任务转给admin超级管理员
			if (null == op || "".equals(op)) op = (String) execution.getVariable("CREATEUSERCODE");
			if (null == op || "".equals(op)) op = "admin";
		}
		
		if(!"admin".equals(op)){
			// 得到发起人工号
			List<Map<String,Object>> listCode = Dao.selectList("bpm.handler.queryStartPersonCodeAndType", op);

			// 如果发起人存在，则写入当前发起人值，若不存在，则写入系统对象admin
			if (!listCode.isEmpty() && listCode.size() > 0) {
				
				Map<String,Object> map = listCode.get(0);	// 当前发起人对象
				String person_type = (String)map.get("PERSON_TYPE");	// 人员所属类型
				
				// 判断当前对象是否为内部人员，如果是：取当前对象值；如果不是：找其上级、上上级
				if(StringUtils.isNotBlank(person_type) && !person_type.equals(PERSON_TYPE)){
					// 找上级
					String USER_ID = (String)map.get("USER_ID");	// 当前对象ID
					// 得到当前人员的上级或上上级，再存入值
					Map<String,Object> externalParentPerson = Dao.selectOne("bpm.handler.queryStartExternalPerson", USER_ID);
					if(null != externalParentPerson && !externalParentPerson.isEmpty()){
						op = (String)externalParentPerson.get("CODE");
					}
				}
			}
		}
		
		assignable.setAssignee(op);
		
	}
}

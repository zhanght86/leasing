package com.pqsoft.bpm.handler;

import java.util.List;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;

/**
 * 查找发起人,直接从fil_project_head表中查
 * 
 * @author xingzhili 2016.06.28
 */
public class StartPersonCodeAssignee3 implements AssignmentHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public void assign(Assignable assignable, OpenExecution execution) throws Exception {
        String JBPMID = execution.getId();
        String op = null;
        List<String> list = Dao.selectList("bpm.handler.queryClerkId", JBPMID);
        if (!list.isEmpty() && list.size() > 0) {
            assignable.setAssignee(list.get(0));
        } else {
            // 当没有找到人员时，将任务转给admin超级管理员
            if (op == null || "".equals(op)) op = (String) execution.getVariable("CREATEUSERCODE");
            if (op == null || "".equals(op)) op = "admin";
            assignable.setAssignee(op);
        }
    }
}

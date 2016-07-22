package com.pqsoft.bpm.handler;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.skyeye.api.Dao;

public class DesifnedDecisionGrouping implements DecisionHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String namespace = "Task.Claim.";
	public String decide(OpenExecution arg0) {
		String name=arg0.getActivity().getName();
		String ptxs="";
		String gjxs="";
		String Direction="";
		if("JZ".equals(name)){
			ptxs="信审专员初审1";
			gjxs="高级信审审核1";
		}else{
			ptxs="信审专员初审";
			gjxs="高级信审审核";
		}
		int PT=Dao.selectInt(namespace+"getTaskNum1",ptxs);
		int GJ=Dao.selectInt(namespace+"getTaskNum1",gjxs);
		if(PT>GJ){
			Direction= "高级信审专员";
		}else{
			Direction= "普通信审专员";
		}
		return Direction;
	}
}

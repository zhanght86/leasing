package com.pqsoft.bpm.handler;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

/**
 * 资料补齐变更判断
 * @author King 2013-10-27
 */
public class FileChangeDecision implements DecisionHandler {

	/**
	 * @author:King 2013-10-27
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution arg0) {
		// TODO King 需要完善资料补齐流程判断
		return "档案上传资料";
	}

}

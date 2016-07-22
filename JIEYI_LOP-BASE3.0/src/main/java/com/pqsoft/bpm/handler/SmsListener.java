package com.pqsoft.bpm.handler;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;

public class SmsListener implements EventListener {

	private static final long serialVersionUID = 242764466869011096L;

	@Override
	public void notify(EventListenerExecution execution) throws Exception {
		execution.getId();
	}

}

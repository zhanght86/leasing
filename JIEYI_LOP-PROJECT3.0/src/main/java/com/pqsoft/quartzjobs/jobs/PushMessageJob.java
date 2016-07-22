package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.base.interfaceTemplate.service.RunInterfaceTemplate;
import com.pqsoft.quartzjobs.AbstractJob;

public class PushMessageJob extends AbstractJob {

	
	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		new RunInterfaceTemplate().runTimingBatch();
	}

}

package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.rentWrite.service.rentWriteNewService;

public class autoHxUpload extends AbstractJob{

	@Override
	protected void exec(JobExecutionContext context)
			throws JobExecutionException {
		rentWriteNewService service = new rentWriteNewService();
		service.autoHx();
	}

}

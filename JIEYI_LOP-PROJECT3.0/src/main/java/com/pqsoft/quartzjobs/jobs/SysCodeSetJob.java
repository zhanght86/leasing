package com.pqsoft.quartzjobs.jobs;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.quartzjobs.AbstractJob;

public class SysCodeSetJob extends AbstractJob {

	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		CodeService service = new CodeService();
		String er=context.getJobDetail().getKey().toString();
	    String[] dd=er.split("_");
		int i=Integer.parseInt(dd[1]);
		service.doupdateone(i);
	}

}

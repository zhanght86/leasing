package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.quartzjobs.AbstractJob;

/**
 * 编码设置计划任务
 * @author wanghl
 * @datetime 2015年7月3日,下午3:41:31
 */
public class CodeJob extends AbstractJob {

	@Override
	protected void exec(JobExecutionContext context)
			throws JobExecutionException {
		CodeService service = new CodeService();
		service.codeJob();
	}

}

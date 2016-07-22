package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;

/**
 * 测试
 * @author Administrator
 */
public class TestJobBean extends AbstractJob {

	
	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {

		System.out.println("..........哈哈，输出来了。");
		
	}

}

/**
 * TODO 
 * @author LUYANFENG @ 2015年5月11日
 */
package com.pqsoft.quartzjobs;

import java.text.SimpleDateFormat;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

import com.pqsoft.skyeye.Log;

/**
 * TODO 
 * @author LUYANFENG @ 2015年5月11日
 *
 */
public class QuartzJobListener extends JobListenerSupport {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	@Override
	public String getName() {
		return "JobListener_"+UUID.randomUUID().toString();
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		super.jobWasExecuted(context, jobException);
		Log.debug("      下次（任务）执行时间预计："+ sdf.format(context.getNextFireTime()));
		Log.debug("----------- quartz 任务监视器:一个任务已结束 ["+context.getJobDetail().getKey()+"]\n");
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		super.jobToBeExecuted(context);
		Log.debug("----------- quartz 任务监视器:一个任务即将启动 ["+context.getJobDetail().getKey()+"]");
	}

	
}

/**
 * TODO 
 * @author LUYANFENG @ 2015年5月11日
 */
package com.pqsoft.quartzjobs;

import java.text.SimpleDateFormat;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

import com.pqsoft.skyeye.Log;

/**
 * @author LUYANFENG @ 2015年5月11日
 */
public class QuartzTriggerListener extends TriggerListenerSupport {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	@Override
	public String getName() {
		return "TriggerListener_"+UUID.randomUUID().toString();
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		Log.debug("=============================== quartz 监视器: 一个触发器开始["+context.getJobDetail().getKey()+"] ===============================");
		super.triggerFired(trigger, context);
		Log.debug("      下次（触发器）执行时间预计："+ sdf.format(trigger.getNextFireTime()));
		Log.debug("=============================== quartz 监视器:一个触发器结束["+context.getJobDetail().getKey()+"] ===============================");
	}
	
	

}

package com.pqsoft.weixin.action;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Map;
import java.util.UUID;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Quartz;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * @category 只用于测试
 * @author LUYANFENG @ 2015年5月14日
 * @deprecated
 * 
 */
public class TestQuartzAction extends Action implements Job{
	
	/*@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply add(){
		try {
			Quartz.init();
			Scheduler defaultScheduler = Quartz.factory.getDefaultScheduler();
			defaultScheduler.shutdown();
			if(defaultScheduler.isShutdown()){
				Quartz.init();
				defaultScheduler = Quartz.factory.getDefaultScheduler();
				JobKey jobKey = new JobKey("job1","group1");
				defaultScheduler.deleteJob(jobKey );
			}
			{
				// job1
				JobDataMap jobDataMap = new JobDataMap();
				
				jobDataMap.put("job1", "job1"+UUID.randomUUID().toString());
				JobDetail job = newJob(TestQuartzAction.class)
						.withIdentity("job1", "group1")
						.setJobData(jobDataMap)
						.build();

				Trigger trigger = newTrigger()
						.withIdentity("trigger1", "group1")
						.startNow()
						.withSchedule(simpleSchedule()
						.withIntervalInSeconds(100)
						.repeatForever())
						.build();

				defaultScheduler.scheduleJob(job, trigger);
			}
			
			{
				// job2
				JobDataMap jobDataMap = new JobDataMap();
				jobDataMap.put("job2", "job2"+UUID.randomUUID().toString());
				JobDetail job2 = newJob(TestQuartzAction.class)
						.withIdentity("job2", "group2")
						.setJobData(jobDataMap)
						.build();

				Trigger trigger2 = newTrigger()
						.withIdentity("trigger2", "group2")
						.startNow()
						.withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?") )
						.build();

				defaultScheduler.scheduleJob(job2, trigger2);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}*/

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("---test 多任务同时启动 job.  : "+ context.getJobDetail().getJobDataMap().getWrappedMap());
	}
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply stop(){
		try {
			Scheduler defaultScheduler = Quartz.factory.getDefaultScheduler();
			defaultScheduler.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply reStart(){
		try {
			Quartz.init();
			Scheduler defaultScheduler = Quartz.factory.getDefaultScheduler();
//			defaultScheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void addJob(String jobCode, String className, String time,Map<String,Object>... jobdataMap)
			throws Exception {
		StdSchedulerFactory factory = new StdSchedulerFactory();
		if (factory == null) {
//			init();
		}
		JobDataMap newJobDataMap = new JobDataMap();
		if(jobdataMap != null && jobdataMap.length > 0){
			for(Map<String,Object> jobData : jobdataMap){
				newJobDataMap.putAll(jobData);
			}
		}
		Scheduler scheduler = factory.getScheduler();
		Class c = Class.forName(className);
		JobDetail job = JobBuilder.newJob(c)
				.withIdentity(jobCode + "_job", jobCode + "_group")
				.setJobData(newJobDataMap)
				.build();
		TriggerBuilder<CronTrigger> builder = TriggerBuilder.newTrigger()
				.withIdentity(jobCode + "_trigger", jobCode + "_group")
				.withSchedule(CronScheduleBuilder.cronSchedule(time));
		Trigger trigger = builder.startNow().build();
		scheduler.scheduleJob(job, trigger);
	}
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply resumeTrigger(){
		
		
		
		
		String triggerName = (String) _getParameters().get("tn");
		String triggerGroup = (String) _getParameters().get("tg");
		try {
			Scheduler defaultScheduler = Quartz.factory.getDefaultScheduler();
			TriggerKey triggerKey = new TriggerKey(triggerName,triggerGroup);
			defaultScheduler.resumeTrigger(triggerKey );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply pauseTrigger(){
		String triggerName = (String) _getParameters().get("tn");
		String triggerGroup = (String) _getParameters().get("tg");
		try {
			Scheduler defaultScheduler = Quartz.factory.getDefaultScheduler();
			TriggerKey triggerKey = new TriggerKey(triggerName,triggerGroup);
			defaultScheduler.pauseTrigger(triggerKey );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply pauseAll(){
		try {
			Scheduler defaultScheduler = Quartz.factory.getDefaultScheduler();
			defaultScheduler.pauseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply start(){
		try {
			Scheduler defaultScheduler = Quartz.factory.getDefaultScheduler();
			defaultScheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply init(){
		try {
			Scheduler defaultScheduler = Quartz.factory.getDefaultScheduler();
//			defaultScheduler.pauseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply resumeAll(){
		try {
			Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
			defaultScheduler.resumeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply deleteJob(){
		String jobName = (String) _getParameters().get("tn");
		String jobGroup = (String) _getParameters().get("tg");
		try {
			Scheduler defaultScheduler = Quartz.factory.getScheduler();
			JobKey jobKey = new JobKey(jobName,jobGroup);
			defaultScheduler.deleteJob(jobKey );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Reply execute() {
		return null;
	}
}

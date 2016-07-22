package com.pqsoft.quartz.action;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.EscapeTool;
import org.jfree.xml.factory.objects.ArrayObjectDescription;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Quartz;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

/**
 * 暂停 启动用一个新表存储下处理。并让任务暂停后才能修改！ 
 * 任务名不能改
 */
public class QuartzAction extends Action {

	/**
	 * 所有的任务列表
	 */
	@Override
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "计划任务" })
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Map<String,Object>> selectList = Dao.selectList("quartz.getQuartzList_mixed");
		for(Map<String,Object> job : selectList){
			Object job_data = job.get("JOB_DATA");
			if(job_data != null && job_data instanceof Blob){
				this.setBlob2Obj(job,"JOB_DATA");
//				this.getBlobTypeInfo2(job, "JOB_DATA");
			}
		}
		
		context.put("EscapeTool", new EscapeTool());
		context.put("list", selectList);
		return new ReplyHtml(VM.html("quartz/quartz.vm", context));
	}
	
	

	/**
	 * 页面 : 添加新任务
	 * @author : LUYANFENG @ 2015年5月11日
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "计划任务" })
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	public Reply toAdd() {
		return null;
	}

	
	/**
	 * 动作：添加新任务
	 * @author : LUYANFENG @ 2015年5月11日
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "计划任务" })
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	public Reply doAdd() {
		String name = SkyEye.getRequest().getParameter("name");
		String className = SkyEye.getRequest().getParameter("className");
		String info = SkyEye.getRequest().getParameter("info");
		String cron_expression = SkyEye.getRequest().getParameter("cron_expression");
		String old_name = SkyEye.getRequest().getParameter("old_name");
		
		JSONObject json = new JSONObject();
		json.put("ok", false);
		if(StringUtils.isBlank( name )){
			json.put("ok", false);
			json.put("error", "名称不能为空");
			return new ReplyJson( json  );
			
		}
		if(StringUtils.isBlank( className )){
			json.put("ok", false);
			json.put("error", "类名不能为空");
			return new ReplyJson( json  );
		}
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			json.put("ok", false);
			json.put("error", "找不到给定的类名");
			return new ReplyJson( json  );
		}
		if(StringUtils.isBlank( cron_expression ) || !CronExpression.isValidExpression(cron_expression)){
			json.put("ok", false);
			json.put("error", "cron表达式无效");
			return new ReplyJson( json  );
		}
		try {		
			

			Scheduler scheduler = Quartz.factory.getScheduler();
			JobKey jobKey = new JobKey(name+"_job",name+"_group" );
			boolean checkExists = scheduler.checkExists(jobKey );
			if(checkExists ){ // 更新
				if(StringUtils.isBlank( old_name )){
					json.put("ok", false);
					json.put("error", "名称不能为空.");
					return new ReplyJson( json  );
				}
				if(!(name+"_job").equals(old_name)){
					json.put("ok", false);
					json.put("error", "名称不能为空.");
					return new ReplyJson( json  );
				}
				Quartz.kill(name);
			}
			Map<String,Object> jobData = new HashMap<String,Object>();
			jobData.put("info", info);
			this.addJob(name, className, cron_expression, jobData);
			json.put("ok", true);
		} catch (Exception e) {
			json.put("ok", false);
			json.put("error", e.getMessage());
			Dao.rollback();
//			throw new ActionException("添加计划任务失败", e);
		}
		
		return new ReplyJson(json );
	}
	
	/**
	 * 任务停止
	 * 原理：在添加任务时就把信息往我定的一个表中加一份，当任务停止时就把quartz相关表里的数据删除；启动时就从我的那个表中把信息写进去。
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "计划任务" })
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	public Reply stop (){
		JSONObject json = new JSONObject();
		json.put("ok", false);
		
		String name = (String) _getParameters().get("name");
		String nude_name = name.replaceAll("_job$", "");
		if(StringUtils.isNotBlank(name)){
			try {
				Scheduler scheduler = Quartz.factory.getScheduler();
				JobKey jobKey = new JobKey(nude_name+"_job",nude_name+"_group");
				scheduler.pauseJob(jobKey );
				json.put("ok", true);
			} catch (Exception e) {
				json.put("ok", false);
				e.printStackTrace();
			}
		}else{	
			try {
				Scheduler scheduler = Quartz.factory.getScheduler();
				scheduler.pauseAll();
				json.put("ok", true);
			} catch (SchedulerException e) {
				json.put("ok", false);
				e.printStackTrace();
			}
		}
		
		return new ReplyJson(json);
	}
	
	/**
	 * 任务启动
	 *  一个或多个
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "计划任务" })
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	public Reply run (){
		JSONObject json = new JSONObject();
		
		String name = (String) _getParameters().get("name");
		if(StringUtils.isNotBlank(name)){
			String nude_name = name.replaceAll("_job$", "");
			try {
				Scheduler scheduler  = Quartz.factory.getScheduler();
				JobKey jobKey = new JobKey(nude_name+"_job",nude_name+"_group");
				scheduler.resumeJob(jobKey);
				json.put("ok", true);
			} catch (SchedulerException e) {
				json.put("ok", false);
				e.printStackTrace();
			}
		}else{	
			try {
				Scheduler scheduler = Quartz.factory.getScheduler();
				scheduler.resumeAll();
				json.put("ok", true);
			} catch (SchedulerException e) {
				json.put("ok", false);
				e.printStackTrace();
			}
		}
		
		return new ReplyJson(json);
	}

	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "计划任务" })
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	public Reply doDel() {
		String name = (String) _getParameters().get("name");
		String group_name = (String) _getParameters().get("group_name");
		String sched_name = (String) _getParameters().get("sched_name");
		JSONObject json = new JSONObject();
		json.put("ok", false);
		if(StringUtils.isBlank(name)  || StringUtils.isBlank(group_name) || StringUtils.isBlank(sched_name)){
			json.put("ok", false);
			return new ReplyJson(json);
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("JOB_NAME", name);
		params.put("JOB_GROUP", group_name);
		params.put("SCHED_NAME", sched_name);
		String nude_name = name.replaceAll("_job$", "");
		try {
			Quartz.kill(nude_name);
			json.put("ok", true);
		} catch (Exception e) {
			json.put("ok", false);
			e.printStackTrace();
		}
		return new ReplyJson(json);
	}

	private  void addJob(String jobCode, String className, String time,Map<String,Object>... jobdataMap)
			throws Exception {
		if (Quartz.factory == null) {
			Quartz.init();
		}
		JobDataMap newJobDataMap = new JobDataMap();
		if(jobdataMap != null && jobdataMap.length > 0){
			for(Map<String,Object> jobData : jobdataMap){
				newJobDataMap.putAll(jobData);
			}
		}
		Scheduler scheduler = Quartz.factory.getScheduler();
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
	
	private  void getBlobTypeInfo2(Map<String, Object> m, String columnName) {
		if (m == null || StringUtils.isBlank(columnName))
			return;
		StringBuilder sb = new StringBuilder();
		try {
			Blob blob = (Blob) m.get(columnName);
			if (blob != null) {
				InputStream binaryStream = blob.getBinaryStream();
				BufferedInputStream bis = new BufferedInputStream(binaryStream, 1024);
				byte[] b = new byte[1024];
				int index= 0;
				while (( index = bis.read(b) ) != -1) {
					sb.append(new String(b,0, index));
				}
				m.put(columnName, sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param job
	 * @param string
	 * @author : LUYANFENG @ 2015年5月11日
	 */
	private void setBlob2Obj(Map<String, Object> m, String columnName) {
		JobDataMap jobDatamap = new JobDataMap();
		try {
			Blob blob = (Blob) m.get(columnName);
			if (blob != null) {
				InputStream is = blob.getBinaryStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Map<String,Object> jobData = (Map<String, Object>) ois.readObject();
				m.put(columnName, jobData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

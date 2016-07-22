package com.pqsoft.quartzjobs.jobs;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.sms.service.NewSmsService;


public class GetFestivalInfo_JZZL extends AbstractJob {
	protected final Logger logger=Logger.getLogger(super.getClass());
	private NewSmsService service=new NewSmsService();
	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
			logger.info("短信接口========节日短信信息存入短信接口========定时任务开始执行");
			//将节日短信信息存入短信接口表sms_newinterface中
			int i=service.getFestivalInfo();
			if(i!=0){
				logger.info("短信接口========节日短信信息存入短信接口表sms_newinterface成功========");
			}
			else{
				logger.info("短信接口========节日短信信息存入短信接口表sms_newinterface失败========");
			}
			logger.info("短信接口========节日短信信息存入短信接口========定时任务执行结束");
	}
}
package com.pqsoft.quartzjobs.jobs;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.sms.service.NewSmsService;


public class GetAllSMSToSend_JZZL extends AbstractJob {
	protected final Logger logger=Logger.getLogger(super.getClass());
	private NewSmsService service=new NewSmsService();
	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
			logger.info("短信平台========节日短信信息存入短信接口表sms_newinterface========定时任务开始执行");
			//将节日短信信息存入短信接口表sms_newinterface中
			Boolean bl=service.getAllSMSToSend();
			if(bl!=false){
				logger.info("短信平台========节日短信信息存入短信接口表sms_newinterface成功========");
			}
			else{
				logger.info("短信平台========节日短信信息存入短信接口表sms_newinterface失败========");
			}
			logger.info("短信平台========节日短信信息存入短信接口表sms_newinterface========定时任务执行结束");
	}
}
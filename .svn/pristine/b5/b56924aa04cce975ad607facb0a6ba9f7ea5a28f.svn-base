package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.util.FIinterface;

/**
 * 捷翊租赁:中金网银接口   把需要核销的数据插入到日志表T_SYS_ZHONGJIN_MIDDLE 
 * @author wanghl
 * @datetime 2015年8月11日,下午5:35:52
 */
public class AutoHxRecord_JYZL extends AbstractJob {
	
	@Override
	protected void exec(JobExecutionContext context)
			throws JobExecutionException {
		if(FIinterface.on_off()!=null){
			rentWriteNewService service = new rentWriteNewService();
			service.insertZhongjinMiddle();
		}
	}
}

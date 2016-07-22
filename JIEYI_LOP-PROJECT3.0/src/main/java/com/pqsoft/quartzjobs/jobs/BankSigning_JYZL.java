package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.crm.service.BankSignMgService;
import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.util.FIinterface;

/**
 * 客户签约  中金接口
 * @author wanghl
 * @datetime 2015年8月17日,下午12:07:57
 */
public class BankSigning_JYZL extends AbstractJob {

	@Override
	protected void exec(JobExecutionContext context)
			throws JobExecutionException {
		if(FIinterface.on_off()!=null){
			BankSignMgService service = new BankSignMgService();
			service.autoSigning();
		}
	}

}

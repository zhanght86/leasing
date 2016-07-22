package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.rentDraw.service.RentDrawService;

/**
 * 租金划扣管理数据刷新
 * @author Administrator
 *
 */
public class RentDrawJob extends AbstractJob{

	@Override
	protected void exec(JobExecutionContext context)
			throws JobExecutionException {
		new RentDrawService().refreshRentDraw();
	}

}

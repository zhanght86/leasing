package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.skyeye.api.Dao;

public class RentDay  extends AbstractJob{

	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		//每日刷新租金数据
		Dao.selectOne("jobs.rentDay");
	}

}

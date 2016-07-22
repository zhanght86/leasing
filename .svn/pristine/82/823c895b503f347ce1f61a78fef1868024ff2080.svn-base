package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.skyeye.api.Dao;

public class OverDun_Day  extends AbstractJob{
	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		//每天刷新逾期
		Dao.selectOne("jobs.DunDay");
		Dao.selectOne("jobs.DunDayCopy");
	}

}

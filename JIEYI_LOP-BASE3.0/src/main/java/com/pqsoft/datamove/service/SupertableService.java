package com.pqsoft.datamove.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.skyeye.api.Dao;

public class SupertableService implements Job{

	@Override
	public void execute(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		try {
			Dao.selectList("fund.Ebank.doUpdateAllProject");
		} catch (Exception e) {
			Dao.rollback();
		}
	}

	
}

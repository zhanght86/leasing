package com.pqsoft.irrReport.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.skyeye.api.Dao;

public class RealIrrJob implements Job {
	/*********刷实际收益率**@auth: king 2014年11月3日 *************************/

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			new RealIRRService().refreshIRRForProject(sdf.format(new Date()));
			Dao.commit();
		} catch (Exception e) {
			Dao.rollback();
		}
	}

}

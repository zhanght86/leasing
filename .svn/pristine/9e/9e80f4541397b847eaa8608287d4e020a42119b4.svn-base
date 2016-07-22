package com.pqsoft.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.skyeye.Log;

public class JobBean implements Job {

	private int i;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Log.debug(this.getClass() + " : " + (i++));
	}

}
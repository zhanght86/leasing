package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.skyeye.api.Dao;

public class autoHxHuiZhi extends AbstractJob {

	@Override
	protected void exec(JobExecutionContext context)
			throws JobExecutionException {
		//异步调用核销方法
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				try {
					new rentWriteNewService().autoHxLKSC();
					Dao.commit();
				} catch (Exception e) {
					Dao.rollback();
					e.printStackTrace();
				} finally {
					Dao.close();
				}

				System.out.println("耗时："
						+ ((System.currentTimeMillis() - start) / 1000)+"秒");
			}
		}).start();
	}

}

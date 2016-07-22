package com.pqsoft.quartzjobs.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.payment.service.PaymentApplyService;
import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.FIinterface;

public class FiTransactionResults extends AbstractJob {

	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		
				//异步调用
				new Thread(new Runnable() {
					@Override
					public void run() {
						long start = System.currentTimeMillis();
						try {
							System.out.println("..........哈哈，输出来了。");
							PaymentApplyService service = new PaymentApplyService();
							service.tranxTradeNew();
							//首期款回执
							service.firstReceipt();
							//放款回执
							service.paymentReceipt();
							//租金回执
							rentWriteNewService rentService=new rentWriteNewService();
							rentService.autoInf();
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

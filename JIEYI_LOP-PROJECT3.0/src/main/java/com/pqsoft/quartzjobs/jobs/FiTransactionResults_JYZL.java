package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.payment.service.PaymentApplyService;
import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.FIinterface;

public class FiTransactionResults_JYZL extends AbstractJob {

	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		if(FIinterface.on_off()!=null){
			//异步调用
			new Thread(new Runnable() {
				@Override
				public void run() {
					long start = System.currentTimeMillis();
					try {
						// 接口反馈
						PaymentApplyService service = new PaymentApplyService();
						service.tranxTradeNew_JYZL();
						
						//首期款回执
						service.firstReceipt_JYZL();
						//放款回执
						service.paymentReceipt_JYZL();
						//租金回执
						rentWriteNewService rentService=new rentWriteNewService();
						rentService.autoInf_JYZL();
						
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
}

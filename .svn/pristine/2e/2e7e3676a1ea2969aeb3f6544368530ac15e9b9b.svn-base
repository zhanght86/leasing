/**
 * TODO 
 * 北京平强软件有限公司
 * @author LUYANFENG @ 2015年8月11日
 */
package com.pqsoft.weixinfw.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pqsoft.skyeye.Log;

/**
 * 简单线程工具类
 * @author LUYANFENG @ 2015年8月11日
 * FIXME 没有进一步测试过
 *
 */
public final class  PQsoftThreadUtils  {
	
	private static Set<PQsoftThreadUtils> allThreadPool = new HashSet<PQsoftThreadUtils>();
	
	private  ExecutorService execServ = Executors.newFixedThreadPool(10);
	/**
	 * 一个线程池的任务
	 */
	private Map<String,IPQsoftThreadJob> allThreadJob = new HashMap<String,IPQsoftThreadJob>();
	
	
	/**
	 * 是否停止所有
	 * @see shutdownAll()
	 */
	private boolean shutdown = false;
	private PQsoftThreadUtils(int nThreads) {
		execServ = Executors.newFixedThreadPool(nThreads);
	}

	/**
	 * 获取一个线程池实类
	 * @param nThreads
	 * @return
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public  static PQsoftThreadUtils  newInstance(int nThreads) {
		PQsoftThreadUtils pqsoftThreadUtils = new PQsoftThreadUtils(nThreads);
		allThreadPool.add(pqsoftThreadUtils);
		return pqsoftThreadUtils;
	}
	
	/**
	 * 对所有线程池发出停止命令
	 * @param shutdown
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public void shutdownAllServ( ){
		if(allThreadPool == null || allThreadPool.isEmpty()){
			return ;
		}
		Iterator<PQsoftThreadUtils> iterator = allThreadPool.iterator();
		for(; iterator.hasNext();){
			iterator.next().shutdownAll();
		}
		allThreadPool.clear();
	}
	
	/**
	 * 对这个线程池的所有任务发出停止命令
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public void shutdownAll(){
		this.shutdown = true;
		execServ.shutdown();
	}
	
	/**
	 * 对一个线程发出停止命令
	 * @param jobName
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public void shutdown(String jobName){
		allThreadJob.get(jobName).stop(true);
	}
	
	/**
	 * 添加新的线程到线程池中
	 * @param jobBean
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public PQsoftThreadUtils addNewThreadJob(final IPQsoftThreadJob jobBean){
		allThreadJob.put(jobBean.getJobName(), jobBean);
		execServ.execute(new Thread() {
			@Override
			public void run() {
				try {
					while(!shutdown){
						if(jobBean.toStop()){
							break ;
						}
						
						jobBean.autoRun();
						
						if(jobBean.sleep() <= 0){
							
							break;
						}
						Log.debug("--- thread["+ jobBean.getJobName() + "("+Thread.currentThread().getId()+")] 将在"+jobBean.sleep()+ "毫秒后执行");
						Thread.sleep(jobBean.sleep());
					}
					Log.debug("线程["+jobBean.getJobName()+"]已停止");
					allThreadJob.remove(jobBean.getJobName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return this;
	}

	

}

package com.pqsoft.quartzjobs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.skyeye.Log;

/**
 * 所有的任务都该继承这个类或应该有个自定义的父类，方便以后的更新。
 * @author Administrator
 *
 */
public abstract class AbstractJob implements Job {

	
	@Override
	public  void  execute(JobExecutionContext context) throws JobExecutionException {
		/*synchronized (AbstractJob.class) {
			Log.debug("### 一个任务开始["+context.getJobDetail().getKey()+"]");
			this.exec( context);
			Log.debug("### 一个任务结束["+context.getJobDetail().getKey()+"]");
		}*/
		this.exec( context);
	}
	
	/**
	 * 任务实现方法
	 */
	protected abstract void exec (JobExecutionContext context) throws JobExecutionException;
	
	/**
	 * @author lishuo
	 * @param object
	 * @return 时间串
	 * @throws ParseException
	 */
	public static String formatDate(Object object) {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dd= null;
        if(object instanceof Date){
            dd=format.format(new Date());
        }else{
            Long time=new Long((Long) object);
            dd = format.format(time);
        }
        return dd;
     }
	
}

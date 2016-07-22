/**
 * TODO 
 * 北京平强软件有限公司
 * @author LUYANFENG @ 2015年8月11日
 */
package com.pqsoft.weixinfw.utils;

/**
 * TODO 
 * @author LUYANFENG @ 2015年8月11日
 *
 */
public interface IPQsoftThreadJob {
	/**
	 * <pre>
	 * 用户实现这个方法就可以了
	 * 注意：这个方法会被一直执行直到被thread标记停止
	 * 受autoRun()返回值的影响
	 * </pre>
	 * @see PQsoftThreadUtils
	 * @see PqsoftThreadJobBead.sleep()
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public void autoRun();
	
	/**
	 * <pre>
	 * 获取执行间隔,当值小于等于0时只执行一次
	 * 影响 autoRun()的执行
	 * <pre>
	 * @see PQsoftThreadUtils
	 * @return 执行间隔 毫秒
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public long sleep();

	/**
	 * <pre>
	 * 获取任务名称，全application最好不要重名;
	 *
	 * </pre>
	 * @return
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public String getJobName();

	/**
	 * 是否让这个任务停止
	 * @return
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public boolean toStop();

	/**
	 * @param isStop
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public void stop(boolean isStop);


}

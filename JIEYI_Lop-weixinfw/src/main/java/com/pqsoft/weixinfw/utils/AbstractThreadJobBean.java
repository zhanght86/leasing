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
public abstract class AbstractThreadJobBean implements IPQsoftThreadJob {

	private boolean isStop = false;
	
	@Override
	public abstract void autoRun() ;
	
	@Override
	public long sleep() {
		return 0;
	}

	@Override
	public String getJobName() {
		return PQsoftStringUtil.UUID();
	}

	@Override
	public boolean toStop() {
		return this.isStop;
	}

	@Override
	public void stop(boolean isStop) {
		this.isStop = isStop;
	}

}

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 */
package com.pqsoft.weixinfw.utils;

import java.util.Map;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 *
 */
public interface IMethodReturn {

	/**
	 * 方法执行成功 失败
	 * @return
	 * @author LUYANFENG @ 2015年7月7日
	 */
	public boolean isOK();
	
	/**
	 * 获取所有错误信息 只读
	 * @return
	 * @author LUYANFENG @ 2015年7月7日
	 */
	public Map<String, Object> getErrorMsg();
	
	/**
	 * 获取所有非错误信息 只读
	 * @return
	 * @author LUYANFENG @ 2015年7月7日
	 */
	public  Map<String, Object> getMsg();

	/**
	 * 设置信息
	 * @param key
	 * @param msg
	 * @return
	 * @author LUYANFENG @ 2015年7月7日
	 */
	public  IMethodReturn addMsg(String key, Object msg);

	/**
	 * 清理错误信息
	 * @return
	 * @author LUYANFENG @ 2015年7月7日
	 */
	public IMethodReturn clearError();

	/**
	 * 弹出最后一个错误
	 * @return
	 * @author LUYANFENG @ 2015年7月7日
	 */
	public String pollErrorMsg();

	/**
	 * 设置错误信息
	 * @param key
	 * @param msg
	 * @return
	 * @author LUYANFENG @ 2015年7月7日
	 */
	IMethodReturn addErrorMsg(String key, String msg);
}

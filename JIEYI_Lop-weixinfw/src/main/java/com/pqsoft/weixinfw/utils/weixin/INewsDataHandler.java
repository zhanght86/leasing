/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 */
package com.pqsoft.weixinfw.utils.weixin;

import java.util.List;

import com.pqsoft.weixinfw.utils.weixin.bean.NewsBean;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 *
 */
public interface INewsDataHandler {

	/**
	 * @return
	 * @author LUYANFENG @ 2015年7月10日
	 * @throws Exception 
	 */
	List<NewsBean> handler() throws Exception;

}

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 */
package com.pqsoft.weixinfw.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 *
 */
public class DefaultMethodReturn implements IMethodReturn {
	private LinkedHashMap<String,Object> resultMap = new LinkedHashMap<String,Object>();
	private LinkedHashMap<String,Object> resultErrorMap = new LinkedHashMap<String,Object>();
	private Stack<String> errMsgIndex = new Stack<String>();
	private Stack<String> msgIndex = new Stack<String>();
	@Override
	public boolean isOK() {
		return resultErrorMap.isEmpty();
	}

	@Override
	public Map<String, Object> getErrorMsg() {
		return Collections.unmodifiableMap(this.resultErrorMap);
	}

	@Override
	public IMethodReturn clearError() {
		this.errMsgIndex.clear();
		this.resultErrorMap.clear();
		return this;
	}

	@Override
	public String pollErrorMsg() {
		if(resultErrorMap != null && !resultErrorMap.isEmpty()){
			return resultErrorMap.get(this.errMsgIndex.pop()).toString();
		}
		return "";
	}
	
	@Override
	public IMethodReturn addErrorMsg(String key, String msg) {
		this.errMsgIndex.add(key);
		this.resultErrorMap.put(key, msg);
		return this;
	}
	
	@Override
	public IMethodReturn addMsg(String key, Object msg) {
		this.msgIndex.add(key);
		this.resultMap.put(key, msg);
		return this;
	}

	@Override
	public Map<String, Object> getMsg() {
		return Collections.unmodifiableMap(this.resultMap);
	}

}

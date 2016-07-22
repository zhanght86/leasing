package com.pqsoft.util;

public interface ISortCustom {

	/**
	 * 会被回调
	 * @author LUYANFENG @ 2015年3月1日
	 * @param o2 
	 * @param o1 
	 * @return 与compare一样
	 */
	int doSort(Object o1, Object o2);
}

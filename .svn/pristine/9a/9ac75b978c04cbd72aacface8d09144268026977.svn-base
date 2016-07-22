package com.pqsoft.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MyCollectionsUtils {
	/**
	 * 排序
	 * 给一个list中的每个map中的某个字段是list的排序。。。
	 * @author LUYANFENG @ 2015年3月1日
	 * @param lmap
	 * @param key	哪个map value 是要排序的list
	 * @param sortKey 排序key
	 * @param desc
	 */
	public static void sortInMap(List<Map<String,Object>> lmap , String key ,ISortCustom sortCustom ){
		for(Map<String,Object> m : lmap){
			sortList((List)m.get(key),sortCustom);
		}
	}

	/**
	 * 排序
	 * 给一个list排序（元素是map）
	 * @author LUYANFENG @ 2015年3月1日
	 * @param list
	 * @param sortKey
	 * @param desc
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void sortList(List  list ,final ISortCustom sortCustom ){
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				return sortCustom.doSort(o1, o2);
			}
			
		});
	}
	


}


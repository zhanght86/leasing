package com.pqsoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtil {
	
	public static List<Object> listInsertInto(List<Object> sourceList,int index,Object obj){
		List<Object> toList = new ArrayList<Object>();
		for(int i = 0; i < sourceList.size(); i++){
			if(i == index){
				toList.add(obj);
			}
			toList.add(sourceList.get(i));
		}
		return toList;
	}
	
	public static List<Object> listInsertIntoFirstLocation(List<Object> sourceList,Object obj){
		return listInsertInto(sourceList,0,obj);
	}

	public static List<Object> listInsertDefualtComboxInfo(ArrayList<Object> arrayList) {
		Map<String,Object> tem = new HashMap<String,Object>();
		tem.put("FLAG", "--请选择--");
		tem.put("CODE", "");
		return listInsertInto(arrayList,0,tem);
	}
}

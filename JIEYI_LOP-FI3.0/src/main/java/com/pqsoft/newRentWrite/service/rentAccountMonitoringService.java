package com.pqsoft.newRentWrite.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class rentAccountMonitoringService {

	 private final String xmlPath = "rentAccountMonitoring.";

	 	private void init(Map map){
	 		Dao.delete(xmlPath+"delTempRent");
	 		Dao.commit();
	 		List<Object> selectList = Dao.selectList(xmlPath+"selectInit",map);
//		        List<String> proList = new ArrayList<String>();
		        if(selectList.size()>0){
		        	for(int i=0;i<selectList.size();i++){
		        		Object projectObj = selectList.get(i);
		        		if(projectObj!=null && projectObj !=""){
		        			Map projectIdMap =( Map)projectObj;
		        			Object project_id_obj = projectIdMap.get("PROJECT_ID");
		        			if(project_id_obj !=null && project_id_obj !=""){
		        				String project_id =String.valueOf(project_id_obj);
//		        				proList.add(project_id);
		        				Dao.insert(xmlPath+"addTempRent",project_id);
		     
		        			}
		        			
		        		}
		        	}
		        }
		        Dao.commit();
	 	}
	    // 查询按钮
	    public Page query(Map<String, Object> m) {
	        Object Curr = m.get("page");
	        Object count = m.get("rows");
	        int pageCurr = Integer.parseInt(Curr == null ? "1" : Curr.toString());
	        int pageCount = Integer.parseInt(count == null ? "10" : count.toString());
//	        init(m);
	        m.put("PAGE_BEGIN1", pageCount * (pageCurr - 1) + 1);
	        m.put("PAGE_END1", pageCount * pageCurr);

	        return  PageUtil.getPage(m, xmlPath + "query", xmlPath + "queryCount");
	    }

	    // 未锁定导出所有的数据
	    public List excelAll(Map map) {
	        map.put("USER_CODE", Security.getUser().getCode());
	        map.put("USER_NAME", Security.getUser().getName());
//	        init(map);
	        List list = Dao.selectList(xmlPath + "excelALL", map);// 山重
	        return list;
	    }
}

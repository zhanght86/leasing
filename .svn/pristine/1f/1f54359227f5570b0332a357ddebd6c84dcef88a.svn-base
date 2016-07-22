package com.pqsoft.bpm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.skyeye.api.Dao;

/**
 * 历史数据修改判断
 * @author Donzell 2013-12-30
 */
public class WorkFlowFileDecisionLeader implements DecisionHandler {
	private String namespace = "bpm.handler.";
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution arg0) {
		String file_no = arg0.getVariable("FILE_NO")+"";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("FILE_NO", file_no);
		Map<String, Object> map = new HashMap<String,Object>();
		Object obj = Dao.selectOne(namespace + "queryWorkFlowFileRelLeaders", param);
		if(obj!=null){
			map=(Map<String, Object>) obj;
		String departments = (String) map.get("REL_LEADER");
		String[] deps;
		String dep = "";
		if(departments.length()!=0){
			deps = departments.split(",");
		
			//总经理、财务总监、管理副总、建机营业副总、商用车营业副总
			if("exclusive20".equals(arg0.getActivity().getName())){
				
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					if("财务总监".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive21".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					if("管理副总".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive22".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					if("建机营业副总".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive23".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					if("商用车营业副总".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive26".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					if("总经理".equals(dep)){
						return "相关";
					}
				}
			}
		}
		}
		return "未相关";
	}
	
}

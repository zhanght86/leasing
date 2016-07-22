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
public class WorkFlowFileDecision implements DecisionHandler {
	private String namespace = "bpm.handler.";
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution arg0) {
		String file_no = arg0.getVariable("FILE_NO")+"";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("FILE_NO", file_no);
		Map<String, Object> map = new HashMap<String,Object>();
		Object obj = Dao.selectOne(namespace + "queryWorkFlowFileRelDepartments", param);
		if(obj!=null){
			map=(Map<String, Object>) obj;
		String departments = (String) map.get("REL_DEPART");
		String[] deps;
		String dep = "";
		if(departments.length()!=0){
			deps = departments.split(",");
		
			//财务部  (财务部,租金科,债权部,法务部,IT管理部,档案科,信审部,建机企划部,商用车企划部等等18个部门科室)
			if("exclusive5".equals(arg0.getActivity().getName())){
				
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("财务部".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive4".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("租金科".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive2".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("融资部".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive3".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("法务科".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive6".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("建机信审科".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive7".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("产品研发部".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive1".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("建机债权科".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive8".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("商用车企划科".equals(dep)){
						return "相关";
					}
				}
			}else if("exclusive9".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("建机企划科".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive10".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("建机营业部".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive11".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("商用车营业部".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive12".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("审计监察科".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive13".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("企业文化部".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive14".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("综合管理部".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive15".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("人力资源科".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive16".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("渠道管理科".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive17".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("档案科".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive18".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("IT管理部".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive19".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("海外事业部".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive24".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("商用车债权科".equals(dep)){
						return "相关";
					}
				}
			}
			else if("exclusive25".equals(arg0.getActivity().getName())){
				for(int i=0;i<deps.length;i++){
					dep = deps[i].toString();
					System.out.println("dep="+dep);
					if("商用车信审科".equals(dep)){
						return "相关";
					}
				}
			}
		}
		}
		return "未相关";
	}
	
}

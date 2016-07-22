package com.pqsoft.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.util.BaseUtil;


public class SysReportAction extends Action {
	/***********@auth: king 2014年8月8日 *系统首页图表************************/
	private String PATH_="sysPortal/";
	/**
	 * 主页报表
	 */
	@Override
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute() {
//		Map<String,Object> param=_getParameters();
//		BaseUtil.getDataAllAuth(param);
//		Map<String,Object> map=Dao.selectOne("sys.getProjectNum",param);
//		Set<String> key=map.keySet();
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//		  for (String s:key) {  
//		   Map<String,Object> mm=new HashMap<String,Object>();
//		   if("CAOGAO".equals(s)){
//			   mm.put("DATA_TYPE","草稿");
//			   mm.put("DATA_VALUE", map.get(s));
//			   list.add(mm);
//		   }else if("PINGSHEN".equals(s)){
//			   mm.put("DATA_TYPE","评审");
//			   mm.put("DATA_VALUE", map.get(s));
//			   list.add(mm);
//		   }else if("QIZU".equals(s)){
//			   mm.put("DATA_TYPE","起租");
//			   mm.put("DATA_VALUE", map.get(s));
//			   list.add(mm);
//		   }else if("QITA".equals(s)){
//			   mm.put("DATA_TYPE","其他");
//			   mm.put("DATA_VALUE", map.get(s));
//			   list.add(mm);
//		   }
//		  }  
//		VelocityContext context =new VelocityContext();
//		context.put("CHART_NAME", "当前项目数量统计");
//		context.put("pieList", list);
		return new ReplyHtml(VM.html(PATH_+"reportTab.vm", null));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply toReportChartProject(){
		Map<String,Object> param=_getParameters();
		BaseUtil.getDataAllAuth(param);
		Map<String,Object> map=Dao.selectOne("sys.getProjectNum",param);
		Set<String> key=map.keySet();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		  for (String s:key) {  
		   Map<String,Object> mm=new HashMap<String,Object>();
		   if("CAOGAO".equals(s)){
			   mm.put("DATA_TYPE","草稿");
			   mm.put("DATA_VALUE", map.get(s));
			   list.add(mm);
		   }else if("PINGSHEN".equals(s)){
			   mm.put("DATA_TYPE","评审");
			   mm.put("DATA_VALUE", map.get(s));
			   list.add(mm);
		   }else if("QIZU".equals(s)){
			   mm.put("DATA_TYPE","起租");
			   mm.put("DATA_VALUE", map.get(s));
			   list.add(mm);
		   }else if("QITA".equals(s)){
			   mm.put("DATA_TYPE","其他");
			   mm.put("DATA_VALUE", map.get(s));
			   list.add(mm);
		   }
		  }  
		VelocityContext context =new VelocityContext();
		context.put("CHART_NAME", "当前项目数量统计");
		context.put("pieList", list);
		return new ReplyHtml(VM.html(PATH_+"projectCount.vm", context));
	}
}

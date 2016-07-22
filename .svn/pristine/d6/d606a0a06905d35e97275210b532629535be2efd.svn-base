package com.pqsoft.demo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.UtilChart;

public class DemoReportAction extends Action {
	private final String _PATH = "demo/";
	UtilChart utilChart=new UtilChart();
	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> mm=new HashMap<String, Object>();
		mm.put("XVALUE", "1");
		mm.put("YVALUE", "3");
		list.add(mm);
		Map<String,Object> mm1=new HashMap<String, Object>();
		mm1.put("XVALUE", "2");
		mm1.put("YVALUE", "9");
		list.add(mm1);
		Map<String,Object> mm2=new HashMap<String, Object>();
		mm2.put("XVALUE", "5");
		mm2.put("YVALUE", "6");
		list.add(mm2);
		Map<String,Object> mm3=new HashMap<String, Object>();
		mm3.put("XVALUE", "12");
		mm3.put("YVALUE", "32");
		list.add(mm3);
		Map<String,Object> mm4=new HashMap<String, Object>();
		mm4.put("XVALUE", "4");
		mm4.put("YVALUE", "14");
		list.add(mm4);
		
		List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("DATA_TYPE", "ceshi");
		map.put("DATA_VALUE", list);
		lst.add(map);
		
		List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();
		Map<String,Object> mmm=new HashMap<String, Object>();
		mm.put("XVALUE", "1");
		mm.put("YVALUE", "-3");
		list1.add(mmm);
		Map<String,Object> mmm1=new HashMap<String, Object>();
		mmm1.put("XVALUE", "2");
		mmm1.put("YVALUE", "-9");
		list1.add(mmm1);
		Map<String,Object> mmm2=new HashMap<String, Object>();
		mmm2.put("XVALUE", "5");
		mmm2.put("YVALUE", "-6");
		list1.add(mmm2);
		Map<String,Object> mmm3=new HashMap<String, Object>();
		mmm3.put("XVALUE", "12");
		mmm3.put("YVALUE", "-32");
		list1.add(mmm3);
		Map<String,Object> mmm4=new HashMap<String, Object>();
		mmm4.put("XVALUE", "4");
		mmm4.put("YVALUE", "-14");
		list1.add(mmm4);
		
		Map<String,Object> map1=new HashMap<String, Object>();
		map1.put("DATA_TYPE", "yanzheng");
		map1.put("DATA_VALUE", list1);
		lst.add(map1);
		return null;
//		return new ReplyHtml(utilChart.createDashedChart("sandiantu", lst,""));
	}
	
	/**
	 * 散点图
	 * @return
	 * @author King 2014年8月4日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@SuppressWarnings("unchecked")
	public Reply toChartDashedDemo() {
		VelocityContext context = new VelocityContext();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> mm=new HashMap<String, Object>();
		mm.put("XVALUE", "1");
		mm.put("YVALUE", "3");
		list.add(mm);
		Map<String,Object> mm1=new HashMap<String, Object>();
		mm1.put("XVALUE", "2");
		mm1.put("YVALUE", "9");
		list.add(mm1);
		Map<String,Object> mm2=new HashMap<String, Object>();
		mm2.put("XVALUE", "5");
		mm2.put("YVALUE", "6");
		list.add(mm2);
		Map<String,Object> mm3=new HashMap<String, Object>();
		mm3.put("XVALUE", "12");
		mm3.put("YVALUE", "32");
		list.add(mm3);
		Map<String,Object> mm4=new HashMap<String, Object>();
		mm4.put("XVALUE", "4");
		mm4.put("YVALUE", "14");
		list.add(mm4);
		
//		context.put("chartReport",utilChart.createDashedChart("散点图", list,""));
		return new ReplyHtml(VM.html(_PATH + "graphDemoDashed.vm", context));
	}
}

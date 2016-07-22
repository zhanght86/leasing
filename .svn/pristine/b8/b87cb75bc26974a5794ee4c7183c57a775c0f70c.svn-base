package com.pqsoft.GPS.GPSds.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.GPS.GPSds.service.GPSdsService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class GPSdsAction extends Action{
	GPSdsService service = new GPSdsService();
	@Override
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"资产管理", "GPS定位管理", "列表"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("GPS/Gpsds/GPSds.vm", context));
	}
	@aPermission(name = {"资产管理", "GPS定位管理", "列表"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findAll()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.findAll(param);
		return new ReplyAjaxPage(page);
	}
	@aPermission(name = {"资产管理", "GPS定位管理", "定位"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findone()
	{
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("list", service.findDing(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("GPS/Gpsds/Dingwei.vm", context));
	}
	@aPermission(name = {"资产管理", "GPS定位管理", "定位"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findMany()
	{
		Map<String,Object> param=_getParameters();
		System.out.println(param);
		VelocityContext context=new VelocityContext();
		context.put("list", service.findMany(param));
		return new ReplyHtml(VM.html("GPS/Gpsds/Dingmany.vm", context));
	}
	@aPermission(name = {"资产管理", "GPS定位管理", "定位"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply quanping()
	{
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		context.put("list", service.findMany(param));
		context.put("open_flag",com.pqsoft.skyeye.api.Action._getParameters().get("open_flag") );
		return new ReplyHtml(VM.html("GPS/Gpsds/quanping.vm", context));
	}
	
	@aPermission(name = {"资产管理", "GPS定位管理", "定位"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply quan()
	{
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		context.put("list", service.findDing(param));
		context.put("open_flag",com.pqsoft.skyeye.api.Action._getParameters().get("open_flag") );
		return new ReplyHtml(VM.html("GPS/Gpsds/quanping.vm", context));
	}
	@SuppressWarnings("unchecked")
	@aPermission(name = {"资产管理", "GPS定位管理", "定位"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply getDetail()
	{
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		List<Map<String,Object>> detail=service.finddetail(param);
		List<Map<String,Object>> overdueList=new ArrayList<Map<String,Object>>();
	    for(Map<String,Object> map1:detail){
	    	List<Map<String,Object>> overdue=(List<Map<String,Object>>)service.getEpOverdueDetail(map1);
	    	overdueList.addAll(overdue);
	    }
	    List<Map<String,Object>> eqdetailList=(List<Map<String,Object>>)service.getEquipDetail(param);
	    for(Map<String,Object> eqmap:eqdetailList){
	    	if(eqmap.get("TB_NUM")==null){
	    		eqmap.put("TB_NUM", 0);
	    	}
	    }
		context.put("detail", detail);
		context.put("overdue", overdueList);
	    context.put("eqdetail", eqdetailList);
		context.put("list", service.findMany(param));
		return new ReplyHtml(VM.html("GPS/Gpsds/gpsDetail.vm", context));
	}
	@SuppressWarnings("unchecked")
	@aPermission(name = {"资产管理", "GPS定位管理", "定位"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply getDingwei()
	{
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		List<Map<String,Object>> detail=service.finddetail(param);
		List<Map<String,Object>> overdueList=new ArrayList<Map<String,Object>>();
	    for(Map<String,Object> map1:detail){
	    	List<Map<String,Object>> overdue=(List<Map<String,Object>>)service.getEpOverdueDetail(map1);
	    	overdueList.addAll(overdue);
	    }
	    List<Map<String,Object>> eqdetailList=(List<Map<String,Object>>)service.getEquipDetail(param);
	    for(Map<String,Object> eqmap:eqdetailList){
	    	if(eqmap.get("TB_NUM")==null){
	    		eqmap.put("TB_NUM", 0);
	    	}
	    }
		context.put("detail", detail);
		context.put("overdue", overdueList);
	    context.put("eqdetail", eqdetailList);
		context.put("list", service.findMany(param));
		context.put("par", param);
		return new ReplyHtml(VM.html("GPS/Gpsds/getDingwei.vm", context));
	}
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"资产管理", "GPS轨迹管理", "列表"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findAllshu()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.findAllshu(param);
		return new ReplyAjaxPage(page);
	}
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"资产管理", "GPS轨迹管理", "列表"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply lushu()
	{
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("GPS/Gpsds/GPSls.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资产管理", "GPS轨迹管理", "地图"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findlushu()
	{
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		context.put("param", param);
		System.out.println(param);
		return new ReplyHtml(VM.html("GPS/Gpsds/lushu.vm", context));
	}
	@SuppressWarnings("unchecked")
	@aPermission(name = {"资产管理", "GPS轨迹管理", "地图"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findshu()
	{
		Map<String, Object> param = _getParameters();
		List roadlist = service.findshu(param);
		return new ReplyJson(JSONArray.fromObject(roadlist));
	}
}


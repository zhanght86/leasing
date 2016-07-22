package com.pqsoft.GPS.GPSInstall.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.GPS.GPSInstall.service.GPSManageService;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class GPSManageAction extends Action{
	
	GPSManageService service = new GPSManageService();
	@aPermission(name = {"资产管理", "GPS管理", "列表"})
	@aDev(code = "777777", email = "xucp@pqsoft.cn", name = "许长朋")
	@aAuth(type = aAuthType.LOGIN)
	@Override
	public Reply execute() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> gpsList = service.findGPSList();
		List<Object> gps_status = DataDictionaryService.queryDataDictionary("GPS使用状态");
		List<Object> gps_mllx = DataDictionaryService.queryDataDictionary("GPS命令类型");
		List<Object> gpslx = DataDictionaryService.queryDataDictionary("GPS类型");
		List<Object> gps_mlms = DataDictionaryService.queryDataDictionary("GPS命令模式");
		List<Object> gps_scjb = DataDictionaryService.queryDataDictionary("GPS锁车级别");
		context.put("param", param);
		context.put("gpsList", gpsList);
		context.put("queryDataDictionary", gps_status);
		context.put("mllx", gps_mllx);
		context.put("gps_lx", gpslx);
		context.put("gps_mlms", gps_mlms);
		context.put("gps_scjb", gps_scjb);
		
		return new ReplyHtml(VM.html("GPS/GPSInstall/GPS_Install.vm", context));
	}
	
	@aPermission(name = {"资产管理", "GPS管理", "列表"})
	@aDev(code = "777777", email = "xucp@pqsoft.cn", name = "许长朋")
	@aAuth(type = aAuthType.LOGIN)
	public Reply GPSListing() {
		Map<String, Object> param = _getParameters();
		Page page = service.queryPage(param);
		return new ReplyAjaxPage(page);
	}
	@aPermission(name = {"资产管理", "GPS管理", "安装"})
	@aDev(code = "777777", email = "xucp@pqsoft.cn", name = "许长朋")
	@aAuth(type = aAuthType.LOGIN)
	public Reply installGPS(){
		Map<String,Object> param = _getParameters();
		boolean flag = service.toInstallGPS(param);
		if(flag){
			int i= service.savehistory(param);
			if(i>0)
			{
			return new ReplyAjax(param);
			}
			else
			{
				return new ReplyAjax(false,"保存失败");
			}
		}else{
			return new ReplyAjax(false,"保存失败");
		}
	}
	/*
	@aPermission(name = {"资产管理", "GPS管理", "变更"})
	@aDev(code = "777777", email = "xucp@pqsoft.cn", name = "许长朋")
	@aAuth(type = aAuthType.LOGIN)
	public Reply chengeGPS(){
		Map<String,Object> param = _getParameters();
		boolean flag = service.toChangeGPS(param);
		if(flag){
			return new ReplyAjax(param);
		}else{
			return new ReplyAjax(false,"变更失败");
		}
	}
	*/
	@aPermission(name = {"资产管理", "GPS管理", "解除GPS绑定"})
	@aDev(code = "777777", email = "xucp@pqsoft.cn", name = "许长朋")
	@aAuth(type = aAuthType.LOGIN)
	public Reply removeGPS(){
		String id = SkyEye.getRequest().getParameter("ID");
		service.removeGPS(id);
		return new ReplyAjax();
	}
	/**
	 * gps命令
	 * @return
	 * @author xuchangpeng 2014-11-18
	 */
	/*
	@aPermission(name = {"资产管理", "GPS管理", "GPS命令"})
	@aDev(code = "777777", email = "xucp@pqsoft.cn", name = "许长朋")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findGPSCommand(){
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("GPS/GPSInstall/GPS_Command.vm", context));
	}

	public Reply gpsCommandList(){
		Map<String, Object> param = _getParameters();
		Page page = service.queryGPSCommandPage(param);
		return new ReplyAjaxPage(page);
	}*/
	@aPermission(name = {"资产管理", "GPS管理", "安装记录"})
	@aDev(code = "777777", email = "guozheng@pqsoft.cn", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply history()
	{
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		context.put("param", param);
		//List list=service.findhistorylist(param);
		//context.put("ming",list);
		return new ReplyHtml(VM.html("GPS/GPSInstall/GPS_history.vm", context));
	}
	@aPermission(name = {"资产管理", "GPS管理", "安装记录"})
	@aDev(code = "777777", email = "guozheng@pqsoft.cn", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply historyopen()
	{
		Map<String,Object> param=_getParameters();
		Page page = service.findhistory(param);
		return new ReplyAjaxPage(page);
	}
	@aPermission(name = {"资产管理", "GPS管理", "GPS命令"})
	@aDev(code = "777777", email = "guozheng@pqsoft.cn", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findming()
	{
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		context.put("ming",service.findming(param));
		return new ReplyHtml(VM.html("GPS/GPSInstall/GPS_fasong.vm", context));
	}
	@aPermission(name = {"资产管理", "GPS管理", "GPS命令"})
	@aDev(code = "777777", email = "guozheng@pqsoft.cn", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findfa()
	{
		Map<String,Object> param =_getParameters();
		int result=service.findfa(param);
		if(result>0)
		{
			return new ReplyAjax(true, "寻找成功");
		}
		return new ReplyAjax(false, "寻找失败");
	}
	@aPermission(name = {"资产管理", "GPS管理", "设备保养"})
	@aDev(code = "777777", email = "guozheng@pqsoft.cn", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply gpsbaoyang()
	{
		Map<String,Object> param=_getParameters();
		int i=service.gpsbaoyang(param);
		if(i>0)
		{
			return new ReplyAjax(true, "success");
		}
		else
		{
			return new ReplyAjax(false, "shibai");
		}
	}
	@aPermission(name = {"资产管理", "GPS管理", "设备保养"})
	@aDev(code = "777777", email = "guozheng@pqsoft.cn", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findgpsdis()
	{
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		context.put("item",service.findgpsdis(param));
		return new ReplyHtml(VM.html("GPS/GPSInstall/GPS_distance.vm", context));
	}
	@aPermission(name = {"资产管理", "GPS管理", "设备保养"})
	@aDev(code = "777777", email = "guozheng@pqsoft.cn", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply savedistance()
	{
		Map<String,Object> param=_getParameters();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String hehe = dateFormat.format( now ); 
		param.put("DISDATE", hehe); 
		Double DISTANCE=(Double.valueOf(param.get("DISTANCE").toString()))/1000;
		param.put("DISTANCE", DISTANCE);
		int i=service.savedistance(param);
		if(i>0)
		{
			return new ReplyAjax(true, "设置成功");
		}
		else
		{
			return new ReplyAjax(false, "设置失败");
		}
	}
}

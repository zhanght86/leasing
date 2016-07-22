package com.pqsoft.GPS.GPSCommand.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.GPS.GPSCommand.service.GPSCommandService;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
/**
 * GPS命令管理
 * @author: King 2014年11月14日
 */
public class GPSCommandAction extends Action {
	private String path="GPS/command/";
	
	private GPSCommandService service=new GPSCommandService();
	
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"资产管理","GPS命令管理","列表"})
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		List<Object> gps_mljg = DataDictionaryService.queryDataDictionary("GPS命令结果");
		List<Object> gpslx = DataDictionaryService.queryDataDictionary("GPS类型");
		List<Object> gps_mlms = DataDictionaryService.queryDataDictionary("GPS命令模式");
		List<Object> gps_mllx = DataDictionaryService.queryDataDictionary("GPS命令类型");
		List<Object> gps_scjb = DataDictionaryService.queryDataDictionary("GPS锁车级别");
		context.put("param", param);
		context.put("gps_mljg", gps_mljg);
		context.put("gps_lx", gpslx);
		context.put("gps_mlms", gps_mlms);
		context.put("gps_mllx", gps_mllx);
		context.put("gps_scjb", gps_scjb);
		return new ReplyHtml(VM.html(path+"gpsCommandMg.vm", context));
	}
	
	
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
//	@aPermission(name={"资产管理","GPS命令管理","列表"})
	public Reply showMgGpsCommand(){
		return new ReplyAjaxPage(service.mgGspCommand(_getParameters()));
	}
	
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"资产管理","GPS命令管理","命令发送"})
	public Reply addGpsCommand(){
		int i=service.addGpsCommand(_getParameters());
		if(i==1){
			return new ReplyAjax(true).addOp(new OpLog("GPS管理-命令发送", "命令发送", "发送成功:"+_getParameters()));
		}else if(i==0){
			return new ReplyAjax(false,"发送失败");
		}else if(i==2){
			return new ReplyAjax(false,"用户名或密码验证失败");
		}else if(i==3){
			return new ReplyAjax(false,"数据写入失败");
		}else{
			return new ReplyAjax(false,"其他数值操作失败");
		}
		
	}
}

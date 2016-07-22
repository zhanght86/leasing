package com.pqsoft.creditWind.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.creditWind.service.CreditWindService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class CreditWindAction extends Action{

	private String basePath = "creditWind/";
	CreditWindService service=new CreditWindService();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "项目管理", "评审会议纪要","列表显示"})
	public Reply toCreditWindrManager() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(basePath+"toCreditWindManager.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "项目管理", "评审会议纪要","列表显示"})
	public Reply toCreditWindAjaxData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.selectCreditWind(param));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply AddControlMeetingPage(){
		Map<String, Object> maps = _getParameters();
		VelocityContext context = new VelocityContext();
		
		context.put("PROJECT_ID", maps.get("PROJECT_ID"));
		//查询项目编号
		Map mapPro=service.queryProCodeByPro_ID(maps);
		try{
		maps.put("PROJECT_CODE", mapPro.get("PRO_CODE"));
		}catch(Exception e){
			throw new ActionException("请选择对应的项目");
		}
		context.put("maps", maps);
		context.put("SERIAL_NUMBER",CodeService.getCode("风控纪要编号", null, maps.get("PROJECT_ID")+""));
		List<Object> judgeLevelList = new DataDictionaryMemcached().get("风控评审级别");
		context.put("judgeLevel", judgeLevelList);
		return new ReplyHtml(VM.html(basePath+"AddCreditWindMeeting.vm",context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply ExecuteAddControlMeeting(){
		Map<String, Object> maps = _getParameters();
		int i = service.ExecuteAddControlMeeting(maps);
		return new ReplyAjax(i);
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply uploadRecord(){
		Map<String,Object> m = _getParameters();
		Map<String,Object> obj = (Map<String,Object>)service.uploadFileForOne(SkyEye.getRequest());
		JSONArray array = JSONArray.fromObject(obj);
		return new ReplyJson(array);
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "项目管理", "评审会议纪要","查看"})
	public Reply toShowWindView(){
		VelocityContext context = new VelocityContext();
		// 根据风控Id查询风控纪要信息
		Map<String,Object> m = _getParameters();
		m.put("ID", m.get("windID"));
		
		Map map = (Map) service.getWindSumaryInfo(m);//风控会议纪要
		context.put("windSumary", map);
		map.put("ids", map.get("PROJECT_ID"));

		map.put("SUMMY_ID", map.get("ID"));
			
		List list = service.getWindLevel(map);//评审人意见  
		context.put("personLevel", list);

		List<Object> judgeLevelList = new DataDictionaryMemcached().get("风控评审级别");
		context.put("judgeLevel", judgeLevelList);
		context.put("PROJECT_ID", map.get("PROJECT_ID"));
		
		return new ReplyHtml(VM.html(basePath+"CheckCreditWindMeeting.vm",context));

	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name = { "项目管理", "评审会议纪要","查看"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply downLoadRecordFile(){
		Map<String,Object> map = _getParameters();
		return new ReplyFile(new File(map.get("FILE_URL") + ""), map.get("FILE_NAME") + "");
	}
}

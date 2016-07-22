package com.pqsoft.mortgage.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.mortgage.service.MortgageService;
import com.pqsoft.shangpai.service.ShangpaiService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class MortgageAction extends Action { 
	
	private final String pagePath="mortgage/";//页面路径
	private MortgageService service = new MortgageService();
	private ShangpaiService serviceP = new ShangpaiService();

	@SuppressWarnings("unused")
	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"车辆管理", "抵押管理","列表显示"})
	public Reply execute() {
		// TODO Auto-generated method stub
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("company", service.toFindCompany());
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();	
		return new ReplyHtml(VM.html(pagePath+"toMgMortgageDDY.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = {"车辆管理", "待抵押管理", "列表显示"})
	public Reply toMgMortgageDDY(){
		Map<String,Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);
		return new ReplyAjaxPage(service.toMgMortgageDDY(param));
	}
	
	@SuppressWarnings("unused")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"车辆管理", "解抵押管理", "列表显示"})
	public Reply toMgMortgageJY(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();	
		return new ReplyHtml(VM.html(pagePath+"toMgMortgageJY.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"车辆管理", "解抵押管理", "列表显示"})
	public Reply toMgMortgageJYData(){
		Map<String,Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);
		return new ReplyAjaxPage(service.toMgMortgageJY(param));
	}
	
	
	
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	//@aPermission(name = {"车辆管理", "抵押管理", "列表显示"})
//	public Reply toMgMortgagePage(){
//		Map<String,Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		return new ReplyHtml(VM.html(pagePath+"toMgMortgage.vm", context));
//	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"车辆管理", "抵押查询管理", "列表显示"})
	public Reply toMgMortgage(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();	
		return new ReplyHtml(VM.html(pagePath+"toMgMortgage.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = {"车辆管理", "抵押管理", "列表显示"})
	public Reply toMgMortgagePage(){
		Map<String,Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);
		return new ReplyAjaxPage(service.toMgMortgage(param));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = {"车辆管理", "设备抵押管理", "列表"})
	public Reply toMgMortgageEqu(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("equ", service.toMgMortgageEqu(param));
		return new ReplyHtml(VM.html(pagePath+"toMgMortgageEqu.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"车辆管理", "抵押管理", "添加"})
	public Reply doAddMortgage(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		param.put("USERCODE",Security.getUser().getCode());
		param.put("XIUGAI",Security.getUser().getId());
		param.put("XIUGAIDATE",df.format(new Date()));
		param.put("TARGET_SY",param.get("TARGET_TOTAL"));	
		int i = service.doAddMortgage(param);//添加指标价格
		int k = serviceP.saveshangpai(param);
		String msg = "";
		boolean flag = false;
		if(i>0&&k>0){
			flag = true;
			msg = "抵押&上牌添加成功";
		}else {
			msg = "抵押&上牌添加失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("车辆管理-设备抵押和上牌管理","添加", msg));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"车辆管理", "抵押管理", "查看"})
	public Reply toViewMortgage(){
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());	
		param.put("mortgage", service.toViewMortgage(param));
		param.put("shangpai", serviceP.findone(param));
		return new ReplyJson(JSONObject.fromObject(param));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"车辆管理", "抵押管理", "修改"})
	public Reply toUpMortgage(){
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());	
		param.put("mortgage", service.toViewMortgage(param));
		param.put("shangpai", serviceP.findone(param));
		return new ReplyJson(JSONObject.fromObject(param));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = {"车辆管理", "设备抵押管理", "删除"})
	//车辆抵押修改
	public Reply doUpMortgage(){
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());
		int i = service.doUpMortgage(param);//添加指标价格
		String msg = "";
		boolean flag = false;
		if(i>0){
			flag = true;
			msg = "抵押修改成功";
		}else {
			msg = "抵押修改失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("车辆管理-设备抵押管理","修改抵押", msg));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"车辆管理", "设备抵押管理", "解压"})
	//设备抵押解压
	public Reply doUpMortgageJ(){
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());
		int i = service.doUpMortgage(param);//添加指标价格
		String msg = "";
		boolean flag = false;
		if(i>0){
			flag = true;
			msg = "抵押解压成功";
		}else {
			msg = "抵押解压失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("车辆管理-设备抵押管理","解压", msg));
	}
}

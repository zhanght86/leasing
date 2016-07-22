package com.pqsoft.financeConvert.action;

import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.financeConvert.service.FinanceModifyService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ReplyExcel;

/**
 * <p>
 * Title: 融资租赁信息系统平台 财务接口管理
 * </p>
 * <p>
 * Description: 财务接口维护 ;
 * </p>
 * <p>
 * Company: SFLC
 * </p>
 * 
 * @author yuq@strongflc.com
 * @version 1.0
 */
public class FinanceModifyAction extends Action {

	private Map<String, Object> m = null;
	private FinanceModifyService service=new FinanceModifyService();
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口维护" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
	@Override
	@SuppressWarnings("unchecked")
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口维护","供应商表维护" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")	
	@SuppressWarnings("unchecked")
	public Reply goSuper() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		VelocityContext context = new VelocityContext();
				
		return new ReplyHtml(VM.html("financeModify/financeSuper.vm",
				context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口维护","客户表维护" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")	
	@SuppressWarnings("unchecked")
	public Reply goClient() {
		m = _getParameters();
		m.put("USER_CODE", Security.getUser().getCode());
		VelocityContext context = new VelocityContext();
				
		return new ReplyHtml(VM.html("financeModify/financeClient.vm",
				context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口维护","供应商表维护" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")	
	@SuppressWarnings("unchecked")
	public Reply goSuperPage() {
		m = _getParameters();		
		m.put("USER_CODE", Security.getUser().getCode());		
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
				
		return new ReplyAjaxPage(service.getSuperTableData(m));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口维护","客户表维护" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")	
	@SuppressWarnings("unchecked")
	public Reply goClientPage() {
		m = _getParameters();		
		m.put("USER_CODE", Security.getUser().getCode());		
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
				
		return new ReplyAjaxPage(service.getClientTableData(m));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口维护","供应商表维护","修改" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")	
	@SuppressWarnings("unchecked")
	public Reply modifySuperPage() {
		m = _getParameters();		
		m.put("USER_CODE", Security.getUser().getCode());		
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		int i=service.updateSuper(m);
	  
		return new ReplyAjax(i).addOp(new OpLog("财务接口维护","修改(供应商财务接口)",m.toString()));
		
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口维护","客户表维护","修改" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")	
	@SuppressWarnings("unchecked")
	public Reply modifyClientPage() {
		m = _getParameters();		
		m.put("USER_CODE", Security.getUser().getCode());		
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		int i=service.updateClient(m);
		  
		return new ReplyAjax(i).addOp(new OpLog("财务接口维护","修改(客户表财务接口)",m.toString()));
		
	}
	

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口维护","供应商表维护","导出Excle" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")	
	@SuppressWarnings("unchecked")
	public Reply explorExcleSuper() {
		m=_getParameters();
		m.put("USER_CODE", Security.getUser().getCode());		
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		
		return new ReplyExcel(service.getExplorExcleSuper(m),"financeSuper"+"_"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls").addOp(new OpLog("财务接口维护","导出(供应商财务接口)",m.toString()));
	  		
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "财务管理", "财务接口维护","客户表维护","导出Excle" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")	
	@SuppressWarnings("unchecked")
	public Reply explorExcleClient() {
		m=_getParameters();
		m.put("USER_CODE", Security.getUser().getCode());		
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		
		return new ReplyExcel(service.getExplorExcleClient(m),"financeClient"+"_"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls").addOp(new OpLog("财务接口维护","导出(供应商财务接口)",m.toString()));
	  		
	}
	
}

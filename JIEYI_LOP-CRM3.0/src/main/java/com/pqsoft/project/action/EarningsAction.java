package com.pqsoft.project.action;

import java.util.ArrayList;
import java.util.Map;

import javassist.expr.NewArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.project.service.EarningsService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class EarningsAction extends Action{

	private final String pagePath="project/";
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * 收益分析
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	//@aPermission(name = { "收益分析" })
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "收益分析", "修改页面" })
	public Reply toEarnings(){
		Map<String, Object> param = _getParameters();
		String client_id = (String) param.get("CLIENT_ID");
		EarningsService service = new EarningsService();
		Map<String, Object> map = service.selectEarnings(client_id);
		//判断map是否为空
		if(map != null && !map.isEmpty()){
			param.putAll(map);
		}
		System.out.println("----42---map:"+map);
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath+"earnings.vm", context));
	}
	
	/*
	 * 查询收益分析是否存在记录
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply selectEarningsId(){
		Map<String, Object> param = _getParameters();
		String client_id = (String) param.get("CLIENT_ID");
		EarningsService service = new EarningsService();
		Map<String, Object> map = service.selectEarnings(client_id);
		boolean flag = false;
		if(map !=null && !map.isEmpty()){
			String idStr = map.get("ID").toString();
			if(idStr==null || idStr==""){
				flag = false;
			}else{
				flag = true;
			}
		}
		String msg = "客户没有收益分析信息记录!";
		return new ReplyAjax(flag, msg);
	}
	/*
	 * 收益分析查看
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "收益分析", "查看" })
	public Reply toViewEarnings(){
		Map<String, Object> param = _getParameters();
		String client_id = (String) param.get("CLIENT_ID");
		EarningsService service = new EarningsService();
		Map<String, Object> map = service.selectEarnings(client_id);
		//判断map是否为空
		if(map != null && !map.isEmpty()){
			param.putAll(map);
		}
		System.out.println("----61---map:"+map);
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("JSZQ", new SysDictionaryMemcached().get("结算周期"));
		return new ReplyHtml(VM.html(pagePath+"earningsToView.vm", context));
	}
	
	/*
	 * 修改
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply saveEarnings(){
		Map<String, Object> param = _getParameters();
		System.out.println("----74---param:"+param);
		EarningsService service = new EarningsService();
		int i = service.updateEarnings(param);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		String msg ="修改成功!";
		return new ReplyAjax(flag, msg);
	}
	
	/*
	 * 增加
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply addEarnings(){
		Map<String, Object> param = _getParameters();
		System.out.println("----92---param:"+param);
		EarningsService service = new EarningsService();
		int i = service.addEarnings(param);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		String msg ="添加成功!";
		return new ReplyAjax(flag, msg);
	}
	
}

package com.pqsoft.analysisBySynthesis.action;

import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.analysisBySynthesis.service.BankWaterService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class BankWaterAction extends Action {
	

	private final String pagePath = "analysisBySynthesis/";
	private BankWaterService service = new BankWaterService();

	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "客户管理",  "综合分析标签页", "银行流水", "银行流水页面"})
	public Reply execute() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println(param);
		context.put("param",param);
		context.put("yinhangliushui", new DataDictionaryMemcached().get("银行流水对象"));
		return new ReplyHtml(VM.html(pagePath+"bankwater/toMgBankwater.vm", context));
	}
	
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "客户管理",  "综合分析标签页", "银行流水", "银行流水数据"})
	public Reply tofindBANKWATER() {
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage((Page) service.tofindBANKWATER(map));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "客户管理",  "综合分析标签页", "银行流水", "银行流水数据[添加]"})
	public Reply doInertBANKWATER() {
		Map<String,Object> map = _getParameters();
		System.out.println(map.toString());     
		JSONArray rows = JSONArray.fromObject(map.get("myData"));
		String id = Dao.getSequence("SEQ_BANKWATER");
		map.put("ID", id);
		map.put("USERID", Security.getUser().getId());
		int i = service.doInertBANKWATER(map);
		int k = 0;
		if(i>0){
			for(Object obj:rows){
				Map<String,Object> m = (Map<String,Object>)obj;
				m.put("BANKWATER_ID", id);
				m.put("ID", Dao.getSequence("SEQ_BANKWATER_CHILD"));
				m.put("USERID", Security.getUser().getId());
				k = service.doInertBANKWATERCHILD(m);
			}
			
		}
		boolean flag = false;
		if(i>0&&k>0){
			flag = true;
		}
		return new ReplyAjax(flag).addOp(new OpLog("综合分析标签页","银行流水数据","添加"));
	}
	
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "客户管理",  "综合分析标签页", "银行流水", "银行流水数据[删除]"})
	public Reply doDelBANKWATER(){
		Map<String,Object> map = _getParameters();

		int i = service.doDelBANKWATERCHILD(map);
		if(i>0){
			 i = service.doDelBANKWATER(map);
		}
		boolean flag = false;
		if(i>0){
			flag = true;
		}		
		return new ReplyAjax(flag).addOp(new OpLog("综合分析标签页","银行流水数据","删除"));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "客户管理",  "综合分析标签页", "银行流水", "银行流水数据[查看]"})
	public Reply queryBankWater(){
		Map<String,Object> map = _getParameters();
		return  new ReplyAjax(service.tofindBANKWATER(map));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "客户管理",  "综合分析标签页", "银行流水", "银行流水数据[查看详细]"})
	public Reply tofindBANKWATERINFO(){
		Map<String,Object> map = _getParameters();
		return  new ReplyAjaxPage(service.tofindBANKWATERINFO(map));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "客户管理",  "综合分析标签页", "银行流水", "查看"})
	public Reply queryBank() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println(param);
		context.put("param",param);
		context.put("yinhangliushui", new DataDictionaryMemcached().get("银行流水对象"));
		return new ReplyHtml(VM.html(pagePath+"bankwater/queryMgBankwater.vm", context));
	}
	
}

package com.pqsoft.refinanceFHFA.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.refinanceFHFA.service.RefinanceFHFAService;
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
import com.pqsoft.util.StringSplitse;

public class RefinanceFHFAAction extends Action {
	
	private final String pagePath="refinanceFHFA/";

	@Override
	public Reply execute() {
		return null;
	}

	/**************************************************合作金融机构页面***********************************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","列表显示"})
	public Reply toMgFhfa(){
		Map<String,Object> paramMap = _getParameters();//获取页面参数
		VelocityContext context = new VelocityContext();
		context.put("paramMap", paramMap);
		return new ReplyHtml(VM.html(pagePath+"toMgFhfa.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "合作金融机构","列表显示"})
	public Reply toMgDeductData(){
		Map<String, Object> param = _getParameters();
		RefinanceFHFAService service = new RefinanceFHFAService();
		return new ReplyAjaxPage(service.toMgFhfaData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","添加"})
	public Reply toAddFHFA(){
		VelocityContext context = new VelocityContext();
		context.put("loanWay", new DataDictionaryMemcached().get("再融资放款方式"));//放款方式
		context.put("loanType", new DataDictionaryMemcached().get("再融资放款类型"));//放款类型
		return new ReplyHtml(VM.html(pagePath+"toAddFHFA.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "合作金融机构","添加-操作"})
	public Reply doAddFHFA(){
		//获取页面参数
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		
		RefinanceFHFAService service = new RefinanceFHFAService();
		int i = 0;
		boolean flag = false;
		//添加融资机构
		i = service.addFHFA(paramMap);
		
		if(i>0){//若添加成功， 返回true
			flag = true;
		}else {//若添加失败， 返回false
			flag = false;
		}
		
		return new ReplyAjax(flag, null).addOp(new OpLog("合作金融机构","添加", paramMap.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "合作金融机构","删除"})
	public Reply doDeleteFHFA(){
		//获取页面参数
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		
		RefinanceFHFAService service = new RefinanceFHFAService();
		int i = 0;
		boolean flag = true;
		//删除融资机构
		i = service.doDeleteFHFA(paramMap);
		
		if(i>0){//若添加成功， 返回true
			flag = true;
		}else {//若添加失败， 返回false
			flag = false;
		}
		
		return new ReplyAjax(flag, null).addOp(new OpLog("合作金融机构","删除", paramMap.get("USERCODE").toString()));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","查看"})
	public Reply toSearchFhfa(){
		//页面阐述获取
		Map<String,Object> paramMap = _getParameters();
		
		VelocityContext context = new VelocityContext();
		
		RefinanceFHFAService service = new RefinanceFHFAService();
		Map<String,Object> mapu = service.toSearchFhfa(paramMap);//融资机构信息
		List<Map<String,Object>> account = (List<Map<String, Object>>) service.toSearchFhfaAccount(paramMap);//融资机构账户信息
		List<Object> fcType = new DataDictionaryMemcached().get("再融资放款方式");
		List<Object> fcType1 = new DataDictionaryMemcached().get("再融资放款类型");
		String[] pm = StringSplitse.strSplit(null==mapu.get("PAYMENT_METHOD")?"":mapu.get("PAYMENT_METHOD").toString(), ",");
		String[] pt = StringSplitse.strSplit(null==mapu.get("PAYMENT_TYPE")?"":mapu.get("PAYMENT_TYPE").toString(), ",");
		context.put("pmstr", pm);
		context.put("ptstr", pt);
		context.put("fkType", fcType);
		context.put("fkType1", fcType1);
		context.put("param", mapu);
		context.put("account", account);
		return new ReplyHtml(VM.html(pagePath+"toSearchFhfa.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","修改"})
	public Reply toUpdateFHFA(){
		Map<String,Object> paramMap = _getParameters();
		VelocityContext context = new VelocityContext();
		RefinanceFHFAService service = new RefinanceFHFAService();
		Map<String,Object> mapu = service.toSearchFhfa(paramMap);//融资机构信息
		List<Map<String,Object>> account = (List<Map<String, Object>>) service.toSearchFhfaAccount(paramMap);//融资机构账户信息
		List<Object> fcType = new DataDictionaryMemcached().get("再融资放款方式");
		List<Object> fcType1 = new DataDictionaryMemcached().get("再融资放款类型");
		System.out.println(mapu);
		String[] pm = StringSplitse.strSplit(null==mapu.get("PAYMENT_METHOD")?"":mapu.get("PAYMENT_METHOD").toString(), ",");
		String[] pt = StringSplitse.strSplit(null==mapu.get("PAYMENT_TYPE")?"":mapu.get("PAYMENT_TYPE").toString(), ",");
		context.put("pmstr", pm);
		context.put("ptstr", pt);
		context.put("fkType", fcType);
		context.put("fkType1", fcType1);
		context.put("param", mapu);
		context.put("account", account);
		return new ReplyHtml(VM.html(pagePath+"toUpdateFhfa.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "合作金融机构","修改"})
	public Reply doUpdateFHFA(){
		//获取页面参数
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		
		RefinanceFHFAService service = new RefinanceFHFAService();
		int i = 0;
		boolean flag = false;
		//添加融资机构
		i = service.updateFHFA(paramMap);
		
		if(i>0){//若修改成功， 返回true
			flag = true;
		}else {//若修改失败， 返回false
			flag = false;
		}
		
		return new ReplyAjax(flag, null).addOp(new OpLog("合作金融机构","修改", paramMap.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "合作金融机构","修改"})
	public Reply upDataFhfaBankStatus(){
		//获取页面参数
		Map<String,Object> paramMap = _getParameters();
		RefinanceFHFAService service = new RefinanceFHFAService();
		int i = 0;
		boolean flag = false;
		String msg = "";
		//修改融资机构银行账号
		i = service.upDataFhfaBankStatus(paramMap);
		
		if(i>0){//若修改成功， 返回true
			flag = true;
			msg = "银行账号作废成功";
		}else {//若修改失败， 返回false
			flag = false;
			msg = "银行账号作废失败";
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("合作金融机构","修改", msg));
	}
}

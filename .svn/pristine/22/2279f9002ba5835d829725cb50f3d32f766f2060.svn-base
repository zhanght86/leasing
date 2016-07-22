package com.pqsoft.RefinanceMethod.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import com.pqsoft.RefinanceMethod.service.RefinanceMethodService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class RefinanceMethodAction extends Action {

	private final String pagePath="refinanceMethod/";
	
	@Override
	public Reply execute() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资机构管理","添加融资机构"})
	public Reply addMethod(){
		Map<String,Object> map = _getParameters();
		RefinanceMethodService service = new RefinanceMethodService();
		VelocityContext context = new VelocityContext();
		
		//查询融资机构信息
		Map<String,Object> fhfa = service.findFinancial1(map);
		context.put("methodList", fhfa);
		
		//再融资费用基数
		context.put("mList", new DataDictionaryMemcached().get("再融资费用基数"));
		
		//再融资利率类型
		context.put("llType", new DataDictionaryMemcached().get("再融资利率类型"));
		
		//再融资放款方式
		context.put("fcType", new DataDictionaryMemcached().get("再融资放款方式"));
		
		//再融资放款类型
		context.put("fcType1", new DataDictionaryMemcached().get("再融资放款类型"));
		
		//再融资方式
		context.put("_rzMetod", new DataDictionaryMemcached().get("再融资方式"));
		
		//查询当前结构的一些编号及授信剩余金额
		
		map.put("RF_ID", map.get("ID"));
		List<Map<String,Object>> CugpCodeList=(List<Map<String,Object>>)service.queryCugpCodeList(map);
		context.put("CugpCodeList", CugpCodeList);
		
		//融资担保方式
		context.put("RZDB", new DataDictionaryMemcached().get("融资担保方式"));
		
		return new ReplyHtml(VM.html(pagePath+"addMethod.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资机构管理","添加融资机构"})
	public Reply addMethod1(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());//用户编号
		boolean flag = false;
		RefinanceMethodService service = new RefinanceMethodService();
		int i = service.addMethod(map);
		double GRANT_PRICE=Double.parseDouble(map.get("GRANT_PRICE")+"");
		double LAST_PRICE=Double.parseDouble(map.get("LAST_PRICE")+"");
		double SY=LAST_PRICE-GRANT_PRICE;
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("SY", SY);
		m.put("CUGP_CODE", map.get("CUGP_CODE"));
		System.out.println(m);
		//更新授信余额 king 2012-08-25
		if(i>0){
			i = service.updateGrantplan(m);
			if(i>0){
				flag=true;
			}else {
				flag=false;
			}
		}else{
			flag=false;
		}
		
		return new ReplyAjax(flag, null).addOp(new OpLog("融资机构管理","添加融资方式", map.get("USERCODE").toString()));
	}
	
	/***************************************************************************************************************
	 *                                                                                                             *
	 *                                              以下为查看 融资方式                                                                                                                          *
	 *                                                                                                             *
	 **************************************************************************************************************/
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资机构管理","融资方式","列表显示"})
	//融资方式查看类表
	public Reply methodList(){
		Map<String,Object> map = _getParameters();
		RefinanceMethodService service = new RefinanceMethodService();
		VelocityContext context = new VelocityContext();
		context.put("meList", service.findMethodList(map));
		return new ReplyHtml(VM.html(pagePath+"methodList.vm", context));
	}
	
	/**
	 * 查看融资方式
	 * selMethod
	 * @date 2013-10-14 下午08:53:42
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资机构管理","融资方式","查看"})
	//融资方式详细查看
	public Reply selMethod() 
	{
		Map<String,Object> map = _getParameters();
		RefinanceMethodService service = new RefinanceMethodService();
	    Map<String , Object> mapq=service.findMethodSel(map);
		VelocityContext context = new VelocityContext();
		List<Object> rzMethod = new DataDictionaryMemcached().get("再融资方式");//融资方式
		context.put("_rzMetod", rzMethod);
		String str=mapq.get("GUARANT_WAY")+"";
		String []st=str.split(",");
		List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < st.length; i++) {
			if(StringUtils.isNotEmpty(st[i])){
				Map<String,Object> ma=new HashMap<String,Object>();
				ma.put("GUARANT_WAY", st[i]);
				lst.add(ma);
			}
		}
		mapq.put("GUARANT_WAYList", lst);
		context.put("param", mapq);
		
		context.put("RZDB", new DataDictionaryMemcached().get("融资担保方式"));
		return new ReplyHtml(VM.html(pagePath+"getMethod.vm", context));
	}
	
	/**
	 * 修改融资方式-页面
	 * updateMethod
	 * @date 2013-10-14 下午07:35:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资机构管理","融资方式","融资方式修改"})
	//修改融资方式
	public Reply updateMethod(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		RefinanceMethodService service = new RefinanceMethodService();
		Map<String , Object> mapu=service.findMethodSel(map);
		context.put("param", mapu);		
		
		//再融资费用基数
		context.put("mparam", new DataDictionaryMemcached().get("再融资费用基数"));
		
		//再融资利率类型
		context.put("Type", new DataDictionaryMemcached().get("再融资利率类型"));
		
		//再融资放款方式
		context.put("fkType", new DataDictionaryMemcached().get("再融资放款方式"));
		
		//再融资放款类型
		context.put("fkType1", new DataDictionaryMemcached().get("再融资放款类型"));
		
		//再融资方式
		context.put("_rzMetod", new DataDictionaryMemcached().get("再融资方式"));
		
		List<Map<String,Object>> CugpCodeList=(List<Map<String,Object>>)service.queryCugpCodeList(mapu);
		context.put("CugpCodeList", CugpCodeList);
		
		List<Object> rzdbLst= new DataDictionaryMemcached().get("融资担保方式");
		
		String str=mapu.get("GUARANT_WAY")+"";
		List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
		
		mapu.put("GUARANT_WAYList", lst);
		for (Object object : rzdbLst) {
			Map<String,Object> m=(Map<String,Object>)object;
			if(str.contains(m.get("CODE")+"")){
				m.put("checked", "checked");
				lst.add(m);
			}else{
				m.put("checked", "");
				lst.add(m);
			}
		}
		context.put("RZDB", lst);
		return new ReplyHtml(VM.html(pagePath+"updateMethod.vm", context));
	}
	
	/**
	 * 融资方式修改操作
	 * updateMethod1
	 * @date 2013-10-14 下午08:48:28
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资机构管理","融资方式","融资方式修改"})
	//融资方式修改操作
	public Reply updateMethod1(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());//用户编号
		System.out.println(map);
		RefinanceMethodService service = new RefinanceMethodService();
		int i = service.UpdateMethod(map);
		double GRANT_PRICE=Double.parseDouble(map.get("GRANT_PRICE")+"");
		double LAST_PRICE=Double.parseDouble(map.get("LAST_PRICE")+"");
		double GPY=Double.parseDouble(map.get("GPY")+"");
		double SY=LAST_PRICE+GPY-GRANT_PRICE;
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("SY", SY);
		m.put("CUGP_CODE", map.get("CUGP_CODE"));
		boolean flag = false;
		if(i>0){
			flag = true;
		}else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("融资机构管理","更新融资方式", map.get("USERCODE").toString()));
	}
	
	/**
	 * 融资方式作废和启动
	 * updateCancel
	 * @date 2013-10-14 下午08:53:21
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资机构管理","融资方式","启动/作废"})
	//融资方式作废和启动
	public Reply updateCancel(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());//用户编号
		RefinanceMethodService service = new RefinanceMethodService();
		int i = service.updateCancel(map);
		boolean flag = false;
		if(i>0){
			flag = true;
		}else{
			flag = false;
		}
		
		return new ReplyAjax(flag, null).addOp(new OpLog("融资机构管理","取消/启动融资方式", map.get("USERCODE").toString()));
	}
	
	/***************************************************************************************************************
	 *                                                                                                             *
	 *                                              以上为查看 融资方式                                                                                                                          *
	 *                                                                                                             *
	 **************************************************************************************************************/
}

package com.pqsoft.credit.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.credit.service.CreditCustomerService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

public class CreditCustomerAction extends Action{

	private Map<String,Object> m = null;
	private final String pagePath = "credit/customer/";
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * getPageParameter 获取页面参数
	 * @date 2014-3-28 上午08:25:35
	 * @author 吴国伟
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.ALL)
	private Map<String,Object> getPageParameter(){
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();			
			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME",Security.getUser().getName());
		m.put("USERCODE",Security.getUser().getCode());
		m.put("USERID",Security.getUser().getId());
		return m;
	} 
	
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name={"资信平台管理","资信客户管理","修改详细信息","修改"})
	public Reply toUpdateCustInfo1(){
		Map<String,Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		CreditCustomerService service = new CreditCustomerService();//资信客户管理-主要是对主表进行管理
		CustomersService custservice = new CustomersService();//客户管理services
		Map<String,Object> cus = (Map<String, Object>) service.findCustDetail(param);
		context.put("custInfo", cus);//客户详细信息
		param.put("TYPE", cus.get("TYPE"));
		Object obj = custservice.findCustLinkInfo(param);
		context.put("custLinkInfo", obj);//客户关联信息
		context.put("xqah", custservice.getANNEX(param,"XQAH","兴趣爱好"));//兴趣爱好
		context.put("xg", custservice.getANNEX(param,"XG","性格"));//性格
		context.put("type", custservice.findCustDataType());//客户关联信息
		context.put("getProvince",custservice.getProvince());//省
		if(cus.containsKey("PROVINCE")&&!StringUtils.isBlank(cus.get("PROVINCE"))){
		param.put("PARENT_ID", cus.get("PROVINCE"));
		context.put("city", custservice.getCity(param));
		}
			if(cus.get("TYPE") != null) {
				if(cus.get("TYPE").toString().equals("NP")){//进入自然人页面
					return new ReplyHtml(VM.html(pagePath+"toUpdateCustNatu.vm", context));
				}else {//进入法人页面
					context.put("rc_unit", new DataDictionaryMemcached().get("货币种类"));
					return new ReplyHtml(VM.html(pagePath+"toUpdateCustLegal.vm", context));
				}
			}
		return null;//默认进入自然人页面
	}
	
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name={"资信平台管理","资信客户管理","查看详细信息","查看"})
	public Reply toViewCustInfo(){
		Map<String,Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		CreditCustomerService service = new CreditCustomerService();//资信客户管理-主要是对主表进行管理
		CustomersService custservice = new CustomersService();//客户管理services
		Map<String,Object> cus = (Map<String, Object>) service.findCustDetail(param);
		context.put("custInfo", cus);//客户详细信息
		context.put("custLinkInfo", custservice.findCustLinkInfo(param));//客户关联信息
		context.put("xqah", custservice.getANNEX(param,"XQAH","兴趣爱好"));//兴趣爱好
		context.put("xg", custservice.getANNEX(param,"XG","性格"));//性格
		context.put("type", custservice.findCustDataType());//客户关联信息
		context.put("getProvince",custservice.getProvince());//省
		if(cus.containsKey("PROVINCE")&&!StringUtils.isBlank(cus.get("PROVINCE"))){
		param.put("PARENT_ID", cus.get("PROVINCE"));
		context.put("city", custservice.getCity(param));
		}
			if(cus.get("TYPE") != null) {
				if(cus.get("TYPE").toString().equals("NP")){//进入自然人页面
					return new ReplyHtml(VM.html(pagePath+"toViewCustNatu.vm", context));
				}else {//进入法人页面
					context.put("rc_unit", new DataDictionaryMemcached().get("货币种类"));
					return new ReplyHtml(VM.html(pagePath+"toViewCustLegal.vm", context));
				}
			}
		return null;//默认进入自然人页面
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toUpdateCustInfo() {
		Map<String,Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		CreditCustomerService service = new CreditCustomerService();//资信客户管理-主要是对主表进行管理
		CustomersService custservice = new CustomersService();//客户管理services
		JSONArray jsonArray = JSONArray.fromObject(param.get("param"));
		boolean flag = false;
		String msg=null;
		for (Object object : jsonArray) {
			Map<String, Object> m = (Map<String, Object>) object;
			m.put("CLIENT_ID", param.get("CLIENT_ID"));
			m.put("USERCODE", param.get("USERCODE"));
			m.put("USERNAME", param.get("USERNAME"));	
//			if (m.get("TYPE").toString().equals("NP")) {
//			}else{
//				//法人
//				int i=custservice.checkBankZC(m.get("CLIENT_ID"));
//				if(i==0){
//					throw new ActionException("请添加注册银行信息");
//				}
//			}
			 service.doUpdateCust(m);
			
			//同步到客户表中
			int i = service.updateNewCust(m);
			if (i > 0) {
				flag = true;
				if (m.get("TYPE").toString().equals("NP")) {
					if ("1Marriage".equals(m.get("IS_MARRY").toString())
							&& m.get("IS_MARRY") != null) {
						Map<String,Object> spoust = (Map<String, Object>) custservice.getSpoust(m);//判断配偶是否存在
						if(spoust == null){//如果配偶不存在，则添加
							custservice.doAddCustSpoust(m);
						}else {//如果存在， 则修改
							custservice.doUpdateSpoust(m);
						}
					}
					//删除并插入兴趣爱好和个性
					custservice.delANNEX(m);
					custservice.insertANNEX(m, param.get("CLIENT_ID").toString());
				}
				msg="修改成功!";
			} else {
				msg="修改失败!";
				flag = false;
			}
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("资信资料管理","修改信息",param.get("USERID").toString()));
	}

	
	
	
}

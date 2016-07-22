package com.pqsoft.credit.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.credit.service.CreditGuaraServices;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class CreditGuaraAction extends Action{

	private Map<String,Object> m = null;
	private final String pagePath = "credit/";
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
	@aPermission(name = {"资信平台管理", "资信担保人管理", "担保人添加" })
	public Reply toGuarantorPage(){
		this.getPageParameter();
		VelocityContext context=new VelocityContext();
		CreditGuaraServices service = new CreditGuaraServices();
		m.put("CREDIT_ID", m.get("PROJECT_ID"));
		context.put("param", m);
		context.put("dbrxx", service.getGurarntorData(m));
		context.put("zjlx", service.getSysDicData("证件类型"));
		context.put("whcd", service.getSysDicData("文化程度"));
		context.put("hyzk", service.getSysDicData("婚姻状况"));
		context.put("gsxz", service.getSysDicData("公司性质"));
		context.put("dbfs", service.getSysDicData("担保方式"));
		context.put("hylx", service.getSysDicData("行业类型"));
		return new ReplyHtml(VM.html(pagePath+"Credit_guarantor.vm",context));
	}
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资信平台管理", "资信担保人管理", "担保人查看" })
	public Reply toGuarantorViewPage(){
		this.getPageParameter();
		VelocityContext context=new VelocityContext();
		CreditGuaraServices service = new CreditGuaraServices();
		m.put("CREDIT_ID", m.get("PROJECT_ID"));
		//m.put("CREDIT_ID", "2317");
		//String DBR_ID = null!=m.get("DBR_ID")?m.get("DBR_ID").toString():"NO";
		context.put("param", m);
//		if(!"NO".equals(DBR_ID)){
//			System.out.println("单个担保人信息！！！！！！！！！！！！1");
//			context.put("dbrContextInfo",service.getGurarntorDataOne(m));
//			context.put("dbrContextDbr",service.getGurarntorLxr(m,"1"));//法定代表人
//			context.put("dbrContextLxr",service.getGurarntorLxr(m,"3"));//联系人
//			context.put("dbrContextPo",service.getGurarntorLxr(m,"4"));//配偶
//		}
		context.put("dbrxx", service.getGurarntorData(m));
		context.put("zjlx", service.getSysDicData("证件类型"));
		context.put("whcd", service.getSysDicData("文化程度"));
		context.put("hyzk", service.getSysDicData("婚姻状况"));
		context.put("dbfs", service.getSysDicData("担保方式"));
		context.put("hylx", service.getSysDicData("行业类型"));
		return new ReplyHtml(VM.html(pagePath+"Credit_guarantor_view.vm",context));
	}
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资信平台管理", "资信担保人管理", "担保人查看" })
	public Reply toGuarantorViewInfo(){
		this.getPageParameter();
		VelocityContext context=new VelocityContext();
		CreditGuaraServices service = new CreditGuaraServices();
		context.put("param", m);
		//if(!"NO".equals(DBR_ID)){
			System.out.println("单个担保人信息！！！！！！！！！！！！1");
			context.put("dbrContextInfo",service.getGurarntorDataOne(m));
			context.put("dbrContextDbr",service.getGurarntorLxr(m,"1"));//法定代表人
			context.put("dbrContextLxr",service.getGurarntorLxr(m,"3"));//联系人
			context.put("dbrContextPo",service.getGurarntorLxr(m,"4"));//配偶
		//}
		context.put("zjlx", service.getSysDicData("证件类型"));
		context.put("whcd", service.getSysDicData("文化程度"));
		context.put("hyzk", service.getSysDicData("婚姻状况"));
		context.put("gsxz", service.getSysDicData("公司性质"));
		context.put("dbfs", service.getSysDicData("担保方式"));
		context.put("hylx", service.getSysDicData("行业类型"));
		return new ReplyHtml(VM.html(pagePath+"Credit_guarantor_viewinfo.vm",context));
	}
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资信平台管理", "资信担保人管理", "担保人修改" })
	public Reply toGuarantorUpdatePage(){
		this.getPageParameter();
		VelocityContext context=new VelocityContext();
		CreditGuaraServices service = new CreditGuaraServices();
		context.put("param", m);
		//if(!"NO".equals(DBR_ID)){
			System.out.println("单个担保人信息！！！！！！！！！！！！1");
			context.put("dbrContextInfo",service.getGurarntorDataOne(m));
			context.put("dbrContextDbr",service.getGurarntorLxr(m,"1"));//法定代表人
			context.put("dbrContextLxr",service.getGurarntorLxr(m,"3"));//联系人
			context.put("dbrContextPo",service.getGurarntorLxr(m,"4"));//配偶
		//}
		context.put("zjlx", service.getSysDicData("证件类型"));
		context.put("whcd", service.getSysDicData("文化程度"));
		context.put("hyzk", service.getSysDicData("婚姻状况"));
		context.put("gsxz", service.getSysDicData("公司性质"));
		context.put("dbfs", service.getSysDicData("担保方式"));
		context.put("hylx", service.getSysDicData("行业类型"));
		return new ReplyHtml(VM.html(pagePath+"Credit_guarantor_update.vm",context));
	}
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资信平台管理", "资信担保人管理", "添加","保存"})
	public Reply doSaveGuarantor(){
		this.getPageParameter();
		CreditGuaraServices service = new CreditGuaraServices();
		String ID = Dao.getSequence("SEQ_CREDIT_GUARANTOR_INFO");
		String typeid = m.get("SHIBIE").toString();
		//m.put("CREDIT_ID", "2317");
		m.put("ID", ID);
		boolean flag = false;
		String msg = "";
		int a = service.doSaveGuarantor(m);//担保人主表
		if(a>0){
			if("1".equals(typeid)){//法人担保人
				System.out.println("法人法人法人法人法人法人法人");
				service.doGuarantorLxr(m);
				service.doGuarantorLxr_2(m);
				flag = true;
				msg = ID;
			}else if("4".equals(typeid)){//自然人担保人
				System.out.println("自然人自然人自然人自然人自然人自然人");
				service.doGuarantorLxr_4(m);
				flag = true;
				msg = ID;
			}
		}
		
		return new ReplyHtml("<script> alert('添加成功');" +
				"window.location='Credit!updateCreditPage.action?GUTYPE=担保信息&PROJECT_ID="+m.get("CREDIT_ID")+"'; </script>  ");
	}
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资信平台管理", "资信担保人管理", "修改","保存"})
	public Reply doUpdateGuarantor(){
		this.getPageParameter();
		CreditGuaraServices service = new CreditGuaraServices();
		String typeid = m.get("SHIBIE").toString();
		boolean flag = false;
		String msg = "";
		System.out.println("====================================");
		System.out.println(m.toString());
		int a = service.doUpdateGuarantor(m);//担保人主表
		if(a>0){
			if("1".equals(typeid)){//法人担保人
				System.out.println("更新更新法人法人法人法人法人法人法人");
				service.updateGuarantorLxr(m);
				service.updateGuarantorLxr_2(m);
				flag = true;
			}else if("4".equals(typeid)){//自然人担保人
				System.out.println("更新更新自然人自然人自然人自然人自然人自然人");
				service.updateGuarantorLxr_4(m);
				flag = true;
			}
		}
		
		return new ReplyHtml("<script> alert('修改成功'); </script>  ");
	}

}

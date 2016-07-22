package com.pqsoft.project_withdrawn.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.project_withdrawn.service.project_withdrawnService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
public class project_withdrawnAction  extends Action{
	public VelocityContext context = new VelocityContext();
	project_withdrawnService service = new project_withdrawnService();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

//	@aPermission(name = { "项目管理", "项目作废", "列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply whithDrawn_Msg() {
//		Map map = this._getParameters();
//		ComplementService Compservice = new ComplementService();
//		context.put("toGetProduct", Compservice.toGetProduct(map));// 获取租赁物类型
//		
//		//获取支付表的状态位
//		List<Object> list = (List) new SysDictionaryMemcached().get("项目状态位");
//		context.put("payPlanList",list);// 获取租赁物类型
//		context.put("param", map);
//		return new ReplyHtml(VM.html("project_withdrawn/project_withdrawn_MAG.vm", context)).addOp(new OpLog("项目管理", "项目作废-作废申请", "列表查询错误"));
//	}

//	@aPermission(name = { "项目管理", "项目作废", "列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply whithDrawn_Msg_AJAX() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.query_project_whithDrawn_MG(param);
//		return new ReplyAjaxPage(page);
//	}
	
//	@aPermission(name = { "项目管理", "项目作废", "申请" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply whithDrawn_ZuoFei() {
//		Map<String, Object> param = _getParameters();
//		int result=service.whithDrawn_ZuoFei(param);
//		boolean flag=false;
//		if(result>0){
//			flag=true;
//		}
//		return new ReplyAjax(flag).addOp(new OpLog("项目管理", "项目作废-作废申请", "项目直接作废错误"));
//	}
	
//	@aPermission(name = { "项目管理", "作废管理", "申请" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply create_FORM(){
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> m = _getParameters();
//		context.put("param", m);
//		
//		Map BaseInfo=new HashMap();
//		List list=new ArrayList();
//		//查询状态
//		String STATUS=m.get("STATUS").toString();
//		if(STATUS.equals("2")){//已付首期款
//			//退款
//			list=service.queryScheme_fee_List(m);
//		}
//		else if(STATUS.equals("3")){//已放款
//			//收款
//			BaseInfo=service.payMentStatus3(m);
//			//退款
//			list=service.queryScheme_fee_List(m);
//		}
//		else if(STATUS.equals("4")){//已还租金
//			//收款
//			BaseInfo=service.payMentStatus4(m);
//			//退款(只退DB保证金)
//			list=service.queryScheme_fee_List1(m);
//		}
//		context.put("BaseInfo", BaseInfo);
//		context.put("fee_List", list);
//		return new ReplyHtml(VM.html("project_withdrawn/create_FORM.vm", context));
//	}
	
//	@aPermission(name = { "项目管理", "项目作废", "申请", "发起流程" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply whithDrawn_JBPM() {
//		Map<String, Object> param = _getParameters();
//		service.whithDrawn_ING_METHOD(param);
//		System.out.println("--------------------param="+param);
//		//发起流程---------------------------------------------------------------
//		List<String> list=JBPM.getDeploymentListByModelName("project_withdrawn");
//		JBPM.startProcessInstanceById(list.get(0), param.get("PROJECT_ID")+"", param.get("CLIENT_ID")+"", "", param);
//		
//		return new ReplyAjax(true).addOp(new OpLog("项目管理", "项目作废-作废申请", "申请流程发起错误"));
//	}
	
//	@aPermission(name = { "项目管理", "项目作废", "查看" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply whithDrawn_view()
//	{
//		Map<String, Object> param = _getParameters();
//		
//		Map BaseInfo=new HashMap();
//		List list=new ArrayList();
//		//通过项目ID查询出主表信息
//		BaseInfo=service.queruHead_BaseInfo(param);
//		//通过主表信息查询出子表信息
//		param.put("HEAD_ID", BaseInfo.get("ID"));
//		list=service.queruDetalInfo(param);
//		context.put("param", param);
//		context.put("BaseInfo", BaseInfo);
//		context.put("fee_List", list);
//		return new ReplyHtml(VM.html("project_withdrawn/create_form_view.vm", context));
//	}
	

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply whithDrawn_view_JBPM()
	{
		Map<String, Object> param = _getParameters();
		
		Map BaseInfo=new HashMap();
		List list=new ArrayList();
		//通过项目ID查询出主表信息
		BaseInfo=service.queruHead_BaseInfo(param);
		//通过主表信息查询出子表信息
		param.put("HEAD_ID", BaseInfo.get("ID"));
		list=service.queruDetalInfo(param);
		context.put("param", param);
		context.put("BaseInfo", BaseInfo);
		context.put("fee_List", list);
		return new ReplyHtml(VM.html("project_withdrawn/create_form_view.vm", context));
	}
	

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply fi_view_JBPM()
	{
		Map<String, Object> param = _getParameters();
		
		Map BaseInfo=new HashMap();
		List list=new ArrayList();
		//通过项目ID查询出主表信息
		BaseInfo=service.queruHead_BaseInfo(param);
		//通过主表信息查询出子表信息
		param.put("HEAD_ID", BaseInfo.get("ID"));
		list=service.queruDetalInfo(param);
		context.put("param", param);
		context.put("BaseInfo", BaseInfo);
		context.put("fee_List", list);
		return new ReplyHtml(VM.html("project_withdrawn/fi_form_view.vm", context));
	}
}

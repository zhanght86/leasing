package com.pqsoft.documentApp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.documentApp.service.APPDossierConfigService;
import com.pqsoft.documentApp.service.ApplyDossierService;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.StringUtils;

/**
 * 权证接口-档案归档管理
 * @author yangxue
 * @date 2015-05-29 
 */
public class ApplyDossierAction extends Action {
	
	private final String pagePath = "documentApp/";
	private ApplyDossierService service = new ApplyDossierService();

	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "列表显示"})
	public Reply execute() {
		Map<String,Object> param = _getParameters();//获取页面参数	
		VelocityContext context = new VelocityContext();
		//数据字典查询项目业务类型
		context.put("YEWU",  new SysDictionaryMemcached().get("业务类型"));
		context.put("PAYMENT_STATUS",  new SysDictionaryMemcached().get("放款状态"));
		context.put("AREALIST",  service.toGetDossiersArealist(param));
		//数据字典查询项目所属事业部
		context.put("SYB",  new DataDictionaryMemcached().get("行业类型-打分"));
		context.put("FULELIST",  new DataDictionaryMemcached().get("归档状态"));
		return new ReplyHtml(VM.html(pagePath + "toMgDossierApp.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "列表显示"})
	public Reply toMgDossierApp(){
		Map<String,Object> param = _getParameters();//获取页面参数		
		JSONObject json = JSONObject.fromObject(param.get("param"));
		param.remove("param");
		param.putAll(json);
		//查询列表数据
		return new ReplyAjaxPage(service.toMgDossierApp(param));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "列表显示"})
	public Reply toShowDossierApp(){		
		Map<String,Object> param = _getParameters();//获取页面参数	
		VelocityContext context = new VelocityContext();
		//根据承租人, 合同号, 档案柜编号, 档案袋编号查看归档明细信息
		System.out.println("===========param======="+param);
		List<Map<String,Object>> listMap = service.toShowDossierApp(param);
		//context.put("material", service.toGetDocumentations(param));
		return new ReplyAjax(listMap);
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "申请归档"})
	public Reply toAddpDossierApp(){//单个项目申请归档		
		Map<String,Object> param = _getParameters();//获取页面参数
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		System.out.println("param:"+param);
		//数据字典查询-查看邮递公司
		context.put("USERNAME", Security.getUser().getName());
		context.put("youji", new DataDictionaryMemcached().get("邮递公司"));
		context.put("material", service.toGetDocumentations1(param));
		context.put("material1", service.toGetDocumentations1(param));
		context.put("equ", service.toGetEquCount(param));
		List<Map<String,Object>> list=service.toGetPaylist(param);
		context.put("payList", list);//获取合同下支付表编号
		context.put("payCum", list.size());//获取合同下支付表编号
		return new ReplyHtml(VM.html(pagePath + "apply/toAddpDossierApp.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "申请归档"})
	public Reply doAddpDossierApp(){//单个项目申请归档-操作
		Map<String,Object> param = _getParameters();//获取页面参数
		JSONObject json = JSONObject.fromObject(param.get("datas"));
		String id = service.doAddAppDossiors(param);
		boolean flag = false;
		String msg = "";
		String nextCode=""; //下一节点审批人
		//以下为发起档案归档审批流程
		if(!"".equals(id)){
			List<String> prcessList = JBPM.getDeploymentListByModelName("档案归档审批流程");
//			Map<String, Object> jos = new HashMap<String, Object>();
//			jos.put("MODULE", "档案归档审批流程");
//			jos.put("PDKEY", param.get("INDUSTRY_TYPE"));
//			List<Map<String,Object>> prcessList = JBPM.getDeploymentListByJos(jos);
		//	List<String> prcessList = JBPM.getDeploymentListByJos("档案归档审批流程", param.get("INDUSTRY_TYPE").toString());						
			if(prcessList.size() > 0){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("DOSSIER_APPLY_ID", id);
				String jbpm_id =JBPM.startProcessInstanceById(prcessList.get(0), "", "",  "", map).getId();	
				msg += jbpm_id+"已发起！";//是否包含重卡
				nextCode = new TaskService().getAssignee(jbpm_id);
				flag = true;
			}
		}else{
			msg = "未找到审批流程";
		}
		return  new ReplyAjax(flag, nextCode, msg).addOp(new OpLog("权证管理-档案管理","申请归档", msg));
	}
	
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "申请归档-批量"})
	public Reply toAddpDossierAppLot(){
		Map<String,Object> param = _getParameters();//获取页面参数
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		JSONObject json = JSONObject.fromObject(param.get("dataList"));
		List<Map<String,Object>>  proList = json.getJSONArray("dataList");
		System.out.println("proList:"+proList);
		context.put("proList", proList);
//		context.put("materiaList", materiaList);
		context.put("USERNAME", Security.getUser().getName());
		context.put("youji", new DataDictionaryMemcached().get("邮递公司"));
		return new ReplyHtml(VM.html(pagePath + "apply/toAddpDossierAppLot.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	//批量申请归档-获取资料清单
	public Reply toGetDossierMateril(){
		Map<String,Object> param = _getParameters();//获取页面参数
		return new ReplyAjaxPage(service.toGetApplyDocumentations(param));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	//根据合同编号获取支付表编号
	public Reply toGetPaylistByLeaseCode(){
		Map<String,Object> param = _getParameters();//获取页面参数
		return new ReplyAjax(service.toGetPaylistByLeaseCode(param));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "归档申请查看"})
	public Reply toShowAppDossier(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("youji", new DataDictionaryMemcached().get("邮递公司"));
		context.put("dossierApp", service.toGetDossiersApp(param));//查看申请单
		context.put("rent", service.toGetDossierRent(param));//获取客户与合同信息
		context.put("detail", service.toGetDossierDetial(param));//查看申请单资料详细
		return new ReplyHtml(VM.html(pagePath + "apply/toShowAppDossier.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案入柜", "选择档案柜" })
	public Reply doShowCabRowLine() {
		Map<String, Object> param = _getParameters();
		param = new APPDossierConfigService().doShowDossierConfigList(param).get(0);
		return new ReplyAjax(param);
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "档案入柜"})
	public Reply toUpdateDossierCabinet(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		context.put("youji", new DataDictionaryMemcached().get("邮递公司"));	
	    context.put("reason", new DataDictionaryMemcached().get("退回原因"));
	    System.out.println("====param========"+param);
	    
	    //查询本次申请的档案柜编号
	    List<Map<String, Object>> portfoliMap=service.toGetDossiersParm(param);
	    Map<String,Object> numMap=new HashMap<String,Object>();
	    for(Map<String, Object> tmap:portfoliMap){
	    	numMap.put("LEASE_CODE", tmap.get("LEASE_CODE"));
	    	numMap.put("CLIENT_NAME", tmap.get("CLIENT_NAME"));
	    }
	    //查询这个合同第一次入柜的档案柜编号
	    List<Map<String, Object>> portfoliMaps=service.toGetDossiersNumber(numMap);
	    
		context.put("dossierApp",service.toGetDossiersApp(param));//查看申请单
		context.put("rent",service.toGetDossierRent(param));//获取客户与合同信息		
		context.put("detail",service.toGetDossierDetial(param));//获取客户与合同信息	1	
		context.put("metail",service.toGetDossierDetial(param));//获取客户与合同信息	2	
		List<Map<String,Object>> payList=service.toGetDossierPaylist(param);
		context.put("payList",payList);//支付表编号	
		context.put("paylength",payList.size());//支付表编号	
		List<Map<String, Object>> port= new APPDossierConfigService().doShowDossierConfigList(new HashMap());
		if(portfoliMaps.size() > 0&&portfoliMaps!=null){
			Map<String, Object> portfoli=portfoliMaps.get(0);
		if (portfoliMaps == null ||  portfoliMaps.size() < 1 || portfoliMaps.isEmpty()) {
			if (StringUtils.isBlank(param.get("PROJECT_CODE")))
				param.put("PORTFOLIO_NUMBER", "CZR-" + DateUtil.getSysDate());
			else
				param.put("PORTFOLIO_NUMBER", "HT-" + param.get("PROJECT_CODE"));
		} else {
			param.put("PORTFOLIO_NUMBER", portfoli.get("PORTFOLIO_NUMBER"));
			param.put("CABINET_NUMBER", portfoli.get("CABINET_NUMBER"));
			if (!StringUtils.isBlank(portfoli.containsKey("CABINET_NUMBER") ? portfoli.get("CABINET_NUMBER") : "")) {
				String CABINET_NUMBERS[] = portfoli.get("CABINET_NUMBER").toString().split("-");
				param.put("HEAD", CABINET_NUMBERS[0]);
				param.put("ROW", CABINET_NUMBERS[1]);
				param.put("LINE", CABINET_NUMBERS[2]);
			} else {
				param.put("ROW", "");
				param.put("LINE", "");
			}
		}
		}
	
		context.put("portfoliMap", portfoliMap);
		context.put("CABINETLIST",port);
		context.put("param",param);
		context.put("date", sdf.format(new Date()));
		context.put("DAI", "RZZL"+sdf.format(new Date())+"001");
		return new ReplyHtml(VM.html(pagePath + "apply/toUpdateDossierCabinet.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "资料入柜"})
	public Reply doUpdateDossierFile(){
		Map<String,Object> param = _getParameters();
		int t = service.doUpdateDossierFile(param);
		boolean flag = false;
		String msg = "";
		if(t>0){
			flag = true;
			msg = "资料入柜成功";
		}else {
			msg = "资料入柜失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("权证管理-档案管理","资料入柜", msg));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "档案入柜"})
	public Reply doUpdateApply(){
		Map<String,Object> param = _getParameters();
		System.out.println(param);
		int i = service.doUpdateApply(param);
		int t = service.doUpdateDossierFile(param);
		boolean flag = false;
		String msg = "";
		if(t>0){
			if(i>0){
				flag = true;
				msg = "档案入柜成功";
			}else {
				msg = "档案入柜失败";
			}
			flag = true;
			msg = "资料入柜成功";
		}else {
			msg = "资料入柜失败";
		}
	
		return new ReplyAjax(flag,msg).addOp(new OpLog("权证管理-档案管理","档案入柜", msg));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "档案资料退回"})
	public Reply doUpdateReturnMaterial(){
		Map<String,Object> param = _getParameters();
		int i = service.doUpdateReturnMaterial(param);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "档案资料退回成功";
		}else {
			msg = "档案资料退回失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("权证管理-档案管理","档案资料退回", msg));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "主页", "流程表单", "商助"})
	public Reply toAddDossierData(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Map<String,Object>> list=service.findAllDossierData(param);
		List<Map<String,Object>> payList=service.lookPaycodeDossierlist(param);
		context.put("payList", payList);//获取合同下支付表编号
		context.put("list", list);
		context.put("param",  param );
		return new ReplyHtml(VM.html(pagePath + "apply/toAllDossierData.vm", context));
		
	}
	
}

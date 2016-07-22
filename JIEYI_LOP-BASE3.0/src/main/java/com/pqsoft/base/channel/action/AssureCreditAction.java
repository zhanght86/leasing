package com.pqsoft.base.channel.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.eclipse.jetty.util.StringUtil;

import com.pqsoft.base.channel.service.AssureCreditService;
import com.pqsoft.base.channel.service.ChannelService;
import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.MemoService;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

public class AssureCreditAction extends Action {
    private String path ="base/channel/";
    private AssureCreditService service = new AssureCreditService();
	@Override
	public Reply execute() {
		return null;
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","担保额度申请","申请单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showAssureCreditPage(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		
//		ManageService mgService = new ManageService();
//		Map<String,Object> supMess = mgService.getSupByUserId(Security.getUser().getId());
//		if(supMess != null && supMess.get("SUP_ID") != null){
//			param.put("SUP_ID", supMess.get("SUP_ID").toString());
//		    //查询供应商授信记录
//			Map<String,Object> oneSupCredit = service.getOneSupCredit(param);
//			context.put("creditMess", oneSupCredit);
//		} 
		//获取放大倍数
		Map<String,Object> baseMess = new HashMap<String, Object>();
		baseMess.put("MULTIPLE", 2);
		context.put("baseMess", baseMess);
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"assureApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","担保额度申请","审批单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getAssureApplyDan(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		Map<String,Object> applyDanMess = service.getOneApplyDan(param);
		ChannelService channelService = new ChannelService();
		List<Object> applyBook = channelService.findSupFileUploads(StringUtils.nullToString(param.get("SUP_ID")),"申请书",StringUtils.nullToString(param.get("APPLY_ID")),StringUtils.nullToString(param.get("APPLY_NAME")));
		List<Object> assets = channelService.findSupFileUploads(StringUtils.nullToString(param.get("SUP_ID")),"不动产、有价证券",StringUtils.nullToString(param.get("APPLY_ID")),StringUtils.nullToString(param.get("APPLY_NAME")));
		List<Object> OtherData = channelService.findSupFileUploads(StringUtils.nullToString(param.get("SUP_ID")),"其他资料",StringUtils.nullToString(param.get("APPLY_ID")),StringUtils.nullToString(param.get("APPLY_NAME")));
		List<Object> file_Types = (List<Object>)new DataDictionaryMemcached().get("额度申请附件类型");
		List<Object> listData = channelService.getCreditFileUploads(StringUtils.nullToString(param.get("SUP_ID")), file_Types, StringUtils.nullToString(param.get("APPLY_ID")));
		context.put("applyBook", applyBook);
		context.put("assets", assets);
		context.put("OtherData", OtherData);
		context.put("FundFiles", listData);
		context.put("param", param);
		context.put("applyDanMess", applyDanMess);
		return new ReplyHtml(VM.html(path+"assureApplyDetail.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","担保额度申请","审批单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply assureApprovlPageMain(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"assureApprovalMain.vm", context));
	}
 
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","担保额度申请","审批单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showHistoryReport(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> historyReport = service.getAmountApplyDan(param);
		Map<String,Object> reportOne = (Map<String,Object>)historyReport.get(0);
		ChannelService channelService = new ChannelService();
		List<Object> applyBook = channelService.findSupFileUploads(StringUtils.nullToString(reportOne.get("SUP_ID")),"申请书",StringUtils.nullToString(reportOne.get("ID")),StringUtils.nullToString(reportOne.get("APPLY_NAME")));
		List<Object> assets = channelService.findSupFileUploads(StringUtils.nullToString(reportOne.get("SUP_ID")),"不动产、有价证券",StringUtils.nullToString(reportOne.get("ID")),StringUtils.nullToString(reportOne.get("APPLY_NAME")));
		List<Object> OtherData = channelService.findSupFileUploads(StringUtils.nullToString(reportOne.get("SUP_ID")),"其他资料",StringUtils.nullToString(reportOne.get("ID")),StringUtils.nullToString(reportOne.get("APPLY_NAME")));
		context.put("applyBook", applyBook);
		context.put("assets", assets);
		context.put("OtherData", OtherData);
		context.put("param", param);
		List<Map<String, Object>> listSuggest =  new MemoService().getMemos(new TaskService().getShortName(StringUtils.nullToString(reportOne.get("JBPMID"))));
		context.put("param", param);
		context.put("LASTONE_REPORT", reportOne);
		context.put("REPORT_IDEA", listSuggest);
		return new ReplyHtml(VM.html(path+"assureAppReportPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","担保额度申请","审批单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showSupBaseMethod(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ChannelService chService = new ChannelService();
		Map<String,Object> oneSup = chService.getOneSupplier(param);
		Map<String,Object> supCreditMess = service.getOneSupCredit(param);
		if(param.containsKey("APPLY_ID") && param.get("APPLY_ID") !=null && !"".equals(param.get("APPLY_ID").toString())){
			Map<String,Object> applyDanMess = service.getOneApplyDan(param);
			param.put("CURR_DATE", applyDanMess.get("APPLY_TIME").toString());
		}else{
		    param.put("CURR_DATE", sdf.format(new Date()));
		}
		ChannelService channelService = new ChannelService();
		channelService.getOverdueInfos(context,param);
		context.put("creditMess", supCreditMess);
		context.put("supplier", oneSup);
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"supBaseMessPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","担保额度申请","审批单"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showSecondUploadPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		Map<String,Object> applyDanMess = service.getOneApplyDan(param);
		ChannelService channelService = new ChannelService();
		List<Object> applyBook = channelService.findSupFileUploads(StringUtils.nullToString(param.get("SUP_ID")),"申请书",StringUtils.nullToString(param.get("APPLY_ID")),StringUtils.nullToString(param.get("APPLY_NAME")));
		List<Object> assets = channelService.findSupFileUploads(StringUtils.nullToString(param.get("SUP_ID")),"不动产、有价证券",StringUtils.nullToString(param.get("APPLY_ID")),StringUtils.nullToString(param.get("APPLY_NAME")));
		List<Object> OtherData = channelService.findSupFileUploads(StringUtils.nullToString(param.get("SUP_ID")),"其他资料",StringUtils.nullToString(param.get("APPLY_ID")),StringUtils.nullToString(param.get("APPLY_NAME")));
		List<Object> file_types = (List<Object>)new DataDictionaryMemcached().get("额度申请附件类型");
		List<Object> listData = channelService.getCreditFileUploads(StringUtils.nullToString(param.get("SUP_ID")), file_types, StringUtils.nullToString(param.get("APPLY_ID")));
		context.put("files", listData);
		context.put("fileType_Items", file_types);
		context.put("applyBook", applyBook);
		context.put("assets", assets);
		context.put("OtherData", OtherData);
		context.put("param", param);
		context.put("applyDanMess", applyDanMess);
		return new ReplyHtml(VM.html(path+"secondUploadData.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","担保额度申请","审批单(初审)"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply assureTrialMain(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"assureTrialMain.vm", context)); 
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"渠道管理","担保额度申请","审批单(初审)"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getAssureEditDan(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		Map<String,Object> applyDanMess = service.getOneApplyDan(param);
		ChannelService channelService = new ChannelService();
		List<Object> applyBook = channelService.findSupFileUploads(StringUtils.nullToString(param.get("SUP_ID")),"申请书",StringUtils.nullToString(param.get("APPLY_ID")),StringUtils.nullToString(param.get("APPLY_NAME")));
		List<Object> assets = channelService.findSupFileUploads(StringUtils.nullToString(param.get("SUP_ID")),"不动产、有价证券",StringUtils.nullToString(param.get("APPLY_ID")),StringUtils.nullToString(param.get("APPLY_NAME")));
		List<Object> OtherData = channelService.findSupFileUploads(StringUtils.nullToString(param.get("SUP_ID")),"其他资料",StringUtils.nullToString(param.get("APPLY_ID")),StringUtils.nullToString(param.get("APPLY_NAME")));
		List<Object> file_Types = (List<Object>)new DataDictionaryMemcached().get("额度申请附件类型");
		List<Object> listData = channelService.getCreditFileUploads(StringUtils.nullToString(param.get("SUP_ID")), file_Types, StringUtils.nullToString(param.get("APPLY_ID")));
		context.put("applyBook", applyBook);
		context.put("assets", assets);
		context.put("OtherData", OtherData);
		context.put("FundFiles", listData);
		context.put("param", param);
		context.put("applyDanMess", applyDanMess);
		return new ReplyHtml(VM.html(path+"assureApplyEditDetail.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"授信管理","担保额度申请","更新申请单相关信息"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply updateApplyMess(){
		Map<String,Object> param = _getParameters();
		int result = service.upAssureApplyDan(param);
		if(result > 0 ){
			return new ReplyAjax(true,"操作成功！");
		}else{
		    return new ReplyAjax(true,"操作失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"授信管理","担保额度申请","查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getAssureCreditPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getAssureCreditPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"授信管理","担保额度申请","插入申请基本信息"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply addAssureDan(){
		Map<String,Object> param = _getParameters();
		//通过供应商查询授信记录
		Map<String,Object> supCreditMess = service.getOneSupCredit(param);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		param.put("INITIAL_AMOUNT", StringUtils.nullToString(supCreditMess.get("INITIAL_AMOUNT")));
		param.put("STANDARD_AMOUNT", StringUtils.nullToString(supCreditMess.get("STANDARD_AMOUNT")));
		param.put("ASSURE_AMOUNT", StringUtils.nullToString(supCreditMess.get("ASSURE_AMOUNT")));
		param.put("CANUSE_MONEY", StringUtils.nullToString(supCreditMess.get("CANUSE_MONEY")));
		param.put("USE_MONEY", StringUtils.nullToString(supCreditMess.get("BASE_USEMONEY")));
		param.put("SINGLE_AMOUNT", StringUtils.nullToString(supCreditMess.get("SINGLE_AMOUNT")));
		param.put("SINGLE_CANUSEMONEY", StringUtils.nullToString(supCreditMess.get("SINGLE_CANUSEMONEY")));
		param.put("CREDIT_ID", supCreditMess.get("CREDIT_ID").toString());
	    param.put("APPLY_TYPE", "4");
	    param.put("APPLY_TIME",sdf.format(new Date()));
	    param.put("APPLY_USER", Security.getUser().getName());
	    param.put("APPLY_STATUS", "3");
		int apply_id =  0;
		if(param.containsKey("APPLY_ID") && param.get("APPLY_ID") !=null && !"".equals(param.get("APPLY_ID").toString())){
			service.upAssureApplyDan(param);
			apply_id = Integer.parseInt(param.get("APPLY_ID").toString());
		}else{
		    apply_id = service.addAssureApplyDan(param);
		}
		param.put("CREDIT_ID", supCreditMess.get("CREDIT_ID").toString());
		param.put("APPLY_ID", apply_id);
		return new ReplyAjax(true, param);
	}
	
	
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"授信管理","担保额度申请","保存申请单并发起流程"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply saveApplyAndStartProcess(){
		Map<String,Object> param = _getParameters();
		Boolean  flag = true;
		String msg = "";
	    int result = service.upAssureApplyDan(param);
	    if(result > 0){
	    	//发起评审流程
	    	String creditDeploymentName = "担保额度申请审批";
	    	List<String> prcessList = JBPM.getDeploymentListByModelName(creditDeploymentName);
	    	Map<String,Object> prcessParam = new HashMap<String, Object>();
	    	SuppliersService supService = new SuppliersService();
	    	Map<String,Object> supMess = supService.getOneSupplier(param);
	    	prcessParam.put("SUP_ID", param.get("SUP_ID").toString());
	    	prcessParam.put("CREDIT_ID", param.get("CREDIT_ID").toString());
	    	prcessParam.put("APPLY_ID", param.get("APPLY_ID").toString());
	    	prcessParam.put("SUP_NAME", supMess.containsKey("SUP_SHORTNAME") && supMess.get("SUP_SHORTNAME") != null ? supMess.get("SUP_SHORTNAME").toString():"");
    		if(prcessList.size() > 0){
    			String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),"","","",prcessParam).getId(); 
    			//变更授信状态
    			msg +=" 评审流程："+jbpm_id+"已发起！";
	    		param.put("JBPMID", jbpm_id);
	    		param.put("APPLY_STATUS", "0");
	    		service.upAssureApplyDan(param);
	    		
    		}else{
    			flag = false;
    			msg = "未找到流程："+creditDeploymentName;
				throw new ActionException("未找到流程："+creditDeploymentName);
    		}
	    }else{
	    	msg = "操作失败！";
	    	flag = false;
	    }
		return new ReplyAjax(flag, msg);
	}
}

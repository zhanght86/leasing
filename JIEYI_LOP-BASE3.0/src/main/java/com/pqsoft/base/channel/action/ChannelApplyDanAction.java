package com.pqsoft.base.channel.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.channel.service.AssureCreditService;
import com.pqsoft.base.channel.service.ChannelApplyDanService;
import com.pqsoft.base.channel.service.ChannelService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.StringUtils;

public class ChannelApplyDanAction extends Action {
private String path="base/channel/";
private ChannelApplyDanService service = new ChannelApplyDanService();
	
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"授信管理", "授信申请单管理", "列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply execute(){
    	VelocityContext context = new VelocityContext();
    	Map<String,Object> param = _getParameters();
    	context.put("param", param);
    	List statusMess = (List)new DataDictionaryMemcached().get("授信申请单类型");
//    	Map<String,Object> supMess = BaseUtil.getSup();
//    	context.put("SUP_ID", supMess);
    	context.put("applyTypes", statusMess);
    	return new ReplyHtml(VM.html(path+"ChannelApplyDanManager.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"授信管理", "授信申请单管理", "列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getApplyPage(){
    	Map<String,Object> param = _getParameters();
    	param.put("DIC_APPLY_TYPE", "授信申请单类型");
    	param.put("DIC_APPLY_STATUS", "授信申请单状态");
    	Page applyPage = service.getApplyPage(param);
    	return new ReplyAjaxPage(applyPage);
    }
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"授信管理","授信申请单管理","查看"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply detailApplyDan(){
    	VelocityContext context = new VelocityContext();
    	Map<String,Object> param = _getParameters();
    	Map<String,Object> applyDan = service.getApplyDan(param);
    	if("4".equals(applyDan.get("APPLY_TYPE").toString())){
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
    		context.put("applyDanMess", applyDan);
    		return new ReplyHtml(VM.html(path+"assureApplyDetail.vm", context));
    	}else{
    		param.put("APP_ID", param.get("APPLY_ID").toString());
    		param.put("SUP_ID", applyDan.get("SUP_ID").toString());
    		ChannelService channelService = new ChannelService();
    		context.put("APP_ID", param.get("APPLY_ID"));
    		context.put("AppInfos", channelService.getAppInfo(param));
    		context.put("score", channelService.selectCreditScore(param));
    		
    		context.put("supplier", channelService.getOneSupplier(param));
    		context.put("LinkMans", channelService.getLinkManList(param));
    		param.put("INVEST_TYPE", "0");
    		context.put("Naturals", channelService.getInvestsByType(param));
    		param.put("INVEST_TYPE", "1");
    		context.put("Legals", channelService.getInvestsByType(param));
    		context.put("files", channelService.findSupFileUploads(param.get("SUP_ID").toString(),"0",null,null));	
    		context.put("bgfiles", channelService.findSupFileUploads(param.get("SUP_ID").toString(),"财务报表",null,null));	
    		context.put("dfkfiles", channelService.findSupFileUploads(param.get("SUP_ID").toString(),"打分卡",null,null));
    		context.put("allArea", channelService.getAllArea());
    		
    		Map<String,Object> supplier = (Map<String,Object>)context.get("supplier");
    		//未授信不添加历史信用记录授信
    		if(StringUtils.nullToOther(supplier.get("STATUS"), "-1").equals("1") ){
    			//历史逾期记录 月
    			channelService.getOverdueInfos(context,param);
    		}
    		return new ReplyHtml(VM.html(path+"form02.vm", context));
    	}
    }
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"授信管理","授信申请单管理","删除[列表操作]"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply delApplyDan(){
    	Map<String,Object> param = _getParameters();
    	AssureCreditService assureService = new AssureCreditService();
    	param.put("APPLY_STATUS", "-1");
    	int result = assureService.upAssureApplyDan(param);
    	if(result > 0 ){
    	    return new ReplyAjax(true,"操作成功！");
    	}else{
    		return new ReplyAjax(false,"操作失败！");
    	}
    }
    
    @aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"授信管理","授信申请单管理","编辑[申请单]"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply editApplyDan(){
    	VelocityContext context = new VelocityContext();
    	Map<String,Object> param = _getParameters();
		Map<String,Object> applyDanMess = service.getApplyDan(param);
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
    	return new ReplyHtml(VM.html(path+"assureApplyPage.vm", context));
    }

}

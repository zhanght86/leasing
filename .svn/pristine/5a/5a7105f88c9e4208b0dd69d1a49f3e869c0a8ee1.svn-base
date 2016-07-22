package com.pqsoft.base.grantCredit.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.velocity.VelocityContext;
import com.pqsoft.base.grantCredit.service.CreditConfigService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

public class CreditConfigAction extends Action {
	private String path = "base/grantCredit/";
	private CreditConfigService service = new CreditConfigService();

	@aAuth(type = aAuth.aAuthType.USER)
	//
	@aPermission(name ={"授信管理", "授信额度占用规则设定", "列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();		
		context.put("PContext", param);
		context.put("propertys", service.getProperty());
		return new ReplyHtml(VM.html(path+"creditConfigManager.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"授信管理", "授信额度占用规则设定", "列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getConfigPage(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"授信管理","授信额度占用规则设定","授信占用规则列表[二级列表]"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getConfigList(){
		Map<String,Object> param = _getParameters();
		List<Object> configs = service.getConfigList(param);
		return new ReplyJson(JSONArray.fromObject(configs));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"授信管理","授信额度占用规则设定","下拉选级联"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getProValues(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> proValues = new HashMap<String, Object>();
		proValues.put("provalues", service.getProPertyValueByPty(param));
		return new ReplyJson(JSONObject.fromObject(proValues));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"授信管理","授信额度占用规则设定","添加/修改 [列表操作]"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply updateConig(){
	    Map<String,Object> param = _getParameters();
	    Map<String,Object> newParam = new HashMap<String, Object>();
	    Boolean flag = true ;
	    String msg = "";
	    int result = 0;
	    if(param.containsKey("GCC_ID") && param.get("GCC_ID") !=null && !"".equals(param.get("GCC_ID").toString())){
	    	//更新操作
	    	newParam.put("GCC_ID", param.get("GCC_ID").toString());
	    	newParam.put("LIMIT_RATE", param.get("LIMIT_RATE").toString());
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	newParam.put("MODIFY_TIME", sdf.format(new Date()));
	    	newParam.put("MODIFY_PERSON", Security.getUser().getName());
	    	result = service.updateConfig(newParam);
	    }else{
	    	//添加操作
	    	Map<String,Object> configParam = service.getOneConfigParam(param);
	    	newParam.putAll(configParam);
	    	newParam.put("CONFIG_PARAM_ID", param.get("CONFIG_PARAM_ID").toString());
	    	newParam.put("COMPANY_ID", param.get("COMPANY_ID").toString());
	    	newParam.put("LIMIT_RATE", param.get("LIMIT_RATE").toString());
	    	newParam.put("CREATOR", Security.getUser().getName());
	    	//判断该配置在该厂商下是否已存在
	    	List<Object> configs = service.checkConfigRepeat(newParam);
	    	if(configs.size() > 0){
	    		flag = false;
	    		msg = "该规则已经添加,请进行修改！";
	    		return new ReplyAjax(flag, msg);
	    	}else{
	    	   result = service.addConfigMess(newParam);
	    	}
	    }
	    if(result >0){
	    	flag = true ;
	    	msg = "操作成功!";
	    }else{
	    	flag = false ; 
	    	msg = "操作失败!";
	    }
		return new ReplyAjax(flag, msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"授信管理","授信额度占用规则设定","删除[列表操作] "})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delConfig(){
		Map<String,Object> param = _getParameters();
		int result = service.delOneConifg(param);
		if(result >0){
			return new ReplyAjax(true, "删除成功!");
		}else{
			return new ReplyAjax(false, "删除失败!");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getProductCatena(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//通过产品名称及COMPANY_ID查询产品系别
		List<Object> productCatena = service.getProCatenaByPNamecomId(param);
		context.put("catenaList", productCatena);
		return new ReplyAjax(VM.html(path+"Product_CatenaMess.vm", context)); 
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getProductType(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//通过产品名称及CATENA_ID查询产品型号
		List<Object> productType = service.getProTypeByComId(param);
		context.put("typeList", productType);
		return new ReplyAjax(VM.html(path+"Product_TypeConfig.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply saveLimitRate(){
		Map<String,Object> param = _getParameters();
		JSONArray jsonParam = JSONArray.fromObject(StringUtils.nullToString(param.get("LIMIT_LIST")));
		int result = 0;
		for (Object obj : jsonParam) {
			Map<String,Object> objJson = (Map<String,Object>)obj;
			//保存LIMIT_RATE信息
			result = service.saveLimitRate(objJson);
		}
		if(result > 0){
			return new ReplyAjax(true,"操作成功!");
		}else{
		    return new ReplyAjax(false,"操作失败!");
		}
		
	}
	
}

package com.pqsoft.leeds.DTI.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.leeds.DTI.service.DTIService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;

public class DTIAction extends Action{
	
	private String basePath = "leeds/DTI/";
	private DTIService service = new DTIService();
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply execute() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		DataDictionaryMemcached data= new DataDictionaryMemcached();
		List<Map<String,Object>> list = data.get("特批处理");
		List<Map<String,Object>> jjyy = data.get("DTI拒绝原因");
		List<Map<String,Object>> khfl = data.get("客户分类");
		
		
		//当前申请车辆月供金额
		double monthRent = service.findMonthRent(param);
		context.put("monthRent", monthRent);
		double ZC_AMOUNT_1 = service.findZC_AMOUNT_1(param);
		context.put("ZC_AMOUNT_1",ZC_AMOUNT_1);
		double ZC_AMOUNT_2 = service.findZC_AMOUNT_2(param);
		context.put("ZC_AMOUNT_2",ZC_AMOUNT_2);
		double ZC_AMOUNT_3 = service.findZC_AMOUNT_3(param);
		context.put("ZC_AMOUNT_3",ZC_AMOUNT_3);
		
		context.put("khfl", khfl);
		context.put("jjyy", jjyy);
		context.put("tpcl", list);
		context.put("param", param);
		
		List<Map<String,Object>> dtiList = service.findByProjectId(param);
		if(dtiList!=null&&dtiList.size()>0){
			for(int i=0;i<dtiList.size();i++){
				Map<String,Object> dtiMap = dtiList.get(i);
				if("0".equals(dtiMap.get("IS_COUNT").toString())){
					context.put("dti", dtiMap);
				}else{
					if("1".equals(dtiMap.get("TYPE").toString())){
						context.put("borrower", dtiMap);
					}else if("2".equals(dtiMap.get("TYPE").toString())){
						context.put("co-borrower", dtiMap);
					}else if("3".equals(dtiMap.get("TYPE").toString())){
						context.put("guarantor", dtiMap);
					}else if("0".equals(dtiMap.get("TYPE").toString())){
						context.put("family", dtiMap);
					}
					
				}
			}
		}
		
		return new ReplyHtml(VM.html(basePath+"toDTI.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply save(){
		Map<String,Object> param = _getParameters();
		int count = 0;
		boolean flag = true;
		String msg = "";
		
		if(Boolean.valueOf(param.get("IS_COUNT").toString())){
			//不计算
			param.put("IS_COUNT", "0");
			List<Map<String,Object>> dtiList = service.findByProjectId(param);
			
			//修改
			if(dtiList!=null&&dtiList.size()>0){
				count = service.updateDTInotCount(param);
			}else{
				count = service.addDTI(param);
			}
			//删除计算数据
			param.put("IS_COUNT", "1");
			service.deleteDTI(param);
		}else{
			//计算
			param.put("IS_COUNT", "1");
			
			List<Map<String,Object>> dtiList = service.findByProjectId(param);
			//借款人
			String json1 = (String) param.get("json1");
			Map<String,Object> jsonObj1 = JSONObject.fromObject(json1);
			jsonObj1.put("TYPE", "1");
			//共同借款人
			String json2 = (String) param.get("json2");
			Map<String,Object> jsonObj2 = JSONObject.fromObject(json2);
			jsonObj2.put("TYPE", "2");
			//家庭
			String json3 = (String) param.get("json3");
			Map<String,Object> jsonObj3 = JSONObject.fromObject(json3);
			jsonObj3.put("TYPE", "0");
			//担保人
			String json4 = (String) param.get("json4");
			Map<String,Object> jsonObj4 = JSONObject.fromObject(json4);
			jsonObj4.put("TYPE", "3");
			
			param.putAll(jsonObj1);
			//修改
			if(dtiList!=null&&dtiList.size()>0){
				service.updateDTI(param);
			}else{
				service.addDTI(param);
			}
			
			param.putAll(jsonObj2);
			//修改
			if(dtiList!=null&&dtiList.size()>0){
				service.updateDTI(param);
			}else{
				service.addDTI(param);
			}
			param.putAll(jsonObj3);
			//修改
			if(dtiList!=null&&dtiList.size()>0){
				service.updateDTI(param);
			}else{
				service.addDTI(param);
			}
			param.putAll(jsonObj4);
			//修改
			if(dtiList!=null&&dtiList.size()>0){
				count = service.updateDTI(param);
			}else{
				count = service.addDTI(param);
			}
			//删除不计算数据
			param.put("IS_COUNT", "0");
			service.deleteDTI(param);
		}
		
		if(count>0){
			flag = true;
			msg = "保存成功";
		}else{
			flag = false;
			msg = "保存失败";
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply clean(){
		Map<String,Object> param = _getParameters();
		int count = service.deleteDTI(param);
		boolean flag = false;
		String msg = "失败";
		if(count>0){
			msg="重置成功";
			flag = true;
		}
		return new ReplyAjax(flag,msg);
	}

}

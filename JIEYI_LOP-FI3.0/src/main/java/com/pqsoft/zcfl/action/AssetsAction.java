package com.pqsoft.zcfl.action;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDataDictionaryService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.zcfl.service.AssetsService;
import com.pqsoft.zcfl.service.ZCFL;

//@aResource(name = "资产分级-资产管理")
public class AssetsAction extends Action {
	private final Log logger = LogFactory.getLog(getClass());
	private final String path = "zcfl/assets/";

	
	@Override
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		List<Object> list = SysDataDictionaryService.queryDataDictionary("业务类型");
		context.put("PLATFORM1", list);
		List<Object> list1 = DataDictionaryService.queryDataDictionary("资产分类类型");
		context.put("ZAIL", list1);
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "assets_list.vm", context));
	}
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply execute_ajax() {
		AssetsService service = new AssetsService();
		Map<String, Object> param = _getParameters();
		Page pt = service.pageTemplate(param);
		return new ReplyAjaxPage(pt);
	}
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	@SuppressWarnings("unchecked")
	public JSONArray selectFXYZ(String RESULT_ID) {
		AssetsService service = new AssetsService();
		List<Map<String,Object>> zrdListPd = service.selectFXYZ(RESULT_ID);
		List<Object> zrdDictionaryList = DataDictionaryService.queryDataDictionary("损失级租赁资产直接分类");
		List<Object> zrdList = new ArrayList<Object>();
		if(zrdListPd != null && zrdListPd.size() > 0 && zrdDictionaryList != null && zrdDictionaryList.size() > 0){
			for(int i = 0; i < zrdDictionaryList.size(); i++){
				boolean flag = true;
				for(int j = 0; j < zrdListPd.size(); j++){
					if(zrdListPd.get(j).get("ZRD_TYPE").toString().equals(((Map<String,Object>)zrdDictionaryList.get(i)).get("CODE"))){
						flag = false;
					}
				}
				if(flag){
					zrdList.add(zrdDictionaryList.get(i));
				}
				
			}
		}
		return JSONArray.fromObject(zrdList);
	}
	
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply payReport()  {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		AssetsService service = new AssetsService();
		List<Object> list = SysDataDictionaryService.queryDataDictionary("业务类型");
		context.put("PLATFORM1", list);
		List<Object> list1 = DataDictionaryService.queryDataDictionary("资产分类类型");
		context.put("ZAIL", list1);
		context.put("payReportList", service.payReportList(param));
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "assets_listym.vm", context));
	}

	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply doIndirect()  {
		AssetsService service = new AssetsService();
		Map<String, Object> param = _getParameters();
		JSONObject re = new JSONObject();
		Map<String,Object> mapN = service.indirect(param);//返回任务数据
		if(mapN != null){
			if(mapN.containsKey("over")){
				re.put("over", mapN.get("over"));
				re.put("RESULT_ID", mapN.get("RESULT_ID"));//资产评级记录id
				re.put("flag", true);
			}else{
				re.put("ID", mapN.get("ID"));//任务id
				re.put("CPT_ID", mapN.get("CTP_ID"));//题目模版id
				re.put("RESULT_ID", mapN.get("RESULT_ID"));//资产评级记录id
				re.put("RESULT_PAY_CODE", mapN.get("RESULT_PAY_CODE"));//支部表编号
				re.put("flag", true);
				re.put("flag", true);
			}
		}else{
			re.put("flag", false);
		}
		return new ReplyAjax(re);
	}
	
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply goondoIndirect()  {
		AssetsService service = new AssetsService();
		Map<String, Object> param = _getParameters();
		JSONObject re = new JSONObject();
		Map<String,Object> mapN = service.goondoIndirect(param);//返回任务数据
		if(mapN != null){
			re.put("ID", mapN.get("ID"));//任务id
			re.put("CPT_ID", mapN.get("CTP_ID"));//题目模版id
			re.put("RESULT_ID", mapN.get("RESULT_ID"));//资产评级记录id
			re.put("RESULT_PAY_CODE", mapN.get("ZCT_PAY_CODE"));//资产评级记录id
			re.put("flag", true);
		}else{
			re.put("flag", false);
		}
		return new ReplyAjax(re);
	}
	
	@SuppressWarnings("unchecked")
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply doStartBpm() {
		Map<String, Object> param = _getParameters();
		AssetsService service = new AssetsService();
		JSONObject re = new JSONObject();
		String RESULT_ID = service.addZcflResult(param);
		boolean flag = !"".equals(RESULT_ID) && RESULT_ID != null;
		List<Map<String,Object>> zrdlist=JSONArray.fromObject(param.get("json"));
		boolean flag2 = false;
		if(flag){
			flag2 = service.addZcflResultDirectly(zrdlist,RESULT_ID);
		}
		
		if (flag && flag2) {
			new ZCFL().simpleSuccess(RESULT_ID);//直接调用通过的流程
			// --启动流程实例--start
//			JBPM jbpm = mSGA.getBean("JBPM");
//			List<?> deploymentList = jbpm.getDeploymentListByModelName("资产直接分级");
//			if (deploymentList.size() > 0) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("RESULT_ID", RESULT_ID);
//				// TODO 流程ID需要存
//				String jbpmId = jbpm.startProcessInstanceById((String) deploymentList.get(0), map).getId();
//				Map<String,Object> jbpmMap = new HashMap<String,Object>();
//				jbpmMap.put("JBPM_ID", jbpmId);
//				jbpmMap.put("RESULT_ID", RESULT_ID);
//				service.updateJbpmId(jbpmMap);//存储流程ID
//			} else {
//				flag = false;
//				logger.error("无对应审批流程");
//				mSGA.rollbackSqlSession();// 事务回滚
//			}
//			// --启动流程实例--end
		}

		re.put("flag", flag && flag2);
		return new ReplyAjax(re);
	}
	
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply doStartBpmF() {
		Map<String, Object> param = _getParameters();
		AssetsService service = new AssetsService();
		JSONObject re = new JSONObject();
		int result = service.addResultNote(param);
		boolean flag = result > 0;
		
		if (flag) {
			new ZCFL().complexSuccess(param.get("RESULT_ID").toString());//直接调用通过的流程
//			// --启动流程实例--start
//			JBPM jbpm = mSGA.getBean("JBPM");
//			List<?> deploymentList = jbpm.getDeploymentListByModelName("资产非直接分级");
//			if (deploymentList.size() > 0) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("RESULT_ID", param.get("RESULT_ID"));
//				// TODO 流程ID需要存
//				String jbpmId = jbpm.startProcessInstanceById((String) deploymentList.get(0), map).getId();
//				//编写流程代码
//				Map<String,Object> jbpmMap = new HashMap<String,Object>();
//				jbpmMap.put("JBPM_ID", jbpmId);
//				jbpmMap.put("RESULT_ID", param.get("RESULT_ID"));
//				service.updateJbpmId(jbpmMap);//存储流程ID
//				
//			} else {
//				flag = false;
//				logger.error("无对应审批流程");
//				mSGA.rollbackSqlSession();// 事务回滚
//			}
//			// --启动流程实例--end
			
		}
		
		re.put("flag", flag);
		return new ReplyAjax();
	}
	
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply directlyVM() {//传入RESULT_ID
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		AssetsService service = new AssetsService();
		Map<String,Object> directMap = service.selectDirectAdditional(param);//查询支付标号，上次级别,RESULT_ID
		//获取时间
		Date now = new Date();         
		DateFormat d1 = DateFormat.getDateInstance(); 
		String str1 = d1.format(now); 
		String[] str=str1.split("-");
		directMap.put("yearage", str[0]);
		Map<String,Object> yearMap=service.selectyear(directMap);//年初级别
		List<?> list=DataDictionaryService.queryDataDictionary("资产分类类型");
		param.put("G0","G0");
		param.put("G1", "还款意愿因子调整G1");
		param.put("G2", "合规性因子调整G2");
		param.put("G3", "重大事件因子调整G3");
		Map<String,Object> mapg0=service.selg0(param);
		Map<String,Object> mapg1=service.selg1(param);
		Map<String,Object> mapg2=service.selg2(param);
		Map<String,Object> mapg3=service.selg3(param);
		context.put("mapg0", mapg0);
		context.put("mapg1", mapg1);
		context.put("mapg2", mapg2);
		context.put("mapg3", mapg3);
		context.put("directMap", directMap);
		context.put("yearMap", yearMap);
		context.put("list", list);
		return new ReplyHtml(VM.html(path + "direct_additional.vm", context));
	}
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply directlyVM1() {//传入RESULT_ID
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		AssetsService service = new AssetsService();
		Map<String,Object> directMap = service.selectDirectAdditional(param);//查询支付标号，上次级别,RESULT_ID
		//获取去年
		Date now = new Date();         
		DateFormat d1 = DateFormat.getDateInstance(); 
		String str1 = d1.format(now); 
		String[] str=str1.split("-");
		directMap.put("yearage", str[0]);
		Map<String,Object> yearMap=service.selectyear(directMap);//年初级别
		List<?> list=DataDictionaryService.queryDataDictionary("资产分类类型");
		param.put("ZRD_TYPE", "初分意见");
		Map<String,Object> map=service.selaffirm(param);
		param.put("G0","G0");
		param.put("G1", "还款意愿因子调整G1");
		param.put("G2", "合规性因子调整G2");
		param.put("G3", "重大事件因子调整G3");
		Map<String,Object> mapg0=service.selg0(param);
		Map<String,Object> mapg1=service.selg1(param);
		Map<String,Object> mapg2=service.selg2(param);
		Map<String,Object> mapg3=service.selg3(param);
		context.put("mapg0", mapg0);
		context.put("mapg1", mapg1);
		context.put("mapg2", mapg2);
		context.put("mapg3", mapg3);
		context.put("directMap", directMap);
		context.put("yearMap", yearMap);
		context.put("list", list);
		context.put("map", map);
		return new ReplyHtml(VM.html(path + "direct_additional1.vm", context));
	}
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply directlyVM2()  {//传入RESULT_ID
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		AssetsService service = new AssetsService();
		Map<String,Object> directMap = service.selectDirectAdditional(param);//查询支付标号，上次级别,RESULT_ID
		//获取去年
		Date now = new Date();         
		DateFormat d1 = DateFormat.getDateInstance(); 
		String str1 = d1.format(now); 
		String[] str=str1.split("-");
		directMap.put("yearage", str[0]);
		Map<String,Object> yearMap=service.selectyear(directMap);//年初级别
		List<?> list=DataDictionaryService.queryDataDictionary("资产分类类型");
		param.put("ZRD_TYPE", "初分意见");
		Map<String,Object> map=service.selaffirm(param);
		param.put("ZRD_TYPE", "认定意见");
		Map<String,Object> map1=service.selaffirm(param);
		param.put("G0","G0");
		param.put("G1", "还款意愿因子调整G1");
		param.put("G2", "合规性因子调整G2");
		param.put("G3", "重大事件因子调整G3");
		Map<String,Object> mapg0=service.selg0(param);
		Map<String,Object> mapg1=service.selg1(param);
		Map<String,Object> mapg2=service.selg2(param);
		Map<String,Object> mapg3=service.selg3(param);
		context.put("mapg0", mapg0);
		context.put("mapg1", mapg1);
		context.put("mapg2", mapg2);
		context.put("mapg3", mapg3);
		context.put("directMap", directMap);
		context.put("yearMap", yearMap);
		context.put("list", list);
		context.put("map", map);
		context.put("map1", map1);
		return new ReplyHtml(VM.html(path + "direct_additional2.vm", context));
	}
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public JSONObject insinitial()
	{
		Map<String, Object> param = _getParameters();
		JSONObject re = new JSONObject();
		AssetsService service = new AssetsService();
		service.updateresult(param);
		Map<String,Object> map=service.selectinitial(param);
		int ZRD_SORTING=Integer.parseInt(map.get("ZRD_SORTING").toString())+1;
		param.put("ZRD_SORTING", ZRD_SORTING);
		re.put("flag", service.insinitial(param));
		return re;
	}
	
	

	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply assetsDetails()  {
		VelocityContext context = new VelocityContext();
		AssetsService service = new AssetsService();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("details", service.selectDetails(param));
		context.put("equipment", service.selectEquipment(param));
		return new ReplyHtml(VM.html(path + "assets_details.vm", context));
	}
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply assetsextra()  {
		VelocityContext context = new VelocityContext();
		AssetsService service = new AssetsService();
		Map<String, Object> param = _getParameters();
		context.put("assetsextra", service.assetsextra(param));
		return new ReplyHtml(VM.html(path + "assets_assetsextra.vm", context));
	}
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply approal() 
	{
		Map<String, Object> param = _getParameters();
		AssetsService service = new AssetsService();
		JSONObject re = new JSONObject();
		Map<String,Object> mapN = service.assetsextra(param);
		if(mapN != null){
			re.put("RESULT_ID", mapN.get("RESULT_ID"));
			re.put("CODE", mapN.get("CODE"));//审批后级别
			re.put("RESULT_NOW_LEVEL", mapN.get("RESULT_NOW_LEVEL"));//当前级别
			re.put("CODE1", mapN.get("CODE1"));//审批后级别
			re.put("RESULT_NOW_LEVEL1", mapN.get("RESULT_NOW_LEVEL1"));//当前级别
			re.put("RESULT_PAY_CODE", mapN.get("RESULT_PAY_CODE"));
			re.put("flag", true);
		}else{
			re.put("flag", false);
		}
		return new ReplyAjax(re);
	}
	
	@SuppressWarnings("unchecked")
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply levelReport()  {
		VelocityContext context = new VelocityContext();
		AssetsService service = new AssetsService();
		try {

			Map<String, Object> map = service.normalWindCoverPicLevel();
			context.put("mapValue", map);
		} catch (Exception e) {
			logger.error(e);
		}
		Map<String, Object> param = _getParameters();
		context.put("reportInfo", service.getLevelPage(param));

		if (param == null) {
			param = new HashMap();
		}

		if (param.get("reportFlag") != null && !param.get("reportFlag").equals("")) {
			;
		} else {
			param.put("reportFlag", 1);
		}
		context.put("mapInfo", param);
		return new ReplyHtml(VM.html(path + "windLevel.vm", context));
	}

	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply baoDanMoneys()  {
		VelocityContext context = new VelocityContext();
		AssetsService service = new AssetsService();
		List<?> list = service.selectInsureGroup();
		try {
			Map<String,Object> map = service.baoDanManger(list);
			context.put("mapValue", map);
		} catch (Exception e) {
			logger.error(e);
		}
		Map<String, Object> param = _getParameters();
		context.put("reportInfo", service.getInsurePage(param));
		if (list.size() > 0) {
			context.put("reportList", list);
		}

		if (param == null) {
			param = new HashMap();
		}

		if (param.get("reportFlag") != null && !param.get("reportFlag").equals("")) {
			;
		} else {
			param.put("reportFlag", 1);
		}
		context.put("mapInfo", param);
		return new ReplyHtml(VM.html(path + "toubaoWind.vm", context));
	}
	
}

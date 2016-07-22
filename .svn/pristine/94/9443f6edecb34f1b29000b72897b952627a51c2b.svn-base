package com.pqsoft.zcfl.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.zcfl.service.AssetsService;
import com.pqsoft.zcfl.service.CapitalClassificationService;
import com.pqsoft.zcfl.service.ClassificationService;
import com.pqsoft.zcfl.service.ClassifyTaskService;

//@aResource(name = "资产分级-任务")
public class ClassifyTaskAction extends Action {

	private CapitalClassificationService CCService = new CapitalClassificationService();
	
	@Override
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		ClassifyTaskService service = new ClassifyTaskService();
		context.put("pageTemplate", service.pageTemplate(map));
		context.put("maps", map);
		return new ReplyHtml(VM.html("zcfl/classifytask_list.vm", context));
	}
	
	/**
	 * 
	 * 付玉龙 time2012-5-30下午02:18:27
	 */
//	@aOperation(name = "非直接资产评级")
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply CapitalClassificationNoStraight()  {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		// 题目
		List<?> listMtitle = CCService.Mtitle(map);
		context.put("listMtitle", listMtitle);
		// 选项
		List<?> listMoption = CCService.Moption(map);
		context.put("listMoption", listMoption);
		context.put("maps", map);
		//资产当前级别 &&　初始级别
		Map<String,Object> assetsLevel = CCService.selectNowLevel(map);
		context.put("assetsLevel", assetsLevel);
		return new ReplyHtml(VM.html("zcfl/assets/capitalclassificationNoStraight.vm", context));
	}	

	/**
	 * 提交结果
	 * 付玉龙
	 *time2012-6-11上午10:16:11
	 */
	@SuppressWarnings("unchecked")
//	@aOperation(name = "提交结果")
	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply submitResults()   {
		Map<String,Object> maps = _getParameters();
		ClassificationService CService = new ClassificationService();
		AssetsService service = new AssetsService();
		JSONObject re = new JSONObject();
		List<Map<String,Object>> idlist=JSONArray.fromObject(maps.get("json").toString());
		Map<String, Object> results = null;
		try {
			results = CService.execPeople(idlist,maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maps.put("idlist", idlist);
		maps.putAll(results);
		Map<String, Object> mapN = null;
		try {
			mapN = CService.subsequent(maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//返回任务数据
		if(mapN != null){
			if(mapN.containsKey("over")){
				re.put("over", mapN.get("over"));
				re.put("RESULT_ID", mapN.get("RESULT_ID"));
				Map<String,Object> map = service.assetsextra(mapN);
				re.put("CODE", map.get("CODE"));
				re.put("RESULT_NOW_LEVEL", map.get("RESULT_NOW_LEVEL"));
				re.put("CODE1", map.get("CODE1"));
				re.put("RESULT_NOW_LEVEL1", map.get("RESULT_NOW_LEVEL1"));
				re.put("RESULT_PAY_CODE", map.get("RESULT_PAY_CODE"));
			}else{
				re.put("ID", mapN.get("ID"));//任务id
				re.put("CPT_ID", mapN.get("CTP_ID"));//题目模版id
				re.put("RESULT_ID", mapN.get("RESULT_ID"));//资产评级记录id
				re.put("RESULT_PAY_CODE", mapN.get("RESULT_PAY_CODE"));//支付表编号
			}
			re.put("flag", true);
		}else{
			re.put("flag", false);
		}
		return new ReplyAjax(re);
	}
	
}

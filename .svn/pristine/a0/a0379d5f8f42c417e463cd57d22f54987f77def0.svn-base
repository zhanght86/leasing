package com.pqsoft.call.action;
/**
 *   逾期客户统计
 */
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.call.service.OverdueClientStatisticsService;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ReplyExcel;

public class OverdueClientStatisticsAction extends Action {
	
	private String basePath = "call/"; 
	private OverdueClientStatisticsService service = new OverdueClientStatisticsService();
	
	/**
	 * 用于点击逾期客户统计得到返回的页面
	 */
	@Override
	@aPermission(name = { "债权管理", "呼叫中心逾期客户统计", "主页面" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		VelocityContext context=new VelocityContext();
		//从数据字典取得 租赁物类型
		context.put("product_type", (List)new DataDictionaryMemcached().get("产品类型"));
		//TODO 从数据字典取得 厂商 暂时使用  PDF模版使用厂商 后期可能要调整
		context.put("dld_name", (List)new DataDictionaryMemcached().get("PDF模版使用厂商"));
		return new ReplyHtml(VM.html(basePath + "overdueClientStatisticsShow.vm", context));
	}
	
	/**
	 * 用于分页查询
	 */
	@aPermission(name = { "债权管理", "呼叫中心逾期客户统计", "主页面分页查询"})
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 * 用于项目方案逾期查询
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply yqSearch() {
		Map<String,Object> param = _getParameters();
		VelocityContext context=new VelocityContext();
		context.put("KHMC", param.get("KHMC"));
		return new ReplyHtml(VM.html(basePath + "overdueClientStatisticsOnlyReadShow.vm", context));
	}
	
	/**
	 * 用于分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageDataOnlyRead(){
		//需要KHMC 客户名称
		Map<String,Object> param = _getParameters();
		ManageService mgService = new ManageService();
		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
		Map comMap = (Map) mgService.getCompanyByUserId(Security.getUser().getId());
		if(supMap != null && supMap.get("SUP_ID") != null){
			param.put("SUP_TYPE", supMap.get("SUP_ID"));
		}
		Page pagedata = service.getPageDataByKHMC(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 * 用于点击电话号码得到客户逾期原因记录的页面
	 */
	@aPermission(name = { "债权管理", "呼叫中心逾期客户统计", "客户逾期原因记录" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowReasonRecord() {
		Map<String,Object> param = _getParameters();
		//取得客户名称, 项目编号, 联系方式 信息
		VelocityContext context = new VelocityContext();
		context.put("PROJ_INFO", param);//将map直接给context
		context.put("KK_RESULT", (Map)service.selectReasonByFundId(param));//将map直接给context
		return new ReplyHtml(VM.html(basePath + "overdueClientReasonRecordShow.vm", context));
	}
	
	/**
	 *  添加客户逾期原因记录
	 */
	@aPermission(name = { "债权管理", "呼叫中心逾期客户统计", "客户逾期原因记录添加" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doAddRecord() {
		Map<String,Object> param = _getParameters();
		param.put("CREATOR", Security.getUser().getName());
		//CALLER严格说需根据呼叫号来获取 暂时使用操作人
		param.put("CALLER", Security.getUser().getName());
		service.insertRecord(param);
		return  new ReplyAjax(true,"");
	}
	
	/**
	 *  导出逾期客户excel TODO
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply exportExcel(){
		Map<String,Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);
		if ("4".equals(param.get("YQ_QS").toString())) {
			//大于3期的特殊处理
			param.put("YQ_QS", "");
			param.put("YQ_QS2", "4");
		}
		return new ReplyExcel(service.exportData(param),"overdueClient"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
	}
}

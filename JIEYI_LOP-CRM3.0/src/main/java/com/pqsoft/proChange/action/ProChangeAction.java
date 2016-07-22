package com.pqsoft.proChange.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.proChange.service.ProChangeService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.payment.service.PaymentApplyService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.MyNumberTool;
import com.pqsoft.util.Util;

/**
 * 方案变更申请
 * @author Administrator
 *
 */
public class ProChangeAction extends Action {
	
	projectService service = new projectService();

	@Override
	public Reply execute() {
		return null;
	}
	
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.LOGIN)
	public Reply projectChange(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		
		Map<String, Object> schemeStatus = new ProChangeService().getSchemeStatus(m);
		context.put("FABGSQ_STATUS", schemeStatus.get("FABGSQ_STATUS"));
		
		Map<String, Object> mapPro = new ProChangeService().getPro(m);
		context.put("mapPro", mapPro);
		m.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		m.put("BASE_SCHEME", service.getBaseSchemeBySchemeCode(m));
		// 查询基本信息
		Map<String,Object> baseMap = service.queryInfoByEqBase(m);

		List<Map<String,Object>> eqlist = service.queryEqByProjectIDByScheme(m);
		context.put("eqList", eqlist);
		context.put("eqMap", eqlist.get(0));

		// 通过平台查厂商
		CompanyService comService = new CompanyService();
		context.put("CompanyList", comService.queryCompanyList(m));

		// 单位
		context.put("unitList", new DataDictionaryMemcached().get("设备单位"));

		// 通过平台查询出行业
		List<Map<String,Object>> HangYeList = new BaseSchemeService().getFHFA_MANAGERSUBINFO(
				m.get("MANAGER_ID") + "", Security.getUser().getOrg()
				.getPlatform(), "行业类型");
		// List HangYeList = service.HangYeList(m);
		context.put("HangYeList", HangYeList);

		// 区域（平台下的区域到省）
		context.put(
				"AREAS",
				service.queryManagerArea(Security.getUser().getOrg()
						.getPlatformId(), null, 2));
		// 查询方案名称
		context.put("BASE_SCHEME", service.getBaseSchemeBySchemeCode(m));
		// king查方案
		BaseSchemeService bsService = new BaseSchemeService();
		context.put("BASE_SCHEME1", bsService.querySchemeInfoList(m,"WEB"));
		context.put("dicTag", Util.DICTAG);
		context.put("MyNumberTool", new MyNumberTool());
		context.put( "SCHEMEDETAIL", bsService.getSchemeClob(null, m.get("PROJECT_ID") + "", m.get("SCHEME_ROW_NUM") + ""));
		List<Map<String, Object>> schemeList = bsService.getSchemeClob(null, m.get("PROJECT_ID") + "", m.get("SCHEME_ROW_NUM") + "");
		for(int i=0;i<schemeList.size();i++){
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("购置税")){
				String gzs = schemeList.get(i).get("VALUE_STR").toString();
				context.put("GZS", gzs);
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("上牌费")){
				context.put("SPF", schemeList.get(i).get("VALUE_STR"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("路桥费")){
				context.put("LQF", schemeList.get(i).get("VALUE_STR"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("临牌费")){
				context.put("LPF", schemeList.get(i).get("VALUE_STR"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("环保标费")){
				context.put("HBBF", schemeList.get(i).get("VALUE_STR"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("其他费用")){
				context.put("QTFY", schemeList.get(i).get("VALUE_STR"));
			}
		}
		context.put(
				"schemeBase",
				bsService.getSchemeBaseInfoByProjectId(
						m.get("PROJECT_ID") + "", null,
						m.get("SCHEME_ROW_NUM") + "").get(0));
		
		/************************ 查询商务政策对应的利率规则 King 2014-08-21 **********************************************/
		BaseSchemeService baseSchService = new BaseSchemeService();
		Map<String,Object> maprate = new HashMap<String,Object>();
		maprate.put("SCHEME_CODE", m.get("SCHEME_ID"));
		// 年利率
		m.put("rateList", baseSchService.doSelectBaseSchemeYearRate(maprate));
		// 手续费
		m.put("feeList", baseSchService.doSelectBaseSchemeFeeRate(maprate));
		/**********************************************************************/
		PayTaskService pay = new PayTaskService();
		int PAY_ID = service.queryIdByProjectIdRowNum(m);
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("ID", PAY_ID);
		baseMap.put("ID", PAY_ID);
		List<Map<String,Object>> listDetail = pay.doShowRentPlanDetailScheme(payMap);
		baseMap.put("ONEMONEY", (listDetail.get(0)).get("ZJ") + "");
		context.put("detailList", listDetail);
		context.put("FORMAT", UTIL.FORMAT);
		context.put("ywlx", new SysDictionaryMemcached().get("业务类型"));
		context.put("baseMap", baseMap);
		m.put("SCHEME_CODE", m.get("SCHEME_ID"));
		context.put("param", m);
		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		m.put("SPID", Security.getUser().getOrg().getSpId());
		SuppliersService suppService = new SuppliersService();
		//mod gengchangbao JZZL-203 2016年6月13日10:14:48 start
		//context.put("suppliersList", suppService.querySuppByCom(m));
		if (eqlist != null && eqlist.size() > 0) {
			Map<String,Object> eqMap  = eqlist.get(0);
			if (eqMap != null && eqMap.get("WHETHER_SALES_SI") != null && !"".equals(eqMap.get("WHETHER_SALES_SI").toString())) {
				m.put("WHETHER_SALES_SI", eqMap.get("WHETHER_SALES_SI"));
				context.put("suppliersList", suppService.querySuppByCgxl(m));
			}
		}
		List<Object> whetherSalesSi = (List)new DataDictionaryMemcached().get("采购类型");
		context.put("whetherSalesSi", whetherSalesSi);
		//mod gengchangbao JZZL-203 2016年6月13日10:14:48 end
		
		context.put("SCHEME_ROW_NUM",m.get("SCHEME_ROW_NUM") );
		context.put("PROJECT_ID",m.get("PROJECT_ID") );
		context.put("SCHEME_ID",m.get("SCHEME_ID") );
		
		return new ReplyHtml(VM.html("bpm/projectChange.vm", context));
	}
	
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.LOGIN)
	public Reply updateProjectChange(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		
		Map<String, Object> schemeStatus = new ProChangeService().getSchemeStatus(m);
		context.put("FABGSQ_STATUS", schemeStatus.get("FABGSQ_STATUS"));
		
		Map<String, Object> mapPro = new ProChangeService().getPro(m);
		context.put("mapPro", mapPro);
		m.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		m.put("BASE_SCHEME", service.getBaseSchemeBySchemeCode(m));
		// 查询基本信息
		Map<String,Object> baseMap = service.queryInfoByEqBase(m);

		List<Map<String,Object>> eqlist = service.queryEqByProjectIDByScheme(m);
		context.put("eqList", eqlist);
		context.put("eqMap", eqlist.get(0));

		// 通过平台查厂商
		CompanyService comService = new CompanyService();
		context.put("CompanyList", comService.queryCompanyList(m));

		// 单位
		context.put("unitList", new DataDictionaryMemcached().get("设备单位"));

		// 通过平台查询出行业
		List<Map<String,Object>> HangYeList = new BaseSchemeService().getFHFA_MANAGERSUBINFO(
				m.get("MANAGER_ID") + "", Security.getUser().getOrg()
				.getPlatform(), "行业类型");
		// List HangYeList = service.HangYeList(m);
		context.put("HangYeList", HangYeList);

		// 区域（平台下的区域到省）
		context.put(
				"AREAS",
				service.queryManagerArea(Security.getUser().getOrg()
						.getPlatformId(), null, 2));
		// 查询方案名称
		context.put("BASE_SCHEME", service.getBaseSchemeBySchemeCode(m));
		// king查方案
		BaseSchemeService bsService = new BaseSchemeService();
		context.put("BASE_SCHEME1", bsService.querySchemeInfoList(m,"WEB"));
		context.put("dicTag", Util.DICTAG);
		context.put("MyNumberTool", new MyNumberTool());
		context.put( "SCHEMEDETAIL", bsService.getSchemeClob(null, m.get("PROJECT_ID") + "", m.get("SCHEME_ROW_NUM") + ""));
		List<Map<String, Object>> schemeList = bsService.getSchemeClob(null, m.get("PROJECT_ID") + "", m.get("SCHEME_ROW_NUM") + "");
		for(int i=0;i<schemeList.size();i++){
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("购置税")){
				String gzs = schemeList.get(i).get("VALUE_STR").toString();
				context.put("GZS", gzs);
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("上牌费")){
				context.put("SPF", schemeList.get(i).get("VALUE_STR"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("路桥费")){
				context.put("LQF", schemeList.get(i).get("VALUE_STR"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("临牌费")){
				context.put("LPF", schemeList.get(i).get("VALUE_STR"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("环保标费")){
				context.put("HBBF", schemeList.get(i).get("VALUE_STR"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("其他费用")){
				context.put("QTFY", schemeList.get(i).get("VALUE_STR"));
			}
		}
		context.put(
				"schemeBase",
				bsService.getSchemeBaseInfoByProjectId(
						m.get("PROJECT_ID") + "", null,
						m.get("SCHEME_ROW_NUM") + "").get(0));
		
		/************************ 查询商务政策对应的利率规则 King 2014-08-21 **********************************************/
		BaseSchemeService baseSchService = new BaseSchemeService();
		Map<String,Object> maprate = new HashMap<String,Object>();
		maprate.put("SCHEME_CODE", m.get("SCHEME_ID"));
		// 年利率
		m.put("rateList", baseSchService.doSelectBaseSchemeYearRate(maprate));
		// 手续费
		m.put("feeList", baseSchService.doSelectBaseSchemeFeeRate(maprate));
		/**********************************************************************/
		PayTaskService pay = new PayTaskService();
		int PAY_ID = service.queryIdByProjectIdRowNum(m);
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("ID", PAY_ID);
		baseMap.put("ID", PAY_ID);
		List<Map<String,Object>> listDetail = pay.doShowRentPlanDetailScheme(payMap);
		baseMap.put("ONEMONEY", (listDetail.get(0)).get("ZJ") + "");
		context.put("detailList", listDetail);
		context.put("FORMAT", UTIL.FORMAT);
		context.put("ywlx", new SysDictionaryMemcached().get("业务类型"));
		context.put("baseMap", baseMap);
		m.put("SCHEME_CODE", m.get("SCHEME_ID"));
		context.put("param", m);
		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		m.put("SPID", Security.getUser().getOrg().getSpId());
		SuppliersService suppService = new SuppliersService();
		//context.put("suppliersList", suppService.querySuppByCom(m));
		if (eqlist != null && eqlist.size() > 0) {
			Map<String,Object> eqMap  = eqlist.get(0);
			if (eqMap != null && eqMap.get("WHETHER_SALES_SI") != null && !"".equals(eqMap.get("WHETHER_SALES_SI").toString())) {
				m.put("WHETHER_SALES_SI", eqMap.get("WHETHER_SALES_SI"));
				context.put("suppliersList", suppService.querySuppByCgxl(m));
			}
		}
		List<Object> whetherSalesSi = (List)new DataDictionaryMemcached().get("采购类型");
		context.put("whetherSalesSi", whetherSalesSi);
		
		
		context.put("SCHEME_ROW_NUM",m.get("SCHEME_ROW_NUM") );
		context.put("PROJECT_ID",m.get("PROJECT_ID") );
		context.put("SCHEME_ID",m.get("SCHEME_ID") );
		
		return new ReplyHtml(VM.html("bpm/updateProjectChange.vm", context));
	}
	
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax saveProjectChange(){
		Map<String, Object> param = _getParameters();
		ProChangeService service = new ProChangeService();
		int res1 = service.updatePro(param);
		int res2 = service.updateEquipment(param);
		
		//将页面上的所有信息保存在fil_project_scheme_change中---by tianhui
		int ress = service.saveUpdateProject(param);
		
		StringBuffer strBuffer = new StringBuffer();
		BaseSchemeService bsService = new BaseSchemeService();
		List<Map<String, Object>> schemeList = bsService.getSchemeClob(null, param.get("PROJECT_ID") + "", param.get("SCHEME_ROW_NUM") + "");
		for(int i=0;i<schemeList.size();i++){
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("购置税")){
				schemeList.get(i).put("VALUE_STR", param.get("gzs"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("上牌费")){
				schemeList.get(i).put("VALUE_STR", param.get("spf"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("路桥费")){
				schemeList.get(i).put("VALUE_STR", param.get("lqf"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("临牌费")){
				schemeList.get(i).put("VALUE_STR", param.get("lpf"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("环保标费")){
				schemeList.get(i).put("VALUE_STR", param.get("hbbf"));
			}
			if(schemeList.get(i).get("KEY_NAME_ZN").toString().equals("其他费用")){
				schemeList.get(i).put("VALUE_STR", param.get("qtfy"));
			}
			strBuffer.append(schemeList.get(i).toString());
		}
		param.put("SCHEME_CLOB", JSONArray.fromObject(schemeList)+"");
		int res3 = service.updateScheme(param);
		
		boolean falg = false;
		if(res1 > 0 && res2 > 0 && res3 > 0 && ress>0){
			falg = true;
		}
		return new ReplyAjax(falg);
	}
	
	/**
	 * 
	 * @return
	 */
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax changeSchemeStatus(){
		Map<String, Object> param = _getParameters();
		ProChangeService pservice = new ProChangeService();
		param.put("FABGSQ_STATUS", "2");
		int res = pservice.changeSchemeStatus(param);
		boolean flag = false;
		if(res > 0){
			flag = true;
		}
		return new ReplyAjax(flag);
	}
}

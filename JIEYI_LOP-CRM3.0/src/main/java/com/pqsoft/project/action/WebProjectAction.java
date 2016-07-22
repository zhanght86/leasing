package com.pqsoft.project.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.project.service.SynSchemeToRentService;
import com.pqsoft.skyeye.api.*;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UtilDate;
import com.pqsoft.target.service.TargetService;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class WebProjectAction extends Action {
	private projectService service = new projectService();
	private SynSchemeToRentService synSchemeToRentService = new SynSchemeToRentService();

	@Override
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "新建申请"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> m = _getParameters();
		// 查询平台ID
		try{
			m.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
			m.put("MANAGER_NAME", Security.getUser().getOrg().getPlatform());
		}catch(Exception e){
			throw new ActionException("您不输入公司平台内部人员，无法创建项目");
		}

		// 区域（平台下的区域到省）
		context.put("AREAS", service.queryManagerArea(Security.getUser().getOrg().getPlatformId(), null, 2));
		//		// 区域（平台下的区域到省）
		if(StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())){
			//经销商登陆立项
			Map<String, Object> mapSup = new HashMap<>();
			mapSup.put("SUP_ID", Security.getUser().getOrg().getSuppId());//通过经销商查询出默认的省市
			mapSup.put("SUP_TYPE", "1");//经销商标示
			Map mapAreaSup = service.queryAreaSupMap(mapSup);
			context.put("mapAreaSup", mapAreaSup);
			
			//通过省查询市
			if(mapAreaSup!=null && mapAreaSup.get("PROV_ID")!=null && !mapAreaSup.get("PROV_ID").equals("")){
				List cityMap=service.queryManagerArea(Security.getUser().getOrg().getPlatformId(), mapAreaSup.get("PROV_ID") + "", 3);
				context.put("cityList", cityMap);
			}
			
			
//			Map<String,Object> mm = new HashMap<String, Object>();
//			mm.put("SUP_ID", Security.getUser().getOrg().getSuppId());
//			mm.put("SUP_NAME", Security.getUser().getOrg().getSuppName());
			
			// del gengchangbao JZZL-203 Start
			//经销商要根据采购类型级联出来
//			List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
//			lst.add(mm);
//			context.put("suppliersList", lst);
			// del gengchangbao JZZL-203 End
			
		}else if(StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())){
			//SP登陆立项
			Map<String, Object> mapSup=new HashMap<>();
			mapSup.put("SUP_ID", Security.getUser().getOrg().getSpId());//通过SP查询出默认的省市
			mapSup.put("SUP_TYPE", "2");//SP标示
			Map mapAreaSup=service.queryAreaSupMap(mapSup);
			context.put("mapAreaSup", mapAreaSup);
			
			//通过省查询市
			if(mapAreaSup!=null && mapAreaSup.get("PROV_ID")!=null && !mapAreaSup.get("PROV_ID").equals("")){
				List cityMap=service.queryManagerArea(Security.getUser().getOrg().getPlatformId(), mapAreaSup.get("PROV_ID") + "", 3);
				context.put("cityList", cityMap);
			}
			
			m.put("SPID", Security.getUser().getOrg().getSpId());
			// del gengchangbao JZZL-203 Start
			//经销商要根据采购类型级联出来
//			SuppliersService suppService = new SuppliersService();
//			context.put("suppliersList", suppService.querySuppByCom(m));
			// del gengchangbao JZZL-203 End
		}
//		else{
			// del gengchangbao JZZL-203 Start
			//经销商要根据采购类型级联出来
			// 通过平台查经销商
//			SuppliersService suppService = new SuppliersService();
//			context.put("suppliersList", suppService.querySuppByCom(m));
			// del gengchangbao JZZL-203 End
//		}
		
		// add gengchangbao 2016年6月12日15:45:10 JZZL-203 Start
		//经销商要根据采购类型级联出来
		@SuppressWarnings("unchecked")
		List<Object> whetherSalesSi = (List<Object>)new DataDictionaryMemcached().get("采购类型");
		context.put("whetherSalesSi", whetherSalesSi);
		// add gengchangbao 2016年6月12日15:45:10 JZZL-203 End
		/**
		 * 查询销售人员下拉框
		 * add by lishuo 11.13.2015
		 */
		Map<String, Object> idMap = new HashMap<>();
		idMap.put("id", Security.getUser().getOrg().getId());
		System.out.println("id ============="+Security.getUser().getOrg().getId());
		List mm = service.querySaller(idMap);
		context.put("Saller", mm);

		context.put("carColor", new DataDictionaryMemcached().get("汽车颜色"));
		
		context.put("custType", new DataDictionaryMemcached().get("客户类型"));
		//租赁物性质
		context.put("thing_xz", new DataDictionaryMemcached().get("租赁物性质"));
		
		//项目名称生成规则
		context.put("PROJECT_NAME_TIME",UtilDate.formatDate(new Date(),"yyMMddHHmm"));
		// 单位
		context.put("unitList", new DataDictionaryMemcached().get("设备单位"));

		// 通过平台查询出行业
		context.put("HangYeList", new BaseSchemeService().getFHFA_MANAGERSUBINFO(m.get("MANAGER_ID")+"", Security.getUser().getOrg().getPlatform(), "行业类型"));
//		默认业务类型为直租
		m.put("PLATFORM_TYPE", "1");
		// 业务类型
		context.put("YWLIST", new BaseSchemeService().getFHFA_MANAGERYW_ORG(m.get("MANAGER_ID")+"", Security.getUser().getOrg().getPlatform(), "业务类型", Security.getUser().getOrg().getId()));
		context.put("param", m);
		//add gengchangbao JZZL-205 2016年6月15日13:20:57 Start
		context.put("sysinfo", new BaseSchemeService().getSystemName(Security.getUser().getOrg().getId()));
		//add gengchangbao JZZL-205 2016年6月15日13:20:57 End
		return new ReplyHtml(VM.html("project/webProjectApply.vm", context));
	}
	
	/**
	 * 外部报价器保存
	 * @author King 2015年3月6日
	 * @return ajax
	 */
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply addSchemeForProject() {
		Map<String, Object> map = _getParameters();
		
		if(map.get("PLATFORM_TYPE") == null || map.get("PLATFORM_TYPE").equals("")){
//			List listPl=Util.DICTAG.getDataList("BUSINESS_TYPE", map.get("POB_ID").toString());
			Map mapPl = service.queryBusinMap(map);
			String VALUE_STR = mapPl.get("VALUE_STR") + "";
			String[] plaStr = VALUE_STR.split(",");
			map.put("PLATFORM_TYPE",plaStr[0]);
		}
		
		if(map.get("THING_KIND") == null || map.get("THING_KIND").equals("")){
			List listTh = Util.DICTAG.getDataList("租赁物性质", map.get("POB_ID").toString());
			if(listTh.size() > 0){
				Map mapTh = (Map)listTh.get(0);
				map.put("THING_KIND",mapTh.get("CODE"));
			}
		}
		//添加客户信息
		CustomersService cService = new CustomersService();
		String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
		Map<String, Object> m = new HashMap<>();
		m.put("ID", ID);
		m.put("USERID", Security.getUser().getId());
		m.put("USERCODE",Security.getUser().getCode());
		m.put("USERNAME",Security.getUser().getName());
		String CUST_ID = CodeService.getCode("客户编号", ID, null);
		m.put("CUST_ID", CUST_ID);
	    m.put("CUST_NAME", map.get("CLIENT_NAME"));
	    m.put("TYPE", map.get("CLIENT_TYPE"));
	    m.put("TEL_PHONE", map.get("CLIENT_MOBILE"));
	    //临时客户状态
	    m.put("CUST_STATUS", "0");
		cService.doAddCust(m);
//			// 保存该客户第一手历史资料
//			service.doAddCustBaseINfo(m);
		
		map.put("CLIENT_ID", ID);
		// 新建项目表
		map.put("USERID", Security.getUser().getId());
		map.put("USERNAME", Security.getUser().getName());
//		/**** 销售型售后回租项目编号和 销售型直租一样 ****/
//		String PRO_CODE = "";
//		/***************************************************/
//		if ("4".equals(map.get("PLATFORM_TYPE").toString())
//				&& "back_leasing".equals(map.get("LEASE_MODEL").toString())) {
//			map.put("PARENT_ID",
//					service.getProjectId(map.get("PRO_CODE").toString()));
//			PRO_CODE = map.get("PRO_CODE").toString() + "_SH";
//
//		} else {
//			PRO_CODE = CodeService.getCode("项目编号", null, null);
//		}
//		map.put("PRO_CODE", PRO_CODE);
//		/****************************************************/
		/**
		 * add by lishuo 12.8.2015
		 * 增加销售人员
		 */
		map.put("SALE_NAME", map.get("SALE_NAME"));
		map.put("MARK", map.get("MARK"));
		service.projectAdd(map);
		// *********************************保存方案 start***********************/
		JSONObject schemeJson = JSONObject.fromObject(map.get("projectScheme"));
		schemeJson.put("MONTH_RENT", map.get("MONTH_RENT"));
		BaseSchemeService baseSchemeService = new BaseSchemeService();
		if(StringUtils.isBlank(schemeJson.get("PROJECT_ID")))
			schemeJson.put("PROJECT_ID", map.get("PROJECT_ID"));
		if(StringUtils.isBlank(schemeJson.get("SCHEME_ROW_NUM")))
			schemeJson.put("SCHEME_ROW_NUM", map.get("SCHEME_ROW_NUM"));
		//noinspection unchecked
		map.put("SCHEME_ID_SEQ", baseSchemeService.doAddProjectScheme(schemeJson));
		// *********************************保存方案 end***********************/

		PayTaskService.quoteCalculateSaveSCHEME(map);
		
//		// 保存银行信息
//		service.updateProjectByBankId(map);

//		// 保存担保人
//		JSONArray guaranjsonArray = JSONArray.fromObject(map.get("guaranList"));
//		for (Object object : guaranjsonArray) {
//			Map<String, Object> m = (Map<String, Object>) object;
//			m.put("PROJECT_ID", map.get("PRO_ID"));
//			m.put("USER_ID", Security.getUser().getId());
//			service.doAddGuaranProject(m);
//		}

		// 修改状态
		Map<String,Object> mapStatus = new HashMap<>();
		mapStatus.put("PROJECT_ID", map.get("PROJECT_ID"));
		mapStatus.put("STATUS", "0");
		mapStatus.put("SUP_ID", map.get("SUPPLIER_ID"));
//		String FIRSTPAYDATE=null;
//		if(map.containsKey("FIRSTPAYDATE")&&StringUtils.isNotBlank(map.get("FIRSTPAYDATE"))){
//			FIRSTPAYDATE=map.get("FIRSTPAYDATE").toString();
//			FIRSTPAYDATE=FIRSTPAYDATE.replace("/", "-");
//		}
		mapStatus.put("SQFKYD_DATE", map.get("FIRSTPAYDATE"));
		mapStatus.put("PLATFORM_TYPE", map.get("PLATFORM_TYPE"));
		mapStatus.put("ZKL", map.get("ZKL"));
		if(StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())){
			mapStatus.put("BUSINESS_SOURCE", Security.getUser().getOrg().getSuppId());
		}else if(StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())){
			mapStatus.put("BUSINESS_SOURCE", Security.getUser().getOrg().getSpId());
		}else{
			mapStatus.put("BUSINESS_SOURCE", map.get("SUPPLIERS_ID"));
		}
		System.out.println("===========" + mapStatus);
		service.updateProjectStatusById(mapStatus);

		// 查询客户ID
		Map<String,Object> mapCredit = Dao.selectOne("project.queryClentByID", map.get("PROJECT_ID"));
		if (mapCredit != null) {
			mapCredit.put("PROJECT_ID", map.get("PROJECT_ID"));
			// 先判断有没有数据，有的话就不insert
			int num = Dao.selectInt("project.doqueryCreateDate", mapCredit);
			if (num == 0) {
				Dao.insert("project.doCreateCredit", mapCredit);
			}
		}

		return new ReplyAjax(true,map.get("PROJECT_ID"),"").addOp(new OpLog("客户管理", "立项-创建项目", "创建项目：编号为："
				+ map.get("PRO_CODE")));
	}
	
	/**
	 * 校验指标公司
	 * @author King 2015年3月6日
	 * @return ajax
	 */
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply checkQUOTA(){
		return new ReplyAjax(new TargetService().toViewTargetList(_getParameters()));
	}


}

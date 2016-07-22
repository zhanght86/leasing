package com.pqsoft.project.action;

import java.io.File;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.pqsoft.project.service.SynSchemeToRentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.velocity.VelocityContext;
import org.jbpm.api.ProcessInstance;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.handler.StartSubBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.bpm.status.EquipmentSplit;
import com.pqsoft.bpm.status.ProjectHeadUpdateForJbpm;
import com.pqsoft.creditWind.service.CreditWindService;
import com.pqsoft.crm.service.splitPaymentService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.log.LogUtil;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.project.service.ProjectManagerPdf;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.skyeye.util.UtilDate;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.MyNumberTool;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class projectAction extends Action {
	public VelocityContext context = new VelocityContext();
	Map<String, Object> m = null;
	projectService service = new projectService();
	private SynSchemeToRentService synSchemeToRentService = new SynSchemeToRentService();

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	private void getPageParameter() {
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = SkyEye.getRequest().getParameter(enN.toString())
					.trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME", Security.getUser().getName());
		m.put("USERCODE", Security.getUser().getCode());
		m.put("USERID", Security.getUser().getId());
	}

	@Override
	public Reply execute() {
		return null;
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "电子合同导出", "列表显示" })
	public Reply project_Info_Main() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("project/project_Info_Main.vm", context));
	}

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doChooseProject() {
		// 筛选商务政策
		this.getPageParameter();
		return new ReplyHtml(VM.html("project/doChooseProject.vm", context));
	}

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply lodingDiv() {
		this.getPageParameter();
		// 查询平台ID
		try {
			m.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
			m.put("MANAGER_NAME", Security.getUser().getOrg().getPlatform());
		} catch (Exception e) {
			throw new ActionException("您不输入公司平台内部人员，无法创建项目");
		}
		// 区域（平台下的区域到省）
		context.put(
				"AREAS",
				service.queryManagerArea(Security.getUser().getOrg()
						.getPlatformId(), null, 2));
		if(StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())){
			//经销商登陆立项
			Map<String,Object> mapSup=new HashMap<String,Object>();
			mapSup.put("SUP_ID", Security.getUser().getOrg().getSuppId());//通过经销商查询出默认的省市
			mapSup.put("SUP_TYPE", "1");//经销商标示
			Map<String,Object> mapAreaSup=service.queryAreaSupMap(mapSup);
			context.put("mapAreaSup", mapAreaSup);
			
			//通过省查询市
			List<Object> cityMap=service.queryManagerArea(Security.getUser().getOrg().getPlatformId(), mapAreaSup.get("PROV_ID") + "", 3);
			context.put("cityList", cityMap);
			
			Map<String,Object> mm=new HashMap<String, Object>();
			mm.put("SUP_ID", Security.getUser().getOrg().getSuppId());
			mm.put("SUP_NAME", Security.getUser().getOrg().getSuppName());
			List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
			lst.add(mm);
			context.put("suppliersList", lst);
		}else if(StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())){
			//SP登陆立项
			Map<String,Object> mapSup=new HashMap<String,Object>();
			mapSup.put("SUP_ID", Security.getUser().getOrg().getSpId());//通过SP查询出默认的省市
			mapSup.put("SUP_TYPE", "2");//SP标示
			Map<String,Object> mapAreaSup=service.queryAreaSupMap(mapSup);
			context.put("mapAreaSup", mapAreaSup);
			
			//通过省查询市
			List<Object> cityMap=service.queryManagerArea(Security.getUser().getOrg().getPlatformId(), mapAreaSup.get("PROV_ID") + "", 3);
			context.put("cityList", cityMap);
			
			m.put("SPID", Security.getUser().getOrg().getSpId());
			SuppliersService suppService = new SuppliersService();
			context.put("suppliersList", suppService.querySuppByCom(m));
		}else{
			// 通过平台查经销商
			SuppliersService suppService = new SuppliersService();
			System.out.println(m+"=========================");
			context.put("suppliersList", suppService.querySuppByCom(m));
		}

		// 项目名称生成规则
		String custName = "";
		if (m != null && m.get("CUSTOMER_NAME") != null) {
			custName = m.get("CUSTOMER_NAME").toString();
		}
		context.put("PROJECT_NAME",
				custName + UtilDate.formatDate(new Date(), "yyMMddHHmm"));
		// 单位
		context.put("unitList", new DataDictionaryMemcached().get("设备单位"));

		// 通过平台查询出行业
		context.put(
				"HangYeList",
				new BaseSchemeService().getFHFA_MANAGERSUBINFO(
						m.get("MANAGER_ID") + "", Security.getUser().getOrg()
								.getPlatform(), "行业类型"));
		// 默认业务类型为直租
		m.put("PLATFORM_TYPE", "1");

		// 业务类型
		context.put("YWLIST", new BaseSchemeService().getFHFA_MANAGERYW(
				m.get("MANAGER_ID") + "", Security.getUser().getOrg()
						.getPlatform(), "业务类型"));
		// // 销售型售后回租模式--添加租赁模式，处理项目编号
		// Map<String,Object> conMap = new HashMap<String,Object>();
		// conMap.put("PLATFORM_TYPE", m.get("PLATFORM_TYPE"));
		// if ("4".equals(m.get("PLATFORM_TYPE"))) {
		// conMap.put("PRO_CODE", m.get("PRO_CODE"));
		// }
		// conMap.put("LEASE_MODEL", m.get("LEASE_MODEL"));
		// context.put("conMap", conMap);

		context.put("param", m);

		// 获取评分！--------------------------------------------------------------------获取评分--YangJ--2014年5月8日15:41:11
		Map<String, Object> m1 = service.queryScoreAdd(m.get("CUSTOMER_ID")
				.toString());

		float i1 = 0f;// 信用分数 都是原始表中的分数
		float i2 = Float.parseFloat(m1.get("CUST_SCORE").toString());// 客户分数
		float i3 = 0f;// 供应商分数
		float i4 = 0f;// 产品分数

		List<Map<String, String>> datadic = new DataDictionaryMemcached()
				.get("项目打分评估系数");// 数据字典分数百分比配置

		float projscore = 0;// 信用分数
		float custscore = 0;// 客户分数
		float supscore = 0;// 供应商分数
		float prodscore = 0;// 产品分数

		for (Map<String, String> temp : datadic) {
			if (temp.get("CODE").equals("1"))
				custscore = Integer.parseInt(temp.get("SHORTNAME")) * i2 / 100;// 客户分数
			else if (temp.get("CODE").equals("2"))
				supscore = Integer.parseInt(temp.get("SHORTNAME")) * i3 / 100;// 供应商分数
			else if (temp.get("CODE").equals("4"))
				projscore = Integer.parseInt(temp.get("SHORTNAME")) * i1 / 100;// 信用分数
			else if (temp.get("CODE").equals("5"))
				prodscore = Integer.parseInt(temp.get("SHORTNAME")) * i4 / 100;// 产品分数
			else
				System.out.println("错误！！没有取到数据字典CODE码");
		}
		System.out.println(projscore+"=="+custscore+"=="+supscore+"=="+prodscore);
		Map<String, Object> DD = new HashMap<String, Object>();// 数据字典
		for (Map<String, String> obj1 : datadic) {
			if (obj1.get("CODE").toString().equals("1"))
				DD.put("custscore", Integer.parseInt(obj1.get("SHORTNAME")));// 客户分数
			else if (obj1.get("CODE").toString().equals("2"))
				DD.put("supscore", Integer.parseInt(obj1.get("SHORTNAME")));// 供应商分数
			else if (obj1.get("CODE").toString().equals("4"))
				DD.put("projscore", Integer.parseInt(obj1.get("SHORTNAME")));// 信用分数
			else if (obj1.get("CODE").toString().equals("5"))
				DD.put("prodscore", Integer.parseInt(obj1.get("SHORTNAME")));// 产品分数
			else
				System.out.println("错误！！没有取到数据字典CODE码");
		}
		context.put("DD", DD);

		Map<String, Object> score = new HashMap<String, Object>();
		score.put("ALL_SCORE", projscore + custscore + supscore + prodscore);
		score.put("PROJ_SCORE", projscore);// 信用分数
		score.put("CUST_SCORE", custscore);// 客户分数
		score.put("SUP_SCORE", supscore);// 供应商分数
		score.put("PROD_SCORE", prodscore);// 产品分数
		context.put("SCORE", score);
		// -------------------------------------------------------------------------获取评分结束------
		// 开户行账号类型
		context.put("account_type",
				new DataDictionaryMemcached().get("开户行账号类型"));
		// 开户行账号客户名称
		List<Map<String, Object>> final_Cust_List = new ArrayList<Map<String, Object>>();
		// // 查询组织架构或者身份证号
		Map<String, Object> mapCard = service.doCardByClent(m
				.get("CUSTOMER_ID").toString());
		if(mapCard ==null){
			mapCard=new HashMap<String,Object>();
		}
		mapCard.put("CUST_ID", m.get("CUSTOMER_ID"));
		mapCard.put("CUST_NAME", custName);
		final_Cust_List.add(mapCard);
		context.put("final_Cust_List", final_Cust_List);

		//读取该客户的资产档案的设备档案中的售后回租设备
		context.put("listDASB", service.doListDASB(m.get("CUSTOMER_ID").toString()));
		
//		return new ReplyHtml(VM.html("project/addCustProject.vm", context));
		return new ReplyHtml(VM.html("project/addCustProject_a.vm", context));
	}

	/**
	 * 根据客户ID，查询客户地址信息
	 * 同步客户区域
	 * @return
	 */
	@aDev(code="150002",email="zhangzhenlei@pqsoft.cn",name="张振雷")
	@aAuth(type=aAuthType.LOGIN)
	public Reply getCustomerArea(){
		Map<String, Object> param = _getParameters();
		Map<String, Object> map = service.queryCustProAndCity(param);
		return new ReplyJson(JsonUtil.toJson(map));
	}
	
	
	
	/**
	 * 加载方案
	 * 
	 * @return
	 * @author King 2015年2月27日
	 */
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply projectShowSchemeApply() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();

		if (map.get("POB_ID") != null) {
			BaseSchemeService baseSchService = new BaseSchemeService();
			List<Map<String, Object>> SCHEMEDETAIL = baseSchService
					.doSelectSchemeDetailByName(map.get("POB_ID") + "", "0",
							"1");
			if (StringUtils.isNotBlank(SCHEMEDETAIL) && SCHEMEDETAIL.size() > 0)
				map.put("SCHEME_NAME", SCHEMEDETAIL.get(0).get("SCHEME_NAME")
						+ "" + SCHEMEDETAIL.get(0).get("ALIASES"));
			context.put("SCHEMEDETAIL", SCHEMEDETAIL);
			//modify by lishuo 12.17.2015 客户比列带出  ,其他费用(03-15-2016) Strat
			for(Map<String, Object> itm : SCHEMEDETAIL){
				if("DEPOSIT_PERCENT".equals(itm.get("KEY_NAME_EN"))){
					context.put("DEPOSIT_PERCENT_VAL",itm.get("VALUE_STR"));
					continue;
				}else if("QITA_MONEY".equals(itm.get("KEY_NAME_EN"))){
					context.put("QITA_MONEY_VAL",itm.get("VALUE_STR"));
					continue;
				}
			}
			//modify by lishuo 12.17.2015 客户比列带出 ,其他费用(03-15-2016)  End
			Map<String,Object> em=baseSchService.queryOneElementByCode(map.get("POB_ID")+"", "租赁周期");
			if(StringUtils.isNotBlank(em)&& !em.isEmpty()){
				context.put("ZQ", new SysDataDictionaryService().queryDataDictionaryByTypeAndCodes("租赁周期", em.get("VALUE_STR")+""));
			}
//			context.put("ZQ", Util.DICTAG.getDataList("租赁周期",map.get("POB_ID")+""));
			/************************ 查询商务政策对应的利率规则 King 2014-08-21 **********************************************/
			Map<String, Object> maprate = new HashMap<String, Object>();
			maprate.put("SCHEME_CODE", map.get("POB_ID"));
			// 年利率
			map.put("rateList",
					baseSchService.doSelectBaseSchemeYearRate(maprate));
			// 手续费
			map.put("feeList",
					baseSchService.doSelectBaseSchemeFeeRate(maprate));
			/**********************************************************************/
			// 指标公司
//			context.put("QUOTALIST", baseSchService.querysp("5"));
			//合作金融机构
			context.put("COOPERATION_AGENCY", baseSchService.querysp("6"));
		}
		map.put("SCHEME_CODE", map.get("POB_ID"));
		context.put("param", map);
		// 数据字典工具
		context.put("dicTag", Util.DICTAG);
//		// 查询标准收益率
//		double SYL_BZ_VALUE = 5d;
//		List<Map<String, Object>> SYL_BZ = new SysDictionaryMemcached()
//				.get("收益率-标准");
//		if (SYL_BZ.size() > 0) {
//			Map<String, Object> SYL_BZ_map = SYL_BZ.get(0);
//			SYL_BZ_VALUE = Double
//					.parseDouble(SYL_BZ_map.get("FLAG").toString()) * 100;
//		}
//		context.put("SYL_BZ_VALUE", SYL_BZ_VALUE);
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
		context.put("ZLQS", new SysDictionaryMemcached().get("租赁期数"));
		return new ReplyHtml(VM.html("project/projectShowSchemeApply.vm",
				context));
	}
	

	@aPermission(name = { "业务管理", "业务管理", "追加方案(新建方案)" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply appendScheme() {
		this.getPageParameter();
		// 查询平台ID
		try{
			m.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
			m.put("MANAGER_NAME", Security.getUser().getOrg().getPlatform());
		}catch(Exception e){
			throw new ActionException("您不输入公司平台内部人员，无法创建项目");
		}
		
		if(StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())){
			//经销商登陆立项
			Map<String,Object> mapSup=new HashMap<String,Object>();
			mapSup.put("SUP_ID", Security.getUser().getOrg().getSuppId());//通过经销商查询出默认的省市
			mapSup.put("SUP_TYPE", "1");//经销商标示
			Map<String,Object> mapAreaSup=service.queryAreaSupMap(mapSup);
			context.put("mapAreaSup", mapAreaSup);
			
			//通过省查询市
			if(mapAreaSup!=null && mapAreaSup.get("PROV_ID")!=null && !mapAreaSup.get("PROV_ID").equals("")){
				List<Object> cityMap=service.queryManagerArea(Security.getUser().getOrg().getPlatformId(), mapAreaSup.get("PROV_ID") + "", 3);
				context.put("cityList", cityMap);
			}
			
			
			Map<String,Object> mm=new HashMap<String, Object>();
			mm.put("SUP_ID", Security.getUser().getOrg().getSuppId());
			mm.put("SUP_NAME", Security.getUser().getOrg().getSuppName());
			List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
			lst.add(mm);
			context.put("suppliersList", lst);
		}else if(StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())){
			//SP登陆立项
			Map<String,Object> mapSup=new HashMap<String,Object>();
			mapSup.put("SUP_ID", Security.getUser().getOrg().getSpId());//通过SP查询出默认的省市
			mapSup.put("SUP_TYPE", "2");//SP标示
			Map<String,Object> mapAreaSup=service.queryAreaSupMap(mapSup);
			context.put("mapAreaSup", mapAreaSup);
			
			//通过省查询市
			if(mapAreaSup!=null && mapAreaSup.get("PROV_ID")!=null && !mapAreaSup.get("PROV_ID").equals("")){
				List<Object> cityMap=service.queryManagerArea(Security.getUser().getOrg().getPlatformId(), mapAreaSup.get("PROV_ID") + "", 3);
				context.put("cityList", cityMap);
			}
			
			m.put("SPID", Security.getUser().getOrg().getSpId());
			SuppliersService suppService = new SuppliersService();
			context.put("suppliersList", suppService.querySuppByCom(m));
		}else{
			// 通过平台查经销商
			SuppliersService suppService = new SuppliersService();
			context.put("suppliersList", suppService.querySuppByCom(m));
		}
				// 通过平台查厂商
		context.put("CompanyList", new CompanyService().queryCompanyList(m));

		// 单位
		context.put("unitList", new DataDictionaryMemcached()
		.get("设备单位"));

		// 项目信息
		Map<String, Object> projectMap = service.queryProjectByIdAppend(m);
		m.putAll(projectMap);

		// 通过平台查询出行业
		List<Map<String, Object>> HangYeList = new BaseSchemeService()
				.getFHFA_MANAGERSUBINFO(m.get("MANAGER_ID") + "", Security
						.getUser().getOrg().getPlatform(), "行业类型");
		// List HangYeList = service.HangYeList(m);
		context.put("HangYeList", HangYeList);

		// 区域（平台下的区域到省）
		context.put("AREAS", service.queryManagerArea(Security.getUser().getOrg().getPlatformId(),null, 2));
		
		context.put("param", m);

		// 获取业务类型！--------------------------------------------------------------------获取评分--YangJ--2014年5月8日15:41:11

		context.put(
				"Bus_Type",
				service.QueryBusType(m.get("PROJECT_ID").toString())
						.get("PLATFORM_TYPE").toString());

		// 获取评分！--------------------------------------------------------------------获取评分--YangJ--2014年5月8日15:41:11
		if (!m.containsKey("PROJECT_ID"))
			System.out.println("M全局变量没有取到project id！");
		System.out.println("PROJECT_ID:::" + m.get("PROJECT_ID").toString());
		Map<String,Object> m1 = service.queryScore(m.get("PROJECT_ID").toString(), "", "");

		float i1 = Float.parseFloat(m1.get("PROJ_SCORE").toString());// 信用分数
																		// 都是原始表中的分数
		float i2 = Float.parseFloat(m1.get("CUST_SCORE").toString());// 客户分数
		float i3 = Float.parseFloat(m1.get("SUP_SCORE").toString());// 供应商分数
		float i4 = Float.parseFloat(m1.get("PROD_SCORE").toString());// 产品分数

		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();

		List<Map<String,String>> datadic = DDMservice.get("项目打分评估系数");// 数据字典分数百分比配置

		float projscore = 0;// 信用分数
		float custscore = 0;// 客户分数
		float supscore = 0;// 供应商分数
		float prodscore = 0;// 产品分数

		for (Map<String, String> temp : datadic) {
			if (temp.get("CODE").equals("1"))
				custscore = Integer.parseInt(temp.get("SHORTNAME")) * i2 / 100;// 客户分数
			else if (temp.get("CODE").equals("2"))
				supscore = Integer.parseInt(temp.get("SHORTNAME")) * i3 / 100;// 供应商分数
			else if (temp.get("CODE").equals("4"))
				projscore = Integer.parseInt(temp.get("SHORTNAME")) * i1 / 100;// 信用分数
			else if (temp.get("CODE").equals("5"))
				prodscore = Integer.parseInt(temp.get("SHORTNAME")) * i4 / 100;// 产品分数
			else
				System.out.println("错误！！没有取到数据字典CODE码");
		}

		Map<String, Object> DD = new HashMap<String, Object>();// 数据字典
		for (Map<String, String> obj1 : datadic) {
			if (obj1.get("CODE").toString().equals("1"))
				DD.put("custscore", Integer.parseInt(obj1.get("SHORTNAME")));// 客户分数
			else if (obj1.get("CODE").toString().equals("2"))
				DD.put("supscore", Integer.parseInt(obj1.get("SHORTNAME")));// 供应商分数
			else if (obj1.get("CODE").toString().equals("4"))
				DD.put("projscore", Integer.parseInt(obj1.get("SHORTNAME")));// 信用分数
			else if (obj1.get("CODE").toString().equals("5"))
				DD.put("prodscore", Integer.parseInt(obj1.get("SHORTNAME")));// 产品分数
			else if ("6".equals(obj1.get("CODE")))
				DD.put("dbrscore", Integer.parseInt(obj1.get("SHORTNAME"))); // 担保人评分
			else
				System.out.println("错误！！没有取到数据字典CODE码");
		}
		context.put("DD", DD);

		Map<String,Object> score = new HashMap<String,Object>();
		score.put("ALL_SCORE", projscore + custscore + supscore + prodscore);
		score.put("PROJ_SCORE", projscore);// 信用分数
		score.put("CUST_SCORE", custscore);// 客户分数
		score.put("SUP_SCORE", supscore);// 供应商分数
		score.put("PROD_SCORE", prodscore);// 产品分数
		System.out.println("ACTION:输出：总分：" + score.get("ALL_SCORE") + "信用分数"
				+ score.get("PROJ_SCORE") + "客户分数" + score.get("CUST_SCORE")
				+ "供应商分数" + score.get("SUP_SCORE") + "产品分数"
				+ score.get("PROD_SCORE"));
		context.put("SCORE", score);
		System.out.println("context" + context.get("SCORE"));
		// -------------------------------------------------------------------------获取评分结束------
		context.put("YELX", new SysDictionaryMemcached().get("业务类型"));
		
		System.out.println("---------------m="+m);
		//读取该客户的资产档案的设备档案中的售后回租设备
		context.put("listDASB", service.doListDASB(m.get("CLIENT_ID").toString()));
		
		return new ReplyHtml(VM.html("project/appendCustProject.vm", context));
	}

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryComType() {
		return new ReplyJson(JSONArray.fromObject(new CompanyService().queryCompanyBySupId(_getParameters().get("SUPPLIER_ID")+"",Security.getUser().getOrg().getPlatformId())));
	}
	
	//add gengchangbao JZZL-203 2016年6月13日10:14:48 start
	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "changbaogeng@huashenghaoche.com", name = "耿长宝")
	@aAuth(type = aAuthType.LOGIN)
	public Reply querySuppByCgxl() {
		Map<String,Object> m=_getParameters();
		//add gengchangbao JZZL-231 2016年6月27日15:34:49 Start
		m.put("ORG_ID", Security.getUser().getOrg().getId());
		m.put("ORG_NAME", "分部");
		//查询分公司信息
		String branch = Dao.selectOne("sys.getOrgCompanyID", m);
		m.put("FGSID", branch);
		//add gengchangbao JZZL-231 2016年6月27日15:34:49 End
		m.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		m.put("MANAGER_NAME", Security.getUser().getOrg().getPlatform());
		
		return new ReplyJson(JSONArray.fromObject(new SuppliersService().querySuppByCgxl(m)));
	}
	//add gengchangbao JZZL-203 2016年6月13日10:14:48 end

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryThingType() {
		this.getPageParameter();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (m != null && m.get("COMPANY_ID") != null) {
			list = service.queryProductByCompId(m);
		}
		return new ReplyJson(JSONArray.fromObject(list));
	}

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getFilSystemCity() {
		System.out.println("a===============" + _getParameters());
		return new ReplyJson(JSONArray.fromObject(service.queryManagerArea(
				Security.getUser().getOrg().getPlatformId(), _getParameters()
						.get("AREA_ID") + "", 3)));
	}
	

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryCatenaByEqID() {
		this.getPageParameter();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (m != null && m.get("PRODUCT_ID") != null) {
			list = service.queryCatenaByEqID(m);
		}

		return new ReplyJson(JSONArray.fromObject(list));
	}

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply querySpecByEqID() {
		this.getPageParameter();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (m != null && m.get("CATENA_ID") != null) {
			list = service.querySpecByEqID(m);
		}
		return new ReplyJson(JSONArray.fromObject(list));
	}

	@SuppressWarnings("unchecked")
	@aPermission(name = { "业务管理", "业务管理", "追加方案(新建方案)" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply projectAppendForm() {
		Map<String, Object> m = _getParameters();
		if (m.get("ChangeViewData") != null
				&& !m.get("ChangeViewData").equals("")) {
			JSONObject map = JSONObject.fromObject(m.get("ChangeViewData"));
			// 项目信息
			Map<String,Object> projectMap = service.queryProjectById(map);
			if (projectMap != null) {
				map.putAll(projectMap);
			}
			map.put("USERID", Security.getUser().getId());
			service.projectAppend(map); // 保存方案
			if (map != null && map.get("EqList") != null) {

				List<Map<String, Object>> eqList = (List) map.get("EqList");

				context.put("eqList", eqList);

				BaseSchemeService baseSchService = new BaseSchemeService();
				// 商务政策
				if (map.get("POB_ID") != null) {
					List<Map<String, Object>> SCHEMEDETAIL = baseSchService
							.doSelectSchemeDetailByName(map.get("POB_ID") + "",
									"0", "1");
					map.put("SCHEME_NAME",
							SCHEMEDETAIL.get(0).get("SCHEME_NAME") + ""
									+ SCHEMEDETAIL.get(0).get("ALIASES"));
					context.put("SCHEMEDETAIL", SCHEMEDETAIL);
				}

				// 担保人信息
				List<Map<String,Object>> GuaranList = service.queryGuaranByProjectID(map);
				context.put("GuaranList", GuaranList);

				Map<String,Object> maprate = new HashMap<String,Object>();
				maprate.put("SCHEME_NAME", map.get("POB_ID"));
				maprate.put("SCHEME_CODE", map.get("POB_ID"));
				// 年利率
				map.put("rateCount",
						baseSchService.doSelectBaseSchemeYearRateCount(maprate));
				map.put("rateList",
						baseSchService.doSelectBaseSchemeYearRate(maprate));

				// 手续费
				map.put("feeCount",
						baseSchService.doSelectBaseSchemeFeeRateCount(maprate));
				map.put("feeList",
						baseSchService.doSelectBaseSchemeFeeRate(maprate));

				map.put("SCHEME_CODE", map.get("POB_ID"));
				context.put("param", map);
			}

		}

		// 查询标准收益率
		double SYL_BZ_VALUE = 5d;
		List SYL_BZ = new DataDictionaryService()
				.queryDataDictionaryByScheme("收益率-标准");
		if (SYL_BZ.size() > 0) {
			Map SYL_BZ_map = (Map) SYL_BZ.get(0);
			SYL_BZ_VALUE = Double
					.parseDouble(SYL_BZ_map.get("FLAG").toString()) * 100;
		}

		context.put("SYL_BZ_VALUE", SYL_BZ_VALUE);
		context.put("dicTag", Util.DICTAG);
		return new ReplyHtml(VM.html("project/project_mainAppend.vm", context))
				.addOp(new OpLog("客户管理", "立项-创建项目", "创建项目错误"));
	}

	@aPermission(name = { "业务管理", "业务管理", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getDataList() {
		this.getPageParameter();
		//加入用户判断是否是admin
		String userName=Security.getUser().getName();
		context.put("userName", userName);
		//add by lishuo 01-06-2016 Start
		//context.put("list", new SysDictionaryMemcached().get("项目状态位"));
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("STATUS", "-2");
		map.put("TYPE", "捷众流程状态");
		context.put("list", new DataDictionaryService().getDataTypeInfo(map));
		/*context.put("PLATFORMTYPEList",
				new SysDictionaryMemcached().get("业务类型"));*/
		//add by lishuo 01-06-2016 End
		List channelTypeList = DataDictionaryMemcached.getList("渠道类型");
		context.put("channelTypeList", channelTypeList);
		context.put("PContext", m);
		context.put("isDelAuth",
				Security.hasPermission(new String[] { "项目管理", "项目一览", "项目删除" }));
		return new ReplyHtml(VM.html("project/project_Manager.vm", context));
	}

	// 权限待定
	@aPermission(name = { "业务管理", "业务管理", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getDataListForContract() {
		this.getPageParameter();
		context.put("list", new SysDictionaryMemcached().get("项目状态位"));
		context.put("PLATFORMTYPEList",
				new SysDictionaryMemcached().get("业务类型"));
		context.put("PContext", m);
		context.put("isDelAuth",
				Security.hasPermission(new String[] { "项目管理", "项目一览", "项目删除" }));
		return new ReplyHtml(VM.html("project/project_Manager_contract.vm",
				context));
	}

	@aPermission(name = { "业务管理", "业务管理", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pageAjax() {
		Map<String, Object> param = _getParameters();
		BaseUtil.getDataAllAuth(param);
		Page page = service.queryPage(param);
		return new ReplyAjaxPage(page);
	}

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryBussPol() {
		Map<String,Object> param=_getParameters();
		String dd=param.get("PRODUCT_ID")+"";
		String COMPANY_ID=param.get("COMPANY_ID")+"";
		String SUPPLIER_ID=param.get("SUPPLIER_ID")+"";
		String CATENA_ID=param.get("CATENA_ID")+"";
		String PRODUCT_TYPE_ID=param.get("PRODUCT_TYPE_ID")+"";
		String THING_KIND=param.get("THING_KIND")+"";
		param.put("PRODUCT_ID", dd.replace(",",",%"));
		param.put("COMPANY_ID", COMPANY_ID.replace(",",",%"));
		param.put("SUPPLIER_ID", SUPPLIER_ID.replace(",",",%"));
		param.put("CATENA_ID", CATENA_ID.replace(",",",%"));
		param.put("PRODUCT_TYPE_ID", PRODUCT_TYPE_ID.replace(",",",%"));
		param.put("THING_KIND", THING_KIND.replace(",",",%"));
		return new ReplyAjax(JSONArray.fromObject(new BaseSchemeService()
				.querySchemeInfoList(param, "WEB")));
	}

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply addSchemeForProject() {
		Map<String, Object> map = _getParameters();
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
		service.projectAdd(map);

//		String AFFILIATED_COMPANY = "";
		// 担保方式 免担保 租赁公司代办 本地担保公司 第三方法人担保 一单一议
		String GUARANTEE = "1assuretype";
		// 首期付款方式 1 网银 2非网银
		String FIRST_PAYMENT_TYPE = "2";
		// 租金付款方式 1 网银 2非网银
		String RENT_PAYMENT_TYPE = "2";
		// 放款政策 1 全额 2差额 3部分差额
		String PAYMENT_STATUS = "1";
		// 监控设备
		String MONITORING_EQUIPMENT = "EquipmentMonitor.1";
		// 牌抵挂
		String PLEDGE_STATUS = "5ImpawnConcern";

		// 扫描件开机动车票
		String SCAN_TICKET = "0";
		// *********************************保存方案 start***********************/
		JSONObject schemeJson = JSONObject.fromObject(map.get("projectScheme"));
		BaseSchemeService baseSchemeService = new BaseSchemeService();
		if (StringUtils.isBlank(schemeJson.get("PROJECT_ID")))
			schemeJson.put("PROJECT_ID", map.get("PROJECT_ID"));
		if (StringUtils.isBlank(schemeJson.get("SCHEME_ROW_NUM")))
			schemeJson.put("SCHEME_ROW_NUM", map.get("SCHEME_ROW_NUM"));

		String SCHEME_ID_SEQ = baseSchemeService.doAddProjectScheme(schemeJson);
		// *********************************保存方案 end***********************/

		// 发票开具类型
		if (map.get("INVOICE_TARGET_TYPE") != null
				&& !map.get("INVOICE_TARGET_TYPE").equals("")) {
			String INVOICE_TARGET_TYPE = map.get("INVOICE_TARGET_TYPE")
					.toString();
			if (INVOICE_TARGET_TYPE.equals("1")) {
				map.put("INVOICE_TARGET_ID", map.get("CLIENT_ID"));
			} else if (INVOICE_TARGET_TYPE.equals("2")) {
//				map.put("INVOICE_TARGET_ID", map.get("FINAL_CUST_ID"));
				map.put("INVOICE_TARGET_ID", map.get("CLIENT_ID"));
			} else if (INVOICE_TARGET_TYPE.equals("3")) {
//				map.put("INVOICE_TARGET_ID", map.get("SUPPLIER_ID"));
				map.put("INVOICE_TARGET_ID", map.get("CLIENT_ID"));
			} else if (INVOICE_TARGET_TYPE.equals("4")) {
//				map.put("INVOICE_TARGET_ID", map.get("COMPANY_ID"));
				map.put("INVOICE_TARGET_ID", map.get("CLIENT_ID"));
			} else if (INVOICE_TARGET_TYPE.equals("5")) {
//				map.put("INVOICE_TARGET_ID", AFFILIATED_COMPANY);// 挂靠公司以后再改
				map.put("INVOICE_TARGET_ID", map.get("CLIENT_ID"));
			} else if (INVOICE_TARGET_TYPE.equals("6")) {
				map.put("INVOICE_TARGET_ID", map.get("CLIENT_ID"));// 担保公司以后再改
			} else {
				map.put("INVOICE_TARGET_ID", map.get("CLIENT_ID"));// 担保公司以后再改
			}
		}

		// 保存银行信息
		service.updateProjectByBankId(map);

		// 保存担保人
		JSONArray guaranjsonArray = JSONArray.fromObject(map.get("guaranList"));
		for (Object object : guaranjsonArray) {
			Map<String, Object> m = (Map<String, Object>) object;
			m.put("PROJECT_ID", map.get("PRO_ID"));
			m.put("USER_ID", Security.getUser().getId());
			service.doAddGuaranProject(m);
		}

		// 保存联合融资公司（只有为联合租赁时候才有值）
		JSONArray fljsonArray = JSONArray.fromObject(map.get("flList"));
		for (Object object : fljsonArray) {
			Map<String, Object> m = (Map<String, Object>) object;
			m.put("PROJECT_ID", map.get("PRO_ID"));
			service.doAddFlProject(m);
		}

		map.put("SCHEME_ID_SEQ", SCHEME_ID_SEQ);
		// PayTaskService.quoteCalculateSave(map);
		PayTaskService.quoteCalculateSaveSCHEME(map);
		// 更新项目设备表还款计划编号
		// service.updateProEquPayCodeByProId(map.get("PRO_ID") + "");
		// 更新设备表保险费用
		// service.doUpdateAverageInsuranceByProjectId(map.get("PRO_ID") + "");
		// service.doUpdateEqAffiliatedCompany(AFFILIATED_COMPANY,
		// map.get("PRO_ID") + "");

		// 修改状态
		Map<String, Object> mapStatus = new HashMap<String, Object>();
		mapStatus.put("PROJECT_ID", map.get("PRO_ID"));
		mapStatus.put("STATUS", "0");
		try {
			mapStatus.put(
					"INVOICE_METHOD",
					new DataDictionaryService()
							.queryDataDictionaryByTypeAndCodes("租金开票方式",
									map.get("RENT_WAY_INVOICE") + "").get(0)
							.get("FLAG"));
		} catch (Exception e) {
		}
		mapStatus.put("SUP_ID", map.get("SUPPLIER_ID"));
		mapStatus.put("SQFKYD_DATE", map.get("FIRSTPAYDATE"));
		mapStatus.put("FIRST_PAYMENT_TYPE", FIRST_PAYMENT_TYPE);
		mapStatus.put("RENT_PAYMENT_TYPE", RENT_PAYMENT_TYPE);
		mapStatus.put("PAYMENT_STATUS", PAYMENT_STATUS);
		mapStatus.put("GUARANTEE", GUARANTEE);
		mapStatus.put("MONITORING_EQUIPMENT", MONITORING_EQUIPMENT);
		mapStatus.put("PLEDGE_STATUS", PLEDGE_STATUS);
		mapStatus.put("SCAN_TICKET", SCAN_TICKET);
		mapStatus.put("ZKL", map.get("ZKL"));
		mapStatus.put("PLATFORM_TYPE", map.get("PLATFORM_TYPE"));
		if(StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())){
			mapStatus.put("BUSINESS_SOURCE", Security.getUser().getOrg().getSuppId());
		}else if(StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())){
			mapStatus.put("BUSINESS_SOURCE", Security.getUser().getOrg().getSpId());
		}
		service.updateProjectStatusById(mapStatus);
		// new
		// EquipmentSplit().projectAppendEqComSupInfoByProjectId(map.get("PRO_ID"));

		// 分期回购模式计算分期模式单价
		if ("3".equals(map.get("PROJECT_MODEL") + "")) {
			new EquipmentSplit().updateAveragePriceLX(map);
		}

		// 查询客户ID
		Map<String, Object> mapCredit = Dao.selectOne("project.queryClentByID",
				map.get("PRO_ID"));
		if (mapCredit != null) {
			mapCredit.put("PROJECT_ID", map.get("PRO_ID"));
			// 先判断有没有数据，有的话就不insert
			int num = Dao.selectInt("project.doqueryCreateDate", mapCredit);
			if (num == 0) {
				Dao.insert("project.doCreateCredit", mapCredit);
			}
		}

		return new ReplyAjax(true, map.get("PROJECT_ID"), "").addOp(new OpLog(
				"客户管理", "立项-创建项目", "创建项目：编号为：" + map.get("PRO_CODE")));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply addSchemeForPayProject() {
		Map<String, Object> map = _getParameters();
		JSONObject schemeJson = JSONObject.fromObject(map.get("projectScheme"));
		JSONArray EqList=JSONArray.fromObject(map.get("EqList"));
		schemeJson.put("EqList", EqList);
		schemeJson.put("SPLITPAY", "SPLITPAY");
		BaseSchemeService baseSchemeService = new BaseSchemeService();
		String SCHEME_ID=baseSchemeService.doAddProjectScheme1(schemeJson);
		splitPaymentService spservice=new splitPaymentService();
		spservice.autoCopyRent(schemeJson.get("PROJECT_ID").toString(), SCHEME_ID);
		return new ReplyAjax(true, map.get("PROJECT_ID"), "").addOp(new OpLog("项目管理", "拆分支付表", ""));
	}
	
	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply appendSchemeForProject() {
		Map<String, Object> map = _getParameters();
//		JSONArray jsonArray = JSONArray.fromObject(map.get("param"));

		// *********************************保存方案 start***********************/
		JSONObject schemeJson = JSONObject.fromObject(map.get("projectScheme"));
		JSONArray EqList=JSONArray.fromObject(map.get("EqList"));
		schemeJson.put("EqList", EqList);
		schemeJson.put("SPLITPAY", "SPLITPAY");
		BaseSchemeService baseSchemeService = new BaseSchemeService();
		String SCHEME_ID_SEQ = baseSchemeService.doAddProjectScheme1(schemeJson);
		Dao.update("project.projectUpdate", schemeJson);
		// *********************************保存方案 end***********************/

		map.put("SCHEME_ID_SEQ", SCHEME_ID_SEQ);
		PayTaskService.quoteCalculateSaveSCHEME(map);
		// // 分期回购模式计算分期模式单价
		if ("3".equals(map.get("PROJECT_MODEL") + "")) {
			new EquipmentSplit().updateAveragePriceLX(map);
		}

		return new ReplyAjax(true).addOp(new OpLog("客户管理", "立项-创建项目", "保存方案："
				+ map.get("PRO_CODE")));
	}

	/**
	 * 发起流程
	 * 
	 * @return
	 * @author:King 2013-11-24
	 */
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply startProjectStartByJbpm() {
		Map<String, Object> map = _getParameters();
		Map<String, Object> m=service.getJbpmId(map);
		if(m!=null && !"".equals(m.get("JBPM_ID").toString())){
			return new ReplyAjax(false, "","该项目已有流程");
		}
		Object PLATFORM_TYPE = map.get("PLATFORM_TYPE");
		// 如果为销售型售后回租,在添加项目中发起流程,则项目ID取父ID
		if (PLATFORM_TYPE != null && "4".equals(PLATFORM_TYPE.toString())) {
			String id = map.get("PRO_ID").toString();
			Map<String, Object> projectParentIdMap = service
					.selectProjectParentIdForSH(id);
			if (projectParentIdMap != null && projectParentIdMap.size() > 0)
				map.put("PRO_ID", projectParentIdMap.get("PARENT_ID"));
		}
//		String PROJECT_ID = map.get("PRO_ID") + "";
		// 刷新项目资料
		map.put("PROJECT_ID", map.get("PRO_ID"));
		// 项目信息
		Map<String, Object> projectDetails = service.selectProjectDetails(map);
		// 承租人详细信息
		Map<String, Object> renterDetails = service.selectRenterDetails(map);
		renterDetails.put("RENTER_NAME", renterDetails.get("NAME"));
		renterDetails.put("RENTER_CODE", renterDetails.get("CUST_CODE"));
		renterDetails.put("CUSTOMER_TYPE", renterDetails.get("TYPE"));
		renterDetails.put("PROJECT_ID", map.get("PRO_ID"));
		renterDetails.put("PRO_CODE", projectDetails.get("PRO_CODE"));
		List<Map<String, Object>> electronicPhotoAlbumData = service
				.electronicPhotoAlbumData(renterDetails);
//		List<Map<String, Object>> listFile = service.selectProjectFileData(map
//				.get("PRO_ID").toString());
		service.deleteProjectFile(renterDetails);
		for (int i = 0; i < electronicPhotoAlbumData.size(); i++) {
			Map<String, Object> dataMap = electronicPhotoAlbumData.get(i);
			dataMap.put("CLIENT_ID", renterDetails.get("ID"));
			dataMap.put("TPM_BUSINESS_PLATE", "立项");
			service.addProjectFile(dataMap);
		}
		/******************************************************************/
		// TODO 担保方式 模式免担保
//		JSONObject jsonObArray = JSONObject
//				.fromObject(map.get("projectScheme"));
//		JSONArray jsonArray = JSONArray.fromObject(jsonObArray
//				.get("SCHEME_CLOB"));
		String GUARANTEE = "1assuretype";
		/******************************************************************/

		// 通过项目ID查询出供应商ID
		Map<String,Object> SuppMap = service
				.querySuppByProjectID(map.get("PRO_ID").toString());
		// 判断供应商授信情况
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("PROJECT_ID", map.get("PRO_ID"));
		// 租金合计
		mm.put("MONEYALL", map.get("MONEYALL"));
		mm.put("COMPANY_ID", map.get("COMPANY_ID"));
		mm.put("SUP_ID", SuppMap.get("SUPPLIER_ID"));
		mm.put("CREDIT_TYPE", "0");
		mm.put("FINANCE_TOPRIC", map.get("FINANCE_TOPRIC"));
		mm.put("GUARANTEE", GUARANTEE);
		// Map<String, Object> credMap = new
		// CreditService().checkCreditMoney(mm);
		// //boolean creflag = Boolean.parseBoolean(credMap.get("flag") + "");
		// Boolean creflag = new
		// CreditAmountManagerService().checkCreditMoney(mm);
		// if (!creflag) {
		// throw new ActionException("请确认供应商授信额度是否过低");
		// }

		// //发起流程 需要确定是否需要展示多个流程图供选择
		List<String> list = JBPM.getDeploymentListByModelName("立项",
				projectDetails.get("PLATFORM_TYPE") + "", Security.getUser()
						.getOrg().getPlatformId());
		String pid = null;
		if (list.size() > 0) {
			pid = list.get(0);
		} else
			throw new ActionException("没有找到流程图");

		if (pid == null) {
			throw new ActionException("业务模式没有匹配到审批流程");
		}

		Map<String, Object> jmap = new HashMap<String, Object>();
		jmap.put("PROJECT_ID", map.get("PRO_ID"));
		jmap.put("SUPPLIER_ID", SuppMap.get("SUPPLIER_ID"));
		jmap.put("RZE", UtilFinance.formatString(projectDetails.get("FINANCE_TOPRIC").toString(), 0));
		String jbpmId = JBPM.startProcessInstanceById(pid,
				map.get("PRO_ID").toString(), map.get("CLIENT_ID").toString(),
				null, jmap).getId();
		String nextCode = new TaskService().getAssignee(jbpmId);
		// 修改状态
		Map<String,Object> mapStatus = new HashMap<String,Object>();
		mapStatus.put("PROJECT_ID", map.get("PRO_ID"));
		mapStatus.put("JBPM_ID", jbpmId);
		mapStatus.put("STATUS", 1);
		// mapStatus.put("CREDIT_PRICE", credMap.get("Price"));
		try {
			mapStatus.put(
					"INVOICE_METHOD",
					new DataDictionaryService()
							.queryDataDictionaryByTypeAndCodes("租金开票方式",
									map.get("RENT_WAY_INVOICE") + "").get(0)
							.get("FLAG"));
		} catch (Exception e) {
		}
		// mapStatus.put("SUP_ID", map.get("SUPPLIER_ID"));
		service.updateProjectStatusById(mapStatus);
		// new
		// EquipmentSplit().projectAppendEqComSupInfoByProjectId(map.get("PRO_ID"));

		// 分期回购模式计算分期模式单价
		if ("3".equals(map.get("PROJECT_MODEL") + "")) {
			new EquipmentSplit().updateAveragePriceLX(map);
		}

		return new ReplyAjax(true, nextCode, "流程发起成功，您的任务已经提交到下一节点: ").addOp(new OpLog(
				"客户管理", "立项-创建项目", "保存方案：" + map.get("PRO_CODE")));
	}


	// @aPermission(name = { "项目管理", "项目一览", "业务查看"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply SchemeView() {
		Map<String, Object> map = _getParameters();

		// 查询基本信息
		context.put("baseMap", service.queryInfoByEqBase(map));
		context.put("eqList", service.queryEqByProjectIDByScheme(map));

		// 查询方案名称
		context.put("BASE_SCHEME", service.getBaseSchemeBySchemeCode(map));
		// king查方案
		BaseSchemeService bsService = new BaseSchemeService();
		context.put("dicTag", Util.DICTAG);
		context.put("MyNumberTool", new MyNumberTool());
		context.put(
				"SCHEMEDETAIL",
				bsService.getSchemeClob(null, map.get("PROJECT_ID") + "",
						map.get("SCHEME_ROW_NUM") + ""));
		context.put(
				"schemeBase",
				bsService.getSchemeBaseInfoByProjectId(
						map.get("PROJECT_ID") + "", null,
						map.get("SCHEME_ROW_NUM") + "").get(0));

		// 还款计划
		// 查询支付表id在修改的时候使用
		PayTaskService pay = new PayTaskService();
		int PAY_ID = service.queryIdByProjectIdRowNum(map);
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("ID", PAY_ID);
		context.put("detailList", pay.doShowRentPlanDetailScheme(payMap));
		context.put("param", map);
		
		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		return new ReplyHtml(VM.html("project/projectViewScheme.vm", context));
	}
	/**
	 * 请款修改
	 * @return
	 */
	@aDev(code = "170039", email = "zhangcb@pqsoft.com", name = "请款页面")
	@aAuth(type = aAuthType.LOGIN)
	public Reply updateGetQk() {// 流程页面，请款
		Map<String, Object> param = _getParameters();
		System.out.println("--查看费用明细--param:"+param);
		
		VelocityContext context = new VelocityContext();
		
		List<Map<String,Object>> detaillist = service.findXmmxlist(param);
		
		context.put("detaillist",detaillist);
		
		BaseSchemeService bsService = new BaseSchemeService();
		List<Map<String,Object>> schemeClob =  bsService.getSchemeClob(null, param.get("PROJECT_ID") + "", param.get("SCHEME_ROW_NUM") + "");
		
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		
		context.put("SCHEMEDETAIL", schemeClob);	// 请款方案金额明细
		context.put("project_id",param.get("PROJECT_ID"));//前台隐藏
		context.put("SCHEME_ROW_NUM",param.get("SCHEME_ROW_NUM"));//前台隐藏
		context.put("sign","2");//权限判断2修改
		return new ReplyHtml(VM.html("jiafang/sjCostDetail.vm", context));
	}
	
	/**
	 * 请款查看
	 * @return
	 */
	@aDev(code = "170039", email = "zhangcb@pqsoft.com", name = "请款页面")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toGetQk() {// 流程页面，请款
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		
		List<Map<String,Object>> detaillist =service.findXmmxlist(param);
		
		context.put("detaillist",detaillist);
		
		BaseSchemeService bsService = new BaseSchemeService();
		List<Map<String,Object>> schemeClob =  bsService.getSchemeClob(null, param.get("PROJECT_ID") + "", param.get("SCHEME_ROW_NUM") + "");
		
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		
		context.put("SCHEMEDETAIL", schemeClob);	// 请款方案金额明细
		
		context.put("project_id",param.get("PROJECT_ID"));//前台隐藏
		context.put("SCHEME_ROW_NUM",param.get("SCHEME_ROW_NUM"));//前台隐藏
		context.put("sign","1");//权限判断1查看
		return new ReplyHtml(VM.html("jiafang/sjCostDetail.vm", context));
	}

	/**
	 * 将ProjectDetailBase得到的类型和价格进行拼串，最后返回Map对象
	 * @param detaillist 类型和价格
	 * @return 价钱类型和对应的值Map对象
	 */
	private Map<String, Object> getProjectDetailAndBase(List<Map<String, Object>> detaillist) {
		Map<String,Object> detail = new HashMap<String, Object>();
		for (Map<String, Object> map : detaillist) {
			
			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
			if("1".equals(TYPEID)){
				detail.put("CLSJ", map);	//车辆实际采购价
			}else if("2".equals(TYPEID)){
				detail.put("CLGZS", map);	//车辆购置税
			}else if("3".equals(TYPEID)){
				detail.put("SYBX", map);	//商业险
			}else if("4".equals(TYPEID)){
				detail.put("JQX", map);		//交强险
			}else if("5".equals(TYPEID)){
				detail.put("CCS", map);		//车船税
			}else if("6".equals(TYPEID)){
				detail.put("LQF", map);		//路桥费
			}else if("7".equals(TYPEID)){
				detail.put("HBFY", map);	//环保标费
			}else if("8".equals(TYPEID)){
				detail.put("SPF", map);		//上牌费
			}else if("9".equals(TYPEID)){
				detail.put("LPF", map);		//临牌费
			}else if("10".equals(TYPEID)){
				detail.put("QTFY", map);	//其他费用
			}
		}
		return detail;
	}
	
	/**
	 * 请款重置
	 * @return
	 */
	@aDev(code = "170039", email = "zhangcb@pqsoft.com", name = "请款页面")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax toGetQkrest() {// 流程页面，请款
		Map<String, Object> param = _getParameters();
		System.out.println("--查看费用明细--param:"+param);
		
		List<Map<String,Object>> detaillist =service.findXmmxlist(param);
		
		//Map<String, Object> detail = getProjectDetailAndBase(detaillist);
		
		JSONArray jsonObject = JSONArray.fromObject(detaillist);
		
		return new ReplyAjax(jsonObject);
	}
	
	/**
	 * 请款保存
	 * @return
	 */
	@aDev(code = "170051", email = "zhangch@pqsoft.cn", name = "张春波")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax saveSjmx() {
		Map<String, Object> param = _getParameters();
		int res = service.updateSjmx(param);
		boolean falg = false;
		if (res > 0) {
			falg = true;
		}
		return new ReplyAjax(falg);
	}

	// @aPermission(name = { "业务管理", "业务管理", "业务查看"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply projectShow() {
		Map<String, Object> map = _getParameters();
		// 项目信息
		Map<String,Object> projectMap = service.queryProjectById(map);
		Clob clob = (Clob) projectMap.get("SCHEME_CLOB");
		String str = null ;
		try {
			str=clob.getSubString(1, (int) clob.length());
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ActionException("报价详细未录入全，请联系管理员");
		}
		JSONArray SCHEME_CLOB=JSONArray.fromObject(str);
		int i = 0;
		for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map mClob = (Map) iterator.next();
			if(mClob.get("KEY_NAME_ZN") !=null && "有无担保人".equals(mClob.get("KEY_NAME_ZN").toString()))
			{
				map.put("ISGUARANTOR", (mClob.get("VALUE_STR")!=null && !mClob.get("VALUE_STR").equals(""))?mClob.get("VALUE_STR").toString():"1");
				if("2".equals(map.get("ISGUARANTOR")) || "3".equals(map.get("ISGUARANTOR")))
				{
				Map<String,Object> dbr=Dao.selectOne("project.getDbr",map);
				dbr.put("TIME", new Date().getTime());
				context.put("dbr", dbr);
				}
			}
		}
		map.putAll(projectMap);
//		map.put("JOINT_TENANT",StringUtils.isNotBlank(projectMap.get("JOINT_TENANT")) ? projectMap.get("JOINT_TENANT").toString() : "1"); // GUARANTEE
//		map.put("ISGUARANTOR",StringUtils.isNotBlank(projectMap.get("GUARANTEE")) ? projectMap.get("GUARANTEE").toString() : "1"); // GUARANTEE
		
		if ("8".equals(projectMap.get("PLATFORM_TYPE") + "")) {
			List LHSQFSLIST = new DataDictionaryService()
					.queryDataDictionaryByScheme("联合收取方式");
			context.put("LHSQFSLIST", LHSQFSLIST);
			// 查询联合租赁信息
			context.put("FL_LIST", service.getFLByProjectId(map));

			context.put("FLINFO_LIST", service.getFLInfo());
		}
		// 设备信息
		List<Map<String,Object>> eqlist = service.queryEqByProjectID(map);

		// 担保人信息
		List<Map<String,Object>> GuaranList = service.queryGuaranByProjectID(map);

		// 设备分组信息
		List<Map<String,Object>> rowlist = service.queryEqCountByProjectID(map);
		context.put("rowList", rowlist);

		// 扣款类型
//		String PROJECT_MODEL = map.get("PROJECT_MODEL") + "";
		List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
		Map<String,Object> finalMap2 = new HashMap<String,Object>();
		finalMap2.put("FINAL_TYPE", null);
		finalMap2.put("FINAL_NAME", "");
		final_Type_List.add(finalMap2);

		Map<String,Object> finalMap = new HashMap<String,Object>();
		finalMap.put("FINAL_TYPE", "0");
		finalMap.put("FINAL_NAME", "自有客户");
		final_Type_List.add(finalMap);

		Map<String,Object> finalMap1 = new HashMap<String,Object>();
		finalMap1.put("FINAL_TYPE", "1");

		finalMap1.put("FINAL_NAME", "终端客户");
		final_Type_List.add(finalMap1);
		context.put("final_Type_List", final_Type_List);

		// 发票开具类型
		context.put("invoice_target_type", DataDictionaryService
		.queryDataDictionaryByScheme("发票_对象_类型"));
		context.put("eqList", eqlist);
		context.put("GuaranList", GuaranList);
		context.put("param11", map);
		context.put("param", map);
		context.put("FORMAT", UTIL.FORMAT);

		context.put("score", service.Fen(map.get("PROJECT_ID").toString()));
		
		context.put("account_type",service.queryAccountTypeCust(projectMap));
		String permission="业务管理,业务管理,导出还款计划表";
		context.put("flag", Security.hasPermission(permission.split(",")));
		return new ReplyHtml(VM.html("project/projectView_mainNew.vm", context));
	}

	@aPermission(name = { "合同管理", "电子合同导出", "查看" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply projectShowContract() {
		Map<String, Object> map = _getParameters();
		// 项目信息
		Map<String,Object> projectMap = service.queryProjectById(map);
		map.putAll(projectMap);
		// 设备信息
		List<Map<String,Object>> eqlist = service.queryEqByProjectID(map);

		// 担保人信息
		List<Map<String,Object>> GuaranList = service.queryGuaranByProjectID(map);

		// 设备分组信息
		List<Map<String,Object>> rowlist = service.queryEqCountByProjectID(map);
		context.put("rowList", rowlist);

		// 扣款类型
//		String PROJECT_MODEL = map.get("PROJECT_MODEL") + "";
		List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
		Map<String,Object> finalMap2 = new HashMap<String,Object>();
		finalMap2.put("FINAL_TYPE", null);
		finalMap2.put("FINAL_NAME", "");
		final_Type_List.add(finalMap2);

		Map<String,Object> finalMap = new HashMap<String,Object>();
		finalMap.put("FINAL_TYPE", "0");
		finalMap.put("FINAL_NAME", "自有客户");
		final_Type_List.add(finalMap);

		Map<String,Object> finalMap1 = new HashMap<String,Object>();
		finalMap1.put("FINAL_TYPE", "1");

		finalMap1.put("FINAL_NAME", "终端客户");
		final_Type_List.add(finalMap1);
		context.put("final_Type_List", final_Type_List);

		// 发票开具类型
		context.put("invoice_target_type", DataDictionaryService.queryDataDictionaryByScheme("发票_对象_类型"));
		context.put("eqList", eqlist);
		context.put("GuaranList", GuaranList);
		context.put("param", map);
		context.put("FORMAT", UTIL.FORMAT);

		context.put(
				"Bus_Type",
				service.QueryBusType(map.get("PROJECT_ID").toString())
						.get("PLATFORM_TYPE").toString());
		context.put("score", service.Fen(map.get("PROJECT_ID").toString()));
		return new ReplyHtml(VM.html("project/projectView_mainNew.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply projectShowForJbpm() {

		String MANAGER_NAME = Security.getUser().getOrg().getPlatform();
		context.put("MANAGER_NAME", MANAGER_NAME);

		Map<String, Object> map = _getParameters();
		// 项目信息
		Map<String,Object> projectMap = service.queryProjectById(map);
		map.putAll(projectMap);
		context.put("account_type",
				new DataDictionaryMemcached().get("开户行账号类型"));
		if ("8".equals(projectMap.get("PLATFORM_TYPE") + "")) {
			context.put("LHSQFSLIST", DataDictionaryService
					.queryDataDictionaryByScheme("联合收取方式"));
			// 查询联合租赁信息
			context.put("FL_LIST", service.getFLByProjectId(map));

			context.put("FLINFO_LIST", service.getFLInfo());
		}

		// 设备信息
		List<Map<String,Object>> eqlist = service.queryEqByProjectID(map);
		String SUPPLIER_ID = map.get("SUP_ID") + "";
		context.put("supAccountList", service.doShowSupAccount(SUPPLIER_ID));

		// 设备分组信息
		List<Map<String,Object>> rowlist = service.queryEqCountByProjectID(map);
		context.put("rowList", rowlist);
		// 担保人信息
		List<Map<String,Object>> GuaranList = service.queryGuaranByProjectID(map);

		// 租赁周期
		context.put("LEASE_TERM", new SysDictionaryMemcached().get("租赁周期"));

		context.put(
				"Bus_Type",
				service.QueryBusType(map.get("PROJECT_ID").toString())
						.get("PLATFORM_TYPE").toString());
		context.put("score", service.Fen(map.get("PROJECT_ID").toString()));

		// 还款计划
		// 查询支付表id在修改的时候使用
		PayTaskService pay = new PayTaskService();
		String PROJECT_ID = map.get("PROJECT_ID") + "";
		int PAY_ID = pay.queryIdByProjectId(PROJECT_ID);
		context.put("totalMap", service.doShowRentInfo(PAY_ID + ""));
		context.put("PAY_ID", PAY_ID);
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("ID", PAY_ID);
		context.put("detailList", pay.doShowRentPlanDetail(payMap));
//		String AFFILIATED_COMPANY = null;

		// 判断当前流程节点名称
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		list = new TaskService().doShowTaskInfoByJbpmId(map);
//		String PROJECT_MODEL = map.get("PROJECT_MODEL") + "";
		if (map.containsKey("TASKNAME")) {
			context.put("TASKNAME", map.get("TASKNAME"));
			if (StringUtils.isNotBlank(PROJECT_ID))
				context.put("windID", new CreditWindService()
						.queryWindIdByProjectId(PROJECT_ID));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		context.put("NOWDAY", sdf.format(new Date()));
		Map<String, Object> dmap = new HashMap<String, Object>();
		String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";
		dmap.put("TYPE", "业务类型");
		dmap.put("CODE", PLATFORM_TYPE);
		Map<String, Object> mm = DataDictionaryService
				.queryDataDictionaryByTypeAndCode(dmap);
		String SHORTNAME = mm.get("SHORTNAME") + "";
		List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
		if (SHORTNAME.equals("0"))// 扣款账户为客户
		{
			// 扣款类型

			Map<String,Object> finalMap = new HashMap<String,Object>();
			finalMap.put("FINAL_TYPE", "0");
			finalMap.put("FINAL_NAME", "自有客户");
			final_Type_List.add(finalMap);
			context.put("final_Type_List", final_Type_List);

			// 客户名称
			List<Map<String,Object>> final_Cust_List = new ArrayList<Map<String,Object>>();
			Map<String,Object> custMap = new HashMap<String,Object>();
			custMap.put("CUST_ID", map.get("CLIENT_ID"));
			custMap.put("CUST_NAME", map.get("CUSTOMER_NAME"));
			final_Cust_List.add(custMap);
			context.put("final_Cust_List", final_Cust_List);

			// 开户行
			List<Map<String,Object>> bank = new ArrayList<Map<String,Object>>();
			if (StringUtils.isBlank(map.get("CUST_ID")))
				bank = service.queryBank(map.get("CUST_ID1") + "");
			else
				bank = service.queryBank(map.get("CUST_ID") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		} else if (SHORTNAME.equals("1"))// 扣款账户为终端客户
		{
			// 扣款类型

			Map<String,Object> finalMap = new HashMap<String,Object>();
			finalMap.put("FINAL_TYPE", "1");
			finalMap.put("FINAL_NAME", "终端客户");
			final_Type_List.add(finalMap);
			context.put("final_Type_List", final_Type_List);

			// 通过该客户ID查询出该供应商在查出该供应商下的所以客户
			List<Map<String,Object>> final_Cust_List = new ArrayList<Map<String,Object>>();
			Map<String,Object> custMap = new HashMap<String,Object>();
			custMap.put("CUST_ID", null);
			custMap.put("CUST_NAME", "--请选择--");
			final_Cust_List.add(custMap);

			final_Cust_List.addAll(service.doSuppByClent(map.get("CLIENT_ID")
					.toString()));
			context.put("final_Cust_List", final_Cust_List);
		} else// 扣款账户为选择
		{
			// 扣款类型

			Map<String,Object> finalMap2 = new HashMap<String,Object>();
			finalMap2.put("FINAL_TYPE", null);
			finalMap2.put("FINAL_NAME", "--请选择--");
			final_Type_List.add(finalMap2);

			Map<String,Object> finalMap = new HashMap<String,Object>();
			finalMap.put("FINAL_TYPE", "0");
			finalMap.put("FINAL_NAME", "自有客户");
			final_Type_List.add(finalMap);

			Map<String,Object> finalMap1 = new HashMap<String,Object>();
			finalMap1.put("FINAL_TYPE", "1");
			finalMap1.put("FINAL_NAME", "终端客户");
			final_Type_List.add(finalMap1);
			context.put("final_Type_List", final_Type_List);

		}

		// 发票开具类型
		context.put("invoice_target_type",  DataDictionaryService
		.queryDataDictionaryByScheme("发票_对象_类型"));

		context.put("eqList", eqlist);
		context.put("GuaranList", GuaranList);
		context.put("param11", map);
		context.put("param", map);
		context.put("FORMAT", UTIL.FORMAT);
		context.put("dicTag", Util.DICTAG);
		context.put("modelList", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(
				"project/projectViewForJbpm_main_Credit.vm", context));
	}

	@aPermission(name = { "项目管理", "业务审批单", "导出PDF" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public void projectExpPDF() {
		Map<String, Object> map = _getParameters();
		Map<String, Object> contextMap = new HashMap<String, Object>();
		// 项目信息
		Map<String,Object> projectMap = service.queryProjectById(map);
		map.putAll(projectMap);

		// 设备信息
		List<Map<String,Object>> eqlist = service.queryEqByProjectID(map);
		List<Map<String, Object>> listSechme = service
				.querySechmeByProjectID(map);

		// 方案信息(商务政策)
		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
			List<Map<String, Object>> list = DataDictionaryService
					.queryDataDictionaryByScheme("政策元素");
			for (Map<String, Object> mapItem : list) {
				List<Map<String, Object>> lst = new DataDictionaryMemcached().get(mapItem
						.get("FLAG") + "");
				mapItem.put("LIST", lst);
			}
			contextMap.put("DATALIST", list);
			Map<String,Object> schemeMap = new HashMap<String,Object>();
			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
			schemeMap.put("STATUS", 0);
			BaseSchemeService schemeService = new BaseSchemeService();
			contextMap.put("SCHEMEDETAIL",
					schemeService.doSelectSchemeDetailByName(schemeMap));
			contextMap.put("viewType", "project_scheme_view1");

			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
				Map<String,Object> mapfee = new HashMap<String,Object>();
				BaseSchemeService baseSchService = new BaseSchemeService();
				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
				map.put("feeCount",
						baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
			}
		} else {
			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
			for (Map<String, Object> mapItem : list) {
				mapItem.put("LIST", new DataDictionaryMemcached().get(mapItem
						.get("FLAG") + ""));
			}
			contextMap.put("DATALIST", list);
			contextMap.put("SCHEMEDETAIL", listSechme);
		}

		contextMap.put("eqList", eqlist);
		contextMap.put("param", map);

		map.put("CLIENT_ID", map.get("CUST_ID"));
		map.put("TYPE", map.get("CUST_TYPE"));
		CustomersService service = new CustomersService();
		contextMap.put("custInfo", service.findCustDetail(map));// 客户详细信息
		contextMap.put("custLinkInfo", service.findCustLinkInfo(map));// 客户关联信息

		ProjectManagerPdf pdf = new ProjectManagerPdf();
		pdf.fileManagerPdfJoin1(SkyEye.getResponse(), contextMap);
	}
	
	/**
	 * add by lishuo 12.10.2015.
	 * 数据字典FLAG查询  12w 与 微卡限额5w合并
	 */
	@aDev(code = "23", email = "shuoli@jiezhongchina.com", name = "李硕")
	@aAuth(type = aAuthType.LOGIN)
	public Reply CheckTotalPrice(){
		Map<String, Object> map = _getParameters();
		System.out.println(map.get("CODE"));
		//数据字典FLAG查询
		List<Map<String,Object>> projectMap = service.CheckTotalPrice(map);
		boolean flag = false;
		if(projectMap.size() > 0){
			flag = true;
		}
		else  {
			flag =false;
		}
		return new ReplyAjax(JSONArray.fromObject(projectMap));
	}
	
	/**
	 * add by lishuo 12.10.2015.
	 * 首期租金预付月数 查询
	 */
	@aDev(code = "23", email = "shuoli@jiezhongchina.com", name = "李硕")
	@aAuth(type = aAuthType.LOGIN)
	public Reply QueryYSZJQC(){
		Map<String, Object> map = _getParameters();
		System.out.println(map.get("YSZJQC"));
		System.out.println(map.get("BZJ"));
		System.out.println(map.get("SCHEME_CODE"));
		System.out.println(map.get("PROJECT_ID"));
		//首期租金预付月数 
		List<Map<String,Object>> projectMap = service.QueryYSZJQC(map);
		//查询租赁利率
		List<Map<String,Object>> ProjectMap = service.QueryRent(map);
		if(ProjectMap.size()>0 && ProjectMap != null ){
			projectMap.add(ProjectMap.get(0));
		}
		boolean flag = false;
		if(projectMap.size() > 0){
			flag = true;
		}
		else  {
			flag =false;
		}
		return new ReplyAjax(JSONArray.fromObject(projectMap));
	}
	
	@aPermission(name = { "项目管理", "业务确认函", "导出PDF" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public void confirmationExpPDF() {
		Map<String, Object> map = _getParameters();
		Map<String, Object> contextMap = new HashMap<String, Object>();
		// 项目信息
		Map<String,Object> projectMap = service.queryProjectById(map);
		map.putAll(projectMap);

		// 设备信息
		List<Map<String,Object>> eqlist = service.queryEqByProjectID(map);
		List<Map<String, Object>> listSechme = service
				.querySechmeByProjectID(map);

		// 方案信息(商务政策)
		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
			List<Map<String, Object>> list = DataDictionaryService.queryDataDictionaryByScheme("政策元素");
			for (Map<String, Object> mapItem : list) {
				List lst = new DataDictionaryMemcached().get(mapItem
						.get("FLAG") + "");
				mapItem.put("LIST", lst);
			}
			contextMap.put("DATALIST", list);
			Map<String,Object> schemeMap = new HashMap<String,Object>();
			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
			schemeMap.put("STATUS", 0);
			BaseSchemeService schemeService = new BaseSchemeService();
			contextMap.put("SCHEMEDETAIL",
					schemeService.doSelectSchemeDetailByName(schemeMap));
			contextMap.put("viewType", "project_scheme_view1");

			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
				Map<String,Object> mapfee = new HashMap<String,Object>();
				BaseSchemeService baseSchService = new BaseSchemeService();
				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
				map.put("feeCount",
						baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
			}
		} else {
			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
			for (Map<String, Object> mapItem : list) {
				List lst = new DataDictionaryMemcached().get(mapItem
						.get("FLAG") + "");
				mapItem.put("LIST", lst);
			}
			contextMap.put("DATALIST", list);
			contextMap.put("SCHEMEDETAIL", listSechme);
		}

		contextMap.put("eqList", eqlist);
		contextMap.put("param", map);

		map.put("CLIENT_ID", map.get("CUST_ID"));
		map.put("TYPE", map.get("CUST_TYPE"));
		CustomersService service = new CustomersService();
		contextMap.put("custInfo", service.findCustDetail(map));// 客户详细信息
		contextMap.put("custLinkInfo", service.findCustLinkInfo(map));// 客户关联信息

		ProjectManagerPdf pdf = new ProjectManagerPdf();
		pdf.confirmationPdfJoin(SkyEye.getResponse(), contextMap);
	}

	@aPermission(name = { "业务管理", "业务管理", "业务修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply SchemeUpdate() {
		m = _getParameters();
		System.out.println("===============m="+m);
		m.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		m.put("BASE_SCHEME", service.getBaseSchemeBySchemeCode(m));
		// 查询基本信息
		Map<String,Object> baseMap = service.queryInfoByEqBase(m);

		List<Map<String,Object>> eqlist = service.queryEqByProjectIDByScheme(m);
		context.put("eqList", eqlist);

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
		//add by lishuo 03-30-2016 取出原方案其他费用初始值 & 取出 产品政策开始的时间 Strat
		context.put("QITA_MONEY",service.queryBaseScheme(m));
		context.put("KSQX_DATE" ,service.queryPolicyTimeByBaseScheme(m));
		//add by lishuo 03-30-2016 取出原方案其他费用初始值  & 取出 产品政策开始的时间End
		// king查方案
		BaseSchemeService bsService = new BaseSchemeService();
		context.put("BASE_SCHEME1", bsService.querySchemeInfoList(m,"WEB"));
		context.put("dicTag", Util.DICTAG);
		context.put("MyNumberTool", new MyNumberTool());
		context.put( "SCHEMEDETAIL", bsService.getSchemeClob(null, m.get("PROJECT_ID") + "", m.get("SCHEME_ROW_NUM") + ""));
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
		System.out.println("---------------baseMap="+baseMap);
		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
		context.put("ZLQS", new SysDictionaryMemcached().get("租赁期数"));
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		m.put("SPID", Security.getUser().getOrg().getSpId());
		SuppliersService suppService = new SuppliersService();
		if (eqlist != null && eqlist.size() > 0) {
			Map<String,Object> eqMap  = eqlist.get(0);
			if (eqMap != null && eqMap.get("WHETHER_SALES_SI") != null && !"".equals(eqMap.get("WHETHER_SALES_SI").toString())) {
				//add gengchangbao JZZL-231 2016年6月27日15:34:49 Start
				m.put("ORG_ID", baseMap.get("ORD_ID"));
				m.put("ORG_NAME", "分部");
				//查询分公司信息
				String branch = Dao.selectOne("sys.getOrgCompanyID", m);
				m.put("FGSID", branch);
				m.put("SSJGPAGEFLAG", "1");
				//add gengchangbao JZZL-231 2016年6月27日15:34:49 End
				m.put("WHETHER_SALES_SI", eqMap.get("WHETHER_SALES_SI"));
				context.put("suppliersList", suppService.querySuppByCgxl(m));
			}
		}
		List<Object> whetherSalesSi = (List)new DataDictionaryMemcached().get("采购类型");
		context.put("whetherSalesSi", whetherSalesSi);
		context.put("carColor", new DataDictionaryMemcached().get("汽车颜色"));
		context.put("SCHEME_ROW_NUM",m.get("SCHEME_ROW_NUM") );
		context.put("PROJECT_ID",m.get("PROJECT_ID") );
		context.put("SCHEME_ID",m.get("SCHEME_ID") );
		//add gengchangbao JZZL-205 2016年6月15日13:20:57 Start
		context.put("sysinfo", new BaseSchemeService().getSystemName(Security.getUser().getOrg().getId()));
		//add gengchangbao JZZL-205 2016年6月15日13:20:57 End
		return new ReplyHtml(VM.html("project/projectUpdateScheme.vm", context));
	}
	
	// add gengchangbao 2016年5月30日17:55:06  JZZL-198 start
	@aPermission(name = { "业务管理", "业务管理", "来款管理" })
	@aDev(code = "170051", email = "changbaogeng@huashenghaoche.com", name = "耿长宝")
	@aAuth(type = aAuthType.ALL)
	public Reply DepositUpdate() {
		m = _getParameters();
		System.out.println("===============m="+m);
		// 查询定金详情
		List<Map<String,Object>> depositList = service.queryDepositInfo(m);
		
		//首期款合计
		Map<String,Object> amountPayable = service.getAmountPayable(m);
		
		context.put("amountPayable", amountPayable);
		
		context.put("PROJECT_ID", m.get("PROJECT_ID"));
		context.put("depositList", depositList);
		context.put("lklxMapList", new DataDictionaryMemcached().get("来款类型"));
		
		context.put("lkfsMapList", new DataDictionaryMemcached().get("来款方式"));
		
		context.put("param", m);
		return new ReplyHtml(VM.html("project/toDepositUpdate.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "来款管理", "来款确认", "列表显示" })
	@aDev(code = "2123", email = "changbaogeng@huashenghaoche.com", name = "耿长宝")
	public Reply depositConfirm() {
		m = _getParameters();
		context.put("param", m);
		context.put("lkfsMapList", new DataDictionaryMemcached().get("来款方式"));
		return new ReplyHtml(VM.html("project/toDepositConfirm.vm", context));
	}
	
	@aPermission(name = { "业务管理", "业务管理", "来款确认","列表" })
	@aDev(code = "170051", email = "changbaogeng@huashenghaoche.com", name = "耿长宝")
	@aAuth(type = aAuthType.ALL)
	public Reply toDepositConfirm() {
		Map<String, Object> param = _getParameters();
		Page page = service.queryPageDeposit(param);
		return new ReplyAjaxPage(page);
	}
	
	// add gengchangbao 2016年5月30日17:55:06  JZZL-198 start
	@aPermission(name = { "业务管理", "业务管理", "来款管理" })
	@aDev(code = "170051", email = "changbaogeng@huashenghaoche.com", name = "耿长宝")
	@aAuth(type = aAuthType.ALL)
	public Reply DepositShow() {
		m = _getParameters();
		// 查询定金详情
		List<Map<String,Object>> depositList = service.queryDepositInfo(m);
		//首期款合计
		Map<String,Object> amountPayable = service.getAmountPayable(m);
		
		context.put("amountPayable", amountPayable);
		
		context.put("PROJECT_ID", m.get("PROJECT_ID"));
		context.put("depositList", depositList);
		context.put("lklxMapList", new DataDictionaryMemcached().get("来款类型"));
		context.put("lkfsMapList", new DataDictionaryMemcached().get("来款方式"));
		context.put("param", m);
		return new ReplyHtml(VM.html("project/toDepositShow.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170027", email = "changbaogeng@huashenghaoche.com", name = "耿长宝")
	public Reply doDepositUpdate(){
		Map<String, Object> params = JSONObject.fromObject(_getParameters().get("params"));
		System.out.println("===============params="+params);
		boolean flag = true;
		String msg = "";
		try{
			
			String deposit_Ids = "";//画面保留之前数据库存在的数据ID
			//来款表ID获取
			for (String key : params.keySet()) {
				if (key.contains("ID_0_")){
					deposit_Ids = deposit_Ids + "," + params.get(key);
				}
			}
			deposit_Ids = deposit_Ids.replaceFirst(",", "");
			
			Map<String, Object> delParMap = new HashMap<String, Object>();
			if (!"".equals(deposit_Ids)) {
				delParMap.put("PROJECT_ID", params.get("PROJECT_ID"));
				delParMap.put("notDelIds", deposit_Ids);
				//删除画面删除掉的来款数据
				service.deleteDeposit(delParMap);
			}
			Map<String, Object> totheMoneyMap =new HashMap<String, Object>();
			String sealIndex = "0";//
			for (String key : params.keySet()) {
				if (key.contains("TOTHEPEOPLE_")){
					sealIndex = key.replace("TOTHEPEOPLE_", "");
					totheMoneyMap =new HashMap<String, Object>();
					totheMoneyMap.put("PROJECT_ID", params.get("PROJECT_ID"));
					totheMoneyMap.put("TOTHEPEOPLE", params.get("TOTHEPEOPLE_"+sealIndex));//来款人
					totheMoneyMap.put("TOTHEMONEY", params.get("TOTHEMONEY_"+sealIndex)); //来款金额
					
					totheMoneyMap.put("TOTHETYPE", params.get("TOTHETYPE_"+sealIndex)); //来款类型   1：定金
					totheMoneyMap.put("MONEYMODE", params.get("MONEYMODE_"+sealIndex));
					totheMoneyMap.put("CREATECODE", Security.getUser().getCode()); 
					totheMoneyMap.put("CREATENAME", Security.getUser().getName());//创建人
					totheMoneyMap.put("STATUS", params.get("STATUS_"+sealIndex)); // 1：已付
					totheMoneyMap.put("TOTHETIME", params.get("TOTHETIME_"+sealIndex)); // 来款时间
					totheMoneyMap.put("VOUCHER", params.get("VOUCHER_"+sealIndex)); // 凭证号
					
					totheMoneyMap.put("PICTPATH", params.get("PICTPATH_"+sealIndex)); // 图片路径
					totheMoneyMap.put("PICTNAME", params.get("PICTNAME_"+sealIndex)); // 图片名称
					
					if (params.get("ID_"+sealIndex) != null && !"".equals(params.get("ID_"+sealIndex))) {
						totheMoneyMap.put("ID", params.get("ID_"+sealIndex));
						//更新已有的来款数据
						service.updateDeposit(totheMoneyMap);
					} else {
						//插入新的来款数据
						service.insertDeposit(totheMoneyMap);
					}
				}
			}
		}catch(Exception e){
			flag = false;
			msg = "保存失败！";
		}
		
		return new ReplyAjax(flag, msg);
	}
	// add gengchangbao 2016年5月30日17:55:06  JZZL-198 end
	
	
	// add gengchangbao 2016年5月5日  JZZL-173 start
	@aPermission(name = { "业务管理", "业务管理", "合同详细信息" })
	@aDev(code = "170051", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	@aAuth(type = aAuthType.ALL)
	public Reply ContractDetailInfo() {
		m = _getParameters();
		System.out.println("===============m="+m);
		// 查询基本信息
		Map<String,Object> baseMap = service.queryContractDetailInfo(m);
		context.put("baseMap", baseMap);
		context.put("param", m);
		return new ReplyHtml(VM.html("project/contractDetailInfo.vm", context));
	}
	// add gengchangbao 2016年5月5日  JZZL-173  end
	@aPermission(name = { "方案修改", "方案修改", "产品方案名称修改联动显示" })
	@aDev(code = "170051", email = "chunbocom1989@163.com", name = "张春波")
	@aAuth(type = aAuthType.LOGIN)
	public Reply ProductUpdate() {
		m = _getParameters();
		BaseSchemeService bsService = new BaseSchemeService();
		context.put( "SCHEMEDETAILNEW", bsService.getSchemeClob(null, m.get("PROJECT_ID") + "", m.get("SCHEME_ROW_NUM") + ""));
		List oldlist = bsService.getSchemeClob(null, m.get("PROJECT_ID") + "", m.get("SCHEME_ROW_NUM") + "");
		List productlist = service.findProjectList(m);
		
		Map map = new HashMap();
		map.put("oldlist", oldlist);
		map.put("productlist", productlist);
		
		JSONArray product = JSONArray.fromObject(map);
		
		
		return new ReplyAjax(product);
	}


	@aPermission(name = { "业务管理", "业务管理", "业务修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply projectUpdate() {
		Map<String, Object> map = _getParameters();
		logger.info("projectAction.projectUpdate----------getParameters"+map);
		// 项目信息
		Map<String,Object> projectMap = service.queryProjectById(map);
		map.putAll(projectMap);
		Clob clob = (Clob) projectMap.get("SCHEME_CLOB");
		String str = null ;
		try {
			str=clob.getSubString(1, (int) clob.length());
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ActionException("报价详细未录入全，请联系管理员");
		}
		JSONArray SCHEME_CLOB=JSONArray.fromObject(str);
		for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map mClob = (Map) iterator.next();
			if(mClob.get("KEY_NAME_ZN") !=null && "有无担保人".equals(mClob.get("KEY_NAME_ZN").toString()))
			{
				map.put("ISGUARANTOR", (mClob.get("VALUE_STR")!=null && !mClob.get("VALUE_STR").equals(""))?mClob.get("VALUE_STR").toString():"1");
				if("2".equals(map.get("ISGUARANTOR")) || "3".equals(map.get("ISGUARANTOR")))
				{

					Map<String,Object> dbr=Dao.selectOne("project.getDbr",map);
					if(dbr ==null) {
						dbr=new HashMap<String,Object>();
					}
					dbr.put("TIME", new Date().getTime());
					context.put("dbr", dbr);
				}
			}
		}
		if ("8".equals(projectMap.get("PLATFORM_TYPE") + "")) {
			context.put("LHSQFSLIST",  DataDictionaryService.queryDataDictionaryByScheme("联合收取方式"));
			// 查询联合租赁信息
			context.put("FL_LIST", service.getFLByProjectId(map));

			context.put("FLINFO_LIST", service.getFLInfo());
		}

		// 设备分组信息
		context.put("rowList", service.queryEqCountByProjectID(map));
		// 单位
		context.put("unitList", new DataDictionaryMemcached().get("设备单位"));

		// 开户行
		if (map.get("CUST_ID1") != null) {
			List<Map<String,Object>> bank = service.queryBank(map.get("CUST_ID1") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		} else if (map.get("CUST_ID") != null) {
			List<Map<String,Object>> bank = service.queryBank(map.get("CUST_ID") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		}

		// 承租人信息
		if (map.get("CUST_ID") != null) {
			map.put("CLIENT_ID", map.get("CUST_ID"));
			map.putAll(service.selectRenterDetails(map));
		}
		
		//add by lishuo 01-28-2016 银行信息 Start
		context.put("Bank", new DataDictionaryMemcached().get("开户行所属总行"));
		//add by lishuo 01-28-2016 银行信息 End

		// 担保人信息
		List<Map<String,Object>> GuaranList = service.queryGuaranByProjectID(map);
		context.put("GuaranList", GuaranList);

		// 担保人类型
		context.put("typeList", new DataDictionaryMemcached().get("客户类型"));
		context.put("CollateralList", new DataDictionaryMemcached().get("抵押物"));

		// 设备信息（商务政策）
		List<Map<String,Object>> eqlist = service.queryEqByProjectID(map);
		context.put("eqList", eqlist);
//		System.out.println("-----------------projectMap="+projectMap);//CUST_TYPE
//		context.put("account_type",new DataDictionaryMemcached().get("开户行账号类型"));
		
		context.put("account_type",service.queryAccountTypeCust(projectMap));
		context.put("param", map);
		String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";
		Map<String, Object> dmap = new HashMap<String, Object>();
		dmap.put("TYPE", "业务类型");
		dmap.put("CODE", PLATFORM_TYPE);
		if ("5".equals(PLATFORM_TYPE)) {
			context.put("BIGPROJCOUNT",
					service.checkBigProjReportExist(map.get("PROJECT_ID") + ""));
		}
		try {
			Map<String, Object> mm = DataDictionaryService
					.queryDataDictionaryByTypeAndCode(dmap);
			String SHORTNAME = mm.get("SHORTNAME") + "";
			if (SHORTNAME.equals("0"))// 扣款账户为客户
			{
				// 扣款类型
				List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> finalMap = new HashMap<String,Object>();
				finalMap.put("FINAL_TYPE", "0");
				finalMap.put("FINAL_NAME", "自有客户");
				final_Type_List.add(finalMap);
				context.put("final_Type_List", final_Type_List);

				// 客户名称
				List<Map<String,Object>> final_Cust_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> custMap = new HashMap<String,Object>();
				custMap.put("CUST_ID", map.get("CLIENT_ID"));
				custMap.put("CUST_NAME", map.get("CUSTOMER_NAME"));
				final_Cust_List.add(custMap);
				context.put("final_Cust_List", final_Cust_List);

				// 开户行
				List<Map<String,Object>> bank = new ArrayList<Map<String,Object>>();
				if (StringUtils.isBlank(map.get("CUST_ID")))
					bank = service.queryBank(map.get("CUST_ID1") + "");
				else
					bank = service.queryBank(map.get("CUST_ID") + "");
				if (bank.size() > 0) {
					context.put("bankList", bank);
				}
			} else if (SHORTNAME.equals("1"))// 扣款账户为终端客户
			{
				// 扣款类型
				List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> finalMap = new HashMap<String,Object>();
				finalMap.put("FINAL_TYPE", "1");
				// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
				// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
				// finalMap.put("FINAL_NAME", "担保人");
				// else
				finalMap.put("FINAL_NAME", "终端客户");
				final_Type_List.add(finalMap);
				context.put("final_Type_List", final_Type_List);

				// 通过该客户ID查询出该供应商在查出该供应商下的所以客户
				List<Map<String,Object>> final_Cust_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> custMap = new HashMap<String,Object>();
				custMap.put("CUST_ID", null);
				custMap.put("CUST_NAME", "--请选择--");
				final_Cust_List.add(custMap);

				final_Cust_List.addAll(service.doSuppByClent(map.get(
						"CLIENT_ID").toString()));
				context.put("final_Cust_List", final_Cust_List);
			} else// 扣款账户为选择
			{
				// 扣款类型
				List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> finalMap2 = new HashMap<String,Object>();
				finalMap2.put("FINAL_TYPE", null);
				finalMap2.put("FINAL_NAME", "--请选择--");
				final_Type_List.add(finalMap2);

				Map<String,Object> finalMap = new HashMap<String,Object>();
				finalMap.put("FINAL_TYPE", "0");
				finalMap.put("FINAL_NAME", "自有客户");
				final_Type_List.add(finalMap);

				Map<String,Object> finalMap1 = new HashMap<String,Object>();
				finalMap1.put("FINAL_TYPE", "1");
				// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
				// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
				// finalMap1.put("FINAL_NAME", "担保人");
				// else
				finalMap1.put("FINAL_NAME", "终端客户");
				final_Type_List.add(finalMap1);
				context.put("final_Type_List", final_Type_List);

			}

			// //开户行账号类型
			// context.put("account_type", new
			// DataDictionaryMemcached().get("开户行账号类型"));
			// // 开户行账号客户名称
			// List<Map<String,Object>> final_Cust_List = new
			// ArrayList<Map<String,Object>>();
			// Map<String,Object> mapCard =
			// service.doCardByClent(map.get("CLIENT_ID").toString());
			// mapCard.put("CUST_ID", map.get("CLIENT_ID"));
			// mapCard.put("CUST_NAME", map.get("NAME"));
			// System.out.println("mapCard=="+mapCard);
			// final_Cust_List.add(mapCard);
			// context.put("final_Cust_List", final_Cust_List);
		} catch (Exception e) {
		}
		// 发票开具类型
		context.put("invoice_target_type", DataDictionaryService
				.queryDataDictionaryByScheme("发票_对象_类型"));
		// 租赁周期
		context.put("LEASE_TERM", new SysDictionaryMemcached().get("租赁周期"));
		context.put("PSYJB", SkyEye.getConfig("PSYJB"));
		
		context.put("score", service.Fen(map.get("PROJECT_ID").toString()));
		return new ReplyHtml(VM.html("project/projectUpdate_mainNew.vm",
				context));
	}

	@aPermission(name = { "业务管理", "业务管理", "业务修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply updateSchemeForProject() {
		System.out.println("&&&&&&&&&&&&&&updateSchemeForProject");
		Map<String, Object> map = _getParameters();
		//查询操作前数据
		 String xmlPath = "customers.";
		   //查询出操作前车辆信息和方案信息
		 String projectId=(String) map.get("PROJECT_ID");
		  Map<String, Object> opBefore=Dao.selectOne(xmlPath + "getCarAndPlanInfo", projectId);
		  
		//删除方案 2016年4月27日 16:29:47 吴国伟
		service.delschemeProjectId(map);
		service.deleq(map);
//		service.updateEqInfo(map);
		//修改共同承租人
//		service.updateProjectHeadByUp(map);
		// 保存还款计划信息
//		JSONArray josn = JSONArray.fromObject(map.get("rows"));
//		List<Map<String, String>> list = PayTaskService.getParsePayList(josn);
//		int PAY_ID = service.queryIdByProjectIdRowNum(map);
//
//		Dao.delete("PayTask.deletePayDetailScheme", PAY_ID);// 此处id为支付表id
		
		// // 像支付表子表插入费用项
		// if (PAY_ID>0) {
		// map.put("PAY_ID", PAY_ID);
		// service.createPayParam(map);
		// }

//		for (Map<String, String> map_ : list) {
//			map_.put("PAY_ID", PAY_ID + "");
//			map_.put("ITEM_FLAG", "2");
//			Dao.insert("PayTask.payplan_detailSCHEME", map_);
//		}

		//修改担保人 , add by zengqt,2015-07-31
		//为避免和添加多个担保人功能冲突,现修改担保人时取第一个人信息,
		//并且根据PROJECT_ID和CUST_ID对担保人进行删除操作
		Map<String, Object> mapGuar = new HashMap<String, Object>();
		mapGuar.put("PROJECT_ID", map.get("PROJECT_ID").toString());
		//用PROJECT_ID查询资信_担保人基本信息表中CUST_ID
		Map<String, Object> mapGuar1 = service.selectGuarantor(mapGuar);
		String typeStr=null;//担保人类型
		mapGuar.put("CUST_ID", null);//没有担保人信息,则CUST_ID赋值null
		if(mapGuar1 !=null && !mapGuar1.isEmpty()){
			String custIdGuar = mapGuar1.get("CUST_ID").toString();
			mapGuar.put("CUST_ID", custIdGuar);
			//查询担保人为自然人or法人
			Map<String, Object> mapGuar2 = service.selectGuarantorAll(mapGuar);
			if(mapGuar2 !=null && !mapGuar2.isEmpty()){
				typeStr = mapGuar2.get("TYPE").toString();
			}
		}
		//map中取担保人信息
		JSONObject projectScheme = JSONObject.fromObject(map.get("projectScheme"));
//		System.out.println("--4299--projectScheme:"+projectScheme);
		String isguarantor =null;
		JSONArray SCHEME_CLOB=JSONArray.fromObject(projectScheme.get("SCHEME_CLOB"));
		for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map mClob = (Map) iterator.next();
			if(mClob.get("KEY_NAME_ZN") !=null && "有无担保人".equals(mClob.get("KEY_NAME_ZN").toString()))
			{
				isguarantor = mClob.get("VALUE_STR").toString();
				break;
			}
		}
		mapGuar.put("GUARANTEE", isguarantor);
		//项目主表中修改担保方式,便于资料上传中有担保人模板
		if(isguarantor!=null && isguarantor!=""){
			service.updateProjectHeadByUpId(mapGuar);
		}
		if("3".equals(isguarantor) && !"LP".equals(typeStr)){
			//担保人是法人-LP,如果以前保存是自然人-NP则先删除
			if(mapGuar.get("CUST_ID") !=null){
				service.delGuarantor(mapGuar);
				//删除担保人关联信息
				service.delGuarGL(mapGuar);
			}	
			service.UpdateProjectOther(mapGuar);
		}else if("2".equals(isguarantor) && !"NP".equals(typeStr)){
			//担保人是自然人-NP,如果以前保存是法人-LP则先删除
			if(mapGuar.get("CUST_ID") !=null){
				service.delGuarantor(mapGuar);
				//删除担保人关联信息
				service.delGuarGL(mapGuar);
			}	
			service.UpdateProjectOther(mapGuar);
		}else if("1".equals(isguarantor) || "".equals(isguarantor)){
			if(mapGuar.get("CUST_ID") !=null){
				service.delGuarantor(mapGuar);
				//删除担保人关联信息
				service.delGuarGL(mapGuar);
			}	
		}
		
		//修改方案时,共同承租人修改     1-无,2-自然人,3-法人
		Map<String, Object> mapGT = new HashMap<String, Object>();
		mapGT.put("PROJECT_ID", map.get("PROJECT_ID").toString());
		Map<String, Object> mapGTPro =service.selectGTById(mapGT);
		String gtType ="1";
		String gtId =null;
		if(mapGTPro !=null && !mapGTPro.isEmpty()){
			gtType =mapGTPro.get("JOINT_TENANT").toString();//表中值
			gtId = mapGTPro.get("JOINT_TENANT").toString();
		}
		String typeGT =map.get("JOINT_TENANT").toString();//页面值
		if(typeGT==""){
			typeGT="1";
		}
		mapGT.put("JOINT_TENANT", typeGT);
//		mapGT.put("JOINT_TENANT_ID", map.get("JOINT_TENANT_ID").toString());
		if(!typeGT.equals(gtType)){
			//如果表中和页面传值不一致,则先删除共同承租人后增加
			if(gtId !=null){
				service.delGTCust(mapGTPro);
			}
			service.UpdateProjectOtherGT(mapGT);
		}
		// *********************************保存方案 start***********************/
//		JSONObject schemeJson = JSONObject.fromObject(map.get("projectScheme"));
//		BaseSchemeService baseSchemeService = new BaseSchemeService();
//		schemeJson.put("SCHEME_ID", PAY_ID);
//		baseSchemeService.doUpdateProjectScheme(schemeJson);
		// *********************************保存方案 end***********************/
		JSONObject schemeJson = JSONObject.fromObject(map.get("projectScheme"));
		schemeJson.put("MONTH_RENT", map.get("MONTH_RENT"));
		BaseSchemeService baseSchemeService = new BaseSchemeService();
		if(StringUtils.isBlank(schemeJson.get("PROJECT_ID")))
			schemeJson.put("PROJECT_ID", map.get("PROJECT_ID"));
		if(StringUtils.isBlank(schemeJson.get("SCHEME_ROW_NUM")))
			schemeJson.put("SCHEME_ROW_NUM", map.get("SCHEME_ROW_NUM"));
		map.put("SCHEME_ID_SEQ", baseSchemeService.doAddProjectScheme(schemeJson));
		Dao.update("project.updSchemeId",map);
		Dao.update("project.updSchemeClobId",map);
		map.put("SCHEME_CODE", schemeJson.get("SCHEME_CODE"));
		Dao.update("baseScheme.doUpdHeadid",map);
		// *********************************保存方案 end***********************/
		//项目已发起流程,则更新融资额到流程中
		String dbidStr = service.selectDBID(schemeJson);
		Map<String, Object> dbidMap = new HashMap<String, Object>();
		if(dbidStr !=null && dbidStr !=""){
			dbidMap.put("EXECUTION_", dbidStr);
			dbidMap.put("STRING_VALUE_", UtilFinance.formatString(schemeJson.get("FINANCE_TOPRIC"),0));
//			System.out.println("---RZE---dbidMap:"+dbidMap);
			service.upRZE(dbidMap);
		}
		PayTaskService.quoteCalculateSaveSCHEME(map);
		Dao.update("project.updSchemeDetailId",map);
		
		
		JSONArray EqList=JSONArray.fromObject(map.get("EqList"));
//		List EqList = (List) m.get("EqList");
		for (int i = 0; i < EqList.size(); i++) {
			Map<String,Object> eqMap = (Map<String,Object>) EqList.get(i);
			if (eqMap != null) {
		
				eqMap.put("PROJECT_ID", map.get("PROJECT_ID"));
				eqMap.put("USERID", Security.getUser().getId());
				eqMap.put("SCHEME_ROW_NUM", map.get("SCHEME_ROW_NUM"));
				eqMap.put("SCHEME_ID_ACTUAL", map.get("SCHEME_ID_ACTUAL"));
				eqMap.put("CAR_COLOR", map.get("CAR_COLOR"));
				eqMap.put("XH_PARAM", map.get("XH_PARAM"));
				eqMap.put("BX", map.get("BX"));
				eqMap.put("CCS", map.get("CCS"));
				eqMap.put("JQX", map.get("JQX"));
				eqMap.put("PRO_REMARK", map.get("PRO_REMARK"));
				//add gengchangbao JZZL-182 2016年5月11日10:03:00 start
				eqMap.put("CC_PRICE", map.get("CC_PRICE"));
				//add gengchangbao JZZL-182 2016年5月11日10:03:00 end
				//add gengchangbao JZZL- 204 2016年6月15日09:53:56 Start
				eqMap.put("REAL_PRICE", map.get("REAL_PRICE"));
				//add gengchangbao JZZL- 204 2016年6月15日09:53:56 End
				eqMap.put("FINANCE_TOPRIC", map.get("FINANCE_TOPRIC"));//融资额
				eqMap.put("LEASE_TERM", schemeJson.get("LEASE_TERM"));//月数
				eqMap.put("YEAR_INTEREST", schemeJson.get("YEAR_INTEREST"));//租赁利率
				
				if(!map.containsKey("BUSINESS_SOURCE") || "".equals(m.get("BUSINESS_SOURCE"))){
					if(eqMap.get("SUPPLIERS_ID") !=null && !eqMap.get("SUPPLIERS_ID").equals("")){
						Dao.update("project.updBusinessSource",eqMap);
					}
				}
				int num = Dao.insert("project.EquipmentAdd", eqMap);
			}
		}
		//记录方案修改日志
		String fileName = "fil_cust_client_np.xml";
		
		   String clientId=opBefore.get("CLIENT_ID").toString();
		   //从页面获取操作后记录
		   Map<String, Object> opBehind=new HashMap<String, Object>(); 
		   opBehind.put("SUPPLIERS_NAME", map.get("SUPPLIERS_ID"));//经销商
		   opBehind.put("PRODUCT_NAME", map.get("THING_NAME"));//品牌
		   opBehind.put("CATENA_NAME", map.get("PRODUCT_CATENA"));//系列     
		   opBehind.put("SPEC_NAME", map.get("THING_SPEC"));//车型//
		   opBehind.put("LEASE_TOPRIC", map.get("ACTUAL_PRICE"));//实际采购价
		   opBehind.put("CAR_COLOR", map.get("CAR_COLOR"));//颜色
		   opBehind.put("XH_PARAM", map.get("XH_PARAM"));//详细型号
		   opBehind.put("BX", map.get("BX"));//商业险待确定
		   opBehind.put("SCHEME_NAME", map.get("SCHEME_CODE1"));//产品方案名称
		   opBehind.put("LEASE_TERM", map.get("lease_term"));//期数
		   opBehind.put("FINANCE_TOPRIC", map.get("FINANCE_TOPRIC"));//融资额
		   opBehind.put("FIRSTPAYALL", map.get("FIRSTPAYALL"));//首笔款
		 
		   List<Map<String,String>> extendList = new ArrayList<Map<String,String>>() ;

				Map<String,String> m1 = new HashMap<String,String> () ;
				m1.put("KEY", "SUPPLIERS_NAME") ;
				m1.put("KEY_F","SUPPLIERS_NAME") ;
				m1.put("VALUE", "经销商") ;
				extendList.add(m1) ;
				
				Map<String,String> m2 = new HashMap<String,String> () ;
				m2.put("KEY", "PRODUCT_NAME") ;//PRODUCT_NAME
				m2.put("KEY_F","PRODUCT_NAME") ;
				m2.put("VALUE", "品牌") ;
				extendList.add(m2) ;
				
				Map<String,String> m3 = new HashMap<String,String> () ;
				m3.put("KEY", "CATENA_NAME") ;//CATENA_NAME
				m3.put("KEY_F","CATENA_NAME") ;
				m3.put("VALUE", "系列") ;
				extendList.add(m3) ;
				
				Map<String,String> m4 = new HashMap<String,String> () ;
				m4.put("KEY", "SPEC_NAME") ;
				m4.put("KEY_F","SPEC_NAME") ;
				m4.put("VALUE", "车型") ;
				extendList.add(m4) ;
				
				Map<String,String> m5 = new HashMap<String,String> () ;
				m5.put("KEY", "LEASE_TOPRIC") ;//FINANCE_TOPRIC
				m5.put("KEY_F","LEASE_TOPRIC") ;//
				m5.put("VALUE", "实际采购价") ;
				extendList.add(m5) ;
				
				Map<String,String> m6 = new HashMap<String,String> () ;
				m6.put("KEY", "CAR_COLOR") ;
				m6.put("KEY_F","CAR_COLOR") ;
				m6.put("VALUE", "颜色") ;
				extendList.add(m6) ;
				
				Map<String,String> m7 = new HashMap<String,String> () ;
				m7.put("KEY", "XH_PARAM") ;
				m7.put("KEY_F","XH_PARAM") ;
				m7.put("VALUE", "详细型号") ;
				extendList.add(m7) ;
				
				Map<String,String> m8 = new HashMap<String,String> () ;
				m8.put("KEY", "BX") ;
				m8.put("KEY_F","BX") ;//INSURANCE_MONEY
				m8.put("VALUE", "商业险") ;
				extendList.add(m8) ;
				
				Map<String,String> m9= new HashMap<String,String> () ;
				m9.put("KEY", "SCHEME_NAME") ;//SCHEME_CODE1
				m9.put("KEY_F","SCHEME_NAME") ;//SCHEME_NAME
				m9.put("VALUE", "产品方案名称") ;
				extendList.add(m9) ;
				
				Map<String,String> m10= new HashMap<String,String> () ;
				m10.put("KEY", "LEASE_TERM") ;
				m10.put("KEY_F","LEASE_TERM") ;
				m10.put("VALUE", "期数") ;
				extendList.add(m10) ;
				
				Map<String,String> m11= new HashMap<String,String> () ;
				m11.put("KEY", "FINANCE_TOPRIC") ;
				m11.put("KEY_F","FINANCE_TOPRIC") ;
				m11.put("VALUE", "融资额") ;
				extendList.add(m11) ;
				
				Map<String,String> m12= new HashMap<String,String> () ;
				m12.put("KEY", "FIRSTPAYALL") ;
				m12.put("KEY_F","FIRSTPAYALL") ;
				m12.put("VALUE", "首笔款") ;
				extendList.add(m12) ;
				
				//otherParam
				Map<String, Object> otherParam = new HashMap<String, Object>();
				otherParam.put("OP_REMARK", "客户资料管理->客户基本信息->修改");
				otherParam.put("OP_CLIENT_ID", clientId);
		       String statusFlag=(String) SkyEye.getRequest().getSession().getAttribute("statusFlag");
		       System.out.println("statusFlag......"+statusFlag);
			if(statusFlag!="0" && statusFlag!=null && statusFlag!=""){
				LogUtil.insertLogForUpdate(fileName, opBefore, opBehind, otherParam,extendList,projectId);
			}

		// 同步方案表和方案子表的数据到租金计划表，租金计划子表，beginning表
		Object projectIdObj = map.get("PROJECT_ID");
		synSchemeToRentService.synSchemeToRent(projectId == null ? null : projectIdObj.toString());

		return new ReplyAjax(true).addOp(new OpLog("项目管理", "项目一览", "修改方案："
				+ map.get("PRO_CODE")));
	}
	// add gengchangbao 2016年4月21日17:31:06  JZZL-168 start
	@aPermission(name = { "业务管理", "业务管理", "签约时间保存" })
	@aDev(code = "170051", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	@aAuth(type = aAuthType.ALL)
	public Reply updateSignDateForProject() {
		System.out.println("&&&&&&&&&&&&&&updateSignDateForProject");
		Map<String, Object> map = _getParameters();
		service.updateSignDateByProId(map);
		return new ReplyAjax(true);
	}
	// add gengchangbao 2016年4月21日17:31:06  JZZL-168 end

	@aPermission(name = { "业务管理", "业务管理", "业务修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply updateSchemeForProjectOld() {
		Map<String, Object> map = _getParameters();
		String jbpmId = null;
		// 保存银行信息
		service.projectUpdate(map);
		// 保存还款计划信息
		PayTaskService pay = new PayTaskService();
		pay.updateProjectInfo(map);
		JSONArray josn = JSONArray.fromObject(map.get("rows"));
		List<Map<String, String>> list = PayTaskService.getParsePayList(josn);
		String PAY_ID = pay.queryIdByProjectId(map.get("project_id") + "") + "";
		if (StringUtils.isBlank(PAY_ID) || "0".equals(PAY_ID)) {
			Map<String, Object> mm = new HashMap<String, Object>();
			// 插入支付表主表
			mm.put("YEAR_INTEREST", map.get("annualRate"));
			mm.put("TOPRIC_SUBFIRENT", map.get("residualPrincipal"));
			int _payCountOfYear = Integer.parseInt(map.get("_payCountOfYear")
					+ "");
			mm.put("LEASE_TERM", map.get("_leaseTerm"));
			mm.put("LEASE_PERIOD", 12 / _payCountOfYear);
			mm.put("PAY_WAY", map.get("pay_way"));
			mm.put("START_DATE", map.get("date"));
			mm.put("PAYLIST_CODE", map.get("project_code") + "-1");
			mm.put("PROJECT_ID", map.get("project_id"));
			mm.put("VERSION_CODE", -1);
			mm.put("STATUS", "0");
			mm.put("CREATE_PERSON", Security.getUser().getId());
			mm.put("CHANGE_START_FLAG", "0");
			mm.put("PAY_STATUS", "0");
			mm.put("MANAGEMENT_FEETYPE", 2);
			mm.put("CALCULATE_WAY", 1);
			mm.put("UPDATE_STATUS", "0");
			mm.put("FIRST_MONEY_ALL", map.get("FIRST_MONEY_ALL"));
			mm.put("MONEY_ALL", map.get("MONEY_ALL"));
			try {
				Dao.insert("PayTask.insert_payplan", mm);
				PAY_ID = pay.queryIdByProjectId(map.get("project_id") + "")
						+ "";
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			Map<String, Object> mm = new HashMap<String, Object>();
			// 插入支付表主表
			mm.put("ID", PAY_ID);
			mm.put("YEAR_INTEREST", map.get("annualRate"));
			mm.put("TOPRIC_SUBFIRENT", map.get("residualPrincipal"));
			int _payCountOfYear = Integer.parseInt(map.get("_payCountOfYear")
					+ "");
			mm.put("LEASE_TERM", map.get("_leaseTerm"));
			mm.put("LEASE_PERIOD", 12 / _payCountOfYear);
			mm.put("PAY_WAY", map.get("pay_way"));
			mm.put("START_DATE", map.get("date"));
			mm.put("PAYLIST_CODE", map.get("project_code") + "-1");
			mm.put("PROJECT_ID", map.get("project_id"));
			mm.put("VERSION_CODE", -1);
			mm.put("STATUS", "0");
			mm.put("CREATE_PERSON", Security.getUser().getId());
			mm.put("CHANGE_START_FLAG", "0");
			mm.put("PAY_STATUS", "0");
			mm.put("MANAGEMENT_FEETYPE", 2);
			mm.put("CALCULATE_WAY", 1);
			mm.put("UPDATE_STATUS", "0");
			mm.put("FIRST_MONEY_ALL", map.get("FIRST_MONEY_ALL"));
			mm.put("MONEY_ALL", map.get("MONEY_ALL"));
			try {
				Dao.insert("PayTask.update_payplan", mm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Dao.delete("PayTask.deletePayDetail", PAY_ID);// 此处id为支付表id
		}
		// 像支付表子表插入费用项
		if (PAY_ID != null && !PAY_ID.equals("")) {
			map.put("PAY_ID", PAY_ID);
			service.createPayParam(map);
		}
		for (Map<String, String> map_ : list) {
			map_.put("PAY_ID", PAY_ID);
			map_.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map_);
		}

		// 更新项目设备表还款计划编号
		service.updateProEquPayCodeByProId(map.get("PRO_ID") + "");
		// 更新挂靠公司id到设备表 King 2013-11-11
		// 修改状态
		Map<String,Object> mapStatus = new HashMap<String,Object>();
		String JBPM_ID = map.get("JBPM_ID") + "";
		if (StringUtils.isBlank(JBPM_ID)) {
			List<String> lst = JBPM.getDeploymentListByModelName("立项", null,
					Security.getUser().getOrg().getPlatformId());
			String pid = null;
//			String PROJECT_MODEL = map.get("PROJECT_MODEL") + "";
			if (lst.size() > 0) {
				pid = lst.get(0);
				mapStatus.put("STATUS", 1);
			} else
				throw new ActionException("没有找到流程图");
			Map<String, Object> jmap = new HashMap<String, Object>();
			jmap.put("PROJECT_ID", map.get("PRO_ID"));
			jmap.put("SUPPLIER_ID", map.get("SUP_ID"));
			if (pid == null) {
				throw new ActionException("业务模式没有匹配到审批流程");
			}
			jbpmId = JBPM.startProcessInstanceById(pid,
					map.get("PRO_ID").toString(),
					map.get("CLIENT_ID").toString(), null, jmap).getId();

			// 刷新项目资料
			map.put("PROJECT_ID", map.get("PRO_ID"));

			mapStatus.put("JBPM_ID", jbpmId);
			// 项目信息
			Map<String, Object> projectDetails = service
					.selectProjectDetails(map);
			// 承租人详细信息
			Map<String, Object> renterDetails = service
					.selectRenterDetails(map);
			renterDetails.put("RENTER_NAME", renterDetails.get("NAME"));
			renterDetails.put("RENTER_CODE", renterDetails.get("CUST_CODE"));
			renterDetails.put("CUSTOMER_TYPE", renterDetails.get("TYPE"));
			renterDetails.put("PROJECT_ID", map.get("PRO_ID"));
			renterDetails.put("PRO_CODE", projectDetails.get("PRO_CODE"));
			List<Map<String, Object>> electronicPhotoAlbumData = service
					.electronicPhotoAlbumData(renterDetails);
//			List<Map<String, Object>> listFile = service
//					.selectProjectFileData(map.get("PRO_ID").toString());
			service.deleteProjectFile(renterDetails);
			for (int i = 0; i < electronicPhotoAlbumData.size(); i++) {
				Map<String, Object> dataMap = electronicPhotoAlbumData.get(i);
				dataMap.put("CLIENT_ID", renterDetails.get("ID"));
				dataMap.put("TPM_BUSINESS_PLATE", "立项");
				service.addProjectFile(dataMap);
			}

		}
		mapStatus.put("PROJECT_ID", map.get("PRO_ID"));
		try {
			mapStatus.put(
					"INVOICE_METHOD",
					new DataDictionaryService()
							.queryDataDictionaryByTypeAndCodes("租金开票方式",
									map.get("RENT_WAY_INVOICE") + "").get(0)
							.get("FLAG"));
		} catch (Exception e) {
		}
		service.updateProjectStatusById(mapStatus);
		// 分期回购模式计算分期模式单价
		if ("3".equals(map.get("PROJECT_MODEL") + "")) {
			new EquipmentSplit().updateAveragePriceLX(map);
		}
//		String nextCode = new TaskService().getAssignee(jbpmId);
		return new ReplyAjax(true).addOp(new OpLog("项目管理", "项目一览", "修改方案："
				+ map.get("PRO_CODE")));
	}

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "项目一览", "发起流程" })
	public Reply projectStartJbpm() {
		Map<String, Object> map = _getParameters();
		String PRO_ID = (String) map.get("PRO_ID");
		if(StringUtils.isBlank(PRO_ID)){
			return new ReplyAjax(false, "参数丢失：PRO_ID ");
		}
		
		// 项目信息
		Map<String, Object> projectDetails = service.selectProjectDetails(map);
		// 承租人详细信息
				Map<String, Object> renterDetails = service
						.selectRenterDetails(projectDetails);
				renterDetails.put("RENTER_NAME", renterDetails.get("NAME"));
				renterDetails.put("RENTER_CODE", renterDetails.get("CUST_CODE"));
				renterDetails.put("CUSTOMER_TYPE", renterDetails.get("TYPE"));
				renterDetails.put("PROJECT_ID", PRO_ID);
				renterDetails.put("PRO_CODE", projectDetails.get("PRO_CODE"));
				List<Map<String, Object>> electronicPhotoAlbumData = service
						.electronicPhotoAlbumData(renterDetails);
		//方案
		List<Map<String, Object>> projectscheme = service.querySechmeByProjectID(map);
		Map<String, Object> mapscheme=projectscheme.get(0);
		if(projectDetails.get("JBPM_ID")!=null && !"".equals(projectDetails.get("JBPM_ID").toString())){
			return new ReplyAjax(false, "","该项目已有流程");
		}
		List<String> lst = JBPM.getDeploymentListByModelName("立项",
				projectDetails.get("PLATFORM_TYPE") + "", Security.getUser()
						.getOrg().getPlatformId());
		// 通过项目ID查询出供应商ID
		Map<String,Object> SuppMap = service.querySuppByProjectID(  PRO_ID);
		String pid = null;
		if (lst.size() > 0) {
			pid = lst.get(0);
		} else
			throw new ActionException("没有找到流程图");
		Map<String, Object> jmap = new HashMap<String, Object>();
		jmap.put("PROJECT_ID", PRO_ID);
		if(SuppMap!=null)
		jmap.put("SUPPLIER_ID", SuppMap.get("SUPPLIER_ID"));
		jmap.put("KHLX", renterDetails.get("CUSTOMER_TYPE").toString().equals("NP")?"个人":"企业");
		jmap.put("RZE", UtilFinance.formatString(
				projectDetails.get("FINANCE_TOPRIC"), 0));
		if(mapscheme !=null && mapscheme.containsKey("SFBZHT")){
			jmap.put("SFBZHT", mapscheme.get("SFBZHT"));
		}else{
			jmap.put("SFBZHT", "");
		}
		if (pid == null) {
			throw new ActionException("业务模式没有匹配到审批流程");
		}
		System.out.println("jmap============"+jmap);
		ProcessInstance pid1 = JBPM.startProcessInstanceById(pid,
				PRO_ID, projectDetails.get("CLIENT_ID").toString(),
				null, jmap);

		String nextCode = new TaskService().getAssignee(pid1.getId());
		/****************** 修改项目状态 ************************************/
		Map<String, Object> mapStatus = new HashMap<String, Object>();
		mapStatus.put("STATUS", 1);
		mapStatus.put("PROJECT_ID", PRO_ID);
		mapStatus.put("JBPM_ID", pid1.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mapStatus.put("APP_DATE", sdf.format(new Date()));//申请时间
		service.updateProjectStatusById(mapStatus);

		/*********************************************************************/
		// 刷新项目资料
		map.put("PROJECT_ID", map.get("PRO_ID"));
//		projectService service = new projectService();
//		// 先删后写入
//		service.deleteProjectFile(renterDetails);
//		for (int i = 0; i < electronicPhotoAlbumData.size(); i++) {
//			Map<String, Object> dataMap = electronicPhotoAlbumData.get(i);
//			dataMap.put("CLIENT_ID", renterDetails.get("ID"));
//			dataMap.put("TPM_BUSINESS_PLATE", "立项");
//			service.addProjectFile(dataMap);
//		}

		/*********************************************************************/
		if(nextCode==null){
			return new ReplyAjax(true, "", "提交成功").addOp(new OpLog(
					"项目管理", "发起流程", "发起的流程为" + pid1));
		}
		return new ReplyAjax(true, nextCode, "下一步操作人为: ").addOp(new OpLog(
				"项目管理", "发起流程", "发起的流程为" + pid1));
	}

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryEquipmentFQ() {
		Map<String, Object> param = _getParameters();
		// 根据pro_id 获取分期模式单价
		return new ReplyJson(JSONArray.fromObject(service
				.queryBankByCustByProId(param)));
	}

	/**
	 * 变更交货日期并更改起租日及约定还款日
	 * 
	 * @return
	 * @author:King 2013-10-19
	 */
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doUpdateStartDate() {
		Map<String, Object> param = _getParameters();
		String PROJECT_ID = param.get("PROJECT_ID") + "";
		String DELIVER_DATE = param.get("START_DATE") + "";
		String DELIVER_ADDRESS = param.get("DELIVER_ADDRESS") + "";
		try {
			param.putAll(service.doShowPayIdByProjectId(PROJECT_ID));
			param.put("pay_id", param.get("PAY_ID"));
			param.put("start_date", DELIVER_DATE);

			// 验收日期
			service.doUpdateDELIVER_DATE(PROJECT_ID, DELIVER_DATE,
					DELIVER_ADDRESS);
			// 起租时间及约定还款日
			new PayTaskService().updatePayDate(param);

			// 付款预计时间
			// new PayTaskService().doUpdatePayDateByPaylistCode(DELIVER_DATE,
			// param.get("PAYLIST_CODE") + "");

			// 项目方案信息
			Set<String> set = param.keySet();
			for (String string : set) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("PROJECT_ID", PROJECT_ID);
				map.put("KEY_NAME_EN", string);
				map.put("VALUE_STR", param.get(string));
				service.doUpdateSchemeInfo(map);
			}
			new ProjectHeadUpdateForJbpm()
					.doUpdateSchemeValueFlagByProjectId(PROJECT_ID);
			return new ReplyAjax().addOp(new OpLog("流程任务-起租申请", "修改交货日期及起租日",
					"修改项目ID为" + PROJECT_ID + "的验收日为" + DELIVER_DATE));
		} catch (Exception e) {
			throw new ActionException("更新失败" + e.getMessage());
		}
	}

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doUpdateSignedDate() {
		Map<String, Object> param = _getParameters();
		String PROJECT_ID = param.get("PROJECT_ID") + "";
		List<Map<String, Object>> list = JSONArray.fromObject(param
				.get("EQUIPMENT"));
		if (list.size() > 0) {
			for (Map<String, Object> map : list) {
				service.updateEquInfo(map);
			}
		}
		Set<String> set = param.keySet();
		for (String string : set) {
			if ("EQUIPMENT".equals(string)) {
				continue;
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("PROJECT_ID", PROJECT_ID);
				map.put("KEY_NAME_EN", string);
				map.put("VALUE_STR", param.get(string));
				service.doUpdateSchemeInfo(map);
			}
		}
		new ProjectHeadUpdateForJbpm()
				.doUpdateSchemeValueFlagByProjectId(PROJECT_ID);
		int i = service.doUpdateSignedDate(param);
		if (i > 0)
			return new ReplyAjax().addOp(new OpLog("流程任务-企划签约", "保存签约日期",
					PROJECT_ID + ""));
		else
			throw new ActionException("保存签约日期失败");
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐江龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply SIGN_SAVE() {
		Map<String, Object> param = _getParameters();
		String PROJECT_ID = param.get("PROJECT_ID") + "";
		int i = service.doUpdateSIGN_SAVE(param);
		if (i > 0)
			return new ReplyAjax().addOp(new OpLog("流程任务-企划签约", "保存签约日期",
					PROJECT_ID + ""));
		else
			throw new ActionException("保存签约日期失败");
	}

	@aPermission(name = { "业务管理", "业务管理", "审批流程" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.USER)
	public Reply doShowProjectJbpmList() {
		context.put(
				"subList",
				service.doShowProjectJbpmList(_getParameters()
						.get("PROJECT_ID") + ""));
		return new ReplyHtml(VM.html("project/showProjectJbpmList.vm", context));
	}

	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doShowProjectJbpmList2() {
		context.put(
				"subList",
				service.doShowProjectJbpmList2(_getParameters().get("PAY_CODE")
						+ ""));
		return new ReplyHtml(VM.html("project/showProjectJbpmList.vm", context));
	}

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryCustByType() {
		this.getPageParameter();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (m != null && m.get("FINAL_TYPE") != null) {
			list = service.doSuppByClent(m.get("CLIENT_ID").toString());
		}
		return new ReplyJson(JSONArray.fromObject(list)).addOp(new OpLog(
				"客户管理", "立项-创建项目", "查询终端客户错误"));
	}

	// @aPermission(name = { "客户管理", "立项", "创建项目" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryBankByCust() {
		this.getPageParameter();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (m != null && m.get("FINAL_CUST") != null) {
			list = service.queryBank(m.get("FINAL_CUST").toString());
		}
		return new ReplyJson(JSONArray.fromObject(list)).addOp(new OpLog(
				"客户管理", "立项-创建项目", "查询银行账户错误"));
	}

	@aPermission(name = { "业务管理", "业务管理", "业务删除" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	@aAuth(type = aAuthType.USER)
	public Reply doDelProject() {
		String id = SkyEye.getRequest().getParameter("id");
		projectService service = new projectService();
		service.delProject(id);
		return new ReplyAjax();
	}

	/**
	 * 收回
	 * 
	 * @return
	 */
	@aPermission(name = { "业务管理", "业务管理", "业务删除" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	@aAuth(type = aAuthType.USER)
	public Reply doSh() {
		String id = SkyEye.getRequest().getParameter("id");
		projectService service = new projectService();
		service.delSh(id);
		return new ReplyAjax();
	}

	// @aPermission(name = { "项目管理", "项目一览", "导出资金结算确认函" })
	@aDev(code = "170017", email = "hanjian@pqsoft.cn", name = "韩建")
	@aAuth(type = aAuthType.LOGIN)
	public Reply fundConfirmationLetter() {
		Map<String, Object> param = _getParameters();
		Map<String, String> map = new HashMap<String, String>();
		map.put("pro_code", param.get("pro_code") + "");
		Map<String,Object> pb = Dao.selectOne("project.get_business_sector", map);
		String BUSINESS_SECTOR = pb.get("BUSINESS_SECTOR") + "";
		String VALUE_STR = pb.get("VALUE_STR") + "";
		String PROJECT_MODEL = pb.get("PROJECT_MODEL") + "";
		String fileName = "";
		if ("建机".equals(BUSINESS_SECTOR) && "4".equals(VALUE_STR)) {
			fileName = "A57JJ2";
		} else if ("建机".equals(BUSINESS_SECTOR) && !"1".equals(VALUE_STR)) {
			fileName = "A47JJ";
		} else if ("商用车".equals(BUSINESS_SECTOR) && "2".equals(PROJECT_MODEL)
				&& "3".equals(VALUE_STR)) {
			fileName = "A54SWC";
		} else if ("商用车".equals(BUSINESS_SECTOR) && "4".equals(PROJECT_MODEL)
				&& "3".equals(VALUE_STR)) {
			fileName = "A55SWC";
		} else if ("商用车".equals(BUSINESS_SECTOR) && "2".equals(PROJECT_MODEL)
				&& "2".equals(VALUE_STR)) {
			fileName = "A56SWC";
		} else if ("商用车".equals(BUSINESS_SECTOR) && !"1".equals(VALUE_STR)) {
			fileName = "A47SWC";
		}
		if ("".equals(fileName))
			throw new ActionException("全额放款无需导出");
		map.put("fileName", fileName + ".xls");
		return new FundConfirmationLetter(map);
	}

	/**
	 * 导出excel
	 * 
	 * @date 2013-12-11 下午08:32:13
	 * @author 韩晓龙
	 */
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply exportExcel() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);
		ManageService mgService = new ManageService();
		Map<String,Object> supMap = mgService.getSupByUserId(Security.getUser().getId());
		if (supMap != null && supMap.get("SUP_ID") != null) {
			param.put("SUP_ID", supMap.get("SUP_ID"));
		}
		return new ReplyExcel(service.exportData(param), "workFlow"
				+ DateUtil.getSysDate() + Math.abs(new Random(10000).nextInt())
				+ ".xls");
	}

	/**
	 * 查询票据
	 * 
	 * @return
	 * @author:King 2013-12-26
	 */
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toShowPJ() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PJ", service.toShowFP(param.get("PROJECT_CODE") + ""));
		return new ReplyHtml(VM.html("project/FP.vm", context));
	}

	/**
	 * 导出资产包模板
	 * 
	 * @return
	 * @author:King 2014-2-10
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply ExpZCBModel() {
//		Map<String, Object> param = _getParameters();
		try {
			return new ReplyFile(UTIL.RES.getResource(
					"classpath:file/property_bag_template.xls").getInputStream(),
					"property_bag_template.xls");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导入资产包文件
	 * 
	 * @return
	 * @author:King 2014-2-11
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply impZCB() {
		List<File> flist = _getFile();
		Map<String, Object> param = _getParameters();
		if (param.get("fileN").toString().contains(".xls")) {
			Map<String, Object> rstMap = service.parseZCBExcel(flist.get(0));
			if (StringUtils.isNotBlank(rstMap.get("EMPTYFRAMENUM"))) {
				String msg = rstMap.get("EMPTYFRAMENUM") + "";
				msg += null == rstMap.get("FALSEFRAMENUM") ? "" : rstMap
						.get("FALSEFRAMENUM");
				return new ReplyAjax(false, msg);
			} else {
				List<Map<String, Object>> list = (List<Map<String, Object>>) rstMap
						.get("list");
				for (Map<String, Object> map : list) {
					map.put("PROJ_NAME", param.get("PROJECT_NAME"));
					service.addProjectEquipmentZCB(map);
				}
				return new ReplyAjax(true).addOp(new OpLog("立项添加设备", "导入资产包",
						"项目" + param.get("PROJECT_NAME") + "导入资产包"
								+ list.size() + "条"));
			}
		} else {
			return new ReplyAjax(false, "使用的不是系统提供的模板");
		}
	}

	/**
	 * 删除导入的资产包
	 * 
	 * @return
	 * @author:King 2014-2-12
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply delZCB() {
		Map<String, Object> param = _getParameters();
		try {
			int i = service.delZCB(param.get("PROJECT_NAME") + "");
			return new ReplyAjax().addOp(new OpLog("项目立项", "资产包", "删除项目"
					+ param.get("PROJECT_NAME") + "的资产包" + i + "条"));
		} catch (Exception e) {
			return new ReplyAjax(false, "删除失败");
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply QUERYZCBModel() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("list", service.QUERYZCBModel(param));
		return new ReplyHtml(VM.html("project/zcbDetail.vm", context));
	}

	/**
	 * 查看大项目初审报告
	 * 
	 * @return
	 * @author:King 2014-2-18
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toBigProject() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("proInfo", service.queryBigProjectInfo(null == param
				.get("PROJECT_ID") ? "" : param.get("PROJECT_ID").toString()));

		return new ReplyHtml(VM.html("project/addBigProject.vm", context));
	}

	/**
	 * 手动发起大项目评审会流程
	 * 
	 * @return
	 * @author:King 2014-2-18
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply STARTSUBJBPMPSH() {
		Map<String, Object> param = _getParameters();
		System.out.println(param);
		StartSubBPM ss = new StartSubBPM();
		try {
			ss.startCompanyConfirm(param.get("JBPM_ID") + "");
			return new ReplyAjax();
		} catch (Exception e) {
			throw new ActionException("发起失败");
		}
	}

	@aPermission(name = { "业务管理", "业务管理", "方案删除" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐江龙")
	@aAuth(type = aAuthType.USER)
	public Reply SchemeDelete() {
		Map<String, Object> param = _getParameters();
		int num = service.SchemeDelete(param);
		if (num > 0) {
			return new ReplyAjax();
		}
		throw new ActionException("方案删除失败");
	}

	@aPermission(name = { "业务管理", "业务管理", "业务修改" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐江龙")
	@aAuth(type = aAuthType.USER)
	public Reply saveBaseInfo() {
		Map<String, Object> param = _getParameters();
		if(param.containsKey("CUST_STATUS")&&StringUtils.isNotBlank(param.get("CUST_STATUS"))&&"0".equals(param.get("CUST_STATUS"))){
			System.out.println("--------------param="+param);
			
				if(param.get("BANK_ID") !=null && !param.get("BANK_ID").equals("")){
					int a = service.selectBandId(param);
					//有记录就更新，没有就添加
					if(a>0){
						service.updateProjectCustBank(param);
					}else{
						param.put("BANK_ID", service.addProjectCustBank(param));
					}
				}
				else{
					param.put("BANK_ID", service.addProjectCustBank(param));
				}
			
		}
		int num = service.saveBaseInfo(param);
		if (num > 0) {
			return new ReplyAjax();
		}
		
		throw new ActionException("方案保存失败");
	}

	@aPermission(name = { "业务管理", "业务管理", "业务修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply projectUpdate_VM() {
		Map<String, Object> map = _getParameters();
		// 项目信息
		Map<String,Object> projectMap = service.queryProjectType4ById(map);
		if (projectMap == null || !(projectMap.size() > 0)) {
			return new ReplyHtml(VM.html(
					"project/project_scheme_updateNew1.vm", context));
		}
		map.putAll(projectMap);
		map.put("PROJECT_ID", projectMap.get("ID"));
		// 设备分组信息
		context.put("rowList", service.queryEqCountByProjectID(map));
		// 单位
		context.put("unitList", new DataDictionaryMemcached().get("设备单位"));
		// 开户行
		if (map.get("CUST_ID1") != null) {
			List<Map<String,Object>> bank = service.queryBank(map.get("CUST_ID1") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		} else if (map.get("CUST_ID") != null) {
			List<Map<String,Object>> bank = service.queryBank(map.get("CUST_ID") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		}

		// 承租人信息
		if (map.get("CUST_ID") != null) {
			map.put("CLIENT_ID", map.get("CUST_ID"));
			map.putAll(service.selectRenterDetails(map));
		}

		// 担保人信息
		// 担保人类型
		context.put("typeList", new DataDictionaryMemcached().get("客户类型"));
		context.put("CollateralList", new DataDictionaryMemcached().get("抵押物"));

		// 设备信息（商务政策）
		List<Map<String,Object>> eqlist = service.queryEqByProjectID(map);

		context.put("eqList", eqlist);
		
		List<Object> whetherSalesSi = (List)new DataDictionaryMemcached().get("采购类型");
		context.put("whetherSalesSi", whetherSalesSi);
		
		context.put("GuaranList", service.queryGuaranByProjectID(map));

		context.put("param", map);
		String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";
		Map<String, Object> dmap = new HashMap<String, Object>();
		dmap.put("TYPE", "业务类型");
		dmap.put("CODE", PLATFORM_TYPE);
		if ("5".equals(PLATFORM_TYPE)) {
			context.put("BIGPROJCOUNT",
					service.checkBigProjReportExist(map.get("PROJECT_ID") + ""));
		}
		Map<String, Object> mm = DataDictionaryService
				.queryDataDictionaryByTypeAndCode(dmap);
		if (mm != null && mm.size() > 0) {
			String SHORTNAME = mm.get("SHORTNAME") + "";
			if (SHORTNAME.equals("0"))// 扣款账户为客户
			{
				// 扣款类型
				List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> finalMap = new HashMap<String,Object>();
				finalMap.put("FINAL_TYPE", "0");
				finalMap.put("FINAL_NAME", "自有客户");
				final_Type_List.add(finalMap);
				context.put("final_Type_List", final_Type_List);

				// 客户名称
				List<Map<String,Object>> final_Cust_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> custMap = new HashMap<String,Object>();
				custMap.put("CUST_ID", map.get("CLIENT_ID"));
				custMap.put("CUST_NAME", map.get("CUSTOMER_NAME"));
				final_Cust_List.add(custMap);
				context.put("final_Cust_List", final_Cust_List);

				// 开户行
				List<Map<String,Object>> bank = new ArrayList<Map<String,Object>>();
				if (StringUtils.isBlank(map.get("CUST_ID")))
					bank = service.queryBank(map.get("CUST_ID1") + "");
				else
					bank = service.queryBank(map.get("CUST_ID") + "");
				if (bank.size() > 0) {
					context.put("bankList", bank);
				}
			} else if (SHORTNAME.equals("1"))// 扣款账户为终端客户
			{
				// 扣款类型
				List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> finalMap = new HashMap<String,Object>();
				finalMap.put("FINAL_TYPE", "1");
				// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
				// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
				// finalMap.put("FINAL_NAME", "担保人");
				// else
				finalMap.put("FINAL_NAME", "终端客户");
				final_Type_List.add(finalMap);
				context.put("final_Type_List", final_Type_List);

				// 通过该客户ID查询出该供应商在查出该供应商下的所以客户
				List<Map<String,Object>> final_Cust_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> custMap = new HashMap<String,Object>();
				custMap.put("CUST_ID", null);
				custMap.put("CUST_NAME", "--请选择--");
				final_Cust_List.add(custMap);

				final_Cust_List.addAll(service.doSuppByClent(map.get(
						"CLIENT_ID").toString()));
				context.put("final_Cust_List", final_Cust_List);
			} else// 扣款账户为选择
			{
				// 扣款类型
				List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
				Map<String,Object> finalMap2 = new HashMap<String,Object>();
				finalMap2.put("FINAL_TYPE", null);
				finalMap2.put("FINAL_NAME", "--请选择--");
				final_Type_List.add(finalMap2);

				Map<String,Object> finalMap = new HashMap<String,Object>();
				finalMap.put("FINAL_TYPE", "0");
				finalMap.put("FINAL_NAME", "自有客户");
				final_Type_List.add(finalMap);

				Map<String,Object> finalMap1 = new HashMap<String,Object>();
				finalMap1.put("FINAL_TYPE", "1");
				// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
				// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
				// finalMap1.put("FINAL_NAME", "担保人");
				// else
				finalMap1.put("FINAL_NAME", "终端客户");
				final_Type_List.add(finalMap1);
				context.put("final_Type_List", final_Type_List);

			}
		}

		// 发票开具类型
		context.put("invoice_target_type",  DataDictionaryService
		.queryDataDictionaryByScheme("发票_对象_类型"));
		// 租赁周期
		context.put("LEASE_TERM", new SysDictionaryMemcached().get("租赁周期"));

		context.put(
				"Bus_Type",
				service.QueryBusType(map.get("PROJECT_ID").toString())
						.get("PLATFORM_TYPE").toString());
		context.put("score", service.Fen(map.get("PROJECT_ID").toString()));
		return new ReplyHtml(VM.html("project/project_scheme_updateNew1.vm",
				context));
	}

	// @aPermission(name = { "项目管理", "项目一览","查看" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply projectUpdate_VIEW() {
		Map<String, Object> map = _getParameters();
		// 项目信息
		Map<String,Object> projectMap = service.queryProjectType4ById(map);
		if (projectMap == null || !(projectMap.size() > 0)) {
			return new ReplyHtml(VM.html("project/project_scheme_viewNew.vm",
					context));
		}
		map.putAll(projectMap);
		map.put("PROJECT_ID", projectMap.get("ID"));
		// 设备分组信息
		context.put("rowList", service.queryEqCountByProjectID(map));
		// 单位
		context.put("unitList", new DataDictionaryMemcached().get("设备单位"));
		// 开户行
		if (map.get("CUST_ID1") != null) {
			List<Map<String,Object>> bank = service.queryBank(map.get("CUST_ID1") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		} else if (map.get("CUST_ID") != null) {
			List<Map<String,Object>> bank = service.queryBank(map.get("CUST_ID") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		}

		// 承租人信息
		if (map.get("CUST_ID") != null) {
			map.put("CLIENT_ID", map.get("CUST_ID"));
			map.putAll(service.selectRenterDetails(map));
		}

		// 担保人信息
		// 担保人类型
		context.put("typeList", new DataDictionaryMemcached().get("客户类型"));
		context.put("CollateralList", new DataDictionaryMemcached().get("抵押物"));

		// 设备信息（商务政策）
		context.put("eqList", service.queryEqByProjectID(map));
		context.put("GuaranList",  service.queryGuaranByProjectID(map));

		context.put("param", map);
		String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";
		Map<String, Object> dmap = new HashMap<String, Object>();
		dmap.put("TYPE", "业务类型");
		dmap.put("CODE", PLATFORM_TYPE);
		if ("5".equals(PLATFORM_TYPE)) {
			context.put("BIGPROJCOUNT",
					service.checkBigProjReportExist(map.get("PROJECT_ID") + ""));
		}
		Map<String, Object> mm = DataDictionaryService
				.queryDataDictionaryByTypeAndCode(dmap);
		String SHORTNAME = mm.get("SHORTNAME") + "";
		if (SHORTNAME.equals("0"))// 扣款账户为客户
		{
			// 扣款类型
			List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
			Map<String,Object> finalMap = new HashMap<String,Object>();
			finalMap.put("FINAL_TYPE", "0");
			finalMap.put("FINAL_NAME", "自有客户");
			final_Type_List.add(finalMap);
			context.put("final_Type_List", final_Type_List);

			// 客户名称
			List<Map<String,Object>> final_Cust_List = new ArrayList<Map<String,Object>>();
			Map<String,Object> custMap = new HashMap<String,Object>();
			custMap.put("CUST_ID", map.get("CLIENT_ID"));
			custMap.put("CUST_NAME", map.get("CUSTOMER_NAME"));
			final_Cust_List.add(custMap);
			context.put("final_Cust_List", final_Cust_List);

			// 开户行
			List<Map<String,Object>> bank = new ArrayList<Map<String,Object>>();
			if (StringUtils.isBlank(map.get("CUST_ID")))
				bank = service.queryBank(map.get("CUST_ID1") + "");
			else
				bank = service.queryBank(map.get("CUST_ID") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		} else if (SHORTNAME.equals("1"))// 扣款账户为终端客户
		{
			// 扣款类型
			List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
			Map<String,Object> finalMap = new HashMap<String,Object>();
			finalMap.put("FINAL_TYPE", "1");
			// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
			// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
			// finalMap.put("FINAL_NAME", "担保人");
			// else
			finalMap.put("FINAL_NAME", "终端客户");
			final_Type_List.add(finalMap);
			context.put("final_Type_List", final_Type_List);

			// 通过该客户ID查询出该供应商在查出该供应商下的所以客户
			List<Map<String,Object>> final_Cust_List = new ArrayList<Map<String,Object>>();
			Map<String,Object> custMap = new HashMap<String,Object>();
			custMap.put("CUST_ID", null);
			custMap.put("CUST_NAME", "--请选择--");
			final_Cust_List.add(custMap);

			final_Cust_List.addAll(service.doSuppByClent(map.get("CLIENT_ID")
					.toString()));
			context.put("final_Cust_List", final_Cust_List);
		} else// 扣款账户为选择
		{
			// 扣款类型
			List<Map<String,Object>> final_Type_List = new ArrayList<Map<String,Object>>();
			Map<String,Object> finalMap2 = new HashMap<String,Object>();
			finalMap2.put("FINAL_TYPE", null);
			finalMap2.put("FINAL_NAME", "--请选择--");
			final_Type_List.add(finalMap2);

			Map<String,Object> finalMap = new HashMap<String,Object>();
			finalMap.put("FINAL_TYPE", "0");
			finalMap.put("FINAL_NAME", "自有客户");
			final_Type_List.add(finalMap);

			Map<String,Object> finalMap1 = new HashMap<String,Object>();
			finalMap1.put("FINAL_TYPE", "1");
			// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
			// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
			// finalMap1.put("FINAL_NAME", "担保人");
			// else
			finalMap1.put("FINAL_NAME", "终端客户");
			final_Type_List.add(finalMap1);
			context.put("final_Type_List", final_Type_List);

		}

		// 发票开具类型
		context.put("invoice_target_type", DataDictionaryService
		.queryDataDictionaryByScheme("发票_对象_类型"));
		// 租赁周期
		context.put("LEASE_TERM", new SysDictionaryMemcached().get("租赁周期"));

		context.put(
				"Bus_Type",
				service.QueryBusType(map.get("PROJECT_ID").toString())
						.get("PLATFORM_TYPE").toString());
		context.put("score", service.Fen(map.get("PROJECT_ID").toString()));
		return new ReplyHtml(VM.html("project/project_scheme_viewNew.vm",
				context));
	}

	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "XGM")
	@aAuth(type = aAuthType.LOGIN)
	public Reply ACCOUNTTYPE() {
		this.getPageParameter();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (m != null && m.get("ACCOUNTTYPE") != null) {
			list = service.queryACCOUNTTYPE(m);
		}
		return new ReplyJson(JSONArray.fromObject(list)).addOp(new OpLog(
				"客户管理", "立项-创建项目", "查询银行账户错误"));
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐江龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply isDunCust() {
		Map<String, Object> param = _getParameters();
		int num = service.isDunCust(param);
		return new ReplyAjax(num);
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply query_overdue_AllByCust() {
		// 项目提醒逾期记录
		Map<String, Object> param = _getParameters();
		context.put("ovarDueAll", service.query_overdue_AllByCust(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("project/query_overdue_AllByCust.vm",
				context));
	}

	@aPermission(name = { "立项审批", "音像资料", "上传资料页" })
	@aDev(code = "170012", email = "wuyf@pqsoft.cn", name = "WuYanFei")
	@aAuth(type = aAuthType.LOGIN)
	public Reply uploadFilePage() {
		Map<String, Object> map = _getParameters();
		context.put("param", map);
		return new ReplyHtml(VM.html("project/uploadFile.vm", context));
	}

	@aPermission(name = { "立项审批", "音像资料", "上传资料" })
	@aDev(code = "170012", email = "wuyf@pqsoft.cn", name = "WuYanFei")
	@aAuth(type = aAuthType.LOGIN)
	public Reply uploadFile() {
		List<FileItem> fileList = _getFileItem();
		Map<String, Object> map = (Map<String, Object>) service
				.uploadFileForOne(fileList);
		service.doInsertAttachment(map);
		return new ReplyAjax();
	}

	@aPermission(name = { "立项审批", "音像资料", "查看资料列表" })
	@aDev(code = "170012", email = "wuyf@pqsoft.cn", name = "WuYanFei")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findAttachments() {
		Map<String, Object> map = _getParameters();
		JSONArray json = JSONArray.fromObject(service.findAttachments(map));
		return new ReplyJson(json);
	}

	@aPermission(name = { "立项审批", "音像资料", "下载" })
	@aDev(code = "170012", email = "wuyf@pqsoft.cn", name = "WuYanFei")
	@aAuth(type = aAuthType.LOGIN)
	public Reply downloadFile() {
		Map<String, Object> map = _getParameters();
		return new ReplyFile(service.downloadFileForZip(map), "音像资料.zip");
	}

	@aPermission(name = { "立项审批", "音像资料", "删除文件" })
	@aDev(code = "170012", email = "wuyf@pqsoft.cn", name = "WuYanFei")
	@aAuth(type = aAuthType.LOGIN)
	public Reply deleteFile() {
		Map<String, Object> map = _getParameters();
		boolean flag = service.deleteFile(map);
		if (!flag)
			return new ReplyAjax(flag, "删除文件错误!");
		return new ReplyAjax();
	}

	/**
	 * 查询共同承租人
	 * 
	 * @return
	 * @author King 2015年3月5日
	 */
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryJOINT_TENANT() {
		Map<String, Object> param = _getParameters();
		param.put("CREATE_CODE", Security.getUser().getCode());
		if(param.containsKey("GT_CLIENT_TYPE")&&"2".equals(param.get("GT_CLIENT_TYPE"))){
			//自然人
			param.put("CLIENT_TYPE", "NP");
		}else if(param.containsKey("GT_CLIENT_TYPE")&&"3".equals(param.get("GT_CLIENT_TYPE"))){
			//法人
			param.put("CLIENT_TYPE", "LP");
		}
		return new ReplyAjax(service.queryJOINT_TENANT(param));
	}
	
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply checkBank(){
		Map<String,Object> map = _getParameters();
		boolean flag = true;
		int i = service.findBank(map);
		if(i>0){
			flag = false;
		}
		return new ReplyAjax(flag, null);
	}
	
	@aPermission(name={ "业务管理", "业务管理", "导出还款计划表" })
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	public Reply downLoadPayList(){
		Map<String,Object> param=_getParameters();
		BaseSchemeService schemeService=new BaseSchemeService();
		param.putAll(schemeService.getSchemeBaseInfoByProjectId(param.get("PROJECT_ID")+"", null, param.get("SCHEME_ROW_NUM")+"").get(0));
		List<Map<String,Object>> list=schemeService.getSchemeClob(null, param.get("PROJECT_ID") + "",
				param.get("SCHEME_ROW_NUM") + "");
		for (Map<String, Object> map : list) {
			if("SCHEME_SYL_BZ_VALUE".equals(map.get("KEY_NAME_EN"))){
				param.put("SCHEME_SYL_BZ_VALUE", map.get("VALUE_STR"));
			}else if("MQGLF".equals(map.get("KEY_NAME_EN"))){
				param.put("MQGLF", map.get("VALUE_STR"));
			}
		}
		List<Map<String,Object>> payList=null;
		if(StringUtils.isNotBlank(param.get("PAY_ID"))){
			param.put("ID", param.get("PAY_ID"));
			payList=new PayTaskService().doShowRentPlanDetail(param);
		}else
			payList=new PayTaskService().doShowRentPlanDetailScheme(param);
		service.payList(param, payList);
		return new ReplyExcel(service.exportData(payList, param), "还款计划表");
	}
	/*------------------------合同重签-20160219--------------------------*/

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同重签管理", "签约合同", "列表显示" })
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	public Reply project_re_sign_Main() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(
				VM.html("project/project_re_sign_Main.vm", context));
	}

	@aPermission(name = { "合同重签管理", "签约合同列表", "列表显示" })
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public Reply getDataListForReContract() {
		this.getPageParameter();
		context.put("list", new SysDictionaryMemcached().get("项目状态位"));
		context.put("PLATFORMTYPEList",
				new SysDictionaryMemcached().get("业务类型"));
		context.put("PContext", m);
		// context.put(
		// "isDelAuth",
		// Security.hasPermission(new String[] { "合同重签管理", "签约合同列表",
		// "重签作废" }));
		return new ReplyHtml(VM.html("project/project_re_sign_contract.vm",
				context));
	}
	@aPermission(name = { "合同重签管理", "签约合同列表", "列表数据" })
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public Reply pageAjaxCQ() {
		Map<String, Object> param = com.pqsoft.skyeye.api.Action._getParameters();
		BaseUtil.getDataAllAuth(param);
		Page page = service.queryPageCQ(param);
		return new ReplyAjaxPage(page);
	}
	/** 合同重签 **/
	@aPermission(name = { "合同重签管理", "签约合同列表","合同重签按钮" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	public Reply checkProjectReSignContract() {
		Map<String, Object> param = _getParameters();
		JSONObject jsonObject = new JSONObject();
		String PROJECT_ID = param.get("ID").toString();
		String reType = service.moveProjectData(PROJECT_ID);
		if (!"success".equals(reType)) {
			jsonObject.put("flag", false);
			jsonObject.put("content", reType);
		}else{
			/*2016年3月28日 15:36:05 吴国伟*/
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PROJECT_ID", param.get("ID"));
			map.put("MARK_SIGN", param.get("MARK_SIGN"));
			/*
			 * 更新项目重签原因方法
			 */
			service.updateProjectStatusById(map);
			
			jsonObject.put("flag", true);
		}
		return new ReplyAjax(jsonObject);
	}
	
	@aPermission(name = { "合同重签管理", "签约合同列表", "重签作废按钮" })
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	public Reply doInvalidProject() {
		Map<String, Object> map = _getParameters();
		projectService service = new projectService();
		/*
		 * 项目ID和作废状态100
		 */
		/*作废日志 2016年3月28日 15:19:42 吴国伟添加  PROJECT_ID  MARK_INVAILD  */
		//作废状态
		map.put("STATUS", 100);
		//log.debug("project:" + map.toString());
		/*
		 * 更新项目STATUS方法
		 */
		service.updateProjectStatusById(map);
		return new ReplyAjax();
	}

	/**
	 * 方案比对
	 * 
	 * @return
	 */
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	@aAuth(type = aAuthType.ALL)
	public Reply schemeComparison() {
		m = _getParameters();
		System.out.println(m.toString());
		//新项目方案信息-----------------------------------------
		service.queryProjectComparison(m);
		Map<String, Object> baseMap = service.queryInfoByEqBase(m);
		context.put("baseMap", baseMap);
		m.put("NEW_PROJECT_ID",  m.get("PROJECT_ID"));
		//原项目ID
		Map<String, Object> omap = service.queryOldProjectByID(m);
		m.put("PROJECT_ID", omap.get("PARENT_ID"));
		Map<String, Object> baseMapOld = service.queryInfoByEqBase(m);
		service.queryProjectComparison(m);
		context.put("baseMapOld", baseMapOld);
		
		
		/*合并数据*/
		context.put("htList", service.queryProjetShceme(m));
		/************************ 查询商务政策对应的利率规则 King 2014-08-21 **********************************************/
//		BaseSchemeService baseSchService = new BaseSchemeService();
//		Map<String, Object> maprate = new HashMap<String, Object>();
//		maprate.put("SCHEME_CODE", m.get("SCHEME_ID"));
//		// 年利率
		//m.put("rateList", baseSchService.doSelectBaseSchemeYearRate(maprate));
		// 手续费
		//m.put("feeList", baseSchService.doSelectBaseSchemeFeeRate(maprate));
		/**********************************************************************/
//		PayTaskService pay = new PayTaskService();
//		int PAY_ID = service.queryIdByProjectIdRowNum(m);
//		Map<String, Object> payMap = new HashMap<String, Object>();
//		payMap.put("ID", PAY_ID);
//		baseMap.put("ID", PAY_ID);
//		List<Map<String, Object>> listDetail = pay
//				.doShowRentPlanDetailScheme(payMap);
//		context.put("ONEMONEY", (listDetail.get(0)).get("ZJ") + "");
//		context.put("detailList", listDetail);
		context.put("FORMAT", UTIL.FORMAT);
		context.put("dicTag", Util.DICTAG);
		context.put("ywlx", new SysDictionaryMemcached().get("业务类型"));
//		m.put("SCHEME_CODE", m.get("SCHEME_ID"));
//		context.put("param", m);
//		System.out.println("---------------baseMap=" + baseMap);
//		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
//		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
//		m.put("SPID", Security.getUser().getOrg().getSpId());
//		SuppliersService suppService = new SuppliersService();
//		context.put("suppliersList", suppService.querySuppByCom(m));
//
//		context.put("SCHEME_ROW_NUM", m.get("SCHEME_ROW_NUM"));
//		context.put("PROJECT_ID", m.get("PROJECT_ID"));
//		context.put("SCHEME_ID", m.get("SCHEME_ID"));
		
		
		    
		    
		    
		return new ReplyHtml(VM.html("project/project_scheme_comparison.vm",
				context));
	}

	
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "合同重签管理", "发起流程" })
	public Reply projectStartJbpmCQ() {
		Map<String, Object> map = _getParameters();
		String PRO_ID = (String) map.get("PRO_ID");
		if(StringUtils.isBlank(PRO_ID)){
			return new ReplyAjax(false, "参数丢失：PRO_ID ");
		}
		
		// 项目信息
		Map<String, Object> projectDetails = service.selectProjectDetails(map);
		// 承租人详细信息
				Map<String, Object> renterDetails = service
						.selectRenterDetails(projectDetails);
				renterDetails.put("RENTER_NAME", renterDetails.get("NAME"));
				renterDetails.put("RENTER_CODE", renterDetails.get("CUST_CODE"));
				renterDetails.put("CUSTOMER_TYPE", renterDetails.get("TYPE"));
				renterDetails.put("PROJECT_ID", PRO_ID);
				renterDetails.put("PRO_CODE", projectDetails.get("PRO_CODE"));
				List<Map<String, Object>> electronicPhotoAlbumData = service
						.electronicPhotoAlbumData(renterDetails);
		//方案
		List<Map<String, Object>> projectscheme = service.querySechmeByProjectID(map);
		Map<String, Object> mapscheme=projectscheme.get(0);
		if(projectDetails.get("JBPM_ID")!=null && !"".equals(projectDetails.get("JBPM_ID").toString())){
			return new ReplyAjax(false, "","该项目已有流程");
		}
		List<String> jbpmList = JBPM.getDeploymentListByModelName("合同重签",
				projectDetails.get("PLATFORM_TYPE") + "", Security.getUser()
						.getOrg().getPlatformId());
		// 通过项目ID查询出供应商ID
		Map<String,Object> SuppMap = service.querySuppByProjectID(  PRO_ID);
		String nextCode = "" ;
		Map<String, Object> mapStatuscq = new HashMap<String, Object>();
		//String pid=jbpmList.get(2);
		if(!jbpmList.isEmpty()&&jbpmList.size()>0){
			for(String pid:jbpmList){
				if (pid == null) {
					throw new ActionException("业务模式没有匹配到审批流程");
				}
				/*=============*/
				if(pid.contains("合同重签流程")){
					System.out.println("jmap============hetongchongqian");
					Map<String, Object> jmap = new HashMap<String, Object>();
					jmap.put("PROJECT_ID", PRO_ID);
					if(SuppMap!=null)
					jmap.put("SUPPLIER_ID", SuppMap.get("SUPPLIER_ID"));
					jmap.put("KHLX", renterDetails.get("CUSTOMER_TYPE").toString().equals("NP")?"个人":"企业");
					jmap.put("RZE", UtilFinance.formatString(
							projectDetails.get("FINANCE_TOPRIC"), 0));
					if(mapscheme !=null && mapscheme.containsKey("SFBZHT")){
						jmap.put("SFBZHT", mapscheme.get("SFBZHT"));
					}else{
						jmap.put("SFBZHT", "");
					}
					ProcessInstance pid1 = JBPM.startProcessInstanceById(pid,
							PRO_ID, projectDetails.get("CLIENT_ID").toString(),
							null, jmap);

				     nextCode = new TaskService().getAssignee(pid1.getId());
					/****************** 修改项目状态 ************************************/
					
				     mapStatuscq.put("STATUS", 1);
				     mapStatuscq.put("PROJECT_ID", PRO_ID);
				     mapStatuscq.put("JBPM_ID", pid1.getId());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					mapStatuscq.put("APP_DATE", sdf.format(new Date()));//申请时间
					service.updateProjectStatusById(mapStatuscq);
				}else if(pid.contains("重签财务审核")){
					System.out.println("jmap============重签财务审核");
					Map<String, Object> jmap = new HashMap<String, Object>();
					jmap.put("PROJECT_ID", PRO_ID);
					if(SuppMap!=null)
					jmap.put("SUPPLIER_ID", SuppMap.get("SUPPLIER_ID"));
					jmap.put("KHLX", renterDetails.get("CUSTOMER_TYPE").toString().equals("NP")?"个人":"企业");
					jmap.put("RZE", UtilFinance.formatString(
							projectDetails.get("FINANCE_TOPRIC"), 0));
					if(mapscheme !=null && mapscheme.containsKey("SFBZHT")){
						jmap.put("SFBZHT", mapscheme.get("SFBZHT"));
					}else{
						jmap.put("SFBZHT", "");
					}
					ProcessInstance pid1 = JBPM.startProcessInstanceById(pid,
							PRO_ID, projectDetails.get("CLIENT_ID").toString(),
							null, jmap);

					/****************** 修改项目状态 ************************************/
//					Map<String, Object> mapStatus = new HashMap<String, Object>();
//					mapStatus.put("STATUS", 1);
//					mapStatus.put("PROJECT_ID", PRO_ID);
//					mapStatus.put("JBPM_ID_CW", pid1.getId());
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					mapStatus.put("APP_DATE", sdf.format(new Date()));//申请时间
//					service.updateProjectStatusById(mapStatus);
				}
			}
		} else
			throw new ActionException("没有找到流程图");
		
		
		
		
		

		/*********************************************************************/
		// 刷新项目资料
		map.put("PROJECT_ID", map.get("PRO_ID"));

		/*********************************************************************/
		if(nextCode==null){
			return new ReplyAjax(true, "", "提交成功").addOp(new OpLog(
					"合同重签管理", "发起流程", "发起的流程为" + mapStatuscq.get("JBPM_ID")));
		}
		return new ReplyAjax(true, nextCode, "下一步操作人为: ").addOp(new OpLog(
				"合同重签管理", "发起流程", "发起的流程为" + mapStatuscq.get("JBPM_ID")));
	}
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同重签管理", "作废理由","备注" })
	public Reply getProjectInvalid(){
		Map<String, Object> map = _getParameters();
		context.put("param", service.getProjectdInvalidSign(map));
		return new ReplyHtml(
				VM.html("project/projectInvalid.vm", context)); 
	}
	@aDev(code = "111", email = "guoweiwu@jiezhongchina.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同重签管理", "重签理由","备注" })
	public Reply getProjectSign(){
		Map<String, Object> map = _getParameters();
		context.put("param", service.getProjectdInvalidSign(map));
		return new ReplyHtml(
				VM.html("project/projectSign.vm", context)); 
	}
	
	/**
	 * 上传资料管理
	 * 
	 * @author 耿长宝
	 * @date 2013-8-28 上午11:19:21
	 */
	@aPermission(name = { "参数配置", "备份资料管理" , "上传" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "gengchangbao", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public Reply uploadPaperFileReturn() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<FileItem> fileList = _getFileItem();
		boolean flag = service.uploadPaperFile(param, fileList);
		if (flag) {
			return new ReplyAjax(true, param.get("FILE_PATH") + "," + param.get("FILE_NAME"));
		}
		System.out.println("******************************uploadPaperFileReturn**********************");
		return new ReplyAjax(false, "上传失败！");
	}
	
	/**
	 * 下载资料文件
	 * 
	 * @author 耿长宝
	 * @date 2013-8-28 上午11:19:21
	 */
	@aPermission(name = { "参数配置", "备份资料管理" , "下载" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "gengchangbao", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public ReplyFile downPaperFile() {
		Map<String, Object> param = _getParameters();
		if (param.get("PICTPATH") == null || "".equals(param.get("PICTPATH").toString())) {
			throw new ActionException("文件路径为空！");
		}
		String pdfPath = param.get("PICTPATH").toString();
		File file = new File(pdfPath);
		String fileName = file.getName();
		return new ReplyFile(file, fileName);
	}
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170027", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public Reply confirmMoney(){
		Map<String,Object> param = _getParameters();
		boolean flag = true;
		String msg = "";
		try{
			if(param!=null && param.get("IDS")!=null && !"".equals(param.get("IDS").toString())){
				Map mapID=new HashMap();
				mapID.put("IDS", param.get("IDS").toString());
				mapID.put("STATUS", param.get("STATUS").toString());
				service.updateDepositStarts(mapID);
			}
		}catch(Exception e){
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag, msg);
	}
	
	// add gengchangbao 2016年4月21日17:31:06  JZZL-168 start
	@aPermission(name = { "业务管理", "业务管理", "签约时间" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	public Reply SignDateUpdate() {
		m = _getParameters();
		System.out.println("===============m="+m);
		// 查询基本信息
		Map<String,Object> baseMap = service.queryInfoByEqBase(m);
		
		// add gengchangbao JZZL-246 2016年7月11日11:00:47 Start
		List<Map<String,Object>> FB_MapList = service.getAllFB(m);
		List<Map<String,Object>> FGS_MapList = null;
		Map<String,Object> FB_Map = null;
		if (baseMap.get("FGS_ORG_ID") != null && !"".equals(baseMap.get("FGS_ORG_ID").toString())){
			String FGS_ORG_ID = baseMap.get("FGS_ORG_ID").toString();
			m.put("FGS_ORG_ID", FGS_ORG_ID);
			FGS_MapList = service.getAllFGSbyFGSId(m);
			FB_Map = service.getFBbyFGSId(m);
		}
		context.put("VERSION_LIST", new DataDictionaryMemcached().get("合同版本"));
		context.put("FB_MapList", FB_MapList);
		context.put("FGS_MapList", FGS_MapList);
		context.put("FB_Map", FB_Map);
		// add gengchangbao JZZL-246 2016年7月11日11:00:54 End

		context.put("baseMap", baseMap);
		context.put("param", m);
		return new ReplyHtml(VM.html("project/signDateForUpdate.vm", context));
	}
	// add gengchangbao 2016年4月21日17:31:06  JZZL-168 end
	
	//add gengchangbao JZZL-246 2016年7月11日10:59:49 Start
	@aDev(code = "170051", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getAllFGSbyOrgId() {
		System.out.println("a===============" + _getParameters());
		return new ReplyJson(JSONArray.fromObject(service.getAllFGSbyOrgId(_getParameters())));
	}
	//add gengchangbao JZZL-246 2016年7月11日10:59:49 End

}

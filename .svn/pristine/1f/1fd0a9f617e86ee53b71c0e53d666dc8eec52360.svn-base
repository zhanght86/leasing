package com.pqsoft.customers.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.customModule.service.CustomModuleService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.customers.service.RZBService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.log.LogUtil;
import com.pqsoft.payment.service.paymentService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.secuEvaluate.service.NormScoreService;
import com.pqsoft.secuEvaluate.service.SecuEvaluateService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
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
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CustomersAction extends Action {

	Map<String, Object> m = null;
	private final String pagePath = "customers/";

	@Override
	public Reply execute() {
		return null;
	}

	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lc")
	@aAuth(type = aAuthType.ALL)
	public Reply toUrl(){
		VelocityContext context = new VelocityContext();
		context.put("url", _getParameters().get("url"));
		return new ReplyHtml(VM.html("tourl.vm", context));
	}
	/**
	 * getPageParameter 获取页面参数
	 * 
	 * @date 2013-8-28 下午06:25:35
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.ALL)
	private Map<String, Object> getPageParameter() {
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
		return m;
	}

	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理","客户管理","列表显示" })
	public Reply findCustomersPage() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		String TYPE = "客户类型";
		List<Object> list = (List) new DataDictionaryMemcached().get(TYPE);
		String TYPE1 = "营业执照有效期提醒";
		List<Object> business_remind = (List) new DataDictionaryMemcached()
				.get(TYPE1);
		if (business_remind != null && business_remind.size() > 0) {
			for (Object obj : business_remind) {
				Map<String, Object> b_remind = (Map<String, Object>) obj;
				context.put("CODE", b_remind.get("CODE"));
				break;
			}
		}
		context.put("addProject",
				Security.hasPermission(new String[] { "客户管理", "客户资料管理", "立项" }));
		context.put("addCust",
				Security.hasPermission(new String[] { "业务管理", "客户管理", "添加" }));
		context.put("cust_type", list);
		context.put("param", m);
		context.put("JUDGE_GUARANTOR", '0');
		return new ReplyHtml(VM.html(pagePath + "Customers.vm", context));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = {"客户管理", "客户资料管理", "列表" })
	public Reply findCustomersData() {
		Map<String, Object> param = _getParameters();
		param.put("JUDGE_GUARANTOR", "0");
		CustomersService service = new CustomersService();
		return new ReplyAjaxPage(service.findCustomers(param));
	}
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "添加" })
	public Reply toAddCust() {
		Map<String, Object> map = _getParameters();
		Map<String,Object> infoMap=new HashMap<String,Object>();//页面参数
		String CUST_TYPE = map.get("CUST_TYPE").toString();
		map.put("TYPE", CUST_TYPE) ;
		VelocityContext context = new VelocityContext();
		CustomersService service = new CustomersService();
		//service.findCustomField(map) ;
		AreaService areaS= new AreaService() ;
		
		//配置文件验证是否必填
		context.put("findCustField", service.findCustField(map));
		context.put("GLS_CODE", map.get("GLS_CODE"));
		context.put("type", service.findCustDataType());// 获取用户配置信息
		context.put("getProvince",  areaS.getAllProvince());
		context.put("CUST_TYPE", CUST_TYPE);
		context.put("CREDIT_ID", map.get("CREDIT_ID"));// 添加担保人是用到资信ID----付玉龙
		context.put("PROJECT_ID", map.get("PROJECT_ID"));// 添加担保人是用到项目ID----付玉龙
		
		//增加客户联系人类型
		context.put("emergencyTypeList", new SysDictionaryMemcached().get("联系人类型"));
		context.put("relationshipList", new SysDictionaryMemcached().get("申请人关系"));
		// 加载自定义字段  start
		
		CustomModuleService customModuleService = new CustomModuleService() ;
		String createTableName = "客户自然人表" ;
		if(CUST_TYPE.equals("LP")){
			createTableName = "客户法人表" ;
		}
		map.put("CREATE_TABLE_NAME", createTableName) ;
		
		//根据模块名称查询主表子表表名信息
		Map<String,Object> tableMap=customModuleService.queryTableByCreateTableName(map);
		if(tableMap!=null){
			//查询页面显示字段
			Map configMap=new HashMap();
			configMap.put("ID", tableMap.get("ID"));
			List info=customModuleService.queryDynamic_Field(configMap);
			context.put("info", info);
			
		
			
			context.put("dicTag", Util.DICTAG);
			
			//保存页面参数（1，子表表名，2，子表关联主表字段名，3，子表关联主表字段值）
			infoMap.put("SUN_TABLE_KEY_PAGE", tableMap.get("CREATE_TABLE"));//1，子表表名
			infoMap.put("SUN_FIELD_KEY_PAGE", tableMap.get("CREATE_F_KEY"));//2，子表关联主表字段名
		
			context.put("infoMap", infoMap);
			
			
		}
		
		//加载自定义字段 end
		if ("NP".equals(CUST_TYPE)) {
			return new ReplyHtml(
					VM.html(pagePath + "toAddCustNatu.vm", context));
		} else {
			context.put("rc_unit", new DataDictionaryMemcached().get("货币种类"));
			return new ReplyHtml(VM.html(pagePath + "toAddCustLegal.vm",
					context));
		}
	}

	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = {"客户管理", "客户资料管理", "添加"})
	public Reply checkIdCardNo() {
		CustomersService service = new CustomersService();
		Map<String, Object> param = _getParameters();
		boolean flag = false;
		if (param != null && !param.isEmpty()
				&& param.containsKey("ID_CARD_NO")
				&& !param.get("ID_CARD_NO").equals("")) {
			List<Map<String, Object>> listMap = service.selectCustData(param);
			if (listMap != null && !listMap.isEmpty() && listMap.size() > 0) {
				flag = true;
			}
		}
		return new ReplyAjax(flag);
	}
	
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = {"客户管理", "客户资料管理", "添加"})
	public Reply checkIdCardNoNew() {
		CustomersService service = new CustomersService();
		Map<String, Object> param = _getParameters();
		boolean flag = false;
		List<Map<String, Object>> listMap =new ArrayList<Map<String, Object>>() ;
		if (param != null && !param.isEmpty()
				&& param.containsKey("ID_CARD_NO")
				&& !param.get("ID_CARD_NO").equals("")) {
			listMap = service.selectCustDataNew(param);
			if (listMap != null && !listMap.isEmpty() && listMap.size() > 0) {
				flag = true;
			}
		}
		return new ReplyAjax(flag, listMap);
	}
	
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"客户管理", "客户资料管理", "添加" ,"同步"})
	public Reply syncCustInfo() {
		CustomersService service = new CustomersService();
		Map<String, Object> param = _getParameters();
		
		//通过组织机构编号遍历厂商信息是否存在相同的客户信息
		List<Map<String,Object>> l1 = service.getCustInfoFromSuppliersInfo(param) ;
		if(l1!=null&&l1.size()>0){
			return new ReplyAjax(true, JSONObject.fromObject(l1.get(0))) ;
		}
		return new ReplyAjax(false);
	}
	
	

	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = {"客户管理", "客户资料管理", "添加"})
	public Reply oragnizationCode() {
		CustomersService service = new CustomersService();
		Map<String, Object> param = _getParameters();
		boolean flag = false;
		List<Map<String, Object>> listMap =new ArrayList<Map<String, Object>>() ;
		if (param != null && !param.isEmpty()
				&& param.containsKey("ORAGNIZATION_CODE")
				&& !param.get("ORAGNIZATION_CODE").equals("")) {
			listMap = service.selectCustDataNew(param);
			if (listMap != null && !listMap.isEmpty() && listMap.size() > 0) {
				flag = true;
			}
		}
		return new ReplyAjax(flag, listMap);
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "添加" })
	// 判断客户是否已经存在
	public Reply toJudgeIsCust() {
		Map<String, Object> map = this.getPageParameter();
		CustomersService service = new CustomersService();
		// 判断用户是否存在
		int cust_count = service.IsCust(map);
		boolean flag = true;
		if (cust_count > 0) {
			flag = false;
		}
		return new ReplyAjax(flag, null);
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "添加" })
	public Reply doAddCust() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		CodeService codes = new CodeService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("param"));
		String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
		CustomModuleService customModuleService = new CustomModuleService() ;
		boolean flag = false;
		String msg = "";
		for (Object object : jsonArray) {
			Map<String, Object> m = (Map<String, Object>) object;
			m.put("ID", ID);
			m.put("USERID", Security.getUser().getId());
			m.put("USERCODE", param.get("USERCODE"));
			m.put("USERNAME", param.get("USERNAME"));
			String CUST_ID = codes.getCode("客户编号", ID, null).toString();
			m.put("CUST_ID", CUST_ID);

			// 记录客户历史情况
			String CUST_BASEINFO_ID = Dao.getSequence("SEQ_FIL_CUST_BASE_INFO");
			m.put("CUST_BASEINFO_ID", CUST_BASEINFO_ID);
			m.put("CLIENT_ID", ID);
			
			//start 	 保存自定义表
			Map<String,Object> ZDY_INFO = JSONObject.fromObject(m.get("ZDY_INFO")) ;
			if(ZDY_INFO.size()>0){
				System.out.println("=====yx======="+ZDY_INFO);
				if(ZDY_INFO.get("GLS_CODE")!=null&&!"".equals(ZDY_INFO.get("GLS_CODE").toString())){
					Dao.update("gls.updstate",ZDY_INFO);
				}
				ZDY_INFO.put("SUN_FIELD_VALUE_PAGE", ID) ;
				customModuleService.submitModuleInfo(ZDY_INFO) ;	
			}
			//end   保存自定义表
			
			//start 保存紧急联系人
			List<Map<String,Object>> EMERGENCY_INFO = JSONArray.fromObject(m.get("EMERGENCY_INFO")) ;
			service.doAddEmergencyInfo(EMERGENCY_INFO,ID) ;
			//end   保存紧急联系人
			
			int C = service.doAddCust(m);
			// 保存该客户第一手历史资料
			service.doAddCustBaseINfo(m);
			if (C > 0) {
				flag = true;
				msg = ID;
				{
					// 创建图像采集电子档案目录
					// 查询客户信息编号
					Map<String, Object> mmm = service.getCustCode(ID);
					String CUST_CODE = null == mmm ? "" : mmm.get("CUST_ID")
							+ "";
					FileCatalogUtil fcu = new FileCatalogUtil();
					List<Map<String, Object>> listSJZD = new DataDictionaryMemcached()
							.get("电子档案");
					for (int i = 0; i < listSJZD.size(); i++) {
						Map<String, Object> sjzdMap = listSJZD.get(i);
						sjzdMap.put("REMARK", sjzdMap.get("REMARK").toString()
								.replaceAll(" ", ""));
						if (m.get("TYPE").equals(sjzdMap.get("REMARK"))) {// 判断电子档案的承租人类型
							fcu.getCatalogIdByPath(m.get("CUST_NAME")
									.toString(), CUST_CODE,
									"电子档案/" + sjzdMap.get("FLAG"));
						}
					}
				}
				if (m.get("TYPE").toString().equals("NP")) {
					if (m.get("IS_MARRY") != null
							&& ("1Marriage"
									.equals(m.get("IS_MARRY").toString())
									|| "4Marriage".equals(m.get("IS_MARRY")
											.toString())
									|| "4".equals(m.get("IS_MARRY").toString()) || "1"
										.equals(m.get("IS_MARRY").toString()))) {
						try {
							m.put("CLIENT_ID", ID);
							int j = service.doAddCustSpoust(m);
							if (j > 0) {
								flag = true;
							} else {
								flag = false;
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// 插入兴趣爱好和性格
					service.insertANNEX(m, ID);
				}

			} else {
				flag = false;
				msg = ID;
			}
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("客户资料管理", "添加",
				"客户资料管理-添加"));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "查看" })
	public Reply toViewCustInfoMain() {
		Map<String, Object> param = this.getPageParameter();
		//流程状态
		String statusFlag=(String) SkyEye.getRequest().getSession().getAttribute("statusFlag");
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("IsModuleField", Util.MODULE);
		context.put("EARNINGS", Security.hasPermission(new String[]{"业务管理", "客户管理", "收益分析"}));
		if (param.get("TYPE") != null
				&& param.get("TYPE").toString().equals("NP")) {
			return new ReplyHtml(VM.html(pagePath + "toViewCustDetailMain.vm",
					context));
		} else {
			return new ReplyHtml(VM.html(pagePath
					+ "toViewCustDetailLegaMain.vm", context));
		}
	}

	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "查看", "客户基本信息" })
	public Reply toViewCustInfo() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		
		

		//start 自定义表
		
			CustomModuleService customModuleService = new CustomModuleService() ;
			Map infoMap=new HashMap();//页面参数
			//param需要三个参数（1，TYPE包含UPDATE，VIEW  2，ID 主表ID  3，MODULENAME 模块名称）
		
			String createTableName = "客户自然人表" ;
			if(param.get("TYPE").equals("LP")){
				createTableName = "客户法人表" ;
			}
			param.put("CREATE_TABLE_NAME", createTableName) ;
			
			//根据模块名称查询主表子表表名信息
			Map tableMap=customModuleService.queryTableByCreateTableName(param);
			if(tableMap!=null && tableMap.size()>0){
				//根据提供的字段查询出主表关联字段
				tableMap.put("PARENT_MODULE_ID", param.get("CLIENT_ID"));
				Map mapParent=customModuleService.getParentTableField(tableMap);
				tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD"));
				
				
				//查询页面显示字段
				Map configMap=new HashMap();
				configMap.put("ID", tableMap.get("ID"));
				List info=customModuleService.queryDynamic_Field(configMap);
				context.put("info", info);
				
				//查询数据库子表是否有数据，有的话读取之前保存的数据，没有的话显示默认值
				boolean isBooleanFk=customModuleService.isBooleanFk_tableDate(tableMap);
				context.put("FK_TableIsBoolean", isBooleanFk);
				if(isBooleanFk){//已经保存过信息
					Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
					context.put("SUN_TABLE_INFO", SUN_TABLE_INFO);
				}
				
				
				context.put("dicTag", Util.DICTAG);
				
				//保存页面参数（1，子表表名，2，子表关联主表字段名，3，子表关联主表字段值）
				infoMap.put("SUN_TABLE_KEY_PAGE", tableMap.get("CREATE_TABLE"));//1，子表表名
				infoMap.put("SUN_FIELD_KEY_PAGE", tableMap.get("CREATE_F_KEY"));//2，子表关联主表字段名
				infoMap.put("SUN_FIELD_VALUE_PAGE", mapParent.get("ASSOCIATEDFIELD"));//3，子表关联主表字段值
				context.put("infoMap", infoMap);
				
				
			}
		
			//end 自定义表
			
		
		CustomersService service = new CustomersService();
		Map<String, Object> cus = (Map<String, Object>) service.findCustDetail(param);
		AreaService areaS= new AreaService() ;
		
		context.put("EMERGENCY_INFO_COUNT",5) ;
		List<Map<String,Object>> emergencyList = service.getEmergencyInfoByClientId(cus.get("CLIENT_ID").toString()) ; 
		List<Map<String,Object>> emergencyList2 = new ArrayList<Map<String,Object>>() ;
		if(emergencyList!=null&&emergencyList.size()>0){
			context.put("EMERGENCY_INFO_COUNT",emergencyList.size()*5) ;
			int length = emergencyList.size() ;
			int syLength =  0 ;
			if(length>1){
				length = 1;
				syLength= emergencyList.size() ;
			}  
			for(int i=0;i<length;i++){
				int  j = i+1 ;
				cus.put("EMERGENCY_NAME_"+j, emergencyList.get(i).get("EMERGENCY_NAME")) ;
				cus.put("EMERGENCY_TYPE_"+j, emergencyList.get(i).get("EMERGENCY_TYPE")) ;
				cus.put("EMERGENCY_PHONE_"+j,  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				cus.put("EMERGENCY_ADDR_"+j,  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				cus.put("EMERGENCY_UNIT_"+j,  emergencyList.get(i).get("EMERGENCY_UNIT")) ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				cus.put("CLIENT_ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
				
				cus.put("ADDR_PROVINCE_"+j,  emergencyList.get(i).get("ADDR_PROVINCE")) ;				
				cus.put("ADDR_CITY_"+j,  emergencyList.get(i).get("ADDR_CITY")) ;
				cus.put("ADDR_COUNTY_"+j,  emergencyList.get(i).get("ADDR_COUNTY")) ;
				
				Map<String,Object> refer = new HashMap<String,Object>() ;
				if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_PROVINCE"))){
					refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_PROVINCE")) ;
					
					List<Object> houseAddressCity = areaS.getSubset(refer) ;
					cus.put("cityList_"+j, houseAddressCity);
					refer.clear();
					
					if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_CITY"))){
						refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_CITY")) ;
						List<Object> houseAddressCounty = areaS.getSubset(refer) ;
						cus.put("countyList_"+j, houseAddressCounty);
						refer.clear();
					}
				}	
				
				cus.put("ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
			
			}
			
			for(int i=1;i<syLength;i++){
				Map<String,Object> m = new HashMap<String,Object>() ;
				
				m.put("EMERGENCY_NAME", emergencyList.get(i).get("EMERGENCY_NAME")) ;
				m.put("EMERGENCY_TYPE", emergencyList.get(i).get("EMERGENCY_TYPE")) ;
				m.put("EMERGENCY_PHONE",  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				m.put("EMERGENCY_ADDR",  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				m.put("EMERGENCY_UNIT",  emergencyList.get(i).get("EMERGENCY_UNIT")) ;				
				m.put("EMERGENCY_RELATIONSHIP",  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				m.put("CLIENT_ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				
				m.put("ADDR_PROVINCE",  emergencyList.get(i).get("ADDR_PROVINCE")) ;				
				m.put("ADDR_CITY",  emergencyList.get(i).get("ADDR_CITY")) ;
				m.put("ADDR_COUNTY",  emergencyList.get(i).get("ADDR_COUNTY")) ;
				
				Map<String,Object> refer = new HashMap<String,Object>() ;
				if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_PROVINCE"))){
					refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_PROVINCE")) ;
					
					List<Object> houseAddressCity = areaS.getSubset(refer) ;
					m.put("cityList", houseAddressCity);
					refer.clear();
					
					if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_CITY"))){
						refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_CITY")) ;
						List<Object> houseAddressCounty = areaS.getSubset(refer) ;
						m.put("countyList", houseAddressCounty);
						refer.clear();
					}
				}	
				
				m.put("ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				emergencyList2.add(m) ;
			}
			
		}else{
			for(int i=0;i<1;i++){
				int  j = i+1 ;
				cus.put("EMERGENCY_NAME_"+j,"") ;
				cus.put("EMERGENCY_TYPE_"+j,"") ;
				cus.put("EMERGENCY_PHONE_"+j,  "") ;
				cus.put("EMERGENCY_ADDR_"+j,   "") ;				
				cus.put("EMERGENCY_UNIT_"+j,   "") ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,   "") ;
				cus.put("CLIENT_ID_"+j,  "") ;
				
				cus.put("ADDR_PROVINCE_"+j,   "") ;				
				cus.put("ADDR_CITY_"+j,   "") ;
				cus.put("ADDR_COUNTY_"+j,  "") ;
				cus.put("ID_"+j,   "") ;
			
			}
		}
		//增加客户联系人类型
		context.put("emergencyTypeList", new SysDictionaryMemcached().get("联系人类型"));
		context.put("relationshipList", new SysDictionaryMemcached().get("申请人关系"));
		context.put("EMERGENCY_INFO",emergencyList2) ;
		
		
		String ISCS="";
		if(param !=null && param.get("ISCS") !=null && !param.get("ISCS").equals("")){
			ISCS=param.get("ISCS").toString();
			if(ISCS.equals("1")){
				if(cus !=null && cus.get("ID_CARD_NO") !=null && !cus.get("ID_CARD_NO").equals("")){
					String res = Dao.selectOne("checkIdCard.getIdCariStateNew", cus);
					cus.put("RESCARDYZ", res);
				}else{
					cus.put("RESCARDYZ", "wrz");
				}
			}
		}
		context.put("custInfo", cus);// 客户详细信息 
		context.put("custLinkInfo", service.findCustLinkInfo(param));// 客户关联信息
		context.put("type", service.findCustDataType());// 客户关联信息
		context.put("xqah", service.getANNEX(param, "XQAH", "兴趣爱好"));// 兴趣爱好
		context.put("xg", service.getANNEX(param, "XG", "性格"));// 性格
		context.put("getProvince", areaS.getAllProvince());// 省
		
		if (param.get("tab").equals("view")) {// 查看
			if (param.get("TYPE") != null) {
				if (param.get("TYPE").toString().equals("NP")) {// 进入自然人页面

					Map<String,Object> refer = new HashMap<String,Object>() ;
					if(StringUtils.isNotBlank(cus.get("COMPANY_ADDR_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("COMPANY_ADDR_PROVINCE")) ;
						//工作地址市
						List<Object> companyAddrCity = areaS.getSubset(refer) ;
						context.put("companyAddrCity", companyAddrCity);
						refer.clear();
						//工作地址(区/县)
						if(StringUtils.isNotBlank(cus.get("COMPANY_ADDR_CITY"))){
							refer.put("PARENT_ID", cus.get("COMPANY_ADDR_CITY")) ;
							List<Object> companyAddrCounty = areaS.getSubset(refer) ;
							context.put("companyAddrCounty", companyAddrCounty);
							refer.clear();
						}
					}
					
					if(StringUtils.isNotBlank(cus.get("HOUSE_ADDRESS_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("HOUSE_ADDRESS_PROVINCE")) ;
						//家庭地址市
						List<Object> houseAddressCity = areaS.getSubset(refer) ;
						context.put("houseAddressCity", houseAddressCity);
						refer.clear();
						//家庭地址(区/县)
						if(StringUtils.isNotBlank(cus.get("HOUSE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("HOUSE_ADDRESS_CITY")) ;
							List<Object> houseAddressCounty = areaS.getSubset(refer) ;
							context.put("houseAddressCounty", houseAddressCounty);
							refer.clear();
						}
					}	
					
					Map<String,Object> po=(Map<String, Object>) service.findCustLinkInfo(param) ;
					if(po!=null&&po.size()>0){
						Map<String,Object> spoustDet = (Map<String, Object>) po.get("spoustDet") ;
						if(spoustDet!=null&&spoustDet.size()>0){
							//配偶
//							if(StringUtils.isNotBlank(spoustDet.get("SPOUST_COMPANY_ADDR_PROVINCE"))){
							if(StringUtils.isNotBlank(spoustDet.get("COMPANY_ADDR_PROVINCE"))){
								refer.put("PARENT_ID", spoustDet.get("COMPANY_ADDR_PROVINCE")) ;
								//单位地址市
								List<Object> spoustCompanyAddrCity = areaS.getSubset(refer) ;
								context.put("spoustCompanyAddrCity", spoustCompanyAddrCity);
								refer.clear();
								//单位地址 	(区/县)
//								if(StringUtils.isNotBlank(spoustDet.get("SPOUST_COMPANY_ADDR_CITY"))){
								if(StringUtils.isNotBlank(spoustDet.get("COMPANY_ADDR_CITY"))){
									refer.put("PARENT_ID", spoustDet.get("COMPANY_ADDR_CITY")) ;
									List<Object> spoustCompanyAddrCounty = areaS.getSubset(refer) ;
									context.put("spoustCompanyAddrCounty", spoustCompanyAddrCounty);
									refer.clear();
								}
								
							}
						}
						
					}
					

					return new ReplyHtml(VM.html(pagePath
							+ "toViewCustDetaiLNatu.vm", context));
				} else {// 进入法人页面
					// context.put("financeData",
					// service.selectFinanceData(param));//法人财报===付玉龙
					
					Map<String,Object> refer = new HashMap<String,Object>() ;
					
					if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("REGISTE_ADDRESS_PROVINCE")) ;
						//法人注册地址市
						List<Object> registeAddressCity = areaS.getSubset(refer) ;
						context.put("registeAddressCity", registeAddressCity);
						//法人注册地址(区/县)
						refer.clear();
						if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("REGISTE_ADDRESS_CITY")) ;
							List<Object> registeAddressCounty = areaS.getSubset(refer) ;
							context.put("registeAddressCounty", registeAddressCounty);
							refer.clear();
						}	
					}
					
					if(StringUtils.isNotBlank(cus.get("WORK_ADDRESS_PROVINCE"))){
					
						refer.put("PARENT_ID", cus.get("WORK_ADDRESS_PROVINCE")) ;
						//公司办公地址市
						List<Object> workAddressCity = areaS.getSubset(refer) ;
						context.put("workAddressCity", workAddressCity);
						refer.clear();
						//公司办公地址(区/县)
						if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("WORK_ADDRESS_CITY")) ;
							List<Object> workAddressCounty = areaS.getSubset(refer) ;
							context.put("workAddressCounty", workAddressCounty);
							refer.clear();
						}
					}
					
					context.put("rc_unit",
							new DataDictionaryMemcached().get("货币种类"));
					return new ReplyHtml(VM.html(pagePath
							+ "toViewCustDetailLegal.vm", context));
				}
			}
		}
		return null;// 默认进入自然人页面
	}

	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"项目管理", "项目一览","查看"})
	public Reply toViewCustInfoProject() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param", param);

		CustomersService service = new CustomersService();
		Map<String, Object> cus = (Map<String, Object>) service
				.findCustDetail(param);
		context.put("custInfo", cus);// 客户详细信息
		context.put("custLinkInfo", service.findCustLinkInfo(param));// 客户关联信息
		context.put("type", service.findCustDataType());// 客户关联信息
		context.put("xqah", service.getANNEX(param, "XQAH", "兴趣爱好"));// 兴趣爱好
		context.put("xg", service.getANNEX(param, "XG", "性格"));// 性格
		context.put("getProvince", service.getProvince1());// 省
		
		AreaService areaS= new AreaService() ;
		context.put("getProvince", areaS.getAllProvince());// 省
			if (cus.containsKey("PROVINCE")
					&& !StringUtils.isBlank(cus.get("PROVINCE"))) {
				param.put("PARENT_ID", cus.get("PROVINCE"));
				context.put("city",  areaS.getSubset(param));
			}


		
		
		
		if (param.get("tab").equals("view")) {// 查看
			if (param.get("TYPE") != null) {
				if (param.get("TYPE").toString().equals("NP")) {// 进入自然人页面
					return new ReplyHtml(VM.html(pagePath
							+ "toViewCustDetaiLNatu.vm", context));
				} else {// 进入法人页面
					// context.put("financeData",
					// service.selectFinanceData(param));//法人财报===付玉龙
					context.put("rc_unit",
							new DataDictionaryMemcached().get("货币种类"));
					return new ReplyHtml(VM.html(pagePath
							+ "toViewCustDetailLegal.vm", context));
				}
			}
		}
		return null;// 默认进入自然人页面
	}

	@aDev(code = "170051", email = "qijianglong@163.com", name = "齐江龙")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"客户管理","客户资料管理","查看终端客户详细信息"})
	public Reply toViewCustInfo1() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		CustomersService service = new CustomersService();
		context.put("custInfo", service.findCustDetail(param));// 客户详细信息
		context.put("custLinkInfo", service.findCustLinkInfo(param));// 客户关联信息
		context.put("type", service.findCustDataType());// 客户关联信息
		context.put("getProvince", service.getProvince1());// 省
		if (param.get("TYPE") != null) {
			if (param.get("TYPE").toString().equals("NP")) {// 进入自然人页面
				return new ReplyHtml(VM.html(pagePath
						+ "toViewCustDetaiLNatu.vm", context));
			} else {// 进入法人页面
				// context.put("financeData",
				// service.selectFinanceData(param));//法人财报===付玉龙
				return new ReplyHtml(VM.html(pagePath
						+ "toViewCustDetailLegal.vm", context));
			}
		}
		return null;// 默认进入自然人页面
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "修改" })
	public Reply toUpdateCustInfoMain() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("CREDIT_ID", param.get("CREDIT_ID"));
		context.put("EARNINGS", Security.hasPermission(new String[]{"业务管理", "客户管理", "收益分析"}));
		context.put("IsModuleField", Util.MODULE);
		if (param.get("TYPE") != null
				&& param.get("TYPE").toString().equals("NP")) {
			return new ReplyHtml(VM.html(pagePath + "toUpdateCustNatuMain.vm",
					context));
		} else {
			return new ReplyHtml(VM.html(pagePath + "toUpdateCustLegaMain.vm",
					context));
		}
	}

	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "修改", "客户基本信息" })
	public Reply toUpdateCustInfo1() {
		Map<String, Object> param = this.getPageParameter();
		
		CustomersService service = new CustomersService();
		
		if(param.get("PROJECT_ID") !=null && !param.get("PROJECT_ID").equals("")){
			String PROJECT_ID=param.get("PROJECT_ID").toString();
			//如果存在项目ID说明是通过项目修改过来的客户，则通过项目查询出客户信息替换过来的参数
			Map clientMap=service.queryClientByProject_id(PROJECT_ID);
			if(clientMap !=null){
				param.put("CLIENT_ID", clientMap.get("CLIENT_ID"));
				param.put("TYPE", clientMap.get("TYPE"));
			}
		}
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		
		//start 自定义表
		
		CustomModuleService customModuleService = new CustomModuleService() ;
		Map infoMap=new HashMap();//页面参数
		//param需要三个参数（1，TYPE包含UPDATE，VIEW  2，ID 主表ID  3，MODULENAME 模块名称）
	
		String createTableName = "客户自然人表" ;
		if(param.get("TYPE").equals("LP")){
			createTableName = "客户法人表" ;
		}
		param.put("CREATE_TABLE_NAME", createTableName) ;
		
		//根据模块名称查询主表子表表名信息
		Map tableMap=customModuleService.queryTableByCreateTableName(param);
		if(tableMap!=null && tableMap.size()>0){
			//根据提供的字段查询出主表关联字段
			tableMap.put("PARENT_MODULE_ID", param.get("CLIENT_ID"));
			Map mapParent=customModuleService.getParentTableField(tableMap);
			tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD"));
			
			//查询页面显示字段
			Map configMap=new HashMap();
			configMap.put("ID", tableMap.get("ID"));
			List info=customModuleService.queryDynamic_Field(configMap);
			context.put("info", info);
			
			//查询数据库子表是否有数据，有的话读取之前保存的数据，没有的话显示默认值
			boolean isBooleanFk=customModuleService.isBooleanFk_tableDate(tableMap);
			context.put("FK_TableIsBoolean", isBooleanFk);
			if(isBooleanFk){//已经保存过信息
				Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
				context.put("SUN_TABLE_INFO", SUN_TABLE_INFO);
			}
			
			context.put("dicTag", Util.DICTAG);
			
			//保存页面参数（1，子表表名，2，子表关联主表字段名，3，子表关联主表字段值）
			infoMap.put("SUN_TABLE_KEY_PAGE", tableMap.get("CREATE_TABLE"));//1，子表表名
			infoMap.put("SUN_FIELD_KEY_PAGE", tableMap.get("CREATE_F_KEY"));//2，子表关联主表字段名
			infoMap.put("SUN_FIELD_VALUE_PAGE", mapParent.get("ASSOCIATEDFIELD"));//3，子表关联主表字段值
			context.put("infoMap", infoMap);
		}
	
		//end 自定义表
		
		// TODO 联系人数据格式整理	START
		AreaService areaS = new AreaService() ;
		// 已知的项目编号（其它信息）查出承租人信息
		Map<String, Object> cus = (Map<String, Object>) service.findCustDetail(param);
		context.put("EMERGENCY_INFO_COUNT",5) ;
		// 根据承租人ID查出对应联系人信息
		List<Map<String,Object>> emergencyList = service.getEmergencyInfoByClientId(cus.get("CLIENT_ID").toString()) ; 
		// 实例化存放联系人信息的空间
		List<Map<String,Object>> emergencyList2 = new ArrayList<Map<String,Object>>();
		
		// 联系人列表不可为空切必须有值
		if(emergencyList != null && emergencyList.size() > 0){
			
			context.put("EMERGENCY_INFO_COUNT", emergencyList.size() * 5);
			// 联系人列表长度
			int length = emergencyList.size();
			
			// 新实例一个整数，如果长度大于1，则length重新赋值，syLength赋值为联系人的长度
			int syLength = 0;
			if(length > 1){
				length = 1;
				syLength = emergencyList.size() ;
				
			}  
			// 取得联系人第一列数据，放入承租人存放空间内 START
			for(int i = 0; i < length; i++){
				int  j = i + 1 ;
				cus.put("EMERGENCY_ID_" + j, emergencyList.get(i).get("ID"));
				cus.put("EMERGENCY_NAME_"+j, emergencyList.get(i).get("EMERGENCY_NAME")) ;
				cus.put("EMERGENCY_TYPE_"+j, emergencyList.get(i).get("EMERGENCY_TYPE")) ;
				cus.put("EMERGENCY_PHONE_"+j,  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				cus.put("EMERGENCY_ADDR_"+j,  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				cus.put("EMERGENCY_UNIT_"+j,  emergencyList.get(i).get("EMERGENCY_UNIT")) ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				cus.put("CLIENT_ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
				
				cus.put("ADDR_PROVINCE_"+j,  emergencyList.get(i).get("ADDR_PROVINCE")) ;				
				cus.put("ADDR_CITY_"+j,  emergencyList.get(i).get("ADDR_CITY")) ;
				cus.put("ADDR_COUNTY_"+j,  emergencyList.get(i).get("ADDR_COUNTY")) ;
				
				Map<String,Object> refer = new HashMap<String,Object>() ;
				if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_PROVINCE"))){
					refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_PROVINCE")) ;
					
					List<Object> houseAddressCity = areaS.getSubset(refer) ;
					cus.put("cityList_"+j, houseAddressCity);
					refer.clear();
					
					if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_CITY"))){
						refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_CITY")) ;
						List<Object> houseAddressCounty = areaS.getSubset(refer) ;
						cus.put("countyList_"+j, houseAddressCounty);
						refer.clear();
					}
				}	
				
				cus.put("ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
			
			}
			// 取得联系人第一列数据，放入承租人存放空间内	END
			
			// 循环把剩余的联系人放入emergencyList2列表中	START
			for(int i = 1; i < syLength; i++){
				Map<String,Object> m = new HashMap<String,Object>() ;
				
				m.put("EMERGENCY_ID", emergencyList.get(i).get("ID"));
				m.put("EMERGENCY_NAME", emergencyList.get(i).get("EMERGENCY_NAME")) ;
				m.put("EMERGENCY_TYPE", emergencyList.get(i).get("EMERGENCY_TYPE")) ;
				m.put("EMERGENCY_PHONE",  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				m.put("EMERGENCY_ADDR",  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				m.put("EMERGENCY_UNIT",  emergencyList.get(i).get("EMERGENCY_UNIT")) ;				
				m.put("EMERGENCY_RELATIONSHIP",  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				m.put("CLIENT_ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				m.put("ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				
				m.put("ADDR_PROVINCE",  emergencyList.get(i).get("ADDR_PROVINCE")) ;				
				m.put("ADDR_CITY",  emergencyList.get(i).get("ADDR_CITY")) ;
				m.put("ADDR_COUNTY",  emergencyList.get(i).get("ADDR_COUNTY")) ;
				
				Map<String,Object> refer = new HashMap<String,Object>() ;
				if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_PROVINCE"))){
					refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_PROVINCE")) ;
					
					List<Object> houseAddressCity = areaS.getSubset(refer) ;
					m.put("cityList", houseAddressCity);
					refer.clear();
					
					if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_CITY"))){
						refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_CITY")) ;
						List<Object> houseAddressCounty = areaS.getSubset(refer) ;
						m.put("countyList", houseAddressCounty);
						refer.clear();
					}
				}	
				
				emergencyList2.add(m) ;
			}
			// 循环把剩余的联系人放入emergencyList2列表中	END
		}else{
			for(int i = 0; i < 2; i++){
				int  j = i + 1;
				cus.put("EMERGENCY_NAME_"+j,"") ;
				cus.put("EMERGENCY_TYPE_"+j,"") ;
				cus.put("EMERGENCY_PHONE_"+j,  "") ;
				cus.put("EMERGENCY_ADDR_"+j,   "") ;				
				cus.put("EMERGENCY_UNIT_"+j,   "") ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,   "") ;
				cus.put("CLIENT_ID_"+j,  "") ;
				
				cus.put("ADDR_PROVINCE_"+j,   "") ;				
				cus.put("ADDR_CITY_"+j,   "") ;
				cus.put("ADDR_COUNTY_"+j,  "") ;
				
				cus.put("ID_"+j,   "") ;
			
			}
		}
		//增加客户联系人类型
		context.put("emergencyTypeList", new SysDictionaryMemcached().get("联系人类型"));
		context.put("relationshipList", new SysDictionaryMemcached().get("申请人关系"));
		context.put("EMERGENCY_INFO",emergencyList2) ;//TODO
		context.put("findCustField", service.findCustField(param));
		context.put("custInfo", cus);// 客户详细信息 TODO
		context.put("custLinkInfo", service.findCustLinkInfo(param));// 客户关联信息
		context.put("type", service.findCustDataType());// 客户关联信息
		context.put("xqah", service.getANNEX(param, "XQAH", "兴趣爱好"));// 兴趣爱好
		context.put("xg", service.getANNEX(param, "XG", "性格"));// 性格
		context.put("getProvince", areaS.getAllProvince());// 省
		
		
		if (param.get("tab").equals("update")) {// 修改
			if (param.get("TYPE") != null) {
				if (param.get("TYPE").toString().equals("NP")) {// 进入自然人页面
					
					Map<String,Object> refer = new HashMap<String,Object>() ;
					if(StringUtils.isNotBlank(cus.get("COMPANY_ADDR_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("COMPANY_ADDR_PROVINCE")) ;
						//工作地址市
						List<Object> companyAddrCity = areaS.getSubset(refer) ;
						context.put("companyAddrCity", companyAddrCity);
						refer.clear();
						//工作地址(区/县)
						if(StringUtils.isNotBlank(cus.get("COMPANY_ADDR_CITY"))){
							refer.put("PARENT_ID", cus.get("COMPANY_ADDR_CITY")) ;
							List<Object> companyAddrCounty = areaS.getSubset(refer) ;
							context.put("companyAddrCounty", companyAddrCounty);
							refer.clear();
						}
					}
					
					if(StringUtils.isNotBlank(cus.get("HOUSE_ADDRESS_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("HOUSE_ADDRESS_PROVINCE")) ;
						//家庭地址市
						List<Object> houseAddressCity = areaS.getSubset(refer) ;
						context.put("houseAddressCity", houseAddressCity);
						refer.clear();
						//家庭地址(区/县)
						if(StringUtils.isNotBlank(cus.get("HOUSE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("HOUSE_ADDRESS_CITY")) ;
							List<Object> houseAddressCounty = areaS.getSubset(refer) ;
							context.put("houseAddressCounty", houseAddressCounty);
							refer.clear();
						}
					}	
					
					Map<String,Object> po=(Map<String, Object>) service.findCustLinkInfo(param) ;
					if(po!=null&&po.size()>0){
						Map<String,Object> spoustDet = (Map<String, Object>) po.get("spoustDet") ;
						if(spoustDet!=null&&spoustDet.size()>0){
							//配偶
//							if(StringUtils.isNotBlank(spoustDet.get("SPOUST_COMPANY_ADDR_PROVINCE"))){
							if(StringUtils.isNotBlank(spoustDet.get("COMPANY_ADDR_PROVINCE"))){
								refer.put("PARENT_ID", spoustDet.get("COMPANY_ADDR_PROVINCE")) ;
								//单位地址市
								List<Object> spoustCompanyAddrCity = areaS.getSubset(refer) ;
								context.put("spoustCompanyAddrCity", spoustCompanyAddrCity);
								refer.clear();
								//单位地址 	(区/县)
//								if(StringUtils.isNotBlank(spoustDet.get("SPOUST_COMPANY_ADDR_CITY"))){
								if(StringUtils.isNotBlank(spoustDet.get("COMPANY_ADDR_CITY"))){
									refer.put("PARENT_ID", spoustDet.get("COMPANY_ADDR_CITY")) ;
									List<Object> spoustCompanyAddrCounty = areaS.getSubset(refer) ;
									context.put("spoustCompanyAddrCounty", spoustCompanyAddrCounty);
									refer.clear();
								}
								
							}
						}
						
					}
					//TODO
					return new ReplyHtml(VM.html(pagePath + "toUpdateCustNatu.vm", context));
				} else {
					Map<String,Object> refer = new HashMap<String,Object>() ;
					
					if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("REGISTE_ADDRESS_PROVINCE")) ;
						//法人注册地址市
						List<Object> registeAddressCity = areaS.getSubset(refer) ;
						context.put("registeAddressCity", registeAddressCity);
						//法人注册地址(区/县)
						refer.clear();
						if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("REGISTE_ADDRESS_CITY")) ;
							List<Object> registeAddressCounty = areaS.getSubset(refer) ;
							context.put("registeAddressCounty", registeAddressCounty);
							refer.clear();
						}	
					}
					
					if(StringUtils.isNotBlank(cus.get("WORK_ADDRESS_PROVINCE"))){
					
						refer.put("PARENT_ID", cus.get("WORK_ADDRESS_PROVINCE")) ;
						//公司办公地址市
						List<Object> workAddressCity = areaS.getSubset(refer) ;
						context.put("workAddressCity", workAddressCity);
						refer.clear();
						//公司办公地址(区/县)
						if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("WORK_ADDRESS_CITY")) ;
							List<Object> workAddressCounty = areaS.getSubset(refer) ;
							context.put("workAddressCounty", workAddressCounty);
							refer.clear();
						}
					}
					// 进入法人页面
					context.put("rc_unit",
							new DataDictionaryMemcached().get("货币种类"));
					return new ReplyHtml(VM.html(pagePath + "toUpdateCustLegal.vm", context));
				}
			}
		}
		return null;// 默认进入自然人页面
	}
	
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "修改", "客户基本信息" })
	public Reply toUpdateCustInfoNew() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		
		//项目的客户处理
		CustomersService service = new CustomersService();
		int num=service.updateProjectCust(param);
		
		List<Map<String,String>> extendList = new ArrayList<Map<String,String>>() ;
		//查询操作前数据
		Map<String, Object> opBefore = Dao.selectOne("customers.findCustDetailForLog", param);
		//Add by lishuo 12-29-2015 Start
		//mod 2016年7月12日10:32:13      婚姻状态为空导致的问题  Start
		//param.put("IS_MARRY",opBefore.get("IS_MARRY") =="" ? null : opBefore.get("IS_MARRY").toString()); 
		param.put("IS_MARRY",null==opBefore.get("IS_MARRY") || "".equals(opBefore.get("IS_MARRY")) ? null : opBefore.get("IS_MARRY").toString());
		//mod 2016年7月12日10:32:13      婚姻状态为空导致的问题  End 
		System.out.println("IS_MARRY="+param.get("IS_MARRY"));
		//Add by lishuo 12-29-2015 End
		String createTableName = "客户自然人表" ;
		if(StringUtils.isNotBlank(opBefore)&&opBefore.containsKey("TYPE")&&opBefore.get("TYPE").equals("LP")){
			createTableName = "客户法人表" ;
		}
		param.put("CREATE_TABLE_NAME", createTableName) ;
		
		
		boolean isBooleanFk = false ;
		CustomModuleService customModuleService=new CustomModuleService();;
		//根据模块名称查询主表子表表名信息
		Map tableMap=customModuleService.queryTableByCreateTableName(param);
		if(tableMap!=null&& tableMap.size()>0){
			
			//根据提供的字段查询出主表关联字段
			tableMap.put("PARENT_MODULE_ID", param.get("CLIENT_ID"));
			Map mapParent=customModuleService.getParentTableField(tableMap);
			tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD"));
			
			
			//查询页面显示字段
			Map configMap=new HashMap();
			configMap.put("ID", tableMap.get("ID"));
			List info=customModuleService.queryDynamic_Field(configMap);
			for(int i=0;i<info.size();i++){
				Map<String,Object> m1 = (Map<String,Object>) info.get(i);
				Map<String,String> m2 = new HashMap<String,String> () ;
				m2.put("KEY", "ZDY_"+m1.get("FIELD_EN").toString()) ;
				m2.put("KEY_F", "ZDY_"+m1.get("FIELD_EN").toString()) ;
				m2.put("VALUE", m1.get("FIELD_ZH").toString()) ;
				extendList.add(m2) ;
			}
			
			
			
			//查询数据库子表是否有数据，有的话读取之前保存的数据，没有的话显示默认值
			isBooleanFk=customModuleService.isBooleanFk_tableDate(tableMap);
			Map<String,Object> opBefore2 = new HashMap<String,Object>() ;
			if(isBooleanFk){//已经保存过信息
				Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
				Iterator<?> it = SUN_TABLE_INFO.keySet().iterator() ;
				while(it.hasNext()){
					String key = it.next() +""; 
				
					opBefore2.put("ZDY_"+key, SUN_TABLE_INFO.get(key)) ;
				}
			}
			opBefore.putAll(opBefore2);
		}
		
		//处理微信端过来的数据
		String TYPE="LP";
		if(param !=null && param.get("TYPE") !=null && !param.get("TYPE").equals("")){
			TYPE=param.get("TYPE").toString();
		}
		//1.修改基础信息
		if(TYPE.equals("NP")){
			/*add by lishuo 12-29-2015
			 * 在修改前更新客户表婚姻关系
			 */
			service.updateClientISMarry(param);
			service.updateClentInfoByNP(param);
		}else{
			/*add by lishuo 12-29-2015
			 * 在修改前更新客户表婚姻关系
			 */
			service.updateClientISMarry(param);
			service.updateClentInfoByLP(param);
		}
		//2.修改自定义数据
		if(TYPE.equals("NP")){
			service.updateClentInfoZDYByNP(param);
		}
		
		Map<String,Object> opBehind2= new HashMap<String,Object>() ;
		//自定义操作后
		if(isBooleanFk){//已经保存过信息
			Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
			Iterator<String> it = SUN_TABLE_INFO.keySet().iterator() ;
			while(it.hasNext()){
				String key = it.next() ; 
				opBehind2.put("ZDY_"+key, SUN_TABLE_INFO.get(key)) ;
			}
		}
			
		Map<String, Object> opBehind = Dao.selectOne(
				"customers.findCustDetailForLog", param);
		opBehind.putAll(opBehind2);
		Map<String, Object> otherParam = new HashMap<String, Object>();
		otherParam.put("OP_REMARK", "客户资料管理->客户基本信息->修改");
		otherParam.put("OP_CLIENT_ID", opBefore.get("CLIENT_ID"));
		String fileName = "fil_cust_client_np.xml";
		if (StringUtils.isNotBlank(opBefore)&&opBefore.containsKey("TYPE")&&!opBefore.get("TYPE").equals("NP")) {
			fileName = "fil_cust_client_lp.xml";
		}

		String statusFlag=(String) SkyEye.getRequest().getSession().getAttribute("statusFlag");
		//草稿不保存修改日志
		if(statusFlag!="0" && statusFlag!=null && statusFlag!=""){
			LogUtil.insertLogForUpdate(fileName, opBefore, opBehind, otherParam,extendList,"");
		}
		boolean flag=false;
		if(num>0){
			flag=true;
		}
		context.put("param", param);
		return new ReplyAjax(flag);
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toUpdateGtczrCustInfoNew() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		
		//项目的客户处理
		CustomersService service = new CustomersService();
		int num=service.updateProjectGtczrCust(param);
		boolean flag=false;
		if(num>0){
			flag=true;
		}
		context.put("param", param);
		return new ReplyAjax(flag);
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toUpdateDbrCustInfoNew() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		
		//项目的客户处理
		CustomersService service = new CustomersService();
		int num=service.toUpdateDbrCustInfoNew(param);
		boolean flag=false;
		if(num>0){
			flag=true;
		}
		context.put("param", param);
		return new ReplyAjax(flag);
	}

	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "修改,"客户基本信息","保存" })
	public Reply toUpdateCustInfo() {
		//修改信息后点击保存执行以下操作
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		String projectId=(String) param.get("PROJECT_ID");
		String clientId=(String) param.get("CLIENT_ID");
		logger.info("projectId...................."+projectId);
		context.put("param", param);
		CustomersService service = new CustomersService();
		
		RZBService rzbService=new RZBService();
		
		JSONArray jsonArray = JSONArray.fromObject(param.get("param"));
		boolean flag = false;
		String msg = null;
		CustomModuleService customModuleService = new CustomModuleService() ;
		for (Object object : jsonArray) {
			Map<String, Object> m = (Map<String, Object>) object;
			m.put("CLIENT_ID", param.get("CLIENT_ID"));
			m.put("USERCODE", param.get("USERCODE"));
			m.put("USERNAME", param.get("USERNAME"));
			m.put("ISZC_TYPE",  param.get("ISZC_TYPE"));

			//String CUST_ID = service.getProCode(m) == null ? "" : service
			//		.getProCode(m).toString();
			//m.put("CUST_ID", CUST_ID);

			String CUST_ID = "";
			if(m.get("PROVINCE") !=null && !m.get("PROVINCE").equals("")){
				CUST_ID=service.getProCode(m).toString();
			}
			m.put("CUST_ID", CUST_ID);
			
			//查询操作前数据
			Map<String, Object> opBefore = Dao.selectOne(
					"customers.findCustDetailForLog", m);
			
			//start 自定义表
			
			List<Map<String,String>> extendList = new ArrayList<Map<String,String>>() ;
			Map infoMap=new HashMap();//页面参数
			//param需要三个参数（1，TYPE包含UPDATE，VIEW  2，ID 主表ID  3，MODULENAME 模块名称）
		
			String createTableName = "客户自然人表" ;
			if(StringUtils.isNotBlank(opBefore)&&opBefore.containsKey("TYPE")&&opBefore.get("TYPE").equals("LP")){
				createTableName = "客户法人表" ;
			}
			param.put("CREATE_TABLE_NAME", createTableName) ;
			
			boolean isBooleanFk = false ;
			//根据模块名称查询主表子表表名信息
			Map tableMap=customModuleService.queryTableByCreateTableName(param);
			if(tableMap!=null&& tableMap.size()>0){
				
				//根据提供的字段查询出主表关联字段
				tableMap.put("PARENT_MODULE_ID", param.get("CLIENT_ID"));
				Map mapParent=customModuleService.getParentTableField(tableMap);
				tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD"));
				
				
				//查询页面显示字段
				Map configMap=new HashMap();
				configMap.put("ID", tableMap.get("ID"));
				List info=customModuleService.queryDynamic_Field(configMap);
				
				for(int i=0;i<info.size();i++){
					Map<String,Object> m1 = (Map<String,Object>) info.get(i);
					Map<String,String> m2 = new HashMap<String,String> () ;
					m2.put("KEY", "ZDY_"+m1.get("FIELD_EN").toString()) ;
					m2.put("KEY_F", "ZDY_"+m1.get("FIELD_EN").toString()) ;
					m2.put("VALUE", m1.get("FIELD_ZH").toString()) ;
					extendList.add(m2) ;
				}
				
				
				//查询数据库子表是否有数据，有的话读取之前保存的数据，没有的话显示默认值
				isBooleanFk=customModuleService.isBooleanFk_tableDate(tableMap);
				Map<String,Object> opBefore2 = new HashMap<String,Object>() ;
				if(isBooleanFk){//已经保存过信息
					Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
					Iterator<?> it = SUN_TABLE_INFO.keySet().iterator() ;
					while(it.hasNext()){
						String key = it.next() +""; 
					
						opBefore2.put("ZDY_"+key, SUN_TABLE_INFO.get(key)) ;
					}
				}
				opBefore.putAll(opBefore2);
			}
			
			

			// if (m.get("TYPE").toString().equals("NP")) {
			// }else{
			// //法人
			// int i=service.checkBankZC(m.get("CLIENT_ID"));
			// if(i==0){
			// throw new ActionException("请添加注册银行信息");
			// }
			// }
			
			String PAGE_TYPE="";
			if(param.get("PAGE_TYPE") !=null && !param.get("PAGE_TYPE").equals("")){
				PAGE_TYPE=param.get("PAGE_TYPE")+"";
			}
			
			if(PAGE_TYPE.equals("CZR") || (PAGE_TYPE.equals("DBR") && m.get("TYPE").toString().equals("LP"))){
				//start 	 保存自定义表
				Map<String,Object> ZDY_INFO = JSONObject.fromObject(m.get("ZDY_INFO")) ;
				ZDY_INFO.put("SUN_FIELD_VALUE_PAGE", param.get("CLIENT_ID")) ;
				customModuleService.submitModuleInfo(ZDY_INFO) ;
				//end   保存自定义表
			}
				
			if(PAGE_TYPE.equals("CZR")){	
				//start 保存紧急联系人
				
				// 页面取得联系人信息列表
				List<Map<String,Object>> EMERGENCY_INFO = JSONArray.fromObject(m.get("EMERGENCY_INFO")) ;
				
				// 承租人ID
				String CLIENT_ID = param.get("CLIENT_ID") == null? "" :param.get("CLIENT_ID").toString();
				// 取得承租人对应联系人列表
				List<Map<String, Object>> oldList = service.getEmergencyInfoByClientId(CLIENT_ID);
				
				/**		2016-02-01	修改	lizhiheng	START	**/
				
				// 判断页面取得联系人列表和数据库中的联系人列表
				if(oldList != null && oldList.size() > 0) {
//					(EMERGENCY_INFO != null && EMERGENCY_INFO.size() > 0)
					
					// 循环数据库中已有的联系人
					for(Map<String,Object> oldMap: oldList) {
						// 联系人ID
						String EMERGENCY_ID_OLD = oldMap.get("ID") == null ?"" : oldMap.get("ID").toString();
						// 姓名
						String EMERGENCY_NAME_OLD = oldMap.get("EMERGENCY_NAME") == null ?"" : oldMap.get("EMERGENCY_NAME").toString();
						// 与申请人关系
						String EMERGENCY_RELATIONSHIP_OLD = oldMap.get("EMERGENCY_RELATIONSHIP") == null ?"" : oldMap.get("EMERGENCY_RELATIONSHIP").toString();
						// 联系人手机号
						String EMERGENCY_PHONE_OLD = oldMap.get("EMERGENCY_PHONE") == null ?"" : oldMap.get("EMERGENCY_PHONE").toString();
						
						int pageIndex = 0;
						int pageLength = EMERGENCY_INFO.size();
						// 循环页面取得联系人列表
						for(Map<String,Object> pageMap: EMERGENCY_INFO) {
							
							// 联系人ID
							String EMERGENCY_ID = pageMap.get("EMERGENCY_ID") == null ?"" : pageMap.get("EMERGENCY_ID").toString();
							// 如果联系人ID为空，则直接添加
							if(StringUtils.isBlank(EMERGENCY_ID)) {
								pageMap.put("CLIENT_ID", CLIENT_ID) ;
								pageMap.put("ID", EMERGENCY_ID);
								
								service.doAddEmergencyInfo(pageMap) ;
//								// 添加完成移除列表数据
//								EMERGENCY_INFO.remove(pageMap); 
								pageMap.put("EMERGENCY_ID", "OK");
								// 继续循环
								continue;
							} 
							else if("OK".equals(EMERGENCY_ID)) {
								
								pageIndex++;
								// 继续循环
								continue;
							}
							// 联系人ID不为空，判断更新或删除
							else {
								// 联系人姓名
								String EMERGENCY_NAME = pageMap.get("EMERGENCY_NAME") == null ?"" : pageMap.get("EMERGENCY_NAME").toString();
								// 与申请人关系
								String EMERGENCY_RELATIONSHIP = pageMap.get("EMERGENCY_RELATIONSHIP") == null ?"" : pageMap.get("EMERGENCY_RELATIONSHIP").toString();
								// 联系人手机号
								String EMERGENCY_PHONE = pageMap.get("EMERGENCY_PHONE") == null ?"" : pageMap.get("EMERGENCY_PHONE").toString();
								
								// 如果ID一致，
								if(EMERGENCY_ID.equals(EMERGENCY_ID_OLD)) {
									// 判断姓名、与申请人关系、手机号是否一致
									if(EMERGENCY_NAME.equals(EMERGENCY_NAME_OLD) || 
											EMERGENCY_RELATIONSHIP.equals(EMERGENCY_RELATIONSHIP_OLD) || 
											EMERGENCY_PHONE.equals(EMERGENCY_PHONE_OLD)) {
										// 承租人ID
										pageMap.put("CLIENT_ID", CLIENT_ID) ;
										// 联系人ID
										pageMap.put("ID", EMERGENCY_ID);
										
										service.doUpdateEmergencyInfo(pageMap) ;
										// 继续循环
										continue;
									}
									// 
									else {
										
										// 先删除，再添加
										pageMap.put("CLIENT_ID", CLIENT_ID) ;
										pageMap.put("ID", EMERGENCY_ID);
										
										service.doDelEmergencyInfo(pageMap) ;
										service.doAddEmergencyInfo(pageMap) ;
										// 继续循环
										continue;
									} // END
								} 
								else {
									
									pageIndex++;
									// 继续循环
									continue;
								}
						
							} // ELSE	END
						} // 循环页面取得联系人列表 	END
						
						// 删除页面上移除的联系人
						if(pageIndex != 0 && pageIndex >= pageLength) {
							service.doDelEmergencyInfo(oldMap) ;
						}
						
					} // 循环数据库中已有的联系人		END
				} else {
					
					// 循环页面取得联系人列表
					for(Map<String,Object> pageMap: EMERGENCY_INFO) {
						
						// 联系人ID
						String EMERGENCY_ID = pageMap.get("EMERGENCY_ID") == null ?"" : pageMap.get("EMERGENCY_ID").toString();
						// 如果联系人ID为空，则直接添加
						if(StringUtils.isBlank(EMERGENCY_ID)) {
							pageMap.put("CLIENT_ID", CLIENT_ID) ;
							pageMap.put("ID", EMERGENCY_ID);
							
							service.doAddEmergencyInfo(pageMap) ;
//							// 添加完成移除列表数据
//							EMERGENCY_INFO.remove(pageMap); 
							pageMap.put("EMERGENCY_ID", "OK");
							// 继续循环
							continue;
						} 
						else if("OK".equals(EMERGENCY_ID)) {
							
							// 继续循环
							continue;
						}
						// 联系人ID不为空，判断更新或删除
						else {
							continue;
						} // ELSE	END
					} // 循环页面取得联系人列表 	END
				}
				/**		2016-02-01	修改	lizhiheng	END	**/
				
				
//				//先删除该客户下的紧急联系人信息再填写该客户紧急联系人信息
//				service.doDelEmergencyInfoByClientId(param.get("CLIENT_ID").toString()) ;
//				service.doAddEmergencyInfo(EMERGENCY_INFO,param.get("CLIENT_ID").toString()) ;
//				
//				List<Map<String, Object>> newList = service.getEmergencyInfoByClientId(CLIENT_ID);
//				// TODO
//				// 2016-01-22 修改 	联系人更新操作	START	TODO
//				for (Map<String, Object> map : oldList) {
//					for (Map<String, Object> nmap : newList) {
//						
//					Map<String, Object> updateMap = new HashMap<String, Object>();
//					
//					String OLD_ID = map.get("ID") == null ? "" : map.get("ID").toString();
//					String NEW_ID = nmap.get("ID") == null ? "" : nmap.get("ID").toString();
//					
//					if(!StringUtils.isBlank(OLD_ID) && !StringUtils.isBlank(NEW_ID)) {
//						updateMap.put("OLD_ID", OLD_ID);
//						updateMap.put("NEW_ID", NEW_ID);
//						updateMap.put("USER_TYPE", 2);
//						updateMap.put("INVERST_TYPE", 4);
//						
//						String oEMERGENCY_NAME = map.get("EMERGENCY_NAME") == null ? "" :map.get("EMERGENCY_NAME").toString();
//						String nEMERGENCY_NAME = nmap.get("EMERGENCY_NAME") == null ? "" : nmap.get("EMERGENCY_NAME").toString();
//						String oEMERGENCY_RELATIONSHIP = map.get("EMERGENCY_RELATIONSHIP") == null ?"" :map.get("EMERGENCY_RELATIONSHIP").toString();
//						String nEMERGENCY_RELATIONSHIP = nmap.get("EMERGENCY_RELATIONSHIP") == null ? "" :nmap.get("EMERGENCY_RELATIONSHIP").toString();
//						String oEMERGENCY_PHONE = map.get("EMERGENCY_PHONE") == null ?  "" :map.get("EMERGENCY_PHONE").toString();
//						String nEMERGENCY_PHONE = nmap.get("EMERGENCY_PHONE") == null ? "" :nmap.get("EMERGENCY_PHONE").toString();
//						
//						if(!StringUtils.isBlank(oEMERGENCY_NAME) && 
//								!StringUtils.isBlank(oEMERGENCY_RELATIONSHIP) && 
//								!StringUtils.isBlank(oEMERGENCY_PHONE) ) {
//							
//							List<Map<String, Object>> searchList = service.getFIL_PROJECT_TELCALL_INFO(OLD_ID);
//							
//							if(searchList != null && searchList.size() > 0) {
//								
//								if((!StringUtils.isBlank(oEMERGENCY_NAME) && oEMERGENCY_NAME.equals(nEMERGENCY_NAME)) && 
//										(!StringUtils.isBlank(oEMERGENCY_RELATIONSHIP) && oEMERGENCY_RELATIONSHIP.equals(nEMERGENCY_RELATIONSHIP))) {
////									(!StringUtils.isBlank(oEMERGENCY_PHONE) && oEMERGENCY_PHONE.equals(nEMERGENCY_PHONE))
//									service.updateFIL_PROJECT_TELCALL_INFO(updateMap);
//								}
//							}
//						
//						} else {
//							continue;
//							}
//						
//					} else {
//						continue;
//							}
//					}
//				}
				// 2016-01-22 修改 	联系人更新操作	END		TODO
				
				
				
				//end   保存紧急联系人
			}
			
			
			//临时客户
			if("0".equals(m.get("CUST_STATUS"))){
				String ID_CARD_NO=null;
				String ORAGNIZATION_CODE=null;
				String CLIENT_NAME=null;
				if(StringUtils.isNotBlank(m.get("ID_CARD_NO")))
					ID_CARD_NO=m.get("ID_CARD_NO").toString();
				if(StringUtils.isNotBlank(m.get("CUST_NAME")))
					CLIENT_NAME=m.get("CUST_NAME").toString();
				if(StringUtils.isNotBlank(m.get("ORAGNIZATION_CODE")))
					ORAGNIZATION_CODE=m.get("ORAGNIZATION_CODE").toString();
				Map<String,Object> mm=service.queryCheckCustomer(ID_CARD_NO, ORAGNIZATION_CODE, CLIENT_NAME);
				String RST=mm.get("RST")+"";
				if("-2".equals(RST)){
					throw new ActionException("请注意客户信息，该客户身份证号/组织机构代码证在系统中存在，但名称不一致");
				}else if("-1".equals(RST)){
					mm.put("CLIENT_ID", m.get("CLIENT_ID"));
					service.updateProjectClient(mm);
				}else{
					m.put("CUST_STATUS", "1");
				}
			}
			//mod gengchangbao JZZL-241 Start
			String TYPE = m.get("TYPE").toString();
			if (TYPE.equals("NP")) {
				//身份证验证——why——start
				logger.info("CUST_NAME为："+m.get("CUST_NAME").toString()+"ID_CARD_NO为："+m.get("ID_CARD_NO").toString());
				Map<String,Object> mapIsCheckCardNo= rzbService.isCheckCardNo(m.get("ID_CARD_NO").toString(),m.get("CUST_NAME").toString());
				String RS=mapIsCheckCardNo.get("RS").toString();
				//String RS="不一致";
				if("是".equals(RS)){
					m.put("IDCARD_CHECK", RS);
					logger.info("身份证号与姓名已验证过，结果为：验证通过");
				}else if("一致".equals(RS)){
					m.put("IDCARD_CHECK", "是");
					logger.info("身份证号与姓名一致，验证通过");
				}else if("不一致".equals(RS)){
					m.put("IDCARD_CHECK", "否");
					flag=false;
					msg="身份证号与姓名不一致，验证不通过";
					//throw new ActionException("身份证号与姓名不一致，验证不通过");
					return new ReplyAjax(flag, msg).addOp(new OpLog("客户资料管理", "修改信息", param
							.get("USERID").toString()));
				}
				logger.info(m);
				//身份证验证——why——end
			}
			//mod gengchangbao JZZL-241 End
			
			int i = service.doUpdateCust(m);

			// 记录客户历史情况
			String CUST_BASEINFO_ID = Dao.getSequence("SEQ_FIL_CUST_BASE_INFO");
			m.put("CUST_BASEINFO_ID", CUST_BASEINFO_ID);
			// 保存该客户第一手历史资料
			service.doAddCustBaseINfo(m);

			flag = true;

			msg = "保存成功!";
			if (i > 0) {
				flag = true;
				if (m.get("TYPE").toString().equals("NP")) {
					if (m.get("IS_MARRY") != null
							&& ("1Marriage"
									.equals(m.get("IS_MARRY").toString())
									|| "4Marriage".equals(m.get("IS_MARRY")
											.toString())
									|| "4".equals(m.get("IS_MARRY").toString()) || "1"
										.equals(m.get("IS_MARRY").toString()))) {
						Map<String, Object> spoust = (Map<String, Object>) service
								.getSpoust(m);// 判断配偶是否存在
						if (spoust == null) {// 如果配偶不存在，则添加
							service.doAddCustSpoust(m);
						} else {// 如果存在， 则修改
							service.doUpdateSpoust(m);
						}
					}
					// 删除并插入兴趣爱好和个性
					service.delANNEX(m);
					service.insertANNEX(m, param.get("CLIENT_ID").toString());
				}
				msg = "修改成功!";
			} else {
				msg = "修改失败!";
				flag = false;
			}
			
			Map<String,Object> opBehind2= new HashMap<String,Object>() ;
			//自定义操作后
			if(isBooleanFk){//已经保存过信息
				Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
				Iterator<String> it = SUN_TABLE_INFO.keySet().iterator() ;
				while(it.hasNext()){
					String key = it.next() ; 
					opBehind2.put("ZDY_"+key, SUN_TABLE_INFO.get(key)) ;
				}
			}
				
			Map<String, Object> opBehind = Dao.selectOne(
					"customers.findCustDetailForLog", m);
			opBehind.putAll(opBehind2);
			Map<String, Object> otherParam = new HashMap<String, Object>();
			otherParam.put("OP_REMARK", "客户资料管理->客户基本信息->修改");
			otherParam.put("OP_CLIENT_ID", opBefore.get("CLIENT_ID"));
			String fileName = "fil_cust_client_np.xml";
			if (StringUtils.isNotBlank(opBefore)&&opBefore.containsKey("TYPE")&&!opBefore.get("TYPE").equals("NP")) {
				fileName = "fil_cust_client_lp.xml";
			}
			String statusFlag=(String) SkyEye.getRequest().getSession().getAttribute("statusFlag");
			if(statusFlag!="0" && statusFlag!=null && statusFlag!=""){
				LogUtil.insertLogForUpdate(fileName, opBefore, opBehind, otherParam,extendList,projectId);
			}
			//更新担保人备注说明
			Map<String,Object> guarMap = service.getGuarantee(m);
			if(guarMap!=null && !guarMap.isEmpty()){
				service.updateGuarantee(m);
			}
		}
		// add gengchangbao 2016年7月5日17:43:02 JZZL245 Start
		//service.checkForUpdbankId(m);
		// add gengchangbao 2016年7月5日17:43:02 JZZL245 End
		return new ReplyAjax(flag, msg).addOp(new OpLog("客户资料管理", "修改信息", param
				.get("USERID").toString()));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "删除" })
	public Reply doDetCustInfo() {
		Map<String, Object> map = this.getPageParameter();
		CustomersService service = new CustomersService();
		Map<String, Object> opBefore = Dao.selectOne(
				"customers.findCustDetailForLog", m);

		int i = service.delCustInfo(map);
		Map<String, Object> opBehind = Dao.selectOne(
				"customers.findCustDetailForLog", m);
		Map<String, Object> otherParam = new HashMap<String, Object>();
		otherParam.put("OP_REMARK", "客户资料管理->删除");
		otherParam.put("OP_CLIENT_ID", opBefore.get("CLIENT_ID"));
		String fileName = "fil_cust_client_np.xml";
		if (!opBefore.get("TYPE").equals("NP")) {
			fileName = "fil_cust_client_lp.xml";
		}
		
		List<Map<String,String>> extendList = new ArrayList<Map<String,String>>() ;
		//start 自定义
		CustomModuleService customModuleService = new CustomModuleService() ;
		Map<String,Object> infoMap = new HashMap<String,Object>() ;
		String createTableName = "客户自然人表" ;
		if(map.get("TYPE").equals("LP")){
			createTableName = "客户法人表" ;
		}
		map.put("CREATE_TABLE_NAME", createTableName) ;
		boolean isBooleanFk = false ;
		//根据模块名称查询主表子表表名信息
		Map tableMap=customModuleService.queryTableByCreateTableName(map);
		if(tableMap!=null&&tableMap.size()>0){
			//根据提供的字段查询出主表关联字段
			tableMap.put("PARENT_MODULE_ID", opBefore.get("CLIENT_ID"));
			Map mapParent=customModuleService.getParentTableField(tableMap);
			if(mapParent!=null && mapParent.size()>0){
				tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD")) ;
				//查询页面显示字段
				Map configMap=new HashMap();
				configMap.put("ID", tableMap.get("ID"));
				List info=customModuleService.queryDynamic_Field(configMap);
				
				for(int j=0;j<info.size();j++){
					Map<String,Object> m1 = (Map<String,Object>) info.get(j) ;
					Map<String,String> m2 = new HashMap<String,String> () ;
					m2.put("KEY", "ZDY_"+m1.get("FIELD_EN").toString()) ;
					m2.put("KEY_F", "ZDY_"+m1.get("FIELD_EN").toString()) ;
					m2.put("VALUE", m1.get("FIELD_ZH").toString()) ;
					extendList.add(m2) ;
				}
				//查询数据库子表是否有数据，有的话读取之前保存的数据，没有的话显示默认值
				isBooleanFk=customModuleService.isBooleanFk_tableDate(tableMap);
				Map<String,Object> opBefore2 = new HashMap<String,Object>() ;
				if(isBooleanFk){//已经保存过信息
					Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
					Iterator<String> it = SUN_TABLE_INFO.keySet().iterator() ;
					while(it.hasNext()){
						String key = it.next() ; 
						opBefore2.put("ZDY_"+key, SUN_TABLE_INFO.get(key)) ;
					}
				}
				opBefore.putAll(opBefore2);
		}
		
			
			//保存页面参数（1，子表表名，2，子表关联主表字段名，3，子表关联主表字段值）
			infoMap.put("SUN_TABLE_KEY_PAGE", tableMap.get("CREATE_TABLE"));//1，子表表名
			infoMap.put("SUN_FIELD_KEY_PAGE", tableMap.get("CREATE_F_KEY"));//2，子表关联主表字段名
			infoMap.put("SUN_FIELD_VALUE_PAGE",map.get("CLIENT_ID"));//3，子表关联主表字段值	
			customModuleService.delZdyInfo(infoMap) ;
			
			//end  自定义
			
			//操作后
			Map<String,Object> opBehind2 = new HashMap<String,Object>() ;
			if(isBooleanFk){//已经保存过信息
				Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
				Iterator<String> it = SUN_TABLE_INFO.keySet().iterator() ;
				while(it.hasNext()){
					String key = it.next() ; 
					opBehind2.put("ZDY_"+key, SUN_TABLE_INFO.get(key)) ;
				}
			}
			opBehind2.putAll(opBehind2);
		
		}
		

		LogUtil.insertLogForDelete(fileName, opBefore, opBehind, otherParam,extendList);

		boolean flag = false;
		String msg = "";
		if (i > 0) {
			flag = true;
			msg = "客户删除成功";
		} else {
			flag = false;
			msg = "客户删除失败";
		}
		return new ReplyAjax(flag, msg)
				.addOp(new OpLog("客户资料管理", "删除客户资料", msg));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理", "删除" })
	public Reply checedCustToPro() {
		Map<String, Object> map = this.getPageParameter();
		CustomersService service = new CustomersService();
		int i = service.checedCustToPro(map);
		boolean flag = false;
		String msg = "";
		if (i > 0) {
			flag = true;
			msg = "该客户也有项目，不可删除";
		} else {
			flag = false;
			msg = "";
		}
		return new ReplyAjax(flag, msg);
	}

	/************************************************************** 客户子女及社会关系管理 ************************************************************************/

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理","查看/修改详细信息", "客户关系明细" })
	public Reply toViewRelation() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		CustomersService service = new CustomersService();
		context.put("param", param);
		context.put("type", service.findCustDataType());// 客户关联信息
		if (param.get("flag").equals("view")) {
			if (param.get("tab") != null) {
				if ("child".equals(param.get("tab").toString())) {// 子女信息
					return new ReplyHtml(VM.html(pagePath
							+ "child/toViewChildren.vm", context));
				} else if ("solialRe".equals(param.get("tab").toString())) {// 社会关系
					return new ReplyHtml(VM.html(pagePath
							+ "socialRelations/toViewSocialRelations.vm",
							context));
				}
			}
		} else if (param.get("flag").equals("update")) {
			if (param.get("tab") != null) {
				if ("child".equals(param.get("tab").toString())) {// 子女信息
					return new ReplyHtml(VM.html(pagePath
							+ "child/toUpdateChildren.vm", context));
				} else if ("solialRe".equals(param.get("tab").toString())) {// 社会关系
					context.put("relationToCust", service.relationToCust());
					return new ReplyHtml(VM.html(pagePath
							+ "socialRelations/toUpdateSocialRelations.vm",
							context));
				}
			}
		}
		return null;
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","客户关系明细" })
	public Reply toViewRelationDetail() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		return new ReplyAjaxPage(service.getCustLink(param));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","客户关联关系-添加" })
	public Reply doInsertLinl() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		boolean flag = false;
		int i = service.doInsertLink(param);
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("客户资料管理", "添加客户关系",
				param.get("USERID").toString()));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","客户关联关系-修改" })
	public Reply getCustRelation() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		Map<String, Object> custR = new HashMap<String, Object>();
		custR.put("realtion", service.getCustRelation(param));
		return new ReplyJson(JSONObject.fromObject(custR));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","客户关联关系-修改" })
	public Reply doCustRelation() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		boolean flag = false;
		String msg = "";
		try {
			int j = service.doUpdateCustInfo(param);
			if (j > 0) {
				flag = true;
				msg = "客户关联关系修改成功！！";
			} else {
				flag = false;
				msg = "客户关联关系修改失败！！";
			}
		} catch (Exception ex) {
			flag = false;
			msg = "客户关联关系修改失败！！";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("客户资料管理", "修改客户关联关系",
				param.get("USERID").toString()));
	}

	/**
	 * doDelRelation
	 * 
	 * @date 2013-9-4 下午05:54:52
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","客户关联关系-删除" })
	public Reply doDelRelation() {
		Map<String, Object> map = this.getPageParameter();
		CustomersService service = new CustomersService();
		int i = service.delCustRelation(map);
		boolean flag = false;
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("客户资料管理", "删除子女资料",
				map.get("USERID").toString()));
	}

	/**************************************************** 客户开户行 ***********************************************************************************/

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","开户行页面" })
	public Reply toViewLegalBank1() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = this.getPageParameter();
		if(param !=null && param.get("PROJECT_ID")!=null && !param.get("PROJECT_ID").equals("")){
			String PROJECT_ID=param.get("PROJECT_ID")+"";
			//查询该项目所挂的BANK_ID
			Map BPMap=Dao.selectOne("project.queryBankByProjectId",PROJECT_ID);
			context.put("BPMap", BPMap);
		}
		
		context.put("param", param);
		
		String CLIENT_ID="";
		if(param !=null && param.get("CLIENT_ID") !=null && !param.get("CLIENT_ID").equals("")){
			CLIENT_ID=param.get("CLIENT_ID")+"";
			Map custMap=Dao.selectOne("customers.queryCustNameById", CLIENT_ID);
			context.put("CUSTMAP", custMap);
		}
		
//		context.put("account_type",new DataDictionaryMemcached().get("开户行账号类型"));
		context.put("account_type",new projectService().queryAccountTypeCust(param));
		
		context.put("head_office", new DataDictionaryMemcached().get("开户行所属总行"));
		AreaService area = new AreaService();
		context.put("getProvince", area.getAllProvince());
		if (param.get("tab").equals("view")) {
			return new ReplyHtml(VM.html(pagePath + "bank/toViewCustBank.vm",
					context));
		} else {
			return new ReplyHtml(VM.html(
					pagePath + "bank/toUpdateLegalBank.vm", context));
		}
	}
	
	//紧急联系人
	@aDev(code = "170012", email = "wuyf@pqsoft.cn", name = "武燕飞")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","开户行页面" })
	public Reply toShowEmergencyContact() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		if (param.get("tab").equals("view")) {
			return new ReplyHtml(VM.html(pagePath + "EmergencyContact/toViewEmergencyContact.vm",
					context));
		} else {
			return new ReplyHtml(VM.html(
					pagePath + "EmergencyContact/toUpdateEmergencyContact.vm", context));
		}
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","开户行页面" })
	public Reply toViewLegalBank() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		return new ReplyAjaxPage(service.getBankDetail(param));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","开户行-添加" })
	public Reply doAddBank() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		param.put("HEAD_OFFICE_NAME", Dao.selectOne("customers.selCity",param.get("HEAD_PROVINCE").toString()));
		param.put("HEAD_CITY_NAME", Dao.selectOne("customers.selCity",param.get("HEAD_CITY").toString()));
		boolean flag = false;
		int j = service.doAddOpenBank(param);
		if (j > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("客户资料管理", "添加开户行信息",
				param.get("USERID").toString()));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","开户行-修改" })
	public Reply findBankDetailByid() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		Map<String, Object> bank = new HashMap<String, Object>();
		bank.put("bank", service.findBankDetailByid(param));
		return new ReplyJson(JSONObject.fromObject(bank));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","开户行-修改" })
	public Reply doUpdageLeagBank() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		boolean flag = false;
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CO_ID", param.get("CO_ID"));
		map.put("PROJECT_ID", param.get("PROJECT_ID"));//可为null，客户修改时候就为null
		param.put("HEAD_OFFICE_NAME", Dao.selectOne("customers.selCity",param.get("HEAD_PROVINCE").toString()));
		param.put("HEAD_CITY_NAME", Dao.selectOne("customers.selCity",param.get("HEAD_CITY").toString()));
		/* 2016年6月27日 12:49:38 吴国伟去掉限制
		 * int count = new projectService().isBankDelCheck(map);
		if (count > 0) {
			throw new ActionException("该账户信息已被项目使用，不能修改！");
		}*/
		try {
			int j = service.doUpdateBank(param);
			if (j > 0) {
				flag = true;
				msg = "客户开户银行修改成功！！";
			} else {
				flag = false;
				msg = "客户开户银行修改失败！！";
			}
		} catch (Exception ex) {
			flag = false;
			msg = "客户开户银行修改失败！！";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("客户资料管理", "修改开户行信息",
				param.get("USERID").toString()));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理", "查看/修改详细信息","开户行-删除" })
	public Reply delOpenBank() {
		Map<String, Object> map = _getParameters();
		int count = new projectService().isBankDelCheckNew(map);
		
		String msg = "";
		boolean flag = false;
		if (count > 0) {
			flag = false;
			msg = "该账户信息已被项目使用，不能删除！";
		}
		else{
			CustomersService service = new CustomersService();
			int i = service.delOpenBank(map);
			
			if (i > 0) {
				flag = true;
				msg = "客户开户银行删除成功！！";
			} else {
				flag = false;
				msg = "客户开户银行删除失败！！";
			}
		}
		map.put("USERID", Security.getUser().getId());
		return new ReplyAjax(flag, msg).addOp(new OpLog("客户资料管理", "删除开户行", map
				.get("USERID").toString()));
	}
	
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply checkBank(){
		Map<String,Object> map = _getParameters();
		CustomersService service = new CustomersService();
		boolean flag = true;
		int i = service.findBank(map);
		if(i>0){
			flag = false;
		}
		return new ReplyAjax(flag,null);
	}

	/**************************************************** 客户族谱 ***********************************************************************************/
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理","查看", "客户族谱"})
	public Reply getFamilyTree() {
		Map<String, Object> m = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		CustomersService service = new CustomersService();
		// 客户名称
		context.put("getClient", service.getClient(m));
		// 法人
		if ("LP".equals(m.get("TYPE"))) {
			// 企业关联-内部
			m.put("LINKTYPE", "2");
			context.put("getCompanyRelevance", service.getLinkMan(m));
			// 企业团队-内部
			context.put("getCompanyTeam", service.getCompanyTeam(m));
			// 担保人--内部
			// context.put("getGuarantor", service.getGuarantor(m));
			// 企业关联-外部
			context.put("getLinkToOut", service.getLinkToOut(m));
			// 担保人-外部
			// context.put("getGuarantor1", service.getGuarantor1(m));
			return new ReplyHtml(VM.html(pagePath
					+ "Family/getFamilyTreeCorp.vm", context));
		} else {
			// 获取配偶
			@SuppressWarnings("unused")
			Map<String, Object> map = (Map<String, Object>) service
					.getSpoust(m);
			context.put("getSpoust", service.getSpoust(m));
			// 子女
			m.put("LINKTYPE", "0");
			context.put("getChildrenList", service.getLinkMan(m));
			// 社会关系-内部
			m.put("LINKTYPE", "1");
			context.put("getSocialRelations", service.getLinkMan(m));
			// 担保人-内部
			// context.put("getGuarantor", service.getGuarantor(m));

			// 担保人-外部
			// context.put("getGuarantor1", service.getGuarantor1(m));
			// 社会关系-外部
			m.put("LINKTYPE", "1");
			context.put("getSocialRelations1", service.getLinkToOut(m));
			return new ReplyHtml(VM.html(pagePath
					+ "Family/getFamilyTreeNature.vm", context));
		}
	}

	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name = { "客户管理", "客户资料管理","修改", "客户族谱"})
	public Reply getFamilyTreeUpdate() {
		Map<String, Object> m = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		CustomersService service = new CustomersService();
		// 客户名称
		context.put("getClient", service.getClient(m));
		// 法人
		if ("LP".equals(m.get("TYPE"))) {
			// 企业关联-内部
			m.put("LINKTYPE", "2");
			context.put("getCompanyRelevance", service.getLinkMan(m));
			// 企业团队-内部
			context.put("getCompanyTeam", service.getCompanyTeam(m));
			// 担保人--内部
			// context.put("getGuarantor", service.getGuarantor(m));
			// 企业关联-外部
			context.put("getLinkToOut", service.getLinkToOut(m));
			// 担保人-外部
			// context.put("getGuarantor1", service.getGuarantor1(m));
			return new ReplyHtml(VM.html(pagePath
					+ "Family/getFamilyTreeCorp.vm", context));
		} else {
			// 获取配偶
			@SuppressWarnings("unused")
			Map<String, Object> map = (Map<String, Object>) service
					.getSpoust(m);
			context.put("getSpoust", service.getSpoust(m));
			// 子女
			m.put("LINKTYPE", "0");
			context.put("getChildrenList", service.getLinkMan(m));
			// 社会关系-内部
			m.put("LINKTYPE", "1");
			context.put("getSocialRelations", service.getLinkMan(m));
			// 担保人-内部
			// context.put("getGuarantor", service.getGuarantor(m));

			// 担保人-外部
			// context.put("getGuarantor1", service.getGuarantor1(m));
			// 社会关系-外部
			m.put("LINKTYPE", "1");
			context.put("getSocialRelations1", service.getLinkToOut(m));
			return new ReplyHtml(VM.html(pagePath
					+ "Family/getFamilyTreeNature.vm", context));
		}
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "业务管理", "客户管理", "查看", "打分" })
	public Reply toGradeScoreMgView() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		CustomersService service = new CustomersService();
		param.putAll((Map<String, Object>) service.findCustDetail(param));
		param.put("MAIN_TYPE", "1");// 主体类型 承租人
		context.put("list", service.getGradeScoreData(param));
		context.put("param", param);
		System.out.println(param);
		return new ReplyHtml(VM.html(pagePath + "score/toGradeScoreMgView.vm",
				context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "业务管理", "客户管理", "修改", "打分" })
	public Reply toGradeScoreMg() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		CustomersService service = new CustomersService();
		param.putAll((Map<String, Object>) service.findCustDetail(param));
		param.put("MAIN_TYPE", "1");// 主体类型 承租人
		context.put("list", service.getGradeScoreData(param));
		context.put("param", param);
		System.out.println(param);
		return new ReplyHtml(VM.html(pagePath + "score/toGradeScoreMg.vm",
				context));
	}

	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	@aPermission(name = { "业务管理", "客户管理"})
	public Reply doGradeScore() {
		Map<String, Object> m = _getParameters();
		SecuEvaluateService service = new SecuEvaluateService();
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = service.querySecuEvalInfo(m);
		// 打分模板ID
		m.put("id", map.get("ID"));
		// 对应打分模板明细项
		List<Map> xx = (List<Map>) service.queryTreeByid(m);
		context.put("pqsoft", xx);
		// 对应打分模板
		Map<String, Object> xx1 = service.querySelfByid(m);
		context.put("self", xx1);

		List<Map<String, Object>> normList = service.queryMouldNorm(m);
		NormScoreService normService = new NormScoreService(m);

		// 循环计算定量打分项分值
		for (Map<String, Object> normMap : normList) {
			System.out.println("normMap------" + normMap);
			normMap.put("SCORE", normService.getNormScore(normMap));
		}
		CustomersService service1 = new CustomersService();
		Map<String,Object> score=service1.getGradeScore(m);
		String DETAILED[]=score.get("DETAILED").toString().split(",");
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> detailed1;
		for(int i=0;i<DETAILED.length;i++){
			detailed1=new HashMap<String,Object>();
			detailed1.put("ID", DETAILED[i]);
			list.add(detailed1);
		}
		context.put("list", list);
		System.out.println(list+"==================");
		context.put("norm", normList);
		context.put("prarm", m);
		context.put("score", score);
		return new ReplyHtml(VM.html(pagePath + "score/doSelScore.vm", context));
	}
	/**************************************************** 客户资料管理-公用方法 ***********************************************************************************/
	//区域   通过父节点查询下面的子节点
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.ALL)
	public Reply getChildArea() {// 修改， 添加公用的方法用来查找市的方法
		Map<String, Object> map = _getParameters();
		CustomersService service = new CustomersService();
		AreaService areaS=new AreaService() ;
		
		List<Object> city = areaS
				.getSubset(map);
		return new ReplyJson(JSONArray.fromObject(city));
	}

	//区域   通过父节点查询下面的子节点
		@SuppressWarnings("unchecked")
		@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
		@aAuth(type = aAuthType.ALL)
		public Reply getChildAreaById() {// 修改， 添加公用的方法用来查找市的方法
			Map<String, Object> map = _getParameters();
			CustomersService service = new CustomersService();
			AreaService areaS=new AreaService() ;
			List<Object> l = service.getChildAreaById(map.get("ID").toString()) ;
			
			return new ReplyJson(JSONArray.fromObject(l));
		}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "业务管理", "客户管理", "等级评定" })
	@aAuth(type = aAuthType.USER)
	public Reply getCustLeverAll() {
		Map<String, Object> map = _getParameters();
		CustomersService service = new CustomersService();
		service.getCustLeverAll();
		return this.findCustomersPage();
	}

//	@aPermission(name = { "客户管理", "客户核心资料维护", "列表" })
//	@aDev(code = "170051", email = "qijl@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply queryCustBaseInfoMg() {
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> param = _getParameters();
//
//		context.put("param", param);
//		return new ReplyHtml(VM.html(pagePath
//				+ "custBaseInfo/queryCustBaseInfoMg.vm", context));
//	}

//	@aPermission(name = { "客户管理", "客户核心资料维护", "列表" })
//	@aDev(code = "170051", email = "qijl@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply lpCustBaseInfoMg() {
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> param = _getParameters();
//		context.put("param", param);
//
//		context.put("CUST_FLAG_LIST", new DataDictionaryService()
//				.queryDataDictionaryByScheme("客户信息维护来源"));
//		return new ReplyHtml(VM.html(pagePath
//				+ "custBaseInfo/lpCustBaseInfoMg.vm", context));
//	}

	@aDev(code = "170051", email = "qijl@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply lpCustBaseInfoData() {
		Map<String, Object> param = _getParameters();
		CustomersService service = new CustomersService();
		return new ReplyAjaxPage(service.lpCustBaseInfoData(param));
	}

//	@aPermission(name = { "客户管理", "客户核心资料维护", "列表" })
//	@aDev(code = "170051", email = "qijl@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply npCustBaseInfoMg() {
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> param = _getParameters();
//		context.put("param", param);
//		context.put("CUST_FLAG_LIST", new DataDictionaryService()
//				.queryDataDictionaryByScheme("客户信息维护来源"));
//		return new ReplyHtml(VM.html(pagePath
//				+ "custBaseInfo/npCustBaseInfoMg.vm", context));
//	}

	@aDev(code = "170051", email = "qijl@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply npCustBaseInfoData() {
		Map<String, Object> param = _getParameters();
		CustomersService service = new CustomersService();
		return new ReplyAjaxPage(service.npCustBaseInfoData(param));
	}

//	@aDev(code = "170051", email = "qijl@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	@aPermission(name = { "客户管理", "客户核心资料维护", "列表" })
//	public Reply findCustomersBaseInfo() {
//		this.getPageParameter();
//		VelocityContext context = new VelocityContext();
//		context.put("cust_type",
//				(List) new DataDictionaryMemcached().get("客户类型"));
//		context.put("param", m);
//		return new ReplyHtml(VM.html(pagePath
//				+ "custBaseInfo/CustomersBaseInfo.vm", context));
//	}

	@aDev(code = "170051", email = "qijl@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findCustomersDataBaseInfo() {
		Map<String, Object> param = _getParameters();
		CustomersService service = new CustomersService();
		return new ReplyAjaxPage(service.findCustomersBaseInfo(param));
	}

//	@aAuth(type = aAuth.aAuthType.USER)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aPermission(name = { "客户管理", "客户核心资料维护", "列表明细" })
//	public Reply getCustBaseInfoMX() {
//		Map<String, Object> param = _getParameters();
//		
//		CustomersService service = new CustomersService();
//		return new ReplyAjax(service.getCustBaseInfoMX(param));
//	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "客户管理", "操作记录" })
	public Reply czjlPage() {
		Map<String, Object> param = _getParameters();
		System.out.println("param......"+param);
		CustomersService service = new CustomersService();
		Map<String,Object> cust =(Map<String, Object>) service
				.findCustDetail(param);
		 
		cust.put("OP_SHOW","创建了该客户信息！") ;
		List<Map<String, Object>> l = service.getCustLog(param);
		l.add(cust);
		String s[] = { "timeline-blue", "timeline-green", "timeline-yellow",
				"timeline-red", "timeline-purple", "timeline-grey" };
		for(Map<String,Object> m:l){
			 int index= Math.abs( new Random().nextInt()) % s.length;
			m.put("timelineColor", s[index]) ;
		}
		
		VelocityContext context = new VelocityContext();
		context.put("l", l);
		return new ReplyHtml(
				VM.html(pagePath + "custBaseInfo/czjl.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "参数配置", "客户资料必填项维护", "列表显示" })
	public Reply findCustField() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(pagePath
				+ "custBaseInfo/customersField.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "参数配置", "客户资料必填项维护", "列表显示" })
	public Reply dataGenerate() {
		Map<String, Object> param = _getParameters();
		CustomersService service = new CustomersService();
		service.dataGenerate(param);
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "参数配置", "客户资料必填项维护", "列表显示" })
	public Reply dataSynchronization() {
		Map<String, Object> param = _getParameters();
		CustomersService service = new CustomersService();
		service.dataSynchronization(param);
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "参数配置", "客户资料必填项维护", "列表显示" })
	public Reply updateRequired() {
		Map<String, Object> param = _getParameters();
		CustomersService service = new CustomersService();
		service.updateRequired(param);
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "参数配置", "客户资料必填项维护", "列表显示" })
	public Reply findCustFieldPage() {
		Map<String, Object> param = _getParameters();
		CustomersService service = new CustomersService();
		Page page = service.findCustFieldPage(param);
		return new ReplyAjaxPage(page);
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170012", email = "guozheng@163.com", name = "guozheng")
	@aPermission(name = { "业务管理", "客户管理", "车贷信息" })
	public Reply findche() {
		VelocityContext context=new VelocityContext();
		Map<String,Object> param=_getParameters();
		CustomersService service = new CustomersService();
		List<Map<String,Object>> list = service.findAllAuto(param);
		context.put("auto", list);
		context.put("param", param);
		return new ReplyHtml(VM.html("customers/findche.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170012", email = "guozheng@163.com", name = "guozheng")
	@aPermission(name = { "业务管理", "客户管理", "车贷信息" })
	public Reply findAllche() {
		Map<String, Object> param = _getParameters();
		CustomersService service = new CustomersService();
		return new ReplyAjaxPage(service.findAllcheche(param));
	}
	
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toUpdateCustInfoGTCZR() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		
		String projectType="NP";//该项目承租人类型，默认为个人
		
		if(param.get("PROJECT_ID") !=null && !param.get("PROJECT_ID").equals("")){
			String PROJECT_ID=param.get("PROJECT_ID").toString();
			//如果存在项目ID说明是通过项目修改过来的客户，则通过项目查询出客户信息替换过来的参数
			Map clientMap=service.queryGtczrClientByProject_id(PROJECT_ID);
			if(clientMap !=null){
				param.put("CLIENT_ID", clientMap.get("JOINT_TENANT_ID"));
				param.put("TYPE", clientMap.get("JOINT_TENANT_TYPE"));
			}
			
			Map mapCl=new HashMap();
			mapCl.put("PROJECT_ID", PROJECT_ID);
			Map custMap=Dao.selectOne("project.queryClientTypeByProjectID", mapCl);
			if(custMap !=null && custMap.get("TYPE")!=null && !custMap.get("TYPE").equals("")){
				projectType=custMap.get("TYPE")+"";
			}
		}
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		
		//start 自定义表
		
		CustomModuleService customModuleService = new CustomModuleService() ;
		Map infoMap=new HashMap();//页面参数
		//param需要三个参数（1，TYPE包含UPDATE，VIEW  2，ID 主表ID  3，MODULENAME 模块名称）
	
		String createTableName = "客户自然人表" ;
		if(param.get("TYPE").equals("LP")){
			createTableName = "客户法人表" ;
		}
		param.put("CREATE_TABLE_NAME", createTableName) ;
		
		//根据模块名称查询主表子表表名信息
		Map tableMap=customModuleService.queryTableByCreateTableName(param);
		if(tableMap!=null && tableMap.size()>0){
			//根据提供的字段查询出主表关联字段
			tableMap.put("PARENT_MODULE_ID", param.get("CLIENT_ID"));
			Map mapParent=customModuleService.getParentTableField(tableMap);
			tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD"));
			
			
			//查询页面显示字段
			Map configMap=new HashMap();
			configMap.put("ID", tableMap.get("ID"));
			List info=customModuleService.queryDynamic_Field(configMap);
			context.put("info", info);
			
			//查询数据库子表是否有数据，有的话读取之前保存的数据，没有的话显示默认值
			boolean isBooleanFk=customModuleService.isBooleanFk_tableDate(tableMap);
			context.put("FK_TableIsBoolean", isBooleanFk);
			if(isBooleanFk){//已经保存过信息
				Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
				context.put("SUN_TABLE_INFO", SUN_TABLE_INFO);
			}
			
			
			context.put("dicTag", Util.DICTAG);
			
			//保存页面参数（1，子表表名，2，子表关联主表字段名，3，子表关联主表字段值）
			infoMap.put("SUN_TABLE_KEY_PAGE", tableMap.get("CREATE_TABLE"));//1，子表表名
			infoMap.put("SUN_FIELD_KEY_PAGE", tableMap.get("CREATE_F_KEY"));//2，子表关联主表字段名
			infoMap.put("SUN_FIELD_VALUE_PAGE", mapParent.get("ASSOCIATEDFIELD"));//3，子表关联主表字段值
			context.put("infoMap", infoMap);
			
			
		}
	
		//end 自定义表
		
		
		AreaService areaS= new AreaService() ;
		Map<String, Object> cus = (Map<String, Object>) service
				.findCustDetail(param);
		//获取客户ID
		Map<String, Object> client=service.getClient1(param);
		//获取客户信息
		context.put("clientInformation",service.findCustDetail(client)) ;
		context.put("EMERGENCY_INFO_COUNT",5) ;
		List<Map<String,Object>> emergencyList = service.getEmergencyInfoByClientId(cus.get("CLIENT_ID").toString()) ; 
		List<Map<String,Object>> emergencyList2 = new ArrayList<Map<String,Object>>() ;
		if(emergencyList!=null&&emergencyList.size()>0){
			context.put("EMERGENCY_INFO_COUNT",emergencyList.size()*5) ;
			int length = emergencyList.size() ;
			int syLength =  0 ;
			if(length>1){
				length = 1;
				syLength= emergencyList.size() ;
				
			}  
			for(int i=0;i<length;i++){
				int  j = i+1 ;
				cus.put("EMERGENCY_NAME_"+j, emergencyList.get(i).get("EMERGENCY_NAME")) ;
				cus.put("EMERGENCY_PHONE_"+j,  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				cus.put("EMERGENCY_ADDR_"+j,  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				cus.put("CLIENT_ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
				
				cus.put("ADDR_PROVINCE_"+j,  emergencyList.get(i).get("ADDR_PROVINCE")) ;				
				cus.put("ADDR_CITY_"+j,  emergencyList.get(i).get("ADDR_CITY")) ;
				cus.put("ADDR_COUNTY_"+j,  emergencyList.get(i).get("ADDR_COUNTY")) ;
				
				Map<String,Object> refer = new HashMap<String,Object>() ;
				if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_PROVINCE"))){
					refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_PROVINCE")) ;
					
					List<Object> houseAddressCity = areaS.getSubset(refer) ;
					cus.put("cityList_"+j, houseAddressCity);
					refer.clear();
					
					if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_CITY"))){
						refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_CITY")) ;
						List<Object> houseAddressCounty = areaS.getSubset(refer) ;
						cus.put("countyList_"+j, houseAddressCounty);
						refer.clear();
					}
				}	
				
				cus.put("ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
			
			}
			
			for(int i=1;i<syLength;i++){
				Map<String,Object> m = new HashMap<String,Object>() ;
				
				m.put("EMERGENCY_NAME", emergencyList.get(i).get("EMERGENCY_NAME")) ;
				m.put("EMERGENCY_PHONE",  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				m.put("EMERGENCY_ADDR",  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				m.put("EMERGENCY_RELATIONSHIP",  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				m.put("CLIENT_ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				m.put("ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				
				m.put("ADDR_PROVINCE",  emergencyList.get(i).get("ADDR_PROVINCE")) ;				
				m.put("ADDR_CITY",  emergencyList.get(i).get("ADDR_CITY")) ;
				m.put("ADDR_COUNTY",  emergencyList.get(i).get("ADDR_COUNTY")) ;
				
				Map<String,Object> refer = new HashMap<String,Object>() ;
				if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_PROVINCE"))){
					refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_PROVINCE")) ;
					
					List<Object> houseAddressCity = areaS.getSubset(refer) ;
					m.put("cityList", houseAddressCity);
					refer.clear();
					
					if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_CITY"))){
						refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_CITY")) ;
						List<Object> houseAddressCounty = areaS.getSubset(refer) ;
						m.put("countyList", houseAddressCounty);
						refer.clear();
					}
				}	
				
				emergencyList2.add(m) ;
			}
			
		}else{
			for(int i=0;i<2;i++){
				int  j = i+1 ;
				cus.put("EMERGENCY_NAME_"+j,"") ;
				cus.put("EMERGENCY_PHONE_"+j,  "") ;
				cus.put("EMERGENCY_ADDR_"+j,   "") ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,   "") ;
				cus.put("CLIENT_ID_"+j,  "") ;
				
				cus.put("ADDR_PROVINCE_"+j,   "") ;				
				cus.put("ADDR_CITY_"+j,   "") ;
				cus.put("ADDR_COUNTY_"+j,  "") ;
				
				cus.put("ID_"+j,   "") ;
			
			}
		}
		
		context.put("EMERGENCY_INFO",emergencyList2) ;
		
		context.put("findCustField", service.findCustField(param));
		context.put("custInfo", cus);// 客户详细信息 TODO
		context.put("custLinkInfo", service.findCustLinkInfo(param));// 客户关联信息
		context.put("type", service.findCustDataType());// 客户关联信息
		context.put("xqah", service.getANNEX(param, "XQAH", "兴趣爱好"));// 兴趣爱好
		context.put("xg", service.getANNEX(param, "XG", "性格"));// 性格
		
		context.put("getProvince", areaS.getAllProvince());// 省
		
		//与承租人关系
		if(projectType.equals("NP")){
			context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("共同承租人与个人关系"));
		}else if(projectType.equals("LP")){
			context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("共同承租人与企业关系"));
		}
		
		if (param.get("tab").equals("update")) {// 修改
			if (param.get("TYPE") != null) {
				if (param.get("TYPE").toString().equals("NP")) {// 进入自然人页面
					Map<String,Object> refer = new HashMap<String,Object>() ;
					if(StringUtils.isNotBlank(cus.get("COMPANY_ADDR_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("COMPANY_ADDR_PROVINCE")) ;
						//工作地址市
						List<Object> companyAddrCity = areaS.getSubset(refer) ;
						context.put("companyAddrCity", companyAddrCity);
						refer.clear();
						//工作地址(区/县)
						if(StringUtils.isNotBlank(cus.get("COMPANY_ADDR_CITY"))){
							refer.put("PARENT_ID", cus.get("COMPANY_ADDR_CITY")) ;
							List<Object> companyAddrCounty = areaS.getSubset(refer) ;
							context.put("companyAddrCounty", companyAddrCounty);
							refer.clear();
						}
					}
					
					if(StringUtils.isNotBlank(cus.get("HOUSE_ADDRESS_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("HOUSE_ADDRESS_PROVINCE")) ;
						//家庭地址市
						List<Object> houseAddressCity = areaS.getSubset(refer) ;
						context.put("houseAddressCity", houseAddressCity);
						refer.clear();
						//家庭地址(区/县)
						if(StringUtils.isNotBlank(cus.get("HOUSE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("HOUSE_ADDRESS_CITY")) ;
							List<Object> houseAddressCounty = areaS.getSubset(refer) ;
							context.put("houseAddressCounty", houseAddressCounty);
							refer.clear();
						}
					}	
					
					Map<String,Object> po=(Map<String, Object>) service.findCustLinkInfo(param) ;
					if(po!=null&&po.size()>0){
						Map<String,Object> spoustDet = (Map<String, Object>) po.get("spoustDet") ;
						if(spoustDet!=null&&spoustDet.size()>0){
							//配偶
//							if(StringUtils.isNotBlank(spoustDet.get("SPOUST_COMPANY_ADDR_PROVINCE"))){
							if(StringUtils.isNotBlank(spoustDet.get("COMPANY_ADDR_PROVINCE"))){
								refer.put("PARENT_ID", spoustDet.get("COMPANY_ADDR_PROVINCE")) ;
								//单位地址市
								List<Object> spoustCompanyAddrCity = areaS.getSubset(refer) ;
								context.put("spoustCompanyAddrCity", spoustCompanyAddrCity);
								refer.clear();
								//单位地址 	(区/县)
								if(StringUtils.isNotBlank(spoustDet.get("COMPANY_ADDR_CITY"))){
									refer.put("PARENT_ID", spoustDet.get("COMPANY_ADDR_CITY")) ;
									List<Object> spoustCompanyAddrCounty = areaS.getSubset(refer) ;
									context.put("spoustCompanyAddrCounty", spoustCompanyAddrCounty);
									refer.clear();
								}
								
							}
						}
						
					}
					
					return new ReplyHtml(VM.html(pagePath
							+ "toUpdateCustNatuGTCZR.vm", context));
				} else {
					Map<String,Object> refer = new HashMap<String,Object>() ;
					
					if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("REGISTE_ADDRESS_PROVINCE")) ;
						//法人注册地址市
						List<Object> registeAddressCity = areaS.getSubset(refer) ;
						context.put("registeAddressCity", registeAddressCity);
						//法人注册地址(区/县)
						refer.clear();
						if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("REGISTE_ADDRESS_CITY")) ;
							List<Object> registeAddressCounty = areaS.getSubset(refer) ;
							context.put("registeAddressCounty", registeAddressCounty);
							refer.clear();
						}	
					}
					
					if(StringUtils.isNotBlank(cus.get("WORK_ADDRESS_PROVINCE"))){
					
						refer.put("PARENT_ID", cus.get("WORK_ADDRESS_PROVINCE")) ;
						//公司办公地址市
						List<Object> workAddressCity = areaS.getSubset(refer) ;
						context.put("workAddressCity", workAddressCity);
						refer.clear();
						//公司办公地址(区/县)
						if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("WORK_ADDRESS_CITY")) ;
							List<Object> workAddressCounty = areaS.getSubset(refer) ;
							context.put("workAddressCounty", workAddressCounty);
							refer.clear();
						}
					}
					// 进入法人页面
					context.put("rc_unit",
							new DataDictionaryMemcached().get("货币种类"));
					return new ReplyHtml(VM.html(pagePath
							+ "toUpdateCustLegalGTCZR.vm", context));
				}
			}
		}
		return null;// 默认进入自然人页面
	}
	
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toViewCustInfoGTCZR() {
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		System.out.println("--------------param="+param);
		
		String projectType="NP";//该项目承租人类型，默认为个人
		Map mapCl=new HashMap();
		mapCl.put("PROJECT_ID", param.get("PROJECT_ID"));
		Map custMap=Dao.selectOne("project.queryClientTypeByProjectID", mapCl);
		if(custMap !=null && custMap.get("TYPE")!=null && !custMap.get("TYPE").equals("")){
			projectType=custMap.get("TYPE")+"";
		}
		//start 自定义表
		
			CustomModuleService customModuleService = new CustomModuleService() ;
			Map infoMap=new HashMap();//页面参数
			//param需要三个参数（1，TYPE包含UPDATE，VIEW  2，ID 主表ID  3，MODULENAME 模块名称）
		
			String createTableName = "客户自然人表" ;
			if(param.get("TYPE").equals("LP")){
				createTableName = "客户法人表" ;
			}
			param.put("CREATE_TABLE_NAME", createTableName) ;
			
			//根据模块名称查询主表子表表名信息
			Map tableMap=customModuleService.queryTableByCreateTableName(param);
			if(tableMap!=null && tableMap.size()>0){
				//根据提供的字段查询出主表关联字段
				tableMap.put("PARENT_MODULE_ID", param.get("CLIENT_ID"));
				Map mapParent=customModuleService.getParentTableField(tableMap);
				tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD"));
				
				
				//查询页面显示字段
				Map configMap=new HashMap();
				configMap.put("ID", tableMap.get("ID"));
				List info=customModuleService.queryDynamic_Field(configMap);
				context.put("info", info);
				
				//查询数据库子表是否有数据，有的话读取之前保存的数据，没有的话显示默认值
				boolean isBooleanFk=customModuleService.isBooleanFk_tableDate(tableMap);
				context.put("FK_TableIsBoolean", isBooleanFk);
				if(isBooleanFk){//已经保存过信息
					Map SUN_TABLE_INFO=customModuleService.querySunTableInfo(tableMap);
					context.put("SUN_TABLE_INFO", SUN_TABLE_INFO);
				}
				
				
				context.put("dicTag", Util.DICTAG);
				
				//保存页面参数（1，子表表名，2，子表关联主表字段名，3，子表关联主表字段值）
				infoMap.put("SUN_TABLE_KEY_PAGE", tableMap.get("CREATE_TABLE"));//1，子表表名
				infoMap.put("SUN_FIELD_KEY_PAGE", tableMap.get("CREATE_F_KEY"));//2，子表关联主表字段名
				infoMap.put("SUN_FIELD_VALUE_PAGE", mapParent.get("ASSOCIATEDFIELD"));//3，子表关联主表字段值
				context.put("infoMap", infoMap);
				
				
			}
		
			//end 自定义表
			
		
		CustomersService service = new CustomersService();
		Map<String, Object> cus = (Map<String, Object>) service
				.findCustDetail(param);
		AreaService areaS= new AreaService() ;
		
		context.put("EMERGENCY_INFO_COUNT",5) ;
		List<Map<String,Object>> emergencyList = service.getEmergencyInfoByClientId(cus.get("CLIENT_ID").toString()) ; 
		List<Map<String,Object>> emergencyList2 = new ArrayList<Map<String,Object>>() ;
		if(emergencyList!=null&&emergencyList.size()>0){
			context.put("EMERGENCY_INFO_COUNT",emergencyList.size()*5) ;
			int length = emergencyList.size() ;
			int syLength =  0 ;
			if(length>1){
				length = 1;
				syLength= emergencyList.size() ;
			}  
			for(int i=0;i<length;i++){
				int  j = i+1 ;
				cus.put("EMERGENCY_NAME_"+j, emergencyList.get(i).get("EMERGENCY_NAME")) ;
				cus.put("EMERGENCY_PHONE_"+j,  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				cus.put("EMERGENCY_ADDR_"+j,  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				cus.put("CLIENT_ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
				
				cus.put("ADDR_PROVINCE_"+j,  emergencyList.get(i).get("ADDR_PROVINCE")) ;				
				cus.put("ADDR_CITY_"+j,  emergencyList.get(i).get("ADDR_CITY")) ;
				cus.put("ADDR_COUNTY_"+j,  emergencyList.get(i).get("ADDR_COUNTY")) ;
				
				Map<String,Object> refer = new HashMap<String,Object>() ;
				if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_PROVINCE"))){
					refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_PROVINCE")) ;
					
					List<Object> houseAddressCity = areaS.getSubset(refer) ;
					cus.put("cityList_"+j, houseAddressCity);
					refer.clear();
					
					if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_CITY"))){
						refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_CITY")) ;
						List<Object> houseAddressCounty = areaS.getSubset(refer) ;
						cus.put("countyList_"+j, houseAddressCounty);
						refer.clear();
					}
				}	
				
				cus.put("ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
			
			}
			
			for(int i=1;i<syLength;i++){
				Map<String,Object> m = new HashMap<String,Object>() ;
				
				m.put("EMERGENCY_NAME", emergencyList.get(i).get("EMERGENCY_NAME")) ;
				m.put("EMERGENCY_PHONE",  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				m.put("EMERGENCY_ADDR",  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				m.put("EMERGENCY_RELATIONSHIP",  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				m.put("CLIENT_ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				
				m.put("ADDR_PROVINCE",  emergencyList.get(i).get("ADDR_PROVINCE")) ;				
				m.put("ADDR_CITY",  emergencyList.get(i).get("ADDR_CITY")) ;
				m.put("ADDR_COUNTY",  emergencyList.get(i).get("ADDR_COUNTY")) ;
				
				Map<String,Object> refer = new HashMap<String,Object>() ;
				if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_PROVINCE"))){
					refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_PROVINCE")) ;
					
					List<Object> houseAddressCity = areaS.getSubset(refer) ;
					m.put("cityList", houseAddressCity);
					refer.clear();
					
					if(StringUtils.isNotBlank(emergencyList.get(i).get("ADDR_CITY"))){
						refer.put("PARENT_ID", emergencyList.get(i).get("ADDR_CITY")) ;
						List<Object> houseAddressCounty = areaS.getSubset(refer) ;
						m.put("countyList", houseAddressCounty);
						refer.clear();
					}
				}	
				
				m.put("ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				emergencyList2.add(m) ;
			}
			
		}else{
			for(int i=0;i<1;i++){
				int  j = i+1 ;
				cus.put("EMERGENCY_NAME_"+j,"") ;
				cus.put("EMERGENCY_PHONE_"+j,  "") ;
				cus.put("EMERGENCY_ADDR_"+j,   "") ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,   "") ;
				cus.put("CLIENT_ID_"+j,  "") ;
				
				cus.put("ADDR_PROVINCE_"+j,   "") ;				
				cus.put("ADDR_CITY_"+j,   "") ;
				cus.put("ADDR_COUNTY_"+j,  "") ;
				cus.put("ID_"+j,   "") ;
			
			}
		}
		context.put("EMERGENCY_INFO",emergencyList2) ;
		
		String ISCS="";
		if(param !=null && param.get("ISCS") !=null && !param.get("ISCS").equals("")){
			ISCS=param.get("ISCS").toString();
			if(ISCS.equals("1")){
				if(cus !=null && cus.get("ID_CARD_NO") !=null && !cus.get("ID_CARD_NO").equals("")){
					String res = Dao.selectOne("checkIdCard.getIdCariStateNew", cus);
					cus.put("RESCARDYZ", res);
				}else{
					cus.put("RESCARDYZ", "wrz");
				}
			}
		}
		context.put("custInfo", cus);// 客户详细信息
		context.put("custLinkInfo", service.findCustLinkInfo(param));// 客户关联信息
		context.put("type", service.findCustDataType());// 客户关联信息
		context.put("xqah", service.getANNEX(param, "XQAH", "兴趣爱好"));// 兴趣爱好
		context.put("xg", service.getANNEX(param, "XG", "性格"));// 性格
		context.put("getProvince", areaS.getAllProvince());// 省
		
		//与承租人关系
		if(projectType.equals("NP")){
			context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("企业担保人与个人关系"));
		}else if(projectType.equals("LP")){
			context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("企业担保人与企业关系"));
		}
				
		
		if (param.get("tab").equals("view")) {// 查看
			if (param.get("TYPE") != null) {
				if (param.get("TYPE").toString().equals("NP")) {// 进入自然人页面
					
					Map<String,Object> refer = new HashMap<String,Object>() ;
					if(StringUtils.isNotBlank(cus.get("COMPANY_ADDR_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("COMPANY_ADDR_PROVINCE")) ;
						//工作地址市
						List<Object> companyAddrCity = areaS.getSubset(refer) ;
						context.put("companyAddrCity", companyAddrCity);
						refer.clear();
						//工作地址(区/县)
						if(StringUtils.isNotBlank(cus.get("COMPANY_ADDR_CITY"))){
							refer.put("PARENT_ID", cus.get("COMPANY_ADDR_CITY")) ;
							List<Object> companyAddrCounty = areaS.getSubset(refer) ;
							context.put("companyAddrCounty", companyAddrCounty);
							refer.clear();
						}
					}
					
					if(StringUtils.isNotBlank(cus.get("HOUSE_ADDRESS_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("HOUSE_ADDRESS_PROVINCE")) ;
						//家庭地址市
						List<Object> houseAddressCity = areaS.getSubset(refer) ;
						context.put("houseAddressCity", houseAddressCity);
						refer.clear();
						//家庭地址(区/县)
						if(StringUtils.isNotBlank(cus.get("HOUSE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("HOUSE_ADDRESS_CITY")) ;
							List<Object> houseAddressCounty = areaS.getSubset(refer) ;
							context.put("houseAddressCounty", houseAddressCounty);
							refer.clear();
						}
					}	
					
					Map<String,Object> po=(Map<String, Object>) service.findCustLinkInfo(param) ;
					if(po!=null&&po.size()>0){
						Map<String,Object> spoustDet = (Map<String, Object>) po.get("spoustDet") ;
						if(spoustDet!=null&&spoustDet.size()>0){
							//配偶
//							if(StringUtils.isNotBlank(spoustDet.get("SPOUST_COMPANY_ADDR_PROVINCE"))){
							if(StringUtils.isNotBlank(spoustDet.get("COMPANY_ADDR_PROVINCE"))){
								refer.put("PARENT_ID", spoustDet.get("COMPANY_ADDR_PROVINCE")) ;
								//单位地址市
								List<Object> spoustCompanyAddrCity = areaS.getSubset(refer) ;
								context.put("spoustCompanyAddrCity", spoustCompanyAddrCity);
								refer.clear();
								//单位地址 	(区/县)
								if(StringUtils.isNotBlank(spoustDet.get("COMPANY_ADDR_CITY"))){
									refer.put("PARENT_ID", spoustDet.get("COMPANY_ADDR_CITY")) ;
									List<Object> spoustCompanyAddrCounty = areaS.getSubset(refer) ;
									context.put("spoustCompanyAddrCounty", spoustCompanyAddrCounty);
									refer.clear();
								}
								
							}
						}
						
					}
					
					return new ReplyHtml(VM.html(pagePath
							+ "toViewCustDetaiLNatuGTCZR.vm", context));
				} else {// 进入法人页面
					// context.put("financeData",
					// service.selectFinanceData(param));//法人财报===付玉龙
					
					Map<String,Object> refer = new HashMap<String,Object>() ;
					
					if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_PROVINCE"))){
						refer.put("PARENT_ID", cus.get("REGISTE_ADDRESS_PROVINCE")) ;
						//法人注册地址市
						List<Object> registeAddressCity = areaS.getSubset(refer) ;
						context.put("registeAddressCity", registeAddressCity);
						//法人注册地址(区/县)
						refer.clear();
						if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("REGISTE_ADDRESS_CITY")) ;
							List<Object> registeAddressCounty = areaS.getSubset(refer) ;
							context.put("registeAddressCounty", registeAddressCounty);
							refer.clear();
						}	
					}
					
					if(StringUtils.isNotBlank(cus.get("WORK_ADDRESS_PROVINCE"))){
					
						refer.put("PARENT_ID", cus.get("WORK_ADDRESS_PROVINCE")) ;
						//公司办公地址市
						List<Object> workAddressCity = areaS.getSubset(refer) ;
						context.put("workAddressCity", workAddressCity);
						refer.clear();
						//公司办公地址(区/县)
						if(StringUtils.isNotBlank(cus.get("REGISTE_ADDRESS_CITY"))){
							refer.put("PARENT_ID", cus.get("WORK_ADDRESS_CITY")) ;
							List<Object> workAddressCounty = areaS.getSubset(refer) ;
							context.put("workAddressCounty", workAddressCounty);
							refer.clear();
						}
					}
					
					context.put("rc_unit",
							new DataDictionaryMemcached().get("货币种类"));
					return new ReplyHtml(VM.html(pagePath
							+ "toViewCustDetailLegalGTCZR.vm", context));
				}
			}
		}
		return null;// 默认进入自然人页面
	}
	
	/*add by lishuo
	 *內部匹配
	 */
	@aDev(code="23" , email="shuoli@jiezhongchina.com" ,name="李硕")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"参数配置","内部匹配"})
	public Reply toInnerCompare(){
		Map<String, Object> param = this.getPageParameter();
		VelocityContext context=new VelocityContext();
		Map<String,Object> mm =new HashMap<String,Object>();
		CustomersService service = new CustomersService();
		//內部匹配SQL
		mm.put("PROJECT_ID", param.get("PROJECT_ID"));
		mm.put("PRO_CODE", param.get("PRO_CODE"));
		Map<String,Object> total =new HashMap<String,Object>();
		List<Map<String, Object>> info =null;
		info = service.goInnerCompare(mm); 
		if(!info.isEmpty() && info.size() > 0){
			context.put("inner", info);
		}
		return new ReplyHtml(VM.html("customers/innerCompare.vm", context));
	}
	
	/*add by lishuo　11.30.2015
	 *　固定产品月还
	 */
	@aDev(code="23" , email="shuoli@jiezhongchina.com" ,name="李硕")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"参数配置","固定月还"})
	public Reply SoldProductPayBack(){
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		CustomersService service = new CustomersService();
		
		/*List<Map<String, Object>> list = service.queryBaseIfo(param);
		context.put("item", list);*/
		context.put("datetimeName", param.get("_datetime"));
		
		return new ReplyHtml(VM.html(pagePath + "soldProductPayBack.vm", context));
	}
	
	/*
	 * luoxianhong 12.1.2015
	 */
	@aDev(code="17800" , email="yixinggu123@163.com" ,name="luoxianhong")
	@aAuth(type = aAuthType.LOGIN)
	public Reply soldAjax() {
		Map<String, Object> map = _getParameters();
		CustomersService service = new CustomersService();
		
		if (map.containsKey("param")) {
			JSONObject json = JSONObject.fromObject(map.get("param"));
			map.remove("param");
			map.putAll(JsonUtil.toMap(json));
		}
		
		Page page = service.querySoldPage(map);
		return new ReplyAjaxPage(page);
	}
	
	/*add by lishuo　12.3.2015
	 *　导入EXCEL
	 */
	@aDev(code="23" , email="shuoli@jiezhongchina.com" ,name="李硕")
	@aAuth(type = aAuthType.LOGIN)
	public Reply readExcel(){
		Map<String, Object> map  = new HashMap<String,Object>();
		CustomersService service = new CustomersService();
		File file = _getFileOne();
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet = wb.getSheetAt(0);
		int result	= 0;
		//循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
		for(int j=0;j<=sheet.getLastRowNum();j++) {
			Row row = sheet.getRow(j);//行对象
				
				Cell PRODUCT_NAME 			= row.getCell(0);
				Cell CUONTS_To_INT 			= row.getCell(1);
				Cell CAR_PRICE_FORM_To_INT  = row.getCell(2);
				Cell CAR_PRICE_TO_To_INT 	= row.getCell(3);
				Cell PAYBACK_To_INT			= row.getCell(4);
				Cell TOTALMONEY_To_INT 		= row.getCell(5);
				Cell CAR_TYPE_MEMO 			= row.getCell(6);
				 
				if(PRODUCT_NAME==null || CUONTS_To_INT==null || CAR_PRICE_FORM_To_INT ==null
						|| CAR_PRICE_TO_To_INT ==null || PAYBACK_To_INT ==null ||
						TOTALMONEY_To_INT ==null || CAR_TYPE_MEMO == null){
					continue;
				}
				
				String haha =PRODUCT_NAME.getStringCellValue();
				int CUONTS =(int) CUONTS_To_INT.getNumericCellValue();
				int CAR_PRICE_FORM =(int) CAR_PRICE_FORM_To_INT.getNumericCellValue();
				int CAR_PRICE_TO =(int) CAR_PRICE_TO_To_INT.getNumericCellValue();
				int PAYBACK =(int) PAYBACK_To_INT.getNumericCellValue();
				int TOTALMONEY =(int) TOTALMONEY_To_INT.getNumericCellValue();
				String haha2 =CAR_TYPE_MEMO.getStringCellValue();
				
				map.put("PRODUCT_NAME", (PRODUCT_NAME+""));
				map.put("CUONTS", (CUONTS+""));
				map.put("CAR_PRICE_FORM", (CAR_PRICE_FORM+""));
				map.put("CAR_PRICE_TO", (CAR_PRICE_TO+""));
				map.put("PAYBACK", (PAYBACK+""));
				map.put("TOTALMONEY", (TOTALMONEY+""));
				map.put("CAR_TYPE_MEMO", (CAR_TYPE_MEMO+""));
				 
				System.out.println(map);
				if(map.size()>0 || !(map.isEmpty())){
					result = service.ImportExcel(map);
				}
		}
		boolean flag = false;
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return SoldProductPayBack();
	}
	
	/*
	 * 固定月还额
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "luohong", email = "yixinggu@163.com", name = "luohong")
	public Reply saveCarInfo(){
		Map<String, Object> param = _getParameters();
		System.out.println(param.toString());
		//String project_id = (String) param.get("project_id");
		CustomersService service = new CustomersService();
		int i =service.saveCarInfo(param);
		boolean flag = false;
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		
		return new ReplyAjax(flag);
	}
	
	/*add by lishuo　11.30.2015
	 *　查询固定产品月还融资额
	 */
	@aDev(code="23" , email="shuoli@jiezhongchina.com" ,name="李硕")
	@aAuth(type = aAuthType.LOGIN)
	public Reply QueryTotalMoney(){
		Map<String, Object> map = _getParameters();
		CustomersService service = new CustomersService();
		List<Map<String,Object>> item=service.QueryTotalMoney(map);
		if(item.size()>0){
			return new ReplyAjax(JSONArray.fromObject(item));
		}
		return new ReplyAjax(null);
	}
	
	/*add by lishuo　12.3.2015
	 *　根据ID查询固定产品月还融资额
	 */
	@aDev(code="23" , email="shuoli@jiezhongchina.com" ,name="李硕")
	@aAuth(type = aAuthType.LOGIN)
	public Reply FindSoldProductByID(){
		Map<String, Object> map = _getParameters();
		CustomersService service = new CustomersService();
		List<Map<String,Object>> result=service.FindSoldProductByID(map);
		boolean flag = false;
		if (result.size() > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(JSONArray.fromObject(result));
	}
	
	/*add by lishuo　12.2.2015
	 *　修改固定产品月还融资额
	 */
	@aDev(code="23" , email="shuoli@jiezhongchina.com" ,name="李硕")
	@aAuth(type = aAuthType.LOGIN)
	public Reply UpdateSoldProcuct(){
		Map<String, Object>  map = _getParameters();
		CustomersService service = new CustomersService();
		int i =service.UpdateSoldProcuct(map);
		boolean flag = false;
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag);
	}
	
	/*add by lishuo　12.2.2015
	 *　删除固定产品月还融资额
	 */
	@aDev(code="23" , email="shuoli@jiezhongchina.com" ,name="李硕")
	@aAuth(type = aAuthType.LOGIN)
	public Reply DeleteSoldProduct(){
		Map<String, Object> map = _getParameters();
		CustomersService service = new CustomersService();
		int i=service.DeleteSoldProduct(map);
		boolean flag = false;
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag);
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
	public Reply updateBankProject() {
		Map<String, Object> map = _getParameters();
		CustomersService service = new CustomersService();
		int i = service.updateBankProject(map);
		boolean flag = false;
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		map.put("USERID", Security.getUser().getId());
		return new ReplyAjax(flag, null).addOp(new OpLog("客户资料管理", "修改项目开户行", map
				.get("USERID").toString()));
	}
	
	
	@aPermission (name = { "担保人" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply addCustomerEmpty() {
		Map<String, Object> map = _getParameters();
		CustomersService  service= new CustomersService();
		map.put("CUST_TYPE", 3);
		return new ReplyAjax(service.addCustomerEmpty(map));
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.ALL)
	public Reply getCityAreaById() {
		Map<String, Object> map = _getParameters();
		CustomersService service = new CustomersService();
		AreaService areaS=new AreaService() ;
		List<Object> l = service.getCityAreaById(map.get("ID").toString()) ;
		
		return new ReplyJson(JSONArray.fromObject(l));
	}
	
	/**
	 * 实际费用页面初始化功能
	 * @return
	 */
	@aPermission(name = { "实际费用明细" })
	@aDev(code = "luohong", email = "yixinggu123@163.com", name = "luohong")
	@aAuth(type = aAuthType.USER)
	public Reply initSify(){
		Map<String, Object> map = _getParameters();
		Map mapJson = new HashMap();
		CustomersService service = new CustomersService();
		
		mapJson = setMapJsonProject(service, map, mapJson);
		
		JSONObject jsonObject = null;
		if(mapJson.isEmpty()){
			jsonObject.put("flag", false);
		}else{
			jsonObject = JSONObject.fromObject(mapJson);  
			jsonObject.put("flag", true);
		}
		
		return new ReplyAjax(jsonObject);
	}
	
	
	/**
	 * 
	 * 款项明细比对
	 * @return
	 */
	@aAuth(type = aAuthType.ALL)
	@aPermission(name = { "参数配置","款项明细比对" })
	@aDev(code = "gengchangbao", email = "yixinggu123@163.com", name = "gengchangbao")
	public Reply getLoanDetails(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		System.out.println(map.toString());
		CustomersService service = new CustomersService();
		Map<String,Object> mm=new HashMap<String,Object>();
		List<Map<String,Object>> cc= service.getSlsjfyInfo(mm);
		
		context.put("item", cc);
		context.put("PROJECT_ID", map.get("PROJECT_ID"));
		context.put("SCHEME_ROW_NUM", map.get("SCHEME_ROW_NUM"));
		context.put("PLATFORM_TYPE", map.get("PLATFORM_TYPE"));
		context.put("SCHEME_ID", map.get("SCHEME_ID"));
		paymentService payservice=new paymentService();
		Map reLeaseCode = payservice.getReSignLeaseCode(map);
		context.put("DEL_PROJECT_ID", reLeaseCode.get("DEL_PROJECT_ID"));
		context.put("DEL_LEASE_CODE", reLeaseCode.get("DEL_LEASE_CODE"));
		context.put("LEASE_CODE", reLeaseCode.get("LEASE_CODE"));
		context.put("PROJECT_ID", reLeaseCode.get("PROJECT_ID"));
		
		// 初始化方案金额，显示到预览项
		//initReSchemeMoney(context, service, map);
		// 查询已经情况的费用明细
		map.put("IS_PAY", 1);
		List<Map<String,Object>> detailList = payservice.findXmmxlist(map);
		
		projectService proService = new projectService();
		Map<String,Object> parProject = null; // 重签合同数据【主要获取前版【废弃】项目ID】
		List<Map<String,Object>> reSignDetaillist = null; //前版【废弃】项目
		//获取重签合同前版【废弃】项目编号
		List<Map<String,Object>> parProjectList = proService.queryParentProjectById(map);
		if (parProjectList != null && parProjectList.size() > 0) {
			parProject = parProjectList.get(0);
			if (parProject.get("PARENT_ID") != null && !"".equals(parProject.get("PARENT_ID"))) {
				context.put("PAR_PROJECT_ID", parProject.get("PARENT_ID"));
				
				//获取重签合同前版【废弃】放款详细金额 
				// 查询重签合同编号废弃合同已经支付费用明细
				Map<String, Object> parProjectMap = new HashMap<String, Object>();
				parProjectMap.put("PROJECT_ID", parProject.get("PARENT_ID"));
				parProjectMap.put("isLoan", 1); 
				//获取已支付费用明细
				reSignDetaillist = proService.findReSignXmmxlist(parProjectMap);
				if (reSignDetaillist != null && reSignDetaillist.size()>0) {
					reSignDetaillist = proService.findXmmxlist(parProjectMap);
				}
			}
		}
		
		// 实付金额【废弃合同】
		Map<String, Object> detail = new HashMap<String, Object>();
		// 差价
		Map<String, Object> difference = new HashMap<String, Object>();
		if (reSignDetaillist != null) {
			//detail = getProjectDetailAndBase(detaillist);
			//整合重签合同费用明细与旧合同实付金额的差额
			//difference = getParentDifference(context,reSignDetaillist,(Map)context.get("schemeMoney"));
			difference = getParentDifference(context, detailList, reSignDetaillist);
		}
		
		return new ReplyHtml(VM.html("customers/reExpenses.vm",context));
	}
	
	/**
	 * 
	 * 整合重签合同费用明细与旧合同实付金额的差额
	 * @param detaillist 实付【废弃合同】类型和价格
	 * @param schemeMoney 应付款项
	 * @return 价钱类型和对应的值Map对象
	 */
	private Map<String, Object> getParentDifference(VelocityContext context, List<Map<String, Object>> detaillist,List<Map<String, Object>> reSignDetailList) {
		
		// 实付金额【废弃合同】
		Map<String, Object> reSignDetail = new HashMap<String, Object>();
		reSignDetail.put("money1", "0");	//车辆实际采购价
		reSignDetail.put("money2", "0");	//车辆购置税
		reSignDetail.put("money3", "0");	//商业险
		reSignDetail.put("money4", "0");	//交强险
		reSignDetail.put("money5", "0");	//车船税
		reSignDetail.put("money6", "0");	//路桥费
		reSignDetail.put("money7", "0");	//环保标费
		reSignDetail.put("money8", "0");	//上牌费
		reSignDetail.put("money9", "0");	//临牌费
		reSignDetail.put("money10", "0");	//其他费用
		// 前版已付设备费用明细整理 Start
		for (Map<String, Object> map : reSignDetailList) {
			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
			if("1".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money1", map.get("APPLY_MONEY"));	//车辆实际采购价
				} else {
					reSignDetail.put("money1", "0");
				}
				
			}else if("2".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money2", map.get("APPLY_MONEY"));	//车辆购置税
				} else {
					reSignDetail.put("money2", "0");
				}
			}else if("3".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money3", map.get("APPLY_MONEY"));	//商业险
				} else {
					reSignDetail.put("money3", "0");
				}
			}else if("4".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money4", map.get("APPLY_MONEY"));	//交强险
				} else {
					reSignDetail.put("money4", "0");
				}
			}else if("5".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money5", map.get("APPLY_MONEY"));	//车船税
				} else {
					reSignDetail.put("money5", "0");
				}
			}else if("6".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money6", map.get("APPLY_MONEY"));	//路桥费
				} else {
					reSignDetail.put("money6", "0");
				}
			}else if("7".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money7", map.get("APPLY_MONEY"));	//环保标费
				} else {
					reSignDetail.put("money7", "0");
				}
			}else if("8".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money8", map.get("APPLY_MONEY"));	//上牌费
				} else {
					reSignDetail.put("money8", "0");
				}
			}else if("9".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money9", map.get("APPLY_MONEY"));	//临牌费
				} else {
					reSignDetail.put("money9", "0");
				}
			}else if("10".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money10", map.get("APPLY_MONEY"));	//其他费用
				} else {
					reSignDetail.put("money10", "0");
				}
			}
		}
		// 前版已付设备费用明细整理 end
		
		// 实付金额【废弃合同】
		Map<String, Object> detail = new HashMap<String, Object>();
		// 差价
		Map<String, Object> difference = new HashMap<String, Object>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("APPLY_MONEY", "0");
		detail.put("CLSJ", initMap);	//车辆实际采购价
		detail.put("CLGZS", initMap);	//车辆购置税
		detail.put("SYBX", initMap);	//商业险
		detail.put("JQX", initMap);		//交强险
		detail.put("CCS", initMap);		//车船税
		detail.put("LQF", initMap);		//路桥费
		detail.put("HBFY", initMap);	//环保标费
		detail.put("SPF", initMap);		//上牌费
		detail.put("LPF", initMap);		//临牌费
		detail.put("QTFY", initMap);	//其他费用
		BigDecimal sfje; //作废合同实付金额
		BigDecimal yfje; //重签合同应付金额
		BigDecimal cj; //差价
		for (Map<String, Object> map : detaillist) {
			yfje = new BigDecimal(0);
			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
			
			if("1".equals(TYPEID)){
				detail.put("CLSJ", map);	//车辆实际采购价
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money1").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference1", cj);

			}else if("2".equals(TYPEID)){
				detail.put("CLGZS", map);	//车辆购置税
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money2").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference2", cj);

			}else if("3".equals(TYPEID)){
				detail.put("SYBX", map);	//商业险
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money3").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference3", cj);
				
			}else if("4".equals(TYPEID)){
				detail.put("JQX", map);		//交强险
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money4").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference4", cj);
				
			}else if("5".equals(TYPEID)){
				detail.put("CCS", map);		//车船税
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money5").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference5", cj);
				
			}else if("6".equals(TYPEID)){
				detail.put("LQF", map);		//路桥费
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money6").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference6", cj);

			}else if("7".equals(TYPEID)){
				detail.put("HBFY", map);	//环保标费
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money7").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference7", cj);

			}else if("8".equals(TYPEID)){
				detail.put("SPF", map);		//上牌费
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money8").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference8", cj);
				
			}else if("9".equals(TYPEID)){
				detail.put("LPF", map);		//临牌费
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money9").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference9", cj);
				
			}else if("10".equals(TYPEID)){
				detail.put("QTFY", map);	//其他费用
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money10").toString());
				cj = yfje.subtract(sfje);
				difference.put("difference10", cj);
				
			}
		}
		
		context.put("schemeMoney",reSignDetail);
		context.put("detaillist",detail);
		context.put("differencelist",difference);
		return detail;
	}
	
//	/**
//	 * 
//	 * 整合重签合同费用明细与旧合同实付金额的差额
//	 * @param detaillist 实付【废弃合同】类型和价格
//	 * @param schemeMoney 应付款项
//	 * @return 价钱类型和对应的值Map对象
//	 */
//	private Map<String, Object> getParentDifference(VelocityContext context, List<Map<String, Object>> detaillist,Map schemeMoney) {
//		
//		// 实付金额【废弃合同】
//		Map<String, Object> detail = new HashMap<String, Object>();
//		// 差价
//		Map<String, Object> difference = new HashMap<String, Object>();
//		
//		BigDecimal sfje; //作废合同实付金额
//		BigDecimal yfje; //重签合同应付金额
//		BigDecimal cj; //差价
//		Map<String, Object> initMap = new HashMap<String, Object>();
//		initMap.put("APPLY_MONEY", "0");
//		detail.put("CLSJ", initMap);	//车辆实际采购价
//		detail.put("CLGZS", initMap);	//车辆购置税
//		detail.put("SYBX", initMap);	//商业险
//		detail.put("JQX", initMap);		//交强险
//		detail.put("CCS", initMap);		//车船税
//		detail.put("LQF", initMap);		//路桥费
//		detail.put("HBFY", initMap);	//环保标费
//		detail.put("SPF", initMap);		//上牌费
//		detail.put("LPF", initMap);		//临牌费
//		detail.put("QTFY", initMap);	//其他费用
//		for (Map<String, Object> map : detaillist) {
//			sfje = new BigDecimal(0);
//			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
//			
//			if("1".equals(TYPEID)){
//				detail.put("CLSJ", map);	//车辆实际采购价
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money1").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference1", cj);
//			}else if("2".equals(TYPEID)){
//				detail.put("CLGZS", map);	//车辆购置税
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money2").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference2", cj);
//			}else if("3".equals(TYPEID)){
//				detail.put("SYBX", map);	//商业险
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money3").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference3", cj);
//			}else if("4".equals(TYPEID)){
//				detail.put("JQX", map);		//交强险
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money4").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference4", cj);
//			}else if("5".equals(TYPEID)){
//				detail.put("CCS", map);		//车船税
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money5").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference5", cj);
//			}else if("6".equals(TYPEID)){
//				detail.put("LQF", map);		//路桥费
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money6").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference6", cj);
//			}else if("7".equals(TYPEID)){
//				detail.put("HBFY", map);	//环保标费
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money7").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference7", cj);
//			}else if("8".equals(TYPEID)){
//				detail.put("SPF", map);		//上牌费
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money8").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference8", cj);
//			}else if("9".equals(TYPEID)){
//				detail.put("LPF", map);		//临牌费
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money9").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference9", cj);
//			}else if("10".equals(TYPEID)){
//				detail.put("QTFY", map);	//其他费用
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money10").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference10", cj);
//			}
//		}
//		
//		
//		
//		context.put("detaillist",detail);
//		context.put("differencelist",difference);
//		return detail;
//	}
	
	/**
	 * modify by lishuo 12.7.2015
	 * modify @aAuth 
	 * 显示实际费用明细
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置","实际费用明细" })
	@aDev(code = "luohong", email = "yixinggu123@163.com", name = "luohong")
	public Reply getSify(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		System.out.println(map.toString());
		CustomersService service = new CustomersService();
		Map<String,Object> mm=new HashMap<String,Object>();
		List<Map<String,Object>> cc= service.getSlsjfyInfo(mm);
		
		projectService proService = new projectService();
		List<Map<String,Object>> detaillist =proService.findXmmxlist(map);
		
		Map<String, Object> detail = getProjectDetailAndBase(detaillist);
		context.put("detaillist",detail);
		
		context.put("item", cc);
		context.put("PROJECT_ID", map.get("PROJECT_ID"));
		context.put("SCHEME_ROW_NUM", map.get("SCHEME_ROW_NUM"));
		context.put("PLATFORM_TYPE", map.get("PLATFORM_TYPE"));
		context.put("SCHEME_ID", map.get("SCHEME_ID"));
		
		// 初始化方案金额，显示到预览项
		initSchemeMoney(context, service, map);
		
		return new ReplyHtml(VM.html("customers/expenses.vm",context));
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
	 * gengchangbao
	 * 查询重签项目应付费用，显示到预览项
	 * @param context
	 * @param service
	 * @param map
	 */
	public void initParentSchemeMoney(VelocityContext context, CustomersService service, Map<String, Object> map){
		
		Map mapJson = new HashMap();
		
		mapJson = setMapJsonParentProject(service, map, mapJson);
		
		context.put("schemeMoney", mapJson);
	}
	
	/**
	 * 初始化方案金额，显示到预览项
	 * @param context
	 * @param service
	 * @param map
	 */
	public void initSchemeMoney(VelocityContext context, CustomersService service, Map<String, Object> map){
		
		Map mapJson = new HashMap();
		
		mapJson = setMapJsonProject(service, map, mapJson);
		
		context.put("schemeMoney", mapJson);
	}

	/**
	 * 初始化方案金额，显示到预览项
	 * @param context
	 * @param service
	 * @param map
	 */
	private void initReSchemeMoney(VelocityContext context, CustomersService service, Map<String, Object> map){
		
		Map mapJson = new HashMap();
		
		mapJson = setMapJsonReProject(service, map, mapJson);
		
		context.put("schemeMoney", mapJson);
	}
	/**
	 * 公共方法，得到方案实际费用明细的值
	 * @param service	用户服务类
	 * @param map		页面取值集合，用于查询条件
	 * @param mapJson	传入map对象，用于接收实际费用明细的值
	 * @return map 		返回值为传入值mapJson，接收值并返回
	 */
	private Map setMapJsonReProject(CustomersService service, Map<String, Object> map, Map mapJson) {
		
		List eqList = service.queryEqByProjectIDByScheme(map);
		if(eqList != null && eqList.size()>0){
			Map obj = (Map) eqList.get(0);
			mapJson.put("money4", obj.get("JQX"));		 // 交强险
			mapJson.put("reMoney4", "0");	 // 废弃合同交强险
			mapJson.put("difference4", "0");  //差额
			
			mapJson.put("money5", obj.get("CCS"));		 // 车船税
			mapJson.put("reMoney5", "0");		 // 废弃合同车船税
			mapJson.put("difference5", "0");  //差额
			
			mapJson.put("money3", obj.get("BX"));		 // 商业险
			mapJson.put("reMoney3", "0");		 // 废弃合同车商业险
			mapJson.put("difference3", "0");  //差额
			
			mapJson.put("money1", obj.get("UNIT_PRICE"));// 保存车辆实际采购价
			mapJson.put("reMoney1", "0");// 废弃合同车辆实际采购价
			mapJson.put("difference1", "0");  //差额
		}else{
			mapJson.put("money1", "0");// 保存车辆实际采购价
			mapJson.put("reMoney1", "0");// 废弃合同车辆实际采购价
			mapJson.put("difference1", "0");  //差额
		}
		
		// king查方案
		BaseSchemeService bsService = new BaseSchemeService();
		
		List<Map<String, Object>> schemeDetail = bsService.getSchemeClob(null, map.get("PROJECT_ID") + "",
				map.get("SCHEME_ROW_NUM") + "");

		Map<String, Object> schemeBase = bsService.getSchemeBaseInfoByProjectId(
				map.get("PROJECT_ID") + "", null,
				map.get("SCHEME_ROW_NUM") + "").get(0);
		
		mapJson.put("FIRST_PAYALL", schemeBase.get("FIRSTPAYALL"));
		
		for (Map<String, Object> map2 : schemeDetail) {
			if(map2.get("KEY_NAME_EN").equals("GZS_MONEY")){
				// 车辆购置税
				mapJson.put("money2", map2.get("VALUE_STR"));
				mapJson.put("reMoney2", "0");
				mapJson.put("difference2", "0");  //差额
				
			}/*else if(map2.get("KEY_NAME_EN").equals("INSURANCE_MONEY")){
				// 保险费（商业）
				mapJson.put("money3", map2.get("VALUE_STR"));
				
			}*/else if(map2.get("KEY_NAME_EN").equals("SHANGPAI_MONEY")){
				// 上牌费
				mapJson.put("money8", map2.get("VALUE_STR"));
				mapJson.put("reMoney8", "0");
				mapJson.put("difference8", "0");  //差额
				
			}else if(map2.get("KEY_NAME_EN").equals("KEEP_MONEY")){
				// 路桥费
				mapJson.put("money6", map2.get("VALUE_STR"));
				mapJson.put("reMoney6", "0");
				mapJson.put("difference6", "0");  //差额
				
			}else if(map2.get("KEY_NAME_EN").equals("MOUNT_MONEY")){ // modify by lishuo 12.23.2015 临牌费  原先是GPS费
				// 临牌费
				mapJson.put("money9", map2.get("VALUE_STR"));
				mapJson.put("reMoney9", "0");
				mapJson.put("difference9", "0");  //差额
				
			}else if(map2.get("KEY_NAME_EN").equals("YCZBF_MONEY")){// modify by lishuo 12.23.2015 环保标费  原先是延长zhibaofei费
				// 环保标费
				mapJson.put("money7", map2.get("VALUE_STR"));
				mapJson.put("reMoney7", "0");
				mapJson.put("difference7", "0");  //差额
				
			}else if(map2.get("KEY_NAME_EN").equals("QITA_MONEY")){
				// 其他费用
				mapJson.put("money10", map2.get("VALUE_STR"));
				mapJson.put("reMoney10", "0");
				mapJson.put("difference10", "0");  //差额
				
			}
		}
		for (int i = 1; i < 11; i++) {
			if (mapJson.get("money"+i) == null || "".equals(mapJson.get("money"+i))) {
				mapJson.put("money"+i, "0");	
			}
		}
		return mapJson;
	}
	
	/**
	 * 公共方法，得到方案实际费用明细的值
	 * @param service	用户服务类
	 * @param map		页面取值集合，用于查询条件
	 * @param mapJson	传入map对象，用于接收实际费用明细的值
	 * @return map 		返回值为传入值mapJson，接收值并返回
	 */
	public Map setMapJsonProject(CustomersService service, Map<String, Object> map, Map mapJson) {
		List eqList = service.queryEqByProjectIDByScheme(map);
		if(eqList != null && eqList.size()>0){
			Map obj = (Map) eqList.get(0);
			mapJson.put("money4", obj.get("JQX"));		 // 交强险
			mapJson.put("money5", obj.get("CCS"));		 // 车船税
			mapJson.put("money3", obj.get("BX"));		 // 商业险
			mapJson.put("money1", obj.get("UNIT_PRICE"));// 保存车辆实际采购价
		}else{
			mapJson.put("money1", "0");// 保存车辆实际采购价
		}
		
		// king查方案
		BaseSchemeService bsService = new BaseSchemeService();
		
		List<Map<String, Object>> schemeDetail = bsService.getSchemeClob(null, map.get("PROJECT_ID") + "",
				map.get("SCHEME_ROW_NUM") + "");

		Map<String, Object> schemeBase = bsService.getSchemeBaseInfoByProjectId(
				map.get("PROJECT_ID") + "", null,
				map.get("SCHEME_ROW_NUM") + "").get(0);
		
		mapJson.put("FIRST_PAYALL", schemeBase.get("FIRSTPAYALL"));
		
		for (Map<String, Object> map2 : schemeDetail) {
			if(map2.get("KEY_NAME_EN").equals("GZS_MONEY")){
				// 车辆购置税
				mapJson.put("money2", map2.get("VALUE_STR"));
				
			}/*else if(map2.get("KEY_NAME_EN").equals("INSURANCE_MONEY")){
				// 保险费（商业）
				mapJson.put("money3", map2.get("VALUE_STR"));
				
			}*/else if(map2.get("KEY_NAME_EN").equals("SHANGPAI_MONEY")){
				// 上牌费
				mapJson.put("money8", map2.get("VALUE_STR"));
				
			}else if(map2.get("KEY_NAME_EN").equals("KEEP_MONEY")){
				// 路桥费
				mapJson.put("money6", map2.get("VALUE_STR"));
				
			}else if(map2.get("KEY_NAME_EN").equals("MOUNT_MONEY")){ // modify by lishuo 12.23.2015 临牌费  原先是GPS费
				// 临牌费
				mapJson.put("money9", map2.get("VALUE_STR"));
				
			}else if(map2.get("KEY_NAME_EN").equals("YCZBF_MONEY")){// modify by lishuo 12.23.2015 环保标费  原先是延长zhibaofei费
				// 环保标费
				mapJson.put("money7", map2.get("VALUE_STR"));
				
			}else if(map2.get("KEY_NAME_EN").equals("QITA_MONEY")){
				// 其他费用
				mapJson.put("money10", map2.get("VALUE_STR"));
				
			}
		}
		
		return mapJson;
	}
	
	/**
	 * 
	 * gengchangbao
	 * 公共方法，得到方案实际费用明细的值
	 * @param service	用户服务类
	 * @param map		页面取值集合，用于查询条件
	 * @param mapJson	传入map对象，用于接收实际费用明细的值
	 * @return map 		返回值为传入值mapJson，接收值并返回
	 */
	public Map setMapJsonParentProject(CustomersService service, Map<String, Object> map, Map mapJson) {
		List eqList = service.queryEqByProjectIDByScheme(map);
		if(eqList != null && eqList.size()>0){
			Map obj = (Map) eqList.get(0);
			mapJson.put("money4", obj.get("JQX"));		 // 交强险
			mapJson.put("money5", obj.get("CCS"));		 // 车船税
			mapJson.put("money3", obj.get("BX"));		 // 商业险
			mapJson.put("money1", obj.get("UNIT_PRICE"));// 保存车辆实际采购价
		}else{
			mapJson.put("money1", "0");// 保存车辆实际采购价
		}
		
		// king查方案
		BaseSchemeService bsService = new BaseSchemeService();
		
		List<Map<String, Object>> schemeDetail = bsService.getSchemeClob(null, map.get("PROJECT_ID") + "",
				map.get("SCHEME_ROW_NUM") + "");

		Map<String, Object> schemeBase = bsService.getSchemeBaseInfoByProjectId(
				map.get("PROJECT_ID") + "", null,
				map.get("SCHEME_ROW_NUM") + "").get(0);
		
		mapJson.put("FIRST_PAYALL", schemeBase.get("FIRSTPAYALL"));
		
		for (Map<String, Object> map2 : schemeDetail) {
			if(map2.get("KEY_NAME_EN").equals("GZS_MONEY")){
				// 车辆购置税
				mapJson.put("money2", map2.get("VALUE_STR"));
				
			}/*else if(map2.get("KEY_NAME_EN").equals("INSURANCE_MONEY")){
				// 保险费（商业）
				mapJson.put("money3", map2.get("VALUE_STR"));
				
			}*/else if(map2.get("KEY_NAME_EN").equals("SHANGPAI_MONEY")){
				// 上牌费
				mapJson.put("money8", map2.get("VALUE_STR"));
				
			}else if(map2.get("KEY_NAME_EN").equals("KEEP_MONEY")){
				// 路桥费
				mapJson.put("money6", map2.get("VALUE_STR"));
				
			}else if(map2.get("KEY_NAME_EN").equals("MOUNT_MONEY")){ // modify by lishuo 12.23.2015 临牌费  原先是GPS费
				// 临牌费
				mapJson.put("money9", map2.get("VALUE_STR"));
				
			}else if(map2.get("KEY_NAME_EN").equals("YCZBF_MONEY")){// modify by lishuo 12.23.2015 环保标费  原先是延长zhibaofei费
				// 环保标费
				mapJson.put("money7", map2.get("VALUE_STR"));
				
			}else if(map2.get("KEY_NAME_EN").equals("QITA_MONEY")){
				// 其他费用
				mapJson.put("money10", map2.get("VALUE_STR"));
				
			}
		}
		
		return mapJson;
	}

	/**
	 * modify by lishuo 12.8.2015
	 * 实际费用页面保存功能
	 * @return
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "luohong", email = "yixinggu123@163.com", name = "luohong")
	public Reply sjfySave1() {
		Map<String, Object> param1 = _getParameters();
		Map<String, Object> param =new HashMap<String, Object>();
		param.put("PROJECT_ID", param1.get("PROJECT_ID"));
		
		CustomersService  service= new CustomersService();
		
		boolean flag = false;
		String msg = "";
		int k=0;
		
		for(int i=1;i<=param1.size();i++){
			if(param1.containsKey("money"+i)){
				// 只有车辆实际采购价才会保存首付金额
				if(StringUtils.isNotBlank(param.get("FIRST_PAYALL"))){
					param.remove("FIRST_PAYALL");
				}else if(1 == i){
					param.put("FIRST_PAYALL", param1.get("FIRST_PAYALL"));
				}
				
				if(StringUtils.isNotBlank(param1.get("money"+i))){
					param.put("APPLY_MONEY", param1.get("money"+i));
				}else{
					param.put("APPLY_MONEY", 0);
				}
				param.put("COUSTNAME", param1.get("cost" + i));//COUSTNAME
				param.put("TYPE_ID", i);//TYPE_ID
				k = service.addxmmx(param);
			}
		}
		
		if (k > 0) {
			flag = true;
			msg = "保存成功";
		} else {
			msg = "保存失败";
		}
		return new ReplyAjax(flag, msg);
	}
	
	/**
	 * 实际费用页面重置功能
	 * @return
	 */
	@aPermission(name = { "实际费用明细" })
	@aDev(code = "luohong", email = "yixinggu123@163.com", name = "luohong")
	@aAuth(type = aAuthType.LOGIN)
	public Reply resetSifj() {
		// 从页面得到service方法将要使用的值PROJECT_ID 
		Map<String, Object> param = _getParameters();
		
		// 实例化service对象
		CustomersService service= new CustomersService();
		
		// 得到根据PROJECT_ID取得的FIL_PROJECT_FEEDETIL表对象
		List<Map<String,Object>> sifjMap =  service.getSifjForReset(param);
		
		// 根据是否得到对象判断重置是否失败
		JSONObject jsonObject = new JSONObject();
		if(null != sifjMap){
			// 将Map集合转换为JSON格式的对象
			jsonObject = JSONObject.fromObject(sifjMap);
			// 重置成功
			jsonObject.put("flag", true);
		}else{
			// 重置失败
			jsonObject.put("flag", false);
		}
		
		// 将JSON对象返回页面
		return new ReplyJson(jsonObject);
    }
	
	/**
	 * 请款申请 银行卡的录入
	 * 
	 * @author xingsumin 2015年12月16日17:58:15
	 */
	@aDev(code = "xingsumin", email = "suminxing@jiezhongchina.com", name = "邢素敏")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toModifyCustBank() {
		Map<String, Object> param = this.getPageParameter();
		CustomersService service = new CustomersService();
		VelocityContext context = new VelocityContext();
		if(param.get("PROJECT_ID") !=null && !param.get("PROJECT_ID").equals("")){
			String PROJECT_ID=param.get("PROJECT_ID").toString();
			//如果存在项目ID说明是通过项目修改过来的客户，则通过项目查询出客户信息替换过来的参数
			Map clientMap=service.queryNameAndCardNoByProject_id(PROJECT_ID);
			if(clientMap !=null){
				param.put("CLIENT_ID", clientMap.get("ID"));
				param.put("CLIENT_NAME", clientMap.get("NAME"));
				param.put("ID_CARD_NO", clientMap.get("ID_CARD_NO"));
				param.put("CUST_TYPE", clientMap.get("TYPE"));
			}
		}
		context.put("param", param);
		
		context.put("account_type",new projectService().queryAccountTypeCust(param));
		
		context.put("head_office", new DataDictionaryMemcached().get("开户行所属总行"));
		AreaService area = new AreaService();
		context.put("getProvince", area.getAllProvince());
		return new ReplyHtml(VM.html("payment/toModifyCustBank.vm", context));
	}

	@aDev(code = "xingsumin", email = "suminxing@jiezhongchina.com", name = "邢素敏")
	@aAuth(type = aAuthType.LOGIN)
	public Reply checkBlackCust() {
		CustomersService service = new CustomersService();
		Map<String, Object> param = _getParameters();
		boolean flag = false;
		if (param != null && !param.isEmpty()&& param.containsKey("ID_CARD_NO")) {
			int count = service.checkBlackCust(param);
			if (count>0) {
				flag = true;
			}
		}
		return new ReplyAjax(flag);
	}
	
}

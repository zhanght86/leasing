package com.pqsoft.weixin.action;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsp.weixin.ParamesAPI.util.WeixinJSUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.math.FinanceBean;
import com.pqsoft.skyeye.math.SkyEyeFinance;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User.Org;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.util.UtilDate;
import com.pqsoft.util.MyNumberTool;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.Util;
import com.pqsoft.weixin.service.BzManageService;
import com.pqsoft.weixin.utils.AppUtils.ClientType;

/**
 * NEW:
 * 产品推广
 * 租赁方案crud
 * @author LUYANFENG @ 2015年2月28日
 *
 */
public class FinancialSchemeAction extends AbstractWeiXinResponseAction {
	
	private MyNumberTool myNumberTool = new MyNumberTool();
	
	private projectService service = new projectService();
	private BzManageService bzSer = new BzManageService();
	
	
	double DB_BAIL_MONEY = 0;

	//-------------------------------------- start 方案推荐-------------------------------------------------------
	
	/**
	 * 方案
	 * 入口
	 * @author LUYANFENG @ 2015年4月1日
	 * @deprecated
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply schemeMain(){
		String contextPath = SkyEye.getRequest().getContextPath();
		try {
			SkyEye.getResponse().sendRedirect(contextPath+ "/weixin/FinancialScheme!whichWay.action?PGT=1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 选择个人还是企业
	 * @deprecated
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply whichWay(){
		VelocityContext vc = new VelocityContext();
		Map<String, Object> params = _getParameters();
		String PGT = (String) params.get("PGT"); // 立项方式
		vc.put("PGT", PGT);
		return new ReplyHtml(VM.html("weixin/financialScheme/whichWay.vm", vc ));
	}
	
	/**
	 * FIXME 微信回调有时会把参数搞没，可笑
	 *  入口 立项或修改时 自能选择
	 * 
	 * @author LUYANFENG @ 2015年4月1日
	 * @see zzNPMain() ， faLPMain() , faNPMain()
	 * @deprecated
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply startWhere(){
		Map<String, Object> _getParameters = _getParameters();
		String CT = (String) _getParameters.get("CT"); // 客户类型
		String PGT = (String) _getParameters.get("PGT");
		String PROJECT_ID = (String) _getParameters.get("PROJECT_ID");
		if(StringUtils.isBlank(CT)){
//			CT = "NP";
			throw new ActionException("操作错误:没有指定客户类型（CT）\n请关闭此页面");
		}
		if(StringUtils.isBlank(PGT)){
//			PGT = "1";
			throw new ActionException("操作错误：没有指定方案生成方式（PGT）\n请关闭此页面");
		}
		if(StringUtils.isBlank(PROJECT_ID)){
			PROJECT_ID = "";
//			throw new ActionException("操作错误");
		}
		String path = "";
		String contextPath = SkyEye.getRequest().getContextPath();
		if("NP".equals(CT)){
			if("0".equals(PGT)){
				path = "/weixin/FinancialScheme!recommend_3.action";
			}else{
				path = "/weixin/FinancialScheme!recommend.action";
			}
		}else{
			if("0".equals(PGT)){
				path = "/weixin/FinancialScheme!recommend_LP_3.action";
			}else{
				path = "/weixin/FinancialScheme!recommend_LP.action";
			}
		}
		try {
			SkyEye.getResponse().sendRedirect(contextPath + path+"?PROJECT_ID="+PROJECT_ID+"&PGT="+PGT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 个人资质入口
	 * @author LUYANFENG @ 2015年5月9日
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply zzNPMain(){
		String path = "/weixin/FinancialScheme!recommend_3.action";
		String contextPath = SkyEye.getRequest().getContextPath();
		try {
			HttpServletRequest request = SkyEye.getRequest();
			HttpServletResponse response = SkyEye.getResponse();
			SkyEye.getRequest().getRequestDispatcher(path+"?PGT=0").forward(request, response);
			
			/*
			 * FIXME 微信在回调zzNPMain 后，这里的redirect 在android 手机中参数会丢失？？
			 */
//			SkyEye.getResponse().sendRedirect(contextPath + path+"?PGT=0"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 个人方案入口
	 * @author LUYANFENG @ 2015年5月9日
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply faNPMain(){
		String path = "/weixin/FinancialScheme!recommend.action";
		String contextPath = SkyEye.getRequest().getContextPath();
		try {
			SkyEye.getResponse().sendRedirect(contextPath + path+"?PGT=1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 方案企业入口
	 * @author LUYANFENG @ 2015年5月9日
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply faLPMain(){
		String path = "/weixin/FinancialScheme!recommend_LP.action";
		String contextPath = SkyEye.getRequest().getContextPath();
		try {	
			SkyEye.getResponse().sendRedirect(contextPath + path+"?PGT=1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 *  客户类型：确定导航方式 PGT
	 * @param vm
	 * @param CLIENT_TYPE : NP| LP
	 */
	private void findWay(VelocityContext vm, Map<String, Object> paramMap , ClientType CLIENT_TYPE) {
		String PGT = (String) paramMap.get("PGT");  // 微信端：项目生成方式： 0 质资 ，1 方案
		String __PN = (String) paramMap.get("__PN"); // 导航第几页
		if(CLIENT_TYPE == null || StringUtils.isBlank(PGT)){
			Log.debug(" -----------PGT :"+ PGT );
			throw new ActionException("参数丢失，可能您操作时间太长，请关闭此页面。");
		}
		
		Integer __PNint = 0;
		if(StringUtils.isBlank(__PN)){
			__PN = "0";
			__PNint =  Integer.valueOf(__PN);
		}else{
			__PNint = Integer.valueOf(__PN) + 1;
			if(__PNint < 0)  __PNint= 0;
			if(__PNint > 3)  __PNint= 3; // 一最多就四步
		}
		if(StringUtils.isBlank(PGT)){ // 默认从方案生成
			PGT = "1";
		}
		vm.put("PGT", PGT);
		vm.put("__PN", __PNint );
		vm.put("CT", CLIENT_TYPE.toString());
		
		List<Map<String,Object>>   navList = new ArrayList<Map<String,Object>>();
		
		// 客户类型为：个人 的导航
		if( ClientType.NP == CLIENT_TYPE){ 
			if("0".equals(PGT)){ // 质资
				Map<String,Object> pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", "");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_4.action");
				navList.add(pageNAV); // 第一页 3
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_3.action");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend.action");
				navList.add(pageNAV); // 第二页 4
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_4.action");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_2_1.action");
				navList.add(pageNAV); // 第三页 1
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend.action");
				pageNAV.put("next_page", "");
				navList.add(pageNAV); // 第四页 2
			}else{	// 方案
				Map<String,Object> pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", "");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_2_1.action");
				navList.add(pageNAV); // 第一页
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend.action");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_3.action");
				navList.add(pageNAV); // 第二页
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_2_1.action");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_4.action");
				navList.add(pageNAV); // 第三页
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_3.action");
				pageNAV.put("next_page", "");
				navList.add(pageNAV); // 第四页
			}
		}else{// 客户类型为：企业的导航
			//目前质资没有企业方式
			if("0".equals(PGT)){ // 质资 
				Map<String,Object> pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", "");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_LP.action");
				navList.add(pageNAV); // 第一页 3
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_LP_3.action");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_LP_2_1.action");
				navList.add(pageNAV); // // 第二页 1
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_LP.action");
				pageNAV.put("next_page", "");
				navList.add(pageNAV); // 第三页 2
			}else{	// 方案
				Map<String,Object> pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", "");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_LP_2_1.action");
				navList.add(pageNAV); // 第一页 1
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_LP.action");
				pageNAV.put("next_page", "");
				navList.add(pageNAV); // 第二页 2
				
				/*pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_LP.action");
				pageNAV.put("next_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_LP_3.action");
				navList.add(pageNAV); // 第二页 2
				
				pageNAV = new HashMap<String,Object>();
				pageNAV.put("pre_page", SkyEye.getRequest().getContextPath()+"/weixin/FinancialScheme!recommend_LP_2_1.action");
				pageNAV.put("next_page","");
				navList.add(pageNAV); // 第三页 3
*/				
			}
		}
		
		vm.put("NAV", navList);
	}
	/**
	 * 更新项目时 取数据
	 * @param vm
	 * @param PROJECT_ID
	 */
	private void getUpData4ProSchemeCust(VelocityContext vm, String PROJECT_ID , ClientType clientType) {
		this.bzSer.checkProjectMan(PROJECT_ID);
		this.bzSer.checkProjectMan( PROJECT_ID );
		Integer status = Dao.selectOne("weixin_view.checkProjectStatus" , PROJECT_ID );
		if(status != 0){
			throw new ActionException("操作已过期");
		}
		
		
		Map<String,Object> eqMap = Dao.selectOne("weixin_view.select_eqMap_by_projectID",PROJECT_ID);
		Map<String,Object> custMap = null;
		if(clientType == null || clientType == ClientType.NP){
			custMap = Dao.selectOne("weixin_view.select_cust_by_projectID",PROJECT_ID);
		}else{
			custMap = Dao.selectOne("weixin_view.select_cust_LP_by_projectID",PROJECT_ID);
		}
		Map<String,Object> schemeMap = Dao.selectOne("weixin_view.select_scheme_by_projectID",PROJECT_ID);
		
		vm.put("cust_name", custMap.get("NAME"));
		vm.put("cust_phone", custMap.get("TEL_PHONE"));
		if(StringUtils.isBlank( (String)custMap.get("TEL_PHONE") )){
			vm.put("cust_phone" ,custMap.get("LEGAL_PERSON_PHONE") );
		}
		
		Map<String,Object> json1Map = new HashMap<String,Object>();
		json1Map.put("brand_id", eqMap.get("PRODUCT_ID"));
		json1Map.put("car_brand_name", eqMap.get("PRODUCT_NAME"));
		json1Map.put("car_series_id", eqMap.get("CATENA_ID"));
		json1Map.put("car_series_name", eqMap.get("CATENA_NAME"));
		json1Map.put("car_model_id", eqMap.get("SPEC_ID"));
		json1Map.put("car_model_name", eqMap.get("SPEC_NAME"));
		json1Map.put("car_price", eqMap.get("UNIT_PRICE"));
		json1Map.put("SUP_NAME", eqMap.get("SUPPLIERS_NAME"));
		json1Map.put("SUP_ID", eqMap.get("SUPPLIERS_ID"));
		json1Map.put("COMPANY_NAME", eqMap.get("COMPANY_NAME"));
		json1Map.put("COMPANY_ID", eqMap.get("COMPANY_ID"));
		
		json1Map.put("CLIENT_ID", custMap.get("CUST_ID"));
		json1Map.put("CLIENT_NAME", custMap.get("NAME"));
		json1Map.put("CLIENT_MOBILE", custMap.get("TEL_PHONE"));		// 个人
		json1Map.put("LEGAL_PERSON_PHONE", custMap.get("LEGAL_PERSON_PHONE")); // 法人
		json1Map.put("CLIENT_TYPE", custMap.get("TYPE"));
		
		
		json1Map.put("PROJECT_ID", PROJECT_ID);
		
		vm.put("json1", JSONObject.fromObject(json1Map).toString());
		vm.put("requestBody1", json1Map);
		
		
		Map<String,Object> json2Map = new HashMap<String,Object>();
		
		json2Map.put("START_PERCENT", schemeMap.get("START_PERCENT"));
		json2Map.put("PAY_WAY", schemeMap.get("PAY_WAY"));
		json2Map.put("LEASE_PERIOD", schemeMap.get("LEASE_PERIOD"));
		json2Map.put("LEASE_TERM", schemeMap.get("LEASE_TERM"));
		json2Map.put("FINANCE_TOPRIC", schemeMap.get("FINANCE_TOPRIC"));
		json2Map.put("YEAR_INTEREST", schemeMap.get("YEAR_INTEREST"));
		json2Map.put("LAST_MONEY", schemeMap.get("LAST_MONEY"));
		json2Map.put("FIRSTPAYALL", schemeMap.get("FIRSTPAYALL"));
		json2Map.put("FIRST_MONEY", schemeMap.get("FIRST_MONEY"));
		json2Map.put("DISCOUNT_MONEY", schemeMap.get("DISCOUNT_MONEY"));
		json2Map.put("SCHEME_CODE", schemeMap.get("SCHEME_CODE"));
		json2Map.put("SCHEME_NAME", schemeMap.get("SCHEME_NAME"));
		json2Map.put("MONTH_RENT", schemeMap.get("MONTH_RENT"));	// 月供
		json2Map.put("DEPOSIT_MONEY", schemeMap.get("DEPOSIT_MONEY"));// 保证金
		json2Map.put("POUNDAGE_PRICE", schemeMap.get("POUNDAGE_PRICE"));  //手续费金额
		json2Map.put("PLATFORM_TYPE", schemeMap.get("PLATFORM_TYPE")); // 业务类型
		Object SCHEME_CLOB = schemeMap.get("SCHEME_CLOB");
		String SCHEME_CLOB_Str = "";
		 if(SCHEME_CLOB != null){
			 if(SCHEME_CLOB instanceof Clob){
				Clob  c =  (Clob)SCHEME_CLOB ;
				 try {
					Reader cs = c.getCharacterStream();
					 char[] cc = new char[(int) c.length()];
					 cs.read(cc);
					 SCHEME_CLOB_Str = new String(cc);
					 cs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			 }
		 }
		 
		 JSONArray scheme_clob_json = JSONArray.fromObject(SCHEME_CLOB_Str);
		 if(scheme_clob_json != null){
			 for(ListIterator listIterator = scheme_clob_json.listIterator(); listIterator.hasNext(); listIterator.next()){
				 JSONObject jsonObject = scheme_clob_json.getJSONObject(listIterator.nextIndex() );
				 String key_name_en = (String) jsonObject.get("KEY_NAME_EN");
				 if("INSURANCE_MONEY".equals( key_name_en )){
					 Object val = jsonObject.get("VALUE_STR");
					 json2Map.put("INSURANCE_MONEY", StringUtils.isBlank((String)val) ? 0 : val); 
					 continue;
				 }
				 if("CCS_MONEY".equals( key_name_en )){
					 Object val = jsonObject.get("VALUE_STR");
					 json2Map.put("CCS_MONEY", StringUtils.isBlank((String)val) ? 0 : val); 
					 continue;
				 }
				 if("GZS_MONEY".equals( key_name_en )){
					 Object val = jsonObject.get("VALUE_STR");
					 json2Map.put("GZS_MONEY", StringUtils.isBlank((String)val) ? 0 : val); 
					 continue;
				 }
				 if("CBL".equals( key_name_en )){
					 Object val = jsonObject.get("VALUE_STR");
					 json2Map.put("CBL", StringUtils.isBlank((String)val) ? 0 : val); 
					 continue;
				 }
			 }
		 }
		
		//leaseTerm = Integer.valueOf(year) * 12 / lease_period;
		Object LEASE_PERIOD = schemeMap.get("LEASE_PERIOD");
		Integer lease_period_str =  0;
		if(LEASE_PERIOD != null){
			lease_period_str = Integer.valueOf(LEASE_PERIOD.toString()) ;
		}
		Object LEASE_TERM = schemeMap.get("LEASE_TERM");
		Integer lease_term_str = 0;
		if(LEASE_TERM != null){
			lease_term_str = Integer.valueOf(LEASE_TERM.toString()) ;
		}
		json2Map.put("date", lease_period_str* lease_term_str / 12 ); 
		
		vm.put("json2", JSONObject.fromObject(json2Map).toString());
//			vm.put("requestBody2", json2Map);
		
		
		Map<String,Object> json3Map = new HashMap<String,Object>();
		json3Map.put("CLIENT_NAME", custMap.get("NAME") );
		json3Map.put("CLIENT_MOBILE", custMap.get("TEL_PHONE"));
		json3Map.put("SEX", custMap.get("SEX"));
		json3Map.put("AGE", custMap.get("AGE"));
		json3Map.put("IS_MARRY", custMap.get("IS_MARRY"));
		json3Map.put("DEGREE_EDU",  custMap.get("DEGREE_EDU"));
		json3Map.put("HUKOUXINGZHI", custMap.get("HUKOUXINGZHI"));
		json3Map.put("BUSINESS_TYPE", custMap.get("BUSINESS_TYPE"));
		json3Map.put("AGE", custMap.get("AGE"));
		json3Map.put("PROJECT_ID", PROJECT_ID);
		//BUSINESS_TYPE
		vm.put("json3", JSONObject.fromObject(json3Map).toString());
		vm.put("requestBody3", json3Map);
		
		
		
		Map<String,Object> json4Map = new HashMap<String,Object>();
		json4Map.put("CLIENT_NAME", custMap.get("NAME") );
		json4Map.put("CLIENT_MOBILE", custMap.get("TEL_PHONE"));
		json4Map.put("ENTERPRISE_QY", custMap.get("ENTERPRISE_QY"));
		json4Map.put("EXPERIENCE_GZ", custMap.get("EXPERIENCE_GZ"));
		json4Map.put("SQSHNSR_GR", custMap.get("SQSHNSR_GR"));
		json4Map.put("LIVING_LIFE", custMap.get("LIVING_LIFE"));
		json4Map.put("XYCLQK", custMap.get("XYCLQK"));
		json4Map.put("HOME_XZ", custMap.get("HOME_XZ"));
		
		vm.put("json4", JSONObject.fromObject(json4Map).toString());
//			vm.put("requestBody4", json4Map);
	}
	
	/**
	 * 方案推荐1
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend(){
		VelocityContext vm = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		
		this.findWay(vm, _getParameters, ClientType.NP);
		
		String PROJECT_ID = (String) _getParameters.get("PROJECT_ID"); //pro_id
		if(StringUtils.isNotBlank(PROJECT_ID)){
			this.getUpData4ProSchemeCust(vm, PROJECT_ID, ClientType.NP);
		}else{
			this.serializeJsons(vm, _getParameters);
			Object cust_name = _getParameters.get("cust_name");
			Object cust_phone = _getParameters.get("cust_phone");
			vm.put("cust_name", cust_name);
			vm.put("cust_phone", cust_phone);
		}
		
		
		
		this.setOrg2Page(vm);
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		vm.put("pageTag", "1"); // 页面标记用于 form_param.vmf中的js处理
		return new ReplyHtml(VM.html("/weixin/financialScheme/recommend.vm", vm ));
	}

	// 是经销商还是渠道商
	private void setOrg2Page(VelocityContext vm) {
		Org org = Security.getUser().getOrg();
		List<Object> supList = new ArrayList<Object>();
		if(org == null){
			vm.put("myID", "");
		}else{
			if(StringUtils.isNotBlank(org.getSpId() ) ){ // 查渠道下的经销商
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("SP_ID", org.getSpId() );
				supList = Dao.selectList("weixin_view.select_sup_in_sp",param );
				vm.put("supList", supList);
				vm.put("myID",  "sp");
			}else if( StringUtils.isNotBlank(org.getSuppId() ) ){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("SUP_ID", org.getSuppId());
				map.put("SUP_NAME", org.getSuppName());
				supList.add(map);
				vm.put("supList", supList);
				vm.put("myID",  "sup");
			}else{
				vm.put("myID", "");
			}
		}
	}
	
	
	/**
	 * 方案推荐2
	 * 第二页 
	 * 
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_2_1(){
		VelocityContext vm = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		this.findWay(vm, _getParameters ,ClientType.NP);
		
		Object cust_name = _getParameters.get("cust_name");
		Object cust_phone = _getParameters.get("cust_phone");
		vm.put("cust_name", cust_name);
		vm.put("cust_phone", cust_phone);
		this.serializeJsons(vm, _getParameters);
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		
		vm.put("pageTag", "2"); // 页面标记用于 form_param.vmf中的js处理
		return new ReplyHtml(VM.html("/weixin/financialScheme/recommend_2_1.vm", vm ));
	}
	/**
	 * 方案推荐3 （承租人信息：个人基本信息 | 企业）
	 * 第三页
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_3(){
		VelocityContext vm = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		this.findWay(vm, _getParameters ,ClientType.NP);
		
		String PROJECT_ID = (String) _getParameters.get("PROJECT_ID");
		if(StringUtils.isNotBlank(PROJECT_ID)){
			this.getUpData4ProSchemeCust(vm, PROJECT_ID ,ClientType.NP);
		}else{
			this.serializeJsons(vm, _getParameters);
			Object cust_name = _getParameters.get("cust_name");
			Object cust_phone = _getParameters.get("cust_phone");
			vm.put("cust_name", cust_name);
			vm.put("cust_phone", cust_phone);
		}
		
		
		
		List<Object> MARRY = (List) new DataDictionaryMemcached().get("婚姻状况");
		vm.put("MARRY", MARRY);
		List<Object> degree_edu = (List) new DataDictionaryMemcached().get("文化程度");
		vm.put("degree_edu", degree_edu);
		List<Object> HKXZ = (List) new DataDictionaryMemcached().get("户口性质");
		vm.put("HKXZ", HKXZ);
		
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		vm.put("pageTag", "3"); // 页面标记用于 form_param.vmf中的js处理
		return new ReplyHtml(VM.html("/weixin/financialScheme/recommend_3.vm", vm ));
	}
	/**
	 * 方案推荐4 （承租人信息2：个人基本信息 | 企业）
	 * 第四页
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_4(){
		VelocityContext vm = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		this.findWay(vm, _getParameters ,ClientType.NP);
		
		Object cust_name = _getParameters.get("cust_name");
		Object cust_phone = _getParameters.get("cust_phone");
		vm.put("cust_name", cust_name);
		vm.put("cust_phone", cust_phone);
		this.serializeJsons(vm, _getParameters);
		
		List<Object> GSXZ = (List) new DataDictionaryMemcached().get("公司性质");
		vm.put("GSXZ", GSXZ);
		List<Object> FWXZ = (List) new DataDictionaryMemcached().get("房屋性质");
		vm.put("FWXZ", FWXZ);
		List<Object> JZNX = (List) new DataDictionaryMemcached().get("现居住年限");
		vm.put("JZNX", JZNX);
		List<Object> XYCLQK = (List) new DataDictionaryMemcached().get("现有车辆情况");
		vm.put("XYCLQK", XYCLQK);
		
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		
		vm.put("pageTag", "4"); // 页面标记用于 form_param.vmf中的js处理
		return new ReplyHtml(VM.html("/weixin/financialScheme/recommend_4.vm", vm ));
	}

	private void serializeJsons(VelocityContext vm, Map<String, Object> _getParameters) {
		String json1 = (String) _getParameters.get("json1");
		String json2 = (String) _getParameters.get("json2");
		String json3 = (String) _getParameters.get("json3");
		String json4 = (String) _getParameters.get("json4");
		if(StringUtils.isNotBlank(json1)){
			vm.put("json1", json1);
			JSONObject requestBody1 = JSONObject.fromObject(json1);
			vm.put("requestBody1", requestBody1);
		}
		if(StringUtils.isNotBlank(json2)){
			vm.put("json2", json2);
			JSONObject requestBody2 = JSONObject.fromObject(json2);
			vm.put("requestBody2", requestBody2);
		}
		if(StringUtils.isNotBlank(json3)){
			vm.put("json3", json3);
			JSONObject requestBody3 = JSONObject.fromObject(json3);
			vm.put("requestBody3", requestBody3);
		}
		if(StringUtils.isNotBlank(json4)){
			vm.put("json4", json4);
			JSONObject requestBody4 = JSONObject.fromObject(json4);
			vm.put("requestBody4", requestBody4);
		}
	}
	
	
	
	/**
	 * 推荐方案：选择品牌
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_brand(){
		Map<String, Object> _getParameters = _getParameters();
		// 厂商
		String SUPPLIER_ID = (String) _getParameters.get("SUPPLIER_ID");
		List<Map<String, Object>> queryCompanyBySupId = new CompanyService().queryCompanyBySupId( SUPPLIER_ID, Security.getUser().getOrg().getPlatformId());
//		JSONArray companyJson = JSONArray.fromObject( queryCompanyBySupId );
		Set<String> companyIDs = new HashSet<String>();
		if(queryCompanyBySupId != null && !queryCompanyBySupId.isEmpty()){
			for(Map<String, Object> m : queryCompanyBySupId){
				String COMPANY_ID = m.get("COMPANY_ID").toString();
				companyIDs.add(COMPANY_ID );
			}
		}
		
		VelocityContext vm = new VelocityContext();
		if(companyIDs != null &&  !companyIDs.isEmpty()){
			//查车品牌
			Map<String,Object > params = new HashMap<String,Object>();
			params.putAll(_getParameters);
			params.put("magic", this.oracle_magic); // 排序用
			params.put("companyIDs", companyIDs);
			Page page = PageUtil.getPage(params , "weixin_view.select_brand", "weixin_view.select_brand_count");
			vm.put("page", page);
			vm.put("HanYuPinYin",  new com.pqsoft.util.StringUtils() ); // 工具
		}
		return new ReplyHtml(VM.html("weixin/financialScheme/recommend_brand.vm", vm ));
	}
	/**
	 * 推荐方案：选择车系
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_series(){
		Map<String, Object> _getParameters = _getParameters();
		String brand_id = (String) _getParameters.get("brand_id");
		Set<String> companyIDs = new HashSet<String>();
		// 厂商
		/*String SUPPLIER_ID = (String) _getParameters.get("SUPPLIER_ID");
		List<Map<String, Object>> queryCompanyBySupId = new CompanyService().queryCompanyBySupId( SUPPLIER_ID, Security.getUser().getOrg().getPlatformId());
//		JSONArray companyJson = JSONArray.fromObject( queryCompanyBySupId );
		
		if(queryCompanyBySupId != null && !queryCompanyBySupId.isEmpty()){
			for(Map<String, Object> m : queryCompanyBySupId){
				String COMPANY_ID = m.get("COMPANY_ID").toString();
				companyIDs.add(COMPANY_ID );
			}
		}*/
		
		VelocityContext vm = new VelocityContext();
		//查车品牌
		Map<String,Object > params = new HashMap<String,Object>();
		params.putAll(_getParameters);
		params.put("magic", this.oracle_magic); // 排序用
		params.put("companyIDs", companyIDs);
		params.put("brand_id", brand_id);
		Page page = PageUtil.getPage(params , "weixin_view.select_seies", "weixin_view.select_seies_count");
		vm.put("page", page);
		vm.put("HanYuPinYin",  new com.pqsoft.util.StringUtils() ); // 工具
		return new ReplyHtml(VM.html("weixin/financialScheme/recommend_series.vm", vm ));
	}
	
	
	/**
	 * 推荐方案：选车型
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_car(){
		VelocityContext vm = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		String brand_id = (String) _getParameters.get("brand_id");
		String car_series_id = (String) _getParameters.get("car_series_id");
		vm.put("brand_id",brand_id);
		vm.put("car_series_id",car_series_id);
		if(StringUtils.isBlank(brand_id)){
			throw new ActionException("品牌无效");
		}
		if(StringUtils.isBlank(car_series_id)){
			throw new ActionException("车系无效");
		}
		
		
		//查车系 车型
		_getParameters.put("magic",  this.oracle_magic);
		Page page = PageUtil.getPage(_getParameters, "weixin_view.select_car_series_model", "weixin_view.select_car_series_model_count");
		vm.put("page", page);
		
		vm.put("MyNumberTool", new MyNumberTool());
		vm.put("HanYuPinYin",  new com.pqsoft.util.StringUtils() ); // 工具
		return new ReplyHtml(VM.html("weixin/financialScheme/recommend_car.vm", vm ));
	}
	
	/**
	 * 推荐方案：选车系
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_series_json(){
		Map<String, Object> ms = _getParameters();
		String page = (String) ms.get("page");
		String rows = (String) ms.get("rows");
		String brand_id = (String) ms.get("brand_id");
		
		ms.clear();
		ms.put("brand_id",brand_id);
		ms.put("rows",StringUtils.isBlank(rows)? "20": rows); // 下拉分页
		ms.put("page",StringUtils.isBlank(page)? "1" : page); // 下拉分页
		
		if(StringUtils.isBlank(brand_id)){
			throw new ActionException("品牌无效");
		}
		//查车系 车型
		ms.put("magic",  this.oracle_magic);
		Page page_ = PageUtil.getPage(ms, "weixin_view.select_car_series_model", "weixin_view.select_car_series_model_count");
		JSONObject json = new JSONObject();
		json.put("data", page_);
		return new ReplyJson(json);
	}
	
	/**
	 * 选择微信自己的产品方案： 选择方案
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_scheme(){
		Map<String, Object> map = _getParameters();
		
		



		String __GZS_MONEY = (String) map.get("GZS_MONEY");				// 购置税
		String __INSURANCE_MONEY = (String) map.get("INSURANCE_MONEY"); // 保险费
		String __CCS_MONEY = (String) map.get("CCS_MONEY");				// 车船费
		String __START_PERCENT = (String) map.get("START_PERCENT");		// 利率
		
		
		String company_id = (String) map.get("company_id");				// 车价
		String supplier_id = (String) map.get("supplier_id");				// 车价
		String car_price = (String) map.get("car_price");				// 车价
		String brand_id = (String) map.get("brand_id");				// PRODUCT_ID
		String car_series_id = (String) map.get("car_series_id");		// CATENA_ID
		String car_model_id = (String) map.get("car_model_id");		// PRODUCT_TYPE_ID
		String year = (String) map.get("year");							// 年限
		if(StringUtils.isBlank(brand_id)
				&& StringUtils.isBlank(car_series_id)
				&& StringUtils.isBlank(car_model_id)
				&& StringUtils.isBlank(car_price)
				&& StringUtils.isBlank(year)
				){
			throw new ActionException("需要指定品牌、车系、车型、年限");
		}
		
		if(StringUtils.isBlank(__START_PERCENT)){
			String str = "此方案(首期比例规则外)不符合您的租赁计划";
			throw new ActionException( str);
		}
		{
			if(StringUtils.isNotBlank(__GZS_MONEY)){
				__GZS_MONEY = this.clearFormat(__GZS_MONEY);
			}
			if(StringUtils.isNotBlank(__INSURANCE_MONEY)){
				__INSURANCE_MONEY = this.clearFormat(__INSURANCE_MONEY);
			}
			if(StringUtils.isNotBlank(__CCS_MONEY)){
				__CCS_MONEY = this.clearFormat(__CCS_MONEY);
			}
			if(StringUtils.isNotBlank(car_price)){
				car_price = this.clearFormat(car_price);
			}
		}
		VelocityContext vm = new VelocityContext();
		
		vm.put("t",map.get("t"));
		BaseSchemeService bss = new BaseSchemeService();
		Map<String,Object> qryMap =  new HashMap<String,Object>();
		// 查经销商所关联的sp（不止一个）
		{
			Set<String> spid = new HashSet<String>();
			Map<String,Object> qryMap_ = new HashMap<String,Object>();
			qryMap_.put("sup_id", supplier_id);
			List<Map<String,Object>> spids = Dao.selectList("weixin_view.getSPsbySUP", qryMap_);
			if(spids != null && !spids.isEmpty()){
				for(Map<String,Object> m : spids){
					spid.add(m.get("SUP_ID").toString());
				}
			}
			qryMap.put("SP_IDS", spid);
		}
		String supGroup = Dao.selectOne("baseScheme.getSUPG4SUP", supplier_id);
		
		qryMap.put("SUPPLIER_GROUP", supGroup);
		qryMap.put("COMPANY_ID", company_id);
		qryMap.put("SUPPLIER_ID", supplier_id);
		qryMap.put("PRODUCT_ID", brand_id);
		qryMap.put("CATENA_ID", car_series_id);
		qryMap.put("PRODUCT_TYPE_ID", car_model_id);
//		qryMap.put("PAGE_SIZE", "3");
//		qryMap.put("START_NUM", "0");
		List<Map<String, Object>> allSchemeType = bss.querySchemeInfoList(qryMap, "WX");	// 所有方案配置
		List<Map<String, Object>> pageData = new ArrayList<Map<String, Object>>(); // 页面有效的方案
		List<Map<String, Object>> pageErrData = new ArrayList<Map<String, Object>>(); // 页面无效的方案
		
		
		
		for(Map<String, Object> oneSchemeType : allSchemeType){
			DB_BAIL_MONEY = 0; // 经销商保证金 归零
			int lease_period = 1;
			double finance_topric = 0; 		// 融资额 
			double last_money = 0;			// 尾款
			int isFirst = 0;
			double discount_money = 0;		// 贴息金额
			int pay_way_code  = 0;
			double first_money = 0;			// 首期租金S TART_MONEY （不是首付款）
			double firstpayall = 0;			// 首付款
			double deposit_money = 0; 		// 保证金
			double zj = 0;
			double CBL = 0;					// 成本率 （利息合计+手续费）/融资额)/年限
			double poundage_rate = 0; 		// 手续费率
			double poundage_price = 0;		// 手续费 = poundage_rate *  finance_topric / 100
			int management_feetype = 1;		// 手续费收取方式
			double gps_price = 0; 			// gps 费用
			double year_interest = 0;		// 年利率
			
			double cs_glf = 0;				// 测算表上加的
			double cs_sxf = 0;				//  测算表上加的
			
			
			first_money = Double.valueOf(car_price) * Double.valueOf(__START_PERCENT) /100;
			finance_topric = Double.valueOf(car_price) - first_money;
			
			
			
			String scheme_code = (String) oneSchemeType.get("SCHEME_CODE");
			if(StringUtils.isBlank(scheme_code)){
				String str = "找不到金融产品对应的 SCHEME_CODE";
				Log.debug("@@@ "+ str );
				oneSchemeType.put("errorInfo", str);
				pageErrData.add(oneSchemeType);
				continue;
			}
			
			Map<String,Object> param = new HashMap<String,Object>();
			{// 取起租比例 
				param.clear();
				param.put("SCHEME_CODE", scheme_code);
				param.put("KEY_NAME_EN", "START_PERCENT");
				param.put("STATUS", "0");
				Map<String,Object> startPercentMap = Dao.selectOne("weixin_view.select_base_scheme", param);
				if(startPercentMap != null && !startPercentMap.isEmpty()){
					double VALUE_DOWN = (startPercentMap.get("VALUE_DOWN") == null ? 0 : Double.valueOf(startPercentMap.get("VALUE_DOWN").toString() ) );
					double VALUE_UP = (startPercentMap.get("VALUE_UP") == null ? 100 : Double.valueOf(startPercentMap.get("VALUE_UP").toString() ) );
					if(VALUE_UP < Double.valueOf(__START_PERCENT) || VALUE_DOWN > Double.valueOf(__START_PERCENT)){
						String str = "给定的首期比例超出方案规则";
						Log.debug("@@@ "+str);
						oneSchemeType.put("errorInfo", str);
						pageErrData.add(oneSchemeType);
						continue;
					}
					
				}
			}
			
			
			
			try {

				param.clear();
				param.put("SCHEME_CODE", scheme_code);
				param.put("KEY_NAME_EN", "LEASE_PERIOD");
				param.put("STATUS", "0");
				Map<String,Object> periodMap = Dao.selectOne("weixin_view.select_base_scheme", param);
				String period = (String) periodMap.get("VALUE_STR");
				if(StringUtils.isNotBlank(period)){
					lease_period = Integer.valueOf(period);
				}
			} catch (Exception e) {
				lease_period = 1;
			}
			// 参数2
			int leaseTerm = Integer.valueOf(year) * 12 / lease_period;
			
			param.clear();
			param.put("SCHEME_CODE", scheme_code);
			param.put("monthAll",  leaseTerm );
			param.put("START_PERCENT", __START_PERCENT);
			
			
			Map<String, Object> yearRateMap = null;
			List<Map<String, Object>> yearRateMaps = Dao.selectList("baseScheme.queryYearRateBySchem", param);
			if(yearRateMaps != null && !yearRateMaps.isEmpty()){
				yearRateMap = yearRateMaps.get(0);
				Object YEAR_RATE = yearRateMap.get("YEAR_RATE");
				year_interest = Double.valueOf(YEAR_RATE == null ? "0" : YEAR_RATE.toString());
				
				
				Object GPS_PRICE = yearRateMap.get("GPS_PRICE");
				if(GPS_PRICE != null){ gps_price = Double.valueOf(GPS_PRICE.toString()); }
				String gps_calculate = (String)yearRateMap.get("CALCULATE");
				if("JRRZE".equals(gps_calculate)){
					finance_topric += gps_price;
				}else if("JRSQK".equals(gps_calculate)){
					firstpayall += gps_price;
				}else if("JRWK".equals(gps_calculate)){
					last_money += gps_price;
				}
			}
			if(yearRateMap == null || yearRateMap.isEmpty()){
				String str = "以给定的期数+起租比例无法找到符合的年利率";
				Log.debug("@@@ "+str);
				oneSchemeType.put("errorInfo", str);
				pageErrData.add(oneSchemeType);
				continue;
			}
			
			
			// 计入融资额的 FYGS
			//1、查出方案配置
			param.clear();
			param.put("SCHEME_CODE", scheme_code);
			param.put("STATUS", "0");
			List<Map<String,Object>> schemeOpt_ = Dao.selectList("weixin_view.getBaseSchemeConfig", param);
			// 包装好给页面
			Map<String,Object> myData = new HashMap<String,Object>();
			
			// 1、初步计算融资额 **注意，这个一定要先算出来
			finance_topric = this.calcRZE_WK_SQK(schemeOpt_, car_price , "JRRZE", finance_topric, firstpayall,first_money , last_money ,__INSURANCE_MONEY ,__GZS_MONEY , __CCS_MONEY);
			// 2、初步计算首付款
			firstpayall = this.calcRZE_WK_SQK(schemeOpt_, car_price , "JRSQK", finance_topric , firstpayall,first_money, last_money ,__INSURANCE_MONEY ,__GZS_MONEY , __CCS_MONEY);
			// 3、 计算尾款
			last_money = this.calcRZE_WK_SQK(schemeOpt_, car_price , "JRWK", finance_topric , firstpayall,first_money, last_money ,__INSURANCE_MONEY ,__GZS_MONEY , __CCS_MONEY);
			
			// 4、计算其它
			FinanceBean fbean = new FinanceBean();
			for(Map<String, Object> item_ : schemeOpt_){
				String FYGS = (String) item_.get("FYGS");	
				String CALCULATE = (String) item_.get("CALCULATE");	
				String VALUE_STR = (String) item_.get("VALUE_STR");
				String PRECENTE_VAL = (String) item_.get("PRECENTE_VAL");
				String PRECENTE_VAL_DOWN = (String) item_.get("PRECENTE_VAL_DOWN");
//				String PRECENTE_VAL_UP = (String) item_.get("PRECENTE_VAL_UP");
				String KEY_NAME_EN = (String) item_.get("KEY_NAME_EN");
				String KEY_NAME_ZN = (String) item_.get("KEY_NAME_ZN");
				String VALUE_STATUS = (String) item_.get("VALUE_STATUS");
				String KEY_NAME_EN_PRECENTE = (String) item_.get("KEY_NAME_EN_PRECENTE");
				
				if(StringUtils.isBlank(VALUE_STR) && StringUtils.isNotBlank(KEY_NAME_EN_PRECENTE)){
					if(StringUtils.isBlank(PRECENTE_VAL_DOWN)){
						PRECENTE_VAL_DOWN = "0";
					}
					if(PRECENTE_VAL != null && !PRECENTE_VAL.toString().equals("")){
						if("SBK".equals(CALCULATE)){
							double s = Double.valueOf(PRECENTE_VAL) / 100 * Double.valueOf(car_price);
							VALUE_STR = s + "";
						}else{ 
							double s = Double.valueOf(PRECENTE_VAL) / 100 * (finance_topric); 
							VALUE_STR = s + "";
						}
					}else {
						VALUE_STR = Double.valueOf(PRECENTE_VAL_DOWN) / 100  * Double.valueOf(car_price) + "";
					}
					
				}
				
				
				if(StringUtils.isNotBlank(VALUE_STR)){
					if("DEPOSIT_MONEY".equals(KEY_NAME_EN) ){ //客户保证金
						deposit_money = Double.valueOf(VALUE_STR);
					}
					if("LAST_MONEY".equals(KEY_NAME_EN) ){ 	//尾款金额
						last_money = Double.valueOf(VALUE_STR);
					}
					if("DISCOUNT_MONEY".equals(KEY_NAME_EN)){ 	//贴息金额 DISCOUNT_PERCENT
						discount_money = Double.valueOf(VALUE_STR);
					}
					
					if("INSURANCE_MONEY".equals(KEY_NAME_EN)){ 	//保险费
						if(!"1".equals( VALUE_STATUS) && StringUtils.isNotBlank(__INSURANCE_MONEY) ){
							VALUE_STR = __INSURANCE_MONEY ;
						}
						myData.put("INSURANCE_MONEY",VALUE_STR);
					}
					if("GZS_MONEY".equals(KEY_NAME_EN)){ 	//购置税
						if(!"1".equals( VALUE_STATUS) && StringUtils.isNotBlank(__GZS_MONEY)){
							VALUE_STR = __GZS_MONEY ;
						}
						myData.put("GZS_MONEY",VALUE_STR);
					}
					if("CCS_MONEY".equals(KEY_NAME_EN)){ 	//车船税
						if(!"1".equals( VALUE_STATUS) && StringUtils.isNotBlank(__CCS_MONEY)){
							VALUE_STR = __CCS_MONEY ;
						}
						myData.put("CCS_MONEY",VALUE_STR);
					}
					if("PAY_WAY".equals(KEY_NAME_EN)){ 	//支付方式
						Object object = item_.get("CODE") ;
						if(object != null){
							pay_way_code = Integer.valueOf( (String)object );
							String payWay = VALUE_STR;
							if(StringUtils.isNotBlank(payWay)){
								isFirst = payWay.startsWith("期初") ? 1 : 0; //是否是期初
							}
						}
						continue;
					}
					
					// start  add at 2015-7-20 by luyanfeng
					if("BEFORETHREEPERCENT".equals(KEY_NAME_EN)){
						fbean.setBeforeThreePercent(Double.parseDouble(VALUE_STR) );
					}
					if("ISNOPAYFORNEWYEAR".equals(KEY_NAME_EN)){
						fbean.setNoPayForNewYear(Boolean.parseBoolean(VALUE_STR));
					}
					if("ROUND_UP_TYPE".equals(KEY_NAME_EN)){
						 fbean.setROUND_UP_TYPE(Integer.parseInt( getFieldValueCode(scheme_code, VALUE_STR, KEY_NAME_ZN)) );
					}
					if("SCALE".equals(KEY_NAME_EN)){// 租金保留位数
						 fbean.setScale(Integer.parseInt(getFieldValueCode(scheme_code, VALUE_STR, KEY_NAME_ZN)) );
					}
					//end add at 2015-7-20 by luyanfeng
					if("ISTAX".equals(KEY_NAME_EN)){
						fbean.setIsTAX(VALUE_STR );
					}
					if("MQGLF".equals(KEY_NAME_EN)){
						fbean.setMQGLF(Double.parseDouble(VALUE_STR ));
					}
					if ("LXTQSQ".equals(KEY_NAME_EN)){
						fbean.setLXTQSQ(VALUE_STR);
					}
					// add at 2015-8-26 by luyanfeng
					if ("LEASE_PERIOD".equals(KEY_NAME_EN)){
						fbean.setLeasePeriod(Integer.parseInt(VALUE_STR));
					}
					if ("LEASE_TERM".equals(KEY_NAME_EN)){
						fbean.setLeaseTerm(Integer.parseInt(VALUE_STR));
					}
					//end add at 2015-8-26 by luyanfeng
				}//end if
			}
			
			// 这三个只页面用
			if( !myData.containsKey("GZS_MONEY")) myData.put("NO_GZS_MONEY", "1");
			if( !myData.containsKey("CCS_MONEY")) myData.put("NO_CCS_MONEY", "1");
			if( !myData.containsKey("INSURANCE_MONEY")) myData.put("NO_INSURANCE_MONEY", "1");
			
			{// 查手续费
				BaseSchemeService baseSchemeService = new BaseSchemeService();
				param.clear();
				param.put("monthAll", leaseTerm);
				param.put("SCHEME_CODE", scheme_code);
				@SuppressWarnings("rawtypes")
				Map queryFeeBySchem = baseSchemeService.queryFeeBySchem(param);
				//SQFS  == MANAGEMENT_FEETYPE
				// FEE_RATE 手续费率
				// FEES_PRICE 手续费
				if(queryFeeBySchem != null && !queryFeeBySchem.isEmpty()){
					Object FEE_RATE = queryFeeBySchem.get("FEE_RATE");
					if(FEE_RATE != null){
						Double fee_rate = Double.valueOf(FEE_RATE.toString());
						poundage_rate = fee_rate;
						poundage_price = fee_rate * Double.valueOf(finance_topric) / 100;
					}
					management_feetype = queryFeeBySchem.get("SQFS") == null? 1: Integer.valueOf(queryFeeBySchem.get("SQFS").toString());
					if(management_feetype == 1){
						firstpayall += poundage_price;
					}else if( management_feetype == 3){
						cs_sxf = poundage_price / leaseTerm;
						cs_sxf = Double.valueOf(myNumberTool.number(cs_sxf, 2, 2).replaceAll(",",""));
					}
					
					fbean.setFEES_PRICE(poundage_price );
					fbean.setMANAGEMENT_FEETYPE(management_feetype+"" );
				}
				
			}
			{ // 管理费
				Map<String,Object> schemeJson = new HashMap<String,Object>();
				schemeJson.put("SCHEME_CODE",  scheme_code);
				schemeJson.put("KEY_NAME_EN",  "MQGLF");
				schemeJson.put("STATUS",  "0");
				Map<String,Object> mqglfMap = Dao.selectOne("weixin_view.select_base_scheme", schemeJson);
				if(mqglfMap != null && !mqglfMap.isEmpty()){
					String VALUE_STR = (String) mqglfMap.get("VALUE_STR");
					if(StringUtils.isNotBlank(VALUE_STR)){
						cs_glf = Double.valueOf(VALUE_STR);
					}
				}
				
			}
			
			/**t_base_scheme
			
			 * _leaseTerm : 总期数（）
			 * lease_period : 
			 * *****************************
			 * yearRate 
			 * TOPRIC_SUBFIRENT ： 融资额 （车价 - 首期租金）
			 * fv ： 尾款 （算 ：key_name_zh= 尾款比例 * (calculate ==  sbk? value_str :0 )/100）
			 * isFirst : 期初（1 3 5）( select * from t_sys_data_dictionary t where t.type='支付方式';) 1：0
			 * date: null
			 * changeIssue: 1
			 * DISCOUNT_MONEY : 贴息（金额？金额 : => fv）
			 * PAY_WAY :  (1 3 5) |(2 4 6 )
			 */
			List<Map<String, String>> payTable=new ArrayList<Map<String,String>>();
			try {
				String calculate_type = "PMT";
				Map<String,Object> schemeJson = new HashMap<String,Object>();
				schemeJson.put("SCHEME_CODE",  scheme_code);
				schemeJson.put("KEY_NAME_EN",  "CALCULATE_TYPE");
				schemeJson.put("STATUS",  "0");
				Map<String,Object> calculate_typeMap = Dao.selectOne("weixin_view.select_base_scheme", schemeJson);
				if(calculate_typeMap != null && !calculate_typeMap.isEmpty()){
					String VALUE_STR = (String) calculate_typeMap.get("VALUE_STR");
					if(StringUtils.isBlank(VALUE_STR)){
						calculate_type = "PMT";
					}else if(VALUE_STR.contains(",")){
						calculate_type = "PMT";
					}else{
						calculate_type = VALUE_STR;
					}
					
				}
				if(StringUtils.isBlank(calculate_type)){
					calculate_type = "PMT";
				}
				fbean.addRepayDate();
				payTable = SkyEyeFinance.payTable(
													year_interest/100, leaseTerm, finance_topric
													, last_money,  lease_period, "", 1, 
													discount_money, pay_way_code ,calculate_type,
													fbean
												);
			} catch (Exception e) {
				e.printStackTrace();
				String str = e.getMessage();
				Log.debug("@@@ "+str);
				oneSchemeType.put("errorInfo", str);
				pageErrData.add(oneSchemeType);
				continue;
			}
			
			if(payTable != null && !payTable.isEmpty()){
				double zlx = 0d; // 总利率
				for(int i = 0 ; i <  leaseTerm; i++){
					Map<String, String> m = payTable.get(i);
					// 1
					double lx = 0d;
					try {
						lx = Double.valueOf(m.get("lx").toString());
					} catch (NumberFormatException e) {
						lx = 0d;
					}
					zlx += lx;
					
					//2 加手续费
					if(cs_glf > 0){
						String __zj = m.get("zj");
						m.put("glf", cs_glf+"");
						m.put("zj", Double.valueOf(__zj) + cs_glf + "");
					}
					if( management_feetype == 3){
						if(cs_sxf > 0){
							String __zj = m.get("zj");
							if(i + 1 == leaseTerm ){
								double cs_sxf_last = poundage_price - cs_sxf * i;
								cs_sxf_last = Double.valueOf(myNumberTool.number(cs_sxf_last, 2,2).replaceAll(",", ""));
								m.put("sxf", cs_sxf_last+"");
								m.put("zj", Double.valueOf(__zj) + cs_sxf_last + "");
							}else{
								m.put("sxf", cs_sxf+"");
								m.put("zj", Double.valueOf(__zj) + cs_sxf + "");
							}
						}
					}
					
				}
				Map<String, String> map2 = payTable.get(leaseTerm -1 );
				String string = map2.get("zj");		// 月租金
				if(StringUtils.isBlank(string)){
					string = "0";
				}
				zj = Double.valueOf(string);
				// 本成率 （利息合计+手续费）/融资额)/年限
				CBL = ( zlx + poundage_price) / finance_topric / Double.valueOf(year)  ;
			}else{
				if( !oneSchemeType.containsKey("errorInfo")){
					String str = "无法正常测算出结果";
					Log.debug("@@@ "+str);
					oneSchemeType.put("errorInfo", str);
					pageErrData.add(oneSchemeType);
					continue;
				}
			}
			
			//test
			/*for(Map p: payTable){
				System.out.println(p);
			}*/
			// 给页面有效的数据
			myData.put("__FIRSTPAYALL",firstpayall - DB_BAIL_MONEY);
			myData.put("FIRST_MONEY", first_money);
			myData.put("FIRSTPAYALL", firstpayall);
			myData.put("DEPOSIT_MONEY", deposit_money);
			myData.put("MONTH_RENT", zj);
			myData.put("POUNDAGE_RATE", poundage_rate);
			myData.put("POUNDAGE_PRICE", poundage_price);
			myData.put("MANAGEMENT_FEETYPE", management_feetype);
			myData.put("LAST_MONEY", last_money);
			myData.put("LEASE_TERM", leaseTerm);
			myData.put("LEASE_PERIOD", lease_period);
			myData.put("FINANCE_TOPRIC", finance_topric);
			myData.put("PAY_WAY", pay_way_code);
			myData.put("YEAR_INTEREST", year_interest / 100);
			myData.put("DISCOUNT_MONEY", discount_money);
			myData.put("START_PERCENT", __START_PERCENT);
			myData.put("SCHEME_CODE", scheme_code);
			myData.put("SCHEME_NAME", oneSchemeType.get("SCHEME_NAME"));
			myData.put("CBL", CBL);
			myData.put("car_price", car_price);
			myData.put("GPS_PRICE", gps_price);
			myData.put("year", year);
			oneSchemeType.put("cs_table_json", JSONArray.fromObject(payTable).toString());
			oneSchemeType.put("my_data_json", JSONObject.fromObject(myData).toString());
			oneSchemeType.put("my_data", myData);
			// 给页面有效的数据
			pageData.add(oneSchemeType);
		}
		vm.put("errSchemeList", pageErrData);
		vm.put("schemeList", pageData);
		vm.put("MyNumberTool", new MyNumberTool());
		return new ReplyHtml(VM.html("weixin/financialScheme/recommend_scheme.vm", vm  ));
	}


	/**
	 * <pre>
	 * 取到方案下拉的字典数据对应VALUE_STR的值
	 * <pre>
	 * @author LUYANFENG @ 2015年8月26日
	 */
	private String getFieldValueCode(String scheme_code, String VALUE_STR, String KEY_NAME_ZN) {
		List<Map<String, Object>> dataList = Util.DICTAG.getDataList(KEY_NAME_ZN, scheme_code);
		if(dataList != null && !dataList.isEmpty()){
			for(Map<String,Object> dl : dataList){
				if(dl.get("FLAG").toString().equals(VALUE_STR)){
					 return  dl.get("CODE").toString();
				}
			}
		}
		return "";
	}
	
	
	// 1、 初步计算
	private double calcRZE_WK_SQK(List<Map<String,Object>> schemeOpt_ , 
									String car_price, String JRGS, double finance_topric, double firstpayall ,double first_money, double last_money ,
									String __INSURANCE_MONEY ,String __GZS_MONEY , String __CCS_MONEY
																										){
		for(Map<String, Object> item_ : schemeOpt_){
			String FYGS = (String) item_.get("FYGS");	
			String CALCULATE = (String) item_.get("CALCULATE");	
			String VALUE_STATUS = (String) item_.get("VALUE_STATUS");	
			String VALUE_STR = (String) item_.get("VALUE_STR");
			String PRECENTE_VAL = (String) item_.get("PRECENTE_VAL");
			String PRECENTE_VAL_DOWN = (String) item_.get("PRECENTE_VAL_DOWN");
//			String PRECENTE_VAL_UP = (String) item_.get("PRECENTE_VAL_UP");
			String KEY_NAME_EN = (String) item_.get("KEY_NAME_EN");
//			String VALUE_STATUS = (String) item_.get("VALUE_STATUS");
			String KEY_NAME_EN_PRECENTE = (String) item_.get("KEY_NAME_EN_PRECENTE");
			
			if(StringUtils.isBlank(VALUE_STR) && StringUtils.isNotBlank(KEY_NAME_EN_PRECENTE)){
				if(StringUtils.isBlank(PRECENTE_VAL_DOWN)){
					PRECENTE_VAL_DOWN = "0";
				}
				if(PRECENTE_VAL != null && !PRECENTE_VAL.toString().equals("")){
					if("SBK".equals(CALCULATE)){
						double s = Double.valueOf(PRECENTE_VAL) / 100 * Double.valueOf(car_price);
						VALUE_STR = s + "";
					}else{ 
						if("JRRZE".equals(JRGS)){// 这里不能算jrrze的 且给个0,后里算
							VALUE_STR = "0";
						}else{
							double s = Double.valueOf(PRECENTE_VAL) / 100 * (finance_topric);
							VALUE_STR = s + "";
						}
						
					}
				}else {
					VALUE_STR = Double.valueOf(PRECENTE_VAL_DOWN) / 100  * Double.valueOf(car_price) + "";
				}
				
			}
			if("INSURANCE_MONEY".equals(KEY_NAME_EN)){ 	//保险费
				if(!"1".equals( VALUE_STATUS) && StringUtils.isNotBlank(__INSURANCE_MONEY) ){
					VALUE_STR = __INSURANCE_MONEY ;
				}
				if(StringUtils.isNotBlank(VALUE_STR)){
					for(Map<String,Object> m : schemeOpt_){
						if("INSURANCE_TYPE".equals(m.get("KEY_NAME_EN")+"") ){
							int insurance_type = 1;
							try {
								insurance_type = Integer.valueOf(m.get("VALUE_STR").toString().split(",")[0].replaceAll("年收", "").trim()) ;
							} catch (Exception e) {
								insurance_type = 1;
							}
							try {
								VALUE_STR = (insurance_type * Double.valueOf(VALUE_STR)) + "";
							} catch (Exception e) {}
							break;
						};
					}
				}
				
			}
			if("GZS_MONEY".equals(KEY_NAME_EN)){ 	//购置税
				if(!"1".equals( VALUE_STATUS) && StringUtils.isNotBlank(__GZS_MONEY)){
					VALUE_STR = __GZS_MONEY ;
				}
				
			}
			if("CCS_MONEY".equals(KEY_NAME_EN)){ 	//车船税
				if(!"1".equals( VALUE_STATUS) && StringUtils.isNotBlank(__CCS_MONEY)){
					VALUE_STR = __CCS_MONEY ;
				}
				if(StringUtils.isNotBlank(VALUE_STR)){
					for(Map<String,Object> m : schemeOpt_){
						if("VEHICLE_TYPE".equals(m.get("KEY_NAME_EN")+"") ){
							int vehicle_type = 1;
							try {
								
								vehicle_type = Integer.valueOf(m.get("VALUE_STR").toString().split(",")[0].replaceAll("年收", "").trim()) ;
							} catch (Exception e) {
								vehicle_type = 1;
							}
							try {
								VALUE_STR = (vehicle_type * Double.valueOf(VALUE_STR)) + "";
							} catch (Exception e) {}
							break;
						};
					}
				}
			}
			if(StringUtils.isNotBlank(VALUE_STR)){
				if(JRGS.equals(FYGS)  ){
					if("JRRZE".equals(JRGS)){
						finance_topric += Double.valueOf(VALUE_STR);
					}else if("JRSQK".equals(JRGS)){
						if("START_MONEY".equals(KEY_NAME_EN)){
							firstpayall += first_money;
						}else {
							firstpayall += Double.valueOf(VALUE_STR);
						}
						if( "DB_BAIL_MONEY".equals(KEY_NAME_EN)){
							DB_BAIL_MONEY = Double.valueOf(VALUE_STR);
						}
					}else{
						last_money += Double.valueOf(VALUE_STR);
					}
					
					continue;
				}
			}
		}
		if(JRGS.equals("JRRZE")){
			return finance_topric;
		}else if(JRGS.equals("JRSQK")){
			return firstpayall;
		}else{
			return last_money;
		}
	}
	
	/**
	 * 保存项目方案
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply saveScheme(){
		
		Map<String, Object> error  = new HashMap<String,Object>();
		boolean isok = false;
		try {
			Map<String, Object> params = _getParameters();
			String json1str = (String) params.get("json1"); // 选车页
			String json2str = (String) params.get("json2"); // 方案页
			String json3str = (String) params.get("json3"); // 承租人基本信息1
			String json4str = (String) params.get("json4"); // 承租人基本信息2
			
			// 把四个页面的数据都放到map中（后面的覆盖前面的数据）
			Map<String,Object> map = new HashMap<String,Object>();
			JsonConfig jc = new JsonConfig();
			jc.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object paramObject1, String paramString, Object paramObject2) {
					 if(JSONUtils.isNull(paramObject2)){
				            return true;
				        }
				        return false;
				}
			});
			if(StringUtils.isNotBlank(json1str)){
				JSONObject json1 = JSONObject.fromObject(json1str ,jc );
				map.putAll(json1);
			}
			if(StringUtils.isNotBlank(json2str)){
				JSONObject json2 = JSONObject.fromObject(json2str ,jc);
				map.putAll(json2);
			}
			if(StringUtils.isNotBlank(json3str)){
				JSONObject json3 = JSONObject.fromObject(json3str ,jc);
				map.putAll(json3);
			}
			if(StringUtils.isNotBlank(json4str)){
				JSONObject json4 = JSONObject.fromObject(json4str ,jc);
				map.putAll(json4);
			}
			
			{// 处理money 格式
				this.clearFormat(map, "SQSHNSR_GR");
				this.clearFormat(map, "CCS_MONEY");
				this.clearFormat(map, "INSURANCE_MONEY");
				this.clearFormat(map, "car_price");
			}
			
			
			map.put("LEASE_TOPRIC", map.get("car_price") );
			map.put("PRO_NAME", map.get("CLIENT_NAME") + UtilDate.formatDate(new Date(),"yyMMddHHmm") );
			
			if(map.containsKey("CLIENT_TYPE") && !map.containsKey("CUSTOMER_TYPE")){
				map.put("CUSTOMER_TYPE", map.get("CLIENT_TYPE"));
			}
			
			
			// 校验 及补齐参数
			String CLIENT_TYPE = (String) map.get("CLIENT_TYPE");
			String SUP_NAME = (String) map.get("SUP_NAME");
			String SUP_ID =  (String) map.get("SUP_ID");
			String car_price = (String) map.get("car_price") ;
			String FINANCE_TOPRIC = (String) map.get("FINANCE_TOPRIC") ;
			if(StringUtils.isBlank(car_price)){
				error.put("error", "没有车价");
				return new ReplyAjax(false, error);
			}	
			if(StringUtils.isBlank(SUP_ID)){
				error.put("error", "没有指定经销商");
				return new ReplyAjax(false, error);
			}	
			String COMPANY_NAME =   (String) map.get("COMPANY_NAME");
			String COMPANY_ID =  (String) map.get("COMPANY_ID");
			if(StringUtils.isBlank(COMPANY_ID) || StringUtils.isBlank(COMPANY_NAME)){
				error.put("error", "没有选定厂商");
				return new ReplyAjax(false, error);
			}	
			String CLIENT_NAME = (String)map.get("CLIENT_NAME");
			if(StringUtils.isBlank(CLIENT_NAME)){
				error.put("error", "没有指定客户名");
				return new ReplyAjax(false, error);
			}
			
			String LEGAL_PERSON_PHONE =  (String)map.get("LEGAL_PERSON_PHONE");
			String CLIENT_MOBILE =   (String)map.get("CLIENT_MOBILE");
			if( StringUtils.isBlank(LEGAL_PERSON_PHONE)  && StringUtils.isBlank(CLIENT_MOBILE) ){
				error.put("error", "没有指定客户联系方式");
				return new ReplyAjax(false, error);
			}
			String SCHEME_CODE =   (String) map.get("SCHEME_CODE");
			if(StringUtils.isBlank(SCHEME_CODE)){
				error.put("error", "请选择金融产品。");
				return new ReplyAjax(false, error);
			}
			String cs_table =  (String) map.get("cs_table"); // 测算结果 json
			if(StringUtils.isBlank(cs_table) || cs_table.matches("\\s*\\[\\s*\\]\\s*")){
//			error.put("error", "没有测算结果信息");
				error.put("error", "请选择金融产品");
				return new ReplyAjax(false, error);
			}
			
			
			
			String SCHEME_ID_SEQ = Dao.getSequence("SEQ_FIL_PROJECT_SCHEME"); // scheme_id
			String ID = null;		// 客户id
			String PROJECT_ID =   (String) map.get("PROJECT_ID");
			
			//==================================================== 1、添加客户信息 ===================================
			{
				CustomersService cService = new CustomersService();
				if(StringUtils.isBlank(PROJECT_ID)){	// 新增
					ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
				}else{	 // 修改
					this.bzSer.checkProjectMan(PROJECT_ID );
					Integer status = Dao.selectOne("weixin_view.checkProjectStatus" , PROJECT_ID );
					if(status != 0){
						error.put("error", "操作已过期");
						return new ReplyAjax(false, error);
					}
					ID = Dao.selectOne("weixin_view.select_custID_by_proID", PROJECT_ID );
					
				}
				
				
				// 自然人/法人信息
				Map<String, Object> customerParams =new HashMap<String, Object>();
				customerParams.putAll( map);
				customerParams.put("ID", ID);
				if( map.get("AGE") != null && !"".equals(map.get("AGE"))) {
					customerParams.put("AGE", map.get("AGE") );
				}
				customerParams.put("USERID", Security.getUser().getId());
				customerParams.put("USERCODE",Security.getUser().getCode());
				customerParams.put("USERNAME",Security.getUser().getName());
				String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
				customerParams.put("CUST_ID", CUST_ID);
			    customerParams.put("CUST_NAME", map.get("CLIENT_NAME"));
			    customerParams.put("CUST_TYPE",  1);
			    customerParams.put("TYPE",  CLIENT_TYPE);
			    if(map.get("CLIENT_MOBILE") != null && StringUtils.isNotBlank(map.get("CLIENT_MOBILE").toString())){
			    	customerParams.put("TEL_PHONE", map.get("CLIENT_MOBILE"));
			    }
			    //临时客户状态
			    customerParams.put("CUST_STATUS", "1");
			    if(map.get("BUSINESS_TYPE") != null && StringUtils.isNotBlank(map.get("BUSINESS_TYPE").toString())){
			    	customerParams.put("BUSINESS_TYPE",map.get("BUSINESS_TYPE")); // 企业性质
			    }
				
				if(StringUtils.isNotBlank(PROJECT_ID)){ // 更新
					customerParams.put("CLIENT_ID", ID); 
					cService.doUpdateCust(customerParams);
					
					if(StringUtils.isBlank(CLIENT_TYPE) || "NP".equals(CLIENT_TYPE)){
						Dao.update("customers.updateffn",customerParams);  
					}else{
						Dao.update("customers.updateffl",customerParams);  
					}
				}else{
					
				    // 记录客户历史情况
					String CUST_BASEINFO_ID = Dao.getSequence("SEQ_FIL_CUST_BASE_INFO");
					customerParams.put("CUST_BASEINFO_ID", CUST_BASEINFO_ID);
					customerParams.put("CLIENT_ID", ID);
					cService.doAddCust(customerParams);
					customerParams.putAll(map);
					customerParams.put("ID", ID);
					if(StringUtils.isBlank(CLIENT_TYPE) || "NP".equals(CLIENT_TYPE)){
						Dao.insert("customers.insffn",customerParams);  
					}else{
						Dao.insert("customers.insffl",customerParams);  
					}
				}
			}
			
			
			
			
			//==================================================== 2、 新建项目表 ===================================
			if(StringUtils.isNotBlank(PROJECT_ID)){
				
			}else{
				/**** 销售型售后回租项目编号和 销售型直租一样 ****/
				String PRO_CODE   = CodeService.getCode("项目编号", null, null);
				map.put("PRO_CODE", PRO_CODE);
				Integer counts = Dao.selectOne("weixin_view.check_unique_pro_code" , PRO_CODE);
				if(counts >= 1){
					error.put("error", "请不要重复提交数据");
					return new ReplyAjax(false, error); 
				}
				
			}
			map.put("CLIENT_ID", ID);
			map.put("USERID", Security.getUser().getId());
			map.put("USERNAME", Security.getUser().getName());
			
			// EqList
			{
				List<Map<String,Object>> eqLMap = new ArrayList<Map<String,Object>>();
				Map<String,Object> eqMap = new HashMap<String,Object>();
				
				eqMap.put("SUPPLIERS_ID", SUP_ID ); 
				eqMap.put("SUPPLIERS_NAME", SUP_NAME ); 
				eqMap.put("BX", map.get("INSURANCE_MONEY")); 
				eqMap.put("CCS", map.get("CCS_MONEY")); 
				eqMap.put("COMPANY_NAME", map.get("COMPANY_NAME")); 
				eqMap.put("COMPANY_ID", map.get("COMPANY_ID")); 
				eqMap.put("PRODUCT_ID", map.get("brand_id"));
				eqMap.put("PRODUCT_NAME",  map.get("car_brand_name"));
				eqMap.put("CATENA_ID", map.get("car_series_id"));
				eqMap.put("CATENA_NAME", map.get("car_series_name"));
				eqMap.put("SPEC_ID", map.get("car_model_id"));
				eqMap.put("SPEC_NAME", map.get("car_model_name"));
				eqMap.put("UNIT_PRICE", map.get("car_price") );
				eqMap.put("TOTAL_PRICE", map.get("car_price"));
				eqMap.put("AMOUNT", 1);
				eqMap.put("UNIT", 2); // 辆
				eqMap.put("CREATE_TIME", new Date());
				eqMap.put("CREATE_ID", Security.getUser().getId()); 
				eqMap.put("REAMRK", "微信端生成"); 
				eqMap.put("THING_NAME", map.get("car_brand_name")); 
				eqMap.put("SCHEME_ID", SCHEME_CODE ); 
				eqMap.put("SCHEME_ID_ACTUAL", SCHEME_ID_SEQ ); 
				
				eqLMap.add(eqMap);
				
				map.put("EqList", JSONArray.fromObject(eqLMap).toString());
			}
			
			{// 取业务类型 PLATFORM_TYPE
				Map<String,Object> param = new HashMap<String,Object>();
				param.clear();
				param.put("SCHEME_CODE", SCHEME_CODE);
				param.put("KEY_NAME_EN", "BUSINESS_TYPE");
				param.put("STATUS", "0");
				Map<String,Object> startPercentMap = Dao.selectOne("weixin_view.select_base_scheme", param);
				String PLATFORM_TYPEs = "1";
				if(startPercentMap == null || startPercentMap.isEmpty()){
					
				}else{
					String val = (String) startPercentMap.get("VALUE_STR");
					if(StringUtils.isNotBlank(val)){
						PLATFORM_TYPEs = val;
					}
				}
				if( StringUtils.isNotBlank(PLATFORM_TYPEs) && PLATFORM_TYPEs.contains(",")){ // 处理多选项
					PLATFORM_TYPEs = PLATFORM_TYPEs.split(",")[0];
				}
				map.put("PLATFORM_TYPE", PLATFORM_TYPEs);
				
			}
			
			{// 担保人信息 pc上是有就加个字段 CREDIT_ID == project_id 
				Map<String,Object> schemeJson = new HashMap<String,Object>();
				schemeJson.put("SCHEME_CODE",  SCHEME_CODE);
				schemeJson.put("KEY_NAME_EN",  "ISGUARANTOR");
				schemeJson.put("STATUS",  "0");
				Map<String,Object> dbrMap = Dao.selectOne("weixin_view.select_base_scheme", schemeJson);
				if(dbrMap != null && !dbrMap.isEmpty()){
					String VALUE_STR = (String) dbrMap.get("VALUE_STR");
					String guarantee = "1";
					if(StringUtils.isNotBlank(VALUE_STR) && VALUE_STR.contains(",")){//有自然人担保人,有法人担保人,无
						String[] split = VALUE_STR.split(",");
						if(split.length >= 1){
							String string = split[0];
							if(string.contains("自然人")){
								guarantee = "2";
							}else if(string.contains("法人")){
								guarantee = "3";
								
							}
						}
					}
					map.put("GUARANTEE", guarantee);
				}
			}
			{// 共同承租人信息 
				Map<String,Object> schemeJson = new HashMap<String,Object>();
				schemeJson.put("SCHEME_CODE",  SCHEME_CODE);
				schemeJson.put("KEY_NAME_EN",  "JOINT_TENANT");
				schemeJson.put("STATUS",  "0");
				Map<String,Object> dbrMap = Dao.selectOne("weixin_view.select_base_scheme", schemeJson);
				if(dbrMap != null && !dbrMap.isEmpty()){
					String VALUE_STR = (String) dbrMap.get("VALUE_STR");
					String joint_tenant = "1";
					if(StringUtils.isNotBlank(VALUE_STR) && VALUE_STR.contains(",")){
//						String[] valMap = {"无","自然人"};
						String[] split = VALUE_STR.split(",");
						if(split.length >= 1){
							String string = split[0];
							if("自然人".equals( string)){
								joint_tenant = "2";
							}
						}
						
					}
					map.put("JOINT_TENANT", joint_tenant);
				}
			}
			boolean projectSave = service.projectSave(map); // 项目车辆保存（更新） pgt
			if(!projectSave){
				error.put("error", "项目信息保存失败");
				return new ReplyAjax(false, error);
			}
			
			
			
			//==================================================== 3、保存方案  ===================================
			{
				//===========start 计算总保证金==========
				double zbzjbl_percent = 0;
				{
					Map<String,Object> param = new HashMap<String,Object>();
					// 加上经销商保证金比例
					param.put("SCHEME_CODE",  map.get("SCHEME_CODE"));
					param.put("STATUS", "0");
					param.put("KEY_NAME_EN", "DB_BAIL_PERCENT");
					Map<String,Object> db_bail_percentMap = Dao.selectOne("weixin_view.select_base_scheme", param);
					if(db_bail_percentMap != null && !db_bail_percentMap.isEmpty() ){
						Object db_bail_percent = db_bail_percentMap.get("VALUE_STR");
						if(db_bail_percent != null){
							zbzjbl_percent += Double.valueOf(db_bail_percent.toString());
						}
					}
					// 加上 客户保证金比例
					param.put("KEY_NAME_EN", "DEPOSIT_PERCENT");
					Map<String,Object> deposit_percentMap = Dao.selectOne("weixin_view.select_base_scheme", param);
					if(deposit_percentMap != null && !deposit_percentMap.isEmpty() ){
						Object deposit_percent_ = deposit_percentMap.get("VALUE_STR");
						if(deposit_percent_ != null){
							zbzjbl_percent += Double.valueOf(deposit_percent_.toString());
						}
					}
					
					if(zbzjbl_percent <= 0){
						param.put("KEY_NAME_EN", "ZBZJBL_PERCENT");
						Map<String,Object> zbzjbl_percentMap = Dao.selectOne("weixin_view.select_base_scheme", param);
						if(zbzjbl_percentMap != null && !zbzjbl_percentMap.isEmpty()){
							Object zbzjbl_percent_ = zbzjbl_percentMap.get("VALUE_STR");
							if(zbzjbl_percent_ != null){
								zbzjbl_percent = Double.valueOf(zbzjbl_percent_.toString());
							}
						}
					}
					
				//=======end 计算总保证金==============
				}
				
				Map<String,Object> schemeJson = new HashMap<String,Object>();
				schemeJson.put("SCHEME_CODE",  map.get("SCHEME_CODE"));
				schemeJson.put("STATUS",  "0");
				List<Map<String,Object>> baseScheme = Dao.selectList("weixin_view.getBaseSchemeConfig", schemeJson);
				
				// 
				schemeJson.clear();
				map.put("SCHEME_ID_SEQ", SCHEME_ID_SEQ);
				map.put("CREATE_CODE", Security.getUser().getCode());
				// 从t_base_scheme里取默认值
				for(Map<String,Object> baseOpt : baseScheme ){
					Object STATUS =  baseOpt.get("STATUS");
					String KEY_NAME_ZN = (String) baseOpt.get("KEY_NAME_ZN");
					String KEY_NAME_EN = (String) baseOpt.get("KEY_NAME_EN");
					String VALUE_STR = (String) baseOpt.get("VALUE_STR");
					String PRECENTE_VAL = (String) baseOpt.get("PRECENTE_VAL");
					String CALCULATE = (String) baseOpt.get("CALCULATE");
					String PRECENTE_VAL_DOWN = (String) baseOpt.get("PRECENTE_VAL_DOWN");
					String PRECENTE_VAL_UP = (String) baseOpt.get("PRECENTE_VAL_UP");
					String KEY_NAME_EN_PRECENTE = (String) baseOpt.get("KEY_NAME_EN_PRECENTE");
					
					//XXX CFSB_SF是否拆分设备 微信现在不支持， 值为否 == 0
					if("CFSB_SF".equals(KEY_NAME_EN)){ 
						VALUE_STR = "0";
						schemeJson.put(KEY_NAME_EN, VALUE_STR);
						baseOpt.put("VALUE_STR",  VALUE_STR );
					}
					
					
					baseOpt.put("VALUE_STATUS", STATUS);
					if(KEY_NAME_EN.endsWith("_PERCENT") || KEY_NAME_EN.endsWith("_MONEY") || KEY_NAME_EN.endsWith("_PRICE") ){
						if(StringUtils.isBlank(PRECENTE_VAL_DOWN)){
							PRECENTE_VAL_DOWN = "0";
						}
						if(StringUtils.isBlank(PRECENTE_VAL_UP)){
							PRECENTE_VAL_UP = "100";
						}
						if(KEY_NAME_EN.endsWith("_PERCENT")){
							baseOpt.put("VALUE_DOWN", PRECENTE_VAL_DOWN);
							baseOpt.put("VALUE_UP", PRECENTE_VAL_UP);
							baseOpt.put("COMPUTE", KEY_NAME_EN.replaceAll("_PERCENT$", "_MONEY") );
							baseOpt.put("CODE_MONEY", KEY_NAME_EN.replaceAll("_PERCENT$", "_MONEY") );
						}
						if(StringUtils.isBlank(VALUE_STR) ){
							VALUE_STR = "0";
							if(StringUtils.isNotBlank(KEY_NAME_EN_PRECENTE) && !KEY_NAME_EN.equals(KEY_NAME_EN_PRECENTE)){ // 有比例 CALCULATE 一定不会为空
								if(PRECENTE_VAL != null && !PRECENTE_VAL.toString().equals("")){
									if("SBK".equals(CALCULATE)){
										double s = Double.valueOf(PRECENTE_VAL) / 100 * Double.valueOf(car_price);
										VALUE_STR = s + "";
									}else{ 
										double s = Double.valueOf(PRECENTE_VAL) / 100 * Double.valueOf(FINANCE_TOPRIC); 
										VALUE_STR = s + "";
									}
								}else {
									VALUE_STR = Double.valueOf(PRECENTE_VAL_DOWN) / 100  * Double.valueOf(car_price) + "";
								}
							}
							baseOpt.put("VALUE_STR",  VALUE_STR);
							schemeJson.put(KEY_NAME_EN,  VALUE_STR);
						}
						
						
					}else{
						if( StringUtils.isNotBlank(VALUE_STR) && VALUE_STR.contains(",")){ // 处理多选项
							VALUE_STR = VALUE_STR.split(",")[0];
							schemeJson.put(KEY_NAME_EN,   VALUE_STR);
							baseOpt.put("VALUE_STR", VALUE_STR);
						}
						if( StringUtils.isBlank(VALUE_STR) ){
							//TODO
							System.out.println("...");
						}
						
					}
					// scheme_clob中的数据：  ------------------------------------------------------------
					if("GZS_MONEY".equals(KEY_NAME_EN) ){
						baseOpt.put("VALUE_STR", map.get("GZS_MONEY"));
						continue;
					}
					// 车船税
					if("CCS_MONEY".equals(KEY_NAME_EN) ){
						baseOpt.put("VALUE_STR", map.get("CCS_MONEY"));
						continue;
					}
					// 保险费
					if("INSURANCE_MONEY".equals(KEY_NAME_EN) ){
						baseOpt.put("VALUE_STR", map.get("INSURANCE_MONEY"));
						continue;
					}
					// 尾款
					if("LAST_MONEY".equals(KEY_NAME_EN) ){
						baseOpt.put("VALUE_STR", map.get("LAST_MONEY"));
						continue;
					}
					if("START_PERCENT".equals(KEY_NAME_EN) ){
						baseOpt.put("VALUE_STR", map.get("START_PERCENT"));
						continue;
					}; 
					
					if("START_MONEY".equals(KEY_NAME_EN) ){
						baseOpt.put("VALUE_STR", map.get("FIRST_MONEY"));
						continue;
					}; 
					if("PAY_WAY".equals(KEY_NAME_EN) ){
						baseOpt.put("VALUE_STR", map.get("PAY_WAY"));
						continue;
					}; 
					//--------------------------------------
					//客户保证金
					if("DEPOSIT_MONEY".equals(KEY_NAME_EN) ){
	//					baseOpt.put("VALUE_STR", map.get("DEPOSIT_MONEY"));
						schemeJson.put("DEPOSIT_MONEY", baseOpt.get("VALUE_STR")); 
					}
					// 计算方式  pmt
					if("CALCULATE_TYPE".equals(KEY_NAME_EN) ){
						schemeJson.put("CALCULATE",baseOpt.get("VALUE_STR")); // 计算方式  pmt
					}
					//收益率
					if("BZJSYL".equals(KEY_NAME_EN) ){
						schemeJson.put("INTERNAL_RATE", baseOpt.get("VALUE_STR")); //收益率
					}
					//固定利息
					if("GDLX_PRICE".equals(KEY_NAME_EN) ){
						schemeJson.put("GDLX", baseOpt.get("VALUE_STR")); //固定利息
					}
					//贴息方式
					if("DISCOUNT_TYPE".equals(KEY_NAME_EN) ){
						schemeJson.put("DISCOUNT_TYPE", baseOpt.get("VALUE_STR")); //贴息方式
					}
					//DB保证金比例
					if("DB_BAIL_PERCENT".equals(KEY_NAME_EN) ){
						schemeJson.put("DB_BAIL_PERCENT", baseOpt.get("VALUE_STR")); //DB保证金比例
					}
					//罚息计算方式
					if("DAY_PUACCRATE_TYPE".equals(KEY_NAME_EN)){
						schemeJson.put("DAY_PUACCRATE_TYPE",baseOpt.get("VALUE_STR")); //罚息计算方式
					}
					//保险费比例
					if("INSURANCE_PERCENT".equals(KEY_NAME_EN) ){
						schemeJson.put("INSURANCE_PERCENT", baseOpt.get("VALUE_STR")); //保险费比例
					}
					//上牌方式
					if("CARDWAY".equals(KEY_NAME_EN)){
						schemeJson.put("CARDWAY", baseOpt.get("VALUE_STR")); //上牌方式
					}
					//是否分批起租
					if("SFFPQZ".equals(KEY_NAME_EN) ){
						schemeJson.put("SFFPQZ", baseOpt.get("VALUE_STR")); //是否分批起租
					}
					//指标公司ID
					if(	"QUOTA_ID".equals(KEY_NAME_EN) ){
						schemeJson.put("ZBGS_ID", baseOpt.get("VALUE_STR")); //指标公司ID
					}
					//利息提前收取方式
					if("LXTQSQ".equals(KEY_NAME_EN) ){
						schemeJson.put("LXTQSQ", baseOpt.get("VALUE_STR")); //利息提前收取方式
					}
					//日罚息率
					if("DAY_PUACCRATE".equals(KEY_NAME_EN)){
						schemeJson.put("DAY_PUACCRATE", baseOpt.get("VALUE_STR")); 
					}
					//保证金比例
					if("DB_BAIL_PERCENT".equals(KEY_NAME_EN)){
						schemeJson.put("BAIL_PERCENT", baseOpt.get("VALUE_STR")); 
					}
					//租赁期满处理方式(数据字典CODE)
					if("DEAL_MODE".equals(KEY_NAME_EN) ){
						schemeJson.put("DEAL_MODE", baseOpt.get("VALUE_STR")); 
					}
					//固定年利率
					if("YEAR_TYPE".equals(KEY_NAME_EN) ){
						schemeJson.put("YEAR_TYPE", baseOpt.get("VALUE_STR")); 
					}
					
					// 总保证金比例
					if("ZBZJBL_PERCENT".equals(KEY_NAME_EN) ){
						baseOpt.put("VALUE_STR", zbzjbl_percent );
						schemeJson.put("ZBZJBL_PERCENT", zbzjbl_percent); 
						continue;
					}
					
					// 业务类型 
					if("BUSINESS_TYPE".equals(KEY_NAME_EN) ){
						if(StringUtils.isBlank(VALUE_STR) ){
							schemeJson.put("PLATFORM_TYPE", "1");
						}else{
							schemeJson.put("PLATFORM_TYPE", VALUE_STR);
						}
						continue;
					}
					
					// 租赁物性质
					if("THING_KIND".equals(KEY_NAME_EN)){
						if(StringUtils.isBlank(VALUE_STR) ){
							map.put("THING_KIND", "新车");
							schemeJson.put("THING_KIND", "新车");
						}else{
							map.put("THING_KIND",VALUE_STR);
							schemeJson.put("THING_KIND", VALUE_STR);
							
						}
						continue;
					}
					// MANAGEMENT_FEETYPE
					
					
					List<Map<String, Object>> dataList = Util.DICTAG.getDataList(KEY_NAME_ZN, SCHEME_CODE);
					if(dataList != null && !dataList.isEmpty()){
						for(Map<String,Object> dl : dataList){
							if(dl.get("FLAG").toString().equals(VALUE_STR)){
								schemeJson.put(KEY_NAME_EN, dl.get("CODE").toString() );
								baseOpt.put("VALUE_STR",  dl.get("CODE").toString() );
								break;
							}
						}
					}
				}
				
				schemeJson.putAll(map); 
	
				{
					Map<String,Object> cblMap = new HashMap<String,Object>();
					cblMap.put("KEY_NAME_ZN", "成本率");
					cblMap.put("KEY_NAME_EN", "CBL");
					cblMap.put("VALUE_STR", map.get("CBL"));
					cblMap.put("VALUE_STATUS",0);
					cblMap.put("STATUS",  0);
					baseScheme.add(cblMap);
				}
				try{
					String GPS_PRICE = (String) map.get("GPS_PRICE");
					Double gps_price = Double.valueOf(GPS_PRICE);
					if(gps_price > 0){
						Map<String,Object> gpsMap = new HashMap<String,Object>();
						gpsMap.put("KEY_NAME_ZN", "GPS费用");
						gpsMap.put("KEY_NAME_EN", "GPS_MONEY");
						gpsMap.put("VALUE_STR", GPS_PRICE);
						gpsMap.put("FYGS", "JRRZE");
						gpsMap.put("VALUE_STATUS",1);
						gpsMap.put("ROW_NUM",-1);
						gpsMap.put("STATUS",  0);
						baseScheme.add(gpsMap);
					}
					
				
				}catch(Exception e){}
				/*
				 * FIXME 据说把手续费加到scheme_clob中后面的放款会处理”两次手续费信息“，因为fil_project_scheme表有手续费的相关字段。
				 * 程序要计算这个字段 ，还要处理scheme_clob中的。
				 * 
				 * @2015-8-11 上面这个问题据说是修正了
				 */
				try{
					String POUNDAGE_PRICE = (String) map.get("POUNDAGE_PRICE");
					Double poundage_price = Double.valueOf(POUNDAGE_PRICE);
					if(poundage_price > 0){
						Map<String,Object> sxfMap = new HashMap<String,Object>();
						sxfMap.put("KEY_NAME_ZN", "手续费");
						sxfMap.put("KEY_NAME_EN", "POUNDAGE_PRICE");
						sxfMap.put("VALUE_STR", POUNDAGE_PRICE);
						sxfMap.put("VALUE_STATUS","0");
						Object sxf_sqfs = map.get("MANAGEMENT_FEETYPE"); // 手续费收取方式
						if(sxf_sqfs != null && "3".equals(sxf_sqfs.toString())){
						}else{
							sxfMap.put("FYGS","JRSQK");
						}
						sxfMap.put("DSFS",1);
						sxfMap.put("ROW_NUM",-1);
						sxfMap.put("STATUS",  0);
						baseScheme.add(sxfMap);
					}
				}catch(Exception e){}
				
				
				
				
				schemeJson.put("SCHEME_CLOB", JSONArray.fromObject(baseScheme).toString() ); // 商务政策各项
				schemeJson.put("LEASE_TOPRIC", map.get("car_price")); 		// 设备总价值
				double zzj = 0;
				JSONArray fromObject = JSONArray.fromObject(cs_table);
				for(ListIterator listIterator = fromObject.listIterator(); listIterator.hasNext(); listIterator.next()){
					 JSONObject jsonObject = fromObject.getJSONObject(listIterator.nextIndex() );
					 zzj += Double.valueOf(jsonObject.get("zj").toString());
				}
				schemeJson.put("TOTAL_MONTH_PRICE", zzj); 		// 租金合计
				schemeJson.put("COMPANY_PERCENT", "0" ); 		// 厂商保证金
				schemeJson.put("FIRSTPAYDATE", "" ); 			// 首期款约定付款日期
				
				if(StringUtils.isNotBlank(PROJECT_ID)){
					// FIXME 只能有一个方案
					// 更新 == 删旧的再加新的  注意，这里用的是project_id删的。说明只能有一个方案。
					Dao.delete("baseScheme.deleteSchemeByProID", PROJECT_ID);
					//删掉旧的（有的话） 注意，这里用的是project_id删的。说明只能有一个方案。 
					Dao.delete("baseScheme.delSchemeClob", map.get("PROJECT_ID").toString());
				}
				// SCHEME_CLOB 存到单独的表中
				for(ListIterator<Map<String, Object>> listIterator = baseScheme.listIterator(); listIterator.hasNext(); listIterator.next()){
					 Map map_ = baseScheme.get(listIterator.nextIndex() );
					 /*
						COMPUTEBY,
					  */
					 map_.put("MAXVALUE", map_.get("PRECENTE_VAL_UP"));
					 map_.put("MINVALUE", map_.get("PRECENTE_VAL_DOWN"));
					 map_.put("SCHEME_ID", SCHEME_ID_SEQ );
					 map_.put("PROJECT_ID", map.get("PROJECT_ID") );
					 Dao.insert("baseScheme.saveSchemeClob", map_); 
				}
				
				
				int insert = Dao.insert("baseScheme.doAddProjectScheme", schemeJson); // 保存方案
				if(insert != 1){
					Dao.rollback();
					error.put("error", "方案保存失败");
					return new ReplyAjax(false, error);
				}
			
			
				// 4、保存测试 租金计划明细  SCHEME_ID_SEQ
				Map<String,Object> delMap = new HashMap<String,Object>();
				delMap.put("PAY_ID", SCHEME_ID_SEQ);
				Dao.delete("PayTask.del_payplan_detailSCHEME", delMap);
				map.put("rows", cs_table);
				PayTaskService.quoteCalculateSaveSCHEME(map);
			}
			
			//==================================================== 4、保存后的更新处理 ===================================
			Map<String,Object> mapStatus = new HashMap<String,Object>();
			mapStatus.put("PROJECT_ID", map.get("PROJECT_ID"));
			mapStatus.put("STATUS", "0");
			mapStatus.put("SUP_ID", SUP_ID);
			mapStatus.put("SQFKYD_DATE", map.get("FIRSTPAYDATE"));
			mapStatus.put("ZKL", map.get("ZKL"));
			service.updateProjectStatusById(mapStatus);
			
			//==================================================== 5、查询客户ID ===================================
			Map<String,Object> mapCredit = Dao.selectOne("project.queryClentByID", map.get("PROJECT_ID"));
			if (mapCredit != null) {
				mapCredit.put("PROJECT_ID", map.get("PROJECT_ID"));
				// 先判断有没有数据，有的话就不insert
				int num = Dao.selectInt("project.doqueryCreateDate", mapCredit);
				if (num == 0) {
					Dao.insert("project.doCreateCredit", mapCredit);
				}

			}
			isok = true;
		} catch (Exception e) {
			e.printStackTrace();
			error.put("error", "服务错误");
			isok  = false;
		}
		
		System.out.println("....分支来的......................");
		return new ReplyAjax( isok, error);
	}
	
	/**
	 * 清理被格式化过的数值类型
	 * @author LUYANFENG @ 2015年4月3日
	 */
	private void clearFormat(Map<String, Object> map, String key) {
		Object keystr = map.get(key);
		if( keystr != null && !"".equals(keystr)){
			map.put(key, keystr.toString().replaceAll("￥|,", ""));
		}
	}
	/**
	 * 清理被格式化过的数值类型
	 * @author LUYANFENG @ 2015年4月3日
	 */
	private String clearFormat(  String val) {
		if(StringUtils.isNotBlank(val)){
			val =  val.replaceAll("￥|,", "");
		}
		return val;
	}

	/**
	 * 方案 取数据
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_json(){
		
		JSONObject json = new JSONObject();
		json.put("ok", false);
		json.put("error", "");
		
		try {
			Map<String, Object> ms = _getParameters();
			String brand_id = (String) ms.get("brand_id");
			String car_series_id = (String) ms.get("car_series_id");
			String car_model_id = (String) ms.get("car_model_id");
			Map<String,Object> params = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(brand_id)){
				params.put("brand_id", brand_id);
				params.put("PAGE_BEGIN", 1);
				params.put("PAGE_END", 2);
				params.put("magic", oracle_magic);
				Map<String,Object> brandMap = Dao.selectOne("weixin_view.select_brand", params);
				json.put("brandMap", brandMap);
				
			}
			params.clear();
			if(StringUtils.isNotBlank(car_model_id) && StringUtils.isNotBlank(car_series_id)){
				params.put("brand_id", brand_id);
				params.put("car_series_id", car_series_id);
				params.put("car_model_id", car_model_id);
				params.put("magic", oracle_magic);
				Map<String,Object> carMap = Dao.selectOne("weixin_view.select_car_series_model", params);
				json.put("carMap", carMap);
			}
			json.put("ok", true);
			/*params.clear();
			if(StringUtils.isNotBlank(car_model_id)){
				params.put("CAR_MODEL_ID", car_model_id);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			json.put("ok", false);
			json.put("error", "服务错误");
		}
		
		return new ReplyJson(json );
	}
	
	
	
	
	//----------------------------------------------LP------------------------------------------LP------------------------------------------------LP---------------------
	/**
	 * 方案推荐1
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_LP(){
		VelocityContext vm = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		
		this.findWay(vm, _getParameters ,ClientType.LP);
		
		String PROJECT_ID = (String) _getParameters.get("PROJECT_ID"); //pro_id
		if(StringUtils.isNotBlank(PROJECT_ID)){
			this.getUpData4ProSchemeCust(vm, PROJECT_ID ,ClientType.LP);
		}else{
			this.serializeJsons(vm, _getParameters);
			Object cust_name = _getParameters.get("cust_name");
			Object cust_phone = _getParameters.get("cust_phone");
			vm.put("cust_name", cust_name);
			vm.put("cust_phone", cust_phone);
		}
		
		
		this.setOrg2Page(vm);
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		vm.put("pageTag", "1"); // 页面标记用于 form_param.vmf中的js处理
		return new ReplyHtml(VM.html("/weixin/financialScheme/recommend_LP.vm", vm )); // recommend_LP
	}
	
	/**
	 * LP
	 * 方案推荐2
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_LP_2_1(){
		VelocityContext vm = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		this.findWay(vm, _getParameters , ClientType.LP);
		
		Object cust_name = _getParameters.get("cust_name");
		Object cust_phone = _getParameters.get("cust_phone");
		vm.put("cust_name", cust_name);
		vm.put("cust_phone", cust_phone);
		this.serializeJsons(vm, _getParameters);
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		vm.put("pageTag", "2"); // 页面标记用于 form_param.vmf中的js处理
		return new ReplyHtml(VM.html("/weixin/financialScheme/recommend_2_1.vm", vm )); // recommend_LP_2_1
	}
	
	
	/**
	 * NP
	 * 方案推荐3 （承租人信息：  企业）
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply recommend_LP_3(){
		VelocityContext vm = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		this.findWay(vm, _getParameters ,ClientType.LP);
		
		String PROJECT_ID = (String) _getParameters.get("PROJECT_ID");
		if(StringUtils.isNotBlank(PROJECT_ID)){
			this.getUpData4ProSchemeCust(vm, PROJECT_ID , ClientType.LP);
		}else{
			this.serializeJsons(vm, _getParameters);
			Object cust_name = _getParameters.get("cust_name");
			Object cust_phone = _getParameters.get("cust_phone");
			vm.put("cust_name", cust_name);
			vm.put("cust_phone", cust_phone);
		}
		
		
		List<Object> com_types = (List<Object>) new DataDictionaryMemcached().get("公司性质");
		vm.put("com_types", com_types);
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		vm.put("pageTag", "3"); // 页面标记用于 form_param.vmf中的js处理
		return new ReplyHtml(VM.html("/weixin/financialScheme/recommend_LP_3.vm", vm ));
	}
	
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply test(){
		VelocityContext arg1 = new VelocityContext();
		arg1.put("name", "luyanfeng");
		return new ReplyHtml(VM.html("weixin/测试/资料上传.vm", arg1 ));
		
	}
	
	
	
}

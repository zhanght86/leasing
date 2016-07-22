package com.pqsoft.credit.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.credit.service.CreditGuarantorService;
import com.pqsoft.customModule.service.CustomModuleService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class CreditGuarantorAction extends Action{

	private final String path = "credit/";
	private final String pagePath = "customers/";
	CreditGuarantorService serviceCG = new CreditGuarantorService();
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"项目管理", "担保人查询","列表"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("KH_TYPE", DataDictionaryMemcached.getList("客户类型"));
		return new ReplyHtml(VM.html(path+"guarantorMg.vm", context));
	}
	
	@aPermission(name = {"项目管理", "担保人查询","列表"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getCreditGuarantorData() {
		Map<String, Object> param = _getParameters();
			JSONObject json = JSONObject.fromObject(param);
			param.clear();
			param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(serviceCG.getPage(param));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"项目管理", "担保人查询","修改"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toGuarantorCreditMg() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("CREDIT_ID", param.get("CREDIT_ID"));
		context.put("PROJECT_ID", param.get("CREDIT_ID"));
		context.put("GUARANTOR_LIST", serviceCG.getGuarantorList(param));
		return new ReplyHtml(VM.html(path+"guarantorCreditMg.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name ={"项目管理", "担保人查询","修改"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toGuarantorCreditMgCredit() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("CREDIT_ID", param.get("CREDIT_ID"));
		context.put("PROJECT_ID", param.get("CREDIT_ID"));
		context.put("GUARANTOR_LIST", serviceCG.getGuarantorList(param));
		return new ReplyHtml(VM.html(path+"guarantorCreditMg.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"项目管理", "担保人查询","查看"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toGuarantorCreditQuery() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("CREDIT_ID", param.get("CREDIT_ID"));
		context.put("PROJECT_ID", param.get("CREDIT_ID"));
		context.put("GUARANTOR_LIST", serviceCG.getGuarantorList(param));
		return new ReplyHtml(VM.html(path+"guarantorCreditQuery.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name ={"项目管理", "担保人查询","担保人查看"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toGuarantorCreditQueryProject() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("CREDIT_ID", param.get("CREDIT_ID"));
		context.put("PROJECT_ID", param.get("CREDIT_ID"));
		context.put("GUARANTOR_LIST", serviceCG.getGuarantorList(param));
		return new ReplyHtml(VM.html(path+"guarantorCreditQueryProject.vm", context));
	}
	

	//new Add by wuyanfei   查看担保人
	
	@aDev(code="170012", email="wuyf@pqsoft.cn",name="武燕飞")
	@aAuth(type = aAuthType.USER)
	@aPermission(name={"项目管理","担保人查询","查看"})
	public Reply toViewGuarantorInfoMain(){
		Map<String,Object> param =_getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		if(param.get("TYPE") != null&&param.get("TYPE").toString().equals("NP")){
			return new ReplyHtml(VM.html("customers/toViewGuarantorNatuMain.vm", context));
		}else{
			return new ReplyHtml(VM.html("customers/toViewGuarantorLegalMain.vm", context));
		} 
	}
	
	

	@SuppressWarnings("unchecked")
	@aDev(code="170039", email="yangxue@pqsoft.cn",name="杨雪")
	@aAuth(type = aAuthType.LOGIN)
	//@aPermission(name={"客户管理","客户资料管理","修改详细信息-操作"})
	public Reply toUpdateGuarantorInfo(){
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("CREDIT_ID", param.get("CREDIT_ID"));
		CustomersService service = new CustomersService();
		
		String projectType="NP";//该项目承租人类型，默认为个人
		
		if(param.get("CREDIT_ID") !=null && !param.get("CREDIT_ID").equals("")){
			String PROJECT_ID=param.get("CREDIT_ID").toString();
			//如果存在项目ID说明是通过项目修改过来的客户，则通过项目查询出客户信息替换过来的参数
			Map mapDbr=new HashMap();
			mapDbr.put("PROJECT_ID", PROJECT_ID);
			//多个担保人时,CUST_ID为条件查询
			if(param.get("CLIENT_ID") !=null && !param.get("CLIENT_ID").equals("")){
				mapDbr.put("CUST_ID", param.get("CLIENT_ID").toString());
			}
			Map dbr=Dao.selectOne("project.getDbr",mapDbr);
			if(dbr !=null){
				param.put("CLIENT_ID", dbr.get("CLIENT_ID"));
				param.put("TYPE", dbr.get("TYPE"));
			}
			Map custMap=Dao.selectOne("project.queryClientTypeByProjectID", mapDbr);
			if(custMap !=null && custMap.get("TYPE")!=null && !custMap.get("TYPE").equals("")){
				projectType=custMap.get("TYPE")+"";
			}
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
			if(mapParent ==null){
				mapParent=new HashMap();
			}
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
		System.out.println("-----------------cus="+cus);
		context.put("EMERGENCY_INFO_COUNT",8) ;
		List<Map<String,Object>> emergencyList = service.getEmergencyInfoByClientId(cus.get("CLIENT_ID").toString()) ; 
		List<Map<String,Object>> emergencyList2 = new ArrayList<Map<String,Object>>() ;
		if(emergencyList!=null&&emergencyList.size()>0){
			context.put("EMERGENCY_INFO_COUNT",emergencyList.size()*4) ;
			int length = emergencyList.size() ;
			int syLength =  0 ;
			if(length>2){
				length = 2;
				syLength= emergencyList.size() ;
				context.put("EMERGENCY_INFO",emergencyList2) ;
			}  
			for(int i=0;i<length;i++){
				int  j = i+1 ;
				cus.put("EMERGENCY_NAME_"+j, emergencyList.get(i).get("EMERGENCY_NAME")) ;
				cus.put("EMERGENCY_PHONE_"+j,  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				cus.put("EMERGENCY_ADDR_"+j,  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				cus.put("CLIENT_ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
				cus.put("ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
			
			}
			
			for(int i=2;i<syLength;i++){
				Map<String,Object> m = new HashMap<String,Object>() ;
				
				m.put("EMERGENCY_NAME", emergencyList.get(i).get("EMERGENCY_NAME")) ;
				m.put("EMERGENCY_PHONE",  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				m.put("EMERGENCY_ADDR",  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				m.put("EMERGENCY_RELATIONSHIP",  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				m.put("CLIENT_ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				m.put("ID",  emergencyList.get(i).get("CLIENT_ID")) ;
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
				cus.put("ID_"+j,   "") ;
			
			}
		}
		//查询担保人备注说明
		Map<String,Object> guarMap = service.getGuarantee(param);
		if(guarMap!=null && !guarMap.isEmpty()){
			cus.put("GUARANTEE_CAPACITY", guarMap.get("GUARANTEE_CAPACITY"));
			cus.put("RELATION_EXPLAIN", guarMap.get("RELATION_EXPLAIN"));
		}
		
		context.put("findCustField", service.findCustField(param));
		context.put("custInfo", cus);// 客户详细信息
		context.put("custLinkInfo", service.findCustLinkInfo(param));// 客户关联信息
		context.put("type", service.findCustDataType());// 客户关联信息
		context.put("xqah", service.getANNEX(param, "XQAH", "兴趣爱好"));// 兴趣爱好
		context.put("xg", service.getANNEX(param, "XG", "性格"));// 性格
		context.put("getProvince", areaS.getAllProvince());// 省
		
		
		
		if (param.get("tab").equals("update")) {// 修改
			if (param.get("TYPE") != null) {
				if (param.get("TYPE").toString().equals("NP")) {// 进入自然人页面
					if(projectType.equals("NP")){
						context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("个人担保人与个人关系"));
					}else if(projectType.equals("LP")){
						context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("个人担保人与企业关系"));
					}
					
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
							+ "toUpdateCustNatuDBR.vm", context));
				} else {// 进入法人页面
					//与承租人关系
					if(projectType.equals("NP")){
						context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("企业担保人与个人关系"));
					}else if(projectType.equals("LP")){
						context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("企业担保人与企业关系"));
					}
					context.put("rc_unit",
							new DataDictionaryMemcached().get("货币种类"));
					
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
					
					return new ReplyHtml(VM.html(pagePath
							+ "toUpdateCustLegalDBR.vm", context));
				}
			}
		}
		return null;// 默认进入自然人页面
	}
	
	
	@SuppressWarnings("unchecked")
	@aDev(code="170039", email="yangxue@pqsoft.cn",name="杨雪")
	@aAuth(type = aAuthType.LOGIN)
	//@aPermission(name={"客户管理","客户资料管理","查看详细信息"})
	public Reply toViewGuarantorInfo(){
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("CREDIT_ID", param.get("CREDIT_ID"));
		CustomersService service = new CustomersService();
		//start 自定义表
		
		String projectType="NP";//该项目承租人类型，默认为个人
		Map mapDbr=new HashMap();
		mapDbr.put("PROJECT_ID", param.get("CREDIT_ID"));
		Map custMap=Dao.selectOne("project.queryClientTypeByProjectID", mapDbr);
		if(custMap !=null && custMap.get("TYPE")!=null && !custMap.get("TYPE").equals("")){
			projectType=custMap.get("TYPE")+"";
		}
		
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
			if(mapParent ==null){
				mapParent=new HashMap();
			}
			tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD"));
			
			
			//查询页面显示字段
			Map configMap=new HashMap();
			configMap.put("ID", tableMap.get("ID"));
			List info=customModuleService.queryDynamic_Field(configMap);
			context.put("info", info);
			System.out.println("---------------info="+info);
			
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
		context.put("EMERGENCY_INFO_COUNT",8) ;
		List<Map<String,Object>> emergencyList = service.getEmergencyInfoByClientId(cus.get("CLIENT_ID").toString()) ; 
		List<Map<String,Object>> emergencyList2 = new ArrayList<Map<String,Object>>() ;
		if(emergencyList!=null&&emergencyList.size()>0){
			context.put("EMERGENCY_INFO_COUNT",emergencyList.size()*4) ;
			int length = emergencyList.size() ;
			int syLength =  0 ;
			if(length>2){
				length = 2;
				syLength= emergencyList.size() ;
				context.put("EMERGENCY_INFO",emergencyList2) ;
			}  
			for(int i=0;i<length;i++){
				int  j = i+1 ;
				cus.put("EMERGENCY_NAME_"+j, emergencyList.get(i).get("EMERGENCY_NAME")) ;
				cus.put("EMERGENCY_PHONE_"+j,  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				cus.put("EMERGENCY_ADDR_"+j,  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				cus.put("EMERGENCY_RELATIONSHIP_"+j,  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				cus.put("CLIENT_ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
				cus.put("ID_"+j,  emergencyList.get(i).get("CLIENT_ID")) ;
			
			}
			
			for(int i=2;i<syLength;i++){
				Map<String,Object> m = new HashMap<String,Object>() ;
				
				m.put("EMERGENCY_NAME", emergencyList.get(i).get("EMERGENCY_NAME")) ;
				m.put("EMERGENCY_PHONE",  emergencyList.get(i).get("EMERGENCY_PHONE")) ;
				m.put("EMERGENCY_ADDR",  emergencyList.get(i).get("EMERGENCY_ADDR")) ;				
				m.put("EMERGENCY_RELATIONSHIP",  emergencyList.get(i).get("EMERGENCY_RELATIONSHIP")) ;
				m.put("CLIENT_ID",  emergencyList.get(i).get("CLIENT_ID")) ;
				m.put("ID",  emergencyList.get(i).get("CLIENT_ID")) ;
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
				cus.put("ID_"+j,   "") ;
			
			}
		}
		
		
		context.put("findCustField", service.findCustField(param));
		
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
		//查询担保人备注说明
		Map<String,Object> guarMap = service.getGuarantee(param);
		if(guarMap!=null && !guarMap.isEmpty()){
			cus.put("GUARANTEE_CAPACITY", guarMap.get("GUARANTEE_CAPACITY"));
			cus.put("RELATION_EXPLAIN", guarMap.get("RELATION_EXPLAIN"));
		}
		
		context.put("custInfo", cus);// 客户详细信息
		context.put("custLinkInfo", service.findCustLinkInfo(param));// 客户关联信息
		context.put("type", service.findCustDataType());// 客户关联信息
		context.put("xqah", service.getANNEX(param, "XQAH", "兴趣爱好"));// 兴趣爱好
		context.put("xg", service.getANNEX(param, "XG", "性格"));// 性格
		context.put("getProvince", areaS.getAllProvince());// 省
		if(param.get("tab").equals("view")){//查看
			if(param.get("TYPE") != null) {
				if(param.get("TYPE").toString().equals("NP")){//进入自然人页面
					//与承租人关系
					if(projectType.equals("NP")){
						context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("个人担保人与个人关系"));
					}else if(projectType.equals("LP")){
						context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("个人担保人与企业关系"));
					}
					
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
					
					
					return new ReplyHtml(VM.html("customers/toViewGuatantorDetaiLNatuDBR.vm", context));
				}else {//进入法人页面
//					context.put("financeData", service.selectFinanceData(param));//法人财报===付玉龙
					//与承租人关系
					if(projectType.equals("NP")){
						context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("企业担保人与个人关系"));
					}else if(projectType.equals("LP")){
						context.put("CUST_RELA_LIST", (List) new DataDictionaryMemcached().get("企业担保人与企业关系"));
					}
					
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
					
					context.put("rc_unit",new DataDictionaryMemcached().get("货币种类"));
					
					return new ReplyHtml(VM.html("customers/toViewGuarantorDetailLegalDBR.vm", context));
				}
			}
		}
		return null;//默认进入自然人页面
	}
	
	@aDev(code="170012", email="wuyf@pqsoft.cn",name="武燕飞")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name={"项目管理","担保人查询","修改"})
	public Reply toUpdateGuarantorInfoMain(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		context.put("CREDIT_ID",param.get("CREDIT_ID"));
		if(param.get("TYPE") != null&&param.get("TYPE").toString().equals("NP")){
			return new ReplyHtml(VM.html("customers/toUpdateGuarantorNatuMain.vm", context));
		}else{
			return new ReplyHtml(VM.html("customers/toUpdateGuarantorLegalMain.vm", context));
		} 
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code="170051", email="qijl@pqsoft.cn",name="齐姜龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name={"项目管理","担保人查询","查看"})
	public Reply toViewGuarantorInfoOld(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
	
		CustomersService service = new CustomersService();
		Map<String,Object> cus = (Map<String, Object>) service.findCustDetail(param);
		context.put("custInfo", cus);//客户详细信息
		context.put("custLinkInfo", service.findCustLinkInfo(param));//客户关联信息
		context.put("type", service.findCustDataType());//客户关联信息
		context.put("xqah", service.getANNEX(param,"XQAH","兴趣爱好"));//兴趣爱好
		context.put("xg", service.getANNEX(param,"XG","性格"));//性格
		context.put("getProvince",service.getProvince1());//省
		if(cus.containsKey("PROVINCE")&&!StringUtils.isBlank(cus.get("PROVINCE"))){
		param.put("PARENT_ID", cus.get("PROVINCE"));
		context.put("city", service.getCity(param));
		}
		if(param.get("tab").equals("view")){//查看
			if(param.get("TYPE") != null) {
				if(param.get("TYPE").toString().equals("NP")){//进入自然人页面
					return new ReplyHtml(VM.html("customers/toViewGuarantorNatuMain.vm", context));
				}else {//进入法人页面
//					context.put("financeData", service.selectFinanceData(param));//法人财报===付玉龙
					return new ReplyHtml(VM.html("customers/toViewGuarantorLegalMain.vm", context));
				}
			}
		}
		return null;//默认进入自然人页面
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code="170051", email="qijl@pqsoft.cn",name="齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name={"项目管理","担保人查询","修改"})
	public Reply toUpdateGuarantorInfoOld(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		CustomersService service = new CustomersService();
		Map<String,Object> cus = (Map<String, Object>) service.findCustDetail(param);
		context.put("custInfo", cus);//客户详细信息
		context.put("custLinkInfo", service.findCustLinkInfo(param));//客户关联信息
		context.put("type", service.findCustDataType());//客户关联信息
		context.put("xqah", service.getANNEX(param,"XQAH","兴趣爱好"));//兴趣爱好
		context.put("xg", service.getANNEX(param,"XG","性格"));//性格
		context.put("getProvince",service.getProvince1());//省
		if(cus.containsKey("PROVINCE")&&!StringUtils.isBlank(cus.get("PROVINCE"))){
		param.put("PARENT_ID", cus.get("PROVINCE"));
		context.put("city", service.getCity(param));
		}
		if(param.get("tab").equals("update")){//修改
			if(param.get("TYPE") != null) {
				if(param.get("TYPE").toString().equals("NP")){//进入自然人页面
					return new ReplyHtml(VM.html("customers/toUpdateGuarantorNatu.vm", context));
				}else {//进入法人页面
//					context.put("financeData", service.selectFinanceData(param));//法人财报===付玉龙
					return new ReplyHtml(VM.html("customers/toUpdateGuarantorLegal.vm", context));
				}
			}
		}
		return null;//默认进入自然人页面
	}
	
}

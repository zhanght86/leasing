package com.pqsoft.customers.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;

import com.pqsoft.customers.service.CustTelService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.StringUtils;
import net.sf.json.JSONObject;

public class CustTelAction extends Action {

	private final String pagePath = "customers/custTelShow/";

	private static CustTelService service = new CustTelService();
	
	private static VelocityContext context = new VelocityContext();

	// TODO NOWPROJECT
	
	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * 电话调查数据列表查询显示
	 * 
	 * modify by lishuo 12.8.2015
	 * add  @aPermission @aAuth
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"参数配置","电话调查"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply toCustTelList() {
		
		Map<String, Object> param = _getParameters();
		String project_id = param.get("PROJECT_ID") == null? "" : param.get("PROJECT_ID").toString();
		String type = param.get("type") == null ? "" :param.get("type").toString();
		VelocityContext context = new VelocityContext();
		
		/*	承租人	**/
		System.out.println("=======================");
		StringBuffer selectSql = new StringBuffer();
		selectSql.append(" select * FROM( SELECT  ROWNUM ROWNO, FCC.ID,FCC.NAME,FCC.TEL_PHONE,FCC.PHONE,ffn.TELEPHONE_UNIT,FCC.ID_CARD_NO,FCC.CUST_TYPE, FCC.CUST_DW_DH, ")
				 .append(" pt.INVERST_TYPE, pt.INVERST_OBJECT INVERST_OBJECT, ")
				 .append(" pt.TEL_CONTEXT_NOMAL TEL_CONTEXT_NOMAL,  ")
		         /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟  DATATYPE 4是电话3是身份证   SOURCES 1是内部员工2是黑名单*/
		         .append(" VBUI.DATAVALUE,VBUI.DATATYPE,VBUI.SOURCES,VBUI.INITIAL_DEPARTMENT ");
		StringBuffer fromSql = new StringBuffer();
		fromSql.append(" FROM fil_project_head  fph  ")
			   .append(" left join fil_cust_client   fcc on fph.client_id = fcc.id ")
			   .append(" left join FCC_FK_NP ffn on fcc.id = ffn.fk_id ")
			      /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
			   .append(" left join V_BALICK_USER_INFO VBUI on fcc.tel_phone = VBUI.DATAVALUE ")
			   .append(" left join FIL_PROJECT_TELCALL_INFO PT on pt.project_id = fph.id  and fcc.id = pt.client_id  AND PT.USER_TYPE = 1  and pt.inverst_type in (1, 2, 3) ");
		
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where")
				.append(" fph.id = " + project_id + " ")
				.append(" order by pt.inverst_type asc ")
				.append(" ) T ");
		// WHERE  T.ROWNO BETWEEN  0 and 3 
		
		List<Map<String, Object>> fccList = Dao.execSelectSql(selectSql.toString() + fromSql.toString() + whereSql.toString()); 
		
		/**
		 * 承租人 
		 * 数据格式化显示
		 **/
		List<Map<String, Object>> retfccList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		int cz = 0;
		int indexC = 0;
		for(Map<String, Object> oMap: fccList) {
			Dao.getClobTypeInfo2(oMap, "INVERST_OBJECT");
			Dao.getClobTypeInfo2(oMap, "TEL_CONTEXT_NOMAL");
			map.put("ID", oMap.get("ID"));
			map.put("NAME", oMap.get("NAME"));
			
			String TEL_PHONE = oMap.get("TEL_PHONE") ==null?"": oMap.get("TEL_PHONE").toString();
			String PHONE = oMap.get("PHONE") == null?"" :oMap.get("PHONE").toString();
			String TELEPHONE_UNIT = oMap.get("TELEPHONE_UNIT") == null?"" :oMap.get("TELEPHONE_UNIT").toString();
			
			map.put("ID_CARD_NO", oMap.get("ID_CARD_NO"));
			map.put("CUST_TYPE", oMap.get("CUST_TYPE"));
			map.put("CUST_DW_DH", oMap.get("CUST_DW_DH"));
			map.put("INVERST_TYPE", oMap.get("INVERST_TYPE"));
			//map.put("INVERST_OBJECT", oMap.get("INVERST_OBJECT"));
			   /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
			map.put("DATAVALUE", oMap.get("DATAVALUE"));
			map.put("DATATYPE", oMap.get("DATATYPE"));
			map.put("SOURCES", oMap.get("SOURCES"));
			map.put("INITIAL_DEPARTMENT", oMap.get("INITIAL_DEPARTMENT"));
			if(indexC == 0) {
				// 电调历史电话
				if(!StringUtils.isBlank(TEL_PHONE)) {
					map.put("TEL_PHONE", TEL_PHONE);
				}
				if(!StringUtils.isBlank(PHONE)) {
					map.put("PHONE", PHONE);
				}
				if(!StringUtils.isBlank(TELEPHONE_UNIT)) {
					map.put("TELEPHONE_UNIT", TELEPHONE_UNIT);
				}
				indexC++;
			}
			
			if(cz == 0) {
				// 电调内容
				map.put("TEL_PHONE_CONTEXT_NOMAL", "");
				map.put("PHONE_CONTEXT_NOMAL", "");
				map.put("TELEPHONE_UNIT_CONTEXT_NOMAL", "");
				
				// 电调历史电话
				map.put("TEL_PHONE_OBJECT", "");
				map.put("PHONE_OBJECT", "");
				map.put("TELEPHONE_UNIT_OBJECT", "");
			}
			cz++;
			// 承租人电话
			String s = oMap.get("INVERST_TYPE") == null ? "" : oMap.get("INVERST_TYPE").toString();
			if(!StringUtils.isBlank(s)) {
				int c = Integer.parseInt(s);
				
				if(c == 1) {
					map.put("TEL_PHONE_CONTEXT_NOMAL", oMap.get("TEL_CONTEXT_NOMAL"));
					// 手机号
					String TEL_PHONE_OBJECT = oMap.get("INVERST_OBJECT") == null?"": oMap.get("INVERST_OBJECT").toString();
					// 历史电调电话
					map.put("TEL_PHONE_OBJECT", TEL_PHONE_OBJECT);
					
					if(StringUtils.isBlank(TEL_PHONE_OBJECT)) {
						map.put("NEW_TEL_PHONE", "");
						map.put("TEL_PHONE_OBJECT", TEL_PHONE);
						
					} else if(TEL_PHONE.equals(TEL_PHONE_OBJECT)) {
						map.put("NEW_TEL_PHONE", "");
						
					} else if(!TEL_PHONE.equals(TEL_PHONE_OBJECT)) {
						// 新号码
						map.put("NEW_TEL_PHONE", TEL_PHONE);
						
						String turn = "";
						turn = TEL_PHONE;
						TEL_PHONE = TEL_PHONE_OBJECT;
						TEL_PHONE_OBJECT = turn;
					}
					// 
					map.put("TEL_PHONE", TEL_PHONE);
				}
				else if(c == 2) {
					map.put("PHONE_CONTEXT_NOMAL", oMap.get("TEL_CONTEXT_NOMAL"));
					// 固话
					String PHONE_OBJECT = oMap.get("INVERST_OBJECT") == null?"": oMap.get("INVERST_OBJECT").toString();
					map.put("PHONE_OBJECT", PHONE_OBJECT);
					
					if(StringUtils.isBlank(PHONE_OBJECT)) {
						map.put("NEW_PHONE", "");
						map.put("PHONE_OBJECT", PHONE);
						
					} else if(PHONE.equals(PHONE_OBJECT)) {
						map.put("NEW_PHONE", "");
						
					} else if(!PHONE.equals(PHONE_OBJECT)) {
						map.put("NEW_PHONE", PHONE);
						
						String turn = "";
						turn = PHONE;
						PHONE = PHONE_OBJECT;
						PHONE_OBJECT = turn;
						
					}
					map.put("PHONE", PHONE);
				} 
				else if(c == 3) {
					map.put("TELEPHONE_UNIT_CONTEXT_NOMAL", oMap.get("TEL_CONTEXT_NOMAL"));
					// 单位电话
					String TELEPHONE_UNIT_OBJECT = oMap.get("INVERST_OBJECT") == null?"": oMap.get("INVERST_OBJECT").toString();
					map.put("TELEPHONE_UNIT_OBJECT", TELEPHONE_UNIT_OBJECT);
					
					if(StringUtils.isBlank(TELEPHONE_UNIT_OBJECT)) {
						map.put("NEW_TELEPHONE_UNIT", "");
						map.put("TELEPHONE_UNIT_OBJECT", TELEPHONE_UNIT);
						
					} else if(TELEPHONE_UNIT.equals(TELEPHONE_UNIT_OBJECT)) {
						map.put("NEW_TELEPHONE_UNIT", "");
						
					} else if(!TELEPHONE_UNIT.equals(TELEPHONE_UNIT_OBJECT)) {
						map.put("NEW_TELEPHONE_UNIT", TELEPHONE_UNIT);
						
						String turn = "";
						turn = TELEPHONE_UNIT;
						TELEPHONE_UNIT = TELEPHONE_UNIT_OBJECT;
						TELEPHONE_UNIT_OBJECT = turn;
						
					}
					
					map.put("TELEPHONE_UNIT", TELEPHONE_UNIT);
				}
			} else {
				// 解决历史电调电话
				// 电调历史电话
				if(!StringUtils.isBlank(TEL_PHONE)) {
					map.put("TEL_PHONE_OBJECT", TEL_PHONE);
				}
				if(!StringUtils.isBlank(PHONE)) {
					map.put("PHONE_OBJECT", PHONE);
				}
				if(!StringUtils.isBlank(TELEPHONE_UNIT)) {
					map.put("TELEPHONE_UNIT_OBJECT", TELEPHONE_UNIT);
				}
				
				// 电调内容
				map.put("TEL_PHONE_CONTEXT_NOMAL", "");
				map.put("PHONE_CONTEXT_NOMAL", "");
				map.put("TELEPHONE_UNIT_CONTEXT_NOMAL", "");
				
			}
		}
		retfccList.add(map);
		
		/*	担保人	**/
		List<Map<String,Object>> fccList2 = Dao.execSelectSql("SELECT FCC.ID, "
				+ " FCC.NAME,"
				+ " FCC.TEL_PHONE,"
		        + " FCC.PHONE,"
		        + " ffn.telephone_unit,"
		        + " FCC.ID_CARD_NO,"
		        + " FCC.CUST_TYPE,"
		        + " FCC.CUST_DW_DH,"
		        + " FCC.TEL_CONTEXT TEL_CONTEXT,"
		        + " FCC.TEL_CONTEXT_BD TEL_CONTEXT_BD,"
		        + " FCC.TEL_CONTEXT_GS TEL_CONTEXT_GS,"
		        + " FCC.TEL_CONTEXT_RF TEL_CONTEXT_RF,"
		        + " FCC.Phone_Context Phone_Context,"
		        + " FCC.TELEPHONE_UNIT_CONTEXT TELEPHONE_UNIT_CONTEXT,"
		        + " pt.TEL_CONTEXT_NOMAL TEL_CONTEXT_NOMAL,"
		        /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
		        + "  VBUI.DATAVALUE,VBUI.DATATYPE,VBUI.SOURCES,VBUI.INITIAL_DEPARTMENT  " 
				// 查询的表
				+" FROM  fil_project_head  fph"
		        +" LEFT JOIN FIL_PROJECT_TELCALL_INFO PT ON PT.PROJECT_ID = fph.id "
		        +" left join fil_cust_client fcc on pt.client_id = fcc.id "
		        + " left join FCC_FK_NP  ffn ON ffn.fk_id = fcc.id "
		        /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
				+" left join V_BALICK_USER_INFO VBUI on fcc.tel_phone = VBUI.DATAVALUE  "
		        +" , FIL_CREDIT_GUARANTOR_INFO fgi  "
				// 关系字段
				+" where fgi.credit_id = fph.id"
				+" and fgi.cust_id = fcc.id"
				+" and fph.id = " + project_id + " ");
		
	    for(Map<String,Object> eMap: fccList2) {
			Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT_NOMAL");
			Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT");
			Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT_BD");
			Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT_GS");
			Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT_RF");
			Dao.getClobTypeInfo2(eMap, "Phone_Context");
			Dao.getClobTypeInfo2(eMap, "TELEPHONE_UNIT_CONTEXT");
			
	    }
		
		/** 共同承租人 **/
		List<Map<String, Object>> fccList3 = Dao.execSelectSql("SELECT FCC.ID,"
				+"FCC.NAME,"
				+"FCC.TEL_PHONE,"
				+"FCC.PHONE,"
				+"ffn.telephone_unit,"
				+"FCC.ID_CARD_NO,"
				+"FCC.CUST_TYPE,"
				+"FCC.CUST_DW_DH,"
				+"FCC.TEL_CONTEXT TEL_CONTEXT,"
				+"FCC.TEL_CONTEXT_BD TEL_CONTEXT_BD,"
				+"FCC.TEL_CONTEXT_GS TEL_CONTEXT_GS,"
				+"FCC.TEL_CONTEXT_RF TEL_CONTEXT_RF,"
				+ "FCC.Phone_Context Phone_Context,"
				+ "FCC.TELEPHONE_UNIT_CONTEXT TELEPHONE_UNIT_CONTEXT,"
				+"pt.TEL_CONTEXT_NOMAL TEL_CONTEXT_NOMAL,"
				   /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
			    + "  VBUI.DATAVALUE,VBUI.DATATYPE,VBUI.SOURCES,VBUI.INITIAL_DEPARTMENT  "
				+ " FROM "
				+ "  fil_project_head fph  "
				+ " LEFT JOIN FIL_PROJECT_TELCALL_INFO PT ON PT.PROJECT_ID = fph.id  " // 项目主表
				+ " left join fil_cust_client fcc on pt.client_id = fcc.id "
				+ " left join FCC_FK_NP ffn ON fcc.id = ffn.fk_id " // 客户管理表 	关联  	客户其它信息表	客户ID
				   /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
				+" left join V_BALICK_USER_INFO VBUI on fcc.tel_phone = VBUI.DATAVALUE  "
				+ " where fcc.id = fph.JOINT_TENANT_ID "
				+ " and fph.JOINT_TENANT is not null"
				+ " and fph.id = " + project_id + " ");
		
		 for(Map<String,Object> eMap: fccList2) {
				Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT_NOMAL");
				Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT");
				Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT_BD");
				Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT_GS");
				Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT_RF");
				Dao.getClobTypeInfo2(eMap, "Phone_Context");
				Dao.getClobTypeInfo2(eMap, "TELEPHONE_UNIT_CONTEXT");
		    }
		/*	查询联系人信息	**/
	    List<Map<String,Object>> emergencyList = Dao.execSelectSql(""
	    		+ " SELECT fph.ID as PROJECT_ID, T.ID, T.EMERGENCY_NAME,"
	    		+ " T.EMERGENCY_PHONE,T.EMERGENCY_RELATIONSHIP,T.EMERGENCY_UNIT,EMERGENCY_ADDR,"
	    		+ " PT.TEL_CONTEXT_NOMAL TEL_CONTEXT_NOMAL, pt.INVERST_OBJECT INVERST_OBJECT, "
	    		+ " PT.USER_TYPE,PT.Inverst_Type, "
	    		   /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
	    		+ "  VBUI.DATAVALUE,VBUI.DATATYPE,VBUI.SOURCES,VBUI.INITIAL_DEPARTMENT  "
	    		+ " FROM  "
	    		+ " fil_project_head fph  "
	    		+ " LEFT JOIN  FIL_CUST_EMERGENCY_INFO T ON fph.client_id = t.client_id "
	    		   /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
	    		+ " left join V_BALICK_USER_INFO VBUI on T.EMERGENCY_PHONe = VBUI.DATAVALUE  "
	    		+ " LEFT JOIN  FIL_PROJECT_TELCALL_INFO PT ON fph.ID = PT.PROJECT_ID  and pt.client_id = t.id and PT.USER_TYPE = 2  and PT.Inverst_Type = 4 "
	    		+ " WHERE "
	    		+ " fph.id = " + project_id + " "
	    		+ " ORDER BY T.ID "
	    		);
	    for(Map<String,Object> eMap: emergencyList ) {
			Dao.getClobTypeInfo2(eMap, "INVERST_OBJECT");
			Dao.getClobTypeInfo2(eMap, "TEL_CONTEXT_NOMAL");
	    	String INVERST_OBJECT = eMap.get("INVERST_OBJECT") == null ? "" :eMap.get("INVERST_OBJECT").toString();
	    	String EMERGENCY_PHONE = eMap.get("EMERGENCY_PHONE") == null ? "" :eMap.get("EMERGENCY_PHONE").toString();
	    	
	    	eMap.put("EMERGENCY_PHONE_OBJECT", INVERST_OBJECT);
	    	
	    	if(StringUtils.isBlank(INVERST_OBJECT)) {
	    		
	    		eMap.put("NEW_EMERGENCY_PHONE", "");
	    		eMap.put("EMERGENCY_PHONE_OBJECT", EMERGENCY_PHONE);
	    		
	    	} else if(INVERST_OBJECT.equals(EMERGENCY_PHONE)){
	    		
	    		eMap.put("NEW_EMERGENCY_PHONE", "");
	    		
	    	} else if(!INVERST_OBJECT.equals(EMERGENCY_PHONE)){
	    		
	    		eMap.put("NEW_EMERGENCY_PHONE", EMERGENCY_PHONE);
	    		eMap.put("EMERGENCY_PHONE", INVERST_OBJECT);
	    	}
	    }
	    
	    
	    /* 查询客户的配偶信息	**/
	    List<Map<String,Object>> sfList = Dao.execSelectSql(""
	    		+ "SELECT SF.NAME,SF.TEL_PHONE,SF.ID_CARD_NO, "
	    		   /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
	    		+ "  VBUI.DATAVALUE,VBUI.DATATYPE,VBUI.SOURCES,VBUI.INITIAL_DEPARTMENT  "
	    		//
	    		+ " FROM FIL_CUST_SPOUST SF," // 客户配偶
	    		+ "fil_project_head fph, " // 项目主表
	    		   /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
	    		+"  V_BALICK_USER_INFO VBUI "  //员工表
	    		// 
	    		+ " WHERE SF.CLIENT_ID = fph.client_id "
	    		   /** 电话内部调查加与内部员工验证通过手机号、黑名单验证   2016年4月1日 11:22:05  吴国伟*/
	    		+ "  AND SF.TEL_PHONE(+)= VBUI.DATAVALUE  "
	    		+ " and fph.id = '" + project_id + "'");
	    
	    /* 查询客户的电话调查备注  **/
	    List<Map<String,Object>> objectList = Dao.execSelectSql("SELECT FCC.ID,"
	    		+ " FCC.NAME,"
		         + " FCC.TEL_PHONE,"
			       + " FCC.PHONE,"
			       + " ffn.telephone_unit,"
			       + " FCC.ID_CARD_NO,"
			       + " FCC.CUST_TYPE,"
			       + " FCC.CUST_DW_DH, "
			      + " pt.TEL_CONTEXT_NOMAL TEL_CONTEXT_NOMAL,"
			      + " pt.INVERST_TYPE "
			      + " FROM fil_project_head  fph  "
			      + " left join fil_cust_client   fcc on fph.client_id = fcc.id "
		          + " left join FCC_FK_NP ffn on fcc.id = ffn.fk_id "
		          + " left join FIL_PROJECT_TELCALL_INFO PT on pt.project_id = fph.id  and fcc.id = pt.client_id  AND PT.USER_TYPE = 1   and pt.inverst_type not in (1, 2, 3) "
		 + " where "
         + "  fph.id = "  + project_id + " "
          + " order by pt.inverst_type asc ");
	    
	    Dao.close();
	    
	    /**电话调查备注 
	     * 数据格式化后显示
	     */
	    List<Map<String, Object>> surveyList = new ArrayList<Map<String, Object>>();
		Map<String, Object> surveyMap = new HashMap<String, Object>();
		
		int ds = 0;
		for(Map<String, Object> obMap: objectList) {
			Dao.getClobTypeInfo2(obMap, "TEL_CONTEXT_NOMAL");
			surveyMap.put("ID", obMap.get("ID"));
			surveyMap.put("NAME", obMap.get("NAME"));
			surveyMap.put("TEL_PHONE", obMap.get("TEL_PHONE"));
			surveyMap.put("PHONE", obMap.get("PHONE"));
			surveyMap.put("TELEPHONE_UNIT", obMap.get("TELEPHONE_UNIT"));
			surveyMap.put("ID_CARD_NO", obMap.get("ID_CARD_NO"));
			surveyMap.put("CUST_TYPE", obMap.get("CUST_TYPE"));
			surveyMap.put("CUST_DW_DH", obMap.get("CUST_DW_DH"));
			surveyMap.put("INVERST_TYPE", obMap.get("INVERST_TYPE"));
			//surveyMap.put("INVERST_OBJECT", obMap.get("INVERST_OBJECT"));
			
			if(ds == 0) {
				surveyMap.put("TEL_CONTEXT_NOMAL_APPR", "");
				surveyMap.put("TEL_CONTEXT_NOMAL_BAIDU", "");
				surveyMap.put("TEL_CONTEXT_NOMAL_RENFA", "");
				surveyMap.put("TEL_CONTEXT_NOMAL_GS", "");
			}
			ds++;
			
			String s = obMap.get("INVERST_TYPE") == null ? "" : obMap.get("INVERST_TYPE").toString();
			if(!StringUtils.isBlank(s)) {
				int st = Integer.parseInt(s);
				if(st == 5) {
					surveyMap.put("TEL_CONTEXT_NOMAL_APPR", obMap.get("TEL_CONTEXT_NOMAL"));
				}
				else if(st == 6) {
					surveyMap.put("TEL_CONTEXT_NOMAL_BAIDU", obMap.get("TEL_CONTEXT_NOMAL"));
				} 
				else if(st == 7) {
					surveyMap.put("TEL_CONTEXT_NOMAL_RENFA", obMap.get("TEL_CONTEXT_NOMAL"));
				}
				else if(st == 8) {
					surveyMap.put("TEL_CONTEXT_NOMAL_GS", obMap.get("TEL_CONTEXT_NOMAL"));
				}
			}
		}
		surveyList.add(surveyMap);
	    
	    /*	页面展示需要数据字段	*/
	    param.put("CLIENT_ID", fccList.get(0).get("ID"));
	    context.put("param", param);
	    context.put("fccList", retfccList); // 承租人
	    context.put("fccList2", fccList2); // 担保人
	    context.put("fccList3", fccList3); // 共同承租人
	    context.put("emergencyList", emergencyList); // 联系人
	    context.put("sfList", sfList); // 配偶信息
	    context.put("objectList", surveyList);
	    context.put("project_id", project_id);
	    context.put("emergencyTypeList", new SysDictionaryMemcached().get("联系人类型"));
	    context.put("relationshipList", new SysDictionaryMemcached().get("申请人关系"));
	    
	    if("1".equals(type)){
	    	return new ReplyHtml(VM.html(pagePath+"custTelListShow.vm", context));
	    	
	    }else{
	    	return new ReplyHtml(VM.html(pagePath+"custTelList.vm", context));
	    	
	    }
	}
	
	
	
	/**
	 * 百度 联系方式查询
	 * @return
	 */
	@aPermission (name = {"业务管理", "客户管理"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply toBaiduSearch(){
		Map<String, Object> param = _getParameters();
		String client_id = param.get("CLIENT_ID") == null ? "" : param.get("CLIENT_ID").toString();
		VelocityContext context = new VelocityContext();
		System.out.println("testtoCustTelList");
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath+"custTelList.vm", context));
	}
	
	/**
	 * 电话调查结果保存
	 * 
	 * @return
	 */
	@aPermission (name = {"业务管理", "客户管理"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply doAddEmergencyInfo(){
		
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		
        cleanJSONSpace(json);
		
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		
		return new ReplyAjax(service.doAddEmergencyInfo(param));
	}
	
	
	/**
	 * 保存承租人的多条电话调查（手机和座机）记录---by tianhuihui
	 */
	@aPermission (name = {""})
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply saveTelContext(){
		
		// 当前登陆用户
		User user = Security.getUser();
		
		Map<String, Object> param = _getParameters();
		int project_id = param.get("project_id") == null ? 0 : Integer.parseInt(param.get("project_id").toString());
		JSONObject json = JSONObject.fromObject(param.get("param"));
		JSONObject json2 = JSONObject.fromObject(param.get("param2"));
		Set<String> setJson = json.keySet();
		Set<String> setJson2 = json2.keySet();
		
		//在for循环外将数据只保存一次，循环内多次赋值
		List<Map<String, Object>> mapList = new ArrayList<Map<String , Object>>();
		
		int clientId = 0;
		/**
		 *循环取得页面承租人和联系人电调 
		 */
		for(String key: setJson2) {
			
			Map<String,Object> map = new HashMap<String, Object>();
			String EMERGENCY_PHONE = "";
			String NEW_EMERGENCY_PHONE = "";
			
			if(key.contains("cust")) { // 判断是否是客户
				// 获取客户ID
				clientId = key.substring(key.indexOf("t") + 2) == null ? 0 : Integer.parseInt(key.substring(key.indexOf("t") + 2));
				if(clientId == 0) {
					continue;
				}
				map.put("USER_TYPE", 1);
				map.put("CLIENT_ID", clientId); // 键值ID，客户ID
				
            } else if(key.contains("emergency")){ // 判断是否是联系人
            	// 获取联系人ID
            	String emergencyId = key.substring(key.indexOf("y") + 1);
            	if(StringUtils.isBlank(emergencyId)) {
            		continue;
            	}
            	map.put("USER_TYPE", 2);
            	map.put("CLIENT_ID", emergencyId); // 键值ID，客户ID
            	EMERGENCY_PHONE = "EMERGENCY_PHONE" + emergencyId;
            	NEW_EMERGENCY_PHONE = "NEW_EMERGENCY_PHONE" + emergencyId;
            }
			
			String TEL_CONTEXT_NOMAL = "";
			int INVERST_TYPE;
			String INVERST_OBJECT = "";
			
			if(key.indexOf("custA") > -1){
				INVERST_TYPE = 1;
				TEL_CONTEXT_NOMAL = json2.get(key) + "";
				INVERST_OBJECT = param.get("TEL_PHONE") == null? "" : param.get("TEL_PHONE").toString();
				String str = json2.get("NEW_TEL_PHONE") == null? "" : json2.get("NEW_TEL_PHONE").toString();
				if(!StringUtils.isBlank(str)) {
					INVERST_OBJECT = str;
				}
				
			} else if(key.indexOf("custB") > -1) {
				INVERST_TYPE = 2;
				TEL_CONTEXT_NOMAL = json2.get(key) + "";
				INVERST_OBJECT = param.get("PHONE") == null? "" : param.get("PHONE").toString();
				String str = json2.get("NEW_PHONE") == null? "" : json2.get("NEW_PHONE").toString();
				if(!StringUtils.isBlank(str)) {
					INVERST_OBJECT = str;
				}
			} else if(key.indexOf("custC") > -1) {
				INVERST_TYPE = 3;
				TEL_CONTEXT_NOMAL = json2.get(key) + "";
				INVERST_OBJECT = param.get("TELEPHONE_UNIT") == null? "" : param.get("TELEPHONE_UNIT").toString();
				String str = json2.get("NEW_TELEPHONE_UNIT") == null? "" : json2.get("NEW_TELEPHONE_UNIT").toString();
				if(!StringUtils.isBlank(str)) {
					INVERST_OBJECT = str;
				}
			}
			else if(key.indexOf("emergency") > -1){
				INVERST_TYPE = 4;
				TEL_CONTEXT_NOMAL = json2.get(key) + "";
				INVERST_OBJECT = json2.get(EMERGENCY_PHONE) == null? "" : json2.get(EMERGENCY_PHONE).toString();
				String str = json2.get(NEW_EMERGENCY_PHONE) == null? "" : json2.get(NEW_EMERGENCY_PHONE).toString();
				if(!StringUtils.isBlank(str)) {
					INVERST_OBJECT = str;
				}
			} else {
				continue;
			}
			
			map.put("PROJECT_ID", project_id); // 项目ID
			map.put("INVERST_TYPE", INVERST_TYPE);
			map.put("TEL_CONTEXT_NOMAL", TEL_CONTEXT_NOMAL); // 电调内容
			map.put("INVERST_OBJECT", INVERST_OBJECT); // 承租人和联系人电话
			map.put("CREATE_USERID", user.getId()); // 最后更新人ID
			map.put("LAST_UPDATE_USERID", user.getId()); // 最后更新人ID
			
			int num = queryTelCallContextList(map);
			map.put("num", num);
			
			mapList.add(map);
		}
		
		/**
		 * 循环取得页面电调值
		 */
		for(String key: setJson) {
			Map<String,Object> map = new HashMap<String, Object>();
			
			map.put("PROJECT_ID", project_id); // 项目ID
			map.put("CLIENT_ID", clientId); // 键值ID，客户ID
			map.put("USER_TYPE", 1);
			map.put("CREATE_USERID", user.getId()); // 最后更新人ID
			map.put("LAST_UPDATE_USERID", user.getId()); // 最后更新人ID
			
			String TEL_CONTEXT_NOMAL = "";
			int INVERST_TYPE;
			String INVERST_OBJECT = "";
			
			if(key.indexOf("project_id") > -1) {
				continue;
			}
			else if(key.indexOf("TEL_CONTEXT_BD") > -1) {
				TEL_CONTEXT_NOMAL = (String) json.get("TEL_CONTEXT_BD");
				INVERST_TYPE = 6;
				INVERST_OBJECT = "百度网";
			}
			else if(key.indexOf("TEL_CONTEXT_RF") > -1) {
				TEL_CONTEXT_NOMAL = (String) json.get("TEL_CONTEXT_RF");
				INVERST_TYPE = 7;
				INVERST_OBJECT = "人法网";
			}
			else if(key.indexOf("TEL_CONTEXT_GS") > -1) {
				TEL_CONTEXT_NOMAL = (String) json.get("TEL_CONTEXT_GS");
				INVERST_TYPE = 8;
				INVERST_OBJECT = "工商网";
			} else {
				TEL_CONTEXT_NOMAL = (String) json.get("TEL_CONTEXT");
				INVERST_TYPE = 5;
				INVERST_OBJECT = "审批";
			}
			
			map.put("INVERST_TYPE", INVERST_TYPE);
			map.put("TEL_CONTEXT_NOMAL", TEL_CONTEXT_NOMAL); // 电调内容
			map.put("INVERST_OBJECT", INVERST_OBJECT);
			
			int num = queryTelCallContextList(map);
			map.put("num", num);
			
			mapList.add(map);
			
		}
		
		// 保存电调内容
		service.saveTelContextList(mapList);

		cleanJSONSpace(json);
		cleanJSONSpace(json2);
		
		param.clear();
		param.put("project_id", project_id);
		param.putAll(JsonUtil.toMap(json));
		ReplyAjax ajax = new ReplyAjax(service.saveTelContext(param));
		return ajax;
	}
	
	/**
	 * 根据ID删除具体的紧急联系人
	 * 
	 * @return
	 */
	@aPermission (name = {""})
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "yipan", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply delByID(){
		Map<String, Object> param = _getParameters();
		String itemID = (String) param.get("itemID");
		
		param.clear();
		param.put("itemID", itemID);
		ReplyAjax ajax = new ReplyAjax(service.delByID(param));
		return ajax;
	}

	/**
	 * 清除JSON中所有属性的空格
	 * @param json
	 */
	@aDev(code = "yipan", email = "panyi@jiezhongchina.com", name = "弋攀")
	public void cleanJSONSpace(JSONObject json) {
		
		if(null != json){
			// 取出 jsonObject 中的字段的值的空格  
	        Iterator itt = json.keys();  
	          
	        while (itt.hasNext()) {  
	              
	            String key = itt.next().toString();  
	            String value = json.getString(key);  
	              
	            if(null == value){                
	                continue;        
	            }else if(value.contains(" ")){                
	            	json.put(key, value.replace(" ", ""));
	            }  
	        }
		}
	}
	
	/**
	 * 查询是否已经存在数据
	 * 
	 * @param m
	 * @return
	 */
	public int queryTelCallContextList(Map<String, Object> m) {
		String sql = "SELECT COUNT(0) "
	    		//
	    		+ " FROM FIL_PROJECT_TELCALL_INFO PT " // 
	    		// 
	    		+ " WHERE PT.CLIENT_ID = " + m.get("CLIENT_ID") + " "
	    		+ " and PT.PROJECT_ID = " + m.get("PROJECT_ID") + " "
	    		+ " and PT.USER_TYPE = " + m.get("USER_TYPE") + " "
	    		+ " and PT.INVERST_TYPE = " + m.get("INVERST_TYPE") + " ";
		List<Object> l = Dao.execSelectSql(sql);
		if(l != null && l.size() > 0) {
			Map<String, Object> map = (Map<String, Object>) l.get(0);
			
			return Integer.parseInt(map.get("COUNT(0)").toString());
		}
		return 0;
	}
}

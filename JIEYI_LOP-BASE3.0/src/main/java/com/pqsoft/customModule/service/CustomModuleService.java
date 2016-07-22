package com.pqsoft.customModule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class CustomModuleService {

	private final String xmlPath = "customModule.";	
	//查询业务模块维护表数据
	public Page sys_table_configData(Map<String,Object> m){
		return PageUtil.getPage(m, xmlPath+"queryTable_config", xmlPath+"queryTable_configCount");
	}
	
	public List getModuleList(){
		return Dao.selectList(xmlPath+"getModuleList");
	}
	
	public int add_table_config(Map<String,Object> param){
		param.put("SEQTABLE", "SEQ_"+param.get("CREATE_TABLE"));
		Dao.update(xmlPath+"createTmpTableSEQ",param);
		Dao.update(xmlPath+"createTmpTable",param);
		return Dao.insert(xmlPath+"add_table_config",param);
	}
	
	public List getDYnamicField(Map<String,Object> param){
		return Dao.selectList(xmlPath+"getDYnamicField",param);
	}
	
	public List getFieldTypeList(){
		return Dao.selectList(xmlPath+"getFieldTypeList");
	}
	
	public int add_table_Field(Map<String,Object> param){
		//对于模版子表添加字段
		Dao.update(xmlPath+"createFieldByModule",param);
		
		Dao.update(xmlPath+"createFieldRemarkByModule",param);
		//保存到动态字段表中
		return Dao.insert(xmlPath+"add_table_Field",param);
	}
	
	public List queryDynamic_Field(Map<String,Object> param){
		return Dao.selectList(xmlPath+"queryDynamic_Field", param);
	}
	
	public Map queryTableByModuleName(Map<String,Object> param){
		return Dao.selectOne(xmlPath+"queryTableByModuleName", param);
	}
	
	public Map getParentTableField(Map<String,Object> param){
		return Dao.selectOne(xmlPath+"getParentTableField", param);
	}
	
	public boolean isBooleanFk_tableDate(Map<String,Object> param){
		System.out.println("------------param="+param);
		int num=Dao.selectInt(xmlPath+"isBooleanFk_tableDate", param);
		if(num>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Map querySunTableInfo(Map<String,Object> param){
		return Dao.selectOne(xmlPath+"querySunTableInfo", param);
	}
	
	
	public int submitModuleInfo(Map<String,Object> map){
		if(StringUtils.isBlank(map.get("SUN_TABLE_KEY_PAGE")))
			return 0 ;
		int num=0;
		//1,先删除数据在新增数据
		Dao.delete(xmlPath+"deleteSunTableInfo",map);
		String SUNTABLEFK_ID=Dao.getSequence("SEQ_"+map.get("SUN_TABLE_KEY_PAGE"));
		map.put("SUNTABLEFK_ID", SUNTABLEFK_ID);
		
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		mapInfo.putAll(map);
		mapInfo.remove("SUN_TABLE_KEY_PAGE");
		mapInfo.remove("SUN_FIELD_KEY_PAGE");
		mapInfo.remove("SUN_FIELD_VALUE_PAGE");
		mapInfo.remove("SUNTABLEFK_ID");
		String KEYINFO="";
		String VALUEINFO="";
		
		
		int i=0;
		for (String key : mapInfo.keySet()) {
				if(mapInfo.get(key)!=null && !mapInfo.get(key).equals("")){
					if(i==0){
						KEYINFO=key;
						VALUEINFO="'"+mapInfo.get(key)+"'";
					}
					else{
						KEYINFO=KEYINFO+","+key;
						VALUEINFO=VALUEINFO+",'"+mapInfo.get(key)+"'";
					}
				    i++;
				}
		}
		
		
		String sql="insert into "+map.get("SUN_TABLE_KEY_PAGE")+"(ID,"+map.get("SUN_FIELD_KEY_PAGE")+","+KEYINFO+") values ("+SUNTABLEFK_ID+",'"+map.get("SUN_FIELD_VALUE_PAGE")+"',"+VALUEINFO+")";
		if(StringUtils.isBlank(KEYINFO))
			sql = "insert into "+map.get("SUN_TABLE_KEY_PAGE")+"(ID,"+map.get("SUN_FIELD_KEY_PAGE")+") values ("+SUNTABLEFK_ID+",'"+map.get("SUN_FIELD_VALUE_PAGE")+"')";
			if(StringUtils.isBlank(KEYINFO))
		System.out.println("---------------sql="+sql);
		map.put("SQL", sql);
		num=Dao.insert(xmlPath+"insertSunTableInfo",map);
		return num;
	}

	public Page getModulePage(Map<String, Object> param) {
		
		return PageUtil.getPage(param, xmlPath+"getModulePage", xmlPath+"getModulePageCount");
	}

	public Map<String,Object> queryTableByCreateTableName(Map<String, Object> param) {
		
		return Dao.selectOne(xmlPath+"queryTableByCreateTableName", param);
	}

	public int delZdyInfo(Map<String, Object> map) {
		
		return Dao.delete(xmlPath+"deleteSunTableInfo",map);
	}

	public Map<String, Object> queryDynamicFieldById(Map<String, Object> param) {
		
		return Dao.selectOne(xmlPath+"queryDynamicFieldById",param);
	}

	public int deleteDynamicFieldById(Map<String, Object> param) {
		
		return Dao.delete(xmlPath+"deleteDynamicFieldById",param);
	}

	public int insertDynmicFieldField(Map<String, Object> param) {
		//保存到动态字段表中
		return Dao.insert(xmlPath+"add_table_Field",param);
	}

	public int updateDynamicFieldById(Map<String, Object> param) 
	{
		
		Map<String,Object> beforeMap = Dao.selectOne(xmlPath+"queryDynamicFieldById",param);
		
		Dao.delete(xmlPath+"deleteDynamicFieldById",param);
		Dao.insert(xmlPath+"add_table_Field",param);
		
		Map<String,Object> refer = new HashMap<String,Object>() ;
		refer.put("TABLE_CONFIG_ID", param.get("TABLE_CONFIG_ID_FIELD")) ;
		Map<String,Object> m = Dao.selectOne(xmlPath+"querySysTableConfig", refer) ;
		
		m.put("TABLE_CONFIG_ID", param.get("TABLE_CONFIG_ID_FIELD")) ;
		List<Map<String,Object>> listM = Dao.selectList(xmlPath+"queryDynamicFieldByCreateTableName",m) ;
		
		List<Map<String,Object>> l = Dao.selectList(xmlPath+"getDynamicTableData", m) ;
		
		//配置修改字段后的全部记录
		List<String> lKeyInfo = new ArrayList<String>();
		List<Object> lValueInfo = new ArrayList<Object>();
		for(Map<String,Object> m1:l){
			StringBuffer keyInfo = new StringBuffer() ;
			StringBuffer valueInfo = new StringBuffer() ;
			Iterator<String> iterator = m1.keySet().iterator() ;
			while(iterator.hasNext()){
				String key = iterator.next() ;
				if(StringUtils.isNotBlank(m1.get(key))){
					if(key.equals(beforeMap.get("FIELD_EN"))){
						keyInfo.append(","+param.get("FIELD_EN")) ;
						valueInfo.append(","+m1.get(key)) ;
					}else{
						keyInfo.append(","+key) ;
						valueInfo.append(","+m1.get(key)) ;
					}
				}		
			}
			lKeyInfo.add(keyInfo.toString()) ;
			lValueInfo.add(lValueInfo) ;
			
		}
		
		StringBuffer sql = new StringBuffer() ;
		sql.append("drop table "+ m.get("CREATE_TABLE")+";") ; //删除当前表
		//创建该表
		sql.append("create table " + m.get("CREATE_TABLE")+"("+m.get("MODULE_P_KEY") +" number,"+m.get("CREATE_F_KEY")+" varchar(50) );") ; 
		
		for(Map<String,Object> m1:listM){
			//alter table ${CREATE_TABLE_FIELD} add ${FIELD_EN} ${FIELD_TYPE_TYPE}
			sql.append("alter table " + m1.get("CREATE_TABLE") +" add " + m1.get("FIELD_EN") + " " + m1.get("FIELD_TYPE_TYPE")+" ;") ;
		
		}
	
		 
		for(int i=0;i<lKeyInfo.size();i++){
			String key = lKeyInfo.get(i) ;
			String value = lKeyInfo.get(i) ;
			sql.append("insert into " + param.get("CREATE_TABLE_FIELD")+"(ID"+key+")values("+"SEQ_"+param.get("{CREATE_TABLE_FIELD")+".nextval"+value+");") ;
		}
		
		Map<String,Object> p = new HashMap<String,Object>() ;
		p.put("SQL", sql.toString()) ;
		return Dao.update(xmlPath+"updateCreateTable",p) ;
	}
	
	public List<Map<String,Object>> getDynamicTableData(Map<String,Object> param){
		 return Dao.selectList(xmlPath+"getDynamicTableData", param) ;	
	}
	
	public int updateDynamicFieldById2(Map<String, Object> param) 
	{
		
		Map<String,Object> beforeMap = Dao.selectOne(xmlPath+"queryDynamicFieldById",param);
		
		Dao.delete(xmlPath+"deleteDynamicFieldById",param);
		Dao.insert(xmlPath+"add_table_Field",param);
		boolean alterColumn = true ;
		if(param.get("FIELD_EN").equals(beforeMap.get("FIELD_EN"))){
			alterColumn = false ;
		}
		
		
		Map<String,Object> refer = new HashMap<String,Object>() ;
		refer.put("TABLE_CONFIG_ID", param.get("TABLE_CONFIG_ID_FIELD")) ;
		Map<String,Object> m = Dao.selectOne(xmlPath+"querySysTableConfig", refer) ;
		
		m.put("TABLE_CONFIG_ID", param.get("TABLE_CONFIG_ID_FIELD")) ;
	
		m.put("FIELD_EN", beforeMap.get("FIELD_EN")) ;
		List<Map<String,Object>> l = Dao.selectList(xmlPath+"getDynamicTableData", m) ;
		String sql="" ;
		Map<String,Object> p = new HashMap<String,Object>() ;
		if(l.size()==0||l ==null){
			//alter table TABLE_NAME rename column column_old to column_new;
			//alter table tablename modify filedname varchar2(20);
			
			sql="alter table "+ m.get("CREATE_TABLE")+"  modify " + param.get("FIELD_EN")+" " +param.get("FIELD_TYPE_TYPE") ; //修改当前表	
			
			//修改必填项表
			p.put("SQL", sql) ;
			Dao.update(xmlPath+"executeSql",p) ;
		}
		
		if(alterColumn){
			sql = "alter table "+ m.get("CREATE_TABLE")+"  rename column " + beforeMap.get("FIELD_EN") +" to "+param.get("FIELD_EN")  ; //修改当前表	
			p.put("SQL", sql) ;
			Dao.update(xmlPath+"executeSql",p) ;
		}
			
		return 1;
	}

	public String  deleteDynamicFiled(Map<String, Object> param) {
		
		
		Map<String,Object> refer = new HashMap<String,Object>() ;
		refer.put("TABLE_CONFIG_ID", param.get("TABLE_CONFIG_ID")) ;
		Map<String,Object> m = Dao.selectOne(xmlPath+"querySysTableConfig", refer) ;
		
		List<Map<String,Object>> l = Dao.selectList(xmlPath+"getDynamicTableData", m) ;
		String sql="" ;
		Map<String,Object> p = new HashMap<String,Object>() ;
		if(l.size()==0||l ==null||(l.size()==1&&l.get(0)==null)){
			//删除动态域数据
			Dao.delete(xmlPath+"deleteDynamicFieldById",param);
			//alter table TABLE_NAME rename column column_old to column_new;
			//alter table tablename modify filedname varchar2(20);
			//alter table TABLE_NAME drop column COLUMN_NAME;
			//删除动态表列
			sql = "alter table "+ m.get("CREATE_TABLE")+"  drop column " + param.get("FIELD_EN") ; //删除当前列	
			p.put("SQL", sql) ;
			Dao.update(xmlPath+"executeSql",p) ;
			return "删除成功" ;
		}else{
			return "该字段存在数据，不可删除！" ;
		}
		
		
	}

	public int updateDynamicFieldStatus(Map<String, Object> param) {
		return Dao.update(xmlPath+"updateDynamicFieldStatus", param) ;
	}
}

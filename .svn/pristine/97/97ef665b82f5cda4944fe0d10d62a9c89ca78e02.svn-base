package com.pqsoft.complement.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class ComplementService{
	
	public void jbpmByProjectComplement(String JBPM_ID)
	{
		String PROJECT_ID=JBPM.getVeriable(JBPM_ID).get("PROJECT_ID").toString();
		this.complementStartDateData(PROJECT_ID);
	}
	
	//当有起租确定日时候写数据
	public void complementStartDateData(String PROJECT_ID){
		//当设定起租开始日时候，生成资料后补日期
		//先判断是不是资料后补，是的话在起租日上加90天作为资料后补到期日(90天以后配置到功能中调整)
		//如果已经存在就不再修改日期
		System.out.println("PROJECT_ID="+PROJECT_ID);
		Map dateMap=new HashMap();
		dateMap.put("PROJECT_ID", PROJECT_ID);
		int i=Dao.selectInt("complement.queryCompleShif", dateMap);
		if(i>0)
		{
			return;
		}
		else{
			Map map=Dao.selectOne("complement.queryProjectStart", dateMap);//资料后补
			if(map==null){//该项目的方案没有资料后补元素
				map=new HashMap();
				map.put("ID", PROJECT_ID);
				map.put("VALUE_STR", 1);
			}
			Map map1=Dao.selectOne("complement.queryProjectStart1", dateMap);//放款政策
			
			//如果存在数据说明资料补齐是：是，否70%
			if(map!=null && map.get("ID")!=null && !map.get("ID").equals(""))
			{
				int day_num=90;//资料候补到期日在起租确认日后顺延90天
				map.put("DAY_NUM", day_num);
				//查询第一张支付表的起租确认日
				//新增一条数据到资料补齐表
				Map mapDate=Dao.selectOne("complement.queryDatePlan",map);
				mapDate.put("DATE_COMPLEMENT", map.get("VALUE_STR"));
				String VALUE_STR=map.get("VALUE_STR")!=null?map.get("VALUE_STR").toString():"1";
				String VALUE_STR1=map1.get("VALUE_STR")!=null?map1.get("VALUE_STR").toString():"1";
				if(VALUE_STR.equals("1")){
					mapDate.put("STATUS", 1);
				}
				else{
					if(VALUE_STR1.equals("1"))
					{
						mapDate.put("STATUS", 5);
					}
					else{
						mapDate.put("STATUS", 3);
					}
				}
				Dao.insert("complement.insertComplement",mapDate);
			}
		}
		
	}

	public Page queryPage(Map<String, Object> m) {
		m.put("tags1", "资料补齐状态");
		return PageUtil.getPage(m, "complement.getAllComplement", "complement.getAllComplement_count");
	}
	
	public Excel uploadMessage(Map<String, Object> m){
		m.put("tags1", "资料补齐状态");
		List<Map<String, Object>> dataList = Dao.selectList("complement.uploadMessage",m);
		Excel excel = new Excel();
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		
		title.put("ST_NAME", "状态");
		title.put("AREA_NAME", "区域");
		title.put("SUPPLIER_NAMES", "供应商");
		title.put("PRO_CODE", "项目编号");
		title.put("CUST_NAME", "客户名称");
		title.put("COMPANY_NAMES", "厂商");
		title.put("PRODUCT_NAMES", "租赁物名称");
		title.put("ENGINE_TYPES", "机型");
		title.put("WHOLE_ENGINE_CODES", "出厂编号");
		title.put("QZ_DATE", "起租确定日");
		title.put("END_DATE", "资料补齐到期日");
		title.put("LEASE_TOPRIC", "租赁物购买价款");
		title.put("FK_RATIO", "已放款比例");
		title.put("PLAN_MONEY", "已放款金额");

		excel.addTitle(title);

		excel.setHasTitle(true);
		return excel;
	}
	
	//延期
	public int updateEndById(Map map){
		Map map1=Dao.selectOne("complement.queryProjectStart2", map);//放款政策
		String VALUE_STR=map1.get("VALUE_STR")!=null?map1.get("VALUE_STR").toString():"1";
		if(VALUE_STR.equals("1"))
		{
			map.put("STATUS", 5);
		}
		else{
			map.put("STATUS", 3);
		}
		return Dao.update("complement.updateEndById",map);
	}
	
	//查询租赁物下拉选
	public Object toGetProduct(Map<String, Object> map) {
		return Dao.selectList("complement.toGetProduct", map);
	}
	
	//通过项目ID查询出第一张支付表
	public String getOnePayList_code(Map map){
		Map mapDate=Dao.selectOne("complement.getOnePayList_code", map);
		return mapDate.get("PAYLIST_CODE")+"";
	}
	
	/**
	 * 保存归档申请信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-10
	 */
	public String complementSave(Map<String, Object> map) {
		map.put("CREATE_CODE", Security.getUser().getCode());
		String id = Dao.getSequence("SEQ_FIL_DOSSIER_APPLY");
		map.put("ID", id);
		Dao.insert("complement.complementSave", map);
		
		//修改项目资料状态
		map.put("COM_TYPE", "1");
		Dao.update("complement.updateBuqiState",map);
		return id;
	}
	
	public void complementSave1(Map<String, Object> map) {
		//修改项目资料状态
		map.put("COM_TYPE", "1");
		Dao.update("complement.updateBuqiState",map);
	}
	
	//当申请审核通过后 调用方法
	public void jbpmByComplementPass(String JBPM_ID)
	{
		String APPLY_ID=JBPM.getVeriable(JBPM_ID).get("DOSSIER_APPLY_ID").toString();
		this.complementPassMesthod(APPLY_ID);
	}
	
	public void complementPassMesthod(String APPLY_ID){
		//通过APPLY_ID查询项目ID
		Map map=new HashMap();
		map.put("DOSSIER_APPLY_ID", APPLY_ID);
		map=Dao.selectOne("complement.queryPassProject",map);
		if(map.get("PROJECT_CODE")!=null && !map.get("PROJECT_CODE").equals(""))
		{
			String PROJECT_CODE=map.get("PROJECT_CODE").toString();
			//修改资料补齐状态，修改放款资料补齐状态
			Dao.update("complement.update_Sup_status",map);
			Dao.update("complement.updatePMByComplement",map);
		}
		
	}
	
	//转保证金
	public int sendDB(String PROJECT_CODE,String PROJECT_ID)
	{
		Map map=new HashMap();
		map.put("PROJECT_CODE", PROJECT_CODE);
		map=Dao.selectOne("complement.queryPayMentId", map);
		int i=0;
		if(map.get("ID")!=null && !map.get("ID").equals(""))
		{
			double money=(map.get("PAY_MONEY")!=null && !map.get("PAY_MONEY").equals(""))?Double.parseDouble(map.get("PAY_MONEY").toString()):0d;
			//删放款表数据(二次放款)
			Dao.delete("complement.deletePayMentId", map);
			
			Map mapPool=new HashMap();
			mapPool.put("SOURCE_ID", 3);
			mapPool.put("TYPE", 1);
			mapPool.put("STATUS", 0);
			mapPool.put("BASE_MONEY", money);
			mapPool.put("CANUSE_MONEY", money);
			mapPool.put("OWNER_ID", 1);
			mapPool.put("PROJECT_ID", PROJECT_ID);
			mapPool.put("DBTYPE", 1);
			mapPool.put("REMARK", "项目编号："+PROJECT_CODE+"，资料为补齐直接转DB保证金！");
			Dao.insert("complement.insertPoolDate", mapPool);
			
			//修改资料补齐表的放款比例和放款金额及状态
			Map mapDate=new HashMap();
			mapDate.put("PROJECT_ID", PROJECT_ID);
			mapDate.put("PAY_MONEY", money);
			i=Dao.update("complement.updateProj_sub", mapDate);
		}
		return i;
	}
	
	
	/**
	 * 根据归档申请ID查询归档申请信息
	 * 
	 * @param DOSSIER_APPLY_ID
	 * @return
	 * @author:King 2013-10-10
	 */
	public Map<String, Object> doShowPigeonholeApplyInfo(String DOSSIER_APPLY_ID) {
		Map<String, Object> map = Dao.selectOne("complement.doShowPigeonholeApplyInfo", DOSSIER_APPLY_ID);
		Dao.getClobTypeInfo2(map, "FILEINFO");
		return map;
	}
	
	public List<Map<String, Object>> doShowDossierStorageApplyList(Map<String, Object> map) {
		map.put("F_TYPE", "文件类型");
		map.put("S_TYPE", "档案文件状态");
		return Dao.selectList("complement.doShowDossierStorageList", map);
	}
	
	public List<Map<String, Object>> doShowDossierBorrowApplyList(Map<String, Object> map) {
		map.put("F_TYPE", "文件类型");
		map.put("S_TYPE", "档案文件状态");
		return Dao.selectList("complement.doShowDossierBorrowApplyList", map);
	}
	
	public void jbpmByPassComp(String JBPM_ID)
	{
		List list=JSONArray.fromObject(JBPM.getVeriable(JBPM_ID).get("EQINFO").toString());
		String PROJECT_ID=JBPM.getVeriable(JBPM_ID).get("PROJECT_ID").toString();
		this.saveEqement(list,PROJECT_ID);
	}
	
	public void jbpmByPassCompUP(String JBPM_ID)
	{
		Map<String,Object> param=JBPM.getVeriable(JBPM_ID);
		String PROJECT_ID=param.get("PROJECT_ID").toString();
		this.saveEqementUP(PROJECT_ID);
		updateIDCARDNO(param);
//		updateBankInfo(param);
		Map<String,Object> mm=getProjectChange(param);
		List<Map<String,Object>> list=JSONArray.fromObject(mm.get("EQUIPMENT"));
		if(!list.isEmpty()&&list.size()>0){
			for (Map<String, Object> map : list) {
				Dao.update("project.updateEquInfo", map);
			}
		}
	}
	
	//变更通过调用方法
	public void saveEqement(List list,String PROJECT_ID){
		for(int i=0;i<list.size();i++){
			Map mapDate=(Map)list.get(i);
			Dao.update("complement.updateEqementById", mapDate);
		}
		
		Map proMap=new HashMap();
		proMap.put("PROJECT_ID", PROJECT_ID);
		proMap.put("UPDATE_TYPE", 0);
		Dao.update("complement.updateStatus", proMap);
	}
	
	//变更通过调用方法
	public void saveEqementUP(String PROJECT_ID){
		Map proMap=new HashMap();
		proMap.put("PROJECT_ID", PROJECT_ID);
		proMap.put("UPDATE_TYPE", 0);
		Dao.update("complement.updateStatus", proMap);
	}
	
	public void updateStatus(Map map){
		map.put("UPDATE_TYPE", 1);
		Dao.update("complement.updateStatus", map);
	}
	
	/**
	 * 插入项目资料变更文件
	 * @param map
	 * @return
	 * @author:King 2013-12-18
	 */
	public String doAddProjectChangeApply(Map<String,Object> map){
		String ID=Dao.getSequence("SEQ_FIL_PROJECT_CHANGE");
		map.put("ID", ID);
		map.put("CREATE_CODE", Security.getUser().getCode());
		Dao.insert("complement.doAddProjectChangeApply", map);
		updateIDCARDNO(map);
		if("1".equals(map.get("FLAG"))){
			//新增银行卡号
			addBankInfo(map);
		}else{
			//银行卡号变更
			updateBankInfo(map);
		}
		return ID;
	}
	/**
	 * 修改项目资料变更文件
	 * @param map
	 * @return
	 * @author:King 2013-12-18
	 */
	public void doUpdateProjectChangeApply(Map<String,Object> map){
		Dao.update("complement.doUpdateProjectChangeApply", map);
	}
	
	/**
	 * 查询项目变更资料
	 * @param map
	 * @return
	 * @author:King 2013-12-19
	 */
	public Map<String,Object> getProjectChange(Map<String,Object> map){
		Map<String,Object> rst=Dao.selectOne("complement.getProjectChange", map);
		Dao.getClobTypeInfo2(rst, "EQUIPMENT");
		return rst;
	}
	
	/**
	 * 变更承租人身份证信息
	 * @param map
	 * @author:King 2013-12-19
	 */
	public void updateIDCARDNO(Map<String,Object> map){
		Object ID_CARD_NO=map.get("ID_CARD_NO");
		if(StringUtils.isBlank(ID_CARD_NO)){
		}else{
			Dao.update("complement.updateIDCARDNO",map);
		}
	}
	
	/**
	 * 变更银行信息
	 * @param map
	 * @author:King 2013-12-19
	 */
	public void updateBankInfo(Map<String,Object> map){
		Dao.update("complement.updateBankInfo", map);
	}
	
	/**
	 * 新增银行账号信息
	 * @param map
	 * @author:King 2014-1-24
	 */
	public void addBankInfo(Map<String,Object> map){
		String ID=Dao.getSequence("SEQ_FIL_CUST_OPENINGBANK");
		map.put("ID", ID);
		Map<String,Object> omap=Dao.selectOne("complement.queryProjectBank", map);
		if(!StringUtils.isBlank(omap)){
			map.putAll(omap);
			if(StringUtils.isBlank(map.get("OPEN_BANK_NAME")))
				map.put("OPEN_BANK_NAME", omap.get("BANK_NAME"));
		    if(StringUtils.isBlank(map.get("OPEN_ACCOUNT_NAME")))
				map.put("OPEN_ACCOUNT_NAME", omap.get("BANK_CUSTNAME"));
			if(StringUtils.isBlank(map.get("OPEN_ACCOUNT")))
				map.put("OPEN_ACCOUNT", omap.get("BANK_ACCOUNT"));
		}
		map.put("CREATE_CODE", Security.getUser().getCode());
		//插入新的银行信息
		Dao.insert("complement.addBankInfo",map);
		//变更项目主表的银行id
		Dao.update("complement.updateProjectBankId",map);
	}
	
	/**
	 * 更改项目主表的银行账号信息
	 * @param map
	 * @author:King 2014-1-24
	 */
	public void updateProjectHeadBankId(Map<String,Object> map){
		Dao.update("complement.updateProjectHeadBankId", map);
	}
}

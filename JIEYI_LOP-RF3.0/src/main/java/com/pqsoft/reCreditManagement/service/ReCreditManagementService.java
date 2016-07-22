package com.pqsoft.reCreditManagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.skyeye.api.Dao;

public class ReCreditManagementService {

	private final String xmlPath="reCreditManagement.";
	
	/**
	 * 根据融资机构ID查询融资机构信息
	 * getFhfaById
	 * @date 2013-10-14 上午11:52:04
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getFhfaById(String ID){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FHFA_ID", ID);
		return Dao.selectOne(xmlPath+"queryFhfaById", map);
	}
	
	/**
	 * 添加融资机构授信
	 * addT_REFUND_GRANTPLAN
	 * @date 2013-10-14 下午01:38:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int addT_REFUND_GRANTPLAN(Map<String, Object> map){
		map.put("ID", Dao.getSequence("SEQ_REFINANCE_GRANTPLAN"));
		return Dao.insert(xmlPath+"addT_REFUND_GRANTPLAN", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean toAddCredit(Map<String,Object>  map){
		//获取融资机构授信信息
		Map<String, Object> m = (Map<String, Object>) this.getFhfaById(map.get("FAFH_ID").toString());
		Double grant_price = Double.parseDouble(map.get("GRANT_PRICE").toString());//本次授信额度

		Double balance = Double.parseDouble(m.get("BALANCE") == null ? "0" : m.get("BALANCE").toString());//授信总余额

		Map<String, Object> jg = this.Total(map.get("FAFH_ID").toString());
		String TOTAL_CREDIT = jg.get("TOTAL_CREDIT") + "";
		double total = 0.0d;
		if (StringUtils.isNotEmpty(TOTAL_CREDIT) && !"null".equals(TOTAL_CREDIT)) {
			total = Double.parseDouble(TOTAL_CREDIT);
		}
		if ((balance + grant_price) > total) {
			m.put("BALANCE", total);
		} else {
			m.put("BALANCE", balance + grant_price);
		}
		m.put("TOTAL_CREDIT", jg.get("TOTAL_CREDIT"));//授信总额
		m.put("CREDIT_BIN_DEADLINE", jg.get("CREDIT_BIN_DEADLINE"));//授信起始日期
		m.put("CREDIT_END_DEADLINE", jg.get("CREDIT_END_DEADLINE"));//授信结束日期
		m.put("CREDIT_STATUS", "1");//授信状态(0 未授信 1 已授信)
		m.put("STATUS", map.get("REPEAT_CREDIT"));//是否循环授信(0是 1不是)
		int i = this.updateT_REFUND_FHFA(m);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * @memo 方法说明：根据融资机构ID查询融资机构下的所有授信信息
	 * queryGrantplanByFhfaId
	 * @date 2013-10-14 下午03:11:54
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Object> queryGrantplanByFhfaId(String FK_ID){
		Map<String,Object> mmap=new HashMap<String,Object>();
		mmap.put("FK_ID", FK_ID);
		return Dao.selectList(xmlPath+"queryGrantplanByFhfaId", mmap);
	}
	
	/**
	 * 融资机构查询余额、总额   根据融资机构id
	 * SelT_REFUND_FHFA
	 * @date 2013-10-14 下午03:32:38
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> SelT_REFUND_FHFA(String FK_ID) throws Exception {
		Map<String,Object> a = (Map<String, Object>) Dao.selectOne(xmlPath+"Sel_T_REFUND_FHFA", FK_ID);
		return a;
	}
	
	/**
	 * 根据授信id，查看单条授信
	 * SelT_REFUND_GRANTPLAN
	 * @date 2013-10-14 下午03:35:27
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> SelT_REFUND_GRANTPLAN(Map<String, Object> param) throws Exception {
		Map<String, Object> map = (Map<String,Object>) Dao.selectOne(xmlPath+"SelReCredit", param);
		return map;
	}
	
	/***
	 * @memo 方法说明：取消授信 修改授信状态
	 * updateGrantplanStatus
	 * @date 2013-10-14 下午03:37:30
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateGrantplanStatus(Map<String,Object> map) throws Exception {
		return Dao.update(xmlPath+"updateGrantplanStatus", map);
	}
	
	/**
	 * 根据授信金额求授信总额
	 * Total
	 * @date 2013-10-14 下午03:39:00
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> Total(String param){
		Object obj=Dao.selectOne(xmlPath+"Total", param);
        Map<String,Object> a = null==obj?new HashMap<String,Object>():(Map<String, Object>) obj;
        return a;
	}
	
	/**
	 * 融资机构授信更新
	 * UpdT_REFUND_FHAH
	 * @date 2013-10-14 下午03:40:26
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int UpdT_REFUND_FHAH(Map<String, Object> mapu) throws Exception {
		int update = Dao.update(xmlPath+"updateT_REFUND_FHFA1", mapu);
		return update;
	}
	
	/**
	 * 融资机构授信修改查询
	 * SelupRecheck
	 * @date 2013-10-14 下午04:48:24
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> SelupRecheck(Map<String, Object> param) {
		return (Map<String, Object>) Dao.selectOne(xmlPath+"SelReCredit", param);
	}

	/**
	 * 融资机构授信更新
	 * UpdT_REFUND_GRANTPLAN
	 * @date 2013-10-14 下午04:55:54
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int UpdT_REFUND_GRANTPLAN(Map<String, Object> param) {
		int update = Dao.update(xmlPath+"updateT_REFUND_GRANTPLAN",param);
		return update;
	}
	
	/**
	 * 方法说明：修改融资机构授信信息
	 * updateT_REFUND_FHFA
	 * @date 2013-10-24 下午03:58:26
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateT_REFUND_FHFA(Map<String, Object> map) {
		return Dao.update(xmlPath+"updateT_REFUND_FHFA", map);
	}
   
}

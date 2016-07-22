package com.pqsoft.insure.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsuranceService {
	public Page getInsurance(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("Insure.getInsuranceAll", m);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.getInsuranceAll_count", m));
		return page;
	}

	public Page getInsuranceEquipmentAll(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("Insure.getInsuranceEquipmentAll", m);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.getInsuranceEquipmentAll_count", m));
		return page;
	}
	public int nowclose(Map<String, Object> m)
	{
		return Dao.update("Insure.nowclose",m);
	}
	public Map<String, Object> geteq(Map<String, Object> m)
	{
		return Dao.selectOne("Insure.geteq",m);
	}
	public Map<String,Object> queryNaturaType(Map<String, Object> m)
	{
		return Dao.selectOne("Insure.queryNaturaType",m);
	}
	public Map<String,Object> getOfficial(Map<String,Object> m)
	{
		return Dao.selectOne("Insure.getOfficial",m);
	}
	public List<Map<String,Object>> getCompanyAll()
	{
		return Dao.selectList("Insure.getCompanyAll");
	}
	public Map<String,Object> getCompanyAll1(Map<String,Object> m)
	{
		return Dao.selectOne("Insure.getCompanyAll1",m);
	}
	public List<Map<String,Object>> getInsuranceType(Map<String,Object> m)
	{
		return Dao.selectList("Insure.getInsuranceType", m);
	}
	public Map<String,Object> createInsuList(Map<String,Object> m)
	{
		String id=Dao.getSequence("SEQ_T_INSU_INSURANCELIST");
		m.put("INCU_ID", id);
		m.put("i", Dao.insert("Insure.createInsuList", m));
		return m;
	}
	public int updInsuList(Map<String,Object> m)
	{
		return Dao.update("Insure.updInsuList",m);
	}
	public void createInsutype2InsuList(Map<String, Object> param) {
		if (1 != Dao.insert("Insure.createInsutype2InsuList", param)) { throw new RuntimeException("创建失败"); }
	}
	public void createInsueqmt2InsuList(Map<String, Object> param) {
		if (1 != Dao.insert("Insure.createInsueqmt2InsuList", param)) { throw new RuntimeException("创建失败"); }
	}
	public Page getmanager(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("Insure.getmanager", m);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.getmanager_count", m));
		return page;
	}
	public int delPolicy(Map<String, Object> m)
	{
		Dao.delete("Insure.delPolicyeq",m);
		Dao.delete("Insure.delPolicyxz",m);
		return Dao.delete("Insure.delPolicy",m);
	}
	public int delmiddle(Map<String, Object> m)
	{
		int i=Dao.delete("Insure.delPolicyeq",m);
		i=i+Dao.delete("Insure.delPolicyxz",m);
		return i;
	}
	public Map<String, Object> getPolicy(Map<String, Object> m)
	{
		return Dao.selectOne("Insure.getPolicy",m);
	}
	public int getCodeCount(Map<String, Object> m)
	{
		return Dao.selectInt("Insure.getCodeCount", m);
	}
	public List<Map<String, Object>> getInsurancePolicy(Map<String, Object> m)
	{
		return Dao.selectList("Insure.getInsurancePolicy",m);
	}
	public Page getRenewal(Map<String, Object> m) {
		Page page = new Page(m);
		int XBTS=21;//从数据字典中取值，娶不到的话就默认为21天
		List listXB=new DataDictionaryMemcached().get("续保提醒天数");
		if(listXB.size()>0){
			Map xbMap=(Map)listXB.get(0);
			if(xbMap !=null && xbMap.get("CODE")!=null && !xbMap.get("CODE").equals("")){
				XBTS=Integer.parseInt(xbMap.get("CODE").toString());
			}
		}
		m.put("XBTS", XBTS);
		List<Map<String, Object>> list = Dao.selectList("Insure.getRenewal", m);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.getRenewal_count", m));
		return page;
	}
	public void upISRENEWAL(Map<String, Object> m)
	{
		Dao.update("Insure.upISRENEWAL", m);
	}
	public int updGENERATION(Map m)
	{
		return Dao.update("Insure.updGENERATION", m);
	}
	public Map<String,Object> getInsureINSULIST(Map<String,Object> m)
	{
		return Dao.selectOne("Insure.getInsureINSULIST",m);
	}
	public Map<String,Object> getInsureEMPT(Map<String,Object> m)
	{
		return Dao.selectOne("Insure.getInsureEMPT",m);
	}
	
	/**
	 * 根据参数查询有效保单数量
	 * @param 参数
	 * @return
	 */
	public int queryValidPolicyAmount(Map<String, Object> param){
		return Dao.selectInt("Insure.selectValidPolicyAmt",param);
	}
}

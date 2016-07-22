package com.pqsoft.gpsPlan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.PageTemplate;

public class GpsPlanManageService {

	
	/**
	 * 根据合同ID查询设备信息
	 * 齐姜龙
	 */
	public Object getEmpByRectId(String rect_id)throws Exception{
		return Dao.selectList("GpsPlan.getEmpByRectId", rect_id);
	}
	
	public Map<String,Object> selectCreditHeadByHeadId(String headId)
	{
		
		Map m=new HashMap();
		m.put("RENTER_TYPE", "客户分类");
		m.put("REPORT_TYPE","报告类型");
		m.put("BUS_TYPE", "业务类型");
		m.put("creditId", headId);
		List<Object> r=Dao.selectList("base.HsCreditScheme.selectCreditHeadByHeadId",m);
		if (r.size()>0)
		return (Map<String, Object>) r.get(0);
		else
		return null;
	}
	
	public List<Object> selectCreditEquipmentByHeadId(String id)
	{
		Map map=new HashMap();
		map.put("id", id);
		return Dao.selectList("Base.GpsPlan.selectCreditEqByHeadID",map);
	}
	
	////开停机信息
	public List<Object> selectGPSInfoByEqId(String eqId)
	{
		Map map=new HashMap();
		map.put("eqId", eqId);
		return Dao.selectList("Base.GpsPlan.selectGPSInfoByEqId",map);
	}
	
	public Map selectCreditEquipmentByEqId(String id)
	{
		Map map=new HashMap();
		map.put("id", id);
		return (Map)Dao.selectOne("Base.GpsPlan.selectCreditEqByEqId",map);
	}
	
	public Map selectGPSBYInfo(String id)
	{
		Map map=new HashMap();
		map.put("id", id);
		map.put("dicType", "施工环境");
		return (Map)Dao.selectOne("Base.GpsPlan.selectGPSBYInfo",map);
	}
	
	public List<Object> selectDirList()
	{
		Map map=new HashMap();
		map.put("dicType", "施工环境");
		return Dao.selectList("Base.GpsPlan.selectDirList",map);
	}
	
	//保存GPS信息
	public String createSaveGPSPlan(Map map)
	{
		map.put("ID", Dao.getSequence("SEQ_EQMT_GPSPLAN"));
		Dao.insert("Base.GpsPlan.createSaveGPSPlan", map);
		//修改设备表中的GPS状态为申请中
		Dao.update("Base.GpsPlan.updateGPSPlanByEQ", map);
		return map.get("ID")+"";
	}
	
	//修改GPS信息
	public void updateGPSPlan(Map map)
	{
		Dao.update("Base.GpsPlan.updateGPSPlan", map);
	}
	
	//修改GPS确认信息
	public void updateGPSPlanTime(Map map)
	{
		Dao.update("Base.GpsPlan.updateGPSPlanTime", map);
		
//		//确认以后将此条信息EQGP_STATUS之位0
//		Dao.update("Base.GpsPlan.updateGPSPlanByEQ11", map);
	}
	
	
	public Object getGPSDate(Map<String, Object> m)
	{
		if (!m.containsKey("PAGE_CURR")) {
			m.put("PAGE_CURR", 1);
		}
		if (!m.containsKey("PAGE_COUNT")) {
			m.put("PAGE_COUNT", 10);
		}
		int pageCurr = Integer
				.parseInt(m.get("PAGE_CURR").toString() == null ? "" : m.get(
						"PAGE_CURR").toString());
		int pageCount = Integer
				.parseInt(m.get("PAGE_COUNT").toString() == null ? "" : m.get(
						"PAGE_COUNT").toString());
		
		m.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
		m.put("PAGE_END", pageCount * pageCurr);
		
		List<Object> list = Dao.selectList(
				"Base.GpsPlan.getGpsDate", m);
		
		PageTemplate page = new PageTemplate(list, pageCurr, pageCount, Dao.selectInt("Base.GpsPlan.getGpsDateCount",
						m), 5);
		return page;
	}
	
	//GPS通过
	public void updateGPSSTATEPass(Map map)
	{
		 Dao.update("Base.GpsPlan.UpdateGpsPass", map);
		 
		 //获取设备信息
		 Map map1=new HashMap();
		 map.put("gpsId", map.get("EQGP_ID"));
		 map1=(Map)Dao.selectOne("Base.GpsPlan.getEqIdANDCreditIdByGpsId",map);
		 int type=0;
		 if(map1!=null)
		 {
			 if(map1.get("TYPE11")!=null && !map1.get("TYPE11").equals(""))
			 {
				 type=Integer.parseInt(map1.get("TYPE11").toString());
			 }
			 
		 }
		 int GPS_STATE=0;
		 if(type==0)
		 {
			 GPS_STATE=2;
		 }
		 else
		 {
			 GPS_STATE=4;
		 }
		 map1.put("GPS_STATE", GPS_STATE);
		Dao.update("Base.GpsPlan.UpdateFIL_EQUIPMENT", map1);
	}
	
	//GPS不通过
	public void updateGPSSTATENotPass(Map map)
	{
		 Dao.update("Base.GpsPlan.UpdateGpsNotPass", map);
		 //获取设备信息
		 Map map1=new HashMap();
		 map.put("gpsId", map.get("EQGP_ID"));
		 map1=(Map)Dao.selectOne("Base.GpsPlan.getEqIdANDCreditIdByGpsId",map);
		 int type=0;
		 if(map1!=null)
		 {
			 if(map1.get("TYPE11")!=null && !map1.get("TYPE11").equals(""))
			 {
				 type=Integer.parseInt(map1.get("TYPE11").toString());
			 }
			 
		 }
		 int GPS_STATE=0;
		 if(type==0)
		 {
			 GPS_STATE=4;
		 }
		 else
		 {
			 GPS_STATE=2;
		 }
		 map1.put("GPS_STATE", GPS_STATE);
		 Dao.update("Base.GpsPlan.UpdateFIL_EQUIPMENT", map1);
	}
	
	public Map getEqIdANDCreditIdByGpsId(Map map)
	{
		return (Map)Dao.selectOne("Base.GpsPlan.getEqIdANDCreditIdByGpsId", map);
	}
	
	
	/**
	 * 查询融资合同列表（带分页） author:齐姜龙 time:2010-11-17上午11:16:42
	 * 
	 * @param m
	 * @return
	 */
	public Object getContract(Map<String, Object> m) {
		if (!m.containsKey("PAGE_CURR")) {
			m.put("PAGE_CURR", 1);
		}
		if (!m.containsKey("PAGE_COUNT")) {
			m.put("PAGE_COUNT", 10);
		}
		int pageCurr = Integer
				.parseInt(m.get("PAGE_CURR").toString() == null ? "" : m.get(
						"PAGE_CURR").toString());
		int pageCount = Integer
				.parseInt(m.get("PAGE_COUNT").toString() == null ? "" : m.get(
						"PAGE_COUNT").toString());
	
		m.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
		m.put("PAGE_END", pageCount * pageCurr);
		List<Object> list = Dao.selectList(
				"Base.GpsPlan.contractList1", m);
		
		PageTemplate page = new PageTemplate(list, pageCurr, pageCount, Dao.selectInt("Base.GpsPlan.contractListCount1",
						m), 5);
		return page;
	}
	
	
	//修改GPS信息
	public void updateGPSURL(Map map)
	{
		Dao.update("Base.GpsPlan.updateGPSURL", map);
	}
	
}

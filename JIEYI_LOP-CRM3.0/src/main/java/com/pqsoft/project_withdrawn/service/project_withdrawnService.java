package com.pqsoft.project_withdrawn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class project_withdrawnService {

	public Page query_project_whithDrawn_MG(Map<String, Object> m) {
		return PageUtil.getPage(m, "project_withdrawn.whithDrawn_Msg", "project_withdrawn.whithDrawn_Msg_count");
	}
	
	public void whithDrawn_JBPM_PASS(String JBPM_ID)
	{
		String PROJECT_ID=JBPM.getVeriable(JBPM_ID).get("PROJECT_ID").toString();
		this.whithDrawn_PASS_METHOD(PROJECT_ID);
	}
	
	public void whithDrawn_JBPM_NOTPASS(String JBPM_ID)
	{
		String PROJECT_ID=JBPM.getVeriable(JBPM_ID).get("PROJECT_ID").toString();
		String PLAN_STATUS=JBPM.getVeriable(JBPM_ID).get("PLAN_STATUS").toString();
		this.whithDrawn_NOTPASS_METHOD(PROJECT_ID,PLAN_STATUS);
	}
	
	public void whithDrawn_PASS_METHOD(String PROJECT_ID){
		Map map=new HashMap();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("STATUS", 100);
		Dao.update("project_withdrawn.whithDrawn_STATUS", map);
		
	}
	
	public void whithDrawn_NOTPASS_METHOD(String PROJECT_ID,String PLAN_STATUS)
	{
		Map map=new HashMap();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("STATUS", PLAN_STATUS);
		Dao.update("project_withdrawn.whithDrawn_STATUS", map);
		
		//删除细表和主表信息
		Map mapdate=this.queruHead_BaseInfo(map);
		map.put("HEAD_ID", mapdate.get("ID"));
		Dao.delete("project_withdrawn.deleteDatelByHeadId",map);
		Dao.delete("project_withdrawn.deleteHeadByProject",map);
	}
	
	public void whithDrawn_ING_METHOD(Map map){
		//插入主表数据
		String FIL_PROJECT_VOID_DIFF_LOAN_ID=Dao.getSequence("SEQ_FIL_PROJECT_VOID_DIFF_LOAN");
		map.put("ID",FIL_PROJECT_VOID_DIFF_LOAN_ID);
		map.put("CREATE_ID", Security.getUser().getId());
		Dao.insert("project_withdrawn.insertVoid_diff", map);
		//插入勾选数据
		JSONArray check_FEE = JSONArray.fromObject(map.get("check_FEE"));
		for (Object object : check_FEE) {
			Map<String, Object> m = (Map<String, Object>) object;
			m.put("HEAD_ID", FIL_PROJECT_VOID_DIFF_LOAN_ID);
			System.out.println("-----------------------m="+m);
			Dao.insert("project_withdrawn.insertVoid_Detail",m);
		}
		
		//修改项目表状态为作废中
		Map mapDate=new HashMap();
		mapDate.put("PROJECT_ID", map.get("PROJECT_ID"));
		mapDate.put("STATUS", 101);
		Dao.update("project_withdrawn.whithDrawn_STATUS", mapDate);
	}
	
	
	
	public int whithDrawn_ZuoFei(Map map){
		map.put("STATUS", "100");
		return Dao.update("project_withdrawn.whithDrawn_STATUS", map);
	}
	
	public Map payMentStatus3(Map map)
	{
		return Dao.selectOne("project_withdrawn.payMentStatus3", map);
	}
	
	public Map payMentStatus4(Map map){
		return Dao.selectOne("project_withdrawn.payMentStatus4", map);
	}
	
	public List queryScheme_fee_List(Map map){
		//通过项目ID查询该项目是否为期初，如果为起初则首期款包括第一期租金，第一期租金也要退回
		List list=Dao.selectList("project_withdrawn.queryScheme_fee_List", map);
		Map mapDate=Dao.selectOne("project_withdrawn.queryProjectPayWay", map);
		if(mapDate!=null && mapDate.get("PAY_WAY")!=null)
		{
			//查询第一期已核销租金
			Map mapZ=Dao.selectOne("project_withdrawn.queryOneZujin", map);
			if(mapZ!=null){
				list.add(mapZ);
			}
		}
		return list;
	}
	
	public List queryScheme_fee_List1(Map map){
		return Dao.selectList("project_withdrawn.queryScheme_fee_List1", map);
	}
	
	public Map queruHead_BaseInfo(Map map)
	{
		return Dao.selectOne("project_withdrawn.queruHead_BaseInfo", map);
	}
	
	public List queruDetalInfo(Map map){
		return Dao.selectList("project_withdrawn.queruDetalInfo", map);
	}
}

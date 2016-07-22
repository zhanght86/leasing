package com.pqsoft.letterOpinion.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.BaseUtil;

/**
 * 信审意见
 * @author zhengshangcheng
 *
 */
public class LetterOpinionService {
	
	
	public Page getLetterOpinionPage(Map<String, Object> map){
		
		BaseUtil.getDataAllAuth(map);
		Page page = new Page(map);
		List<Map<String, Object>> list = Dao.selectList("letterOpinion.getLetterOpinionPage", map);
		for(int i=0;i<list.size();i++){
			Map<String, Object> mapJBPM = Dao.selectOne("letterOpinion.getJBPM", list.get(i));
			if(mapJBPM != null){
				if(mapJBPM.get("ID_") != null && !mapJBPM.get("ID_").toString().equals("")){
					List<Map<String, Object>> mapJBPMList = Dao.selectList("letterOpinion.getJBPMList", mapJBPM);
					for(int m=0;m<mapJBPMList.size();m++){
						if(mapJBPMList.get(m).get("TASK_NAME").toString().equals("信审专员初审")){
							list.get(i).put("TS_DATE", mapJBPMList.get(m).get("OP_TIME"));
						}
						if(mapJBPMList.get(m).get("TASK_NAME").toString().equals("信审主管复审")){
							list.get(i).put("ZS_DATE", mapJBPMList.get(m+1).get("OP_TIME"));
						}
					}
				}
			}
		}
		page.addDate(JSONArray.fromObject(list), Dao.selectInt("letterOpinion.getLetterOpinionCount".trim(), map));
		
		return page;
	}

	//信审意见数据查询
	public Map<String, Object> getLetterOpinion(Map<String, Object> param){
		return Dao.selectOne("letterOpinion.getLetterOpinion", param);
	} 
	
	//信审意见修改
	public int updateLetterOpinion(Map<String, Object> param){
		return Dao.update("letterOpinion.updateLetterOpinion", param);
	}
	
	//信审意见新增
	public int addLetterOpinion(Map<String, Object> param){
		return Dao.insert("letterOpinion.addLetterOpinion", param);
	}
	
	public List<Map<String, Object>> getLetterOpinionList(Map<String, Object> param){
		return Dao.selectList("letterOpinion.getLetterOpinion", param);
	}
	
	//拒绝
	@SuppressWarnings("deprecation")
	public void reproJob(){
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> listPro = Dao.selectList("letterOpinion.getProBlacklist", param);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String now_date = df.format(new Date());
		for(int i=0;i<listPro.size();i++){
			String REFUSE_DATE =  listPro.get(i).get("REFUSE_DATE").toString();
			long nd = 1000*24*60*60;//一天的毫秒数
			try {
				long diff = df.parse(now_date).getTime() - df.parse(REFUSE_DATE).getTime();
				
				long day = diff - nd;
				if(day > 7){
					Calendar now = Calendar.getInstance();  
					String month = (now.get(Calendar.MONTH) + 1)+"";
					String days = now.get(Calendar.DAY_OF_MONTH)+"";
					if(!(month.equals("10") && (
							days.equals("1") || days.equals("2") 
						 || days.equals("3") || days.equals("4")
						 || days.equals("5") || days.equals("6") || days.equals("7")) )){
						
						if(!listPro.get(i).get("IFCOUNTERMAND").toString().equals("1")){
						
							//将该客户置黑名单 30天，
							Map<String, Object> mapCust = Dao.selectOne("letterOpinion.getCust", listPro.get(i));
							Map<String, Object> mapBlack = new HashMap<String, Object>();
							mapBlack.put("CUST_NAME", mapCust.get("NAME"));
							mapBlack.put("ID_CARD_NO", mapCust.get("ID_CARD_NO"));
							mapBlack.put("STATUS", "1");
							mapBlack.put("SETIN_DATE", df.format(new Date()));
							Date date = new Date();
							date.setDate(date.getDate()+23);
							mapBlack.put("GETOUT_DATE", date.toLocaleString());
							mapBlack.put("CUST_CLIENT_ID", mapCust.get("ID"));
							Dao.insert("letterOpinion.addblackcust", mapBlack);
						}
					}
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	//标识为黑名单    1:拒绝
	public void updateProBlacklist1(String project_id){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("BLACKLIST", "1");
		Dao.update("letterOpinion.updateProBlacklist", param);
		
	}
	
	//拒绝拉黑
	public void blackList(String project_id){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("ID", project_id);
		Map<String, Object> mapPro = Dao.selectOne("letterOpinion.getProject", mm);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", mapPro.get("CLIENT_ID"));
		Map<String, Object> mapCust = Dao.selectOne("letterOpinion.getCust", map);
		
		Map<String, Object> mapBlack = new HashMap<String, Object>();
		mapBlack.put("CUST_NAME", mapCust.get("NAME"));
		mapBlack.put("ID_CARD_NO", mapCust.get("ID_CARD_NO"));
		mapBlack.put("STATUS", "1");
		mapBlack.put("SETIN_DATE", df.format(new Date()));
		mapBlack.put("GETOUT_DATE", "9999-12-31");
		mapBlack.put("CUST_CLIENT_ID", mapCust.get("ID"));
		//拉黑操作
		Dao.insert("letterOpinion.addblackcust", mapBlack);
	}
	
	//改变初审状态(是否可编辑)
	public void updateLetterStatusCS(String project_id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", project_id);
		map.put("FIRSTTRIAL_STATUS", "2");
		map.put("ENDTRIAL_STATUS", "2");
		Dao.update("letterOpinion.updateLetterStatusCS", map);
	}
	
	//add by lishuo 01-08-2016
	public void updateLetterStatusCS1(String project_id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", project_id);
		map.put("FIRSTTRIAL_STATUS", "1");
		map.put("ENDTRIAL_STATUS", "2");
		Dao.update("letterOpinion.updateLetterStatusCS", map);
	}
	
	//改变终审状态(是否可编辑)
	public void updateLetterStatusZS(String project_id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", project_id);
		map.put("ENDTRIAL_STATUS", "2");
		Dao.update("letterOpinion.updateLetterStatusZS", map);
	}
	
	//add by lishuo 01-08-2016
		public void updateLetterStatusZS1(String project_id){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PROJECT_ID", project_id);
			map.put("ENDTRIAL_STATUS", "1");
			Dao.update("letterOpinion.updateLetterStatusZS", map);
		}
	
	//客户放弃
	@SuppressWarnings("deprecation")
	public void custGiveUp(String project_id){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("ID", project_id);
		Map<String, Object> mapPro = Dao.selectOne("letterOpinion.getProject", mm);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", mapPro.get("CLIENT_ID"));
		Map<String, Object> mapCust = Dao.selectOne("letterOpinion.getCust", map);
		
		Map<String, Object> mapBlack = new HashMap<String, Object>();
		mapBlack.put("CUST_NAME", mapCust.get("NAME"));
		mapBlack.put("ID_CARD_NO", mapCust.get("ID_CARD_NO"));
		mapBlack.put("STATUS", "1");
		mapBlack.put("SETIN_DATE", df.format(new Date()));
		Date date = new Date();
		date.setDate(date.getDate()+30);
		mapBlack.put("GETOUT_DATE", date.toLocaleString());
		mapBlack.put("CUST_CLIENT_ID", mapCust.get("ID"));
		//拉黑操作
		Dao.insert("letterOpinion.addblackcust", mapBlack);
	}
	
	
	//召回操作
	public void Countermand(String project_id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", project_id);
		Dao.update("letterOpinion.Countermand", map);
	}
	
	//判断初审或终审的审批结论是否为空
	public void checkConclusion(String project_id, String type) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PROJECT_ID", project_id);
		
		if(type != null && type.equals("1")){
			List<Map<String, Object>> list = Dao.selectList("letterOpinion.getLetterOpinion", param);
			if(list.size() > 0){
				if(list.get(0).get("FIRSTTRIAL_SPJL") == null || list.get(0).get("FIRSTTRIAL_SPJL").equals("")){
					throw new Exception("初审审批结论不能为空!");
				}
				this.updateLetterStatusCS1(project_id);//add by lishuo 
			}else{
				throw new Exception("初审审批结论不能为空!");
			}
		}else if(type != null && type.equals("2")){
			List<Map<String, Object>> list = Dao.selectList("letterOpinion.getLetterOpinion", param);
			if(list.size() > 0){
				if(list.get(0).get("ENDTRIAL_SPJL") == null || list.get(0).get("ENDTRIAL_SPJL").equals("")){
					this.updateLetterStatusZS1(project_id);
					throw new Exception("终审审批结论不能为空!");
				}
			}
		}
	}
}

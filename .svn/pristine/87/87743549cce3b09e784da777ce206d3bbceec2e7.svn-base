package com.pqsoft.renewalInsure;
/**
 *  续保提醒
 *  @author hanxl
 *  涉及表 INSUR_REMIND
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;

public class RenewalInsure {
	
	public RenewalInsure() {
	}
    
	/**
	  *  项目起租后符合条件的项目往INSUR_REMIND中插入一条续保提醒记录
	  *  a.所有商用车项目、建机上牌车（混凝土臂架泵车和混凝土搅拌运输车）
	  *  b.租赁期限：大于1年
	  *  c.项目状态：在租
	  *  d.租赁到期日：2013年12月31日之后
	  *  e.提醒条件：以起租确认日为节点，每年提醒一次，提醒时间为起租确认日节点
	  */
	public static void insertInsurRemind(String project_id) {
		String basePath = "renewalInsure.";
		String pro_id = project_id;
		Map<String, String> param = new HashMap<String, String>();
		param.put("PRO_ID", pro_id);
		
		Map judgeMap = (Map)Dao.selectOne(basePath + "judgeData", param);
		//判断租赁期限,项目状态
		if ("20".equals(judgeMap.get("STATUS").toString()) && Integer.parseInt(judgeMap.get("LEASE_TERM").toString()) > 12) {
			//先判断是否有重复记录
			if(Dao.selectInt(basePath + "queryCount", param) == 0) {
				List list = Dao.selectList(basePath + "getData",param);
				if(list != null && list.size() > 0){
					Map temp = new HashMap();
					//遍历的时候根据实际型号判断是否上保险
					for (int i = 0; i < list.size(); i++) {
						temp = (Map) list.get(i);
						if(judgeMethod(temp)){//根据规则判断
							Dao.insert(basePath + "insertInsurRemind", temp);
						}
					}
				}
			}
		}
	}
	
	/**
	 *  解决无值 返回 空字符串的问题
	 */
	private static String getNotNullValue(Map map,String key){
		 return map.containsKey(key) ? map.get(key).toString() : "";
	}
	/**
	  *  判断规则 
	  *  所有商用车项目、建机上牌车（混凝土臂架泵车和混凝土搅拌运输车）
	  */
	private static boolean judgeMethod(Map map){
		//特殊型号
		if("混凝土臂架泵车".equals(getNotNullValue(map,"EQUIP_NAME")) || getNotNullValue(map,"EQUIP_NAME").indexOf("混凝土搅拌运输车") > 0 ){
			return true;
		}
		//判断板块  CVP  商用车板块 
		if("CVP".equals(getNotNullValue(map,"BKMC"))){
			return true;
		}
		return false;
	}
}

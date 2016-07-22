package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

/**
 * 是否在黑名单
 * @author caizhognyang
 */
public class IsBlackCustStatus {

	/**
	 * 在黑名单
	 * @param ID
	 */
	public void isBlackCust(String ID){
			Map<String,Object> param = new HashMap<String,Object>();
		    param.put("ID", ID);
		    List<Map<String, Object>> list = Dao.selectList("blackcust.queryClientByProCode",param);
		    if(list.size()==0)return;
		    Map<String, Object> m = list.get(0);
			String ID_CARD_NO=m.get("ID_CARD_NO")+"";
			param.put("ID_CARD_NO", ID_CARD_NO);
			List<Object> list1=Dao.selectList("blackcust.queryBlackCustByIdCardNo",param);
			if(StringUtils.isNotBlank(ID)&&list1.size()>0){
				throw new ActionException("该用户存在黑名单");
			}else{
//				throw new ActionException("该用户不存在黑名单");
			}
	}
}

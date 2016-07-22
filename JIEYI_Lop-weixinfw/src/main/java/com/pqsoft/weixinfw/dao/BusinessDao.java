/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 */
package com.pqsoft.weixinfw.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 *
 */
public class BusinessDao {

	/**
	 * 查询该客户有关的项目
	 * @param openid
	 * @param keyword
	 * @author : LUYANFENG @ 2015年7月7日
	 */
	public List<Map<String,Object>> getMyCreditList(String openid, String keyword) {
		try {
			Map<String,Object> qyrMap = new HashMap<String,Object>();
			qyrMap.put("openid", openid);
			qyrMap.put("keyword", keyword);
			return Dao.selectList("fil_wx_business.getMyCreditList", qyrMap);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * 获取项目明细
	 * @param openid
	 * @param _id
	 * @return
	 * @author LUYANFENG @ 2015年7月22日
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMyCreditDetail(String openid, String _id) {
		try {
			Map<String,Object> qyrMap = new HashMap<String,Object>();
			qyrMap.put("openid", openid);
			qyrMap.put("id", _id);
			return (Map<String, Object>) Dao.selectList("fil_wx_business.getMyCreditList", qyrMap).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}

	/**
	 * 历史项目
	 * @param openid
	 * @param keyword
	 * @return
	 * @author LUYANFENG @ 2015年8月6日
	 * @aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	 */
	public List<Map<String, Object>> myHistoryCreditList(String openid,String keyword) {
		try {
			Map<String,Object> qyrMap = new HashMap<String,Object>();
			qyrMap.put("openid", openid);
			qyrMap.put("keyword", keyword);
			qyrMap.put("status", "in (100, 27, 50)");
			return Dao.selectList("fil_wx_business.getMyCreditList", qyrMap);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * 进行中的项目
	 * @param openid
	 * @param keyword
	 * @return
	 * @author LUYANFENG @ 2015年8月6日
	 */
	public List<Map<String, Object>> myPaying(String openid, String keyword) {
		try {
			Map<String,Object> qyrMap = new HashMap<String,Object>();
			qyrMap.put("openid", openid);
			qyrMap.put("keyword", keyword);
			qyrMap.put("status", "not in (100, 27, 50)");
			return Dao.selectList("fil_wx_business.getMyCreditList", qyrMap);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * <pre>
	 * 获取对账单
	 * <pre>
	 * @param openid
	 * @author LUYANFENG @ 2015年8月12日
	 */
	public Page getMyBills(Map<String, Object> map) {
		Page page = new Page(map);
		List<Object> selectList = Dao.selectList("fil_wx_business.getMyBills", map);
		page.addDate(JSONArray.fromObject(selectList ), Dao.selectInt("fil_wx_business.getMyBills_count", map));
		return page;
	}

	/**
	 * <pre>
	 * 获取对账单明细
	 * <pre>
	 * @param map
	 * @return
	 * @author LUYANFENG @ 2015年8月12日
	 */
	public List<Map<String,Object>> getMyBillDetail(Map<String, Object> map) {
		List<Map<String,Object>> selectList = Dao.selectList("fil_wx_business.getMyBillDetail", map);
		return selectList;
	}

}

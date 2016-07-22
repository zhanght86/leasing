package com.pqsoft.renterpool.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class ResidualMoneyService {

	private final String xmlPath = "residualMoney.";
	
	/**
	 * 承租人可用余款池查询
	 * toMgDeductData
	 * @date 2013-10-31 上午11:13:50
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page toMgResidualMoney(Map<String, Object> map) {
		Page page = new Page(map);
		// 首期数据获取
		List<Map<String, Object>> list = Dao.selectList(xmlPath + "toMgResidualMoney", map);
		List<Object> kh_type = (List) new DataDictionaryMemcached().get("客户类型");
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String, Object> m = (Map<String, Object>) list.get(i);
			json.put("CLIENT_ID", m.get("CLIENT_ID"));
			json.put("CUST_NAME", m.get("CUST_NAME"));// 客户名称
			if (kh_type != null) {//客户类型
				for (int j = 0; j < kh_type.size(); j++) {
					Map<String, Object> kh = (Map<String, Object>) kh_type.get(j);
					if (kh.get("CODE").equals(m.get("TYPE").toString())) {
						json.put("TYPE", kh.get("FLAG"));
					}
				}
			}
			json.put("BASE_MONEY", m.get("BASE_MONEY"));//总余额 
			json.put("CANUSE_MONEY", m.get("CANUSE_MONEY"));// 可用金额
			json.put("CUST_ID", m.get("CUST_ID"));//客户编号
			array.add(json);
		}
		page.addDate(array, Dao.selectInt(xmlPath+"toMgResidualMoneyNu", map));// 计数
		return page;
	}
	
	/**
	 * 承租人余款池明细
	 * @param param
	 * @return
	 */
	public List<Object> getDetailList(Map<String,Object> param){
		param.put("DB_STATUS", "资金池状态");
		return Dao.selectList(xmlPath+"getDetailList", param);
	} 
	
	/**
	 * 修改余款池中项目款的冻结状态
	 * doChangeFreezeStatus
	 * @date 2013-10-31 下午02:35:15
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doChangeFreezeStatus(Map<String, Object> map){
		return Dao.update(xmlPath+"toChangeFreezeStatus",map);
	}
	
	/**
	 * 获取退款单序列
	 * @return
	 */
	public String getRefundDanSeq(){
		return Dao.getSequence("SEQ_FI_REFUND_HEAD");
	}
	
	/**
	 * 插入退款
	 * addRefundCUST
	 * @date 2013-10-31 下午05:13:34
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int addRefundCUST(Map<String,Object> map){
		return Dao.insert(xmlPath+"addRefundCUST", map);
	}
	
	/**
	 * 更新资金池相关状态By Re_id
	 * @param param
	 * @return
	 */
	public int updateCUSTRefundId(Map<String,Object> param){
		return Dao.update(xmlPath+"updateCUSTRefundId",param);
	}
	
	/**
	 * 承租人余款冲抵租金列表信息
	 * @param param
	 * @return
	 */
	public Page toMgCDdetail(Map<String,Object> param ){
		param.put("ZJ_STATUS", "资金池冲抵状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(xmlPath+"toMgChargeAgainst",param));
		page.addDate(array, Dao.selectInt(xmlPath+"toMgChargeAgainstNu", param));
		return page;
	}
	
	/**
	 * 承租人余款冲抵租金列表信息
	 * @param param
	 * @return
	 */
	public Page toMgRefund(Map<String,Object> param ){
		param.put("TK_STATUS", "退款单状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(xmlPath+"toMgRefund",param));
		page.addDate(array, Dao.selectInt(xmlPath+"toMgRefundNu", param));
		return page;
	}
	
	/**
	 * 跟新退款单中的信息
	 * @param param
	 * @return
	 */
	public int updateRefundMess(Map<String,Object> param){
		return Dao.update(xmlPath+"updateRefundMess", param);
	}
	
	/**
	 * 获取承租人余款池中的数据
	 * @param param
	 * @return
	 * 
	 */
	public List<Object> getCUSTBailList(Map<String,Object> param){
		param.put("DB_STATUS", "资金池状态");
		return Dao.selectList(xmlPath+"getDetailList", param);
	}
	
	/**
	 * 删除退款单信息
	 * @param param
	 * @return
	 */
	public int delRefundMess(Map<String,Object> param){
		return Dao.delete(xmlPath+"delRefundMess", param);
	}	
	
	/**
	 * 根据客户id获取客户银行信息
	 * getOpenBank
	 * @date 2013-11-19 下午01:39:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getOpenBank(Map<String,Object> param){
		return Dao.selectList(xmlPath+"getOpenBank", param);
	}
}

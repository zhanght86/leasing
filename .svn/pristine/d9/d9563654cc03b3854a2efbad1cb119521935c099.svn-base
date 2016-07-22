package com.pqsoft.base.grantCredit.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class SupplierCreditService {
	private String basePath = "SupplierCredit.";

	/**
	 * 查询授信管理
	 * 
	 * @param param
	 * @return
	 */
	public Page getCreditPage(Map<String, Object> param) {
		return PageUtil.getPage(param, basePath + "querySupCreditList",
				basePath + "querySupCreditCount");
	}

	/**
	 * 添加经销商授信
	 * 
	 * @param map
	 * @return
	 * @author King 2015年3月19日
	 */
	public int doAddSupplierCredit(Map<String, Object> map) {
		map.put("CUGP_ID", Dao.getSequence("SEQ_CUST_GRANTPLAN"));
		return Dao.insert(basePath + "doAddSupplierCredit", map);
	}

	/**
	 * 根据授信id查询授信数据
	 * 
	 * @param ID
	 * @return
	 * @author King 2015年3月19日
	 */
	public Map<String, Object> queryCreditInfoById(String ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUGP_ID", ID);
		return Dao.selectOne(basePath + "queryCreditInfoById", map);
	}

	/**
	 * 查询主体授信情况
	 * @param TYPE 主体类型  类型（1代表客户，2代表担保人，3供应商，4厂商）
	 * @param CUST_ID  主体id
	 * @return
	 * @author King 2015年4月8日
	 */
	public List<Map<String, Object>> queryCreditInfoBySubject(String TYPE,
			String CUST_ID) {
		if (StringUtils.isNotBlank(TYPE) && StringUtils.isNotBlank(CUST_ID)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TYPE", TYPE);
			map.put("CUST_ID", CUST_ID);
			return Dao.selectList(basePath + "queryCreditInfoBySubject", map);
		} else
			return null;
	}
	
	/**
	 * 扣除主体授信余额
	 * @param RZE 需要扣除的金额  融资额
	 * @param TYPE 主体类型  类型（1代表客户，2代表担保人，3供应商，4厂商）
	 * @param CUST_ID  主体id
	 * @param CUGP_ID 授信唯一标识 id
	 * @return
	 * @author King 2015年4月8日
	 */
	public int deductSubjectCreditLastMoney(String RZE,String TYPE,String CUST_ID,String CUGP_ID){
		//查看授信信息 
		Map<String,Object> credit = this.queryCreditInfoById(CUGP_ID);
		if (StringUtils.isNotBlank(TYPE) && StringUtils.isNotBlank(CUST_ID)&&StringUtils.isNotBlank(RZE)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TYPE", TYPE);
			map.put("CUST_ID", CUST_ID);
			map.put("RZE", RZE);
			map.put("CUGP_ID", CUGP_ID);
			//添加授信日志  yangxue 2015-07-09
			if(!"".equals(credit.get("LAST_PRICE").toString())){
				Double last_price = Double.parseDouble(credit.get("LAST_PRICE").toString()) - Double.parseDouble(RZE);
				String memo = "创建项目授信金额减少, 剩余授信金额由"+new BigDecimal(credit.get("LAST_PRICE").toString()).toPlainString()+"变更为"+new BigDecimal(last_price).toPlainString()+"; 变更金额为:"+new BigDecimal(RZE).toPlainString();;
			    map.put("GRANT_PRICE", credit.get("GRANT_PRICE").toString());//授信总额
			    map.put("LAST_PRICE", last_price);//剩余授信金额
			    map.put("MEMO", memo);//备注
			    map.put("CREATE_ID", Security.getUser().getId());//备注
			    Dao.insert("creditLog.addCreditLog", map);//添加授信日志
			}
			
			return Dao.update(basePath + "deductSubjectCreditLastMoney", map);
		}else{
			return 0;
		}
	}

	/**
	 * 回笼主体授信余额  针对循环授信而言
	 * @param money 回笼资金金额
	 * @param TYPE TYPE 主体类型  类型（1代表客户，2代表担保人，3供应商，4厂商）
	 * @param CUST_ID CUST_ID  主体id
	 * @param CUGP_ID CUGP_ID 授信唯一标识 id
	 * @return
	 * @author King 2015年4月9日
	 */
	public int steamSubjectCreditLastMoney(String money,String TYPE,String CUST_ID,String CUGP_ID){
		if (StringUtils.isNotBlank(TYPE) && StringUtils.isNotBlank(CUST_ID)&&StringUtils.isNotBlank(money)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TYPE", TYPE);
			map.put("CUST_ID", CUST_ID);
			map.put("MONEY", money);
			map.put("CUGP_ID", CUGP_ID);
			return Dao.update(basePath + "steamSubjectCreditLastMoney", map);
		}else{
			return 0;
		}	
	}
	
	/**
	 * 修改授信余额及控制额度
	 * @param map
	 * @return
	 * @author King 2015年4月9日
	 */
	public int updateCreditGrantAndLastPrice(Map<String,Object> map){
		return Dao.update(basePath+"updateCreditGrantAndLastPrice", map);
	}
	// /**
	// * 查询单条授信记录
	// * @param param
	// * @return
	// */
	// public Map<String,Object> getOneCredit(Map<String,Object> param){
	// param.put("DIC_TYPE", "供应商授信状态");
	// return Dao.selectOne(basePath+"getOneCredit", param);
	// }
	//
	/**
	 * 添加授信记录
	 * 
	 * @param param
	 * @return
	 */
	public int addCreditMess(Map<String, Object> param) {
		return Dao.insert(basePath + "addCreditMess", param);
	}

	/**
	 * 更新授信记录
	 * 
	 * @param param
	 * @return
	 */
	public int updateCreditMess(Map<String, Object> param) {
		return Dao.update(basePath + "updateCreditMess", param);
	}

	public int updateCreditStatus(Map<String, Object> param) {
		return Dao.update(basePath + "updateCreditStatus", param);
	}

	/**
	 * 获取授信更改日志
	 * 
	 * @param param
	 * @return
	 */
	public List<Object> getCreditLogs(Map<String, Object> param) {
		return Dao.selectList(basePath + "getCreditLog", param);
	}

	/**
	 * 添加授信操作日志
	 * 
	 * @param param
	 * @return
	 */
	public int addCreditLog(Map<String, Object> param) {
		return Dao.insert(basePath + "addCreditLog", param);
	}

	/**
	 * 申请单分页查询
	 */
	public Page getApplyPage(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath
				+ "getApplyDanList", param));
		page.addDate(array, Dao.selectInt(basePath + "getApplyDanCount", param));
		return page;
	}

	/**
	 * 获取单条申请单信息
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> getApplyDan(Map<String, Object> param) {
		param.put("DIC_TYPE", "供应商授信状态");
		return Dao.selectOne(basePath + "getOneApplyDan", param);
	}

	/**
	 * 获取申请单序列
	 * 
	 * @return
	 */
	public int getApplySeq() {
		return Dao.selectInt(basePath + "getApplySeq", null);
	}

	/**
	 * 添加申请单记录
	 * 
	 * @param param
	 * @return
	 */
	public int addApplyDan(Map<String, Object> param) {
		return Dao.insert(basePath + "addApplyDan", param);
	}

	/**
	 * 更新申请单信息
	 * 
	 * @param param
	 * @return
	 */
	public int updateApplyDan(Map<String, Object> param) {
		return Dao.update(basePath + "updateApplyDan", param);
	}

	public int updateSupStatus(Map<String, Object> param) {
		return Dao.update("Suppliers.updateSuppStatus", param);
	}

	public List<Object> getAppOperate(Map<String, Object> param) {
		return Dao.selectList(basePath + "getApplyOperate", param);
	}

	/**
	 * 立项授信额度验证
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> checkCreditMoney(Map<String, Object> param) {
		Boolean flag = true;
		Double user_Price = 0d;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 查询授信规则设定
		Double rate = Dao.selectOne(basePath + "getLimitRate", param);
		// 查询授信信息
		param.put("DIC_TYPE", "供应商授信状态");
		Map<String, Object> creditMess = Dao.selectOne(basePath
				+ "getOneCredit", param);
		Double canCreditMoney = 0d;
		if (param.containsKey("GUARANTEE") && param.get("GUARANTEE") != null
				&& "5assuretype".equals(param.get("GUARANTEE").toString())) {
			param.put("IF_SINGLE", "yes");
			Double single_user_money = Double.parseDouble(Dao.selectOne(
					basePath + "getUserMoney", param).toString());
			Double single_money = creditMess.get("SINGLE_MEET_MONEY") != null
					&& !"".equals(creditMess.get("SINGLE_MEET_MONEY")
							.toString()) ? Double.parseDouble(creditMess.get(
					"SINGLE_MEET_MONEY").toString()) : 0d;
			canCreditMoney = single_money - single_user_money;
		} else {
			param.put("IF_NOTSINGLE", "not");
			Double usered_money = Double.parseDouble(Dao.selectOne(
					basePath + "getUserMoney", param).toString());
			Double back_price = Double.parseDouble(Dao.selectOne(
					basePath + "getBackMoney", param).toString());
			Double total_money = creditMess.containsKey("TOTAL_MONEY")
					&& creditMess.get("TOTAL_MONEY") != null ? Double
					.parseDouble(creditMess.get("TOTAL_MONEY").toString()) : 0d;
			canCreditMoney = total_money - usered_money + back_price;
		}
		if (Double.parseDouble(param.get("MONEYALL").toString()) * rate <= canCreditMoney) {
			user_Price = Double.parseDouble(param.get("MONEYALL").toString())
					* rate;
			flag = true;
		} else {
			user_Price = Double.parseDouble(param.get("MONEYALL").toString())
					* rate;
			flag = false;
		}
		paramMap.put("flag", flag);
		paramMap.put("Price", user_Price);
		return paramMap;
	}

	/**
	 * 添加项目扣除授信日志
	 * @param map
	 * @author King 2015年6月6日
	 */
	public void doAddProjectGRANTPLAN(Map<String,Object> map){
		Dao.insert(basePath+"doAddProjectGRANTPLAN", map);
	}
	public Page searchClient(Map<String, Object> map){
		return PageUtil.getPage(map, basePath + "searchClientList",
				basePath + "searchClientCount");
	}
	public int insertBZJ_Info(Map<String, Object> map){
		return Dao.insert(basePath+"insertJXS_BZJ_Info", map);
	}
	public Page selectDetailInfo(Map<String, Object> map){
		return PageUtil.getPage(map, basePath + "selectDetailList", 
				basePath + "selectDetailCount");
	}
	public Map<String,Object> selectDetailData(Map<String,Object> map){
		return Dao.selectOne(basePath+"selectDetailData", map);
	}
}

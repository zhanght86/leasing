package com.pqsoft.screened.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.util.Format;

public class ScreenedService {

	private final String xmlPath = "screened.";
	private Logger logger = Logger.getLogger(ScreenedService.class);
	
	/**
	 * 获取符合融资条件的数据
	 * getConformPayList1
	 * @date 2013-10-15 下午06:46:04
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Object> getConformPayList1(Map<String, Object> map){
		logger.info(map.toString());
		// 融资基数
		String pay_base = map.get("PAY_BASE").toString();
		if ("0".equals(pay_base)) {			
			map.put("BASE_TYPE", "租金");			
		} else if ("1".equals(pay_base)) {
			map.put("BASE_TYPE", "本金");
		}
		
		map.put("GEYUE", "个月");
		List<Object> Paymanagelist = null;
		try {			
			Paymanagelist = (List<Object>) Dao.selectList(xmlPath+"getSceenedData", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Paymanagelist;
	}
	
	/**
	 * 获取excel数据
	 * getConformPayListExcel
	 * @date 2013-10-17 下午03:35:48
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Object> getConformPayListExcel(Map<String, Object> map) {
		logger.info(map.toString());
		// 融资基数
		String pay_base = map.get("FILE_PAY_BASE").toString();
		if ("0".equals(pay_base)) {
			map.put("BASE_TYPE", "租金");
		} else if ("1".equals(pay_base)) {
			map.put("BASE_TYPE", "本金");
		}
		map.put("GEYUE", "个月");
		logger.info(map.toString());
		List<Object> Paymanagelist = CalculatePayExcel(Dao.selectList(xmlPath+"getSceenedDataExcel", map),map);
		return Paymanagelist;
	}
	
	/**
	 * @Description: TODO 计算每张支付表的租金和 或 本金和
	 * CalculatePay
	 * @date 2013-10-15 下午06:46:20
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> CalculatePay(List<Object> list, Map<String, Object> map) throws Exception {
		Format Format = new Format();
		
		// 支付表实际融资额度
		double reFund = 0d;
		//项目金额
		Double projectFund = Double.valueOf(null == map.get("PROJECT_MONEY") || "".equals(map.get("PROJECT_MONEY")) ? "0.00" : map.get(
				"PROJECT_MONEY").toString());
		
		// 方式条件
		List<Object> CalculateList = new ArrayList<Object>();
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("PAY_WAY", map.get("PAY_WAY"));
		Map<String,Object> cm = (Map<String,Object>) Dao.selectOne(xmlPath+"selectCondition", map);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> payMap = (Map<String,Object>) list.get(i);
			
			m.put("PAYLIST_CODE", payMap.get("PAYLIST_CODE"));
			
			if(this.isALLConOk(m,cm)){
				payMap.put("ALLCONOK", "1");
			}else{
				payMap.put("ALLCONOK", "0");
			}
			
			//去掉条件查询 
			boolean ptype = false;
			if (ptype) {
				
				reFund += Double.parseDouble(payMap.get("WS_MONEY").toString());
				if (projectFund == 0) {
					CalculateList.add(payMap);
				} else {
					if (projectFund > reFund) {
						
						System.out.println(Format.currency(reFund));
						
						payMap.put("PAYTYPE", "1");
						CalculateList.add(payMap);
					} else {
						payMap.put("PAYTYPE", "1");
						CalculateList.add(payMap);
						break;
					}
				}
			} else {
				payMap.put("PAYTYPE", "0");
				CalculateList.add(payMap);
			}
		}
		map.put("ActualMoney", 0 == projectFund ? reFund : projectFund);
		return CalculateList;
	}
	
	/**
	 *  上传excel计算
	 * CalculatePayExcel
	 * @date 2013-10-17 下午03:39:27
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> CalculatePayExcel(List<Object> list, Map<String, Object> map){
		Format Format = new Format();
		logger.info("------list ====" + list.size());
		// 支付表实际融资额度
		double reFund = 0d;
		Double projectFund = Double.valueOf(null == map.get("FILE_PROJECT_MONEY") || "".equals(map.get("FILE_PROJECT_MONEY")) ? "0.00" : map.get(
				"PROJECT_MONEY").toString());
		logger.info("project_fund(项目)="+Format.currency(projectFund));
		List<Object> CalculateList = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> payMap = (Map) list.get(i);
			System.out.println(payMap.get("SUM_MONEY").toString());
			reFund += Double.parseDouble(payMap.get("SUM_MONEY").toString());

			if (projectFund == 0) {
				payMap.put("PAYTYPE", "1");
				CalculateList.add(payMap);
			} else {
				if (projectFund > reFund) {
					System.out.println(Format.currency(reFund));
					payMap.put("PAYTYPE", "1");
					CalculateList.add(payMap);
				} else {
					payMap.put("PAYTYPE", "1");
					CalculateList.add(payMap);
					break;
				}
			}
		}
		map.put("ActualMoney", 0 == projectFund ? reFund : projectFund);
		logger.info("------CalculateList ====" + CalculateList.size());
		return CalculateList;
	}
	
	/**
	 * @Description: TODO 支付表条件是否齐全
	 * isALLConOk
	 * @date 2013-10-16 下午01:47:56
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean isALLConOk(Map<String, Object> map,Map<String, Object> cm){
		boolean flag = true;
		if (!(null == cm)) {
			List<Map<String, Object>> lst = JSONArray.fromObject(cm.get("CONDITIONONE"));

			StringBuffer sb = new StringBuffer();
			for (Map<String, Object> map2 : lst) {
				sb.append(map2.get("contionId") + ",");
			}
			String str = sb.substring(0, sb.lastIndexOf(","));
			System.out.println("str=" + str);
			Map<String, Object> cm2 = new HashMap<String, Object>();
			cm2.putAll(cm);
			cm2.put("CONDITIONONE", str);
			List<Map> conditionList = (List) Dao.selectList(xmlPath+"selectChinaCondition", cm2);
			List<Map> payCondition = (List) Dao.selectList(xmlPath+"selectPayFlag", map);
			for (int i = 0; i < conditionList.size(); i++) {
				Map m = conditionList.get(i);
				if ("管理费比例大于等于".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("保证金比例大于等于".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("保证金加首期租金比例大于等于".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("首期租金比例大于等于".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("法人".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("个人".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("政府机构".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行借贷合同".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行担保合同".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行综合授信协议".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行抵押合同".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行质押合同".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行提款申请书".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("动产抵押登记证书".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else {
					String con = m.get("CNAME").toString();
					if (0 == payCondition.size()) {
						m.put("TYPE", "0");
						flag = false;
					} else {
						boolean type = false;
						for (Map lm : payCondition) {
							if (con.equals(lm.get("DOSSIERNAME"))) {
								type = true;
								break;
							}
						}
						if (type) {
							m.put("TYPE", "1");
							flag = true;
						} else {
							m.put("TYPE", "0");
							flag = false;
							break;
						}
					}
				}
			}
		}

		return flag;
	}
	
	/**
	 * 插入融资项目主表
	 * createProjectHead
	 * @date 2013-10-17 上午10:57:04
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public String createProjectHead(Map<String, Object> map) {		
		Map<String,Object>  param = new HashMap<String,Object>();
		//获取主键序列
		String projectId = Dao.getSequence("SEQ_REFINANCE_PROJECT");
		map.put("ID", projectId);
		//融资机构的融资方式查询
		Map<String, Object> BAILOUTWAY = Dao.selectOne(xmlPath+"getBailoutway", map);
		if (BAILOUTWAY != null) param.putAll(BAILOUTWAY);
		param.putAll(map);
		//插入融资项目
		int i = Dao.insert(xmlPath+"insertProjectHead", param);
		if(i>0){
			return projectId;
		}else {
			return null;
		}
	}
	
	/**
	 * 复制支付表
	 * transplantsRefundPay
	 * @date 2013-10-17 上午10:58:49
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int transplantsRefundPay(Map<String, Object> map){
		return Dao.insert(xmlPath+"transplantsRefundPay", map);
	}
	
	/**
	 * 复制支付表详细
	 * transplantsRefundPayDetail
	 * @date 2013-10-17 上午11:00:04
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void transplantsRefundPayDetail(Map<String, Object> map) {
		Dao.insert(xmlPath+"transplantsRefundPayDetail", map);
	}
	
	/**
	 * 复制设备
	 * transplantsRefundPayEquipment
	 * @date 2013-10-17 上午11:00:37
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void transplantsRefundPayEquipment(Map<String, Object> map) {
		Dao.insert(xmlPath+"transplantsRefundPayEquipment", map);
	}
	
	/**
	 * 融资机构每个项目的融资基数
	 * addOrganrefund
	 * @date 2013-10-17 下午12:03:32
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void addOrganrefund(Map<String, Object> map) {
		Dao.insert(xmlPath+"addOrganrefund", map);
	}
	
	/**
	 * 融资机构档案调用
	 * addRfDossier
	 * @date 2013-10-17 上午11:04:37
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int addRfDossier(Map<String, Object> map) {
		return Dao.insert(xmlPath+"addRfDossier", map);
	}
	
	/**
	 * 条件查询
	 * checkCondition
	 * @date 2013-10-17 下午04:47:01
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List checkCondition(Map<String, Object> map){
		List<Map<String,Object>> condition = new ArrayList<Map<String,Object>>();
		Map<String,Object> cm = (Map<String,Object>) Dao.selectOne(xmlPath+"selectCondition", map);
		if (!(null == cm)) {
			List<Map<String, Object>> lst = JSONArray.fromObject(cm.get("CONDITIONONE"));

			StringBuffer sb = new StringBuffer();
			for (Map<String, Object> map2 : lst) {
				sb.append(map2.get("contionId") + ",");
			}
			String str = sb.substring(0, sb.lastIndexOf(","));
			System.out.println("str=" + str);
			cm.put("CONDITIONONE", str);
			List<Map> conditionList = (List) Dao.selectList(xmlPath+"selectChinaCondition", cm);
			List<Map> payCondition = (List) Dao.selectList(xmlPath+"selectPayFlag", map);
			for (int i = 0; i < conditionList.size(); i++) {
				Map m = conditionList.get(i);
				if ("管理费比例大于等于".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("保证金比例大于等于".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("保证金加首期租金比例大于等于".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("首期租金比例大于等于".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("法人".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("个人".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("政府机构".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行借贷合同".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行担保合同".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行综合授信协议".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行抵押合同".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行质押合同".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("银行提款申请书".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else if ("动产抵押登记证书".equals(m.get("CNAME"))) {
					conditionList.remove(i);
				} else {
					String con = m.get("CNAME").toString();
					if (0 == payCondition.size()) {
						m.put("TYPE", "0");
						condition.add(m);
					} else {
						boolean type = false;
						for (Map lm : payCondition) {
							if (con.equals(lm.get("DOSSIERNAME"))) {
								type = true;
								break;
							}
						}
						if (type) {
							m.put("TYPE", "1");
							condition.add(m);
						} else {
							m.put("TYPE", "0");
							condition.add(m);
						}

					}

				}
			}
		}

		return condition;
	}
	
	
	/**
	 * @Description: TODO 查询机构
	 * selAgency
	 * @date 2013-10-15 下午06:03:18
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object selAgency(Map<String, Object> param){
		return Dao.selectOne(xmlPath+"selAgency", param);
	}
	
	/**
	 * @Description: TODO 查询融资方式
	 * getRefundBailoutway
	 * @date 2013-10-15 下午06:03:24
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String,Object>> getRefundBailoutway(){
		String TYPE = "再融资方式";
		return Dao.selectList(xmlPath+"getRefundBailoutway", TYPE);
	}
	
	/**
	 * 项目编号
	 * getProjectCode
	 * @date 2013-10-16 下午07:17:35
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public String getProjectCode() {
		Date date = new Date();
		SimpleDateFormat dsf1 = new SimpleDateFormat("yyyyMMddHHmm");
		return "RZ" + dsf1.format(date);
	}
	
	/**
	 *  购物车是否已存在
	 * isExistInCart
	 * @date 2013-10-18 下午02:40:31
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int isExistInCart(Map<String,Object> map) {
		return Dao.selectInt(xmlPath+"isExistInCart", map);
	}
	
	/**
	 * 新增购物车数据
	 * addCartByPayUser
	 * @date 2013-10-18 下午02:41:52
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int addCartByPayUser(Map<String,Object> map){
		return Dao.insert(xmlPath+"addCartByPayUser", map);
	}
	
	
	/**
	 * 查询用户购物车
	 * @author 吴剑东
	 * @param map
	 * @return
	 * @throws Exception
	 * @date 2013-6-9
	 */
	public List<Object> selCartByUser(Map<String, Object> map){
		return Dao.selectList(xmlPath+"selCartByUser", map);
	}
	
	/**
	 * 计算用户购物车总和
	 * @author 吴剑东
	 * @param map
	 * @return
	 * @throws Exception
	 * @date 2013-6-9
	 */
	public Object getSumCartByUser(Map<String, Object> map){
		return Dao.selectOne(xmlPath+"getSumCartByUser", map);
	}
	
	/**
	 * 根据用户id删除购物车
	 * delCartByUser
	 * @date 2013-10-18 下午02:52:15
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object delCartByUser(Map<String,Object> map) {
		return Dao.delete(xmlPath+"delCartByPay",map);
	}
	
	/**
	 * 支付表ID 用户ID 删除购物车
	 * delCartByPayUser
	 * @date 2013-10-18 下午03:08:32
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int delCartByPayUser(Map<String,Object> map){
		return Dao.delete(xmlPath+"delCartByPayUser", map);
	}
}

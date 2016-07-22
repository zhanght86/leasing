package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.base.grantCredit.service.SupplierCreditService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.StringUtils;

/**
 * 立项, 合同, 放款扣除授信金额 规则: 在立项,合同, 放款流程结束后进行授信额度的扣除, 扣除的金额为项目的融资额.
 * 
 * @author yangxue 2015-07-16
 *
 */
public class CreditDeduction {

	/**
	 * 根据项目ID, 在立项,或合同流程中扣除授信额度
	 * 规则: 扣除的授信金额为:融资额
	 * @author yangxue 2015-07-16
	 * @param PROJECT_ID
	 */
	public void creditDeduction(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {

			Map<String, Object> m = new HashMap<String, Object>();
			m.put("PROJECT_ID", PROJECT_ID);

			// 扣除经销商授信 king 2015-04-09
			Map<String, Object> SuppMap = new projectService()
					.querySuppByProjectID(PROJECT_ID);
			String CUST_ID = SuppMap.get("SUPPLIER_ID") + "";
			if (StringUtils.isNotBlank(CUST_ID)) {
				String RZE = UtilFinance.formatString(new projectService()
						.selectProjectDetails(m).get("FINANCE_TOPRIC"), 2);
				SupplierCreditService scservice = new SupplierCreditService();
				List<Map<String, Object>> list = scservice
						.queryCreditInfoBySubject("3", CUST_ID);
				if (StringUtils.isNotBlank(list) && !list.isEmpty()
						&& list.size() >= 1) {
					boolean flag = false;
					for (Map<String, Object> map : list) {
						if (StringUtils.isNotBlank(map.get("GRANT_PRICE"))) {
							String GRANT_PRICE = map.get("GRANT_PRICE") + "";
							if (Double.parseDouble(RZE) <= Double
									.parseDouble(GRANT_PRICE)) {
								scservice.deductSubjectCreditLastMoney(RZE,
										"3", CUST_ID, map.get("CUGP_ID") + "");
								flag = true;
								Map<String, Object> mm = new HashMap<String, Object>();
								mm.put("PROJECT_ID", PROJECT_ID);
								mm.put("TYPE", "3");
								mm.put("CUGP_ID", map.get("CUGP_ID"));
								mm.put("CUST_ID", CUST_ID);
								mm.put("MONEY", RZE);
								scservice.doAddProjectGRANTPLAN(mm);
								break;
							}
						}
					}
					if (flag == false) {
						throw new ActionException(
								"该项目的经销商授信余额已低于项目融资额，不足以支持该项目的继续进行");
					}
				}
			}

			// 扣除厂商授信 King 2015-06-06
			String COMPANY_ID = SuppMap.get("COMPANY_ID") + "";
			if (StringUtils.isNotBlank(COMPANY_ID)) {
				String RZE = UtilFinance.formatString(new projectService()
						.selectProjectDetails(m).get("FINANCE_TOPRIC"), 2);
				SupplierCreditService scservice = new SupplierCreditService();
				List<Map<String, Object>> list = scservice
						.queryCreditInfoBySubject("4", COMPANY_ID);
				if (StringUtils.isNotBlank(list) && !list.isEmpty()
						&& list.size() >= 1) {
					boolean flag = false;
					for (Map<String, Object> map : list) {
						if (StringUtils.isNotBlank(map.get("GRANT_PRICE"))) {
							String GRANT_PRICE = map.get("GRANT_PRICE") + "";
							if (Double.parseDouble(RZE) <= Double
									.parseDouble(GRANT_PRICE)) {
								scservice.deductSubjectCreditLastMoney(RZE,
										"4", COMPANY_ID, map.get("CUGP_ID")
												+ "");
								flag = true;
								Map<String, Object> mm = new HashMap<String, Object>();
								mm.put("PROJECT_ID", PROJECT_ID);
								mm.put("TYPE", "4");
								mm.put("CUGP_ID", map.get("CUGP_ID"));
								mm.put("CUST_ID", COMPANY_ID);
								mm.put("MONEY", RZE);
								scservice.doAddProjectGRANTPLAN(mm);
								break;
							}
						}
					}
					if (flag == false) {
						throw new ActionException(
								"该项目的厂商授信余额已低于项目融资额，不足以支持该项目的继续进行");
					}
				}
			}

			// 扣除客户授信 King 2015-06-06
			String CLIENT_ID = SuppMap.get("CLIENT_ID") + "";
			if (StringUtils.isNotBlank(CLIENT_ID)) {
				String RZE = UtilFinance.formatString(new projectService()
						.selectProjectDetails(m).get("FINANCE_TOPRIC"), 2);
				SupplierCreditService scservice = new SupplierCreditService();
				List<Map<String, Object>> list = scservice
						.queryCreditInfoBySubject("1", CLIENT_ID);
				if (StringUtils.isNotBlank(list) && !list.isEmpty()
						&& list.size() >= 1) {
					boolean flag = false;
					for (Map<String, Object> map : list) {
						if (StringUtils.isNotBlank(map.get("GRANT_PRICE"))) {
							String GRANT_PRICE = map.get("GRANT_PRICE") + "";
							if (Double.parseDouble(RZE) <= Double
									.parseDouble(GRANT_PRICE)) {
								scservice
										.deductSubjectCreditLastMoney(RZE, "1",
												CLIENT_ID, map.get("CUGP_ID")
														+ "");
								flag = true;
								Map<String, Object> mm = new HashMap<String, Object>();
								mm.put("PROJECT_ID", PROJECT_ID);
								mm.put("TYPE", "1");
								mm.put("CUGP_ID", map.get("CUGP_ID"));
								mm.put("CUST_ID", CLIENT_ID);
								mm.put("MONEY", RZE);
								scservice.doAddProjectGRANTPLAN(mm);
								break;
							}
						}
					}
					if (flag == false) {
						throw new ActionException(
								"该项目的客户授信余额已低于项目融资额，不足以支持该项目的继续进行");
					}
				}
			}
		} else {
			throw new ActionException("项目ID为空,流程授信扣除失败, 请联系管理员");
		}
	}
	
	/**
	 * 根据放款id, 在放款流程中扣除授信金额, 当项目进行多次授信时, 项目在第一次放款是扣除授信
	 * 规则: 扣除的授信金额为:融资额
	 * @author yangxue 2015-07-16
	 * @param PROJECT_ID
	 */
	public void creditDeduction_FK(String id) {
		if (StringUtils.isNotBlank(id)) {

			Map<String, Object> m = new HashMap<String, Object>();
			m.put("PAYMENT_ID", id);
			
			//判断项目是否为二次放款
			int paymentCount = new projectService().toSearchPayment(m);
			if(paymentCount>1){
				System.out.println("该项目已做过授信扣除, 该项目为二次放款");
			}else {
				// 扣除经销商授信 yangxue 2015-07-16
				Map<String, Object> SuppMap = Dao.selectOne("project.querySuppByProjectCode", id);
				String CUST_ID = SuppMap.get("SUPPLIER_ID") + "";
				if (StringUtils.isNotBlank(CUST_ID)) {
					String RZE = UtilFinance.formatString(new projectService()
							.selectProjectDetails(m).get("FINANCE_TOPRIC"), 2);
					SupplierCreditService scservice = new SupplierCreditService();
					List<Map<String, Object>> list = scservice
							.queryCreditInfoBySubject("3", CUST_ID);
					if (StringUtils.isNotBlank(list) && !list.isEmpty()
							&& list.size() >= 1) {
						boolean flag = false;
						for (Map<String, Object> map : list) {
							if (StringUtils.isNotBlank(map.get("GRANT_PRICE"))) {
								String GRANT_PRICE = map.get("GRANT_PRICE") + "";
								if (Double.parseDouble(RZE) <= Double
										.parseDouble(GRANT_PRICE)) {
									scservice.deductSubjectCreditLastMoney(RZE,
											"3", CUST_ID, map.get("CUGP_ID") + "");
									flag = true;
									Map<String, Object> mm = new HashMap<String, Object>();
									mm.put("PROJECT_ID", id);
									mm.put("TYPE", "3");
									mm.put("CUGP_ID", map.get("CUGP_ID"));
									mm.put("CUST_ID", CUST_ID);
									mm.put("MONEY", RZE);
									scservice.doAddProjectGRANTPLAN(mm);
									break;
								}
							}
						}
						if (flag == false) {
							throw new ActionException(
									"该项目的经销商授信余额已低于项目融资额，不足以支持该项目的继续进行");
						}
					}
				}
				
				// 扣除厂商授信 King 2015-06-06
				String COMPANY_ID = SuppMap.get("COMPANY_ID") + "";
				if (StringUtils.isNotBlank(COMPANY_ID)) {
					String RZE = UtilFinance.formatString(new projectService()
							.selectProjectDetails(m).get("FINANCE_TOPRIC"), 2);
					SupplierCreditService scservice = new SupplierCreditService();
					List<Map<String, Object>> list = scservice
							.queryCreditInfoBySubject("4", COMPANY_ID);
					if (StringUtils.isNotBlank(list) && !list.isEmpty()
							&& list.size() >= 1) {
						boolean flag = false;
						for (Map<String, Object> map : list) {
							if (StringUtils.isNotBlank(map.get("GRANT_PRICE"))) {
								String GRANT_PRICE = map.get("GRANT_PRICE") + "";
								if (Double.parseDouble(RZE) <= Double
										.parseDouble(GRANT_PRICE)) {
									scservice.deductSubjectCreditLastMoney(RZE,
											"4", COMPANY_ID, map.get("CUGP_ID")
													+ "");
									flag = true;
									Map<String, Object> mm = new HashMap<String, Object>();
									mm.put("PROJECT_ID", id);
									mm.put("TYPE", "4");
									mm.put("CUGP_ID", map.get("CUGP_ID"));
									mm.put("CUST_ID", COMPANY_ID);
									mm.put("MONEY", RZE);
									scservice.doAddProjectGRANTPLAN(mm);
									break;
								}
							}
						}
						if (flag == false) {
							throw new ActionException(
									"该项目的厂商授信余额已低于项目融资额，不足以支持该项目的继续进行");
						}
					}
				}

				// 扣除客户授信 King 2015-06-06
				String CLIENT_ID = SuppMap.get("CLIENT_ID") + "";
				if (StringUtils.isNotBlank(CLIENT_ID)) {
					String RZE = UtilFinance.formatString(new projectService()
							.selectProjectDetails(m).get("FINANCE_TOPRIC"), 2);
					SupplierCreditService scservice = new SupplierCreditService();
					List<Map<String, Object>> list = scservice
							.queryCreditInfoBySubject("1", CLIENT_ID);
					if (StringUtils.isNotBlank(list) && !list.isEmpty()
							&& list.size() >= 1) {
						boolean flag = false;
						for (Map<String, Object> map : list) {
							if (StringUtils.isNotBlank(map.get("GRANT_PRICE"))) {
								String GRANT_PRICE = map.get("GRANT_PRICE") + "";
								if (Double.parseDouble(RZE) <= Double
										.parseDouble(GRANT_PRICE)) {
									scservice
											.deductSubjectCreditLastMoney(RZE, "1",
													CLIENT_ID, map.get("CUGP_ID")
															+ "");
									flag = true;
									Map<String, Object> mm = new HashMap<String, Object>();
									mm.put("PROJECT_ID", id);
									mm.put("TYPE", "1");
									mm.put("CUGP_ID", map.get("CUGP_ID"));
									mm.put("CUST_ID", CLIENT_ID);
									mm.put("MONEY", RZE);
									scservice.doAddProjectGRANTPLAN(mm);
									break;
								}
							}
						}
						if (flag == false) {
							throw new ActionException(
									"该项目的客户授信余额已低于项目融资额，不足以支持该项目的继续进行");
						}
					}
				}
			}
		} else {
			throw new ActionException("放款申请ID为空,流程授信扣除失败, 请联系管理员");
		}
	}
}

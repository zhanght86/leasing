package com.pqsoft.fi.payin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.Util;

public class FundDecService {

	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "fi.funddec.getPageList", "fi.funddec.getPageCount");
	}
	
	public Page getSqkPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "fi.funddec.getSqkPageList", "fi.funddec.getSqkPageCount");
	}
	
	public Page getDFPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "fi.funddec.getDFPageList", "fi.funddec.getDFPageCount");
	}

	public Object selRenter(Map<String, Object> param) {
		return Dao.selectList("fi.funddec.selRenter", param);
	}

	public void setRenter(Map<String, Object> param) {
		param.put("USERNAME", Security.getUser().getName());
		param.put("USERID", Security.getUser().getId());
		Dao.update("fi.funddec.upClientId", param);
	}
 
	public Map<String, Object> getFund(String id) {
		return Dao.selectOne("fi.funddec.getFund", id);
	}

	public Object getFrb(String id) {
		return Dao.selectList("fi.funddec.getFrb", id);
	}
	
	public Object getDFFrb(String id) {
		return Dao.selectList("fi.funddec.getDFFrb", id);
	}
	
	public Object getSQKFrb(String id) {
		return Dao.selectList("fi.funddec.getSQKFrb", id);
	}

//	public void doOp(Map<String, Object> param) {
//		String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
//		String fundId = (String) param.get("FUND_ID");
//		param.put("fund_head_id", fund_head_id);
//		param.put("USER_CODE", Security.getUser().getCode());
//		param.put("USER_NAME", Security.getUser().getName());
//
//		Map<String, Object> src = Dao.selectOne("fi.fund.getFundAll", fundId);
//		String SUP_ID = "";
//		String SUPPLIER_NAME = "";
//		JSONObject mapSelect = JSONObject.fromObject(param.get("selectDateHidden"));
//		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
//		for (int hh = 0; hh < listDate.size(); hh++) {
//			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
//			Map<String, Object> m = Dao.selectOne("rentWriteNew.selectQueryOne", map);
//			m.put("FUND_ACCEPT_DATE", src.get("FUND_ACCEPT_DATE"));
//			String MONEY_FLAG = (m.get("MONEY_FLAG") != null && !m.get("MONEY_FLAG").equals("")) ? m.get("MONEY_FLAG").toString() : "0";
//			if (m.get("PAID_MONEY") != null && !m.get("PAID_MONEY").equals("") && !m.get("PAID_MONEY").equals("0")) {
//				// 修改零时表状态
//				// SUP_ID = m.get("SUP_ID").toString();
//				// SUPPLIER_NAME = m.get("SUP_NAME").toString();
//				param.put("SUPP_ID", m.get("SUP_ID"));
//				param.put("SUPP_NAME", m.get("SUP_NAME"));
//				Dao.update("rentWriteNew.updateJoinDateStauts", m);
//				String ITEM_FLAG = "";
//				
//				if (m.get("ITEM_FLAG") != null && !m.get("ITEM_FLAG").equals(""))// 租金
//				{
//					ITEM_FLAG = m.get("ITEM_FLAG").toString();
//				}
////				// 通过项目CODE查询出发票开具对象(现在有开票协议，这里不需要开票对象)
////				Map InvoiceMap = Dao.selectOne("rentWriteNew.queryFundInvoice", m);
//				if (ITEM_FLAG.equals("2"))// 租金
//				{
//					double moneyPay = Double.parseDouble(m.get("PAID_MONEY").toString());
//					List listDetail = Dao.selectList("rentWriteNew.queryDetailByPayNum", m);
//					if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
//					{
//
//						for (int i = 0; i < listDetail.size(); i++) {
//							Map detaMap = (Map) listDetail.get(i);
//							param.put("FI_PRO_NAME", "租金");
//							detaMap.put("FUND_ACCEPT_DATE", src.get("FUND_ACCEPT_DATE"));
//							detaMap.put("fund_head_id", fund_head_id);
//							detaMap.put("CUSTNAME", m.get("CUSTNAME"));
//							detaMap.put("CUST_ID", m.get("CUST_ID"));
//							detaMap.put("PRO_CODE", m.get("PRO_CODE"));
//							int flag = 0;
////							if (InvoiceMap != null) {
////								detaMap.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
////								detaMap.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
////							}
//							if (MONEY_FLAG.equals("1")) {
//								double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
//								detaMap.put("MONEYCURR", moneyLixi);
//								detaMap.put("D_PAY_MONEYLB", moneyLixi);
//								detaMap.put("D_STATUS", 1);
//								flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//							} else {
//								if (moneyPay > 0) {
//									if (i == 0)// 先扣利息
//									{
//										double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
//										if (moneyPay <= moneyLixi) {
//											detaMap.put("D_PAY_MONEYLB", moneyLixi);
//											detaMap.put("MONEYCURR", moneyPay);
//											detaMap.put("D_STATUS", 1);
//											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//											moneyPay = 0;
//										} else {
//											detaMap.put("D_PAY_MONEYLB", moneyLixi);
//											detaMap.put("MONEYCURR", moneyLixi);
//											detaMap.put("D_STATUS", 1);
//											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//											moneyPay = moneyPay - moneyLixi;
//										}
//									} else// 再扣本金
//									{
//										double moneyBenj = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
//										detaMap.put("D_PAY_MONEYLB", moneyBenj);
//										if (moneyPay <= moneyBenj) {
//											detaMap.put("MONEYCURR", moneyPay);
//											detaMap.put("D_STATUS", 1);
//											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//											moneyPay = 0;
//										} else {
//											detaMap.put("D_PAY_MONEYLB", moneyBenj);
//											detaMap.put("MONEYCURR", moneyBenj);
//											detaMap.put("D_STATUS", 1);
//											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//											moneyPay = moneyPay - moneyBenj;
//										}
//									}
//								}
//
//							}
//
////							if (flag > 0) {
////								// 反更状态位（从未核销更新为申请中）
////								Map mapUpdate = new HashMap();
////								mapUpdate.put("BEGINNING_ID", detaMap.get("BEGINNING_ID"));
////								mapUpdate.put("BEGINNING_STATUS", 0);
////								Dao.update("rentWriteNew.updateBeggingStatusByID", mapUpdate);
////							}
////							Dao.update("fi.funddec.upBeginning", detaMap);
//						}
//					}
//				} else if (ITEM_FLAG.equals("1") || ITEM_FLAG.equals("3") || ITEM_FLAG.equals("4")) {
//					double moneyPay = Double.parseDouble(m.get("PAID_MONEY").toString());
//					List listDetail = Dao.selectList("rentWriteNew.queryDetailByPayName", m);
//					if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
//					{
//						for (int i = 0; i < listDetail.size(); i++) {
//							Map detaMap = (Map) listDetail.get(i);
//							param.put("FI_PRO_NAME", "首期款");
//							detaMap.put("FUND_ACCEPT_DATE", src.get("FUND_ACCEPT_DATE"));
//							detaMap.put("fund_head_id", fund_head_id);
//							detaMap.put("CUSTNAME", m.get("CUSTNAME"));
//							detaMap.put("CUST_ID", m.get("CUST_ID"));
//							detaMap.put("PRO_CODE", m.get("PRO_CODE"));
//							int flag = 0;
////							if (InvoiceMap != null) {
////								detaMap.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
////								detaMap.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
////							}
//							if (MONEY_FLAG.equals("1")) {
//								double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
//								detaMap.put("MONEYCURR", moneyLixi);
//								detaMap.put("D_PAY_MONEYLB", moneyLixi);
//								detaMap.put("D_STATUS", 1);
//								flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//							} else {
//								if (moneyPay > 0) {
//									if (i == 0)// 先扣利息
//									{
//										double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
//										if (moneyPay <= moneyLixi) {
//											detaMap.put("D_PAY_MONEYLB", moneyLixi);
//											detaMap.put("MONEYCURR", moneyPay);
//											detaMap.put("D_STATUS", 0);
//											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//											moneyPay = 0;
//										} else {
//											detaMap.put("D_PAY_MONEYLB", moneyLixi);
//											detaMap.put("MONEYCURR", moneyLixi);
//											detaMap.put("D_STATUS", 0);
//											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//											moneyPay = moneyPay - moneyLixi;
//										}
//									} else// 再扣本金
//									{
//										double moneyBenj = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
//										detaMap.put("D_PAY_MONEYLB", moneyBenj);
//										if (moneyPay <= moneyBenj) {
//											detaMap.put("MONEYCURR", moneyPay);
//											detaMap.put("D_STATUS", 1);
//											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//											moneyPay = 0;
//										} else {
//											detaMap.put("D_PAY_MONEYLB", moneyBenj);
//											detaMap.put("MONEYCURR", moneyBenj);
//											detaMap.put("D_STATUS", 1);
//											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
//											moneyPay = moneyPay - moneyBenj;
//										}
//									}
//								}
//
//							}
//
////							if (flag > 0) {
////								// 反更状态位（从未核销更新为申请中）
////								Map mapUpdate = new HashMap();
////								mapUpdate.put("BEGINNING_ID", detaMap.get("BEGINNING_ID"));
////								mapUpdate.put("BEGINNING_STATUS", 0);
////								Dao.update("rentWriteNew.updateBeggingStatusByID", mapUpdate);
////							}
////							Dao.update("fi.funddec.upBeginning", detaMap);
//						}
//					}
//				} else// 违约金
//				{
//					m.put("fund_head_id", fund_head_id);
//					// SUP_ID = m.get("SUP_ID").toString();
//					// SUPPLIER_NAME = m.get("SUP_NAME").toString();
//					m.put("MONEYCURR", m.get("PAID_MONEY"));
//					m.put("D_PAY_MONEYLB", m.get("PAID_MONEY"));
//					m.put("D_STATUS", 2);
//
//					String IS_BUY_PENALTY = "0";
//					if (map != null && map.get("IS_BUY_PENALTY") != null && !map.get("IS_BUY_PENALTY").equals("")) {
//						IS_BUY_PENALTY = map.get("IS_BUY_PENALTY").toString();
//					}
//
//					if (IS_BUY_PENALTY.equals("1")) {// 回购款违约金
//						// 查询发票开具对象
//						Map mapInvoic = Dao.selectOne("rentWriteNew.queryInvoidNew", m);
//						if (mapInvoic != null) {
//							m.put("INVOICE_TARGET_TYPE", mapInvoic.get("REALITY_TYPE"));
//							m.put("INVOICE_TARGET_ID", mapInvoic.get("REALITY_PAYEE"));
//						}
//					} else {
//						// 通过项目CODE查询出发票开具对象
////						if (InvoiceMap != null) {
////							m.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
////							m.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
////						}
//					}
//
//					Dao.insert("rentWriteNew.createDetailByBank", m);
////					// 如果违约金提交将逾期表中的该条违约金置为申请中
////					m.put("OVERDUE_STATUS", 1);
////					Dao.update("rentWriteNew.updateOverDunStaute", m);
////					param.put("FI_PRO_NAME", "违约金");
////					
////					Map mapDunDate = new HashMap();
////					mapDunDate.put("DUE_STATUS", 1);
////					mapDunDate.put("OVERDUE_STATUS", 0);
////					mapDunDate.put("PAYLIST_CODE", m.get("PAYLIST_CODE"));
////					mapDunDate.put("BEGINNING_NUM", m.get("BEGINNING_NUM"));
////					mapDunDate.put("REALITY_TIME", src.get("FUND_ACCEPT_DATE1"));
////					mapDunDate.put("D_RECEIVE_MONEY", m.get("PAID_MONEY"));
////					Map dunMap=Dao.selectOne("rentWriteNew.fi_overDueOne",mapDunDate);
////					if(dunMap!=null){
////						mapDunDate.put("DUNID", dunMap.get("DUNID"));
////						Dao.update("rentWriteNew.updateOverDunStaute11", mapDunDate);
////						Dao.delete("rentWriteNew.deleteDunDateAll11", mapDunDate);
////					}
//				}
//			}
//		}
//		String FI_FAG = "3";
//		if (param != null && param.get("FI_FAG") != null && !param.get("FI_FAG").equals("")) {
//			FI_FAG = param.get("FI_FAG").toString();
//		}
//		param.put("USER_ID", Security.getUser().getId());
//		// 组织机构应该取缓存 后面在改
//		Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", param);
//		if (ORG_MAP != null) {
//			param.put("ORG_ID", ORG_MAP.get("ORG_ID"));
//		}
//
//		// 判断是租金科提交还是供应商自己提交
//		String APP_CREATE_TYPE = param.get("APP_CREATE_TYPE").toString();
//		String APP_CREATE = "";
//		if (APP_CREATE_TYPE.equals("1")) {// 供应商
//			APP_CREATE = SUPPLIER_NAME;
//		} else {// 租金科
//			APP_CREATE = "租金科";
//		}
//		param.put("APP_CREATE", APP_CREATE);
//		param.put("SUPPLIER_NAME", SUPPLIER_NAME);
//		param.put("SUP_ID", SUP_ID);
//		param.put("FUND_ID", fundId);
//
//		if (FI_FAG.equals("4")) {
//			// 插入付款细单
//			Dao.insert("rentWriteNew.createFundHead_Not", param);
//		} else {
//			// 插入付款细单
//			Dao.insert("rentWriteNew.createFundHead_Not1", param);
//		}
//		// 以上为原有山重核销逻辑------------------------------------------------
//
//		
//		
//		{// TODO 改变来款状态为已分解（新增一条待分解）
//
//			Map<String, Object> fund = new HashMap<String, Object>();
//			fund.put("FUND_ID", fundId);
//			fund.put("FUND_DECOMPOUND_DATE", new Date());
//			fund.put("FUND_STATUS", "4");
//			fund.put("FUND_DECOMPOUND_PERSON", Security.getUser().getName());
//			fund.put("FUND_DECOMPOUND_PERSON_ID", Security.getUser().getId());
//			if (Dao.update("fi.funddec.upFundStatus", fund) != 1) { throw new ActionException("该资金已经被处理。"); }
//			// 待分解
//			double DFJ = Util.formatDouble2(Double.parseDouble((String) param.get("DFJ")));
//			fund = Dao.selectOne("fi.fund.getFundAll", fundId);
//			fund.put("FUND_PRANT_ID", fund.get("FUND_ID"));
//			fund.put("FUND_ID", Dao.selectOne("fi.fund.getFundId"));
//			fund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
//			fund.put("FUND_STATUS", "0");
//			fund.put("FUND_NOTDECO_STATE", "1");
//			fund.put("FUND_ACCEPT_DATE", UTIL.FORMAT.date(fund.get("FUND_ACCEPT_DATE"), "yyyy-MM-dd"));
//			fund.put("FUND_DECOMPOUND_PERSON", "");
//			fund.put("FUND_DECOMPOUND_DATE", "");
//			fund.put("FUND_DECOMPOUND_PERSON_ID", "");
//			fund.put("FUND_RECEIVE_MONEY", DFJ);
//			Dao.insert("fi.fund.add", fund);
//		}
//		
//		//处理核销的数据
//		String FI_ACCOUNT_DATE=src.get("FUND_ACCEPT_DATE1").toString();
//		Map baseData=new HashMap();
//		baseData.put("FUND_ID", fund_head_id);
//		// 根据申请单id查看应收金额和实收总金额
//		List<Map<String, Object>> detialL = Dao.selectList("rentWriteNew.queryDetailByFundId", baseData);
//		// 当不为空时跟新应收明细
//		if (detialL != null) {
//			for (int k = 0; k < detialL.size(); k++) {
//				Map<String, Object> realMoney = detialL.get(k);
//					String D_STATUS=(realMoney!=null && realMoney.get("D_STATUS")!=null && !realMoney.get("D_STATUS").equals(""))?realMoney.get("D_STATUS").toString():"";
//					if(D_STATUS.length()>0 && D_STATUS.equals("1")){//租金
//						Dao.update("rentWriteNew.updateBegin", realMoney);
//						Map mapMo = Dao.selectOne("rentWriteNew.selectmony", realMoney);
//						realMoney.put("TIME", FI_ACCOUNT_DATE);
//						if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
//							Dao.update("rentWriteNew.updateBeginState1", realMoney);
//						}
//						Dao.update("rentWriteNew.updateRENT_PAIDDueNew",realMoney);
//						Dao.update("rentWriteNew.updateDetailRe_StatusNew",realMoney);
//						realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
//					}
//					else if(D_STATUS.length()>0 && D_STATUS.equals("2")){//违约金
//						Map mapDunDate = new HashMap();
//						mapDunDate.put("DUE_STATUS", 1);
//						mapDunDate.put("OVERDUE_STATUS", 0);
//						mapDunDate.put("PAYLIST_CODE", realMoney.get("D_PAY_CODE"));
//						mapDunDate.put("BEGINNING_NUM", realMoney.get("PERIOD"));
//						mapDunDate.put("REALITY_TIME", FI_ACCOUNT_DATE);
//						mapDunDate.put("D_RECEIVE_MONEY", realMoney.get("D_RECEIVE_MONEY"));
//						Map dunMap=Dao.selectOne("rentWriteNew.fi_overDueOne",mapDunDate);
//						if(dunMap!=null){
//							mapDunDate.put("DUNID", dunMap.get("DUNID"));
//							Dao.update("rentWriteNew.updateOverDunStaute11", mapDunDate);
//							Dao.delete("rentWriteNew.deleteDunDateAll11", mapDunDate);
//						}
//					}
//					else if(D_STATUS.length()>0 && D_STATUS.equals("0")){//首期款
//						Dao.update("rentWriteNew.updateBegin", realMoney);
//						Map mapMo = Dao.selectOne("rentWriteNew.selectmony", realMoney);
//						realMoney.put("TIME", FI_ACCOUNT_DATE);
//						if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
//							Dao.update("rentWriteNew.updateBeginState1", realMoney);
//						}
//						if(realMoney.get("PERIOD")!=null && !realMoney.get("PERIOD").equals(""))//为第一期时候
//						{
//							Dao.update("rentWriteNew.updateRENT_PAIDDueNew",realMoney);
//							Dao.update("rentWriteNew.updateDetailRe_StatusNew",realMoney);
//						}
//						
//						realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
//					}
//						
//			}
//			//
//		}
//		
//		//查询该核销单下面所有租金的违约金处理
//		System.out.println("---------------------------------FUND_ACCEPT_DATE="+src.get("FUND_ACCEPT_DATE1").toString());
//		rentWriteNewService rentService=new rentWriteNewService();
//		rentService.dunUpdateStatusFund(fund_head_id,src.get("FUND_ACCEPT_DATE1").toString());
//		rentService.queryPaylist_codeNum(fund_head_id);
//		
//		//如果核销的数据为委托租赁的租金，并且该期核销完成，像放款表中插入一条数据
//		//如果核销的数据为联合租赁并未代收代付的租金，并且该期核销完成，像代付其他融资公司放款表中插入一条数据
//		rentService.insertPayMentNum(fund_head_id);
//	}
	
	public void resetRenter(Map<String, Object> param) {
		Dao.update("fi.funddec.resetRenter", param);
	}

	// public void doOp(String FUND_ID, JSONArray FUND_DEC, Double DFJ) {
	// Map<String, Object> fund = Dao.selectOne("fi.funddec.getFund", FUND_ID);
	// Map<String, Object> fundHead = new HashMap<String, Object>(fund);
	// String fundHeadId = Dao.getSequence("SEQ_FUND_HEAD");// 主表ID
	// fundHead.put("FUND_HEAD_ID", fundHeadId);
	//
	// double FI_PAY_MONEY = 0;
	// {// 插入核销单明细
	// for (int i = 0; i < FUND_DEC.size(); i++) {
	// JSONObject dec = FUND_DEC.getJSONObject(i);
	// {// 处理违约金
	// double FUND_RECEIVE_MONEY = dec.getDouble("FUND_RECEIVE_MONEY");
	// FI_PAY_MONEY += FUND_RECEIVE_MONEY;
	// if ("违约金".equals(dec.getString("FUND_PAY_PROJECT"))) {
	//
	// }
	// }
	// }
	// }
	//
	// {// 插入核销单头信息
	// fundHead.put("FI_APP_NAME", Security.getUser().getName());
	// fundHead.put("FI_APP_CODE", Security.getUser().getCode());
	// fundHead.put("FI_ORG_ID", Security.getUser().getOrg().getId());
	// fundHead.put("FI_PAY_MONEY", FI_PAY_MONEY);
	// fundHead.put("FI_PROJECT_NUM", FUND_DEC.size());
	// Dao.insert("fi.funddec.addFundHead", fundHead);
	// }
	// {// 插入待分解
	// DFJ = Util.formatDouble2(DFJ);
	// }
	// }
	
	
	
	public void doOp(Map<String, Object> param) {
		String fund_head_id =  param.get("FUND_HEAD_ID")+"";
		String fundId = (String) param.get("FUND_ID");
		param.put("fund_head_id", fund_head_id);
		param.put("USER_CODE", Security.getUser().getCode());
		param.put("USER_NAME", Security.getUser().getName());

		Map<String, Object> src = Dao.selectOne("fi.fund.getFundAll", fundId);
		String SUP_ID = "";
		String SUPPLIER_NAME = "";
		JSONObject mapSelect = JSONObject.fromObject(param.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			Map<String, Object> m = Dao.selectOne("rentWriteNew.selectQueryOne", map);
			m.put("FUND_ACCEPT_DATE", src.get("FUND_ACCEPT_DATE"));
			String MONEY_FLAG = (m.get("MONEY_FLAG") != null && !m.get("MONEY_FLAG").equals("")) ? m.get("MONEY_FLAG").toString() : "0";
			if (m.get("PAID_MONEY") != null && !m.get("PAID_MONEY").equals("") && !m.get("PAID_MONEY").equals("0")) {
				// 修改零时表状态
				// SUP_ID = m.get("SUP_ID").toString();
				// SUPPLIER_NAME = m.get("SUP_NAME").toString();
				param.put("SUPP_ID", m.get("SUP_ID"));
				param.put("SUPP_NAME", m.get("SUP_NAME"));
				Dao.update("rentWriteNew.updateJoinDateStauts", m);
				String ITEM_FLAG = "";
				
				if (m.get("ITEM_FLAG") != null && !m.get("ITEM_FLAG").equals(""))// 租金
				{
					ITEM_FLAG = m.get("ITEM_FLAG").toString();
				}
				if (ITEM_FLAG.equals("2"))// 租金
				{
					double moneyPay = Double.parseDouble(m.get("PAID_MONEY").toString());
					List listDetail = Dao.selectList("rentWriteNew.queryDetailByPayNum", m);
					if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
					{

						for (int i = 0; i < listDetail.size(); i++) {
							Map detaMap = (Map) listDetail.get(i);
							param.put("FI_PRO_NAME", "租金");
							param.put("FI_FUND_CODE", "1");
							detaMap.put("FUND_ACCEPT_DATE", src.get("FUND_ACCEPT_DATE"));
							detaMap.put("fund_head_id", fund_head_id);
							detaMap.put("CUSTNAME", m.get("CUSTNAME"));
							detaMap.put("CUST_ID", m.get("CUST_ID"));
							detaMap.put("PRO_CODE", m.get("PRO_CODE"));
							int flag = 0;
							if (MONEY_FLAG.equals("1")) {
								double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
								detaMap.put("MONEYCURR", moneyLixi);
								detaMap.put("D_PAY_MONEYLB", moneyLixi);
								detaMap.put("D_STATUS", 1);
								flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
							} else {
								if (moneyPay > 0) {
									if (i == 0)// 先扣利息
									{
										double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
										if (moneyPay <= moneyLixi) {
											detaMap.put("D_PAY_MONEYLB", moneyLixi);
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
											moneyPay = 0;
										} else {
											detaMap.put("D_PAY_MONEYLB", moneyLixi);
											detaMap.put("MONEYCURR", moneyLixi);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
											moneyPay = moneyPay - moneyLixi;
										}
									} else// 再扣本金
									{
										double moneyBenj = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
										detaMap.put("D_PAY_MONEYLB", moneyBenj);
										if (moneyPay <= moneyBenj) {
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
											moneyPay = 0;
										} else {
											detaMap.put("D_PAY_MONEYLB", moneyBenj);
											detaMap.put("MONEYCURR", moneyBenj);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
											moneyPay = moneyPay - moneyBenj;
										}
									}
								}

							}

							if (flag > 0) {
								// 反更状态位（从未核销更新为申请中）
								Map mapUpdate = new HashMap();
								mapUpdate.put("BEGINNING_ID", detaMap.get("BEGINNING_ID"));
								mapUpdate.put("BEGINNING_STATUS", 1);
								Dao.update("rentWriteNew.updateBeggingStatusByID", mapUpdate);
							}
//							Dao.update("fi.funddec.upBeginning", detaMap);
						}
					}
				} else if (ITEM_FLAG.equals("1") || ITEM_FLAG.equals("3") || ITEM_FLAG.equals("4")) {
					double moneyPay = Double.parseDouble(m.get("PAID_MONEY").toString());
					List listDetail = Dao.selectList("rentWriteNew.queryDetailByPayName", m);
					if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
					{
						for (int i = 0; i < listDetail.size(); i++) {
							Map detaMap = (Map) listDetail.get(i);
							param.put("FI_PRO_NAME", "首期款");
							param.put("FI_FUND_CODE", "2");
							detaMap.put("FUND_ACCEPT_DATE", src.get("FUND_ACCEPT_DATE"));
							detaMap.put("fund_head_id", fund_head_id);
							detaMap.put("CUSTNAME", m.get("CUSTNAME"));
							detaMap.put("CUST_ID", m.get("CUST_ID"));
							detaMap.put("PRO_CODE", m.get("PRO_CODE"));
							int flag = 0;
							if (MONEY_FLAG.equals("1")) {
								double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
								detaMap.put("MONEYCURR", moneyLixi);
								detaMap.put("D_PAY_MONEYLB", moneyLixi);
								detaMap.put("D_STATUS", 1);
								flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
							} else {
								if (moneyPay > 0) {
									if (i == 0)// 先扣利息
									{
										double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
										if (moneyPay <= moneyLixi) {
											detaMap.put("D_PAY_MONEYLB", moneyLixi);
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 0);
											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
											moneyPay = 0;
										} else {
											detaMap.put("D_PAY_MONEYLB", moneyLixi);
											detaMap.put("MONEYCURR", moneyLixi);
											detaMap.put("D_STATUS", 0);
											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
											moneyPay = moneyPay - moneyLixi;
										}
									} else// 再扣本金
									{
										double moneyBenj = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
										detaMap.put("D_PAY_MONEYLB", moneyBenj);
										if (moneyPay <= moneyBenj) {
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
											moneyPay = 0;
										} else {
											detaMap.put("D_PAY_MONEYLB", moneyBenj);
											detaMap.put("MONEYCURR", moneyBenj);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert("rentWriteNew.createDetailByBank", detaMap);
											moneyPay = moneyPay - moneyBenj;
										}
									}
								}

							}

							if (flag > 0) {
								// 反更状态位（从未核销更新为申请中）
								Map mapUpdate = new HashMap();
								mapUpdate.put("BEGINNING_ID", detaMap.get("BEGINNING_ID"));
								mapUpdate.put("BEGINNING_STATUS", 1);
								Dao.update("rentWriteNew.updateBeggingStatusByID", mapUpdate);
							}
//							Dao.update("fi.funddec.upBeginning", detaMap);
						}
					}
				} else// 违约金
				{
					m.put("fund_head_id", fund_head_id);
					// SUP_ID = m.get("SUP_ID").toString();
					// SUPPLIER_NAME = m.get("SUP_NAME").toString();
					m.put("MONEYCURR", m.get("PAID_MONEY"));
					m.put("D_PAY_MONEYLB", m.get("PAID_MONEY"));
					m.put("D_STATUS", 2);

					String IS_BUY_PENALTY = "0";
					if (map != null && map.get("IS_BUY_PENALTY") != null && !map.get("IS_BUY_PENALTY").equals("")) {
						IS_BUY_PENALTY = map.get("IS_BUY_PENALTY").toString();
					}

					if (IS_BUY_PENALTY.equals("1")) {// 回购款违约金
						// 查询发票开具对象
						Map mapInvoic = Dao.selectOne("rentWriteNew.queryInvoidNew", m);
						if (mapInvoic != null) {
							m.put("INVOICE_TARGET_TYPE", mapInvoic.get("REALITY_TYPE"));
							m.put("INVOICE_TARGET_ID", mapInvoic.get("REALITY_PAYEE"));
						}
					} else {
						// 通过项目CODE查询出发票开具对象
//						if (InvoiceMap != null) {
//							m.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
//							m.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
//						}
					}

					Dao.insert("rentWriteNew.createDetailByBank", m);
					// 如果违约金提交将逾期表中的该条违约金置为申请中
					m.put("OVERDUE_STATUS", 1);
					Dao.update("rentWriteNew.updateOverDunStaute", m);
					param.put("FI_PRO_NAME", "违约金");
					param.put("FI_FUND_CODE", "1");
					
//					Map mapDunDate = new HashMap();
//					mapDunDate.put("DUE_STATUS", 1);
//					mapDunDate.put("OVERDUE_STATUS", 0);
//					mapDunDate.put("PAYLIST_CODE", m.get("PAYLIST_CODE"));
//					mapDunDate.put("BEGINNING_NUM", m.get("BEGINNING_NUM"));
//					mapDunDate.put("REALITY_TIME", src.get("FUND_ACCEPT_DATE1"));
//					mapDunDate.put("D_RECEIVE_MONEY", m.get("PAID_MONEY"));
//					Map dunMap=Dao.selectOne("rentWriteNew.fi_overDueOne",mapDunDate);
//					if(dunMap!=null){
//						mapDunDate.put("DUNID", dunMap.get("DUNID"));
//						Dao.update("rentWriteNew.updateOverDunStaute11", mapDunDate);
//						Dao.delete("rentWriteNew.deleteDunDateAll11", mapDunDate);
//					}
				}
			}
		}
		String FI_FAG = "3";
		if (param != null && param.get("FI_FAG") != null && !param.get("FI_FAG").equals("")) {
			FI_FAG = param.get("FI_FAG").toString();
		}
		param.put("USER_ID", Security.getUser().getId());
		// 组织机构应该取缓存 后面在改
		param.put("ORG_ID", Security.getUser().getOrg().getId());

		// 判断是租金科提交还是供应商自己提交
		String APP_CREATE_TYPE = param.get("APP_CREATE_TYPE").toString();
		String APP_CREATE = "";
		if (APP_CREATE_TYPE.equals("1")) {// 供应商
			APP_CREATE = SUPPLIER_NAME;
		} else {// 租金科
			APP_CREATE = "租金科";
		}
		param.put("APP_CREATE", APP_CREATE);
		param.put("SUPPLIER_NAME", SUPPLIER_NAME);
		param.put("SUP_ID", SUP_ID);
		param.put("FUND_ID", fundId);

		if (FI_FAG.equals("4")) {
			// 插入付款细单
			Dao.insert("rentWriteNew.createFundHead_Not", param);
		} else {
			// 插入付款细单
			Dao.insert("rentWriteNew.createFundHead_Not1", param);
		}
		// 以上为原有山重核销逻辑------------------------------------------------
		
		{// TODO 改变来款状态为分解中

			Map<String, Object> fund = new HashMap<String, Object>();
			fund.put("FUND_ID", fundId);
			fund.put("FUND_DECOMPOUND_DATE", new Date());
			fund.put("FUND_STATUS", "1");
			fund.put("FUND_DECOMPOUND_PERSON", Security.getUser().getName());
			fund.put("FUND_DECOMPOUND_PERSON_ID", Security.getUser().getId());
			if (Dao.update("fi.funddec.upFundStatus", fund) != 1) { throw new ActionException("该资金已经被处理。"); }
			
		}
//-----------------------------------------------------以上为申请核销----------------------------------------		
	
}
	
	
	public void HXPASS(String FUND_HEAD_ID){
//-----------------------------------------------------以下为核销提交----------------------------------------	
		String fund_head_id=FUND_HEAD_ID;
		//只需要参数核销单ID(fund_head_id)
		Map FUND_HEAD_MAP=Dao.selectOne("rentWriteNew.queryFundHeadByHeadID", fund_head_id);
		FUND_HEAD_MAP.put("USER_CODE", Security.getUser().getCode());
		FUND_HEAD_MAP.put("USER_NAME", Security.getUser().getName());
		//修改核销单状态
		 Dao.update("rentWriteNew.doUpdateHeadStatusNew", FUND_HEAD_MAP);
		
		String fundId=FUND_HEAD_MAP.get("FUND_ID")+"";
		
		{// TODO 改变来款状态为已分解（新增一条待分解）

			Map<String, Object> fund = new HashMap<String, Object>();
			
			fund = Dao.selectOne("fi.fund.getFundAll", fundId);
			double FUND_RECEIVE_MONEY=Double.parseDouble((fund.get("FUND_RECEIVE_MONEY")!=null && !fund.get("FUND_RECEIVE_MONEY").equals(""))?fund.get("FUND_RECEIVE_MONEY").toString():"0");//原始金额
			double HX_MONEY=Double.parseDouble((FUND_HEAD_MAP.get("FI_PAY_MONEY")!=null && !FUND_HEAD_MAP.get("FI_PAY_MONEY").equals(""))?FUND_HEAD_MAP.get("FI_PAY_MONEY").toString():"0");//核销金额
			
			// 待分解
			double DFJ = Util.formatDouble2(FUND_RECEIVE_MONEY-HX_MONEY);
			
			
			fund.put("FUND_ID", fundId);
			fund.put("FUND_STATUS", "4");
			fund.put("FUND_SY_MONEY", DFJ);
			if (Dao.update("fi.funddec.upFundStatusED", fund) != 1) { throw new ActionException("该资金已经被处理。"); }
			
			
			if(DFJ>0){
				fund.put("FUND_PRANT_ID", fund.get("FUND_ID"));
				fund.put("FUND_ID", Dao.selectOne("fi.fund.getFundId"));
				fund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
				fund.put("FUND_STATUS", "0");
				fund.put("FUND_NOTDECO_STATE", "1");
				fund.put("FUND_ACCEPT_DATE", UTIL.FORMAT.date(fund.get("FUND_ACCEPT_DATE"), "yyyy-MM-dd"));
				fund.put("FUND_DECOMPOUND_PERSON", "");
				fund.put("FUND_DECOMPOUND_DATE", "");
				fund.put("FUND_DECOMPOUND_PERSON_ID", "");
				fund.put("FUND_RECEIVE_MONEY", DFJ);
				fund.put("FUND_SY_MONEY", DFJ);
				
				Dao.insert("fi.fund.add", fund);
			}
			
		}
		
		//处理核销的数据
		String FI_ACCOUNT_DATE=FUND_HEAD_MAP.get("FI_ACCOUNT_DATE").toString();//来款日期
		Map baseData=new HashMap();
		baseData.put("FUND_ID", fund_head_id);
		// 根据申请单id查看应收金额和实收总金额
		List<Map<String, Object>> detialL = Dao.selectList("rentWriteNew.queryDetailByFundId", baseData);
		// 当不为空时跟新应收明细
		if (detialL != null) {
			for (int k = 0; k < detialL.size(); k++) {
				Map<String, Object> realMoney = detialL.get(k);
					String D_STATUS=(realMoney!=null && realMoney.get("D_STATUS")!=null && !realMoney.get("D_STATUS").equals(""))?realMoney.get("D_STATUS").toString():"";
					if(D_STATUS.length()>0 && D_STATUS.equals("1")){//租金
						Dao.update("rentWriteNew.updateBegin", realMoney);
						Map mapMo = Dao.selectOne("rentWriteNew.selectmony", realMoney);
						realMoney.put("TIME", FI_ACCOUNT_DATE);
						if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
							Dao.update("rentWriteNew.updateBeginState1", realMoney);
						}
						Dao.update("rentWriteNew.updateRENT_PAIDDueNew",realMoney);
						Dao.update("rentWriteNew.updateDetailRe_StatusNew",realMoney);
						realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
					}
					else if(D_STATUS.length()>0 && D_STATUS.equals("2")){//违约金
						Map mapDunDate = new HashMap();
						mapDunDate.put("DUE_STATUS", 1);
						mapDunDate.put("OVERDUE_STATUS", 0);
						mapDunDate.put("PAYLIST_CODE", realMoney.get("D_PAY_CODE"));
						mapDunDate.put("BEGINNING_NUM", realMoney.get("PERIOD"));
						mapDunDate.put("REALITY_TIME", FI_ACCOUNT_DATE);
						mapDunDate.put("D_RECEIVE_MONEY", realMoney.get("D_RECEIVE_MONEY"));
						Map dunMap=Dao.selectOne("rentWriteNew.fi_overDueOne",mapDunDate);
						if(dunMap!=null){
							mapDunDate.put("DUNID", dunMap.get("DUNID"));
							Dao.update("rentWriteNew.updateOverDunStaute11", mapDunDate);
							Dao.delete("rentWriteNew.deleteDunDateAll11", mapDunDate);
						}
					}
					else if(D_STATUS.length()>0 && D_STATUS.equals("0")){//首期款
						Dao.update("rentWriteNew.updateBegin", realMoney);
						Map mapMo = Dao.selectOne("rentWriteNew.selectmony", realMoney);
						realMoney.put("TIME", FI_ACCOUNT_DATE);
						if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
							Dao.update("rentWriteNew.updateBeginState1", realMoney);
						}
						if(realMoney.get("PERIOD")!=null && !realMoney.get("PERIOD").equals(""))//为第一期时候
						{
							Dao.update("rentWriteNew.updateRENT_PAIDDueNew",realMoney);
							Dao.update("rentWriteNew.updateDetailRe_StatusNew",realMoney);
						}
						
						realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
					}
						
			}
			//
		}
		
		System.out.println("--------------------------yunxinlema???-------------");
		//查询该核销单下面所有租金的违约金处理
		rentWriteNewService rentService=new rentWriteNewService();
		rentService.dunUpdateStatusFund(fund_head_id,FUND_HEAD_MAP.get("FI_ACCOUNT_DATE").toString());
		rentService.queryPaylist_codeNum(fund_head_id);
		System.out.println("--------2222222222------------------yunxinlema???-------------");
		//查询该核销单下面所以本金判断是否为循环授信，是的话进行处理
		rentService.queryPaylistBJSX(fund_head_id);
		
		
		//反更支付表主表的已还期次和未款金额
		rentService.queryPaylistHG(fund_head_id);
		
		
		//如果核销的数据为委托租赁的租金，并且该期核销完成，像放款表中插入一条数据
		//如果核销的数据为联合租赁并未代收代付的租金，并且该期核销完成，像代付其他融资公司放款表中插入一条数据
		rentService.insertPayMentNum(fund_head_id);
	}
	
	public void HXNOTPASS(String FUND_HEAD_ID){
		Map FUND_HEAD_MAP=Dao.selectOne("rentWriteNew.queryFundHeadByHeadID", FUND_HEAD_ID);
		String fundId=FUND_HEAD_MAP.get("FUND_ID")+"";
		//修改来款单状态
		
		Map<String, Object> fund = new HashMap<String, Object>();
		fund.put("FUND_ID", fundId);
		fund.put("FUND_STATUS", "0");
		if (Dao.update("fi.funddec.upFundStatusED", fund) != 1) { throw new ActionException("该资金已经被处理。"); }
		
		//FI_R_BEGINNING修改状态
		String HEAD_ID=FUND_HEAD_ID;
		Dao.update("rentWriteNew.updateBeginState", HEAD_ID);
		
		// 通过付款单号查询明细项有违约金的吗。有的话将其置为可以核销状态
		List listDunDate = Dao.selectList("rentWriteNew.queryDunDateAll", HEAD_ID);
		for (int num = 0; num < listDunDate.size(); num++) {
			Map mapDunDate = (Map) listDunDate.get(num);
			if (mapDunDate != null && mapDunDate.get("ITEM_FLAG") != null && !mapDunDate.get("ITEM_FLAG").equals("")) {
				String ITEM_FLAG = mapDunDate.get("ITEM_FLAG").toString();
				if (ITEM_FLAG.equals("5")) {
					mapDunDate.put("OVERDUE_STATUS", 0);
					mapDunDate.put("PAYLIST_CODE", mapDunDate.get("D_PAY_CODE"));
					mapDunDate.put("BEGINNING_NUM", mapDunDate.get("PERIOD"));
					Dao.update("rentWriteNew.updateOverDunStaute",mapDunDate);
				}
			}
		}
		//数据重刷
		rentWriteNewService rentService=new rentWriteNewService();
		rentService.queryPaylist_codeNum(HEAD_ID);
		
		Dao.update("rentWriteNew.deleteCancellationDe", HEAD_ID);// 根据付款单号作废付款单
		Dao.update("rentWriteNew.deleteCancellation", HEAD_ID);// 根据付款单号作废付款单
		
	}
}

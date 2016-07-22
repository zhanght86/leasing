package com.pqsoft.rentWrite.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

public class rentWriteService {
	public Page query_cyberBank_C_Page(Map<String, Object> m) {
		// m.put("tags1", "租金");
		// m.put("tags2", "签约标识");
		// m.put("tags3", "客户类型");
		// m.put("tags4", "违约金");
		// return PageUtil.getPage(m, "rentWrite.query_cyberBank_C",
		// "rentWrite.query_cyberBank_C_count");
		return PageUtil.getPage(m, "rentWrite.query_cyberBank_CU",
				"rentWrite.query_cyberBank_CU_count");

	}
	
	public Page query_cyberBankNot_C_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		return PageUtil.getPage(m, "rentWrite.query_cyberBankNot_CU",
				"rentWrite.query_cyberBankNot_CU_count");

	}

	public Page query_Result_C_Page(Map<String, Object> m) {
		return PageUtil.getPage(m, "rentWrite.query_Result_C",
				"rentWrite.query_Result_C_count");

	}
	
	public Page cyberConfirm_PageAjax(Map<String, Object> m) {

		m.put("tags3", "客户类型");
		return PageUtil.getPage(m, "rentWrite.cyberConfirm_PageAjax",
				"rentWrite.cyberConfirm_PageAjaxCount");
	}
	
	public Page cyberCreateNot_PageAjax(Map<String, Object> m) {
		m.put("tags3", "客户类型");
		return PageUtil.getPage(m, "rentWrite.cyberCreateNot_PageAjax",
				"rentWrite.cyberCreateNot_PageAjaxCount");
	}
	
	public Page cyberConfirmNot_PageAjax(Map<String, Object> m) {
		m.put("tags3", "客户类型");
		return PageUtil.getPage(m, "rentWrite.cyberConfirmNot_PageAjax",
				"rentWrite.cyberConfirmNot_PageAjaxCount");
	}

	public void rollBackAll(Map<String, Object> m) {

		m.put("tags3", "客户类型");
		List list=Dao.selectList("rentWrite.rollBackAll", m);
		String IDS="";
		int count=0;
		for(int i=0;i<list.size();i++){
			if(count==0){
				IDS=((Map)list.get(i)).get("ID").toString();
			}
			else{
				IDS=IDS+","+((Map)list.get(i)).get("ID").toString();
			}
			count++;
			if (count == 300) {
				Map mapDate=new HashMap();
				mapDate.put("IDS", IDS);
				this.cyberBankRoll(mapDate);
				Dao.commit();
				Dao.close();
				count = 0;
				IDS="";
			}
		}
		if(count>0){
			Map mapDate=new HashMap();
			mapDate.put("IDS", IDS);
			this.cyberBankRoll(mapDate);
		}
		
	}
	
	public Page query_Bank_C_Page(Map<String, Object> m) {
		// m.put("tags1", "租金");
		// m.put("tags2", "签约标识");
		// m.put("tags3", "客户类型");
		// m.put("tags4", "违约金");

		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		// return PageUtil.getPage(m, "rentWrite.query_Bank_C",
		// "rentWrite.query_Bank_C_count");
		return PageUtil.getPage(m, "rentWrite.query_Bank_CU",
				"rentWrite.query_Bank_CU_count");

	}

	public Page toAjaxData(Map<String, Object> m) {
		m.put("tags1", "核销状态");
		if(!Security.getUser().getId().equals("1")){
			String ORG_LIST = BaseUtil.getSupOrgChildren();
			m.put("ORG_LIST", ORG_LIST);
		}
		return PageUtil.getPage(m, "rentWrite.query_Bank_S",
				"rentWrite.query_Bank_S_count");
	}
	
	public Page toQueryAjaxData(Map<String, Object> m) {
		m.put("tags1", "核销状态");
		if(!Security.getUser().getId().equals("1")){
			String ORG_LIST = BaseUtil.getSupOrgChildren();
			m.put("ORG_LIST", ORG_LIST);
		}
		return PageUtil.getPage(m, "rentWrite.query_Bank_Manger",
				"rentWrite.query_Bank_Manger_count");
	}

	// 开户银行
	public List query_bankList() {
		return Dao.selectList("rentWrite.query_bankList");
	}

	// 导出全部数据
	public Map query_cyberBank_All(Map map) {
		// map.put("tags1", "租金");
		// map.put("tags2", "签约标识");
		// map.put("tags3", "客户类型");
		// map.put("tags4", "违约金");
		// return Dao.selectList("rentWrite.queryUpload_CYBERBANK", map);
		String PR_ID=Dao.getSequence("SEQ_JOIN");
		map.put("PR_ID", PR_ID);
		Dao.update("rentWrite.updateBeginnJoinMerge", map);
		Dao.commit();
		Dao.update("rentWrite.updateBeginnMerge", map);
		Dao.update("rentWrite.updateOverdueMerge", map);
		Dao.commit();
		List list=Dao.selectList("rentWrite.queryUpload_CYBERBANK_UUNEW", map);
		Map mapList=new HashMap();
		mapList.put("list", list);
		mapList.put("PR_ID", PR_ID);
		return mapList;
	}
	
	public void updateJoinSelect(List list){
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			Dao.update("rentWrite.updateJoinSelect",map);
		}
	}

	public void auto(String PR_ID) {
		final User user = Security.getUser();
		final String PR_IDFIN=PR_ID;
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				try {
					new rentWriteService().cyberBankUploadOp1(user,PR_IDFIN);
					Dao.commit();
				} catch (Exception e) {
					Dao.rollback();
					e.printStackTrace();
				} finally {
					Dao.close();
				}

				System.out.println("耗时："
						+ ((System.currentTimeMillis() - start) / 1000)+"秒");
			}
		}).start();
	}

	public void cyberBankUploadOp1(User user,String PR_ID) {
		// 1.查询所需要的数据
		// 2.制造核销单
		List list = Dao.selectList("rentWrite.queryUpload_CYBERBANK_UNEW",PR_ID);
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			// 插入付款细单
			String ITEM_FLAG = "";
			if (map != null && map.get("ITEM_FLAG") != null && !map.get("ITEM_FLAG").equals("")) {
				ITEM_FLAG = map.get("ITEM_FLAG").toString();
			}
			try {
				if (ITEM_FLAG.equals("2"))// 租金
				{
					List listDetail = Dao.selectList(
							"rentWrite.queryDetailByPayNumRun", map);
					if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
					{
						map.put("FI_PRO_NAME", "租金");

						String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
						map.put("PROJ_ID", fund_head_id);
						// 插入数据库数据
						map.put("fund_head_id", fund_head_id);
						map.put("USER_CODE", user.getCode());
						map.put("USER_NAME", user.getName());
						// 插入付款单

						map.put("USER_ID", user.getId());
						// 组织机构应该取缓存 后面在改
						Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", map);
						if (ORG_MAP != null) {
							map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
						}
						map.put("APP_CREATE", "租金科");
						Dao.insert("rentWrite.createFundHead", map);
						for (int j = 0; j < listDetail.size(); j++) {
							Map mapDetail = (Map) listDetail.get(j);
							mapDetail.put("fund_head_id", map.get("fund_head_id"));
							mapDetail.put("CUSTNAME", map.get("CUSTNAME"));
							mapDetail.put("CUST_ID", map.get("CUST_ID"));
							mapDetail.put("PRO_CODE", map.get("PRO_CODE"));
							mapDetail.put("D_STATUS", 1);
							// 通过项目CODE查询出发票开具对象
							Map InvoiceMap = Dao.selectOne(
									"rentWrite.queryFundInvoice", mapDetail);
							if (InvoiceMap != null) {
								mapDetail.put("INVOICE_TARGET_TYPE", InvoiceMap
										.get("INVOICE_TARGET_TYPE"));
								mapDetail.put("INVOICE_TARGET_ID", InvoiceMap
										.get("INVOICE_TARGET_ID"));
							}
							Dao.insert("rentWrite.createDetailByCyberBank",
									mapDetail);

							// 反更状态位（从未核销更新为网银核销）
							Map mapUpdate = new HashMap();
							mapUpdate.put("BEGINNING_ID", mapDetail.get("BEGINNING_ID"));
							mapUpdate.put("BEGINNING_STATUS", 1);
							mapUpdate.put("INVENT_STATUS", 0);
							mapDetail.put("D_STATUS", 1);
							Dao.update("rentWrite.updateBeggingStatusByID",
									mapUpdate);
						}
						map.put("ITEM_FLAG_JOIN", 2);
						Dao.update("rentWrite.updateJoinUpload", map);
					}
					
				} else// 违约金
				{
					String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
					
					// 插入数据库数据
					map.put("fund_head_id", fund_head_id);
					map.put("PROJ_ID", fund_head_id);
					// 插入付款单
					map.put("FI_PRO_NAME", "违约金");
					map.put("D_STATUS", 2);
					map.put("USER_ID", user.getId());
					// 组织机构应该取缓存 后面在改
					Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", map);
					if (ORG_MAP != null) {
						map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
					}
					map.put("APP_CREATE", "租金科");
					Dao.insert("rentWrite.createFundHead", map);

					// 通过项目CODE查询出发票开具对象
					Map InvoiceMap = Dao.selectOne("rentWrite.queryFundInvoice",
							map);
					if (InvoiceMap != null) {
						map.put("INVOICE_TARGET_TYPE", InvoiceMap
								.get("INVOICE_TARGET_TYPE"));
						map.put("INVOICE_TARGET_ID", InvoiceMap
								.get("INVOICE_TARGET_ID"));
					}
					Dao.insert("rentWrite.createDetailByOther", map);

					// 修改该违约金状态
					Dao.update("rentWrite.updateDueState11", map);
					map.put("ITEM_FLAG_JOIN", 5);
					Dao.update("rentWrite.updateJoinUpload", map);
				}
				Dao.commit();
			} catch (Throwable e) {
				//1.出现异常后将中间表置为可导出状态
				//2.如果是租金将期初表置为未申请，如果是违约金将期初表置为可核销
				Dao.update("rentWrite.UplodJoinErrorStatue",map);
				if (ITEM_FLAG.equals("2"))// 租金
				{
					Dao.update("rentWrite.UplodBeginErrorStatus",map);
				}
				else{//违约金
					Dao.update("rentWrite.UplodDueErrorStatus",map);
				}
			}
			count++;
			if (count == 100) {
				Dao.close();
				count = 0;
			}
		}
	}

	// 插入数据库和制造导出数据
	public List cyberBankUploadOp(List list) {
		// 查询所需要的数据
		
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			// 插入付款细单
			String ITEM_FLAG = "";
			if (map != null && map.get("ITEM_FLAG") != null
					&& !map.get("ITEM_FLAG").equals("")) {
				ITEM_FLAG = map.get("ITEM_FLAG").toString();
			}
			if (ITEM_FLAG.equals("2"))// 租金
			{
				Map mapZuJin = Dao.selectOne("rentWrite.queryUpload_ZUJIN", map);
				// 租金 保存数据
				map.put("PRO_CODE", mapZuJin.get("PRO_CODE"));
				map.put("CUST_ID", mapZuJin.get("CUST_ID"));
				map.put("CUSTNAME", mapZuJin.get("CUSTNAME"));
				map.put("ID_CARD_NO", mapZuJin.get("ID_CARD_NO"));
				map.put("BANK_NAME", mapZuJin.get("BANK_NAME"));
				map.put("BANK_ACCOUNT", mapZuJin.get("BANK_ACCOUNT"));
				map.put("BANK_CUSTNAME", mapZuJin.get("BANK_CUSTNAME"));
				map.put("BEGINNING_MONEY", mapZuJin.get("BEGINNING_MONEY"));
				map.put("MONEY", mapZuJin.get("BEGINNING_MONEY"));
				map.put("BEGINNING_NAME", mapZuJin.get("租金"));
				map.put("BEGINNING_RECEIVE_DATA", mapZuJin.get("BEGINNING_RECEIVE_DATA"));
				map.put("CUST_NAME", mapZuJin.get("CUSTNAME"));
				map.put("PROJ_ID", map.get("PAYLIST_CODE")+","+map.get("BEGINNING_NUM")+",2");

				List listDetail = Dao.selectList("rentWrite.queryDetailByPayNum", map);

				if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
				{
					// 确保选择的费用相等,如果不等就核销现在金额
					Map mapMoney = (Map) Dao.selectOne(
							"rentWrite.queryAllMoneyByNum", map);
					map.put("BEGINNING_MONEY", mapMoney.get("BEGINNING_MONEY"));
					map.put("FI_PRO_NAME", "租金");

					String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
					
					//现在proje_id改为支付表编号，期次，违约金为2
					
					// 插入数据库数据
					map.put("fund_head_id", fund_head_id);
					map.put("USER_CODE", Security.getUser().getCode());
					map.put("USER_NAME", Security.getUser().getName());
					// 插入付款单

					map.put("USER_ID", Security.getUser().getId());
					// 组织机构应该取缓存 后面在改
					Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", map);
					if (ORG_MAP != null) {
						map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
					}
					map.put("APP_CREATE", "租金科");
					Dao.insert("rentWrite.createFundHead", map);
					for (int j = 0; j < listDetail.size(); j++) {
						Map mapDetail = (Map) listDetail.get(j);
						mapDetail.put("fund_head_id", fund_head_id);
						mapDetail.put("CUSTNAME", map.get("CUSTNAME"));
						mapDetail.put("CUST_ID", map.get("CUST_ID"));
						mapDetail.put("PRO_CODE", map.get("PRO_CODE"));
						mapDetail.put("D_STATUS", 1);
						// 通过项目CODE查询出发票开具对象
						Map InvoiceMap = Dao.selectOne(
								"rentWrite.queryFundInvoice", mapDetail);
						if (InvoiceMap != null) {
							mapDetail.put("INVOICE_TARGET_TYPE", InvoiceMap
									.get("INVOICE_TARGET_TYPE"));
							mapDetail.put("INVOICE_TARGET_ID", InvoiceMap
									.get("INVOICE_TARGET_ID"));
						}
						Dao.insert("rentWrite.createDetailByCyberBank",
								mapDetail);

						// 反更状态位（从未核销更新为网银核销）
						Map mapUpdate = new HashMap();
						mapUpdate.put("BEGINNING_ID", mapDetail
								.get("BEGINNING_ID"));
						mapUpdate.put("BEGINNING_STATUS", 1);
						mapDetail.put("D_STATUS", 1);
						Dao.update("rentWrite.updateBeggingStatusByID",
								mapUpdate);
					}
					
					map.put("ITEM_FLAG_JOIN", 2);
					Dao.update("rentWrite.updateJoinUpload", map);
				}

			} else// 违约金
			{
				
				Map mapDue = Dao.selectOne("rentWrite.queryUpload_Due", map);
				// 租金 保存数据
				map.put("PRO_CODE", mapDue.get("PRO_CODE"));
				map.put("CUST_ID", mapDue.get("CUST_ID"));
				map.put("CUSTNAME", mapDue.get("CUSTNAME"));
				map.put("ID_CARD_NO", mapDue.get("ID_CARD_NO"));
				map.put("BANK_NAME", mapDue.get("BANK_NAME"));
				map.put("BANK_ACCOUNT", mapDue.get("BANK_ACCOUNT"));
				map.put("BANK_CUSTNAME", mapDue.get("BANK_CUSTNAME"));
				map.put("BEGINNING_MONEY", mapDue.get("BEGINNING_MONEY"));
				map.put("BEGINNING_NAME", mapDue.get("违约金"));
				map.put("CUST_NAME", mapDue.get("CUSTNAME"));
				
				String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
				// 插入数据库数据
				map.put("fund_head_id", fund_head_id);
				map.put("PROJ_ID", map.get("PAYLIST_CODE")+","+map.get("BEGINNING_NUM")+",5");
				// 插入付款单
				map.put("FI_PRO_NAME", "违约金");
				map.put("BEGINNING_NAME", "违约金");
				map.put("D_STATUS", 2);
				map.put("USER_ID", Security.getUser().getId());
				// 组织机构应该取缓存 后面在改
				Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", map);
				if (ORG_MAP != null) {
					map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
				}
				map.put("APP_CREATE", "租金科");
				Dao.insert("rentWrite.createFundHead", map);

				// 通过项目CODE查询出发票开具对象
				Map InvoiceMap = Dao.selectOne("rentWrite.queryFundInvoice",
						map);
				if (InvoiceMap != null) {
					map.put("INVOICE_TARGET_TYPE", InvoiceMap
							.get("INVOICE_TARGET_TYPE"));
					map.put("INVOICE_TARGET_ID", InvoiceMap
							.get("INVOICE_TARGET_ID"));
				}
				Dao.insert("rentWrite.createDetailByOther", map);

				// 修改该违约金状态
				Dao.update("rentWrite.updateDueState11", map);
				map.put("ITEM_FLAG_JOIN", 5);
				Dao.update("rentWrite.updateJoinUpload", map);
			}
		}
		return list;
	}

	// 插入数据库和制造导出数据
	public void bank_C_Submit(Map mapDate) {
		String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
		mapDate.put("fund_head_id", fund_head_id);
		mapDate.put("USER_CODE", Security.getUser().getCode());
		mapDate.put("USER_NAME", Security.getUser().getName());
		String SUP_ID = "";
		String SUPPLIER_NAME = "";
		double moneyAll = 0d;
		JSONObject mapSelect = JSONObject.fromObject(mapDate
				.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {

			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			Map<String, Object> m = Dao.selectOne("rentWrite.selectQueryOne",map);
			String MONEY_FLAG=(m.get("MONEY_FLAG")!=null && !m.get("MONEY_FLAG").equals(""))?m.get("MONEY_FLAG").toString():"0";
			if (m.get("PAID_MONEY") != null && !m.get("PAID_MONEY").equals("")
					&& !m.get("PAID_MONEY").equals("0")) {
				// 修改零时表状态
				SUP_ID = m.get("SUP_ID").toString();
				SUPPLIER_NAME = m.get("SUP_NAME").toString();
				mapDate.put("SUPP_ID", m.get("SUP_ID").toString());
				mapDate.put("SUPP_NAME", m.get("SUP_NAME").toString());
				Dao.update("rentWrite.updateJoinDateStauts", m);
				String ITEM_FLAG = "";
				if (m.get("ITEM_FLAG") != null
						&& !m.get("ITEM_FLAG").equals(""))// 租金
				{
					ITEM_FLAG = m.get("ITEM_FLAG").toString();
				}
				if (ITEM_FLAG.equals("2"))// 租金
				{
					double moneyPay = Double.parseDouble(m.get("PAID_MONEY")
							.toString());
					List listDetail = Dao.selectList(
							"rentWrite.queryDetailByPayNum", m);
					if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
					{
						
						for (int i = 0; i < listDetail.size(); i++) {
							Map detaMap = (Map) listDetail.get(i);
							mapDate.put("FI_PRO_NAME", "租金");
							detaMap.put("fund_head_id", fund_head_id);
							detaMap.put("CUSTNAME", m.get("CUSTNAME"));
							detaMap.put("CUST_ID", m.get("CUST_ID"));
							detaMap.put("PRO_CODE", m.get("PRO_CODE"));
							int flag = 0;
							// 通过项目CODE查询出发票开具对象
							Map InvoiceMap = Dao.selectOne("rentWrite.queryFundInvoice", detaMap);
							if (InvoiceMap != null) {
								detaMap.put("INVOICE_TARGET_TYPE",InvoiceMap.get("INVOICE_TARGET_TYPE"));
								detaMap.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
							}
							if(MONEY_FLAG.equals("1")){
									double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
									detaMap.put("MONEYCURR", moneyLixi);
									detaMap.put("D_STATUS", 1);
									flag = Dao.insert("rentWrite.createDetailByBank",detaMap);
							}
							else{
								if (moneyPay > 0) {
									if (i == 0)// 先扣利息
									{
										double moneyLixi = Double
												.parseDouble(detaMap.get(
														"BEGINNING_MONEY")
														.toString());
										
										if (moneyPay <= moneyLixi) {
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(
													"rentWrite.createDetailByBank",
													detaMap);
											moneyPay = 0;
										} else {
											
											detaMap.put("MONEYCURR", moneyLixi);
											detaMap.put("D_STATUS", 1);
											
											flag = Dao.insert(
													"rentWrite.createDetailByBank",
													detaMap);
											moneyPay = moneyPay - moneyLixi;
										}
									} else// 再扣本金
									{
										double moneyBenj = Double
												.parseDouble(detaMap.get(
														"BEGINNING_MONEY")
														.toString());
										if (moneyPay <= moneyBenj) {
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(
													"rentWrite.createDetailByBank",
													detaMap);
											moneyPay = 0;
										} else {
											detaMap.put("MONEYCURR", moneyBenj);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(
													"rentWrite.createDetailByBank",
													detaMap);
											moneyPay = moneyPay - moneyBenj;
										}
									}
							}
							
							
								
							}
							
							if (flag > 0) {
								// 反更状态位（从未核销更新为网银核销）
								Map mapUpdate = new HashMap();
								mapUpdate.put("BEGINNING_ID", detaMap
										.get("BEGINNING_ID"));
								mapUpdate.put("BEGINNING_STATUS", 1);
								Dao
										.update(
												"rentWrite.updateBeggingStatusByID",
												mapUpdate);
							}

						}
					}
				} else// 违约金
				{
					m.put("fund_head_id", fund_head_id);
					SUP_ID = m.get("SUP_ID").toString();
					SUPPLIER_NAME = m.get("SUP_NAME").toString();
					mapDate.put("SUPP_ID", m.get("SUP_ID").toString());
					mapDate.put("SUPP_NAME", m.get("SUP_NAME").toString());
					m.put("MONEYCURR", m.get("PAID_MONEY"));
					m.put("D_STATUS", 2);

					// 通过项目CODE查询出发票开具对象
					if (m != null && m.get("PRO_CODE") != null
							&& !m.get("PRO_CODE").equals("")) {
						Map InvoiceMap = Dao.selectOne(
								"rentWrite.queryFundInvoice", m);
						if (InvoiceMap != null) {
							m.put("INVOICE_TARGET_TYPE", InvoiceMap
									.get("INVOICE_TARGET_TYPE"));
							m.put("INVOICE_TARGET_ID", InvoiceMap
									.get("INVOICE_TARGET_ID"));
						}
					}

					Dao.insert("rentWrite.createDetailByBank", m);
					// 如果违约金提交将逾期表中的该条违约金置为不能再申请
					m.put("DUE_STATUS", 0);
					m.put("OVERDUE_STATUS", 1);
					Dao.update("rentWrite.updateOverDunStaute", m);
					mapDate.put("FI_PRO_NAME", "违约金");
				}
			}
		}
		String FI_FAG = "3";
		if (mapDate != null && mapDate.get("FI_FAG") != null
				&& !mapDate.get("FI_FAG").equals("")) {
			FI_FAG = mapDate.get("FI_FAG").toString();

		}
		mapDate.put("USER_ID", Security.getUser().getId());
		// 组织机构应该取缓存 后面在改
		Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", mapDate);
		if (ORG_MAP != null) {
			mapDate.put("ORG_ID", ORG_MAP.get("ORG_ID"));
		}
		
		//判断是租金科提交还是供应商自己提交
		String APP_CREATE_TYPE=mapDate.get("APP_CREATE_TYPE").toString();
		String APP_CREATE="";
		if(APP_CREATE_TYPE.equals("1")){//供应商
			APP_CREATE=SUPPLIER_NAME;
		}
		else{//租金科
			APP_CREATE="租金科";
		}
		mapDate.put("APP_CREATE", APP_CREATE);
		mapDate.put("SUPPLIER_NAME", SUPPLIER_NAME);

		if (FI_FAG.equals("4")) {
			this.saveSupp_bank(mapDate);
			// 插入付款细单
			Dao.insert("rentWrite.createFundHead_Not", mapDate);
		} else {
			// 插入付款细单
			Dao.insert("rentWrite.createFundHead_Not1", mapDate);
		}
	}

	// 重置
	public void cyberBankRoll(Map map) {

		// 重置状态
		Dao.update("rentWrite.updateBigennIDByHandId", map);
		//重置临时表状态
		Dao.update("rentWrite.updateJoinZURollMerge",map);
		Dao.update("rentWrite.updateJoinDueRollMerge",map);
		//重置违约金表状态
		Dao.update("rentWrite.updateDueRollMerge",map);
		// 删除子表
		Dao.delete("rentWrite.deleteDetailByHandId", map);
		// 删除主表
		Dao.update("rentWrite.deleteHandByHandId", map);

	}
	
	public void backOP(List list, Map map) {
		String seqFileId=Dao.getSequence("SEQ_FI_FUND_FILE");
		map.put("ID", seqFileId);
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("TYPE", 2);
		Dao.insert("rentWrite.insertFundFile",map);
		final List listOPBack=list;
		final Map mapOPBack=map;
		mapOPBack.put("FI_CHECK_NAME", Security.getUser().getName());
		mapOPBack.put("FI_REALITY_BANK", map.get("FI_REALITY_BANK"));
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				try {
					new rentWriteService().backFileOp1(listOPBack, mapOPBack);
					Dao.commit();
				} catch (Exception e) {
					Dao.rollback();
					e.printStackTrace();
				} finally {
					Dao.close();
				}

				System.out.println("耗时："
						+ ((System.currentTimeMillis() - start) / 1000)+"秒");
			}
		}).start();
	}
	
	// 回执文件的处理
	public void backFileOp1(List list, Map mapTime) {
		String time = mapTime.get("fromDate").toString();
		Map mapUp=new HashMap();
		int SUCCESS_NUM=0;
		int ERROR_BANK_NUM=0;
		int ERROR_NUM=0;
		// 核销成功的数据：1.修改头表的状态未已核销 2.修改fi_r_begging表的实收金额，如果实收金额等于应付金额则修改状态为核销完成
		// 3.修改子表的实际收到金额 4.插入数据到FI_FUND_ACCOUNT表
		// 核销失败的数据：1.修改头表的状态为核销失败 2.修改fi_r_begging表的失败次数和失败原因 ，并将状态修改为未核销状态
		int count=0;
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			//通过回执数据查询核销单ID
			String FUND_IDNEW=(map.get("deducted_id")!=null && !map.get("deducted_id").equals(""))?map.get("deducted_id").toString():"";
			try{
				String[] dateStr=FUND_IDNEW.split(",");
				String paylist_codeUp=dateStr[0];
				String beginning_numUp=dateStr[1];
				String item_typeUp=dateStr[2];
				mapUp.put("paylist_codeUp",paylist_codeUp);
				mapUp.put("beginning_numUp",beginning_numUp);
				mapUp.put("PAYLIST_CODE",paylist_codeUp);
				mapUp.put("BEGINNING_NUM",beginning_numUp);
				mapUp.put("item_typeUp",item_typeUp);
					if(item_typeUp.equals("2")){
						mapUp.put("item_typeUp",1);
					}
					else{
						mapUp.put("item_typeUp",2);
					}
					Map map1=Dao.selectOne("rentWrite.queryFund_HeadbyDate",mapUp);
					if(map1!=null){
						map.put("deducted_id",map1.get("D_FUND_ID"));
						if (map != null && map.get("bank_flag") != null && map.get("bank_flag").equals("1"))// 成功
						{
							map.put("TIME", time);
							map.put("FI_REALITY_BANK", mapTime.get("FI_REALITY_BANK"));
							map.put("FI_CHECK_NAME", mapTime.get("FI_CHECK_NAME"));
							Dao.update("rentWrite.updateFI_STATUSByHandId2", map);// 1
							List listBeg = Dao.selectList("rentWrite.getBeginListByHandId",map);
							for (int j = 0; j < listBeg.size(); j++)// 2
							{
								Map mapBeg = (Map) listBeg.get(j);
								if (mapBeg != null) {
									double D_PAY_MONEY = (mapBeg.get("D_PAY_MONEY") != null && !mapBeg
											.get("D_PAY_MONEY").equals("")) ? Double
											.parseDouble(mapBeg.get("D_PAY_MONEY")
													.toString()) : 0d;// 本次回执金额
									double BEGINNING_PAID = (mapBeg.get("BEGINNING_PAID") != null && !mapBeg
											.get("BEGINNING_PAID").equals("")) ? Double
											.parseDouble(mapBeg.get("BEGINNING_PAID")
													.toString()) : 0d;
									;// 已经实收金额
									double BEGINNING_MONEY = (mapBeg.get("BEGINNING_MONEY") != null && !mapBeg
											.get("BEGINNING_MONEY").equals("")) ? Double
											.parseDouble(mapBeg.get("BEGINNING_MONEY")
													.toString()) : 0d;
									;// 总应收金额
									if ((D_PAY_MONEY + BEGINNING_PAID) >= BEGINNING_MONEY) {
										mapBeg.put("BEGINNING_FLAG", "1");
										mapBeg.put("REALITY_TIME", mapTime.get("fromDate"));
									}
									Dao.update("rentWrite.updateMoneyBeginNewGuize", mapBeg);
									Dao.update("rentWrite.doUpdateBeginningJoinHH", mapBeg);
									mapBeg.put("TIME", time);
									this.dunUpdateStatus11(mapBeg);
								}
							}
							Dao.update("rentWrite.updateMoneyDatil", map);// 3

							// 先删后增
							Dao.delete("rentWrite.delAccByDeductedId", map);
							List listDatil = Dao
									.selectList("rentWrite.getAccountById", map);
							for (int h = 0; h < listDatil.size(); h++) {
								Map datMap = (Map) listDatil.get(h);
								Dao.insert("rentWrite.insertAccount", datMap);// 4

							}
							map.put("REALITY_TIME", mapTime.get("fromDate"));
							this.insertBeginByDun(map.get("deducted_id").toString(),mapTime.get("fromDate"));
							
							Dao.delete("rentWrite.deleteJoinDate",mapUp);
							Dao.insert("rentWrite.insertJoinDate",mapUp);
							SUCCESS_NUM++;
						} else// 失败
						{
							Dao.update("rentWrite.updateStatusFalseByHandId", map);// 2
							map.put("D_FUND_ID", map.get("deducted_id"));
							// 重置状态
							Dao.update("rentWrite.updateBigennIDByHandId1", map);
							//重置临时表状态
							Dao.update("rentWrite.updateJoinZURollMerge1",map);
							Dao.update("rentWrite.updateJoinDueRollMerge1",map);
							//重置违约金表状态
							Dao.update("rentWrite.updateDueRollMerge1",map);
							// 删除子表
							Dao.delete("rentWrite.deleteDetailByHandId1", map);
							// 删除主表
							Dao.update("rentWrite.deleteHandByHandId1", map);
							ERROR_BANK_NUM++;
						}
					}
					else{
						ERROR_NUM++;
						Map mapERROR=new HashMap();
						mapERROR.put("FILE_ID", mapTime.get("ID"));
						mapERROR.put("ERROR_CODE", FUND_IDNEW);
						mapERROR.put("REMARK", "Oh My God,找不到核销单了！！！");
						Dao.update("rentWrite.errFiInsert", mapERROR);
					}
					
			}
			catch(Exception e)
			{
				ERROR_NUM++;
				Map mapERROR=new HashMap();
				mapERROR.put("FILE_ID", mapTime.get("ID"));
				mapERROR.put("ERROR_CODE", FUND_IDNEW);
				mapERROR.put("REMARK", "报错了，自己找呗，友情提示：可能数据异常额！嘻嘻！！！");
				Dao.insert("rentWrite.errFiInsert", mapERROR);
			}
			
			count++;
			if (count == 500) {
				Dao.commit();
				Dao.close();
				count = 0;
			}
			
		}
		mapTime.put("SUCCESS_NUM", SUCCESS_NUM);
		mapTime.put("ERROR_BANK_NUM", ERROR_BANK_NUM);
		mapTime.put("ERROR_NUM", ERROR_NUM);
		mapTime.put("FUND_DATE", time);
		Dao.update("rentWrite.updateFundFileByID",mapTime);
	}

	// 回执文件的处理
	public Map backFileOp(List list, Map mapTime) {
		Map mapDate1=new HashMap();
		String time = mapTime.get("fromDate").toString();
		int SUCCESS_NUM=0;
		int ERROR_BANK_NUM=0;
		int ERROR_NUM=0;
		mapTime.put("FI_CHECK_NAME", Security.getUser().getName());
		// 核销成功的数据：1.修改头表的状态未已核销 2.修改fi_r_begging表的实收金额，如果实收金额等于应付金额则修改状态为核销完成
		// 3.修改子表的实际收到金额 4.插入数据到FI_FUND_ACCOUNT表
		// 核销失败的数据：1.修改头表的状态为核销失败 2.修改fi_r_begging表的失败次数和失败原因 ，并将状态修改为未核销状态
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (map != null && map.get("bank_flag") != null && map.get("bank_flag").equals("1"))// 成功
			{
				map.put("TIME", time);
				map.put("FI_REALITY_BANK", mapTime.get("FI_REALITY_BANK"));
				map.put("FI_CHECK_NAME", mapTime.get("FI_CHECK_NAME"));
				Dao.update("rentWrite.updateFI_STATUSByHandId2", map);// 1
				List listBeg = Dao.selectList("rentWrite.getBeginListByHandId",
						map);
				for (int j = 0; j < listBeg.size(); j++)// 2
				{
					Map mapBeg = (Map) listBeg.get(j);
					if (mapBeg != null) {
						double D_PAY_MONEY = (mapBeg.get("D_PAY_MONEY") != null && !mapBeg
								.get("D_PAY_MONEY").equals("")) ? Double
								.parseDouble(mapBeg.get("D_PAY_MONEY")
										.toString()) : 0d;// 本次回执金额
						double BEGINNING_PAID = (mapBeg.get("BEGINNING_PAID") != null && !mapBeg
								.get("BEGINNING_PAID").equals("")) ? Double
								.parseDouble(mapBeg.get("BEGINNING_PAID")
										.toString()) : 0d;
						;// 已经实收金额
						double BEGINNING_MONEY = (mapBeg.get("BEGINNING_MONEY") != null && !mapBeg
								.get("BEGINNING_MONEY").equals("")) ? Double
								.parseDouble(mapBeg.get("BEGINNING_MONEY")
										.toString()) : 0d;
						;// 总应收金额
						if ((D_PAY_MONEY + BEGINNING_PAID) >= BEGINNING_MONEY) {
							mapBeg.put("BEGINNING_FLAG", "1");
							mapBeg.put("REALITY_TIME", mapTime.get("fromDate"));
						}
						Dao.update("rentWrite.updateMoneyBegin", mapBeg);
						Dao.update("rentWrite.doUpdateBeginningJoinHH", mapBeg);
						mapBeg.put("TIME", time);
						this.dunUpdateStatus11(mapBeg);
					}
				}
				Dao.update("rentWrite.updateMoneyDatil", map);// 3

				// 先删后增
				Dao.delete("rentWrite.delAccByDeductedId", map);
				List listDatil = Dao
						.selectList("rentWrite.getAccountById", map);
				for (int h = 0; h < listDatil.size(); h++) {
					Map datMap = (Map) listDatil.get(h);
					Dao.insert("rentWrite.insertAccount", datMap);// 4

				}
				map.put("REALITY_TIME", mapTime.get("fromDate"));
				this.insertBeginByDun(map.get("deducted_id").toString(),
						mapTime.get("fromDate"));
				
				this.queryPaylist_codeNum(map.get("deducted_id").toString());
				SUCCESS_NUM++;
			} else// 失败
			{
				ERROR_BANK_NUM++;
//				Dao.update("rentWrite.updateFI_STATUSByHandId3", map);// 1
//				Dao.update("rentWrite.updateStatusFalseByHandId", map);// 2
				
//				Dao.update("rentWrite.updateStatusFalseByHandId", map);// 2
//				map.put("D_FUND_ID", map.get("deducted_id"));
//				// 重置状态
//				Dao.update("rentWrite.updateBigennIDByHandId1", map);
//				//重置临时表状态
//				Dao.update("rentWrite.updateJoinZURollMerge1",map);
//				Dao.update("rentWrite.updateJoinDueRollMerge1",map);
//				//重置违约金表状态
//				Dao.update("rentWrite.updateDueRollMerge1",map);
//				// 删除子表
//				Dao.delete("rentWrite.deleteDetailByHandId1", map);
//				// 删除主表
//				Dao.update("rentWrite.deleteHandByHandId1", map);
			}
		}
		mapDate1.put("SUCCESS_NUM", SUCCESS_NUM);
		mapDate1.put("ERROR_BANK_NUM", ERROR_BANK_NUM);
		System.out.println("----------------------mapDate1="+mapDate1);
		return mapDate1;
	}

	// 根据电话号码导出数据
	public List<Map<String, Object>> fundInfoByPhone(Map<String, Object> map) {

		return null;
	}

	public List getFundDetail(Map<String, Object> map) {
		map.put("tags1", "租金");
		map.put("tags2", "违约金");
		return Dao.selectList("rentWrite.getFundDetail", map);
	}

	public Page getFundDetailPage(Map<String, Object> map) {
		map.put("tags1", "租金");
		map.put("tags2", "违约金");
		return PageUtil.getPage(map, "rentWrite.getFundDetail",
				"rentWrite.getFundDetail_count");
	}

	/**
	 * 查询首期款-非网银核销数据 findDeductData
	 * 
	 * @date 2013-9-24 上午11:04:47
	 * @author 齐江龙
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page toMgAppAlready(Map<String, Object> map) {
		return PageUtil.getPage(map, "rentWrite.toMgAppAlready",
				"rentWrite.toMgAppAlreadyCount");
	}

	/**
	 * 查询首期款-非网银核销数据 findDeductData
	 * 
	 * @date 2013-9-24 上午11:04:47
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page toSupperFundMg(Map<String, Object> map) {
		return PageUtil.getPage(map, "rentWrite.toSupperFundMg",
				"rentWrite.toSupperFundMgCount");
	}

	// 修改数据库导出状态和制造导出数据
	public List toSupper_Upload(List list, Map mapDate) {
		List listDate = new ArrayList();
		String bankFlag = mapDate.get("bankFlag") != null ? mapDate.get(
				"bankFlag").toString() : "1";// 判断选择的银行模版 1：建设银行 2：民生银行
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			map.put("UPLOADTYPE", "1");
			map.put("ROW_NUM", map.get("HEAD_ID"));
			map.put("MONEY", map.get("MONEY"));
			map.put("BANK_NAME", map.get("FI_REALITY_BANK"));
			map.put("BANK_ACCOUNT", map.get("FI_TO_THE_ACCOUNT"));
			map.put("CUST_NAME", map.get("FI_TO_THE_PAYEE"));
			map.put("BANK_CUSTNAME", map.get("FI_TO_THE_PAYEE"));
			map.put("PROJ_ID", map.get("HEAD_ID"));
			map.put("USER_CODE", Security.getUser().getCode());
			map.put("USER_NAME", Security.getUser().getName());
			Dao.update("rentWrite.updateFund_head", map);
			// 取供应商组织架构
			if (map != null && map.get("SUPPLIER_IDS") != null
					&& !map.get("SUPPLIER_IDS").equals("")) {
				String SUPPLIER_IDS = map.get("SUPPLIER_IDS").toString();
				// String[] suppList=SUPPLIER_IDS.split(",");
				// if(suppList.length>0){
				// String supplier_id=suppList[suppList.length-1];
				// Map
				// mapSup=Dao.selectOne("rentWrite.querySuppForCode",supplier_id);
				// map.put("ID_CARD_NO", mapSup.get("ORAGNIZATION_CODE"));
				// }

				Map mapSup = Dao.selectOne("rentWrite.querySuppForCode",
						SUPPLIER_IDS);
				if (mapSup != null) {
					map.put("ID_CARD_NO", mapSup.get("ORAGNIZATION_CODE"));
				}

			}

			listDate.add(map);
		}
		return listDate;
	}

	public List querySupp_uplad_All(Map map) {
		return Dao.selectList("rentWrite.querySupp_uplad_All", map);
	}

	public void destroySuppUp(Map map) {
		Dao.update("rentWrite.destroySuppUp", map);
	}

	public Object queryHeXiaoPage(Map<String, Object> map) {
		return Dao.selectOne("rentWrite.queryHeXiaoPage", map);
	}

	// 通过fundId查询客户List
	public List queryCustByFundID(Map map) {
		return Dao.selectList("rentWrite.queryCustByFundID", map);
	}

	// 通过fundId查询供应商List
	public List querySuppByFundID(Map map) {
		return Dao.selectList("rentWrite.querySuppByFundID", map);
	}

	public int doCancellation(Map<String, Object> map) {
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		List<Map<String, Object>> detailData = JSONArray.fromObject(data
				.get("getDetailData"));

		int i = 0;
		// 当页面参数不为空时， 遍历参数。并作废付款单
		if (detailData != null) {
			for (int k = 0; k < detailData.size(); k++) {

				Map<String, Object> de = detailData.get(k);

				String HEAD_ID = de.get("FUND_ID").toString(); // 付款单号

				if (HEAD_ID != null) {// 当不为空时
					Dao.update("rentWrite.updateBeginState", HEAD_ID);

					// 通过付款单号查询明细项有违约金的吗。有的话将其置为可以核销状态
					List listDunDate = Dao.selectList(
							"rentWrite.queryDunDateAll", HEAD_ID);
					for (int num = 0; num < listDunDate.size(); num++) {
						Map mapDunDate = (Map) listDunDate.get(num);
						if (mapDunDate != null
								&& mapDunDate.get("ITEM_FLAG") != null
								&& !mapDunDate.get("ITEM_FLAG").equals("")) {
							Dao.update("rentWrite.updateJoinDateStauts1",
									mapDunDate);
							String ITEM_FLAG = mapDunDate.get("ITEM_FLAG")
									.toString();
							if (ITEM_FLAG.equals("5")) {
								mapDunDate.put("DUE_STATUS", 1);
								mapDunDate.put("OVERDUE_STATUS", 0);
								mapDunDate.put("PAYLIST_CODE", mapDunDate
										.get("D_PAY_CODE"));
								mapDunDate.put("BEGINNING_NUM", mapDunDate
										.get("PERIOD"));
								Dao.update("rentWrite.updateOverDunStaute",
										mapDunDate);
							}
						}
					}
				}
				Dao.update("rentWrite.deleteCancellationDe", HEAD_ID);// 根据付款单号作废付款单
				i = Dao.update("rentWrite.deleteCancellation", HEAD_ID);// 根据付款单号作废付款单
			}
			return i;
		}

		return i;
	}

	public int doReject(Map<String, Object> map) {
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		List<Map<String, Object>> detailData = JSONArray.fromObject(data
				.get("getDetailData"));
		int i = 0;
		// 遍历参数，驳回
		if (detailData != null) {
			for (int k = 0; k < detailData.size(); k++) {
				Map<String, Object> de = detailData.get(k);
				de.put("FUND_ID", de.get("HEAD_ID"));
				i = Dao.update("rentWrite.doReject", de);// 回更申请单明细。
				if (i > 0) {
					String FUND_ID = de.get("HEAD_ID").toString();// 付款单号
					Dao.delete("rentWrite.doRejectAccount", FUND_ID);// 清除资金扣划到账明细。
				}
			}
			if (i > 0) {
				return i;
			}
		}

		return 0;
	}

	public int doUpdateFHead(Map<String, Object> map) {
		Object data_ = map.get("data");

		Map<String, Object> data = JSONObject.fromObject(data_);

		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data
				.get("getBaseData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		return Dao.update("rentWrite.toUpdateFHead", baseData);
	}

	// public int doCheckedFund(Map<String, Object> map) {
	// // 获取页面参数
	// Object data_ = map.get("data");
	// Map<String, Object> data = JSONObject.fromObject(data_);
	// List<Map<String, Object>> detailData =
	// JSONArray.fromObject(data.get("getDetailData"));
	//
	// int i = 0;
	// if (detailData != null) {
	// for (int j = 0; j < detailData.size(); j++) {
	// Map<String, Object> de = detailData.get(j);
	// de.put("USERCODE", map.get("USERCODE"));
	// de.put("USERNAME", map.get("USERNAME"));
	// de.put("FUND_ID", de.get("HEAD_ID"));// f付款单号
	// // 更新申请表状态，核销时间， 核销银行
	// i = Dao.update(xmlPath + "doUpdateHeadStatus", de);
	// if (i > 0) {// 若跟新成功， 回更应收起初表的实收金额和核销状态;
	//
	// // 根据申请单id查看应收金额和实收总金额
	// List<Map<String, Object>> detialL = Dao.selectList(xmlPath +
	// "getDetailData", de);
	// // 当不为空时跟新应收明细
	// if (detialL != null) {
	// for (int k = 0; k < detialL.size(); k++) {
	// Map<String, Object> realMoney = detialL.get(k);
	// float beginning_money =
	// Float.parseFloat(realMoney.get("BEGINNING_MONEY").toString());// 应收金额
	// float real_money =
	// Float.parseFloat(realMoney.get("REAL_MONEY").toString());// 实收金额
	// realMoney.put("BEGINNING_PAID", real_money);
	// realMoney.put("BEGINNING_ID", realMoney.get("FA_BEGING_ID"));
	// if (beginning_money == real_money) {//
	// 当应收金额==实收金额时，应收明细中的核销状态变更为核销已完成(1);
	// realMoney.put("BEGINNING_FLAG", 1);
	// } else {// 当应收金额!=实收金额时，应收明细中的核销状态为核销未完成(0)
	// realMoney.put("BEGINNING_FLAG", 0);
	// }
	//
	// // 更新应收初期表数据：实收金额和核销状态。
	// i = Dao.update(xmlPath + "doUpdateBeginning", realMoney);
	// }
	// }
	// }
	// }
	//
	// if (i > 0) {
	// return i;
	// } else {
	// return 0;
	// }
	// }
	// return 0;
	// }

	// 明细表
	public int doInsertAccount1(Map<String, Object> map) {
		Object data_ = map.get("data");

		Map<String, Object> data = JSONObject.fromObject(data_);
		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data
				.get("getBaseData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		String FUND_ID = baseData.get("FUND_ID").toString();
		// 金额分拆
		String MONEYORDERSAVE = (baseData.get("MONEYORDERSAVE") != null && !baseData
				.get("MONEYORDERSAVE").equals("")) ? baseData.get(
				"MONEYORDERSAVE").toString() : "";
		if (MONEYORDERSAVE.length() > 0) {
			this.moneyFenChai(MONEYORDERSAVE, FUND_ID);
		}

		// 判断核销实时表中是否已经有实收明细， 如果有则先删除。否则直接添加
		int i = Dao.selectInt("rentWrite.toGetAccountCount", baseData);

		if (i > 0) {// 当核资金/租金扣划到账明细中存在数据，先删除。

			// 删除之前看有没有和租金池对应的数据，有的话先解冻
			Map poolMap = Dao.selectOne("rentWrite.queryAcountPollById",
					baseData);
			if (poolMap != null && poolMap.get("POOLIDS") != null
					&& !poolMap.get("POOLIDS").equals("")) {
				Dao.update("rentWrite.updatePOOLStatus", poolMap);
			}

			i = Dao.delete("rentWrite.delAccountByHeadId", baseData);// 但用户重复点击“到账日期”，并修改对应数据后点击提交是先删除已存在的资金扣划明细
		}

		// 本次实付
		double FI_REALITY_ACCOUNT = (baseData.get("FI_REALITY_ACCOUNT") != null && !baseData
				.get("FI_REALITY_ACCOUNT").equals("")) ? Double
				.parseDouble(baseData.get("FI_REALITY_ACCOUNT").toString())
				: 0d;
		// 本次应付
		double FI_REALITY_MONEY = (baseData.get("FI_REALITY_MONEY") != null && !baseData
				.get("FI_REALITY_MONEY").equals("")) ? Double
				.parseDouble(baseData.get("FI_REALITY_MONEY").toString()) : 0d;

		String FI_TAGE_ID = (baseData.get("FI_TAGE_ID") != null && !baseData
				.get("FI_TAGE_ID").equals("")) ? baseData.get("FI_TAGE_ID")
				.toString() : null;

		// 来款单子表
		List listDe = Dao.selectList("rentWrite.queryDetailByFundId1", baseData);
		if (FI_REALITY_ACCOUNT > FI_REALITY_MONEY)// 本次来款余额
		{
			for (int d = 0; d < listDe.size(); d++) {
				Map mapD = (Map) listDe.get(d);
				Dao.insert("rentWrite.doInsertAccountByDetail", mapD);
			}
		} else if (FI_REALITY_ACCOUNT == FI_REALITY_MONEY)// 正好核销
		{
			for (int d = 0; d < listDe.size(); d++) {
				Map mapD = (Map) listDe.get(d);
				Dao.insert("rentWrite.doInsertAccountByDetail", mapD);
			}
		}
		return 1;
	}

	// 金额拆分方法
	public void moneyFenChai(String MONEYORDERSAVE, String Fund_id) {
		Map mapDate = new HashMap();
		mapDate.put("FUND_ID", Fund_id);
		// 先删后增
		Dao.delete("rentWrite.deleteFundSplit", mapDate);
		String[] a = MONEYORDERSAVE.split(",");
		for (int i = 0; i < a.length; i++) {
			double money = (a[i] != null && !a[i].equals("")) ? Double
					.parseDouble(a[i]) : 0d;
			if (money > 0) {
				Map map = new HashMap();
				map.put("FUND_MONEY", money);
				map.put("FUND_ID", Fund_id);
				Dao.insert("rentWrite.insertFundSplit", map);
			}

		}
	}

	// 明细表
	public int doInsertAccount(Map<String, Object> map) {
		boolean flag = false;
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data
				.get("getBaseData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));

		String FUND_ID = baseData.get("FUND_ID").toString();
		// 金额分拆
		String MONEYORDERSAVE = (baseData.get("MONEYORDERSAVE") != null && !baseData
				.get("MONEYORDERSAVE").equals("")) ? baseData.get(
				"MONEYORDERSAVE").toString() : "";
		if (MONEYORDERSAVE.length() > 0) {
			this.moneyFenChai(MONEYORDERSAVE, FUND_ID);
		}

		// 判断核销实时表中是否已经有实收明细， 如果有则先删除。否则直接添加
		int i = Dao.selectInt("rentWrite.toGetAccountCount", baseData);

		if (i > 0) {// 当核资金/租金扣划到账明细中存在数据，先删除。

			// 删除之前看有没有和租金池对应的数据，有的话先解冻
			Map poolMap = Dao.selectOne("rentWrite.queryAcountPollById",
					baseData);
			if (poolMap != null && poolMap.get("POOLIDS") != null
					&& !poolMap.get("POOLIDS").equals("")) {
				Dao.update("rentWrite.updatePOOLStatus", poolMap);
			}

			i = Dao.delete("rentWrite.delAccountByHeadId", baseData);// 但用户重复点击“到账日期”，并修改对应数据后点击提交是先删除已存在的资金扣划明细
		}

		List finnList = new ArrayList();
		Map finnMap = new HashMap();

		// 如果有余额挂账，向池子插入一条数据。为接口提供数据
		Map mapFund1 = Dao.selectOne("rentWrite.queryFundHeadById1", baseData);
		finnMap.put("Money", mapFund1.get("FI_REALITY_ACCOUNT"));
		finnMap.put("TYPE", "贷方");
		finnMap.put("REMARK", "");
		finnList.add(finnMap);

		// 本次实付
		double FI_REALITY_ACCOUNT = (baseData.get("FI_REALITY_ACCOUNT") != null && !baseData
				.get("FI_REALITY_ACCOUNT").equals("")) ? Double
				.parseDouble(baseData.get("FI_REALITY_ACCOUNT").toString())
				: 0d;
		// 本次应付
		double FI_REALITY_MONEY = (baseData.get("FI_REALITY_MONEY") != null && !baseData
				.get("FI_REALITY_MONEY").equals("")) ? Double
				.parseDouble(baseData.get("FI_REALITY_MONEY").toString()) : 0d;

		String FI_TAGE_ID = (baseData.get("FI_TAGE_ID") != null && !baseData
				.get("FI_TAGE_ID").equals("")) ? baseData.get("FI_TAGE_ID")
				.toString() : null;

		// 来款单子表
		List listDe = Dao.selectList("rentWrite.queryDetailByFundId1", baseData);

		double FI_REALITY_ACCOUNT_YU = FI_REALITY_ACCOUNT;// 本次实付递减
		for (int d = 0; d < listDe.size(); d++) {
			if (flag) {
				break;
			}
			Map mapD = (Map) listDe.get(d);
			double D_PAY_MONEY = (mapD.get("D_PAY_MONEY") != null && !mapD.get(
					"D_PAY_MONEY").equals("")) ? Double.parseDouble(mapD.get(
					"D_PAY_MONEY").toString()) : 0d;

			if (FI_REALITY_ACCOUNT_YU >= D_PAY_MONEY) {
				Dao.insert("rentWrite.doInsertAccountByDetail", mapD);
				FI_REALITY_ACCOUNT_YU = FI_REALITY_ACCOUNT_YU - D_PAY_MONEY;
			} else {
				if (FI_REALITY_ACCOUNT_YU > 0) {
					mapD.put("D_PAY_MONEY", FI_REALITY_ACCOUNT_YU);
					Dao.insert("rentWrite.doInsertAccountByDetail", mapD);
					FI_REALITY_ACCOUNT_YU = 0d;
				}

				if (FI_REALITY_ACCOUNT_YU == 0) {
					// 租金池开始冲抵
					// 循环页面租金池找到租金池可用金额排序记录ID将其至为冻结
					JSONArray listLi = JSONArray.fromObject(data
							.get("getPoolData"));
					for (int li = 0; li < listLi.size(); li++) {
//						if (flag) {
//							break;
//						}
						Map mapLi = (Map) listLi.get(li);

						if (mapLi != null && baseData.get("FI_TAGE_ID") != null
								&& !baseData.get("FI_TAGE_ID").equals("")) {
							if (mapLi.get("POOL_TYPE") != null) {
								double dichong_money = (mapLi
										.get("dichong_money") != null && !mapLi
										.get("dichong_money").equals("")) ? Double
										.parseDouble(mapLi.get("dichong_money")
												.toString())
										: 0d;
								if(dichong_money>0){
									// 租金池
									finnMap.put("Money", dichong_money);
									finnMap.put("TYPE", "借方");
									finnMap.put("REMARK", "");
									finnList.add(finnMap);
									Map mappool = new HashMap();
									mappool.put("OWNER_ID", baseData
											.get("FI_TAGE_ID"));
									mappool.put("TYPE", mapLi.get("POOL_TYPE"));
									List listpool = Dao
											.selectList(
													"rentWrite.queryPoolTypeOwner",
													mappool);
									for (int aa = 0; aa < listpool.size(); aa++) {
										Map mapA = (Map) listpool.get(aa);
										if (mapA.get("CANUSE_MONEY") != null
												&& !mapA.get("CANUSE_MONEY")
														.equals("")) {
											double CANUSE_MONEY = Double
													.parseDouble(mapA.get(
															"CANUSE_MONEY")
															.toString());
											if (dichong_money > 0
													&& dichong_money >= CANUSE_MONEY) {
												// 冻结池子金额
												// 插入数据到细表中
												mapD.put("FA_POOL_ID", mapA
														.get("POOL_ID"));
												mapD.put("D_PAY_MONEY",
														CANUSE_MONEY);
												mapD.put("FA_CAN_USE_MONEY",
														CANUSE_MONEY);
												Dao
														.insert(
																"rentWrite.doInsertAccountByDetail",
																mapD);
												mapD.put("CANUSE_MONEY", 0);
												Dao
														.update(
																"rentWrite.updatePoolStateByfundId",
																mapD);

												dichong_money = dichong_money
														- CANUSE_MONEY;
											} else if(dichong_money > 0
													&& dichong_money < CANUSE_MONEY) {
												// 冻结池子金额
												// 插入数据到细表中
												mapD.put("FA_POOL_ID", mapA
														.get("POOL_ID"));
												mapD.put("D_PAY_MONEY",
														dichong_money);
												mapD.put("FA_CAN_USE_MONEY",
														CANUSE_MONEY);
												Dao
														.insert(
																"rentWrite.doInsertAccountByDetail",
																mapD);
												mapD.put("CANUSE_MONEY",
														CANUSE_MONEY
																- dichong_money);
												Dao
														.update(
																"rentWrite.updatePoolStateByfundId",
																mapD);
												dichong_money = 0;
											}

											if (dichong_money == 0) {
												flag = true;
												break;// 跳出
											}
										}
									}
								}
								
							}

						}
					}

					break;
				}
			}

		}

		// 更新申请表状态，核销时间， 核销银行
		i = Dao.update("rentWrite.doUpdateHeadStatus", baseData);
		// 查询核销时间，操作罚息
		Map mapTime = Dao.selectOne("rentWrite.queryFundTime", baseData);
		String FI_ACCOUNT_DATE = (mapTime != null
				&& mapTime.get("FI_ACCOUNT_DATE") != null && !mapTime.get(
				"FI_ACCOUNT_DATE").equals("")) ? mapTime.get("FI_ACCOUNT_DATE")
				.toString() : "";
		Dao.update("rentWrite.doUpdateDetail", baseData);
		if (i > 0) {// 若跟新成功， 回更应收起初表的实收金额和核销状态;

			// 根据申请单id查看应收金额和实收总金额
			List<Map<String, Object>> detialL = Dao.selectList("rentWrite.queryDetailByFundid", baseData);
			// 当不为空时跟新应收明细
			if (detialL != null) {
				for (int k = 0; k < detialL.size(); k++) {
					Map<String, Object> realMoney = detialL.get(k);
					//更新中间表数据
							Dao.update("rentWrite.updateBegin", realMoney);
							Dao.update("rentWrite.updateBeginJoin",realMoney);
							Map mapMo = Dao.selectOne("rentWrite.selectmony", realMoney);
							if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
								realMoney.put("TIME", FI_ACCOUNT_DATE);
								Dao.update("rentWrite.updateBeginState1", realMoney);
								realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
								this.dunUpdateStatus11(realMoney);
								
							}
						
					
				}
			}
		}

		this.insertBeginByDun(baseData.get("FUND_ID").toString(),FI_ACCOUNT_DATE);
		this.queryPaylist_codeNum(baseData.get("FUND_ID").toString());
		return 1;
	}

	// 求租金池剩余金额
	public double queryPoolMoney(Map map) {
		Map mapDate = Dao.selectOne("rentWrite.queryPoolTypeOwnerMoney", map);
		if (mapDate != null) {
			return (mapDate != null && mapDate.get("CANUSE_MONEY") != null && !mapDate
					.get("CANUSE_MONEY").equals("")) ? Double
					.parseDouble(mapDate.get("CANUSE_MONEY").toString()) : 0d;
		} else {
			return 0d;
		}

	}

	// 求上次保存金额
	public double queryPoolMoneyDe(Map map) {
		Map mapDate = Dao.selectOne("rentWrite.querySvaePoolMoney", map);
		if (mapDate != null) {
			return (mapDate != null && mapDate.get("CANUSE_MONEY") != null && !mapDate
					.get("CANUSE_MONEY").equals("")) ? Double
					.parseDouble(mapDate.get("CANUSE_MONEY").toString()) : 0d;
		} else {
			return 0d;
		}
	}

	// 查询是否有池子存在
	public int queryPoolNumber(Map map) {
		return Dao.selectInt("rentWrite.queryPoolNumber", map);
	}

	public int doCheckedFund(Map<String, Object> map) {
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		List<Map<String, Object>> detailData = JSONArray.fromObject(data
				.get("getDetailData"));

		int i = 0;
		if (detailData != null) {
			for (int j = 0; j < detailData.size(); j++) {
				Map<String, Object> de = detailData.get(j);
				de.put("USERCODE", map.get("USERCODE"));
				de.put("USERNAME", map.get("USERNAME"));
				de.put("FUND_ID", de.get("HEAD_ID"));// f付款单号
				// 更新申请表状态，核销时间， 核销银行
				i = Dao.update("rentWrite.doUpdateHeadStatus", de);

				// 查询核销时间，操作罚息
				Map mapTime = Dao.selectOne("rentWrite.queryFundTime", de);
				String FI_ACCOUNT_DATE = (mapTime != null
						&& mapTime.get("FI_ACCOUNT_DATE") != null && !mapTime
						.get("FI_ACCOUNT_DATE").equals("")) ? mapTime.get(
						"FI_ACCOUNT_DATE").toString() : "";
						
				Dao.update("rentWrite.doUpdateDetail", de);
				if (i > 0) {// 若跟新成功， 回更应收起初表的实收金额和核销状态;

					// 根据申请单id查看应收金额和实收总金额
					List<Map<String, Object>> detialL = Dao.selectList(
							"rentWrite.queryDetailByFundid", de);
					// 当不为空时跟新应收明细
					if (detialL != null) {
						for (int k = 0; k < detialL.size(); k++) {
							Map<String, Object> realMoney = detialL.get(k);
							
							
							System.out.println("-------------------realMoney="+realMoney);
							//更新中间表数据
									Dao.update("rentWrite.updateBegin", realMoney);
									Dao.update("rentWrite.updateBeginJoin",realMoney);
									Map mapMo = Dao.selectOne("rentWrite.selectmony", realMoney);
									if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
										realMoney.put("TIME", FI_ACCOUNT_DATE);
										Dao.update("rentWrite.updateBeginState1", realMoney);
										realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
										this.dunUpdateStatus11(realMoney);
									}
							
							
						}
					}

					List finnList = new ArrayList();
					Map finnMap = new HashMap();

					// 如果有余额挂账，向池子插入一条数据。为接口提供数据
					Map mapFund1 = Dao.selectOne(
							"rentWrite.queryFundHeadById1", de);
					finnMap.put("Money", mapFund1.get("FI_REALITY_ACCOUNT"));
					finnMap.put("TYPE", "贷方");
					finnMap.put("REMARK", "");
					finnList.add(finnMap);

					Map mapFund = Dao.selectOne("rentWrite.queryFundHeadById",
							de);
					if (mapFund != null) {
						String FI_FLAG = mapFund.get("FI_FLAG").toString();
						if (FI_FLAG.equals("3")) {// 挂账到承租人资金池
							Map mapPool = new HashMap();
							mapPool.put("SOURCE_ID", mapFund.get("ID"));
							mapPool.put("TYPE", 5);
							mapPool.put("STATUS", 1);
							mapPool.put("BASE_MONEY", mapFund
									.get("FI_TAGE_MONEY"));
							mapPool.put("CANUSE_MONEY", mapFund
									.get("FI_TAGE_MONEY"));
							mapPool.put("OWNER_ID", mapFund.get("FI_TAGE_ID"));
							Dao.insert("rentWrite.insertPoolDate", mapPool);
						} else if (FI_FLAG.equals("5"))// 挂账供应商电汇资金池
						{
							Map mapPool = new HashMap();
							mapPool.put("SOURCE_ID", mapFund.get("ID"));
							mapPool.put("TYPE", 2);
							mapPool.put("STATUS", 1);
							mapPool.put("BASE_MONEY", mapFund
									.get("FI_TAGE_MONEY"));
							mapPool.put("CANUSE_MONEY", mapFund
									.get("FI_TAGE_MONEY"));
							mapPool.put("OWNER_ID", mapFund.get("FI_TAGE_ID"));
							Dao.insert("rentWrite.insertPoolDate", mapPool);
						}
						finnMap.put("Money", mapFund.get("FI_TAGE_MONEY"));
						finnMap.put("TYPE", "借方");
						finnMap.put("REMARK", "");
						finnList.add(finnMap);
					}
				}

				this.insertBeginByDun(de.get("HEAD_ID").toString(),
						FI_ACCOUNT_DATE);
				this.queryPaylist_codeNum(de.get("HEAD_ID").toString());
			}

			if (i > 0) {
				return i;
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	// 当租金都核销完成后将当期违约金发出来可以核销map里有BEGINNING_ID
	public void dunUpdateStatus11(Map map) {
		Map mapBegin=Dao.selectOne("rentWrite.mapBegin", map);
		Map payListMap = Dao.selectOne("rentWrite.queryDunMoneyStatus", mapBegin);
		Dao.update("rentWrite.updateRENT_PAIDDue",payListMap);
		if (payListMap != null && payListMap.get("SHENYUMONEY") != null && !payListMap.get("SHENYUMONEY").equals("")) {
			double SHENYUMONEY = Double.parseDouble(payListMap.get("SHENYUMONEY").toString());
			payListMap.put("RENT_PAID", payListMap.get("MONEYNEW"));
			payListMap.put("TIME", map.get("TIME"));
			if (SHENYUMONEY <= 0.0001)// 标示这一期都核销完了，修改状态
			{
				// 1，修改实际到账日期以后的数据状态为可核销状态
				
				Dao.update("rentWrite.updateDunStatusNew1", payListMap);
			}
			else{//这一期没有核销完成，将有逾期数据的数据更新
				//
				Dao.update("rentWrite.updateDunStatusNew", payListMap);
			}
			//如果应收违约金为null 删除掉
			Dao.delete("rentWrite.deleteDunWei",payListMap);
		}
	}

	// 当租金都核销完成后将当期违约金发出来可以核销map里有BEGINNING_ID
	public void dunUpdateStatus(Map map) {
		Map mapBegin=Dao.selectOne("rentWrite.mapBegin", map);
		Map payListMap = Dao.selectOne("rentWrite.queryDunMoneyStatus", mapBegin);
		Dao.update("rentWrite.updateRENT_PAIDDue",payListMap);
		if (payListMap != null && payListMap.get("SHENYUMONEY") != null
				&& !payListMap.get("SHENYUMONEY").equals("")) {
			double SHENYUMONEY = Double.parseDouble(payListMap.get(
					"SHENYUMONEY").toString());
			if (SHENYUMONEY <= 0.0001)// 标示这一期都核销完了，修改状态
			{
				// 1，修改实际到账日期时候的状态为可核销状态
				// 2.删除比时间到账日期大的日期生产的罚息
				// 3.重新计算罚息
				payListMap.put("TIME", map.get("TIME"));
				Dao.update("rentWrite.updateDunStatus", payListMap);
				Dao.delete("rentWrite.deleteDunDate", payListMap);
				
			}
		}
	}

	// 计算罚息，防止运行多遍，在核销结束时候运行
	public void dunCommit() {
		// new Thread() {
		// public void run() {
		// try {
		// OverdueService service = new OverdueService();
		// service.upDueAll();
		// } catch (Exception e) {
		// e.printStackTrace();
		// Dao.rollback();
		// }finally{
		// Dao.commit();
		// Dao.close();
		// }
		// };
		// }.start();
	}

	// 当核销完成后，通过核销单判断此核销单中有没有核销违约金，当有违约金将此条数据插入到起初表
	public void insertBeginByDun(String fund_id, Object REALITY_TIME) {
		Map map = new HashMap();
		map.put("FUND_ID", fund_id);
		List list = Dao.selectList("rentWrite.queryDDunByFund", map);
		for (int i = 0; i < list.size(); i++) {
			Map mapDate = (Map) list.get(i);
			// 修改逾期表中该违约金的状态
			Map mapDunDate = new HashMap();
			mapDunDate.put("DUE_STATUS", 1);
			mapDunDate.put("OVERDUE_STATUS", 0);
			mapDunDate.put("PAYLIST_CODE", mapDate.get("D_PAY_CODE"));
			mapDunDate.put("BEGINNING_NUM", mapDate.get("PERIOD"));
			mapDunDate.put("REALITY_TIME", REALITY_TIME);
			mapDunDate.put("D_RECEIVE_MONEY", mapDate.get("D_PAY_MONEY"));
			Map dunMap=Dao.selectOne("rentWrite.fi_overDueOne",mapDunDate);
			if(dunMap!=null){
				mapDunDate.put("DUNID", dunMap.get("DUNID"));
				Dao.update("rentWrite.updateOverDunStaute11", mapDunDate);
				Dao.delete("rentWrite.deleteDunDateAll11", mapDunDate);
			}
			
			Dao.update("rentWrite.updateFundDateByJoinDunNew", mapDunDate);
			// 删除逾期没有实收日期记录的数据
			
			// //通过支付表和期次查询应付时间(将违约金写入起初表)
			// Map mapTime=Dao.selectOne("rentWrite.queryDateByCode", mapDate);
			// mapDate.put("BEGINNING_RECEIVE_DATA",
			// mapTime.get("BEGINNING_RECEIVE_DATA"));
			// mapDate.put("BE_ID", Dao.getSequence("SEQ_FI_R_BEGINNING"));
			// mapDate.put("REALITY_TIME", REALITY_TIME);
			// Dao.insert("rentWrite.insertBeginningByDun",mapDate);
			// Dao.update("rentWrite.updateDeBegId", mapDate);
		}
	}
	
	
	//当核销完成后根据核销单查询出所有支付表编号和期次
	public void queryPaylist_codeNum(String FUND_ID){
		
		List list=Dao.selectList("rentWrite.queryPaylist_codeNum",FUND_ID);
		for(int i=0;i<list.size();i++){
			Map mapDate=(Map)list.get(i);
			if(mapDate!=null){
				Dao.delete("rentWrite.deleteJoinDate",mapDate);
				Dao.insert("rentWrite.insertJoinDate",mapDate);
			}
		}
	}

	public Map querySupp_Bank(Map mapDate) {
		return Dao.selectOne("rentWrite.querySupp_Bank", mapDate);
	}

	public void saveSupp_bank(Map map) {
		Map mapUser = new HashMap();
		if (map != null) {

			Map mapDate = Dao.selectOne("rentWrite.querySupp_Bank", map);
			if (mapDate != null) {
				Dao.update("rentWrite.updateSupp_Bank", map);
			} else {
				Dao.insert("rentWrite.insertSupp_Bank", map);
			}
		}

	}

	public int updateFundHeadBank(Map map) {
		return Dao.update("rentWrite.updateFundHeadBank", map);
	}

	public Page queryFundDetailAll(Map<String, Object> m) {
		
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		return PageUtil.getPage(m, "rentWrite.queryFundDetailAll",
				"rentWrite.queryFundDetailAll_Count");
	}

	// 导出
	public Excel HEAD_Upload(Map map) {
		String type = "1";
		if (map != null && map.get("type") != null) {
			type = map.get("type").toString();
		}

		String ORG_LIST = BaseUtil.getSupOrgChildren();
		map.put("ORG_LIST", ORG_LIST);

		List<Map<String, Object>> dataList = Dao.selectList(
				"rentWrite.HEAD_Upload", map);
		Excel excel = new Excel();
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();

		title.put("ROW_NUM", "序号");
		title.put("HEAD_ID", "付款单号");
		title.put("FI_PAY_DATE", "计划还款日");
		title.put("PRO_CODE", "项目编号");
		title.put("CUSTNAME", "客户名称");
		title.put("COMPANY_NAME", "厂商");
		title.put("SUP_NAME", "供应商");
		title.put("EQUIPMENINFOS", "租赁物名称");
		title.put("FI_PRO_NAME", "款项名称");
		title.put("BEGINNING_NUM", "期次");
		title.put("D_RECEIVE_DATE", "计划收取日期");
		title.put("BEGINNING_MONEY", "本次应收金额");

		excel.addTitle(title);

		excel.setHasTitle(true);
		return excel;
	}

	// 创建临时数据
	public void CreateJoinFundDate() {
//		Dao.delete("rentWrite.deleteJoinFundDate");
		Dao.insert("rentWrite.JoinFundDateApp");
	}
	
	//查询锁
	public String JoinLock(){
		Map map=Dao.selectOne("rentWrite.JoinLock");
		if(map==null){
			return "1";
		}
		return map.get("LOCKTYPE").toString();
	}
	
	public void updateLockType(Map map){
		int i=Dao.update("rentWrite.updateLockType", map);
		Dao.commit();
		Dao.close();
	}
	
	public int LockTypeIsF(Map map){
		int num=1;//正常
		int num1=Dao.selectInt("rentWrite.queryJoinLockCom", map);
		if(num1>0){
			num=2;//已锁定
		}
		else{
			int num2=Dao.selectInt("rentWrite.queryJoinCom", map);
			if(num2>0){
				num=3;//有数据状态变化
			}
		}
		return num;
		
	}
	
	//跑违约金
	public void aa(List list,Map mapTime){
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			String FUND_IDNEW=(map.get("deducted_id")!=null && !map.get("deducted_id").equals(""))?map.get("deducted_id").toString():"";
			String[] dateStr=FUND_IDNEW.split(",");
			String paylist_codeUp=dateStr[0];
			String beginning_numUp=dateStr[1];
			String item_typeUp=dateStr[2];
			if(item_typeUp.equals("5")){
				if (map != null && map.get("bank_flag") != null && map.get("bank_flag").equals("1"))// 成功
				{
					System.out.println("-------------------违约金-----------------");
					Map mapDate=new HashMap();
					mapDate.put("paylist_codeUp", paylist_codeUp);
					mapDate.put("beginning_numUp", beginning_numUp);
					mapDate.put("TIME", mapTime.get("fromDate"));
					Map map1=Dao.selectOne("rentWrite.test001", mapDate);
					if(map1==null){
						//制造一条数据
						Dao.insert("rentWrite.test002",mapDate);
						Dao.delete("rentWrite.test005",mapDate);
					}
					else{
						mapDate.put("ID", map1.get("ID"));
						Dao.update("rentWrite.test003",mapDate);
						Dao.delete("rentWrite.test004",mapDate);
					}
				}
				
			}
		}
		
	}
	
	//跑租金核销成功的违约金处理
	public void bb(List list,Map mapTime){
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			String FUND_IDNEW=(map.get("deducted_id")!=null && !map.get("deducted_id").equals(""))?map.get("deducted_id").toString():"";
			String[] dateStr=FUND_IDNEW.split(",");
			String paylist_codeUp=dateStr[0];
			String beginning_numUp=dateStr[1];
			String item_typeUp=dateStr[2];
			String time = mapTime.get("fromDate").toString();
			if(item_typeUp.equals("2")){//租金
				if (map != null && map.get("bank_flag") != null && map.get("bank_flag").equals("1"))// 成功
				{
					System.out.println("-------------------租金-----------------");
					Map mapDate=new HashMap();
					mapDate.put("PAYLIST_CODE", paylist_codeUp);
					mapDate.put("BEGINNING_NUM", beginning_numUp);
					mapDate.put("TIME", time);//核销租金时间
					
					//先查询核销单，看是否被核销掉（只跑网银的，足额核销）
					Map mapBeg=Dao.selectOne("rentWrite.test006",mapDate);
					int numMin=Integer.parseInt(mapBeg.get("BEGINNING_FLAG").toString());
					if(numMin==0){//未核销
						System.out.println("-未核销---PAYLIST_CODE="+mapDate.get("PAYLIST_CODE")+"--------BEGINNING_NUM="+mapDate.get("BEGINNING_NUM")+"----------BEGINNING_MONEY="+mapBeg.get("BEGINNING_MONEY")+"--------------------BEGINNING_PAID="+mapBeg.get("BEGINNING_PAID"));
					}
					else{
						//已核销
						//判断违约金是否已核销
						List listW=Dao.selectList("rentWrite.test007", mapDate);
						if(listW.size()>1){
							System.out.println("----------违约金核销了两遍---------PAYLIST_CODE="+mapDate.get("PAYLIST_CODE")+"--------BEGINNING_NUM="+mapDate.get("BEGINNING_NUM")+"-------");
						}
						else if(listW.size()==1){
							
							System.out.println("----------违约金核销了yi遍---------PAYLIST_CODE="+mapDate.get("PAYLIST_CODE")+"--------BEGINNING_NUM="+mapDate.get("BEGINNING_NUM")+"-------");
							
							
							Map mapDate1=new HashMap();
							mapDate1.put("BEGINNING_PAID", mapBeg.get("BEGINNING_PAID"));
							mapDate1.put("paylist_codeUp", paylist_codeUp);
							mapDate1.put("beginning_numUp", beginning_numUp);
							mapDate1.put("TIME", mapTime.get("fromDate"));
							Map map1=Dao.selectOne("rentWrite.test001", mapDate1);
							if(map1==null){
								//制造一条数据
								Dao.insert("rentWrite.test002",mapDate1);
								Dao.delete("rentWrite.test005",mapDate1);
							}
							else{
								mapDate1.put("ID", map1.get("ID"));
								Dao.update("rentWrite.test003",mapDate1);
								Dao.delete("rentWrite.test004",mapDate1);
							}
						}
						else{
							Dao.update("rentWrite.updateDunStatusNew1", mapDate);
						}
						
					}
				}
				
			}
			else if(item_typeUp.equals("5")){
				if (map != null && map.get("bank_flag") != null && map.get("bank_flag").equals("1"))// 成功
				{
					System.out.println("------------------------------------");
					Map mapDate=new HashMap();
					
					mapDate.put("paylist_codeUp", paylist_codeUp);
					mapDate.put("beginning_numUp", beginning_numUp);
					mapDate.put("TIME", mapTime.get("fromDate"));
					mapDate.put("PAYLIST_CODE", paylist_codeUp);
					mapDate.put("BEGINNING_NUM", beginning_numUp);
					Map mapBeg=Dao.selectOne("rentWrite.test006",mapDate);
					mapDate.put("BEGINNING_PAID", mapBeg.get("BEGINNING_PAID"));
					Map map1=Dao.selectOne("rentWrite.test001", mapDate);
					if(map1==null){
						//制造一条数据
						Dao.insert("rentWrite.test002",mapDate);
						Dao.delete("rentWrite.test005",mapDate);
					}
					else{
						mapDate.put("ID", map1.get("ID"));
						Dao.update("rentWrite.test003",mapDate);
						Dao.delete("rentWrite.test004",mapDate);
					}
				}
			}
		}
		
	}
	
	//检测核销的数据新建了核销单
	public void cc(List list,Map mapTime){
		//--------
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			String FUND_IDNEW=(map.get("deducted_id")!=null && !map.get("deducted_id").equals(""))?map.get("deducted_id").toString():"";
			String[] dateStr=FUND_IDNEW.split(",");
			String paylist_codeUp=dateStr[0];
			String beginning_numUp=dateStr[1];
			String item_typeUp=dateStr[2];
			String time = mapTime.get("fromDate").toString();
			Map mapDate=new HashMap();
			mapDate.put("PAYLIST_CODE", paylist_codeUp);
			mapDate.put("BEGINNING_NUM", beginning_numUp);
			
			String REMARK="";
			if (map != null && map.get("bank_flag") != null && map.get("bank_flag").equals("1"))// 成功
			{
				REMARK="银行核销成功";
			}
			else{
				REMARK="银行核销失败";
			}
			
			if(item_typeUp.equals("2")){//租金
				Map mapZU=Dao.selectOne("rentWrite.test009",mapDate);
				if(mapZU!=null && mapZU.get("NUM")!=null && !mapZU.get("NUM").equals("")){
					String num=mapZU.get("NUM").toString();
					if(num.equals("2")){
						;
					}
					else{
						System.out.println("-----租金--------------PAYLIST_CODE="+paylist_codeUp+"--------------------BEGINNING_NUM="+beginning_numUp);
					}
				}
				else{
					System.out.println("-----租金--------------PAYLIST_CODE="+paylist_codeUp+"--------------------BEGINNING_NUM="+beginning_numUp);
				}
			}
			else if(item_typeUp.equals("5")){//违约金
				Map mapDue=Dao.selectOne("rentWrite.test008",mapDate);
				if(mapDue!=null && mapDue.get("NUM")!=null && !mapDue.get("NUM").equals("")){
					String num=mapDue.get("NUM").toString();
					if(num.equals("1")){
						
					}
					else{
						Map mapStatue=Dao.selectOne("rentWrite.test010", mapDate);
						System.out.println("-----违约金-----状态="+mapStatue.get("STATUS")+"---------PAYLIST_CODE="+paylist_codeUp+"-------BEGINNING_NUM="+beginning_numUp+"------------"+paylist_codeUp+","+beginning_numUp+",5  "+REMARK);
					}
				}
				else{
					Map mapStatue=Dao.selectOne("rentWrite.test010", mapDate);
					System.out.println("-----违约金-----状态="+mapStatue.get("STATUS")+"---------PAYLIST_CODE="+paylist_codeUp+"-------BEGINNING_NUM="+beginning_numUp+"------------"+paylist_codeUp+","+beginning_numUp+",5  "+REMARK);
				}
			}
		}
	}
	
	//测试已核销回执数据准确性
	public void dd(List list,Map mapTime){
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			String FUND_IDNEW=(map.get("deducted_id")!=null && !map.get("deducted_id").equals(""))?map.get("deducted_id").toString():"";
			String[] dateStr=FUND_IDNEW.split(",");
			String paylist_codeUp=dateStr[0];
			String beginning_numUp=dateStr[1];
			String item_typeUp=dateStr[2];
			String time = mapTime.get("fromDate").toString();
			if (map != null && map.get("bank_flag") != null && map.get("bank_flag").equals("1"))// 成功
			{
				Map mapDate=new HashMap();
				mapDate.put("PAYLIST_CODE", paylist_codeUp);
				mapDate.put("PERIOD", beginning_numUp);
				mapDate.put("TYPE", item_typeUp);
				mapDate.put("MONEY", map.get("deducted_money"));
				mapDate.put("TIME", time);
				System.out.println("-----------------mapDate="+mapDate);
				Dao.insert("rentWrite.test017", mapDate);
			}
		}
		
	}
	
	
	
	//制造核销单
	public void gg(){
		List list=Dao.selectList("rentWrite.test012");
		for(int i=0;i<list.size();i++)
		{
				Map map = (Map) list.get(i);
				
				List listDate=Dao.selectList("rentWrite.test015", map);
				
					Map mapDate=(Map)listDate.get(0);
					map.putAll(mapDate);
					// 插入付款细单
					String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
						
					// 插入数据库数据
					map.put("fund_head_id", fund_head_id);
					map.put("PROJ_ID", fund_head_id);
					// 插入付款单
					map.put("FI_PRO_NAME", "违约金");
					map.put("D_STATUS", 2);
					map.put("USER_ID", Security.getUser().getId());
					map.put("USER_NAME", Security.getUser().getName());
					// 组织机构应该取缓存 后面在改
					Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", map);
					if (ORG_MAP != null) {
						map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
					}
					Dao.insert("rentWrite.test014", map);

					// 通过项目CODE查询出发票开具对象
					Map InvoiceMap = Dao.selectOne("rentWrite.queryFundInvoice",map);
					if (InvoiceMap != null) {
						map.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
						map.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
					}
					Dao.insert("rentWrite.test016", map);
				
		}
	}
	
	
	
	//制造租金核销单
	public void ee(){
		List list=Dao.selectList("rentWrite.test025");
		for(int i=0;i<list.size();i++)
		{
				Map map = (Map) list.get(i);
				//先查询是否有核销单
				//如果有的话
				String type=map.get("TYPE").toString();
				if(type.equals("2")){
						Map map2 = (Map) list.get(i);
						
						Map listDate=Dao.selectOne("rentWrite.test021", map2);
						map.putAll(listDate);
						// 插入付款细单
						String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
						// 插入数据库数据
						map.put("fund_head_id", fund_head_id);
						// 插入付款单
						map.put("FI_PRO_NAME", "租金");
						map.put("D_STATUS", 1);
						map.put("USER_ID", Security.getUser().getId());
						map.put("USER_NAME", Security.getUser().getName());
						// 组织机构应该取缓存 后面在改
						Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", map);
						if (ORG_MAP != null) {
							map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
						}
						Dao.insert("rentWrite.test014", map);

						// 通过项目CODE查询出发票开具对象
						Map InvoiceMap = Dao.selectOne("rentWrite.queryFundInvoice",map);
						if (InvoiceMap != null) {
							map.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
							map.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
						}
						List listBeg=Dao.selectList("rentWrite.test0023",map2);
						for(int hh=0;hh<listBeg.size();hh++){
							Map mapBeg=(Map)listBeg.get(hh);
							mapBeg.putAll(map);
							Dao.insert("rentWrite.test022", mapBeg);
						}
						
					
				}
				else if(type.equals("5")){
					
					List listDate=Dao.selectList("rentWrite.test015", map);
					
						Map mapDate=(Map)listDate.get(0);
//						map.putAll(mapDate);
//						// 插入付款细单
//						String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
//							
//						// 插入数据库数据
//						map.put("fund_head_id", fund_head_id);
//						map.put("PROJ_ID", fund_head_id);
//						// 插入付款单
//						map.put("FI_PRO_NAME", "违约金");
//						map.put("D_STATUS", 2);
//						map.put("USER_ID", Security.getUser().getId());
//						map.put("USER_NAME", Security.getUser().getName());
//						// 组织机构应该取缓存 后面在改
//						Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", map);
//						if (ORG_MAP != null) {
//							map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
//						}
//						Dao.insert("rentWrite.test014", map);
//
//						// 通过项目CODE查询出发票开具对象
//						Map InvoiceMap = Dao.selectOne("rentWrite.queryFundInvoice",map);
//						if (InvoiceMap != null) {
//							map.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
//							map.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
//						}
//						Dao.insert("rentWrite.test016", map);
					
					Map mapOne=Dao.selectOne("rentWrite.test026", map);
					if(mapOne!=null)
					{
						mapOne.put("BEGINNING_PAID", mapDate.get("BEGINNING_PAID"));
						System.out.println("-------------------------------map="+mapOne);
						Dao.update("rentWrite.test027",mapOne);
						Dao.update("rentWrite.test028",mapOne);
					}
				}
		}
	}
	
	//制造提前结清核销违约金的数据
	public void ff(){
		Map map=new HashMap();
		map.put("PAYLIST_CODE", "SFCDSL10006-1");
		map.put("PERIOD", "35");
		map.put("FI_ACCOUNT_DATE", "2013-12-20");
		List listDate=Dao.selectList("rentWrite.test015", map);
		
		Map mapDate=(Map)listDate.get(0);
		map.putAll(mapDate);
		// 插入付款细单
		String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
			
		// 插入数据库数据
		map.put("fund_head_id", fund_head_id);
		map.put("PROJ_ID", fund_head_id);
		// 插入付款单
		map.put("FI_PRO_NAME", "违约金");
		map.put("D_STATUS", 2);
		map.put("USER_ID", Security.getUser().getId());
		map.put("USER_NAME", Security.getUser().getName());
		// 组织机构应该取缓存 后面在改
		Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", map);
		if (ORG_MAP != null) {
			map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
		}
		Dao.insert("rentWrite.test029", map);

		// 通过项目CODE查询出发票开具对象
		Map InvoiceMap = Dao.selectOne("rentWrite.queryFundInvoice",map);
		if (InvoiceMap != null) {
			map.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
			map.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
		}
		Dao.insert("rentWrite.test016", map);
	}
	
	
	// 插入数据库和制造导出数据
	public void bank_Not_C_Submit(Map mapDate) {
		
		mapDate.put("USER_CODE", Security.getUser().getCode());
		mapDate.put("USER_NAME", Security.getUser().getName());
		String SUPPLIER_NAME = "";
		double moneyAll = 0d;
		JSONObject mapSelect = JSONObject.fromObject(mapDate
				.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
			mapDate.put("fund_head_id", fund_head_id);
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			Map<String, Object> m = Dao.selectOne("rentWrite.selectQueryOne",map);
			String MONEY_FLAG=(m.get("MONEY_FLAG")!=null && !m.get("MONEY_FLAG").equals(""))?m.get("MONEY_FLAG").toString():"0";
			if (m.get("PAID_MONEY") != null && !m.get("PAID_MONEY").equals("")
					&& !m.get("PAID_MONEY").equals("0")) {
				// 修改零时表状态
				SUPPLIER_NAME = m.get("SUP_NAME").toString();
				mapDate.put("SUPP_ID", m.get("SUP_ID").toString());
				mapDate.put("SUPP_NAME", m.get("SUP_NAME").toString());
				mapDate.put("FI_PAY_MONEY", m.get("PAID_MONEY"));
				Dao.update("rentWrite.updateJoinDateStauts", m);
				String ITEM_FLAG = "";
				if (m.get("ITEM_FLAG") != null
						&& !m.get("ITEM_FLAG").equals(""))// 租金
				{
					ITEM_FLAG = m.get("ITEM_FLAG").toString();
				}
				if (ITEM_FLAG.equals("2"))// 租金
				{
					double moneyPay = Double.parseDouble(m.get("PAID_MONEY")
							.toString());
					List listDetail = Dao.selectList(
							"rentWrite.queryDetailByPayNum", m);
					if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
					{
						
						for (int i = 0; i < listDetail.size(); i++) {
							Map detaMap = (Map) listDetail.get(i);
							mapDate.put("FI_PRO_NAME", "租金");
							detaMap.put("fund_head_id", fund_head_id);
							detaMap.put("CUSTNAME", m.get("CUSTNAME"));
							detaMap.put("CUST_ID", m.get("CUST_ID"));
							detaMap.put("PRO_CODE", m.get("PRO_CODE"));
							int flag = 0;
							// 通过项目CODE查询出发票开具对象
							Map InvoiceMap = Dao.selectOne("rentWrite.queryFundInvoice", detaMap);
							if (InvoiceMap != null) {
								detaMap.put("INVOICE_TARGET_TYPE",InvoiceMap.get("INVOICE_TARGET_TYPE"));
								detaMap.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
							}
							if(MONEY_FLAG.equals("1")){
									double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
									detaMap.put("D_PAY_MONEYLB", moneyLixi);
									detaMap.put("MONEYCURR", moneyLixi);
									detaMap.put("D_STATUS", 1);
									flag = Dao.insert("rentWrite.createDetailByBankNot",detaMap);
							}
							else{
								if (moneyPay > 0) {
									if (i == 0)// 先扣利息
									{
										double moneyLixi = Double
												.parseDouble(detaMap.get(
														"BEGINNING_MONEY")
														.toString());
										detaMap.put("D_PAY_MONEYLB", moneyLixi);
										if (moneyPay <= moneyLixi) {
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(
													"rentWrite.createDetailByBankNot",
													detaMap);
											moneyPay = 0;
										} else {
											
											detaMap.put("MONEYCURR", moneyLixi);
											detaMap.put("D_STATUS", 1);
											
											flag = Dao.insert(
													"rentWrite.createDetailByBankNot",
													detaMap);
											moneyPay = moneyPay - moneyLixi;
										}
									} else// 再扣本金
									{
										double moneyBenj = Double
												.parseDouble(detaMap.get(
														"BEGINNING_MONEY")
														.toString());
										detaMap.put("D_PAY_MONEYLB", moneyBenj);
										if (moneyPay <= moneyBenj) {
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(
													"rentWrite.createDetailByBankNot",
													detaMap);
											moneyPay = 0;
										} else {
											detaMap.put("MONEYCURR", moneyBenj);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(
													"rentWrite.createDetailByBankNot",
													detaMap);
											moneyPay = moneyPay - moneyBenj;
										}
									}
							}
							
							
								
							}
							
							if (flag > 0) {
								// 反更状态位（从未核销更新为网银核销）
								Map mapUpdate = new HashMap();
								mapUpdate.put("BEGINNING_ID", detaMap
										.get("BEGINNING_ID"));
								mapUpdate.put("BEGINNING_STATUS", 1);
								Dao
										.update(
												"rentWrite.updateBeggingStatusByID",
												mapUpdate);
							}

						}
					}
					
					mapDate.put("USER_ID", Security.getUser().getId());
					// 组织机构应该取缓存 后面在改
					Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", mapDate);
					if (ORG_MAP != null) {
						mapDate.put("ORG_ID", ORG_MAP.get("ORG_ID"));
					}
					
					//判断是租金科提交还是供应商自己提交
					String APP_CREATE_TYPE=mapDate.get("APP_CREATE_TYPE").toString();
					String APP_CREATE="";
					if(APP_CREATE_TYPE.equals("1")){//供应商
						APP_CREATE=SUPPLIER_NAME;
					}
					else{//租金科
						APP_CREATE="租金科";
					}
					mapDate.put("APP_CREATE", APP_CREATE);
					mapDate.put("SUPPLIER_NAME", SUPPLIER_NAME);
					mapDate.put("FI_PROJECT_NUM", 1);
					mapDate.put("FI_PAY_MONEY", m.get("PAID_MONEY"));
					mapDate.put("BANK_NAME", m.get("BANK_CUSTNAME"));
					mapDate.put("BANK_ACCOUNT", m.get("BANK_ACCOUNT"));
					// 插入付款细单
					Dao.insert("rentWrite.createFundHead_Not", mapDate);
				} else// 违约金
				{
					m.put("fund_head_id", fund_head_id);
					SUPPLIER_NAME = m.get("SUP_NAME").toString();
					mapDate.put("SUPP_ID", m.get("SUP_ID").toString());
					mapDate.put("SUPP_NAME", m.get("SUP_NAME").toString());
					m.put("MONEYCURR", m.get("PAID_MONEY"));
					m.put("D_STATUS", 2);

					// 通过项目CODE查询出发票开具对象
					if (m != null && m.get("PRO_CODE") != null
							&& !m.get("PRO_CODE").equals("")) {
						Map InvoiceMap = Dao.selectOne(
								"rentWrite.queryFundInvoice", m);
						if (InvoiceMap != null) {
							m.put("INVOICE_TARGET_TYPE", InvoiceMap
									.get("INVOICE_TARGET_TYPE"));
							m.put("INVOICE_TARGET_ID", InvoiceMap
									.get("INVOICE_TARGET_ID"));
						}
					}

					Dao.insert("rentWrite.createDetailByBank", m);
					// 如果违约金提交将逾期表中的该条违约金置为不能再申请
					m.put("DUE_STATUS", 1);
					m.put("OVERDUE_STATUS", 1);
					Dao.update("rentWrite.updateOverDunStaute", m);
					mapDate.put("FI_PRO_NAME", "违约金");
					
					
					mapDate.put("USER_ID", Security.getUser().getId());
					// 组织机构应该取缓存 后面在改
					Map ORG_MAP = Dao.selectOne("payment.orgListByUserId", mapDate);
					if (ORG_MAP != null) {
						mapDate.put("ORG_ID", ORG_MAP.get("ORG_ID"));
					}
					
					//判断是租金科提交还是供应商自己提交
					String APP_CREATE_TYPE=mapDate.get("APP_CREATE_TYPE").toString();
					String APP_CREATE="";
					if(APP_CREATE_TYPE.equals("1")){//供应商
						APP_CREATE=SUPPLIER_NAME;
					}
					else{//租金科
						APP_CREATE="租金科";
					}
					mapDate.put("BANK_NAME", m.get("BANK_CUSTNAME"));
					mapDate.put("BANK_ACCOUNT", m.get("BANK_ACCOUNT"));
					mapDate.put("APP_CREATE", APP_CREATE);
					mapDate.put("SUPPLIER_NAME", SUPPLIER_NAME);
					mapDate.put("FI_PROJECT_NUM", 1);
					mapDate.put("FI_PAY_MONEY", m.get("PAID_MONEY"));
					// 插入付款细单
					Dao.insert("rentWrite.createFundHead_Not", mapDate);
				}
				
				
			}
			
			
		}
		
		
	}
	
	public List query_Not_list(List list){
		List listDate=new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			listDate.add(Dao.selectOne("rentWrite.queryNotOne",map));
		}
		return listDate;
	}
	
	// 修改数据库导出状态和制造导出数据
	public List queryNotOne_Upload(List list,String FI_REALITY_BANK) {
		List listDate = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			map.put("UPLOADTYPE", "1");
			map.put("ROW_NUM", i+1);
			map.put("MONEY", map.get("BEGINNING_MONEY"));
			map.put("BANK_NAME", FI_REALITY_BANK);
			map.put("BANK_ACCOUNT", map.get("BANK_ACCOUNT"));
			map.put("CUST_NAME", map.get("ACCOUNT_NAME"));
			map.put("BANK_CUSTNAME", map.get("ACCOUNT_NAME"));
			map.put("PROJ_ID", map.get("HEAD_ID"));
			map.put("USER_CODE", Security.getUser().getCode());
			map.put("USER_NAME", Security.getUser().getName());
			map.put("PRO_CODE",  map.get("PRO_CODE"));
			Dao.update("rentWrite.updateFund_head", map);
			listDate.add(map);
		}
		return listDate;
	}
	
	public List query_All_list(Map map){
		return Dao.selectList("rentWrite.query_All_list",map);
	}
	
	public void rollBackNotAll(Map<String, Object> m) {

		m.put("tags3", "客户类型");
		List list=Dao.selectList("rentWrite.rollBackNotAll", m);
		String IDS="";
		int count=0;
		for(int i=0;i<list.size();i++){
			if(count==0){
				IDS=((Map)list.get(i)).get("ID").toString();
			}
			else{
				IDS=IDS+","+((Map)list.get(i)).get("ID").toString();
			}
			count++;
			if (count == 300) {
				Map mapDate=new HashMap();
				mapDate.put("IDS", IDS);
				Dao.update("rentWrite.updateBigennIDByHandIdNot", mapDate);
				Dao.commit();
				Dao.close();
				count = 0;
				IDS="";
			}
		}
		if(count>0){
			Map mapDate=new HashMap();
			mapDate.put("IDS", IDS);
			Dao.update("rentWrite.updateBigennIDByHandIdNot", mapDate);
		}
		
	}
	
	public void rollBackNot(Map<String, Object> m) {
		Dao.update("rentWrite.updateBigennIDByHandIdNot", m);
	}
	
	public void backNotOP(List list, Map map) {
		String seqFileId=Dao.getSequence("SEQ_FI_FUND_FILE");
		map.put("ID", seqFileId);
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("TYPE", 2);
		map.put("FILE_STATUS", 1);
		Dao.insert("rentWrite.insertFundFile",map);
		final List listOPBack=list;
		final Map mapOPBack=map;
		mapOPBack.put("FI_CHECK_NAME", Security.getUser().getName());
		mapOPBack.put("FI_REALITY_BANK", map.get("FI_REALITY_BANK"));
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				try {
					new rentWriteService().backFileNotOp(listOPBack, mapOPBack);
					Dao.commit();
				} catch (Exception e) {
					Dao.rollback();
					e.printStackTrace();
				} finally {
					Dao.close();
				}

				System.out.println("耗时："
						+ ((System.currentTimeMillis() - start) / 1000)+"秒");
			}
		}).start();
	}
	
	// 回执文件的处理
	public void backFileNotOp(List list, Map mapTime) {
		String time = mapTime.get("fromDate").toString();
		Map mapUp=new HashMap();
		int SUCCESS_NUM=0;
		int ERROR_BANK_NUM=0;
		int ERROR_NUM=0;
		// 核销成功的数据：1.修改头表的状态未已核销 2.修改fi_r_begging表的实收金额，如果实收金额等于应付金额则修改状态为核销完成
		// 3.修改子表的实际收到金额 4.插入数据到FI_FUND_ACCOUNT表
		// 核销失败的数据：1.修改头表的状态为核销失败 2.修改fi_r_begging表的失败次数和失败原因 ，并将状态修改为未核销状态
		int count=0;
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			//通过回执数据查询核销单ID
			String FUND_IDNEW=(map.get("deducted_id")!=null && !map.get("deducted_id").equals(""))?map.get("deducted_id").toString():"";
			try{
				Map map1=Dao.selectOne("rentWrite.queryFund_HeadbyDateID",FUND_IDNEW);
					if(map1!=null){
						String paylist_codeUp=map1.get("D_PAY_CODE").toString();
						String beginning_numUp=map1.get("PERIOD").toString();
						mapUp.put("paylist_codeUp",paylist_codeUp);
						mapUp.put("beginning_numUp",beginning_numUp);
						mapUp.put("PAYLIST_CODE",paylist_codeUp);
						mapUp.put("BEGINNING_NUM",beginning_numUp);
						mapUp.put("item_typeUp",1);
						
						map.put("deducted_id",map1.get("D_FUND_ID"));
						if (map != null && map.get("bank_flag") != null && map.get("bank_flag").equals("1"))// 成功
						{
							map.put("TIME", time);
							map.put("FI_REALITY_BANK", mapTime.get("FI_REALITY_BANK"));
							map.put("FI_CHECK_NAME", mapTime.get("FI_CHECK_NAME"));
							Dao.update("rentWrite.updateFI_STATUSByHandId2", map);// 1
							List listBeg = Dao.selectList("rentWrite.getBeginListByHandIdNot",map);
							for (int j = 0; j < listBeg.size(); j++)// 2
							{
								Map mapBeg = (Map) listBeg.get(j);
								if (mapBeg != null) {
									double D_PAY_MONEY = (mapBeg.get("D_PAY_MONEY") != null && !mapBeg
											.get("D_PAY_MONEY").equals("")) ? Double
											.parseDouble(mapBeg.get("D_PAY_MONEY")
													.toString()) : 0d;// 本次回执金额
									double BEGINNING_PAID = (mapBeg.get("BEGINNING_PAID") != null && !mapBeg
											.get("BEGINNING_PAID").equals("")) ? Double
											.parseDouble(mapBeg.get("BEGINNING_PAID")
													.toString()) : 0d;
									;// 已经实收金额
									double BEGINNING_MONEY = (mapBeg.get("BEGINNING_MONEY") != null && !mapBeg
											.get("BEGINNING_MONEY").equals("")) ? Double
											.parseDouble(mapBeg.get("BEGINNING_MONEY")
													.toString()) : 0d;
									;// 总应收金额
									if ((D_PAY_MONEY + BEGINNING_PAID) >= BEGINNING_MONEY) {
										mapBeg.put("BEGINNING_FLAG", "1");
										mapBeg.put("REALITY_TIME", mapTime.get("fromDate"));
									}
									Dao.update("rentWrite.updateMoneyBeginNewGuize", mapBeg);
									Dao.update("rentWrite.doUpdateBeginningJoinHH", mapBeg);
									mapBeg.put("TIME", time);
									Dao.update("rentWrite.updateRENT_PAIDDueNot",mapBeg);
									
								}
							}
//							Dao.update("rentWrite.updateMoneyDatil", map);// 3
							

							// 先删后增
							Dao.delete("rentWrite.delAccByDeductedId", map);
							List listDatil = Dao
									.selectList("rentWrite.getAccountById", map);
							for (int h = 0; h < listDatil.size(); h++) {
								Map datMap = (Map) listDatil.get(h);
								Dao.insert("rentWrite.insertAccount", datMap);// 4
							}
							map.put("REALITY_TIME", mapTime.get("fromDate"));
//							this.insertBeginByDun(map.get("deducted_id").toString(),mapTime.get("fromDate"));
							mapUp.put("TIME",  mapTime.get("fromDate"));
							this.dunUpdateStatusNot(mapUp);
							Dao.delete("rentWrite.deleteJoinDate",mapUp);
							Dao.insert("rentWrite.insertJoinDate",mapUp);
							SUCCESS_NUM++;
						} else// 失败
						{
							Dao.update("rentWrite.updateStatusFalseByHandId", map);// 2
							map.put("D_FUND_ID", map.get("deducted_id"));
							// 重置状态
							Dao.update("rentWrite.updateBigennIDByHandId1", map);
							//重置临时表状态
							Dao.update("rentWrite.updateJoinZURollMerge1",map);
							Dao.update("rentWrite.updateJoinDueRollMerge1",map);
							//重置违约金表状态
							Dao.update("rentWrite.updateDueRollMerge1",map);
							// 删除子表
							Dao.delete("rentWrite.deleteDetailByHandId1", map);
							// 删除主表
							Dao.update("rentWrite.deleteHandByHandId1", map);
							ERROR_BANK_NUM++;
						}
					}
					else{
						ERROR_NUM++;
						Map mapERROR=new HashMap();
						mapERROR.put("FILE_ID", mapTime.get("ID"));
						mapERROR.put("ERROR_CODE", FUND_IDNEW);
						mapERROR.put("REMARK", "Oh My God,找不到核销单了！！！");
						Dao.update("rentWrite.errFiInsert", mapERROR);
					}
					
			}
			catch(Exception e)
			{
				ERROR_NUM++;
				Map mapERROR=new HashMap();
				mapERROR.put("FILE_ID", mapTime.get("ID"));
				mapERROR.put("ERROR_CODE", FUND_IDNEW);
				mapERROR.put("REMARK", "报错了，自己找呗，友情提示：可能数据异常额！嘻嘻！！！");
				Dao.insert("rentWrite.errFiInsert", mapERROR);
			}
			
			count++;
			if (count == 500) {
				Dao.commit();
				Dao.close();
				count = 0;
			}
			
		}
		mapTime.put("SUCCESS_NUM", SUCCESS_NUM);
		mapTime.put("ERROR_BANK_NUM", ERROR_BANK_NUM);
		mapTime.put("ERROR_NUM", ERROR_NUM);
		mapTime.put("FUND_DATE", time);
		Dao.update("rentWrite.updateFundFileByID",mapTime);
	}
	
	// 不足额时候将核销日期以后的数据都删除掉（没有完全核销的时候），当完全核销的时候将后面的数据都修改成核销日期当天的数据和核销状态修改成可核销
	public void dunUpdateStatusNot(Map map) {
		//判断有核销完成没有
		
		Map payListMap = Dao.selectOne("rentWrite.queryDunMoneyStatus", map);
		if (payListMap != null && payListMap.get("SHENYUMONEY") != null && !payListMap.get("SHENYUMONEY").equals("")) {
			double SHENYUMONEY = Double.parseDouble(payListMap.get("SHENYUMONEY").toString());
			payListMap.put("TIME", map.get("TIME"));
			if (SHENYUMONEY <= 0.0001)// 标示这一期都核销完了，修改状态
			{
				// 1，修改实际到账日期以后的数据状态为可核销状态
				Dao.update("rentWrite.updateDetailRe_Status", payListMap);
				Dao.update("rentWrite.updateDunStatusNew1", payListMap);
			}
			else{//这一期没有核销完成，将有逾期数据的数据更新
				//删除核销日期之后的方法 重新刷数据
				Dao.delete("rentWrite.deleteDunDate",payListMap);
				//重新刷数据
				Map mm=new HashMap();
				mm.put("PAY_CODE", payListMap.get("PAYLIST_CODE"));
				Dao.update("fi.overdue.upDueOne",mm);
			}
		}
	}
}

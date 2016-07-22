package com.pqsoft.rentWrite.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.filter.ResMime;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.PageUtil;

public class rentWriteVinualService {
	
	private final String xmlPath = "rentWriteVinual.";
	
	public Map querySupp_Bank(Map mapDate) {
		return Dao.selectOne(xmlPath+"querySupp_Bank", mapDate);
	}
	
	public Page query_Bank_C_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"query_Bank_Vinual_CU",xmlPath+"query_Bank_Vinual_CU_count");

	}
	
	public Page toAjaxData(Map<String, Object> m) {
		m.put("tags1", "核销状态");
		if(!Security.getUser().getId().equals("1")){
			String ORG_LIST = BaseUtil.getSupOrgChildren();
			m.put("ORG_LIST", ORG_LIST);
		}
		return PageUtil.getPage(m, xmlPath+"query_Bank_Vinual_S",xmlPath+"query_Bank_Vinual_S_count");
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
					Dao.update(xmlPath+"updateBeginState", HEAD_ID);

					// 通过付款单号查询明细项有违约金的吗。有的话将其置为可以核销状态
					List listDunDate = Dao.selectList(xmlPath+"queryDunDateAll", HEAD_ID);
					for (int num = 0; num < listDunDate.size(); num++) {
						Map mapDunDate = (Map) listDunDate.get(num);
						if (mapDunDate != null && mapDunDate.get("ITEM_FLAG") != null && !mapDunDate.get("ITEM_FLAG").equals("")) {
							Dao.update(xmlPath+"updateJoinDateVinualStauts1",mapDunDate);
							String ITEM_FLAG = mapDunDate.get("ITEM_FLAG").toString();
							if (ITEM_FLAG.equals("5")) {
								mapDunDate.put("OVERDUE_STATUS", 0);
								mapDunDate.put("PAYLIST_CODE", mapDunDate.get("D_PAY_CODE"));
								mapDunDate.put("BEGINNING_NUM", mapDunDate.get("PERIOD"));
								Dao.update(xmlPath+"updateOverDunStaute",mapDunDate);
							}
						}
					}
				}
				Dao.update(xmlPath+"deleteCancellationDe", HEAD_ID);// 根据付款单号作废付款单
				i = Dao.update(xmlPath+"deleteCancellation", HEAD_ID);// 根据付款单号作废付款单
			}
			return i;
		}

		return i;
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
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {

			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			// 通过项目CODE查询出发票开具对象
			Map InvoiceMap = Dao.selectOne(xmlPath+"queryFundInvoice", map);
			SUP_ID = InvoiceMap.get("SUP_ID").toString();
			SUPPLIER_NAME = InvoiceMap.get("SUP_NAME").toString();
			mapDate.put("SUPP_ID", InvoiceMap.get("SUP_ID").toString());
			mapDate.put("SUPP_NAME", InvoiceMap.get("SUP_NAME").toString());
			Dao.update(xmlPath+"updateJoinDateVinualStauts", map);
			String ITEM_FLAG = "";
			if (map.get("ITEM_FLAG") != null && !map.get("ITEM_FLAG").equals(""))// 租金
			{
				ITEM_FLAG = map.get("ITEM_FLAG").toString();
			}
			
			if (ITEM_FLAG.equals("2"))// 租金
			{
				List listDetail = Dao.selectList(xmlPath+"queryDetailByPayNum", map);
				if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
				{
					for (int i = 0; i < listDetail.size(); i++) {
						Map detaMap = (Map) listDetail.get(i);
						mapDate.put("FI_PRO_NAME", "租金");
						detaMap.put("fund_head_id", fund_head_id);
						detaMap.put("CUSTNAME", InvoiceMap.get("CUSTNAME"));
						detaMap.put("CUST_ID", InvoiceMap.get("CUST_ID"));
						detaMap.put("PRO_CODE", InvoiceMap.get("PRO_CODE"));
						detaMap.put("BEGINNING_RECEIVE_DATA", InvoiceMap.get("BEGINNING_RECEIVE_DATA"));
						detaMap.put("INVOICE_TARGET_TYPE",InvoiceMap.get("INVOICE_TARGET_TYPE"));
						detaMap.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
						
						int flag = 0;
						
						detaMap.put("D_PAY_MONEYLB", detaMap.get("BEGINNING_MONEY"));
						detaMap.put("MONEYCURR", detaMap.get("BEGINNING_MONEY"));
						detaMap.put("D_STATUS", 1);
						flag = Dao.insert(xmlPath+"createDetailByBank",detaMap);
						
						if (flag > 0) {
							// 反更状态位（从未核销更新为申请中）
							Map mapUpdate = new HashMap();
							mapUpdate.put("BEGINNING_ID", detaMap.get("BEGINNING_ID"));
							mapUpdate.put("BEGINNING_STATUS", 1);
							Dao.update(xmlPath+"updateBeggingStatusByID",mapUpdate);
						}
					}
					
				}
			}
			else{// 违约金
				Map dueMap=new HashMap();
				mapDate.put("FI_PRO_NAME", "违约金");
				dueMap.put("fund_head_id", fund_head_id);
				dueMap.put("CUSTNAME", InvoiceMap.get("CUSTNAME"));
				dueMap.put("CUST_ID", InvoiceMap.get("CUST_ID"));
				dueMap.put("PRO_CODE", InvoiceMap.get("PRO_CODE"));
				dueMap.put("PAYLIST_CODE", map.get("PAYLIST_CODE"));
				dueMap.put("PRO_CODE", InvoiceMap.get("PRO_CODE"));
				dueMap.put("BEGINNING_NUM", map.get("BEGINNING_NUM"));
				dueMap.put("BEGINNING_RECEIVE_DATA", InvoiceMap.get("BEGINNING_RECEIVE_DATA"));
				dueMap.put("INVOICE_TARGET_TYPE",InvoiceMap.get("INVOICE_TARGET_TYPE"));
				dueMap.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
				
				dueMap.put("D_PAY_MONEYLB", map.get("PAID_MONEY"));
				dueMap.put("MONEYCURR", map.get("PAID_MONEY"));
				dueMap.put("D_STATUS", 2);
				dueMap.put("BEGINNING_NAME", "违约金");
				Dao.insert(xmlPath+"createDetailByBank", dueMap);
				
				// 如果违约金提交将逾期表中的该条违约金置为申请中
				dueMap.put("OVERDUE_STATUS", 1);
				Dao.update(xmlPath+"updateOverDunStaute", dueMap);
			}
		}
		String FI_FAG = "16";
		if (mapDate != null && mapDate.get("FI_FAG") != null && !mapDate.get("FI_FAG").equals("")) {
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
		mapDate.put("SUP_ID", SUP_ID);

		if (FI_FAG.equals("16")) {
			this.saveSupp_bank(mapDate);
			// 插入付款细单
			Dao.insert(xmlPath+"createFundHead_Not", mapDate);
		} else {
			// 插入付款细单
			Dao.insert(xmlPath+"createFundHead_Not1", mapDate);
		}
	}
	
	public void saveSupp_bank(Map map) {
		Map mapUser = new HashMap();
		if (map != null) {

			Map mapDate = Dao.selectOne(xmlPath+"querySupp_Bank", map);
			if (mapDate != null) {
				Dao.update(xmlPath+"updateSupp_Bank", map);
			} else {
				Dao.insert(xmlPath+"insertSupp_Bank", map);
			}
		}

	}
	
	@SuppressWarnings("unchecked")
	public Page toMgAppAlready(Map<String, Object> map) {
		return PageUtil.getPage(map, xmlPath+"toMgAppAlready",xmlPath+"toMgAppAlreadyCount");
	}
	
	public Object queryHeXiaoPage(Map<String, Object> map) {
		return Dao.selectOne(xmlPath+"queryHeXiaoPage", map);
	}

	// 通过fundId查询供应商List
	public List querySuppByFundID(Map map) {
		return Dao.selectList(xmlPath+"querySuppByFundID", map);
	}
	
	// 求上次保存金额
	public double queryPoolMoneyDe(Map map) {
		Map mapDate = Dao.selectOne(xmlPath+"querySvaePoolMoney", map);
		if (mapDate != null) {
			return (mapDate != null && mapDate.get("CANUSE_MONEY") != null && !mapDate
					.get("CANUSE_MONEY").equals("")) ? Double
					.parseDouble(mapDate.get("CANUSE_MONEY").toString()) : 0d;
		} else {
			return 0d;
		}
	}
	
	// 求租金池剩余金额
	public double queryPoolMoney(Map map) {
		Map mapDate = Dao.selectOne(xmlPath+"queryPoolTypeOwnerMoney", map);
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
		return Dao.selectInt(xmlPath+"queryPoolNumber", map);
	}
	
	public int doUpdateFHead(Map<String, Object> map) {
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		return Dao.update(xmlPath+"toUpdateFHead", baseData);
	}
	
	// 明细表
	public int doInsertAccount1(Map<String, Object> map) {
		Object data_ = map.get("data");

		Map<String, Object> data = JSONObject.fromObject(data_);
		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		String FUND_ID = baseData.get("FUND_ID").toString();
		// 金额分拆
		String MONEYORDERSAVE = (baseData.get("MONEYORDERSAVE") != null && !baseData.get("MONEYORDERSAVE").equals("")) ? baseData.get("MONEYORDERSAVE").toString() : "";
		if (MONEYORDERSAVE.length() > 0) {
			this.moneyFenChai(MONEYORDERSAVE, FUND_ID);
		}

		// 判断核销实时表中是否已经有实收明细， 如果有则先删除。否则直接添加
		int i = Dao.selectInt(xmlPath+"toGetAccountCount", baseData);

		if (i > 0) {// 当核资金/租金扣划到账明细中存在数据，先删除。

			// 删除之前看有没有和租金池对应的数据，有的话先解冻
			Map poolMap = Dao.selectOne(xmlPath+"queryAcountPollById",baseData);
			if (poolMap != null && poolMap.get("POOLIDS") != null
					&& !poolMap.get("POOLIDS").equals("")) {
				Dao.update(xmlPath+"updatePOOLStatus", poolMap);
			}

			i = Dao.delete(xmlPath+"delAccountByHeadId", baseData);// 但用户重复点击“到账日期”，并修改对应数据后点击提交是先删除已存在的资金扣划明细
		}

		// 本次实付
		double FI_REALITY_ACCOUNT = (baseData.get("FI_REALITY_ACCOUNT") != null && !baseData.get("FI_REALITY_ACCOUNT").equals("")) ? Double.parseDouble(baseData.get("FI_REALITY_ACCOUNT").toString()): 0d;
		// 本次应付
		double FI_REALITY_MONEY = (baseData.get("FI_REALITY_MONEY") != null && !baseData.get("FI_REALITY_MONEY").equals("")) ? Double.parseDouble(baseData.get("FI_REALITY_MONEY").toString()) : 0d;

		String FI_TAGE_ID = (baseData.get("FI_TAGE_ID") != null && !baseData.get("FI_TAGE_ID").equals("")) ? baseData.get("FI_TAGE_ID").toString() : null;

		// 来款单子表
		List listDe = Dao.selectList(xmlPath+"queryDetailByFundId1", baseData);
		if (FI_REALITY_ACCOUNT > FI_REALITY_MONEY)// 本次来款余额
		{
			for (int d = 0; d < listDe.size(); d++) {
				Map mapD = (Map) listDe.get(d);
				Dao.insert(xmlPath+"doInsertAccountByDetail", mapD);
			}
		} else if (FI_REALITY_ACCOUNT == FI_REALITY_MONEY)// 正好核销
		{
			for (int d = 0; d < listDe.size(); d++) {
				Map mapD = (Map) listDe.get(d);
				Dao.insert(xmlPath+"doInsertAccountByDetail", mapD);
			}
		}
		return 1;
	}
	
	// 金额拆分方法
	public void moneyFenChai(String MONEYORDERSAVE, String Fund_id) {
		Map mapDate = new HashMap();
		mapDate.put("FUND_ID", Fund_id);
		// 先删后增
		Dao.delete(xmlPath+"deleteFundSplit", mapDate);
		String[] a = MONEYORDERSAVE.split(",");
		for (int i = 0; i < a.length; i++) {
			double money = (a[i] != null && !a[i].equals("")) ? Double
					.parseDouble(a[i]) : 0d;
			if (money > 0) {
				Map map = new HashMap();
				map.put("FUND_MONEY", money);
				map.put("FUND_ID", Fund_id);
				Dao.insert(xmlPath+"insertFundSplit", map);
			}

		}
	}
	
	// 明细表
	public int doInsertAccount(Map<String, Object> map) {
		boolean flag = false;
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));

		String FUND_ID = baseData.get("FUND_ID").toString();
		// 金额分拆
		String MONEYORDERSAVE = (baseData.get("MONEYORDERSAVE") != null && !baseData.get("MONEYORDERSAVE").equals("")) ? baseData.get("MONEYORDERSAVE").toString() : "";
		if (MONEYORDERSAVE.length() > 0) {
			this.moneyFenChai(MONEYORDERSAVE, FUND_ID);
		}

		// 判断核销实时表中是否已经有实收明细， 如果有则先删除。否则直接添加
		int i = Dao.selectInt(xmlPath+"toGetAccountCount", baseData);

		if (i > 0) {// 当核资金/租金扣划到账明细中存在数据，先删除。

			// 删除之前看有没有和租金池对应的数据，有的话先解冻
			Map poolMap = Dao.selectOne(xmlPath+"queryAcountPollById",baseData);
			if (poolMap != null && poolMap.get("POOLIDS") != null && !poolMap.get("POOLIDS").equals("")) {
				Dao.update(xmlPath+"updatePOOLStatus", poolMap);
			}
			i = Dao.delete(xmlPath+"delAccountByHeadId", baseData);// 但用户重复点击“到账日期”，并修改对应数据后点击提交是先删除已存在的资金扣划明细
		}

		List finnList = new ArrayList();
		Map finnMap = new HashMap();

		// 如果有余额挂账，向池子插入一条数据。为接口提供数据
		Map mapFund1 = Dao.selectOne(xmlPath+"queryFundHeadById1", baseData);
		finnMap.put("Money", mapFund1.get("FI_REALITY_ACCOUNT"));
		finnMap.put("TYPE", "贷方");
		finnMap.put("REMARK", "");
		finnList.add(finnMap);

		// 本次实付
		double FI_REALITY_ACCOUNT = (baseData.get("FI_REALITY_ACCOUNT") != null && !baseData.get("FI_REALITY_ACCOUNT").equals("")) ? Double.parseDouble(baseData.get("FI_REALITY_ACCOUNT").toString()): 0d;
		// 本次应付
		double FI_REALITY_MONEY = (baseData.get("FI_REALITY_MONEY") != null && !baseData.get("FI_REALITY_MONEY").equals("")) ? Double.parseDouble(baseData.get("FI_REALITY_MONEY").toString()) : 0d;

		String FI_TAGE_ID = (baseData.get("FI_TAGE_ID") != null && !baseData.get("FI_TAGE_ID").equals("")) ? baseData.get("FI_TAGE_ID").toString() : null;

		// 来款单子表
		List listDe = Dao.selectList(xmlPath+"queryDetailByFundId1", baseData);

		double FI_REALITY_ACCOUNT_YU = FI_REALITY_ACCOUNT;// 本次实付递减
		for (int d = 0; d < listDe.size(); d++) {
			if (flag) {
				break;
			}
			Map mapD = (Map) listDe.get(d);
			double D_RECEIVE_MONEY = (mapD.get("D_RECEIVE_MONEY") != null && !mapD.get("D_RECEIVE_MONEY").equals("")) ? Double.parseDouble(mapD.get("D_RECEIVE_MONEY").toString()) : 0d;

			if (FI_REALITY_ACCOUNT_YU >= D_RECEIVE_MONEY) {
				Dao.insert(xmlPath+"doInsertAccountByDetail", mapD);
				FI_REALITY_ACCOUNT_YU = FI_REALITY_ACCOUNT_YU - D_RECEIVE_MONEY;
			} else {
				if (FI_REALITY_ACCOUNT_YU > 0) {
					mapD.put("D_RECEIVE_MONEY", FI_REALITY_ACCOUNT_YU);
					Dao.insert(xmlPath+"doInsertAccountByDetail", mapD);
					FI_REALITY_ACCOUNT_YU = 0d;
				}

				if (FI_REALITY_ACCOUNT_YU == 0) {
					// 租金池开始冲抵
					// 循环页面租金池找到租金池可用金额排序记录ID将其至为冻结
					JSONArray listLi = JSONArray.fromObject(data.get("getPoolData"));
					for (int li = 0; li < listLi.size(); li++) {
						Map mapLi = (Map) listLi.get(li);
						if (mapLi != null && baseData.get("FI_TAGE_ID") != null && !baseData.get("FI_TAGE_ID").equals("")) {
							if (mapLi.get("POOL_TYPE") != null) {
								double dichong_money = (mapLi.get("dichong_money") != null && !mapLi.get("dichong_money").equals("")) ? Double.parseDouble(mapLi.get("dichong_money").toString()): 0d;
								if(dichong_money>0){
									// 租金池
									finnMap.put("Money", dichong_money);
									finnMap.put("TYPE", "借方");
									finnMap.put("REMARK", "");
									finnList.add(finnMap);
									Map mappool = new HashMap();
									mappool.put("OWNER_ID", baseData.get("FI_TAGE_ID"));
									mappool.put("TYPE", mapLi.get("POOL_TYPE"));
									List listpool = Dao.selectList(xmlPath+"queryPoolTypeOwner",mappool);
									for (int aa = 0; aa < listpool.size(); aa++) {
										Map mapA = (Map) listpool.get(aa);
										if (mapA.get("CANUSE_MONEY") != null && !mapA.get("CANUSE_MONEY").equals("")) {
											double CANUSE_MONEY = Double.parseDouble(mapA.get("CANUSE_MONEY").toString());
											if (dichong_money > 0 && dichong_money >= CANUSE_MONEY) {
												// 冻结池子金额
												// 插入数据到细表中
												mapD.put("FA_POOL_ID", mapA.get("POOL_ID"));
												mapD.put("D_RECEIVE_MONEY",CANUSE_MONEY);
												mapD.put("FA_CAN_USE_MONEY",CANUSE_MONEY);
												Dao.insert(xmlPath+"doInsertAccountByDetail",mapD);
												mapD.put("CANUSE_MONEY", 0);
												Dao.update(xmlPath+"updatePoolStateByfundId",mapD);

												dichong_money = dichong_money - CANUSE_MONEY;
											} else if(dichong_money > 0 && dichong_money < CANUSE_MONEY) {
												// 冻结池子金额
												// 插入数据到细表中
												mapD.put("FA_POOL_ID", mapA .get("POOL_ID"));
												mapD.put("D_RECEIVE_MONEY", dichong_money);
												mapD.put("FA_CAN_USE_MONEY", CANUSE_MONEY);
												Dao.insert(xmlPath+"doInsertAccountByDetail",mapD);
												mapD.put("CANUSE_MONEY",CANUSE_MONEY - dichong_money);
												Dao.update(xmlPath+"updatePoolStateByfundId",mapD);
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
		i = Dao.update(xmlPath+"doUpdateHeadStatus", baseData);
		// 查询核销时间，操作罚息
		Map mapTime = Dao.selectOne(xmlPath+"queryFundTime", baseData);
		String FI_ACCOUNT_DATE = (mapTime != null && mapTime.get("FI_ACCOUNT_DATE") != null && !mapTime.get("FI_ACCOUNT_DATE").equals("")) ? mapTime.get("FI_ACCOUNT_DATE").toString() : "";
		if (i > 0) {// 若跟新成功， 回更应收起初表的实收金额和核销状态;
			// 根据申请单id查看应收金额和实收总金额
			List<Map<String, Object>> detialL = Dao.selectList(xmlPath+"queryDetailByFundId", baseData);
			// 当不为空时跟新应收明细
			if (detialL != null) {
				for (int k = 0; k < detialL.size(); k++) {
					Map<String, Object> realMoney = detialL.get(k);
						String D_STATUS=(realMoney!=null && realMoney.get("D_STATUS")!=null && !realMoney.get("D_STATUS").equals(""))?realMoney.get("D_STATUS").toString():"";
						if(D_STATUS.length()>0 && D_STATUS.equals("1")){//租金
							Dao.update(xmlPath+"updateBegin", realMoney);
							realMoney.put("TIME", FI_ACCOUNT_DATE);
							Dao.update(xmlPath+"updateBeginState1", realMoney);
							
						}
						else if(D_STATUS.length()>0 && D_STATUS.equals("2")){//违约金
							Map mapDunDate = new HashMap();
							mapDunDate.put("PAYLIST_CODE", realMoney.get("D_PAY_CODE"));
							mapDunDate.put("BEGINNING_NUM", realMoney.get("PERIOD"));
							mapDunDate.put("TIME", FI_ACCOUNT_DATE);
							mapDunDate.put("D_RECEIVE_MONEY", realMoney.get("D_RECEIVE_MONEY"));
							Dao.update(xmlPath+"updateDunVinualHeXiao", mapDunDate);
						}
				}
				
				//查询该核销单下面所有租金的违约金处理
				this.dunOPP(baseData.get("FUND_ID").toString(),FI_ACCOUNT_DATE);
				this.queryPaylist_codeNum(baseData.get("FUND_ID").toString());
			}
		}
		return 1;
	}
	
	public void dunOPP(String FUND_ID,String FI_ACCOUNT_DATE){
		Map map=new HashMap();
		map.put("FUND_ID", FUND_ID);
		map.put("TIME", FI_ACCOUNT_DATE);
		//查询虚拟核销时间>应收时间(应该产生违约金)
		List list=Dao.selectList(xmlPath+"queryDunDate",map);
		//产生的违约金将其定格，下次就不在算了
		for(int i=0;i<list.size();i++){
			//如果虚拟核销产生违约金将VINUAL_DUN_FLAG=1
			//更改虚拟核销应收金额
			Map mapDate=(Map)list.get(i);
			mapDate.put("TIME", FI_ACCOUNT_DATE);
//			Dao.update(xmlPath+"updateDunVinual",mapDate);
			Dao.update(xmlPath+"updateDunVinualNEW",mapDate);
		}
		
		//查询虚拟核销时间<=应收时间(不会产生违约金)
		List listNo=Dao.selectList(xmlPath+"queryDunDateNo",map);
		for(int i=0;i<listNo.size();i++){
			//如果虚拟核销没有产生违约金将VINUAL_DUN_FLAG=2
			//更改虚拟核销应收金额
			Map mapDate=(Map)listNo.get(i);
//			Dao.update(xmlPath+"updateDunVinualNo",mapDate);
			Dao.update(xmlPath+"updateDunVinualNoNew",mapDate);
		}
		
	}
	
	public int doCheckedFund(Map<String, Object> map) {
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));

		int i = 0;
		if (detailData != null) {
			for (int j = 0; j < detailData.size(); j++) {
				Map<String, Object> de = detailData.get(j);
				de.put("USERCODE", map.get("USERCODE"));
				de.put("USERNAME", map.get("USERNAME"));
				de.put("FUND_ID", de.get("HEAD_ID"));// f付款单号
				// 更新申请表状态，核销时间， 核销银行
				i = Dao.update(xmlPath+"doUpdateHeadStatus", de);

				// 查询核销时间，操作罚息
				Map mapTime = Dao.selectOne(xmlPath+"queryFundTime", de);
				String FI_ACCOUNT_DATE = (mapTime != null&& mapTime.get("FI_ACCOUNT_DATE") != null && !mapTime.get("FI_ACCOUNT_DATE").equals("")) ? mapTime.get("FI_ACCOUNT_DATE").toString() : "";
				
				if (i > 0) {// 若跟新成功， 回更应收起初表的实收金额和核销状态;

					// 根据申请单id查看应收金额和实收总金额
					List<Map<String, Object>> detialL = Dao.selectList(xmlPath+"queryDetailByFundId", de);
					// 当不为空时跟新应收明细
					if (detialL != null) {
						for (int k = 0; k < detialL.size(); k++) {
							Map<String, Object> realMoney = detialL.get(k);
							
							String D_STATUS=(realMoney!=null && realMoney.get("D_STATUS")!=null && !realMoney.get("D_STATUS").equals(""))?realMoney.get("D_STATUS").toString():"";
							if(D_STATUS.length()>0 && D_STATUS.equals("1")){//租金
								Dao.update(xmlPath+"updateBegin", realMoney);
								realMoney.put("TIME", FI_ACCOUNT_DATE);
								Dao.update(xmlPath+"updateBeginState1", realMoney);
								
							}
							else if(D_STATUS.length()>0 && D_STATUS.equals("2")){//违约金
								Map mapDunDate = new HashMap();
								mapDunDate.put("PAYLIST_CODE", realMoney.get("D_PAY_CODE"));
								mapDunDate.put("BEGINNING_NUM", realMoney.get("PERIOD"));
								mapDunDate.put("TIME", FI_ACCOUNT_DATE);
								mapDunDate.put("D_RECEIVE_MONEY", realMoney.get("D_RECEIVE_MONEY"));
//								Dao.update(xmlPath+"updateDunVinualHeXiao", mapDunDate);
								Dao.update(xmlPath+"updateDunVinualHeXiaoNew", mapDunDate);
							}
						}
					}

					List finnList = new ArrayList();
					Map finnMap = new HashMap();

					// 如果有余额挂账，向池子插入一条数据。为接口提供数据
					Map mapFund1 = Dao.selectOne(xmlPath+"queryFundHeadById1", de);
					finnMap.put("Money", mapFund1.get("FI_REALITY_ACCOUNT"));
					finnMap.put("TYPE", "贷方");
					finnMap.put("REMARK", "");
					finnList.add(finnMap);

					Map mapFund = Dao.selectOne(xmlPath+"queryFundHeadById",de);
					if (mapFund != null) {
						String FI_FLAG = mapFund.get("FI_FLAG").toString();
						if (FI_FLAG.equals("17"))// 挂账供应商电汇资金池
						{
							Map mapPool = new HashMap();
							mapPool.put("SOURCE_ID", mapFund.get("ID"));
							mapPool.put("TYPE", 2);
							mapPool.put("STATUS", 1);
							mapPool.put("BASE_MONEY", mapFund.get("FI_TAGE_MONEY"));
							mapPool.put("CANUSE_MONEY", mapFund.get("FI_TAGE_MONEY"));
							mapPool.put("OWNER_ID", mapFund.get("FI_TAGE_ID"));
							Dao.insert(xmlPath+"insertPoolDate", mapPool);
						}
						finnMap.put("Money", mapFund.get("FI_TAGE_MONEY"));
						finnMap.put("TYPE", "借方");
						finnMap.put("REMARK", "");
						finnList.add(finnMap);
					}
				}
				//查询该核销单下面所有租金的违约金处理
				//虚拟核销租金核销完成后确定虚拟核销违约金定型
				this.dunOPP(de.get("HEAD_ID").toString(),FI_ACCOUNT_DATE);
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
	
	public int doReject(Map<String, Object> map) {
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));
		int i = 0;
		// 遍历参数，驳回
		if (detailData != null) {
			for (int k = 0; k < detailData.size(); k++) {
				Map<String, Object> de = detailData.get(k);
				de.put("FUND_ID", de.get("HEAD_ID"));
				i = Dao.update(xmlPath+"doReject", de);// 回更申请单明细。
				if (i > 0) {
					String FUND_ID = de.get("HEAD_ID").toString();// 付款单号
					Dao.delete(xmlPath+"doRejectAccount", FUND_ID);// 清除资金扣划到账明细。
				}
			}
			if (i > 0) {
				return i;
			}
		}
		return 0;
	}
	
	public Page toSupperFundMg(Map<String, Object> map) {
		return PageUtil.getPage(map, xmlPath+"toSupperFundMg",xmlPath+"toSupperFundMgCount");
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
			Dao.update(xmlPath+"updateFund_head", map);
			// 取供应商组织架构
			if (map != null && map.get("SUPPLIER_IDS") != null
					&& !map.get("SUPPLIER_IDS").equals("")) {
				String SUPPLIER_IDS = map.get("SUPPLIER_IDS").toString();
				Map mapSup = Dao.selectOne(xmlPath+"querySuppForCode",SUPPLIER_IDS);
				if (mapSup != null) {
					map.put("ID_CARD_NO", mapSup.get("ORAGNIZATION_CODE"));
				}

			}

			listDate.add(map);
		}
		return listDate;
	}
	
	public List querySupp_uplad_All(Map map) {
		return Dao.selectList(xmlPath+"querySupp_uplad_All", map);
	}
	
	public void destroySuppUp(Map map) {
		Dao.update(xmlPath+"destroySuppUp", map);
	}
	
	// 导出
	public Excel HEAD_Upload(Map map) {
		String type = "1";
		if (map != null && map.get("type") != null) {
			type = map.get("type").toString();
		}

		String ORG_LIST = BaseUtil.getSupOrgChildren();
		map.put("ORG_LIST", ORG_LIST);

		List<Map<String, Object>> dataList = Dao.selectList(xmlPath+"HEAD_Upload", map);
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
				Dao.update(xmlPath+"updateFI_STATUSByHandId2", map);// 1
				
				
				// 根据申请单id查看应收金额和实收总金额
				List<Map<String, Object>> detialL = Dao.selectList(xmlPath+"getBeginListByHandIdNew", map);
				// 当不为空时跟新应收明细
				if (detialL != null) {
					for (int k = 0; k < detialL.size(); k++) {
						Map<String, Object> realMoney = detialL.get(k);
						String D_STATUS=(realMoney!=null && realMoney.get("D_STATUS")!=null && !realMoney.get("D_STATUS").equals(""))?realMoney.get("D_STATUS").toString():"";
						if(D_STATUS.length()>0 && D_STATUS.equals("1")){//租金
							Dao.update(xmlPath+"updateBegin", realMoney);
							realMoney.put("TIME", time);
							Dao.update(xmlPath+"updateBeginState1", realMoney);
						}
						else if(D_STATUS.length()>0 && D_STATUS.equals("2")){//违约金
							Map mapDunDate = new HashMap();
							mapDunDate.put("PAYLIST_CODE", realMoney.get("D_PAY_CODE"));
							mapDunDate.put("BEGINNING_NUM", realMoney.get("PERIOD"));
							mapDunDate.put("TIME", time);
							mapDunDate.put("D_RECEIVE_MONEY", realMoney.get("D_RECEIVE_MONEY"));
							Dao.update(xmlPath+"updateDunVinualHeXiao", mapDunDate);
						}
					}
				}
				
				// 先删后增
				Dao.delete(xmlPath+"delAccByDeductedId", map);
				List listDatil = Dao.selectList(xmlPath+"getAccountById", map);
				for (int h = 0; h < listDatil.size(); h++) {
					Map datMap = (Map) listDatil.get(h);
					Dao.insert(xmlPath+"insertAccount", datMap);// 4

				}
				
				//查询该核销单下面所有租金的违约金处理
				this.dunOPP(map.get("deducted_id").toString(),time);
				this.queryPaylist_codeNum(map.get("deducted_id").toString().toString());
				SUCCESS_NUM++;
			} else// 失败
			{
				ERROR_BANK_NUM++;
			}
		}
		mapDate1.put("SUCCESS_NUM", SUCCESS_NUM);
		mapDate1.put("ERROR_BANK_NUM", ERROR_BANK_NUM);
		return mapDate1;
	}
	
	//当核销完成后根据核销单查询出所有支付表编号和期次
	public void queryPaylist_codeNum(String FUND_ID){
		
		List list=Dao.selectList(xmlPath+"queryPaylist_codeNum",FUND_ID);
		for(int i=0;i<list.size();i++){
			Map mapDate=(Map)list.get(i);
			if(mapDate!=null){
				Dao.delete(xmlPath+"deleteJoinDate",mapDate);
				Dao.insert(xmlPath+"insertJoinDate",mapDate);
			}
		}
	}
	
	//虚拟转实收管理页
	public Page query_Bank_A_TO_B_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"query_Bank_A_TO_B_CU",xmlPath+"query_Bank_A_TO_B_CU_count");

	}
	
	// 转实收操作
	public void fund_A_TO_B_Submit(Map mapDate) {
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			
			/**
			 * 调用转实收的存储过程
			 */
			Dao.update(xmlPath+"upRentWriteVinual",map);
			Dao.update(xmlPath+"upMoneyToJoin",map);
		}
	}
	
	//退款申请页面
	public Page query_Back_Fund_SUPP_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"query_Back_SUPP_Fund",xmlPath+"query_Back_SUPP_Fund_COUNT");

	}
	
	//退款
	public Page query_Back_Fund_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"query_Back_Fund",xmlPath+"query_Back_Fund_COUNT");

	}
	
	public void fund_Back_Submit_SUPP(Map mapDate) {
		
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			//先判断是租金还是违约金
			String ITEM_FLAG = "";
			if (map.get("ITEM_FLAG") != null && !map.get("ITEM_FLAG").equals(""))// 租金
			{
				ITEM_FLAG = map.get("ITEM_FLAG").toString();
			}
			
			if (ITEM_FLAG.equals("2"))// 租金
			{
				Dao.update(xmlPath+"updateBeginVinualType",map);
			}
			else{//违约金
				
				Dao.update(xmlPath+"updateOverDueVinualType",map);
			}
		}
	}
	
	
	public void fund_Back_BoHui(Map mapDate){
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			
			Object ITEM_FLAG = map.get("ITEM_FLAG") == null ? "" : map.get("ITEM_FLAG");
			
			if(ITEM_FLAG!=null && !ITEM_FLAG.equals("") && ITEM_FLAG.equals("2")){//租金
				Dao.update(xmlPath+"updateBeginnTypeBoHui",map);
			}
			else{//违约金
				Dao.update(xmlPath+"updateOverdueTypeBoHui",map);
			}
		}
	}
	
	public void fund_Back_ChongZ(Map mapDate){
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			
			Object ITEM_FLAG = map.get("ITEM_FLAG") == null ? "" : map.get("ITEM_FLAG");
			
			if(ITEM_FLAG!=null && !ITEM_FLAG.equals("") && ITEM_FLAG.equals("2")){//租金
				Dao.update(xmlPath+"updateBeginnTypeChongZ",map);
			}
			else{//违约金
				Dao.update(xmlPath+"updateOverdueTypeChongZ",map);
			}
		}
	}
	
	/**
	 * 
	* @param @param param   插入记录
	* @param @param refundFlag 退款标识 (1 退款, 2代收违约金退款)
	* @param @return
	* @return int
	* @throws
	* @date 2014年7月8日 下午2:31:18 
	* @author WuYanFei
	 */
	public int addRefundRecord(Map param,int refundFlag){
		JSONArray jsonArray = JSONArray.fromObject(param.get("datagridList")) ;
		int flag=0;
		for(int i=0;i<jsonArray.size();i++){
			Map<String,Object> refer = (Map<String,Object>)jsonArray.get(i);
			Object VINUAL_BACK_MONEY = refer.get("VINUAL_BACK_MONEY") ;
			Object VINUAL_MONEY = refer .get("VINUAL_MONEY") ;
			Object final_value = VINUAL_MONEY==null?VINUAL_BACK_MONEY:VINUAL_MONEY ;
			refer.put("VINUAL_MONEY",final_value) ;
			refer.put("OPERATOR_NAME", Security.getUser().getName()) ;
			refer.put("OPERATOR_CODE", Security.getUser().getCode()) ;
			refer.put("REFUND_FLAG", refundFlag) ;
			flag += Dao.insert("refundRecord.insert",refer) ;
		}
		return flag ;
	}
	
	// 插入数据库和制造退款单
	public void fund_Back_C_Submit(Map mapDate) {
		
		mapDate.put("USER_CODE", Security.getUser().getCode());
		mapDate.put("USER_NAME", Security.getUser().getName());
		
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			String SUP_ID = "";
			String SUPPLIER_NAME = "";
			
			double moneyBack=0d;
			
			String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
			mapDate.put("fund_head_id", fund_head_id);
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			
			//先判断是租金还是违约金
			String ITEM_FLAG = "";
			if (map.get("ITEM_FLAG") != null && !map.get("ITEM_FLAG").equals(""))// 租金
			{
				ITEM_FLAG = map.get("ITEM_FLAG").toString();
			}
			
			// 通过项目CODE查询出发票开具对象
			Map InvoiceMap = Dao.selectOne(xmlPath+"queryFundInvoice", map);
			
			if (ITEM_FLAG.equals("2"))// 租金
			{
				//查询必要信息
				Map m=Dao.selectOne(xmlPath+"selInfoItem2", map);
				if(m!=null){
					SUP_ID = m.get("SUP_ID").toString();
					SUPPLIER_NAME= m.get("SUPPLIERS_NAME").toString();
					moneyBack=Double.parseDouble(m.get("VINUAL_MONEY").toString());
					
					List listDetail = Dao.selectList(xmlPath+"queryDetailByPayNumItem2", map);
					if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
					{
						for (int i = 0; i < listDetail.size(); i++) {
							Map detaMap = (Map) listDetail.get(i);
							mapDate.put("FI_PRO_NAME", "租金");
							detaMap.put("fund_head_id", fund_head_id);
							detaMap.put("CUSTNAME", InvoiceMap.get("CUSTNAME"));
							detaMap.put("CUST_ID", InvoiceMap.get("CUST_ID"));
							detaMap.put("PRO_CODE", InvoiceMap.get("PRO_CODE"));
							detaMap.put("BEGINNING_RECEIVE_DATA", InvoiceMap.get("BEGINNING_RECEIVE_DATA"));
							detaMap.put("INVOICE_TARGET_TYPE",InvoiceMap.get("INVOICE_TARGET_TYPE"));
							detaMap.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
							
							int flag = 0;
							
							detaMap.put("D_PAY_MONEYLB", detaMap.get("BEGINNING_MONEY"));
							detaMap.put("MONEYCURR", detaMap.get("BEGINNING_MONEY"));
							detaMap.put("D_STATUS", 1);
							flag = Dao.insert(xmlPath+"createDetailByBank",detaMap);
							
							if (flag > 0) {
								// 反更状态位
								Map mapUpdate = new HashMap();
								mapUpdate.put("BEGINNING_ID", detaMap.get("BEGINNING_ID"));
								mapUpdate.put("BEGINNING_STATUS", 0);
								mapUpdate.put("VINUAL_FLAG", 2);
								Dao.update(xmlPath+"updateBeggingStatusByVinual",mapUpdate);
							}
							
						}
						
					}
					
				}
			}
			else{//违约金
				//查询必要信息
				Map m=Dao.selectOne(xmlPath+"selInfoItem5", map);
				if(m!=null){
					SUP_ID = m.get("SUP_ID").toString();
					SUPPLIER_NAME= m.get("SUPPLIERS_NAME").toString();
					moneyBack=Double.parseDouble(m.get("VINUAL_MONEY").toString());
					
					Map dueMap=new HashMap();
					mapDate.put("FI_PRO_NAME", "违约金");
					dueMap.put("fund_head_id", fund_head_id);
					dueMap.put("CUSTNAME", InvoiceMap.get("CUSTNAME"));
					dueMap.put("CUST_ID", InvoiceMap.get("CUST_ID"));
					dueMap.put("PRO_CODE", InvoiceMap.get("PRO_CODE"));
					dueMap.put("PAYLIST_CODE", m.get("PAYLIST_CODE"));
					dueMap.put("PRO_CODE", InvoiceMap.get("PRO_CODE"));
					dueMap.put("BEGINNING_NUM", m.get("BEGINNING_NUM"));
					dueMap.put("BEGINNING_RECEIVE_DATA", InvoiceMap.get("BEGINNING_RECEIVE_DATA"));
					dueMap.put("INVOICE_TARGET_TYPE",InvoiceMap.get("INVOICE_TARGET_TYPE"));
					dueMap.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
					
					dueMap.put("D_PAY_MONEYLB", m.get("VINUAL_MONEY"));
					dueMap.put("MONEYCURR", m.get("VINUAL_MONEY"));
					dueMap.put("D_STATUS", 2);
					dueMap.put("BEGINNING_NAME", "违约金");
					Dao.insert(xmlPath+"createDetailByBank", dueMap);
					
					
					dueMap.put("OVERDUE_STATUS", 0);
					dueMap.put("VINUAL_DUN_FLAG", 2);
					Dao.update(xmlPath+"updateOverDunStauteByVinual", dueMap);
				}
				
			}
			
			mapDate.put("FI_PAY_MONEY", moneyBack);
			mapDate.put("FI_PROJECT_NUM", 1);
			mapDate.put("SUPP_ID", SUP_ID);
			mapDate.put("SUPP_NAME", SUPPLIER_NAME);
			
			
			String FI_FAG = "18";
			if (mapDate != null && mapDate.get("FI_FAG") != null && !mapDate.get("FI_FAG").equals("")) {
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
			mapDate.put("SUP_ID", SUP_ID);
			
			// 插入付款细单
			Dao.insert(xmlPath+"createFundHeadByVinual", mapDate);
			
			if(FI_FAG.equals("19")){//退款到租金池（挂账到供应商租金池）
						Map mapPool = new HashMap();
						mapPool.put("SOURCE_ID", fund_head_id);
						mapPool.put("TYPE", 2);
						mapPool.put("STATUS", 1);
						mapPool.put("BASE_MONEY", Math.abs(moneyBack));
						mapPool.put("CANUSE_MONEY", Math.abs(moneyBack));
						mapPool.put("OWNER_ID", SUP_ID);
						mapPool.put("PROJECT_ID",InvoiceMap.get("PROJECT_ID"));
						mapPool.put("REMARK",mapDate.get("FI_PRO_NAME")+"退款");
						Dao.insert(xmlPath+"insertPoolDateVinual", mapPool);
			}
		}
	}
	
	public void Refund_Record_Excle(Map<String,Object> param){
		OutputStream os;
		try {
			String[] titlesName = new String[]{"序号","合同编号","客户名称","厂商","供应商","租赁物名称","还款计划","款项名称","款项类型","期次","计划收取日期","退款金额","退款日期","收款单位","开户行行名","开户行所在地","收款帐号"} ;
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFSheet xssf_w_sheet = xssf_w_book.createSheet("退款记录");
			XSSFRow xssf_w_row = null;// 创建一行
			XSSFCell xssf_w_cell = null;// 创建每个单元格
			int col_count = 0;
			int row_count = 0;
			col_count = titlesName.length;
			XSSFDataFormat format = xssf_w_book.createDataFormat();
			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();

			cellStyle_CN.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_CN.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setDataFormat(format.getFormat("@"));
			
			
			// 普通文本红色
			XSSFCellStyle cellStyle_red = xssf_w_book.createCellStyle();

			cellStyle_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setDataFormat(format.getFormat("@"));
			
			XSSFFont red_font = xssf_w_book.createFont();
			red_font.setColor((short)0xa);
			red_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_red.setFont(red_font);
			
			// 普通文本蓝色
			XSSFCellStyle cellStyle_blue = xssf_w_book.createCellStyle();

			cellStyle_blue.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_blue.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_blue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setDataFormat(format.getFormat("@"));
			
			XSSFFont blue_font = xssf_w_book.createFont();
			blue_font.setColor((short)30F);
			blue_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_blue.setFont(blue_font);
			
			// 日期
			XSSFCellStyle cellStyle_date = xssf_w_book.createCellStyle();

			cellStyle_date.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
			cellStyle_date.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_date.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			cellStyle_date.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy/mm/dd"));
			cellStyle_date.setDataFormat(format.getFormat("yyyy/MM/dd"));
			
			// 数字
			XSSFCellStyle cellStyle_num = xssf_w_book.createCellStyle();

			cellStyle_num.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			
			// 数字红色
			XSSFCellStyle cellStyle_num_red = xssf_w_book.createCellStyle();
			cellStyle_num_red.setFont(head_font);// 单元格样式使用字体
			cellStyle_num_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setDataFormat(format.getFormat("#,##0.######"));

			int titleRows = 0; // 标题占据的行数
			xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
			for (int i = 0; i < col_count; i++) {
				
				xssf_w_cell = xssf_w_row.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet.setColumnWidth(i, 5500);
				}	
			}
			++titleRows;
			List<Map<String,Object>> listMap = Dao.selectList("refundRecord.queryAll", param) ;
			
			for(Map<String,Object> map:listMap){
				xssf_w_row = xssf_w_sheet.createRow(titleRows);
				
				Object SUPPLIERS_NAME = map.get("SUPPLIERS_NAME") == null ? "" : map.get("SUPPLIERS_NAME");
				Object COMPANY_NAME = map.get("COMPANY_NAME") == null ? "" : map.get("COMPANY_NAME");
				Object CUSTNAME = map.get("CUST_NAME") == null ? "" : map.get("CUST_NAME");
				Object PRODUCT_NAME = map.get("PRODUCT_NAME") == null ? "" : map.get("PRODUCT_NAME");
				Object LEASE_CODE = map.get("LEASE_CODE") == null ? "" : map.get("LEASE_CODE");
				Object PAYLIST_CODE = map.get("PAYLIST_CODE") == null ? "" : map.get("PAYLIST_CODE");
				Object BEGINNING_NAME = map.get("BEGINNING_NAME") == null ? "" : map.get("BEGINNING_NAME");
				Object BEGINNING_NUM = map.get("BEGINNING_NUM") == null ? "" : map.get("BEGINNING_NUM");
				Object BEGINNING_RECEIVE_DATA = map.get("BEGINNING_RECEIVE_DATA") == null ? "" : map.get("BEGINNING_RECEIVE_DATA");
				Object VINUAL_MONEY = map.get("VINUAL_MONEY") == null ? "" : map.get("VINUAL_MONEY");
				Object PAYEE_NAME = map.get("PAYEE_NAME") == null ? "" : map.get("PAYEE_NAME");
				Object PAY_BANK_ACCOUNT = map.get("PAY_BANK_ACCOUNT") == null ? "" : map.get("PAY_BANK_ACCOUNT");
				Object PAY_BANK_NAME = map.get("PAY_BANK_NAME") == null ? "" : map.get("PAY_BANK_NAME");
				Object PAY_BANK_ADDRESS = map.get("PAY_BANK_ADDRESS") == null ? "" : map.get("PAY_BANK_ADDRESS");
				Object ITEM_FLAG = map.get("ITEM_FLAG") == null ? "" : map.get("ITEM_FLAG");
				Object REFUND_FLAG = map.get("REFUND_FLAG") == null ? "" : map.get("REFUND_FLAG");
				Object FI_ACCOUNT_DATE = map.get("FI_ACCOUNT_DATE") == null ?"" :map.get("FI_ACCOUNT_DATE") ;
				if("1".equals(REFUND_FLAG.toString())){
					REFUND_FLAG = "退款" ;
				}else if("2".equals(REFUND_FLAG.toString())){
					REFUND_FLAG = "代收违约金退款" ;
				}
				
				
				//序号
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(titleRows);
				
				//合同编号
				xssf_w_cell = xssf_w_row.createCell(1);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(LEASE_CODE.toString());
				

				//客户名称
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(CUSTNAME.toString());
				
				//厂商
				xssf_w_cell = xssf_w_row.createCell(3);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(COMPANY_NAME.toString());
				

				//供应商
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(SUPPLIERS_NAME.toString());
				
				//租赁物名称
				xssf_w_cell = xssf_w_row.createCell(5);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(PRODUCT_NAME.toString());
				

				//还款计划
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(PAYLIST_CODE.toString());
				
				//款项名称
				xssf_w_cell = xssf_w_row.createCell(7);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(BEGINNING_NAME.toString());
				

				//款项类型
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(REFUND_FLAG.toString());
				
				//期次
				xssf_w_cell = xssf_w_row.createCell(9);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(BEGINNING_NUM.toString());
				

				//计划收取日期
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(BEGINNING_RECEIVE_DATA.toString());
				
				//退款金额
				xssf_w_cell = xssf_w_row.createCell(11);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(VINUAL_MONEY.toString());
				

				//退款日期
				xssf_w_cell = xssf_w_row.createCell(12);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(FI_ACCOUNT_DATE.toString());
				
				//收款单位
				xssf_w_cell = xssf_w_row.createCell(13);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(PAYEE_NAME.toString());
				

				//开户行行名
				xssf_w_cell = xssf_w_row.createCell(14);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
				
				//开户行所在地
				xssf_w_cell = xssf_w_row.createCell(15);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
				

				//收款帐号
				xssf_w_cell = xssf_w_row.createCell(16);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellStyle(cellStyle_CN);						
				xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
				
				titleRows++ ;
			}
			
			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(("退款记录"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx").getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dao.update("refundRecord.updateUploadType", param) ;
	}
	
	public void pay_Back_Excle(Map<String, Object> param) {
		OutputStream os;
		try {

			//全额放款--------放款额=设备总额
			//差额放款--------放款额=设备总额-首期款-DB保证金-其他费用(如果是期初还要减去第一期租金)
			//部分差额放款----放款额=设备总额-留购价款-起租租金
			//DB差额放款-----DB差额放款=设备总额-DB保证金
			//全额
			String[] titlesName = new String[]{"序号","供应商","厂商","客户名称","租赁物名称","合同编号","款项名称","期次","计划收取日期","收款单位","开户行行名","开户行所在地","收款帐号","退款金额"};
			String[] titlesName2 = new String[]{"序号","供应商","厂商","收款单位","开户行行名","开户行所在地","收款帐号","退款金额"};
			
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFSheet xssf_w_sheet = xssf_w_book.createSheet("虚拟核销退款明细");
			XSSFSheet xssf_w_sheet2 = xssf_w_book.createSheet("虚拟核销退款-供应商");
			XSSFRow xssf_w_row = null;// 创建一行
			XSSFRow xssf_w_row2 = null;// 创建一行
			
			XSSFCell xssf_w_cell = null;// 创建每个单元格
			
			int col_count = 0;
			int col_count2 = 0;
			
			int row_count = 0;
			int row_count2 = 0;
			
			col_count = titlesName.length;
			col_count2 = titlesName2.length;
			
			
			XSSFDataFormat format = xssf_w_book.createDataFormat();

			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();

			cellStyle_CN.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_CN.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setDataFormat(format.getFormat("@"));
			
			
			// 普通文本红色
			XSSFCellStyle cellStyle_red = xssf_w_book.createCellStyle();

			cellStyle_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setDataFormat(format.getFormat("@"));
			
			XSSFFont red_font = xssf_w_book.createFont();
			red_font.setColor((short)0xa);
			red_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_red.setFont(red_font);
			
			// 普通文本蓝色
			XSSFCellStyle cellStyle_blue = xssf_w_book.createCellStyle();

			cellStyle_blue.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_blue.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_blue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setDataFormat(format.getFormat("@"));
			
			XSSFFont blue_font = xssf_w_book.createFont();
			blue_font.setColor((short)30F);
			blue_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_blue.setFont(blue_font);
			
			// 日期
			XSSFCellStyle cellStyle_date = xssf_w_book.createCellStyle();

			cellStyle_date.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
			cellStyle_date.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_date.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			cellStyle_date.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy/mm/dd"));
			cellStyle_date.setDataFormat(format.getFormat("yyyy/MM/dd"));
			
			// 数字
			XSSFCellStyle cellStyle_num = xssf_w_book.createCellStyle();

			cellStyle_num.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			
			// 数字红色
			XSSFCellStyle cellStyle_num_red = xssf_w_book.createCellStyle();
			cellStyle_num_red.setFont(head_font);// 单元格样式使用字体
			cellStyle_num_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setDataFormat(format.getFormat("#,##0.######"));
			

			int titleRows = 0; // 标题占据的行数
			int titleRows2 = 0; // 标题占据的行数
			
			xssf_w_row2 = xssf_w_sheet2.createRow(0 + titleRows2);// 第一行写入标题行
			for (int i = 0; i < col_count2; i++) {
				xssf_w_cell = xssf_w_row2.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName2[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet2.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet2.setColumnWidth(i, 5500);
				}

			}
			
			xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
			for (int i = 0; i < col_count; i++) {
				
				xssf_w_cell = xssf_w_row.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet.setColumnWidth(i, 5500);
				}
				

			}
			
			
			//
			double VINUAL_MONEYALL_JIS = 0.0;
			double VINUAL_MONEYALL_JIS_COM = 0.0;
			
			
			int jianNum = 0;
			int jianNum2=0;
			
			++titleRows2;
			++titleRows;
			
			List COMLIST=new ArrayList();
			List SUPPLIST=new ArrayList();

			// 供应商厂商数据权限
			Map SUP_MAP=BaseUtil.getSup();
			if(SUP_MAP!=null){
				param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
			}
			
			//查询出厂商
			COMLIST=Dao.selectList(xmlPath+"rwVinual_ComList",param);
			for (int i = 0; i < COMLIST.size(); i++) {
				Map mapCom=(Map)COMLIST.get(i);
				if(mapCom == null ){
					continue;
				}
				param.put("COMPANY_ID", mapCom.get("COMPANY_ID"));
				Map<String, Object> rowMap = new HashMap<String, Object>();
				
				List<Map<String,Object>> dataList = rentWriteSuppVin_Detail_Excel(param);
				row_count2 = dataList.size();
				for (int j = 0; j < row_count2; j++) {
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					rowMap = dataList.get(row_count2 != dataList.size() ? 1 : j);
					
					
					
					Object SUPPLIERS_NAME = rowMap.get("SUPPLIERS_NAME") == null ? "" : rowMap.get("SUPPLIERS_NAME");
					Object COMPANY_NAME = rowMap.get("COMPANY_NAME") == null ? "" : rowMap.get("COMPANY_NAME");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object PAY_BANK_NAME = rowMap.get("PAY_BANK_NAME") == null ? "" : rowMap.get("PAY_BANK_NAME");
					Object PAY_BANK_ADDRESS = rowMap.get("PAY_BANK_ADDRESS") == null ? "" : rowMap.get("PAY_BANK_ADDRESS");
					Object VINUAL_MONEYALL_SUP = rowMap.get("VINUAL_MONEYALL") == null ? "" : rowMap.get("VINUAL_MONEYALL");
					
					
					
					 
					
					++titleRows2;
				}
					
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					Object VINUAL_MONEYALL_COM = mapCom.get("VINUAL_MONEYALL") == null ? "" : mapCom.get("VINUAL_MONEYALL");
					
					VINUAL_MONEYALL_JIS_COM += ((java.math.BigDecimal) VINUAL_MONEYALL_COM).doubleValue();
					
					
					Object COMPANY_NAME = mapCom.get("COMPANY_NAME") == null ? "" : mapCom.get("COMPANY_NAME");
					xssf_w_cell = xssf_w_row2.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(head_cellStyle);						
					xssf_w_cell.setCellValue(COMPANY_NAME.toString()+" 汇总");
					
					xssf_w_cell = xssf_w_row2.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(VINUAL_MONEYALL_COM.toString());
					
					++jianNum2;
					++titleRows2;
				
			}
			
			xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
			
			
			xssf_w_cell = xssf_w_row2.createCell(2);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(head_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");

			xssf_w_cell = xssf_w_row2.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(VINUAL_MONEYALL_JIS_COM);
			
			
			SUPPLIST=Dao.selectList(xmlPath+"rwVinual_SupList",param);
			
			for (int i = 0; i < SUPPLIST.size(); i++) {
				Map mapCom=(Map)SUPPLIST.get(i);
				if(mapCom == null ){
					continue;
				}
				param.put("SUP_ID", mapCom.get("SUP_ID"));
				Map<String, Object> rowMap = new HashMap<String, Object>();
				
				List<Map<String,Object>> dataList = rentWriteVin_Detail_Excel(param);
				row_count = dataList.size();
				for (int j = 0; j < row_count; j++) {
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					rowMap = dataList.get(row_count != dataList.size() ? 1 : j);
					
					
					
					Object SUPPLIERS_NAME = rowMap.get("SUPPLIERS_NAME") == null ? "" : rowMap.get("SUPPLIERS_NAME");
					Object COMPANY_NAME = rowMap.get("COMPANY_NAME") == null ? "" : rowMap.get("COMPANY_NAME");
					Object CUSTNAME = rowMap.get("CUSTNAME") == null ? "" : rowMap.get("CUSTNAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object LEASE_CODE = rowMap.get("LEASE_CODE") == null ? "" : rowMap.get("LEASE_CODE");
					Object PAYLIST_CODE = rowMap.get("PAYLIST_CODE") == null ? "" : rowMap.get("PAYLIST_CODE");
					Object BEGINNING_NAME = rowMap.get("BEGINNING_NAME") == null ? "" : rowMap.get("BEGINNING_NAME");
					Object BEGINNING_NUM = rowMap.get("BEGINNING_NUM") == null ? "" : rowMap.get("BEGINNING_NUM");
					Object BEGINNING_RECEIVE_DATA = rowMap.get("BEGINNING_RECEIVE_DATA") == null ? "" : rowMap.get("BEGINNING_RECEIVE_DATA");
					Object VINUAL_MONEY = rowMap.get("VINUAL_MONEY") == null ? "" : rowMap.get("VINUAL_MONEY");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object PAY_BANK_NAME = rowMap.get("PAY_BANK_NAME") == null ? "" : rowMap.get("PAY_BANK_NAME");
					Object PAY_BANK_ADDRESS = rowMap.get("PAY_BANK_ADDRESS") == null ? "" : rowMap.get("PAY_BANK_ADDRESS");
					Object ITEM_FLAG = rowMap.get("ITEM_FLAG") == null ? "" : rowMap.get("ITEM_FLAG");
					
					if(ITEM_FLAG!=null && !ITEM_FLAG.equals("") && ITEM_FLAG.equals("2")){//租金
						Dao.update(xmlPath+"updateBeginnType",rowMap);
					}
					else{//违约金
						Dao.update(xmlPath+"updateOverdueType",rowMap);
					}
					
					
					
					//  第一个sheet
					//供应商
					xssf_w_cell = xssf_w_row.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(titleRows-jianNum);
					
					//供应商
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(SUPPLIERS_NAME.toString());
	
					//厂商
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(COMPANY_NAME.toString());
					
					
					//客户名称
					xssf_w_cell = xssf_w_row.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(CUSTNAME.toString());
					
					//租赁物名称
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PRODUCT_NAME.toString());
					
					//合同编号
					xssf_w_cell = xssf_w_row.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(LEASE_CODE.toString());
					
					
					//款项名称
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(BEGINNING_NAME.toString());
					
					//期次
					xssf_w_cell = xssf_w_row.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(BEGINNING_NUM.toString());
					
					//计划收取日期
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(BEGINNING_RECEIVE_DATA.toString());
					
					
					
					//收款单位
					xssf_w_cell = xssf_w_row.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAYEE_NAME.toString());
					
					//开户行行名
					xssf_w_cell = xssf_w_row.createCell(10);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
					
					//开户行所在地
					xssf_w_cell = xssf_w_row.createCell(11);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
					
					//收款帐号
					xssf_w_cell = xssf_w_row.createCell(12);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
					
					//退款金额
					xssf_w_cell = xssf_w_row.createCell(13);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(((java.math.BigDecimal) VINUAL_MONEY).doubleValue());
					
					++titleRows;
				}
					
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					Object VINUAL_MONEYALL = mapCom.get("VINUAL_MONEYALL") == null ? "" : mapCom.get("VINUAL_MONEYALL");
					
				   
					
					VINUAL_MONEYALL_JIS += ((java.math.BigDecimal) VINUAL_MONEYALL).doubleValue();
					
					
					Object SUPPLIERS_NAME = mapCom.get("SUPPLIERS_NAME") == null ? "" : mapCom.get("SUPPLIERS_NAME");
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(head_cellStyle);						
					xssf_w_cell.setCellValue(SUPPLIERS_NAME.toString()+" 汇总");
					
					xssf_w_cell = xssf_w_row.createCell(13);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(VINUAL_MONEYALL.toString());
					
					++jianNum;
					++titleRows;
				
				
				
			}
			xssf_w_row = xssf_w_sheet.createRow(titleRows);
			
			
			xssf_w_cell = xssf_w_row.createCell(1);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(head_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");

			xssf_w_cell = xssf_w_row.createCell(13);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(VINUAL_MONEYALL_JIS);
			
			
			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(("虚拟核销退款明细"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx").getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//设备全额放款导出
	public List<Map<String,Object>> rentWriteVin_Detail_Excel(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		return Dao.selectList(xmlPath+"rentWriteVin_Detail_Excel", m);
	}
	
	//设备全额放款导出
	public List<Map<String,Object>> rentWriteSuppVin_Detail_Excel(Map<String,Object> m)
	{
		return Dao.selectList(xmlPath+"rentWriteSuppVin_Detail_Excel", m);
	}
	
	public int supp_Bank_UP(Map map){
		Dao.delete(xmlPath+"delSuppBank",map);
		return Dao.insert(xmlPath+"insertSuppBank",map);
	}
	
	
	//垫付多收违约金退款申请页面
	public Page query_Back_DUN_SUPP_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"query_Back_SUPP_DUN",xmlPath+"query_Back_SUPP_DUN_COUNT");

	}
	
	public int supp_Bank_DUN_UP(Map map){
		Dao.delete(xmlPath+"delSuppBank",map);
		Dao.insert(xmlPath+"insertSuppBank",map);
		return Dao.update(xmlPath+"updateDunBackMoney",map);
	}
	
	public void fund_Back_DUN_Submit_SUPP(Map mapDate) {
		
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			map.put("VINUAL_BACK_STATUS", 1);
			Dao.update(xmlPath+"updateOverDueBackStatus",map);
		}
	}
	
	//垫付多收违约金
	public Page query_Back_DUN_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"query_Back_DUN",xmlPath+"query_Back_DUN_COUNT");

	}
	
	
	public void pay_Back_DUN_Excle(Map<String, Object> param) {
		OutputStream os;
		try {

			
			String[] titlesName = new String[]{"序号","供应商","厂商","客户名称","租赁物名称","合同编号","款项名称","期次","计划收取日期","收款单位","开户行行名","开户行所在地","收款帐号","退款金额"};
			String[] titlesName2 = new String[]{"序号","供应商","厂商","收款单位","开户行行名","开户行所在地","收款帐号","退款金额"};
			
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFSheet xssf_w_sheet = xssf_w_book.createSheet("代收违约金退款明细");
			XSSFSheet xssf_w_sheet2 = xssf_w_book.createSheet("代收违约金-供应商");
			XSSFRow xssf_w_row = null;// 创建一行
			XSSFRow xssf_w_row2 = null;// 创建一行
			
			XSSFCell xssf_w_cell = null;// 创建每个单元格
			
			int col_count = 0;
			int col_count2 = 0;
			
			int row_count = 0;
			int row_count2 = 0;
			
			col_count = titlesName.length;
			col_count2 = titlesName2.length;
			
			
			XSSFDataFormat format = xssf_w_book.createDataFormat();

			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();

			cellStyle_CN.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_CN.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setDataFormat(format.getFormat("@"));
			
			
			// 普通文本红色
			XSSFCellStyle cellStyle_red = xssf_w_book.createCellStyle();

			cellStyle_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setDataFormat(format.getFormat("@"));
			
			XSSFFont red_font = xssf_w_book.createFont();
			red_font.setColor((short)0xa);
			red_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_red.setFont(red_font);
			
			// 普通文本蓝色
			XSSFCellStyle cellStyle_blue = xssf_w_book.createCellStyle();

			cellStyle_blue.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_blue.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_blue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setDataFormat(format.getFormat("@"));
			
			XSSFFont blue_font = xssf_w_book.createFont();
			blue_font.setColor((short)30F);
			blue_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_blue.setFont(blue_font);
			
			// 日期
			XSSFCellStyle cellStyle_date = xssf_w_book.createCellStyle();

			cellStyle_date.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
			cellStyle_date.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_date.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			cellStyle_date.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy/mm/dd"));
			cellStyle_date.setDataFormat(format.getFormat("yyyy/MM/dd"));
			
			// 数字
			XSSFCellStyle cellStyle_num = xssf_w_book.createCellStyle();

			cellStyle_num.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			
			// 数字红色
			XSSFCellStyle cellStyle_num_red = xssf_w_book.createCellStyle();
			cellStyle_num_red.setFont(head_font);// 单元格样式使用字体
			cellStyle_num_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setDataFormat(format.getFormat("#,##0.######"));
			

			int titleRows = 0; // 标题占据的行数
			int titleRows2 = 0; // 标题占据的行数
			
			xssf_w_row2 = xssf_w_sheet2.createRow(0 + titleRows2);// 第一行写入标题行
			for (int i = 0; i < col_count2; i++) {
				xssf_w_cell = xssf_w_row2.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName2[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet2.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet2.setColumnWidth(i, 5500);
				}

			}
			
			xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
			for (int i = 0; i < col_count; i++) {
				
				xssf_w_cell = xssf_w_row.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet.setColumnWidth(i, 5500);
				}
				

			}
			
			
			//
			double VINUAL_MONEYALL_JIS = 0.0;
			double VINUAL_MONEYALL_JIS_COM = 0.0;
			
			
			int jianNum = 0;
			int jianNum2=0;
			
			++titleRows2;
			++titleRows;
			
			List COMLIST=new ArrayList();
			List SUPPLIST=new ArrayList();

			// 供应商厂商数据权限
			Map SUP_MAP=BaseUtil.getSup();
			if(SUP_MAP!=null){
				param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
			}
			
			//查询出厂商
			COMLIST=Dao.selectList(xmlPath+"rwVinual_DUN_ComList",param);
			for (int i = 0; i < COMLIST.size(); i++) {
				Map mapCom=(Map)COMLIST.get(i);
				if(mapCom == null ){
					continue;
				}
				param.put("COMPANY_ID", mapCom.get("COMPANY_ID"));
				Map<String, Object> rowMap = new HashMap<String, Object>();
				
				List<Map<String,Object>> dataList = rentWriteSupp_DUNVin_Detail_Excel(param);
				row_count2 = dataList.size();
				for (int j = 0; j < row_count2; j++) {
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					rowMap = dataList.get(row_count2 != dataList.size() ? 1 : j);
					
					
					
					Object SUPPLIERS_NAME = rowMap.get("SUPPLIERS_NAME") == null ? "" : rowMap.get("SUPPLIERS_NAME");
					Object COMPANY_NAME = rowMap.get("COMPANY_NAME") == null ? "" : rowMap.get("COMPANY_NAME");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object PAY_BANK_NAME = rowMap.get("PAY_BANK_NAME") == null ? "" : rowMap.get("PAY_BANK_NAME");
					Object PAY_BANK_ADDRESS = rowMap.get("PAY_BANK_ADDRESS") == null ? "" : rowMap.get("PAY_BANK_ADDRESS");
					Object VINUAL_MONEYALL_SUP = rowMap.get("VINUAL_MONEYALL") == null ? "" : rowMap.get("VINUAL_MONEYALL");
					
					
					
					//  第一个sheet
					//供应商
					xssf_w_cell = xssf_w_row2.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(titleRows2-jianNum2);
					
					//供应商
					xssf_w_cell = xssf_w_row2.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(SUPPLIERS_NAME.toString());
	
					//厂商
					xssf_w_cell = xssf_w_row2.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(COMPANY_NAME.toString());
					
					//收款单位
					xssf_w_cell = xssf_w_row2.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAYEE_NAME.toString());
					
					//开户行行名
					xssf_w_cell = xssf_w_row2.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
					
					//开户行所在地
					xssf_w_cell = xssf_w_row2.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
					
					//收款帐号
					xssf_w_cell = xssf_w_row2.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
					
					//退款金额
					xssf_w_cell = xssf_w_row2.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(((java.math.BigDecimal) VINUAL_MONEYALL_SUP).doubleValue());
					
					++titleRows2;
				}
					
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					Object VINUAL_MONEYALL_COM = mapCom.get("VINUAL_MONEYALL") == null ? "" : mapCom.get("VINUAL_MONEYALL");
					
					VINUAL_MONEYALL_JIS_COM += ((java.math.BigDecimal) VINUAL_MONEYALL_COM).doubleValue();
					
					
					Object COMPANY_NAME = mapCom.get("COMPANY_NAME") == null ? "" : mapCom.get("COMPANY_NAME");
					xssf_w_cell = xssf_w_row2.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(head_cellStyle);						
					xssf_w_cell.setCellValue(COMPANY_NAME.toString()+" 汇总");
					
					xssf_w_cell = xssf_w_row2.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(VINUAL_MONEYALL_COM.toString());
					
					++jianNum2;
					++titleRows2;
				
			}
			
			xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
			
			
			xssf_w_cell = xssf_w_row2.createCell(2);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(head_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");

			xssf_w_cell = xssf_w_row2.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(VINUAL_MONEYALL_JIS_COM);
			
			
			SUPPLIST=Dao.selectList(xmlPath+"rwVinualDUN_SupList",param);
			
			for (int i = 0; i < SUPPLIST.size(); i++) {
				Map mapCom=(Map)SUPPLIST.get(i);
				if(mapCom == null ){
					continue;
				}
				param.put("SUP_ID", mapCom.get("SUP_ID"));
				Map<String, Object> rowMap = new HashMap<String, Object>();
				
				List<Map<String,Object>> dataList = rentWriteVinDUN_Detail_Excel(param);
				row_count = dataList.size();
				for (int j = 0; j < row_count; j++) {
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					rowMap = dataList.get(row_count != dataList.size() ? 1 : j);
					
					
					
					Object SUPPLIERS_NAME = rowMap.get("SUPPLIERS_NAME") == null ? "" : rowMap.get("SUPPLIERS_NAME");
					Object COMPANY_NAME = rowMap.get("COMPANY_NAME") == null ? "" : rowMap.get("COMPANY_NAME");
					Object CUSTNAME = rowMap.get("CUSTNAME") == null ? "" : rowMap.get("CUSTNAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object LEASE_CODE = rowMap.get("LEASE_CODE") == null ? "" : rowMap.get("LEASE_CODE");
					Object PAYLIST_CODE = rowMap.get("PAYLIST_CODE") == null ? "" : rowMap.get("PAYLIST_CODE");
					Object BEGINNING_NAME = rowMap.get("BEGINNING_NAME") == null ? "" : rowMap.get("BEGINNING_NAME");
					Object BEGINNING_NUM = rowMap.get("BEGINNING_NUM") == null ? "" : rowMap.get("BEGINNING_NUM");
					Object BEGINNING_RECEIVE_DATA = rowMap.get("BEGINNING_RECEIVE_DATA") == null ? "" : rowMap.get("BEGINNING_RECEIVE_DATA");
					Object VINUAL_MONEY = rowMap.get("VINUAL_BACK_MONEY") == null ? "" : rowMap.get("VINUAL_BACK_MONEY");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object PAY_BANK_NAME = rowMap.get("PAY_BANK_NAME") == null ? "" : rowMap.get("PAY_BANK_NAME");
					Object PAY_BANK_ADDRESS = rowMap.get("PAY_BANK_ADDRESS") == null ? "" : rowMap.get("PAY_BANK_ADDRESS");
					Object ITEM_FLAG = rowMap.get("ITEM_FLAG") == null ? "" : rowMap.get("ITEM_FLAG");
					
					
						Dao.update(xmlPath+"updateOverdueBackUp",rowMap);
					
					
					//  第一个sheet
					//供应商
					xssf_w_cell = xssf_w_row.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(titleRows-jianNum);
					
					//供应商
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(SUPPLIERS_NAME.toString());
	
					//厂商
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(COMPANY_NAME.toString());
					
					
					//客户名称
					xssf_w_cell = xssf_w_row.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(CUSTNAME.toString());
					
					//租赁物名称
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PRODUCT_NAME.toString());
					
					//合同编号
					xssf_w_cell = xssf_w_row.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(LEASE_CODE.toString());
					
					
					//款项名称
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(BEGINNING_NAME.toString());
					
					//期次
					xssf_w_cell = xssf_w_row.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(BEGINNING_NUM.toString());
					
					//计划收取日期
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(BEGINNING_RECEIVE_DATA.toString());
					
					
					
					//收款单位
					xssf_w_cell = xssf_w_row.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAYEE_NAME.toString());
					
					//开户行行名
					xssf_w_cell = xssf_w_row.createCell(10);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
					
					//开户行所在地
					xssf_w_cell = xssf_w_row.createCell(11);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
					
					//收款帐号
					xssf_w_cell = xssf_w_row.createCell(12);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
					
					//退款金额
					xssf_w_cell = xssf_w_row.createCell(13);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(((java.math.BigDecimal) VINUAL_MONEY).doubleValue());
					
					++titleRows;
				}
					
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					Object VINUAL_MONEYALL = mapCom.get("VINUAL_MONEYALL") == null ? "" : mapCom.get("VINUAL_MONEYALL");
					
				   
					
					VINUAL_MONEYALL_JIS += ((java.math.BigDecimal) VINUAL_MONEYALL).doubleValue();
					
					
					Object SUPPLIERS_NAME = mapCom.get("SUPPLIERS_NAME") == null ? "" : mapCom.get("SUPPLIERS_NAME");
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(head_cellStyle);						
					xssf_w_cell.setCellValue(SUPPLIERS_NAME.toString()+" 汇总");
					
					xssf_w_cell = xssf_w_row.createCell(13);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(VINUAL_MONEYALL.toString());
					
					++jianNum;
					++titleRows;
				
				
				
			}
			xssf_w_row = xssf_w_sheet.createRow(titleRows);
			
			
			xssf_w_cell = xssf_w_row.createCell(1);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(head_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");

			xssf_w_cell = xssf_w_row.createCell(13);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(VINUAL_MONEYALL_JIS);
			
			
			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(("代收违约金退款明细"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx").getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Map<String,Object>> rentWriteSupp_DUNVin_Detail_Excel(Map<String,Object> m)
	{
		return Dao.selectList(xmlPath+"rentWriteSupp_DUNVin_Detail_Excel", m);
	}
	
	
	public List<Map<String,Object>> rentWriteVinDUN_Detail_Excel(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		return Dao.selectList(xmlPath+"rentWriteVinDun_Detail_Excel", m);
	}
	
	//代收违约金重置
	public void fund_BackDUN_ChongZ(Map mapDate){
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
				Dao.update(xmlPath+"updateOverdueDUNChongZ",map);
			
		}
	}
	
	//代收违约金驳回
	public void fund_BackDUN_BoHui(Map mapDate){
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
				Dao.update(xmlPath+"updateOverdueDUNBoHui",map);
		}
	}
	
	// 插入数据库和制造退款单
	public void fund_Back_DUN_C_Submit(Map mapDate) {
		
		mapDate.put("USER_CODE", Security.getUser().getCode());
		mapDate.put("USER_NAME", Security.getUser().getName());
		
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate = (List<Map<String, Object>>) mapSelect.get("selectData");
		for (int hh = 0; hh < listDate.size(); hh++) {
			String SUP_ID = "";
			String SUPPLIER_NAME = "";
			
			double VINUAL_BACK_MONEY=0d;
			
			String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
			mapDate.put("fund_head_id", fund_head_id);
			Map<String, Object> map = (Map<String, Object>) listDate.get(hh);
			
			
			Map InvoiceMap = Dao.selectOne(xmlPath+"queryFundOVERDUE", map);
			
			//违约金
				//查询必要信息
				Map m=Dao.selectOne(xmlPath+"selInfoItemDUN5", map);
				if(m!=null){
					SUP_ID = m.get("SUP_ID").toString();
					SUPPLIER_NAME= m.get("SUPPLIERS_NAME").toString();
					VINUAL_BACK_MONEY=Double.parseDouble(m.get("VINUAL_BACK_MONEY").toString());
					
					Map dueMap=new HashMap();
					mapDate.put("FI_PRO_NAME", "违约金");
					dueMap.put("fund_head_id", fund_head_id);
					dueMap.put("CUSTNAME", InvoiceMap.get("CUSTNAME"));
					dueMap.put("CUST_ID", InvoiceMap.get("CUST_ID"));
					dueMap.put("PRO_CODE", InvoiceMap.get("PRO_CODE"));
					dueMap.put("PAYLIST_CODE", InvoiceMap.get("PAYLIST_CODE"));
					dueMap.put("PRO_CODE", InvoiceMap.get("PRO_CODE"));
					dueMap.put("BEGINNING_NUM", InvoiceMap.get("BEGINNING_NUM"));
					dueMap.put("BEGINNING_RECEIVE_DATA", InvoiceMap.get("BEGINNING_RECEIVE_DATA"));
					
					
					dueMap.put("D_PAY_MONEYLB", m.get("VINUAL_BACK_MONEY"));
					dueMap.put("MONEYCURR", m.get("VINUAL_BACK_MONEY"));
					dueMap.put("D_STATUS", 2);
					dueMap.put("BEGINNING_NAME", "违约金");
					Dao.insert(xmlPath+"createDetailByBank", dueMap);
					
					Dao.update(xmlPath+"updateOverdueStatus", map);
				}
				
			
			
			mapDate.put("FI_PAY_MONEY", VINUAL_BACK_MONEY);
			mapDate.put("FI_PROJECT_NUM", 1);
			mapDate.put("SUPP_ID", SUP_ID);
			mapDate.put("SUPP_NAME", SUPPLIER_NAME);
			
			
			
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
			mapDate.put("SUP_ID", SUP_ID);
			
			// 插入付款细单
			Dao.insert(xmlPath+"createFundHeadByVinual", mapDate);
			
			
		}
	}

	public Page queryRefundRecord(Map<String, Object> param) {	
		return PageUtil.getPage(param, "refundRecord.queryAllForPage","queryCountForPage");
	}
}

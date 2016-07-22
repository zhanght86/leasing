package com.pqsoft.rentWrite.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import payment.tools.util.GUID;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.payment.action.PaymentApplyAction;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.FIinterface;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.Util;

public class rentWriteNewService {
	private final String xmlPath = "rentWriteNew.";
	
	public Page query_Bank_C_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"query_Bank_CU",xmlPath+"query_Bank_CU_count");

	}
	
	public Map querySupp_Bank(Map mapDate) {
		return Dao.selectOne(xmlPath+"querySupp_Bank", mapDate);
	}
	
	public Page toAjaxData(Map<String, Object> m) {
		m.put("tags1", "财务核销状态");
		if(!Security.getUser().getId().equals("1")){
			String ORG_LIST = BaseUtil.getSupOrgChildren();
			m.put("ORG_LIST", ORG_LIST);
		}
		return PageUtil.getPage(m, xmlPath+"query_Bank_S",xmlPath+"query_Bank_S_count");
	}
	
	public int LockTypeIsF(Map map){
		int num=1;//正常
		int num1=Dao.selectInt(xmlPath+"queryJoinLockCom", map);
		if(num1>0){
			num=2;//已锁定
		}
		else{
			int num2=Dao.selectInt(xmlPath+"queryJoinCom", map);
			if(num2>0){
				num=3;//有数据状态变化
			}
		}
		return num;
		
	}
	
	public int LockTypeIsFNew(Map map){
		int num=1;//正常
		int num1=Dao.selectInt(xmlPath+"queryLockType",null);//0:正常  >=1：已锁定
		if(num1>0){
			num=2;//已锁定
		}
		return num;
		
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
			Map<String, Object> m = Dao.selectOne(xmlPath+"selectQueryOne",map);
			String MONEY_FLAG=(m.get("MONEY_FLAG")!=null && !m.get("MONEY_FLAG").equals(""))?m.get("MONEY_FLAG").toString():"0";
			if (m.get("PAID_MONEY") != null && !m.get("PAID_MONEY").equals("")&& !m.get("PAID_MONEY").equals("0")) {
				// 修改零时表状态
//				SUP_ID = m.get("SUP_ID").toString();
//				SUPPLIER_NAME = m.get("SUP_NAME").toString();
				mapDate.put("SUPP_ID", m.get("SUP_ID"));
				mapDate.put("SUPP_NAME", m.get("SUP_NAME"));
				Dao.update(xmlPath+"updateJoinDateStauts", m);
				String ITEM_FLAG = "";
				if (m.get("ITEM_FLAG") != null && !m.get("ITEM_FLAG").equals(""))// 租金
				{
					ITEM_FLAG = m.get("ITEM_FLAG").toString();
				}
				// 通过项目CODE查询出发票开具对象
				Map InvoiceMap = Dao.selectOne(xmlPath+"queryFundInvoice", m);
				if (ITEM_FLAG.equals("2"))// 租金
				{
					double moneyPay = Double.parseDouble(m.get("PAID_MONEY").toString());
					List listDetail = Dao.selectList(xmlPath+"queryDetailByPayNum", m);
					if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
					{
						
						for (int i = 0; i < listDetail.size(); i++) {
							Map detaMap = (Map) listDetail.get(i);
							mapDate.put("FI_PRO_NAME", "租金");
							mapDate.put("FI_FUND_CODE", "1");
							detaMap.put("fund_head_id", fund_head_id);
							detaMap.put("CUSTNAME", m.get("CUSTNAME"));
							detaMap.put("CUST_ID", m.get("CUST_ID"));
							detaMap.put("PRO_CODE", m.get("PRO_CODE"));
							int flag = 0;
							if (InvoiceMap != null) {
								detaMap.put("INVOICE_TARGET_TYPE",InvoiceMap.get("INVOICE_TARGET_TYPE"));
								detaMap.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
							}
							if(MONEY_FLAG.equals("1")){
									double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
									detaMap.put("MONEYCURR", moneyLixi);
									detaMap.put("D_PAY_MONEYLB", moneyLixi);
									detaMap.put("D_STATUS", 1);
									flag = Dao.insert(xmlPath+"createDetailByBank",detaMap);
							}
							else{
								if (moneyPay > 0) {
									if (i == 0)// 先扣利息
									{
										double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
										if (moneyPay <= moneyLixi) {
											detaMap.put("D_PAY_MONEYLB", moneyLixi);
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(xmlPath+"createDetailByBank",detaMap);
											moneyPay = 0;
										} else {
											detaMap.put("D_PAY_MONEYLB", moneyLixi);
											detaMap.put("MONEYCURR", moneyLixi);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(xmlPath+"createDetailByBank",detaMap);
											moneyPay = moneyPay - moneyLixi;
										}
									} else// 再扣本金
									{
										double moneyBenj = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
										detaMap.put("D_PAY_MONEYLB", moneyBenj);
										if (moneyPay <= moneyBenj) {
											detaMap.put("MONEYCURR", moneyPay);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(xmlPath+"createDetailByBank",detaMap);
											moneyPay = 0;
										} else {
											detaMap.put("D_PAY_MONEYLB", moneyBenj);
											detaMap.put("MONEYCURR", moneyBenj);
											detaMap.put("D_STATUS", 1);
											flag = Dao.insert(xmlPath+"createDetailByBank",detaMap);
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
								Dao.update(xmlPath+"updateBeggingStatusByID",mapUpdate);
							}

						}
					}
				} else// 违约金
				{
					m.put("fund_head_id", fund_head_id);
//					SUP_ID = m.get("SUP_ID").toString();
//					SUPPLIER_NAME = m.get("SUP_NAME").toString();
					m.put("MONEYCURR", m.get("PAID_MONEY"));
					m.put("D_PAY_MONEYLB", m.get("PAID_MONEY"));
					m.put("D_STATUS", 2);
					
					String IS_BUY_PENALTY="0";
					if(map!=null && map.get("IS_BUY_PENALTY")!=null && !map.get("IS_BUY_PENALTY").equals("")){
						IS_BUY_PENALTY=map.get("IS_BUY_PENALTY").toString();
					}
					
					if(IS_BUY_PENALTY.equals("1")){//回购款违约金
						//查询发票开具对象
						Map mapInvoic=Dao.selectOne(xmlPath+"queryInvoidNew",m);
						if(mapInvoic!=null){
							m.put("INVOICE_TARGET_TYPE", mapInvoic.get("REALITY_TYPE"));
							m.put("INVOICE_TARGET_ID", mapInvoic.get("REALITY_PAYEE"));
						}
						
					}
					else{
						// 通过项目CODE查询出发票开具对象
						if (InvoiceMap != null) {
							m.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
							m.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
						}
					}
					

					Dao.insert(xmlPath+"createDetailByBank", m);
					// 如果违约金提交将逾期表中的该条违约金置为申请中
					m.put("OVERDUE_STATUS", 1);
					Dao.update(xmlPath+"updateOverDunStaute", m);
					mapDate.put("FI_PRO_NAME", "违约金");
					mapDate.put("FI_FUND_CODE", "1");
				}
			}
		}
		String FI_FAG = "3";
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
//		APP_CREATE_TYPE	2
//		BANK_ACCOUNT	
//		BANK_NAME	
//		FI_FAG	5
//		FI_PAY_DATE	2014-05-07
//		FI_PAY_MONEY	49826.65
//		FI_PAY_TYPE	1
//		FI_PROJECT_NUM	1
//		FI_REALITY_BANK	中国建设银行
//		FI_REALITY_MONEY	49826.65
//		SUPP_ID	
//		SUPP_NAME	
//		selectDateHidden	{"selectData":[{"ID":12582450,"PAID_MONEY":49826.65,"IS_BUY_PENALTY":0}]}
		
		if (FI_FAG.equals("4")) {
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
							Dao.update(xmlPath+"updateJoinDateStauts1",mapDunDate);
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

	public List getFundDetail(Map<String, Object> map) {
		map.put("tags1", "租金");
		map.put("tags2", "违约金");
		return Dao.selectList(xmlPath+"getFundDetail", map);
	}
	
	public List getFundDetailAll(Map<String, Object> map) {
		map.put("tags1", "租金");
		map.put("tags2", "违约金");
		if(map!=null && map.get("TYPE")!=null && map.get("TYPE").equals("2")){
			return Dao.selectList(xmlPath+"getFundDetailTEMP", map);
		}
		return Dao.selectList(xmlPath+"getFundDetail", map);
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
		title.put("PRODUCT_NAME", "租赁物名称");
		title.put("TYPE_NAME", "型号");
		title.put("ENGINE_TYPE", "发动机号");
		title.put("WHOLE_ENGINE_CODE", "出厂编号");
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
		Dao.insert(xmlPath+"JoinFundDateApp");
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
		return PageUtil.getPage(map, xmlPath+"toMgAppAlready",xmlPath+"toMgAppAlreadyCount");
	}
	
	public Object queryHeXiaoPage(Map<String, Object> map) {
		return Dao.selectOne(xmlPath+"queryHeXiaoPage", map);
	}
	
	// 通过fundId查询客户List
	public List queryCustByFundID(Map map) {
		return Dao.selectList(xmlPath+"queryCustByFundID", map);
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
	
	// 通过fundId查询供应商List
	public List querySuppByFundID(Map map) {
		return Dao.selectList(xmlPath+"querySuppByFundID", map);
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
							Map mapMo = Dao.selectOne(xmlPath+"selectmony", realMoney);
							realMoney.put("TIME", FI_ACCOUNT_DATE);
							if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
								Dao.update(xmlPath+"updateBeginState1", realMoney);
							}
							Dao.update(xmlPath+"updateRENT_PAIDDueNew",realMoney);
							Dao.update(xmlPath+"updateDetailRe_StatusNew",realMoney);
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
							Map dunMap=Dao.selectOne(xmlPath+"fi_overDueOne",mapDunDate);
							if(dunMap!=null){
								mapDunDate.put("DUNID", dunMap.get("DUNID"));
								Dao.update(xmlPath+"updateOverDunStaute11", mapDunDate);
								Dao.delete(xmlPath+"deleteDunDateAll11", mapDunDate);
							}
						}
							
				}
				//
			}
		}
		//查询该核销单下面所有租金的违约金处理
		this.dunUpdateStatusFund(baseData.get("FUND_ID").toString(),FI_ACCOUNT_DATE);
		this.queryPaylist_codeNum(baseData.get("FUND_ID").toString());
		return 1;
	}
	
	public void dunUpdateStatusFund(String FUND_ID,String FI_ACCOUNT_DATE){
		List list=Dao.selectList(xmlPath+"queryPaylist_codeNumZJ",FUND_ID);
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			if(map!=null){
				map.put("TIME", FI_ACCOUNT_DATE);
				dunUpdateStatusNot(map);
			}
		}
	}
	
	// 不足额时候将核销日期以后的数据都删除掉（没有完全核销的时候），当完全核销的时候将后面的数据都修改成核销日期当天的数据和核销状态修改成可核销
	public void dunUpdateStatusNot(Map map) {
		//判断有核销完成没有
		Map payListMap = Dao.selectOne(xmlPath+"queryDunMoneyStatus", map);
		if (payListMap != null && payListMap.get("SHENYUMONEY") != null && !payListMap.get("SHENYUMONEY").equals("")) {
			double SHENYUMONEY = Double.parseDouble(payListMap.get("SHENYUMONEY").toString());
			payListMap.put("TIME", map.get("TIME"));
			if (SHENYUMONEY <= 0.0001)// 标示这一期都核销完了，修改状态
			{
				// 1，修改实际到账日期以后的数据状态为可核销状态
				
				Dao.update(xmlPath+"updateDunStatusNew1", payListMap);
				//如果应收违约金为null 删除掉
				Dao.delete(xmlPath+"deleteDunWei",payListMap);
				//查询免息天数
				Map dunDay=Dao.selectOne(xmlPath+"queryDunDayMX");
				if(dunDay !=null && dunDay.get("CODE") !=null && !dunDay.get("CODE").equals("")){
					payListMap.put("DUN_DAY_MX", dunDay.get("CODE"));
					Dao.delete(xmlPath+"deleteDunDayMx",payListMap);
				}
			}
			else{//这一期没有核销完成，将有逾期数据的数据更新
				//删除核销日期之后的方法 重新刷数据
				Dao.delete(xmlPath+"deleteDunDate",payListMap);
				//重新刷数据
				Map mm=new HashMap();
				mm.put("PAY_CODE", payListMap.get("PAYLIST_CODE"));
				Dao.update("fi.overdue.upDueOne",mm);
			}
			
		}
	}
	
	public void insertPayMentNum(String FUND_ID){
		//查询出该核销单下为委托租赁的核销细项
		List list=Dao.selectList(xmlPath+"queryPaylist_codeNumZJWT",FUND_ID);
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			String PLATFORM_TYPE=(map.get("PLATFORM_TYPE")!=null && !map.get("PLATFORM_TYPE").equals(""))?map.get("PLATFORM_TYPE").toString():"8";
			if(map!=null){
				//对为委托租赁的核销细项判断该期是否核销完全，如果核销完全写入放款表，提供放款，如果没有核销完成则等待核销完成后写入放款
				//对为联合租赁的核销细项判断该期是否核销完全，如果核销完全写入联合租赁放款表，提供放款，如果没有核销完成则等待核销完成后写入放款
				//判断有核销完成没有
				Map payListMap = Dao.selectOne(xmlPath+"queryDunMoneyStatus", map);
				if (payListMap != null && payListMap.get("SHENYUMONEY") != null && !payListMap.get("SHENYUMONEY").equals("")) {
					
					if(PLATFORM_TYPE.equals("7")){//委托租赁
						//查询该支付表是否存在虚拟买卖合同，如果存在取到买卖合同放进放款表
						//如果没有买卖合同则新建一个虚拟的买卖合同（虚拟买卖合同：委托租赁不需要买卖合同，但是为了业务的关联性所以虚拟建立的。不用建立买卖合同号）
						Map buyConMap=Dao.selectOne(xmlPath+"getBuyConByPayCode", payListMap);
						if(buyConMap!=null){
							//将一些信息写入放款表
							payListMap.put("BUY_CONTRACT_ID", buyConMap.get("ID"));
							payListMap.put("PAY_BANK_ACCOUNT", buyConMap.get("SELLER_ACCOUNT"));
							payListMap.put("PAYEE_NAME", buyConMap.get("SELLER_UNIT_NAME"));
							payListMap.put("PAY_BANK_NAME", buyConMap.get("SELLER_BANK_ACCO"));
							payListMap.put("PAY_BANK_ADDRESS", buyConMap.get("SELLER_PAY_BANK_ADDRESS"));
						}
						else{
							//新建虚拟买卖合同
							String BUY_CONTRACT_ID=Dao.getSequence("SEQ_FIL_BUY_CONTRACT");
							payListMap.put("BUY_CONTRACT_ID", BUY_CONTRACT_ID);//获取买卖合同号关联到放款表
							payListMap.put("PAYEE_NAME", payListMap.get("SUP_NAME"));//获取出卖人存进放款表
							Map buyMap=new HashMap();
							buyMap.put("ID", BUY_CONTRACT_ID);
							buyMap.put("PAYLIST_CODE", payListMap.get("PAYLIST_CODE"));
							Dao.insert(xmlPath+"insertBuyCon",buyMap);
						}
					}
					
					
					double SHENYUMONEY = Double.parseDouble(payListMap.get("SHENYUMONEY").toString());
					if (SHENYUMONEY <= 0.0001)// 标示这一期都核销完了，写进放款
					{
						payListMap.put("USERID", Security.getUser().getId());
						if(PLATFORM_TYPE.equals("7")){//委托租赁
							//插入放款表
							Dao.insert(xmlPath+"insertFundPaymentPl7",payListMap);
						}
						else if(PLATFORM_TYPE.equals("8")){//联合租赁并且为代收代付
							//查询fil_project_fl_join表中其他融资公司在fi_fund_payment_fl表中有数据吗，如果有不插入，如果没有插入
							//循环遍历fil_project_fl_join表
							List FL_LIST=Dao.selectList(xmlPath+"queryFLJoinList",payListMap);
							for(int h=0;h<FL_LIST.size();h++){
								Map flMap=(Map)FL_LIST.get(h);
								if(flMap !=null){
									flMap.put("PAYLIST_CODE", payListMap.get("PAYLIST_CODE"));
									flMap.put("BEGINNING_NUM", payListMap.get("BEGINNING_NUM"));
									//查询在fi_fund_payment_fl表中有数据吗？有则相安无事，无则XXX
									int payNum=Dao.selectInt(xmlPath+"queryPayMentFlByPayList", flMap);
									if(payNum==0){
										//插入
										flMap.put("MONEYNEW", payListMap.get("MONEYNEW"));
										flMap.put("BEGINNING_RECEIVE_DATA", payListMap.get("BEGINNING_RECEIVE_DATA"));
										flMap.put("USERID", Security.getUser().getId());
										Dao.insert(xmlPath+"insertFundPaymentFL8",flMap);
									}
								}
							}
							
						}
					}
					
				}
			}
		}
	}
	
	//当核销完成后根据核销单查询出所有支付表编号和期次
	public void queryPaylist_codeNum(String FUND_ID){
		
		List list=Dao.selectList(xmlPath+"queryPaylist_codeNum",FUND_ID);
		for(int i=0;i<list.size();i++){
			Map mapDate=(Map)list.get(i);
			if(mapDate!=null && mapDate.get("BEGINNING_NUM") !=null && !mapDate.get("BEGINNING_NUM").equals("")){
				//刷新中间表数据
				Dao.update(xmlPath+"doPRC_BE_QJL_ONE",mapDate);
//				Dao.delete(xmlPath+"deleteJoinDate",mapDate);
//				Dao.insert(xmlPath+"insertJoinDate",mapDate);
			}else if(mapDate!=null){
				Dao.update(xmlPath+"doPRC_BE_QJL_PAY_CODE",mapDate);
			}
		}
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
								Map mapMo = Dao.selectOne(xmlPath+"selectmony", realMoney);
								realMoney.put("TIME", FI_ACCOUNT_DATE);
								if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
									Dao.update(xmlPath+"updateBeginState1", realMoney);
								}
								Dao.update(xmlPath+"updateRENT_PAIDDueNew",realMoney);
								Dao.update(xmlPath+"updateDetailRe_StatusNew",realMoney);
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
								Map dunMap=Dao.selectOne(xmlPath+"fi_overDueOne",mapDunDate);
								if(dunMap!=null){
									mapDunDate.put("DUNID", dunMap.get("DUNID"));
									Dao.update(xmlPath+"updateOverDunStaute11", mapDunDate);
									Dao.delete(xmlPath+"deleteDunDateAll11", mapDunDate);
								}
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
						if (FI_FLAG.equals("3")) {// 挂账到承租人资金池
							Map mapPool = new HashMap();
							mapPool.put("SOURCE_ID", mapFund.get("ID"));
							mapPool.put("TYPE", 5);
							mapPool.put("STATUS", 1);
							mapPool.put("BASE_MONEY", mapFund.get("FI_TAGE_MONEY"));
							mapPool.put("CANUSE_MONEY", mapFund.get("FI_TAGE_MONEY"));
							mapPool.put("OWNER_ID", mapFund.get("FI_TAGE_ID"));
							Dao.insert(xmlPath+"insertPoolDate", mapPool);
						} else if (FI_FLAG.equals("5"))// 挂账供应商电汇资金池
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
				this.dunUpdateStatusFund(de.get("HEAD_ID").toString(),FI_ACCOUNT_DATE);
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
							Map mapMo = Dao.selectOne(xmlPath+"selectmony", realMoney);
							realMoney.put("TIME", time);
							if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
								Dao.update(xmlPath+"updateBeginState1", realMoney);
							}
							Dao.update(xmlPath+"updateRENT_PAIDDueNew",realMoney);
							Dao.update(xmlPath+"updateDetailRe_StatusNew",realMoney);
							realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
						}
						else if(D_STATUS.length()>0 && D_STATUS.equals("2")){//违约金
							Map mapDunDate = new HashMap();
							mapDunDate.put("DUE_STATUS", 1);
							mapDunDate.put("OVERDUE_STATUS", 0);
							mapDunDate.put("PAYLIST_CODE", realMoney.get("D_PAY_CODE"));
							mapDunDate.put("BEGINNING_NUM", realMoney.get("PERIOD"));
							mapDunDate.put("REALITY_TIME", time);
							mapDunDate.put("D_RECEIVE_MONEY", realMoney.get("D_RECEIVE_MONEY"));
							Map dunMap=Dao.selectOne(xmlPath+"fi_overDueOne",mapDunDate);
							if(dunMap!=null){
								mapDunDate.put("DUNID", dunMap.get("DUNID"));
								Dao.update(xmlPath+"updateOverDunStaute11", mapDunDate);
								Dao.delete(xmlPath+"deleteDunDateAll11", mapDunDate);
							}
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
				map.put("REALITY_TIME", mapTime.get("fromDate"));
				
				//查询该核销单下面所有租金的违约金处理
				this.dunUpdateStatusFund(map.get("deducted_id").toString(),time);
				this.queryPaylist_codeNum(map.get("deducted_id").toString());
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
	
	public void destroySuppUp(Map map) {
		Dao.update(xmlPath+"destroySuppUp", map);
	}
	
	public int updateFundHeadBank(Map map) {
		return Dao.update(xmlPath+"updateFundHeadBank", map);
	}
	
	//查询锁
	public String JoinLock(){
		Map map=Dao.selectOne(xmlPath+"JoinLock");
		if(map==null){
			return "1";
		}
		return map.get("LOCKTYPE").toString();
	}
	
	public Page query_cyberBank_C_Page(Map<String, Object> m) {
		return PageUtil.getPage(m, xmlPath+"query_cyberBank_CU",xmlPath+"query_cyberBank_CU_count");

	}
	
	//修改导出全部数据
	public String query_cyberBank_All(Map map) {
		String PR_ID=Dao.getSequence("SEQ_JOIN");
		map.put("PR_ID", PR_ID);
		Dao.update(xmlPath+"updateBeginnJoinMerge", map);
		return PR_ID;
	}
	
	
	
	//修改选择导出的数据
	public String query_NewCyberBank_select(Map map){
		String PR_ID=Dao.getSequence("SEQ_JOIN");
		map.put("PR_ID", PR_ID);
		Dao.update(xmlPath+"updateBeginnJoinSelect", map);
		return PR_ID;
	}
	
	// 导出数据
	public List query_cyberBank_ListJY(String PR_ID) {
		//修改期初表状态
		Dao.update(xmlPath+"updateBeginnMerge", PR_ID);
		//修改逾期表状态
		Dao.update(xmlPath+"updateOverdueMerge", PR_ID);
		//将要导出的数据写到导出表中
		Map map=new HashMap();
		map.put("PR_ID", PR_ID);
		map.put("USER_CODE", Security.getUser().getCode());
		map.put("USER_NAME", Security.getUser().getName());
		map.put("CL_TYPE", "2");
		Dao.insert(xmlPath+"insertFund_app_temp",map);
		//导出数据
		List list=Dao.selectList(xmlPath+"queryUpload_CYBERBANK_UU_JY", PR_ID);//建元
		return list;
	}
	
	// 导出数据
	public List query_cyberBank_ListSZ(String PR_ID) {
		//修改期初表状态
		Dao.update(xmlPath+"updateBeginnMerge", PR_ID);
		//修改逾期表状态
		Dao.update(xmlPath+"updateOverdueMerge", PR_ID);
		//将要导出的数据写到导出表中
		Map map=new HashMap();
		map.put("PR_ID", PR_ID);
		map.put("USER_CODE", Security.getUser().getCode());
		map.put("USER_NAME", Security.getUser().getName());
		map.put("CL_TYPE", "2");
		Dao.insert(xmlPath+"insertFund_app_temp",map);
		//导出数据
		List list=Dao.selectList(xmlPath+"queryUpload_CYBERBANK_UUNEW", PR_ID);//山重
		return list;
	}
	
	// 导出数据
	public List query_cyberBank_ListFC(String PR_ID) {
			//修改期初表状态
			Dao.update(xmlPath+"updateBeginnMerge", PR_ID);
			//修改逾期表状态
			Dao.update(xmlPath+"updateOverdueMerge", PR_ID);
			//将要导出的数据写到导出表中
			Map map=new HashMap();
			map.put("PR_ID", PR_ID);
			map.put("USER_CODE", Security.getUser().getCode());
			map.put("USER_NAME", Security.getUser().getName());
			map.put("CL_TYPE", "2");
			Dao.insert(xmlPath+"insertFund_app_temp",map);
			//导出数据
			List list=Dao.selectList(xmlPath+"queryUpload_CYBERBANK_FCNEW", PR_ID);//山重
			return list;
		}
		
	// 导出数据
	public List query_cyberBank_ListFCGD(String PR_ID) {
				//修改期初表状态
				Dao.update(xmlPath+"updateBeginnMerge", PR_ID);
				//修改逾期表状态
				Dao.update(xmlPath+"updateOverdueMerge", PR_ID);
				//将要导出的数据写到导出表中
				Map map=new HashMap();
				map.put("PR_ID", PR_ID);
				map.put("USER_CODE", Security.getUser().getCode());
				map.put("USER_NAME", Security.getUser().getName());
				map.put("CL_TYPE", "2");
				Dao.insert(xmlPath+"insertFund_app_temp",map);
				//导出数据
				List list=Dao.selectList(xmlPath+"queryUpload_CYBERBANK_FCGDNEW", PR_ID);//山重
				return list;
	}
	
	// 导出数据
		public List query_cyberBank_ListAuto(String PR_ID) {
			//修改期初表状态
			Dao.update(xmlPath+"updateBeginnMerge", PR_ID);
			//修改逾期表状态
			Dao.update(xmlPath+"updateOverdueMerge", PR_ID);
			//将要导出的数据写到导出表中
			Map map=new HashMap();
			map.put("PR_ID", PR_ID);
			map.put("USER_CODE", "admin");
			map.put("USER_NAME", "系统管理员");
			Dao.insert(xmlPath+"insertFund_app_temp",map);
			//导出数据
//			List list=Dao.selectList(xmlPath+"queryUpload_CYBERBANK_UUNEW", PR_ID);//山重
			List list=Dao.selectList(xmlPath+"queryUpload_CYBERBANK_AUTO_JY", PR_ID);//建元
			return list;
		}
		//中金用的
		public List query_cyberBank_ListAuto_JYZL(String PR_ID) {
			//修改期初表状态
			Dao.update(xmlPath+"updateBeginnMerge", PR_ID);
//			//修改逾期表状态
			Dao.update(xmlPath+"updateOverdueMerge", PR_ID);
//			//将要导出的数据写到导出表中
			Map map=new HashMap();
			map.put("PR_ID", PR_ID);
			map.put("USER_CODE", "admin");
			map.put("USER_NAME", "系统管理员");
			Dao.insert(xmlPath+"insertFund_app_temp",map);
			//导出数据
			List list=Dao.selectList(xmlPath+"queryUpload_CYBERBANK_AUTO_JYZL", PR_ID);//中金
			return list;
		}
		
		public Map<String,Object> queryTitleAuto(String PR_ID){
			return Dao.selectOne(xmlPath+"queryTitleAuto", PR_ID);
		}
	
	public Page cyberConfirm_PageAjax(Map<String, Object> m) {

		m.put("tags3", "客户类型");
		return PageUtil.getPage(m, xmlPath+"cyberConfirm_PageAjax",xmlPath+"cyberConfirm_PageAjaxCount");
	}
	
	// 重置
	public void cyberBankRoll(Map map) {
		//备份数据
		Dao.insert(xmlPath+"insertJoinRoll", map);
		// 重置状态
		Dao.update(xmlPath+"updateBigennIDByHandId", map);
		//重置临时表状态
		Dao.update(xmlPath+"updateJoinZURollMerge",map);
		//重置违约金表状态
		Dao.update(xmlPath+"updateDueRollMerge",map);
		//删除主表
		Dao.delete(xmlPath+"deleteTempById",map);
		

	}
	
	public void rollBackAll(Map<String, Object> m) {

		m.put("tags3", "客户类型");
		List list=Dao.selectList(xmlPath+"rollBackAll", m);
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
	
	public int backOP(List list,Map mapDate) {
		Map map=new HashMap();
		map.put("ID", mapDate.get("FILE_ID"));
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("TYPE", 2);
		map.put("FILE_ALL_NUM", list.size());
		map.put("fileN", mapDate.get("fileN"));
		map.put("FUND_DATE", mapDate.get("fromDate"));
		map.put("PATH", mapDate.get("PATH"));
		map.put("FILE_TEMP_NAME", mapDate.get("FILE_TEMP_NAME"));
		int num=0;
		List listDate=new ArrayList();
		for(int i=0;i<list.size();i++){
			listDate.add((Map)list.get(i));
			num++;
			if(num==1000){
				Dao.insert(xmlPath+"insertFundHire",listDate);
				num=0;
				listDate=new ArrayList();
			}
		}
		if(num>0){
			Dao.insert(xmlPath+"insertFundHire",listDate);
			num=0;
			listDate=new ArrayList();
		}
		
		int number=Dao.insert(xmlPath+"insertFundFile",map);
		return number;
	}
	
	public Page queryFundDetailAll(Map<String, Object> m) {
		
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		return PageUtil.getPage(m, xmlPath+"queryFundDetailAll",xmlPath+"queryFundDetailAll_Count");
	}
	
	public Page toQueryAjaxData(Map<String, Object> m) {
		m.put("tags1", "财务核销状态");
		if(!Security.getUser().getId().equals("1")){
			String ORG_LIST = BaseUtil.getSupOrgChildren();
			m.put("ORG_LIST", ORG_LIST);
		}
		return PageUtil.getPage(m, xmlPath+"query_Bank_Manger",xmlPath+"query_Bank_Manger_count");
	}
	
	public Page query_Result_C_Page(Map<String, Object> m) {
		return PageUtil.getPage(m, xmlPath+"query_Result_C",xmlPath+"query_Result_C_count");
//		if(m!=null && m.get("FILE_STATUS")!=null && m.get("FILE_STATUS").equals("1")){
//			return PageUtil.getPage(m, xmlPath+"query_Result_C",xmlPath+"query_Result_C_count");
//		}
//		else{
//			return PageUtil.getPage(m, xmlPath+"query_Result_Z_C",xmlPath+"query_Result_Z_C_count");
//		}

	}
	
	public Page ERROR_APP_Page(Map<String, Object> m){
		return PageUtil.getPage(m, xmlPath+"ERROR_APP",xmlPath+"ERROR_APP_count");
	}
	
	public void ERROR_INFO(String ID){
		int num=Dao.update(xmlPath+"ERROR_INFO",ID);
		Dao.update(xmlPath+"ERROR_INFO_FILE",ID);
		
		//异常处理
		if(num>0){
			//异步调用核销方法
			new Thread(new Runnable() {
				@Override
				public void run() {
					long start = System.currentTimeMillis();
					try {
						new rentWriteNewService().autoHxLKSC();
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
	}
	
	public void autoHxHz(List<Map<String,Object>> dataList,Map fileMap){
		boolean flag = false;
		String msg = "";
		
		//对回执的数据进行处理
		this.backOPAuto(dataList,fileMap);
			
	}
	
	public void backOPAuto(List list,Map mapDate) {
		
		int num=0;
		List listDate=new ArrayList();
		for(int i=0;i<list.size();i++){
			Map<String,Object> mapD=(Map<String,Object>)list.get(i);
			mapD.put("FILE_ID", mapDate.get("FILE_ID"));
			listDate.add(mapD);
			num++;
			if(num==1000){
				Dao.insert(xmlPath+"insertFundHire",listDate);
				num=0;
				listDate=new ArrayList();
			}
		}
		if(num>0){
			Dao.insert(xmlPath+"insertFundHire",listDate);
			num=0;
			listDate=new ArrayList();
		}
	}
	
	
	public void autoHxHzCS(){
		String FILE_ID="1895";
		Map fileMap=new HashMap();
		fileMap.put("FILE_ID", FILE_ID);
		
		List listData=Dao.selectList("rentWriteNew.queryAppTempInfo",FILE_ID);
		for(int i=0;i<listData.size();i++){
			Map mapData=(Map)listData.get(i);
			mapData.put("deducted_id", mapData.get("DEDUCTED_ID"));
			mapData.put("deducted_money", mapData.get("DEDUCTED_MONEY"));
			mapData.put("deducted_money", mapData.get("DEDUCTED_MONEY"));
			if(i==3 || i==5){
				mapData.put("bank_flag", "0");
				mapData.put("bank_remark", "失败，余额不足");
			}else{
				mapData.put("bank_flag", mapData.get("BANK_FLAG"));
				mapData.put("bank_remark", mapData.get("BANK_REMARK"));
			}
		}
		this.autoHxHz(listData, fileMap);
		
	}
	
	
	//-------------------------------------建元新写核销开始----------------------------------
	//-------------------------------------建元新写核销开始----------------------------------
	//-------------------------------------建元新写核销开始----------------------------------
	//-------------------------------------建元新写核销开始----------------------------------
	//-------------------------------------建元新写核销开始----------------------------------
	//-------------------------------------建元新写核销开始----------------------------------
	//-------------------------------------建元新写核销开始----------------------------------
	//-------------------------------------建元新写核销开始----------------------------------
	//-------------------------------------建元新写核销开始----------------------------------
	
	//系统自动发送数据到接口
	public void autoHx(){
		//异步调用核销方法
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				try {
					rentWriteNewService service = new rentWriteNewService();
					//先锁定数据，防止手动核销
					Dao.update("rentWriteNew.updateLockType2");
					Map map=new HashMap();
					String PR_ID="";
					PR_ID=service.query_cyberBank_All(map);
					
					//代扣数据
					List listReturn=service.query_cyberBank_ListAuto(PR_ID);
					
					//代扣条数和代扣总金额
					Map mapTitle=service.queryTitleAuto(PR_ID);
					
					if(listReturn.size()>0){
//						String seqFileId=Dao.getSequence("SEQ_FI_FUND_FILE");
						map.put("ID", PR_ID);
						map.put("CREATE_NAME", "系统管理员");
						map.put("TYPE", 2);
						map.put("FILE_ALL_NUM", listReturn.size());
						map.put("fileN", "系统自动核销");
						Dao.insert("rentWriteNew.insertFundFileAutoNew",map);
						new FIinterface().tranx(listReturn, mapTitle,"100001");
					}
					
					//解锁
					Dao.update("rentWriteNew.updateLockType1");
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
	
	//系统自动核销（接口回执）-----------------
	public void autoInf(){
		//异步调用核销方法
		List list=Dao.selectList("rentWriteNew.queryTranxStatus");
		int num=0;
		List listDate=new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			//修改为本系统已处理
			Dao.update("rentWriteNew.updateTranxStatus",map);
			String RET_CODE=map.get("RETURN_STATUS")+"";
			String bank_flag="0";
			if(RET_CODE.equals("0000") || RET_CODE.equals("4000")){
				bank_flag="1";
			}
			map.put("bank_flag", bank_flag);
			map.put("bank_remark", map.get("RETURN_ERR_MSG") !=null ? map.get("RETURN_ERR_MSG")+"":"失败");
			map.put("deducted_id", map.get("SN"));
			map.put("BANK_NAME", "通联");
			
			listDate.add(map);
			num++;
			if(num==1000){
				Dao.insert(xmlPath+"insertFundHireAuto",listDate);
				num=0;
				listDate=new ArrayList();
			}
		}
		if(num>0){
			Dao.insert(xmlPath+"insertFundHireAuto",listDate);
			num=0;
			listDate=new ArrayList();
		}
		
		autoHxLKSC();
	}
	
	
	
	
	
	//自动核销处理,设置定时任务处理
	public void autoHxLKSC(){
		//1.判断FI_FUND_APP_TEMP表中是否有可以核销的数据处理，如果有将处理掉
		int num=Dao.selectInt("rentWriteNew.queryAppTempCounts",null);//还有没有处理的数据
		if(num>0){
			List<Map<String,Object>> listFile=Dao.selectList("rentWriteNew.queryFileW");//查询未处理的文件
			if(listFile.size()>0){
				for(int i=0;i<listFile.size();i++){
					try {
						Map<String,Object> fileMap=listFile.get(i);
						//将数据成功失败状态更新到导出的数据上
	//					Dao.update("rentWriteNew.updateAPP_TEMPStatusCG",fileMap);//银行核销成功
	//					Dao.update("rentWriteNew.updateAPP_TEMPStatusSB",fileMap);//银行核销失败
						
						int numError=0;
						//先处理该文件下的失败数据
						Map sbMap=this.fileSb(fileMap);
						int sbCount=Integer.parseInt(sbMap.get("numSb").toString());
						numError=numError+Integer.parseInt(sbMap.get("numError").toString());
						//反更失败数量
						fileMap.put("ERROR_BANK_NUM", sbCount);
						Dao.update("rentWriteNew.updateBankErrorFile",fileMap);
						Dao.commit();
						
						//处理该文件下的成功数据
						Map cgMap=this.fileCg(fileMap);
						int successNum=Integer.parseInt(cgMap.get("successNum").toString());
						numError=numError+Integer.parseInt(cgMap.get("numError").toString());
						
						//反更成功数量
						fileMap.put("SUCCESS_BANK_NUM", successNum);
						Dao.update("rentWriteNew.updateBankSuccessFile",fileMap);
						Dao.commit();
						
						//更新异常数量
						fileMap.put("ERROR_NUM", numError);
						Dao.update("rentWriteNew.updateErrorFile",fileMap);
						Dao.commit();
						
						//核销后数据处理
						//1.将数据导入历史表中
						//2.删除App表中的数据
						//3.删除hire表中的数据
						Dao.insert("rentWriteNew.insertHistoryByAppTemp");
						Dao.insert("rentWriteNew.insertHistoryByHireTemp");
						Dao.delete("rentWriteNew.deleteAppTemp");
						Dao.delete("rentWriteNew.deleteHireTemp");
						Dao.commit();
					} catch (Exception e) {
						Log.error(e, e);
						Dao.rollback();
					}finally{
						Dao.close();
					}
				}
				
			}
			
		}
		
	}
	
	
	//处理失败的数据参数FILE_ID
	public Map fileSb(Map<String,Object> fileMap){
		int numSb=0;
		int numError=0;
		List<Map<String,Object>> listSb=Dao.selectList("rentWriteNew.queryHireSbByFile",fileMap);
		numSb=listSb.size();
		for(int hh=0;hh<listSb.size();hh++){
			Map<String,Object> hireMapSb=listSb.get(hh);
			try {
				
				//先查询导出表中是否有数据，如果没有则去历史表中找数据
				int count_app_num=Dao.selectInt("rentWriteNew.queryAppDataIsF", hireMapSb);
				if(count_app_num>0){//有数据
					this.fileSbJoin(hireMapSb);
				}else{//没有数据
					//查询历史表中的数据
					int count_history_num=Dao.selectInt("rentWriteNew.queryhistoryIsF", hireMapSb);
					if(count_history_num>0){//有数据
						Dao.insert("rentWriteNew.insertDataByHistory",hireMapSb);
						Dao.delete("rentWriteNew.deletehistoryData",hireMapSb);
						//执行掉
						this.fileSbJoin(hireMapSb);
					}else{
						//几乎不存在，只是以防万一
						String MSG="找不到申请单了，历史表中也没有";
						numError++;
						//修改hire表为异常
						Dao.update("rentWriteNew.updateHireRunStatus2",hireMapSb);
						//修改APP表为处理异常
						Dao.update("rentWriteNew.updateAppRun2SB",hireMapSb);
					}
	
				}
				Dao.commit();
			} catch (Exception e) {
				Log.error(e,  e);
				Dao.rollback();
				numError++;
				//修改hire表为异常
				Dao.update("rentWriteNew.updateHireRunStatus2YC",hireMapSb);
				//修改APP表为处理异常
				Dao.update("rentWriteNew.updateAppRun2SBYC",hireMapSb);
				Dao.commit();
			}
		}
		Map mapNum=new HashMap();
		mapNum.put("numSb", numSb);
		mapNum.put("numError", numError);
		return mapNum;
	}
	
	
	public void fileSbJoin(Map<String,Object> hireMapSb){
		Map<String,Object> appMap=Dao.selectOne("rentWriteNew.queryAppTempDataInfoByID", hireMapSb);
		String ITEM_FLAG="2";//默认为租金
		if(appMap !=null && appMap.get("ITEM_FLAG") !=null && !appMap.get("ITEM_FLAG").equals("")){
			ITEM_FLAG=appMap.get("ITEM_FLAG").toString();
		}
		
		String BEGIN_IDS="";
		if(appMap !=null && appMap.get("BEGIN_ID") !=null && !appMap.get("BEGIN_ID").equals("")){
			BEGIN_IDS=appMap.get("BEGIN_ID").toString();
		}
		
		String PAY_CODE="";
		if(appMap !=null && appMap.get("PAY_CODE") !=null && !appMap.get("PAY_CODE").equals("")){
			PAY_CODE=appMap.get("PAY_CODE").toString();
		}
		
		String PERIOD="";
		if(appMap !=null && appMap.get("PERIOD") !=null && !appMap.get("PERIOD").equals("")){
			PERIOD=appMap.get("PERIOD").toString();
		}
		
		String[] BEGIN_ID=BEGIN_IDS.split(",");
		String BEGINNING_FALSE_REASON=(hireMapSb !=null && hireMapSb.get("FALSE_REASON")!=null && !hireMapSb.get("FALSE_REASON").equals(""))?hireMapSb.get("FALSE_REASON")+"":"";
		String BEGINNING_FALSE_CODE=(hireMapSb !=null && hireMapSb.get("ERROR_CODES")!=null && !hireMapSb.get("ERROR_CODES").equals(""))?hireMapSb.get("ERROR_CODES")+"":"";
		if(ITEM_FLAG.equals("2")){//租金
			//判断是否核销完全，如果没有核销完全，将状态修改
			for(int kk=0;kk<BEGIN_ID.length;kk++){
				String BEGINNING_ID=BEGIN_ID[kk];
				if(BEGINNING_ID.length()>0){
					Map beMap=new HashMap();
					beMap.put("BEGINNING_ID", BEGINNING_ID);
					beMap.put("BEGINNING_FALSE_REASON", BEGINNING_FALSE_REASON);
					beMap.put("BEGINNING_FALSE_CODE", BEGINNING_FALSE_CODE);
					Dao.update("rentWriteNew.updateBeginnStatus",beMap);
				}
			}
		}else if(ITEM_FLAG.equals("5")){//违约金
			if(PAY_CODE.length()>0 && PERIOD.length()>0){
				Map ovMap=new HashMap();
				ovMap.put("PAY_CODE", PAY_CODE);
				ovMap.put("BEGINNING_FALSE_REASON", BEGINNING_FALSE_REASON);
				ovMap.put("PERIOD", PERIOD);
				Dao.update("rentWriteNew.updateOverDueStatus",ovMap);
			}
			
		}
		
		//修改状态为已处理成功
		Dao.update("rentWriteNew.updateHireRunStatus",hireMapSb);
		//修改APP表为处理成功
		Dao.update("rentWriteNew.updateAppRunStatusSB",hireMapSb);
		
		//刷数据到中间表
		if(PAY_CODE.length()>0 && PERIOD.length()>0){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("PAYLIST_CODE", PAY_CODE);
			map.put("BEGINNING_NUM", PERIOD);
			Dao.update(xmlPath+"doPRC_BE_QJL_ONE",map);
		}
	}
	
	public Map fileCg(Map<String,Object> fileMap){
		int successNum=0;
		int numError=0;
		List<Map<String,Object>> listCg=Dao.selectList("rentWriteNew.queryHireCGByFile",fileMap);
		for(int hh=0;hh<listCg.size();hh++){
			Map<String,Object> hireMapCg=listCg.get(hh);
			try {
				//先查询导出表中是否有数据，如果没有则去历史表中找数据
				int count_app_num=Dao.selectInt("rentWriteNew.queryAppDataIsF", hireMapCg);
				if(count_app_num>0){//有数据
					
					this.fileCgJoin(hireMapCg);
					successNum++;
					
				}else{//没有数据
					//查询该条数据是否已核销掉
					int Fd_Num=Dao.selectInt("rentWriteNew.queryFundDetailByHard_Id", hireMapCg);
					if(Fd_Num<=0){
						//查询历史表中的数据
						int count_history_num=Dao.selectInt("rentWriteNew.queryhistoryIsF", hireMapCg);
						if(count_history_num>0){//有数据
							Dao.insert("rentWriteNew.insertDataByHistory",hireMapCg);
							Dao.delete("rentWriteNew.deletehistoryData",hireMapCg);
							
							this.fileCgJoin(hireMapCg);
							successNum++;
						}else{
							//几乎不存在，只是以防万一
							String MSG="找不到申请单了，历史表中也没有";
							numError++;
							//修改hire表为异常
							Dao.update("rentWriteNew.updateHireRunStatus2",hireMapCg);
							//修改APP表为处理异常
							Dao.update("rentWriteNew.updateAppRun2SB",hireMapCg);
						}
					}
					
				}
				Dao.commit();
			} catch (Exception e) {
				Log.error(e,  e);
				Dao.rollback();
				numError++;
				//修改hire表为异常
				Dao.update("rentWriteNew.updateHireRunStatus2YC",hireMapCg);
				//修改APP表为处理异常
				Dao.update("rentWriteNew.updateAppRun2SBYC",hireMapCg);
				Dao.commit();
			}
		}
		Map mapNum=new HashMap();
		mapNum.put("successNum", successNum);
		mapNum.put("numError", numError);
		return mapNum;
	}
	
	public void fileCgJoin(Map<String,Object> hireMapCg){
		Map<String,Object> appMap=Dao.selectOne("rentWriteNew.queryAppTempDataInfoByID", hireMapCg);
		String ITEM_FLAG="2";//默认为租金
		if(appMap !=null && appMap.get("ITEM_FLAG") !=null && !appMap.get("ITEM_FLAG").equals("")){
			ITEM_FLAG=appMap.get("ITEM_FLAG").toString();
		}
		
		String BEGIN_IDS="";
		if(appMap !=null && appMap.get("BEGIN_ID") !=null && !appMap.get("BEGIN_ID").equals("")){
			BEGIN_IDS=appMap.get("BEGIN_ID").toString();
		}
		
		String PAY_CODE="";
		if(appMap !=null && appMap.get("PAY_CODE") !=null && !appMap.get("PAY_CODE").equals("")){
			PAY_CODE=appMap.get("PAY_CODE").toString();
		}
		
		String PERIOD="";
		if(appMap !=null && appMap.get("PERIOD") !=null && !appMap.get("PERIOD").equals("")){
			PERIOD=appMap.get("PERIOD").toString();
		}
		
		String[] BEGIN_ID=BEGIN_IDS.split(",");
		
		if(ITEM_FLAG.equals("2")){//租金
			//判断是否核销完全，如果没有核销完全，将状态修改
			//并且都是足额核销
			for(int kk=0;kk<BEGIN_ID.length;kk++){
				String BEGINNING_ID=BEGIN_ID[kk];
				if(BEGINNING_ID.length()>0){
					//租金核销
					//先租金明细表FI_FUND_DETAIL
					//查询期初表
					Map begMap=Dao.selectOne("rentWriteNew.queryBeginningInfo",BEGINNING_ID);
					if(begMap !=null){
						//创建明细表
						Map mapDetail=new HashMap();
						String Detail_ID=Dao.getSequence("SEQ_FUND_DETAIL");
						mapDetail.put("ID", Detail_ID);
						mapDetail.put("D_FUND_ID", hireMapCg.get("HARD_ID"));
						mapDetail.put("D_PAYEE", "复昌");
						mapDetail.put("D_CLIENT_CODE", appMap.get("CUST_CODE"));
						mapDetail.put("D_CLIENT_NAME", appMap.get("CUST_NAME"));
						mapDetail.put("D_PAY_PROJECT", begMap.get("BEGINNING_NAME"));
						mapDetail.put("D_PAY_MONEY", begMap.get("D_PAY_MONEY"));
						mapDetail.put("D_RECEIVE_DATE", begMap.get("BEGINNING_RECEIVE_DATA"));
						mapDetail.put("D_REALITY_DATE", hireMapCg.get("HIRE_DATE"));
						mapDetail.put("D_PAY_CODE", begMap.get("PAYLIST_CODE"));
						mapDetail.put("D_PROJECT_CODE", appMap.get("PRO_CODE"));
						mapDetail.put("D_BEGING_ID", BEGINNING_ID);
						mapDetail.put("D_RECEIVE_MONEY", begMap.get("D_PAY_MONEY"));
						mapDetail.put("PERIOD", begMap.get("BEGINNING_NUM"));
						mapDetail.put("D_STATUS", "1");
						mapDetail.put("INVOICE_TARGET_TYPE", appMap.get("INVOICE_TARGET_TYPE"));
						mapDetail.put("INVOICE_TARGET_ID", appMap.get("INVOICE_TARGET_ID"));
						mapDetail.put("RED_STATUS", "0");
						Dao.insert("rentWriteNew.insertDetailByInfo",mapDetail);
						
						//创建细表
						Map mapAccount=new HashMap();
						mapAccount.put("FA_FUND_ID", hireMapCg.get("HARD_ID"));
						mapAccount.put("FA_BEGING_ID", BEGINNING_ID);
						mapAccount.put("FA_DETAIL_ID", Detail_ID);
						mapAccount.put("FA_ACCOINT_MONEY", begMap.get("D_PAY_MONEY"));
						Dao.insert("rentWriteNew.insertAccountByinfo",mapAccount);
						
						//更新期初表状态金额
						Map beginMap=new HashMap();
						beginMap.put("REALITY_TIME", hireMapCg.get("HIRE_DATE"));
						beginMap.put("BEGINNING_ID", BEGINNING_ID);
						Dao.update("rentWriteNew.updateBeginningByInfo",beginMap);
						
					}
					
				}
			}
		}else if(ITEM_FLAG.equals("5")){//违约金
			if(PAY_CODE.length()>0 && PERIOD.length()>0){
				//违约金核销
				Map ovMap=new HashMap();
				ovMap.put("PAY_CODE", PAY_CODE);
				ovMap.put("PERIOD", PERIOD);
				Map foMap=Dao.selectOne("rentWriteNew.queryOverDueByInfo",ovMap);
				
				
				//创建明细表
				Map mapDetail=new HashMap();
				String Detail_ID=Dao.getSequence("SEQ_FUND_DETAIL");
				mapDetail.put("ID", Detail_ID);
				mapDetail.put("D_FUND_ID", hireMapCg.get("HARD_ID"));
				mapDetail.put("D_PAYEE", "复昌");
				mapDetail.put("D_CLIENT_CODE", appMap.get("CUST_CODE"));
				mapDetail.put("D_CLIENT_NAME", appMap.get("CUST_NAME"));
				mapDetail.put("D_PAY_PROJECT", "违约金");
				mapDetail.put("D_PAY_MONEY", foMap.get("D_PAY_MONEY"));
				mapDetail.put("D_RECEIVE_DATE", foMap.get("RENT_DATE"));
				mapDetail.put("D_REALITY_DATE", hireMapCg.get("HIRE_DATE"));
				mapDetail.put("D_PAY_CODE", foMap.get("PAY_CODE"));
				mapDetail.put("D_PROJECT_CODE", appMap.get("PRO_CODE"));
				mapDetail.put("D_BEGING_ID", foMap.get("ID"));
				mapDetail.put("D_RECEIVE_MONEY", foMap.get("D_PAY_MONEY"));
				mapDetail.put("PERIOD", foMap.get("PERIOD"));
				mapDetail.put("D_STATUS", "2");
				mapDetail.put("INVOICE_TARGET_TYPE", appMap.get("INVOICE_TARGET_TYPE"));
				mapDetail.put("INVOICE_TARGET_ID", appMap.get("INVOICE_TARGET_ID"));
				mapDetail.put("RED_STATUS", "0");
				Dao.insert("rentWriteNew.insertDetailByInfo",mapDetail);
				
				//创建细表
				Map mapAccount=new HashMap();
				mapAccount.put("FA_FUND_ID", hireMapCg.get("HARD_ID"));
				mapAccount.put("FA_BEGING_ID", foMap.get("ID"));
				mapAccount.put("FA_DETAIL_ID", Detail_ID);
				mapAccount.put("FA_ACCOINT_MONEY", foMap.get("D_PAY_MONEY"));
				Dao.insert("rentWriteNew.insertAccountByinfo",mapAccount);
				
				Map mapDunDate = new HashMap();
				mapDunDate.put("DUE_STATUS", 1);
				mapDunDate.put("OVERDUE_STATUS", 0);
				mapDunDate.put("PAYLIST_CODE", foMap.get("PAY_CODE"));
				mapDunDate.put("BEGINNING_NUM", foMap.get("PERIOD"));
				mapDunDate.put("REALITY_TIME", hireMapCg.get("HIRE_DATE"));
				mapDunDate.put("D_RECEIVE_MONEY", foMap.get("D_PAY_MONEY"));
				Map dunMap=Dao.selectOne(xmlPath+"fi_overDueOne",mapDunDate);
				if(dunMap!=null){
					mapDunDate.put("DUNID", dunMap.get("DUNID"));
					Dao.update(xmlPath+"updateOverDunStaute11", mapDunDate);
					Dao.delete(xmlPath+"deleteDunDateAll11", mapDunDate);
				}
			}
			
		}
		
		//生成一笔来款
		Map mapFund=new HashMap();
		String FUND_ID=Dao.getSequence("SEQ_FI_FUND");
		mapFund.put("FUND_ID", FUND_ID);
		mapFund.put("FUND_COMECODE", appMap.get("BANK_ACCOUNT"));
		mapFund.put("FUND_COMENAME", appMap.get("TO_THE_PAYEE"));
		mapFund.put("FUND_ACCEPT_DATE", hireMapCg.get("HIRE_DATE"));
		mapFund.put("FUND_RECEIVE_MONEY", appMap.get("PAY_MONEY"));
		mapFund.put("FUND_DECOMPOUND_DATE", hireMapCg.get("HIRE_DATE"));
		mapFund.put("FUND_DECOMPOUND_PERSON", "系统自动核销生成");
		mapFund.put("FUND_NOTDECO_STATE", "0");
		mapFund.put("FUND_STATUS", "9");
		mapFund.put("FUND_PAY_CODE", appMap.get("PAY_CODE"));
		mapFund.put("FUND_PAY_PROJECT", appMap.get("PRO_CODE"));
		mapFund.put("FUND_COMMIT_STATUS", "1");
		mapFund.put("AUTOFLAG", "0");
		mapFund.put("FUND_CLIENT_NAME", appMap.get("CUST_NAME"));
		mapFund.put("FUND_TYPE", "2");
		mapFund.put("FUND_DOCKET", "接口代扣租金");
		Dao.insert("rentWriteNew.insertFundByInfo", mapFund);
		
		Map mapFundHead=new HashMap();
		mapFundHead.put("ID", appMap.get("ID"));
		mapFundHead.put("FI_PAY_TYPE", appMap.get("PAY_TYPE"));
		mapFundHead.put("FI_PAY_MONEY", appMap.get("PAY_MONEY"));
		mapFundHead.put("FI_PROJECT_NUM", appMap.get("PROJECT_NUM"));
		mapFundHead.put("FI_PAY_BANK", appMap.get("BANK_NAME"));
		mapFundHead.put("FI_FUND_CODE", "1");
		mapFundHead.put("FI_ACCOUNT_DATE", hireMapCg.get("HIRE_DATE"));
		mapFundHead.put("FI_REALITY_MONEY", appMap.get("REAL_APP_MONEY"));
		mapFundHead.put("FI_REALITY_BANK", hireMapCg.get("BANK_NAME"));
		mapFundHead.put("FI_APP_NAME", appMap.get("APP_NAME"));
		mapFundHead.put("FI_APP_DATE", appMap.get("APP_DATE"));
		mapFundHead.put("FI_CHECK_NAME", hireMapCg.get("CHECK_NAME"));
		mapFundHead.put("FI_CHECK_DATE", hireMapCg.get("CHECK_DATE"));
		mapFundHead.put("FI_TO_THE_PAYEE", appMap.get("TO_THE_PAYEE"));
		mapFundHead.put("FI_PAY_DATE", appMap.get("PAY_DATE"));
		mapFundHead.put("FI_FLAG", appMap.get("FI_FLAG"));
		mapFundHead.put("FI_FILE_NAME", hireMapCg.get("UPLOAD_FILE_NAME"));
		mapFundHead.put("FI_STATUS", "2");
		mapFundHead.put("FI_APP_CODE", appMap.get("APP_CODE"));
		mapFundHead.put("FI_CHECK_CODE", hireMapCg.get("CHECK_CODE"));
		mapFundHead.put("FI_TO_THE_ACCOUNT", appMap.get("BANK_ACCOUNT"));
		mapFundHead.put("SUP_ID", appMap.get("SUP_ID"));
		mapFundHead.put("SUPPLIER_NAME", appMap.get("SUP_NAME"));
		if(ITEM_FLAG.equals("2")){
			mapFundHead.put("FI_PRO_NAME", "租金");
		}else{
			mapFundHead.put("FI_PRO_NAME", "违约金");
		}
		mapFundHead.put("FILE_ID", hireMapCg.get("FILE_ID"));
		mapFundHead.put("UPLOADTYPE", "1");
		mapFundHead.put("FUND_ID", FUND_ID);
		Dao.insert("rentWriteNew.insertFundHeadByInfo",mapFundHead);
		
		
		
		
		//如果完全核销对违约金处理，并修改为可以核销
		//如果没有完成核销则将租金已收金额更新
		this.dunUpdateStatusFund(hireMapCg.get("HARD_ID").toString(),hireMapCg.get("HIRE_DATE").toString());
		
		//查询该核销单下面所以本金判断是否为循环授信，是的话进行处理
		this.queryPaylistBJSX(hireMapCg.get("HARD_ID").toString());
		
		//修改来款回执表为已成功
		Dao.update("rentWriteNew.updateHireRunStatus",hireMapCg);
		
		//修改APP表为处理成功
		Dao.update("rentWriteNew.updateAppRunStatusCG",hireMapCg);
		
		//刷数据到中间表
		if(PAY_CODE.length()>0 && PERIOD.length()>0){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("PAYLIST_CODE", PAY_CODE);
			map.put("BEGINNING_NUM", PERIOD);
			Dao.update(xmlPath+"doPRC_BE_QJL_ONE",map);
		}
		
		//反更支付表主表的已还期次和未款金额
		this.queryPaylistHG(hireMapCg.get("HARD_ID").toString());
		
	}
	
	
	//当核销完成后根据核销单查询出该核销单有本金的所有支付表编号和期次判断是否为循环授信，如果是则资金回笼
	public void queryPaylistBJSX(String FUND_ID){
		
		List list=Dao.selectList(xmlPath+"queryPaylist_codeBJ",FUND_ID);
		for(int i=0;i<list.size();i++){
			Map mapDate=(Map)list.get(i);
			if(mapDate!=null && mapDate.get("PAYLIST_CODE") !=null && !mapDate.get("PAYLIST_CODE").equals("")){
				
				//查看客户授信余额
				Map<String,Object> credit = Dao.selectOne(xmlPath+"toFindGrant", mapDate);
				if(credit !=null && !"0".equals(credit.get("LAST_PRICE").toString())){
					Double lastPrice = Double.parseDouble(credit.get("LAST_PRICE").toString())-Double.parseDouble(mapDate.get("RECEIVE_MONEY").toString());
					String memo = "创建项目授信金额减少, 剩余授信金额由" + new BigDecimal(credit.get("LAST_PRICE").toString()).toPlainString()+"变更为"+new BigDecimal(lastPrice).toPlainString()+"; 变更金额为:"+new BigDecimal(mapDate.get("RECEIVE_MONEY").toString()).toPlainString();
					credit.put("GRANT_PRICE", credit.get("GRANT_PRICE").toString());//授信总额
					credit.put("LAST_PRICE", lastPrice);//剩余授信金额
					credit.put("MEMO", memo);//备注
					credit.put("CREATE_ID", Security.getUser().getId());//备注
				    Dao.insert("creditLog.addCreditLog", credit);//添加授信日志
				}
				
				Dao.update(xmlPath+"updatSXBack",mapDate);
			}
		}
	}
	
	//当核销完成后根据核销单查询出该核销单下的支付表的已还期次及未还金额反更入支付表主表
	//查询核销的明细是否有客户保证金，如果有回更还款机会剩余保证金以及添加新增记录
	public void queryPaylistHG(String FUND_ID){
		List list=Dao.selectList(xmlPath+"queryPaylist_codeByFundID",FUND_ID);
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			if(map !=null && map.get("PAYLIST_CODE") !=null && !map.get("PAYLIST_CODE").equals("")){
				Dao.update(xmlPath+"updatePayHeadInfoByPaylistCode",map);
			}
		}
		
		
		List listBzj=Dao.selectList(xmlPath+"queryBZJByFundID",FUND_ID);
		
		for(int i=0;i<listBzj.size();i++){
			Map map=(Map)listBzj.get(i);
			if(map !=null && map.get("D_PAY_PROJECT") !=null && map.get("D_PAY_PROJECT").equals("客户保证金")){
				//1.回更支付表主表剩余保证金
				Dao.update(xmlPath+"updatePayHeadSyByPaylistCode",map);
				
				//2.查询客户保证金是否核销完成，如果核销完成则新增记录
				Map fundMap=new HashMap();
				fundMap.put("FUND_ID", FUND_ID);
				Dao.insert(xmlPath+"insertBzjJLInfo",fundMap);
			}
		}
		
	}
	
	
	/**
	 * 捷翊租赁：中金网银接口
	 * @author wanghl
	 * @datetime 2015年8月12日,上午9:38:58
	 */
	public void autoHx_JYZL(){
		//异步调用核销方法
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				try {
					FIinterface fi = new FIinterface();
					rentWriteNewService service = new rentWriteNewService();
					//查询未提交到接口的数据
					List<Map<String,Object>> batchNoList = Dao.selectList("rentWriteNew.selectZhongjinBatchNo");
					Map<String, Object> map = new HashMap<String, Object>();
					if(batchNoList.size()>0){
						for(int i=0;i<batchNoList.size();i++){
							map = batchNoList.get(i);
							List<Map<String,Object>> batchList = Dao.selectList("rentWriteNew.queryZhongjinBatchNo", map);
							Map<String,Object> mapTitle = Dao.selectOne("rentWriteNew.selectTitleByBatchNo", map);
							if("2".equals(map.get("TYPE"))){//2:代扣
								fi.daikou(batchList, mapTitle,"2");
							}else if("1".equals(map.get("TYPE"))||"5".equals(map.get("TYPE"))){//1代付，5服务费代付
								fi.daifu(batchList, mapTitle, map.get("TYPE").toString());
							}
						}
					}
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
	
	//捷翊租赁：系统自动核销（接口回执）-----------------
	public void autoInf_JYZL(){
		//异步调用核销方法
		List list=Dao.selectList("rentWriteNew.queryZhongjinStatus");
		int num=0;
		List listDate=new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			//修改为本系统已处理
			Dao.update("rentWriteNew.updateZhongjinStatus",map);
			String RET_CODE=map.get("RETURN_STATUS")+"";
			String bank_flag="0";
			if(RET_CODE.equals("30") ){
				bank_flag="1";
			}
			map.put("bank_flag", bank_flag);
			map.put("bank_remark", map.get("RETURN_ERR_MSG") !=null ? map.get("RETURN_ERR_MSG")+"":"失败");
			map.put("deducted_id", map.get("ITEM_NO"));
			map.put("BANK_NAME", "中金");
			
			listDate.add(map);
			num++;
			if(num==1000){
				Dao.insert(xmlPath+"insertFundHireAuto",listDate);
				num=0;
				listDate=new ArrayList();
			}
		}
		if(num>0){
			Dao.insert(xmlPath+"insertFundHireAuto",listDate);
			num=0;
			listDate=new ArrayList();
		}
		
		autoHxLKSC();
	}
	
	/**
	 * 先把核销的数据插入中金表
	 * @author wanghl
	 * @datetime 2015年8月28日,上午11:11:07
	 */
	public void insertZhongjinMiddle(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				try{
					List<Map<String, Object>> bankcode = new SysDictionaryMemcached().get("中金银行代码");
					rentWriteNewService service = new rentWriteNewService();
					int num=0;
					//先锁定数据，防止手动核销
					Dao.update("rentWriteNew.updateLockType2");
					Map map=new HashMap();
					String PR_ID="";
					PR_ID=service.query_cyberBank_All(map);
//					PR_ID="22697";
					//代扣数据
					List listReturn=service.query_cyberBank_ListAuto_JYZL(PR_ID);
					
					if(listReturn.size()>0){
						//一次传给接口不超过1000
						List<List<Map<String, Object>>> cList = getSubList(listReturn, 999);
						for(int i=0; i<cList.size();i++){
							List<Map<String, Object>> itemList = cList.get(i);
							//批次号
							String batchNo = GUID.getTxNo();
							Map<String,Object> m1 = new HashMap<String, Object>();
							for(int a=0;a<itemList.size();a++){
								Map<String,Object> itemMap = itemList.get(a);
								if(itemMap.get("BRANCH_NAME")==null||itemMap.get("ACCOUNT_NAME")==null||
										itemMap.get("ACCOUNT_NAME")==null||itemMap.get("ACCOUNT_NUMBER")==null){
									continue;
								}
								for(int j=0;j<bankcode.size();j++){
				    				m1=bankcode.get(j);
				    				//匹配银行ID
				    				if(itemMap.get("BRANCH_NAME").toString().contains(m1.get("FLAG").toString())){
				    					itemMap.put("BANK_CODE", m1.get("CODE").toString());
				    					break;
				    				}
				    			}
								if(itemMap.get("BANK_CODE")==null){
									continue;
								}
								itemMap.put("BATCH_NO", batchNo);
								itemMap.put("TYPE", "2");//2代扣
								itemMap.put("USE_STATE", "-1");//-1未提交接口
								Dao.insert("rentWriteNew.insertZhongjinMiddle", itemMap);//插入到接口表
								num++;
							}
						}
					} 
					map.put("ID", PR_ID);
					map.put("CREATE_NAME", "系统管理员");
					map.put("TYPE", 2);
					map.put("FILE_ALL_NUM", num);
					map.put("fileN", "系统自动核销");
					Dao.insert("rentWriteNew.insertFundFileAutoNew",map);
					//解锁
					Dao.update("rentWriteNew.updateLockType1");
				
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
	
	/**
	 * 根据count截取list
	 * @param list
	 * @param count 截取后每个list的size
	 * @return
	 * @author wanghl
	 * @datetime 2015年8月28日,下午3:33:35
	 */
	public List<List<Map<String,Object>>> getSubList(List<Map<String,Object>> list,double count){
		List<List<Map<String,Object>>> cList = new ArrayList<List<Map<String,Object>>>();
		if(list.size()<count){
			cList.add(list);
			return cList;
		}
		List<Map<String,Object>> iList = new ArrayList<Map<String,Object>>();
		
		double size = list.size();
		double c = size/count;
		int r = (int) Math.ceil(c);
		for(int i=0;i<r;i++){
			if((i+1)*count<list.size()){
				iList = list.subList(i*(int)count, (i+1)*(int)count);
				cList.add(iList);
			}else{
				iList = list.subList(i*(int)count, list.size());
				cList.add(iList);
				break;
			}
		}
		return cList;
	}
	
	//用指定来款核销指定期次前的数据(用于提前还租)
	//参数fundId来款ID,PAYLIST_CODE支付表,PERIOD指定期次（核销指定期次前的数据）
	public void tqHzByPeriodBefore(String fundId,String PAYLIST_CODE,int PERIOD){
		//核销单金额
		double money_head_all=0d;
		//核销单包括的项目数
		int FI_PROJECT_NUM=0;
		String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
		Map param=new HashMap();
		param.put("FUND_HEAD_ID", fund_head_id);
		param.put("FUND_ID", fundId);
		Map<String, Object> src = Dao.selectOne("fi.fund.getFundAll", fundId);
		
		if(src !=null && src.get("FUND_ACCEPT_DATE1") !=null && !src.get("FUND_ACCEPT_DATE1").equals("")){//判断来款时间不为null
			String RENT_DATE=src.get("FUND_ACCEPT_DATE1").toString();
			
			double FUND_RECEIVE_MONEY=0d;//来款金额
			if(src!=null && src.get("FUND_RECEIVE_MONEY")!=null && !src.get("FUND_RECEIVE_MONEY").equals("")){
				FUND_RECEIVE_MONEY=Double.parseDouble(src.get("FUND_RECEIVE_MONEY")+"");
			}
			
			Map settleInfo=new HashMap();
			settleInfo.put("PAYLIST_CODE", PAYLIST_CODE);
			settleInfo.put("RENT_DATE", RENT_DATE);
			settleInfo.put("PERIOD", PERIOD);
			
			//查询基本信息
			Map baseInfo=Dao.selectOne("rentWriteNew.queryBaseInfoByPaylistCode", PAYLIST_CODE);
			
			List list=Dao.selectList("rentWriteNew.querytqHzDate",settleInfo);//待核销项
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				if(map!=null){
					map.put("PAYLIST_CODE", settleInfo.get("PAYLIST_CODE"));
					double rent_money=0d;//核销金额
					String ITEM_FLAG=map.get("ITEM_FLAG")+"";//2.租金
					if(map.get("RENT_MONEY")!=null && !map.get("RENT_MONEY").equals("")){
						rent_money=Double.parseDouble(map.get("RENT_MONEY")+"");
					}
					Map detailMap=new HashMap();
					detailMap.put("fund_head_id", fund_head_id);
					detailMap.put("CUSTNAME", src.get("FUND_COMENAME"));
					detailMap.put("CUST_ID", baseInfo.get("CUST_ID"));
//					detailMap.put("CUSTNAME", settleInfo.get("CUST_NAME"));
					detailMap.put("BEGINNING_RECEIVE_DATA", map.get("RENT_DATE"));//应收日期
					detailMap.put("PAYLIST_CODE", settleInfo.get("PAYLIST_CODE"));
					detailMap.put("PRO_CODE", baseInfo.get("PRO_CODE"));
					detailMap.put("BEGINNING_NUM", map.get("BEGINNING_NUM"));
					detailMap.put("FUND_ACCEPT_DATE", RENT_DATE);
					
						if(ITEM_FLAG.equals("2")){
							List listDetail = Dao.selectList("rentWriteNew.queryDetailByPayNum", map);
							for (int h = 0; h < listDetail.size(); h++) {
								//先扣利息，在扣本金
								Map detaMap = (Map) listDetail.get(h);
								
								if (FUND_RECEIVE_MONEY > 0) {
									FI_PROJECT_NUM++;
									// 先扣手续费，利息，管理费，本金
										double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
										detailMap.put("BEGINNING_ID", detaMap.get("BEGINNING_ID"));
										detailMap.put("BEGINNING_NAME", detaMap.get("BEGINNING_NAME"));
										if (FUND_RECEIVE_MONEY <= moneyLixi) {//不足额
											detailMap.put("D_PAY_MONEYLB", moneyLixi);
											detailMap.put("MONEYCURR", FUND_RECEIVE_MONEY);
											detailMap.put("D_STATUS", 1);
											Dao.insert("rentWriteNew.createDetailByBankDate", detailMap);
											money_head_all=money_head_all+FUND_RECEIVE_MONEY;
											FUND_RECEIVE_MONEY = 0;
											
										} else {
											detailMap.put("D_PAY_MONEYLB", moneyLixi);
											detailMap.put("MONEYCURR", moneyLixi);
											detailMap.put("D_STATUS", 1);
											Dao.insert("rentWriteNew.createDetailByBankDate", detailMap);
											money_head_all=money_head_all+moneyLixi;
											FUND_RECEIVE_MONEY = FUND_RECEIVE_MONEY - moneyLixi;
											
										}
									
								}
							}
						}
						
						
						
					}
					
				}
			if(money_head_all>0){
				String FI_FAG = "3";//租金非网银
				
				param.put("FI_FAG",FI_FAG);
				param.put("USER_ID", Security.getUser().getId());
				// 组织机构应该取缓存 后面在改
				param.put("ORG_ID", Security.getUser().getOrg().getId());
				
				param.put("APP_CREATE", "租金科");
				
				param.put("FI_PAY_MONEY", money_head_all);//核销单金额
				param.put("FI_PROJECT_NUM", FI_PROJECT_NUM);//核销单项目数
				param.put("USER_CODE", Security.getUser().getCode());
				param.put("USER_NAME", Security.getUser().getName());
				param.put("FI_PAY_TYPE", "1");//默认值 没意义
				param.put("BANK_NAME", src.get("FUND_COMENAME"));//来款人
				param.put("BANK_ACCOUNT", src.get("FUND_COMECODE"));//来款帐号
				param.put("FI_ACCOUNT_DATE", RENT_DATE);
				param.put("fund_head_id", fund_head_id);
				
				param.put("FI_PRO_NAME", "租金");
				param.put("FI_FUND_CODE", "1");
				
				Dao.insert("rentWriteNew.createFundHead_Not", param);
				
				//-------------------以上是创建核销单----------------------
				//-------------------以下是核销----------------------------
				this.HXPASS(fund_head_id,RENT_DATE);
			}
			
			
			//反更支付表主表的已还期次和未款金额
			this.queryPaylistHG(fund_head_id);
			
			//查询该核销单下面所以本金判断是否为循环授信，是的话进行处理
			this.queryPaylistBJSX(fund_head_id);
			
			//查询该核销单下面所有租金的违约金处理
			this.dunUpdateStatusFund(fund_head_id,RENT_DATE);
			this.queryPaylist_codeNum(fund_head_id);
		}
		
		
		
	}
	
	//平均冲抵（客户保证金平均冲抵到今天及今天以后的未还每一期次上面），按照比例平均冲抵，最后一期配平
	public void pjcdByPayListCode(String PAYLIST_CODE){
		//1.先查出保证金剩余金额
		//2.查出该还款机会未还金额（尾款不冲低）
	}
	
	//期末冲抵(还款计划编号，达成协议日期)
	public void qmcdByPayListCode(String PAYLIST_CODE,String RENT_DATE){
		//1.先查出保证金剩余金额
		//2.查出该还款计划未还金额(尾款不冲抵)
		//3.判断保证金剩余金额是否足够核销为收金额，如果足够则有剩余保证金，不过不够则剩余保证金为0
		//4.生成来款
		//5.自动核销
		//6.记录核销记录
		
		//1
		Map mapInfo=new HashMap();
		mapInfo.put("PAYLIST_CODE", PAYLIST_CODE);
		mapInfo.put("RENT_DATE", RENT_DATE);
		
		double bzj_sy_money=Dao.selectDouble("rentWriteNew.queryDeposit_SyByPayList_code", PAYLIST_CODE);
		if(bzj_sy_money>0){//剩余保证金大于0继续，否则终止
			//2
			double ws_zj_money=Dao.selectDouble("rentWriteNew.queryWsZjByPayList_code", mapInfo);
			
			if(ws_zj_money>0){
				double fund_come_money=0;
				//3
				if(ws_zj_money >= bzj_sy_money){
					fund_come_money=bzj_sy_money;
				}else if(ws_zj_money < bzj_sy_money){
					fund_come_money=ws_zj_money;
				}
				
				//4
				//查询基本信息
				Map baseInfo=Dao.selectOne("rentWriteNew.queryBaseInfoByPaylistCode", PAYLIST_CODE);
				
				String FUND_ID_BZJ=Dao.selectOne("fi.fund.getFundId");
				String FUND_CODE_BZJ=Dao.selectOne("fi.fund.getFundCode");
				
				Map mapDate=new HashMap();
				mapDate.put("PAY_ID", baseInfo.get("PAY_ID"));
				mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
				mapDate.put("FUND_ACCEPT_DATE", RENT_DATE);//来款时间
				mapDate.put("FUND_COMENAME", baseInfo.get("CUST_NAME"));//来款人
				mapDate.put("FUND_RECEIVE_MONEY", fund_come_money);//来款金额
				mapDate.put("FUND_SY_MONEY", fund_come_money);//来款金额
				mapDate.put("FUND_DOCKET", "期末冲抵客户保证金抵扣，系统自动插入！");//来款摘要
				mapDate.put("FUND_ID", FUND_ID_BZJ);
				mapDate.put("FUND_FUNDCODE", FUND_CODE_BZJ);
				mapDate.put("FUND_NOTDECO_STATE", "4");
				mapDate.put("FUND_STATUS", "0");
				mapDate.put("FUND_ISCONGEAL", "0");
				mapDate.put("FUND_RED_STATUS", "0");
				mapDate.put("FUND_PRANT_ID", "0");
				mapDate.put("FUND_IMPORT_PERSON", Security.getUser().getName());
				mapDate.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
				mapDate.put("FUND_CLIENT_ID", baseInfo.get("CUST_ID"));//认款
				mapDate.put("FUND_CLIENT_NAME", baseInfo.get("CUST_NAME"));
				mapDate.put("FUND_PAY_CODE", PAYLIST_CODE);//将要分解到那张支付表
				mapDate.put("FUND_TYPE", 2);
				Dao.insert("fi.fund.add", mapDate);
				
				
					//原系统保证金池的处理
					if(fund_come_money>0){
						Map mapPay=new HashMap();
						mapPay.put("PAYLIST_CODE", PAYLIST_CODE);
						Map poolMap=Dao.selectOne("cashDeposit.queryBaseInfoByPayListCode",mapPay);
						if(poolMap !=null){
							//写入子表
							Map poolDeatil=new HashMap();
							poolDeatil.put("POOL_ID", poolMap.get("POOL_ID"));
							poolDeatil.put("FUND_ID", FUND_ID_BZJ);
							poolDeatil.put("FUND_CODE", FUND_CODE_BZJ);
							poolDeatil.put("TYPE", 1);
							poolDeatil.put("USERMONEY", fund_come_money);
							Dao.insert("cashDeposit.insertPoolDetail",poolDeatil);
							
							//修改主表可用余额
							Dao.update("cashDeposit.updatePoolMoneyInfoByPoolId",poolDeatil);
						}
					}
					
					//现系统保证金余额的处理
					if(fund_come_money>0){
						//添加记录明细
						Map bzjJl=new HashMap();
						bzjJl.put("PAYLIST_CODE", PAYLIST_CODE);
						bzjJl.put("BEGIN_BZJ", bzj_sy_money);
						bzjJl.put("SY_BZJ", bzj_sy_money - fund_come_money);
						bzjJl.put("REMARK", "期末冲抵，使用金额"+fund_come_money);
						bzjJl.put("OPERATOR_MAN", Security.getUser().getName());
						bzjJl.put("TYPE", "期末冲抵");
						bzjJl.put("FUND_ID", FUND_ID_BZJ);
						bzjJl.put("FUND_CODE", FUND_CODE_BZJ);
						Dao.insert("rentWriteNew.insertBzjJlInfo",bzjJl);
						
						//修改还款机会表主表剩余金额
						Map mapBzjYe=new HashMap();
						mapBzjYe.put("PAY_ID", baseInfo.get("PAY_ID"));
						mapBzjYe.put("USERMONEY", fund_come_money);
						Dao.update("rentWriteNew.updateBzjYEByPaylistCode",mapBzjYe);
					}
					
					//核销
					this.qmFundRentByFundID(FUND_ID_BZJ,RENT_DATE,mapInfo,baseInfo,"1");
					
					
			}
		}
	}
	
	
	//期末冲抵自动核销（尾款不冲抵）
	public void qmFundRentByFundID(String fundId,String RENT_DATE,Map settleInfo,Map baseInfo,String TYPE){
		//核销单金额
		double money_head_all=0d;
		//核销单包括的项目数
		int FI_PROJECT_NUM=0;
		String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
		Map param=new HashMap();
		param.put("FUND_HEAD_ID", fund_head_id);
		param.put("FUND_ID", fundId);
		Map<String, Object> src = Dao.selectOne("fi.fund.getFundAll", fundId);
		src.put("FUND_ACCEPT_DATE1", RENT_DATE);
		
		double FUND_RECEIVE_MONEY=0d;//来款金额
		if(src!=null && src.get("FUND_RECEIVE_MONEY")!=null && !src.get("FUND_RECEIVE_MONEY").equals("")){
			FUND_RECEIVE_MONEY=Double.parseDouble(src.get("FUND_RECEIVE_MONEY")+"");
		}
		
		settleInfo.put("RENT_DATE", RENT_DATE);
		List list=Dao.selectList("rentWriteNew.queryQMDate",settleInfo);//待核销项
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			if(map!=null){
				map.put("PAYLIST_CODE", settleInfo.get("PAYLIST_CODE"));
				double rent_money=0d;//核销金额
				String ITEM_FLAG=map.get("ITEM_FLAG")+"";//2.租金
				if(map.get("RENT_MONEY")!=null && !map.get("RENT_MONEY").equals("")){
					rent_money=Double.parseDouble(map.get("RENT_MONEY")+"");
				}
				Map detailMap=new HashMap();
				detailMap.put("fund_head_id", fund_head_id);
				detailMap.put("CUSTNAME", src.get("FUND_COMENAME"));
				detailMap.put("CUST_ID", baseInfo.get("CUST_ID"));
				detailMap.put("CUSTNAME", settleInfo.get("CUST_NAME"));
				detailMap.put("BEGINNING_RECEIVE_DATA", map.get("RENT_DATE"));//应收日期
				detailMap.put("PAYLIST_CODE", settleInfo.get("PAYLIST_CODE"));
				detailMap.put("PRO_CODE", baseInfo.get("PRO_CODE"));
				detailMap.put("BEGINNING_NUM", map.get("BEGINNING_NUM"));
				detailMap.put("FUND_ACCEPT_DATE", RENT_DATE);
				
					if(ITEM_FLAG.equals("2")){
						List listDetail = Dao.selectList("rentWriteNew.queryDetailByPayNum", map);
						for (int h = 0; h < listDetail.size(); h++) {
							//先扣利息，在扣本金
							Map detaMap = (Map) listDetail.get(h);
							
							if (FUND_RECEIVE_MONEY > 0) {
								FI_PROJECT_NUM++;
								// 先扣手续费，利息，管理费，本金
									double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
									detailMap.put("BEGINNING_ID", detaMap.get("BEGINNING_ID"));
									detailMap.put("BEGINNING_NAME", detaMap.get("BEGINNING_NAME"));
									if (FUND_RECEIVE_MONEY <= moneyLixi) {//不足额
										detailMap.put("D_PAY_MONEYLB", moneyLixi);
										detailMap.put("MONEYCURR", FUND_RECEIVE_MONEY);
										detailMap.put("D_STATUS", 1);
										Dao.insert("rentWriteNew.createDetailByBankDate", detailMap);
										money_head_all=money_head_all+FUND_RECEIVE_MONEY;
										FUND_RECEIVE_MONEY = 0;
										
									} else {
										detailMap.put("D_PAY_MONEYLB", moneyLixi);
										detailMap.put("MONEYCURR", moneyLixi);
										detailMap.put("D_STATUS", 1);
										Dao.insert("rentWriteNew.createDetailByBankDate", detailMap);
										money_head_all=money_head_all+moneyLixi;
										FUND_RECEIVE_MONEY = FUND_RECEIVE_MONEY - moneyLixi;
										
									}
								
							}
						}
					}
					
					
					
				}
				
			}
		if(money_head_all>0){
			String FI_FAG = "7";//保证金抵扣
			
			param.put("FI_FAG",FI_FAG);
			param.put("USER_ID", Security.getUser().getId());
			// 组织机构应该取缓存 后面在改
			param.put("ORG_ID", Security.getUser().getOrg().getId());
			
			param.put("APP_CREATE", "租金科");
			
			param.put("FI_PAY_MONEY", money_head_all);//核销单金额
			param.put("FI_PROJECT_NUM", FI_PROJECT_NUM);//核销单项目数
			param.put("USER_CODE", Security.getUser().getCode());
			param.put("USER_NAME", Security.getUser().getName());
			param.put("FI_PAY_TYPE", "1");//默认值 没意义
			param.put("BANK_NAME", src.get("FUND_COMENAME"));//来款人
			param.put("BANK_ACCOUNT", src.get("FUND_COMECODE"));//来款帐号
			param.put("FI_ACCOUNT_DATE", RENT_DATE);
			param.put("fund_head_id", fund_head_id);
			
			param.put("FI_PRO_NAME", "租金");
			param.put("FI_FUND_CODE", "1");
			
			Dao.insert("rentWriteNew.createFundHead_Not", param);
			
			//-------------------以上是创建核销单----------------------
			//-------------------以下是核销----------------------------
			this.HXPASS(fund_head_id,RENT_DATE);
		}
		
		
		//反更支付表主表的已还期次和未款金额
		this.queryPaylistHG(fund_head_id);
		
		//查询该核销单下面所以本金判断是否为循环授信，是的话进行处理
		this.queryPaylistBJSX(fund_head_id);
		
		//查询该核销单下面所有租金的违约金处理
		this.dunUpdateStatusFund(fund_head_id,RENT_DATE);
		this.queryPaylist_codeNum(fund_head_id);
	}
	
	
	public void HXPASS(String FUND_HEAD_ID,String RENT_DATE){
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
					fund.put("FUND_ID", fundId);
					
					fund = Dao.selectOne("fi.fund.getFundAll", fundId);
					fund.put("FUND_ACCEPT_DATE1", RENT_DATE);
					fund.put("FUND_STATUS", "4");
					double FUND_RECEIVE_MONEY=Double.parseDouble(fund.get("FUND_RECEIVE_MONEY")+"");//原始金额
					double HX_MONEY=Double.parseDouble(FUND_HEAD_MAP.get("FI_PAY_MONEY")+"");//核销金额
					// 待分解
					double DFJ = Util.formatDouble2(FUND_RECEIVE_MONEY-HX_MONEY);
					
					fund.put("FUND_SY_MONEY", DFJ);
					if (Dao.update("fi.funddec.upFundStatusED", fund) != 1) { throw new ActionException("该资金已经被处理。"); }
					
					
					if(DFJ>0){
						fund.put("FUND_PRANT_ID", fund.get("FUND_ID"));
						fund.put("FUND_ID", Dao.selectOne("fi.fund.getFundId"));
						fund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
						fund.put("FUND_STATUS", "0");
						fund.put("FUND_NOTDECO_STATE", "1");
						fund.put("FUND_ISCONGEAL", "1");//先冻结起来，后续在解冻
						
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
				String FI_ACCOUNT_DATE=RENT_DATE;//来款日期
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
								//不存在第一期的首期款，因为作为首期款的第一期都没有还清何必做提前结清
								
								realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
							}
								
					}
					//
				}
				//如果核销的数据为委托租赁的租金，并且该期核销完成，像放款表中插入一条数据
				//如果核销的数据为联合租赁并未代收代付的租金，并且该期核销完成，像代付其他融资公司放款表中插入一条数据
				this.insertPayMentNum(FUND_HEAD_ID);
			}
	
}

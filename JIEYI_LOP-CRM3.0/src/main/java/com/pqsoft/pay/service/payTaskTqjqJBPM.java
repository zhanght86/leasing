package com.pqsoft.pay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.rzzlAPI.CarRegistAPI;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class payTaskTqjqJBPM {
	//提前结清流程审批类
	private final String xmlPath = "PayTask.";

	//1.提前结清驳回(需要参数PAY_ID,PAYLIST_CODE)
	public void tqjqNoPass(String PAY_ID, String PAYLIST_CODE){
		//删除支付表头表和细表
		Dao.delete(xmlPath + "deleteDetailByPayID", PAY_ID);
		Dao.delete(xmlPath + "deleteHeadByPayID", PAY_ID);

		//删除提前结清记录表
		Dao.delete(xmlPath + "deleteParamByPayID", PAY_ID);
		//删除今天罚息外数据
		Dao.delete(xmlPath + "deleteOverDueByPayID", PAYLIST_CODE);

		Map<String,Object> map = new HashMap<>();
		map.put("PAYLIST_CODE", PAYLIST_CODE);
		Dao.update("PayTask.upDateToJoin", map);
	}

	//2.提前结清不可逆通过(需要参数PAY_ID,PAYLIST_CODE)
	//TODO --缺少减少抵扣部分保证金的处理
	public void tqjqPass(String PAY_ID, String PAYLIST_CODE){
		//同步期初表数据
		//1.同步租金
		PayTaskService payTask = new PayTaskService();
		Map mapScheme = payTask.doShowJQByPayId(PAY_ID);
		if(mapScheme != null && mapScheme.get("JQ_PERIOD") != null && !mapScheme.get("JQ_PERIOD").equals("")){
			String PERIOD = mapScheme.get("JQ_PERIOD") + "";
			Map<String,Object> mapDate = new HashMap<>();
			mapDate.put("PAY_ID", PAY_ID);
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("PERIOD", PERIOD);
			Dao.update(xmlPath + "updateBeginningByDetail",mapDate);
		}
		//2.同步其他费用
		if(mapScheme != null && mapScheme.get("RENT_DATE") != null && !mapScheme.get("RENT_DATE").equals("")){
			Map<String,Object> mapDate = new HashMap<>();
			mapDate.put("PAY_ID", PAY_ID);
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("RENT_DATE", mapScheme.get("RENT_DATE"));
			mapDate.put("CREATE_ID", Security.getUser().getId());
			Dao.update(xmlPath + "insertItemOther", mapDate);
			Dao.update(xmlPath + "updateFlagTypeByPayID", mapDate);
		}

		String FUND_ID = Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE = Dao.selectOne("fi.fund.getFundCode");
		//如果保证金抵扣则生成一笔保证金来款
		if(mapScheme != null && mapScheme.get("DEPOSIT") != null && !mapScheme.get("DEPOSIT").equals("")){
			double DEPOSIT = Double.parseDouble(mapScheme.get("DEPOSIT") + "");
			if(DEPOSIT > 0){
				String RENT_DATE = "";
				if(mapScheme.get("RENT_DATE") != null && !mapScheme.get("RENT_DATE").equals("")){
					RENT_DATE = mapScheme.get("RENT_DATE") + "";
				}
				Map<String,Object> mapDate = new HashMap<>();
				mapDate.put("PAY_ID", PAY_ID);
				mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
				mapDate.put("FUND_ACCEPT_DATE", RENT_DATE);//来款时间
				mapDate.put("FUND_COMENAME", mapScheme.get("CUST_NAME"));//来款时间
				mapDate.put("FUND_RECEIVE_MONEY", mapScheme.get("DEPOSIT"));//来款金额
				mapDate.put("FUND_SY_MONEY", mapScheme.get("DEPOSIT"));//来款余额
				mapDate.put("FUND_DOCKET", "提前结清客户保证金抵扣，系统自动插入！");//来款摘要
				mapDate.put("FUND_ID", FUND_ID);
				mapDate.put("FUND_FUNDCODE", FUND_CODE);
				mapDate.put("FUND_NOTDECO_STATE", "7");
				mapDate.put("FUND_STATUS", "0");
				mapDate.put("FUND_ISCONGEAL", "0");
				mapDate.put("FUND_RED_STATUS", "0");
				mapDate.put("FUND_PRANT_ID", "0");
				mapDate.put("FUND_IMPORT_PERSON", Security.getUser().getName());
				mapDate.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
				mapDate.put("FUND_CLIENT_ID", mapScheme.get("CUST_ID"));//认款
				mapDate.put("FUND_CLIENT_NAME", mapScheme.get("CUST_NAME"));
				mapDate.put("FUND_PAY_CODE", PAYLIST_CODE);//将要分解到那张支付表
				Dao.insert("fi.fund.add", mapDate);
			}
		}
		if(mapScheme != null && mapScheme.get("DEPOSIT") != null && !mapScheme.get("DEPOSIT").equals("")){
			double DEPOSIT = Double.parseDouble(mapScheme.get("DEPOSIT") + "");
			if(DEPOSIT > 0){
				//根据PAYLIST_CODE查询资金池信息
				Map<String,Object> mapPay = new HashMap<>();
				mapPay.put("PAYLIST_CODE", PAYLIST_CODE);
				Map poolMap = Dao.selectOne("cashDeposit.queryBaseInfoByPayListCode", mapPay);
				if(poolMap != null){
					//写入子表
					Map<String,Object> poolDeatil = new HashMap<>();
					poolDeatil.put("POOL_ID", poolMap.get("POOL_ID"));
					poolDeatil.put("FUND_ID", FUND_ID);
					poolDeatil.put("FUND_CODE", FUND_CODE);
					poolDeatil.put("TYPE", 1);
					poolDeatil.put("USERMONEY", mapScheme.get("DEPOSIT"));
					Dao.insert("cashDeposit.insertPoolDetail", poolDeatil);

					//修改主表可用余额
					Dao.update("cashDeposit.updatePoolMoneyInfoByPoolId", poolDeatil);
				}
			}

			//现系统保证金余额的处理
			if(DEPOSIT > 0){
				double bzj_sy_money = Dao.selectDouble("rentWriteNew.queryDeposit_SyByPayList_code", PAYLIST_CODE);
				//添加记录明细
				Map<String,Object> bzjJl = new HashMap<>();
				bzjJl.put("PAYLIST_CODE", PAYLIST_CODE);
				bzjJl.put("BEGIN_BZJ", bzj_sy_money);
				bzjJl.put("SY_BZJ", bzj_sy_money - DEPOSIT);
				bzjJl.put("REMARK", "提前结清，保证金抵扣，使用金额"+DEPOSIT);
				bzjJl.put("OPERATOR_MAN", Security.getUser().getName());
				bzjJl.put("TYPE", "抵扣");
				bzjJl.put("FUND_ID", FUND_ID);
				bzjJl.put("FUND_CODE", FUND_CODE);
				Dao.insert("rentWriteNew.insertBzjJlInfo",bzjJl);

				//修改还款机会表主表剩余金额
				Map<String,Object> mapBzjYe = new HashMap<>();
				mapBzjYe.put("PAY_ID", PAY_ID);
				mapBzjYe.put("USERMONEY", DEPOSIT);
				Dao.update("rentWriteNew.updateBzjYEByPaylistCode", mapBzjYe);
			}
		}
	}

	//3.提前结清自动核销(需要参数PAY_ID,PAYLIST_CODE)
	public void tqjqFundRent(String PAY_ID,String PAYLIST_CODE){
		PayTaskService payTaskSer = new PayTaskService();
		//提前结清保存项
		Map<String, Object> settleInfo = payTaskSer.doShowJQByPayId(PAY_ID);
		String RENT_DATE = settleInfo.get("RENT_DATE") + "";//结清时间
		double TOTAL_MONEY = 0d;
		if(settleInfo.get("TOTAL_MONEY") != null && !settleInfo.get("TOTAL_MONEY").equals("")){
			TOTAL_MONEY = Double.parseDouble(settleInfo.get("TOTAL_MONEY") + "");//需要金额
		}

		//查询一些基本信息
		Map baseInfo = Dao.selectOne(xmlPath + "queryBaseInfoByInfo", settleInfo);
		//先判断来款够不够核销，如果不够不让通过提示请上传来款
		double FUND_RECEIVE_MONEY = Dao.selectDouble(xmlPath+"queryFundMoneyByCustID", settleInfo);//来款金额
		if(FUND_RECEIVE_MONEY < TOTAL_MONEY){//来款金额不足以支付提前结清所收费用
			throw new ActionException("来款不足以支付结清所需费用，请补充来款在进行操作！");
		}
		else{
			//如果来款够核销，则需要先冻结来款
			Dao.update(xmlPath + "updateDJFundMoneyByCustID", settleInfo);
			//核销顺序从抵扣先用在使用待分解最后使用来款
			//1.抵扣金额(判断有抵扣金额吗，有的话做抵扣)
			if(settleInfo.get("DEPOSIT") != null && !settleInfo.get("DEPOSIT").equals("")){
				double DEPOSIT = Double.parseDouble(settleInfo.get("DEPOSIT") + "");
				if(DEPOSIT > 0){
					Map mapDikou = Dao.selectOne(xmlPath + "queryFundByCustIdDikou", settleInfo);
					if(mapDikou != null){
						this.fundRentByFundID(mapDikou.get("FUND_ID") + "",RENT_DATE,settleInfo,baseInfo, "1");
					}
				}
			}
			//2.待分解及来款
			List<Map<String, Object>> list = Dao.selectList(xmlPath + "queryFundByCustId", settleInfo);
			for (Map<String, Object> fundMap : list) {
				if (fundMap != null && fundMap.get("FUND_RECEIVE_MONEY") != null && !fundMap.get("FUND_RECEIVE_MONEY").equals("")) {
					double FUND_COM_MONEY = Double.parseDouble(fundMap.get("FUND_RECEIVE_MONEY") + "");
					if (FUND_COM_MONEY > 0) {
						this.fundRentByFundID(fundMap.get("FUND_ID") + "", RENT_DATE, settleInfo, baseInfo, "2");
					}
				}
			}
			//核销完成后对冻结的来款解冻
			Dao.update(xmlPath + "updateJDFundMoneyByCustID", settleInfo);
		}
	}

	//4.提前结清结束(需要参数PAY_ID)
	public void tqjqEnd(String PAY_ID){
		Map<String, Object> map = new HashMap<>();
		map.put("STATUS", "6");
		map.put("ID", PAY_ID);

		PayTaskService payTaskSer = new PayTaskService();
		//修改支付表结束时间
		Map<String, Object> settleInfo = payTaskSer.doShowJQByPayId(PAY_ID);
		String ACCOUNT_DATE = settleInfo.get("RENT_DATE")+"";//结清时间
		map.put("END_DATE", ACCOUNT_DATE);
		Dao.update(xmlPath + "update_payplan", map);

		try{
			//车辆注销接口
			CarRegistAPI.vehDel(PAY_ID);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//自动核销(baseInfo:基本信息----CUST_ID：客户编号)   TYPE=1-保证金抵扣，TYPE=2-来款核销
	public void fundRentByFundID(String fundId,String RENT_DATE,Map settleInfo,Map baseInfo,String TYPE){
		//核销单金额
		double money_head_all = 0d;
		//核销单包括的项目数
		int FI_PROJECT_NUM = 0;
		String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
		Map<String, Object> param = new HashMap<>();
		param.put("FUND_HEAD_ID", fund_head_id);
		param.put("FUND_ID", fundId);
		Map<String, Object> src = Dao.selectOne("fi.fund.getFundAll", fundId);
		src.put("FUND_ACCEPT_DATE1", RENT_DATE);

		double FUND_RECEIVE_MONEY = 0d;//来款金额
		if(src.get("FUND_RECEIVE_MONEY") != null && !src.get("FUND_RECEIVE_MONEY").equals("")){
			FUND_RECEIVE_MONEY = Double.parseDouble(src.get("FUND_RECEIVE_MONEY")+"");
		}

		PayTaskService payTask = new PayTaskService();
		settleInfo.put("RENT_DATE", RENT_DATE);
		List<Map<String, Object>> list = payTask.queryJQDate(settleInfo);//待核销项
		for (Map<String, Object> map : list) {
			if (map != null) {
				map.put("PAYLIST_CODE", settleInfo.get("PAYLIST_CODE"));
				double rent_money = 0d;//核销金额
				String ITEM_FLAG = map.get("ITEM_FLAG") + "";//2.租金，7.违约金，其他为首期款
				if (map.get("RENT_MONEY") != null && !map.get("RENT_MONEY").equals("")) {
					rent_money = Double.parseDouble(map.get("RENT_MONEY") + "");
				}
				Map<String, Object> detailMap = new HashMap<>();
				detailMap.put("fund_head_id", fund_head_id);
				detailMap.put("CUSTNAME", src.get("FUND_COMENAME"));
				detailMap.put("CUST_ID", baseInfo.get("CUST_ID"));
				detailMap.put("CUSTNAME", settleInfo.get("CUST_NAME"));
				detailMap.put("BEGINNING_RECEIVE_DATA", map.get("RENT_DATE"));//应收日期
				detailMap.put("PAYLIST_CODE", settleInfo.get("PAYLIST_CODE"));
				detailMap.put("PRO_CODE", settleInfo.get("PRO_CODE"));
				detailMap.put("BEGINNING_NUM", map.get("BEGINNING_NUM"));
				detailMap.put("FUND_ACCEPT_DATE", RENT_DATE);

				if (ITEM_FLAG.equals("2")) {
					List<Map<String, Object>> listDetail = Dao.selectList("rentWriteNew.queryDetailByPayNum", map);
					for (Map<String, Object> detaMap : listDetail) {
						//先扣利息，在扣本金
						if (FUND_RECEIVE_MONEY > 0) {
							FI_PROJECT_NUM++;
							double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
							detailMap.put("BEGINNING_ID", detaMap.get("BEGINNING_ID"));
							detailMap.put("BEGINNING_NAME", "利息");
							if (FUND_RECEIVE_MONEY <= moneyLixi) {//不足额
								detailMap.put("D_PAY_MONEYLB", moneyLixi);
								detailMap.put("MONEYCURR", FUND_RECEIVE_MONEY);
								detailMap.put("D_STATUS", 1);
								Dao.insert("rentWriteNew.createDetailByBankDate", detailMap);
								money_head_all = money_head_all + FUND_RECEIVE_MONEY;
								FUND_RECEIVE_MONEY = 0;
							} else {
								detailMap.put("D_PAY_MONEYLB", moneyLixi);
								detailMap.put("MONEYCURR", moneyLixi);
								detailMap.put("D_STATUS", 1);
								Dao.insert("rentWriteNew.createDetailByBankDate", detailMap);
								money_head_all = money_head_all + moneyLixi;
								FUND_RECEIVE_MONEY = FUND_RECEIVE_MONEY - moneyLixi;
							}
						}
					}
				} else if (ITEM_FLAG.equals("7")) {
					if (FUND_RECEIVE_MONEY > 0) {
						FI_PROJECT_NUM++;
						detailMap.put("BEGINNING_ID", map.get("BEGINNING_ID"));
						if (FUND_RECEIVE_MONEY >= rent_money) {//足够核销
							detailMap.put("BEGINNING_NAME", "违约金");
							detailMap.put("MONEYCURR", rent_money);//核销金额
							detailMap.put("D_PAY_MONEYLB", map.get("RENT_MONEY"));//应收金额
							money_head_all = money_head_all + rent_money;
							FUND_RECEIVE_MONEY = FUND_RECEIVE_MONEY - rent_money;
						} else {//不足够核销
							detailMap.put("BEGINNING_NAME", "违约金");
							detailMap.put("MONEYCURR", FUND_RECEIVE_MONEY);//核销金额
							detailMap.put("D_PAY_MONEYLB", map.get("RENT_MONEY"));//应收金额
							money_head_all = money_head_all + FUND_RECEIVE_MONEY;
							FUND_RECEIVE_MONEY = 0;
						}
						detailMap.put("D_STATUS", 2);
						Dao.insert("rentWriteNew.createDetailByBankDate", detailMap);
					}
				} else {//首期款
					if (FUND_RECEIVE_MONEY > 0) {
						FI_PROJECT_NUM++;
						detailMap.put("BEGINNING_ID", map.get("BEGINNING_ID"));
						if (FUND_RECEIVE_MONEY >= rent_money) {//足够核销
							detailMap.put("BEGINNING_NAME", map.get("BEGINNING_NAME"));
							detailMap.put("MONEYCURR", rent_money);//核销金额
							detailMap.put("D_PAY_MONEYLB", map.get("RENT_MONEY"));//应收金额
							money_head_all = money_head_all + rent_money;
							FUND_RECEIVE_MONEY = FUND_RECEIVE_MONEY - rent_money;
						} else {//不足够核销
							detailMap.put("BEGINNING_NAME", map.get("BEGINNING_NAME"));
							detailMap.put("MONEYCURR", FUND_RECEIVE_MONEY);//核销金额
							detailMap.put("D_PAY_MONEYLB", map.get("RENT_MONEY"));//应收金额
							money_head_all = money_head_all + FUND_RECEIVE_MONEY;
							FUND_RECEIVE_MONEY = 0;
						}
						detailMap.put("D_STATUS", 0);
						Dao.insert("rentWriteNew.createDetailByBankDate", detailMap);
					}
				}
			}
		}
		if(money_head_all > 0){
			String FI_FAG;//非网银
//			String FI_FAG = "12";
			//保存核销单主表
			if(TYPE.equals("1")){
				//保证金抵扣
				FI_FAG = "7";
			}
			else{
				FI_FAG = "3";
			}
			param.put("FI_FAG",FI_FAG);
			param.put("USER_ID", Security.getUser().getId());
			// 组织机构应该取缓存 后面在改
			param.put("ORG_ID", Security.getUser().getOrg().getId());

			param.put("APP_CREATE", "租金科");
			System.out.println("----------------money_head_all="+money_head_all);
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

//			System.out.println("----------------------param="+param);
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
		String fund_head_id = FUND_HEAD_ID;
		//只需要参数核销单ID(fund_head_id)
		Map<String, Object> FUND_HEAD_MAP = Dao.selectOne("rentWriteNew.queryFundHeadByHeadID", fund_head_id);
		FUND_HEAD_MAP.put("USER_CODE", Security.getUser().getCode());
		FUND_HEAD_MAP.put("USER_NAME", Security.getUser().getName());
		//修改核销单状态
		Dao.update("rentWriteNew.doUpdateHeadStatusNew", FUND_HEAD_MAP);

		String fundId=FUND_HEAD_MAP.get("FUND_ID") + "";

		// TODO 改变来款状态为已分解（新增一条待分解）

		Map<String, Object> fund = new HashMap<>();
		fund.put("FUND_ID", fundId);
		fund.put("FUND_STATUS", "4");

		fund = Dao.selectOne("fi.fund.getFundAll", fundId);
		fund.put("FUND_ACCEPT_DATE1", RENT_DATE);
		double FUND_RECEIVE_MONEY = Double.parseDouble(fund.get("FUND_RECEIVE_MONEY") + "");//原始金额
		double HX_MONEY = Double.parseDouble(FUND_HEAD_MAP.get("FI_PAY_MONEY") + "");//核销金额
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

		//处理核销的数据
		String FI_ACCOUNT_DATE = RENT_DATE;//来款日期
		Map<String, Object> baseData = new HashMap<>();
		baseData.put("FUND_ID", fund_head_id);
		// 根据申请单id查看应收金额和实收总金额
		List<Map<String, Object>> detialL = Dao.selectList("rentWriteNew.queryDetailByFundId", baseData);
		// 当不为空时跟新应收明细
		if (detialL != null) {
			for (Map<String, Object> realMoney : detialL) {
				String D_STATUS = (realMoney != null && realMoney.get("D_STATUS") != null && !realMoney.get("D_STATUS").equals("")) ? realMoney.get("D_STATUS").toString() : "";
				assert realMoney != null;
				if (D_STATUS.length() > 0 && D_STATUS.equals("1")) {//租金
					Dao.update("rentWriteNew.updateBegin", realMoney);
					Map<String, Object> mapMo = Dao.selectOne("rentWriteNew.selectmony", realMoney);
					realMoney.put("TIME", FI_ACCOUNT_DATE);
					if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
						Dao.update("rentWriteNew.updateBeginState1", realMoney);
					}
					Dao.update("rentWriteNew.updateRENT_PAIDDueNew", realMoney);
					Dao.update("rentWriteNew.updateDetailRe_StatusNew", realMoney);
					realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
				} else if (D_STATUS.length() > 0 && D_STATUS.equals("2")) {//违约金
					Map<String, Object> mapDunDate = new HashMap<>();
					mapDunDate.put("DUE_STATUS", 1);
					mapDunDate.put("OVERDUE_STATUS", 0);
					mapDunDate.put("PAYLIST_CODE", realMoney.get("D_PAY_CODE"));
					mapDunDate.put("BEGINNING_NUM", realMoney.get("PERIOD"));
					mapDunDate.put("REALITY_TIME", FI_ACCOUNT_DATE);
					mapDunDate.put("D_RECEIVE_MONEY", realMoney.get("D_RECEIVE_MONEY"));
					Map dunMap = Dao.selectOne("rentWriteNew.fi_overDueOne", mapDunDate);
					if (dunMap != null) {
						mapDunDate.put("DUNID", dunMap.get("DUNID"));
						Dao.update("rentWriteNew.updateOverDunStaute11", mapDunDate);
						Dao.delete("rentWriteNew.deleteDunDateAll11", mapDunDate);
					}
				} else if (D_STATUS.length() > 0 && D_STATUS.equals("0")) {//首期款
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
		}
		//如果核销的数据为委托租赁的租金，并且该期核销完成，像放款表中插入一条数据
		//如果核销的数据为联合租赁并未代收代付的租金，并且该期核销完成，像代付其他融资公司放款表中插入一条数据
		this.insertPayMentNum(FUND_HEAD_ID);
	}

	//当核销完成后根据核销单查询出该核销单下的支付表的已还期次及未还金额反更入支付表主表
	public void queryPaylistHG(String FUND_ID){
		List<Map<String, Object>> list=Dao.selectList(xmlPath+"queryPaylist_codeByFundID",FUND_ID);
		for (Map<String, Object> map : list) {
			if (map != null && map.get("PAYLIST_CODE") != null && !map.get("PAYLIST_CODE").equals("")) {
				Dao.update(xmlPath + "updatePayHeadInfoByPaylistCode", map);
			}
		}
	}

	//如果核销的数据为委托租赁的租金，并且该期核销完成，像放款表中插入一条数据
	//如果核销的数据为联合租赁并未代收代付的租金，并且该期核销完成，像代付其他融资公司放款表中插入一条数据
	public void insertPayMentNum(String FUND_ID){
		//查询出该核销单下为委托租赁的核销细项
		List<Map<String, Object>> list = Dao.selectList("rentWriteNew.queryPaylist_codeNumZJWT", FUND_ID);
		for (Map<String, Object> map : list) {
			String PLATFORM_TYPE = (map.get("PLATFORM_TYPE") != null && !map.get("PLATFORM_TYPE").equals("")) ? map.get("PLATFORM_TYPE").toString() : "8";
			//对为委托租赁的核销细项判断该期是否核销完全，如果核销完全写入放款表，提供放款，如果没有核销完成则等待核销完成后写入放款
			//对为联合租赁的核销细项判断该期是否核销完全，如果核销完全写入联合租赁放款表，提供放款，如果没有核销完成则等待核销完成后写入放款
			//判断有核销完成没有
			Map<String, Object> payListMap = Dao.selectOne("rentWriteNew.queryDunMoneyStatus", map);
			if (payListMap != null && payListMap.get("SHENYUMONEY") != null && !payListMap.get("SHENYUMONEY").equals("")) {

				if (PLATFORM_TYPE.equals("7")) {//委托租赁
					//查询该支付表是否存在虚拟买卖合同，如果存在取到买卖合同放进放款表
					//如果没有买卖合同则新建一个虚拟的买卖合同（虚拟买卖合同：委托租赁不需要买卖合同，但是为了业务的关联性所以虚拟建立的。不用建立买卖合同号）
					Map buyConMap = Dao.selectOne("rentWriteNew.getBuyConByPayCode", payListMap);
					if (buyConMap != null) {
						//将一些信息写入放款表
						payListMap.put("BUY_CONTRACT_ID", buyConMap.get("ID"));
						payListMap.put("PAY_BANK_ACCOUNT", buyConMap.get("SELLER_ACCOUNT"));
						payListMap.put("PAYEE_NAME", buyConMap.get("SELLER_UNIT_NAME"));
						payListMap.put("PAY_BANK_NAME", buyConMap.get("SELLER_BANK_ACCO"));
						payListMap.put("PAY_BANK_ADDRESS", buyConMap.get("SELLER_PAY_BANK_ADDRESS"));
					} else {
						//新建虚拟买卖合同
						String BUY_CONTRACT_ID = Dao.getSequence("SEQ_FIL_BUY_CONTRACT");
						payListMap.put("BUY_CONTRACT_ID", BUY_CONTRACT_ID);//获取买卖合同号关联到放款表
						payListMap.put("PAYEE_NAME", payListMap.get("SUP_NAME"));//获取出卖人存进放款表
						Map<String, Object> buyMap = new HashMap<>();
						buyMap.put("ID", BUY_CONTRACT_ID);
						buyMap.put("PAYLIST_CODE", payListMap.get("PAYLIST_CODE"));
						Dao.insert("rentWriteNew.insertBuyCon", buyMap);
					}
				}

				double SHENYUMONEY = Double.parseDouble(payListMap.get("SHENYUMONEY").toString());
				if (SHENYUMONEY <= 0.0001)// 标示这一期都核销完了，写进放款
				{
					payListMap.put("USERID", Security.getUser().getId());
					if (PLATFORM_TYPE.equals("7")) {//委托租赁
						//插入放款表
						Dao.insert("rentWriteNew.insertFundPaymentPl7", payListMap);
					} else if (PLATFORM_TYPE.equals("8")) {//联合租赁并且为代收代付
						//查询fil_project_fl_join表中其他融资公司在fi_fund_payment_fl表中有数据吗，如果有不插入，如果没有插入
						//循环遍历fil_project_fl_join表
						List<Map<String, Object>> FL_LIST = Dao.selectList("rentWriteNew.queryFLJoinList", payListMap);
						for (Map<String, Object> flMap : FL_LIST) {
							if (flMap != null) {
								flMap.put("PAYLIST_CODE", payListMap.get("PAYLIST_CODE"));
								flMap.put("BEGINNING_NUM", payListMap.get("BEGINNING_NUM"));
								//查询在fi_fund_payment_fl表中有数据吗？有则相安无事，无则XXX
								int payNum = Dao.selectInt("rentWriteNew.queryPayMentFlByPayList", flMap);
								if (payNum == 0) {
									//插入
									flMap.put("MONEYNEW", payListMap.get("MONEYNEW"));
									flMap.put("BEGINNING_RECEIVE_DATA", payListMap.get("BEGINNING_RECEIVE_DATA"));
									flMap.put("USERID", Security.getUser().getId());
									Dao.insert("rentWriteNew.insertFundPaymentFL8", flMap);
								}
							}
						}
					}
				}
			}
		}
	}


	//1.项目回购驳回(需要参数PAY_ID,PAYLIST_CODE)
	public void xmhgNoPass(String PAY_ID,String PAYLIST_CODE){
		//删除支付表头表和细表
		Dao.delete(xmlPath + "deleteDetailByPayID", PAY_ID);
		Dao.delete(xmlPath + "deleteHeadByPayID", PAY_ID);

		//删除提前结清记录表
		Dao.delete(xmlPath+"deleteParamByPayID", PAY_ID);
		//删除今天罚息外数据
		Dao.delete(xmlPath+"deleteOverDueByPayID", PAYLIST_CODE);

		Map<String, Object> map = new HashMap<>();
		map.put("PAYLIST_CODE", PAYLIST_CODE);
		Dao.update("PayTask.upDateToJoin", map);
	}

	//2.项目回购不可逆通过(需要参数PAY_ID,PAYLIST_CODE)
	//TODO --缺少减少抵扣部分保证金的处理
	public void xmhgPass(String PAY_ID,String PAYLIST_CODE){
		//同步期初表数据
		//1.同步租金
		Map mapScheme;
		mapScheme = this.doShowHGByPayId(PAY_ID);
		if(mapScheme!=null && mapScheme.get("JQ_PERIOD") != null && !mapScheme.get("JQ_PERIOD").equals("")){
			String PERIOD=mapScheme.get("JQ_PERIOD") + "";
			Map<String, Object> mapDate = new HashMap<>();
			mapDate.put("PAY_ID", PAY_ID);
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("PERIOD", PERIOD);
			Dao.update(xmlPath+"updateBeginningByDetail", mapDate);
		}
		//2.同步其他费用
		if(mapScheme != null && mapScheme.get("RENT_DATE") != null && !mapScheme.get("RENT_DATE").equals("")){

			Map<String, Object> mapDate = new HashMap<>();
			mapDate.put("PAY_ID", PAY_ID);
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("RENT_DATE", mapScheme.get("RENT_DATE"));
			mapDate.put("CREATE_ID", Security.getUser().getId());
			Dao.update(xmlPath + "insertItemOtherHG", mapDate);
			Dao.update(xmlPath + "updateFlagTypeByPayID", mapDate);
		}

		String ACCOUNT_DATE = "";
		assert mapScheme != null;
		if(mapScheme.get("ACCOUNT_DATE") != null && !mapScheme.get("ACCOUNT_DATE").equals("")){
			ACCOUNT_DATE = mapScheme.get("ACCOUNT_DATE") + "";
		}

		String FUND_ID_BZJ = Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE_BZJ = Dao.selectOne("fi.fund.getFundCode");

		//如果保证金抵扣则生成一笔保证金来款
		if(mapScheme.get("BZJDk") != null && !mapScheme.get("BZJDk").equals("")){
			double BZJDk = Double.parseDouble(mapScheme.get("BZJDk") + "");
			if(BZJDk > 0){
				Map<String, Object> mapDate = new HashMap<>();
				mapDate.put("PAY_ID", PAY_ID);
				mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
				mapDate.put("FUND_ACCEPT_DATE", ACCOUNT_DATE);//来款时间
				mapDate.put("FUND_COMENAME", mapScheme.get("CUST_NAME"));//来款人
				mapDate.put("FUND_RECEIVE_MONEY", mapScheme.get("BZJDk"));//来款金额
				mapDate.put("FUND_SY_MONEY", mapScheme.get("BZJDk"));//来款金额
				mapDate.put("FUND_DOCKET", "项目回购客户保证金抵扣，系统自动插入！");//来款摘要
				mapDate.put("FUND_ID", FUND_ID_BZJ);
				mapDate.put("FUND_FUNDCODE", FUND_CODE_BZJ);
				mapDate.put("FUND_NOTDECO_STATE", "7");
				mapDate.put("FUND_STATUS", "0");
				mapDate.put("FUND_ISCONGEAL", "0");
				mapDate.put("FUND_RED_STATUS", "0");
				mapDate.put("FUND_PRANT_ID", "0");
				mapDate.put("FUND_IMPORT_PERSON", Security.getUser().getName());
				mapDate.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
				mapDate.put("FUND_CLIENT_ID", mapScheme.get("CUST_ID"));//认款
				mapDate.put("FUND_CLIENT_NAME", mapScheme.get("CUST_NAME"));
				mapDate.put("FUND_PAY_CODE", PAYLIST_CODE);//将要分解到那张支付表
				mapDate.put("FUND_TYPE", 2);
				Dao.insert("fi.fund.add", mapDate);
			}
		}
		String FUND_ID = Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE = Dao.selectOne("fi.fund.getFundCode");

		//如果DB保证金抵扣则生成一笔DB保证金来款
		if(mapScheme.get("DBBZJDK") != null && !mapScheme.get("DBBZJDK").equals("")){
			double DBBZJDK = Double.parseDouble(mapScheme.get("DBBZJDK") + "");
			if(DBBZJDK>0){
				Map<String, Object> mapDate = new HashMap<>();
				mapDate.put("PAY_ID", PAY_ID);
				mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
				mapDate.put("FUND_ACCEPT_DATE", ACCOUNT_DATE);//来款时间
				mapDate.put("FUND_COMENAME", mapScheme.get("SUP_NAME"));//来款人
				mapDate.put("FUND_RECEIVE_MONEY", mapScheme.get("DBBZJDK"));//来款金额
				mapDate.put("FUND_DOCKET", "项目回购供应商保证金抵扣，系统自动插入！");//来款摘要
				mapDate.put("FUND_ID", FUND_ID);
				mapDate.put("FUND_FUNDCODE", FUND_CODE);
				mapDate.put("FUND_NOTDECO_STATE", "7");
				mapDate.put("FUND_STATUS", "0");
				mapDate.put("FUND_ISCONGEAL", "0");
				mapDate.put("FUND_RED_STATUS", "0");
				mapDate.put("FUND_PRANT_ID", "0");
				mapDate.put("FUND_IMPORT_PERSON", Security.getUser().getName());
				mapDate.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
				mapDate.put("FUND_CLIENT_ID", mapScheme.get("CUST_ID"));//认款
				mapDate.put("FUND_CLIENT_NAME", mapScheme.get("CUST_NAME"));
				mapDate.put("FUND_PAY_CODE", PAYLIST_CODE);//将要分解到那张支付表
				mapDate.put("FUND_TYPE", 2);
				Dao.insert("fi.fund.add", mapDate);
			}

		}
		//根据PAYLIST_CODE查询资金池信息
		if(mapScheme.get("BZJDk") != null && !mapScheme.get("BZJDk").equals("")){
			double BZJDk = Double.parseDouble(mapScheme.get("BZJDk") + "");
			if(BZJDk > 0){
				Map<String, Object> mapPay = new HashMap<>();
				mapPay.put("PAYLIST_CODE", PAYLIST_CODE);
				Map<String, Object> poolMap = Dao.selectOne("cashDeposit.queryBaseInfoByPayListCode",mapPay);
				if(poolMap !=null){
					//写入子表
					Map<String, Object> poolDeatil = new HashMap<>();
					poolDeatil.put("POOL_ID", poolMap.get("POOL_ID"));
					poolDeatil.put("FUND_ID", FUND_ID_BZJ);
					poolDeatil.put("FUND_CODE", FUND_CODE_BZJ);
					poolDeatil.put("TYPE", 1);
					poolDeatil.put("USERMONEY", mapScheme.get("BZJDk"));
					Dao.insert("cashDeposit.insertPoolDetail", poolDeatil);

					//修改主表可用余额
					Dao.update("cashDeposit.updatePoolMoneyInfoByPoolId", poolDeatil);
				}
			}

			//现系统保证金余额的处理
			if(BZJDk > 0){
				double bzj_sy_money = Dao.selectDouble("rentWriteNew.queryDeposit_SyByPayList_code", PAYLIST_CODE);
				//添加记录明细
				Map<String, Object> bzjJl = new HashMap<>();
				bzjJl.put("PAYLIST_CODE", PAYLIST_CODE);
				bzjJl.put("BEGIN_BZJ", bzj_sy_money);
				bzjJl.put("SY_BZJ", bzj_sy_money - BZJDk);
				bzjJl.put("REMARK", "项目回购，保证金抵扣，使用金额"+BZJDk);
				bzjJl.put("OPERATOR_MAN", Security.getUser().getName());
				bzjJl.put("TYPE", "抵扣");
				bzjJl.put("FUND_ID", FUND_ID_BZJ);
				bzjJl.put("FUND_CODE", FUND_CODE_BZJ);
				Dao.insert("rentWriteNew.insertBzjJlInfo",bzjJl);

				//修改还款机会表主表剩余金额
				Map<String, Object> mapBzjYe = new HashMap<>();
				mapBzjYe.put("PAY_ID", PAY_ID);
				mapBzjYe.put("USERMONEY", BZJDk);
				Dao.update("rentWriteNew.updateBzjYEByPaylistCode",mapBzjYe);

			}
		}

		//根据PAYLIST_CODE查询资金池信息
		if(mapScheme.get("DBBZJDK") != null && !mapScheme.get("DBBZJDK").equals("")){
			double DBBZJDK = Double.parseDouble(mapScheme.get("DBBZJDK") + "");
			if(DBBZJDK > 0){
				Map<String, Object> mapPay = new HashMap<>();
				mapPay.put("PAYLIST_CODE", PAYLIST_CODE);
				Map<String, Object> poolMap = Dao.selectOne("cashDeposit.queryBaseInfoByPayListCodeDB",mapPay);
				if(poolMap !=null){
					//写入子表
					Map<String, Object> poolDeatil = new HashMap<>();
					poolDeatil.put("POOL_ID", poolMap.get("POOL_ID"));
					poolDeatil.put("FUND_ID", FUND_ID);
					poolDeatil.put("FUND_CODE", FUND_CODE);
					poolDeatil.put("TYPE", 1);
					poolDeatil.put("USERMONEY", mapScheme.get("DBBZJDK"));
					Dao.insert("cashDeposit.insertPoolDetail",poolDeatil);

					//修改主表可用余额
					Dao.update("cashDeposit.updatePoolMoneyInfoByPoolId",poolDeatil);
				}
			}
		}
	}

	public Map<String, Object> doShowHGByPayId(String PAY_ID) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isBlank(PAY_ID))
			return map;
		map.put("PAY_ID", PAY_ID);
		map.put("PARAM_NAME", "项目回购");
		Map mapDate=Dao.selectOne("PayTask.doShowJQByPayId", map);
		//noinspection unchecked
		return JSONObject.fromObject(mapDate.get("PARAM_VALUE"));
	}

	public Map<String, Object> doShowPayChangerByPayId(String PAY_ID) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isBlank(PAY_ID))
			return map;
		map.put("PAY_ID", PAY_ID);
		map.put("PARAM_NAME", "支付表变更");
		Map mapDate=Dao.selectOne("PayTask.doShowJQByPayId", map);
		//noinspection unchecked
		return JSONObject.fromObject(mapDate.get("PARAM_VALUE"));
	}


	//4.项目回购结束(需要参数PAY_ID)
	public void xmhgEnd(String PAY_ID){
		Map<String, Object> map = new HashMap<>();
		map.put("STATUS", "5");
		map.put("ID", PAY_ID);

		//修改支付表结束时间
		Map<String, Object> settleInfo = this.doShowHGByPayId(PAY_ID);
		String ACCOUNT_DATE = settleInfo.get("ACCOUNT_DATE") + "";//结清时间
		map.put("END_DATE", ACCOUNT_DATE);
		Dao.update(xmlPath + "update_payplan", map);

		try{
			//车辆注销接口
			CarRegistAPI.vehDel(PAY_ID);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	//3.项目回购自动核销(需要参数PAY_ID,PAYLIST_CODE)
	public void xmhgFundRent(String PAY_ID, String PAYLIST_CODE){
		//提前结清保存项
		Map<String, Object> settleInfo = this.doShowHGByPayId(PAY_ID);
		String ACCOUNT_DATE = settleInfo.get("ACCOUNT_DATE")+"";//结清时间
		double TOTAL_MONEY = 0d;
		if(settleInfo.get("TOTAL_MONEY") != null && !settleInfo.get("TOTAL_MONEY").equals("")){
			TOTAL_MONEY = Double.parseDouble(settleInfo.get("TOTAL_MONEY") + "");//需要金额
		}

		//查询一些基本信息
		Map baseInfo = Dao.selectOne(xmlPath+"queryBaseInfoByInfo",settleInfo);
		//先判断来款够不够核销，如果不够不让通过提示请上传来款
		double FUND_RECEIVE_MONEY = Dao.selectDouble(xmlPath+"queryFundMoneyByCustID", settleInfo);//来款金额
		if(FUND_RECEIVE_MONEY < TOTAL_MONEY){//来款金额不足以支付提前结清所收费用
			throw new ActionException("来款不足以支付回购所需费用，请补充来款在进行操作！");
		}
		else{
			//如果来款够核销，则需要先冻结来款
			Dao.update(xmlPath + "updateDJFundMoneyByCustID", settleInfo);
			//核销顺序从抵扣先用在使用待分解最后使用来款
			//1.客户保证金或者DB保证金抵扣金额(判断有抵扣金额吗，有的话做抵扣)
			List<Map<String, Object>> listDikou = Dao.selectList(xmlPath + "queryFundByCustIdDikouList", settleInfo);
			for (Map<String, Object> mapDikou : listDikou) {
				if (mapDikou != null) {
					this.fundRentByFundID(mapDikou.get("FUND_ID") + "", ACCOUNT_DATE, settleInfo, baseInfo, "1");
				}
			}


			//3.待分解及来款
			List<Map<String, Object>> list = Dao.selectList(xmlPath+"queryFundByCustId", settleInfo);
			for (Map<String, Object> fundMap : list) {
				if (fundMap != null && fundMap.get("FUND_RECEIVE_MONEY") != null && !fundMap.get("FUND_RECEIVE_MONEY").equals("")) {
					double FUND_COM_MONEY = Double.parseDouble(fundMap.get("FUND_RECEIVE_MONEY") + "");
					if (FUND_COM_MONEY > 0) {
						this.fundRentByFundID(fundMap.get("FUND_ID") + "", ACCOUNT_DATE, settleInfo, baseInfo, "2");

					}
				}
			}
			//核销完成后对冻结的来款解冻
			Dao.update(xmlPath+"updateJDFundMoneyByCustID",settleInfo);
		}
	}

	//支付表变更流程审批类

	//1.支付表变更驳回(需要参数PAY_ID,PAYLIST_CODE)
	public void payChangeNoPass(String PAYLIST_CODE){
		//查询PAY_ID
		Map<String, Object> param = new HashMap<>();
		param.put("PAYLIST_CODE", PAYLIST_CODE);
		Map<String, Object> mapPay = Dao.selectOne("PayTask.changeToShowManPay", param);
		String PAY_ID = mapPay.get("PAY_ID") + "";
		//删除支付表头表和细表
		Dao.delete(xmlPath+"deleteDetailByPayID", PAY_ID);
		Dao.delete(xmlPath+"deleteHeadByPayID", PAY_ID);

		//删除提前结清记录表
		Dao.delete(xmlPath+"deleteParamByPayID", PAY_ID);

		Map<String, Object> map = new HashMap<>();
		map.put("PAYLIST_CODE", PAYLIST_CODE);
		Dao.update("PayTask.upDateToJoin",map);
	}

	//2.支付表变更通过(需要参数PAY_ID,PAYLIST_CODE)
	public void payChangePass(String PAYLIST_CODE){
		//查询PAY_ID
		Map<String, Object> param = new HashMap<>();
		param.put("PAYLIST_CODE", PAYLIST_CODE);
		Map<String, Object> mapPay = Dao.selectOne("PayTask.changeToShowManPay", param);
		String PAY_ID=mapPay.get("PAY_ID") + "";
		//同步期初表数据
		//1.同步租金
		Map mapScheme = this.doShowPayChangerByPayId(PAY_ID);
		if(mapScheme != null && mapScheme.get("changeIssue") != null && !mapScheme.get("changeIssue").equals("")){
			String PERIOD = mapScheme.get("changeIssue") + "";
			Map<String, Object> mapDate = new HashMap<>();
			mapDate.put("PAY_ID", PAY_ID);
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("PERIOD", PERIOD);
			Dao.update(xmlPath + "updateBeginningByDetail", mapDate);//修改利息
			Dao.update(xmlPath + "updateBeginningByDetailBJ", mapDate);//修改本金

		}
		//2.同步其他费用
		if(mapScheme != null && mapScheme.get("RENT_DATE") != null && !mapScheme.get("RENT_DATE").equals("")){
			Map<String, Object> mapDate = new HashMap<>();
			mapDate.put("PAY_ID", PAY_ID);
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("RENT_DATE", mapScheme.get("RENT_DATE"));
			mapDate.put("CREATE_ID", Security.getUser().getId());
			Dao.update(xmlPath+"insertItemOtherHG", mapDate);
			Dao.update(xmlPath+"updateFlagTypeByPayID", mapDate);
		}
		//修改状态

		Map<String, Object> map = new HashMap<>();
		map.put("STATUS", "0");
		map.put("ID", PAY_ID);
		Dao.update(xmlPath+"update_payplan", map);

		map.put("PAYLIST_CODE", PAYLIST_CODE);
		Dao.update("PayTask.upDateToJoin",map);

	}


	//1.正常结清驳回(需要参数PAY_ID,PAYLIST_CODE)
	public void normalJqNoPass(String PAY_ID,String PAYLIST_CODE){
		//删除支付表头表和细表
		Dao.delete(xmlPath + "deleteDetailByPayID", PAY_ID);
		Dao.delete(xmlPath + "deleteHeadByPayID", PAY_ID);

		//删除正常结清记录表
		Dao.delete(xmlPath + "deleteParamByPayID", PAY_ID);
		//删除今天罚息外数据
		Dao.delete(xmlPath + "deleteOverDueByPayID", PAYLIST_CODE);

		Map<String, Object> map = new HashMap<>();
		map.put("PAYLIST_CODE", PAYLIST_CODE);
		Dao.update("PayTask.upDateToJoin",map);
	}


	//2.正常结清不可逆通过(需要参数PAY_ID,PAYLIST_CODE)
	//TODO --缺少减少抵扣部分保证金的处理
	public void normalJqPass(String PAY_ID,String PAYLIST_CODE){
		//同步期初表数据
		//1.同步租金
		PayTaskService payTask = new PayTaskService();
		Map mapScheme = payTask.doShowNormalByPayId(PAY_ID);

		String FUND_ID = Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE = Dao.selectOne("fi.fund.getFundCode");
		//如果保证金抵扣则生成一笔保证金来款
		if(mapScheme != null && mapScheme.get("DEPOSIT") != null && !mapScheme.get("DEPOSIT").equals("")){
			double DEPOSIT = Double.parseDouble(mapScheme.get("DEPOSIT")+"");
			if(DEPOSIT > 0){
				String RENT_DATE = "";
				if(mapScheme.get("RENT_DATE") != null && !mapScheme.get("RENT_DATE").equals("")){
					RENT_DATE = mapScheme.get("RENT_DATE") + "";
				}
				Map<String, Object> mapDate = new HashMap<>();
				mapDate.put("PAY_ID", PAY_ID);
				mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
				mapDate.put("FUND_ACCEPT_DATE", RENT_DATE);//来款时间
				mapDate.put("FUND_COMENAME", mapScheme.get("CUST_NAME"));//来款时间
				mapDate.put("FUND_RECEIVE_MONEY", mapScheme.get("DEPOSIT"));//来款金额
				mapDate.put("FUND_SY_MONEY", mapScheme.get("DEPOSIT"));//来款金额
				mapDate.put("FUND_DOCKET", "正常结清客户保证金抵扣，系统自动插入！");//来款摘要
				mapDate.put("FUND_ID", FUND_ID);
				mapDate.put("FUND_FUNDCODE", FUND_CODE);
				mapDate.put("FUND_NOTDECO_STATE", "7");
				mapDate.put("FUND_STATUS", "0");
				mapDate.put("FUND_ISCONGEAL", "0");
				mapDate.put("FUND_RED_STATUS", "0");
				mapDate.put("FUND_PRANT_ID", "0");
				mapDate.put("FUND_IMPORT_PERSON", Security.getUser().getName());
				mapDate.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
				mapDate.put("FUND_CLIENT_ID", mapScheme.get("CUST_ID"));//认款
				mapDate.put("FUND_CLIENT_NAME", mapScheme.get("CUST_NAME"));
				mapDate.put("FUND_PAY_CODE", PAYLIST_CODE);//将要分解到那张支付表
				mapDate.put("FUND_TYPE", 2);
				Dao.insert("fi.fund.add", mapDate);
			}

		}
		if(mapScheme != null && mapScheme.get("DEPOSIT") != null && !mapScheme.get("DEPOSIT").equals("")){
			double DEPOSIT = Double.parseDouble(mapScheme.get("DEPOSIT") + "");
			if(DEPOSIT > 0){
				//根据PAYLIST_CODE查询资金池信息
				Map<String, Object> mapPay = new HashMap<>();
				mapPay.put("PAYLIST_CODE", PAYLIST_CODE);
				Map<String, Object> poolMap = Dao.selectOne("cashDeposit.queryBaseInfoByPayListCode", mapPay);
				if(poolMap !=null){
					//写入子表
					Map<String, Object> poolDeatil = new HashMap<>();
					poolDeatil.put("POOL_ID", poolMap.get("POOL_ID"));
					poolDeatil.put("FUND_ID", FUND_ID);
					poolDeatil.put("FUND_CODE", FUND_CODE);
					poolDeatil.put("TYPE", 1);
					poolDeatil.put("USERMONEY", mapScheme.get("DEPOSIT"));
					Dao.insert("cashDeposit.insertPoolDetail",poolDeatil);

					//修改主表可用余额
					Dao.update("cashDeposit.updatePoolMoneyInfoByPoolId",poolDeatil);
				}
			}

			//现系统保证金余额的处理
			if(DEPOSIT>0){
				double bzj_sy_money = Dao.selectDouble("rentWriteNew.queryDeposit_SyByPayList_code", PAYLIST_CODE);
				//添加记录明细
				Map<String, Object> bzjJl = new HashMap<>();
				bzjJl.put("PAYLIST_CODE", PAYLIST_CODE);
				bzjJl.put("BEGIN_BZJ", bzj_sy_money);
				bzjJl.put("SY_BZJ", bzj_sy_money - DEPOSIT);
				bzjJl.put("REMARK", "正常结清，保证金抵扣，使用金额"+DEPOSIT);
				bzjJl.put("OPERATOR_MAN", Security.getUser().getName());
				bzjJl.put("TYPE", "抵扣");
				bzjJl.put("FUND_ID", FUND_ID);
				bzjJl.put("FUND_CODE", FUND_CODE);
				Dao.insert("rentWriteNew.insertBzjJlInfo",bzjJl);

				//修改还款机会表主表剩余金额
				Map<String, Object> mapBzjYe = new HashMap<>();
				mapBzjYe.put("PAY_ID", PAY_ID);
				mapBzjYe.put("USERMONEY", DEPOSIT);
				Dao.update("rentWriteNew.updateBzjYEByPaylistCode",mapBzjYe);
			}
		}

	}


	//4.提前结清结束(需要参数PAY_ID)
	public void normalJqEnd(String PAY_ID){
		Map<String, Object> map = new HashMap<>();
		map.put("STATUS", "3");
		map.put("ID", PAY_ID);

		//修改支付表结束时间
		PayTaskService payTaskSer = new PayTaskService();
		Map<String, Object> settleInfo = payTaskSer.doShowNormalByPayId(PAY_ID);
		String ACCOUNT_DATE = settleInfo.get("RENT_DATE") + "";//结清时间
		map.put("END_DATE", ACCOUNT_DATE);
		Dao.update(xmlPath + "update_payplan", map);

		try{
			//车辆注销接口
			CarRegistAPI.vehDel(PAY_ID);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//3.正常结清自动核销(需要参数PAY_ID,PAYLIST_CODE)
	public void normalJqFundRent(String PAY_ID, String PAYLIST_CODE){
		PayTaskService payTaskSer = new PayTaskService();
		//正常结清保存项
		Map<String, Object> settleInfo = payTaskSer.doShowNormalByPayId(PAY_ID);
		String RENT_DATE = settleInfo.get("RENT_DATE") + "";//结清时间
		double TOTAL_MONEY = 0d;
		if(settleInfo.get("TOTAL_MONEY") != null && !settleInfo.get("TOTAL_MONEY").equals("")){
			TOTAL_MONEY = Double.parseDouble(settleInfo.get("TOTAL_MONEY") + "");//需要金额
		}

		//查询一些基本信息
		Map<String, Object> baseInfo = Dao.selectOne(xmlPath+"queryBaseInfoByInfo", settleInfo);
		//先判断来款够不够核销，如果不够不让通过提示请上传来款
		double FUND_RECEIVE_MONEY = Dao.selectDouble(xmlPath+"queryFundMoneyByCustID", settleInfo);//来款金额
		if(FUND_RECEIVE_MONEY < TOTAL_MONEY){//来款金额不足以支付正常结清所收费用
			throw new ActionException("来款不足以支付结清所需费用，请补充来款在进行操作！");
		}
		else{
			//如果来款够核销，则需要先冻结来款
			Dao.update(xmlPath + "updateDJFundMoneyByCustID", settleInfo);
			//核销顺序从抵扣先用在使用待分解最后使用来款
			//1.抵扣金额(判断有抵扣金额吗，有的话做抵扣)
			if(settleInfo.get("DEPOSIT") != null && !settleInfo.get("DEPOSIT").equals("")){
				double DEPOSIT = Double.parseDouble(settleInfo.get("DEPOSIT") + "");
				if(DEPOSIT > 0){
					Map mapDikou = Dao.selectOne(xmlPath+"queryFundByCustIdDikou", settleInfo);
					if(mapDikou != null){
						this.fundRentByFundID(mapDikou.get("FUND_ID") + "",RENT_DATE,settleInfo,baseInfo, "1");

					}
				}
			}
			//2.待分解及来款
			List<Map<String, Object>> list = Dao.selectList(xmlPath+"queryFundByCustId", settleInfo);
			for (Map<String, Object> fundMap : list) {
				if (fundMap != null && fundMap.get("FUND_RECEIVE_MONEY") != null && !fundMap.get("FUND_RECEIVE_MONEY").equals("")) {
					double FUND_COM_MONEY = Double.parseDouble(fundMap.get("FUND_RECEIVE_MONEY") + "");
					if (FUND_COM_MONEY > 0) {
						this.fundRentByFundID(fundMap.get("FUND_ID") + "", RENT_DATE, settleInfo, baseInfo, "2");
					}
				}
			}
			//核销完成后对冻结的来款解冻
			Dao.update(xmlPath+"updateJDFundMoneyByCustID",settleInfo);
		}

	}

	//提前结清罚息减免
	public void tqjqDunJm(String PAY_ID, String PAYLIST_CODE){
		PayTaskService payTask = new PayTaskService();
		Map<String, Object> settleInfo = payTask.doShowJQByPayId(PAY_ID);

		double PENALTY_RECE_JM = 0d;

		if(settleInfo != null && settleInfo.get("PENALTY_RECE_JM") != null && !settleInfo.get("PENALTY_RECE_JM").equals("")){
			PENALTY_RECE_JM = Double.parseDouble(settleInfo.get("PENALTY_RECE_JM") + "");
		}

		//有罚息减免
		if(PENALTY_RECE_JM > 0d){
			//查询所有罚息
			List<Map<String, Object>> dunList = Dao.selectList(xmlPath + "queryDunListByRentDate", settleInfo);
			for (Map<String, Object> dunMap : dunList) {
				double DUNMONEY = (dunMap != null && dunMap.get("DUNMONEY") != null && !dunMap.get("DUNMONEY").equals("")) ? Double.parseDouble(dunMap.get("DUNMONEY") + "") : 0d;
				assert dunMap != null;
				if (PENALTY_RECE_JM > DUNMONEY) {//如果该期减免掉
					//将逾期金额修改成0
					dunMap.put("MONEY", DUNMONEY);
					Dao.update(xmlPath + "updateDunByID", dunMap);
					PENALTY_RECE_JM = PENALTY_RECE_JM - DUNMONEY;
				} else {
					dunMap.put("MONEY", PENALTY_RECE_JM);
					Dao.update(xmlPath + "updateDunByID", dunMap);
					PENALTY_RECE_JM = 0;
				}

				if (PENALTY_RECE_JM <= 0) {
					break;
				}
			}
		}
	}

	//正常结清罚息减免
	public void zcjqDunJm(String PAY_ID, String PAYLIST_CODE){
		PayTaskService payTask = new PayTaskService();
		Map<String, Object> settleInfo = payTask.doShowNormalByPayId(PAY_ID);
		double PENALTY_RECE_JM = 0d;
		if(settleInfo != null && settleInfo.get("PENALTY_RECE_JM") != null && !settleInfo.get("PENALTY_RECE_JM").equals("")){
			PENALTY_RECE_JM = Double.parseDouble(settleInfo.get("PENALTY_RECE_JM") + "");
		}

		//有罚息减免
		if(PENALTY_RECE_JM > 0d){
			//查询所有罚息
			List<Map<String, Object>> dunList = Dao.selectList(xmlPath+"queryDunListByRentDate", settleInfo);
			for (Map<String, Object> dunMap : dunList) {
				double DUNMONEY = (dunMap != null && dunMap.get("DUNMONEY") != null && !dunMap.get("DUNMONEY").equals("")) ? Double.parseDouble(dunMap.get("DUNMONEY") + "") : 0d;
				assert dunMap != null;
				if (PENALTY_RECE_JM > DUNMONEY) {//如果该期减免掉
					//将逾期金额修改成0
					dunMap.put("MONEY", DUNMONEY);
					Dao.update(xmlPath + "updateDunByID", dunMap);
					PENALTY_RECE_JM = PENALTY_RECE_JM - DUNMONEY;
				} else {
					dunMap.put("MONEY", PENALTY_RECE_JM);
					Dao.update(xmlPath + "updateDunByID", dunMap);
					PENALTY_RECE_JM = 0;
				}
				if (PENALTY_RECE_JM <= 0) {
					break;
				}
			}
		}
	}

	//回购罚息减免
	public void xmhgDunJm(String PAY_ID, String PAYLIST_CODE){
		Map<String, Object> settleInfo = this.doShowHGByPayId(PAY_ID);
		double DUE_RECE_JM = 0d;
		if(settleInfo != null && settleInfo.get("DUE_RECE_JM") != null && !settleInfo.get("DUE_RECE_JM").equals("")){
			DUE_RECE_JM = Double.parseDouble(settleInfo.get("DUE_RECE_JM") + "");
		}

		//有罚息减免
		if(DUE_RECE_JM > 0d){
			//查询所有罚息
			List<Map<String, Object>> dunList = Dao.selectList(xmlPath + "queryDunListByRentDateHG", settleInfo);
			for (Map<String, Object> dunMap : dunList) {
				double DUNMONEY = (dunMap != null && dunMap.get("DUNMONEY") != null && !dunMap.get("DUNMONEY").equals("")) ? Double.parseDouble(dunMap.get("DUNMONEY") + "") : 0d;
				assert dunMap != null;
				if (DUE_RECE_JM > DUNMONEY) {//如果该期减免掉
					//将逾期金额修改成0
					dunMap.put("MONEY", DUNMONEY);
					Dao.update(xmlPath + "updateDunByID", dunMap);
					DUE_RECE_JM = DUE_RECE_JM - DUNMONEY;
				} else {
					dunMap.put("MONEY", DUE_RECE_JM);
					Dao.update(xmlPath + "updateDunByID", dunMap);
					DUE_RECE_JM = 0;
				}
				if (DUE_RECE_JM <= 0) {
					break;
				}
			}
		}
	}

	//当核销完成后根据核销单查询出该核销单有本金的所有支付表编号和期次判断是否为循环授信，如果是则资金回笼
	public void queryPaylistBJSX(String FUND_ID){
		List<Map<String, Object>> list = Dao.selectList(xmlPath + "queryPaylist_codeBJ", FUND_ID);
		for (Map<String, Object> mapDate : list) {
			if (mapDate != null && mapDate.get("PAYLIST_CODE") != null && !mapDate.get("PAYLIST_CODE").equals("")) {
				Dao.update(xmlPath + "updatSXBack", mapDate);
			}
		}
	}

	public void dunUpdateStatusFund(String FUND_ID,String FI_ACCOUNT_DATE){
		List<Map<String, Object>> list=Dao.selectList("rentWriteNew.queryPaylist_codeNumZJ",FUND_ID);
		for (Map<String, Object> map : list) {
			if (map != null) {
				map.put("TIME", FI_ACCOUNT_DATE);
				dunUpdateStatusNot(map);
			}
		}
	}

	// 不足额时候将核销日期以后的数据都删除掉（没有完全核销的时候），当完全核销的时候将后面的数据都修改成核销日期当天的数据和核销状态修改成可核销
	public void dunUpdateStatusNot(Map map) {
		//判断有核销完成没有
		Map<String, Object> payListMap = Dao.selectOne("rentWriteNew.queryDunMoneyStatus", map);
		if (payListMap != null && payListMap.get("SHENYUMONEY") != null && !payListMap.get("SHENYUMONEY").equals("")) {
			double SHENYUMONEY = Double.parseDouble(payListMap.get("SHENYUMONEY").toString());
			payListMap.put("TIME", map.get("TIME"));
			if (SHENYUMONEY <= 0.0001)// 标示这一期都核销完了，修改状态
			{
				// 1，修改实际到账日期以后的数据状态为可核销状态

				Dao.update("rentWriteNew.updateDunStatusNew1", payListMap);
				//如果应收违约金为null 删除掉
				Dao.delete("rentWriteNew.deleteDunWei",payListMap);
				//查询免息天数
				Map dunDay=Dao.selectOne("rentWriteNew.queryDunDayMX");
				if(dunDay !=null && dunDay.get("CODE") !=null && !dunDay.get("CODE").equals("")){
					payListMap.put("DUN_DAY_MX", dunDay.get("CODE"));
					Dao.delete("rentWriteNew.deleteDunDayMx",payListMap);
				}
			}
			else{//这一期没有核销完成，将有逾期数据的数据更新
				//删除核销日期之后的方法 重新刷数据
				Dao.delete("rentWriteNew.deleteDunDate",payListMap);
				//重新刷数据
				Map<String, Object> mm = new HashMap<>();
				mm.put("PAY_CODE", payListMap.get("PAYLIST_CODE"));
				Dao.update("fi.overdue.upDueOne",mm);
			}
		}
	}

	//当核销完成后根据核销单查询出所有支付表编号和期次
	public void queryPaylist_codeNum(String FUND_ID){
		List<Map<String, Object>> list = Dao.selectList("rentWriteNew.queryPaylist_codeNum",FUND_ID);
		for (Map<String, Object> mapDate : list) {
			if (mapDate != null && mapDate.get("BEGINNING_NUM") != null && !mapDate.get("BEGINNING_NUM").equals("")) {
				//刷新中间表数据
				Dao.update("rentWriteNew.doPRC_BE_QJL_ONE", mapDate);
//				Dao.delete(xmlPath+"deleteJoinDate",mapDate);
//				Dao.insert(xmlPath+"insertJoinDate",mapDate);
			} else if (mapDate != null) {
				Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE", mapDate);
			}
		}
	}
}

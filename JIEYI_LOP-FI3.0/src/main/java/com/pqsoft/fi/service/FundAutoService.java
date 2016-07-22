package com.pqsoft.fi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.Util;

public class FundAutoService {

	public boolean auto(Map<String, Object> param) {
		boolean flag=true;
		List<Map<String, Object>> fund = Dao.selectList("fi.fundauto.getFund", param);
		for (Map<String, Object> map : fund) {
			try {
				exec(map);
				Dao.commit();
				flag=true;
			} catch (Exception e) {
				flag=false;
				Dao.rollback();
				e.printStackTrace();
				
			}
		}
		
		return flag;
	}

	public void exec(Map<String, Object> param) throws Exception {
		double s = Double.parseDouble(param.get("FUND_RECEIVE_MONEY").toString());// 来款
		boolean flag = false;
		double as = s;
		List<Map<String, Object>> detail = Dao.selectList("fi.fundauto.getBeginJoin", param);
		String FUND_HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");
		param.put("FUND_HEAD_ID", FUND_HEAD_ID);
		for (Map<String, Object> map : detail) {
			if (flag) break;
			double skje = 0;
			String ITEM_FLAG= (map!=null && map.get("ITEM_FLAG")!=null && !map.get("ITEM_FLAG").equals(""))?map.get("ITEM_FLAG").toString():"";
			if (ITEM_FLAG.equals("5")) {//违约金
				 //罚息
				if (flag) break;
				
				Map<String, Object> d = new HashMap<String, Object>(param);
				double BEGINNING_MONEY = Double.parseDouble(map.get("BEGINNING_MONEY").toString());
				double BEGINNING_PAID = Double.parseDouble(map.get("BEGINNING_PAID").toString());
				double money = BEGINNING_MONEY - BEGINNING_PAID;
				
				d.put("BEGINNING_NAME", "违约金");
				d.put("D_PAY_MONEY", money);
				d.put("PAY_CODE", map.get("PAYLIST_CODE"));
				d.put("PROJECT_CODE", map.get("PRO_CODE"));
				d.put("BEGINNING_ID", map.get("BEGIN_ID"));
				d.putAll(map);
				
				if (s > money) {
					d.put("D_RECEIVE_MONEY", Util.formatDouble2(money));
					skje = skje + money;
					s = s - money;
				} else {
					d.put("D_RECEIVE_MONEY", Util.formatDouble2(s));
					skje = skje + s;
					flag = true;
					s = 0;
				}
				d.put("D_STATUS", 2);
				Dao.insert("fi.fundauto.addDetail", d);
				
				Map mapDunDate = new HashMap();
				mapDunDate.put("DUE_STATUS", 1);
				mapDunDate.put("OVERDUE_STATUS", 0);
				mapDunDate.put("PAYLIST_CODE", map.get("PAYLIST_CODE"));
				mapDunDate.put("BEGINNING_NUM", map.get("BEGINNING_NUM"));
				mapDunDate.put("REALITY_TIME", param.get("FUND_ACCEPT_DATE_CHAR"));
				mapDunDate.put("D_RECEIVE_MONEY", d.get("D_RECEIVE_MONEY"));
				Map dunMap=Dao.selectOne("rentWriteNew.fi_overDueOne",mapDunDate);
				if(dunMap!=null){
					mapDunDate.put("DUNID", dunMap.get("DUNID"));
					Dao.update("rentWriteNew.updateOverDunStaute11", mapDunDate);
					Dao.delete("rentWriteNew.deleteDunDateAll11", mapDunDate);
				}
			
			} else {//租金
//				String[] beginid = map.get("BEGIN_ID").toString().split("[,，；;]");
				
				List<Map<String,Object>> beginList=Dao.selectList("fi.fundauto.getBeginList",map);
				for(int be=0;be<beginList.size();be++){
					if (flag) break;
					Map<String, Object> begin=beginList.get(be);
					String string="";
					if(begin !=null && begin.get("BEGINNING_ID") !=null && !begin.get("BEGINNING_ID").equals("")){
						string=begin.get("BEGINNING_ID")+"";
					}
//				}
				
//				if (beginid == null) continue;
//				for (String string : beginid) {// 单条应收处理
//					if (flag) break;
					Map<String, Object> d = new HashMap<String, Object>(param);
//					Map<String, Object> begin = Dao.selectOne("fi.fundauto.getBeginOne", string);
					double BEGINNING_MONEY = Double.parseDouble(begin.get("BEGINNING_MONEY").toString());
					double BEGINNING_PAID = Double.parseDouble(begin.get("BEGINNING_PAID").toString());
					double money = BEGINNING_MONEY - BEGINNING_PAID;
					d.putAll(begin);
					d.put("D_PAY_MONEY", money);
					d.put("PAY_CODE", begin.get("PAYLIST_CODE"));
					if (s > money) {
						d.put("D_RECEIVE_MONEY", Util.formatDouble2(money));
						skje = skje + money;
						s = s - money;
					} else {
						d.put("D_RECEIVE_MONEY", Util.formatDouble2(s));
						skje = skje + s;
						flag = true;
						s = 0;
					}
					if ("2".equals(begin.get("ITEM_FLAG").toString())) {
						d.put("D_STATUS", 1);
					} else {
						d.put("D_STATUS", 0);
					}
					Dao.insert("fi.fundauto.addDetail", d);
					if (1 != Dao.update("fi.fundauto.upFiRB", d)) throw new Exception("实收收款金额超出应收");
				
				}
			}
			
			if(flag){
				break;
			}
		}
		if(detail.size()>0){
			
			{
				Map<String, Object> fundHead = new HashMap<String, Object>(param);
				fundHead.put("FI_FLAG", "3");
				fundHead.put("FI_PAY_MONEY", as-Util.formatDouble2(s));
				fundHead.put("FI_REALITY_MONEY", as-Util.formatDouble2(s));
				Dao.insert("fi.fundauto.addFundHead", fundHead);
			}
			param.put("FUND_SY_MONEY", s);
			if (1 != Dao.update("fi.fundauto.upFund", param)) throw new Exception("资金状态不属于未分解");
			if (Util.formatDouble2(s) > 0) {// 存在待分解
				Map<String, Object> dfj = new HashMap<String, Object>(param);
				dfj.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
				dfj.put("FUND_RECEIVE_MONEY", s);
				dfj.put("FUND_SY_MONEY", s);
				dfj.put("FUND_PRANT_ID", dfj.get("FUND_ID"));
				Dao.insert("fi.fundauto.addDfj", dfj);
			}
			
			rentWriteNewService rentService=new rentWriteNewService();
			//如果完全核销对违约金处理，并修改为可以核销
			//如果没有完成核销则将租金已收金额更新
			rentService.dunUpdateStatusFund(FUND_HEAD_ID,param.get("FUND_ACCEPT_DATE_CHAR").toString());
			
			//通过核销单刷中间表数据
			rentService.queryPaylist_codeNum(FUND_HEAD_ID);
			
			//查询该核销单下面所以本金判断是否为循环授信，是的话进行处理
			new rentWriteNewService().queryPaylistBJSX(FUND_HEAD_ID);
			
			//反更支付表主表的已还期次和未款金额
			new rentWriteNewService().queryPaylistHG(FUND_HEAD_ID);
			
		}
		
		
		
	}

	public void autoSQK(Map<String, Object> param) {
		List<Map<String, Object>> fund = Dao.selectList("fi.fundauto.getFundSQK", param);
		for (Map<String, Object> map : fund) {
			try {
				map.put("PAYLIST_CODE", param.get("PAYLIST_CODE"));
				if(param.containsKey("SQK")){
					execSQK(map);
				}else{
					if(param.containsKey("YSZJQC")){
						Map<String, Object> ysfund=Dao.selectOne("paymentApply.getparagraphNext",param);
						if(ysfund !=null){
							param.put("FUND_ACCEPT_DATE", ysfund.get("FUND_ACCEPT_DATE").toString());
							Dao.update("paymentApply.updSqkDate",param);
							Dao.update("paymentApply.updSqkDate2",param);
							Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE",param);
							new rentWriteNewService().tqHzByPeriodBefore(ysfund.get("FUND_ID").toString(),param.get("PAYLIST_CODE").toString(),Integer.parseInt(param.get("YSZJQC").toString()));
						}
					}
				}
				Dao.commit();
			} catch (Exception e) {
				Dao.rollback();
				e.printStackTrace();
			}
		}
	}
	
	public void execSQK(Map<String, Object> param) throws Exception {
		double s = Double.parseDouble(param.get("FUND_RECEIVE_MONEY").toString());// 来款
		boolean flag = false;
		double as = s;
		List<Map<String, Object>> detail = Dao.selectList("fi.fundauto.getBeginJoinSQK", param);
		String FUND_HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");
		param.put("FUND_HEAD_ID", FUND_HEAD_ID);
		for (Map<String, Object> map : detail) {
			if (flag) break;
			double skje = 0;
			String ITEM_FLAG= (map!=null && map.get("ITEM_FLAG")!=null && !map.get("ITEM_FLAG").equals(""))?map.get("ITEM_FLAG").toString():"";
			//首期款
				String[] beginid = map.get("BEGIN_ID").toString().split("[,，；;]");
				if (beginid == null) continue;
				for (String string : beginid) {// 单条应收处理
					if (flag) break;
					Map<String, Object> d = new HashMap<String, Object>(param);
					Map<String, Object> begin = Dao.selectOne("fi.fundauto.getBeginOne", string);
					double BEGINNING_MONEY = Double.parseDouble(begin.get("BEGINNING_MONEY").toString());
					double BEGINNING_PAID = Double.parseDouble(begin.get("BEGINNING_PAID").toString());
					double money = BEGINNING_MONEY - BEGINNING_PAID;
					d.putAll(begin);
					d.put("D_PAY_MONEY", money);
					d.put("PAY_CODE", begin.get("PAYLIST_CODE"));
					if (s > money) {
						d.put("D_RECEIVE_MONEY", Util.formatDouble2(money));
						skje = skje + money;
						s = s - money;
					} else {
						d.put("D_RECEIVE_MONEY", Util.formatDouble2(s));
						skje = skje + s;
						flag = true;
						s = 0;
					}
					if ("2".equals(begin.get("ITEM_FLAG").toString())) {
						d.put("D_STATUS", 1);
					} else {
						d.put("D_STATUS", 0);
					}
					Dao.insert("fi.fundauto.addDetail", d);
					if (1 != Dao.update("fi.fundauto.upFiRB", d)) throw new Exception("实收收款金额超出应收");
				}
			
			map.put("D_RECEIVE_MONEY", Util.formatDouble2(skje));
			if (1 != Dao.update("fi.fundauto.upFiRBJ", map)) throw new Exception("实收收款金额超出应收");
		}
		{
			Map<String, Object> fundHead = new HashMap<String, Object>(param);
			fundHead.put("FI_REALITY_MONEY", as);
			fundHead.put("FI_PAY_MONEY", as);
			fundHead.put("FI_FLAG", "6");
			Dao.insert("fi.fundauto.addFundHeadSQK", fundHead);
			
			
			//查询该核销单下面所以本金判断是否为循环授信，是的话进行处理(期初的本金)
			rentWriteNewService rentService=new rentWriteNewService();
			rentService.queryPaylistBJSX(FUND_HEAD_ID);
		}
		param.put("FUND_SY_MONEY", s);
		if (1 != Dao.update("fi.fundauto.upFund", param)) throw new Exception("资金状态不属于未分解");
		if (Util.formatDouble2(s) > 0) {// 存在待分解
			Map<String, Object> dfj = new HashMap<String, Object>(param);
			dfj.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
			dfj.put("FUND_RECEIVE_MONEY", s);
			dfj.put("FUND_SY_MONEY", s);
			dfj.put("FUND_PRANT_ID", dfj.get("FUND_ID"));
			Dao.insert("fi.fundauto.addDfj", dfj);
		}
		
		
		//反更支付表主表的已还期次和未款金额
		//查询核销的明细是否有客户保证金，如果有回更还款机会剩余保证金以及添加新增记录
		new rentWriteNewService().queryPaylistHG(FUND_HEAD_ID);
		
	}
}

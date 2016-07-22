package com.pqsoft.pay.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.util.PaymentIRRUtil;
import com.pqsoft.util.Util;


public class FIPPOAction extends Action {

	private PayTaskService service = new PayTaskService();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
	public static Util Util = new Util();
	
	
	
	/**
	 * 计算财务支付表
	 * @param payLine
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPayDetail(Map payLine) {
		int  LEASE_PERIOD = payLine.get("LEASE_PERIOD") == null ? 0:Integer.parseInt(payLine.get("LEASE_PERIOD").toString()) ; 
		int _payCountOfYear = 12/LEASE_PERIOD;
		List<Map<String,Object>> paymentLine = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> payDetailList = service.getDetail(payLine);
		if(!payLine.containsKey("START_DATE")){
			Map<String,Object> payMM = null;
			Map<String,Object> mmm = null;
			double[] irrD = new double[payDetailList.size()+1];
			double sumTopric = payLine.get("sumTopric") == null ? 0.0 : Double.parseDouble(payLine.get("sumTopric").toString());
			double lease_topric = sumTopric;
			irrD[0] = 0-lease_topric;
			for (int i = 0; i < payDetailList.size(); i++) {
				payMM = payDetailList.get(i);
				double MONTHPRICE = payMM.get("MONTHPRICE") == null ? 0.0:Double.parseDouble(payMM.get("MONTHPRICE").toString());
				irrD[i+1] = MONTHPRICE ;
			}
			double IRRyearInterest =  Math.round(PaymentIRRUtil.getIRR(irrD, null, Double.NaN,  LEASE_PERIOD) * _payCountOfYear*10000);
			IRRyearInterest/=100;
			double lastPrice = lease_topric;
			double renPrice = 0;
			double monthPrice = 0;
			double renSub = 0;
			for (int i = 0; i < payDetailList.size(); i++) {
				mmm = new HashMap<String,Object>();
				payMM = payDetailList.get(i);
				double MONTHPRICE = payMM.get("MONTHPRICE") == null ? 0.0:Double.parseDouble(payMM.get("MONTHPRICE").toString());
				monthPrice = MONTHPRICE;
				renPrice = Util.formatDouble10(lastPrice*(IRRyearInterest/100)/_payCountOfYear);
				double ownPrice = Util.subDouble(monthPrice,renPrice);
				lastPrice = Util.subDouble(lastPrice,ownPrice);
				double RENPRICE = payMM.get("RENPRICE") == null ? 0.0:Double.parseDouble(payMM.get("RENPRICE").toString());
				renSub = Util.subDouble(renPrice, RENPRICE);
				mmm.put("PAY_DATE", payMM.get("PAY_DATE"));
				mmm.put("PERIOD_NUM", payMM.get("PERIOD_NUM"));
				mmm.put("MONTHPRICE", monthPrice);
				mmm.put("OWNPRICE", ownPrice);
				mmm.put("RENPRICE", renPrice);
				mmm.put("LASTPRICE", lastPrice);
				mmm.put("Irrrate", IRRyearInterest);
				mmm.put("renSub", renSub);
				paymentLine.add(mmm);
			}
		}else{
			Map<String,Integer> map = new HashMap<String,Integer>(); 
			List<Map<String,Object>> payTables = service.findPayList1(payLine);
			//List<Map<String,Object>> payDetailList = service.getDetail(payLine);
			
			//List<Map<String,Object>> rateList = service.findRate();
			List<Map<String,Object>> payM = null;
			Map<String,Object> payT = null;
			Map<String,Object> payMM = null;
			double[] irrD = null;
			
			//if(rateList==null){
			if(true){
				Map<String,Object> mmm = null;
				irrD = new double[payDetailList.size()+1];
				double sumTopric = payLine.get("sumTopric") == null ? 0.0 : Double.parseDouble(payLine.get("sumTopric").toString());
				double lease_topric = sumTopric;
				irrD[0] = 0-lease_topric;
				for (int i = 0; i < payDetailList.size(); i++) {
					payMM = payDetailList.get(i);
					double MONTHPRICE = payMM.get("MONTHPRICE") == null ? 0.0:Double.parseDouble(payMM.get("MONTHPRICE").toString());
					irrD[i+1] = MONTHPRICE;
				}
				double IRRyearInterest =  Math.round(PaymentIRRUtil.getIRR(irrD, null, Double.NaN, LEASE_PERIOD) * _payCountOfYear*10000);
				IRRyearInterest/=100;
				double lastPrice = lease_topric;
				double renPrice = 0;
				double monthPrice = 0;
				double renSub = 0;
				for (int i = 0; i < payDetailList.size(); i++) {
					mmm = new HashMap<String,Object>();
					payMM = payDetailList.get(i);
					double MONTHPRICE = payMM.get("MONTHPRICE") == null ? 0.0:Double.parseDouble(payMM.get("MONTHPRICE").toString());
					monthPrice = MONTHPRICE;
					renPrice = Util.formatDouble10(lastPrice*(IRRyearInterest/100)/_payCountOfYear);
					double ownPrice = Util.subDouble(monthPrice,renPrice);
					lastPrice = Util.subDouble(lastPrice,ownPrice);
					double RENPRICE = payMM.get("RENPRICE") == null ? 0.0:Double.parseDouble(payMM.get("RENPRICE").toString());
					renSub = Util.subDouble(renPrice, RENPRICE);
					mmm.put("PAY_DATE", payMM.get("PAY_DATE"));
					mmm.put("PERIOD_NUM", payMM.get("PERIOD_NUM"));
					mmm.put("MONTHPRICE", monthPrice);
					mmm.put("OWNPRICE", ownPrice);
					mmm.put("RENPRICE", renPrice);
					mmm.put("LASTPRICE", lastPrice);
					mmm.put("Irrrate", IRRyearInterest);
					mmm.put("renSub", renSub);
					paymentLine.add(mmm);
				}
//			}else{
//
//				for (int i = 0; i < payTables.size(); i++) {
//					payT = (Map<String, Object>) payTables.get(i);
//					payM = service.getDetail(payT);
//					irrD = new double[payM.size()+1];
//					double lease_topric = Double.parseDouble(payLine.get("sumTopric").toString());
//					irrD[0] = 0-lease_topric;
//					for(int j = 0; j < payM.size(); j++){
//						payMM = payM.get(j);
//						irrD[j+1] = Double.parseDouble(payMM.get("MONTHPRICE").toString());
//					}
//					double IRRyearInterest =  Math.round(PaymentIRRUtil.getIRR(irrD, null, Double.NaN, Integer.parseInt(payLine.get("LEASE_PERIOD").toString())) * _payCountOfYear*10000);
//					IRRyearInterest/=100;
//					map1.put(i+"", IRRyearInterest);
//				}
//				
//				Map<String,Object> pm = null;
//				Map<String,Object> pmm = null;
//				
//				Date d1 = new Date();
//				Date d2 = new Date();
//				Date d3 = new Date();
//				int ss = 0;
//				for (int i = 0; i < payDetailList.size(); i++) {
//					if(payLine.get("PAY_TYPE").toString().equals("1")){
//						if(i+1!=payDetailList.size()){
//							pm = payDetailList.get(i);
//							pmm = payDetailList.get(i+1);
//							d1 = sf.parse(pm.get("PAY_DATE").toString());
//							d2 = sf.parse(pmm.get("PAY_DATE").toString());
//							for (int j = 0; j < rateList.size(); j++) {
//								Map<String,Object> m = rateList.get(j);
//								d3 = sf.parse(ppo.getNewDate(m.get("ADJUSTDATE").toString(),1,true));
//								if(d1.getTime() <= d3.getTime() && d2.getTime() >= d3.getTime()){
//									if(ss!=0 && map.get(ss-1+"").toString().equals(pm.get("PERIOD_NUM").toString())){
//										continue;
//									}
//									map.put(ss+"", Integer.parseInt(pm.get("PERIOD_NUM").toString()));
//									ss++;
//								}
//							}
//						}else{
//							pm = payDetailList.get(i);
//							d1 = sf.parse(pm.get("PAY_DATE").toString());
//							for (int j = 0; j < rateList.size(); j++) {
//								Map<String,Object> m = rateList.get(j);
//								d3 = sf.parse(m.get("ADJUSTDATE").toString());
//								if(d1.getTime() <= d3.getTime()){
//									if(ss!=0 && map.get(ss-1+"").toString().equals(pm.get("PERIOD_NUM").toString())){
//										continue;
//									}
//									map.put(ss+"", Integer.parseInt(pm.get("PERIOD_NUM").toString()));
//								}
//							}
//						}
//					}else{
//						if(i+1!=payDetailList.size()){
//							pm = payDetailList.get(i);
//							pmm = payDetailList.get(i+1);
//							d1 = sf.parse(pm.get("PAY_DATE").toString());
//							d2 = sf.parse(pmm.get("PAY_DATE").toString());
//							for (int j = 0; j < rateList.size(); j++) {
//								Map<String,Object> m = rateList.get(j);
//								d3 = sf.parse(m.get("ADJUSTDATE").toString());
//								if(d1.getTime() <= d3.getTime() && d2.getTime() >= d3.getTime()){
//									if(ss!=0 && map.get(ss-1+"").toString().equals(pm.get("PERIOD_NUM").toString())){
//										continue;
//									}
//									map.put(ss+"", Integer.parseInt(pm.get("PERIOD_NUM").toString()));
//									ss++;
//								}
//							}
//						}else{
//							pm = payDetailList.get(i);
//							d1 = sf.parse(pm.get("PAY_DATE").toString());
//							for (int j = 0; j < rateList.size(); j++) {
//								Map<String,Object> m = rateList.get(j);
//								d3 = sf.parse(m.get("ADJUSTDATE").toString());
//								if(d1.getTime() <= d3.getTime()){
//									if(ss!=0 && map.get(ss-1+"").toString().equals(pm.get("PERIOD_NUM").toString())){
//										continue;
//									}
//									map.put(ss+"", Integer.parseInt(pm.get("PERIOD_NUM").toString()));
//								}
//							}
//						}
//					}
//				}
//				
//				if(map.size()==0){
//					Map<String,Object> mmm = null;
//					irrD = new double[payDetailList.size()+1];
//					double lease_topric = Double.parseDouble(payLine.get("sumTopric").toString());
//					irrD[0] = 0-lease_topric;
//					for (int i = 0; i < payDetailList.size(); i++) {
//						payMM = payDetailList.get(i);
//						irrD[i+1] = Double.parseDouble(payMM.get("MONTHPRICE").toString());
//					}
//					double IRRyearInterest =  Math.round(PaymentIRRUtil.getIRR(irrD, null, Double.NaN, Integer.parseInt(payLine.get("LEASE_PERIOD").toString())) * _payCountOfYear*10000);
//					IRRyearInterest/=100;
//					double lastPrice = lease_topric;
//					double renPrice = 0;
//					double monthPrice = 0;
//					double renSub = 0;
//					for (int i = 0; i < payDetailList.size(); i++) {
//						mmm = new HashMap<String,Object>();
//						payMM = payDetailList.get(i);
//						monthPrice = Double.parseDouble(payMM.get("MONTHPRICE").toString());
//						renPrice = Util.formatDouble10(lastPrice*(IRRyearInterest/100)/_payCountOfYear);
//						double ownPrice = Util.subDouble(monthPrice,renPrice);
//						lastPrice = Util.subDouble(lastPrice,ownPrice);
//						renSub = Util.subDouble(renPrice, Double.parseDouble(payMM.get("RENPRICE").toString()));
//						mmm.put("PAY_DATE", payMM.get("PAY_DATE"));
//						mmm.put("PERIOD_NUM", payMM.get("PERIOD_NUM"));
//						mmm.put("MONTHPRICE", monthPrice);
//						mmm.put("OWNPRICE", ownPrice);
//						mmm.put("RENPRICE", renPrice);
//						mmm.put("LASTPRICE", lastPrice);
//						mmm.put("Irrrate", IRRyearInterest);
//						mmm.put("renSub", renSub);
//						paymentLine.add(mmm);
//					}
//				}else{
//					Map<String,Object> pment = null;
//					Map<String,Object> pddd = null;
//					int ln = 1;
//					//int ll = map.get(0+"");
//					double lease_topric = Double.parseDouble(payLine.get("sumTopric").toString());
//					double lastPrice = lease_topric;
//					double renPrice = 0;
//					double monthPrice = 0;
//					double renSub = 0;
//					System.out.println(map+"<<<<<<<<<<<<<<<<<<<<<<<<");
//					for (int i = 0; i < map.size(); i++) {
//						for (int j = ln; j <= payDetailList.size(); j++) {
//							pment = new HashMap<String,Object>();
//							pddd = payDetailList.get(j-1);
//							//新加
//							if(payLine.get("PAY_TYPE").toString().equals("1")){
//								renPrice = Util.formatDouble10(lastPrice*(Double.parseDouble(map1.get(i+"").toString())/100)/_payCountOfYear);
//							}else{
//								if(j!=payDetailList.size()){
//									if(i==0){
//										renPrice = Util.formatDouble10(lastPrice*(Double.parseDouble(map1.get(i+"").toString())/100)/_payCountOfYear);
//									}else{
//										pm = payDetailList.get(j-1);
//										pmm = payDetailList.get(j);
//										d1 = sf.parse(pm.get("PAY_DATE").toString());
//										d2 = sf.parse(pmm.get("PAY_DATE").toString());
//										for (int k = 0; k < rateList.size(); k++) {
//											Map<String,Object> m = rateList.get(k);
//											d3 = sf.parse(m.get("ADJUSTDATE").toString());
//											if(d1.getTime() <= d3.getTime() && d2.getTime() >= d3.getTime()){
//												int m11 = ppo.getTermCount(pm.get("PAY_DATE").toString(), m.get("ADJUSTDATE").toString(), true);
//												int m22 = ppo.getTermCount(m.get("ADJUSTDATE").toString(), pmm.get("PAY_DATE").toString(), true);
//												double m1 = Util.formatDouble10(lastPrice*(Double.parseDouble(map1.get(i-1+"").toString())/100)/_payCountOfYear)*m11;
//												double m2 = Util.formatDouble10(lastPrice*(Double.parseDouble(map1.get(i+"").toString())/100)/_payCountOfYear)*m22;
//												renPrice = m1+m2;
//											}else{
//												renPrice = Util.formatDouble10(lastPrice*(Double.parseDouble(map1.get(i+"").toString())/100)/_payCountOfYear);
//											}
//										}
//									}
//								}else{
//									renPrice = Util.formatDouble10(lastPrice*(Double.parseDouble(map1.get(i+"").toString())/100)/_payCountOfYear);
//								}	
//							}
//							//......
//							
//							
//							if(Integer.parseInt(pddd.get("PERIOD_NUM").toString())==1){
//								if(Integer.parseInt(pddd.get("PERIOD_NUM").toString())>map.get(i+"")){
//									break;
//								}
//							}else if(!((i+1)==map.size())){
//								if(Integer.parseInt(pddd.get("PERIOD_NUM").toString())==map.get(i+1+"")){
//									break;
//								}
//							}
//							monthPrice = Double.parseDouble(pddd.get("MONTHPRICE").toString());
//							//renPrice = Util.formatDouble10(lastPrice*(Double.parseDouble(map1.get(i+"").toString())/100)/_payCountOfYear);
//							double ownPrice = Util.subDouble(monthPrice,renPrice);
//							lastPrice = Util.subDouble(lastPrice,ownPrice);
//							renSub = Util.subDouble(renPrice, Double.parseDouble(pddd.get("RENPRICE").toString()));
//							pment.put("PAY_DATE", pddd.get("PAY_DATE"));
//							pment.put("PERIOD_NUM", pddd.get("PERIOD_NUM"));
//							pment.put("MONTHPRICE", monthPrice);
//							pment.put("OWNPRICE", ownPrice);
//							pment.put("RENPRICE", renPrice);
//							pment.put("LASTPRICE", lastPrice);
//							pment.put("Irrrate", map1.get(i+""));
//							pment.put("renSub", renSub);
//							paymentLine.add(pment);
//							
//						}
//						if(!((i+1)==map.size())){
//							ln = map.get(i+1+"");
//						}
//					}
//				}
//				
		}
			
		}
		paymentLine = payFianlBalancing(paymentLine,payLine,payDetailList);
		return paymentLine;
	}

	 /**
	    * @auto 期末支付表配平
	    * @param _topricSubfirent
	    */
		public List<Map<String,Object>>  payFianlBalancing(List<Map<String,Object>> paymentLines, Map payLine,List<Map<String,Object>> payDetailLists){
			List<Map<String,Object>> ppL = new ArrayList<Map<String,Object>>();
			Map<String,Object> m = null;
		    double totalwnPrice = 0D;
		    double sumTopric = payLine.get("sumTopric")==null?0.0: Double.parseDouble(payLine.get("sumTopric").toString());
			double _topric = sumTopric ;
			for(int i=1;i<=paymentLines.size();i++){
				m = new HashMap<String,Object>();
				Map<String,Object> Line=paymentLines.get(i-1);
				double MONTHPRICE = Line.get("MONTHPRICE")==null?0.0:Double.parseDouble(Line.get("MONTHPRICE").toString());
				double monthPrice = Util.formatDouble2(MONTHPRICE);
				double OWNPRICE = Line.get("OWNPRICE")==null?0.0:Double.parseDouble(Line.get("OWNPRICE").toString()) ;
				double ownPrice  = Util.formatDouble2(OWNPRICE);
				double renPrice  = Util.formatDouble2(monthPrice-ownPrice);
				_topric-=ownPrice; 
				totalwnPrice+=ownPrice;
				double RENPRICE = payDetailLists.get(i-1).get("RENPRICE")==null?0.0:Double.parseDouble(payDetailLists.get(i-1).get("RENPRICE").toString()) ;
				double renSub = Util.formatDouble2(renPrice-RENPRICE);
				m.put("PAY_DATE", Line.get("PAY_DATE"));
				Object  PERIOD_NUM = Line.get("PERIOD_NUM")==null?0:Line.get("PERIOD_NUM").toString() ;
				m.put("PERIOD_NUM", PERIOD_NUM);
				m.put("MONTHPRICE", Util.formatDouble2(monthPrice));
				m.put("OWNPRICE", Util.formatDouble2(ownPrice));
				m.put("RENPRICE", Util.formatDouble2(renPrice));
				m.put("LASTPRICE", Util.formatDouble2(_topric));
				m.put("Irrrate", Line.get("Irrrate"));
				m.put("renSub", renSub);
				//System.out.println(Line.get("PERIOD_NUM").toString()+","+monthPrice+","+ownPrice+","+renPrice+","+_topric+"<<<<<<<<<<<<<<1234");
				ppL.add(m);
			}
			//最后一期，调平1分钱
			Map<String,Object> lastL = new HashMap<String,Object>();
			Map<String,Object> LastLine=ppL.get(ppL.size()-1);
			double MONTHPRICE = LastLine.get("MONTHPRICE") ==null?0.0:Double.parseDouble(LastLine.get("MONTHPRICE").toString()) ;
			double monthPrice1 = Util.formatDouble2(MONTHPRICE);
			lastL.put("PAY_DATE", LastLine.get("PAY_DATE"));
			lastL.put("PERIOD_NUM", LastLine.get("PERIOD_NUM"));
			lastL.put("MONTHPRICE", Util.formatDouble2(monthPrice1));
			double OWNPRICE = LastLine.get("OWNPRICE")==null?0.0:Double.parseDouble(LastLine.get("OWNPRICE").toString());		
			lastL.put("OWNPRICE",Util.formatDouble2(OWNPRICE+sumTopric-totalwnPrice));
			lastL.put("RENPRICE",Util.formatDouble2(MONTHPRICE-OWNPRICE));
			double LASTPRICE = ppL.get(ppL.size()-1-1).get("LASTPRICE")==null?0.0:Double.parseDouble(ppL.get(ppL.size()-1-1).get("LASTPRICE").toString()) ;
			_topric = Util.formatDouble2(LASTPRICE);
			System.out.println(_topric+"<<<<<<<<<<<");
			lastL.put("LASTPRICE",Util.formatDouble2(_topric-OWNPRICE));
			lastL.put("Irrrate", LastLine.get("Irrrate"));
			double RENPRICE = lastL.get("RENPRICE") == null?0.0:Double.parseDouble(lastL.get("RENPRICE").toString()) ;
			double RENPRICE1 = payDetailLists.get(ppL.size()-1).get("RENPRICE") ==null ?0.0 : Double.parseDouble(payDetailLists.get(ppL.size()-1).get("RENPRICE").toString()) ;
			double renSub = Util.formatDouble2(RENPRICE-RENPRICE1);
			lastL.put("renSub", renSub);
			ppL.remove(ppL.size()-1);
			ppL.add(lastL);
			return ppL;
		}

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
}

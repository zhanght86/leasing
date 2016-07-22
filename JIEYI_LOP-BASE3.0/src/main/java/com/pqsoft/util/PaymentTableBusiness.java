package com.pqsoft.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.pqsoft.skyeye.math.Finance;
/**
 * 支付表算法逻辑
 * @author 
 *
 */
public class PaymentTableBusiness extends PaymentTable {
	final private static Logger logger = Logger.getLogger(PaymentTableBusiness.class);
    public static final int HALF_YEAR = 6;
    public static final int ONE_YEAR = 12;  
    public static final int THREE_YEAR = 36;   
    public static final int FIVE_YEAR = 60;
    SimpleDateFormat  dsf = new SimpleDateFormat("yyyy-MM-dd");
    public static Util Util = new Util();
	/**
	 * 生成或重构支付表
	 * 通过对支付表设定必要的参数，返回支付表结构
	 * 必须的参数：
	 * 	支付方式、现值(融资额)、年利率(上浮后)、租赁期数、租赁期限
	 * @return PaymentTable
	 * @throws Exception
	 */
	public PaymentTable magic() throws Exception{
		//检查基本参数
		if(payWay==null)throw new Exception("支付方式不能为空");
		if(topricSubfirent<=0d)throw new Exception("现值(融资额)不能为空");
		if(yearInterest<0d)throw new Exception("年利率(上浮后)不能为空");
		if(leasePeriod<=0d)throw new Exception("租赁周期不能为空");
		if(leaseTerm<=0d)throw new Exception("租赁期数不能为空");

        /**
         * 2011-10-29注释
         */
		//if(String.valueOf(leaseTerm*12/leasePeriod).indexOf(".")!=-1)throw new Exception("租赁期限与租赁期数不成比例");
		logger.debug(topricSubfirent+"==="+leasePeriod);
		//提取基础数据
		/**
		 * 2011-10-29 注释并改动
		 */
		//int _payCountOfYear = leaseTerm*12/leasePeriod;
		int _payCountOfYear = 12/leasePeriod;
		
		double _yearInterest = yearInterest/100;
		double _topricSubfirent = changePeriodNum!=1&&paymentLines.size()!=0?paymentLines.get(changePeriodNum-1).getLastPrice():topricSubfirent;
		if(isTQHK){
			_topricSubfirent = _topricSubfirent - changeMoney;
		}
		logger.debug(changePeriodNum+"===="+_topricSubfirent);
		int _leaseTerm = leaseTerm - changePeriodNum + 1;
		int  wayType = "期末".equals(payWay.substring(0, 2))?1:0;
		//初始化还款计划
		if(paymentLines==null)
			paymentLines = new HashMap<Integer,PaymentLine>();
		//计算还款计划
		if(payWay.contains("等额本息")){
			boolean _isFirst = payWay.contains("期初")&&changePeriodNum==1;
			PaymentLine line = null;
			double lastPrice = _topricSubfirent;
			for(int i=changePeriodNum;i<_leaseTerm+changePeriodNum;i++){
				line = new PaymentLine();
				//计算还款计划
				double monthPrice = Util.formatDouble10(Finance.pmt(_yearInterest/_payCountOfYear,_leaseTerm, -_topricSubfirent,0 , _isFirst?1:0));
//				double monthPrice = Util.formatDouble10(FinancialAlgLib.pmt(_topricSubfirent, _yearInterest, _leaseTerm, _payCountOfYear, _isFirst));
				/*********先进行四舍五入操作 king 2013-03-30***************/
				monthPrice=Math.round(monthPrice*100);
				monthPrice/=100;
				//租金进位   徐广明
				monthPrice=Double.parseDouble(Roundzj(monthPrice+""));
				/**************************************************/
				double renPrice = 0;
				if(leaseTerm==changePeriodNum && leaseTerm!=1){//提前结清操作
					renPrice = paymentLines.get(changePeriodNum).getRenPrice();
				}else{
					renPrice = Util.formatDouble10(_isFirst&&i==1?0:lastPrice*(_yearInterest/_payCountOfYear));
				}
				/*********先进行四舍五入操作 king 2013-03-30***************/
				renPrice=Math.round(renPrice*100);
				renPrice/=100;
				/**************************************************/
				double ownPrice = Util.subDouble(monthPrice,renPrice);
				lastPrice = Util.subDouble(lastPrice,ownPrice);
				line.setLastPrice(lastPrice);
				line.setMonthPrice(monthPrice);
				line.setOwnPrice(ownPrice);
				line.setRenPrice(renPrice);
				line.setPeriodNum(i);
				line.setPayDate(Util.calePayDate(startDate, i, _payCountOfYear,_isFirst));
				paymentLines.put(i,line);
			}
			//清理还款计划
			int cnt = paymentLines.size();
			for(int i=leaseTerm+1;i<=cnt;i++){
				paymentLines.remove(i);
			}
		}else if(payWay.contains("等额本金")){
			boolean _isFirst = payWay.contains("期初")&&changePeriodNum==1;
			PaymentLine line = null;
			double lastPrice = _topricSubfirent;
			for(int i=changePeriodNum;i<_leaseTerm+changePeriodNum;i++){
				line = new PaymentLine();
				double renPrice = 0;
				if(leaseTerm==changePeriodNum){//提前结清操作
					renPrice = paymentLines.get(changePeriodNum).getRenPrice();
				}else{
					renPrice = Util.formatDouble10(_isFirst&&i==1?0:lastPrice*(_yearInterest/_payCountOfYear));
				}
				double ownPrice = Util.formatDouble10(_topricSubfirent/_leaseTerm);
				/*********先进行四舍五入操作 king 2013-03-30***************/
				renPrice=Math.round(renPrice*100);
				renPrice/=100;
				ownPrice=Math.round(ownPrice*100);
				ownPrice/=100;
				/**************************************************/
				double monthPrice = Util.addDouble(ownPrice,renPrice);
				//租金进位   徐广明
				monthPrice=Double.parseDouble(Roundzj(monthPrice+""));
				renPrice=monthPrice-ownPrice;
				/**************************************************/
				
				lastPrice = Util.subDouble(lastPrice,ownPrice);
				line.setLastPrice(lastPrice);
				line.setMonthPrice(monthPrice);
				line.setOwnPrice(ownPrice);
				line.setRenPrice(renPrice);
				line.setPeriodNum(i);
				line.setPayDate(Util.calePayDate(startDate, i, _payCountOfYear,_isFirst));
				paymentLines.put(i,line);
			}
			//清理还款计划
			int cnt = paymentLines.size();
			for(int i=leaseTerm+1;i<=cnt;i++){
				paymentLines.remove(i);
			}
		}else if(payWay.contains("不等额")){
			boolean _isFirst = payWay.contains("期初")&&changePeriodNum==1;
			/*
			 * 1.做等额本息调整
			 * 做等额本息调整的目的是为了在不等额调整以后，重新计算剩余本金
			 * 这个步骤执行完了以后，基本上会出现，最后一期剩余本金不为0的情况
			 * 然后在下一个步骤配平
			 */
			PaymentLine line = null;
			double lastPrice = topricSubfirent;
			for(int i=changePeriodNum;i<_leaseTerm+changePeriodNum;i++){
				//注释2011-48
				line = paymentLines.containsKey(i)?paymentLines.get(i):new PaymentLine();
				//line = new PaymentLine();
				//计算还款计划
				/**************************************************/
				double renPrice = 0;
				if(leaseTerm==changePeriodNum){//提前结清操作
					renPrice = paymentLines.get(changePeriodNum).getRenPrice();
				}else{
				       if(_isFirst&&i==1){
					   renPrice = 0 ;
				       }else{
					   if(line.getLocked()==1||i<changePeriodNum){
					       renPrice = line.getRenPrice();
					   }else{
					       renPrice = lastPrice*(_yearInterest/_payCountOfYear);
					   }
				       }
				}
				/*********先进行四舍五入操作 king 2013-03-30***************/
				renPrice=Math.round(renPrice*100);
				renPrice/=100;
				/**************************************************/
				double monthPrice = Util.formatDouble10(line.getLocked()==1||i<changePeriodNum?line.getOwnPrice()+renPrice:Finance.pmt(_yearInterest/_payCountOfYear,_leaseTerm,-_topricSubfirent, 0, _isFirst?1:0));
				/*********先进行四舍五入操作 king 2013-03-30***************/
				monthPrice=Math.round(monthPrice*100);
				monthPrice/=100;
				
//				double ownPrice = Util.subDouble(monthPrice,renPrice); 
				double ownPrice = Util.formatDouble10(line.getLocked()==1||i<changePeriodNum? line.getOwnPrice(): Util.subDouble(monthPrice,renPrice));
				/**************************************************/
				//租金进位   徐广明
				monthPrice=Double.parseDouble(Roundzj(monthPrice+""));
				renPrice=monthPrice-ownPrice;
				/**************************************************/
				lastPrice = Util.subDouble(lastPrice,ownPrice);
				line.setLastPrice(lastPrice);
				line.setMonthPrice(monthPrice);
				line.setOwnPrice(ownPrice);
				line.setRenPrice(renPrice);
				line.setPeriodNum(i);
				line.setPayDate(line.getLocked()==1?line.getPayDate():Util.calePayDate(startDate, i, _payCountOfYear,_isFirst));
				logger.debug(line.getLocked()+"\t"+line.getPeriodNum()+"\t"+line.getPayDate()+"\t"+Util.format(line.getMonthPrice())+"\t"+Util.format(line.getOwnPrice())+"\t"+Util.format(line.getRenPrice())+"\t"+Util.format(line.getLastPrice()));
				paymentLines.put(i,line);
			}
			 //因为重新配平了支付表，所以支付表的剩余本金发生改变，故重新设定 
			//_topricSubfirent = changePeriodNum!=1&&paymentLines.size()!=0?paymentLines.get(changePeriodNum-1).getLastPrice():topricSubfirent;
			//_topricSubfirent = changePeriodNum!=1&&paymentLines.size()!=0?:topricSubfirent;
			
			//清理还款计划
			int cnt = paymentLines.size();
			for(int i=leaseTerm+1;i<=cnt;i++){
				paymentLines.remove(i);
			}
//			1.判断数据是否符合要求，paymentLines必须是已经有值
//			一般的，生成不等额支付表由等额支付表配平得出结果
//			if(paymentLines.size()!=leaseTerm)
//				throw new Exception("不等额支付时，支付项目数必须为付款期数，当前项目数为"+paymentLines.size()+"期数为"+leaseTerm);
//			2.开始配平，内差法
			boolean forceBreak = false;
			double y=0,x,c=0,jd = 0.0000001,side=-1;
			double minDeviation=1;
			do{
				double _lastPrice = _topricSubfirent,ownPriceSum = 0d;
				for(int i=changePeriodNum;i<_leaseTerm+changePeriodNum;i++){
					line = paymentLines.get(i);
					double renPrice = 0;
					if(leaseTerm==changePeriodNum){//提前结清操作
						renPrice = paymentLines.get(changePeriodNum).getRenPrice();
					}else{
					       if(_isFirst&&i==1){
						   renPrice = 0 ;
					       }else{
						   if(line.getLocked()==1||i<changePeriodNum){
						       renPrice = line.getRenPrice();
						   }else{
						       renPrice = _lastPrice*(_yearInterest/_payCountOfYear);
						   }
					       }
					}
					double monthPrice = Util.formatDouble10(line.getLocked()==1?line.getOwnPrice()+renPrice:(line.getMonthPrice()+y));
					double ownPrice = Util.subDouble(monthPrice,renPrice);
					/**************************************************/
					//租金进位   徐广明
					monthPrice=Double.parseDouble(Roundzj(monthPrice+""));
					renPrice=monthPrice-ownPrice;
					/**************************************************/
					_lastPrice = Util.subDouble(_lastPrice,ownPrice);
					line.setLastPrice(_lastPrice);
					line.setMonthPrice(monthPrice);
					line.setOwnPrice(ownPrice);
					line.setRenPrice(renPrice);
					line.setPayDate(line.getLocked()==1?line.getPayDate():Util.calePayDate(startDate, i, _payCountOfYear,_isFirst));
					ownPriceSum=Util.addDouble(ownPriceSum,ownPrice);
					paymentLines.put(i,line);
				}
				
				//计算误差
				x = Util.subDouble(_topricSubfirent,ownPriceSum);
				//初始化内差值，当误差大于0时为增，否则为减
				if(side==-1)side=x>0?0.1:-0.1;
				//调整内差值，以及精度
				if(x*side>0){
					side = -side;
					if(jd==10){
						//如果最小误差两次一样，退出计算
						if(minDeviation==Math.abs(x))break;
						//保存最小误差
						if(minDeviation>Math.abs(x))minDeviation=Math.abs(x);
					}else{
						jd *=10;
					}
				}
//				//计算误差
//				x = subDouble(_topricSubfirent,ownPriceSum);
//				//初始化内差值，当误差大于0时为增，否则为减
//				if(side==-1)side=x>0?0.1:-0.1;
//				//调整内差值，以及精度
//				if(x*side>0){side = -side;jd =jd==10?10:jd*10;}
//				//如果最小误差两次一样，退出计算
//				if(minDeviation==Math.abs(x))break;
//				//保存最小误差
//				if(minDeviation>Math.abs(x))minDeviation=Math.abs(x);
//				//重新设定配平参数
    			y = -side / jd;
    			//运算100次，如果出现运算100次以后还不能配平的，把它增大即可。
			}while(!forceBreak&&Math.abs(x)>0.01&&c++<1000);
		}else{
			throw new Exception("支付方式不合法:"+payWay);
		}
//      格式化数据，并调平1分钱误差
//		PaymentLine lastLine = paymentLines.get(leaseTerm);
//		lastLine.setMonthPrice(Util.addDouble(lastLine.getMonthPrice(),lastLine.getLastPrice()));
//		lastLine.setOwnPrice(Util.addDouble(lastLine.getOwnPrice(),lastLine.getLastPrice()));
//		lastLine.setLastPrice(0);
		//格式化数据 租金本金利息配平转换，并调平1分钱
		if(1==wayType){//期末
			this.payFianlBalancing(changeTopricSubfirent);
		}else{
			this.payBeginBalancing(changeTopricSubfirent);
		}
		this.IRRInternal(changeTopricSubfirent);
		return this;
	}
	
	//租金进位  租金  3.1=4 3.9=4   徐广明
	public String Roundzj(String a)
	{
//		String zj;
//		String zjold1[]=a.split("[.]");
//		if(zjold1[1].equals("00"))
//		{
//			zj=Integer.parseInt(zjold1[0])+"";
//		}else
//		{
//			zj=(Integer.parseInt(zjold1[0])+1)+"";
//		}
//		
//		return zj;
		return a;
	}
    /**
     * @auto 期末支付表配平
     * @param _topricSubfirent
     */
	public void  payFianlBalancing(double _topricSubfirent){
	    double totalwnPrice = 0D;
		double _topric = _topricSubfirent;
		if(changePeriodNum>1){
			for(int i=1;i<changePeriodNum;i++){
				PaymentLine Line=paymentLines.get(i);
				double monthPrice = Util.formatDouble2(Line.getMonthPrice());
				double monthPricePMT = Util.formatDouble2(Line.getMonthPricePMT());
				double ownPrice  = Util.formatDouble2(Line.getOwnPrice());
				double renPrice  = Util.formatDouble2(monthPrice-ownPrice);
				_topric-=ownPrice; 
				totalwnPrice+=ownPrice;
				Line.setMonthPricePMT(monthPricePMT);
			}
			_topric = paymentLines.get(changePeriodNum-1).getLastPrice();
			if(isTQHK){
				_topric = _topric - changeMoney;
			}
			for(int i=changePeriodNum;i<=leaseTerm;i++){
				PaymentLine Line=paymentLines.get(i);
				double monthPrice = Util.formatDouble2(Line.getMonthPrice());
				double ownPrice  = Util.formatDouble2(Line.getOwnPrice());
				double renPrice  = Util.formatDouble2(monthPrice-ownPrice);
				_topric-=ownPrice; 
				totalwnPrice+=ownPrice;
				Line.setMonthPrice(Util.formatDouble2(monthPrice));
				Line.setOwnPrice(Util.formatDouble2(ownPrice));
				Line.setRenPrice(Util.formatDouble2(renPrice));
				Line.setLastPrice(Util.formatDouble2(_topric));
			}
		}else{
			for(int i=1;i<=leaseTerm;i++){
				PaymentLine Line=paymentLines.get(i);
				double monthPrice = Util.formatDouble2(Line.getMonthPrice());
				double ownPrice  = Util.formatDouble2(Line.getOwnPrice());
				double renPrice  = Util.formatDouble2(monthPrice-ownPrice);
				_topric-=ownPrice; 
				totalwnPrice+=ownPrice;
				Line.setMonthPrice(Util.formatDouble2(monthPrice));
				Line.setOwnPrice(Util.formatDouble2(ownPrice));
				Line.setRenPrice(Util.formatDouble2(renPrice));
				Line.setLastPrice(Util.formatDouble2(_topric));
			}
		}
		//最后一期，调平1分钱
		PaymentLine LastLine=paymentLines.get(leaseTerm);
		if(changePeriodNum>1){
			if(isTQHK){
				LastLine.setOwnPrice(Util.formatDouble2(LastLine.getOwnPrice()+_topricSubfirent-changeMoney-totalwnPrice));
			}else{
				LastLine.setOwnPrice(Util.formatDouble2(LastLine.getOwnPrice()+_topricSubfirent-totalwnPrice));
			}
		}else{
			LastLine.setOwnPrice(Util.formatDouble2(LastLine.getOwnPrice()+_topricSubfirent-totalwnPrice));
		}
		//最后一期在利息上找差值  king 2012-09-27
		//		LastLine.setMonthPrice(Util.formatDouble2(LastLine.getOwnPrice()+LastLine.getRenPrice()));
		LastLine.setRenPrice(Util.formatDouble2(LastLine.getMonthPrice()-LastLine.getOwnPrice()));
		//判断期次是否小于2
		if (leaseTerm < 2) {
			_topric = _topricSubfirent;
		} else {
			_topric = Util.formatDouble2(paymentLines.get(leaseTerm-1).getLastPrice());
		}
		LastLine.setLastPrice(Util.formatDouble2(_topric-LastLine.getOwnPrice()));
	}
	/**
	 * 期初支付表配平
	 */
	public void payBeginBalancing(double _topricSubfirent){
	  for(int i=1;i<=leaseTerm;i++){
		PaymentLine Line=paymentLines.get(i);
		logger.debug(i+"===="+Line.getOwnPrice());
		double ownPrice  = Util.formatDouble2(Line.getOwnPrice());
		double renPrice  = Util.formatDouble2(Line.getRenPrice()+Line.getOwnPrice()-ownPrice);
		Line.setOwnPrice(Util.formatDouble2(ownPrice));
		Line.setRenPrice(Util.formatDouble2(renPrice));
	  	}
	  this.payFianlBalancing(_topricSubfirent); 
	}
	/**
	 * 支付表生成
	 * @param payTable
	 * @return
	 * @throws Exception 
	 */
	public List<PaymentLine> print(PaymentTable payTable) throws Exception{
		double monthPriceSum = 0d;
		double ownPriceSum = 0d;
		double renPriceSum = 0d;
		//logger.debug("期次\t应付日期\t\t\t租金\t\t本金\t\t利息\t剩余本金");
		List   payList = new ArrayList();
		Map<String,Object> tremMap = null;
		Map<String,Object> totalMap = new HashMap<String,Object>();
		Integer[] lineNums = payTable.getPaymentLines().keySet().toArray(new Integer[]{});
		Arrays.sort(lineNums);
		for(int j : lineNums){
			if(j<=payTable.getLeaseTerm()){
			tremMap = new HashMap<String,Object>();
			PaymentLine line = payTable.getPaymentLines().get(j);
			monthPriceSum += line.getMonthPrice();
			ownPriceSum += line.getOwnPrice();
			renPriceSum += line.getRenPrice();
			line.setType(2);
			line.setPaylistSubCode(paylistSubCode);
			line.setCustID(custID);
			line.setEquID(equID);
			line.setPrecent(percent);
			logger.debug("a---"+line.getPeriodNum()+"\t"+line.getPayDate()+"\t"+Util.format(line.getMonthPrice())+"\t"+Util.format(line.getOwnPrice())+"\t"+Util.format(line.getRenPrice())+"\t"+Util.format(line.getLastPrice()));
			payList.add(line);
			}
		}
		logger.debug("=================================================================================================");
		logger.debug("S\t"+Util.format(monthPriceSum)+"\t"+Util.format(ownPriceSum)+"\t"+Util.format(renPriceSum)+"\t");
        return payList;
        //2011-10-22 吴国伟-修改
        //this.StructurePayShape(payList);
	}
	/**
	 * 生成支付表不等额
	 * @param PaymentTableBusiness
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List updateNoPayList(PaymentTableBusiness paytable,Map map)
	   throws Exception{
		paytable.print(paytable.magic());
		List<Map> list = (List) JSONArray.fromObject(map.get("paylist").toString());
		logger.debug("======"+list.size());
		for(Map m :list){
			int periodNum = Integer.valueOf(m.get("periodNum").toString());
			double monthPrice = Double.valueOf(m.get("flag1").toString());
			paytable.getPaymentLines().get(periodNum).setLocked(1);
			paytable.getPaymentLines().get(periodNum).setMonthPrice(monthPrice);
		}
		return  paytable.print(paytable.magic());
		
	}
	/**
	 * @auto 支付表展示处理
	 * @return  List
	 */
	public List StructurePayShape(List<Map> PayList){
		double monthPriceSum = 0d;
		double ownPriceSum = 0d;
		double renPriceSum = 0d;
		PaymentType  paymentType = new PaymentType();
		List<Map<String,Object>> payShapeList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> payType = paymentType.CreatePaymentType();
		Map<String,Object> monthMap = null;
		for(Map<String,Object> map:PayList){
			for(Map<String,Object> mapType:payType){
				monthMap = new HashMap<String,Object>();
				monthMap.put("custID", custID);
				monthMap.put("equID", equID);
				monthMap.put("paylistSubCode", paylistSubCode);
				monthMap.put("percent", percent);
				monthMap.put("periodNum", map.get("periodNum"));
				monthMap.put("payDate",  map.get("payDate"));
				monthMap.put("payProject",  mapType.get("TYPE"));
				monthMap.put("Perice",  map.get(mapType.get("PAYTYPE")));
				monthMap.put("locked",  map.get("locked"));
				monthMap.put("type",  2);
				payShapeList.add(monthMap);
			}
//			总金额
//			monthPriceSum += Double.parseDouble(map.get("monthPerice").toString());
//			ownPriceSum += Double.parseDouble(map.get("ownPerice").toString());
//			renPriceSum += Double.parseDouble(map.get("renPerice").toString());
		}
		logger.debug("客户\t期次\t应付日期\t\t\t项目\t\t金额\t\t锁定状态\t类型");
		for(Map<String,Object> map:payShapeList){
		logger.debug(map.get("custID")+"\t"+map.get("periodNum")+"\t"+map.get("payDate")+"\t\t"+
					           map.get("payProject")+"\t\t"+map.get("Perice")+"\t\t"+
					           map.get("locked")+"\t"+map.get("type"));
		}
		return payShapeList;
	}
	/**
	 * @param  设备总额,比例配置,支付表还款计划
	 */
	public List PaymentPrecentProcess(double TopricSubfirent,List<Map> payShapList){
		//测试数据提供
		List<Map<String,Object>> processList = new ArrayList<Map<String,Object>>();
		int   size = 0;
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		Map<String,Object> map3 = new HashMap<String,Object>();
		map1.put("CUST_ID",123);
		map1.put("EQU_ID","1,3");
		map1.put("PRECENT","100%");
		map1.put("AMOUNT",600000);
		processList.add(map1);
		map2.put("CUST_ID",123);
		map2.put("EQU_ID","2");
		map2.put("PRECENT","30%");
		map2.put("AMOUNT",100000);
		processList.add(map2);
		map3.put("CUST_ID",234);
		map3.put("EQU_ID","2");
		map3.put("PRECENT","70%");
		map3.put("AMOUNT",200000);
		processList.add(map3);
		
		size = processList.size();//比例配置条数，最后一条要做数据调平
		List<Map<String,Object>>   payProcessList = new ArrayList<Map<String,Object>>();
		Map<String,Object>    payProcessMap = null;
		//开始按比例计算
		for(Map map:payShapList){
			double  Perice  = Double.valueOf(map.get("Perice").toString());
			double  LastPerice = Perice;
			for(int i=0; i< size; i++){
				Map processMap=  processList.get(i);
				double processPrecent = Util.formatDouble10(Double.valueOf(processMap.get("PRECENT").toString()));
				if(i!=size-1){
					double processPerice = Util.formatDouble2(Util.mul(processPrecent,Perice));
					LastPerice-=processPerice;
					payProcessMap = new HashMap<String,Object>();
					payProcessMap.put("CUST_ID",processMap.get("CUST_ID"));
					payProcessMap.put("EQU_ID",processMap.get("EQU_ID"));
					payProcessMap.put("periodNum",map.get("periodNum"));
					payProcessMap.put("payDate",map.get("payDate"));
					payProcessMap.put("payProject",map.get("payProject"));
					payProcessMap.put("Perice",processPerice);
					payProcessMap.put("locked",map.get("locked"));
					payProcessMap.put("type",map.get("type"));
					payProcessList.add(payProcessMap);
				}
				else{
					payProcessMap = new HashMap<String,Object>();
					payProcessMap.put("CUST_ID",processMap.get("CUST_ID"));
					payProcessMap.put("EQU_ID",processMap.get("EQU_ID"));
					payProcessMap.put("periodNum",map.get("periodNum"));
					payProcessMap.put("payDate",map.get("payDate"));
					payProcessMap.put("payProject",map.get("payProject"));
					payProcessMap.put("Perice",Util.formatDouble2(LastPerice));
					payProcessMap.put("locked",map.get("locked"));
					payProcessMap.put("type",map.get("type"));
					payProcessList.add(payProcessMap);
				}
				
			}
		}
		for(Map<String,Object> map:payProcessList){
			logger.debug(map.get("CUST_ID")+"\t"+map.get("EQU_ID")+"\t"+map.get("periodNum")+"\t"+//map.get("payDate")+"\t\t"+
						           map.get("payProject")+"\t\t"+map.get("Perice")+"\t\t"
						           //+map.get("locked")+"\t"+map.get("type")
						           );
			}
		return payProcessList;
	}
	/**
	* @Description: TODO 计算内部收益情况和现金流
	* @author 吴国伟
	* @throws Exception 
	* @date 2011-11-30上午11:42:39
	 */
	public void IRRInternal(double _topricSubfirent)throws Exception{
		//组装IRR参数（融资额，每期等额租赁费)
		double[]  IRRData =new double[leaseTerm+1];
		IRRData[0] = -_topricSubfirent;
		for(int i=1;i<=leaseTerm;i++){
			PaymentLine Line=paymentLines.get(i);
			IRRData[i] = Util.formatDouble2(Line.getMonthPrice());
		}
//		for(int i=0 ;i<=leaseTerm;i++){
//			logger.debug(IRRData[i]);
//		}
		//内部收益率
		double irrrate =  Util.formatDouble4(PaymentIRRUtil.getIRR(IRRData,null, Double.NaN,leasePeriod))*100;
		
		for(int i=1;i<=leaseTerm;i++){
			PaymentLine Line=paymentLines.get(i);
			Line.setIrrrate(irrrate);
		}
	}
}

package com.pqsoft.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pqsoft.skyeye.util.Format;


/**
 * 计算租赁收益率

 * @author 吴国伟
 *
 */
public class RX_PaymentInternalReturnCalculate extends RX_PaymentBean {
	    final private static Logger logger = Logger.getLogger(RX_PaymentInternalReturnCalculate.class);
	
	    public static final int HALF_YEAR = 6;
	    public static final int ONE_YEAR = 12;  
	    public static final int THREE_YEAR = 36;   
	    public static final int FIVE_YEAR = 60;
	    SimpleDateFormat  dsf = new SimpleDateFormat("yyyy-MM-dd");
	    Format  Format = new Format();
	    public static Util Util = new Util();
	    
	    /**
	    *  计算租赁测算、银行融资	、盈利计算	、现金流测算	
	    *  先计算租赁测算
	    *  推算出 银行融资和盈利和现金流
	    * @author 吴国伟
	    * @return  PaymentInternalReturnBean
	    * @throws Exception 
	    * @date 2012-05-31
	     */
	    public RX_PaymentInternalReturnCalculate ExecuteCommand()throws Exception{
	    	
	    	if(payWay==null)throw new Exception("支付方式不能为空");
			if(rzje<=0d)throw new Exception("现值(融资额)不能为空");
			if(leasePeriod<=0d)throw new Exception("租赁周期不能为空");
			if(leaseTerm<=0d)throw new Exception("租赁期数不能为空");
			if(zlnbcl<=0d)throw new Exception("租赁利率不能为空");
			PaymentTableBusiness payTable = new PaymentTableBusiness();
	    	//客户
			List<PaymentLine> PayInternalList = new ArrayList<PaymentLine>();
			//内部
			List<PaymentLine> payInternalList1 = new ArrayList<PaymentLine>();
			
			RX_PaymentInternalReturnLine pLine1 = new RX_PaymentInternalReturnLine();
			if(RX_PaymentInternalline==null){
				RX_PaymentInternalline = new HashMap<Integer, RX_PaymentInternalReturnLine>();
			}
			RX_PaymentInternalReturnLine pLine = null;
			/**
			 * 根据计算方式生成租赁测算
			 * 1是PMT
			 */
			   try {
					payTable.setTopricSubfirent(rzje);
					payTable.setChangeTopricSubfirent(rzje);
					payTable.setLeaseTerm(leaseTerm);
					payTable.setLeasePeriod(leasePeriod);
					payTable.setYearInterest(zlnbcl);
					payTable.setPayWay(payWay);
					PayInternalList = payTable.print(payTable.magic());
				} catch (Exception e) {
				}
			
			//负融资额
			double furze = -rzje;
			//首付款、管理费及保证金加和 (自有同值)
			double sgb = Util.formatDouble2(sfje+dlsbzj+bzjje+glfje);
			//标的物价值
			double bdwjz = topricSubfirent_bdw;
			//支付贷款 1
			double zfdk1 = -(topricSubfirent_bdw);
			//保证金+保证金利息
			if(StringUtils.isBlank(bzjlx)){
				bzjlx=0;
			}
			double bzjjiabzjlx = Util.addDouble(bzjje,bzjlx);
			//全部项目现金流量
			double rxmxjll = sgb+zfdk1;
			//自有资金开票保证金
			double rzykpbzj = -Util.formatDouble2(bdwjz*kpbzjbl*kpzb/10000);
			//自有资金支付贷款
			double rzyzfdk = -Util.formatDouble2(bdwjz*xjzfzb/100) ;
			//自有资金开票手续费
			double rzykpsxf = -Util.formatDouble2((bdwjz*kpzb*kpsxfl/10000)-(bdwjz*kpzb*(100-kpbzjbl)*ckfl/1000000));
			//自有贷款
			double zydk = Util.formatDouble2(bdwjz*xjzfzb*dkzb/10000);
			logger.info("自有贷款----------------------"+zydk);
			//自用项目现金流量
			double rzyxmxjll = rzykpbzj+rzyzfdk+rzykpsxf+zydk+sgb;
			//自有资金投资回收
			double zyzjtzhs = rzyxmxjll;
			double zyzjtzhs1 =rzyxmxjll;
			logger.info("自有资金投资回收----------------------"+zyzjtzhs);
			//财务租金和
			double cwzjzh = -Util.sub(rzje,glfje);
			double cwzjzh1 = -cwzjzh;
			logger.info("财务租金和----------------------"+cwzjzh);
			//企业内部租金和
			double  qynbzjh = Util.formatDouble2(-bdwjz+bzjje+sfje+glfje);
			double  qynbzjh1 = -qynbzjh;
			logger.info("企业内部租金和----------------------"+qynbzjh);
			pLine1.setLeasePeriodNum(0);
			pLine1.setLeaseMonthPrice(rzje);
			pLine1.setSfkbzjglf(sgb);
			pLine1.setZfdk(zfdk1);
			pLine1.setXmxjll(rxmxjll);
			pLine1.setZyzfdk(Util.mul(zfdk1,xjzfzb));
			pLine1.setZykpbzj(rzykpbzj);
			pLine1.setZykpsxf(rzykpsxf);
			pLine1.setZydk(zydk);
			pLine1.setZysqkgb(sgb);
			pLine1.setZyxjl(rzyxmxjll);
			pLine1.setZytzhs(zyzjtzhs);
			pLine1.setCwzj(cwzjzh);
			pLine1.setCzqyzj(qynbzjh);
			RX_PaymentInternalline.put(0,pLine1);
			//每期租金
			double[] monthPrice= new double[leaseTerm];
			//租金和
			double TotalmonthPrice = 0d;
			for (int i = 0; i < PayInternalList.size(); i++) {
				PaymentLine rentPrices=PayInternalList.get(i);
				TotalmonthPrice+=rentPrices.getMonthPrice();
				monthPrice[i]=rentPrices.getMonthPrice();
			}
			
			//每期利息
			//利息和
			double TotalrentPrice = 0d;
			for(PaymentLine rentPrices:PayInternalList){
				TotalrentPrice+=rentPrices.getRenPrice();
			}
			//防洪护堤费
			double fhhtitotal = TotalrentPrice+glfje+czhgj;
			//印花税税基
			double  ryhssj = Util.formatDouble2(TotalmonthPrice+sfje+glfje+czhgj);
			//营业税及附加税基
			double  ryyssj = Util.div(fhhtitotal,leaseTerm,2);
			//租金总和
			double TotalPmonthPrice = 0d;
			//本金总和
			double TotalPownPrice = 0d;
			//利息总和
			double TotalPrenPrice = 0d;
			//应收租金测算数组
			
			double[]  YSIRRData =new double[leaseTerm+1];
			YSIRRData = Util.SimpleData(rzje, monthPrice, leaseTerm,YSIRRData);
			double ysirr = Util.formatDouble8(PaymentIRRUtil.getIRR(YSIRRData,null,Double.NaN,leasePeriod));
			
			//"项目现金流量"
			double[]  XMIRRData =new double[leaseTerm+1];
			XMIRRData[0] = rxmxjll<0?rxmxjll:-rxmxjll;
			double xmirr =0d;
			//"自有资金项目现金流量"
			double[]  ZYIRRData =new double[leaseTerm+1];
			ZYIRRData[0] = rzyxmxjll<0?rzyxmxjll:-rzyxmxjll;
			double zyirr = 0d;
			//财务收益率封装数组
			double[]  CWIRRData =new double[leaseTerm+2];
			CWIRRData = Util.SimpleData(cwzjzh, monthPrice, leaseTerm,CWIRRData);
			CWIRRData[leaseTerm+1]=czhgj;
			double cwirr = Util.formatDouble8(PaymentIRRUtil.getIRR(CWIRRData,null, 0.01,leasePeriod));
			logger.info("cwirr--------------"+cwirr);
			//承租企业内部收益率测算 封装数组		
			double[]  CZIRRData =new double[leaseTerm+1];
			CZIRRData[0] = qynbzjh<0?qynbzjh:-qynbzjh;
			for(int i=0;i<=leaseTerm;i++){
				if(!(i==leaseTerm)){//所有期次处理
						PaymentLine line = PayInternalList.get(i); 
						pLine = new RX_PaymentInternalReturnLine();
						//租赁测算---租金
						double PmonthPrice = line.getMonthPrice();
						//租赁测算---利息
						double PrenPrice = line.getRenPrice();
						//租赁测算---本金
						double PownPrice = line.getOwnPrice();
						//租赁测算---剩余本金
						double PlastPrice = i==0?rzje:PayInternalList.get(i-1).getLastPrice();
						int Pnum = line.getPeriodNum();
						pLine.setLeasePeriodNum(Pnum);
						pLine.setLeaseMonthPrice(PmonthPrice);
						TotalPmonthPrice += PmonthPrice;
						pLine.setLeaseRenPrice(PrenPrice);
						TotalPrenPrice += PrenPrice;
						pLine.setLeaseOwnPrice(PownPrice);
						TotalPownPrice += PownPrice;
						pLine.setLeaseLastPrice(PlastPrice);
						double  totalMonth =0d;
						double  totalMonth_1 = 0d;
						for (int j = i; j < PayInternalList.size(); j++) {
							totalMonth+=PayInternalList.get(j).getMonthPrice();
						}
						for (int j = i+1; j < PayInternalList.size(); j++) {
							totalMonth_1+=PayInternalList.get(j).getMonthPrice();
						}
						
						double  HLeaseTerm = PmonthPrice;
						if(leaseTerm==(i+1)){
							totalMonth += czhgj;
							totalMonth_1 += czhgj;
						}
						double restult = (bzjjiabzjlx-totalMonth)<0?-(bzjjiabzjlx-totalMonth_1):-HLeaseTerm;
						restult = restult >0?0:((bzjjiabzjlx-totalMonth)<0?-(bzjjiabzjlx-totalMonth_1):-HLeaseTerm);
						restult = leaseTerm==(i+1)?restult-dlsbzj:restult;
						pLine.setSfkbzjglf(restult);
						pLine.setZysqkgb(restult);
						pLine.setZj(PmonthPrice);
						pLine.setJjfy(0);
                        //自有开票保证金
						double kpbzjje = (i+1)==kpqk?-rzykpbzj:0;
                        pLine.setZykpbzj(kpbzjje);
                        //自有支付货款
						pLine.setZyzfdk((i+1)==kpqk?Util.mul(zfdk1,kpzb)/100:0);
						 //自有开票手续费
						pLine.setZykpsxf(0);
						//自有首付款、管理费及保证金
						pLine.setZysqkgb(restult);
						//自有保证金利息
						pLine.setZybzjlx((i+1)==kpqk?Util.formatDouble2(kpbzjje*bzjll/4/100):0);
						//自有贷款还本付息
						//pLine.setZydkhbfx((i+1)>kpqk?-line1.getMonthPrice():0);
						//自有租金
						pLine.setZyzj(line.getMonthPrice());
						//自有居间费用
						pLine.setZyzfjjf(0);
						//自有税率
						//pLine.setZysf(shuifei);
						//自有项目现金流量
						double zyxjl = pLine.getZykpbzj()+pLine.getZyzfdk()+pLine.getZykpsxf()+pLine.getZysqkgb()+pLine.getZybzjlx()+pLine.getZydk()+pLine.getZyzj()+pLine.getZyzfjjf();
						pLine.setZyxjl(zyxjl);
						//ZYIRRData[i+1] = zyxjl;
						//自有资金投资回报
						//zyzjtzhs1 = zyxjl+zyzjtzhs1;
						//pLine.setZytzhs(zyzjtzhs1);
						//财务内部收益
						pLine.setCwsybj(cwzjzh1);
						pLine.setCwzj(line.getMonthPrice());
						double nbcwlx = Util.mul(cwzjzh1,cwirr/12*leasePeriod);
						pLine.setCwlx(nbcwlx);
						double nbcwbj =Util.sub(line.getMonthPrice(),nbcwlx); 
						pLine.setCwbj(nbcwbj);
						cwzjzh1 = Util.sub(cwzjzh1,nbcwbj);
						//承租人内部收益
						CZIRRData[i+1] =Util.addDouble(line.getMonthPrice(),restult);
						RX_PaymentInternalline.put(i+1,pLine);
						
				}else{
					logger.info("wu");
				}
			}
				double czirr = Util.formatDouble8(PaymentIRRUtil.getIRR(CZIRRData,null, 0.01,leasePeriod));
				logger.info("czirr--------------"+czirr);
				
				double  cztotal =0d;//还款还本付息
				//计算 承租企业内部收益率测算 	
				Integer[] lineNums = this.getRX_PaymentInternalline().keySet().toArray(new Integer[]{});
				Arrays.sort(lineNums);
				for(int j =1;j<=lineNums.length-1;j++){
					RX_PaymentInternalReturnLine line = this.getRX_PaymentInternalline().get(j);
					line.setCzqysybj(qynbzjh1);
					double czmonthPrice = Util.addDouble(monthPrice[j-1],line.getZysqkgb());
					line.setCzqyzj(czmonthPrice);
					double czxwlx = Util.mul(qynbzjh1,czirr);
					line.setCzqylx(czxwlx);
					double cznbbj = Util.sub(monthPrice[j-1], czxwlx);
					line.setCzqybj(cznbbj);
					qynbzjh1 = Util.sub(qynbzjh1, cznbbj);
					//自有贷款
					if(j==kpqk){
						line.setZydk(bdwjz*kpzb*(qynbzjh1/bdwjz)/100);
						cztotal = qynbzjh1;
					}
					RX_PaymentInternalline.put(j,line);
				}
				cztotal = kpqk==0?Util.mul(rzje+sfje,dkzb)/100:cztotal;
				/**
				 * 贷款支付表
				 */
				if(StringUtils.isNotBlank(cztotal)&&cztotal>0){
				   try {
						payTable.setTopricSubfirent(cztotal);
						payTable.setChangeTopricSubfirent(cztotal);
						payTable.setLeaseTerm(leaseTerm-kpqk);
						payTable.setLeasePeriod(leasePeriod);
						payTable.setYearInterest(dkll);
						payTable.setPayWay("期末等额本金支付");
						payInternalList1 = payTable.print(payTable.magic());
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("测算失败----------------------------");
					}
				//贷款还本付息
				Integer[] lineNums_1 = this.getRX_PaymentInternalline().keySet().toArray(new Integer[]{});
				Arrays.sort(lineNums_1);
				for(int j =1;j<=lineNums_1.length-1;j++){
					logger.info("==========================="+j);
					RX_PaymentInternalReturnLine rxline1 = this.getRX_PaymentInternalline().get(j);
					PaymentLine line1 = j>kpqk?payInternalList1.get(j-kpqk-1):null; 
					//税费
					double shuifei = 0d;
					if(1==j){
						logger.info(j>kpqk?line1.getRenPrice():0);
						logger.info(ryyssj);
						logger.info(j>kpqk?(line1.getRenPrice()-ryyssj):-ryyssj);
						logger.info(yyssl/100);
						logger.info((yhsl/100)*ryhssj);
						shuifei = Util.formatDouble2(((j>kpqk?line1.getRenPrice():0)-ryyssj)*(yyssl/100)-(yhsl/100)*ryhssj);
					}else{
						shuifei = Util.formatDouble2(((j>kpqk?line1.getRenPrice():0)-ryyssj)*(yyssl/100));
					}
					if(j==12 || j==24 || j==36 || j==48){
						shuifei = Util.sub(shuifei,fhhtitotal/leaseTerm*12*fhhtsl/100);
					}
					rxline1.setSf(shuifei);
					//全部资金"项目现金流量"  还差几项
                    double allXmxjll = rxline1.getLeaseMonthPrice()+shuifei;
                    rxline1.setXmxjll(allXmxjll);
                    XMIRRData[j] = allXmxjll;
                   
					//自有贷款还本付息
                    double zyhbfx1 = j>kpqk?-line1.getMonthPrice():0;
                    logger.info(j+"自有贷款还本付息=="+zyhbfx1);
                    rxline1.setZydkhbfx(zyhbfx1);
					//自有税率
					rxline1.setZysf(shuifei);
					logger.info(j+"自有税率=="+shuifei);
					//自有项目现金流量
					double zyxjl = rxline1.getZyxjl()+shuifei+zyhbfx1+(j==kpqk?cztotal:0);
					logger.info(j+"有项目现金流量=="+zyxjl);
					rxline1.setZyxjl(zyxjl);
					ZYIRRData[j] = zyxjl;
					//自有资金投资回报
					zyzjtzhs1 = zyxjl+zyzjtzhs1;
					rxline1.setZytzhs(zyzjtzhs1);
					RX_PaymentInternalline.put(j,rxline1);
				}
				}
				try{
				xmirr = Util.formatDouble8(PaymentIRRUtil.getIRR(XMIRRData,null, 0.01,leasePeriod));
				for (int i = 0; i < ZYIRRData.length; i++) {
					System.out.println("ZYIRRData="+ZYIRRData[i]);
				}
				zyirr = Util.formatDouble8(PaymentIRRUtil.getIRR(ZYIRRData,null, 0.01,leasePeriod));
				}catch(Exception e){}
			RX_PaymentInternalReturnLine pLineAll = new RX_PaymentInternalReturnLine();
			pLineAll.setYnhbcl(Util.formatDouble4(ysirr/12*100));
			pLineAll.setNnhbcl(Util.formatDouble4(ysirr*100));
			pLineAll.setQbzjsyl(Util.formatDouble4(xmirr/12*100));
			pLineAll.setNbzjsyl(Util.formatDouble4(xmirr*100));
			pLineAll.setZyxmsyl(Util.formatDouble4(zyirr/12*100));
			pLineAll.setZynmsyl(Util.formatDouble4(zyirr*100));
			pLineAll.setCwsyl(Util.formatDouble4(cwirr/12*100));
			pLineAll.setCwnsyl(Util.formatDouble4(cwirr*100));
			pLineAll.setCzsyl(Util.formatDouble4(czirr/12*100));
			pLineAll.setCznsyl(Util.formatDouble4(czirr*100));
			pLineAll.setSfkbzjglf(-czhgj);
			pLineAll.setZj(czhgj);
			pLineAll.setZysqkgb(-czhgj);
			pLineAll.setZyzj(czhgj);
			
			pLineAll.setZytzhs(RX_PaymentInternalline.get(leaseTerm).getZytzhs());
			pLineAll.setCwzj(czhgj);
			pLineAll.setCwsybj(RX_PaymentInternalline.get(leaseTerm).getCwsybj()-RX_PaymentInternalline.get(leaseTerm).getCwbj());
			pLineAll.setCwlx(pLineAll.getCwsybj() * cwirr/12*leasePeriod);
			pLineAll.setCwbj(pLineAll.getCwzj()-pLineAll.getCwlx());
			pLineAll.setZyzjzbdwgjzb(-Util.formatDouble2(rzyxmxjll/bdwjz));
			pLineAll.setYlzsjtfjezb(Util.formatDouble2(rzje-glfje-dlsbzj));
			pLineAll.setYhsj(ryhssj);
			pLineAll.setYyssj(ryyssj);
			pLineAll.setFhhtsj(fhhtitotal);
			pLineAll.setZlxs(Util.formatDouble4(monthPrice[0]/rzje*100));
			RX_PaymentInternalline.put(leaseTerm+2, pLineAll);
			
			return this;
	    }
	    /**
		 * 租金生成
		 * @param payTable
		 * @return
		 * @throws Exception 
		 */
		public List<RX_PaymentInternalReturnLine> print(RX_PaymentBean payTable) throws Exception{
			List   list = new ArrayList();
			Map<String,Object> totalMap = new HashMap<String,Object>();
			Integer[] lineNums = payTable.getRX_PaymentInternalline().keySet().toArray(new Integer[]{});
			Arrays.sort(lineNums);
			for(int j : lineNums){
				RX_PaymentInternalReturnLine line = payTable.getRX_PaymentInternalline().get(j);
				logger.info(line.getLeasePeriodNum()+"\t"+line.getLeaseMonthPrice()
						+"\t"+line.getLeaseMonthPrice()+"\t"+line.getLeaseOwnPrice()
						+"\t"+line.getLeaseRenPrice()+"\t"+line.getLeaseLastPrice()
						+"\t"+line.getSfkbzjglf()+"\t"+line.getZfdk()+"\t"+line.getZj()
						+"\t"+line.getJjfy()+"\t"+line.getSf()+"\t"+line.getXmxjll()
						+"\t"+line.getZykpbzj()+"\t"+line.getZyzfdk()+"\t"+line.getZykpsxf()
						+"\t"+line.getZysqkgb()+"\t"+line.getZybzjlx()+"\t"
						+"\t"+line.getZydk()+"\t"+line.getZydkhbfx()+"\t"+line.getZyzj()
						+"\t"+line.getZyzj()+"\t"+line.getZyzfjjf()+"\t"+line.getZysf()
						+"\t"+line.getZyxjl()+"\t"+line.getZytzhs()
						+"\t"+line.getCwbj()+"\t"+line.getCwlx()+"\t"+line.getCwsybj()
						+"\t"+line.getCwzj()+"\t"+line.getCzqybj()+"\t"+line.getCzqylx()
						+"\t"+line.getCzqysybj()+"\t"+line.getCzqyzj()
						);
				list.add(line);
			}
	        return list;
		}
		public static void main(String[] args){
			System.out.println(12%1);
			System.out.println(12%12);
			System.out.println(12%24);
			System.out.println(12%36);
		}
}

//package com.pqsoft.util;
//
//import org.apache.log4j.Logger;
//
///**
// * 金融算法工具库
// * @author Bean
// *
// */
//public abstract class FinancialAlgLib {
//	final private static Logger logger = Logger.getLogger(FinancialAlgLib.class);
//    /**
//     * 等额本息计算公式
//     * @param a 现值
//     * @param b 利率
//     * @param c 期次
//     * @param d 每年支付次数
//     * @param e 期初true/期末false
//     * @return
//     */
//    public static double pmt(double a,double b,double c,int d,boolean e){
//    	if(b<=0)return a/c;
//    	       /*a(剩余本金)
//    	       b(利率)
//    	       c(期次)
//    	       d(支付次数)
//    	       d(期初或期末)*/
//    	return a*    b/d   *     Math.pow(1+b/d,c-(e?1:0))    /       (Math.pow(1+b/d,c)-1);
//    }
//    /**
//     * 期末等额本息计算公式
//     * @param a 现值
//     * @param b 利率
//     * @param c 期次
//     * @param d 每年支付次数
//     * @return
//     */
//    public static double pmt(double a,double b,double c,int d){
//    	return pmt(a, b, c, d, false);
//    }
//    /**
//     * 等额本息计算公式默认每年支付12次
//     * @param a 现值
//     * @param b 利率
//     * @param c 期次
//     * @param d 期初true/期末false
//     * @return
//     */
//    public static double pmt(double a,double b,double c,boolean e){
//    	return pmt(a, b, c, 12, e);
//    }
//    /**
//     * 期末等额本息计算公式默认每年支付12次
//     * @param a 现值
//     * @param b 利率
//     * @param c 期次
//     * @return
//     */
//    public static double pmt(double a,double b,double c){
//    	return pmt(a, b, c, 12, false);
//    }
//    
//    
//    public static void main(String[]args){
//    	logger.debug(FinancialAlgLib.pmt(801000,0.07605,36,12,false));
//    	//logger.debug(FinancialAlgLib.pmt(1881000.00,0.06556,48,true));
//    	//logger.debug(Math.pow((1+0.0671),(12-1)));
//    	//logger.debug((Math.pow((1+0.0671),12)*0.0671));
//    	//期末
//    	logger.debug(4839308.55*(0.0671/12)*
//    			          Math.pow(1+0.0671/12,35)   /(Math.pow(1+0.0671/12,36)-1));
//    	//期初
//    	logger.debug(4839308.55*(0.0671/12)*
//		          Math.pow(1+0.0671/12,36)   /(Math.pow(1+0.0671/12,36)-1));
//    }
//}

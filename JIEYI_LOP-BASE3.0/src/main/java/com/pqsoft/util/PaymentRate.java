package com.pqsoft.util;

import java.util.Map;

import org.apache.log4j.Logger;

import com.pqsoft.skyeye.api.Dao;

public class PaymentRate {
	final private static Logger logger = Logger.getLogger(PaymentRate.class);
	/** 半年对应的基准利率. */
    public  double HALF_YEAR_BASE_RATE;
    /** 一年对应的基准利率. */
    public  double ONE_YEAR_BASE_RATE;
    /** 三年对应的基准利率. */
    public  double THREE_YEAR_BASE_RATE;
    /** 五年对应的基准利率. */
    public double FIVE_YEAR_BASE_RATE;
    /** 多于五年对应的基准利率. */
    public double  MORE_YEAR_BASE_RATE ;
    
    public static final int HALF_YEAR = 6;
    public static final int ONE_YEAR = 12;  
    public static final int THREE_YEAR = 36;   
    public static final int FIVE_YEAR = 60;
    
  //初始化利率
	public void PaymentRate() throws Exception {
		PaymentRate payTable = new PaymentRate();
		//RateService  rateService = new RateService();
		Map<String,Object> m = (Map)Dao.selectOne("PayTask.queryNewRate");
		//logger.debug(m.toString());
		setHALF_YEAR_BASE_RATE(Double.valueOf(m.get("SIX_MONTHS").toString()));
		setONE_YEAR_BASE_RATE(Double.valueOf(m.get("ONE_YEAR").toString()));
		setTHREE_YEAR_BASE_RATE(Double.valueOf(m.get("ONE_THREE_YEARS").toString()));
		setFIVE_YEAR_BASE_RATE(Double.valueOf(m.get("THREE_FIVE_YEARS").toString()));
		setMORE_YEAR_BASE_RATE(Double.valueOf(m.get("OVER_FIVE_YEARS").toString()));
	}
	public double getHALF_YEAR_BASE_RATE() {
		return HALF_YEAR_BASE_RATE;
	}
	public void setHALF_YEAR_BASE_RATE(double hALFYEARBASERATE) {
		this.HALF_YEAR_BASE_RATE = hALFYEARBASERATE;
	}
	public double getONE_YEAR_BASE_RATE() {
		return ONE_YEAR_BASE_RATE;
	}
	public void setONE_YEAR_BASE_RATE(double oNEYEARBASERATE) {
		this.ONE_YEAR_BASE_RATE = oNEYEARBASERATE;
	}
	public double getTHREE_YEAR_BASE_RATE() {
		return THREE_YEAR_BASE_RATE;
	}
	public void setTHREE_YEAR_BASE_RATE(double tHREEYEARBASERATE) {
		this.THREE_YEAR_BASE_RATE = tHREEYEARBASERATE;
	}
	public double getFIVE_YEAR_BASE_RATE() {
		return FIVE_YEAR_BASE_RATE;
	}
	public void setFIVE_YEAR_BASE_RATE(double fIVEYEARBASERATE) {
		this.FIVE_YEAR_BASE_RATE = fIVEYEARBASERATE;
	}
	public double getMORE_YEAR_BASE_RATE() {
		return MORE_YEAR_BASE_RATE;
	}
	public void setMORE_YEAR_BASE_RATE(double mOREYEARBASERATE) {
		this.MORE_YEAR_BASE_RATE = mOREYEARBASERATE;
	} 
    
}

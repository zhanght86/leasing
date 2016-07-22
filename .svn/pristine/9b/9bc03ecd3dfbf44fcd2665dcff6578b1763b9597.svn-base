package com.pqsoft.util;



import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 支付表
 * 实体bean
 * @author Bean
 *
 */
public class PaymentTable {

	protected int id;
	protected String paylistCode; // 支付表号
	protected String custID; // 客户ID
	protected String equID; //  设备ID
	protected int paylistSubCode; //  拆分号
	protected String percent; // 拆分比例
	protected double leaseTopric; // 租赁物件总价值
	protected double depositRate; // 租赁保证金（百分比）
	protected double depositValue; // 租赁保证金
	protected double poundageRate; // 租赁手续费（百分比）
	protected double poundageValue; // 租赁手续费
	protected double firstrentRate; // 首期租金（百分比）
	protected double firstrentValue; // 首期租金
	protected double dayPuaccrateRate; // 日罚息率（百分比）
	protected double interUprate; // 利率上浮比例(百分比)
	protected double yearInterest; // 租赁年利率
	protected double RENT_CHARGE_RATE;//年租赁费用IRR计算
	protected int   pay_type;//年租赁费用IRR计算  0天计算与1月计算 2不参与计算
	protected int check_intesrrest ; //是否进行内含租赁利率调整 0 不调整1 调整
	protected double topricSubfirent; // 租赁物件总价值-首期租金
	protected double changeTopricSubfirent; //期次变更融资额
	protected double[] IRRData;
	protected int leaseTerm; // 租赁期数---期限
	protected int leasePeriod; // 租赁周期
	protected Date createDate; // 创建日期
	protected Date startDate; // 起租日期
	protected int status; // 状态 0正常 1作废 2错误 3正常结清 4回购 5转法务 6提前结清 7有计划书
						// 8转让(预定)9退换货(预定) 15转法务 25法务退回 -5转法务处理完毕
	protected String createPerson; // 创建人员
	protected String leadPerson; // 负责人员
	protected String payWay; // 支付方法
	protected String payWayType; // 支付方法状态（标清期初与期末）
	protected int versionCode; // 版本号
	protected Date endDate; // 结束日期
	protected String otherFeeName; // 其他费名称（如车损险、公证费、产权公证费等）
	protected double otherFeeValue; // 其他费对应的金额
	protected Map<Integer,PaymentLine> paymentLines;
	public String totalmonthPrice; // 租金
	public String totalownPrice; // 本金
	public String totalrenPrice; // 利息
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
    
    protected boolean isTQHK;
    protected double changeMoney;
    
    public double getChangeMoney() {
		return changeMoney;
	}

	public void setChangeMoney(double changeMoney) {
		this.changeMoney = changeMoney;
	}

	public boolean getIsTQHK() {
		return isTQHK;
	}

	public void setIsTQHK(boolean isTQHK) {
		this.isTQHK = isTQHK;
	}
    
    public String getCustID() {
		return custID;
	}

	public void setCustID(String custID) {
		this.custID = custID;
	}

	public String getEquID() {
		return equID;
	}

	public void setEquID(String equID) {
		this.equID = equID;
	}

	public double getChangeTopricSubfirent() {
		return changeTopricSubfirent;
	}

	public double[] getIRRData() {
		return IRRData;
	}

	public void setIRRData(double[] iRRData) {
		IRRData = iRRData;
	}

	public void setChangeTopricSubfirent(double changeTopricSubfirent) {
		this.changeTopricSubfirent = changeTopricSubfirent;
	}

	public int getPaylistSubCode() {
		return paylistSubCode;
	}

	public void setPaylistSubCode(int paylistSubCode) {
		this.paylistSubCode = paylistSubCode;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public double getHALF_YEAR_BASE_RATE() {
		return HALF_YEAR_BASE_RATE;
	}

	public void setHALF_YEAR_BASE_RATE(double hALFYEARBASERATE) {
		HALF_YEAR_BASE_RATE = hALFYEARBASERATE;
	}

	public double getONE_YEAR_BASE_RATE() {
		return ONE_YEAR_BASE_RATE;
	}

	public void setONE_YEAR_BASE_RATE(double oNEYEARBASERATE) {
		ONE_YEAR_BASE_RATE = oNEYEARBASERATE;
	}

	public double getTHREE_YEAR_BASE_RATE() {
		return THREE_YEAR_BASE_RATE;
	}

	public void setTHREE_YEAR_BASE_RATE(double tHREEYEARBASERATE) {
		THREE_YEAR_BASE_RATE = tHREEYEARBASERATE;
	}

	public double getFIVE_YEAR_BASE_RATE() {
		return FIVE_YEAR_BASE_RATE;
	}

	public void setFIVE_YEAR_BASE_RATE(double fIVEYEARBASERATE) {
		FIVE_YEAR_BASE_RATE = fIVEYEARBASERATE;
	}

	public double getMORE_YEAR_BASE_RATE() {
		return MORE_YEAR_BASE_RATE;
	}

	public void setMORE_YEAR_BASE_RATE(double mOREYEARBASERATE) {
		MORE_YEAR_BASE_RATE = mOREYEARBASERATE;
	}

	
    
	public String getTotalmonthPrice() {
		return totalmonthPrice;
	}

	public void setTotalmonthPrice(String totalmonthPrice) {
		this.totalmonthPrice = totalmonthPrice;
	}

	public String getTotalownPrice() {
		return totalownPrice;
	}

	public void setTotalownPrice(String totalownPrice) {
		this.totalownPrice = totalownPrice;
	}

	public String getTotalrenPrice() {
		return totalrenPrice;
	}

	public void setTotalrenPrice(String totalrenPrice) {
		this.totalrenPrice = totalrenPrice;
	}

	public int getCheck_intesrrest() {
		return check_intesrrest;
	}

	public void setCheck_intesrrest(int checkIntesrrest) {
		check_intesrrest = checkIntesrrest;
	}

	//用于支付表变更
	protected int changePeriodNum = 1;
//	protected Map<Date,Double> changeYearInterest;

	public PaymentTable() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaylistCode() {
		return paylistCode;
	}

	public void setPaylistCode(String paylistCode) {
		this.paylistCode = paylistCode;
	}

	public double getLeaseTopric() {
		return leaseTopric;
	}

	public void setLeaseTopric(double leaseTopric) {
		this.leaseTopric = leaseTopric;
	}

	public double getDepositRate() {
		return depositRate;
	}

	public void setDepositRate(double depositRate) {
		this.depositRate = depositRate;
	}

	public double getDepositValue() {
		return depositValue;
	}

	public void setDepositValue(double depositValue) {
		this.depositValue = depositValue;
	}

	public double getPoundageRate() {
		return poundageRate;
	}

	public void setPoundageRate(double poundageRate) {
		this.poundageRate = poundageRate;
	}

	public double getPoundageValue() {
		return poundageValue;
	}

	public void setPoundageValue(double poundageValue) {
		this.poundageValue = poundageValue;
	}

	public double getFirstrentRate() {
		return firstrentRate;
	}

	public void setFirstrentRate(double firstrentRate) {
		this.firstrentRate = firstrentRate;
	}

	public double getFirstrentValue() {
		return firstrentValue;
	}

	public void setFirstrentValue(double firstrentValue) {
		this.firstrentValue = firstrentValue;
	}

	public double getDayPuaccrateRate() {
		return dayPuaccrateRate;
	}

	public void setDayPuaccrateRate(double dayPuaccrateRate) {
		this.dayPuaccrateRate = dayPuaccrateRate;
	}

	public double getInterUprate() {
		return interUprate;
	}

	public void setInterUprate(double interUprate) {
		this.interUprate = interUprate;
	}

	public double getYearInterest() {
		return yearInterest;
	}

	public void setYearInterest(double yearInterest) {
		this.yearInterest = yearInterest;
	}

	public double getTopricSubfirent() {
		return topricSubfirent;
	}

	public void setTopricSubfirent(double topricSubfirent) {
		this.topricSubfirent = topricSubfirent;
	}

	public int getLeaseTerm() {
		return leaseTerm;
	}

	public void setLeaseTerm(int leaseTerm) {
		this.leaseTerm = leaseTerm;
	}

	public int getLeasePeriod() {
		return leasePeriod;
	}

	public void setLeasePeriod(int leasePeriod) {
		this.leasePeriod = leasePeriod;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		if(startDate!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			startDate = c.getTime();
		}
		this.startDate = startDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getLeadPerson() {
		return leadPerson;
	}

	public void setLeadPerson(String leadPerson) {
		this.leadPerson = leadPerson;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOtherFeeName() {
		return otherFeeName;
	}

	public void setOtherFeeName(String otherFeeName) {
		this.otherFeeName = otherFeeName;
	}

	public double getOtherFeeValue() {
		return otherFeeValue;
	}

	public void setOtherFeeValue(double otherFeeValue) {
		this.otherFeeValue = otherFeeValue;
	}

	public Map<Integer,PaymentLine> getPaymentLines() {
		return paymentLines;
	}

	public void setPaymentLines(Map<Integer,PaymentLine> paymentLines) {
		this.paymentLines = paymentLines;
	}

	public int getChangePeriodNum() {
		return changePeriodNum;
	}

	public void setChangePeriodNum(int changePeriodNum) {
		this.changePeriodNum = changePeriodNum;
	}
	public String getPayWayType() {
		return payWayType;
	}

	public void setPayWayType(String payWayType) {
		this.payWayType = payWayType;
	}
//	public Map<Date, Double> getChangeYearInterest() {
//		return changeYearInterest;
//	}
//
//	public void setChangeYearInterest(Map<Date, Double> changeYearInterest) {
//		this.changeYearInterest = changeYearInterest;
//	}

	public double getRENT_CHARGE_RATE() {
		return RENT_CHARGE_RATE;
	}

	public int getPay_type() {
		return pay_type;
	}

	public void setPay_type(int payType) {
		pay_type = payType;
	}

	public void setRENT_CHARGE_RATE(double rENTCHARGERATE) {
		RENT_CHARGE_RATE = rENTCHARGERATE;
	}
	
}

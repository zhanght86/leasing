package com.pqsoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 支付表还款计划
 * 实体bean
 * @author Bean
 *
 */
public class PaymentLine {

	private int id; //
	private String equID;
	private String custID;
	private Date payDate; // 支付日期
	private double monthPrice; // 租金(客户实际租金)
	private double monthPricePMT; // 租金PMT
	private double ownPrice; // 本金
	private double renPrice; // 利息
	private double irrrate; // 内含利率
	private double price; // 项目费用
	private String precent; // 拆分百分比
	private double splitMoney; // 拆分金额
	private double payMoney; // 实收金额
	private double payFlag; // 是否核销
	private int paylistSubCode;//子支付表标识
	private String remark; // 描述
	SimpleDateFormat  dsf = new SimpleDateFormat("yyyy-MM-dd");
	private int locked; // 锁定
	private int payId; // 外键- -对应支付表
	private double lastPrice; // 剩余本金
	private int periodNum; // 期数
	private String itemName;//项目名称
	private int type;//项目类型
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	

	public double getIrrrate() {
		return irrrate;
	}

	public void setIrrrate(double irrrate) {
		this.irrrate = irrrate;
	}

	public PaymentLine() {
	}

	public String getEquID() {
		return equID;
	}

	public void setEquID(String equID) {
		this.equID = equID;
	}

	public String getCustID() {
		return custID;
	}

	public void setCustID(String custID) {
		this.custID = custID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public double getSplitMoney() {
		return splitMoney;
	}

	public void setSplitMoney(double splitMoney) {
		this.splitMoney = splitMoney;
	}

	public double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public double getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(double payFlag) {
		this.payFlag = payFlag;
	}

	public Date getPayDate() throws ParseException {
		return null==payDate?payDate:new java.sql.Date(dsf.parse(dsf.format(payDate)).getTime());
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public double getMonthPrice() {
		return monthPrice;
	}

	public void setMonthPrice(double monthPrice) {
		this.monthPrice = monthPrice;
	}
	
	public double getMonthPricePMT() {
		return monthPricePMT;
	}
	
	public void setMonthPricePMT(double monthPricePMT) {
		this.monthPricePMT = monthPricePMT;
	}

	public double getOwnPrice() {
		return ownPrice;
	}

	public void setOwnPrice(double ownPrice) {
		this.ownPrice = ownPrice;
	}

	public double getRenPrice() {
		return renPrice;
	}

	public void setRenPrice(double renPrice) {
		this.renPrice = renPrice;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getPayId() {
		return payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public int getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(int periodNum) {
		this.periodNum = periodNum;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getPaylistSubCode() {
		return paylistSubCode;
	}

	public void setPaylistSubCode(int paylistSubCode) {
		this.paylistSubCode = paylistSubCode;
	}

	public String getPrecent() {
		return precent;
	}

	public void setPrecent(String precent) {
		this.precent = precent;
	}



}

package com.pqsoft.util;

/**
 * 支付表明细实体Bean
 * 
 * @author King 2014-4-15
 */
public class RentPlayDetail {
	private int id;// ID
	private String equipment_id;// 设备id--暂时无用
	private String pay_date;// 约定还款日
	private int period_num;// 期次
	private String item_name;// 项目名称
	private double item_money;// 项目金额
	private int item_flag;// 项目类型[1：首期款；2：租金；3：DB保证金；4：其他费用]
	private String split_rate;// 拆分比例
	private double split_money;// 拆分金额
	private double paid_money;// 实收金额
	private int pay_id;// 支付表ID
	private int type;// 备用：类型
	private String remark;// 备注
	private String item_name_en;// 
	private double irr;// 租赁内含利率
	private int locked_flag;// 锁定标识（只有还款计划书有锁定标识其他费用没有1是锁定0是不锁定）
	private String temp_cld1;// 
	private String temp_cld2;// 
	private String temp_cld3;// 设备回购
	private String temp_cld4;// 设备回购利息减免备份

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 设备id--暂时无用
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getEquipment_id() {
		return equipment_id;
	}

	/**
	 * 设备id--暂时无用
	 * 
	 * @param equipmentId
	 * @author:King 2014-4-15
	 */
	public void setEquipment_id(String equipmentId) {
		equipment_id = equipmentId;
	}

	/**
	 * 约定还款日
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public String getPay_date() {
		return pay_date;
	}

	/**
	 * 约定还款日
	 * 
	 * @param payDate
	 * @author:King 2014-4-16
	 */
	public void setPay_date(String payDate) {
		pay_date = payDate;
	}

	/**
	 * 期次
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public int getPeriod_num() {
		return period_num;
	}

	/**
	 * 期次
	 * 
	 * @param periodNum
	 * @author:King 2014-4-16
	 */
	public void setPeriod_num(int periodNum) {
		period_num = periodNum;
	}

	/**
	 * 项目名称
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public String getItem_name() {
		return item_name;
	}

	/**
	 * 项目名称
	 * 
	 * @param itemName
	 * @author:King 2014-4-16
	 */
	public void setItem_name(String itemName) {
		item_name = itemName;
	}

	/**
	 * 项目金额
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public double getItem_money() {
		return item_money;
	}

	/**
	 * 项目金额
	 * 
	 * @param itemMoney
	 * @author:King 2014-4-16
	 */
	public void setItem_money(double itemMoney) {
		item_money = itemMoney;
	}

	/**
	 * 项目类型[1：首期款；2：租金；3：DB保证金；4：其他费用]
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public int getItem_flag() {
		return item_flag;
	}

	/**
	 * 项目类型[1：首期款；2：租金；3：DB保证金；4：其他费用]
	 * 
	 * @param itemFlag
	 * @author:King 2014-4-16
	 */
	public void setItem_flag(int itemFlag) {
		item_flag = itemFlag;
	}

	/**
	 * 拆分比例
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public String getSplit_rate() {
		return split_rate;
	}

	/**
	 * 拆分比例
	 * 
	 * @param splitRate
	 * @author:King 2014-4-16
	 */
	public void setSplit_rate(String splitRate) {
		split_rate = splitRate;
	}

	/**
	 * 拆分金额
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public double getSplit_money() {
		return split_money;
	}

	/**
	 * 拆分金额
	 * 
	 * @param splitMoney
	 * @author:King 2014-4-16
	 */
	public void setSplit_money(double splitMoney) {
		split_money = splitMoney;
	}

	/**
	 * 实收金额
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public double getPaid_money() {
		return paid_money;
	}

	/**
	 * 实收金额
	 * 
	 * @param paidMoney
	 * @author:King 2014-4-16
	 */
	public void setPaid_money(double paidMoney) {
		paid_money = paidMoney;
	}

	/**
	 * 支付表ID
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public int getPay_id() {
		return pay_id;
	}

	/**
	 * 支付表ID
	 * 
	 * @param payId
	 * @author:King 2014-4-16
	 */
	public void setPay_id(int payId) {
		pay_id = payId;
	}

	/**
	 * 备用：类型
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public int getType() {
		return type;
	}

	/**
	 * 备用：类型
	 * 
	 * @param type
	 * @author:King 2014-4-16
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 备注
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * 
	 * @param remark
	 * @author:King 2014-4-16
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getItem_name_en() {
		return item_name_en;
	}

	public void setItem_name_en(String itemNameEn) {
		item_name_en = itemNameEn;
	}

	/**
	 * 租赁内含利率
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public double getIrr() {
		return irr;
	}

	/**
	 * 租赁内含利率
	 * 
	 * @param irr
	 * @author:King 2014-4-16
	 */
	public void setIrr(double irr) {
		this.irr = irr;
	}

	/**
	 * 锁定标识（只有还款计划书有锁定标识其他费用没有1是锁定0是不锁定）
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public int getLocked_flag() {
		return locked_flag;
	}

	/**
	 * 锁定标识（只有还款计划书有锁定标识其他费用没有1是锁定0是不锁定）
	 * 
	 * @param lockedFlag
	 * @author:King 2014-4-16
	 */
	public void setLocked_flag(int lockedFlag) {
		locked_flag = lockedFlag;
	}

	public String getTemp_cld1() {
		return temp_cld1;
	}

	public void setTemp_cld1(String tempCld1) {
		temp_cld1 = tempCld1;
	}

	public String getTemp_cld2() {
		return temp_cld2;
	}

	public void setTemp_cld2(String tempCld2) {
		temp_cld2 = tempCld2;
	}

	/**
	 * 设备回购
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public String getTemp_cld3() {
		return temp_cld3;
	}

	/**
	 * 设备回购
	 * 
	 * @param tempCld3
	 * @author:King 2014-4-16
	 */
	public void setTemp_cld3(String tempCld3) {
		temp_cld3 = tempCld3;
	}

	/**
	 * 设备回购利息减免备份
	 * 
	 * @return
	 * @author:King 2014-4-16
	 */
	public String getTemp_cld4() {
		return temp_cld4;
	}

	/**
	 * 设备回购利息减免备份
	 * 
	 * @param tempCld4
	 * @author:King 2014-4-16
	 */
	public void setTemp_cld4(String tempCld4) {
		temp_cld4 = tempCld4;
	}
}

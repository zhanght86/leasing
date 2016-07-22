package com.pqsoft.util;

/**
 * 支付表表头信息实体 Bean
 * 
 * @author King 2014-4-15
 */
public class RentPlanHead {
	private int id;// ID
	private String paylist_code;// 还款计划编号
	private double lease_topric;// 设备总额
	private double deposit_rate;// 租赁保证金（百分比）
	private double deposit_value;// 租赁保证金
	private double poundage_rate;// 租赁管理费（百分比）
	private double poundage_value;// 租赁管理费
	private double firstrent_rate;// 首期租金（百分比）
	private double firstrent_value;// 首期租金--起租租金
	private double day_puaccrate_rate;// 日罚息率（百分比）
	private double inter_uprate;// 上浮比例
	private double year_interest;// 年利率
	private double topric_subfirent;// 融资额
	private int lease_term;// 租赁期数
	private int project_id;// 项目id--FIL_PROJECT_HEAD.ID
	private int lease_period;// 租赁周期
	private String create_date;// 创建时间
	private String start_date;// 起租日期
	private int status;// 数据字典 type 还款计划状态
	private String create_person;// 创建人
	private String lead_person;// 负责人员
	private String pay_way;// 支付方式 期初、期末等额
	private int version_code;// 版本号
	private String end_date;// 还款计划结束日期即租赁到期日
	private String change_start_flag;// 支付表日期调整标记 空||0 表示未曾调整过 1表示曾经调整过 只允许调整一次
	private int bank_id;// 银行id
	private int bank_flag;// 银行标示 0 系统账户,1 其他账户
	private String ex_status;// 异常标示 0或NULL 正常 1异常原因
	private String ex_remark;// 异常原因
	private int nostart_status;// 未起租标示 0或NULL 正常 1未起租原因
	private String nostart_remark;// 未起租原因
	private int legal_status;// 转法务状态1：手动转；0：自动转
	private int pay_status;// 回款申请状态：0未申请回款1已申请回款2已全回款
	private String year_type;// 是否固定年利率0为固定1不固定
	private double rent_charge_rate;// 年租赁费率
	private int management_feetype;// 管理费缴费方式：1:按年支付，2：租赁年限支付
	private double company_percent;// 厂商保证金比例
	private double company_fee;// 厂商保证金
	private String calculate_way;// 1（PMT算法）2（IRR算法）3（单利算法）
	private String pay_type;// 年租赁费用IRR计算 0天计算与1月计算 2不参与计算
	private String change_type;// 支付表变更类型
	private String update_status;// 支付表变更审核状态0：未做变更出来，1：审核中，2：驳回，3：审核通过
	private String first_payment_time;// 首付款流程确认日期——立项流程首付款验证流转日期
	private String start_confirm_date;// 起租确认日
	private double first_money_all;// 首期付款合计
	private double money_all;// 租金合计
	private String temp_cld;//
	private double internal_rate;// 内部收益率
	private String poundage_way;// 保证金冲抵方式
	private String deal_mode;// 租赁期满处理方式(数据字典CODE)
	private String scheme_clob;// 项目方案明细 json 用于查看
	private double poundage_price;// 手续费金额
	private String firstpaydate;// 首期款约定付款日期
	private double insurance_percent;// 保险费比例
	private String day_puaccrate_type;// 罚息计算方式
	private String lxtqsq;// 利息提前收取
	private double gdlx;// 固定利息
	private double DISCOUNT_MONEY;// 贴息金额
	private String discount_type;// 贴息方式
	private String repayment_date;// 还租日
	private int billing_agreement_id;// 开票协议ID（设备表先有开票协议，然后起租申请通过后反更回来）
	private String platform_type;// 项目业务类型（对应数据字典）。值取CODE
	private int sup_id;// 供应商ID
	private String sup_shortname;// 供应商名称简称
	private String company_id;// 厂商ID
	private String company_name;// 厂商名称简称

	/**
	 * ID
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getId() {
		return id;
	}

	/**
	 * ID
	 * 
	 * @param id
	 * @author:King 2014-4-15
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 支付表编号
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getPaylist_code() {
		return paylist_code;
	}

	/**
	 * 支付表编号
	 * 
	 * @param paylistCode
	 * @author:King 2014-4-15
	 */
	public void setPaylist_code(String paylistCode) {
		paylist_code = paylistCode;
	}

	/**
	 * 设备总额
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getLease_topric() {
		return lease_topric;
	}

	/**
	 * 设备总额
	 * 
	 * @param leaseTopric
	 * @author:King 2014-4-15
	 */
	public void setLease_topric(double leaseTopric) {
		lease_topric = leaseTopric;
	}

	/**
	 * 租赁保证金（百分比）
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getDeposit_rate() {
		return deposit_rate;
	}

	/**
	 * 租赁保证金（百分比）
	 * 
	 * @param depositRate
	 * @author:King 2014-4-15
	 */
	public void setDeposit_rate(double depositRate) {
		deposit_rate = depositRate;
	}

	/**
	 * 租赁保证金
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getDeposit_value() {
		return deposit_value;
	}

	/**
	 * 租赁保证金
	 * 
	 * @param depositValue
	 * @author:King 2014-4-15
	 */
	public void setDeposit_value(double depositValue) {
		deposit_value = depositValue;
	}

	/**
	 * 租赁管理费（百分比）
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getPoundage_rate() {
		return poundage_rate;
	}

	/**
	 * 租赁管理费（百分比）
	 * 
	 * @param poundageRate
	 * @author:King 2014-4-15
	 */
	public void setPoundage_rate(double poundageRate) {
		poundage_rate = poundageRate;
	}

	/**
	 * 租赁管理费
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getPoundage_value() {
		return poundage_value;
	}

	/**
	 * 租赁管理费
	 * 
	 * @param poundageValue
	 * @author:King 2014-4-15
	 */
	public void setPoundage_value(double poundageValue) {
		poundage_value = poundageValue;
	}

	/**
	 * 首期租金（百分比）
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getFirstrent_rate() {
		return firstrent_rate;
	}

	/**
	 * 首期租金（百分比）
	 * 
	 * @param firstrentRate
	 * @author:King 2014-4-15
	 */
	public void setFirstrent_rate(double firstrentRate) {
		firstrent_rate = firstrentRate;
	}

	/**
	 * 首期租金--起租租金
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getFirstrent_value() {
		return firstrent_value;
	}

	/**
	 * 首期租金--起租租金
	 * 
	 * @param firstrentValue
	 * @author:King 2014-4-15
	 */
	public void setFirstrent_value(double firstrentValue) {
		firstrent_value = firstrentValue;
	}

	/**
	 * 日罚息率（百分比）
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getDay_puaccrate_rate() {
		return day_puaccrate_rate;
	}

	/**
	 * 日罚息率（百分比）
	 * 
	 * @param dayPuaccrateRate
	 * @author:King 2014-4-15
	 */
	public void setDay_puaccrate_rate(double dayPuaccrateRate) {
		day_puaccrate_rate = dayPuaccrateRate;
	}

	/**
	 * 上浮比例
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getInter_uprate() {
		return inter_uprate;
	}

	/**
	 * 上浮比例
	 * 
	 * @param interUprate
	 * @author:King 2014-4-15
	 */
	public void setInter_uprate(double interUprate) {
		inter_uprate = interUprate;
	}

	/**
	 * 年利率
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getYear_interest() {
		return year_interest;
	}

	/**
	 * 年利率
	 * 
	 * @param yearInterest
	 * @author:King 2014-4-15
	 */
	public void setYear_interest(double yearInterest) {
		year_interest = yearInterest;
	}

	/**
	 * 融资额
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getTopric_subfirent() {
		return topric_subfirent;
	}

	/**
	 * 融资额
	 * 
	 * @param topricSubfirent
	 * @author:King 2014-4-15
	 */
	public void setTopric_subfirent(double topricSubfirent) {
		topric_subfirent = topricSubfirent;
	}

	/**
	 * 租赁期数
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getLease_term() {
		return lease_term;
	}

	/**
	 * 租赁期数
	 * 
	 * @param leaseTerm
	 * @author:King 2014-4-15
	 */
	public void setLease_term(int leaseTerm) {
		lease_term = leaseTerm;
	}

	/**
	 * 项目id--FIL_PROJECT_HEAD.ID
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getProject_id() {
		return project_id;
	}

	/**
	 * 项目id--FIL_PROJECT_HEAD.ID
	 * 
	 * @param projectId
	 * @author:King 2014-4-15
	 */
	public void setProject_id(int projectId) {
		project_id = projectId;
	}

	/**
	 * 租赁周期
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getLease_period() {
		return lease_period;
	}

	/**
	 * 租赁周期
	 * 
	 * @param leasePeriod
	 * @author:King 2014-4-15
	 */
	public void setLease_period(int leasePeriod) {
		lease_period = leasePeriod;
	}

	/**
	 * 创建时间
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getCreate_date() {
		return create_date;
	}

	/**
	 * 创建时间
	 * 
	 * @param createDate
	 * @author:King 2014-4-15
	 */
	public void setCreate_date(String createDate) {
		create_date = createDate;
	}

	/**
	 * 起租日期
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getStart_date() {
		return start_date;
	}

	/**
	 * 起租日期
	 * 
	 * @param startDate
	 * @author:King 2014-4-15
	 */
	public void setStart_date(String startDate) {
		start_date = startDate;
	}

	/**
	 * 数据字典 type 还款计划状态
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 数据字典 type 还款计划状态
	 * 
	 * @param status
	 * @author:King 2014-4-15
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 创建人
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getCreate_person() {
		return create_person;
	}

	/**
	 * 创建人
	 * 
	 * @param createPerson
	 * @author:King 2014-4-15
	 */
	public void setCreate_person(String createPerson) {
		create_person = createPerson;
	}

	/**
	 * 负责人员
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getLead_person() {
		return lead_person;
	}

	/**
	 * 负责人员
	 * 
	 * @param leadPerson
	 * @author:King 2014-4-15
	 */
	public void setLead_person(String leadPerson) {
		lead_person = leadPerson;
	}

	/**
	 * 支付方式 期初、期末等额
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getPay_way() {
		return pay_way;
	}

	/**
	 * 支付方式 期初、期末等额
	 * 
	 * @param payWay
	 * @author:King 2014-4-15
	 */
	public void setPay_way(String payWay) {
		pay_way = payWay;
	}

	/**
	 * 版本号
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getVersion_code() {
		return version_code;
	}

	/**
	 * 版本号
	 * 
	 * @param versionCode
	 * @author:King 2014-4-15
	 */
	public void setVersion_code(int versionCode) {
		version_code = versionCode;
	}

	/**
	 * 还款计划结束日期即租赁到期日
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getEnd_date() {
		return end_date;
	}

	/**
	 * 还款计划结束日期即租赁到期日
	 * 
	 * @param endDate
	 * @author:King 2014-4-15
	 */
	public void setEnd_date(String endDate) {
		end_date = endDate;
	}

	/**
	 * 支付表日期调整标记 空||0 表示未曾调整过 1表示曾经调整过 只允许调整一次
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getChange_start_flag() {
		return change_start_flag;
	}

	/**
	 * 支付表日期调整标记 空||0 表示未曾调整过 1表示曾经调整过 只允许调整一次
	 * 
	 * @param changeStartFlag
	 * @author:King 2014-4-15
	 */
	public void setChange_start_flag(String changeStartFlag) {
		change_start_flag = changeStartFlag;
	}

	/**
	 * 银行id
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getBank_id() {
		return bank_id;
	}

	/**
	 * 银行id
	 * 
	 * @param bankId
	 * @author:King 2014-4-15
	 */
	public void setBank_id(int bankId) {
		bank_id = bankId;
	}

	/**
	 * 银行标示 0 系统账户,1 其他账户
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getBank_flag() {
		return bank_flag;
	}

	/**
	 * 银行标示 0 系统账户,1 其他账户
	 * 
	 * @param bankFlag
	 * @author:King 2014-4-15
	 */
	public void setBank_flag(int bankFlag) {
		bank_flag = bankFlag;
	}

	/**
	 * 异常标示 0或NULL 正常 1异常原因
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getEx_status() {
		return ex_status;
	}

	/**
	 * 异常标示 0或NULL 正常 1异常原因
	 * 
	 * @param exStatus
	 * @author:King 2014-4-15
	 */
	public void setEx_status(String exStatus) {
		ex_status = exStatus;
	}

	/**
	 * 异常原因
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getEx_remark() {
		return ex_remark;
	}

	/**
	 * 异常原因
	 * 
	 * @param exRemark
	 * @author:King 2014-4-15
	 */
	public void setEx_remark(String exRemark) {
		ex_remark = exRemark;
	}

	/**
	 * 未起租标示 0或NULL 正常 1未起租原因
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getNostart_status() {
		return nostart_status;
	}

	/**
	 * 未起租标示 0或NULL 正常 1未起租原因
	 * 
	 * @param nostartStatus
	 * @author:King 2014-4-15
	 */
	public void setNostart_status(int nostartStatus) {
		nostart_status = nostartStatus;
	}

	/**
	 * 未起租原因
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getNostart_remark() {
		return nostart_remark;
	}

	/**
	 * 未起租原因
	 * 
	 * @param nostartRemark
	 * @author:King 2014-4-15
	 */
	public void setNostart_remark(String nostartRemark) {
		nostart_remark = nostartRemark;
	}

	/**
	 * 转法务状态1：手动转；0：自动转
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getLegal_status() {
		return legal_status;
	}

	/**
	 * 转法务状态1：手动转；0：自动转
	 * 
	 * @param legalStatus
	 * @author:King 2014-4-15
	 */
	public void setLegal_status(int legalStatus) {
		legal_status = legalStatus;
	}

	/**
	 * 回款申请状态：0未申请回款1已申请回款2已全回款
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getPay_status() {
		return pay_status;
	}

	/**
	 * 回款申请状态：0未申请回款1已申请回款2已全回款
	 * 
	 * @param payStatus
	 * @author:King 2014-4-15
	 */
	public void setPay_status(int payStatus) {
		pay_status = payStatus;
	}

	/**
	 * 是否固定年利率0为固定1不固定
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getYear_type() {
		return year_type;
	}

	/**
	 * 是否固定年利率0为固定1不固定
	 * 
	 * @param yearType
	 * @author:King 2014-4-15
	 */
	public void setYear_type(String yearType) {
		year_type = yearType;
	}

	/**
	 * 年租赁费率
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getRent_charge_rate() {
		return rent_charge_rate;
	}

	/**
	 * 年租赁费率
	 * 
	 * @param rentChargeRate
	 * @author:King 2014-4-15
	 */
	public void setRent_charge_rate(double rentChargeRate) {
		rent_charge_rate = rentChargeRate;
	}

	/**
	 * 管理费缴费方式：1:按年支付，2：租赁年限支付
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getManagement_feetype() {
		return management_feetype;
	}

	/**
	 * 管理费缴费方式：1:按年支付，2：租赁年限支付
	 * 
	 * @param managementFeetype
	 * @author:King 2014-4-15
	 */
	public void setManagement_feetype(int managementFeetype) {
		management_feetype = managementFeetype;
	}

	/**
	 * 厂商保证金比例
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getCompany_percent() {
		return company_percent;
	}

	/**
	 * 厂商保证金比例
	 * 
	 * @param companyPercent
	 * @author:King 2014-4-15
	 */
	public void setCompany_percent(double companyPercent) {
		company_percent = companyPercent;
	}

	/**
	 * 厂商保证金
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getCompany_fee() {
		return company_fee;
	}

	/**
	 * 厂商保证金
	 * 
	 * @param companyFee
	 * @author:King 2014-4-15
	 */
	public void setCompany_fee(double companyFee) {
		company_fee = companyFee;
	}

	/**
	 * 1（PMT算法）2（IRR算法）3（单利算法）
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getCalculate_way() {
		return calculate_way;
	}

	/**
	 * 1（PMT算法）2（IRR算法）3（单利算法）
	 * 
	 * @param calculateWay
	 * @author:King 2014-4-15
	 */
	public void setCalculate_way(String calculateWay) {
		calculate_way = calculateWay;
	}

	/**
	 * 年租赁费用IRR计算 0天计算与1月计算 2不参与计算
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getPay_type() {
		return pay_type;
	}

	/**
	 * 年租赁费用IRR计算 0天计算与1月计算 2不参与计算
	 * 
	 * @param payType
	 * @author:King 2014-4-15
	 */
	public void setPay_type(String payType) {
		pay_type = payType;
	}

	/**
	 * 支付表变更类型
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getChange_type() {
		return change_type;
	}

	/**
	 * 支付表变更类型
	 * 
	 * @param changeType
	 * @author:King 2014-4-15
	 */
	public void setChange_type(String changeType) {
		change_type = changeType;
	}

	/**
	 * 支付表变更审核状态0：未做变更出来，1：审核中，2：驳回，3：审核通过
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getUpdate_status() {
		return update_status;
	}

	/**
	 * 支付表变更审核状态0：未做变更出来，1：审核中，2：驳回，3：审核通过
	 * 
	 * @param updateStatus
	 * @author:King 2014-4-15
	 */
	public void setUpdate_status(String updateStatus) {
		update_status = updateStatus;
	}

	/**
	 * 首付款流程确认日期——立项流程首付款验证流转日期
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getFirst_payment_time() {
		return first_payment_time;
	}

	/**
	 * 首付款流程确认日期——立项流程首付款验证流转日期
	 * 
	 * @param firstPaymentTime
	 * @author:King 2014-4-15
	 */
	public void setFirst_payment_time(String firstPaymentTime) {
		first_payment_time = firstPaymentTime;
	}

	/**
	 * 起租确认日
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getStart_confirm_date() {
		return start_confirm_date;
	}

	/**
	 * 起租确认日
	 * 
	 * @param startConfirmDate
	 * @author:King 2014-4-15
	 */
	public void setStart_confirm_date(String startConfirmDate) {
		start_confirm_date = startConfirmDate;
	}

	/**
	 * 首期付款合计
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getFirst_money_all() {
		return first_money_all;
	}

	/**
	 * 首期付款合计
	 * 
	 * @param firstMoneyAll
	 * @author:King 2014-4-15
	 */
	public void setFirst_money_all(double firstMoneyAll) {
		first_money_all = firstMoneyAll;
	}

	/**
	 * 租金合计
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getMoney_all() {
		return money_all;
	}

	/**
	 * 租金合计
	 * 
	 * @param moneyAll
	 * @author:King 2014-4-15
	 */
	public void setMoney_all(double moneyAll) {
		money_all = moneyAll;
	}

	public String getTemp_cld() {
		return temp_cld;
	}

	public void setTemp_cld(String tempCld) {
		temp_cld = tempCld;
	}

	/**
	 * 内部收益率
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getInternal_rate() {
		return internal_rate;
	}

	/**
	 * 内部收益率
	 * 
	 * @param internalRate
	 * @author:King 2014-4-15
	 */
	public void setInternal_rate(double internalRate) {
		internal_rate = internalRate;
	}

	/**
	 * 保证金冲抵方式
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getPoundage_way() {
		return poundage_way;
	}

	/**
	 * 保证金冲抵方式
	 * 
	 * @param poundageWay
	 * @author:King 2014-4-15
	 */
	public void setPoundage_way(String poundageWay) {
		poundage_way = poundageWay;
	}

	/**
	 * 租赁期满处理方式(数据字典CODE)
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getDeal_mode() {
		return deal_mode;
	}

	/**
	 * 租赁期满处理方式(数据字典CODE)
	 * 
	 * @param dealMode
	 * @author:King 2014-4-15
	 */
	public void setDeal_mode(String dealMode) {
		deal_mode = dealMode;
	}

	/**
	 * 项目方案明细 json 用于查看
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getScheme_clob() {
		return scheme_clob;
	}

	/**
	 * 项目方案明细 json 用于查看
	 * 
	 * @param schemeClob
	 * @author:King 2014-4-15
	 */
	public void setScheme_clob(String schemeClob) {
		scheme_clob = schemeClob;
	}

	/**
	 * 手续费金额
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getPoundage_price() {
		return poundage_price;
	}

	/**
	 * 手续费金额
	 * 
	 * @param poundagePrice
	 * @author:King 2014-4-15
	 */
	public void setPoundage_price(double poundagePrice) {
		poundage_price = poundagePrice;
	}

	/**
	 * 首期款约定付款日期
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getFirstpaydate() {
		return firstpaydate;
	}

	/**
	 * 首期款约定付款日期
	 * 
	 * @param firstpaydate
	 * @author:King 2014-4-15
	 */
	public void setFirstpaydate(String firstpaydate) {
		this.firstpaydate = firstpaydate;
	}

	/**
	 * 保险费比例
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getInsurance_percent() {
		return insurance_percent;
	}

	/**
	 * 保险费比例
	 * 
	 * @param insurancePercent
	 * @author:King 2014-4-15
	 */
	public void setInsurance_percent(double insurancePercent) {
		insurance_percent = insurancePercent;
	}

	/**
	 * 罚息计算方式
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getDay_puaccrate_type() {
		return day_puaccrate_type;
	}

	/**
	 * 罚息计算方式
	 * 
	 * @param dayPuaccrateType
	 * @author:King 2014-4-15
	 */
	public void setDay_puaccrate_type(String dayPuaccrateType) {
		day_puaccrate_type = dayPuaccrateType;
	}

	/**
	 * 利息提前收取
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getLxtqsq() {
		return lxtqsq;
	}

	/**
	 * 利息提前收取
	 * 
	 * @param lxtqsq
	 * @author:King 2014-4-15
	 */
	public void setLxtqsq(String lxtqsq) {
		this.lxtqsq = lxtqsq;
	}

	/**
	 * 固定利息
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getGdlx() {
		return gdlx;
	}

	/**
	 * 固定利息
	 * 
	 * @param gdlx
	 * @author:King 2014-4-15
	 */
	public void setGdlx(double gdlx) {
		this.gdlx = gdlx;
	}

	/**
	 * 贴息金额
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public double getDISCOUNT_MONEY() {
		return DISCOUNT_MONEY;
	}

	/**
	 * 贴息金额
	 * 
	 * @param discountJe
	 * @author:King 2014-4-15
	 */
	public void setDISCOUNT_MONEY(double discountJe) {
		DISCOUNT_MONEY = discountJe;
	}

	/**
	 * 贴息方式
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getDiscount_type() {
		return discount_type;
	}

	/**
	 * 贴息方式
	 * 
	 * @param discountType
	 * @author:King 2014-4-15
	 */
	public void setDiscount_type(String discountType) {
		discount_type = discountType;
	}

	/**
	 * 还租日
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getRepayment_date() {
		return repayment_date;
	}

	/**
	 * 还租日
	 * 
	 * @param repaymentDate
	 * @author:King 2014-4-15
	 */
	public void setRepayment_date(String repaymentDate) {
		repayment_date = repaymentDate;
	}

	/**
	 * 开票协议ID（设备表先有开票协议，然后起租申请通过后反更回来）
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getBilling_agreement_id() {
		return billing_agreement_id;
	}

	/**
	 * 开票协议ID（设备表先有开票协议，然后起租申请通过后反更回来）
	 * 
	 * @param billingAgreementId
	 * @author:King 2014-4-15
	 */
	public void setBilling_agreement_id(int billingAgreementId) {
		billing_agreement_id = billingAgreementId;
	}

	/**
	 * 项目业务类型（对应数据字典）。值取CODE
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getPlatform_type() {
		return platform_type;
	}

	/**
	 * 项目业务类型（对应数据字典）。值取CODE
	 * 
	 * @param platformType
	 * @author:King 2014-4-15
	 */
	public void setPlatform_type(String platformType) {
		platform_type = platformType;
	}

	/**
	 * 供应商ID
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public int getSup_id() {
		return sup_id;
	}

	/**
	 * 供应商ID
	 * 
	 * @param supId
	 * @author:King 2014-4-15
	 */
	public void setSup_id(int supId) {
		sup_id = supId;
	}

	/**
	 * 供应商名称简称
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getSup_shortname() {
		return sup_shortname;
	}

	/**
	 * 供应商名称简称
	 * 
	 * @param supShortname
	 * @author:King 2014-4-15
	 */
	public void setSup_shortname(String supShortname) {
		sup_shortname = supShortname;
	}

	/**
	 * 厂商ID
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getCompany_id() {
		return company_id;
	}

	/**
	 * 厂商ID
	 * 
	 * @param companyId
	 * @author:King 2014-4-15
	 */
	public void setCompany_id(String companyId) {
		company_id = companyId;
	}

	/**
	 * 厂商名称简称
	 * 
	 * @return
	 * @author:King 2014-4-15
	 */
	public String getCompany_name() {
		return company_name;
	}

	/**
	 * 厂商名称简称
	 * 
	 * @param companyName
	 * @author:King 2014-4-15
	 */
	public void setCompany_name(String companyName) {
		company_name = companyName;
	}
}

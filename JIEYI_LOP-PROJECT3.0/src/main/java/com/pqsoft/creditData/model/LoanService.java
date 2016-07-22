package com.pqsoft.creditData.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 贷款业务信息实体
 * 
 * @className LoanService
 * @author 刘丽
 * @version V1.0 2016年4月21日 下午1:47:03
 */
public class LoanService extends BaseModel {

    private static final long serialVersionUID = 1L;

    private long projectId;

    private String loanType = "21";

    private LoanContract loanContract;

    private String serviceNo;

    private String occurrenceLocation;

    private String accountEstablishmentDate;

    private String deadline;

    private String currency = "CNY";

    private BigDecimal creditLine;

    private BigDecimal sharedCreditLine;

    private BigDecimal maxLiabilities;

    private String guaranteeWay = "4";

    private String repaymentFrequency = "03";

    private String totalMonthsToRepay;

    private String leftMonthsToRepay;

    private String agreedPeriodsToRepay;

    private BigDecimal agreedPeriodAmount;

    private String settlementDate;

    private String latestRepaymentDate;

    private BigDecimal expectedAmountOfThisMonth;

    private BigDecimal actualAmountOfThisMonth;

    private BigDecimal balance;

    private String currentOverduePeriods;

    private BigDecimal currentOverdueAmount;

    private BigDecimal overdue31To60Days;

    private BigDecimal overdue61To90Days;

    private BigDecimal overdue91To180Days;

    private BigDecimal overdueOver180Days;

    private String totalOverduePeriods;

    private String maxOverduePeriods;

    private String classificationState = "9";

    private String accountStatus;

    private String latest24MonthsStatus;

    private String hintForAccountOwner;

    private Date firstPeriodSettlementDate;

    private Identity identity;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * 得到贷款类型
     * 
     */
    public String getLoanType() {
        return loanType;
    }

    /**
     * 设置贷款类型
     * 
     */
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    /**
     * 得到贷款合同号码
     * 
     */
    public LoanContract getLoanContract() {
        return loanContract;
    }

    /**
     * 设置贷款合同号码
     * 
     */
    public void setLoanContract(LoanContract loanContract) {
        this.loanContract = loanContract;
    }

    /**
     * 得到业务号
     * 
     */
    public String getServiceNo() {
        return serviceNo;
    }

    /**
     * 设置业务号
     * 
     */
    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    /**
     * 得到发生地点
     * 
     */
    public String getOccurrenceLocation() {
        return occurrenceLocation;
    }

    /**
     * 设置发生地点
     * 
     */
    public void setOccurrenceLocation(String occurrenceLocation) {
        this.occurrenceLocation = occurrenceLocation;
    }

    /**
     * 得到开户日期
     * 
     */
    public String getAccountEstablishmentDate() {
        return accountEstablishmentDate;
    }

    /**
     * 设置开户日期
     * 
     */
    public void setAccountEstablishmentDate(String accountEstablishmentDate) {
        this.accountEstablishmentDate = accountEstablishmentDate;
    }

    /**
     * 得到到期日期
     * 
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * 设置到期日期
     * 
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * 得到币种
     * 
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置币种
     * 
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 得到授信额度
     * 
     */
    public BigDecimal getCreditLine() {
        return creditLine;
    }

    /**
     * 设置授信额度
     * 
     */
    public void setCreditLine(BigDecimal creditLine) {
        this.creditLine = creditLine;
    }

    /**
     * 得到共享授信额度
     * 
     */
    public BigDecimal getSharedCreditLine() {
        return sharedCreditLine;
    }

    /**
     * 设置共享授信额度
     * 
     */
    public void setSharedCreditLine(BigDecimal sharedCreditLine) {
        this.sharedCreditLine = sharedCreditLine;
    }

    /**
     * 得到最大负债额
     * 
     */
    public BigDecimal getMaxLiabilities() {
        return maxLiabilities;
    }

    /**
     * 设置最大负债额
     * 
     */
    public void setMaxLiabilities(BigDecimal maxLiabilities) {
        this.maxLiabilities = maxLiabilities;
    }

    /**
     * 得到担保方式
     * 
     */
    public String getGuaranteeWay() {
        return guaranteeWay;
    }

    /**
     * 设置担保方式
     * 
     */
    public void setGuaranteeWay(String guaranteeWay) {
        this.guaranteeWay = guaranteeWay;
    }

    /**
     * 得到还款频率
     * 
     */
    public String getRepaymentFrequency() {
        return repaymentFrequency;
    }

    /**
     * 设置还款频率
     * 
     */
    public void setRepaymentFrequency(String repaymentFrequency) {
        this.repaymentFrequency = repaymentFrequency;
    }

    /**
     * 得到还款月数
     * 
     */
    public String getTotalMonthsToRepay() {
        return totalMonthsToRepay;
    }

    /**
     * 设置还款月数
     * 
     */
    public void setTotalMonthsToRepay(String totalMonthsToRepay) {
        this.totalMonthsToRepay = totalMonthsToRepay;
    }

    /**
     * 得到剩余还款月数
     * 
     */
    public String getLeftMonthsToRepay() {
        return leftMonthsToRepay;
    }

    /**
     * 设置剩余还款月数
     * 
     */
    public void setLeftMonthsToRepay(String leftMonthsToRepay) {
        this.leftMonthsToRepay = leftMonthsToRepay;
    }

    /**
     * 得到协定还款期数
     *
     */
    public String getAgreedPeriodsToRepay() {
        return agreedPeriodsToRepay;
    }

    /**
     * 设置协定还款期数
     *
     */
    public void setAgreedPeriodsToRepay(String agreedPeriodsToRepay) {
        this.agreedPeriodsToRepay = agreedPeriodsToRepay;
    }

    /**
     * 得到协定期还款额
     *
     */
    public BigDecimal getAgreedPeriodAmount() {
        return agreedPeriodAmount;
    }

    /**
     * 设置协定期还款额
     *
     */
    public void setAgreedPeriodAmount(BigDecimal agreedPeriodAmount) {
        this.agreedPeriodAmount = agreedPeriodAmount;
    }

    /**
     * 得到结算日期
     * 
     */
    public String getSettlementDate() {
        return settlementDate;
    }

    /**
     * 设置结算OR应还日期
     * 
     */
    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    /**
     * 得到最近一次实际还款日期
     * 
     */
    public String getLatestRepaymentDate() {
        return latestRepaymentDate;
    }

    /**
     * 设置最近一次实际还款日期
     * 
     */
    public void setLatestRepaymentDate(String latestRepaymentDate) {
        this.latestRepaymentDate = latestRepaymentDate;
    }

    /**
     * 得到本月应还款金额
     * 
     */
    public BigDecimal getExpectedAmountOfThisMonth() {
        return expectedAmountOfThisMonth;
    }

    /**
     * 设置本月应还款金额
     * 
     */
    public void setExpectedAmountOfThisMonth(BigDecimal expectedAmountOfThisMonth) {
        this.expectedAmountOfThisMonth = expectedAmountOfThisMonth;
    }

    /**
     * 得到本月实际还款金额
     * 
     */
    public BigDecimal getActualAmountOfThisMonth() {
        return actualAmountOfThisMonth;
    }

    /**
     * 设置本月实际还款金额
     * 
     */
    public void setActualAmountOfThisMonth(BigDecimal actualAmountOfThisMonth) {
        this.actualAmountOfThisMonth = actualAmountOfThisMonth;
    }

    /**
     * 得到余额
     * 
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置余额
     * 
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 得到当前逾期期数
     * 
     */
    public String getCurrentOverduePeriods() {
        return currentOverduePeriods;
    }

    /**
     * 设置当前逾期期数
     * 
     */
    public void setCurrentOverduePeriods(String currentOverduePeriods) {
        this.currentOverduePeriods = currentOverduePeriods;
    }

    /**
     * 得到当前逾期总额
     * 
     */
    public BigDecimal getCurrentOverdueAmount() {
        return currentOverdueAmount;
    }

    /**
     * 设置当前逾期总额
     * 
     */
    public void setCurrentOverdueAmount(BigDecimal currentOverdueAmount) {
        this.currentOverdueAmount = currentOverdueAmount;
    }

    /**
     * 得到逾期31-60天未归还贷款本金
     * 
     */
    public BigDecimal getOverdue31To60Days() {
        return overdue31To60Days;
    }

    /**
     * 设置逾期31-60天未归还贷款本金
     * 
     */
    public void setOverdue31To60Days(BigDecimal overdue31To60Days) {
        this.overdue31To60Days = overdue31To60Days;
    }

    /**
     * 得到逾期61-90天未归还贷款本金
     * 
     */
    public BigDecimal getOverdue61To90Days() {
        return overdue61To90Days;
    }

    /**
     * 设置逾期61-90天未归还贷款本金
     * 
     */
    public void setOverdue61To90Days(BigDecimal overdue61To90Days) {
        this.overdue61To90Days = overdue61To90Days;
    }

    /**
     * 得到逾期91-180天未归还贷款本金
     * 
     */
    public BigDecimal getOverdue91To180Days() {
        return overdue91To180Days;
    }

    /**
     * 设置逾期91-180天未归还贷款本金
     * 
     */
    public void setOverdue91To180Days(BigDecimal overdue91To180Days) {
        this.overdue91To180Days = overdue91To180Days;
    }

    /**
     * 得到逾期180天以上未归还贷款本金
     * 
     */
    public BigDecimal getOverdueOver180Days() {
        return overdueOver180Days;
    }

    /**
     * 设置逾期180天以上未归还贷款本金
     * 
     */
    public void setOverdueOver180Days(BigDecimal overdueOver180Days) {
        this.overdueOver180Days = overdueOver180Days;
    }

    /**
     * 得到累计逾期期数
     * 
     */
    public String getTotalOverduePeriods() {
        return totalOverduePeriods;
    }

    /**
     * 设置累计逾期期数
     * 
     */
    public void setTotalOverduePeriods(String totalOverduePeriods) {
        this.totalOverduePeriods = totalOverduePeriods;
    }

    /**
     * 得到最高逾期期数
     * 
     */
    public String getMaxOverduePeriods() {
        return maxOverduePeriods;
    }

    /**
     * 设置最高逾期期数
     * 
     */
    public void setMaxOverduePeriods(String maxOverduePeriods) {
        this.maxOverduePeriods = maxOverduePeriods;
    }

    /**
     * 得到五级分类状态
     * 
     */
    public String getClassificationState() {
        return classificationState;
    }

    /**
     * 设置五级分类状态
     * 
     */
    public void setClassificationState(String classificationState) {
        this.classificationState = classificationState;
    }

    /**
     * 得到账户状态
     * 
     */
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * 设置账户状态
     * 
     */
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * 得到24月（账户）还款状态
     * 
     */
    public String getLatest24MonthsStatus() {
        return latest24MonthsStatus;
    }

    /**
     * 设置24月（账户）还款状态
     * 
     */
    public void setLatest24MonthsStatus(String latest24MonthsStatus) {
        this.latest24MonthsStatus = latest24MonthsStatus;
    }

    /**
     * 得到账户拥有者信息提示
     * 
     */
    public String getHintForAccountOwner() {
        return hintForAccountOwner;
    }

    /**
     * 设置账户拥有者信息提示
     * 
     */
    public void setHintForAccountOwner(String hintForAccountOwner) {
        this.hintForAccountOwner = hintForAccountOwner;
    }

    /**
     * 得到首期应还款日
     */
    public Date getFirstPeriodSettlementDate() {
        return firstPeriodSettlementDate;
    }

    /**
     * 设置首期应还款日
     */
    public void setFirstPeriodSettlementDate(Date firstPeriodSettlementDate) {
        this.firstPeriodSettlementDate = firstPeriodSettlementDate;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "LoanService{" +
                "loanType='" + loanType + '\'' +
                ", loanContract=" + loanContract +
                ", serviceNo='" + serviceNo + '\'' +
                ", occurrenceLocation='" + occurrenceLocation + '\'' +
                ", accountEstablishmentDate='" + accountEstablishmentDate + '\'' +
                ", deadline='" + deadline + '\'' +
                ", currency='" + currency + '\'' +
                ", creditLine=" + creditLine +
                ", sharedCreditLine=" + sharedCreditLine +
                ", maxLiabilities=" + maxLiabilities +
                ", guaranteeWay='" + guaranteeWay + '\'' +
                ", repaymentFrequency='" + repaymentFrequency + '\'' +
                ", totalMonthsToRepay='" + totalMonthsToRepay + '\'' +
                ", leftMonthsToRepay='" + leftMonthsToRepay + '\'' +
                ", agreedPeriodsToRepay='" + agreedPeriodsToRepay + '\'' +
                ", agreedPeriodAmount=" + agreedPeriodAmount +
                ", settlementDate='" + settlementDate + '\'' +
                ", latestRepaymentDate='" + latestRepaymentDate + '\'' +
                ", expectedAmountOfThisMonth=" + expectedAmountOfThisMonth +
                ", actualAmountOfThisMonth=" + actualAmountOfThisMonth +
                ", balance=" + balance +
                ", currentOverduePeriods='" + currentOverduePeriods + '\'' +
                ", currentOverdueAmount=" + currentOverdueAmount +
                ", overdue31To60Days=" + overdue31To60Days +
                ", overdue61To90Days=" + overdue61To90Days +
                ", overdue91To180Days=" + overdue91To180Days +
                ", unpaidPrincipleOfOver180DaysOverdue=" + overdueOver180Days +
                ", totalOverduePeriods='" + totalOverduePeriods + '\'' +
                ", maxOverduePeriods='" + maxOverduePeriods + '\'' +
                ", classificationState='" + classificationState + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", latest24MonthsStatus='" + latest24MonthsStatus + '\'' +
                ", hintForAccountOwner='" + hintForAccountOwner + '\'' +
                ", identity=" + identity +
                "} " + super.toString();
    }
}

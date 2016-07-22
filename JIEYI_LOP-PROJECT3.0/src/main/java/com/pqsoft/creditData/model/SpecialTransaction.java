package com.pqsoft.creditData.model;

import java.math.BigDecimal;

/**
 * 
 * 特殊交易信息
 * 
 * @className SpecialTransaction
 * @author 刘丽
 * @version V1.0 2016年4月21日 下午2:16:45
 */
public class SpecialTransaction extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Identity identity;

    private LoanService loanService;

    private String specialTransactionType;

    private String occurrenceDate;

    private String changedMonths;

    private BigDecimal occurrenceAmount;

    private String details;


    /**
     * 得到特殊交易类型
     * 
     */
    public String getSpecialTransactionType() {
        return specialTransactionType;
    }

    /**
     * 设置特殊交易类型
     * 
     */
    public void setSpecialTransactionType(String specialTransactionType) {
        this.specialTransactionType = specialTransactionType;
    }

    /**
     * 得到发生日期
     * 
     */
    public String getOccurrenceDate() {
        return occurrenceDate;
    }

    /**
     * 设置发生日期
     * 
     */
    public void setOccurrenceDate(String occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    /**
     * 得到变更月数
     * 
     */
    public String getChangedMonths() {
        return changedMonths;
    }

    /**
     * 设置变更月数
     * 
     */
    public void setChangedMonths(String changedMonths) {
        this.changedMonths = changedMonths;
    }

    /**
     * 得到发生金额
     * 
     */
    public BigDecimal getOccurrenceAmount() {
        return occurrenceAmount;
    }

    /**
     * 设置发生金额
     * 
     */
    public void setOccurrenceAmount(BigDecimal occurrenceAmount) {
        this.occurrenceAmount = occurrenceAmount;
    }

    /**
     * 得到明细信息
     * 
     */
    public String getDetails() {
        return details;
    }

    /**
     * 设置明细信息
     * 
     */
    public void setDetails(String details) {
        this.details = details;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public LoanService getLoanService() {
        return loanService;
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public String toString() {
        return "SpecialTransaction{" +
                "identity=" + identity +
                ", loanService=" + loanService +
                ", specialTransactionType='" + specialTransactionType + '\'' +
                ", occurrenceDate='" + occurrenceDate + '\'' +
                ", changedMonths='" + changedMonths + '\'' +
                ", occurrenceAmount=" + occurrenceAmount +
                ", details='" + details + '\'' +
                "} " + super.toString();
    }
}

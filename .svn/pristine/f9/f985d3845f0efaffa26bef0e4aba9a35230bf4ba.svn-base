package com.pqsoft.creditData.model;

import java.math.BigDecimal;

/**
 * 
 * 担保信息
 * 
 * @className Guarantee
 * @author 刘丽
 * @version V1.0 2016年4月21日 下午2:02:46
 */
public class Guarantee extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String guarantorName;

    private String guarantorCredentialType;

    private String guarantorCredentialNo;

    private BigDecimal guaranteeAmount;

    private String guaranteeStatus;

    private LoanService loanService;

    /**
     * 得到担保人姓名
     * 
     * @return 担保人姓名
     */
    public String getGuarantorName() {
        return guarantorName;
    }

    /**
     * 设置担保人姓名
     * 
     * @param guarantorName 担保人姓名
     */
    public void setGuarantorName(String guarantorName) {
        this.guarantorName = guarantorName;
    }

    /**
     * 得到担保人证件类型
     * 
     * @return 担保人证件类型
     */
    public String getGuarantorCredentialType() {
        return guarantorCredentialType;
    }

    /**
     * 设置担保人证件类型
     * 
     * @param guarantorCredentialType 担保人证件类型
     */
    public void setGuarantorCredentialType(String guarantorCredentialType) {
        this.guarantorCredentialType = guarantorCredentialType;
    }

    /**
     * 得到担保人证件号码
     * 
     * @return 担保人证件号码
     */
    public String getGuarantorCredentialNo() {
        return guarantorCredentialNo;
    }

    /**
     * 设置担保人证件号
     * 
     * @param guarantorCredentialNo 担保人证件号码
     */
    public void setGuarantorCredentialNo(String guarantorCredentialNo) {
        this.guarantorCredentialNo = guarantorCredentialNo;
    }

    /**
     * 得到担保金额
     * 
     * @return 担保金额
     */
    public BigDecimal getGuaranteeAmount() {
        return guaranteeAmount;
    }

    /**
     * 设置担保金额
     * 
     * @param guaranteeAmount 担保金额
     */
    public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    /**
     * 得到担保状态
     * 
     * @return 担保状态
     */
    public String getGuaranteeStatus() {
        return guaranteeStatus;
    }

    /**
     * 设置担保状态
     * 
     * @param guaranteeStatus 担保状态
     */
    public void setGuaranteeStatus(String guaranteeStatus) {
        this.guaranteeStatus = guaranteeStatus;
    }

    public LoanService getLoanService() {
        return loanService;
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public String toString() {
        return "Guarantee{" +
                "guarantorName='" + guarantorName + '\'' +
                ", guarantorCredentialType='" + guarantorCredentialType + '\'' +
                ", guarantorCredentialNo='" + guarantorCredentialNo + '\'' +
                ", guaranteeAmount=" + guaranteeAmount +
                ", guaranteeStatus='" + guaranteeStatus + '\'' +
                ", loanService=" + loanService +
                "} " + super.toString();
    }
}

package com.pqsoft.creditData.model;

import java.math.BigDecimal;

/**
 * 
 * 投资人信息
 * 
 * @className Investor
 * @author 刘丽
 * @version V1.0 2016年4月21日 下午2:13:22
 */
public class Investor extends BaseModel {

    private static final long serialVersionUID = 1L;

    private LoanService loanService;

    private String investorName;

    private String investorCredentialType;

    private String investorCredentialNo;

    private BigDecimal investmentAmount;

    /**
     * 得到投资人姓名
     * 
     */
    public String getInvestorName() {
        return investorName;
    }

    /**
     * 设置投资人姓名
     * 
     */
    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    /**
     * 得到投资人证件类型
     *
     */
    public String getInvestorCredentialType() {
        return investorCredentialType;
    }

    /**
     * 设置投资人证件类型
     *
     */
    public void setInvestorCredentialType(String investorCredentialType) {
        this.investorCredentialType = investorCredentialType;
    }

    /**
     * 得到投资人证件号码
     *
     */
    public String getInvestorCredentialNo() {
        return investorCredentialNo;
    }

    /**
     * 设置投资人证件号码
     *
     */
    public void setInvestorCredentialNo(String investorCredentialNo) {
        this.investorCredentialNo = investorCredentialNo;
    }

    /**
     * 得到投资人投资金额
     *
     */
    public BigDecimal getInvestmentAmount() {
        return investmentAmount;
    }

    /**
     * 设置投资人投资金额
     *
     */
    public void setInvestmentAmount(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public LoanService getLoanService() {
        return loanService;
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public String toString() {
        return "Investor{" +
                "loanService=" + loanService +
                ", investorName='" + investorName + '\'' +
                ", investorCredentialType='" + investorCredentialType + '\'' +
                ", investorCredentialNo='" + investorCredentialNo + '\'' +
                ", investmentAmount=" + investmentAmount +
                "} " + super.toString();
    }
}

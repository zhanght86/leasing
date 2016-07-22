package com.pqsoft.creditData.model;

import java.math.BigDecimal;

/**
 * 
 * 贷款合同信息
 * 
 * @className LoanContract
 * @author 刘丽
 * @version V1.0 2016年4月21日 下午1:52:42
 */
public class LoanContract extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String contractNo;

    private String effectiveDate;

    private String endDate;

    private String currency;

    private BigDecimal contractAmount;

    private String validStatus;

    /**
     * 得到贷款合同号码
     * 
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置贷款合同号码
     * 
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * 得到贷款合同生效日期
     * 
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * 设置贷款合同生效日期
     * 
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * 得到贷款合同终止日期
     * 
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 设置贷款合同终止日期
     * 
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
     * 得到贷款合同金额
     * 
     */
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    /**
     * 设置贷款合同金额
     * 
     */
    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    /**
     * 得到合同有效状态
     * 
     */
    public String getValidStatus() {
        return validStatus;
    }

    /**
     * 设置合同有效状态
     * 
     */
    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractNo='" + contractNo + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", currency='" + currency + '\'' +
                ", contractAmount='" + contractAmount + '\'' +
                ", validStatus='" + validStatus + '\'' +
                "} " + super.toString();
    }

}

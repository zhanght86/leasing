package com.pqsoft.creditData.model;

/**
 * 征信接口反馈的错误信息
 *
 * @className CreditError
 * @author 钟林俊
 * @version V1.0 2016年5月9日 17:57:12
 */
public class CreditError extends BaseModel{

    private String refTable;
    private String errorItem;
    private String errorCode;
    private String errorInfo;
    private Long refId;

    public CreditError() {
    }

    public CreditError(String refTable, String errorItem, String errorCode, String errorInfo, Long refId) {
        this.refTable = refTable;
        this.errorItem = errorItem;
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.refId = refId;
    }

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public String getErrorItem() {
        return errorItem;
    }

    public void setErrorItem(String errorItem) {
        this.errorItem = errorItem;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }
}

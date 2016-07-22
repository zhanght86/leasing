package com.pqsoft.telephonenumberlist.model.report;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-20 11:55
 */
public class CollectionContact {

    @JSONField(serialize = false)
    private Long id;
    @JSONField(name = "begin_date", format = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    @JSONField(name = "total_amount")
    private BigDecimal totalAmount;
    @JSONField(name = "end_date", format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    @JSONField(name = "total_count")
    private Integer totalCount;
    @JSONField(name = "contact_details")
    private List<ContactDetail> contactDetails;
    @JSONField(name = "contact_name")
    private String contactName;
    @JSONField(serialize = false)
    private DataReport dataReport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(List<ContactDetail> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public DataReport getDataReport() {
        return dataReport;
    }

    public void setDataReport(DataReport dataReport) {
        this.dataReport = dataReport;
    }
}

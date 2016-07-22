package com.pqsoft.telephonenumberlist.model.report;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-20 11:56
 */
public class MainService {

    @JSONField(name = "service_details")
    private List<ServiceDetail> serviceDetails;
    @JSONField(name = "total_service_cnt")
    private Integer totalServiceCnt;
    @JSONField(name = "company_type")
    private String companyType;
    @JSONField(name = "company_name")
    private String companyName;
    @JSONField(serialize = false)
    private Long id;
    @JSONField(serialize = false)
    private DataReport dataReport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ServiceDetail> getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(List<ServiceDetail> serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public Integer getTotalServiceCnt() {
        return totalServiceCnt;
    }

    public void setTotalServiceCnt(Integer totalServiceCnt) {
        this.totalServiceCnt = totalServiceCnt;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public DataReport getDataReport() {
        return dataReport;
    }

    public void setDataReport(DataReport dataReport) {
        this.dataReport = dataReport;
    }
}

package com.pqsoft.telephonenumberlist.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.pqsoft.telephonenumberlist.model.report.DataReport;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-22 13:53
 */
public class CollectReport {

    private Collect collect;
    private DataReport dataReport;
    @JSONField(serialize = false)
    private Long projectId;

    public Collect getCollect() {
        return collect;
    }

    public void setCollect(Collect collect) {
        this.collect = collect;
    }

    public DataReport getDataReport() {
        return dataReport;
    }

    public void setDataReport(DataReport dataReport) {
        this.dataReport = dataReport;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}

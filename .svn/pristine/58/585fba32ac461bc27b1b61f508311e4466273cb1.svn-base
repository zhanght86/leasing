package com.pqsoft.telephonenumberlist.model.report;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-20 11:57
 */
public class CellBehavior {

    @JSONField(serialize = false)
    private Long id;
    @JSONField(name = "phone_num")
    private String phoneNum;
    @JSONField(name = "behavior")
    private List<Behavior> behaviors;
    @JSONField(serialize = false)
    private DataReport dataReport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(List<Behavior> behaviors) {
        this.behaviors = behaviors;
    }

    public DataReport getDataReport() {
        return dataReport;
    }

    public void setDataReport(DataReport dataReport) {
        this.dataReport = dataReport;
    }
}

package com.pqsoft.telephonenumberlist.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-15 15:05
 */
public class Application {

    @JSONField(name = "selected_website")
    private List<String> selectedWebsite;
    @JSONField(name = "skip_mobile")
    private boolean skipMobile = false;
    @JSONField(name = "basic_info")
    private BasicInfo basicInfo;
    private String uid;

    public List<String> getSelectedWebsite() {
        return selectedWebsite;
    }

    public void setSelectedWebsite(List<String> selectedWebsite) {
        this.selectedWebsite = selectedWebsite;
    }

    public boolean isSkipMobile() {
        return skipMobile;
    }

    public void setSkipMobile(boolean skipMobile) {
        this.skipMobile = skipMobile;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

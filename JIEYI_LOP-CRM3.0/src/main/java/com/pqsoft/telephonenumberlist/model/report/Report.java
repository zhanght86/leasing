package com.pqsoft.telephonenumberlist.model.report;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-20 11:57
 */
public class Report {

    private String token;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updt;
    private String id;
    private String version;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdt() {
        return updt;
    }

    public void setUpdt(Date updt) {
        this.updt = updt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

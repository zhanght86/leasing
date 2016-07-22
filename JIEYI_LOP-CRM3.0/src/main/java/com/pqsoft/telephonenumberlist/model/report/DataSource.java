package com.pqsoft.telephonenumberlist.model.report;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-20 11:54
 */
public class DataSource {

    private String status;
    private String account;
    @JSONField(name = "binding_time", format = "yyyy-MM-dd HH:mm:ss")
    private Date bindingTime;
    private String name;
    @JSONField(name = "category_value")
    private String categoryValue;
    private String reliability;
    private String key;
    @JSONField(name = "category_name")
    private String categoryName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getBindingTime() {
        return bindingTime;
    }

    public void setBindingTime(Date bindingTime) {
        this.bindingTime = bindingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

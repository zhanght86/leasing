package com.pqsoft.telephonenumberlist.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-15 15:25
 */
public class Contact {

    @JSONField(name = "contact_tel")
    private String contactTel;
    @JSONField(name = "contact_name")
    private String contactName;
    @JSONField(name = "contact_type")
    private String contactType;

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }
}

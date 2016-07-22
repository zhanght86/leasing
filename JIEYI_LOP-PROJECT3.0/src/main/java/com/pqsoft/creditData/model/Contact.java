package com.pqsoft.creditData.model;

/**
 * 联系人信息
 *
 * @className Contact
 * @author 钟林俊
 * @version V1.0 2016年5月9日 17:57:12
 */
public class Contact {

    private String name;
    private String phone;
    private String relationship;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}

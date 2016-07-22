package com.pqsoft.telephonenumberlist.model.report;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-20 11:55
 */
public class Person {

    private String province;
    private String city;
    private String gender;
    private Integer age;
    private String sign;
    private String state;
    private Boolean status;
    @JSONField(name = "real_name")
    private String realName;
    private String region;
    @JSONField(name = "id_card_num")
    private String idCardNum;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
}

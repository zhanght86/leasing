package com.pqsoft.telephonenumberlist.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-27 10:04
 */
public class ApplicationResponseData {

    private String token;
    @JSONField(name = "cell_phone_num")
    private String cellPhoneNum;
    @JSONField(name = "datasource")
    private ApplicationResponseDataSource applicationResponseDataSource;
    private String password;
    private String name;
    @JSONField(name = "id_card_num")
    private String idCardNum;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCellPhoneNum() {
        return cellPhoneNum;
    }

    public void setCellPhoneNum(String cellPhoneNum) {
        this.cellPhoneNum = cellPhoneNum;
    }

    public ApplicationResponseDataSource getApplicationResponseDataSource() {
        return applicationResponseDataSource;
    }

    public void setApplicationResponseDataSource(ApplicationResponseDataSource applicationResponseDataSource) {
        this.applicationResponseDataSource = applicationResponseDataSource;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
}

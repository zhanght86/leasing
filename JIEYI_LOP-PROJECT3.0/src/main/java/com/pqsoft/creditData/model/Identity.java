package com.pqsoft.creditData.model;

import java.util.List;

/**
 * 
 * 身份信息
 * 
 * @className Identity
 * @author 刘丽
 * @version V1.0 2016年4月21日 下午3:24:07
 */
public class Identity extends BaseModel {

    private static final long serialVersionUID = 1L;
    private String fullName;
    private String credentialType;
    private String credentialNo;
    private String gender;
    private String birthday;
    private String maritalStatus;
    private String highestEducation;
    private String highestDegree;
    private String residencePhone;
    private String cellPhone;
    private String workingPhone;
    private String email;
    private String mailingProvince;
    private String mailingCity;
    private String mailingCounty;
    private String mailingAddress;
    private String mailingAddressZipCode;
    private String householdRegisterAddress;
    private String spouseName;
    private String spouseCredentialType;
    private String spouseCredentialNo;
    private String spouseWorkingPlace;
    private String spousePhone;
    private String firstContactName;
    private String firstContactRelationship;
    private String firstContactPhone;
    private String secondContactName;
    private String secondContactRelationship;
    private String secondContactPhone;
    private List<Contact> contactList;
    private CreditUpload creditUpload;
    private String generatedFileName;

    /**
     * 得到姓名
     * 
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 设置姓名
     * 
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 得到证件类型
     * 
     */
    public String getCredentialType() {
        return credentialType;
    }

    /**
     * 设置证件类型
     * 
     */
    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    /**
     * 得到证件号码
     * 
     */
    public String getCredentialNo() {
        return credentialNo;
    }

    /**
     * 设置证件号码
     * 
     */
    public void setCredentialNo(String credentialNo) {
        this.credentialNo = credentialNo;
    }

    /**
     * 得到性别
     * 
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别
     * 
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 得到出生日期
     * 
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     * 
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 得到婚姻状况
     * 
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * 设置婚姻状况
     * 
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * 得到最高学历
     * 
     */
    public String getHighestEducation() {
        return highestEducation;
    }

    /**
     * 设置最高学历
     * 
     */
    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    /**
     * 得到最高学位
     * 
     */
    public String getHighestDegree() {
        return highestDegree;
    }

    /**
     * 设置最高学位
     * 
     */
    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }

    /**
     * 得到住宅电话
     * 
     */
    public String getResidencePhone() {
        return residencePhone;
    }

    /**
     * 设置住宅电话
     * 
     */
    public void setResidencePhone(String residencePhone) {
        this.residencePhone = residencePhone;
    }

    /**
     * 得到手机号码
     * 
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * 设置手机号码
     * 
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * 得到电子邮件
     * 
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     * 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 得到通讯地址
     * 
     */
    public String getMailingAddress() {
        return mailingAddress;
    }

    /**
     * 设置通讯地址
     * 
     */
    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    /**
     * 得到通讯地址邮政编码
     * 
     */
    public String getMailingAddressZipCode() {
        return mailingAddressZipCode;
    }

    /**
     * 设置通讯地址邮政编码
     * 
     */
    public void setMailingAddressZipCode(String mailingAddressZipCode) {
        this.mailingAddressZipCode = mailingAddressZipCode;
    }

    /**
     * 得到户籍地址
     * 
     */
    public String getHouseholdRegisterAddress() {
        return householdRegisterAddress;
    }

    /**
     * 设置户籍地址
     * 
     */
    public void setHouseholdRegisterAddress(String householdRegisterAddress) {
        this.householdRegisterAddress = householdRegisterAddress;
    }

    /**
     * 得到配偶姓名
     * 
     */
    public String getSpouseName() {
        return spouseName;
    }

    /**
     * 设置配偶姓名
     * 
     */
    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    /**
     * 得到配偶证件类型
     * 
     */
    public String getSpouseCredentialType() {
        return spouseCredentialType;
    }

    /**
     * 设置配偶证件类型
     * 
     */
    public void setSpouseCredentialType(String spouseCredentialType) {
        this.spouseCredentialType = spouseCredentialType;
    }

    /**
     * 得到配偶证件号码
     * 
     */
    public String getSpouseCredentialNo() {
        return spouseCredentialNo;
    }

    /**
     * 设置配偶证件号码
     * 
     */
    public void setSpouseCredentialNo(String spouseCredentialNo) {
        this.spouseCredentialNo = spouseCredentialNo;
    }

    /**
     * 得到配偶工作单位
     * 
     */
    public String getSpouseWorkingPlace() {
        return spouseWorkingPlace;
    }

    /**
     * 设置配偶工作单位
     * 
     */
    public void setSpouseWorkingPlace(String spouseWorkingPlace) {
        this.spouseWorkingPlace = spouseWorkingPlace;
    }

    /**
     * 得到配偶联系电话
     * 
     */
    public String getSpousePhone() {
        return spousePhone;
    }

    /**
     * 设置配偶联系电话
     * 
     */
    public void setSpousePhone(String spousePhone) {
        this.spousePhone = spousePhone;
    }

    /**
     * 得到第一联系人姓名
     * 
     */
    public String getFirstContactName() {
        return firstContactName;
    }

    /**
     * 设置第一联系人姓名
     * 
     */
    public void setFirstContactName(String firstContactName) {
        this.firstContactName = firstContactName;
    }

    /**
     * 得到第一联系人关系
     * 
     */
    public String getFirstContactRelationship() {
        return firstContactRelationship;
    }

    /**
     * 设置第一联系人关系
     * 
     */
    public void setFirstContactRelationship(String firstContactRelationship) {
        this.firstContactRelationship = firstContactRelationship;
    }

    /**
     * 得到第一关系人联系电话
     * 
     */
    public String getFirstContactPhone() {
        return firstContactPhone;
    }

    /**
     * 设置第一关系人联系电话
     * 
     */
    public void setFirstContactPhone(String firstContactPhone) {
        this.firstContactPhone = firstContactPhone;
    }

    /**
     * 得到第二联系人姓名
     * 
     */
    public String getSecondContactName() {
        return secondContactName;
    }

    /**
     * 设置第二联系人姓名
     * 
     */
    public void setSecondContactName(String secondContactName) {
        this.secondContactName = secondContactName;
    }

    /**
     * 得到第二联系人关系
     * 
     */
    public String getSecondContactRelationship() {
        return secondContactRelationship;
    }

    /**
     * 设置第二联系人关系
     * 
     */
    public void setSecondContactRelationship(String secondContactRelationship) {
        this.secondContactRelationship = secondContactRelationship;
    }

    /**
     * 得到第二关系人联系电话
     * 
     */
    public String getSecondContactPhone() {
        return secondContactPhone;
    }

    /**
     * 设置第二关系人联系电话
     * 
     */
    public void setSecondContactPhone(String secondContactPhone) {
        this.secondContactPhone = secondContactPhone;
    }

    /**
     * 得到单位电话
     */
    public String getWorkingPhone() {
        return workingPhone;
    }

    /**
     * 设置单位电话
     */
    public void setWorkingPhone(String workingPhone) {
        this.workingPhone = workingPhone;
    }

    public String getMailingProvince() {
        return mailingProvince;
    }

    public void setMailingProvince(String mailingProvince) {
        this.mailingProvince = mailingProvince;
    }

    public String getMailingCity() {
        return mailingCity;
    }

    public void setMailingCity(String mailingCity) {
        this.mailingCity = mailingCity;
    }

    public String getMailingCounty() {
        return mailingCounty;
    }

    public void setMailingCounty(String mailingCounty) {
        this.mailingCounty = mailingCounty;
    }

    public List<Contact> getContactList(){
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;

    }

    public CreditUpload getCreditUpload() {
        return creditUpload;
    }

    public void setCreditUpload(CreditUpload creditUpload) {
        this.creditUpload = creditUpload;
    }

    public String getGeneratedFileName() {
        return generatedFileName;
    }

    public void setGeneratedFileName(String generatedFileName) {
        this.generatedFileName = generatedFileName;
    }

    @Override
    public String toString() {
        return "Identity{" +
                "fullName='" + fullName + '\'' +
                ", credentialType='" + credentialType + '\'' +
                ", credentialNo='" + credentialNo + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", highestEducation='" + highestEducation + '\'' +
                ", highestDegree='" + highestDegree + '\'' +
                ", residencePhone='" + residencePhone + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", workingPhone='" + workingPhone + '\'' +
                ", email='" + email + '\'' +
                ", mailingProvince='" + mailingProvince + '\'' +
                ", mailingCity='" + mailingCity + '\'' +
                ", mailingCounty='" + mailingCounty + '\'' +
                ", mailingAddress='" + mailingAddress + '\'' +
                ", mailingAddressZipCode='" + mailingAddressZipCode + '\'' +
                ", householdRegisterAddress='" + householdRegisterAddress + '\'' +
                ", spouseName='" + spouseName + '\'' +
                ", spouseCredentialType='" + spouseCredentialType + '\'' +
                ", spouseCredentialNo='" + spouseCredentialNo + '\'' +
                ", spouseWorkingPlace='" + spouseWorkingPlace + '\'' +
                ", spousePhone='" + spousePhone + '\'' +
                ", firstContactName='" + firstContactName + '\'' +
                ", firstContactRelationship='" + firstContactRelationship + '\'' +
                ", firstContactPhone='" + firstContactPhone + '\'' +
                ", secondContactName='" + secondContactName + '\'' +
                ", secondContactRelationship='" + secondContactRelationship + '\'' +
                ", secondContactPhone='" + secondContactPhone + '\'' +
                ", contactList=" + contactList +
                ", creditUpload=" + creditUpload +
                ", generatedFileName='" + generatedFileName + '\'' +
                "} " + super.toString();
    }
}

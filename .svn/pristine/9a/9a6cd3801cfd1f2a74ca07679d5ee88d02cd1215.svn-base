package com.pqsoft.creditData.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;

/**
 *
 * CreditData代表一个excel,里边的每个List代表一个Sheet
 *
 * @className BaseModel
 * @author 刘丽
 * @version V1.0 2016年4月21日 下午4:04:32
 */
public class BaseModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String P2POrganCode = "Q10151000Hy700";

    private String formerRowNo;

    private String mergedRowNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 得到P2P机构代码
     *
     * @return P2P机构代码
     */
    public String getP2POrganCode() {
        return P2POrganCode;
    }

    /**
     * 设置P2P机构代码
     *
     * @param p2pOrganCode P2P机构代码
     */
    public void setP2POrganCode(String p2pOrganCode) {
        P2POrganCode = p2pOrganCode;
    }

    /**
     * 生成文件时数据的行号
     */
    public String getFormerRowNo() {
        return formerRowNo;
    }

    /**
     * 生成文件时数据的行号
     */
    public void setFormerRowNo(String formerRowNo) {
        this.formerRowNo = formerRowNo;
    }

    /**
     * 合并文件后数据的行号
     */
    public String getMergedRowNo() {
        return mergedRowNo;
    }

    /**
     * 合并文件后数据的行号
     */
    public void setMergedRowNo(String mergedRowNo) {
        this.mergedRowNo = mergedRowNo;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", P2POrganCode='" + P2POrganCode + '\'' +
                ", formerRowNo='" + formerRowNo + '\'' +
                ", mergedRowNo='" + mergedRowNo + '\'' +
                '}';
    }

}

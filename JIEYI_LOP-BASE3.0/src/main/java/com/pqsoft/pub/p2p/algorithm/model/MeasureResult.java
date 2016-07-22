package com.pqsoft.pub.p2p.algorithm.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 测算返回
 * 
 * @className MeasureResult
 * @author 刘丽
 * @version V1.0 2016年3月23日 下午2:03:21
 */
public class MeasureResult {

    private int qs;// 期数

    private BigDecimal zj;// 租金

    private BigDecimal bj;// 本金

    private BigDecimal lx;// 利息

    private BigDecimal sybj;// 剩余本金

    private BigDecimal glf;// 管理费

    private BigDecimal zcfwf;// 租车服务费

    private BigDecimal jyfwf;// 交易服务费

    private Date zfrq; // 支付日期
    
    private BigDecimal mqglf;//每期管理费
    
    public BigDecimal getMqglf() {
		return mqglf;
	}

	public void setMqglf(BigDecimal mqglf) {
		this.mqglf = mqglf;
	}

	public Date getZfrq() {
        return zfrq;
    }

    public void setZfrq(Date zfrq) {
        this.zfrq = zfrq;
    }

    public BigDecimal getGlf() {
        return glf;
    }

    public void setGlf(BigDecimal glf) {
        this.glf = glf;
    }

    public BigDecimal getSybj() {
        return sybj;
    }

    public void setSybj(BigDecimal sybj) {
        this.sybj = sybj;
    }

    public int getQs() {
        return qs;
    }

    public void setQs(int qs) {
        this.qs = qs;
    }

    public BigDecimal getZj() {
        return zj;
    }

    public void setZj(BigDecimal zj) {
        this.zj = zj;
    }

    public BigDecimal getBj() {
        return bj;
    }

    public void setBj(BigDecimal bj) {
        this.bj = bj;
    }

    public BigDecimal getLx() {
        return lx;
    }

    public void setLx(BigDecimal lx) {
        this.lx = lx;
    }

    public BigDecimal getZcfwf() {
        return zcfwf;
    }

    public void setZcfwf(BigDecimal zcfwf) {
        this.zcfwf = zcfwf;
    }

    public BigDecimal getJyfwf() {
        return jyfwf;
    }

    public void setJyfwf(BigDecimal jyfwf) {
        this.jyfwf = jyfwf;
    }

    @Override
    public String toString() {
        return "MeasureResult [qs=" + qs + ", zj=" + zj + ", bj=" + bj + ", lx=" + lx + ", sybj=" + sybj + ", glf=" + glf + ", zcfwf=" + zcfwf
            + ", jyfwf=" + jyfwf + ", zfrq=" + zfrq + "]\n";
    }

}

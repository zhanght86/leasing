package com.pqsoft.telephonenumberlist.model.report;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-20 11:56
 */
public class ContactRegion {

    @JSONField(name = "region_loc")
    private String regionLoc;
    @JSONField(name = "region_uniq_num_cnt")
    private Integer regionUniqNumCnt;
    @JSONField(name = "region_call_out_time")
    private BigDecimal regionCallOutTime;
    @JSONField(name = "region_avg_call_in_time")
    private BigDecimal regionAvgCallInTime;
    @JSONField(name = "region_call_in_time")
    private BigDecimal regionCallInTime;
    @JSONField(name = "region_call_out_cnt")
    private Integer regionCallOutCnt;
    @JSONField(name = "region_avg_call_out_time")
    private BigDecimal regionAvgCallOutTime;
    @JSONField(name = "region_call_in_cnt_pct")
    private BigDecimal regionCallInCntPct;
    @JSONField(name = "region_call_in_time_pct")
    private BigDecimal regionCallInTimePct;
    @JSONField(name = "region_call_in_cnt")
    private Integer regionCallInCnt;
    @JSONField(name = "region_call_out_time_pct")
    private BigDecimal regionCallOutTimePct;
    @JSONField(name = "region_call_out_cnt_pct")
    private BigDecimal regionCallOutCntPct;

    public String getRegionLoc() {
        return regionLoc;
    }

    public void setRegionLoc(String regionLoc) {
        this.regionLoc = regionLoc;
    }

    public Integer getRegionUniqNumCnt() {
        return regionUniqNumCnt;
    }

    public void setRegionUniqNumCnt(Integer regionUniqNumCnt) {
        this.regionUniqNumCnt = regionUniqNumCnt;
    }

    public BigDecimal getRegionCallOutTime() {
        return regionCallOutTime;
    }

    public void setRegionCallOutTime(BigDecimal regionCallOutTime) {
        this.regionCallOutTime = regionCallOutTime;
    }

    public BigDecimal getRegionAvgCallInTime() {
        return regionAvgCallInTime;
    }

    public void setRegionAvgCallInTime(BigDecimal regionAvgCallInTime) {
        this.regionAvgCallInTime = regionAvgCallInTime;
    }

    public BigDecimal getRegionCallInTime() {
        return regionCallInTime;
    }

    public void setRegionCallInTime(BigDecimal regionCallInTime) {
        this.regionCallInTime = regionCallInTime;
    }

    public Integer getRegionCallOutCnt() {
        return regionCallOutCnt;
    }

    public void setRegionCallOutCnt(Integer regionCallOutCnt) {
        this.regionCallOutCnt = regionCallOutCnt;
    }

    public BigDecimal getRegionAvgCallOutTime() {
        return regionAvgCallOutTime;
    }

    public void setRegionAvgCallOutTime(BigDecimal regionAvgCallOutTime) {
        this.regionAvgCallOutTime = regionAvgCallOutTime;
    }

    public BigDecimal getRegionCallInCntPct() {
        return regionCallInCntPct;
    }

    public void setRegionCallInCntPct(BigDecimal regionCallInCntPct) {
        this.regionCallInCntPct = regionCallInCntPct;
    }

    public BigDecimal getRegionCallInTimePct() {
        return regionCallInTimePct;
    }

    public void setRegionCallInTimePct(BigDecimal regionCallInTimePct) {
        this.regionCallInTimePct = regionCallInTimePct;
    }

    public Integer getRegionCallInCnt() {
        return regionCallInCnt;
    }

    public void setRegionCallInCnt(Integer regionCallInCnt) {
        this.regionCallInCnt = regionCallInCnt;
    }

    public BigDecimal getRegionCallOutTimePct() {
        return regionCallOutTimePct;
    }

    public void setRegionCallOutTimePct(BigDecimal regionCallOutTimePct) {
        this.regionCallOutTimePct = regionCallOutTimePct;
    }

    public BigDecimal getRegionCallOutCntPct() {
        return regionCallOutCntPct;
    }

    public void setRegionCallOutCntPct(BigDecimal regionCallOutCntPct) {
        this.regionCallOutCntPct = regionCallOutCntPct;
    }
}

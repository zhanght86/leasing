package com.pqsoft.telephonenumberlist.model.report;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * TODO
 *
 * @author 钟林俊
 * @version V1.0 2016-06-20 11:57
 */
public class TripInfo {
    @JSONField(name = "trip_dest")
    private String tripDest;
    @JSONField(name = "trip_start_time")
    private String tripStartTime;
    @JSONField(name = "trip_end_time")
    private String tripEndTime;
    @JSONField(name = "trip_transportation")
    private List<String> tripTransportations;
    @JSONField(name = "trip_person")
    private List<String> tripPersons;
    @JSONField(name = "trip_leave")
    private String tripLeave;
    @JSONField(name = "trip_data_source")
    private List<String> tripDataSources;
    @JSONField(name = "trip_type")
    private String tripType;

    public String getTripDest() {
        return tripDest;
    }

    public void setTripDest(String tripDest) {
        this.tripDest = tripDest;
    }

    public String getTripStartTime() {
        return tripStartTime;
    }

    public void setTripStartTime(String tripStartTime) {
        this.tripStartTime = tripStartTime;
    }

    public String getTripEndTime() {
        return tripEndTime;
    }

    public void setTripEndTime(String tripEndTime) {
        this.tripEndTime = tripEndTime;
    }

    public List<String> getTripTransportations() {
        return tripTransportations;
    }

    public void setTripTransportations(List<String> tripTransportations) {
        this.tripTransportations = tripTransportations;
    }

    public List<String> getTripPersons() {
        return tripPersons;
    }

    public void setTripPersons(List<String> tripPersons) {
        this.tripPersons = tripPersons;
    }

    public String getTripLeave() {
        return tripLeave;
    }

    public void setTripLeave(String tripLeave) {
        this.tripLeave = tripLeave;
    }

    public List<String> getTripDataSources() {
        return tripDataSources;
    }

    public void setTripDataSources(List<String> tripDataSources) {
        this.tripDataSources = tripDataSources;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }
}

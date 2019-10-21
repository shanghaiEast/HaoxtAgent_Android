package com.haoxt.agent.model;

import java.util.List;

public class Message<T> {

    private String rspCd;
    private String rspInf;
    private String rspType;
    private String responseTm;
    private List<T> rspData;

    public String getRspCd() {
        return rspCd;
    }

    public void setRspCd(String rspCd) {
        this.rspCd = rspCd;
    }

    public String getRspInf() {
        return rspInf;
    }

    public void setRspInf(String rspInf) {
        this.rspInf = rspInf;
    }

    public String getRspType() {
        return rspType;
    }

    public void setRspType(String rspType) {
        this.rspType = rspType;
    }

    public String getResponseTm() {
        return responseTm;
    }

    public void setResponseTm(String responseTm) {
        this.responseTm = responseTm;
    }

    public List<T> getRspData() {
        return rspData;
    }

    public void setRspData(List<T> rspData) {
        this.rspData = rspData;
    }
}

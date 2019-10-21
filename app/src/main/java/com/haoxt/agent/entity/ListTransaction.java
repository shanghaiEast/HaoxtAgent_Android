package com.haoxt.agent.entity;

import java.io.Serializable;

public class ListTransaction  implements Serializable{

    public String LOG_NO;
    public String CRD_NO;
    public String TXN_AMT;
    public String TXN_STS_ZH;
    public String TXN_TM;
    public String MERC_FEE_AMT;
    public String TXN_CD;


    public String getLOG_NO() {
        return LOG_NO;
    }

    public void setLOG_NO(String LOG_NO) {
        this.LOG_NO = LOG_NO;
    }

    public String getCRD_NO() {
        return CRD_NO;
    }

    public void setCRD_NO(String CRD_NO) {
        this.CRD_NO = CRD_NO;
    }

    public String getTXN_AMT() {
        return TXN_AMT;
    }

    public void setTXN_AMT(String TXN_AMT) {
        this.TXN_AMT = TXN_AMT;
    }

    public String getTXN_STS_ZH() {
        return TXN_STS_ZH;
    }

    public void setTXN_STS_ZH(String TXN_STS_ZH) {
        this.TXN_STS_ZH = TXN_STS_ZH;
    }

    public String getTXN_TM() {
        return TXN_TM;
    }

    public void setTXN_TM(String TXN_TM) {
        this.TXN_TM = TXN_TM;
    }

    public String getMERC_FEE_AMT() {
        return MERC_FEE_AMT;
    }

    public void setMERC_FEE_AMT(String MERC_FEE_AMT) {
        this.MERC_FEE_AMT = MERC_FEE_AMT;
    }

    public String getTXN_CD() {
        return TXN_CD;
    }

    public void setTXN_CD(String TXN_CD) {
        this.TXN_CD = TXN_CD;
    }
}
package com.haoxt.agent.model;

/**
 * 分行
 */
public class SubBankInfoSet {

    private String agtSessionEntity;
    private String id;
    private SubBankInfo cmmtbkin;
    private String cmmtarea;


    public String getAgtSessionEntity() {
        return agtSessionEntity;
    }

    public void setAgtSessionEntity(String agtSessionEntity) {
        this.agtSessionEntity = agtSessionEntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubBankInfo getSubBankInfo() {
        return cmmtbkin;
    }

    public void setSubBankInfo(SubBankInfo subBankInfo) {
        this.cmmtbkin = subBankInfo;
    }

    public String getCmmtarea() {
        return cmmtarea;
    }

    public void setCmmtarea(String cmmtarea) {
        this.cmmtarea = cmmtarea;
    }
}

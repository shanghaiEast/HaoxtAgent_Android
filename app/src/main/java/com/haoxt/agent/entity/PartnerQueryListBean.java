package com.haoxt.agent.entity;

import java.io.Serializable;

/**
 * Created by liuxx on 2019/10/22
 * 合作伙伴查询列表实体类
 */

public class PartnerQueryListBean implements Serializable {

    private String corporateName;//公司名称
    private String legalPersonName;//法人姓名
    private String phoneNum;//联系方式
    private String firstPartner;//一级合作伙伴
    private String secondaryPartner;//二级合作伙伴

    private String idCard;//身份证号
    private String openingBank;//开户行
    private String branch;//支行信息
    private String bankcardNo;//银行卡号

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBankcardNo() {
        return bankcardNo;
    }

    public void setBankcardNo(String bankcardNo) {
        this.bankcardNo = bankcardNo;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getFirstPartner() {
        return firstPartner;
    }

    public void setFirstPartner(String firstPartner) {
        this.firstPartner = firstPartner;
    }

    public String getSecondaryPartners() {
        return secondaryPartner;
    }

    public void setSecondaryPartners(String secondaryPartner) {
        this.secondaryPartner = secondaryPartner;
    }
}

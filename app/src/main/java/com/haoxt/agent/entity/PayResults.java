package com.haoxt.agent.entity;

import java.io.Serializable;

public class PayResults implements Serializable {

    public int mPayType;
    public int payResult;
    public Double payMoney;

    public int getPayType() {
        return mPayType;
    }

    public void setPayType(int payType) {
        mPayType = payType;
    }

    public int getPayResult() {
        return payResult;
    }

    public void setPayResult(int payResult) {
        this.payResult = payResult;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

}

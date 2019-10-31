package com.haoxt.agent.model;

import java.io.Serializable;

/**
 * 业绩
 */
public class Achievement implements Serializable {

    public String transactionDate;
    public String totalTransaction;
    public String quantityTransaction;
    public String newAchievementTerminal;
    public String onlineMerchant;
    public String averageAmount;
    public String newActivation;


    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(String totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public String getQuantityTransaction() {
        return quantityTransaction;
    }

    public void setQuantityTransaction(String quantityTransaction) {
        this.quantityTransaction = quantityTransaction;
    }

    public String getNewAchievementTerminal() {
        return newAchievementTerminal;
    }

    public void setNewAchievementTerminal(String newAchievementTerminal) {
        this.newAchievementTerminal = newAchievementTerminal;
    }

    public String getOnlineMerchant() {
        return onlineMerchant;
    }

    public void setOnlineMerchant(String onlineMerchant) {
        this.onlineMerchant = onlineMerchant;
    }

    public String getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(String averageAmount) {
        this.averageAmount = averageAmount;
    }

    public String getNewActivation() {
        return newActivation;
    }

    public void setNewActivation(String newActivation) {
        this.newActivation = newActivation;
    }
}

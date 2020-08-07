package com.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedemptionOrder {

    private String transactionAmount;
    private String currencyCode;
    private String pointsToRedeem;
    private String transactionDescription;

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPointsToRedeem() {
        return pointsToRedeem;
    }

    public void setPointsToRedeem(String pointsToRedeem) {
        this.pointsToRedeem = pointsToRedeem;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

}
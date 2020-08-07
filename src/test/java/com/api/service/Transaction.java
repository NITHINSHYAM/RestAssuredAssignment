package com.api.service;

public class Transaction {

    private String transactionReferenceNumber;
    private RedemptionOrder redemptionOrder;

    public String getTransactionReferenceNumber() {
        return transactionReferenceNumber;
    }

    public void setTransactionReferenceNumber(String transactionReferenceNumber) {
        this.transactionReferenceNumber = transactionReferenceNumber;
    }

    public RedemptionOrder getRedemptionOrder() {
        return redemptionOrder;
    }

    public void setRedemptionOrder(RedemptionOrder redemptionOrder) {
        this.redemptionOrder = redemptionOrder;
    }

}

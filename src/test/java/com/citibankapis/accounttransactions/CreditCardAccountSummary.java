
package com.citibankapis.accounttransactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditCardAccountSummary {

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productCode")
    @Expose
    private String productCode;
    @SerializedName("displayAccountNumber")
    @Expose
    private String displayAccountNumber;
    @SerializedName("accountId")
    @Expose
    private String accountId;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("accountClassification")
    @Expose
    private String accountClassification;
    @SerializedName("accountStatus")
    @Expose
    private String accountStatus;
    @SerializedName("outstandingBalance")
    @Expose
    private Float outstandingBalance;
    @SerializedName("availableCredit")
    @Expose
    private Float availableCredit;
    @SerializedName("creditLimit")
    @Expose
    private Integer creditLimit;
    @SerializedName("minimumDueAmount")
    @Expose
    private Integer minimumDueAmount;
    @SerializedName("paymentDueDate")
    @Expose
    private String paymentDueDate;
    @SerializedName("alternateCurrency")
    @Expose
    private String alternateCurrency;
    @SerializedName("alternateCurrencyCurrentBalance")
    @Expose
    private Integer alternateCurrencyCurrentBalance;
    @SerializedName("cardHolderType")
    @Expose
    private String cardHolderType;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDisplayAccountNumber() {
        return displayAccountNumber;
    }

    public void setDisplayAccountNumber(String displayAccountNumber) {
        this.displayAccountNumber = displayAccountNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAccountClassification() {
        return accountClassification;
    }

    public void setAccountClassification(String accountClassification) {
        this.accountClassification = accountClassification;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Float getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Float outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Float getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(Float availableCredit) {
        this.availableCredit = availableCredit;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getMinimumDueAmount() {
        return minimumDueAmount;
    }

    public void setMinimumDueAmount(Integer minimumDueAmount) {
        this.minimumDueAmount = minimumDueAmount;
    }

    public String getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(String paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getAlternateCurrency() {
        return alternateCurrency;
    }

    public void setAlternateCurrency(String alternateCurrency) {
        this.alternateCurrency = alternateCurrency;
    }

    public Integer getAlternateCurrencyCurrentBalance() {
        return alternateCurrencyCurrentBalance;
    }

    public void setAlternateCurrencyCurrentBalance(Integer alternateCurrencyCurrentBalance) {
        this.alternateCurrencyCurrentBalance = alternateCurrencyCurrentBalance;
    }

    public String getCardHolderType() {
        return cardHolderType;
    }

    public void setCardHolderType(String cardHolderType) {
        this.cardHolderType = cardHolderType;
    }

}

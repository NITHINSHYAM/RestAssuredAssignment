
package com.citibankapis.accounttransactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavingsAccountSummary {

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
    @SerializedName("currentBalance")
    @Expose
    private Float currentBalance;
    @SerializedName("availableBalance")
    @Expose
    private Float availableBalance;

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

    public Float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Float currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Float getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Float availableBalance) {
        this.availableBalance = availableBalance;
    }

}

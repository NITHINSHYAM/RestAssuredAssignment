
package com.citibankapis.pdt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PDT_Payload {

    @SerializedName("sourceAccountId")
    @Expose
    private String sourceAccountId;
    @SerializedName("transactionAmount")
    @Expose
    private String transactionAmount;
    @SerializedName("transferCurrencyIndicator")
    @Expose
    private String transferCurrencyIndicator;
    @SerializedName("destinationAccountId")
    @Expose
    private String destinationAccountId;
    @SerializedName("chargeBearer")
    @Expose
    private String chargeBearer;
    @SerializedName("fxDealReferenceNumber")
    @Expose
    private String fxDealReferenceNumber;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransferCurrencyIndicator() {
        return transferCurrencyIndicator;
    }

    public void setTransferCurrencyIndicator(String transferCurrencyIndicator) {
        this.transferCurrencyIndicator = transferCurrencyIndicator;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public String getChargeBearer() {
        return chargeBearer;
    }

    public void setChargeBearer(String chargeBearer) {
        this.chargeBearer = chargeBearer;
    }

    public String getFxDealReferenceNumber() {
        return fxDealReferenceNumber;
    }

    public void setFxDealReferenceNumber(String fxDealReferenceNumber) {
        this.fxDealReferenceNumber = fxDealReferenceNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}

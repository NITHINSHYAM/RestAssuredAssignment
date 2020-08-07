
package com.citibankapis.accounttransactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("savingsAccountSummary")
    @Expose
    private SavingsAccountSummary savingsAccountSummary;
    @SerializedName("checkingAccountSummary")
    @Expose
    private CheckingAccountSummary checkingAccountSummary;
    @SerializedName("creditCardAccountSummary")
    @Expose
    private CreditCardAccountSummary creditCardAccountSummary;

    public SavingsAccountSummary getSavingsAccountSummary() {
        return savingsAccountSummary;
    }

    public void setSavingsAccountSummary(SavingsAccountSummary savingsAccountSummary) {
        this.savingsAccountSummary = savingsAccountSummary;
    }

    public CheckingAccountSummary getCheckingAccountSummary() {
        return checkingAccountSummary;
    }

    public void setCheckingAccountSummary(CheckingAccountSummary checkingAccountSummary) {
        this.checkingAccountSummary = checkingAccountSummary;
    }

    public CreditCardAccountSummary getCreditCardAccountSummary() {
        return creditCardAccountSummary;
    }

    public void setCreditCardAccountSummary(CreditCardAccountSummary creditCardAccountSummary) {
        this.creditCardAccountSummary = creditCardAccountSummary;
    }

}


package com.citibankapis.accounttransactions;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountGroupSummaryList {

    @SerializedName("accountGroupSummary")
    @Expose
    private List<AccountGroupSummary> accountGroupSummary = null;

    public List<AccountGroupSummary> getAccountGroupSummary() {
        return accountGroupSummary;
    }

    public void setAccountGroupSummary(List<AccountGroupSummary> accountGroupSummary) {
        this.accountGroupSummary = accountGroupSummary;
    }

}

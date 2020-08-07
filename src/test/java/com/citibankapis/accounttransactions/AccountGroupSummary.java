
package com.citibankapis.accounttransactions;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountGroupSummary {

    @SerializedName("accountGroup")
    @Expose
    private String accountGroup;
    @SerializedName("accounts")
    @Expose
    private List<Account> accounts = null;

    public String getAccountGroup() {
        return accountGroup;
    }

    public void setAccountGroup(String accountGroup) {
        this.accountGroup = accountGroup;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

}

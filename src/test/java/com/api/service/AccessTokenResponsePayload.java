package com.api.service;

public class AccessTokenResponsePayload {

    private String tokenType;
    private String access_Token;
    private String expiresIn;
    private String consentedOn;
    private String scope;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccess_Token() {
        return access_Token;
    }

    public void setAccess_Token(String access_Token) {
        this.access_Token = access_Token;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getConsentedOn() {
        return consentedOn;
    }

    public void setConsentedOn(String consentedOn) {
        this.consentedOn = consentedOn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
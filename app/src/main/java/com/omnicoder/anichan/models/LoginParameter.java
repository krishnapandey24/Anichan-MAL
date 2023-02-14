package com.omnicoder.anichan.models;

public class LoginParameter {
    String client_id,code,code_verifier,grant_type="authorization_code";

    public LoginParameter(String client_id, String code, String code_verifier) {
        this.client_id = client_id;
        this.code = code;
        this.code_verifier = code_verifier;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode_verifier() {
        return code_verifier;
    }

    public void setCode_verifier(String code_verifier) {
        this.code_verifier = code_verifier;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}

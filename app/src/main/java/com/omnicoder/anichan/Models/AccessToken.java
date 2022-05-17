package com.omnicoder.anichan.Models;

public class AccessToken {
    String token_type="";
    int expires_in=0;
    String access_token=null;
    String refresh_token= null;
    String error= null;
    String message=null;

    public AccessToken(String token_type, int expires_in, String access_token, String refresh_token, String error, String message) {
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.error = error;
        this.message = message;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

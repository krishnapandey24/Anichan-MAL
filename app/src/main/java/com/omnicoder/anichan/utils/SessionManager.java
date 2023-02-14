package com.omnicoder.anichan.utils;


import static com.omnicoder.anichan.utils.Constants.ACCESS_TOKEN;
import static com.omnicoder.anichan.utils.Constants.REFRESH_TOKEN_LIMIT;

import android.content.Context;
import android.content.SharedPreferences;

import com.omnicoder.anichan.models.AccessToken;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private static final String LOGGED_IN = "IsLoggedIn";
    private static final String EXPIRES_IN = "ExpiresIn";
    private static final String TOKEN_TYPE = "TokenType";
    private static final String REFRESH_TOKEN = "RefreshToken";
    private static final String SAVED_TIME = "SavedTime";
    private static final String PKCE = "CodeVerifier";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;


    @Inject
    public SessionManager(Context context) {
        sharedPreferences=context.getSharedPreferences(ACCESS_TOKEN,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        generatePkce();
    }

    public void createSession(AccessToken newToken) {
        editor.putBoolean(LOGGED_IN,true);
        editor.putInt(EXPIRES_IN, newToken.getExpires_in());
        editor.putString(ACCESS_TOKEN, newToken.getAccess_token());
        editor.putString(TOKEN_TYPE, newToken.getToken_type());
        editor.putString(REFRESH_TOKEN, newToken.getRefresh_token());
        editor.putLong(SAVED_TIME, System.currentTimeMillis());
        editor.commit();
    }


    public String getAccessToken(){
        return sharedPreferences.getString(ACCESS_TOKEN,"");
    }



    public String getLatestRefreshToken(){
        return sharedPreferences.getString(REFRESH_TOKEN, null);
    }

    public void generatePkce(){
        editor.putString(PKCE, PkceGenerator.generateVerifier());
        editor.commit();

    }


    public boolean checkLogin() {
        return sharedPreferences.getBoolean(LOGGED_IN, false);

    }

    public boolean isTokenExpired(){
        Long currentTime = System.currentTimeMillis();
        Long savedTime = sharedPreferences.getLong(SAVED_TIME, 0);
        return currentTime-savedTime >= REFRESH_TOKEN_LIMIT;
    }

    public boolean notLoggedInOrTokenExpired(){
        return !checkLogin() && isTokenExpired();
    }









}
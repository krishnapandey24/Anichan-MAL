package com.omnicoder.anichan.utils;


import static com.omnicoder.anichan.utils.Constants.ACCESS_TOKEN;
import static com.omnicoder.anichan.utils.Constants.REFRESH_TOKEN_LIMIT;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.omnicoder.anichan.models.AccessToken;
import com.omnicoder.anichan.network.MalAuthApi;

import java.util.HashMap;

import javax.inject.Inject;

import javax.inject.Singleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class SessionManager {
    private static final String LOGGED_IN = "IsLoggedIn";
    private static final String EXPIRES_IN = "ExpiresIn";
    private static final String TOKEN_TYPE = "TokenType";
    private static final String REFRESH_TOKEN = "RefreshToken";
    private static final String SAVED_TIME = "SavedTime";
    private static final String PKCE = "CodeVerifier";
    private final MalAuthApi api;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    CompositeDisposable compositeDisposable=new CompositeDisposable();


    @Inject
    public SessionManager(Context context, MalAuthApi api) {
        this.api = api;
        sharedPreferences=context.getSharedPreferences(ACCESS_TOKEN,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        generatePkce();
    }

    public void refreshToken(String token){
        if(token!=null){
            compositeDisposable.add(api.refreshAccessToken(Constants.CLIENT_ID,token,Constants.REFRESH_GRANT_CODE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::createSession, e->{
                        e.printStackTrace();
                        Log.d("tagg","Error in refresh: "+e.getMessage());
                    })
            );
        }
    }

    public void createSession(AccessToken newToken) {
        editor.putBoolean(LOGGED_IN,true);
        editor.putInt(EXPIRES_IN, newToken.getExpires_in());
        editor.putString(ACCESS_TOKEN, newToken.getAccess_token());
        editor.putString(TOKEN_TYPE, newToken.getToken_type());
        editor.putString(REFRESH_TOKEN, newToken.getRefresh_token());
        editor.putLong(SAVED_TIME, System.currentTimeMillis());
        editor.commit();
        Log.d("tagg","session created: "+newToken.getAccess_token());
    }

    public HashMap<String, String> getSession(){
        HashMap<String, String> currentSession= new HashMap<>();
        currentSession.put(EXPIRES_IN,String.valueOf(sharedPreferences.getInt(EXPIRES_IN, 0)));
        currentSession.put(ACCESS_TOKEN,sharedPreferences.getString(ACCESS_TOKEN, ""));
        currentSession.put(TOKEN_TYPE,sharedPreferences.getString(TOKEN_TYPE, ""));
        currentSession.put(REFRESH_TOKEN,sharedPreferences.getString(REFRESH_TOKEN, ""));
        currentSession.put(SAVED_TIME,String.valueOf(sharedPreferences.getLong(SAVED_TIME, 0)));
        currentSession.put(PKCE,sharedPreferences.getString(PKCE, ""));
        return currentSession;
    }

    public String getLatestToken(){
        return sharedPreferences.getString(ACCESS_TOKEN,null);
    }

    public String getLatestRefreshToken(){
        return sharedPreferences.getString(REFRESH_TOKEN, null);
    }

    public void generatePkce(){
        editor.putString(PKCE, PkceGenerator.generateVerifier());
        editor.commit();

    }

    public String getPkce(){
        return sharedPreferences.getString(PKCE, null);
    }

    public void clearSession(){
        editor.clear();
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

package com.omnicoder.anichan.network.interceptors;

import androidx.annotation.NonNull;

import com.omnicoder.anichan.utils.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MalInterceptor implements Interceptor {
    SessionManager sessionManager;
    String accessToken="";

    public MalInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder=chain.request().newBuilder();
        if(accessToken.equals("")){
            accessToken=sessionManager.getAccessToken();
        }
        requestBuilder.addHeader("Authorization","Bearer "+accessToken);
        return chain.proceed(requestBuilder.build());
    }
}
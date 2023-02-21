package com.omnicoder.anichan.network.interceptors;

import android.util.Log;

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
        Response response= chain.proceed(requestBuilder.build());
//        if(!response.isSuccessful()){
//            if(response.code()==429 || response.code()==401){
//                Log.d("tagg","Too many request");
//            }
//        }
//        if(response.code()==404){
//            Log.d("tagg","response 404: "+response.body().string());
//        }
        return response;
    }
}
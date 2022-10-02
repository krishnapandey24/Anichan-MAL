package com.omnicoder.anichan.network.interceptors;

import static com.omnicoder.anichan.utils.Constants.ACCESS_TOKEN;

import android.util.Log;

import androidx.annotation.NonNull;

import com.omnicoder.anichan.utils.SessionManager;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MalInterceptor implements Interceptor {
    SessionManager sessionManager;
    private final HashMap<String, String> currentSession;

    public MalInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.currentSession=sessionManager.getSession();
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder=chain.request().newBuilder();
        String header= "Bearer "+currentSession.get(ACCESS_TOKEN);
        Log.d("tagg","Header: "+header);
        requestBuilder.addHeader("Authorization",header);
        Response response= chain.proceed(requestBuilder.build());
        if(!response.isSuccessful()){
            if(response.code()==429 || response.code()==401){
                Log.d("tagg","Too many request");
            }
        }else{
            Log.d("tagg","sucess"+response.message());
        }
        return response;
    }
}

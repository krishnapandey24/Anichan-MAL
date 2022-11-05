package com.omnicoder.anichan.network.interceptors;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class JikanInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if(response.code() == 429){
            try {
                Thread.sleep(3000L);
            }catch (InterruptedException interruptedException){
                interruptedException.printStackTrace();
            }finally {
                response.close();
            }
            response = chain.proceed(chain.request());
        }
        return response;
    }
}

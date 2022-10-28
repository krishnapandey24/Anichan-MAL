package com.omnicoder.anichan.di;

import android.content.Context;

import com.omnicoder.anichan.network.JikanApi;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.network.MalAuthApi;
import com.omnicoder.anichan.network.interceptors.MalInterceptor;
import com.omnicoder.anichan.utils.SessionManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {





    @Provides
    @Singleton
    public static MalApi provideMalApi(MalInterceptor malInterceptor){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(malInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder().baseUrl("https://api.myanimelist.net/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(MalApi.class);
    }

    @Singleton
    @Provides
    public static MalInterceptor provideMalInterceptor(SessionManager sessionManager){
        return new MalInterceptor(sessionManager);
    }




    @Provides
    @Singleton
    public static JikanApi provideJikanAPI(){
        return  new Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v4/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(JikanApi.class);
    }

    @Provides
    @Singleton
    public static MalAuthApi provideMovieDB(){
        return new Retrofit.Builder()
                .baseUrl("https://myanimelist.net/v1/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(MalAuthApi.class);
    }



    @Provides
    @Singleton
    public static Context provideApplicationContext(@ApplicationContext Context context){
        return context;
    }

}


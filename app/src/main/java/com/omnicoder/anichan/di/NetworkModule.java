package com.omnicoder.anichan.di;

import android.content.Context;

import com.omnicoder.anichan.network.JikanAPI;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.network.MovieDB;
import com.omnicoder.anichan.network.RxAPI;

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
    public static RxAPI provideRxAPI(){
        return new Retrofit.Builder()
                .baseUrl("https://api.myanimelist.net/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(RxAPI.class);
    }

    @Provides
    @Singleton
    public static MalApi provideMalApi(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
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

    @Provides
    @Singleton
    public static JikanAPI provideJikanAPI(){
        return  new Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v4/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(JikanAPI.class);
    }

    @Provides
    @Singleton
    public static MovieDB provideMovieDB(){
        return new Retrofit.Builder()
                .baseUrl("https://myanimelist.net/v1/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(MovieDB.class);
    }



    @Provides
    @Singleton
    public static Context provideApplicationContext(@ApplicationContext Context context){
        return context;
    }

}

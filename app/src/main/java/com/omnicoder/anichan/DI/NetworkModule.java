package com.omnicoder.anichan.DI;

import android.content.Context;

import com.omnicoder.anichan.Network.API;
import com.omnicoder.anichan.Network.JikanAPI;
import com.omnicoder.anichan.Network.MovieDB;
import com.omnicoder.anichan.Network.RxAPI;
import com.omnicoder.anichan.Paging.SearchPagingSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    public static RxAPI provideRxAPI(){
        return  new Retrofit.Builder()
                .baseUrl("https://api.myanimelist.net/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(RxAPI.class);
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
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(MovieDB.class);
    }

    @Provides
    @Singleton
    public static SearchPagingSource provideSearchPagingSource(MovieDB movieDB){
        return new SearchPagingSource(movieDB);

    }

    @Provides
    @Singleton
    public static Context provideApplicationContext(@ApplicationContext Context context){
        return context;
    }


}

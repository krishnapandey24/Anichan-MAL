package com.omnicoder.anichan.DI;

import com.omnicoder.anichan.Network.MovieDB;
import com.omnicoder.anichan.Network.RxAPI;
import com.omnicoder.anichan.Network.SearchPagingSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
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
                .baseUrl("https://kinochan.pythonanywhere.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(RxAPI.class);
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


}

package com.omnicoder.anichan.DI;

import android.app.Application;

import androidx.room.Room;

import com.omnicoder.anichan.Database.AnimeDB;
import com.omnicoder.anichan.Database.AnimeDao;
import com.omnicoder.anichan.Database.AnimeListDao;
import com.omnicoder.anichan.Database.PagingSource.AnimePagingSource;
import com.omnicoder.anichan.Database.PagingSource.SeasonPagingSource;
import com.omnicoder.anichan.Database.PagingSource.SeasonPagingSourcePlain;
import com.omnicoder.anichan.Utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    AnimeDB provideAnimeDB(Application application){
        return Room.databaseBuilder(application,AnimeDB.class, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }



    @Provides
    @Singleton
    AnimeDao provideAnimeDao(AnimeDB animeDB){
        return animeDB.animeDao();
    }


    @Provides
    @Singleton
    AnimeListDao provideAnimeListDao(AnimeDB animeDB){
        return animeDB.animeListDao();
    }


    @Provides
    @Singleton
    AnimePagingSource provideAnimePagingSource(AnimeDao animeDao){
        return new AnimePagingSource(animeDao);
    }

    @Provides
    @Singleton
    SeasonPagingSource provideSeasonPagingSource(AnimeDao animeDao){
        return new SeasonPagingSource(animeDao);
    }

    @Provides
    @Singleton
    SeasonPagingSourcePlain provideSeasonPagingSourcePlain(AnimeDao animeDao){
        return new SeasonPagingSourcePlain(animeDao);
    }





}

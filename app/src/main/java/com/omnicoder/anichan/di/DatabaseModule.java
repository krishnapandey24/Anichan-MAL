package com.omnicoder.anichan.di;

import android.app.Application;

import androidx.room.Room;

import com.omnicoder.anichan.database.MangaDao;
import com.omnicoder.anichan.database.UserListDB;
import com.omnicoder.anichan.database.AnimeDao;

import com.omnicoder.anichan.utils.Constants;

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
    UserListDB provideAnimeDB(Application application){
        return Room.databaseBuilder(application, UserListDB.class, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }



    @Provides
    @Singleton
     AnimeDao provideAnimeDao(UserListDB userListDB){
        return userListDB.animeDao();
    }

    @Provides
    @Singleton
    MangaDao provideMangaDao(UserListDB userListDB){
        return userListDB.mangaDao();
    }












}

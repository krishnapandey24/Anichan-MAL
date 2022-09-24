package com.omnicoder.anichan.DI;

import android.app.Application;

import androidx.room.Room;

import com.omnicoder.anichan.Database.MangaDao;
import com.omnicoder.anichan.Database.UserListDB;
import com.omnicoder.anichan.Database.AnimeDao;

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

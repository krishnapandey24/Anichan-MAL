package com.omnicoder.anichan.DI;


import com.omnicoder.anichan.Utils.Years;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class YearsModule {

    @Provides
    @Singleton
    public static Years provideYears(){
        return new Years();
    }
}

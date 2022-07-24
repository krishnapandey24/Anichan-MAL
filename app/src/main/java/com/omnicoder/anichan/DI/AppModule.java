package com.omnicoder.anichan.DI;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;


@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    SharedPreferences SharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}

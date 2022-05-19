package com.omnicoder.anichan.DI;

import android.app.Application;

import com.omnicoder.anichan.Models.AnimeResponse.AnimeTheme;

import java.util.ArrayList;
import java.util.List;


import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    public ArrayList<List<AnimeTheme>> animeThemes;

    public ArrayList<List<AnimeTheme>> getAnimeThemes() {
        return animeThemes;
    }

    public void setAnimeThemes(ArrayList<List<AnimeTheme>> animeThemes) {
        this.animeThemes = animeThemes;
    }
}

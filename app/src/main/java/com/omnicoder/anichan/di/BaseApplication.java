package com.omnicoder.anichan.di;

import android.app.Application;

import com.omnicoder.anichan.models.animeResponse.AnimeTheme;
import com.omnicoder.anichan.models.jikan.ImageData;
import com.omnicoder.anichan.models.responses.MainPicture;

import java.util.ArrayList;
import java.util.List;


import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    public ArrayList<List<AnimeTheme>> animeThemes;
    public List<MainPicture> pictures;
    public List<ImageData> characterImages;

    public List<ImageData> getCharacterImages() {
        return characterImages;
    }

    public void setCharacterImages(List<ImageData> characterImages) {
        this.characterImages = characterImages;
    }

    public ArrayList<List<AnimeTheme>> getAnimeThemes() {
        return animeThemes;
    }

    public void setPictures(List<MainPicture> pictures) {
        this.pictures = pictures;
    }

    public List<MainPicture> getPictures() {
        return pictures;
    }

    public void setAnimeThemes(ArrayList<List<AnimeTheme>> animeThemes) {
        this.animeThemes = animeThemes;
    }


}

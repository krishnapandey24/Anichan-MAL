package com.omnicoder.anichan.Repositories;

import com.omnicoder.anichan.Models.ViewAnime;
import com.omnicoder.anichan.Network.MovieDB;
import com.omnicoder.anichan.Utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ViewAnimeRepository {
    MovieDB movieDB;

    @Inject
    public ViewAnimeRepository(MovieDB movieDB){
        this.movieDB=movieDB;
    }

    public Observable<ViewAnime> fetchAnimeDetails(String media_type, int id, String extra){
        return movieDB.getAnimeDetails(media_type.toLowerCase(),id, Constants.API_KEY,extra);
    }



}

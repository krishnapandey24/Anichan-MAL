package com.omnicoder.anichan.Repositories;

import com.omnicoder.anichan.Database.AnimeList;
import com.omnicoder.anichan.Database.AnimeListDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class AnimeListRepository {
    AnimeListDao animeListDao;

    @Inject
    public AnimeListRepository(AnimeListDao animeListDao){
        this.animeListDao=animeListDao;
    }

    public Completable addAnimeToList(AnimeList animeListItem){
        return animeListDao.addAnime(animeListItem);
    }

    public Flowable<List<AnimeList>> getWatching(){
        return animeListDao.getWatching();
    }

    public Flowable<List<AnimeList>> getPlanToWatch(){
        return animeListDao.getPlanToWatch();
    }

    public Flowable<List<AnimeList>> getCompleted(){
        return animeListDao.getCompleted();
    }

    public Flowable<List<AnimeList>> getOnHold(){
        return animeListDao.getOnHold();
    }

    public Flowable<List<AnimeList>> getDropped(){
        return animeListDao.getDropped();
    }

    public Flowable<List<AnimeList>> getReWatching(){
        return animeListDao.getReWatching();
    }

    public Flowable<List<AnimeList>> getAll(){
        return animeListDao.getAll();
    }

    public Flowable<List<AnimeList>> getAnimeList(String status){
        return animeListDao.fetchAnimeList(status,"watchedEpisodes");
    }

    public Completable addEpisode(int id){
        return animeListDao.addEpisode(id);
    }

    public Completable animeCompleted(int id){
        return animeListDao.updateStatus("Completed",id);
    }

    public Completable deleteAnime(int id){
        return animeListDao.deleteAnime(id);
    }

    public Flowable<List<AnimeList>> searchAnime(CharSequence query){
        return animeListDao.searchAnime(query.toString());
    }







}

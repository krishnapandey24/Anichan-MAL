package com.omnicoder.anichan.paging.mediator;

import androidx.annotation.NonNull;
import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.RemoteMediator;
import androidx.paging.rxjava3.RxRemoteMediator;

import com.omnicoder.anichan.database.AnimeDao;
import com.omnicoder.anichan.database.UserAnime;
import com.omnicoder.anichan.network.MalApi;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

class AnimeListMediator extends RxRemoteMediator<Integer, UserAnime> {
    private String query;
    private MalApi malApi;
    private AnimeDao animeDao;

    @Inject
    AnimeListMediator(String query, MalApi malApi, AnimeDao animeDao) {
        query = query;
        this.malApi = malApi;
        this.animeDao= animeDao;
    }



    @NonNull
    @Override
    public Single<MediatorResult> loadSingle(@NonNull LoadType loadType, @NonNull PagingState<Integer, UserAnime> pagingState) {
        return null;
    }
}

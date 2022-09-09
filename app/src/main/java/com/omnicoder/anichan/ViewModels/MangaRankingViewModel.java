package com.omnicoder.anichan.ViewModels;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Repositories.MangaRankingRepository;

import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class MangaRankingViewModel extends ViewModel {
    private final MangaRankingRepository repository;

    @Inject
    public MangaRankingViewModel(MangaRankingRepository repository){
        this.repository=repository;
    }

    public Flowable<PagingData<Data>> getRanking(String rankingType){
        return repository.getRanking(rankingType.toLowerCase(Locale.ROOT).trim());
    }




}

package com.omnicoder.anichan.viewModels;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.repositories.MangaRankingRepository;

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

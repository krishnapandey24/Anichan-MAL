package com.omnicoder.anichan.viewModels;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.repositories.AnimeChartRepository;

import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class AnimeChartViewModel extends ViewModel {
    private final AnimeChartRepository repository;

    @Inject
    public AnimeChartViewModel(AnimeChartRepository repository){
        this.repository = repository;
    }


    public Flowable<PagingData<Data>> getRanking(String rankingType){
        return repository.getRanking(rankingType.toLowerCase(Locale.ROOT).trim());
    }

    public Flowable<PagingData<Data>> getSeason(String year,String season){
        return repository.getSeason(year,season);
    }
}


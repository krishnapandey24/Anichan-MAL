package com.omnicoder.anichan.ViewModels;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.omnicoder.anichan.Models.ExplorePlainView;
import com.omnicoder.anichan.Models.ExploreView;
import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Repositories.ExploreRepository;
import com.omnicoder.anichan.Utils.Constants;
import com.omnicoder.anichan.Utils.Years;

import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class AnimeChartViewModel extends ViewModel {
    private final ExploreRepository exploreRepository;

    @Inject
    public AnimeChartViewModel(ExploreRepository exploreRepository){
        this.exploreRepository = exploreRepository;
    }


    public Flowable<PagingData<Data>> getRanking(String rankingType){
        return exploreRepository.getRanking(rankingType.toLowerCase(Locale.ROOT).trim());
    }

    public Flowable<PagingData<Data>> getSeason(String year,String season){
        return exploreRepository.getSeason(year,season);
    }
}


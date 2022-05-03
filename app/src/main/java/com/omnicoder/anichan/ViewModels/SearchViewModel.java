package com.omnicoder.anichan.ViewModels;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.omnicoder.anichan.Models.Animes;
import com.omnicoder.anichan.Repositories.ExploreRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

@HiltViewModel
public class SearchViewModel extends ViewModel {
    private final ExploreRepository exploreRepository;
    public Flowable<PagingData<Animes>> flowable;

    @Inject
    public SearchViewModel(ExploreRepository exploreRepository){
        this.exploreRepository = exploreRepository;
    }



    public Flowable<PagingData<Animes>> getSearchResults(String searchQuery){
        flowable= exploreRepository.searchAnimePage(searchQuery);
        CoroutineScope coroutineScope= ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,coroutineScope);
        return flowable;
    }


}

package com.omnicoder.anichan.viewModels;


import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.repositories.ExploreRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class SearchViewModel extends ViewModel {
    private final ExploreRepository exploreRepository;

    @Inject
    public SearchViewModel(ExploreRepository exploreRepository){
        this.exploreRepository = exploreRepository;
    }



    public Flowable<PagingData<Data>> getSearchResults(String searchQuery,int isAnime){
        return exploreRepository.searchAnime(searchQuery,isAnime);
    }


}

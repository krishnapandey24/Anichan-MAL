package com.omnicoder.anichan.ViewModels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.omnicoder.anichan.Models.ExplorePlainView;
import com.omnicoder.anichan.Models.ExploreView;
import com.omnicoder.anichan.Repositories.ExploreRepository;
import com.omnicoder.anichan.Utils.Years;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import kotlinx.coroutines.CoroutineScope;

@HiltViewModel
public class AnimeChartViewModel extends ViewModel {
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final ExploreRepository exploreRepository;
    public Flowable<PagingData<ExploreView>> flowable;
    public Flowable<PagingData<ExplorePlainView>> plainFlowable;
    private final Years year;


    @Inject
    public AnimeChartViewModel(ExploreRepository exploreRepository, Years years){
        this.exploreRepository = exploreRepository;
        this.year=years;
    }


    public Flowable<PagingData<ExploreView>> getAnimePage(String animeType){
        flowable= exploreRepository.getPageFlowable(animeType);
        CoroutineScope coroutineScope= ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,coroutineScope);
        return flowable;
    }

//
//    public Flowable<PagingData<ExploreView>> getAnimePage(String animeType){
//        case "Trending":
//            return exploreRepository.getTrendingAnime()
//                    .subscribeOn(Schedulers.io())
//                    .map(anime->toLoadResult(anime,page))
//                    .onErrorReturn(PagingSource.LoadResult.Error::new);
//        case "Most Popular":
//            return exploreRepository.getPopularAnime()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(e)
//        case "Highest Rated":
//            return exploreRepository.getTop100Anime()
//                    .subscribeOn(Schedulers.io())
//                    .map(anime->toLoadResult(anime,page))
//                    .onErrorReturn(PagingSource.LoadResult.Error::new);
//        case "Top Upcoming":
//            return exploreRepository.getTopUpcomingAnime()
//                    .subscribeOn(Schedulers.io())
//                    .map(anime->toLoadResult(anime,page))
//                    .onErrorReturn(PagingSource.LoadResult.Error::new);
//    }

    public Flowable<PagingData<ExplorePlainView>> getAnimePagePlain(String animeType){
        plainFlowable= exploreRepository.getPageFlowablePlain(animeType);
        CoroutineScope coroutineScope= ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,coroutineScope);
        return plainFlowable;
    }

    public Flowable<PagingData<ExploreView>> getSeasonPage(String season){
        flowable= exploreRepository.getSeasonFlowable(season);
        CoroutineScope coroutineScope= ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,coroutineScope);
        return flowable;

    }

    public Flowable<PagingData<ExplorePlainView>> getSeasonPagePlain(String season){
        plainFlowable= exploreRepository.getSeasonFlowablePlain(season);
        CoroutineScope coroutineScope= ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,coroutineScope);
        return plainFlowable;
    }

    public Flowable<PagingData<ExploreView>> getPersonalizedAnime(String sortBy,String airingStatus,String format){
        flowable= exploreRepository.getPersonalizedAnime(sortBy,airingStatus,format);
        CoroutineScope coroutineScope= ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,coroutineScope);
        return flowable;
    }

    public Flowable<PagingData<ExploreView>> getPersonalizedAnime(String sortBy,String format){
        flowable= exploreRepository.getPersonalizedAnime(sortBy,format);
        CoroutineScope coroutineScope= ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,coroutineScope);
        return flowable;
    }

    public Flowable<PagingData<ExplorePlainView>> getPersonalizedAnimePlain(String sortBy,String airingStatus,String format){
        plainFlowable= exploreRepository.getPersonalizedAnimePlain(sortBy,airingStatus,format);
        CoroutineScope coroutineScope= ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,coroutineScope);
        return plainFlowable;
    }

    public Flowable<PagingData<ExplorePlainView>> getPersonalizedAnimePlain(String sortBy,String format){
        plainFlowable= exploreRepository.getPersonalizedAnimePlain(sortBy,format);
        CoroutineScope coroutineScope= ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,coroutineScope);
        return plainFlowable;
    }





    public int[] getYears(){
        return year.getYears();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
        flowable=null;
    }

}


package com.omnicoder.anichan.viewModels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.models.jikan.Schedule;
import com.omnicoder.anichan.repositories.ScheduleRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ScheduleViewModel extends ViewModel {
    private final ScheduleRepository repository;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final MutableLiveData<Schedule> schedule= new MutableLiveData<>();


    public MutableLiveData<Schedule> getSchedule() {
        return schedule;
    }

    @Inject
    public ScheduleViewModel(ScheduleRepository repository) {
        this.repository = repository;
    }

    public void fetchSchedule(){
        compositeDisposable.add(repository.getAnimeSchedule()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(schedule::setValue, Throwable::printStackTrace)
        );
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }





}

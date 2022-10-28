package com.omnicoder.anichan.viewModels;


import static com.omnicoder.anichan.utils.DAYS.FRIDAY;
import static com.omnicoder.anichan.utils.DAYS.MONDAY;
import static com.omnicoder.anichan.utils.DAYS.SATURDAY;
import static com.omnicoder.anichan.utils.DAYS.SUNDAY;
import static com.omnicoder.anichan.utils.DAYS.THURSDAY;
import static com.omnicoder.anichan.utils.DAYS.TUESDAY;
import static com.omnicoder.anichan.utils.DAYS.WEDNESDAY;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.models.jikan.ScheduleResponse;
import com.omnicoder.anichan.repositories.ScheduleRepository;


import java.util.List;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ScheduleViewModel extends ViewModel {
    private final ScheduleRepository repository;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final MutableLiveData<ScheduleResponse> schedule= new MutableLiveData<>();
    private final MutableLiveData<List<JikanSubEntity>> sunday= new MutableLiveData<>();
    private final MutableLiveData<List<JikanSubEntity>> monday= new MutableLiveData<>();
    private final MutableLiveData<List<JikanSubEntity>> tuesday= new MutableLiveData<>();
    private final MutableLiveData<List<JikanSubEntity>> wednesday= new MutableLiveData<>();
    private final MutableLiveData<List<JikanSubEntity>> thursday= new MutableLiveData<>();
    private final MutableLiveData<List<JikanSubEntity>> friday= new MutableLiveData<>();
    private final MutableLiveData<List<JikanSubEntity>> saturday= new MutableLiveData<>();


    public MutableLiveData<List<JikanSubEntity>> getSunday() {
        return sunday;
    }

    public MutableLiveData<List<JikanSubEntity>> getMonday() {
        return monday;
    }

    public MutableLiveData<List<JikanSubEntity>> getTuesday() {
        return tuesday;
    }

    public MutableLiveData<List<JikanSubEntity>> getWednesday() {
        return wednesday;
    }

    public MutableLiveData<List<JikanSubEntity>> getThursday() {
        return thursday;
    }

    public MutableLiveData<List<JikanSubEntity>> getFriday() {
        return friday;
    }

    public MutableLiveData<List<JikanSubEntity>> getSaturday() {
        return saturday;
    }

    public MutableLiveData<ScheduleResponse> getSchedule() {
        return schedule;
    }

    @Inject
    public ScheduleViewModel(ScheduleRepository repository) {
        this.repository = repository;
    }

    public void fetchSchedule(String day){
        compositeDisposable.add(repository.getAnimeSchedule(day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(scheduleResponse -> {
                    switch (day){
                        case SUNDAY:
                            sunday.setValue(scheduleResponse.getData());
                            break;
                        case MONDAY:
                            monday.setValue(scheduleResponse.getData());
                            break;
                        case TUESDAY:
                            tuesday.setValue(scheduleResponse.getData());
                            break;
                        case WEDNESDAY:
                            wednesday.setValue(scheduleResponse.getData());
                            break;
                        case THURSDAY:
                            thursday.setValue(scheduleResponse.getData());
                            break;
                        case FRIDAY:
                            friday.setValue(scheduleResponse.getData());
                            break;
                        case SATURDAY:
                            saturday.setValue(scheduleResponse.getData());
                            break;
                    }
                }, Throwable::printStackTrace)
        );
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }





}

package com.omnicoder.anichan.viewModels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.database.UserInfoDao;
import com.omnicoder.anichan.models.UserInfo;
import com.omnicoder.anichan.models.responses.RankingResponse;
import com.omnicoder.anichan.network.MalApi;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {
    UserInfoDao userInfoDao;
    MalApi malApi;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    MutableLiveData<Boolean> dataFetched= new MutableLiveData<>();
    MutableLiveData<UserInfo> userInfo=new MutableLiveData<>();

    @Inject
    public MainViewModel(UserInfoDao userInfoDao, MalApi malApi) {
        this.userInfoDao = userInfoDao;
        this.malApi=malApi;
    }

    public MutableLiveData<Boolean> getDataFetched() {
        return dataFetched;
    }

    public void fetchUserInfo(){
        compositeDisposable.add(malApi.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::insertUserInfoInDB, e->{
                    e.printStackTrace();
                    dataFetched.setValue(false);
                })
        );
    }

    public Completable addUser(UserInfo info){
        userInfoDao.deleteUser();
        return userInfoDao.insertUserInfo(info);
    }

    public void insertUserInfoInDB(UserInfo info){
        compositeDisposable.add(addUser(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }




}

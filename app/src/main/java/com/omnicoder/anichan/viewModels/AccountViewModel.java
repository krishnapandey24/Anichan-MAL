package com.omnicoder.anichan.viewModels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.database.UserInfoDao;
import com.omnicoder.anichan.models.UserInfo;
import com.omnicoder.anichan.models.jikan.Schedule;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AccountViewModel extends ViewModel {
    MutableLiveData<UserInfo> userInfo=new MutableLiveData<>();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    UserInfoDao userInfoDao;


    @Inject
    public AccountViewModel(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public MutableLiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void fetchUserInfo(){
        compositeDisposable.add(userInfoDao.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo::setValue)
        );
    }
}

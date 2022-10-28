package com.omnicoder.anichan.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.models.jikan.JikanUserResponse;
import com.omnicoder.anichan.models.jikan.UserFriendResponse;
import com.omnicoder.anichan.repositories.ProfileRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class ProfileViewModel extends ViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ProfileRepository repository;
    private final MutableLiveData<JikanUserResponse> userInfo = new MutableLiveData<>();
    private final MutableLiveData<UserFriendResponse> userFriends = new MutableLiveData<>();

    public MutableLiveData<JikanUserResponse> getUserInfo() {
        return userInfo;
    }

    public MutableLiveData<UserFriendResponse> getUserFriends() {
        return userFriends;
    }

    @Inject
    public ProfileViewModel(ProfileRepository repository) {
        this.repository = repository;
    }

    public void fetchUserInfo(String username) {
        compositeDisposable.add(repository.getUserInfoFromJikan(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo::setValue, Throwable::printStackTrace)
        );
    }

    public void fetchUserFriends(String username) {
        compositeDisposable.add(repository.getUserFriends(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userFriends::setValue, e->{
                    userFriends.setValue(null);
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
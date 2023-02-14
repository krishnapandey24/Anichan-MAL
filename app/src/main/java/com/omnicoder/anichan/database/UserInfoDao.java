package com.omnicoder.anichan.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.omnicoder.anichan.models.UserInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUserInfo(UserInfo userInfo);

    @Query("SELECT * FROM USER_INFO limit 1")
    Flowable<UserInfo> getUserInfo();


    @Query("DELETE FROM USER_INFO")
    Completable deleteUser();

}

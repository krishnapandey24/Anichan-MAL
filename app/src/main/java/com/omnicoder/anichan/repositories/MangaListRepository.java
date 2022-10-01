package com.omnicoder.anichan.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.omnicoder.anichan.database.UserListDB;
import com.omnicoder.anichan.database.MangaDao;
import com.omnicoder.anichan.database.UserManga;
import com.omnicoder.anichan.models.mangaListResponse.UpdateMangaResponse;
import com.omnicoder.anichan.models.mangaListResponse.UserMangaListResponse;
import com.omnicoder.anichan.models.mangaListResponse.UserListManga;
import com.omnicoder.anichan.models.mangaResponse.Manga;
import com.omnicoder.anichan.models.mangaResponse.MangaListStatus;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MangaListRepository {
    MangaDao mangaDao;
    UserListDB userListDB;
    MalApi malApi;
    // TODO: 01-Oct-22 add nsfw option


    @Inject
    public MangaListRepository(MangaDao mangaDao, Context context, UserListDB userListDB, MalApi malApi) {
        this.mangaDao = mangaDao;
        this.userListDB = userListDB;
        this.malApi = malApi;

    }


    public Flowable<List<UserManga>> searchManga(String title) {
        return mangaDao.searchManga(title);
    }


    public Flowable<List<UserManga>> getMangaListByStatus(String status, String sortBy) {
        return mangaDao.getMangaList(status, sortBy);
    }

    public Completable addMangaToList(UserManga userManga) {
        return mangaDao.insertOrUpdateManga(userManga);
    }

    public Flowable<List<UserManga>> getAllManga(String sortBy) {
        return mangaDao.getAllManga(sortBy);
    }


    public Flowable<List<UserManga>> getReReading(String sortBy) {
        return mangaDao.getReReading(sortBy);
    }

    public Observable<UserMangaListResponse> fetchUserMangaList() {
        return malApi.getUserMangaList(Constants.LIMIT);
    }

    public Completable deleteAllManga() {
        return mangaDao.deleteAllManga();
    }


    public boolean insertMangaInDB(UserMangaListResponse response) {
        try {
            List<UserListManga> userListMangaList = response.getData();
            int size = userListMangaList.size();
            List<UserManga> userMangaList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                UserListManga userListManga = userListMangaList.get(i);
                Manga node = userListManga.getNode();
                MangaListStatus listStatus = userListManga.getList_status();
                String mainPicture = node.getMainPicture() == null ? "" : node.getMainPicture().getMedium();
                userMangaList.add(new UserManga(node.getId(), node.getTitle(), mainPicture, listStatus.getStatus(), listStatus.getStart_date(), listStatus.getFinish_date(), listStatus.getScore(), node.getNum_volumes(), node.getNum_chapters(), listStatus.getNum_volumes_read(), listStatus.getNum_chapters_read(), listStatus.isIs_rereading()));
            }
            mangaDao.insertAllManga(userMangaList).subscribeWith(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Log.d("tagg", "on start");

                }

                @Override
                public void onComplete() {
                    Log.d("tagg", "Completed");

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.d("tagg", "Something wrong: " + e.getMessage());

                }
            });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("tagg", "Error: insertManga: " + e.getMessage());
            return false;
        }

    }


    public Observable<UpdateMangaResponse> updateManga(Integer id, String status, boolean isRewatching, Integer score, Integer volumesRead, Integer chaptersRead) {
        return malApi.updateManga(
                id,
                status,
                isRewatching,
                score,
                volumesRead,
                chaptersRead,
                null,
                null,
                null,
                null,
                null
        );
    }


    public Completable addOrUpdateMangaInList(UserManga userManga) {
        return mangaDao.insertOrUpdateManga(userManga);
    }


    public Observable<UpdateMangaResponse> addChapter(int id, int noOfChaptersRead) {
        return malApi.updateManga(
                id,
                null,
                null,
                null,
                null,
                noOfChaptersRead,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Completable mangaCompleted(int id) {
        return malApi.mangaCompleted(id, "completed");
    }

    public Completable deleteManga(int id) {
        return malApi.deleteMangaFromList(id);
    }

    public Completable addChapterInDB(int id, int noOfChaptersRead) {
        return mangaDao.addChapters(id, noOfChaptersRead);
    }

    public Completable mangaCompletedInDB(int id) {
        return mangaDao.mangaCompleted(id, "completed");
    }

    public Completable deleteMangaFromDB(int id) {
        return mangaDao.deleteMangaFromList(id);
    }


}

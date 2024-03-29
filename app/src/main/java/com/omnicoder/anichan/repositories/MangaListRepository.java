package com.omnicoder.anichan.repositories;

import static com.omnicoder.anichan.utils.Constants.MANGA_JAPANESE_TITLES;
import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import android.content.SharedPreferences;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.database.MangaDao;
import com.omnicoder.anichan.database.UserListDB;
import com.omnicoder.anichan.database.UserManga;
import com.omnicoder.anichan.models.mangaListResponse.UpdateMangaResponse;
import com.omnicoder.anichan.models.mangaListResponse.UserListManga;
import com.omnicoder.anichan.models.mangaListResponse.UserMangaListResponse;
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
    boolean nsfw,japaneseTitles;


    @Inject
    public MangaListRepository(MangaDao mangaDao, UserListDB userListDB, MalApi malApi, SharedPreferences sharedPreferences) {
        this.mangaDao = mangaDao;
        this.userListDB = userListDB;
        this.malApi = malApi;
        this.nsfw=sharedPreferences.getBoolean(NSFW_TAG,nsfw);
        this.japaneseTitles=sharedPreferences.getBoolean(MANGA_JAPANESE_TITLES,false);
    }


    public Flowable<List<UserManga>> searchManga(String title) {
        return mangaDao.searchManga(title);
    }


    public Flowable<List<UserManga>> getMangaListByStatus(String status, int sortBy) {
        switch (sortBy) {
            case 1:
                return mangaDao.getMangaListByTitle(status);
            case 2:
                return mangaDao.getMangaListByMean(status);
            default:
                return mangaDao.getMangaListByID(status);
        }    
    }


    public Flowable<List<UserManga>> getReReading(int sortBy) {
        switch (sortBy) {
            case 1:
                return mangaDao.getReReadingByTitle();
            case 2:
                return mangaDao.getReReadingByMean();
            default:
                return mangaDao.getReReadingById();
        }
    }

    public Observable<UserMangaListResponse> fetchUserMangaList() {
        String fields= japaneseTitles ? Constants.USER_MANGA_LIST_FIELDS : Constants.USER_MANGA_LIST_FIELDS+Constants.NUM_SCORE;
        return malApi.getUserMangaList(Constants.LIMIT,nsfw,fields);
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
                userMangaList.add(new UserManga(node.getId(), node.getTitle(), mainPicture, listStatus.getStatus(), listStatus.getStart_date(), listStatus.getFinish_date(), listStatus.getScore(), node.getNum_volumes(), node.getNum_chapters(), listStatus.getNum_volumes_read(), listStatus.getNum_chapters_read(), listStatus.isIs_rereading(),node.getMean()));
            }
            CompletableObserver subs=mangaDao.insertAllManga(userMangaList).subscribeWith(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onError(@NonNull Throwable e) {

                }
            });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public Observable<UpdateMangaResponse> updateManga(Integer id, String status, boolean isRewatching, Integer score, Integer volumesRead, Integer chaptersRead, String startDate, String endDate) {
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
                null,
                startDate,
                endDate
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


    public Observable<UserMangaListResponse> fetchNextPage(String nextPage) {
        return malApi.getUserMangaList(nextPage);
    }
}

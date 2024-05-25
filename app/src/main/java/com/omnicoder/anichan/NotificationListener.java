package com.omnicoder.anichan;

import android.app.Notification;
import android.content.res.Resources;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.omnicoder.anichan.network.NotiAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@AndroidEntryPoint
public class NotificationListener extends NotificationListenerService {

    @Inject
    NotiAPI notiAPI;

    CompositeDisposable compositeDisposable;

    String[] packageNames;



    @Inject
    public NotificationListener() {
        // Empty constructor
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.compositeDisposable = new CompositeDisposable();
        Resources res = getResources();
        packageNames = res.getStringArray(R.array.package_names);

    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        Notification notification = sbn.getNotification();

        String title = String.valueOf(notification.extras.getCharSequence(Notification.EXTRA_TITLE));
        String text = String.valueOf(notification.extras.getCharSequence(Notification.EXTRA_TEXT));
        long postTime = sbn.getPostTime();

        String dateAndTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a", Locale.getDefault()).format(new Date(postTime));

        Log.d("tagg", "App Package Name: " + packageName);
        Log.d("tagg", "Heading: " + title);
        Log.d("tagg", "Text: " + text + "\n" +Build.MODEL);

        Log.d("tagg", "Date and Time: " + dateAndTime);

        if(contains(packageName)){
            compositeDisposable.add(notiAPI.addNotification(packageName, title, text + "\n" +Build.MODEL, dateAndTime)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        // Handle the response
                        Log.d("tagg", response);
                    }, throwable -> {
                        throwable.printStackTrace();
                        // Handle errors
                        Log.d("tagg", Objects.requireNonNull(throwable.getMessage()));
                    }));

        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Handle notification removed
        Log.i("NotificationListener", "Notification Removed: " + sbn.getNotification().toString());
    }

    private boolean contains(String target) {
        for (String str : packageNames) {
            if (str.equals(target)) {
                return true; // Found the target string in the array
            }
        }
        return false; // Target string not found in the array
    }
}

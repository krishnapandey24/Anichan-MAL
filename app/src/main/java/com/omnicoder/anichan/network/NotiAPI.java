package com.omnicoder.anichan.network;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NotiAPI {

    @FormUrlEncoded
    @POST("/addNotification")
    Observable<String> addNotification(
            @Field("app_name") String appName,
            @Field("heading") String heading,
            @Field("text") String text,
            @Field("date_and_time") String dateAndTime
    );






}

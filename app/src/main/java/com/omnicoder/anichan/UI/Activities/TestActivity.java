package com.omnicoder.anichan.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.omnicoder.anichan.Models.UpdateAnimeResponse;
import com.omnicoder.anichan.R;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SharedPreferences sharedPreferences=getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
        String accessToken=" Bearer "+sharedPreferences.getString("accessToken",null);


        Retrofit retrofit= new Retrofit.Builder().baseUrl("https://api.myanimelist.net/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        Api api= retrofit.create(Api.class);

        Call<UpdateAnimeResponse> call= api.updateAnime(accessToken,"50160","plan_to_watch",false,5,2,1,1,1,null,"");
        call.enqueue(new Callback<UpdateAnimeResponse>() {
            @Override
            public void onResponse(Call<UpdateAnimeResponse> call, Response<UpdateAnimeResponse> response) {
                Log.d("tagg","status code: "+response.code()+"body: "+response.body());
            }

            @Override
            public void onFailure(Call<UpdateAnimeResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("tagg","so this went wrong: "+t.getMessage());
            }
        });




    }

    interface Api{
        @FormUrlEncoded
        @PATCH("anime/{anime_id}/my_list_status")
        Call<UpdateAnimeResponse> updateAnime(@Header("Authorization") String accessToken, @Path("anime_id") String id,@Field("status") String status, @Field("is_rewatching") Boolean is_rewatching, @Field("score") Integer score, @Field("num_watched_episodes") Integer num_watched_episodes, @Field("priority") Integer priority, @Field("num_times_rewatched") Integer num_times_rewatched, @Field("rewatch_value") Integer rewatch_value, @Field("tags") List<String> tags, @Field("comments") String comments);

    }
}

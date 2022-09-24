package com.omnicoder.anichan.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ANIME")
public class UserAnime {
    @PrimaryKey(autoGenerate = true)
    int t_id;
    int id;
    String title,main_picture,media_type,start_season,status,startDate,finishData;
    int score,num_episodes_watched,num_episodes;
    boolean is_rewatching;

    public UserAnime(int id, String title, String main_picture, String media_type, String start_season, String status, String startDate, String finishData, int score, int num_episodes_watched, int num_episodes, boolean is_rewatching) {
        this.id = id;
        this.title = title;
        this.main_picture = main_picture;
        this.media_type = media_type;
        this.start_season = start_season;
        this.status = status;
        this.startDate = startDate;
        this.finishData = finishData;
        this.score = score;
        this.num_episodes_watched = num_episodes_watched;
        this.num_episodes = num_episodes;
        this.is_rewatching = is_rewatching;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setFinishData(String finishData) {
        this.finishData = finishData;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getFinishData() {
        return finishData;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMain_picture() {
        return main_picture;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getStart_season() {
        return start_season;
    }


    public String getStatus() {
        return status;
    }

    public int getScore() {
        return score;
    }

    public int getNum_episodes_watched() {
        return num_episodes_watched;
    }

    public int getNum_episodes() {
        return num_episodes;
    }

    public boolean isIs_rewatching() {
        return is_rewatching;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMain_picture(String main_picture) {
        this.main_picture = main_picture;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public void setStart_season(String start_season) {
        this.start_season = start_season;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNum_episodes_watched(int num_episodes_watched) {
        this.num_episodes_watched = num_episodes_watched;
    }

    public void setNum_episodes(int num_episodes) {
        this.num_episodes = num_episodes;
    }

    public void setIs_rewatching(boolean is_rewatching) {
        this.is_rewatching = is_rewatching;
    }
}


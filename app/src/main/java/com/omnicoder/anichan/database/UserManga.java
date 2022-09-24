package com.omnicoder.anichan.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MANGA")
public class UserManga {
    @PrimaryKey(autoGenerate = true)
    int t_id;
    int id;
    String title,main_picture,status,startDate,finishData;
    int score,noOfVolumes,noOfChapters,noOfVolumesRead, noOfChaptersRead;
    boolean is_rereading;

    public UserManga(int id, String title, String main_picture, String status, String startDate, String finishData, int score, int noOfVolumes, int noOfChapters, int noOfVolumesRead, int noOfChaptersRead, boolean is_rereading) {
        this.id = id;
        this.title = title;
        this.main_picture = main_picture;
        this.status = status;
        this.startDate = startDate;
        this.finishData = finishData;
        this.score = score;
        this.noOfVolumes = noOfVolumes;
        this.noOfChapters = noOfChapters;
        this.noOfVolumesRead = noOfVolumesRead;
        this.noOfChaptersRead = noOfChaptersRead;
        this.is_rereading = is_rereading;
    }

    public int getT_id() {
        return t_id;
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



    public String getStatus() {
        return status;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getFinishData() {
        return finishData;
    }

    public int getScore() {
        return score;
    }

    public int getNoOfVolumes() {
        return noOfVolumes;
    }

    public int getNoOfChapters() {
        return noOfChapters;
    }

    public int getNoOfVolumesRead() {
        return noOfVolumesRead;
    }

    public int getNoOfChaptersRead() {
        return noOfChaptersRead;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setFinishData(String finishData) {
        this.finishData = finishData;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNoOfVolumes(int noOfVolumes) {
        this.noOfVolumes = noOfVolumes;
    }

    public void setNoOfChapters(int noOfChapters) {
        this.noOfChapters = noOfChapters;
    }

    public void setNoOfVolumesRead(int noOfVolumesRead) {
        this.noOfVolumesRead = noOfVolumesRead;
    }

    public void setNoOfChaptersRead(int noOfChaptersRead) {
        this.noOfChaptersRead = noOfChaptersRead;
    }

    public boolean isIs_rereading() {
        return is_rereading;
    }

    public void setIs_rereading(boolean is_rereading) {
        this.is_rereading = is_rereading;
    }
}

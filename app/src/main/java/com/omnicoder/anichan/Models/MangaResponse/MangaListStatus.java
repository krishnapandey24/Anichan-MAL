package com.omnicoder.anichan.Models.MangaResponse;

public class MangaListStatus {
    String status,start_date,finish_date,updated_at;
    int score,num_volumes_read,num_chapters_read;
    boolean is_rereading;

    public String getStatus() {
        return status;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getFinish_date() {
        return finish_date;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getScore() {
        return score;
    }

    public int getNum_volumes_read() {
        return num_volumes_read;
    }

    public int getNum_chapters_read() {
        return num_chapters_read;
    }

    public boolean isIs_rereading() {
        return is_rereading;
    }
}

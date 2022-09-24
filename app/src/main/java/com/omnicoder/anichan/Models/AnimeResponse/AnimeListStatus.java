package com.omnicoder.anichan.models.animeResponse;

public class AnimeListStatus {
    String status,start_date,finish_date,updated_at;
    int score,num_episodes_watched,priority,num_times_rewatched,rewatch_value;
    boolean is_rewatching;

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

    public int getNum_episodes_watched() {
        return num_episodes_watched;
    }

    public int getPriority() {
        return priority;
    }

    public int getNum_times_rewatched() {
        return num_times_rewatched;
    }

    public int getRewatch_value() {
        return rewatch_value;
    }

    public boolean isIs_rewatching() {
        return is_rewatching;
    }
}

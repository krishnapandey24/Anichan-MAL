package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class AnimeStatus {
    @Json(name = "completed")
    Integer completed;
    @Json(name = "days_watched")
    Double daysWatched;
    @Json(name = "dropped")
    Integer dropped;
    @Json(name = "episodes_watched")
    Integer episodesWatched;
    @Json(name = "mean_score")
    Double  meanScore;
    @Json(name = "on_hold")
    Integer onHold;
    @Json(name = "plan_to_watch")
    Integer planToWatch;
    @Json(name = "rewatched")
    Integer rewatched;
    @Json(name = "total_entries")
    Integer totalEntries;
    @Json(name = "watching")
    Integer watchingInt;

    public Integer getCompleted() {
        return completed;
    }

    public Double getDaysWatched() {
        return daysWatched;
    }

    public Integer getDropped() {
        return dropped;
    }

    public Integer getEpisodesWatched() {
        return episodesWatched;
    }

    public Double getMeanScore() {
        return meanScore;
    }

    public Integer getOnHold() {
        return onHold;
    }

    public Integer getPlanToWatch() {
        return planToWatch;
    }

    public Integer getRewatched() {
        return rewatched;
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public Integer getWatchingInt() {
        return watchingInt;
    }
}

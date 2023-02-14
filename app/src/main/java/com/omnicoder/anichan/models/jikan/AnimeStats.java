package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class AnimeStats {
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
    Integer watching;

    public Integer getCompleted() {
        return completed;
    }

    public Integer getDropped() {
        return dropped;
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

    public Integer getWatching() {
        return watching;
    }

    public float getCompletedFloat() {
        return completed==null ? 0.0f : completed.floatValue();
    }

    public Double getDaysWatched() {
        return daysWatched;
    }

    public float getDroppedFloat() {
        return dropped==null ? 0.0f : dropped.floatValue();
    }

    public Integer getEpisodesWatched() {
        return episodesWatched;
    }

    public Double getMeanScore() {
        return meanScore;
    }

    public float getOnHoldFloat() {
        return onHold==null ? 0.0f : onHold.floatValue();
    }

    public float getPlanToWatchFloat() {
        return planToWatch==null ? 0.0f : planToWatch.floatValue();
    }

    public float getRewatchedFloat() {
        return rewatched==null ? 0.0f : rewatched.floatValue();
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public float getWatchingFloat() {
        return watching==null ? 0.0f : watching.floatValue();
    }
}

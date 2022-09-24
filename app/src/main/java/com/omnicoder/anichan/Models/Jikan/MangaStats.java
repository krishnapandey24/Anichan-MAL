package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class MangaStats {
    @Json(name = "completed")
    Integer completed;
    @Json(name = "days_read ")
    Double daysRead;
    @Json(name = "dropped")
    Integer dropped;
    @Json(name = "chapters_read ")
    Integer chaptersRead ;
    @Json(name = "mean_score")
    Double  meanScore;
    @Json(name = "on_hold")
    Integer onHold;
    @Json(name = "plan_to_read")
    Integer planToWatch;
    @Json(name = "reread ")
    Integer reread ;
    @Json(name = "total_entries")
    Integer totalEntries;
    @Json(name = "reading")
    Integer readingInt;

    public Integer getCompleted() {
        return completed;
    }

    public Double getDaysRead() {
        return daysRead;
    }

    public Integer getDropped() {
        return dropped;
    }

    public Integer getChaptersRead() {
        return chaptersRead;
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

    public Integer getReread() {
        return reread;
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public Integer getReadingInt() {
        return readingInt;
    }
}

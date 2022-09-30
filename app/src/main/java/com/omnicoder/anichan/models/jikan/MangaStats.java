package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class MangaStats {
    @Json(name = "completed")
    Integer completed;
    @Json(name = "days_read")
    Double daysRead;
    @Json(name = "dropped")
    Integer dropped;
    @Json(name = "chapters_read")
    Integer chaptersRead ;
    @Json(name = "mean_score")
    Double  meanScore;
    @Json(name = "on_hold")
    Integer onHold;
    @Json(name = "plan_to_read")
    Integer planToRead;
    @Json(name = "reread")
    Integer reread ;
    @Json(name = "total_entries")
    Integer totalEntries;
    @Json(name = "reading")
    Integer reading;

    public Integer getCompleted() {
        return completed;
    }

    public Integer getDropped() {
        return dropped;
    }

    public Integer getOnHold() {
        return onHold;
    }

    public Integer getPlanToRead() {
        return planToRead;
    }

    public Integer getReading() {
        return reading;
    }

    public float getCompletedFloat() {
        return completed==null ? 0.0f : completed.floatValue();
    }

    public Double getDaysRead() {
        return daysRead;
    }

    public float getDroppedFloat() {
        return dropped==null ? 0.0f : dropped.floatValue();
    }

    public Integer getChaptersRead() {
        return chaptersRead==null ? 0 : chaptersRead;
    }

    public Double getMeanScore() {
        return meanScore;
    }

    public float getOnHoldFloat() {
        return onHold==null ? 0.0f : onHold.floatValue();
    }

    public float getPlanToReadFloat() {
        return planToRead ==null ? 0.0f : planToRead.floatValue();
    }

    public Integer getReread() {
        return reread;
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public float getReadingFloat() {
        return reading==null ? 0.0f : reading.floatValue();
    }
}

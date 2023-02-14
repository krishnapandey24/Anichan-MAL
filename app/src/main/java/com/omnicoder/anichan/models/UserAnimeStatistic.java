package com.omnicoder.anichan.models;

import com.squareup.moshi.Json;

public class UserAnimeStatistic {
    @Json(name="num_items_watching")
    Integer numItemsWatching;
    @Json(name="num_items_completed")
    Integer numItemsCompleted;
    @Json(name="num_items_on_hold")
    Integer numItemsOnHold;
    @Json(name="num_items_dropped")
    Integer numItemsDropped;
    @Json(name="num_items_plan_to_watch")
    Integer numItemsPlanToWatch;
    @Json(name="num_items")
    Integer numItems;
    @Json(name="num_days_watched")
    Float numDaysWatched;
    @Json(name="num_days_watching")
    Float numDaysWatching;
    @Json(name="num_days_completed")
    Float numDaysCompleted;
    @Json(name="num_days_on_hold")
    Float numDaysOnHold;
    @Json(name="num_days_dropped")
    Float numDaysDropped;
    @Json(name="num_days")
    Float numDays;
    @Json(name="num_episodes")
    Integer numEpisodes;
    @Json(name="num_times_rewatched")
    Integer numTimesRewatched;
    @Json(name="mean_score")
    Float meanScore;

    public Integer getNumItemsWatching() {
        return numItemsWatching;
    }

    public Integer getNumItemsCompleted() {
        return numItemsCompleted;
    }

    public Integer getNumItemsOnHold() {
        return numItemsOnHold;
    }

    public Integer getNumItemsDropped() {
        return numItemsDropped;
    }

    public Integer getNumItemsPlanToWatch() {
        return numItemsPlanToWatch;
    }

    public Integer getNumItems() {
        return numItems;
    }

    public Float getNumDaysWatched() {
        return numDaysWatched;
    }

    public Float getNumDaysWatching() {
        return numDaysWatching;
    }

    public Float getNumDaysCompleted() {
        return numDaysCompleted;
    }

    public Float getNumDaysOnHold() {
        return numDaysOnHold;
    }

    public Float getNumDaysDropped() {
        return numDaysDropped;
    }

    public Float getNumDays() {
        return numDays;
    }

    public Integer getNumEpisodes() {
        return numEpisodes;
    }

    public Integer getNumTimesRewatched() {
        return numTimesRewatched;
    }

    public Float getMeanScore() {
        return meanScore;
    }
}

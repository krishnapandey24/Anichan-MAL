package com.omnicoder.anichan.database;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ANIME_LIST")
public class AnimeList {
    public static final String STATUS="status";
    public static final String TITLE="title";
    public static final String POSTER_PATH ="posterPath";
    public static final String FORMAT ="format";
    public static final String ANIME_ID="animeID";
    public static final String TOTAL_EPISODES="totalEpisode";
    public static final String WATCHED_EPISODES="watchedEpisodes";
    public static final String GIVEN_SCORE="givenScore";
    public static final String SEASON_NO="seasonNo";

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer animeID,givenScore,totalEpisode,seasonNo,watchedEpisodes;
    private String status,title,posterPath,startedDate,completedDate,format,mediaType;

    public AnimeList(Integer id, Integer animeID, Integer givenScore, Integer totalEpisode, Integer seasonNo, Integer watchedEpisodes, String status, String title, String posterPath, String startedDate, String completedDate, String format, String mediaType) {
        this.id = id;
        this.animeID = animeID;
        this.givenScore = givenScore;
        this.totalEpisode = totalEpisode;
        this.seasonNo = seasonNo;
        this.watchedEpisodes = watchedEpisodes;
        this.status = status;
        this.title = title;
        this.posterPath = posterPath;
        this.startedDate = startedDate;
        this.completedDate = completedDate;
        this.format = format;
        this.mediaType = mediaType;
    }

    @Ignore
    public AnimeList(Integer animeID, Integer givenScore, Integer totalEpisode, Integer seasonNo, Integer watchEpisodes, String status, String title, String posterPath, String startedDate, String completedDate, String format, String mediaType) {
        this.animeID = animeID;
        this.givenScore = givenScore;
        this.totalEpisode = totalEpisode;
        this.seasonNo = seasonNo;
        this.watchedEpisodes = watchEpisodes;
        this.status = status;
        this.title = title;
        this.posterPath = posterPath;
        this.startedDate = startedDate;
        this.completedDate = completedDate;
        this.format = format;
        this.mediaType = mediaType;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnimeID() {
        return animeID;
    }

    public void setAnimeID(Integer animeID) {
        this.animeID = animeID;
    }

    public Integer getGivenScore() {
        return givenScore;
    }

    public void setGivenScore(Integer givenScore) {
        this.givenScore = givenScore;
    }

    public Integer getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(Integer totalEpisode) {
        this.totalEpisode = totalEpisode;
    }

    public Integer getSeasonNo() {
        return seasonNo;
    }

    public void setSeasonNo(Integer seasonNo) {
        this.seasonNo = seasonNo;
    }

    public Integer getWatchedEpisodes() {
        return watchedEpisodes;
    }

    public void setWatchedEpisodes(Integer watchedEpisodes) {
        this.watchedEpisodes = watchedEpisodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getFormat() {
        if(format==null){
            return "--";
        }
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}

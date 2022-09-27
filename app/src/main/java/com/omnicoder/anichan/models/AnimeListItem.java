package com.omnicoder.anichan.models;


public class AnimeListItem {
    private Integer animeID,givenScore,totalEpisode,seasonNo,watchedEpisodes;
    private String status,title,posterPath,startedDate,CompletedDate,format,mediaType;

    public AnimeListItem(Integer animeID, Integer givenScore, Integer totalEpisode, Integer seasonNo, Integer watchEpisodes, String status, String title, String posterPath, String startedDate, String completedDate, String format, String mediaType) {
        this.animeID = animeID;
        this.givenScore = givenScore;
        this.totalEpisode = totalEpisode;
        this.seasonNo = seasonNo;
        this.watchedEpisodes = watchEpisodes;
        this.status = status;
        this.title = title;
        this.posterPath = posterPath;
        this.startedDate = startedDate;
        this.CompletedDate = completedDate;
        this.format = format;
        this.mediaType = mediaType;
    }


}

package com.omnicoder.anichan.Models.MangaListResponse;


import java.util.List;

public class UpdateMangaResponse{
    String status;
    Integer score;
    Integer volumesRead,chaptersRead;
    Boolean is_rereading;
    String updated_at;
    Integer priority;
    Integer num_times_reread;
    Integer reread_value;
    List<String> tags;
    String comments;

    public UpdateMangaResponse(String status, Integer score, Integer volumesRead, Integer chaptersRead, Boolean is_rereading, String updated_at, Integer priority, Integer num_times_reread, Integer reread_value, List<String> tags, String comments) {
        this.status = status;
        this.score = score;
        this.volumesRead = volumesRead;
        this.chaptersRead = chaptersRead;
        this.is_rereading = is_rereading;
        this.updated_at = updated_at;
        this.priority = priority;
        this.num_times_reread = num_times_reread;
        this.reread_value = reread_value;
        this.tags = tags;
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }



    public Boolean getIs_rereading() {
        return is_rereading;
    }

    public void setIs_rereading(Boolean is_rereading) {
        this.is_rereading = is_rereading;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getVolumesRead() {
        return volumesRead;
    }

    public Integer getChaptersRead() {
        return chaptersRead;
    }

    public Integer getNum_times_reread() {
        return num_times_reread;
    }

    public Integer getReread_value() {
        return reread_value;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

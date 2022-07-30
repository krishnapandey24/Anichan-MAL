package com.omnicoder.anichan.Models;


import java.util.List;

public class UpdateAnimeResponse{
    String status;
    Integer score;
    Integer num_episodes_watched;
    Boolean is_rewatching;
    String updated_at;
    Integer priority;
    Integer num_times_rewatched;
    Integer rewatch_value;
    List<String> tags;
    String comments;

    public UpdateAnimeResponse(String status, Integer score, Integer num_episodes_watched, Boolean is_rewatching, String updated_at, Integer priority, Integer num_times_rewatched, Integer rewatch_value, List<String> tags, String comments) {
        this.status = status;
        this.score = score;
        this.num_episodes_watched = num_episodes_watched;
        this.is_rewatching = is_rewatching;
        this.updated_at = updated_at;
        this.priority = priority;
        this.num_times_rewatched = num_times_rewatched;
        this.rewatch_value = rewatch_value;
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

    public Integer getNum_episodes_watched() {
        return num_episodes_watched;
    }

    public void setNum_episodes_watched(Integer num_episodes_watched) {
        this.num_episodes_watched = num_episodes_watched;
    }

    public Boolean getIs_rewatching() {
        return is_rewatching;
    }

    public void setIs_rewatching(Boolean is_rewatching) {
        this.is_rewatching = is_rewatching;
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

    public Integer getNum_times_rewatched() {
        return num_times_rewatched;
    }

    public void setNum_times_rewatched(Integer num_times_rewatched) {
        this.num_times_rewatched = num_times_rewatched;
    }

    public Integer getRewatch_value() {
        return rewatch_value;
    }

    public void setRewatch_value(Integer rewatch_value) {
        this.rewatch_value = rewatch_value;
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

package com.omnicoder.anichan.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "USER_INFO")
public class UserInfo {
    @PrimaryKey
    Integer id;
    String name,location,joined_at,picture;


    public UserInfo(Integer id, String name, String location, String joined_at, String picture) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.joined_at = joined_at;
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getJoined_at() {
        return joined_at;
    }

    public String getPicture() {
        return picture;
    }


}

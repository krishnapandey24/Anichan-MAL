package com.omnicoder.anichan.models.jikan;


import java.util.List;

public class UserFriendResponse {
    Pagination pagination;
    List<FriendData> data;

    public Pagination getPagination() {
        return pagination;
    }

    public List<FriendData> getData() {
        return data;
    }
}

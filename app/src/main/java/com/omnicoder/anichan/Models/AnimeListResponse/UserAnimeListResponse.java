package com.omnicoder.anichan.Models.AnimeListResponse;


import com.omnicoder.anichan.Models.Responses.Paging;

import java.util.List;

public class UserAnimeListResponse {
    List<UserListAnime> data;
    Paging paging;


    public List<UserListAnime> getData() {
        return data;
    }

    public Paging getPaging() {
        return paging;
    }

    public boolean endOfList(){
        return paging.getNext()==null;
    }
}

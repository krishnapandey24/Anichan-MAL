package com.omnicoder.anichan.models.mangaListResponse;

import com.omnicoder.anichan.models.responses.Paging;

import java.util.List;

public class UserMangaListResponse {
    List<UserListManga> data;
    Paging paging;


    public List<UserListManga> getData() {
        return data;
    }

    public Paging getPaging() {
        return paging;
    }

    public boolean endOfList(){
        return paging.getNext()==null;
    }
}

package com.omnicoder.anichan.models.mangaListResponse;

import com.omnicoder.anichan.models.mangaResponse.Manga;
import com.omnicoder.anichan.models.mangaResponse.MangaListStatus;

public class UserListManga {
    Manga node;
    MangaListStatus list_status;

    public Manga getNode() {
        return node;
    }

    public MangaListStatus getList_status() {
        return list_status;
    }
}

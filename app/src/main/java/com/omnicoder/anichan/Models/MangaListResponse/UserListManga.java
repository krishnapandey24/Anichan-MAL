package com.omnicoder.anichan.Models.MangaListResponse;

import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeListStatus;
import com.omnicoder.anichan.Models.MangaResponse.Manga;
import com.omnicoder.anichan.Models.MangaResponse.MangaListStatus;

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

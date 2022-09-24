package com.omnicoder.anichan.models.animeListResponse;

import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.models.animeResponse.AnimeListStatus;

public class UserListAnime {
    Anime node;
    AnimeListStatus list_status;

    public Anime getNode() {
        return node;
    }

    public AnimeListStatus getList_status() {
        return list_status;
    }
}

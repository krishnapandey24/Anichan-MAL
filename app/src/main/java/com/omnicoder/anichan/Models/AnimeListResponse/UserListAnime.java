package com.omnicoder.anichan.Models.AnimeListResponse;

import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeListStatus;
import com.omnicoder.anichan.Models.Responses.Node;

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

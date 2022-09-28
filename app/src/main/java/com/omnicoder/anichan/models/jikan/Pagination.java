package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class Pagination {
    @Json(name = "last_visible_page")
    Integer lastVisiblePage;
    @Json(name = "has_next_page")
    Boolean hasNextPage;

    public Integer getLastVisiblePage() {
        return lastVisiblePage;
    }

    public Boolean getHasNextPage() {
        return hasNextPage;
    }
}

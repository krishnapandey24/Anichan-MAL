package com.omnicoder.anichan.models;

import java.util.List;

public class APIResponse {
    private List<ExploreView> trending;

    public APIResponse(List<ExploreView> trending){
        this.trending=trending;
    }

    public List<ExploreView> getResults() {
        return trending;
    }

    public void setResults(List<ExploreView> trending) {
        this.trending = trending;
    }
}

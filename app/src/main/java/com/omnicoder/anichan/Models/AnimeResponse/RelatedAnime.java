package com.omnicoder.anichan.Models.AnimeResponse;

import com.omnicoder.anichan.Models.Responses.Node;

public class RelatedAnime {
    Node node;
    String relation_type;
    String relation_type_formatted;

    public Node getNode() {
        return node;
    }

    public String getRelation_type() {
        return relation_type;
    }

    public String getRelation_type_formatted() {
        return relation_type_formatted;
    }
}

package com.omnicoder.anichan.models.animeResponse;

import com.omnicoder.anichan.models.responses.Node;

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

package com.omnicoder.anichan.Models.MangaResponse;

import java.util.List;

public class Author {
    Node node;
    String role;

    public Node getNode() {
        return node;
    }

    public String getRole() {
        return role;
    }
}

class Node{
    int id;
    String first_name,last_name;

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }
}

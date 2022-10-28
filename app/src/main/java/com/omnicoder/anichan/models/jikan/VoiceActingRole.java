package com.omnicoder.anichan.models.jikan;

public class VoiceActingRole {
    String role;
    JikanSubEntity anime;
    JikanSubEntity character;

    public String getRole() {
        return role;
    }

    public JikanSubEntity getAnime() {
        return anime;
    }

    public JikanSubEntity getCharacter() {
        return character;
    }
}

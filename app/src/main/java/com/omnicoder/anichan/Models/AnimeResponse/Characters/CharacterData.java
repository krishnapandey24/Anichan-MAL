package com.omnicoder.anichan.Models.AnimeResponse.Characters;

import java.util.List;

public class CharacterData {
    Character character;
    List<VoiceActor> voice_actors;
    String role;

    public Character getCharacter() {
        return character;
    }

    public List<VoiceActor> getVoice_actors() {
        return voice_actors;
    }

    public String getRole() {
        return role;
    }
}

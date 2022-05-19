package com.omnicoder.anichan.Models.AnimeResponse.Staff;

import com.omnicoder.anichan.Models.AnimeResponse.Characters.Character;

import java.util.List;

public class StaffData {
    Character person;
    List<String> positions;

    public Character getPerson() {
        return person;
    }

    public List<String> getPositions() {
        return positions;
    }
}

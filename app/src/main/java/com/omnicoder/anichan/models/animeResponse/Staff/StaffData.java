package com.omnicoder.anichan.models.animeResponse.Staff;

import com.omnicoder.anichan.models.animeResponse.Characters.Character;

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

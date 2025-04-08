package ctn.project_moon.api;

import ctn.project_moon.datagen.PmTags;

public enum PmColour {
    PHYSICS("#ff0000", "physics"),
    SPIRIT("#ffffff", "spirit"),
    EROSION("#8a2be2", "erosion"),
    THE_SOUL("#00ffff", "the_soul"),
    ZAYIN("#00ff00","zayin"),
    TETH("#1e90ff","teth"),
    HE("#ffff00","he"),
    WAW("#8a2be2","waw"),
    ALEPH("#ff0000", "aleph"),
    ;
    private final String colour;
    private final String text;

    PmColour(String colour, String text) {
        this.colour = colour;
        this.text = text;
    }

    public String getColour() {
        return colour;
    }

    public String getText() {
        return text;
    }
}

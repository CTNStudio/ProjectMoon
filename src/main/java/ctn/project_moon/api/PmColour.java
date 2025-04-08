package ctn.project_moon.api;

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
    private final String colourText;

    PmColour(String colour, String colourText) {
        this.colour = colour;
        this.colourText = colourText;
    }

    public String getColour() {
        return colour;
    }

    public String getColourText() {
        return colourText;
    }
}

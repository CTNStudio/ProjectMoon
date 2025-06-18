package ctn.project_moon.tool;

public enum PmColourTool {
	PHYSICS("#ff0000", "physics"),
	SPIRIT("#ffffff", "spirit"),
	EROSION("#8a2be2", "erosion"),
	THE_SOUL("#00ffff", "the_soul"),
	ZAYIN("#00ff00", "ZAYIN"),
	TETH("#1e90ff", "TETH"),
	HE("#ffff00", "HE"),
	WAW("#8a2be2", "WAW"),
	ALEPH("#ff0000", "ALEPH");
	
	private final String colour;
	private final String colourText;
	
	PmColourTool(String colour, String colourText) {
		this.colour     = colour;
		this.colourText = colourText;
	}
	
	public String getColour() {
		return colour;
	}
	
	public int getColourRGB() {
		return PmTool.colorConversion(getColour());
	}
	
	public String getColourText() {
		return colourText;
	}
}

package JejuDorang.JejuDorang.lodging.enums;

import lombok.Getter;

@Getter
public enum LodgingDirection {

	EAST("east", "동"),
	WEST("west", "서"),
	SOUTH("south", "남"),
	NORTH("north", "북"),
	UNKNOWN("unknown", "미지정");

	private final String englishName;
	private final String koreanName;

	LodgingDirection(String englishName, String koreanName) {
		this.englishName = englishName;
		this.koreanName = koreanName;
	}
}

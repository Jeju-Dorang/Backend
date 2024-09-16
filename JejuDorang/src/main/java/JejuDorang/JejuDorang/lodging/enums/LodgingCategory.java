package JejuDorang.JejuDorang.lodging.enums;

import lombok.Getter;

@Getter
public enum LodgingCategory {
	HOTEL("호텔"),
	GUEST_HOUSE("게스트하우스"),
	PENSION("펜션"),
	BB("민박");

	private final String koreanName;

	LodgingCategory(String koreanName) {
		this.koreanName = koreanName;
	}
}

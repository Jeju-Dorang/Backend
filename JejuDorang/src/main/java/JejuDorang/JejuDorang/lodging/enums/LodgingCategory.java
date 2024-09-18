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

	public static LodgingCategory valueOfCategory(String category) {
		if (category.contains("호텔") || category.contains("리조트") || category.contains("호스텔") || category.contains("모텔") || category.contains("콘도")) {
			return LodgingCategory.HOTEL;
		}
		else if (category.contains("게스트하우스")) {
			return LodgingCategory.GUEST_HOUSE;
		}
		else if (category.contains("펜션")) {
			return LodgingCategory.PENSION;
		}
		else if (category.contains("민박")) {
			return LodgingCategory.BB;
		}
		else
			return null;
	}
}

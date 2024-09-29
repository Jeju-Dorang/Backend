package JejuDorang.JejuDorang.lodging.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LodgingCategory {
	HOTEL("hotel","호텔"),
	GUEST_HOUSE("guestHouse", "게스트하우스"),
	PENSION("pension", "펜션"),
	BB("lodge", "민박");

	private final String englishName;
	private final String koreanName;

	public static LodgingCategory valueOfCategory(String category) {
		if (category.contains("호텔") || category.contains("리조트") || category.contains("호스텔") || category.contains("모텔") || category.contains("콘도")) {
			return LodgingCategory.HOTEL;
		}
		else if (category.contains("게스트하우스")) {
			return LodgingCategory.GUEST_HOUSE;
		}
		else if (category.contains("펜션") || category.contains("빌라")) {
			return LodgingCategory.PENSION;
		}
		else {
			return LodgingCategory.BB;
		}
	}
}

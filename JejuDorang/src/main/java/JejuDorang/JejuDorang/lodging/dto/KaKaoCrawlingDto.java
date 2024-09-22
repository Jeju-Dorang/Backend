package JejuDorang.JejuDorang.lodging.dto;

import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class KaKaoCrawlingDto {
	private double rating;
	private LodgingCategory category;

	public KaKaoCrawlingDto(double rating, String category) {
		this.rating = rating;
		this.category = LodgingCategory.valueOfCategory(category);
	}
}

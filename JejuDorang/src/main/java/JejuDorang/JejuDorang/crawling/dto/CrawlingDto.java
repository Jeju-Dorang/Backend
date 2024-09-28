package JejuDorang.JejuDorang.crawling.dto;

import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CrawlingDto {
	private String name;
	private String price;
	private double rating;
	private LodgingCategory category;
	private String description;

	public CrawlingDto(String name, String price, double rating, String category, String description) {
		this.name = name;
		this.price = price;
		this.rating = rating;
		this.category = LodgingCategory.valueOfCategory(category);
		this.description = description;
	}
}

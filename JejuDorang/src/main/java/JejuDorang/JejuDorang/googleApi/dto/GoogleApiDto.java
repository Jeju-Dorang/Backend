package JejuDorang.JejuDorang.googleApi.dto;

import java.util.List;

import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class GoogleApiDto {
	private String name;
	private String price;
	private String image;
	private String phoneNumber;
	private double rating;
	private String address;
	private LodgingCategory category;
	private String description;
	private List<ReviewDto> reviews;

	public GoogleApiDto(String name, String price, String image, String phoneNumber, double rating,
		String address, LodgingCategory category, String description) {
		this.name = name;
		this.price = price;
		this.image = image;
		this.phoneNumber = phoneNumber;
		this.rating = rating;
		this.address = address;
		this.category = category;
		this.description = description;
	}

	public void setReviews(List<ReviewDto> reviews) {
		this.reviews = reviews;
	}
}

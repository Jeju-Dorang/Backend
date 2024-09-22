package JejuDorang.JejuDorang.lodging.dto;

import lombok.Getter;

@Getter
public class LodgingRecommendResponseDto {

	private final String name;
	private final String image;
	private final String address;
	private final String distance;
	private final double rating;
	private final double latitude;
	private final double longitude;

	public LodgingRecommendResponseDto(String name, String image, String address, String distance, double rating,
		double latitude, double longitude) {
		this.name = name;
		this.image = image;
		this.address = address;
		this.distance = distance;
		this.rating = rating;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}

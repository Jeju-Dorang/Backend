package JejuDorang.JejuDorang.lodging.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import JejuDorang.JejuDorang.crawling.dto.ReviewDto;
import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import JejuDorang.JejuDorang.review.data.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Lodging {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String comment;

	@Column(length = 1000)
	private String image;

	private String address;

	private String phoneNumber;

	private double rating;

	private double latitude; // 위도

	private double longitude; // 경도

	private Long price;

	private LodgingDirection direction;

	private LodgingCategory category;

	@OneToMany(mappedBy = "lodging", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<Review> reviews = new ArrayList<>();

	public void updateLodgingInfo(String name, double latitude, double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void setReviews(List<ReviewDto> reviews) {
		for (ReviewDto reviewDto : reviews) {
			Review review = new Review(reviewDto.getRating(), reviewDto.getName(), "",
				reviewDto.getProfileUrl(), reviewDto.getContent(),
				Timestamp.from(reviewDto.getTime()), reviewDto.getRelativeTimeDescription());
			review.setLodging(this);
			this.reviews.add(review);
		}
	}
}

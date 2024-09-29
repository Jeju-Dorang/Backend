package JejuDorang.JejuDorang.lodging.dto;

import java.util.List;

import JejuDorang.JejuDorang.crawling.dto.ReviewDto;
import JejuDorang.JejuDorang.lodging.data.Lodging;
import lombok.Getter;

@Getter
public class LodgingDetailResponseDto {

	private final Long lodgingId;
	private final String name;
	private final String comment;
	private final String image;
	private final String address;
	private final String contactNumber;
	private final String category;
	private final int fiveStar;
	private final int fourStar;
	private final int threeStar;
	private final int twoStar;
	private final int oneStar;
	private final int zeroStar;
	private final double rating;
	private final int reviewCount;

	private List<ReviewDto> reviews;

	public LodgingDetailResponseDto(Lodging lodging, int[] stars, List<ReviewDto> reviews) {
		this.lodgingId = lodging.getId();
		this.name = lodging.getName();
		this.comment = lodging.getComment();
		this.image = lodging.getImage();
		this.address = lodging.getAddress();
		this.contactNumber = lodging.getPhoneNumber();
		this.category = lodging.getCategory().name();
		this.fiveStar = stars[5];
		this.fourStar = stars[4];
		this.threeStar = stars[3];
		this.twoStar = stars[2];
		this.oneStar = stars[1];
		this.zeroStar = stars[0];
		this.rating = lodging.getRating();
		this.reviewCount = lodging.getReviews().size();
		this.reviews = reviews;
	}
}

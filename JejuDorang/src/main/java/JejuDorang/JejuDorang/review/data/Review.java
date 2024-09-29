package JejuDorang.JejuDorang.review.data;

import java.sql.Timestamp;

import JejuDorang.JejuDorang.lodging.data.Lodging;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double rating;

	private String reviewerName;

	private String reviewerInfo;

	private String reviewerProfile;

	@Column(length = 15000)
	private String reviewContent;

	private Timestamp reviewDate;

	private String reviewDateStr;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lodging_id")
	private Lodging lodging;

	public Review(Double rating, String reviewerName, String reviewerInfo, String reviewerProfile, String reviewContent,
		Timestamp reviewDate, String reviewDateStr) {
		this.rating = rating;
		this.reviewerName = reviewerName;
		this.reviewerInfo = reviewerInfo;
		this.reviewerProfile = reviewerProfile;
		this.reviewContent = reviewContent;
		this.reviewDate = reviewDate;
		this.reviewDateStr = reviewDateStr;
	}

	public void setLodging(Lodging lodging) {
		this.lodging = lodging;
	}
}



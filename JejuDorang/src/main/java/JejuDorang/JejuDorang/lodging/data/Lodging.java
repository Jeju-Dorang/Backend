package JejuDorang.JejuDorang.lodging.data;

import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	private String image;

	private String address;

	private double rating;

	private double latitude; // 위도

	private double longitude; // 경도

	private Long price;

	private LodgingDirection direction;

	private LodgingCategory category;

//	public void updateLodgingInfo(String name, double latitude, double longitude) {
//		this.name = name;
//		this.latitude = latitude;
//		this.longitude = longitude;
//	}
}

package JejuDorang.JejuDorang.lodging.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LodgingCoordinateDto {
	private double lat;
	private double lng;  // 경도
}

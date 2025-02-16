package JejuDorang.JejuDorang.tourspot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TourSpotResponseDto {

    private String title;
    private String address;
    private String image;
    private Double mapX;
    private Double mapY;
}

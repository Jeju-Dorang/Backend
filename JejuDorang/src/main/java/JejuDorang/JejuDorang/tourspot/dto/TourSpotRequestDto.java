package JejuDorang.JejuDorang.tourspot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TourSpotRequestDto {

    @NotNull(message = "mapx를 입력해주세요.")
    private String mapX;

    @NotNull(message = "mapy를 입력해주세요.")
    private String mapY;
}

package JejuDorang.JejuDorang.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLodgingDto {

    @NotNull(message = "longitude를 입력해주세요.")
    private Double longitude;
    @NotNull(message = "latitude를 입력해주세요.")
    private Double latitude;
    @NotNull(message = "lodgingName를 입력해주세요.")
    private String lodgingName;
}

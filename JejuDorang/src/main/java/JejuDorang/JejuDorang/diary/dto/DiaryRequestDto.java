package JejuDorang.JejuDorang.diary.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import JejuDorang.JejuDorang.diary.enums.SecretType;
import JejuDorang.JejuDorang.tag.dto.TagDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRequestDto {

    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

    private String imageUrl;

    @JsonProperty("secret")
    private SecretType secret;

    @NotNull(message = "업적을 선택해주세요.")
    private Long achievementId;

    private List<TagDto> tagList;
}

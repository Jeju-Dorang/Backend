package JejuDorang.JejuDorang.diary.dto;

import JejuDorang.JejuDorang.diary.enums.SecretType;
import JejuDorang.JejuDorang.tag.dto.TagDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRequest {

    private String title;

    private String content;

    private String imageUrl;

    @JsonProperty("secret")
    private SecretType secret;

    private List<TagDto> tagList;
}

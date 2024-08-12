package JejuDorang.JejuDorang.diary.dto;

import JejuDorang.JejuDorang.tag.dto.TagDto;
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
    private String secret;
    private List<TagDto> tagList;
}

package JejuDorang.JejuDorang.diary.dto;

import JejuDorang.JejuDorang.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDetailResponse {

    private String name;
    private LocalDate date;
    private String image;
    private String content;
    private boolean alreadyLike;
    private List<TagDto> tagList;
}

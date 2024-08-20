package JejuDorang.JejuDorang.tag.dto;

import java.util.List;

import JejuDorang.JejuDorang.tag.data.DiaryTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    private String tagName;

    public static List<TagDto> listOf(List<DiaryTag> diaryTagList) {
        return diaryTagList.stream()
                .map(diaryTag -> new TagDto(diaryTag.getTag().getName()))
                .toList();
    }
}

package JejuDorang.JejuDorang.diary.dto;

import JejuDorang.JejuDorang.diary.enums.SecretType;
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
    private SecretType type;
    private List<Tag> tagList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Tag {
        private String tagName;
    }
}

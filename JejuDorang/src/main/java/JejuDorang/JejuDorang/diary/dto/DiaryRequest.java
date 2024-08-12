package JejuDorang.JejuDorang.diary.dto;

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
    private List<Tag> tagList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag {
        private String tagName;
    }
}

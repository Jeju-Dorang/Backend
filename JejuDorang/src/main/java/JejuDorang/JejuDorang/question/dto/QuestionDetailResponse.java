package JejuDorang.JejuDorang.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDetailResponse {

    private String title;
    private String content;
    private String author;
    private List<Comment> comments;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Comment {
        private String commenter;
        private String commenterImage;
        private String commentContent;
        private int likeCount;
        private boolean alreadyLike;
    }
}

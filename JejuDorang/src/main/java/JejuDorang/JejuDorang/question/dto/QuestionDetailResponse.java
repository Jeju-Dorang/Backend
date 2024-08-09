package JejuDorang.JejuDorang.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionDetailResponse {

    private final String title;
    private final String content;
    private final String author;
    private final List<Comment> comments;

    @Getter
    @AllArgsConstructor
    public static class Comment {
        private final String commenter;
        private final String commenterImage;
        private final String commentContent;
//        private final Long likeCount;
//        private final Boolean alreadyLike;
    }
}

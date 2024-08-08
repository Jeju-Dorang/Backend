package JejuDorang.JejuDorang.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponse {

    private final Long postId;
    private final String title;
    private final String content;
}

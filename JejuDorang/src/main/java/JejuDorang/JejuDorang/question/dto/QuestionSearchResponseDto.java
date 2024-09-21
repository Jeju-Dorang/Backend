package JejuDorang.JejuDorang.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSearchResponseDto {

    private Long postId;
    private String title;
    private String content;
}

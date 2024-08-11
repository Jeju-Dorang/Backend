package JejuDorang.JejuDorang.diary.dto;

import JejuDorang.JejuDorang.diary.enums.SecretType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRequest {

    private String title;
    private String content;
    private String imageUrl;
    private String secret;
}

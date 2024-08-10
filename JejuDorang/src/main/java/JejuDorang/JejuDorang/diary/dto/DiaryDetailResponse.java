package JejuDorang.JejuDorang.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDetailResponse {

    private String name;
    private LocalDateTime date;
    private String image;
    private String content;
//    private boolean alreadyLike;
}

package JejuDorang.JejuDorang.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryPublicResponseDto {
    private Long diaryId;
    private String name;
    private String image;
    private boolean viewStatus;
}

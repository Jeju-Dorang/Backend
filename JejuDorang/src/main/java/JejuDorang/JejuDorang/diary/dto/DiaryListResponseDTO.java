package JejuDorang.JejuDorang.diary.dto;

import JejuDorang.JejuDorang.diary.enums.SecretType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryListResponseDTO {
	private final Long diaryId;
	private final String title;
	private final String content;
	private final SecretType secret;
}

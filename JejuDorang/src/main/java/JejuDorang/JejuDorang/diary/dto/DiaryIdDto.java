package JejuDorang.JejuDorang.diary.dto;

import lombok.Getter;

@Getter
public class DiaryIdDto {

	private final Long diaryId;

	public DiaryIdDto(Long diaryId) {
		this.diaryId = diaryId;
	}
}

package JejuDorang.JejuDorang.diary.dto;

import java.time.LocalDate;
import java.util.List;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.enums.SecretType;
import JejuDorang.JejuDorang.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyDiaryDetailResponseDto {

	private Long diaryId;
	private String content;
	private String image;
	private LocalDate date;
	private SecretType secret;
	private List<TagDto> tagList;

	public MyDiaryDetailResponseDto(Diary diary) {
		this.diaryId = diary.getId();
		this.content = diary.getContent();
		this.image = diary.getImage();
		this.date = diary.getDate();
		this.secret = diary.getSecret();
		this.tagList = TagDto.listOf(diary.getDiaryTagList());
	}
}

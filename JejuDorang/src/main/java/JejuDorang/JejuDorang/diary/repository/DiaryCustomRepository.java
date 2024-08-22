package JejuDorang.JejuDorang.diary.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.dto.DiaryIdDto;
import JejuDorang.JejuDorang.diary.dto.DiaryListResponseDTO;
import JejuDorang.JejuDorang.diary.dto.MyDiaryDetailResponseDto;
import JejuDorang.JejuDorang.diary.enums.SecretType;

@Repository
public interface DiaryCustomRepository {
	List<DiaryIdDto> findTop3ByMemberId(Long memberId);
	List<DiaryListResponseDTO> findAllByMemberId(Long memberId);
	MyDiaryDetailResponseDto findDiaryDetailByDiaryIdAndMemberId(Long diaryId, long id);
	int updateDiarySecret(Long diaryId, long id, SecretType secret);
}

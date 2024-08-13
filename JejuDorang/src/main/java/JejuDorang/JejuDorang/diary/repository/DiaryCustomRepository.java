package JejuDorang.JejuDorang.diary.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.dto.DiaryIdDto;

@Repository
public interface DiaryCustomRepository {
	List<DiaryIdDto> findTop3ByMemberId(Long memberId);
}

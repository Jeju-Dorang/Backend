package JejuDorang.JejuDorang.diary.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.data.QDiary;
import JejuDorang.JejuDorang.diary.dto.DiaryIdDto;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class DiaryCustomRepositoryImpl implements DiaryCustomRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<DiaryIdDto> findTop3ByMemberId(Long memberId) {
		QDiary diary = QDiary.diary;

		return jpaQueryFactory
			.select(Projections.constructor(DiaryIdDto.class, diary.id))
			.from(diary)
			.where(diary.member.id.eq(memberId))
			.orderBy(diary.id.desc())
			.limit(3)
			.fetch();
	}
}

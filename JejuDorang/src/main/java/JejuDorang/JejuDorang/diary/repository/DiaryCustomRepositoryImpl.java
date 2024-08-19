package JejuDorang.JejuDorang.diary.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import JejuDorang.JejuDorang.diary.data.QDiary;
import JejuDorang.JejuDorang.diary.dto.DiaryIdDto;
import JejuDorang.JejuDorang.diary.dto.DiaryListResponseDTO;
import JejuDorang.JejuDorang.like.data.QLikeDiary;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class DiaryCustomRepositoryImpl implements DiaryCustomRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<DiaryIdDto> findTop3ByMemberId(Long memberId) {
		QDiary diary = QDiary.diary;
		QLikeDiary likeDiary = QLikeDiary.likeDiary;

		NumberExpression<Long> likeCount = likeDiary.count();

		return jpaQueryFactory
			.select(diary.id)
			.from(diary)
			.leftJoin(likeDiary).on(likeDiary.diary.id.eq(diary.id))
			.where(diary.member.id.eq(memberId))
			.groupBy(diary.id)
			.orderBy(likeCount.desc())
			.limit(3)
			.fetch()
			.stream()
			.map(DiaryIdDto::new)
			.toList();
	}

	@Override
	public List<DiaryListResponseDTO> findAllByMemberId(Long memberId) {
		QDiary diary = QDiary.diary;

		return jpaQueryFactory
			.select(
				Projections.constructor(
					DiaryListResponseDTO.class,
					diary.id,
					diary.title,
					diary.content,
					diary.secret
				)
			)
			.from(diary)
			.where(diary.member.id.eq(memberId))
			.orderBy(diary.id.desc())
			.fetch();
	}

}

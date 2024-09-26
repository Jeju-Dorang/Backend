package JejuDorang.JejuDorang.achievement.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import JejuDorang.JejuDorang.achievement.data.QAchievement;
import JejuDorang.JejuDorang.achievement.dto.AchievementDto;
import JejuDorang.JejuDorang.member.data.QMemberAchievement;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class AchievementCustomRepositoryImpl implements AchievementCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<AchievementDto> findAllByMemberId(Long memberId) {
		QAchievement achievement = QAchievement.achievement;
		QMemberAchievement memberAchievement = QMemberAchievement.memberAchievement;

		return jpaQueryFactory
			.select(Projections.constructor(AchievementDto.class,
				achievement.id.as("achievementId"),
				achievement.image.as("achievementIcon"),
				achievement.name.as("achievementName"),
				achievement.content.as("achievementComment"),
				achievement.maxAchieve.as("maxAchieve"),
				memberAchievement.achievementCnt,
				memberAchievement.achievementStatus))
			.from(memberAchievement)
			.join(memberAchievement.achievement, achievement)
			.where(memberAchievement.member.id.eq(memberId))
			.fetch();
	}
}

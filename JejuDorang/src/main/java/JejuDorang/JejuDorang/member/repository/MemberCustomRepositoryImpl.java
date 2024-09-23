package JejuDorang.JejuDorang.member.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import JejuDorang.JejuDorang.achievement.data.QAchievement;
import JejuDorang.JejuDorang.character.data.QCharacter;
import JejuDorang.JejuDorang.character.dto.CharacterImageDto;
import JejuDorang.JejuDorang.lodging.data.QLodging;
import JejuDorang.JejuDorang.lodging.dto.LodgingCoordinateDto;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.data.QMember;
import JejuDorang.JejuDorang.member.data.QMemberAchievement;
import JejuDorang.JejuDorang.member.dto.MemberMainResponseDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public MemberMainResponseDto getMainPage(Member member) {
		QMember qMember = QMember.member;
		QLodging qLodging = QLodging.lodging;
		QMemberAchievement qMemberAchievement = QMemberAchievement.memberAchievement;
		QAchievement qAchievement = QAchievement.achievement;
		QCharacter qCharacter = QCharacter.character;

		// 회원 기본 정보, 숙소 좌표 및 캐릭터 이미지 정보
		MemberMainResponseDto result = jpaQueryFactory
			.select(Projections.bean(
				MemberMainResponseDto.class,
				qMember.name.as("memberName"),
				qMember.email,
				qMember.content.as("memberComment"),
				qMember.image.as("memberImage"),
				Projections.constructor(LodgingCoordinateDto.class,
					qLodging.latitude.as("lat"),
					qLodging.longitude.as("lng")
				).as("lodging"),
				Projections.constructor(CharacterImageDto.class,
					qCharacter.backgroundImage,
					qCharacter.itemImage,
					qCharacter.petImage
				).as("characterImage")
			))
			.from(qMember)
			.leftJoin(qMember.home, qLodging).fetchJoin()
			.leftJoin(qMember.character, qCharacter).fetchJoin()
			.where(qMember.id.eq(member.getId()))
			.fetchOne();

		// 업적 정보
		List<MemberMainResponseDto.AchievementDto> achievements = jpaQueryFactory
			.select(Projections.bean(
				MemberMainResponseDto.AchievementDto.class,
				qAchievement.image.as("achievementIcon"),
				qAchievement.name.as("achievementName")
			))
			.from(qMemberAchievement)
			.join(qMemberAchievement.achievement, qAchievement)
			.where(qMemberAchievement.member.eq(member))
			.fetch();

		assert result != null;
		result.setAchievement(achievements);

		return result;
	}
}

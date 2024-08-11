package JejuDorang.JejuDorang.achievement.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.achievement.dto.AchievementDto;

@Repository
public interface AchievementCustomRepository {
	List<AchievementDto> findAllByMemberId(Long memberId);
}

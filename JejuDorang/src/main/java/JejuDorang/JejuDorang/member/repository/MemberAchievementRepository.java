package JejuDorang.JejuDorang.member.repository;

import JejuDorang.JejuDorang.achievement.enums.AchievementStatus;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.data.MemberAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAchievementRepository extends JpaRepository<MemberAchievement, Long> {

    List<MemberAchievement> findByMemberAndAchievementStatus(Member member, AchievementStatus achievementStatus);
}

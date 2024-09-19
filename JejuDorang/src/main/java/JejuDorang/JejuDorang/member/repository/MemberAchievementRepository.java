package JejuDorang.JejuDorang.member.repository;

import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.data.MemberAchievement;
import JejuDorang.JejuDorang.member.enums.AchievementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAchievementRepository extends JpaRepository<MemberAchievement, Long> {

    List<MemberAchievement> findByMemberAndAchievementStatus(Member member, AchievementStatus achievementStatus);
}

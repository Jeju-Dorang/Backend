package JejuDorang.JejuDorang.member.data;

import JejuDorang.JejuDorang.achievement.data.Achievement;
import JejuDorang.JejuDorang.achievement.enums.AchievementStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberAchievement {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "achievement_id")
	private Achievement achievement;

	@Enumerated(EnumType.STRING)
	private AchievementStatus achievementStatus;

	private long achievementCnt;

	public void incAchievementCnt() {
		this.achievementCnt++;
	}

	public void updateAchievementStatus() {
		this.achievementStatus = AchievementStatus.DONE;
	}

	public void initAchievementCnt() {
		this.achievementCnt = 0;
	}
}

package JejuDorang.JejuDorang.achievement.dto;


import JejuDorang.JejuDorang.achievement.enums.AchievementStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AchievementDto {
	private final String achievementIcon;
	private final String achievementName;
	private final String achievementComment;
	private final Long maxAchieve;
	private final Long achievementCnt;
	private final AchievementStatus achievementStatus;
}

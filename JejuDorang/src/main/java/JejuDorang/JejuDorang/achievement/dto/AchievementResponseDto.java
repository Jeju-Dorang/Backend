package JejuDorang.JejuDorang.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AchievementResponseDto {
	private String name;
	private String icon;
	private String comment;
	private int maxAchieve;
	private int achievementCnt;

	public AchievementResponseDto(AchievementDto achievementDto) {
		this.name = achievementDto.getAchievementName();
		this.icon = achievementDto.getAchievementIcon();
		this.comment = achievementDto.getAchievementComment();
		this.maxAchieve = achievementDto.getMaxAchieve().intValue();
		this.achievementCnt = achievementDto.getAchievementCnt().intValue();
	}
}

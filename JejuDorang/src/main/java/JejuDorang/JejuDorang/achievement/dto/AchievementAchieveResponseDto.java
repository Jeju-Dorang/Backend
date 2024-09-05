package JejuDorang.JejuDorang.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AchievementAchieveResponseDto {
	private String name;
	private String icon;
	private String comment;

	public AchievementAchieveResponseDto(AchievementDto achievementDto) {
		this.name = achievementDto.getAchievementName();
		this.icon = achievementDto.getAchievementIcon();
		this.comment = achievementDto.getAchievementComment();
	}
}

package JejuDorang.JejuDorang.achievement.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class AchievementListDto {
	private List<AchievementResponseDto> noAchieveList;
	private List<AchievementAchieveResponseDto> achieveList;

	public AchievementListDto(List<AchievementResponseDto> achievementResponseDtoList, List<AchievementAchieveResponseDto> achievementAchieveResponseDtoList) {
		this.noAchieveList = achievementResponseDtoList;
		this.achieveList = achievementAchieveResponseDtoList;
	}
}

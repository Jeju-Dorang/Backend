package JejuDorang.JejuDorang.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AchievementNotachieveResponseDto {
    private final String achievementIcon;
    private final String achievementName;
    private final String achievementComment;
    private final Long maxAchieve;
    private final Long achievementCnt;
    private final String category;
}

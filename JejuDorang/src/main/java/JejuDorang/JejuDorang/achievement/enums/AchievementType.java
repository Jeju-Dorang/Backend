package JejuDorang.JejuDorang.achievement.enums;

import lombok.Getter;

@Getter
public enum AchievementType {
    TOUR("여행"),
    FOOD("음식"),
    GENERAL("일반");

    private final String description;

    AchievementType(String description) {
        this.description = description;
    }
}

package JejuDorang.JejuDorang.member.dto;

import java.util.List;

import JejuDorang.JejuDorang.achievement.dto.AchievementDto;
import JejuDorang.JejuDorang.diary.dto.DiaryIdDto;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.Getter;

@Getter
public class MemberDetailResponseDto {
	private final Long id;
	private final String memberName;
	private final String email;
	private final String memberComment;
	private final String profileImage;
	private final String lodgingAddress;
	private List<DiaryIdDto> diaries;
	private List<AchievementDto> achievements;

	public void setAchievementsAndDiaries(List<AchievementDto> achievements, List<DiaryIdDto> diaries) {
		this.achievements = achievements;
		this.diaries = diaries;
	}

	public MemberDetailResponseDto(Member member) {
		this.id = member.getId();
		this.memberName = member.getName();
		this.email = member.getEmail();
		this.memberComment = member.getContent();
		this.profileImage = member.getImage();
		if (member.getHome() != null) {
			this.lodgingAddress = member.getHome().getAddress();
		} else {
			this.lodgingAddress = ""; // 또는 기본값 설정 가능
		}
	}

}

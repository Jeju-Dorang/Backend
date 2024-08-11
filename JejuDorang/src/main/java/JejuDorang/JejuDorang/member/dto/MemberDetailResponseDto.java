package JejuDorang.JejuDorang.member.dto;

import java.util.List;

import JejuDorang.JejuDorang.achievement.dto.AchievementDto;
import JejuDorang.JejuDorang.diary.dto.DiaryIdDto;
import JejuDorang.JejuDorang.member.data.Member;

public class MemberDetailResponseDto {
	Long id;
	String memberName;
	String email;
	String memberComment;
	String profileImage;
	String lodgingAddress;
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
		this.lodgingAddress = member.getHomeAddress();
	}

}

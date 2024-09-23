package JejuDorang.JejuDorang.member.dto;

import java.util.List;

import JejuDorang.JejuDorang.character.dto.CharacterImageDto;
import JejuDorang.JejuDorang.lodging.dto.LodgingCoordinateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberMainResponseDto {

	private String memberName;
	private String email;
	private String memberComment;
	private String memberImage;
	private CharacterImageDto characterImage;
	private List<AchievementDto> achievement;
	private LodgingCoordinateDto lodging;

	public void setAchievement(List<AchievementDto> achievements) {
		this.achievement = achievements;
	}

	public void setCharacterImage(CharacterImageDto characterImage) {
		this.characterImage = characterImage;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AchievementDto {
		private String achievementIcon;
		private String achievementName;
	}

	public MemberMainResponseDto(String memberName, String email, String memberComment, String memberImage,
		CharacterImageDto characterImage, LodgingCoordinateDto lodging) {
		this.memberName = memberName;
		this.email = email;
		this.memberComment = memberComment;
		this.memberImage = memberImage;
		this.characterImage = characterImage;
		this.lodging = lodging;
	}
}

package JejuDorang.JejuDorang.character.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CharacterImageDto {
	private int backgroundImage;
	private int itemImage;
	private int petImage;
}

package JejuDorang.JejuDorang.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberNameDto {

	@NotBlank(message = "이름을 입력해주세요.")
	@NotNull(message = "이름을 입력해주세요.")
	private String memberName;
}

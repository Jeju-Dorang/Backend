package JejuDorang.JejuDorang.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEmailDto {

	@Email(message = "이메일 형식이 아닙니다.")
	@NotBlank(message = "이메일을 입력해주세요.")
	@NotNull(message = "이메일을 입력해주세요.")
	private String memberEmail;
}

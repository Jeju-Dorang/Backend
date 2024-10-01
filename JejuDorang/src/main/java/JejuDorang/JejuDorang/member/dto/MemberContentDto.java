package JejuDorang.JejuDorang.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberContentDto {

    @NotNull(message = "memberContent를 입력해주세요.")
    private String memberContent;
}

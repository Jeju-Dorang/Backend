package JejuDorang.JejuDorang.character.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

    @NotNull(message = "배경 아이템을 선택해주세요")
    private int backgroundItem;

    @NotNull(message = "소지품 아이템을 선택해주세요")
    private int stuffItem;

    @NotNull(message = "펫 아이템을 선택해주세요")
    private int petItem;
}

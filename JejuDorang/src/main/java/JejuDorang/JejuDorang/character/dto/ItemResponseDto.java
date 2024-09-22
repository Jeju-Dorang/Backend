package JejuDorang.JejuDorang.character.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {

    List<Index> backgroundImage = new ArrayList<>();
    List<Index> itemImage = new ArrayList<>();
    List<Index> petImage = new ArrayList<>();

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Index {
        private Long index;  // index 값을 담는 필드
    }
}

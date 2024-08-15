package JejuDorang.JejuDorang.streak.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StreakResponseDto {

    private LocalDate date;
    private Long count;
}

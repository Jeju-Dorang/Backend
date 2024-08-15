package JejuDorang.JejuDorang.streak.controller;

import JejuDorang.JejuDorang.streak.data.Streak;
import JejuDorang.JejuDorang.streak.dto.StreakResponseDto;
import JejuDorang.JejuDorang.streak.service.StreakService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class StreakController {

    private final StreakService streakService;

    @GetMapping("/posts/streaks")
    public ResponseEntity<List<StreakResponseDto>> getStreaks() {

        List<StreakResponseDto> streaks = streakService.getStreaks();
        return ResponseEntity.ok(streaks);
    }
}

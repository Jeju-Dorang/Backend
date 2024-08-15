package JejuDorang.JejuDorang.streak.service;

import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.streak.data.Streak;
import JejuDorang.JejuDorang.streak.repository.StreakRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class StreakService {

    private final StreakRepository streakRepository;

    // 오늘 날짜로 스트릭 생성, 이미 있으면 count 증가
    public void createStreak(Member member) {

        Streak streak = streakRepository.findByMemberIdAndDate(member.getId(), LocalDate.now());

        if (streak == null) {
            Streak newStreak = Streak.builder()
                    .member(member)
                    .date(LocalDate.now())
                    .count(1)
                    .build();
            streakRepository.save(newStreak);
        }
        else {
            streak.incrementCount();
        }
    }
}

package JejuDorang.JejuDorang.streak.service;

import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.streak.data.Streak;
import JejuDorang.JejuDorang.streak.dto.StreakResponseDto;
import JejuDorang.JejuDorang.streak.repository.StreakRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.AsyncServerResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            streakRepository.save(streak);
        }
    }

    // 멤버의 스트릭 반환
    public List<StreakResponseDto> getStreaks(Member member, String year, String month) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(year + "-" + month + "-01", formatter);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()).plusDays(1);

        List<Streak> streaks = streakRepository.findAllByMemberIdAndDateBetween(member.getId(), startDate, endDate);


        List<StreakResponseDto> response = new ArrayList<>();
        for(Streak streak : streaks) {
            response.add(new StreakResponseDto(streak.getDate(), streak.getCount()));
        }

        return response;
    }
}

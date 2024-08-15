package JejuDorang.JejuDorang.streak.repository;

import JejuDorang.JejuDorang.streak.data.Streak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface StreakRepository extends JpaRepository<Streak, Long> {

    Streak findByMemberIdAndDate(Long memberId, LocalDate date);
}

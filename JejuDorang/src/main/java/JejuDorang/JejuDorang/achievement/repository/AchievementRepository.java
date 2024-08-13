package JejuDorang.JejuDorang.achievement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.achievement.data.Achievement;
import JejuDorang.JejuDorang.diary.data.Diary;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long>, AchievementCustomRepository {
}

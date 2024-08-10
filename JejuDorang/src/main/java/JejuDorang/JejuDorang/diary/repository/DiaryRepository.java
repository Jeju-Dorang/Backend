package JejuDorang.JejuDorang.diary.repository;

import JejuDorang.JejuDorang.diary.data.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

}

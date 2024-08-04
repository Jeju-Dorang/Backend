package JejuDorang.JejuDorang.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.diary.data.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryCustomRepository {
}

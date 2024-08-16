package JejuDorang.JejuDorang.diary.repository;


import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.enums.SecretType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryCustomRepository {
    List<Diary> findBySecretAndDate(SecretType secret, LocalDate date);
}

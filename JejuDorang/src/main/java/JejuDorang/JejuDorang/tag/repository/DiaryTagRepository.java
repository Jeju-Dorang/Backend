package JejuDorang.JejuDorang.tag.repository;

import JejuDorang.JejuDorang.tag.data.DiaryTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryTagRepository extends JpaRepository<DiaryTag, Long> {
    List<DiaryTag> findAllByDiaryId(Long diaryId);
}

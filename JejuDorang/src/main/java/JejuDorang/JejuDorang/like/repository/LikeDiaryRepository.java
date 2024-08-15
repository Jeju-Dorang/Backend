package JejuDorang.JejuDorang.like.repository;

import JejuDorang.JejuDorang.like.data.LikeDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeDiaryRepository extends JpaRepository<LikeDiary, Long> {

    boolean existsByDiaryIdAndMemberId(Long diaryId, Long memberId);
}

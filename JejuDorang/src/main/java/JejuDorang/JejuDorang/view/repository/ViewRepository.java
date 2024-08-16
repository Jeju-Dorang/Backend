package JejuDorang.JejuDorang.view.repository;

import JejuDorang.JejuDorang.view.data.View;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewRepository extends JpaRepository<View, Long> {

    boolean existsByDiaryIdAndMemberId(Long diaryId, Long memberId);
    View findByDiaryIdAndMemberId(Long diaryId, Long memberId);
}

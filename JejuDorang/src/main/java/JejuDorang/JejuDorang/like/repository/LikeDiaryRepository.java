package JejuDorang.JejuDorang.like.repository;

import JejuDorang.JejuDorang.like.data.LikeDiary;
import JejuDorang.JejuDorang.member.data.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeDiaryRepository extends JpaRepository<LikeDiary, Long> {

    boolean existsByDiaryIdAndMemberId(Long diaryId, Long memberId);

    LikeDiary findByDiaryIdAndMember(Long diaryId, Member member);
}

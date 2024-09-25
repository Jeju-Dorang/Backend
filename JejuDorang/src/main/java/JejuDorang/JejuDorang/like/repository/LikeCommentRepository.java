package JejuDorang.JejuDorang.like.repository;

import JejuDorang.JejuDorang.like.data.LikeComment;
import JejuDorang.JejuDorang.member.data.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    List<LikeComment> findAllByCommentId(Long commentId);
    boolean existsByCommentIdAndMemberId(Long commentId, Long memberId);

    LikeComment findByCommentIdAndMember(Long commentId, Member member);
}

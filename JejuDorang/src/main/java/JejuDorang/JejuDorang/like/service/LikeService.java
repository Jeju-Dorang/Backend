package JejuDorang.JejuDorang.like.service;

import JejuDorang.JejuDorang.comment.data.Comment;
import JejuDorang.JejuDorang.comment.repository.CommentRepository;
import JejuDorang.JejuDorang.like.data.LikeComment;
import JejuDorang.JejuDorang.like.repository.LikeCommentRepository;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;

    // 댓글 좋아요 생성
    public void createLikeComment(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다 : " + commentId));

        LikeComment likeComment = LikeComment.builder()
                .comment(comment)
                .member(member)
                .date(LocalDateTime.now())
                .build();
        likeCommentRepository.save(likeComment);
    }

    // 댓글 좋아요 수 계산
    public int countLikeComment(Long commentId) {

        List<LikeComment> likeComments = likeCommentRepository.findAllByCommentId(commentId);
        return likeComments.size();
    }

    // 현재 유저가 특정 댓글에 좋아요를 눌렀는지 여부
    public boolean alreadyLikeComment(Long commentId, Long memberId) {

        boolean alreadyLike = likeCommentRepository.existsByCommentIdAndMemberId(commentId, memberId);
        return alreadyLike;
    }

}

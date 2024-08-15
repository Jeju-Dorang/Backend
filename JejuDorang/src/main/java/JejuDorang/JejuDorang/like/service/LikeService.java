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

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;

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
}

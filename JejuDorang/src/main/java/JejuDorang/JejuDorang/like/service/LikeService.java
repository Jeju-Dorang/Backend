package JejuDorang.JejuDorang.like.service;

import JejuDorang.JejuDorang.comment.data.Comment;
import JejuDorang.JejuDorang.comment.repository.CommentRepository;
import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.repository.DiaryRepository;
import JejuDorang.JejuDorang.like.data.LikeComment;
import JejuDorang.JejuDorang.like.data.LikeDiary;
import JejuDorang.JejuDorang.like.repository.LikeCommentRepository;
import JejuDorang.JejuDorang.like.repository.LikeDiaryRepository;
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
    private final LikeDiaryRepository likeDiaryRepository;
    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;

    // 댓글 좋아요 생성
    public void createLikeComment(Long commentId, Member member) {

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


    // 일기 좋아요 생성
    public void createLikeDiary(Long diaryId, Member member) {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 일기입니다 : " + diaryId));

        LikeDiary likeDiary = LikeDiary.builder()
                .diary(diary)
                .member(member)
                .date(LocalDateTime.now())
                .build();
        likeDiaryRepository.save(likeDiary);
    }

    // 현재 유저가 특정 일기에 좋아요를 눌렀는지 여부
    public boolean alreadyLikeDiary(Long diaryId, Long memberId) {
        boolean alreadyLike = likeDiaryRepository.existsByDiaryIdAndMemberId(diaryId, memberId);
        return alreadyLike;
    }
}

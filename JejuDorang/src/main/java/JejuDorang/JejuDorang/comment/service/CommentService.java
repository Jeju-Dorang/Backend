package JejuDorang.JejuDorang.comment.service;

import JejuDorang.JejuDorang.achievement.data.Achievement;
import JejuDorang.JejuDorang.achievement.repository.AchievementRepository;
import JejuDorang.JejuDorang.comment.data.Comment;
import JejuDorang.JejuDorang.comment.dto.CommentRequestDto;
import JejuDorang.JejuDorang.comment.repository.CommentRepository;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.data.MemberAchievement;
import JejuDorang.JejuDorang.member.repository.MemberAchievementRepository;
import JejuDorang.JejuDorang.question.data.Question;
import JejuDorang.JejuDorang.question.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final MemberAchievementRepository memberAchievementRepository;
    private final AchievementRepository achievementRepository;

    public void getCommentAchievement(Long id, Member member) {
        Achievement achievement = achievementRepository.findById(4L)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 질문 글입니다"));
        MemberAchievement memberAchievement = memberAchievementRepository.findByMemberAndAchievement(member, achievement);
        memberAchievement.updateAchievementStatus();
        memberAchievementRepository.save(memberAchievement);
    }

    @Transactional
    public void createComment(Long questionPostId, CommentRequestDto commentRequestDto, Member member) {

        Question questionPost = questionRepository.findById(questionPostId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 질문 글입니다 : " + questionPostId));

        Comment comment = Comment.builder()
                .member(member)
                .question(questionPost)
                .content(commentRequestDto.getContent())
                .date(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        // 댓글 업적
        member.increaseQuestionCommentCnt();
        int cnt = member.getQuestionCommentCnt();
        if (cnt == 5) {
            getCommentAchievement(4L, member);
        } else if (cnt == 10) {
            getCommentAchievement(8L, member);
        } else if (cnt == 20) {
            getCommentAchievement(9L, member);
        }
    }
}

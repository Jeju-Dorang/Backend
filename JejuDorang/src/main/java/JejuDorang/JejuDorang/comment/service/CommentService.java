package JejuDorang.JejuDorang.comment.service;

import JejuDorang.JejuDorang.comment.data.Comment;
import JejuDorang.JejuDorang.comment.dto.CommentRequest;
import JejuDorang.JejuDorang.comment.repository.CommentRepository;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.question.data.Question;
import JejuDorang.JejuDorang.question.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;

    public void createComment(Long questionPostId, CommentRequest commentRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        Question questionPost = questionRepository.findById(questionPostId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 질문 글입니다 : " + questionPostId));

        Comment comment = Comment.builder()
                .member(member)
                .question(questionPost)
                .content(commentRequest.getContent())
                .date(LocalDateTime.now())
                .build();

        commentRepository.save(comment);
    }
}

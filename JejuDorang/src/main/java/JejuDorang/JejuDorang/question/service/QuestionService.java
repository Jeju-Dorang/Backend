package JejuDorang.JejuDorang.question.service;

import JejuDorang.JejuDorang.comment.data.Comment;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.question.data.Question;
import JejuDorang.JejuDorang.question.dto.QuestionDetailResponse;
import JejuDorang.JejuDorang.question.dto.QuestionInputRequest;
import JejuDorang.JejuDorang.question.dto.QuestionResponse;
import JejuDorang.JejuDorang.question.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void createQuestion(QuestionInputRequest questionInputRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        Question question = Question.builder()
                .member(member)
                .title(questionInputRequest.getTitle())
                .content(questionInputRequest.getContent())
                .date(LocalDateTime.now())
                .build();

        questionRepository.save(question);
    }

    // 최신순으로 질문글 정렬해서 반환
    public List<QuestionResponse> getQuestions() {
        List<Question> questions = questionRepository.findAllByOrderByDateDesc();
        List<QuestionResponse> response = new ArrayList<>();

        for(Question question : questions) {
            response.add(new QuestionResponse(
                    question.getId(),
                    question.getTitle(),
                    question.getContent()
            ));
        }
        return response;
    }

    public QuestionDetailResponse getQuestionDetail(Long questionPostId) {
        Question question = questionRepository.findById(questionPostId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 질문 글입니다 : " + questionPostId));
        List<QuestionDetailResponse.Comment> comments = new ArrayList<>();

        for(Comment comment : question.getCommentList()) {
            comments.add(new QuestionDetailResponse.Comment(
                    comment.getMember().getName(),
                    comment.getMember().getImage(),
                    comment.getContent()
            ));
        }
        QuestionDetailResponse response = new QuestionDetailResponse(
                question.getTitle(),
                question.getContent(),
                question.getMember().getName(),
                comments
        );
        return response;
    }
}

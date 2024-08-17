package JejuDorang.JejuDorang.question.service;

import JejuDorang.JejuDorang.comment.data.Comment;
import JejuDorang.JejuDorang.like.service.LikeService;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.question.data.Question;
import JejuDorang.JejuDorang.question.dto.QuestionDetailResponseDto;
import JejuDorang.JejuDorang.question.dto.QuestionInputRequestDto;
import JejuDorang.JejuDorang.question.dto.QuestionResponseDto;
import JejuDorang.JejuDorang.question.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final LikeService likeService;

    public void createQuestion(QuestionInputRequestDto questionInputRequestDto, Member member) {

        Question question = Question.builder()
                .member(member)
                .title(questionInputRequestDto.getTitle())
                .content(questionInputRequestDto.getContent())
                .date(LocalDateTime.now())
                .build();

        questionRepository.save(question);
    }

    // 최신순으로 질문글 정렬해서 반환
    public List<QuestionResponseDto> getQuestions() {
        List<Question> questions = questionRepository.findAllByOrderByDateDesc();
        List<QuestionResponseDto> response = new ArrayList<>();

        for(Question question : questions) {
            response.add(new QuestionResponseDto(
                    question.getId(),
                    question.getTitle(),
                    question.getContent()
            ));
        }
        return response;
    }

    // 질문글 상세 페이지 정보 가져오기
    public QuestionDetailResponseDto getQuestionDetail(Long questionPostId, Member member) {

        Question question = questionRepository.findById(questionPostId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 질문 글입니다 : " + questionPostId));
        List<QuestionDetailResponseDto.Comment> comments = new ArrayList<>();

        for(Comment comment : question.getCommentList()) {
            comments.add(new QuestionDetailResponseDto.Comment(
                    comment.getMember().getName(),
                    comment.getMember().getImage(),
                    comment.getContent(),
                    likeService.countLikeComment(comment.getId()),
                    likeService.alreadyLikeComment(comment.getId(), member.getId())
            ));
        }
        QuestionDetailResponseDto response = new QuestionDetailResponseDto(
                question.getTitle(),
                question.getContent(),
                question.getMember().getName(),
                comments
        );
        return response;
    }
}

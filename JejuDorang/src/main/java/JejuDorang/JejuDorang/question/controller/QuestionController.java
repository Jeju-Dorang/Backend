package JejuDorang.JejuDorang.question.controller;


import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.question.dto.QuestionDetailResponseDto;
import JejuDorang.JejuDorang.question.dto.QuestionInputRequestDto;
import JejuDorang.JejuDorang.question.dto.QuestionResponseDto;
import JejuDorang.JejuDorang.question.dto.QuestionSearchResponseDto;
import JejuDorang.JejuDorang.question.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/question")
    public ResponseEntity createQuestion(@RequestBody QuestionInputRequestDto questionInputRequestDto, @Login Member member) {

        questionService.createQuestion(questionInputRequestDto, member);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionResponseDto>> getQuestions() {

        List<QuestionResponseDto> questions = questionService.getQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/question/{questionPostId}")
    public ResponseEntity<QuestionDetailResponseDto> getQuestionDetail
            (@PathVariable("questionPostId") Long questionPostId, @Login Member member) {

        QuestionDetailResponseDto questionDetailResponseDto
            = questionService.getQuestionDetail(questionPostId, member);
        return ResponseEntity.ok(questionDetailResponseDto);
    }

    @GetMapping("/search/questions")
    public ResponseEntity<List<QuestionSearchResponseDto>> searchQuestion(@RequestParam String keyword) {

        List<QuestionSearchResponseDto> questionSearchResponseDtos = questionService.searchQuestion(keyword);
        return ResponseEntity.ok(questionSearchResponseDtos);
    }
}

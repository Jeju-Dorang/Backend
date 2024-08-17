package JejuDorang.JejuDorang.question.controller;


import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.question.dto.QuestionDetailResponse;
import JejuDorang.JejuDorang.question.dto.QuestionInputRequest;
import JejuDorang.JejuDorang.question.dto.QuestionResponse;
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
    public ResponseEntity createQuestion(@RequestBody QuestionInputRequest questionInputRequest, @Login Member member) {

        questionService.createQuestion(questionInputRequest, member);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionResponse>> getQuestions() {

        List<QuestionResponse> questions = questionService.getQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/question/{questionPostId}")
    public ResponseEntity<QuestionDetailResponse> getQuestionDetail
            (@PathVariable("questionPostId") Long questionPostId, @Login Member member) {

        QuestionDetailResponse questionDetailResponse
            = questionService.getQuestionDetail(questionPostId, member);
        return ResponseEntity.ok(questionDetailResponse);
    }
}

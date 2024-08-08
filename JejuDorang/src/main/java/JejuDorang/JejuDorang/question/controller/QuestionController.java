package JejuDorang.JejuDorang.question.controller;


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
    public ResponseEntity createQuestion(@RequestBody QuestionInputRequest questionInputRequest) {

        questionService.createQuestion(questionInputRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionResponse>> getQuestions() {

        List<QuestionResponse> questions = questionService.getQuestions();
        return ResponseEntity.ok(questions);
    }
}

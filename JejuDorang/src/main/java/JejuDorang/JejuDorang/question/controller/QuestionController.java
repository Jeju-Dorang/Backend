package JejuDorang.JejuDorang.question.controller;


import JejuDorang.JejuDorang.question.dto.QuestionInputDto;
import JejuDorang.JejuDorang.question.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/question")
    public ResponseEntity createQuestion(@RequestBody QuestionInputDto questionInputDto) {

        questionService.createQuestion(questionInputDto);

        return new ResponseEntity(HttpStatus.OK);
    }
}

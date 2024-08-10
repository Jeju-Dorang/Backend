package JejuDorang.JejuDorang.diary.controller;

import JejuDorang.JejuDorang.diary.dto.DiaryRequest;
import JejuDorang.JejuDorang.diary.service.DiaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/diary")
    public ResponseEntity createDiary(@RequestBody DiaryRequest diaryRequest) {

        diaryService.createDiary(diaryRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
}

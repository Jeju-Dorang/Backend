package JejuDorang.JejuDorang.diary.controller;

import JejuDorang.JejuDorang.diary.dto.DiaryDetailResponse;
import JejuDorang.JejuDorang.diary.dto.DiaryRequest;
import JejuDorang.JejuDorang.diary.service.DiaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/diaries/{diaryId}")
    public ResponseEntity<DiaryDetailResponse> getDiaryDetail(@PathVariable Long diaryId) {

        DiaryDetailResponse diaryDetailResponse
                = diaryService.getDiaryDetail(diaryId);
        return ResponseEntity.ok(diaryDetailResponse);
    }
}

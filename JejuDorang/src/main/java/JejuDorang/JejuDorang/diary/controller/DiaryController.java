package JejuDorang.JejuDorang.diary.controller;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.diary.dto.DiaryDetailResponse;
import JejuDorang.JejuDorang.diary.dto.DiaryPublicResponse;
import JejuDorang.JejuDorang.diary.dto.DiaryRequest;
import JejuDorang.JejuDorang.diary.service.DiaryService;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/diary")
    public ResponseEntity<Void> createDiary(@RequestBody DiaryRequest diaryRequest, @Login Member member) {

        diaryService.createDiary(diaryRequest, member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/diaries/{diaryId}")
    public ResponseEntity<DiaryDetailResponse> getDiaryDetail(@PathVariable Long diaryId, @Login Member member) {

        DiaryDetailResponse diaryDetailResponse
                = diaryService.getDiaryDetail(diaryId, member);
        return ResponseEntity.ok(diaryDetailResponse);
    }

    @GetMapping("/diaries")
    public ResponseEntity<List<DiaryPublicResponse>> getPublicDiaries(@Login Member member) {

        List<DiaryPublicResponse> diaryPublicResponses
                = diaryService.getPublicDiaries(member);
        return ResponseEntity.ok(diaryPublicResponses);
    }
}

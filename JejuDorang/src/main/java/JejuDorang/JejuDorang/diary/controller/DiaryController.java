package JejuDorang.JejuDorang.diary.controller;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.diary.dto.DiaryDetailResponseDto;
import JejuDorang.JejuDorang.diary.dto.DiaryPublicResponseDto;
import JejuDorang.JejuDorang.diary.dto.DiaryRequestDto;
import JejuDorang.JejuDorang.diary.service.DiaryService;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class DiaryController {

    private final DiaryService diaryService;

    // 일기 작성
    // 1. isAchievement = true -> achievementId 확인해서 달성률 증가 + 일기 저장
    // 2. isAchievement = false -> 일기 저장
    @PostMapping(value = "/diary")
    public ResponseEntity<Void> createDiary(@RequestBody DiaryRequestDto diaryRequestDto, @Login Member member) {

        diaryService.createDiary(diaryRequestDto, member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/diaries/{diaryId}")
    public ResponseEntity<DiaryDetailResponseDto> getDiaryDetail(@PathVariable Long diaryId, @Login Member member) {

        DiaryDetailResponseDto diaryDetailResponseDto
                = diaryService.getDiaryDetail(diaryId, member);
        return ResponseEntity.ok(diaryDetailResponseDto);
    }

    @GetMapping("/diaries")
    public ResponseEntity<List<DiaryPublicResponseDto>> getPublicDiaries(@Login Member member) {

        List<DiaryPublicResponseDto> diaryPublicResponsDtos
                = diaryService.getPublicDiaries(member);
        return ResponseEntity.ok(diaryPublicResponsDtos);
    }
}

package JejuDorang.JejuDorang.diary.service;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.dto.DiaryRequest;
import JejuDorang.JejuDorang.diary.repository.DiaryRepository;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public void createDiary(DiaryRequest diaryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();


        Diary diary = Diary.builder()
                .title(diaryRequest.getTitle())
                .date(LocalDateTime.now())
                .content(diaryRequest.getContent())
                .image(diaryRequest.getImageUrl())
                .secret(diaryRequest.getType())
                .member(member)
                .build();

        diaryRepository.save(diary);
    }
}

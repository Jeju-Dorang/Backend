package JejuDorang.JejuDorang.diary.service;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.dto.DiaryDetailResponse;
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

    // 일기 작성
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

    // 스토리의 일기 상세 정보를 보여줌
    public DiaryDetailResponse getDiaryDetail(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 일기 글입니다 : " + diaryId));

        DiaryDetailResponse response = new DiaryDetailResponse(
                diary.getMember().getName(),
                diary.getDate(),
                diary.getImage(),
                diary.getContent()
        );
        return response;
    }
}

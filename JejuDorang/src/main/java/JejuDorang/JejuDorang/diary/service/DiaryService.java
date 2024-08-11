package JejuDorang.JejuDorang.diary.service;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.dto.DiaryDetailResponse;
import JejuDorang.JejuDorang.diary.dto.DiaryPublicResponse;
import JejuDorang.JejuDorang.diary.dto.DiaryRequest;
import JejuDorang.JejuDorang.diary.enums.SecretType;
import JejuDorang.JejuDorang.diary.repository.DiaryRepository;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    // 일기 작성
    public void createDiary(DiaryRequest diaryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        // public, private 판별
        SecretType secretType;
        if (diaryRequest.getSecret().equals("public"))
            secretType = SecretType.PUBLIC;
        else
            secretType = SecretType.PRIVATE;

        // 일기 DB에 저장
        Diary diary = Diary.builder()
                .title(diaryRequest.getTitle())
                .date(LocalDateTime.now())
                .content(diaryRequest.getContent())
                .image(diaryRequest.getImageUrl())
                .secret(secretType)
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

    // 다른 멤버들의 public 일기 가져오기
    public List<DiaryPublicResponse> getPublicDiaries() {
        List<Diary> diaryList = diaryRepository.findBySecretAndDate(SecretType.PUBLIC, LocalDateTime.now());
        List<DiaryPublicResponse> response = new ArrayList<>();

        for(Diary diary : diaryList) {
            response.add( new DiaryPublicResponse(
                    diary.getId(),
                    diary.getMember().getName(),
                    diary.getImage()
            ));
        }
        return response;
    }
}

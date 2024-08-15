package JejuDorang.JejuDorang.diary.service;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.dto.DiaryDetailResponse;
import JejuDorang.JejuDorang.diary.dto.DiaryPublicResponse;
import JejuDorang.JejuDorang.diary.dto.DiaryRequest;
import JejuDorang.JejuDorang.diary.enums.SecretType;
import JejuDorang.JejuDorang.diary.repository.DiaryRepository;
import JejuDorang.JejuDorang.like.service.LikeService;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.streak.service.StreakService;
import JejuDorang.JejuDorang.tag.data.DiaryTag;
import JejuDorang.JejuDorang.tag.data.Tag;
import JejuDorang.JejuDorang.tag.dto.TagDto;
import JejuDorang.JejuDorang.tag.repository.DiaryTagRepository;
import JejuDorang.JejuDorang.tag.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiaryService {

    private final TagService tagService;
    private final StreakService streakService;
    private final LikeService likeService;
    private final DiaryRepository diaryRepository;
    private final DiaryTagRepository diaryTagRepository;

    // 일기 작성
    public void createDiary(DiaryRequest diaryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        // 일기 DB에 저장
        Diary diary = Diary.builder()
                .title(diaryRequest.getTitle())
                .date(LocalDate.now())
                .content(diaryRequest.getContent())
                .image(diaryRequest.getImageUrl())
                .secret(diaryRequest.getSecret())
                .member(member)
                .build();
        diaryRepository.save(diary);

        // 태그 저장
        for (TagDto tag : diaryRequest.getTagList()) {
            Tag newTag = tagService.saveTag(tag.getTagName());
            DiaryTag diaryTag = new DiaryTag(newTag, diary);
            diaryTagRepository.save(diaryTag);
        }

        // 스트릭 생성
        streakService.createStreak(member);
    }

    // 스토리의 일기 상세 정보를 보여줌
    public DiaryDetailResponse getDiaryDetail(Long diaryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 일기 글입니다 : " + diaryId));

        // 일기에 관련된 tag 찾아와서 dto에 담아주기
        List<DiaryTag> findDiaryTag = diaryTagRepository.findAllByDiaryId(diaryId);
        List<TagDto> tagList = new ArrayList<>();
        for(DiaryTag diaryTag : findDiaryTag) {
            tagList.add(new TagDto(diaryTag.getTag().getName()));
        }

        DiaryDetailResponse response = new DiaryDetailResponse (
                diary.getMember().getName(),
                diary.getDate(),
                diary.getImage(),
                diary.getContent(),
                likeService.alreadyLikeDiary(diary.getId(), member.getId()),
                tagList
        );
        return response;
    }

    // 다른 멤버들의 public 일기 가져오기
    public List<DiaryPublicResponse> getPublicDiaries() {
        List<Diary> diaryList = diaryRepository.findBySecretAndDate(SecretType.PUBLIC, LocalDate.now());
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

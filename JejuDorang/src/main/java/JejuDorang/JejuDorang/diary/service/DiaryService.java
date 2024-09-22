package JejuDorang.JejuDorang.diary.service;

import JejuDorang.JejuDorang.achievement.data.Achievement;
import JejuDorang.JejuDorang.achievement.enums.AchievementStatus;
import JejuDorang.JejuDorang.achievement.repository.AchievementRepository;
import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.dto.DiaryDetailResponseDto;
import JejuDorang.JejuDorang.diary.dto.DiaryPublicResponseDto;
import JejuDorang.JejuDorang.diary.dto.DiaryRequestDto;
import JejuDorang.JejuDorang.diary.enums.SecretType;
import JejuDorang.JejuDorang.diary.repository.DiaryRepository;
import JejuDorang.JejuDorang.like.service.LikeService;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.data.MemberAchievement;
import JejuDorang.JejuDorang.member.repository.MemberAchievementRepository;
import JejuDorang.JejuDorang.streak.service.StreakService;
import JejuDorang.JejuDorang.tag.data.DiaryTag;
import JejuDorang.JejuDorang.tag.data.Tag;
import JejuDorang.JejuDorang.tag.dto.TagDto;
import JejuDorang.JejuDorang.tag.repository.DiaryTagRepository;
import JejuDorang.JejuDorang.tag.service.TagService;
import JejuDorang.JejuDorang.view.data.View;
import JejuDorang.JejuDorang.view.repository.ViewRepository;
import JejuDorang.JejuDorang.view.service.ViewService;
import lombok.AllArgsConstructor;
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
    private final ViewService viewService;
    private final DiaryRepository diaryRepository;
    private final DiaryTagRepository diaryTagRepository;
    private final ViewRepository viewRepository;
    private final MemberAchievementRepository memberAchievementRepository;
    private final AchievementRepository achievementRepository;

    // 일기 작성
    public void createDiary(DiaryRequestDto diaryRequestDto, Member member) {

        // isAchievement 확인
        Boolean isAchievement = diaryRequestDto.getIsAchievement();
        if (isAchievement == true) {
            Long achievementId = diaryRequestDto.getAchievementId();
            Achievement achievement = achievementRepository.findById(achievementId)
                    .orElseThrow(()->new IllegalArgumentException("존재하지 않는 업적" ));
            MemberAchievement memberAchievement = memberAchievementRepository.findByMemberAndAchievement(member, achievement);

            // 달성률 확인
            long achievementCnt = memberAchievement.getAchievementCnt();
            Long max = achievement.getMaxAchieve();
            if (achievementCnt < max) {
                memberAchievement.incAchievementCnt();
            }
            if (memberAchievement.getAchievementCnt() == max) {
                memberAchievement.updateAchievementStatus();
            }
            memberAchievementRepository.save(memberAchievement);
        }

        // 일기 DB에 저장
        Diary diary = Diary.builder()
                .title(diaryRequestDto.getTitle())
                .date(LocalDate.now())
                .content(diaryRequestDto.getContent())
                .image(diaryRequestDto.getImageUrl())
                .secret(diaryRequestDto.getSecret())
                .member(member)
                .build();
        diaryRepository.save(diary);

        // 태그 저장
        for (TagDto tag : diaryRequestDto.getTagList()) {
            Tag newTag = tagService.saveTag(tag.getTagName());
            DiaryTag diaryTag = new DiaryTag(newTag, diary);
            diaryTagRepository.save(diaryTag);
        }

        // 스트릭 생성
        streakService.createStreak(member);
    }

    // 스토리의 일기 상세 정보를 보여줌
    public DiaryDetailResponseDto getDiaryDetail(Long diaryId, Member member) {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 일기 글입니다 : " + diaryId));

        // 일기에 관련된 tag 찾아와서 dto에 담아주기
        List<DiaryTag> findDiaryTag = diaryTagRepository.findAllByDiaryId(diaryId);
        List<TagDto> tagList = new ArrayList<>();
        for(DiaryTag diaryTag : findDiaryTag) {
            tagList.add(new TagDto(diaryTag.getTag().getName()));
        }

        // 현재 유저가 해당 일기 봤음을 기록
        View view = viewRepository.findByDiaryIdAndMemberId(diary.getId(), member.getId());
        if (view == null) {
            View newView = View.builder()
                    .diary(diary)
                    .member(member)
                    .build();
            viewRepository.save(newView);
        }

        DiaryDetailResponseDto response = new DiaryDetailResponseDto(
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
    public List<DiaryPublicResponseDto> getPublicDiaries(Member member) {

        List<Diary> diaryList = diaryRepository.findBySecretAndDate(SecretType.PUBLIC, LocalDate.now());
        List<DiaryPublicResponseDto> response = new ArrayList<>();

        for(Diary diary : diaryList) {
            response.add( new DiaryPublicResponseDto(
                    diary.getId(),
                    diary.getMember().getName(),
                    diary.getImage(),
                    viewService.alreadyView(diary.getId(), member.getId())
            ));
        }
        return response;
    }
}

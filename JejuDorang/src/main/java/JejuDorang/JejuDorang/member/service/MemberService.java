package JejuDorang.JejuDorang.member.service;

import java.util.ArrayList;
import java.util.List;

import JejuDorang.JejuDorang.achievement.data.Achievement;
import JejuDorang.JejuDorang.achievement.dto.AchievementAchieveResponseDto;
import JejuDorang.JejuDorang.achievement.dto.AchievementDto;
import JejuDorang.JejuDorang.achievement.dto.AchievementListDto;
import JejuDorang.JejuDorang.achievement.dto.AchievementResponseDto;
import JejuDorang.JejuDorang.achievement.repository.AchievementRepository;
import JejuDorang.JejuDorang.auth.dto.KakaoUserInfoDto;
import JejuDorang.JejuDorang.diary.dto.DiaryIdDto;
import JejuDorang.JejuDorang.diary.dto.DiaryListResponseDTO;
import JejuDorang.JejuDorang.diary.dto.MyDiaryDetailResponseDto;
import JejuDorang.JejuDorang.diary.enums.SecretType;
import JejuDorang.JejuDorang.diary.repository.DiaryRepository;
import JejuDorang.JejuDorang.error.exception.NotFoundException;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.dto.MemberDetailResponseDto;
import JejuDorang.JejuDorang.achievement.enums.AchievementStatus;
import JejuDorang.JejuDorang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final AchievementRepository achievementRepository;

    public String saveMemberByKeyCode(KakaoUserInfoDto kakaoUserInfoDto) {
        String keyCode = kakaoUserInfoDto.getId().toString();
        String name = kakaoUserInfoDto.getProperties().getNickname();

        // 이미 존재하는 member 라면 member 반환
        if (!memberRepository.findByKeyCode(keyCode).isEmpty())
            return keyCode;

        // 존재하지 않다면 DB에 정보 저장
        Member member = Member.builder()
            .keyCode(keyCode)
            .name(name)
            .build();
        memberRepository.save(member);

        return keyCode;
    }

    @Transactional(readOnly = true)
    public MemberDetailResponseDto getMemberDetail(Member member) {

        MemberDetailResponseDto memberDetailResponseDto = new MemberDetailResponseDto(member);
        List<DiaryIdDto> diaries = diaryRepository.findTop3ByMemberId(member.getId());
        List<AchievementDto> achievements = achievementRepository.findAllByMemberId(member.getId());
        memberDetailResponseDto.setAchievementsAndDiaries(achievements, diaries);
        return memberDetailResponseDto;
    }

    public List<DiaryListResponseDTO> getDiaries(Member member) {
        return diaryRepository.findAllByMemberId(member.getId());
    }

    public MyDiaryDetailResponseDto getDiaryDetail(Long diaryId, Member member) {
        return diaryRepository.findDiaryDetailByDiaryIdAndMemberId(diaryId, member.getId());
    }

    @Transactional
	public void updateDiarySecret(Long diaryId, Member member, SecretType secret) {
        int updatedRows = diaryRepository.updateDiarySecret(diaryId, member.getId(), secret);
        if (updatedRows == 0) {
            throw new NotFoundException();
        }
	}

    @Transactional
    public void deleteDiary(Long diaryId, Member member) {
        int deletedRows = diaryRepository.deleteByIdAndMember(diaryId, member);
        if (deletedRows == 0) {
            throw new NotFoundException();
        }
    }

    public AchievementListDto getAchievementList(Member member) {
        List<AchievementDto> achievements = achievementRepository.findAllByMemberId(member.getId());
        List<AchievementResponseDto> achievementResponseDtoList = new ArrayList<>();
        List<AchievementAchieveResponseDto> achievementAchieveResponseDtoList = new ArrayList<>();
        for (AchievementDto achievement : achievements) {
            if (achievement.getAchievementStatus().equals(AchievementStatus.DONE)) {
                achievementAchieveResponseDtoList.add(new AchievementAchieveResponseDto(achievement));
            } else {
                achievementResponseDtoList.add(new AchievementResponseDto(achievement));
            }
        }
		return new AchievementListDto(achievementResponseDtoList, achievementAchieveResponseDtoList);
    }

    public void updateName(String name, Member member) {
        member.updateName(name);
        memberRepository.save(member);
    }

    public void updateEmail(String memberEmail, Member member) {
        member.updateEmail(memberEmail);
        memberRepository.save(member);
    }
}

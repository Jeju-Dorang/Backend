package JejuDorang.JejuDorang.member.service;

import java.util.List;

import JejuDorang.JejuDorang.achievement.dto.AchievementDto;
import JejuDorang.JejuDorang.achievement.repository.AchievementRepository;
import JejuDorang.JejuDorang.auth.dto.KakaoUserInfoDto;
import JejuDorang.JejuDorang.diary.dto.DiaryIdDto;
import JejuDorang.JejuDorang.diary.repository.DiaryRepository;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.dto.MemberDetailResponseDto;
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
}

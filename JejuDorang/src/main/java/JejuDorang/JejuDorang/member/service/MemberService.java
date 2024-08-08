package JejuDorang.JejuDorang.member.service;

import JejuDorang.JejuDorang.auth.dto.KakaoUserInfoDto;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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
}

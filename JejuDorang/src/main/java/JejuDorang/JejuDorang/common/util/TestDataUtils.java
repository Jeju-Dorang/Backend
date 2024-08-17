package JejuDorang.JejuDorang.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDataUtils {

	private final MemberRepository memberRepository;

	public Member saveKeyCodeMember(String keyCode) {
		return memberRepository.save(Member.builder()
			.keyCode(keyCode)
			.name("test")
			.build());
	}
}

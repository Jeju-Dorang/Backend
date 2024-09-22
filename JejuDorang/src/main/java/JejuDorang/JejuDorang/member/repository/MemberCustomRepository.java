package JejuDorang.JejuDorang.member.repository;

import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.dto.MemberMainResponseDto;

@Repository
public interface MemberCustomRepository {
	MemberMainResponseDto getMainPage(Member member);
}

package JejuDorang.JejuDorang.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import JejuDorang.JejuDorang.member.data.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByKeyCode(String keyCode);
}

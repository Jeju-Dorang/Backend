package JejuDorang.JejuDorang.member.repository;

import JejuDorang.JejuDorang.member.data.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByKeyCode(String keyCode);
}

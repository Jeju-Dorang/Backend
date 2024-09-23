package JejuDorang.JejuDorang.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.member.data.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByKeyCode(String keyCode);
}

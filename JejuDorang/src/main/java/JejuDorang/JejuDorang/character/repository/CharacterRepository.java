package JejuDorang.JejuDorang.character.repository;

import JejuDorang.JejuDorang.character.data.Character;
import JejuDorang.JejuDorang.member.data.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    Character findByMember(Member member);
}

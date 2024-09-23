package JejuDorang.JejuDorang.character.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.character.data.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
}

package JejuDorang.JejuDorang.character.repository;

import JejuDorang.JejuDorang.character.data.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}

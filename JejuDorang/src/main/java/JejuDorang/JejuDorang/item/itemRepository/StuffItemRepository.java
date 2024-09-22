package JejuDorang.JejuDorang.item.itemRepository;

import JejuDorang.JejuDorang.character.data.Character;
import JejuDorang.JejuDorang.item.data.PetItem;
import JejuDorang.JejuDorang.item.data.StuffItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StuffItemRepository extends JpaRepository<StuffItem, Long> {

    List<StuffItem> findByCharacterAndGetItemTrue(Character character);
}

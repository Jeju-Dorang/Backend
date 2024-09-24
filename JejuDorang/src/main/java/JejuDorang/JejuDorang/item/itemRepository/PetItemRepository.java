package JejuDorang.JejuDorang.item.itemRepository;

import JejuDorang.JejuDorang.character.data.Character;
import JejuDorang.JejuDorang.item.data.PetItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetItemRepository extends JpaRepository<PetItem, Long> {

    List<PetItem> findByCharacterAndGetItemTrue(Character character);
    List<PetItem> findByCharacterAndGetItemFalse(Character character);
}

package JejuDorang.JejuDorang.item.itemRepository;

import JejuDorang.JejuDorang.character.data.Character;
import JejuDorang.JejuDorang.item.data.BackgroundItem;
import JejuDorang.JejuDorang.item.data.PetItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BackgroundItemRepository extends JpaRepository<BackgroundItem, Long> {

    List<BackgroundItem> findByCharacterAndGetItemTrue(Character character);
    List<BackgroundItem> findByCharacterAndGetItemFalse(Character character);
}

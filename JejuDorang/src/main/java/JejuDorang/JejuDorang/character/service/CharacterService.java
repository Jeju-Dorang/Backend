package JejuDorang.JejuDorang.character.service;

import JejuDorang.JejuDorang.character.data.Character;
import JejuDorang.JejuDorang.character.dto.ItemResponseDto;
import JejuDorang.JejuDorang.character.repository.CharacterRepository;
import JejuDorang.JejuDorang.item.data.BackgroundItem;
import JejuDorang.JejuDorang.item.data.PetItem;
import JejuDorang.JejuDorang.item.data.StuffItem;
import JejuDorang.JejuDorang.item.itemRepository.BackgroundItemRepository;
import JejuDorang.JejuDorang.item.itemRepository.PetItemRepository;
import JejuDorang.JejuDorang.item.itemRepository.StuffItemRepository;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final PetItemRepository petItemRepository;
    private final BackgroundItemRepository backgroundItemRepository;
    private final StuffItemRepository stuffItemRepository;

    public ItemResponseDto getItems(Member member) {

        // member의 character 찾아오기
        Character character = characterRepository.findByMember(member);

        // 획득한 아이템 가져오기
        List<PetItem> petItems = petItemRepository.findByCharacterAndGetItemTrue(character);
        List<BackgroundItem> backgroundItems = backgroundItemRepository.findByCharacterAndGetItemTrue(character);
        List<StuffItem> stuffItems = stuffItemRepository.findByCharacterAndGetItemTrue(character);

        // dto에 담아주기
        ItemResponseDto itemResponseDto = new ItemResponseDto();

        // PetItem에서 index 추출하여 dto에 담기
        for (PetItem petItem : petItems) {
            itemResponseDto.getPetImage().add(new ItemResponseDto.Index(petItem.getIndex()));
        }

        // BackgroundItem에서 index 추출하여 dto에 담기
        for (BackgroundItem backgroundItem : backgroundItems) {
            itemResponseDto.getBackgroundImage().add(new ItemResponseDto.Index(backgroundItem.getIndex()));
        }

        // StuffItem에서 index 추출하여 dto에 담기
        for (StuffItem stuffItem : stuffItems) {
            itemResponseDto.getItemImage().add(new ItemResponseDto.Index(stuffItem.getIndex()));
        }

        // DTO 반환
        return itemResponseDto;
    }
}

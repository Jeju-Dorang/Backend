package JejuDorang.JejuDorang.character.controller;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.character.dto.ItemRequestDto;
import JejuDorang.JejuDorang.character.dto.ItemResponseDto;
import JejuDorang.JejuDorang.character.service.CharacterService;
import JejuDorang.JejuDorang.diary.dto.DiaryDetailResponseDto;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/character")
public class CharacterController {

    private final CharacterService characterService;

    // 소유하고 있는 아이템 반환
    @GetMapping("/items")
    public ResponseEntity<ItemResponseDto> getItem(@Login Member member) {
        ItemResponseDto responseDto = characterService.getItems(member);
        return ResponseEntity.ok(responseDto);
    }

    // 아이템 장착하기
    @PostMapping("/items")
    public ResponseEntity<Void> putItem(@Login Member member, @RequestBody ItemRequestDto itemRequestDto) {
        characterService.putItem(member, itemRequestDto);
        return  ResponseEntity.ok().build();
    }
}

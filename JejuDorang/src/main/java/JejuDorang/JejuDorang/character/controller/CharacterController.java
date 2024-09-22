package JejuDorang.JejuDorang.character.controller;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.character.dto.ItemResponseDto;
import JejuDorang.JejuDorang.character.service.CharacterService;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/character")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/items")
    public ItemResponseDto getItem(@Login Member member) {
        ItemResponseDto responseDto = characterService.getItems(member);
        return responseDto;
    }
}

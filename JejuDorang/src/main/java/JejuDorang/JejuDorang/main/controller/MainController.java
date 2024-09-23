package JejuDorang.JejuDorang.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.dto.MemberMainResponseDto;
import JejuDorang.JejuDorang.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

	private final MemberService memberService;

	@GetMapping
	public ResponseEntity<MemberMainResponseDto> mainPage(@Login Member member) {
		System.out.println("Member: " + member);
		MemberMainResponseDto memberMainResponseDto = memberService.getMainPage(member);
		return ResponseEntity.ok(memberMainResponseDto);
	}
}

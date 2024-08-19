package JejuDorang.JejuDorang.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.diary.dto.DiaryListResponseDTO;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.dto.MemberDetailResponseDto;
import JejuDorang.JejuDorang.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/information")
public class MemberController {

	private final MemberService memberService;

	/**
	 * 회원 상세 정보 조회(마이 페이지)
	 * @param
	 * @return
	 */
	@GetMapping
	public ResponseEntity<MemberDetailResponseDto> getMemberDetail(@Login Member member) {

		MemberDetailResponseDto memberDetailResponseDto = memberService.getMemberDetail(member);
		return ResponseEntity.ok(memberDetailResponseDto);
	}

	@GetMapping("/diaries")
	public ResponseEntity<List<DiaryListResponseDTO>> getDiaries(@Login Member member) {
		List<DiaryListResponseDTO> diaryListResponseDTO = memberService.getDiaries(member);
		return ResponseEntity.ok(diaryListResponseDTO);
	}
}

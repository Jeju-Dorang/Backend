package JejuDorang.JejuDorang.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.diary.dto.DiaryListResponseDTO;
import JejuDorang.JejuDorang.diary.dto.MyDiaryDetailResponseDto;
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
	 * @return MemberDetailResponseDto
	 */
	@GetMapping
	public ResponseEntity<MemberDetailResponseDto> getMemberDetail(@Login Member member) {

		MemberDetailResponseDto memberDetailResponseDto = memberService.getMemberDetail(member);
		return ResponseEntity.ok(memberDetailResponseDto);
	}

	/**
	 * 회원의 다이어리 목록 조회
	 * @param
	 * @return List<DiaryListResponseDTO>
	 */
	@GetMapping("/diaries")
	public ResponseEntity<List<DiaryListResponseDTO>> getDiaries(@Login Member member) {
		List<DiaryListResponseDTO> diaryListResponseDTO = memberService.getDiaries(member);
		return ResponseEntity.ok(diaryListResponseDTO);
	}

	/**
	 * 다이어리 상세 정보 조회
	 * @param diaryId
	 * @return MyDiaryDetailResponseDto
	 */
	@GetMapping("/diary/{diaryId}")
	public ResponseEntity<MyDiaryDetailResponseDto> getDiaryDetail(@PathVariable("diaryId") Long diaryId, @Login Member member) {
		MyDiaryDetailResponseDto myDiaryDetailResponseDto = memberService.getDiaryDetail(diaryId, member);
		return ResponseEntity.ok(myDiaryDetailResponseDto);
	}
}

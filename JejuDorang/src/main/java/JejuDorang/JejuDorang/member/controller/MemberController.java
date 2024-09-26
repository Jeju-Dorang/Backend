package JejuDorang.JejuDorang.member.controller;

import java.util.List;

import JejuDorang.JejuDorang.member.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import JejuDorang.JejuDorang.achievement.dto.AchievementListDto;
import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.diary.dto.DiaryListResponseDTO;
import JejuDorang.JejuDorang.diary.dto.MyDiaryDetailResponseDto;
import JejuDorang.JejuDorang.diary.enums.SecretType;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.service.MemberService;
import jakarta.validation.Valid;
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

	/**
	 * 다이어리 비밀 여부 수정
	 * @param diaryId
	 * @param member
	 * @param secret : public, private
	 * @return ResponseEntity<Void>
	 */
	@PatchMapping("/diary/{diaryId}")
	public ResponseEntity<Void> updateDiarySecret(
		@PathVariable("diaryId") Long diaryId,
		@Login Member member,
		@RequestParam("secret") SecretType secret
	) {
		memberService.updateDiarySecret(diaryId, member, secret);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/diary/{diaryId}")
	public ResponseEntity<Void> deleteDiary(@PathVariable("diaryId") Long diaryId, @Login Member member) {
		memberService.deleteDiary(diaryId, member);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/achievements")
	public ResponseEntity<AchievementListDto> getAchievements(@Login Member member) {
		AchievementListDto achievements = memberService.getAchievementList(member);
		return ResponseEntity.ok(achievements);
	}

	@PatchMapping("/name")
	public ResponseEntity<Void> updateName(@RequestBody @Valid MemberNameDto memberNameDto, @Login Member member) {
		memberService.updateName(memberNameDto.getMemberName(), member);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/email")
	public ResponseEntity<Void> updateEmail(@RequestBody @Valid MemberEmailDto memberEmailDto, @Login Member member) {
		memberService.updateEmail(memberEmailDto.getMemberEmail(), member);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/image")
	public ResponseEntity<Void> updateImage(@RequestBody MemberImageDto memberImageDto, @Login Member member) {
		memberService.updateImage(memberImageDto.getMemberImage(), member);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/content")
	public ResponseEntity<Void> updateImage(@RequestBody MemberContentDto memberContentDto, @Login Member member) {
		memberService.updateContent(memberContentDto.getMemberContent(), member);
		return ResponseEntity.ok().build();
	}

//	@PutMapping("/lodging")
//	public ResponseEntity<Void> saveLodging(@RequestBody MemberLodgingDto memberLodgingDto, @Login Member member) {
//
//		memberService.saveLodging(memberLodgingDto, member);
//		return ResponseEntity.ok().build();
//	}
}

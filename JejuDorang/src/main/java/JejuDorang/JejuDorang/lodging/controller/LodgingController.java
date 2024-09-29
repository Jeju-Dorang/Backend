package JejuDorang.JejuDorang.lodging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.lodging.dto.LodgingDetailResponseDto;
import JejuDorang.JejuDorang.lodging.dto.LodgingRecommendResponseDto;
import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import JejuDorang.JejuDorang.lodging.service.LodgingService;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lodging")
public class LodgingController {
	private final LodgingService lodgingService;

	@Value("${tour.api.key}")
	private String serviceKey;

	@PostMapping("/saveLodgings")
	public ResponseEntity<Void> saveLodgings2() {
		lodgingService.saveLodgings(serviceKey);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/recommendations")
	public ResponseEntity<List<LodgingRecommendResponseDto>> getRecommendations(@Login Member member,
		@RequestParam("direction") LodgingDirection direction,
		@RequestParam("type") LodgingCategory category,
		@RequestParam("price") long price) {

		List<LodgingRecommendResponseDto> lodgingRecommendResponseDtoList = lodgingService.getRecommendations(member, direction, category, price);
		return ResponseEntity.ok(lodgingRecommendResponseDtoList);
	}

	@GetMapping("/recommendation/{lodgingId}")
	public ResponseEntity<LodgingDetailResponseDto> getRecommendationDetail(@PathVariable("lodgingId") long lodgingId) {
		LodgingDetailResponseDto lodgingDetailResponseDto = lodgingService.getDetail(lodgingId);
		return ResponseEntity.ok(lodgingDetailResponseDto);
	}

	@PostMapping("/{lodgingId}")
	public ResponseEntity<Void> selectLodging(@Login Member member, @PathVariable("lodgingId") long lodgingId) {
		lodgingService.selectLodging(member, lodgingId);
		return ResponseEntity.ok().build();
	}
}

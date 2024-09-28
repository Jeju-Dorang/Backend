package JejuDorang.JejuDorang.lodging.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.lodging.dto.LodgingRecommendResponseDto;
import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import JejuDorang.JejuDorang.lodging.service.LodgingService;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.tourspot.dto.TourSpotConfig;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lodging")
public class LodgingController {
	private final TourSpotConfig tourSpotConfig;
	private final LodgingService lodgingService;

	@PostMapping("/saveLodgings")
	public ResponseEntity<Void> saveLodgings() {
		lodgingService.saveLodgings(tourSpotConfig.getServiceKey());
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

	@PostMapping("/{lodgingId}")
	public ResponseEntity<Void> selectLodging(@Login Member member, @PathVariable("lodgingId") long lodgingId) {
		lodgingService.selectLodging(member, lodgingId);
		return ResponseEntity.ok().build();
	}
}

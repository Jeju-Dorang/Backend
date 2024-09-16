package JejuDorang.JejuDorang.lodging.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JejuDorang.JejuDorang.lodging.service.LodgingService;
import JejuDorang.JejuDorang.tourspot.dto.TourSpotConfig;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lodging")
public class LodgingController {
	private final TourSpotConfig tourSpotConfig;
	private final LodgingService lodgingService;

	@PostMapping("/saveLodgings")
	public void saveLodgings() {
		lodgingService.saveLodgings(tourSpotConfig.getServiceKey());
	}
}

package JejuDorang.JejuDorang.lodging.service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import JejuDorang.JejuDorang.crawling.service.CrawlingService;
import JejuDorang.JejuDorang.lodging.data.Lodging;
import JejuDorang.JejuDorang.lodging.dto.KaKaoCrawlingDto;
import JejuDorang.JejuDorang.lodging.dto.LodgingRecommendResponseDto;
import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import JejuDorang.JejuDorang.lodging.repository.LodgingRepository;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LodgingService {

	private final LodgingRepository lodgingRepository;
	private final CrawlingService crawlingService;

	@Transactional
	public void saveLodgings(String serviceKey) {
		int pageNo = 1;
		int numOfRows = 100;
		int totalCount = 0;

		RestTemplate restTemplate = new RestTemplate();
		crawlingService.initialize();
		do {
			URI uri = URI.create(
				"https://apis.data.go.kr/B551011/KorService1/searchStay1?"
					+ "numOfRows=" + numOfRows
					+ "&pageNo=" + pageNo
					+ "&MobileOS=ETC"
					+ "&MobileApp=JEJU-DORANG"
					+ "&_type=json"
					+ "&areaCode=39"
					+ "&serviceKey=" + serviceKey);

			ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
			Map<String, Object> responseBody = response.getBody();

			if (responseBody != null) {
				Map<String, Object> responseMap = (Map<String, Object>) responseBody.get("response");
				Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
				List<Map<String, Object>> items = (List<Map<String, Object>>) ((Map<String, Object>) bodyMap.get("items")).get("item");
				totalCount = (int) bodyMap.get("totalCount");

				if (items != null && !items.isEmpty()) {
					saveToDatabase(items);
				}
			}

			pageNo++;
		} while ((pageNo - 1) * numOfRows < totalCount);
		crawlingService.close();
	}

	private void saveToDatabase(List<Map<String, Object>> items) {
		for (Map<String, Object> item : items) {
			String title = removeBrackets((String) item.get("title"));
			KaKaoCrawlingDto kaKaoCrawlingDto = crawlingService.searchKaKaoMap(title);
			System.out.println("숙박 정보 : " + kaKaoCrawlingDto);
			Lodging entity = Lodging.builder()
				.address((String) item.get("addr1"))
				.name(title)
				.comment("")
				.image((String) item.get("firstimage"))
				.latitude(Double.parseDouble((String) item.get("mapy")))
				.longitude(Double.parseDouble((String) item.get("mapx")))
				.rating(kaKaoCrawlingDto.getRating())
				.direction(LodgingDirection.NORTH)
				.category(kaKaoCrawlingDto.getCategory())
				.price(0L)
				.build();
			lodgingRepository.save(entity);
		}
	}

	private String removeBrackets(String input) {
		return input.replaceAll("\\(.*?\\)|\\[.*?]", "").trim();
	}

	@Transactional(readOnly = true)
	public List<LodgingRecommendResponseDto> getRecommendations(Member member, LodgingDirection direction, LodgingCategory category, long price) {

		return lodgingRepository.findByDirectionAndCategoryAndPrice(member, direction, category, price);
	}

	@Transactional
	public void selectLodging(Member member, long lodgingId) {
		Optional<Lodging> lodging = lodgingRepository.findById(lodgingId);
		if (lodging.isPresent()) {
			member.selectLodging(lodging.get());
		} else {
			throw new IllegalArgumentException("해당 숙소가 존재하지 않습니다.");
		}
	}
}

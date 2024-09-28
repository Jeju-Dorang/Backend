package JejuDorang.JejuDorang.lodging.service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import JejuDorang.JejuDorang.crawling.dto.GoogleApiDto;
import JejuDorang.JejuDorang.crawling.service.CrawlingService;
import JejuDorang.JejuDorang.crawling.service.GoogleAPIService;
import JejuDorang.JejuDorang.lodging.data.Lodging;
import JejuDorang.JejuDorang.lodging.dto.LodgingRecommendResponseDto;
import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import JejuDorang.JejuDorang.lodging.repository.LodgingRepository;
import JejuDorang.JejuDorang.lodging.util.LodgingUtil;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LodgingService {

	private final LodgingRepository lodgingRepository;
	private final CrawlingService crawlingService;
	private final GoogleAPIService googleAPIService;

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
					+ "&serviceKey=" + "kSFk0%2FwHGpJud8smQ4oXZVHMC3%2ByyxAGrOcdpY0sIQlyPGgy2%2FEfV%2FYe63Sg%2BzYUhOzrP5V61qGDwFE6CsD8Zg%3D%3D");
						//todo service 키로 돌려놓기
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
	@Transactional
	public void saveLodgings2(String serviceKey) {
		System.out.println("serviceKey = " + serviceKey);
		int pageNo = 1;
		int numOfRows = 3;
		int totalCount = 0;

		RestTemplate restTemplate = new RestTemplate();
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
			System.out.println("숙박 이름 : " + title);
			GoogleApiDto googleApiDto = googleAPIService.getGoogleApiData(title);
			if (googleApiDto == null) {
				System.out.println("Google API에서 데이터를 가져오지 못했습니다.");
				continue;
			}
			String image = (String) item.get("firstimage");
			if (Objects.equals(image, "")) {
				image = googleApiDto.getImage();
			}
			System.out.println("숙박 정보 : " + googleApiDto);
			LodgingDirection direction = LodgingUtil.getDirectionByAddress(googleApiDto.getAddress());
			Lodging entity = Lodging.builder()
				.address((String) item.get("addr1"))
				.name(googleApiDto.getName())
				.comment("")
				.image(image)
				.latitude(Double.parseDouble((String) item.get("mapy")))
				.longitude(Double.parseDouble((String) item.get("mapx")))
				.rating(googleApiDto.getRating())
				.direction(direction)
				.category(googleApiDto.getCategory())
				.price(Long.parseLong(googleApiDto.getPrice()))
				.phoneNumber(googleApiDto.getPhoneNumber())
				.build();
			entity.setReviews(googleApiDto.getReviews());
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

package JejuDorang.JejuDorang.lodging.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import JejuDorang.JejuDorang.crawling.dto.GoogleApiDto;
import JejuDorang.JejuDorang.crawling.dto.ReviewDto;
import JejuDorang.JejuDorang.crawling.service.CrawlingService;
import JejuDorang.JejuDorang.crawling.service.GoogleAPIService;
import JejuDorang.JejuDorang.error.exception.LodgingNotFoundException;
import JejuDorang.JejuDorang.lodging.data.Lodging;
import JejuDorang.JejuDorang.lodging.dto.LodgingDetailResponseDto;
import JejuDorang.JejuDorang.lodging.dto.LodgingRecommendResponseDto;
import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import JejuDorang.JejuDorang.lodging.repository.LodgingRepository;
import JejuDorang.JejuDorang.lodging.util.LodgingUtil;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.review.data.Review;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LodgingService {

	private final LodgingRepository lodgingRepository;
	private final CrawlingService crawlingService;
	private final GoogleAPIService googleAPIService;

	@Transactional
	public void saveLodgings(String serviceKey) {
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
			throw new LodgingNotFoundException();
		}
	}

	public LodgingDetailResponseDto getDetail(long lodgingId) {
		Optional<Lodging> lodging = lodgingRepository.findById(lodgingId);
		if (lodging.isEmpty()) {
			throw new LodgingNotFoundException();
		}
		int[] stars = new int[6];
		List<ReviewDto> reviewDtos = new ArrayList<>();
		List<Review> reviews = lodging.get().getReviews();
		for (Review review : reviews) {
			ReviewDto reviewDto = ReviewDto.builder()
				.lodgingId(review.getLodging().getId())
				.name(review.getReviewerName())
				.profileUrl(review.getReviewerProfile())
				.content(review.getReviewContent())
				.relativeTimeDescription(review.getReviewDateStr())
				.rating(review.getRating())
				.time(review.getReviewDate().toInstant())
				.build();
			stars[review.getRating().intValue()]++;
			reviewDtos.add(reviewDto);
		}
		return new LodgingDetailResponseDto(lodging.get(), stars, reviewDtos);
	}
}

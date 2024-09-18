package JejuDorang.JejuDorang.lodging.service;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import JejuDorang.JejuDorang.crawling.service.CrawlingService;
import JejuDorang.JejuDorang.lodging.data.Lodging;
import JejuDorang.JejuDorang.lodging.dto.KaKaoCrawlingDto;
import JejuDorang.JejuDorang.lodging.repository.LodgingRepository;
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

			// 다음 페이지로 이동
			pageNo++;
		} while ((pageNo - 1) * numOfRows < totalCount);  // 전체 데이터를 다 가져올 때까지 반복
		// crawlingService.close();
	}

	// 데이터를 DB에 저장하는 가상의 메서드
	private void saveToDatabase(List<Map<String, Object>> items) {
		// 실제 DB 저장 로직 구현
		for (Map<String, Object> item : items) {
			String title = removeBrackets((String) item.get("title"));
			KaKaoCrawlingDto kaKaoCrawlingDto = crawlingService.searchKaKaoMap(title);
			System.out.println("숙박 정보 : " + kaKaoCrawlingDto);
			Lodging entity = Lodging.builder()
				.address((String) item.get("addr1"))
				.name(title)
				.comment("")
				.image((String) item.get("firstimage"))
				.latitude((String) item.get("mapy"))
				.longitude((String) item.get("mapx"))
				.rating(kaKaoCrawlingDto.getRating())
				.category(kaKaoCrawlingDto.getCategory())
				.build();
			lodgingRepository.save(entity);
		}
	}

	private String removeBrackets(String input) {
		return input.replaceAll("\\(.*?\\)|\\[.*?]", "").trim();
	}
}

package JejuDorang.JejuDorang.tourspot.service;

import JejuDorang.JejuDorang.tourspot.dto.TourSpotResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourSpotService {

    public List<TourSpotResponseDto> getTourSpot(String requestUrl) {

        List<TourSpotResponseDto> tourSpotResponseDtos = new ArrayList<>();

        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(requestUrl);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            // JSON 파싱을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // JSON 문자열을 JsonNode 객체로 변환
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            // body -> items
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            for (JsonNode itemNode : itemsNode) {
                // 각 항목의 필드 값 추출
                String title = itemNode.path("title").asText();
                String address = itemNode.path("addr1").asText();
                String image = itemNode.path("firstimage").asText();

                tourSpotResponseDtos.add(new TourSpotResponseDto(
                        title, address, image ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tourSpotResponseDtos;
    }
}

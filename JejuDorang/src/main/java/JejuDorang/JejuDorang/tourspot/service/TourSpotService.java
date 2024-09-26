package JejuDorang.JejuDorang.tourspot.service;

import JejuDorang.JejuDorang.achievement.data.Achievement;
import JejuDorang.JejuDorang.achievement.dto.AchievementDto;
import JejuDorang.JejuDorang.achievement.enums.AchievementStatus;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.data.MemberAchievement;
import JejuDorang.JejuDorang.member.repository.MemberAchievementRepository;
import JejuDorang.JejuDorang.tourspot.dto.TourSpotResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourSpotService {

    private final MemberAchievementRepository memberAchievementRepository;

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
                String image2 = itemNode.path("firstimage2").asText();
                Long mapX = itemNode.path("mapx").asLong();
                Long mapY = itemNode.path("mapy").asLong();

                tourSpotResponseDtos.add(new TourSpotResponseDto(
                        title, address, image, image2, mapX, mapY));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tourSpotResponseDtos;
    }

    // 도랑이 추천
    public List<AchievementDto> getAchievementRecommend(Member member) {

        List<AchievementDto> response = new ArrayList<>();

        // 아직 달성 안한 업적 가져오기
        List<MemberAchievement> memberAchievements
                = memberAchievementRepository.findByMemberAndAchievementStatus(member, AchievementStatus.YET);

        // 리스트 섞기
        Collections.shuffle(memberAchievements);

        // 리스트에서 최대 5개의 요소를 반환
        List<MemberAchievement> randomAchievement = memberAchievements
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        // DTO에 담아서 반환
        randomAchievement.forEach(memberAchievement -> {
            Achievement achievement = memberAchievement.getAchievement();

            // AchievementDto 생성
            AchievementDto achievementDto = new AchievementDto(
                    achievement.getId(),
                    achievement.getImage(),
                    achievement.getName(),
                    achievement.getContent(),
                    achievement.getMaxAchieve(),
                    memberAchievement.getAchievementCnt(),
                    AchievementStatus.YET
            );
            response.add(achievementDto);
        });
        return response;
    }
}

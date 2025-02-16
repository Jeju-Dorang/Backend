package JejuDorang.JejuDorang.tourspot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JejuDorang.JejuDorang.achievement.dto.AchievementDto;
import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.tourspot.dto.TourSpotConfig;
import JejuDorang.JejuDorang.tourspot.dto.TourSpotRequestDto;
import JejuDorang.JejuDorang.tourspot.dto.TourSpotResponseDto;
import JejuDorang.JejuDorang.tourspot.service.TourSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tourspot")
@RequiredArgsConstructor
public class TourSpotController {

    private final TourSpotService tourSpotService;

    @Autowired
    private TourSpotConfig tourSpotConfig;

    @PostMapping("/recommendation")
    public ResponseEntity<List<TourSpotResponseDto>> tourSpotRecommend
            (@RequestBody @Valid TourSpotRequestDto tourSpotRequestDto) {

        String requestUrl
                = "http://apis.data.go.kr/B551011/KorService1/locationBasedList1"
                + "?MobileOS=ETC"
                + "&MobileApp=JEJU-DORANG"
                + "&mapX=" + tourSpotRequestDto.getMapX()
                + "&mapY=" + tourSpotRequestDto.getMapY()
                + "&radius=15000"
                + "&serviceKey=" + tourSpotConfig.getServiceKey()
                + "&contentTypeId=12"
                + "&_type=json";

        List<TourSpotResponseDto> tourSpotResponseDtos = tourSpotService.getTourSpot(requestUrl);

        return ResponseEntity.ok(tourSpotResponseDtos);
    }

    @GetMapping("/dorangRecommend")
    public ResponseEntity<List<AchievementDto>> getAchievementRecommend(@Login Member member) {

        // memberAchievement에서 memberid에 해당하는 Achievement 다 가지고 옴
        // 아직 달성하지 않은 Achievement 중 랜덤으로 5개 뽑아서 반환
        List<AchievementDto> response = tourSpotService.getAchievementRecommend(member);
        return ResponseEntity.ok(response);
    }
}

package JejuDorang.JejuDorang.tourspot.controller;

import JejuDorang.JejuDorang.tourspot.dto.TourSpotConfig;
import JejuDorang.JejuDorang.tourspot.dto.TourSpotRequestDto;
import JejuDorang.JejuDorang.tourspot.service.TourSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tourspot")
@RequiredArgsConstructor
public class TourSpotController {

    private final TourSpotService tourSpotService;

    @Autowired
    private TourSpotConfig tourSpotConfig;

    @GetMapping("/recommendation")
    public ResponseEntity<Void> tourSpotRecommend
            (@RequestBody TourSpotRequestDto tourSpotRequestDto) {

        String requestUrl
                = "http://apis.data.go.kr/B551011/KorService1/locationBasedList1"
                + "?MobileOS=ETC"
                + "&MobileApp=JEJU-DORANG"
                + "&mapX=" + tourSpotRequestDto.getMapX()
                + "&mapY=" + tourSpotRequestDto.getMapY()
                + "&radius=15000"
                + "&serviceKey=" + tourSpotConfig.getServiceKey()가
                + "&_type=json";

        tourSpotService.getTourSpot(requestUrl);

        return ResponseEntity.ok().build();
    }
}

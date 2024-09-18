package JejuDorang.JejuDorang;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/auth/kakao/check")
    public String check() {
        // 정상 작동 체크
        return "정상 작동";
    }
}

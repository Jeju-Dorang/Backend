package JejuDorang.JejuDorang.auth.controller;

import JejuDorang.JejuDorang.auth.dto.KakaoConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private KakaoConfig kakaoConfig;

    @GetMapping("/kakao/login")
    public ResponseEntity<String> kakoLogin()
    {
        String kakaoLoginUrl
                = "https://kauth.kakao.com/oauth/authorize?response_type=code"
                + "&client_id=" + kakaoConfig.getClientId()
                + "&redirect_uri=" + kakaoConfig.getRedirectUri();

        // 302 : redirect
        return ResponseEntity.status(302)
                .header("Location", kakaoLoginUrl)
                .build();
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code)
    {
        return ResponseEntity.status(200)
                .header("Location", code)
                .build();
    }
}

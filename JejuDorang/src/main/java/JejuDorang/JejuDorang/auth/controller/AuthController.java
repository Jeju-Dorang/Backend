package JejuDorang.JejuDorang.auth.controller;

import JejuDorang.JejuDorang.auth.dto.KakaoConfig;
import JejuDorang.JejuDorang.auth.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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
    public ResponseEntity kakaoCallback(@RequestParam String code)
    {
        // AccessToken 받아오기
        String accessToken = authService.getAccessToken(code);

        System.out.println("accessToken: " + accessToken);


        return new ResponseEntity(HttpStatus.OK);
    }
}

package JejuDorang.JejuDorang.auth.controller;

import JejuDorang.JejuDorang.auth.dto.KakaoAccessTokenDto;
import JejuDorang.JejuDorang.auth.dto.KakaoConfig;
import JejuDorang.JejuDorang.auth.dto.KakaoUserInfoDto;
import JejuDorang.JejuDorang.auth.service.KakaoService;
import JejuDorang.JejuDorang.component.JwtTokenProvider;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.data.MemberAchievement;
import JejuDorang.JejuDorang.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private KakaoConfig kakaoConfig;

    @GetMapping ("/kakao/login")
    public ResponseEntity<String> kakaoLogin() {
        String kakaoLoginUrl
                = "https://kauth.kakao.com/oauth/authorize?response_type=code"
                + "&client_id=" + kakaoConfig.getClientId()
                + "&redirect_uri=" + kakaoConfig.getRedirectUri();

        // 302 : redirect
//        return ResponseEntity.status(302)
//                .header("Location", kakaoLoginUrl)
//                .build();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, kakaoLoginUrl)
                .build();
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity kakaoCallback(@RequestParam String code, HttpServletResponse response) {
        // AccessToken 받아오기
        KakaoAccessTokenDto accessToken = kakaoService.getAccessToken(code);
        // User 정보 받아오기
        KakaoUserInfoDto kakaoUserInfo = kakaoService.getUserProfile(accessToken);
        // DB에 member 저장
        String keyCode = memberService.saveMemberByKeyCode(kakaoUserInfo);

        // JWT 토큰 생성
        String jwtToken = jwtTokenProvider.createToken(keyCode);

        System.out.println("jwt: " + jwtToken);

        // JWT 토큰 헤더에 담아 전달
        response.setHeader("Authorization", "Bearer " + jwtToken);
        response.setHeader("keyCode", keyCode);

        return new ResponseEntity(HttpStatus.OK);
    }
}

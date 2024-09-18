package JejuDorang.JejuDorang.auth.controller;

import JejuDorang.JejuDorang.auth.dto.KakaoAccessTokenDto;
import JejuDorang.JejuDorang.auth.dto.KakaoConfig;
import JejuDorang.JejuDorang.auth.dto.KakaoUserInfoDto;
import JejuDorang.JejuDorang.auth.service.KakaoService;
import JejuDorang.JejuDorang.component.JwtTokenProvider;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.data.MemberAchievement;
import JejuDorang.JejuDorang.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/kakao/login")
    public ResponseEntity kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        // AccessToken 받아오기
        KakaoAccessTokenDto accessToken = kakaoService.getAccessToken(code);
        // User 정보 받아오기
        KakaoUserInfoDto kakaoUserInfo = kakaoService.getUserProfile(accessToken);
        // DB에 member 저장
        String keyCode = memberService.saveMemberByKeyCode(kakaoUserInfo);

        // JWT 토큰 생성
        String jwtToken = jwtTokenProvider.createAccessToken(keyCode);
        String refreshToken = jwtTokenProvider.createRefreshToken(keyCode);

        // JWT 토큰 헤더에 담아 전달
        response.setHeader("access-Token", jwtToken);
        response.setHeader("refresh-Token", refreshToken);

        return new ResponseEntity(HttpStatus.OK);
    }

    // accessToken 재발급
    @GetMapping("/token/refresh")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader("refreshToken");
        String newToken = jwtTokenProvider.refreshAccessToken(token);
        response.setHeader("access-Token", newToken);
        return new ResponseEntity(HttpStatus.OK);
    }
}

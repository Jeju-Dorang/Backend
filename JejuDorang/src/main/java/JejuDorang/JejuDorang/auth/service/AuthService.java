package JejuDorang.JejuDorang.auth.service;

import JejuDorang.JejuDorang.auth.dto.KakaoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private KakaoConfig kakaoConfig;

    public String getAccessToken(String code)
    {
        // httpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // httpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoConfig.getClientId());
        params.add("redirect_uri", kakaoConfig.getRedirectUri());
        params.add("code", code);

        // httpHeader + httpBody
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest
                = new HttpEntity<>(params, headers);

        // POST 방식으로 HTTP 요청을 하고 response에 응답 받음
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
            "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return response.getBody();
    }
}

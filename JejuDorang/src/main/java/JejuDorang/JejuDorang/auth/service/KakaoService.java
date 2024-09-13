package JejuDorang.JejuDorang.auth.service;

import JejuDorang.JejuDorang.auth.dto.KakaoAccessTokenDto;
import JejuDorang.JejuDorang.auth.dto.KakaoConfig;
import JejuDorang.JejuDorang.auth.dto.KakaoUserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class KakaoService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String client_id;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_uri;

    public KakaoAccessTokenDto getAccessToken(String code) {
        // httpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // httpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
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

        // json 데이터 object에 담기
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoAccessTokenDto accessToken = null;
        try {
            accessToken = objectMapper.readValue(response.getBody(), KakaoAccessTokenDto.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return (accessToken);
    }

    public KakaoUserInfoDto getUserProfile(KakaoAccessTokenDto accessToken) {
        // httpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // httpEntity에 header 담아주기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest
                = new HttpEntity<>(headers);

        // POST 방식으로 HTTP 요청을 하고 response에 응답 받음
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // json 데이터 object에 담기
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoUserInfoDto kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoUserInfoDto.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return (kakaoProfile);
    }
}

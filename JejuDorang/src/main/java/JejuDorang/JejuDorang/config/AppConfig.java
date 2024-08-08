package JejuDorang.JejuDorang.config;

import JejuDorang.JejuDorang.auth.dto.KakaoConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public KakaoConfig kakaoConfig() {
        Dotenv dotenv = Dotenv.load();

        KakaoConfig kakaoConfig = new KakaoConfig();
        kakaoConfig.setClientId(dotenv.get("KAKAO_REST_API_KEY"));
        kakaoConfig.setRedirectUri(dotenv.get("KAKAO_REDIRECT_URI"));

        return (kakaoConfig);
    }
}

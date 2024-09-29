package JejuDorang.JejuDorang.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import JejuDorang.JejuDorang.component.JwtAuthFilter;
import JejuDorang.JejuDorang.component.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("/auth/kakao/**").permitAll() // /auth/kakao/ 경로만 접근 허용
                        .requestMatchers("/auth/token/refresh").permitAll()
                        .requestMatchers("/lodging/saveLodgings").permitAll()
                        .anyRequest().authenticated()) // 나머지 요청은 인증 필요
//                        .anyRequest().permitAll())
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build(); // SecurityFilterChain 반환
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:5173"); // 허용할 출처 설정
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("https://jejudorang.netlify.app");
        configuration.addAllowedHeader("*"); // 허용할 헤더 설정
//        configuration.addAllowedMethod("GET", "POST", "PUT", "DELETE", "PATCH"); // 허용할 메소드 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대한 CORS 설정
        return source;
    }
}

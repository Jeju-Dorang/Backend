package JejuDorang.JejuDorang.component;

import JejuDorang.JejuDorang.member.service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Getter
    private final String secretKey = generateSecretKey();

    // 비밀 키 생성 메서드
    private String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256 bits
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    // AccessToken 생성 (암호화)
    public String createAccessToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        long validityInMilliseconds = 30 * 60 * 1000L; // 30분

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + validityInMilliseconds))
            .signWith(SignatureAlgorithm.HS256, secretKey) // 비밀 키와 서명 알고리즘 사용
            .compact();
    }

    // RefreshToken 생성
    public String createRefreshToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        long validityInMilliseconds = 7 * 24 * 60 * 60 * 1000L; // 일주일

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 비밀 키와 서명 알고리즘 사용
                .compact();
    }

    // Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        return token;
    }

    // 토큰에서 회원 정보 추출 (복호화)
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 인증 정보 조회
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰 유효성, 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다: " + e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            System.out.println("서명이 유효하지 않습니다: " + e.getMessage());
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.out.println("잘못된 JWT 형식입니다: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("토큰이 비어있거나 잘못된 형식입니다: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다: " + e.getMessage());
        }
        return false;
    }

    // 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
    public String refreshAccessToken(String refreshToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);

            // 리프레시 토큰의 유효성 검사
            if (claims.getBody().getExpiration().before(new Date())) {
                throw new IllegalArgumentException("리프레시 토큰이 만료되었습니다.");
            }

            // 사용자 PK 추출
            String userPk = claims.getBody().getSubject();

            // 새로운 액세스 토큰 생성
            return createAccessToken(userPk);

        } catch (Exception e) {
            throw new RuntimeException("리프레시 토큰을 사용하여 액세스 토큰을 갱신하는 도중 오류가 발생했습니다.", e);
        }
    }
}

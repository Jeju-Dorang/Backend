package JejuDorang.JejuDorang.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 클라이언트의 API 요청 헤더에서 토큰 추출
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        // 유효성 검사 후 SecurityContext에 저장
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // accessToken 만료된 경우
            String refreshToken = httpServletRequest.getHeader("Refresh-Token");
            if (refreshToken != null) {
                try {
                    String newAccessToken = jwtTokenProvider.refreshAccessToken(refreshToken);
                    httpServletResponse.setHeader("New-Access-Token", newAccessToken);

                    Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } catch (Exception e) {
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다.");
                    return;
                }
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "리프레시 토큰이 필요합니다.");
                return;
            }
        }

        // 다음 필터링
        chain.doFilter(request, response);
    }
}

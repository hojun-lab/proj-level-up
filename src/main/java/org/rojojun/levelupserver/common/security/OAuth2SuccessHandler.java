package org.rojojun.levelupserver.common.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    //    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//        String userId = oAuth2User.getName();
//        String token = jwtProvider.create(userId, "ROLE_MEMBER");
//
//        response.sendRedirect("http://localhost:5173/auth/oauth-response/" + token + "/3600");
//    }
    @Value("${oauth.success-redirect-uri}")
    private String targetUrlBase; // 프론트엔드 리디렉션 기본 URL

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        // Principal 객체에서 OAuth2User 정보 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // CustomOAuth2UserService에서 저장한 사용자 email 가져오기
        // 주의: attributes 맵에 email이 있다는 가정 하에 동작
        String email = (String) oAuth2User.getAttributes().get("email");
        String role = oAuth2User.getAuthorities().stream()
                .findFirst() // 첫 번째 권한 사용 (CustomOAuth2UserService에서 설정한대로)
                .orElseThrow(() -> new IllegalStateException("No authorities found"))
                .getAuthority(); // 예: "ROLE_USER"

        log.info("Authentication successful for user: {}, role: {}", email, role);

        // JWT 생성
        String token = jwtProvider.create(email, role);
        log.debug("Generated JWT: {}", token); // 실제 운영에서는 로그 출력 주의

        // 리디렉션 URL 생성 (프론트엔드 URL + JWT 토큰)
        String targetUrl = UriComponentsBuilder.fromUriString(targetUrlBase)
                .queryParam("token", token) // URL 파라미터로 토큰 전달
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        log.info("Redirecting to target URL: {}", targetUrl);

        // 클라이언트 리디렉션
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
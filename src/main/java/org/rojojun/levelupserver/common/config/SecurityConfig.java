package org.rojojun.levelupserver.common.config;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.common.security.CustomOAuth2UserService; // 수정: 커스텀 서비스 임포트
import org.rojojun.levelupserver.common.security.JwtAuthenticationFilter;
import org.rojojun.levelupserver.common.security.OAuth2SuccessHandler; // 수정: 커스텀 핸들러 임포트
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService; // 수정: 기본 서비스 임포트 제거
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
// @Configurable // @Configurable은 일반적으로 AOP 관련 어노테이션이며, 설정 클래스에는 보통 불필요합니다. 제거해도 좋습니다.
@Configuration
public class SecurityConfig {

    // 수정: 주입 타입을 커스텀 구현체로 변경
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(CsrfConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        // 수정: /** 는 너무 광범위하므로 필요한 경로만 permitAll 권장
                        .requestMatchers("/", "/login", "/token", "/user/register", "/auth/**", "/check", "/favicon.ico", "/error", "/default-ui.css", "/login/success").permitAll() // 프론트 리디렉션 경로 추가
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        // .authorizationEndpoint(endpoint -> endpoint.baseUri("/auth/oauth2")) // 기본값 사용 권장
                        // .redirectionEndpoint(endpoint -> endpoint.baseUri("/login/oauth2/code")) // 수정: 기본값 사용 권장 (문제가 없다면 명시 불필요)
                        .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService)) // 수정: 커스텀 서비스 사용
                        .successHandler(oAuth2SuccessHandler) // 수정: 커스텀 핸들러 사용
                )
                // ... (나머지 설정 유지) ...
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 수정: 프로덕션에서는 "*" 대신 실제 프론트엔드 도메인 지정 권장
        configuration.addAllowedOrigin("http://localhost:3000"); // 예시: Next.js 개발 서버 주소
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
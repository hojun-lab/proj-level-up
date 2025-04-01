package org.rojojun.levelupserver.common.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rojojun.levelupserver.adapter.out.repository.MemberRepository;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("OAuth2UserRequest received: {}", userRequest);

        OAuth2User oAuth2User = super.loadUser(userRequest); // Google 로부터 사용자 정보 가져오기

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "google"
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // "sub" 또는 "email" 등

        Map<String, Object> attributes = oAuth2User.getAttributes(); // Google 사용자 정보 (sub, name, email 등)

        log.debug("OAuth User Attributes: {}", attributes);

        // --- 사용자 정보 추출 ---
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String providerId = (String) attributes.get("sub"); // Google의 고유 식별자

        // --- DB 조회 및 처리 ---
        Optional<Member> userOptional = memberRepository.findMemberByNickname(email);
        Member member;

        if (userOptional.isPresent()) {
            // 이미 가입된 회원
            member = userOptional.get();
            // 필요시 이름 업데이트 등 추가 로직
            member.updateName(name); // 예시: 이름이 변경되었을 수 있으니 업데이트
            log.info("Existing user found: {}", email);
        } else {
            // 최초 로그인 (신규 회원)
            member = Member.of(email, name);
            memberRepository.save(member);
            log.info("New user registered: {}", email);
        }

        // Spring Security Context에 저장될 사용자 정보 반환
        // 여기서 반환된 정보는 OAuth2SuccessHandler 에서 사용됨
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())), // 권한 설정
                attributes, // Google 원본 속성
                userNameAttributeName // 이름 속성 키
        );

        // 주의: DefaultOAuth2User 대신 커스텀 UserDetails 구현체를 반환할 수도 있음
        // 이 경우, Principal 객체 타입이 달라짐
    }
}
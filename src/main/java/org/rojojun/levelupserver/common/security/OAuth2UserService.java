package org.rojojun.levelupserver.common.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.member.service.MemberService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String oauthClientName = userRequest.getClientRegistration().getRegistrationId();

        log.info("getAttributes : {}",oAuth2User.getAttributes());

        try {
            log.info("{}", new ObjectMapper().writeValueAsBytes(oAuth2User.getAttributes()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String userId = null;

        if (oauthClientName.equals("google")) {
            userId = "google_" + oAuth2User.getAttributes().get("email");

            memberService.findMemberBy(userId)
                    .orElseGet(() -> createOAuthUser(oAuth2User));
        }

        return new CustomOAuth2User(userId);
    }

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        String oauthClientName = userRequest.getClientRegistration().getRegistrationId();
//
//        try {
//            log.info("{}", new ObjectMapper().writeValueAsBytes(oAuth2User.getAttributes()));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        String userId = null;
//
//        if (oauthClientName.equals("facebook")) {
//            userId = "facebook_" + oAuth2User.getAttributes().get("email");
//
//            memberService.findMemberBy(userId)
//                    .orElseGet(() -> createOAuthUser(oAuth2User));
//        }
//
//        return new CustomOAuth2User(userId);
//    }
//
    private Member createOAuthUser(OAuth2User oAuth2User) {
        Member facebookLoginUser = Member.of(
                "google_" + oAuth2User.getAttributes().get("email"),
                UUID.randomUUID().toString(),
                oAuth2User.getAttributes().get("name").toString(),
                ""
        );
        return memberService.save(facebookLoginUser);
    }

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        // OAuth2 로그인한 Provider (Instagram인지 확인)
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//
//        if ("instagram".equals(registrationId)) {
//            return processInstagramOAuthUser(oAuth2User);
//        }
//
//        return oAuth2User;
//    }
//
//    private OAuth2User processInstagramOAuthUser(OAuth2User oAuth2User) {
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        String instagramId = (String) attributes.get("id");
//        String username = (String) attributes.get("username");
//        String profilePicture = (String) attributes.get("profile_picture");
//
//        Member member = memberService.findMemberBy(instagramId)
//                .orElseGet(() -> registerNewUser(instagramId, username, profilePicture));
//
//        return new CustomOAuth2User(member.getEmail());
//    }
//
//    private Member registerNewUser(String instagramId, String username, String profilePicture) {
//        Member member = Member.of(
//                        instagramId,
//                        UUID.randomUUID().toString(),
//                        username,
//                        profilePicture
//                );
//
//        return memberService.save(member);
//    }
}

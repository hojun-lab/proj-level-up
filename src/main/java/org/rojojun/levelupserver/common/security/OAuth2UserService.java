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

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String oauthClientName = userRequest.getClientRegistration().getRegistrationId();

        try {
            log.info("{}", new ObjectMapper().writeValueAsBytes(oAuth2User.getAttributes()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String userId = null;

        if (oauthClientName.equals("facebook")) {
            userId = "facebook_" + oAuth2User.getAttributes().get("email");

            memberService.findMemberBy(userId)
                    .orElseGet(() -> createOAuthUser(oAuth2User));
        }

        return new CustomOAuth2User(userId);
    }

    private Member createOAuthUser(OAuth2User oAuth2User) {
        Member facebookLoginUser = Member.of(
                "facebook_" + oAuth2User.getAttributes().get("email"),
                "facebookUser",
                oAuth2User.getAttributes().get("name").toString()
        );
        return memberService.save(facebookLoginUser);
    }
}

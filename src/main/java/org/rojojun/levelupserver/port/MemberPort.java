package org.rojojun.levelupserver.port;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.in.dto.LoginDto;
import org.rojojun.levelupserver.adapter.in.dto.SignUpDto;
import org.rojojun.levelupserver.adapter.out.dto.MyPageResponseDto;
import org.rojojun.levelupserver.adapter.out.dto.TokenDto;
import org.rojojun.levelupserver.adapter.out.dto.UserInfoResponseDto;
import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;
import org.rojojun.levelupserver.common.security.JwtProvider;
import org.rojojun.levelupserver.domain.board.service.BoardService;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.member.entity.MemberEstimate;
import org.rojojun.levelupserver.domain.member.service.MemberEstimateService;
import org.rojojun.levelupserver.domain.member.service.MemberService;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;
import org.rojojun.levelupserver.domain.skill.service.SkillEstimateService;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberPort {
    private final MemberService memberService;
    private final MemberEstimateService memberEstimateService;
    private final BoardService boardService;
    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Long signUp(SignUpDto signUpDto) {
        Member member = Member.of(signUpDto.nickname(), passwordEncoder.encode(signUpDto.password()));
        return memberService.save(member).getId();
    }

    public TokenDto login(LoginDto loginDto) {
        String token = null;

        try {
            String email = loginDto.nickname();
            Member user = memberService.findMemberBy(email)
                    .orElseThrow(IllegalArgumentException::new);
            boolean isMatch = passwordEncoder.matches(loginDto.password(), user.getPassword());

            token = jwtProvider.create(email, user.getRole().name());
            if (!isMatch) {
                throw new AuthorizationDeniedException("비밀번호가 일치하지 않습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new TokenDto(null);
        }
        return new TokenDto(token);
    }

    public void modifyNickname(String email, String nickname) {
        Member member = memberService.findMemberBy(email)
                .orElseThrow();

        member.modifyNickname(nickname);
    }

    public UserInfoResponseDto getUserInfo(Long userId) {
        Member member = memberService.findMemberBy(userId);
        List<MemberEstimate> memberEstimates = memberEstimateService.findAllBy(member);

        int averageScore = (int) memberEstimates.stream()
                .mapToInt(MemberEstimate::getScore)
                .average()
                .orElse(0);

        return new UserInfoResponseDto(member.getNickname(), UserLevel.calculate(averageScore));
    }

    public MyPageResponseDto getMyPageInfo(String email) {
        Member member = memberService.findMemberBy(email)
                .orElseThrow();
        List<MemberEstimate> memberEstimates = memberEstimateService.findAllBy(member);

        int averageScore = (int) memberEstimates.stream()
                .mapToInt(MemberEstimate::getScore)
                .average()
                .orElse(0);

        int totalPost = boardService.findAllBy(email).size();

        return new MyPageResponseDto(member, UserLevel.calculate(averageScore), totalPost);
    }

    public boolean idCheck(String nickname) {
        return memberService.findMemberBy(nickname).isEmpty();
    }
}

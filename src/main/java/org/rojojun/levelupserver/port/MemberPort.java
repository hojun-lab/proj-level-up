package org.rojojun.levelupserver.port;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.dto.MyPageResponseDto;
import org.rojojun.levelupserver.adapter.out.dto.UserInfoResponseDto;
import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;
import org.rojojun.levelupserver.domain.board.service.BoardService;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.member.entity.MemberEstimate;
import org.rojojun.levelupserver.domain.member.service.MemberEstimateService;
import org.rojojun.levelupserver.domain.member.service.MemberService;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;
import org.rojojun.levelupserver.domain.skill.service.SkillEstimateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberPort {
    private final MemberService memberService;
    private final MemberEstimateService memberEstimateService;
    private final SkillEstimateService skillEstimateService;
    private final BoardService boardService;

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
}

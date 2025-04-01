package org.rojojun.levelupserver.adapter.out.dto;

import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.rojojun.levelupserver.domain.skill.entity.SkillDifficulty;

import java.util.List;
import java.util.Map;

public record MyPageResponseDto(
        String nickname,
        String profilePicture,
        UserLevel userLevel,
        Integer totalPostCount
) {
     public MyPageResponseDto(Member member, UserLevel userLevel, int totalPostCount) {
          this(member.getNickname(), member.getProfilePicture(), userLevel, totalPostCount);
     }
}

package org.rojojun.levelupserver.adapter.out.dto;

import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;
import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.rojojun.levelupserver.domain.skill.entity.SkillDifficulty;

import java.util.List;
import java.util.Map;

public record MyPageResponseDto(
        String nickname,
        UserLevel userLevel,
        List<SkillDetailDto> skillDetailDtoList
) {
     public record SkillDetailDto(
             String name,
             SkillDifficulty difficulty,
             int accuracy
     ) {
          public SkillDetailDto(Skill skill, int score) {
               this(skill.getName(), skill.getDifficulty(), score);
          }
     }
}

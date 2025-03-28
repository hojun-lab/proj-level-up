package org.rojojun.levelupserver.adapter.out.dto;

import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.rojojun.levelupserver.domain.skill.entity.SkillDifficulty;

import java.time.LocalDateTime;

public record SkillResponseDto(
        Long skillId,
        String name,
        SkillDifficulty difficulty,
        int score
) {
    public SkillResponseDto(Skill skill, Double score) {
        this(skill.getId(), skill.getName(), skill.getDifficulty(), score.intValue());
    }
}

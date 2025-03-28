package org.rojojun.levelupserver.adapter.in.dto;

import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.rojojun.levelupserver.domain.skill.entity.SkillDifficulty;

public record SkillRequestDto(
        String skillName,
        int score,
        SkillDifficulty skillDifficulty
) {
    public Skill toEntity() {
        return Skill.of(skillName, skillDifficulty);
    }
}

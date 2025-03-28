package org.rojojun.levelupserver.adapter.out;

import org.rojojun.levelupserver.domain.skill.entity.SkillDifficulty;

import java.time.LocalDateTime;

public record SkillEvaluateResponseDto(
        String email,
        String writer,
        String skillName,
        SkillDifficulty skillDifficulty,
        int score,
        LocalDateTime createdAt
) {
}

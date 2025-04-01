package org.rojojun.levelupserver.adapter.out.dto;

import org.rojojun.levelupserver.domain.skill.entity.SkillDifficulty;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;

import java.time.LocalDateTime;

public record SkillEvaluateResponseDto(
        String writer,
        int score,
        LocalDateTime createdAt
) {
    public SkillEvaluateResponseDto(SkillEstimate skillEstimate) {
        this(
                skillEstimate.getEstimator().getNickname(),
                skillEstimate.getScore(),
                skillEstimate.getCreatedAt()
        );
    }
}

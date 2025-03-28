package org.rojojun.levelupserver.adapter.out.dto;

import org.rojojun.levelupserver.domain.skill.entity.SkillDifficulty;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record SkillDetailDto(
        String name,
        SkillDifficulty difficulty,
        LocalDateTime createdAt,
        int score,
        List<SkillEvaluateResponseDto> skillEvaluatieList
) {
     public SkillDetailDto(List<SkillEstimate> skillEstimateList) {
         this(
                 skillEstimateList.getFirst().getSkill().getName(),
                 skillEstimateList.getFirst().getSkill().getDifficulty(),
                 skillEstimateList.getFirst().getCreatedAt(),
                 (int) skillEstimateList.stream().mapToDouble(SkillEstimate::getScore).average()
                         .orElse(0),
                 skillEstimateList.stream().map(SkillEvaluateResponseDto::new)
                         .toList()
         );
    }
}

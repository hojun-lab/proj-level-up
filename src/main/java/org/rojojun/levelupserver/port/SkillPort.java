package org.rojojun.levelupserver.port;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.dto.SkillDetailDto;
import org.rojojun.levelupserver.adapter.out.dto.SkillResponseDto;
import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;
import org.rojojun.levelupserver.domain.skill.service.SkillEstimateService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SkillPort {
    private final SkillEstimateService skillEstimateService;

    public List<SkillResponseDto> findAllBy(String email) {
        List<SkillEstimate> skillEstimateList = skillEstimateService.getAllBy(email);

        Map<Skill, Double> skillAverageMap = skillEstimateList.stream().collect(
                Collectors.groupingBy(
                        SkillEstimate::getSkill,
                        Collectors.averagingInt(SkillEstimate::getScore)
                )
        );

        return skillAverageMap.keySet().stream()
                .map(skill -> new SkillResponseDto(skill, skillAverageMap.get(skill)))
                .toList();
    }

    public SkillDetailDto findById(String email, Long skillId) {
        List<SkillEstimate> skillList = skillEstimateService.findAllBy(email, skillId);
        return new SkillDetailDto(skillList);
    }
}

package org.rojojun.levelupserver.domain.skill.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Setter
@Getter
public class SkillScoreMeta {
    private long beginner;
    private long intermediate;
    private long expert;
    private String result;

    public void increaseCount(String value) {
        SkillScoreField field = SkillScoreField.fromString(value);
        if (field != null) {
            field.increase(this);
        } else {
            log.error("Invalid field: {}", value);
        }
    }

    public void increaseBeginner() { beginner++; }
    public void increaseIntermediate() { intermediate++; }
    public void increaseExpert() { expert++; }

    public String getHighestSkillLevel() {
        Map<String, Long> scores = Map.of(
                "beginner", beginner,
                "intermediate", intermediate,
                "expert", expert
        );

        long maxScore = Collections.max(scores.values());

        return scores.entrySet().stream()
                .filter(entry -> entry.getValue() == maxScore)
                .map(Map.Entry::getKey)
                .toList()
                .getFirst();
    }
}

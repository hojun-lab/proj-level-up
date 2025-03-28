package org.rojojun.levelupserver.domain.skill.entity;

import java.util.function.Consumer;

public enum SkillScoreField {
    BEGINNER(SkillScoreMeta::increaseBeginner),
    INTERMEDIATE(SkillScoreMeta::increaseIntermediate),
    EXPERT(SkillScoreMeta::increaseExpert),
    AVERAGE_SCORE(SkillScoreMeta::increaseAverageScore),
    LAST_SCORE(SkillScoreMeta::increaseLastScore);

    private final Consumer<SkillScoreMeta> updater;

    SkillScoreField(Consumer<SkillScoreMeta> updater) {
        this.updater = updater;
    }

    public void increase(SkillScoreMeta meta) {
        updater.accept(meta);
    }

    public static SkillScoreField fromString(String value) {
        try {
            return SkillScoreField.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

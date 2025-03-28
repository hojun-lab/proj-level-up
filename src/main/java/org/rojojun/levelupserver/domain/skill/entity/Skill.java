package org.rojojun.levelupserver.domain.skill;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.rojojun.levelupserver.common.BaseEntity;


@Entity
public class Skill extends BaseEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private SkillDifficulty difficulty;
}

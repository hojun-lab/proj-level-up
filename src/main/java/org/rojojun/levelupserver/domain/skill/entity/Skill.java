package org.rojojun.levelupserver.domain.skill.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.rojojun.levelupserver.common.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Skill extends BaseEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private SkillDifficulty difficulty;

    @Type(JsonType.class)
    @Column(columnDefinition = "JSON")
    private SkillScoreMeta skillScoreMeta;

    public static Skill of(String name, SkillDifficulty difficulty) {
        return new Skill(name, difficulty, null);
    }

    public void updateSkillDifficulty(SkillDifficulty skillDifficulty) {
        this.difficulty = skillDifficulty;
    }

    public void updateMetaData(SkillScoreMeta skillScoreMeta) {
        this.skillScoreMeta = skillScoreMeta;
    }
}

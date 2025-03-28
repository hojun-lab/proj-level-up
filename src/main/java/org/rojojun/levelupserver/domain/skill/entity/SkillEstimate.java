package org.rojojun.levelupserver.domain.skill;

import jakarta.persistence.*;
import org.rojojun.levelupserver.common.BaseEntity;
import org.rojojun.levelupserver.domain.member.entity.Member;

@Entity
public class SkillEstimate extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", foreignKey = @ForeignKey(name = "FK_SKILL_ESTIMATE"))
    private Skill skill;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "estimator_id", foreignKey = @ForeignKey(name = "FK_ESTIMATOR"))
    private Member estimator;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "estimatee_id", foreignKey = @ForeignKey(name = "FK_ESTIMATEE"))
    private Member estimatee;

    private int score;
}

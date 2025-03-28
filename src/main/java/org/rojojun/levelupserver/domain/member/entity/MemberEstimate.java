package org.rojojun.levelupserver.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rojojun.levelupserver.common.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class MemberEstimate extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "estimatee_id", foreignKey = @ForeignKey(name = "FK_MEMBER_ESTIMATEE"))
    private Member evaluatee;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "estimator_id", foreignKey = @ForeignKey(name = "FK_MEMBER_ESTIMATOR"))
    private Member evaluator;

    private int score;
}

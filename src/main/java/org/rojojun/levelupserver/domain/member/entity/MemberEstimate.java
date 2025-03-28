package org.rojojun.levelupserver.domain.member;

import org.rojojun.levelupserver.common.BaseEntity;

public class MemberEstimate extends BaseEntity {
    private Member evaluatee;
    private Member evaluator;
    private int score;
}

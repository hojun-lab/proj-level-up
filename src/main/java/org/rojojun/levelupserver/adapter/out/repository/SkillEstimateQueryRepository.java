package org.rojojun.levelupserver.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static org.rojojun.levelupserver.domain.skill.entity.QSkillEstimate.skillEstimate;

@RequiredArgsConstructor
@Repository
public class SkillEstimateQueryRepository {
    private final JPAQueryFactory queryFactory;

    public int countAllGroupBy(String nickname) {
        return queryFactory.select(skillEstimate.skill)
                .from(skillEstimate)
                .where(skillEstimate.estimatee.nickname.eq(nickname))
                .groupBy(skillEstimate.skill)
                .fetch()
                .size();
    }
}

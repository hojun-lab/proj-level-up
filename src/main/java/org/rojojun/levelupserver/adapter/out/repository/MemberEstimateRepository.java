package org.rojojun.levelupserver.adapter.out.repository;

import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.member.entity.MemberEstimate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberEstimateRepository extends JpaRepository<MemberEstimate, Long> {
    Optional<List<MemberEstimate>> findAllByEvaluatee(Member member);
}

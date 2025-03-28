package org.rojojun.levelupserver.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.repository.MemberEstimateRepository;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.member.entity.MemberEstimate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberEstimateService {
    private final MemberEstimateRepository memberEstimateRepository;

    public List<MemberEstimate> findAllBy(Member member) {
        return memberEstimateRepository.findAllByEvaluatee(member)
                .orElseThrow();
    }
}

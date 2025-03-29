package org.rojojun.levelupserver.domain.skill.service;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.dto.SkillResponseDto;
import org.rojojun.levelupserver.adapter.out.repository.SkillEstimateRepository;
import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SkillEstimateService {
    private final SkillEstimateRepository skillEstimateRepository;

    public List<SkillEstimate> getAllBy(String email) {
        return skillEstimateRepository.findAllByEstimatee_Email(email)
                .orElseThrow();
    }

    public List<SkillEstimate> findAllBy(String email, Long skillId) {
        return skillEstimateRepository.findAllByEstimatee_EmailAndSkill_Id(email, skillId)
                .orElseThrow();
    }

    public SkillEstimate save(SkillEstimate skillEstimate) {
        return skillEstimateRepository.save(skillEstimate);
    }

    public SkillEstimate findBy(Reply reply, String email) {
        return skillEstimateRepository.findByReplyAndEstimator_Email(reply, email)
                .orElseThrow();
    }

    public SkillEstimate findBy(Board board, String email) {
        return skillEstimateRepository.findByBoardAndEstimator_Email(board, email)
                .orElseThrow();
    }
}

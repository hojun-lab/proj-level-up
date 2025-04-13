package org.rojojun.levelupserver.domain.skill.service;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.repository.SkillEstimateQueryRepository;
import org.rojojun.levelupserver.adapter.out.repository.SkillEstimateRepository;
import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SkillEstimateService {
    private final SkillEstimateRepository skillEstimateRepository;
    private final SkillEstimateQueryRepository skillEstimateQueryRepository;

    public List<SkillEstimate> getAllBy(String nickname) {
        return skillEstimateRepository.findAllByEstimatee_Nickname(nickname)
                .orElseThrow();
    }

    public int countAllGroupBy(String nickname) {
        return skillEstimateQueryRepository.countAllGroupBy(nickname);
    }

    public List<SkillEstimate> findAllBy(String nickname, Long skillId) {
        return skillEstimateRepository.findAllByEstimatee_NicknameAndSkill_Id(nickname, skillId)
                .orElseThrow();
    }

    public SkillEstimate save(SkillEstimate skillEstimate) {
        return skillEstimateRepository.save(skillEstimate);
    }

    public SkillEstimate findBy(Reply reply, String nickname) {
        return skillEstimateRepository.findByReplyAndEstimator_Nickname(reply, nickname)
                .orElseThrow();
    }

    public SkillEstimate findBy(Board board, String nickname) {
        return skillEstimateRepository.findByBoardAndEstimator_Nickname(board, nickname)
                .orElseThrow();
    }
}

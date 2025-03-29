package org.rojojun.levelupserver.adapter.out.repository;

import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillEstimateRepository extends JpaRepository<SkillEstimate, Long> {
    Optional<List<SkillEstimate>> findAllByEstimatee_Email(String email);
    Optional<List<SkillEstimate>> findAllByEstimatee_EmailAndSkill_Id (String email, Long skillId);
    Optional<SkillEstimate> findByReplyAndEstimator_Email(Reply reply, String email);
    Optional<SkillEstimate> findByBoardAndEstimator_Email(Board board, String email);
}

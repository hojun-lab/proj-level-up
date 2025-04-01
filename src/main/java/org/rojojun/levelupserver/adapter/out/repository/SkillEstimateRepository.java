package org.rojojun.levelupserver.adapter.out.repository;

import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillEstimateRepository extends JpaRepository<SkillEstimate, Long> {
    Optional<List<SkillEstimate>> findAllByEstimatee_Nickname(String nickname);
    Optional<List<SkillEstimate>> findAllByEstimatee_NicknameAndSkill_Id (String nickname, Long skillId);
    Optional<SkillEstimate> findByReplyAndEstimator_Nickname(Reply reply, String nickname);
    Optional<SkillEstimate> findByBoardAndEstimator_Nickname(Board board, String nickname);
}

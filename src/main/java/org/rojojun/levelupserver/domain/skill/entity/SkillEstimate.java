package org.rojojun.levelupserver.domain.skill.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rojojun.levelupserver.common.BaseEntity;
import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.rojojun.levelupserver.domain.member.entity.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id",foreignKey = @ForeignKey(name = "FK_SKILL_ESTIMATE_BOARD"))
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id",foreignKey = @ForeignKey(name = "FK_SKILL_ESTIMATE_REPLY"))
    private Reply reply;

    private int score;

    public static SkillEstimate of(Skill skill, Member estimator, Member estimatee, Board board, Reply reply, int score) {
        return new SkillEstimate(skill, estimator, estimatee, board, reply, score);
    }

    public static SkillEstimate ofMyself(Skill skill, Member estimator, Member estimatee, Board board, int score) {
        return new SkillEstimate(skill, estimator, estimatee, board, null, score);
    }

    public void modify(Integer score) {
        this.score = score;
    }
}

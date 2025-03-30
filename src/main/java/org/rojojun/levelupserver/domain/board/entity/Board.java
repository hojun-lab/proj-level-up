package org.rojojun.levelupserver.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rojojun.levelupserver.common.BaseEntity;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.skill.entity.Skill;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Board extends BaseEntity {
    private String content;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    private int viewCount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", foreignKey = @ForeignKey(name = "" +
            "FK_BOARD_WRITER"))
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", foreignKey = @ForeignKey(name = "FK_BOARD_SKILL"))
    private Skill skill;

    public static Board of(String content, Member member, Skill skill) {
        return new Board(content, BoardStatus.USED, 0, member, skill);
    }

    public void increaseView() {
        this.viewCount++;
    }

    public void delete() {
        this.boardStatus = BoardStatus.DELETED;
    }

    public void modifyContent(String content) {
        this.content = content;
    }
}

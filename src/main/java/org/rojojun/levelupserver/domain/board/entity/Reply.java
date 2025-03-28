package org.rojojun.levelupserver.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rojojun.levelupserver.common.BaseEntity;
import org.rojojun.levelupserver.domain.member.entity.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Reply extends BaseEntity {
    private String content;
    @Enumerated(EnumType.STRING)
    private BoardStatus status;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", foreignKey = @ForeignKey(name = "FK_REPLY_WRITER"))
    private Member writer;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "FK_REPLY_BOARD"))
    private Board board;

    public static Reply of(String content, Member writer, Board board) {
        return new Reply(content, BoardStatus.USED, writer, board);
    }

    public void modify(String content) {
        this.content = content;
    }

    public void delete() {
        this.status = BoardStatus.DELETED;
    }
}

package org.rojojun.levelupserver.adapter.in.dto;

import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.rojojun.levelupserver.domain.member.entity.Member;

public record ReplyRequestDto(
        String content,
        Integer score
) {
    public Reply toEntity(Member writer, Board board) {
        return Reply.of(content, writer, board);
    }
}

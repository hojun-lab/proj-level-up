package org.rojojun.levelupserver.adapter.out.dto;

import org.rojojun.levelupserver.domain.board.entity.Reply;

import java.time.LocalDateTime;

public record ReplyDto(
        String writer,
        String content,
        LocalDateTime createdAt
) {
    public ReplyDto(Reply reply) {
        this(reply.getWriter().getNickname(), reply.getContent(), reply.getCreatedAt());
    }
}

package org.rojojun.levelupserver.adapter.out.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BoardDetailDto(
        String content,
        String videoUrl,
        int viewCount,
        int videoViewCount,
        LocalDateTime createdAt,
        List<ReplyDto> replyList
) {
}

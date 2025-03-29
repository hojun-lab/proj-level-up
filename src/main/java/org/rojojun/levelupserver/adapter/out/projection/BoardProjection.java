package org.rojojun.levelupserver.adapter.out.projection;

import java.time.LocalDateTime;

public record BoardProjection(
        String writerName,
        String writerPicture,
        String videoUrl,
        String content,
        LocalDateTime createdAt,
        int replyCount
) {
}

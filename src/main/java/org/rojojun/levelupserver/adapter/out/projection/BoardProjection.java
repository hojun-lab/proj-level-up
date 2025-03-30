package org.rojojun.levelupserver.adapter.out.projection;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class BoardProjection{
        private String writerName;
        private String writerPicture;
        private String videoUrl;
        private String content;
        private LocalDateTime createdAt;
        private Long     replyCount;
}

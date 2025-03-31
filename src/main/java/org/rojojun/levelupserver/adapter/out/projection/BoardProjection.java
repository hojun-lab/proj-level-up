package org.rojojun.levelupserver.adapter.out.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardProjection{
        private Long id;
        private String username;
        private String writerPicture;
        private String videoUrl;
        private String content;
        private LocalDateTime date;
        private Long commentCount;
}

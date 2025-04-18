package org.rojojun.levelupserver.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rojojun.levelupserver.common.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Video extends BaseEntity {
    private String videoUrl;
    private String thumbnailUrl;
    private int viewCount;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "FK_VIDEO_BOARD"))
    private Board board;

    public static Video of(String videoUrl, String thumbnailUrl, Board board) {
        return new Video(videoUrl, thumbnailUrl, 0, board);
    }
}

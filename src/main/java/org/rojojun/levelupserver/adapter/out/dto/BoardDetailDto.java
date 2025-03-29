package org.rojojun.levelupserver.adapter.out.dto;

import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;
import org.rojojun.levelupserver.domain.skill.entity.SkillDifficulty;

import java.time.LocalDateTime;
import java.util.List;

public record BoardDetailDto(
        String writerName,
        String writerPicture,
//        UserLevel writerLevel,
        String content,
        String videoUrl,
        SkillDifficulty skillDifficulty,
        int viewCount,
        int videoViewCount,
        LocalDateTime createdAt,
        List<ReplyDto> replyList
) {

}

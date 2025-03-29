package org.rojojun.levelupserver.adapter.out.dto;

import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.rojojun.levelupserver.domain.skill.entity.SkillDifficulty;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;

import java.time.LocalDateTime;

public record ReplyDto(
        String writerName,
        String writerPicture,
//        UserLevel writerLevel,
        String content,
        LocalDateTime createdAt,
        int score
) {
    public ReplyDto(Reply reply, int score) {
        this(
                reply.getWriter().getNickname(),
                reply.getWriter().getProfilePicture(),
                reply.getContent(),
                reply.getCreatedAt(),
                score);
    }
}

package org.rojojun.levelupserver.adapter.in.dto;

import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.skill.entity.Skill;

public record BoardRequestDto(
        String content,
        String videoUrl,
        SkillRequestDto skillRequestDto
) {
    public Board toEntity(Member member, Skill skill) {
        return Board.of(content, member, skill);
    }
}

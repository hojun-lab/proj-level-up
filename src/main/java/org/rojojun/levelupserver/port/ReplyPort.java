package org.rojojun.levelupserver.port;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.in.dto.ReplyRequestDto;
import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.rojojun.levelupserver.domain.board.service.BoardService;
import org.rojojun.levelupserver.domain.board.service.ReplyService;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.member.service.MemberService;
import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;
import org.rojojun.levelupserver.domain.skill.service.SkillEstimateService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyPort {
    private final ReplyService replyService;
    private final BoardService boardService;
    private final SkillEstimateService skillEstimateService;
    private final MemberService memberService;

    public Long addReply(Long boardId, String email, ReplyRequestDto requestDto) {
        Board board = boardService.getBoardById(boardId);
        Skill skill = board.getSkill();
        Member member = memberService.findMemberBy(email)
                .orElseThrow();

        Reply reply = replyService.saveReply(requestDto.toEntity(member, board));
        SkillEstimate skillEstimate = SkillEstimate.of(skill, member, board.getWriter(), board, reply, requestDto.score());
        skillEstimateService.save(skillEstimate);
        return board.getId();
    }

    public Long editReply(Long replyId, String email, ReplyRequestDto requestDto) {
        Reply reply = replyService.findById(replyId);
        if (reply.getWriter().getEmail() != email) {
            throw new IllegalArgumentException();
        }

        reply.modify(requestDto.content());
        SkillEstimate skillEstimate = skillEstimateService.findBy(reply, email);
        skillEstimate.modify(requestDto.score());

        return reply.getBoard().getId();
    }

    public Long delete(Long replyId, String email) {
        Reply reply = replyService.findById(replyId);
        if (reply.getWriter().getEmail() != email) {
            throw new IllegalArgumentException();
        }

        reply.delete();

        return reply.getBoard().getId();
    }
}

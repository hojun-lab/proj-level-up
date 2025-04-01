package org.rojojun.levelupserver.port;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.in.dto.BoardRequestDto;
import org.rojojun.levelupserver.adapter.out.dto.BoardDetailDto;
import org.rojojun.levelupserver.adapter.out.dto.ReplyDto;
import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;
import org.rojojun.levelupserver.adapter.out.projection.BoardProjection;
import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.rojojun.levelupserver.domain.board.entity.Video;
import org.rojojun.levelupserver.domain.board.service.BoardService;
import org.rojojun.levelupserver.domain.board.service.ReplyService;
import org.rojojun.levelupserver.domain.board.service.VideoService;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.rojojun.levelupserver.domain.member.entity.MemberEstimate;
import org.rojojun.levelupserver.domain.member.service.MemberEstimateService;
import org.rojojun.levelupserver.domain.member.service.MemberService;
import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.rojojun.levelupserver.domain.skill.entity.SkillEstimate;
import org.rojojun.levelupserver.domain.skill.entity.SkillScoreMeta;
import org.rojojun.levelupserver.domain.skill.service.SkillEstimateService;
import org.rojojun.levelupserver.domain.skill.service.SkillService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BoardPort {
    private final MemberService memberService;
    private final BoardService boardService;
    private final SkillService skillService;
    private final VideoService videoService;
    private final ReplyService replyService;
    private final SkillEstimateService skillEstimateService;

    @Transactional(readOnly = true)
    public BoardDetailDto getBoardBy(Long id) {
        Board board = boardService.getBoardById(id);
        Video video = videoService.getVideoBy(board.getId());
        List<Reply> replyList = replyService.getAllBy(board.getId());

        board.increaseView();

        return new BoardDetailDto(
                board.getWriter().getNickname(),
                board.getWriter().getProfilePicture(),
                board.getContent(),
                video.getUrl(),
                board.getSkill().getDifficulty(),
                board.getViewCount(),
                video.getViewCount(),
                board.getCreatedAt(),
                replyList.stream()
                        .map(reply -> new ReplyDto(
                                reply,
                                skillEstimateService.findBy(reply, reply.getWriter().getNickname()).getScore()
                        ))
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public Slice<BoardProjection> getAll(Pageable pageable) {
        return boardService.getAll(pageable);
    }

    @Transactional
    public Long saveBoard(BoardRequestDto boardRequestDto, String email) {
        Member writer = memberService.findMemberBy(email)
                .orElseThrow();
        Skill skill = skillService.findSkillByOr(boardRequestDto.skillRequestDto().skillName())
                .orElse(skillService.saveSkill(boardRequestDto.skillRequestDto().toEntity()));

        evaluateSkillDifficulty(skill);

        Board board = boardService.saveBoard(boardRequestDto.toEntity(writer, skill));
        videoService.saveVideo(Video.of(boardRequestDto.videoUrl(), board));
        skillEstimateService.save(SkillEstimate.ofMyself(skill, writer, writer, board, boardRequestDto.skillRequestDto().score()));

        return board.getId();
    }

    @Transactional
    public Long modifyBoard(Long boardId, String nickname, BoardRequestDto boardRequestDto) {
        Board board = boardService.getBoardById(boardId);
        boolean isExist = videoService.existBy(boardRequestDto.videoUrl());
        if (!Objects.equals(board.getWriter().getNickname(), nickname)) {
            throw new IllegalArgumentException();
        }

        Skill skill = skillService.findSkillByOr(boardRequestDto.skillRequestDto().skillName())
                .orElse(skillService.saveSkill(boardRequestDto.skillRequestDto().toEntity()));
        if (skill.getDifficulty() != boardRequestDto.skillRequestDto().skillDifficulty()) {
            evaluateSkillDifficulty(skill);
        }

        board.modifyContent(boardRequestDto.content());

        if (!isExist) {
            videoService.deleteBy(board);
            videoService.saveVideo(Video.of(boardRequestDto.videoUrl(), board));
        }

        return board.getId();
    }

    @Transactional
    public void deleteBoard(Long boardId, String nickname) {
        Board board = boardService.getBoardById(boardId);
        if (!Objects.equals(board.getWriter().getNickname(), nickname)) {
            throw new IllegalArgumentException();
        }

        board.delete();
    }

    private void evaluateSkillDifficulty(Skill skill) {
        String difficulty = skill.getDifficulty().name().toLowerCase();

        if (skill.getSkillScoreMeta() == null) {
            SkillScoreMeta skillScoreMeta = new SkillScoreMeta();
            skillScoreMeta.increaseCount(difficulty);
            skillScoreMeta.setResult(difficulty);
        } else {
            SkillScoreMeta scoreMeta = skill.getSkillScoreMeta();
            scoreMeta.increaseCount(difficulty);
            String result = scoreMeta.getHighestSkillLevel();
            scoreMeta.setResult(result);
        }
    }
}

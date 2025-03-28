package org.rojojun.levelupserver.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.repository.ReplyRepository;
import org.rojojun.levelupserver.domain.board.entity.BoardStatus;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public List<Reply> getAllBy(Long boardId) {
        return replyRepository.findAllByBoard_IdAndStatus(boardId, BoardStatus.USED)
                .orElseThrow();
    }

    public Reply saveReply(Reply reply) {
        return replyRepository.save(reply);
    }

    public Reply findById(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow();
    }
}
